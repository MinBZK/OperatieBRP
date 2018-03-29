/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.ahpublicatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.ahpublicatie.AdmhndProducerVoorLeveringService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * AdmhnProducerVoorLeveringScheduler.
 */
@Component
class AdmhnProducerVoorLeveringScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final AdmhndProducerVoorLeveringService admhndProducerVoorLeveringService;

    @Inject
    public AdmhnProducerVoorLeveringScheduler(final AdmhndProducerVoorLeveringService admhnProducerVoorLeveringService) {
        admhndProducerVoorLeveringService = admhnProducerVoorLeveringService;
    }

    /**
     * herlaad.
     */
    @Scheduled(fixedDelayString = "${brp.levering.admhndpublicatie.delay:500}")
    public void herlaad() {
        LOGGER.trace("start lever batch administratieve handelingen voor levering");
        admhndProducerVoorLeveringService.produceerHandelingenVoorLevering();
        LOGGER.trace("einde lever batch administratieve handelingen voor levering");
    }

}
