/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

/**
 * Constanten in gebruik bij definitie Functionele Melding Enums.
 */
public enum FunctioneleMeldingConstanten {
    STARTEN_APPLICATIE("Starten applicatie."),
    APPLICATIE_CORRECT_GESTART("Appliatie correct gestart.");

    final String melding;

    FunctioneleMeldingConstanten(final String melding) {
        this.melding = melding;
    }

    public String getMelding() {
        return melding;
    }
}
