/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
import org.springframework.stereotype.Service;

/**
 * SelectieLezerServiceImpl.
 */
@Service
final class SelectieLezerServiceImpl implements SelectieLezerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonCacheSelectieRepository persoonCacheRepository;

    @Inject
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Inject
    private SelectieTaakPublicatieService selectieTaakPublicatieService;

    @Inject
    private ConfiguratieService configuratieService;


    private SelectieLezerServiceImpl() {
    }


    @Override
    public void startLezen(final Selectie selectie) throws SelectieException {
        LOGGER.info("start lezen service");
        final BlockingQueue<List<PersoonCache>> cachesBatchesQueue = new ArrayBlockingQueue<>(100);
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        //setup producer
        final PersoonCacheProducer persoonCacheProducer = new PersoonCacheProducer();
        persoonCacheProducer.setQueue(cachesBatchesQueue);
        persoonCacheProducer.setSelectieJobRunStatusService(selectieJobRunStatusService);
        persoonCacheProducer.setConfiguratieService(configuratieService);
        persoonCacheProducer.setPersoonCacheRepository(persoonCacheRepository);
        //setup consumer
        final PersoonCacheConsumer persoonCacheConsumer = new PersoonCacheConsumer();
        persoonCacheConsumer.setQueue(cachesBatchesQueue);
        persoonCacheConsumer.setSelectie(selectie);
        persoonCacheConsumer.setSelectieJobRunStatusService(selectieJobRunStatusService);
        persoonCacheConsumer.setSelectieTaakPublicatieService(selectieTaakPublicatieService);
        persoonCacheConsumer.setConfiguratieService(configuratieService);
        try {
            final long maximaleLoopTijd = configuratieService.getMaximaleLooptijdSelectierun();
            final List<Future<Object>> futures = new ArrayList<>();
            futures.add(executor.submit(persoonCacheProducer));
            futures.add(executor.submit(persoonCacheConsumer));
            // Individuele futures afhandelen voor foutafhandeling. Bij een fout in een Callable houden ook de caller en eventuele andere callee's op.
            for (Future<Object> future : futures) {
                future.get(maximaleLoopTijd, TimeUnit.HOURS);
            }
            LOGGER.info("einde lezen service");
        } catch (InterruptedException e) {
            LOGGER.error("Interrupt error running job");
            Thread.currentThread().interrupt();
            throw new SelectieException(e);
        } catch (ExecutionException | TimeoutException e) {
            LOGGER.error("Error running job");
            throw new SelectieException(e);
        } finally {
            executor.shutdownNow();
        }
    }
}
