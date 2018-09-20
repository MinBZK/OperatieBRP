/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

/**
 * Het soort abonnement.
 */
public enum SoortAbonnement {
    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0. Deze waarde is dan ook puur een dummy waarde en dient dan ook niet gebruikt te
     * worden.
     */
    DUMMY(null),
    /**
     * Levering.
     */
    LEVERING("Levering");

    /**
     * Constructor voor initialisatie.
     */
    private String naam;

    /**
     * Constructor voor initialisatie.
     *
     * @param naam naam.
     */
    private SoortAbonnement(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }
}
