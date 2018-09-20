/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

/**
 * Woonplaats.
 */
public class Plaats {

    private String code;

    private String naam;

    /**
     * Constructor voor een dashboard woonplaats op basis van een logische woonplaats.
     * @param woonplaats logische woonplaats
     */
    public Plaats(final nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats woonplaats) {
        if (woonplaats.getCode() != null) {
            code = woonplaats.getCode().toString();
        }
        naam = woonplaats.getNaam().getWaarde();
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

}
