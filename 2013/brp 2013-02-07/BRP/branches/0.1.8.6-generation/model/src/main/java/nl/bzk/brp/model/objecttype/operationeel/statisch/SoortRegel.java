/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Het soort regel.
 * @version 1.0.18.
 */
public enum SoortRegel {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** Systeemregel. */
    SYSTEEMREGEL("Systeemregel", "Regels die moeten gelden voor een goede technische werking van het stelsel, doordat ze verweven zijn met de structuur van het systeem."),
    /** Bedrijfsregel. */
    BEDRIJFSREGEL("Bedrijfsregel", "Regels die moeten gelden voor een goede inhoudelijke werking van het stelsel.");

    /** Naam van de soort regel. */
    private final String naam;
    /** Omschrijving van de soort regel. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param naam Naam van de soort regel.
     * @param omschrijving Omschrijving van de soort regel.
     *
     */
    private SoortRegel(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return Naam van de soort regel.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return Omschrijving van de soort regel.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
