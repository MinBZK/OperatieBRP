/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import nl.bzk.brp.levering.algemeen.service.AfnemerQueueService;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.service.StappenService;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Controller voor het pre-processen en uiteindelijk verzenden van BRP en LO3 berichten.
 * <p/>
 * LET OP: failover dient oneindig te zijn!
 */
@Component
@ManagedResource(
    objectName = "nl.bzk.brp.levering.verzending:name=Verzender",
    description = "De verzender archiveert/protocolleert en verzendt de berichten.")
public class Verzender {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int    TIMEOUT  = 10;
    private static final String STREEPJE = "_";

    /**
     * Aantal BerichtVerwerker threads dat parallel actief kan zijn.
     */
    @Value("${verzending.aantal.threads:8}")
    private int aantalVerzendThreads;

    /**
     * Aantal berichten dat een BerichtVerwerker per keer verwerken in dezelfde sessie. Deze waarde wordt gelijk gesteld aan de prefetch instelling op de
     * connectie.
     */
    @Value("${verzending.aantal.berichten.periteratie:25}")
    private int aantalBerichtenPerIteratie;

    /**
     * receiveNoWait() geeft soms geen berichten terug terwijl er wel berichten op de queue staan. Dit hangt samen met de prefetch instelling die iets tijd
     * nodig heeft om berichten voor verwerking aan te leveren. Meer tijd is een grotere kans op het beschikbaar zijn van berichten.
     */
    @Value("${verzending.wachttijd.bericht.ontvangen:50}")
    private int wachttijdVoorBerichtMillis;

    @Inject
    private AfnemerQueueService     afnemerQueueService;
    @Inject
    private StappenService          stappenService;
    @Inject
    private PooledConnectionFactory pooledConnectionFactory;
    @Inject
    private VerzendTellerJMXBean    verzendingTellerBean;

    /**
     * Lijst van alle berichtverwerkers waarmee alle afnemerqueues worden uitgelezen.
     * Berichtverwerkers kunnen dynamisch worden toegevoegd, vandaar dat een
     * copy-on-writer list gebruikt wordt.
     *
     * LET OP: dit werkt vooralsnog enkel additief
     */
    private final List<BerichtVerwerker> afnemerQueues = new CopyOnWriteArrayList<>();

    private ExecutorService berichtVerwerkerExecutor;
    private ExecutorService berichtSelectorExecutor;

    /**
     * @throws JMSException als er een JMS fout optreedt
     */
    @PostConstruct
    public final void naMaak() throws JMSException {
        LOGGER.info("Starten Verzender");

        pooledConnectionFactory.setMaxConnections(aantalVerzendThreads);
        pooledConnectionFactory.setCreateConnectionOnStartup(true);
        pooledConnectionFactory.start();
        bouwVerwerkContextVoorAlleMogelijkePartijen();
        berichtSelectorExecutor = Executors.newSingleThreadExecutor();
        berichtSelectorExecutor.submit(new BerichtSelecteerder());
        this.berichtVerwerkerExecutor = Executors.newFixedThreadPool(aantalVerzendThreads);

        LOGGER.info("Einde starten Verzender");
    }

    /**
     * Sluit de threads netjes af.
     *
     * @throws InterruptedException als het afsluitproces onderbroken wordt
     */
    @PreDestroy
    public final void voorDestroy() throws InterruptedException {
        LOGGER.info("Stoppen Verzender");
        // interrupt de selector en wacht tot deze klaar is
        if (berichtSelectorExecutor != null) {
            berichtSelectorExecutor.shutdownNow();
            while (!berichtSelectorExecutor.isTerminated()) {
                LOGGER.info("Selector bezig met stoppen...");
                berichtSelectorExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
            }
        }
        LOGGER.info("Selector gestopt");
        // interrupt de verwerker threads en wacht tot deze klaar zijn
        if (berichtVerwerkerExecutor != null) {
            berichtVerwerkerExecutor.shutdownNow();
            while (!berichtVerwerkerExecutor.isTerminated()) {
                LOGGER.info("Berichtverwerkers bezig met stoppen...");
                berichtVerwerkerExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
            }
        }
        LOGGER.info("Berichtverwerkers gestopt");
        pooledConnectionFactory.stop();
        LOGGER.info("Verzender succesvol gestopt");
    }

    /**
     * JMX managed operation om te controleren op nieuw beschikbare afnemers. LET OP! Migratie (team blauw is afhankelijk van deze operatie)
     */
    @ManagedOperation(description = "updateAfnemers")
    public final void updateAfnemers() {
        LOGGER.info("Start check voor nieuwe afnemers");
        final Set<Integer> oudePartijCodes = new HashSet<>();
        for (final BerichtVerwerker afnemerQueue : afnemerQueues) {
            oudePartijCodes.add(afnemerQueue.getVerwerkContext().getPartijCode());
        }

        final Set<Integer> actuelePartijCodes = new HashSet<>();
        final List<PartijCodeAttribuut> actuelePartijCodeAttributen =
            afnemerQueueService.haalPartijCodesWaarvoorGeleverdMoetWorden();
        for (final PartijCodeAttribuut partijCodeAttribuut : actuelePartijCodeAttributen) {
            actuelePartijCodes.add(partijCodeAttribuut.getWaarde());
        }
        actuelePartijCodes.removeAll(oudePartijCodes);

        for (final Integer actuelePartijCode : actuelePartijCodes) {
            final VerwerkContext taak = new VerwerkContext(actuelePartijCode);
            afnemerQueues.add(new BerichtVerwerker(taak));
            LOGGER.info("Afnemer met code {} toegevoegd", actuelePartijCode);
        }
        updateJmxBean();
        LOGGER.info("Einde check voor nieuwe afnemers");
    }

    private void bouwVerwerkContextVoorAlleMogelijkePartijen() throws JMSException {
        LOGGER.info("Verwerk context wordt opgebouwd voor alle afnemer queues");
        final List<BerichtVerwerker> takenLijst = new LinkedList<>();
        final List<PartijCodeAttribuut> allePartijCodes = this.afnemerQueueService.haalPartijCodesWaarvoorGeleverdMoetWorden();
        for (final PartijCodeAttribuut partijCodeAttribuut : allePartijCodes) {
            final VerwerkContext taak = new VerwerkContext(partijCodeAttribuut.getWaarde());
            takenLijst.add(new BerichtVerwerker(taak));
        }
        afnemerQueues.addAll(takenLijst);
        updateJmxBean();
        LOGGER.info("Voor {} afnemers is een verwerkcontext opgebouwd", takenLijst.size());
    }

    private void updateJmxBean() {
        final List<VerwerkContext> contextList = new LinkedList<>();
        for (final BerichtVerwerker afnemerQueue : afnemerQueues) {
            contextList.add(afnemerQueue.getVerwerkContext());
        }
        verzendingTellerBean.setVerwerkContextList(contextList);
    }

    /**
     * Runnable om iteratief alle afnemer queue's te doorlopen en een verwerkopdracht te plaatsen in de pool van berichtverwerkers.
     */
    private class BerichtSelecteerder implements Runnable {
        public static final int WACHTTIJD_BIJ_GEEN_BERICHT = 50;

        @Override
        public void run() {
            Thread.currentThread().setName(Verzender.class.getSimpleName() + STREEPJE + BerichtSelecteerder.class.getSimpleName());
            LOGGER.info("Verzender gestart met selecteren van berichten uit afnemer queues");
            while (!Thread.interrupted()) {
                selecteerLoop();
            }
        }

        private void selecteerLoop() {
            final int aantalGeleverdeBerichten = verzendingTellerBean.getAantalGeleverdeBerichten();
            final int aantalErrorBerichten = verzendingTellerBean.getAantalErrorGeleverdeBerichten();

            for (final BerichtVerwerker taak : afnemerQueues) {
                final VerwerkContext verwerkContext = taak.getVerwerkContext();
                if (verwerkContext.getFuture() != null && !verwerkContext.getFuture().isDone()) {
                    continue;
                }
                verwerkContext.maakLeeg();
                //optimaliseer
                verwerkContext.setFuture(berichtVerwerkerExecutor.submit(taak));
            }

            final int deltaGeleverdeBerichten = verzendingTellerBean.getAantalGeleverdeBerichten() - aantalGeleverdeBerichten;
            final int deltaErrorBerichten = verzendingTellerBean.getAantalErrorGeleverdeBerichten() - aantalErrorBerichten;
            // voorkom eindeloos spinnen bij geen berichten
            if (deltaGeleverdeBerichten + deltaErrorBerichten == 0) {
                try {
                    //LOGGER.debug("Geen berichten verwerkt in deze iteratie");
                    TimeUnit.MILLISECONDS.sleep(WACHTTIJD_BIJ_GEEN_BERICHT);
                } catch (final InterruptedException e) {
                    LOGGER.error("BerichtSelecteerder interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Runnable om een N aantal berichten van een afnemer queue te verwerken.
     */
    private class BerichtVerwerker implements Runnable {
        private final VerwerkContext verwerkContext;

        public BerichtVerwerker(final VerwerkContext verwerkContext) {
            this.verwerkContext = verwerkContext;
        }

        public VerwerkContext getVerwerkContext() {
            return verwerkContext;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(Verzender.class.getSimpleName() + STREEPJE + verwerkContext.getQueueNaam());
            Connection jmsConnectie = null;
            Session jmsSessie = null;
            try {
                jmsConnectie = pooledConnectionFactory.createConnection();
                jmsConnectie.start();
                jmsSessie = jmsConnectie.createSession(true, Session.SESSION_TRANSACTED);
                final Queue wachtrij = jmsSessie.createQueue(verwerkContext.getQueueNaam());
                if (queueHeeftBerichten(jmsSessie, wachtrij)) {
                    final Destination destination = jmsSessie.createQueue(verwerkContext.getQueueNaam());
                    final MessageConsumer consumer = jmsSessie.createConsumer(destination);
                    for (int i = 0; i < aantalBerichtenPerIteratie; i++) {
                        final Message jmsBericht;
                        if (i == 0) {
                            // de 1e keer wachten we langer zodat de prefetch queue
                            // de tijd krijgt om gevuld te worden
                            jmsBericht = consumer.receive(wachttijdVoorBerichtMillis);
                        } else {
                            // op volgende berichten hoeft niet gewacht te worden omdat
                            // de prefetch meerdere berichten klaar zet
                            jmsBericht = consumer.receiveNoWait();
                        }
                        if (jmsBericht == null) {
                            break;
                        }
                        verwerkBericht(jmsBericht, jmsSessie);
                        LOGGER.debug("Bericht verwerkt");
                    }
                }
            } catch (final JMSException e) {
                LOGGER.error("Fout bij ontvangen van bericht", e);
            } finally {
                try {
                    if (jmsSessie != null) {
                        jmsSessie.close();
                    }
                } catch (final JMSException e) {
                    LOGGER.error("Fout bij jmsSessie.close na iteratie verwerking berichten: " + e.getMessage());
                }
                try {
                    if (jmsConnectie != null) {
                        jmsConnectie.close();
                    }
                } catch (final JMSException e) {
                    LOGGER.error("Fout bij jmsConnectie.close na iteratie verwerking berichten: " + e.getMessage());
                }
            }
        }

        /**
         * Snelle check om te controleren of er berichten op de queue staan
         */
        private boolean queueHeeftBerichten(final Session jmsSessie, final Queue wachtrij) throws JMSException {
            final QueueBrowser wachtrijBrowser = jmsSessie.createBrowser(wachtrij);
            try {
                return wachtrijBrowser.getEnumeration().hasMoreElements();
            } finally {
                wachtrijBrowser.close();
            }
        }

        private void verwerkBericht(final Message jmsBericht, final Session jmsSessie) {

            try {
                LOGGER.debug("Start bericht verwerking stappen");
                final long start = System.currentTimeMillis();
                stappenService.voerStappenUit(new BerichtContext(verwerkContext, jmsBericht));
                LOGGER.debug("Einde bericht verwerking stappen");
                jmsSessie.commit();
                LOGGER.debug("JMSsessie gecommit");
                verwerkContext.addSucces(System.currentTimeMillis() - start);
            } catch (final Exception e) {
                LOGGER.error("Fout bij verwerking van bericht", e);
                verwerkContext.addError();
                try {
                    if (jmsSessie != null) {
                        jmsSessie.rollback();
                    }
                } catch (final JMSException e1) {
                    LOGGER.error("Fout bij jmsSessie.rollback na fout verwerking: " + e1.getMessage());
                }
            }

        }
    }
}
