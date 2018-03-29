/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.autorisatie;

import java.sql.SQLException;
import nl.bzk.algemeenbrp.test.dal.DBUnitBrpUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.dal.TestCasusFactory;
import nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie;
import nl.bzk.migratiebrp.test.dal.runner.TestRunner;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.junit.runner.RunWith;
import org.springframework.context.support.GenericXmlApplicationContext;

@RunWith(TestRunner.class)
public abstract class ConversieTestConfiguratie extends TestConfiguratie {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public boolean useMemoryDS() {
        return false;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFactory()
     */
    @Override
    public TestCasusFactory getCasusFactory() {
        LOG.info("Starting application context");

        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.registerShutdownHook();

        if (useMemoryDS()) {
            context.getEnvironment().setActiveProfiles("memoryDS");
        } else {
            context.getEnvironment().setActiveProfiles("localDS");
        }
        context.load("classpath:test-beans.xml");
        context.refresh();

        LOG.info("Preparing database");
        if (useMemoryDS()) {
            final DBUnitBrpUtil dbUtil = context.getBean(DBUnitBrpUtil.class);
            try {
                final IDatabaseConnection connection = dbUtil.createConnection();
                try {
                    dbUtil.resetSequences(connection);
                    dbUtil.truncateTables(connection);
                    dbUtil.insert(connection, this.getClass(), "/sql/data/brpStamgegevens-kern.xml");
                    dbUtil.insert(connection, this.getClass(), "/sql/data/brpStamgegevens-autaut.xml");
                    // dbUtil.insert(connection, this.getClass(), "/sql/data/brpStamgegevens-lev.xml");
                    dbUtil.insert(connection, this.getClass(), "/sql/data/brpStamgegevens-conv.xml");
                    dbUtil.insert(connection, this.getClass(), "/sql/data/brpStamgegevens-verconv.xml");
                    dbUtil.setStamgegevensSequences(connection);
                } finally {
                    connection.close();
                }
            } catch (final DatabaseUnitException e) {
                throw new RuntimeException("Kan database niet intialiseren.", e);
            } catch (final SQLException e) {
                throw new RuntimeException("Kan database niet intialiseren.", e);
            }
        }
        LOG.info("Returning new test casus factory...");
        return new ConversieTestCasusFactory(context.getAutowireCapableBeanFactory(), getTestSkipper());
    }
}
