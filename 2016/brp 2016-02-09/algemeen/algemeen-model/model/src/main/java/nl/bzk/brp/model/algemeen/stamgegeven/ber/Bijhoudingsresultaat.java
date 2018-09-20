/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * De categorisatie van het resultaat van de bijhouding.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Bijhoudingsresultaat implements SynchroniseerbaarStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Bijhouding direct verwerkt in BRP.
     */
    VERWERKT("Verwerkt", "Bijhouding direct verwerkt in BRP"),
    /**
     * Bijhouding in BRP uitgesteld in verband met fiattering andere partij.
     */
    UITGESTELD("Uitgesteld", "Bijhouding in BRP uitgesteld in verband met fiattering andere partij"),
    /**
     * Bijhouding direct verwerkt in BRP.
     */
    DIRECT("Direct", "Bijhouding direct verwerkt in BRP"),
    /**
     * Bijhouding verwerkt conform plan.
     */
    CONFORM_PLAN("Conform plan", "Bijhouding verwerkt conform plan");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Bijhoudingsresultaat
     * @param omschrijving Omschrijving voor Bijhoudingsresultaat
     */
    private Bijhoudingsresultaat(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Bijhoudingsresultaat.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Bijhoudingsresultaat.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
