/**
 * Copyright (C) 2009-2013 Akiban Technologies, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.foundationdb.server.expression.subquery;

import com.foundationdb.qp.operator.API;
import com.foundationdb.qp.operator.Cursor;
import com.foundationdb.qp.operator.Operator;
import com.foundationdb.qp.operator.QueryBindings;
import com.foundationdb.qp.operator.QueryContext;
import com.foundationdb.qp.row.Row;
import com.foundationdb.qp.rowtype.RowType;
import com.foundationdb.server.expression.ExpressionEvaluation;
import com.foundationdb.server.types.ValueSource;

public abstract class SubqueryExpressionEvaluation extends ExpressionEvaluation.Base {

    @Override
    public void of(QueryContext context) {
        this.context = context;
        cursor = null;
    }

    @Override
    public void of(QueryBindings bindings) {
        this.bindings = bindings;
        cursor = null;
    }

    @Override
    public void of(Row row) {
        if (row.rowType() != outerRowType) {
            throw new IllegalArgumentException("wrong row type: " + outerRowType +
                                               " != " + row.rowType());
        }
        outerRow = row;
    }

    @Override
    public final ValueSource eval() {
        bindings.setRow(bindingPosition, outerRow);
        if (cursor == null) {
            cursor = API.cursor(subquery, context, bindings);
        }
        cursor.openTopLevel();
        try {
            return doEval();
        }
        finally {
            cursor.closeTopLevel();
        }
    }

    @Override
    public void destroy()
    {
        if (cursor != null) {
            cursor.destroy();
        }
    }

    // Shareable interface

    @Override
    public void acquire() {
        outerRow.acquire();
    }

    @Override
    public boolean isShared() {
        return outerRow.isShared();
    }

    @Override
    public void release() {
        outerRow.release();
    }

    // for use by subclasses

    protected abstract ValueSource doEval();

    protected QueryContext queryContext() {
        return context;
    }

    protected QueryBindings queryBindings() {
        return bindings;
    }

    protected Row next() {
        Row row = cursor.next();
        if ((row != null) &&
            (row.rowType() != innerRowType)) {
            throw new IllegalArgumentException("wrong row type: " + innerRowType +
                                               " != " + row.rowType());
        }
        return row;
    }

    protected SubqueryExpressionEvaluation(Operator subquery,
                                           RowType outerRowType, RowType innerRowType, 
                                           int bindingPosition) {
        this.subquery = subquery;
        this.outerRowType = outerRowType;
        this.innerRowType = innerRowType;
        this.bindingPosition = bindingPosition;
    }

    private final Operator subquery;
    private final RowType outerRowType;
    private final RowType innerRowType;
    private final int bindingPosition;
    private Cursor cursor;
    private QueryContext context;
    private QueryBindings bindings;
    private Row outerRow;

}