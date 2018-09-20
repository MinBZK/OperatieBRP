/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

/**
 * Aangever adreshouding.Identiteit.
 *
 */
public class AangeverAdreshoudingIdentiteit {

    private Short  id;

    private String code;

    private String naam;

    private String omschrijving;

    public Short getId() {
        return id;
    }

    public void setId(final Short id) {
        this.id = id;
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

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
