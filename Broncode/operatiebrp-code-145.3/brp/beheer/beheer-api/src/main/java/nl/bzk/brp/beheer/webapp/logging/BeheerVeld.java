/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.logging;

import nl.bzk.algemeenbrp.util.common.logging.MDCVeld;

/**
 * Beheer velden.
 */
public enum BeheerVeld implements MDCVeld {

    /** Veld waarin de naam van de applicatie wordt gecommuniceerd ten behoeve van MDC logging. */
    MDC_APPLICATIE_NAAM("applicatie");

    private final String veld;

    /**
     * Constructor.
     *
     * @param veld
     *            Het veld.
     */
    BeheerVeld(final String veld) {
        this.veld = veld;
    }

    @Override
    public String getVeld() {
        return veld;
    }
}
