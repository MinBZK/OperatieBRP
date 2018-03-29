/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.protocollering;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.selectie.protocollering.SelectieProtocolleringService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * SelectieRunJob.
 */
@Component
final class SelectieProtocolleringScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private SelectieProtocolleringService selectieRunJobService;

    private SelectieProtocolleringScheduler() {
    }

    /**
     * run.
     */
    @Scheduled(fixedDelayString = "${brp.selectie.protocollering.delay:60000}")
    public void run() {
        LOGGER.info("startBericht selectie run");
        selectieRunJobService.start();
    }
}
