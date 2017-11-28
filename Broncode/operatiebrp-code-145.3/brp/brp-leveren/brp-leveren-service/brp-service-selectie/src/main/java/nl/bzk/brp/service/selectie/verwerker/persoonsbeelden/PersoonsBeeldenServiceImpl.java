/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.persoonsbeelden;

import com.google.common.collect.Lists;
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
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.SelectiePersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link PersoonsBeeldenService}
 *
 * Vertaalt de Blobs in de {@link SelectieVerwerkTaakBericht} parallel naar {@link Persoonslijst} objecten.
 */
@Service
final class PersoonsBeeldenServiceImpl implements PersoonsBeeldenService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ConfiguratieService configuratieService;

    @Inject
    private PersoonslijstService persoonslijstService;

    private PersoonsBeeldenServiceImpl() {
    }

    @Override
    public Collection<Persoonslijst> maakPersoonsBeelden(final SelectieVerwerkTaakBericht selectieTaak) throws SelectieException {
        ExecutorService persoonsLijstTaakExecutor = null;
        try {
            //overwegen of dit simpeler kan met parallel streams.
            //Maar we willen in ieder geval behouden dat we kunnen opgeven hoeveel threads het verwerkt wordt.
            LOGGER.debug("Start maak persoonsbeelden");
            //futures
            final List<Future<Object>> futures = new ArrayList<>();
            final int verwerkerPoolSize = configuratieService.getVerwerkerPoolSize();
            persoonsLijstTaakExecutor = Executors.newFixedThreadPool(verwerkerPoolSize);
            //opdrachten queue
            final BlockingQueue<MaakPersoonslijstBatchOpdracht> persoonsBeeldTaakQueue = new ArrayBlockingQueue<>(500);
            //resultaten queue
            final ConcurrentLinkedQueue<Persoonslijst> resultaatQueue = new ConcurrentLinkedQueue<>();
            //werk
            submitTaken(persoonsLijstTaakExecutor, futures, verwerkerPoolSize, persoonsBeeldTaakQueue, resultaatQueue);
            zetOpdrachtenOpQueue(selectieTaak, verwerkerPoolSize, persoonsBeeldTaakQueue);
            poison(verwerkerPoolSize, persoonsBeeldTaakQueue);
            wachtTotKlaar(futures);
            LOGGER.debug("Einde maak persoonsbeelden");
            return resultaatQueue;
        } catch (InterruptedException e) {
            LOGGER.info("Interrupt bij maken persoonsbeelden", e);
            Thread.currentThread().interrupt();
            throw new SelectieException(e);
        } catch (ExecutionException | TimeoutException e) {
            LOGGER.info("Fout bij maken persoonsbeelden", e);
            throw new SelectieException(e);
        } finally {
            if (persoonsLijstTaakExecutor != null) {
                persoonsLijstTaakExecutor.shutdownNow();
            }
        }
    }

    private void submitTaken(ExecutorService persoonsLijstTaakExecutor, List<Future<Object>> futures, int verwerkerPoolSize,
                             BlockingQueue<MaakPersoonslijstBatchOpdracht> persoonsBeeldTaakQueue, ConcurrentLinkedQueue<Persoonslijst> resultaatQueue) {
        for (int i = 0; i < verwerkerPoolSize; i++) {
            final MaakPersoonslijstBatchTaak maakPersoonslijstBatchTaak = new MaakPersoonslijstBatchTaak();
            maakPersoonslijstBatchTaak.setTaakQueue(persoonsBeeldTaakQueue);
            maakPersoonslijstBatchTaak.setResultaatQueue(resultaatQueue);
            maakPersoonslijstBatchTaak.setPersoonslijstService(persoonslijstService);
            futures.add(persoonsLijstTaakExecutor.submit(maakPersoonslijstBatchTaak));
        }
    }

    private void zetOpdrachtenOpQueue(SelectieVerwerkTaakBericht selectieTaak, int verwerkerPoolSize,
                                      BlockingQueue<MaakPersoonslijstBatchOpdracht> persoonsBeeldTaakQueue)
            throws InterruptedException {
        //zet de opdrachten op de queue
        final List<List<SelectiePersoonBericht>> bundelChunks = Lists.partition(selectieTaak.getPersonen(), verwerkerPoolSize);
        for (List<SelectiePersoonBericht> bundelChunk : bundelChunks) {
            final MaakPersoonslijstBatchOpdracht maakPersoonslijstBatchOpdracht = new MaakPersoonslijstBatchOpdracht();
            maakPersoonslijstBatchOpdracht.setCaches(bundelChunk);
            persoonsBeeldTaakQueue.put(maakPersoonslijstBatchOpdracht);
        }
    }

    private void poison(int verwerkerPoolSize, BlockingQueue<MaakPersoonslijstBatchOpdracht> persoonsBeeldTaakQueue) throws InterruptedException {
        //done poison
        for (int i = 0; i < verwerkerPoolSize; i++) {
            final MaakPersoonslijstBatchOpdracht maakPersoonslijstBatchOpdracht = new MaakPersoonslijstBatchOpdracht();
            maakPersoonslijstBatchOpdracht.setStop(true);
            persoonsBeeldTaakQueue.put(maakPersoonslijstBatchOpdracht);
        }
    }

    private void wachtTotKlaar(List<Future<Object>> futures)
            throws InterruptedException, ExecutionException, TimeoutException {
        //wacht voor klaar
        final long wachttijd = configuratieService.getMaximaleWachttijdMaakPersoonsBeeldMin();
        for (Future<Object> future : futures) {
            future.get(wachttijd, TimeUnit.MINUTES);
        }
    }
}
