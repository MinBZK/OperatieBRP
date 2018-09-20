/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.taglet.bedrijfsregel;

/**
 * Representatie van een bedrijfsregel referentie in een javadoc commentaar.
 */
public class BedrijfsregelReference {

    private String ruleId;

    private String packageName;

    private String typeName;

    private String memberName;

    private int    line;

    private int    column;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(final String ruleId) {
        this.ruleId = ruleId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(final String memberName) {
        this.memberName = memberName;
    }

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(final int column) {
        this.column = column;
    }

}
