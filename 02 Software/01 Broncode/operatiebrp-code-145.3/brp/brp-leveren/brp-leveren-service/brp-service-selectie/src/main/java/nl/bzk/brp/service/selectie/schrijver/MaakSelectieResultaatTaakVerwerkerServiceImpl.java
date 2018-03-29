/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SelectieKenmerken;
import nl.bzk.brp.domain.berichtmodel.SelectieResultaatBericht;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Verwerkt alle persoon fragmenten tot selectie resultaat berichten. Daarna wordt een totalen bestand gemaakt en een steekproef bestand.
 *
 * Voor elke selectieautorisatie wordt een producer gemaakt om de fragmenten te lezen en een aantal consumers om
 * hiervan een set resultaatberichten te maken.
 */
@Component
@Qualifier("maakSelectieResultaatTaakVerwerkerServiceImpl")
final class MaakSelectieResultaatTaakVerwerkerServiceImpl implements MaakSelectieResultaatTaakVerwerkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private SelectieResultaatWriterFactory selectieResultaatWriterFactory;

    @Inject
    private ConfiguratieService configuratieService;

    @Inject
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    @Inject
    private SelectieFileService selectieFileService;

    @Inject
    private PartijService partijService;

    @Inject
    private SteekproefService steekproefService;

    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;

    private MaakSelectieResultaatTaakVerwerkerServiceImpl() {
    }

    @Override
    public void verwerk(final MaakSelectieResultaatTaak taak) {
        LOGGER.info("Start met verwerken maak selectie resultaat taak");
        final int poolSize = configuratieService.getSchrijverPoolSize();
        final ExecutorService producerExecutor = Executors.newFixedThreadPool(1);
        final ExecutorService consumerExecutor = Executors.newFixedThreadPool(poolSize);
        try {
            //kijk eerst of taak ongeldig is. In dat geval verwijderen we de resultaat folders
            if (taak.isOngeldig()) {
                LOGGER.warn(String.format("Ongeldige taak [%d] in selectie run, verwijder de resultaat folders", taak.getSelectietaakId()));
            } else {
                //concat nog laatste part files tot fragment files
                selectieFileService.concatLaatsteDeelFragmenten(taak);
                final int queueSize = 50;
                final BlockingQueue<String> persoonFragmentQueue = new ArrayBlockingQueue<>(queueSize);
                //alle futures om op te wachten
                final List<Future<Integer>> consumerFutures = new ArrayList<>();
                //start submitting
                final AtomicInteger berichtIdCounter = new AtomicInteger();
                final Future<Object> producerFuture = submitProducer(taak, producerExecutor, persoonFragmentQueue);
                submitConsumers(taak, poolSize, consumerExecutor, persoonFragmentQueue, consumerFutures, berichtIdCounter);
                wachtTotProducerKlaar(producerFuture);
                poisonConsumers(poolSize, persoonFragmentQueue);
                final int totaalAantalPersonen = wachtTotConsumersKlaar(consumerFutures);
                //maak totalen
                maakTotalenBestand(berichtIdCounter, totaalAantalPersonen, taak);
                steekproefService.maakSteekproefBestand(taak);
            }
            //schoon dirs
            selectieFileService.schoonResultaatDirectory(taak);
            //publiceer resultaat
            publiceerResultaat();
            LOGGER.info("Einde met verwerken maak selectie resultaat taak");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            handleException(e);
        } catch (Exception e) {
            //vang alle fouten af, voor publicatie.
            handleException(e);
        } finally {
            producerExecutor.shutdownNow();
            consumerExecutor.shutdownNow();
        }
    }

    private void submitConsumers(final MaakSelectieResultaatTaak maakSelectieResultaatTaak, int poolSize, ExecutorService fragmentVerwerkerExecutor,
                                 BlockingQueue<String> persoonFragmentQueue, List<Future<Integer>> consumerFutures, AtomicInteger berichtIdCounter) {

        for (int i = 0; i < poolSize; i++) {
            final PersoonFragmentConsumer consumer = new PersoonFragmentConsumer();
            consumer.setQueue(persoonFragmentQueue);
            consumer.setMaakSelectieResultaatTaak(maakSelectieResultaatTaak);
            consumer.setSelectieResultaatWriterFactory(selectieResultaatWriterFactory);
            consumer.setPartijService(partijService);
            consumer.setLeveringsautorisatieService(leveringsautorisatieService);
            consumer.setCounter(berichtIdCounter);
            consumerFutures.add(fragmentVerwerkerExecutor.submit(consumer));
        }
    }

    private Future<Object> submitProducer(MaakSelectieResultaatTaak maakSelectieResultaatTaak, ExecutorService producerExecutor,
                                          BlockingQueue<String> persoonFragmentQueue) {
        final PersoonFragmentProducer producer = new PersoonFragmentProducer();
        producer.setSelectieMaakSelectieResultaatTaak(maakSelectieResultaatTaak);
        producer.setQueue(persoonFragmentQueue);
        producer.setSelectieFileService(selectieFileService);
        return producerExecutor.submit(producer);
    }

    private void publiceerResultaat() {
        SelectieTaakResultaat selectieResultaat = new SelectieTaakResultaat();
        selectieResultaat.setType(TypeResultaat.SELECTIE_RESULTAAT_SCHRIJF);
        selectieTaakResultaatPublicatieService.publiceerSelectieTaakResultaat(selectieResultaat);
    }

    private int wachtTotConsumersKlaar(List<Future<Integer>> consumerFutures)
            throws InterruptedException, ExecutionException, TimeoutException {
        final long wachttijd = configuratieService.getMaximaleWachttijdFragmentVerwerkerMin();
        // Individuele futures afhandelen voor foutafhandeling. Bij een fout in een Callable houden ook de caller en eventuele andere callee's op.
        int totaalAantalPersonen = 0;
        for (Future<Integer> future : consumerFutures) {
            final Integer aantalPersonen = future.get(wachttijd, TimeUnit.MINUTES);
            totaalAantalPersonen += aantalPersonen;
        }
        return totaalAantalPersonen;
    }

    private void wachtTotProducerKlaar(Future<Object> producerFuture)
            throws InterruptedException, ExecutionException, TimeoutException {
        final long wachttijd = configuratieService.getMaximaleWachttijdFragmentVerwerkerMin();
        producerFuture.get(wachttijd, TimeUnit.MINUTES);
    }

    private void poisonConsumers(int poolSize, BlockingQueue<String> persoonFragmentQueue) throws InterruptedException {
        for (int i = 0; i < poolSize; i++) {
            persoonFragmentQueue.put(PersoonFragmentConsumer.POISON);
        }
    }

    private void maakTotalenBestand(final AtomicInteger counter, final int totaalPersonen, final MaakSelectieResultaatTaak maakSelectieResultaatTaak)
            throws SelectieResultaatVerwerkException {
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie =
                leveringsautorisatieService.geefToegangLeveringsAutorisatie(maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId());
        final Partij ontvangendePartij = toegangLeveringsAutorisatie.getGeautoriseerde().getPartij();
        final Dienst dienst = AutAutUtil.zoekDienst(toegangLeveringsAutorisatie.getLeveringsautorisatie(), maakSelectieResultaatTaak.getDienstId());
        final int volgnummer = counter.incrementAndGet();
        final SelectieResultaatSchrijfInfo info = new SelectieResultaatSchrijfInfo();
        info.setBerichtId(volgnummer);
        info.setSelectieRunId(maakSelectieResultaatTaak.getSelectieRunId());
        info.setDienstId(maakSelectieResultaatTaak.getDienstId());
        info.setToegangLeveringsAutorisatieId(maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId());
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .metZendendePartij(partijService.geefBrpPartij())
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metOntvangendePartij(ontvangendePartij)
                .eindeStuurgegevens().build();
        final SelectieKenmerken
                selectieKenmerken =
                SelectieKenmerkenBuilderFactory.maak(toegangLeveringsAutorisatie, dienst, maakSelectieResultaatTaak, volgnummer)
                        .metSoortSelectieresultaatSet("Resultaatset totalen").build();
        final SelectieResultaatBericht totalenBericht = new SelectieResultaatBericht(basisBerichtGegevens, selectieKenmerken);
        if (toegangLeveringsAutorisatie.getLeveringsautorisatie().getStelsel() == Stelsel.BRP) {
            final SelectieResultaatWriterFactory.TotalenBestandWriter totalenWriterBrp = selectieResultaatWriterFactory.totalenWriterBrp(info, totalenBericht);
            totalenWriterBrp.schrijfTotalen(totaalPersonen, volgnummer);
            totalenWriterBrp.eindeBericht();
        } else {
            final SelectieResultaatWriterFactory.TotalenBestandWriter totalenWriterGba = selectieResultaatWriterFactory.totalenWriterGba(info, totalenBericht);
            totalenWriterGba.schrijfTotalen(totaalPersonen, volgnummer);
            totalenWriterGba.eindeBericht();
        }
    }

    private void handleException(Exception e) {
        LOGGER.error("Error met verwerken maak selectie resultaat taak", e);
        selectieTaakResultaatPublicatieService.publiceerFout();
    }

}
