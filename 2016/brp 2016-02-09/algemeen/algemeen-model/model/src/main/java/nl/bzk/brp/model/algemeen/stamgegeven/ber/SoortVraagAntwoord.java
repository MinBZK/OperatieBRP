/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

/**
 * Categorisatie van Vraag en Antwoord
 */
public enum SoortVraagAntwoord {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Vraag.
     */
    VRAAG("Vraag"),
    /**
     * Antwoord.
     */
    ANTWOORD("Antwoord");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortVraagAntwoord
     */
    private SoortVraagAntwoord(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Soort Vraag antwoord.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
