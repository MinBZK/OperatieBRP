/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Typering van Partij..
 */
public enum SoortPartij {

    /** DUMMY. */
    DUMMY(""),
    /** Wetgever. */
    WETGEVER("Wetgever"),
    /** Vertegenwoordiger Regering. */
    VERTEGENWOORDIGER_REGERING("Vertegenwoordiger Regering"),
    /** Gemeente. */
    GEMEENTE("Gemeente"),
    /** Overheidsorgaan. */
    OVERHEIDSORGAAN("Overheidsorgaan"),
    /** Derde. */
    DERDE("Derde"),
    /** Samenwerkingsverband. */
    SAMENWERKINGSVERBAND("Samenwerkingsverband"),
    /** BRP voorziening. */
    BRP_VOORZIENING("BRP voorziening");

    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param naam de naam
     *
     */
    private SoortPartij(final String naam) {
        this.naam = naam;
    }

    /**
     * Naam van de soort partij..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
