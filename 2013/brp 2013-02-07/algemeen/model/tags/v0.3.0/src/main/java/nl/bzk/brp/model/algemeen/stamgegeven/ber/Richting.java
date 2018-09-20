/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

/**
 * De richting gezien vanuit de centrale voorzieningen van de BRP.
 *
 * De centrale voorzieningen wisselen berichten uit. Berichten die vanuit de centrale voorzieningen van de BRP
 * "naar buiten gaan", hebben de richting uitgaand; berichten die de BRP binnenkomen hebben de richting "ingaand".
 *
 *
 *
 */
public enum Richting {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Naar de centrale voorzieningen van de BRP toe..
     */
    INGAAND("Ingaand", "Naar de centrale voorzieningen van de BRP toe."),
    /**
     * Van de centrale voorzieningen van de BRP af..
     */
    UITGAAND("Uitgaand", "Van de centrale voorzieningen van de BRP af.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Richting
     * @param omschrijving Omschrijving voor Richting
     */
    private Richting(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Richting.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Richting.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
