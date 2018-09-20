/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import java.io.Serializable;
import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert een BRP gemeente code;
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public final class BrpGemeenteCode implements BrpAttribuut, Serializable {

    private static final long serialVersionUID = 1L;

    @Text
    private final BigDecimal code;

    /**
     * Maakt een BrpGemeenteCode object.
     * 
     * @param code
     *            de code die binnen BRP een gemeente uniek identficeert, mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public BrpGemeenteCode(@Text final BigDecimal code) {
        if (code == null) {
            throw new NullPointerException();
        }
        this.code = code;
    }

    /**
     * @return the code
     */
    public BigDecimal getCode() {
        return code;
    }

    /**
     * @return formatted code as string.
     */
    public String getFormattedStringCode() {
        return code == null ? "" : String.format("%04d", code.intValue());
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpGemeenteCode)) {
            return false;
        }
        final BrpGemeenteCode castOther = (BrpGemeenteCode) other;
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
