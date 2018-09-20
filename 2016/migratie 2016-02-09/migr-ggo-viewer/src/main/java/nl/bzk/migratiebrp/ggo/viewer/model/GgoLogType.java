/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

/**
 * Log type.
 */
public enum GgoLogType {
    /** Verwerking. */
    VERWERKING("Verwerking"),
    /** Preconditie. */
    PRECONDITIE("Preconditie"),
    /** Bijzondere situatie. */
    BIJZONDERE_SITUATIE("Bijzondere situatie"),
    /** Structuur/domein regel. */
    STRUCTUUR("Structuur"),
    /** Syntax. */
    SYNTAX("Syntax"),
    /** BCM. */
    BCM("BCM");

    private final String label;

    private GgoLogType(final String label) {
        this.label = label;
    }

    /**
     * Geef de waarde van label.
     *
     * @return de label
     */
    public String getLabel() {
        return label;
    }
}
