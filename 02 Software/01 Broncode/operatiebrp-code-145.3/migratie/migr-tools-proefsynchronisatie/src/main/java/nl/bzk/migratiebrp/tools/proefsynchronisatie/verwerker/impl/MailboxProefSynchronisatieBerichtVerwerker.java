/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.domein.ProefSynchronisatieBericht;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.ProefSynchronisatieRepository;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.BerichtVerwerker;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

/**
 * Verwerker die berichten naar de mailbox stuurt.
 */
public final class MailboxProefSynchronisatieBerichtVerwerker extends AbstractProefSynchronisatieBerichtVerwerker
        implements BerichtVerwerker<ProefSynchronisatieBericht> {
    private static final Logger LOG = LoggerFactory.getLogger();
    private ExecutorService verzendenOntvangenMailboxExecutor;

    private final MailboxRepository mailboxRepository;
    private final MailboxClient mailboxClient;

    private final Integer maxThreadPoolSize;
    private final Integer threadPoolWaitTime;

    /**
     * Constructor.
     * @param executor executor
     * @param proefSynchronisatieRepository proefsync repo
     * @param mailboxRepository mailbox repo
     * @param mailboxClient mailbox client
     * @param maxThreadPoolSize Maximaal aantal threads
     * @param threadPoolWaitTime Wachtperiode voor timeout threadpool
     */
    public MailboxProefSynchronisatieBerichtVerwerker(
            final ThreadPoolExecutor executor,
            final ProefSynchronisatieRepository proefSynchronisatieRepository,
            final MailboxRepository mailboxRepository,
            final MailboxClient mailboxClient,
            final Integer maxThreadPoolSize,
            final Integer threadPoolWaitTime) {
        super(executor, proefSynchronisatieRepository);
        this.mailboxRepository = mailboxRepository;
        this.mailboxClient = mailboxClient;
        this.maxThreadPoolSize = maxThreadPoolSize;
        this.threadPoolWaitTime = threadPoolWaitTime;
    }

    @Override
    protected List<ProefSynchronisatieBericht> verstuurProefSynchronisatieBerichten(final List<ProefSynchronisatieBericht> teVersturenBerichten) {
        // Map berichten naar setjes per mailbox
        final Map<String, List<ProefSynchronisatieBericht>> berichten = groepeerBerichtenOpAfzender(teVersturenBerichten);
        final List<ProefSynchronisatieBericht> result = Collections.synchronizedList(new ArrayList<>());
        final Set<String> afzenders = berichten.keySet();

        final int aantalThreads = afzenders.size() == 0 ? 1 : afzenders.size() < maxThreadPoolSize ? afzenders.size() : maxThreadPoolSize;
        LOG.info("Aanmaken executor met {} threads.", aantalThreads);
        verzendenOntvangenMailboxExecutor = Executors.newFixedThreadPool(aantalThreads);

        final List<MailboxVerwerker> verwerkers = new ArrayList<>();

        // Per mailbox verwerker aanmaken.
        for (final String afzender : afzenders) {
            final Mailbox mailbox = mailboxRepository.getMailboxByPartijcode(afzender);
            if (mailbox != null) {
                verwerkers.add(new MailboxVerwerker(mailbox, berichten.get(afzender), result));
            } else {
                LOG.warn(
                        "Mailbox voor instantie {} bestaat niet! De {} berichten voor deze mailbox worden niet verzonden.",
                        afzender,
                        berichten.get(afzender).size());
            }
        }

        try {
            // Verwerkers starten.
            final List<Future<?>> futures = new ArrayList<>();
            verwerkers.forEach(verwerker -> futures.add(verzendenOntvangenMailboxExecutor.submit(verwerker)));
            LOG.info("Er zijn {} verwerkers gesubmit.", verwerkers.size());

            verzendenOntvangenMailboxExecutor.shutdown();

            while (!verzendenOntvangenMailboxExecutor.awaitTermination(threadPoolWaitTime, TimeUnit.SECONDS)) {
                LOG.info("Berichten worden nog verzonden en ontvangen van mailbox.");
            }

        } catch (final InterruptedException e) {
            LOG.error("Verzenden en ontvangen van mailbox is onderbroken.");
        }

        return result;

    }

    private Bericht maakBericht(final ProefSynchronisatieBericht bericht) {
        final Bericht result = new Bericht();

        result.setBerichtInhoud(bericht.getBericht());
        result.setMessageId(Long.toString(bericht.getBerichtId()));
        result.setCorrelationId(null);
        // Centrale mailbox
        result.setRecipient(bericht.getMailbox());

        return result;
    }

    private Map<String, List<ProefSynchronisatieBericht>> groepeerBerichtenOpAfzender(final List<ProefSynchronisatieBericht> teVersturenBerichten) {
        final Map<String, List<ProefSynchronisatieBericht>> result = new HashMap<>();

        for (final ProefSynchronisatieBericht teVersturenBericht : teVersturenBerichten) {
            if (!result.containsKey(teVersturenBericht.getAfzender())) {
                result.put(teVersturenBericht.getAfzender(), new ArrayList<>());
            }

            result.get(teVersturenBericht.getAfzender()).add(teVersturenBericht);
        }

        return result;
    }

    /**
     * Verwerker voor de mailbox.
     */

    private class MailboxVerwerker implements Runnable {

        private final Mailbox mailbox;
        private final List<ProefSynchronisatieBericht> berichten;
        private final List<ProefSynchronisatieBericht> verwerkteBerichten;

        /**
         * Default constructor.
         */
        MailboxVerwerker(
                final Mailbox mailbox,
                final List<ProefSynchronisatieBericht> berichten,
                final List<ProefSynchronisatieBericht> verwerkteBerichten) {
            this.mailbox = mailbox;
            this.berichten = berichten;
            this.verwerkteBerichten = verwerkteBerichten;
        }

        @Override
        public void run() {

            // SSL verbinding opbouwen naar MBS
            LOG.info("Maak verbinding met mailbox server.");
            try (Connection connection = mailboxClient.connect()) {

                LOG.debug("[Mailbox {}]: Start verwerking voor mailbox.", mailbox.getMailboxnr());

                try {
                    LOG.debug("[Mailbox {}]: Aanmelden", mailbox.getMailboxnr());
                    // 1. Inloggen op de Mailbox van de gemeente
                    mailboxClient.logOn(connection, mailbox);

                    if (!berichten.isEmpty()) {
                        LOG.info("[Mailbox {}]: {} berichten te versturen.", mailbox.getMailboxnr(), berichten.size());
                    }

                    // 2. Versturen berichten naar Mailbox
                    berichten.forEach(bericht -> {
                        try {
                            mailboxClient.putMessage(connection, maakBericht(bericht));
                        } catch (final VoaException e) {
                            LOG.warn("Versturen van bericht (id=" + bericht.getBerichtId() + ") gefaald", e);
                        }
                        verwerkteBerichten.add(bericht);
                    });

                    LOG.debug("[Mailbox {}]: Verwerking gereed.", mailbox.getMailboxnr());
                } catch (final VoaException exception) {
                    LOG.warn("Inloggen op mailbox gefaald", exception);
                } catch (final Exception e /* Proces mag er niet uit gaan vanwege een onverwachte fout */) {
                    LOG.debug("[Mailbox {}]: fout opgetreden.", mailbox.getMailboxnr(), e);
                } finally {
                    LOG.info("[Mailbox {}]: Sluit verbinding met mailbox server.", mailbox.getMailboxnr());
                    mailboxClient.logOff(connection);
                    LOG.info("Einde berichten verzenden naar en ontvangen van mailbox");
                }
            } catch (final VoaException e) {
                LOG.error("Kan geen verbinding maken met mailbox server", e);

                // Einde oefening
                return;
            }
        }

    }
}
