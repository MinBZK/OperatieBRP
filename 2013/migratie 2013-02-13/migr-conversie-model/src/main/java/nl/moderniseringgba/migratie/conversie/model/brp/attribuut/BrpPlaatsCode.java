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
 * Deze class representeert een BRP plaats code.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class BrpPlaatsCode implements BrpAttribuut {

    private static final String STANDAARD_WAARDE = ".";

    @Text
    private final String naam;

    /**
     * Maakt een BrpPlaatsCode object.
     * 
     * @param naam
     *            de naam die binnen de BRP een plaats uniek identificeert, mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public BrpPlaatsCode(@Text final String naam) {
        if (naam == null) {
            throw new NullPointerException();
        }
        this.naam = naam;
    }

    /**
     * @return the code
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return true als deze brp plaatscode een standaard waarde bevat, anders false.
     */
    public boolean bevatStandaardWaarde() {
        return STANDAARD_WAARDE.equals(naam);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpPlaatsCode)) {
            return false;
        }
        final BrpPlaatsCode castOther = (BrpPlaatsCode) other;
        return new EqualsBuilder().append(naam, castOther.naam).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(naam).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("naam", naam).toString();
    }
}
