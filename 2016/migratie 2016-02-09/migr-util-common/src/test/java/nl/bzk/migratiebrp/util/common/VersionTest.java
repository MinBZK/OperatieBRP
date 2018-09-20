/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.Test;

public class VersionTest {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void test() {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.voisc", "migr-voisc-runtime");

        LOG.info("Application: " + version.toString());
        LOG.info("Components:\n" + version.toDetailsString());
    }
}
