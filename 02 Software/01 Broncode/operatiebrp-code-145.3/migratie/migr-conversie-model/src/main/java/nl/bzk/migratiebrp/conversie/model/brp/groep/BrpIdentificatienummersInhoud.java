/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep Persoon / identificatienummers.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpIdentificatienummersInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "administratienummer")
    private final BrpString administratienummer;
    @Element(name = "burgerservicenummer")
    private final BrpString burgerservicenummer;

    /**
     * Maakt een BrpIdentificatienummersInhoud object.
     * @param administratienummer het BRP administratienummer, mag null zijn
     * @param burgerservicenummer het BRP burgerservicenummer, mag null zijn
     */
    public BrpIdentificatienummersInhoud(@Element(name = "administratienummer") final BrpString administratienummer,
                                         @Element(name = "burgerservicenummer") final BrpString burgerservicenummer) {
        this.administratienummer = administratienummer;
        this.burgerservicenummer = burgerservicenummer;
    }

    /**
     * Geef de waarde van administratienummer van BrpIdentificatienummersInhoud.
     * @return de waarde van administratienummer van BrpIdentificatienummersInhoud
     */
    public BrpString getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Geef de waarde van burgerservicenummer van BrpIdentificatienummersInhoud.
     * @return de waarde van burgerservicenummer van BrpIdentificatienummersInhoud
     */
    public BrpString getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isAttribuutGevuld(administratienummer) && !BrpValidatie.isAttribuutGevuld(burgerservicenummer);
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
                .append(burgerservicenummer, castOther.burgerservicenummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(administratienummer).append(burgerservicenummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("administratienummer", administratienummer)
                .append("burgerservicenummer", burgerservicenummer)
                .toString();
    }
}
