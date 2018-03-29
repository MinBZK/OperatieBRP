/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;

import nl.bzk.algemeenbrp.test.dal.DBUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest.PortInitializer;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * Abstract supertype voor alle testen die een Spring-context nodig hebben. Hierdoor staat de
 * verwijzing naar de spring-test-context xml-configuratie op een plek.
 */
@Rollback(value = false)
@Transactional(transactionManager = "syncDalTransactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DBUnit.TestExecutionListener.class, TransactionalTestExecutionListener.class})
@ContextConfiguration(locations = {"classpath:synchronisatie-beans-test.xml"}, initializers = {PortInitializer.class})
public abstract class AbstractDatabaseTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    protected EntityManager em;

    /**
     * De DBUnit verify() dient maar een keer uitgevoerd te worden. Of tijdens de
     * {@linkplain @After}, of tijdens de {@linkplain @AfterTransaction}. Dit laatste is nodig omdat
     * dan zeker is dat de transactie is gecommit.
     */
    private boolean transactional;
    private boolean rolledback;

    @Inject()
    @Named("syncDalTransactionManager")
    private JtaTransactionManager syncDalTransactionManager;

    @Before
    public void before() throws SQLException, DatabaseUnitException {
        DBUnit.setInMemory();
        final IDatabaseConnection connection = DBUnit.createConnection();
        DBUnit.truncateTables(connection);
        DBUnit.resetSequences(connection);
        DBUnit.insertBefore(connection);
        DBUnit.setStamgegevensSequences(connection);
        connection.close();
        final Session s = (Session) em.getDelegate();
        final SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictQueryRegions();
    }

    @After
    public void after() throws DatabaseUnitException, SQLException, SystemException {
        if (!transactional) {
            DBUnit.verify();
        } else {
            if (syncDalTransactionManager.getTransaction(null).isRollbackOnly()) {
                // stop het uitvoeren van AfterTransaction zodat de foutmelding die tot de rollback
                // heeft geleid niet
                // wordt ingeslikt
                rolledback = true;
            }
        }
    }

    @BeforeTransaction
    public void beforeTransaction() {
        transactional = true;
    }

    @AfterTransaction
    public void afterTransaction() throws DatabaseUnitException, SQLException, SystemException {
        if (!rolledback) {
            DBUnit.verify();
        }
        transactional = false;
    }

    /**
     * Dynamisch poorten voor resources bepalen.
     */
    public static final class PortInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(final ConfigurableApplicationContext applicationContext) {
            final Properties properties = new Properties();
            try (ServerSocket socket = new ServerSocket(0)) {
                final int port = socket.getLocalPort();
                LOG.info("Configuring database to port: {}", port);
                properties.setProperty("test.database.port", Integer.toString(port));
            } catch (final IOException e) {
                throw new IllegalStateException("Kon geen port voor de database bepalen", e);
            }
            applicationContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("ports", properties));
        }
    }
}
