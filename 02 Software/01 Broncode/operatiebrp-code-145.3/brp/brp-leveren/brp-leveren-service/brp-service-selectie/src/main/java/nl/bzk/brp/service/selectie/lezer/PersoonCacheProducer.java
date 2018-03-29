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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;

/**
 * PersoonCacheProducer.
 */
public final class PersoonCacheProducer implements Callable<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private PersoonCacheSelectieRepository persoonCacheRepository;
    private SelectieJobRunStatusService selectieJobRunStatusService;
    private ConfiguratieService configuratieService;

    private BlockingQueue<List<PersoonCache>> persoonCachesQueue;

    @Override
    public Object call() throws Exception {
        BrpNu.set();
        final BlockingQueue<PersoonCacheBatchOpdracht> taakQueue = new ArrayBlockingQueue<>(100);
        final ExecutorService executor = Executors.newFixedThreadPool(configuratieService.getPoolSizeBlobBatchProducer());
        LOGGER.info("start producer cache batches");
        try {
            final MinMaxPersoonCacheDTO minMaxPersoonCacheDTO = persoonCacheRepository.selecteerMinMaxIdVoorSelectie();
            LOGGER.info(String.format("producer cache batches voor min id %d en max id %d", minMaxPersoonCacheDTO.getMinId(),
                    minMaxPersoonCacheDTO.getMaxId()));
            setTotaalSelectieTaken(minMaxPersoonCacheDTO);
            //de futures
            final List<Future<Object>> futures = new ArrayList<>(configuratieService.getPoolSizeBlobBatchProducer());
            submitTaken(taakQueue, executor, futures);
            LOGGER.info("start producing batch");
            zetOpdrachtenOpQueue(taakQueue, minMaxPersoonCacheDTO);
            poisonTaken(taakQueue);
            wachtTotKlaar(futures);
            LOGGER.info("klaar producing batch, poison messages");
            poisonConsumer();
            LOGGER.info("einde producer batch");
            return null;
        } catch (TimeoutException | ExecutionException e) {
            LOGGER.info("error producer cache batches", e);
            throw new BrpServiceRuntimeException(e);
        } catch (InterruptedException e) {
            LOGGER.info("error producer cache batches", e);
            Thread.currentThread().interrupt();
            throw new BrpServiceRuntimeException(e);
        } finally {
            executor.shutdownNow();
        }
    }

    private void setTotaalSelectieTaken(MinMaxPersoonCacheDTO minMaxPersoonCacheDTO) {
        final int totaalBatches = (int) (minMaxPersoonCacheDTO.getMaxId() - minMaxPersoonCacheDTO.getMinId()) / configuratieService.getBatchSizeBatchProducer();
        final int totaalSelectieTaken = totaalBatches * (configuratieService.getBatchSizeBatchProducer() / configuratieService.getBlobsPerSelectieTaak());
        selectieJobRunStatusService.getStatus().setTotaalAantalSelectieTaken(totaalSelectieTaken);
    }

    private void poisonConsumer() throws InterruptedException {
        persoonCachesQueue.put(new ArrayList<>());
    }

    private void wachtTotKlaar(List<Future<Object>> futures)
            throws InterruptedException, ExecutionException, TimeoutException {
        for (Future<Object> future : futures) {
            future.get(1, TimeUnit.MINUTES);
        }
    }

    private void poisonTaken(BlockingQueue<PersoonCacheBatchOpdracht> taakQueue) throws InterruptedException {
        for (int i = 0; i < configuratieService.getPoolSizeBlobBatchProducer(); i++) {
            final PersoonCacheBatchOpdracht taak = new PersoonCacheBatchOpdracht();
            taak.setStop(true);
            taakQueue.put(taak);
        }
    }

    private void zetOpdrachtenOpQueue(BlockingQueue<PersoonCacheBatchOpdracht> taakQueue, MinMaxPersoonCacheDTO minMaxPersoonCacheDTO)
            throws InterruptedException {
        final boolean stop = selectieJobRunStatusService.getStatus().moetStoppen();
        for (long i = minMaxPersoonCacheDTO.getMinId(); i < minMaxPersoonCacheDTO.getMaxId() + 1 && !stop;
             i = i + configuratieService.getBatchSizeBatchProducer()) {
            final PersoonCacheBatchOpdracht taak = new PersoonCacheBatchOpdracht();
            taak.setMinIdPersoonCache(i);
            taak.setMaxIdPersoonCache(i + configuratieService.getBatchSizeBatchProducer());
            taakQueue.put(taak);
        }
    }

    private void submitTaken(BlockingQueue<PersoonCacheBatchOpdracht> taakQueue, ExecutorService executor, List<Future<Object>> futures) {
        for (int i = 0; i < configuratieService.getPoolSizeBlobBatchProducer(); i++) {
            final PersoonCacheBatchTaak maker = new PersoonCacheBatchTaak();
            maker.setTaakQueue(taakQueue);
            maker.setPersoonCacheRepository(persoonCacheRepository);
            maker.setQueue(persoonCachesQueue);
            futures.add(executor.submit(maker));
        }
    }

    public void setQueue(final BlockingQueue<List<PersoonCache>> queue) {
        this.persoonCachesQueue = queue;
    }

    public void setPersoonCacheRepository(PersoonCacheSelectieRepository persoonCacheRepository) {
        this.persoonCacheRepository = persoonCacheRepository;
    }

    public void setSelectieJobRunStatusService(SelectieJobRunStatusService selectieJobRunStatusService) {
        this.selectieJobRunStatusService = selectieJobRunStatusService;
    }

    public void setConfiguratieService(ConfiguratieService configuratieService) {
        this.configuratieService = configuratieService;
    }
}
