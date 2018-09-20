/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;


/**
 * Classificatie van Autorisatiebesluit.
 */
public enum SoortAutorisatieBesluit {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0.
     */
    DUMMY(null, null),
    /**
     * Leverings autorisatie.
     */
    LEVERINGSAUTORISATIE("Leveringsautorisatie", "00000000");

    private String naam;
    private String omschrijving;

    /**
     * Constructor voor de initialisatie van de enumeratie.
     *
     * @param naam de naam.
     * @param omschrijving de omschrijving van het Soort autorisatiebesluit.
     */
    private SoortAutorisatieBesluit(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
