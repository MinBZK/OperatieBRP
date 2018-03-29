/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.service.impl;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.ProefSynchronisatieRepository;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.service.ProefSynchronisatieService;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl.AbstractProefSynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl.JMSProefSynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl.MailboxProefSynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
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
    private static final Long DEFAULT_TIME_OUT = 3600000L;
    private static final String PARAMETER_BATCH_GROOTTE = "batchSize";
    private static final String PARAMETER_TIME_OUT = "timeout";
    private static final String PARAMETER_SOORT_VERWERKER = "verwerker";

    private static final int DEFAULT_MAX_THREAD_POOL_SIZE = 32;
    private static final int DEFAULT_THREAD_POOL_WAIT_TIME = 10;

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

    private Integer maxThreadPoolSize = DEFAULT_MAX_THREAD_POOL_SIZE;

    private Integer threadPoolWaitTime = DEFAULT_THREAD_POOL_WAIT_TIME;

    /**
     * Maximale grootte van de thread pool.
     * @param maxThreadPoolSize De maximale grootte
     */
    public void setMaxThreadPoolSize(final Integer maxThreadPoolSize) {
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

    /**
     * De maximale wachttijd voordat een thread pool geforceerd wordt beeindigd.
     * @param threadPoolWaitTime De maximale wachttijd
     */
    public void setThreadPoolWaitTime(final Integer threadPoolWaitTime) {
        this.threadPoolWaitTime = threadPoolWaitTime;
    }

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

            checkMailbox();

            executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
            verwerker =
                    new MailboxProefSynchronisatieBerichtVerwerker(
                            executor,
                            proefSynchronisatieRepository,
                            mailboxRepository,
                            mailboxClient,
                            maxThreadPoolSize,
                            threadPoolWaitTime);
        } else {
            LOG.info("Verstuur bericht naar ISC queue");
            executor =
                    new ThreadPoolExecutor(
                            QUEUE_SEND_THREAD_POOL_SIZE,
                            QUEUE_SEND_THREAD_POOL_SIZE,
                            0L,
                            TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<>(),
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


    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** MAILBOX ************************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */


    private void checkMailbox() {
        LOG.info("Controleren SSL verbinding ...");
        final MailboxClient client = applicationContext.getBean(MailboxClient.class);
        try (final Connection mailboxConnection = mailboxClient.connect()) {
            client.logOff(mailboxConnection);
        } catch (final VoaException e) {
            LOG.error("Exception bij opzetten van verbinding naar mailbox: ", e);
            ((ConfigurableApplicationContext) applicationContext).close();
        }
    }

}
