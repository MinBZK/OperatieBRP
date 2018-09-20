/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

/**
 * Toegestane MDC velden.
 */
public enum MDCVeld {

    // ************* SYNCHRONISATIE ***************

    /** Bericht referentie. */
    SYNC_BERICHT_REFERENTIE("berichtReferentie"),

    /** Correlatie referentie. */
    SYNC_CORRELATIE_REFERENTIE("correlatieReferentie"),

    // ************* VOISC ***************

    /** Voisc bericht ID. */
    VOISC_BERICHT_ID("berichtId");

    private final String veld;

    /**
     * Constructor.
     *
     * @param veld
     *            Het veld.
     */
    MDCVeld(final String veld) {
        this.veld = veld;
    }

    public String getVeld() {
        return veld;
    }
}
