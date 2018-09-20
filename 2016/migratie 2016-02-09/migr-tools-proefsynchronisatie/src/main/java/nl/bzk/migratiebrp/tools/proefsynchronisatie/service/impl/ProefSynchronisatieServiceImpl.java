/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.service.impl;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.ProefSynchronisatieRepository;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.service.ProefSynchronisatieService;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl.AbstractProefSynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl.JMSProefSynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl.MailboxProefSynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.spd.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.impl.SslConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * Implementatie van de proefsynchronisatie service interface.
 */
public final class ProefSynchronisatieServiceImpl implements ProefSynchronisatieService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String TOTAAL_VERWERKTE_BERICHTEN = "Totaal verwerkte berichten: {}";
    private static final String INTERRUPTED_EXCEPTION_TIJDENS_WACHTEN = "InterruptedException tijdens wachten op het klaar zijn van de queue vuller";

    private static final int QUEUE_SEND_THREAD_POOL_SIZE = 8;
    private static final int DEFAULT_QUEUE_BATCH_SIZE = 10000;
    private static final Long DEFAULT_TIME_OUT = Long.valueOf(3600000);
    private static final String PARAMETER_BATCH_GROOTTE = "batchSize";
    private static final String PARAMETER_TIME_OUT = "timeout";
    private static final String PARAMETER_SOORT_VERWERKER = "verwerker";
    private static final String PARAMETER_MAILBOX_HOST = "voisc.mailbox.hostname";
    private static final String PARAMETER_MAILBOX_PORT = "voisc.mailbox.portnumber";
    private static final int DEFAULT_MAILBOX_PORT = 1212;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("voiscOntvangstQueue")
    private Destination destination;

    @Inject
    private MailboxClient mailboxClient;
    @Inject
    private MailboxRepository mailboxRepository;

    @Inject
    private ProefSynchronisatieRepository proefSynchronisatieRepository;

    @Inject
    private ApplicationContext applicationContext;

    @Override
    public void laadInitProefSynchronisatieBerichtenTabel(final Properties config) {
        LOG.info("De initiele vulling tabel voor de proefsynchronisatie berichten wordt nu gevuld.");
        proefSynchronisatieRepository.laadInitProefSynchronisatieBerichtenTabel(config.getProperty("datumVanaf"), config.getProperty("datumTot"));
        LOG.info("De initiele vulling tabel voor de proefsynchronisatie berichten is gevuld.");
    }

    @Override
    public void voerProefSynchronisatieUit(final Properties config) {

        final ThreadPoolExecutor executor;
        final AbstractProefSynchronisatieBerichtVerwerker verwerker;
        if ("mailbox".equals(config.getProperty(PARAMETER_SOORT_VERWERKER))) {
            LOG.info("Verstuur berichten naar mailbox.");

            LOG.info("Configureren SSL verbinding ...");
            configureSsl(getMailboxHost(config), getMailboxPort(config));

            executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
            verwerker = new MailboxProefSynchronisatieBerichtVerwerker(executor, proefSynchronisatieRepository, mailboxRepository, mailboxClient);
        } else {
            LOG.info("Verstuur bericht naar ISC queue");
            executor =
                    new ThreadPoolExecutor(
                        QUEUE_SEND_THREAD_POOL_SIZE,
                        QUEUE_SEND_THREAD_POOL_SIZE,
                        0L,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        new ThreadPoolExecutor.CallerRunsPolicy());
            verwerker = new JMSProefSynchronisatieBerichtVerwerker(executor, destination, jmsTemplate, proefSynchronisatieRepository);
        }

        proefSynchronisatieRepository.verwerkProefSynchronisatieBericht(verwerker, getQueueBatchSize(config), getWachtPeriode(config));

        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.HOURS);
        } catch (final InterruptedException e) {
            LOG.warn(INTERRUPTED_EXCEPTION_TIJDENS_WACHTEN);
        }

        LOG.info("Klaar met versturen van ProefSynchronisatieBerichten.");
        LOG.info(TOTAAL_VERWERKTE_BERICHTEN, verwerker.getVerwerkTeller());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** CONFIG ************************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private int getQueueBatchSize(final Properties config) {
        final int queueBatchSize;
        final String queueBatchSizeString = config.getProperty(PARAMETER_BATCH_GROOTTE);
        if (queueBatchSizeString != null) {
            queueBatchSize = Integer.parseInt(queueBatchSizeString);
        } else {
            queueBatchSize = DEFAULT_QUEUE_BATCH_SIZE;
        }
        return queueBatchSize;
    }

    private long getWachtPeriode(final Properties config) {
        final long wachtPeriode;
        final String wachtPeriodeString = config.getProperty(PARAMETER_TIME_OUT);
        if (wachtPeriodeString != null) {
            wachtPeriode = Long.parseLong(wachtPeriodeString);
        } else {
            wachtPeriode = DEFAULT_TIME_OUT;
        }
        return wachtPeriode;
    }

    private String getMailboxHost(final Properties config) {
        final String result = config.getProperty(PARAMETER_MAILBOX_HOST);
        if (result == null) {
            throw new IllegalArgumentException("Paramter '" + PARAMETER_MAILBOX_HOST + "' verplicht voor mailbox verbinding.");
        }
        return result;
    }

    private int getMailboxPort(final Properties config) {
        final int result;
        final String resultString = config.getProperty(PARAMETER_MAILBOX_PORT);
        if (resultString != null) {
            result = Integer.parseInt(resultString);
        } else {
            result = DEFAULT_MAILBOX_PORT;
        }
        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** MAILBOX ************************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void configureSsl(final String host, final Integer port) {
        final SslConnectionFactory connectionFactory = applicationContext.getBean(SslConnectionFactory.class);
        final char[] pwdArray = getPasswd();
        connectionFactory.setPassword(new String(pwdArray));
        if (host != null) {
            connectionFactory.setHostname(host);
        }
        if (port != null) {
            connectionFactory.setPortNumber(port);
        }
        checkMailbox();
    }

    /**
     * Geef de waarde van passwd.
     *
     * @return passwd
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private char[] getPasswd() {
        // readPassword method call.
        char[] pwdArray = new char[] {};
        final File voiscPwd = new File(".voiscPwd");
        if (voiscPwd.exists()) {
            try {
                final BufferedReader br = new BufferedReader(new FileReader(voiscPwd));
                final String pwd = br.readLine();
                if (pwd != null) {
                    pwdArray = pwd.toCharArray();
                }
                br.close();
            } catch (final IOException e) {
                LOG.error("Kan .voiscPwd file niet lezen", e);
                System.exit(1);
            }
        } else {
            final Console console = System.console();
            final String pwdPrompt = "%1$26s";

            if (console == null) {
                LOG.error("Console Object is not available.");
                System.exit(1);
            } else {
                pwdArray = console.readPassword(pwdPrompt, "Voer het SSL wachtwoord in: ");
            }
        }
        return pwdArray;
    }

    private void checkMailbox() {
        LOG.info("Controleren SSL verbinding ...");
        final MailboxClient mailboxClient = applicationContext.getBean(MailboxClient.class);
        try {
            mailboxClient.connect();
            mailboxClient.logOff();
        } catch (final SpdProtocolException e) {
            LOG.error("Exception bij opzetten van verbinding naar mailbox: ", e);
            System.exit(1);
        }
    }

}
