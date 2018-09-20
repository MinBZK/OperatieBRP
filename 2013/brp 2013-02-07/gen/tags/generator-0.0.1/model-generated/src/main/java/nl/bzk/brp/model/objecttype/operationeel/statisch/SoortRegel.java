/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Het soort regel..
 */
public enum SoortRegel {

    /** DUMMY. */
    DUMMY("", ""),
    /** Systeemregel. */
    SYSTEEMREGEL("Systeemregel", "Regels die moeten gelden voor een goede technische werking van het stelsel, doordat ze verweven zijn met de structuur van het systeem."),
    /** Bedrijfsregel. */
    BEDRIJFSREGEL("Bedrijfsregel", "Regels die moeten gelden voor een goede inhoudelijke werking van het stelsel.");

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
    private SoortRegel(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Naam van de soort regel..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Omschrijving van de soort regel..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
