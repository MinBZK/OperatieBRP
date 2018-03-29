/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.vrijbericht;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vrij bericht verzoek (vanuit GBA naar BRP).
 */
@JsonInclude(Include.NON_NULL)
public final class VrijBerichtOpdracht {

    @JsonProperty
    private String verzendendePartijCode;

    @JsonProperty
    private String ontvangendePartijCode;

    @JsonProperty
    private String bericht;

    @JsonProperty
    private String referentienummer;

    public String getVerzendendePartijCode() {
        return verzendendePartijCode;
    }

    public void setVerzendendePartijCode(final String verzendendePartijCode) {
        this.verzendendePartijCode = verzendendePartijCode;
    }

    public String getOntvangendePartijCode() {
        return ontvangendePartijCode;
    }

    public void setOntvangendePartijCode(final String ontvangendePartijCode) {
        this.ontvangendePartijCode = ontvangendePartijCode;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    public String getReferentienummer() {
        return referentienummer;
    }

    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }
}
