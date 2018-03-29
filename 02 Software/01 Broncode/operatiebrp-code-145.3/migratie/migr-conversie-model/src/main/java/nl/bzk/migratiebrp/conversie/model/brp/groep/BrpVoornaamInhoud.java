/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de Brp voornaam inhoud.
 *
 * Deze class is immutable en threadsafe.
 */
@Preconditie(SoortMeldingCode.PRE019)
public final class BrpVoornaamInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "voornaam", required = false)
    private final BrpString voornaam;
    @Element(name = "volgnummer", required = false)
    private final BrpInteger volgnummer;

    /**
     * Maakt een BrpVoornaamInhoud object.
     * @param voornaam de voornaam, minimaal 1 karakter, mag geen leading of trailing spaties bevatten. voornaam mag ook null zijn
     * @param volgnummer het volgnummer, bepaald de volgorder van de voornamen
     * @throws IllegalArgumentException als de inhoud van voornaam niet aan de eisen voldoet
     */
    public BrpVoornaamInhoud(
            @Element(name = "voornaam", required = false) final BrpString voornaam,
            @Element(name = "volgnummer", required = false) final BrpInteger volgnummer) {
        final String voornaamAsString = BrpString.unwrap(voornaam);
        if (voornaamAsString != null && voornaamAsString.length() < 1) {
            throw new IllegalArgumentException("De lengte van voornaam moet minimaal 1 zijn");
        }
        this.voornaam = voornaam;
        this.volgnummer = volgnummer;
    }

    @Override
    public void valideer() {
        final String voornaamAsString = BrpString.unwrap(voornaam);
        if (voornaamAsString != null && voornaamAsString.contains(" ")) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE019, this);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(voornaam);
    }

    /**
     * Geef de waarde van voornaam van BrpVoornaamInhoud.
     * @return de waarde van voornaam van BrpVoornaamInhoud
     */
    public BrpString getVoornaam() {
        return voornaam;
    }

    /**
     * Geef de waarde van volgnummer van BrpVoornaamInhoud.
     * @return de waarde van volgnummer van BrpVoornaamInhoud
     */
    public BrpInteger getVolgnummer() {
        return volgnummer;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpVoornaamInhoud)) {
            return false;
        }
        final BrpVoornaamInhoud castOther = (BrpVoornaamInhoud) other;
        return new EqualsBuilder().append(voornaam, castOther.voornaam).append(volgnummer, castOther.volgnummer).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(voornaam).append(volgnummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("voornaam", voornaam)
                .append("volgnummer", volgnummer)
                .toString();
    }
}
