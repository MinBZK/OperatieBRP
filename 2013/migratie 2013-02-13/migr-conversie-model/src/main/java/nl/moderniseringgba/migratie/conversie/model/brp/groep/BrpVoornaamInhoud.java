/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.lo3.groep.FoutmeldingUtil;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de Brp voornaam inhoud.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
@Preconditie(Precondities.PRE019)
public final class BrpVoornaamInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "voornaam", required = false)
    private final String voornaam;
    @Element(name = "volgnummer", required = false)
    private final int volgnummer;

    /**
     * Maakt een BrpVoornaamInhoud object.
     * 
     * @param voornaam
     *            de voornaam, minimaal 1 karakter, mag geen leading of trailing spacties bevatten. voornaam mag ook
     *            null zijn
     * @param volgnummer
     *            het volgnummer, bepaald de volgorder van de voornamen
     * @throws IllegalArgumentException
     *             als de inhoud van voornaam niet aan de eisen voldoet
     */
    public BrpVoornaamInhoud(@Element(name = "voornaam", required = false) final String voornaam, @Element(
            name = "volgnummer", required = false) final int volgnummer) {
        if (voornaam != null && voornaam.length() < 1) {
            throw new IllegalArgumentException("De lengte van voornaam moet minimaal 1 zijn");
        }
        if (voornaam != null && voornaam.contains(" ")) {
            FoutmeldingUtil.gooiValidatieExceptie("Er is een spatie aangetroffen in een afzonderlijke voornaam",
                    Precondities.PRE019);
        }
        this.voornaam = voornaam;
        this.volgnummer = volgnummer;
    }

    @Override
    public boolean isLeeg() {
        return voornaam == null;
    }

    /**
     * @return de voornaam of null
     */
    public String getVoornaam() {
        return voornaam;
    }

    public int getVolgnummer() {
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
        return new EqualsBuilder().append(voornaam, castOther.voornaam).append(volgnummer, castOther.volgnummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(voornaam).append(volgnummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("voornaam", voornaam).append("volgnummer", volgnummer).toString();
    }
}
