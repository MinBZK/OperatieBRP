/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.model;

/**
 * Positie ten opzichte van een populatie.
 */
public enum Populatie {
    /**
     * Komt binnen de populatie.
     */
    BETREEDT("Komt populatie in"),

    /**
     * Valt in de populatie.
     */
    BINNEN("Binnen populatie"),

    /**
     * Verlaat de populatie.
     */
    VERLAAT("Gaat uit populatie"),

    /**
     * Valt buiten de populatie.
     */
    BUITEN("Buiten populatie");

    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param omschrijving de omschrijving
     */
    Populatie(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public final String getOmschrijving() {
        return omschrijving;
    }
}
