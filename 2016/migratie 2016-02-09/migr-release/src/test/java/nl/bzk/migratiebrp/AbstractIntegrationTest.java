/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp;

import java.io.Closeable;
import java.util.Map;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.register.client.RegisterService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.runtime.VoiscMailbox;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import nl.bzk.migratiebrp.voisc.spd.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import nl.bzk.migratiebrp.voisc.spd.impl.SslConnectionFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class AbstractIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext jtaContext;
    private static GenericXmlApplicationContext mailboxContext;
    private static GenericXmlApplicationContext routeringContext;
    private static GenericXmlApplicationContext brpContext;
    private static GenericXmlApplicationContext voiscContext;
    private static GenericXmlApplicationContext iscContext;
    private static GenericXmlApplicationContext syncContext;
    private static GenericXmlApplicationContext testContext;

    @BeforeClass
    public static void startDependencies() {
        databaseContext = startContext("DATABASE", "classpath:test-embedded-database.xml");
        jtaContext = startContext("JTA", "classpath:test-embedded-jta.xml");
        mailboxContext = startContext("MAILBOX", "classpath:tools-mailbox.xml");
        routeringContext = startContext("ROUTERING", "classpath:routering-runtime.xml");
        brpContext = startContext("BRP", "classpath:test-embedded-brp.xml");
        syncContext = startContext("SYNCHRONISATIE", "classpath:synchronisatie.xml");
        voiscContext = startVoisc(startContext("VOISC", "classpath:voisc-runtime.xml"));
        iscContext = startContext("ISC", "classpath:isc-runtime.xml");
        testContext = startTest(startContext("TEST", "classpath:test-context.xml"));
    }

    private static GenericXmlApplicationContext startVoisc(final GenericXmlApplicationContext context) {
        LOGGER.info("startVoisc: SSL");
        configureSsl(context);
        LOGGER.info("startVoisc: CHECK SSL");
        final VoiscMailbox voiscMailbox = context.getBean(VoiscMailbox.class);
        try {
            voiscMailbox.connectToMailboxServer();
            voiscMailbox.logout();
        } catch (final VoiscMailboxException ce) {
            throw new IllegalArgumentException("Kan geen verbinding maken met de mailbox");
        }

        final Map<String, RegisterService> registerServices = context.getBeansOfType(RegisterService.class);
        for (final RegisterService<?> registerService : registerServices.values()) {
            registerService.refreshRegister();
        }

        LOGGER.info("startVoisc: JOBS");
        final Scheduler scheduler = context.getBean("scheduler", Scheduler.class);
        try {
            scheduler.start();
        } catch (final SchedulerException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("startVoisc: LISTENERS");
        final DefaultMessageListenerContainer voiscBerichtListenerContainer =
                context.getBean("voiscBerichtListenerContainer", DefaultMessageListenerContainer.class);
        voiscBerichtListenerContainer.start();

        return context;
    }

    private static GenericXmlApplicationContext startTest(final GenericXmlApplicationContext context) {
        configureSsl(context);
        return context;
    }

    private static void configureSsl(final GenericXmlApplicationContext context) {
        final SslConnectionFactory connectionFactory = context.getBean(SslConnectionFactory.class);
        connectionFactory.setPassword("changeit");
        connectionFactory.setHostname("localhost");
        connectionFactory.setPortNumber(1233);
    }

    @Before
    public void injectDependencies() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @AfterClass
    public static void stopTestContext() {
        closeContext("TEST", testContext);
        closeContext("ISC", iscContext);
        closeContext("VOISC", voiscContext);
        closeContext("SYNCHRONISATIE", syncContext);
        closeContext("BRP", brpContext);
        closeContext("ROUTERING", routeringContext);
        closeContext("MAILBOX", mailboxContext);
        closeContext("JTA", jtaContext);
        closeContext("DATABASE", databaseContext);
    }

    private static GenericXmlApplicationContext startContext(final String name, final String... configurations) {
        LOGGER.info("Starten {} ...", name);

        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load(configurations);
        context.refresh();

        // final ClassRealm classRealm = classWorld.newRealm(name, ClassLoader.getSystemClassLoader());
        // final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // if (systemClassLoader instanceof URLClassLoader) {
        // for (final URL url : ((URLClassLoader) systemClassLoader).getURLs()) {
        // classRealm.addConstituent(url);
        // }
        // } else {
        // throw new IllegalArgumentException("Onbekend type system class loader");
        // }
        //
        // final Class<?> contextClass = classRealm.loadClass(GenericXmlApplicationContext.class.getCanonicalName());
        // final Method loadMethod = contextClass.getMethod("load", String[].class);
        // final Method refreshMethod = contextClass.getMethod("refresh");
        //
        // final Object context = contextClass.newInstance();
        // loadMethod.invoke(context, new Object[] {configurations });
        // refreshMethod.invoke(context);

        LOGGER.info("{} gestart", name);

        return context;
    }

    private static void closeContext(final String name, final Closeable context) {
        if (context != null) {
            try {
                LOGGER.info("Stoppen {} context ...", name);
                context.close();
                LOGGER.info("{} context gestopt", name);
            } catch (final Exception e) {
                LOGGER.warn("Probleem bij sluiten {} context", name, e);
            }
        }
    }

    // ***********************************
    // ***********************************
    // MAILBOX
    // ***********************************
    // ***********************************

    @Autowired
    private MailboxRepository mailboxRepository;
    @Autowired
    private MailboxClient mailboxClient;

    protected void verstuurBerichtViaMailbox(
        final String messageId,
        final String correlatieId,
        final String verzendendePartij,
        final String ontvangendePartij,
        final String bericht)
    {

        final Mailbox mailbox = mailboxRepository.getMailboxByInstantiecode(Integer.parseInt(verzendendePartij));
        LOGGER.info("Verwerk uitgaand bericht voor mailbox: {}", mailbox);
        Assert.assertNotNull("Geen mailbox gegevens gevonden voor verzendende partij: " + verzendendePartij, mailbox);
        final Mailbox recipient = mailboxRepository.getMailboxByInstantiecode(Integer.parseInt(ontvangendePartij));
        Assert.assertNotNull("Geen mailbox gegevens gevonden voor ontvangende partij: " + ontvangendePartij, recipient);

        // Genereer ID
        final nl.bzk.migratiebrp.voisc.database.entities.Bericht mailboxBericht = new nl.bzk.migratiebrp.voisc.database.entities.Bericht();
        mailboxBericht.setMessageId(messageId);
        mailboxBericht.setCorrelationId(correlatieId);
        mailboxBericht.setBerichtInhoud(bericht);
        mailboxBericht.setOriginator(mailbox.getMailboxnr());
        mailboxBericht.setRecipient(recipient.getMailboxnr());
        mailboxBericht.setStatus(StatusEnum.SENDING_TO_MAILBOX);

        // SSL verbinding opbouwen
        mailboxClient.connect();

        try {
            // Inloggen op de Mailbox
            mailboxClient.logOn(mailbox);

            // Versturen berichten naar Mailbox
            mailboxClient.putMessage(mailboxBericht);
        } catch (final VoaException e) {
            throw new RuntimeException("Fout bij versturen bericht.", e);
        } finally {
            // Logout
            try {
                mailboxClient.logOff();
            } catch (final SpdProtocolException e) {
                throw new RuntimeException("Fout bij uitloggen na versturen bericht.", e);
            }
        }
    }

    // ***********************************
    // ***********************************
    // Databases
    // ***********************************
    // ***********************************

    @Autowired
    @Named("brpDataSource")
    private DataSource brpDataSource;

    @Autowired
    private JtaTransactionManager transactionManager;

    protected <T> T executeInTransaction(final TransactionCallback<T> work) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(work);
    }

    protected int countPersonenInBrp() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(brpDataSource);
        return jdbcTemplate.queryForObject("select count(*) from kern.pers where srt=1", Integer.class);
    }

    // ***********************************
    // ***********************************
    // Queues
    // ***********************************
    // ***********************************

    private JmsTemplate brpJmsTemplate;

    @Autowired
    @Named(value = "brpConnectionFactory")
    public void setBrpConnectionFactory(final ConnectionFactory connectionFactory) {
        brpJmsTemplate = new JmsTemplate(connectionFactory);
        brpJmsTemplate.setReceiveTimeout(10000);
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
}
