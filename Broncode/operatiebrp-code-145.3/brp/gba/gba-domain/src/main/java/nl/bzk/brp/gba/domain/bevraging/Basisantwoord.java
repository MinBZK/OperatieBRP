/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.gba.domain.Antwoord;

/**
 * Basis vraag (voor adres- en persoonantwoorden).
 */
@JsonInclude(Include.NON_NULL)
public class Basisantwoord implements Antwoord {

    @JsonProperty
    private String foutreden;

    @JsonProperty
    private String inhoud;

    /**
     * Geef foutreden.
     * @return foutreden
     */
    public final String getFoutreden() {
        return foutreden;
    }

    /**
     * Zet foutreden.
     * @param foutreden foutreden
     */
    public final void setFoutreden(final String foutreden) {
        this.inhoud = null;
        this.foutreden = foutreden;
    }

    /**
     * Geef inhoud.
     * @return inhoud
     */
    public final String getInhoud() {
        return inhoud;
    }

    /**
     * Zet inhoud.
     * @param inhoud inhoud
     */
    public final void setInhoud(final String inhoud) {
        this.foutreden = null;
        this.inhoud = inhoud;
    }
}
