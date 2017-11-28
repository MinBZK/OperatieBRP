/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import org.springframework.stereotype.Service;

/**
 * VerwerkPersoonServiceImpl.
 */
@Service
final class VerwerkPersoonExecutorServiceImpl implements VerwerkPersoonExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ConfiguratieService configuratieService;

    @Inject
    @Named("verwerkPersoonService")
    private VerwerkPersoonService verwerkPersoonService;

    private VerwerkPersoonExecutorServiceImpl() {
    }

    @Override
    public Collection<VerwerkPersoonResultaat> verwerkPersonen(final SelectieVerwerkTaakBericht selectieTaak,
                                                               final Collection<Persoonslijst> personen,
                                                               final List<SelectieAutorisatiebundel> autorisatiebundels) throws SelectieException {

        LOGGER.debug("Start maken persoon bericht");
        ExecutorService maakBerichtTaakExecutor = null;
        try {
            //de futures
            final List<Future<Object>> futures = new ArrayList<>();
            final int verwerkerPoolSize = configuratieService.getVerwerkerPoolSize();
            //executor
            maakBerichtTaakExecutor = Executors.newFixedThreadPool(verwerkerPoolSize);
            //opdrachten queue
            final BlockingQueue<MaakSelectieResultaatOpdracht> berichtOpdrachtTaakQueue = new ArrayBlockingQueue<>(100);
            //resultaten queue
            final ConcurrentLinkedQueue<VerwerkPersoonResultaat> resultaatQueueBerichten = new ConcurrentLinkedQueue<>();
            submitTaken(maakBerichtTaakExecutor, futures, verwerkerPoolSize, berichtOpdrachtTaakQueue, resultaatQueueBerichten);
            zetOpdrachtenOpQueue(selectieTaak, personen, autorisatiebundels, berichtOpdrachtTaakQueue);
            poison(verwerkerPoolSize, berichtOpdrachtTaakQueue);
            wachtTotKlaar(futures);
            LOGGER.debug("Einde maken persoon bericht");
            return resultaatQueueBerichten;
        } catch (InterruptedException e) {
            LOGGER.info("Interrupt bij maken persoon bericht", e);
            Thread.currentThread().interrupt();
            throw new SelectieException(e);
        } catch (ExecutionException | TimeoutException e) {
            LOGGER.info("Fout bij maken persoon bericht", e);
            throw new SelectieException(e);
        } finally {
            if (maakBerichtTaakExecutor != null) {
                maakBerichtTaakExecutor.shutdownNow();
            }
        }
    }

    private void submitTaken(ExecutorService maakBerichtTaakExecutor, List<Future<Object>> futures, int verwerkerPoolSize,
                             BlockingQueue<MaakSelectieResultaatOpdracht> berichtOpdrachtTaakQueue,
                             ConcurrentLinkedQueue<VerwerkPersoonResultaat> resultaatBerichtenQueue) {
        for (int i = 0; i < verwerkerPoolSize; i++) {
            final VerwerkPersoonTaak maakSelectieResultaatTaak = new VerwerkPersoonTaak();
            maakSelectieResultaatTaak.setResultaatQueue(resultaatBerichtenQueue);
            maakSelectieResultaatTaak.setTaakQueue(berichtOpdrachtTaakQueue);
            maakSelectieResultaatTaak.setVerwerkPersoonService(verwerkPersoonService);
            futures.add(maakBerichtTaakExecutor.submit(maakSelectieResultaatTaak));
        }
    }

    private void zetOpdrachtenOpQueue(SelectieVerwerkTaakBericht selectieTaak,
                                      Collection<Persoonslijst> lijstMetPersonen,
                                      List<SelectieAutorisatiebundel> autorisatiebundels,
                                      BlockingQueue<MaakSelectieResultaatOpdracht> berichtOpdrachtTaakQueue) throws InterruptedException {
        for (Persoonslijst persoonslijst : lijstMetPersonen) {
            final MaakSelectieResultaatOpdracht maakSelectieResultaatOpdracht = new MaakSelectieResultaatOpdracht();
            maakSelectieResultaatOpdracht.setAutorisatiebundels(autorisatiebundels);
            maakSelectieResultaatOpdracht.setPersoonslijst(persoonslijst);
            maakSelectieResultaatOpdracht.setSelectieRunId(selectieTaak.getSelectieRunId());
            maakSelectieResultaatOpdracht.setSelectieStartDatum(selectieTaak.getSelectieStartDatum());
            berichtOpdrachtTaakQueue.put(maakSelectieResultaatOpdracht);
        }
    }

    private void wachtTotKlaar(List<Future<Object>> futures)
            throws InterruptedException, ExecutionException, TimeoutException {
        final long wachttijd = configuratieService.getMaximaleWachttijdPersoonslijstFragmentMin();
        // Individuele futures afhandelen voor foutafhandeling. Bij een fout in een Callable houden ook de caller en eventuele andere callee's op.
        for (Future<Object> future : futures) {
            future.get(wachttijd, TimeUnit.MINUTES);
        }
    }

    private void poison(int verwerkerPoolSize, BlockingQueue<MaakSelectieResultaatOpdracht> berichtOpdrachtTaakQueue) throws InterruptedException {
        for (int i = 0; i < verwerkerPoolSize; i++) {
            final MaakSelectieResultaatOpdracht maakSelectieResultaatOpdracht = new MaakSelectieResultaatOpdracht();
            maakSelectieResultaatOpdracht.setStop(true);
            berichtOpdrachtTaakQueue.put(maakSelectieResultaatOpdracht);
        }
    }
}
