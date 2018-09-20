/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert een BRP land code.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpLandCode implements BrpAttribuut {

    /** Nederland. */
    public static final BrpLandCode NEDERLAND = new BrpLandCode(new Integer("6030"));

    @Text
    private final Integer code;

    /**
     * Maakt een BrpLandCode object.
     * 
     * @param code
     *            de BRP code die een land identificeert binnen BRP, mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public BrpLandCode(@Text final Integer code) {
        if (code == null) {
            throw new NullPointerException();
        }
        this.code = code;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpLandCode)) {
            return false;
        }
        final BrpLandCode castOther = (BrpLandCode) other;
        return new EqualsBuilder().append(code, castOther.code).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("code", code).toString();
    }
}
