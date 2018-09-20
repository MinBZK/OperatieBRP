/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep Persoon / identificatienummers.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 * 
 */
public final class BrpIdentificatienummersInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "administratienummer", required = false)
    private final Long administratienummer;
    @Element(name = "burgerservicenummer", required = false)
    private final Long burgerservicenummer;

    /**
     * Maakt een BrpIdentificatienummersInhoud object.
     * 
     * @param administratienummer
     *            het BRP administratienummer, mag null zijn
     * @param burgerservicenummer
     *            het BRP burgerservicenummer, mag null zijn
     */
    public BrpIdentificatienummersInhoud(
            @Element(name = "administratienummer", required = false) final Long administratienummer,
            @Element(name = "burgerservicenummer", required = false) final Long burgerservicenummer) {
        this.administratienummer = administratienummer;
        this.burgerservicenummer = burgerservicenummer;
    }

    /**
     * @return het administratienummer of null
     */
    public Long getAdministratienummer() {
        return administratienummer;
    }

    /**
     * @return het burgerservicenummer of null
     */
    public Long getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * @return true als administratienummer en burgerservicenummer beide null zijn, anders false
     */
    @Override
    public boolean isLeeg() {
        return administratienummer == null && burgerservicenummer == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpIdentificatienummersInhoud)) {
            return false;
        }
        final BrpIdentificatienummersInhoud castOther = (BrpIdentificatienummersInhoud) other;
        return new EqualsBuilder().append(administratienummer, castOther.administratienummer)
                .append(burgerservicenummer, castOther.burgerservicenummer).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(administratienummer).append(burgerservicenummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("administratienummer", administratienummer)
                .append("burgerservicenummer", burgerservicenummer).toString();
    }
}
