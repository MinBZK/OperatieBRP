/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscDatabaseException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De algemene ingang in de services die de Voisc biedt.
 */
public final class VoiscServiceImpl implements VoiscService {
    private static final String DATE_OUDER_DAN_MAG_NIET_NULL_ZIJN = "Date 'ouderDan' mag niet 'null' zijn.";
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final int DEFAULT_MAX_THREAD_POOL_SIZE = 32;
    private static final int DEFAULT_THREAD_POOL_WAIT_TIME = 60;
    private static final int DEFAULT_BERICHTEN_LIMIT = 100;
    private ExecutorService verzendenOntvangenMailboxExecutor;
    private Integer maxThreadPoolSize = DEFAULT_MAX_THREAD_POOL_SIZE;
    private Integer threadPoolWaitTime = DEFAULT_THREAD_POOL_WAIT_TIME;

    private VoiscDatabase voiscDatabase;
    private VoiscMailbox voiscMailbox;
    private VoiscIsc voiscIsc;
    private MailboxConfiguratie voiscConfiguratie;
    private Integer berichtenLimit = DEFAULT_BERICHTEN_LIMIT;

    /**
     * Constructor.
     * @param voiscDatabase voisc database
     * @param voiscMailbox voisc mailbox
     * @param voiscIsc voisc isc
     * @param voiscConfiguratie voisc mailbox configuratie
     */
    @Inject
    public VoiscServiceImpl(final VoiscDatabase voiscDatabase, final VoiscMailbox voiscMailbox, final VoiscIsc voiscIsc,
                            final MailboxConfiguratie voiscConfiguratie) {
        this.voiscDatabase = voiscDatabase;
        this.voiscMailbox = voiscMailbox;
        this.voiscIsc = voiscIsc;
        this.voiscConfiguratie = voiscConfiguratie;
    }

    /**
     * Setter voor het maxiamaal op te halen aantal berichten.
     * @param berichtenLimit Het maximum aantal op te halen berichten
     */
    public void setBerichtenLimit(final Integer berichtenLimit) {
        this.berichtenLimit = berichtenLimit;
    }

    /**
     * Maximale grootte van de thread pool.
     * @param maxThreadPoolSize De maximale grootte
     */
    public void setMaxThreadPoolSize(final Integer maxThreadPoolSize) {
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

    /**
     * De maximale wachttijd van een thread pool.
     * @param threadPoolWaitTime De thread wachttijd
     */
    public void setThreadPoolWaitTime(final Integer threadPoolWaitTime) {
        this.threadPoolWaitTime = threadPoolWaitTime;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, value = "voiscTransactionManager")
    @ManagedOperation(description = "Verstuur berichten van VOISC (database) naar ISC.")
    public void berichtenVerzendenNaarIsc() {
        final List<Bericht> messagesToSend = voiscDatabase.leesEnLockNaarIscTeVerzendenBericht(berichtenLimit);
        if (!messagesToSend.isEmpty()) {
            LOG.info("Versturen " + messagesToSend.size() + " berichten naar ISC.");
            voiscIsc.accept(messagesToSend);
        }

    }

    @Override
    @Transactional(propagation = Propagation.NEVER, value = "voiscTransactionManager")
    public void berichtenVerzendenNaarEnOntvangenVanMailbox() {
        LOG.debug("Start berichten verzenden naar en ontvangen van mailbox");
        // a. Ophalen mailboxen
        final Set<Mailbox> mailboxes = voiscConfiguratie.bepaalMailboxen();

        // WORKERS HIER, PER MAILBOX
        final int aantalThreads = mailboxes.isEmpty() ? 1 : mailboxes.size() < maxThreadPoolSize ? mailboxes.size() : maxThreadPoolSize;
        if (verzendenOntvangenMailboxExecutor == null || verzendenOntvangenMailboxExecutor.isTerminated()) {
            LOG.debug("Aanmaken executor met {} threads.", aantalThreads);
            verzendenOntvangenMailboxExecutor = Executors.newFixedThreadPool(aantalThreads);

            final List<VerwerkMailbox> verwerkers = new ArrayList<>();

            // Per mailbox verwerker aanmaken.
            mailboxes.forEach(mailbox -> verwerkers.add(new VerwerkMailbox(mailbox)));

            try {
                // Verwerkers starten.
                final List<Future<?>> futures = new ArrayList<>();
                verwerkers.forEach(verwerker -> futures.add(verzendenOntvangenMailboxExecutor.submit(verwerker)));
                LOG.debug("Er zijn {} verwerkers gesubmit.", verwerkers.size());

                verzendenOntvangenMailboxExecutor.shutdown();

                while (!verzendenOntvangenMailboxExecutor.awaitTermination(threadPoolWaitTime, TimeUnit.SECONDS)) {
                    LOG.debug("Berichten worden nog verzonden en ontvangen van mailbox.");
                }

            } catch (final InterruptedException e) {
                LOG.error("Verzenden en ontvangen van mailbox is onderbroken.");
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public int opschonenVoiscBerichten(final Date ouderDan) {
        LOG.debug("Start opschonen verwerkte berichten");

        int aantalOpgeschoondeBerichten = 0;
        if (ouderDan == null) {
            LOG.error(DATE_OUDER_DAN_MAG_NIET_NULL_ZIJN);
            return 0;
        }

        final EnumSet<StatusEnum>
                statussen =
                EnumSet.of(
                        StatusEnum.SENT_TO_ISC,
                        StatusEnum.SENT_TO_MAILBOX,
                        StatusEnum.IGNORED,
                        StatusEnum.PROCESSED_IMMEDIATELY,
                        StatusEnum.ERROR);

        try {
            aantalOpgeschoondeBerichten = voiscDatabase.verwijderVerwerkteBerichtenOuderDan(ouderDan, statussen);
        } catch (final VoiscDatabaseException e) {
            LOG.error("Er is een foutopgetreden bij het opschonen van de verwerkte berichten.", e);
        }

        if (aantalOpgeschoondeBerichten > 0) {
            LOG.debug("{} berichten opgeschoond.", aantalOpgeschoondeBerichten);
        }

        LOG.debug("Einde opschonen verwerkte berichten");
        return aantalOpgeschoondeBerichten;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = "voiscTransactionManager")
    public int herstellenVoiscBerichten(final Date ouderDan) {
        LOG.debug("Start herstellen berichten");

        int aantalHersteldeBerichten = 0;
        if (ouderDan == null) {
            LOG.error(DATE_OUDER_DAN_MAG_NIET_NULL_ZIJN);
            return 0;
        }

        try {
            aantalHersteldeBerichten += voiscDatabase.herstelBerichten(ouderDan, StatusEnum.SENDING_TO_MAILBOX, StatusEnum.RECEIVED_FROM_ISC);
            aantalHersteldeBerichten += voiscDatabase.herstelBerichten(ouderDan, StatusEnum.SENDING_TO_ISC, StatusEnum.RECEIVED_FROM_MAILBOX);
        } catch (final RuntimeException e /* Proces mag er niet uit gaan vanwege een onverwachte fout */) {
            LOG.error("Er is een foutopgetreden bij het herstellen van berichten.", e);
        }

        if (aantalHersteldeBerichten > 0) {
            LOG.info("{} berichten hersteld.", aantalHersteldeBerichten);
        }

        LOG.debug("Einde herstellen berichten");
        return aantalHersteldeBerichten;
    }

    /**
     * Mailbox verwerker.
     */
    private class VerwerkMailbox implements Runnable {

        private final Mailbox mailbox;

        /**
         * Default constructor.
         * @param mailbox De mailbox die verwerkt wordt
         */
        VerwerkMailbox(final Mailbox mailbox) {
            this.mailbox = mailbox;
        }

        @Override
        public void run() {

            // b. SSL verbinding opbouwen naar MBS
            LOG.debug("Maak verbinding met mailbox server.");
            try (Connection connection = voiscMailbox.connectToMailboxServer()) {

                LOG.debug("[Mailbox {}]: Start verwerking voor mailbox.", mailbox.getMailboxnr());
                verwerkMailbox(connection);

            } catch (final VoiscMailboxException e) {
                LOG.error("Kan geen verbinding maken met mailbox server", e);
            }
        }

        private void verwerkMailbox(final Connection connection) {
            try {
                LOG.debug("[Mailbox {}]: Uitlezen te versturen berichten.", mailbox.getMailboxnr());
                // 1. uitlezen tabel voaBericht met nog te versturen berichten.
                final List<Bericht> berichten = voiscDatabase.leesEnLockNaarMailboxTeVerzendenBericht(mailbox.getMailboxnr());

                LOG.debug("[Mailbox {}]: Aanmelden", mailbox.getMailboxnr());
                // 2. Inloggen op de Mailbox van de gemeente
                voiscMailbox.login(connection, mailbox);

                if (!berichten.isEmpty()) {
                    LOG.debug("[Mailbox {}]: {} berichten te versturen.", mailbox.getMailboxnr(), berichten.size());
                }

                // 3. Versturen berichten naar Mailbox
                voiscMailbox.sendMessagesToMailbox(connection, mailbox, berichten);

                LOG.debug("[Mailbox {}]: Ontvangen berichten.", mailbox.getMailboxnr());
                // 4, 5. Berichten uit mailbox ophalen en opslaan.
                voiscMailbox.receiveMessagesFromMailbox(connection, mailbox);

                LOG.debug("[Mailbox {}]: Verwerking gereed.", mailbox.getMailboxnr());
            } catch (final VoiscMailboxException e) {
                LOG.error("Kan geen verbinding maken met mailbox server", e);
            } catch (final RuntimeException e /* Proces mag er niet uit gaan vanwege een onverwachte fout */) {
                LOG.warn("[Mailbox {}]: fout opgetreden.", mailbox.getMailboxnr(), e);
            } finally {
                LOG.debug("Sluit verbinding met mailbox server.");
                voiscMailbox.logout(connection);
                LOG.debug("Einde berichten verzenden naar en ontvangen van mailbox");
            }
        }
    }
}
