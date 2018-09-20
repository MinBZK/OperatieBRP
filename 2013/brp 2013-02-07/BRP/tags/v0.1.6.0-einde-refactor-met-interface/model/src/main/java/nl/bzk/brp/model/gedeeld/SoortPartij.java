/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

/**
 * Soort partij.
 */
public enum SoortPartij {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
    DUMMY(null),
    /** Derde. */
    DERDE("Derde"),
    /** Gemeente. */
    GEMEENTE("Gemeente"),
    /** Vertegenwoordiger regering. */
    VERTEGENWOORDIGER_REGERING("Vertegenwoordiger Regering"),
    /** Overheidsorgaan. */
    OVERHEIDSORGAAN("Overheidsorgaan"),
    /** Samenwerkingsverband. */
    SAMENWERKINGSVERBAND("Samenwerkingsverband"),
    /** Wetgever. */
    WETGEVER("Wetgever");

    private final String naam;

    /**
     * Constructor.
     *
     * @param naam soort partij naam
     */
    private SoortPartij(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

}
