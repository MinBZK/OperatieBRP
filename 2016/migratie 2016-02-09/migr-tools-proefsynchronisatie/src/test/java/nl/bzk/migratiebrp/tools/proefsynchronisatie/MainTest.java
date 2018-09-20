/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie;

import org.junit.Test;

public class MainTest {

    private static final String DUMMY_PARAM = "test";
    private static final String PROEF_SYNC_TEST_BEANS = "proefSynchronisatie-test-beans.xml";
    private static final String TIMEOUT = "1000";
    private static final String TIMEOUT_PARAM = "-timeout";
    private static final String BATCH_SIZE = "100000";
    private static final String BATCH_SIZE_PARAM = "-batchSize";
    private static final String VANAF_TIMESTAMP = "2013-08-17 09:46:00.787";
    private static final String VANAF_DATUM = "2013-08-17";
    private static final String DATUM_VANAF_PARAM = "-datumVanaf";
    private static final String CREATE_PARAM = "-create";
    private static final String PROPERTIES = "src/test/resources/proef-sync-test.properties";
    private static final String CONFIG_PARAM = "-config";

    @Test
    public void testMainHappy() {
        final String[] args =
                new String[] {CONFIG_PARAM,
                              PROPERTIES,
                              CREATE_PARAM,
                              DATUM_VANAF_PARAM,
                              VANAF_DATUM,
                              BATCH_SIZE_PARAM,
                              BATCH_SIZE,
                              TIMEOUT_PARAM,
                              TIMEOUT, };
        Main.setSpringConfig(new String[] {PROEF_SYNC_TEST_BEANS });
        Main.main(args);
    }

    @Test
    public void testMainHappyTs() {
        final String[] args =
                new String[] {CONFIG_PARAM,
                              PROPERTIES,
                              CREATE_PARAM,
                              DATUM_VANAF_PARAM,
                              VANAF_TIMESTAMP,
                              BATCH_SIZE_PARAM,
                              BATCH_SIZE,
                              TIMEOUT_PARAM,
                              TIMEOUT, };
        Main.setSpringConfig(new String[] {PROEF_SYNC_TEST_BEANS });
        Main.main(args);
    }

    @Test
    public void testMainBatchGrootteDefaultTimeout() {
        final String[] args = new String[] {CONFIG_PARAM, PROPERTIES, BATCH_SIZE_PARAM, "10000", };
        Main.setSpringConfig(new String[] {PROEF_SYNC_TEST_BEANS });
        Main.main(args);
    }

    @Test
    public void testMainTimeoutDefaultBatchGrootte() {
        final String[] args = new String[] {CONFIG_PARAM, PROPERTIES, TIMEOUT_PARAM, TIMEOUT, };
        Main.setSpringConfig(new String[] {PROEF_SYNC_TEST_BEANS });
        Main.main(args);
    }

    @Test
    public void testMainGeenGetalBatchGrootte() {
        final String[] args = new String[] {CONFIG_PARAM, PROPERTIES, BATCH_SIZE_PARAM, DUMMY_PARAM, };
        Main.setSpringConfig(new String[] {PROEF_SYNC_TEST_BEANS, });
        Main.main(args);
    }

    @Test
    public void testMainGeenGetalTimeout() {
        final String[] args = new String[] {CONFIG_PARAM, PROPERTIES, TIMEOUT_PARAM, DUMMY_PARAM, };
        Main.setSpringConfig(new String[] {PROEF_SYNC_TEST_BEANS });
        Main.main(args);
    }

    @Test
    public void testMainWrongConfig() {
        final String[] args = new String[] {CONFIG_PARAM, "proef-sync-test.properties" };
        Main.setSpringConfig(new String[] {PROEF_SYNC_TEST_BEANS });
        Main.main(args);
    }

    @Test
    public void testMainNoConfig() {
        final String[] args = new String[] {};
        Main.setSpringConfig(new String[] {PROEF_SYNC_TEST_BEANS });
        Main.main(args);
    }

}
