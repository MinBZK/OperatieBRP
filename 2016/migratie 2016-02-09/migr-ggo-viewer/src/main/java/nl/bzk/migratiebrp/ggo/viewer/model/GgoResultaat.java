/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

/**
 * Ggo resultaat.
 */
public enum GgoResultaat {
    /** Inlezen GBA-PL niet geslaagd. */
    INLEZEN_GBA_PL_NIET_GESLAAGD("Inlezen GBA-PL: niet geslaagd"),
    /** Conversie niet geslaagd. */
    CONVERSIE_NIET_GESLAAGD("Conversie (GBA > BRP): niet geslaagd"),
    /** Conversie gelukt. */
    CONVERSIE_GESLAAGD("Conversie (GBA > BRP): geslaagd"),
    /** Terugconversie niet geslaagd. */
    TERUGCONVERSIE_NIET_GESLAAGD("Terugconversie (GBA > BRP > GBA): niet geslaagd"),
    /** Terugconversie geslaagd. */
    TERUGCONVERSIE_GESLAAGD("Terugconversie (GBA > BRP > GBA): geslaagd");

    private final String label;

    private GgoResultaat(final String label) {
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
