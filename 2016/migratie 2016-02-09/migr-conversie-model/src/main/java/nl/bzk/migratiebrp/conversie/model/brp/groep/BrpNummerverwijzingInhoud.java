/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep Nummerverwijzing.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpNummerverwijzingInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "vorigeAdministratienummer", required = false)
    private final BrpLong vorigeAdministratienummer;
    @Element(name = "volgendeAdministratienummer", required = false)
    private final BrpLong volgendeAdministratienummer;
    @Element(name = "vorigeBurgerservicenummer", required = false)
    private final BrpInteger vorigeBurgerservicenummer;
    @Element(name = "volgendeBurgerservicenummer", required = false)
    private final BrpInteger volgendeBurgerservicenummer;

    /**
     * Maakt een BrpNummerverwijzingInhoud object.
     * 
     * @param vorigeAdministratienummer
     *            het vorige administratienummer, of null
     * @param volgendeAdministratienummer
     *            het volgende administratienummer, of null
     * @param vorigeBurgerservicenummer
     *            het vorige burgerservicenummer, of null
     * @param volgendeBurgerservicenummer
     *            het volgende burgerservicenummer, of null
     */
    public BrpNummerverwijzingInhoud(@Element(name = "vorigeAdministratienummer", required = false) final BrpLong vorigeAdministratienummer, @Element(
            name = "volgendeAdministratienummer", required = false) final BrpLong volgendeAdministratienummer, @Element(
            name = "vorigeBurgerservicenummer", required = false) final BrpInteger vorigeBurgerservicenummer, @Element(
            name = "volgendeBurgerservicenummer", required = false) final BrpInteger volgendeBurgerservicenummer)
    {
        this.vorigeAdministratienummer = vorigeAdministratienummer;
        this.volgendeAdministratienummer = volgendeAdministratienummer;
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
    }

    /**
     * Geef de waarde van vorige administratienummer.
     *
     * @return the vorigeAdministratienummer, of null
     */
    public BrpLong getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * Geef de waarde van volgende administratienummer.
     *
     * @return the volgendeAdministratienummer, of null
     */
    public BrpLong getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Geef de waarde van vorige burgerservicenummer.
     *
     * @return the vorige burgerservicenummer
     */
    public BrpInteger getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * Geef de waarde van volgende burgerservicenummer.
     *
     * @return the volgende burgerservicenummer
     */
    public BrpInteger getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /**
     * Geef de leeg.
     *
     * @return true als alle velden leeg zijn
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(
            volgendeAdministratienummer,
            vorigeAdministratienummer,
            volgendeBurgerservicenummer,
            vorigeBurgerservicenummer);
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
