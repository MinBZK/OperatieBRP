/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * De class representeert een BRP Reden verlies Nederlandschap code.
 * 
 * Deze code verwijst naar de BRP stamtabel Reden Verlies NL Nationaliteit. Dit is geen enum maar een class omdat het
 * hier een dynamische stamtabel betreft.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class BrpRedenVerliesNederlandschapCode implements BrpAttribuut {

    @Text
    private final BigDecimal naam;

    /**
     * Maakt een BrpRedenVerkrijgingNederlandschapCode object.
     * 
     * @param naam
     *            de naam, deze waarde mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public BrpRedenVerliesNederlandschapCode(@Text final BigDecimal naam) {
        if (naam == null) {
            throw new NullPointerException("naam mag niet null zijn");
        }
        this.naam = naam;
    }

    /**
     * @return the naam
     */
    public BigDecimal getNaam() {
        return naam;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpRedenVerliesNederlandschapCode)) {
            return false;
        }
        final BrpRedenVerliesNederlandschapCode castOther = (BrpRedenVerliesNederlandschapCode) other;
        return new EqualsBuilder().append(naam, castOther.naam).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(naam).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("code", naam).toString();
    }
}
