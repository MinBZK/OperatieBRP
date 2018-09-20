/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.model;

/**
 * Het soort abonnement.
 */
public enum SoortAbonnement {
    /**
     * Enum ordinal's beginnen bij 0, maar de id's in de database bij 1.
     */
    ORDINAL_NUL_NIET_GEBRUIKEN(null),
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
