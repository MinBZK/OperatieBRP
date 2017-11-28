/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.vrijbericht;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.gba.domain.Antwoord;

/**
 * Vrij bericht antwoord.
 */
@JsonInclude(Include.NON_NULL)
public final class VrijBerichtAntwoord implements Antwoord {

    @JsonProperty
    private boolean geslaagd;

    @JsonProperty
    private String referentienummer;

    /**
     * Geef indicatie of verwerking geslaagd is.
     * @return de indicatie
     */
    public boolean isGeslaagd() {
        return geslaagd;
    }

    /**
     * Zet de indicatie of verwerking geslaagd is.
     * @param geslaagd de indicatie
     */
    public void setGeslaagd(final boolean geslaagd) {
        this.geslaagd = geslaagd;
    }

    /**
     * Geef het referentienummer.
     * @return het referentienummer.
     */
    public String getReferentienummer() {
        return referentienummer;
    }

    /**
     * Zet het referentienummer.
     * @param referentienummer Het te zetten referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }

}
