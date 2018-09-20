/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Deze class definieert de interface voor het GBA Afnemersindicatie bericht naar de BRP.
 *
 * Moet inhoudelijk overeenkomen met
 * <code>nl.bzk.migratiebrp.synchronisatie.runtime.bericht.brp.AfnemerindicatieOnderhoudAntwoord</code> uit het
 * Migratievoorzieningen model.
 */
@JsonInclude(Include.NON_NULL)
public final class AfnemerindicatieOnderhoudAntwoord {

    @JsonProperty
    private Character foutcode;

    @JsonProperty
    private String referentienummer;

    /**
     * Geef foutcode.
     *
     * @return de foutcode.
     */
    public Character getFoutcode() {
        return foutcode;
    }

    /**
     * Zet de foutcode.
     *
     * @param foutcode
     *            De te zetten foutcode
     */
    public void setFoutcode(final Character foutcode) {
        this.foutcode = foutcode;
    }

    /**
     * Geef het referentienummer.
     *
     * @return het referentienummer.
     */
    public String getReferentienummer() {
        return referentienummer;
    }

    /**
     * Zet het referentienummer.
     *
     * @param referentienummer
     *            Het te zetten referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }

}
