/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

/**
 * De stap waarin een fout gemeld is.
 */
public enum GgoStap {
    /**
     * Lo3.
     */
    LO3("Lo3"),
    /**
     * BRP.
     */
    BRP("BRP"),
    /**
     * Terugconversie.
     */
    TERUGCONVERSIE("Terugconversie"),
    /**
     * Vergelijking.
     */
    VERGELIJKING("Vergelijking"),
    /**
     * Precondities.
     */
    PRECONDITIES("Precondities"),
    /**
     * BCM.
     */
    BCM("BCM");

    private final String label;

    private GgoStap(final String label) {
        this.label = label;
    }

    /**
     * Geef de waarde van label.
     * @return de label
     */
    public String getLabel() {
        return label;
    }
}
