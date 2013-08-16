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
package com.foundationdb.server.types3;

import com.foundationdb.server.types3.pvalue.PValueSource;
import com.foundationdb.util.AkibanAppender;

public interface TClassFormatter {
    /** Format value in <code>source</code> in a type-specific way. */
    public void format(TInstance instance, PValueSource source, AkibanAppender out);
    /** Format value in <code>source</code> as a SQL literal. */
    public void formatAsLiteral(TInstance instance, PValueSource source, AkibanAppender out);
    /** Format value in <code>source</code> as a JSON value, including any necessary quotes. */
    public void formatAsJson(TInstance instance, PValueSource source, AkibanAppender out);
}