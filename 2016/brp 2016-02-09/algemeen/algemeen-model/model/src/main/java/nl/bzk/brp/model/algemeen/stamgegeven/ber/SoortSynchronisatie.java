/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * De vorm van synchronisatie.
 *
 * In kader van het synchroniseren van gegevens kan gebruik worden gemaakt van mutatieberichten of van vulberichten.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortSynchronisatie implements SynchroniseerbaarStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Synchronisatie gewijzigde gegevens in de vorm van een mutatiebericht (delta levering).
     */
    MUTATIEBERICHT("Mutatiebericht", "Synchronisatie gewijzigde gegevens in de vorm van een mutatiebericht (delta levering)"),
    /**
     * Synchronisatie (gewijzigde) gegevens in de vorm van een volledigbericht met daarin de volledige persoonsgegevens.
     */
    VOLLEDIGBERICHT("Volledigbericht", "Synchronisatie (gewijzigde) gegevens in de vorm van een volledigbericht met daarin de volledige persoonsgegevens");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortSynchronisatie
     * @param omschrijving Omschrijving voor SoortSynchronisatie
     */
    private SoortSynchronisatie(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort synchronisatie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort synchronisatie.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
