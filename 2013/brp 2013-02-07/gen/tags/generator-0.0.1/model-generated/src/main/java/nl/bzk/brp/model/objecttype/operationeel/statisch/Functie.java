/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Functie is een door een Partij uitvoerbare activiteit die ten dienste staat aan het vervullen van één of meer rollen van die Partij..
 */
public enum Functie {

    /** DUMMY. */
    DUMMY(""),
    /** Dummy vulling. */
    DUMMY_VULLING("Dummy vulling");

    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param naam de naam
     *
     */
    private Functie(final String naam) {
        this.naam = naam;
    }

    /**
     * Naam van de functie..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
