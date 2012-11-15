/**
 * END USER LICENSE AGREEMENT (“EULA”)
 *
 * READ THIS AGREEMENT CAREFULLY (date: 9/13/2011):
 * http://www.akiban.com/licensing/20110913
 *
 * BY INSTALLING OR USING ALL OR ANY PORTION OF THE SOFTWARE, YOU ARE ACCEPTING
 * ALL OF THE TERMS AND CONDITIONS OF THIS AGREEMENT. YOU AGREE THAT THIS
 * AGREEMENT IS ENFORCEABLE LIKE ANY WRITTEN AGREEMENT SIGNED BY YOU.
 *
 * IF YOU HAVE PAID A LICENSE FEE FOR USE OF THE SOFTWARE AND DO NOT AGREE TO
 * THESE TERMS, YOU MAY RETURN THE SOFTWARE FOR A FULL REFUND PROVIDED YOU (A) DO
 * NOT USE THE SOFTWARE AND (B) RETURN THE SOFTWARE WITHIN THIRTY (30) DAYS OF
 * YOUR INITIAL PURCHASE.
 *
 * IF YOU WISH TO USE THE SOFTWARE AS AN EMPLOYEE, CONTRACTOR, OR AGENT OF A
 * CORPORATION, PARTNERSHIP OR SIMILAR ENTITY, THEN YOU MUST BE AUTHORIZED TO SIGN
 * FOR AND BIND THE ENTITY IN ORDER TO ACCEPT THE TERMS OF THIS AGREEMENT. THE
 * LICENSES GRANTED UNDER THIS AGREEMENT ARE EXPRESSLY CONDITIONED UPON ACCEPTANCE
 * BY SUCH AUTHORIZED PERSONNEL.
 *
 * IF YOU HAVE ENTERED INTO A SEPARATE WRITTEN LICENSE AGREEMENT WITH AKIBAN FOR
 * USE OF THE SOFTWARE, THE TERMS AND CONDITIONS OF SUCH OTHER AGREEMENT SHALL
 * PREVAIL OVER ANY CONFLICTING TERMS OR CONDITIONS IN THIS AGREEMENT.
 */

package com.akiban.ais.model;

import com.akiban.ais.util.TableChange;

import java.util.List;

/** Attached to a <code>UserTable</code> on which <code>ALTER</code> has been performed
 * by <a href="http://www.percona.com/doc/percona-toolkit/2.1/pt-online-schema-change.html">pt-online-schema-change.html</a>. 
 * The same alter will be done to the <code>originalName</code> when a
 * <code>RENAME</code> is requested after all the row copying.
 */
public class PendingOSC
{
    private String originalName, currentName;
    private List<TableChange> columnChanges, indexChanges;

    public PendingOSC(String originalName, List<TableChange> columnChanges, List<TableChange> indexChanges) {
        this.originalName = originalName;
        this.columnChanges = columnChanges;
        this.indexChanges = indexChanges;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public List<TableChange> getColumnChanges() {
        return columnChanges;
    }

    public List<TableChange> getIndexChanges() {
        return indexChanges;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(originalName);
        if (currentName != null)
            str.append("=").append(currentName);
        str.append(columnChanges).append(indexChanges);
        return str.toString();
    }    
}