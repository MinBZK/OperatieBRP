/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De richting gezien vanuit de centrale voorzieningen van de BRP..
 */
public enum Richting {

    /** DUMMY. */
    DUMMY("", ""),
    /** Ingaand. */
    INGAAND("Ingaand", "Naar de centrale voorzieningen van de BRP toe."),
    /** Uitgaand. */
    UITGAAND("Uitgaand", "Van de centrale voorzieningen van de BRP af.");

    /** naam. */
    private String naam;
    /** omschrijving. */
    private String omschrijving;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param omschrijving de omschrijving
     *
     */
    private Richting(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De naam van de Richting..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving van de Richting..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
