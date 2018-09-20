/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Het soort abonnement..
 */
public enum SoortAbonnement {

    /** DUMMY. */
    DUMMY(""),
    /** Levering. */
    LEVERING("Levering");

    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param naam de naam
     *
     */
    private SoortAbonnement(final String naam) {
        this.naam = naam;
    }

    /**
     * Naam van het Soort abonnement..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
