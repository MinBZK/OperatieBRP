/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterVerzoekBericht;
import nl.bzk.migratiebrp.isc.runtime.handler.JmsUtil;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker.JbpmExecution;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal.Strategie;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import org.hibernate.jdbc.ReturningWork;
import org.jbpm.JbpmContext;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class AbstractIntegrationTest {

    private JmsTemplate jmsTemplate;

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext routeringContext;
    private static GenericXmlApplicationContext registerContext;
    private static GenericXmlApplicationContext testContext;

    @Autowired
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    private static final Logger LOG = LoggerFactory.getLogger();

    protected static final String LEVERING_QUEUE = "levering";
    protected static final String SYNC_ANTWOORD_QUEUE = "sync.antwoord";
    protected static final String SYNC_VERZOEK_QUEUE = "sync.verzoek";
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

        // Start Register (SYNC)
        registerContext = new GenericXmlApplicationContext();
        registerContext.load(
            "classpath:test-embedded-register.xml",
            "classpath:test-autorisatieregister-dummy.xml",
            "classpath:test-gemeenteregister-dummy.xml");
        registerContext.refresh();

        // Create test context
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test_context.xml");
        testContext.refresh();
    }

    @Before
    public void injectDependencies() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Assert.assertNotNull(jmsTemplate);
    }

    @AfterClass
    public static void stopTestContext() {
        if (testContext != null) {
            try {
                testContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten TEST context", e);
            }
        }
        if (registerContext != null) {
            try {
                registerContext.close();
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
    public void start() {
        LOG.info("Before test; starting context");
        Main.main(null);
        LOG.info("Before test; context started");
    }

    @After
    public void shutdown() throws InterruptedException {
        if (Main.getContext() != null) {
            LOG.info("After test; closing context");
            Main.getContext().close();
            LOG.info("After test; context closed");
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Bericht> T expectMessage(final String destinationName, final Class<T> clazz) {
        jmsTemplate.setReceiveTimeout(10000);
        Message message;
        try {
            message = JmsUtil.leesMessage(jmsTemplate.receive(destinationName));
        } catch (
            JmsException
            | JMSException e)
        {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Geen bericht ontvangen", message);
        final Bericht bericht = leesBericht(destinationName, message);
        Assert.assertTrue(clazz.isAssignableFrom(bericht.getClass()));
        return (T) bericht;
    }

    private Bericht leesBericht(final String destinationName, final Message message) {
        final BerichtFactory berichtFactory;
        switch (destinationName) {
            case VOISC_ONTVANGST_QUEUE:
            case VOISC_VERZENDEN_QUEUE:
                berichtFactory = new Lo3BerichtFactory();
                break;
            case SYNC_ANTWOORD_QUEUE:
            case SYNC_VERZOEK_QUEUE:
                berichtFactory = SyncBerichtFactory.SINGLETON;
                break;
            // case VOISC_INBOUND_QUEUE:
            // case VOISC_OUTBOUND_QUEUE:
            // berichtFactory = VoiscBerichtFactory.SINGLETON;
            // break;
            default:
                throw new IllegalArgumentException("Queue '" + destinationName + "' onbekend.");
        }

        final Bericht result = berichtFactory.getBericht(message.getContent());
        result.setMessageId(message.getMessageId());
        result.setCorrelationId(message.getCorrelatieId());
        if (result instanceof Lo3Bericht) {
            ((Lo3Bericht) result).setBronGemeente(message.getOriginator());
            ((Lo3Bericht) result).setDoelGemeente(message.getRecipient());
        }
        return result;

    }

    protected void putMessage(final String destinationName, final Bericht bericht) {
        putMessage(destinationName, bericht, null, null);
    }

    protected void putMessage(final String destinationName, final Bericht bericht, final Long msSequenceNumber, final Long administratieveHandelingId) {
        jmsTemplate.send(destinationName, new MessageCreator() {
            @Override
            public javax.jms.Message createMessage(final Session session) throws JMSException {
                try {
                    final TextMessage message = session.createTextMessage(bericht.format());
                    message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
                    message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelationId());
                    if (bericht instanceof Lo3Bericht) {
                        message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, ((Lo3Bericht) bericht).getBronGemeente());
                        message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, ((Lo3Bericht) bericht).getDoelGemeente());
                    }
                    if (msSequenceNumber != null) {
                        message.setStringProperty(JMSConstants.BERICHT_MS_SEQUENCE_NUMBER, msSequenceNumber.toString());
                    }
                    if (administratieveHandelingId != null) {
                        message.setStringProperty(JMSConstants.BERICHT_ADMINISTRATIEVE_HANDELING_ID, administratieveHandelingId.toString());
                    }

                    return message;
                } catch (final BerichtInhoudException e) {
                    throw new JmsException("Probleem bij formatten bericht", e) {
                        private static final long serialVersionUID = 1L;
                    };
                }
            }
        });
    }

    /* *** CONVENIENCE *************************************************************************************** */

    protected <T> T executeInSqlInJbpmInTransaction(final ReturningWork<T> work) {
        return executeInJbpmInTransaction(new JbpmExecution<T>() {
            @Override
            public T doInContext(final JbpmContext jbpmContext) {
                final org.hibernate.Session session =
                        (org.hibernate.Session) jbpmContext.getServices().getPersistenceService().getCustomSession(org.hibernate.Session.class);
                return session.doReturningWork(work);
            }
        });
    }

    protected <T> T executeInJbpmInTransaction(final JbpmExecution<T> work) {
        return executeInTransaction(new TransactionCallback<T>() {

            @Override
            public T doInTransaction(final TransactionStatus status) {
                final JbpmInvoker jbpmInvoker = Main.getContext().getBean(JbpmInvoker.class);
                return jbpmInvoker.executeInContext(work);
            }
        });
    }

    protected <T> T executeInTransaction(final TransactionCallback<T> work) {
        final PlatformTransactionManager transactionManager = Main.getContext().getBean(PlatformTransactionManager.class);
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(work);
    }

    /* *** PROCESSEN *************************************************************************************** */

    public void controleerAlleProcessenBeeindigd() {
        try {
            new Herhaal(1000, 10, Strategie.REGELMATIG).herhaal(new Runnable() {
                @Override
                public void run() {
                    final int aantal = bepaalAantalLopendeProcessen();
                    if (aantal != 0) {
                        throw new IllegalStateException("Aantal lopende processen was niet 0, maar " + aantal);
                    }
                }
            });
        } catch (final HerhaalException e) {
            Assert.fail("Er zijn nog lopende processen.");
        }
    }

    protected int bepaalAantalLopendeProcessen() {
        return executeInSqlInJbpmInTransaction(new ReturningWork<Integer>() {
            @Override
            public Integer execute(final Connection connection) throws SQLException {
                final ResultSet result = connection.createStatement().executeQuery("SELECT count(*) FROM jbpm_processinstance WHERE end_ IS NULL");
                if (!result.next()) {
                    throw new IllegalArgumentException("Query op lopende processen gaf geen resultaat!");
                }

                return result.getInt(1);
            }
        });
    }

    /* *** BERICHTEN *************************************************************************************** */

    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    /* *** GEMEENTE REGISTER ******************************************************************************* */

    protected LeesGemeenteRegisterAntwoordBericht maakLeesGemeenteRegisterAntwoordBericht(
        final LeesGemeenteRegisterVerzoekBericht leesGemeenteRegisterVerzoek)
    {
        final LeesGemeenteRegisterAntwoordType type = new LeesGemeenteRegisterAntwoordType();
        type.setStatus(StatusType.OK);
        type.setGemeenteRegister(new GemeenteRegisterType());
        type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0599", "580599", null));
        type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0429", "580429", null));
        type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0699", "580699", 20090101));
        type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0717", "580717", null));

        final LeesGemeenteRegisterAntwoordBericht result = new LeesGemeenteRegisterAntwoordBericht(type);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(leesGemeenteRegisterVerzoek.getMessageId());

        return result;
    }

    private GemeenteType maakGemeenteType(final String gemeenteCode, final String partijCode, final Integer datumOvergangNaarBrp) {
        final GemeenteType gemeente = new GemeenteType();
        gemeente.setGemeenteCode(gemeenteCode);
        gemeente.setPartijCode(partijCode);
        gemeente.setDatumBrp(datumOvergangNaarBrp == null ? null : BigInteger.valueOf(datumOvergangNaarBrp));
        return gemeente;
    }

    /* *** JMX ******************************************************************************* */

    @Autowired
    private MBeanServerConnection iscMBeanServerConnection;

    protected void triggerOpschoner() {
        try {
            iscMBeanServerConnection.invoke(new ObjectName("nl.bzk.migratiebrp.isc:name=OPSCHONER"), "opschonen", null, null);
        } catch (
            JMException
            | IOException e)
        {
            throw new IllegalArgumentException("Kan opschonen niet triggeren via JMX", e);
        }
    }

    protected void triggerTelling() {
        try {
            iscMBeanServerConnection.invoke(new ObjectName("nl.bzk.migratiebrp.isc:name=TELLING"), "bijwerkenTellingen", null, null);
        } catch (
            JMException
            | IOException e)
        {
            throw new IllegalArgumentException("Kan telling niet triggeren via JMX", e);
        }
    }
}
