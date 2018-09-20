/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.init.naarlo3.repository.ANummerVerwerker;
import nl.bzk.migratiebrp.init.naarlo3.repository.BrpRepository;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de VulNaarLo3QueueService. Deze maakt gebruik van JDBC om te lezen uit de BRP database en JMS om
 * te schrijven naar de QUEUE.
 */
@Service("vulNaarLo3QueueService")
public final class VulNaarLo3QueueServiceImpl implements VulNaarLo3QueueService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int VERSTUUR_LOG_INTERVAL = 1000;

    private static final int QUEUE_SEND_THREAD_POOL_SIZE = 8;

    private static final int QUEUE_BATCH_SIZE = 100;

    @Inject
    private BrpRepository brpRepository;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    private Destination destination;

    /**
     * {@inheritDoc}
     */
    @Override
    public void leesIngeschrevenenInBrpEnVulQueue() {
        LOG.info("Start lezen en versturen LO3 berichten.");

        final ThreadPoolExecutor executor =
                new ThreadPoolExecutor(
                    QUEUE_SEND_THREAD_POOL_SIZE,
                    QUEUE_SEND_THREAD_POOL_SIZE,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(4 * QUEUE_SEND_THREAD_POOL_SIZE),
                    new ThreadPoolExecutor.CallerRunsPolicy());

        final VerzendANummerVerwerker verwerker = new VerzendANummerVerwerker(executor);
        brpRepository.verwerkANummersVanIngeschrevenPersonen(verwerker, QUEUE_BATCH_SIZE);

        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.HOURS);
        } catch (final InterruptedException e) {
            LOG.warn("InterruptedException tijdens wachten op het klaar zijn van de queue vuller");
        }
        LOG.info("Klaar met versturen van A-Nummers.");
        LOG.info("Totaal verwerkte A-Nummers: {}", verwerker.getVerwerkTeller());
    }

    private void verstuurBericht(final List<LeesUitBrpVerzoekBericht> berichten) {
        try {
            Herhaal.herhaalOperatie(new Runnable() {
                @Override
                public void run() {
                    jmsTemplate.execute(destination, new ProducerCallback<Object>() {
                        @Override
                        public Object doInJms(final Session session, final MessageProducer producer) throws JMSException {
                            for (final LeesUitBrpVerzoekBericht bericht : berichten) {
                                final Message message = session.createTextMessage(bericht.format());
                                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, String.valueOf(bericht.getANummer()));
                                producer.send(message);
                            }
                            session.commit();
                            return null;
                        }
                    });
                }
            });
        } catch (final HerhaalException e) {
            LOG.error("Kon berichten niet verzenden.", e);
        }
    }

    /**
     * Verstuurt A-Nummers. Deze class is niet thread-safe, en kan dus niet aangestuurd worden vanuit meerdere threads.
     */
    private class VerzendANummerVerwerker implements ANummerVerwerker {
        private List<LeesUitBrpVerzoekBericht> aNummers = new ArrayList<>(QUEUE_BATCH_SIZE);
        private final AtomicInteger verwerkTeller = new AtomicInteger(0);
        private final AtomicInteger verwerktTotaal = new AtomicInteger(0);
        private final ThreadPoolExecutor executor;

        /**
         * Constructor.
         *
         * @param executor
         *            executor
         */
        public VerzendANummerVerwerker(final ThreadPoolExecutor executor) {
            this.executor = executor;
        }

        /**
         * Voeg een A-nummer toe aan de huidige batch.
         *
         * @param aNummer
         *            het A-nummer
         */
        @Override
        public void addANummer(final long aNummer) {
            aNummers.add(new LeesUitBrpVerzoekBericht(aNummer, AntwoordFormaatType.LO_3_XML));
        }

        /**
         * Verstuur de huidige batch. Het versturen gebeurt a-synchroon door de verwerking uit te laten voeren in een
         * ExecutorService in de omringende class.
         *
         * @return niets
         */
        @Override
        public Void call() {
            final List<LeesUitBrpVerzoekBericht> teVersturenANummers = aNummers;
            aNummers = new ArrayList<>(QUEUE_BATCH_SIZE);

            executor.submit(new Runnable() {
                @Override
                public void run() {
                    verstuurBericht(teVersturenANummers);

                    final int verwerktCount = verwerkTeller.addAndGet(teVersturenANummers.size());
                    if (verwerktCount >= VERSTUUR_LOG_INTERVAL) {
                        LOG.info("Verwerkte A-Nummers: {}", verwerktTotaal.addAndGet(VERSTUUR_LOG_INTERVAL));
                        verwerkTeller.addAndGet(-VERSTUUR_LOG_INTERVAL);
                    }
                }
            });

            return null;
        }

        /**
         * Geef de waarde van verwerk teller.
         *
         * @return verwerk teller
         */
        public int getVerwerkTeller() {
            return verwerkTeller.intValue() + verwerktTotaal.intValue();
        }
    }
}
