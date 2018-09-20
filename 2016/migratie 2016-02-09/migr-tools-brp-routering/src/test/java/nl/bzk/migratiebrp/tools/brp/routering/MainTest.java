/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.brp.routering;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class MainTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Before
    public void setup() {
        Main.main(null);
    }

    @Test
    public void test() throws InterruptedException {
        Assert.assertNotNull(Main.getContext());

        for (int i = 13; i > 0; i--) {
            LOGGER.info("Sleeping for " + i + " seconds...");
            Thread.sleep(1000);
        }
    }

    @After
    public void destroy() {
        Main.stop();
    }

}
