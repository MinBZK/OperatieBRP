/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * .
 *
 */
public final class MeldingUtil {
    private static final Logger  LOGGER = LoggerFactory.getLogger(MeldingUtil.class);
    /** * . */
    private MeldingUtil() {
    }

    /**
     * Haal op de enum Regel aan de hand van de enum melding code.
     * @param code de melding code
     * @return de regel (DUMMY in geval niet gevonden)
     */
    public static Regel zoekRegeOpviaMeldingCode(final MeldingCode code) {
        for (Regel r : Regel.values()) {
            if (r.getCode().equals(code.getNaam())) {
                return r;
            }
        }
        LOGGER.warn("Kan geen Regel.enum vinden voor code {}.", code);
        return Regel.DUMMY;
    }
}
