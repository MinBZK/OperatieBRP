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
 * Typering van Partij.
 * @version 1.0.18.
 */
public enum SoortPartij {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
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

    /** Naam van de soort partij. */
    private final String naam;

    /**
     * Constructor.
     *
     * @param naam Naam van de soort partij.
     *
     */
    private SoortPartij(final String naam) {
        this.naam = naam;
    }

    /**
     * @return Naam van de soort partij.
     */
    public String getNaam() {
        return naam;
    }

}
