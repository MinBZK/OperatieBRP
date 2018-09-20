/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model;

/**
 * De VerschilType enum bestaat uit de types voortkomend uit de verschil analyse. De kortere 'code' wordt gebruikt in de
 * vingerafdruk.
 */
public enum VerschilType {
    /** Geen unieke match. */
    NON_UNIQUE_MATCHED("NON_UNIQUE_MATCHED"),
    /** Toegevoegd. */
    ADDED("A"),
    /** Verwijderd. */
    REMOVED("R"),
    /** Aangepast. */
    MODIFIED("M"),
    /** Niet als actueel terug geconverteerd. */
    NOT_ACTUAL("NOT_ACTUAL"),
    /** Match gevonden. */
    MATCHED("MATCHED");

    private final String code;

    private VerschilType() {
        code = this.toString();
    }

    private VerschilType(final String code) {
        this.code = code;
    }

    /**
     * Geef de waarde van code.
     *
     * @return code
     */
    public String getCode() {
        return code;
    }
}
