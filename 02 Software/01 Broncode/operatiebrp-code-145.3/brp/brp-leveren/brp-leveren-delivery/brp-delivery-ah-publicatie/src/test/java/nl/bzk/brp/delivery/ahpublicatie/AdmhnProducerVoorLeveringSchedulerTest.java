/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.ahpublicatie;

import nl.bzk.brp.service.ahpublicatie.AdmhndProducerVoorLeveringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * AdmhnProducerVoorLeveringSchedulerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class AdmhnProducerVoorLeveringSchedulerTest {

    @Mock
    private AdmhndProducerVoorLeveringService admhnProducerVoorLeveringService;

    @InjectMocks
    private AdmhnProducerVoorLeveringScheduler admhnProducerVoorLeveringScheduler;

    @Test
    public void testHerlaad() {
        admhnProducerVoorLeveringScheduler.herlaad();
        Mockito.verify(admhnProducerVoorLeveringService, Mockito.times(1)).produceerHandelingenVoorLevering();
    }
}
