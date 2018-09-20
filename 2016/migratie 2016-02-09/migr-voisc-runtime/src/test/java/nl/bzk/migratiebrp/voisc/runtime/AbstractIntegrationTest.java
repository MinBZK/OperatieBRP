/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.management.JMException;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

public class AbstractIntegrationTest {

    private MailboxFactory mailboxFactory;

    private JmsTemplate jmsTemplate;

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext routeringContext;
    private static GenericXmlApplicationContext registerContext;
    private static GenericXmlApplicationContext mailboxContext;
    private static GenericXmlApplicationContext testContext;

    private static final Logger LOG = LoggerFactory.getLogger();

    protected static final String LEVERING_QUEUE = "levering";
    protected static final String SYNC_VERZOEK_QUEUE = "sync.verzoek";
    protected static final String SYNC_ANTWOORD_QUEUE = "sync.antwoord";
    protected static final String VOISC_ONTVANGST_QUEUE = "voisc.ontvangst";
    protected static final String VOISC_VERZENDEN_QUEUE = "voisc.verzenden";

    @Autowired
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(10000);
    }

    @BeforeClass
    public static void startDependencies() {
        // Start DB
        LOG.info("Starten DATABASE ...");
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.refresh();
        LOG.info("DATABASE gestart");

        // Start RouteringCentrale
        LOG.info("Starten ROUTERING ...");
        routeringContext = new GenericXmlApplicationContext();
        routeringContext.load("classpath:routering-runtime.xml");
        routeringContext.refresh();
        LOG.info("ROUTERING gestart");

        // Start Register (SYNC)
        LOG.info("Starten REGISTER ...");
        registerContext = new GenericXmlApplicationContext();
        registerContext.load(
            "classpath:test-embedded-register.xml",
            "classpath:test-autorisatieregister-dummy.xml",
            "classpath:test-gemeenteregister-dummy.xml");
        registerContext.refresh();
        LOG.info("REGISTER gestart");

        // Start Mailbox
        LOG.info("Starten MAILBOX ...");
        mailboxContext = new GenericXmlApplicationContext();
        mailboxContext.load("classpath:tools-mailbox.xml");
        mailboxContext.refresh();
        LOG.info("MAILBOX gestart");

        // Create test context
        LOG.info("Starten TEST context ...");
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test_context.xml");
        testContext.refresh();
        LOG.info("TEST context gestart");
    }

    @Before
    public void injectDependencies() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);

        mailboxFactory = mailboxContext.getBean(MailboxFactory.class);
    }

    @AfterClass
    public static void stopTestContext() {
        if (testContext != null) {
            try {
                LOG.info("Stoppen TEST context ...");
                testContext.close();
                LOG.info("TEST context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten TEST context", e);
            }
        }
        if (mailboxContext != null) {
            try {
                LOG.info("Stoppen MAILBOX context ...");
                mailboxContext.close();
                LOG.info("MAILBOX context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten MAILBOX context", e);
            }
        }
        if (registerContext != null) {
            try {
                LOG.info("Stoppen REGISTER context ...");
                registerContext.close();
                LOG.info("REGISTER context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten REGISTER context", e);
            }
        }
        if (routeringContext != null) {
            try {
                LOG.info("Stoppen ROUTERING context ...");
                routeringContext.close();
                LOG.info("ROUTERING context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten ROUTERING context", e);
            }
        }
        if (databaseContext != null) {
            try {
                LOG.info("Stoppen DATABASE context ...");
                databaseContext.close();
                LOG.info("DATABASE context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten DATABASE context", e);
            }
        }
    }

    @Before
    public void start() throws IOException, SchedulerException, JMSException {
        LOG.info("Starten MAIN (before test)");
        final File passwordFile = new File(".voiscPwd");
        try (Writer writer = new FileWriter(passwordFile)) {
            writer.write("changeit");
        }

        VoiscMain.main(new String[] {});
        LOG.info("MAIN gestart (before test)");

        LOG.info("Context: {}", VoiscMain.getContext());
        Assert.assertNotNull(VoiscMain.getContext());
    }

    @After
    public void shutdown() throws IOException, JMException {
        LOG.info("Stoppen MAIN (after test)");
        VoiscMain.stop();
        LOG.info("MAIN gestopt (after test)");
    }

    /* *** CONVENIENCE (MAILBOX) *** */

    protected void sendToMailbox(
        final String fromMailboxnr,
        final String toMailboxnr,
        final String messageId,
        final String crossReferenceId,
        final String message) throws MailboxException
    {

        final Mailbox toMailbox = mailboxFactory.getMailbox(toMailboxnr);
        toMailbox.open();

        final MailboxEntry entry = new MailboxEntry();
        entry.setStatus(MailboxEntry.STATUS_NEW);
        entry.setOriginatorOrRecipient(fromMailboxnr);
        entry.setMesg(message);
        entry.setMessageId(messageId);
        entry.setCrossReference(crossReferenceId);

        toMailbox.addEntry(entry);

        toMailbox.save();
        toMailbox.close();
    }

    /* *** CONVENIENCE (QUEUE) *** */

    protected Message expectMessage(final String destinationName) {
        jmsTemplate.setReceiveTimeout(30000);
        Message message;
        try {
            message = jmsTemplate.receive(destinationName);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Geen bericht ontvangen", message);
        return message;
    }

    protected String getContent(final Message message) throws JMSException {
        // content
        final String content;
        if (message instanceof TextMessage) {
            content = ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("Message type niet ondersteund: " + message.getClass());
        }
        return content;
    }
}
