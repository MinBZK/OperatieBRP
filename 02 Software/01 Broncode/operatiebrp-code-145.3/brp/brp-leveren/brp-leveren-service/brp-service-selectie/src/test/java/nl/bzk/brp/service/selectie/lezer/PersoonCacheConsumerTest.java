/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.service.selectie.TestSelectie;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * PersoonCacheConsumerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersoonCacheConsumerTest {

    @Mock
    private SelectieTaakPublicatieService selectieTaakPublicatieService;

    @Mock
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Mock
    private ConfiguratieService configuratieService;

    @InjectMocks
    private PersoonCacheConsumer persoonCacheConsumer;

    @Before
    public void setUp() {

        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(new SelectieJobRunStatus());

        persoonCacheConsumer.setSelectieJobRunStatusService(selectieJobRunStatusService);
        persoonCacheConsumer.setConfiguratieService(configuratieService);
        persoonCacheConsumer.setSelectieTaakPublicatieService(selectieTaakPublicatieService);
    }

    @Test
    public void testHappyFlow() throws Exception {
        //config
        Mockito.when(configuratieService.getMaxSelectieTaak()).thenReturn(100);
        Mockito.when(configuratieService.getMaxSelectieSchrijfTaak()).thenReturn(100);
        Mockito.when(configuratieService.getBlobsPerSelectieTaak()).thenReturn(1);
        Mockito.when(configuratieService.getAutorisatiesPerSelectieTaak()).thenReturn(1);

        persoonCacheConsumer.setSelectie(TestSelectie.maakSelectie());

        final BlockingQueue<List<PersoonCache>> queue = new ArrayBlockingQueue<>(2);
        final List<PersoonCache> batch = new ArrayList<>();
        final PersoonCache persoonCache = new PersoonCache(new Persoon(SoortPersoon.INGESCHREVENE), (short) 1);
        batch.add(persoonCache);
        queue.put(batch);
        //poison
        queue.put(new ArrayList<>());
        persoonCacheConsumer.setQueue(queue);

        persoonCacheConsumer.call();

        Mockito.verify(selectieTaakPublicatieService, times(1)).publiceerSelectieTaak(Mockito.any());
    }
}
