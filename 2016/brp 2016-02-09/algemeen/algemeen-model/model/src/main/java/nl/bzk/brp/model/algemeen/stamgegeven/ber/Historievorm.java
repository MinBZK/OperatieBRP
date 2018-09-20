/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

import javax.annotation.Generated;

/**
 * De categorisatie van de historievormen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Historievorm {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Geen historie; alleen actuele gegevens leveren.
     */
    GEEN("Geen", "Geen historie; alleen actuele gegevens leveren"),
    /**
     * De actuele gegevens en de materiele historie hiervan leveren.
     */
    MATERIEEL("Materieel", "De actuele gegevens en de materiele historie hiervan leveren"),
    /**
     * De actuele gegevens en zowel de materiele als de formele historie hiervan leveren.
     */
    MATERIEEL_FORMEEL("MaterieelFormeel", "De actuele gegevens en zowel de materiele als de formele historie hiervan leveren");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Historievorm
     * @param omschrijving Omschrijving voor Historievorm
     */
    private Historievorm(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Historievorm.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Historievorm.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
