/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import java.util.UUID;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class AbstractIntegrationTest {

    private JmsTemplate jmsTemplate;
    private JmsTemplate brpJmsTemplate;

    private final String modus;

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext routeringContext;
    private static GenericXmlApplicationContext brpRouteringContext;
    private GenericXmlApplicationContext testContext;

    public AbstractIntegrationTest(final String modus) {
        this.modus = modus;
    }

    @Autowired
    @Named(value = "connectionFactory")
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(10000);
    }

    @Autowired
    @Named(value = "brpConnectionFactory")
    public void setBrpConnectionFactory(final ConnectionFactory connectionFactory) {
        brpJmsTemplate = new JmsTemplate(connectionFactory);
        brpJmsTemplate.setReceiveTimeout(10000);
    }

    private static final Logger LOG = LoggerFactory.getLogger();

    protected static final String ARCHIVERING_QUEUE = "archivering";
    protected static final String SYNC_VERZOEK_QUEUE = "sync.verzoek";
    protected static final String SYNC_ANTWOORD_QUEUE = "sync.antwoord";
    protected static final String VOISC_ONTVANGST_QUEUE = "voisc.ontvangst";
    protected static final String VOISC_VERZENDEN_QUEUE = "voisc.verzenden";

    @BeforeClass
    public static void startDependencies() {
        // Start DB
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.refresh();

        // Start RouteringCentrale
        routeringContext = new GenericXmlApplicationContext();
        routeringContext.load("classpath:routering-runtime.xml");
        routeringContext.refresh();

        // Start RouteringCentrale
        brpRouteringContext = new GenericXmlApplicationContext();
        brpRouteringContext.load("classpath:test-embedded-brp.xml");
        brpRouteringContext.refresh();

    }

    @Before
    public void injectDependencies() {
        // Create test context
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test_context.xml");
        testContext.refresh();

        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Assert.assertNotNull(jmsTemplate);
        Assert.assertNotNull(brpJmsTemplate);
    }

    @After
    public void teardownTextContext() throws InterruptedException {
        if (testContext != null) {
            try {
                testContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten TEST context", e);
            }
        }
    }

    @AfterClass
    public static void stopTestContext() {

        if (brpRouteringContext != null) {
            try {
                brpRouteringContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten REGISTER context", e);
            }
        }
        if (routeringContext != null) {
            try {
                routeringContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten ROUTERING context", e);
            }
        }
        if (databaseContext != null) {
            try {
                databaseContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten DATABASE context", e);
            }
        }
    }

    @Before
    public void start() throws Exception {
        LOG.info("Before test; starting context");

        Main.main(new String[] {"-modus", modus });
        LOG.info("Before test; context started");
    }

    @After
    public void shutdown() throws InterruptedException {
        if (Main.getContext() != null) {
            LOG.info("After test; closing context");
            Main.stop();
            LOG.info("After test; context closed");
        }
    }

    protected void putMessage(final String destinationName, final String bericht, final String messageId) {
        jmsTemplate.send(destinationName, new MessageCreator() {
            @Override
            public javax.jms.Message createMessage(final Session session) throws JMSException {
                final TextMessage message = session.createTextMessage(bericht);
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);

                return message;
            }
        });
    }

    protected Message expectMessage(final String destinationName) {
        Message message;
        try {
            message = jmsTemplate.receive(destinationName);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Bericht verwacht", message);
        return message;
    }

    protected Message expectBrpMessage(final String destinationName) {
        Message message;
        try {
            message = brpJmsTemplate.receive(destinationName);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Bericht verwacht", message);
        return message;
    }

    protected String getContent(final Message message) throws JMSException {
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("BerichtType niet ondersteund");
        }
    }

    /* *** CONVENIENCE *************************************************************************************** */

    protected <T> T executeInTransaction(final TransactionCallback<T> work) {
        final PlatformTransactionManager transactionManager = Main.getContext().getBean(PlatformTransactionManager.class);
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(work);
    }

    /* *** BERICHTEN *************************************************************************************** */

    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

}
