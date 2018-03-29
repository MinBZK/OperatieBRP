/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken;

import nl.bzk.algemeenbrp.test.dal.DBUnitBrpUtil;
import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.dal.TestCasusFactory;
import nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie;
import nl.bzk.migratiebrp.test.dal.runner.TestRunner;
import org.junit.runner.RunWith;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

@RunWith(TestRunner.class)
public abstract class ConversieTestConfiguratie extends TestConfiguratie {

    private static final Logger LOG = LoggerFactory.getLogger();

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFactory()
     */
    @Override
    public TestCasusFactory getCasusFactory() {
        LOG.info("\n#####\n#####\n##### VERSIES\n#####\n#####");
        LOG.info("{}", Version.readVersion("nl.bzk.migratiebrp.test", "migr-test-sleutelrubrieken").toDetailsString());

        if (useMemoryDS()) {
            LOG.info("\n#####\n#####\n##### Starting application context (database)\n#####\n#####");
            final GenericXmlApplicationContext databaseContext = new GenericXmlApplicationContext("classpath:test-database.xml");
            databaseContext.registerShutdownHook();

            final DBUnitBrpUtil dbUtil = databaseContext.getBean(DBUnitBrpUtil.class);
            LOG.info("Resetting DB");
            dbUtil.resetDB(this.getClass(), LOG);
        }

        LOG.info("\n#####\n#####\n##### Starting application context (migratie)\n#####\n#####");
        final GenericXmlApplicationContext migratieContext = new GenericXmlApplicationContext();
        if (useMemoryDS()) {
            migratieContext.getEnvironment().setActiveProfiles("memoryDS");
        }
        migratieContext.load("classpath:test-migratie-beans.xml");
        migratieContext.refresh();
        migratieContext.registerShutdownHook();

        LOG.info("\n#####\n#####\n##### Starting application context (brp datasource)");
        final GenericXmlApplicationContext brpDatasourceContext = new GenericXmlApplicationContext();
        if (useMemoryDS()) {
            brpDatasourceContext.getEnvironment().setActiveProfiles("memoryDS");
        }
        brpDatasourceContext.load("classpath:test-brp-datasource.xml");
        brpDatasourceContext.refresh();
        brpDatasourceContext.registerShutdownHook();

        LOG.info("\n#####\n#####\n##### Starting application context (brp levering)");
        final GenericXmlApplicationContext brpLeveringContext = new GenericXmlApplicationContext();
        if (useMemoryDS()) {
            brpLeveringContext.getEnvironment().setActiveProfiles("memoryDS");
        }
        brpLeveringContext.load("classpath:test-brp-levering-beans.xml");
        brpLeveringContext.refresh();
        brpLeveringContext.registerShutdownHook();

        LOG.info("\n#####\n#####\n##### Starting application context (brp bijhouding)");
        final XmlWebApplicationContext brpBijhoudingContext = new XmlWebApplicationContext();
        brpBijhoudingContext.setParent(brpDatasourceContext);
        if (useMemoryDS()) {
            brpBijhoudingContext.getEnvironment().setActiveProfiles("memoryDS");
        }
        brpBijhoudingContext.setConfigLocation("classpath:test-brp-bijhouding-beans.xml");
        brpBijhoudingContext.refresh();
        brpBijhoudingContext.registerShutdownHook();

        LOG.info("\n#####\n#####\n##### Returning new test casus factory...");
        return new ConversieTestCasusFactory(
                migratieContext.getAutowireCapableBeanFactory(),
                brpLeveringContext.getAutowireCapableBeanFactory(),
                brpBijhoudingContext.getAutowireCapableBeanFactory(),
                getTestSkipper());
    }
}
