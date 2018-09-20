/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.brp.routering;

import java.util.concurrent.CountDownLatch;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Keep alive.
 */
public final class KeepAlive implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final CountDownLatch STOP = new CountDownLatch(1);

    @Override
    public void run() {
        try {
            STOP.await();
        } catch (final InterruptedException e) {
            // Ignore
            LOGGER.info("Interrupted", e);
        }
    }

    /**
     * Stop.
     */
    public void stop() {
        STOP.countDown();
    }

}
