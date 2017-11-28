/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;

/**
 * MaakPersoonslijstBatchTaak.
 */
final class PersoonCacheBatchTaak implements Callable<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private PersoonCacheSelectieRepository persoonCacheRepository;

    private BlockingQueue<List<PersoonCache>> queue;
    private BlockingQueue<PersoonCacheBatchOpdracht> taakQueue;

    @Override
    public Object call() throws Exception {
        LOGGER.info("startBericht persoon cache taak");
        BrpNu.set();
        boolean klaar = false;
        while (!klaar) {
            LOGGER.debug("startBericht persoon cache taak iteratie");
            final PersoonCacheBatchOpdracht taak = taakQueue.poll(10, TimeUnit.SECONDS);
            if (taak != null) {
                if (taak.isStop()) {
                    LOGGER.info("klaar persoon cache taak, poison message ontvangen");
                    klaar = true;
                } else {
                    verwerk(taak);
                }
            }
        }
        LOGGER.info("einde persoon cache taak");
        return null;

    }

    private void verwerk(PersoonCacheBatchOpdracht taak) throws InterruptedException {
        final List<PersoonCache> persoonCaches = persoonCacheRepository.haalPersoonCachesOp(taak.getMinIdPersoonCache(), taak.getMaxIdPersoonCache());
        LOGGER.info("Aantal personen opgehaald {} [min={}, max={}]", persoonCaches.size(), taak.getMinIdPersoonCache(), taak.getMaxIdPersoonCache());
        if (!persoonCaches.isEmpty()) {
            queue.put(persoonCaches);
        }
    }

    /**
     * @param queue queue
     */
    public void setQueue(BlockingQueue<List<PersoonCache>> queue) {
        this.queue = queue;
    }

    /**
     * @param taakQueue taakQueue
     */
    public void setTaakQueue(BlockingQueue<PersoonCacheBatchOpdracht> taakQueue) {
        this.taakQueue = taakQueue;
    }

    /**
     * @param persoonCacheRepository persoonCacheRepository
     */
    public void setPersoonCacheRepository(PersoonCacheSelectieRepository persoonCacheRepository) {
        this.persoonCacheRepository = persoonCacheRepository;
    }
}
