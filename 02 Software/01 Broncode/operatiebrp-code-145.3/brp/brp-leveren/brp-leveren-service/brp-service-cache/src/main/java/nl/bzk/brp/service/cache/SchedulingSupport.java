/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import javax.inject.Inject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Ingangspunt voor de Spring scheduler om caches te verversen.
 */
@Component
class SchedulingSupport {

    private final CacheController cacheController;

    @Inject
    public SchedulingSupport(final CacheController cacheController) {
        this.cacheController = cacheController;
    }


    /**
     * Methode die door de Spring scheduler aangeroepen wordt om de caches te verversen.
     */
    @Scheduled(cron = "${brp.levering.cache.cron:0 0 0 * * *}")
    public void herlaad() {
        cacheController.herlaadCaches();
    }

}


