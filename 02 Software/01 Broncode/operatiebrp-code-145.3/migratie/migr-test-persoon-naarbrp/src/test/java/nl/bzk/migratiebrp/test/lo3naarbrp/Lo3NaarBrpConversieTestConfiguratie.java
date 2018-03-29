/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.lo3naarbrp;

import nl.bzk.migratiebrp.test.dal.TestCasusFactory;
import nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie;
import nl.bzk.migratiebrp.test.dal.runner.TestRunner;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.context.support.GenericXmlApplicationContext;

@RunWith(TestRunner.class)
public abstract class Lo3NaarBrpConversieTestConfiguratie extends TestConfiguratie {

    private static final Logger LOG = LoggerFactory.getLogger();

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFactory()
     */
    @Override
    public TestCasusFactory getCasusFactory() {
        LOG.info("Starting application context");

        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.registerShutdownHook();

        if (useMemoryDS()) {
            LOG.info("Running with HSQLDB");
            context.getEnvironment().setActiveProfiles("memoryDS");
        }

        context.load("classpath:test-beans.xml");
        context.refresh();

        LOG.info("Returning new LO3 -> BRP conversie testcasus factory...");
        return new Lo3NaarBrpConversieTestCasusFactory(context);
    }
}
