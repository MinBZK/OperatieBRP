/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale;

import java.lang.reflect.Method;
import javax.jms.Destination;
import javax.sql.DataSource;
import nl.bzk.brp.dataaccess.test.DBUnitLoaderTestExecutionListener;
import nl.bzk.brp.dataaccess.test.Data;
import nl.bzk.brp.dataaccess.test.DataSourceProvider;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext.HierarchyMode;
import org.springframework.test.context.TestContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Data(resources = {"classpath:/data/stamgegevensStatisch.xml",
                   "classpath:/data/testdata-autaut.xml",
                   "classpath:/data/blob/dataset.xml",
                   "classpath:/data/blob/cleanup.xml" })
public abstract class AbstractIntegratieTest implements DataSourceProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static GenericXmlApplicationContext centraleContext;
    private static GenericXmlApplicationContext brokerContext;
    private static GenericXmlApplicationContext testContext;

    private DBUnitLoaderTestExecutionListener dbUnitListener;
    private DataSource dbUnitDataSource;
    private TestContext dbUnitTestContext;

    @BeforeClass
    public static void setupBroker() {
        // Message broker
        brokerContext = new GenericXmlApplicationContext();
        brokerContext.load("classpath:activemq-broker-context.xml");
        brokerContext.refresh();

        // Gba centrale
        centraleContext = new GenericXmlApplicationContext();
        centraleContext.load("classpath:gba-centrale-context.xml");
        centraleContext.refresh();

        // Gba centrale
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
        testContext.refresh();
    }

    @AfterClass
    public static void destroyBroker() {
        if (testContext != null) {
            try {
                testContext.close();
            } catch (final Exception e) {
                LOGGER.warn("Probleem bij sluiten TEST context", e);
            }
        }
        if (centraleContext != null) {
            try {
                centraleContext.close();
            } catch (final Exception e) {
                LOGGER.warn("Probleem bij sluiten CENTRALE context", e);
            }
        }
        if (brokerContext != null) {
            try {
                brokerContext.close();
            } catch (final Exception e) {
                LOGGER.warn("Probleem bij sluiten BROKER context", e);
            }
        }
    }

    protected void cleanQueues(final JmsTemplate jmsTemplate, final Destination... queues) {
        LOGGER.info("Cleaning queues");
        final long receiveTimeout = jmsTemplate.getReceiveTimeout();

        try {
            jmsTemplate.setReceiveTimeout(1000);

            for (final Destination queue : queues) {
                while (jmsTemplate.receive(queue) != null) {
                    LOGGER.info("Queue {} contained a message ...", queue);
                }
            }
        } finally {
            jmsTemplate.setReceiveTimeout(receiveTimeout);
        }
    }

    @Before
    public void wireTestInstance() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public DataSource getDataSource() {
        return dbUnitDataSource;
    }

    @Before
    public void setupDbUnit() {
        LOGGER.info("LADEN DATABASE");
        dbUnitDataSource = testContext.getBean("lezenSchrijvenDataSource", DataSource.class);
        final PlatformTransactionManager transactionManager = testContext.getBean("lezenSchrijvenTransactionManager", PlatformTransactionManager.class);

        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);
        transaction.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(final TransactionStatus status) {
                dbUnitTestContext = new DummyTestContext(AbstractIntegratieTest.this, testContext);
                dbUnitListener = new DBUnitLoaderTestExecutionListener();
                dbUnitListener.prepareTestInstance(dbUnitTestContext);
                return null;
            }
        });
    }

    @After
    public void destroyDbUnit() throws Exception {
        dbUnitListener.afterTestClass(dbUnitTestContext);
    }

    private static final class DummyTestContext implements TestContext {
        private static final long serialVersionUID = 1L;

        private final Object testInstance;
        private final ApplicationContext applicationContext;

        public DummyTestContext(final Object testInstance, final ApplicationContext applicationContext) {
            this.testInstance = testInstance;
            this.applicationContext = applicationContext;
        }

        @Override
        public String[] attributeNames() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getAttribute(final String arg0) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasAttribute(final String arg0) {
            throw new UnsupportedOperationException();

        }

        @Override
        public Object removeAttribute(final String arg0) {
            throw new UnsupportedOperationException();

        }

        @Override
        public void setAttribute(final String arg0, final Object arg1) {
            throw new UnsupportedOperationException();

        }

        @Override
        public ApplicationContext getApplicationContext() {
            return applicationContext;
        }

        @Override
        public Class<?> getTestClass() {
            return testInstance.getClass();
        }

        @Override
        public Throwable getTestException() {
            throw new UnsupportedOperationException();

        }

        @Override
        public Object getTestInstance() {
            return testInstance;
        }

        @Override
        public Method getTestMethod() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void markApplicationContextDirty(final HierarchyMode arg0) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateState(final Object arg0, final Method arg1, final Throwable arg2) {
            throw new UnsupportedOperationException();
        }

    }

}
