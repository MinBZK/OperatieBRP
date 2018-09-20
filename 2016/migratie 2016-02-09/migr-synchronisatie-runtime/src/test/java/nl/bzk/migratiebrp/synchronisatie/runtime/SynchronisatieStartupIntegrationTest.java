/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.Test;

public class SynchronisatieStartupIntegrationTest extends AbstractIntegrationTest {

    public SynchronisatieStartupIntegrationTest() {
        super("synchronisatie");
    }

    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void startup() throws InterruptedException {
        for (int i = 13; i > 0; i--) {
            LOG.info("Sleeping for " + i + " seconds...");
            Thread.sleep(1000);
        }
    }
}
