/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP partij code;
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public final class BrpPartijCode implements BrpAttribuut, Serializable {

    /**
     * In het geval dat de partij voor de migratie voorziening niet bekend is wordt deze waarde gebruikt (DEF0044).
     */
    public static final BrpPartijCode MIGRATIEVOORZIENING = new BrpPartijCode("Migratievoorziening", null);

    private static final long serialVersionUID = 1L;

    private final String naam;

    private final Integer gemeenteCode;

    /**
     * Maakt een BrpPartijCode object.
     * 
     * @param naam
     *            de naam die binnen BRP een partij uniek identficeert, mag niet null zijn als gemeenteCode null is
     * @param gemeenteCode
     *            de gemeentecode die binnen BRP een partij uniek identificeert, mag niet null zijn als naam null is
     * @throws NullPointerException
     *             als naam en gemmeenteCode null zijn
     */
    public BrpPartijCode(@Element(name = "naam", required = false) final String naam, @Element(name = "gemeenteCode",
            required = false) final Integer gemeenteCode) {
        if (naam == null && gemeenteCode == null) {
            throw new NullPointerException("naam en gemeentecode mogen niet beiden null zijn");
        }
        this.naam = naam;
        this.gemeenteCode = gemeenteCode;
    }

    /**
     * @return de naam
     */
    @Element(name = "naam", required = false)
    public String getNaam() {
        return naam;
    }

    /**
     * @return de gemeente code
     */
    @Element(name = "gemeenteCode", required = false)
    public Integer getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Aangepaste vergelijking. Als de gemeenteCodes gevuld en gelijk zijn wordt het object als gelijk beschouwd
     * (onafhankelijk of de namen gelijk zijn). Als de gemeenteCodes niet gevuld zjin wordt vergeleken op naam.
     * 
     * @param other
     *            te vergelijken object
     * @return true, als de objecten functioneel gelijk zijn
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpPartijCode)) {
            return false;
        }
        final BrpPartijCode castOther = (BrpPartijCode) other;

        if (gemeenteCode != null) {
            return gemeenteCode.equals(castOther.gemeenteCode);
        } else {
            return new EqualsBuilder().append(gemeenteCode, castOther.gemeenteCode).append(naam, castOther.naam)
                    .isEquals();
        }
    }

    @Override
    public int hashCode() {
        if (gemeenteCode != null) {
            return gemeenteCode.hashCode();
        } else {
            return new HashCodeBuilder().append(gemeenteCode).append(naam).toHashCode();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("naam", naam)
                .append("gemeenteCode", gemeenteCode).toString();
    }

}
