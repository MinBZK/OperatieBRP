/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.concurrent.CountDownLatch;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Keep alive.
 */
public final class KeepAlive implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final CountDownLatch stop = new CountDownLatch(1);

    /**
     * Start.
     */
    @Override
    public void run() {
        try {
            stop.await();
        } catch (final InterruptedException e) {
            // Ignore
            LOGGER.info("Interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Stop.
     */
    public void stop() {
        stop.countDown();
    }

}
