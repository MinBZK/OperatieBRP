/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Helper voor logging van expressies.
 */
public final class ExpressieLogger {

    /**
     * Indicatie of debug logging enabled is.
     */
    public static final boolean IS_DEBUG_ENABLED = LoggerFactory.getLogger().isDebugEnabled();

    private ExpressieLogger() {
    }
}
