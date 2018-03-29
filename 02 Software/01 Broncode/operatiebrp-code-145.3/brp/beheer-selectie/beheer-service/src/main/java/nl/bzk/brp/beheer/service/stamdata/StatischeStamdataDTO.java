/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.stamdata;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * DTO voor statische stamdata.
 */
@JsonAutoDetect
public class StatischeStamdataDTO {

    private Integer id;
    private String naam;

    /**
     * Zet het ID
     * @param id het ID
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef het ID.
     * @return het ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de naam.
     * @param naam de naam
     */
    public void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Geef de naam.
     * @return de naam
     */
    public String getNaam() {
        return naam;
    }
}
