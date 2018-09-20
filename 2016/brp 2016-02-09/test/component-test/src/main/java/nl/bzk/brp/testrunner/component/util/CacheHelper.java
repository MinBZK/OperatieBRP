/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.omgeving.CacheHouder;
import nl.bzk.brp.testrunner.omgeving.Omgeving;

/**
 * Helper klasse om de cache van een omgeving te updaten.
 */
public final class CacheHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final Omgeving omgeving;

    public CacheHelper(final Omgeving omgeving) {
        this.omgeving = omgeving;
    }

    /**
     * Update parallel alle caches. Blocked tot alles bijgewerkt is.
     */
    public void update() {
        if(!omgeving.isGestart()) {
            throw new IllegalStateException("Omgeving niet gestart");
        }
        LOGGER.info("Start cache refresh");
        final VerversAlleCaches verversAlleCaches = new VerversAlleCaches();
        final ForkJoinTask taak = forkJoinPool.submit(verversAlleCaches);
        taak.join();
        LOGGER.info("Einde cache refresh");
    }

    private class VerversAlleCaches extends RecursiveTask {

        @Override
        protected Object compute() {
            final List<ForkJoinTask> verversCacheList = new LinkedList<>();
            for (final CacheHouder cacheHouder: omgeving.geefComponenten(CacheHouder.class)) {
                final VerversCache ververCacheTaak = new VerversCache(cacheHouder);
                verversCacheList.add(ververCacheTaak.fork());
            }
            for (final ForkJoinTask taak : verversCacheList) {
                taak.join();
            }
            return null;
        }
    }

    private static class VerversCache extends RecursiveTask {

        private CacheHouder cacheHouder;

        public VerversCache(final CacheHouder cacheHouder) {
            this.cacheHouder = cacheHouder;
        }

        @Override
        protected Object compute() {
            cacheHouder.updateCache();
            return null;
        }
    }
}
