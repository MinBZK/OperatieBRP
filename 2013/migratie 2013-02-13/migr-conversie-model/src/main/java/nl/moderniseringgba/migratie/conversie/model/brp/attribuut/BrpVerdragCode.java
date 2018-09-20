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
 * Deze class representeert een BRP verdrag. Dit is geen enum maar een class omdat het hier een dynamische stamtabel
 * betreft.
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public final class BrpVerdragCode implements BrpAttribuut {

    @Text
    private final String omschrijving;

    /**
     * Maakt een BrpVerdragCode object.
     * 
     * @param code
     *            de code die binnen BRP een verdrag uniek identficeert, mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public BrpVerdragCode(@Text final String code) {
        if (code == null) {
            throw new NullPointerException();
        }
        omschrijving = code;
    }

    /**
     * @return the omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpVerdragCode)) {
            return false;
        }
        final BrpVerdragCode castOther = (BrpVerdragCode) other;
        return new EqualsBuilder().append(omschrijving, castOther.omschrijving).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(omschrijving).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("code", omschrijving).toString();
    }
}
