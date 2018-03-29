/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.logging;

import java.util.logging.Level;
import java.util.logging.LogManager;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * De JUL-TO-SLF4J bridge moet 'handmatig' worden aangeroepen om java.util.Logger logging in SLF4J
 * te krijgen.
 */
public class Configurer {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Configureer JUL.
     */
    public final void configure() {
        LOGGER.debug("Configure java.util.logging to log via SLF4j");
        // Remove existing handlers
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // Install SLF4j bridge
        SLF4JBridgeHandler.install();

        // Uit performance oogpunt sturen we alleen INFO logs door
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);
    }
}
