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
 * Deze class representeert de inhoud van de BRP groep Nummerverwijzing.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpNummerverwijzingInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "vorigeAdministratienummer")
    private final BrpString vorigeAdministratienummer;
    @Element(name = "volgendeAdministratienummer")
    private final BrpString volgendeAdministratienummer;
    @Element(name = "vorigeBurgerservicenummer")
    private final BrpString vorigeBurgerservicenummer;
    @Element(name = "volgendeBurgerservicenummer")
    private final BrpString volgendeBurgerservicenummer;

    /**
     * Maakt een BrpNummerverwijzingInhoud object.
     * @param vorigeAdministratienummer het vorige administratienummer, of null
     * @param volgendeAdministratienummer het volgende administratienummer, of null
     * @param vorigeBurgerservicenummer het vorige burgerservicenummer, of null
     * @param volgendeBurgerservicenummer het volgende burgerservicenummer, of null
     */
    public BrpNummerverwijzingInhoud(
            @Element(name = "vorigeAdministratienummer") final BrpString vorigeAdministratienummer,
            @Element(name = "volgendeAdministratienummer") final BrpString volgendeAdministratienummer,
            @Element(name = "vorigeBurgerservicenummer") final BrpString vorigeBurgerservicenummer,
            @Element(name = "volgendeBurgerservicenummer") final BrpString volgendeBurgerservicenummer) {
        this.vorigeAdministratienummer = vorigeAdministratienummer;
        this.volgendeAdministratienummer = volgendeAdministratienummer;
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
    }

    /**
     * Geef de waarde van vorige administratienummer van BrpNummerverwijzingInhoud.
     * @return de waarde van vorige administratienummer van BrpNummerverwijzingInhoud
     */
    public BrpString getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * Geef de waarde van volgende administratienummer van BrpNummerverwijzingInhoud.
     * @return de waarde van volgende administratienummer van BrpNummerverwijzingInhoud
     */
    public BrpString getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Geef de waarde van vorige burgerservicenummer van BrpNummerverwijzingInhoud.
     * @return de waarde van vorige burgerservicenummer van BrpNummerverwijzingInhoud
     */
    public BrpString getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * Geef de waarde van volgende burgerservicenummer van BrpNummerverwijzingInhoud.
     * @return de waarde van volgende burgerservicenummer van BrpNummerverwijzingInhoud
     */
    public BrpString getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie
                .isEenParameterGevuld(volgendeAdministratienummer, vorigeAdministratienummer, volgendeBurgerservicenummer, vorigeBurgerservicenummer);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpNummerverwijzingInhoud)) {
            return false;
        }
        final BrpNummerverwijzingInhoud castOther = (BrpNummerverwijzingInhoud) other;
        return new EqualsBuilder().append(vorigeAdministratienummer, castOther.vorigeAdministratienummer)
                .append(volgendeAdministratienummer, castOther.volgendeAdministratienummer)
                .append(vorigeBurgerservicenummer, castOther.vorigeBurgerservicenummer)
                .append(volgendeBurgerservicenummer, castOther.volgendeBurgerservicenummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(vorigeAdministratienummer)
                .append(volgendeAdministratienummer)
                .append(vorigeBurgerservicenummer)
                .append(volgendeBurgerservicenummer)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("vorigeAdministratienummer", vorigeAdministratienummer)
                .append("volgendeAdministratienummer", volgendeAdministratienummer)
                .append("vorigeBurgerservicenummer", vorigeBurgerservicenummer)
                .append("volgendeBurgerservicenummer", volgendeBurgerservicenummer)
                .toString();
    }
}
