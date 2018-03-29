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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
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
 * PersoonCacheProducerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersoonCacheProducerTest {

    @Mock
    private PersoonCacheSelectieRepository persoonCacheRepository;
    @Mock
    private SelectieJobRunStatusService selectieJobRunStatusService;
    @Mock
    private ConfiguratieService configuratieService;

    @InjectMocks
    private PersoonCacheProducer persoonCacheProducer;

    @Before
    public void setUp() {

        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(new SelectieJobRunStatus());

        persoonCacheProducer.setSelectieJobRunStatusService(selectieJobRunStatusService);
        persoonCacheProducer.setConfiguratieService(configuratieService);
        persoonCacheProducer.setPersoonCacheRepository(persoonCacheRepository);
    }

    @Test
    public void testProduceMinMaxGelijk() throws Exception {

        //config
        Mockito.when(configuratieService.getMaxSelectieTaak()).thenReturn(100);
        Mockito.when(configuratieService.getMaxSelectieSchrijfTaak()).thenReturn(100);
        Mockito.when(configuratieService.getBlobsPerSelectieTaak()).thenReturn(1);
        Mockito.when(configuratieService.getAutorisatiesPerSelectieTaak()).thenReturn(1);
        Mockito.when(configuratieService.getPoolSizeBlobBatchProducer()).thenReturn(1);
        Mockito.when(configuratieService.getBatchSizeBatchProducer()).thenReturn(1);

        //min max
        MinMaxPersoonCacheDTO minMax = new MinMaxPersoonCacheDTO();
        minMax.setMinId(1);
        minMax.setMaxId(1);
        Mockito.when(persoonCacheRepository.selecteerMinMaxIdVoorSelectie()).thenReturn(minMax);

        final BlockingQueue<List<PersoonCache>> queue = new ArrayBlockingQueue<>(100);
        final List<PersoonCache> batch = new ArrayList<>();
        queue.put(batch);
        //poison
        queue.put(new ArrayList<>());
        persoonCacheProducer.setQueue(queue);

        persoonCacheProducer.call();
        //min, max + 1
        Mockito.verify(persoonCacheRepository, times(1)).haalPersoonCachesOp(1, 2);
    }

    @Test
    public void testProduceMinMaxGroterDanBatchSize() throws Exception {

        //config
        Mockito.when(configuratieService.getMaxSelectieTaak()).thenReturn(100);
        Mockito.when(configuratieService.getMaxSelectieSchrijfTaak()).thenReturn(100);
        Mockito.when(configuratieService.getBlobsPerSelectieTaak()).thenReturn(1);
        Mockito.when(configuratieService.getAutorisatiesPerSelectieTaak()).thenReturn(1);
        Mockito.when(configuratieService.getPoolSizeBlobBatchProducer()).thenReturn(1);
        Mockito.when(configuratieService.getBatchSizeBatchProducer()).thenReturn(1);

        //min max
        MinMaxPersoonCacheDTO minMax = new MinMaxPersoonCacheDTO();
        minMax.setMinId(1);
        minMax.setMaxId(2);
        Mockito.when(persoonCacheRepository.selecteerMinMaxIdVoorSelectie()).thenReturn(minMax);

        final BlockingQueue<List<PersoonCache>> queue = new ArrayBlockingQueue<>(100);
        final List<PersoonCache> batch = new ArrayList<>();
        queue.put(batch);
        //poison
        queue.put(new ArrayList<>());
        persoonCacheProducer.setQueue(queue);

        persoonCacheProducer.call();

        Mockito.verify(persoonCacheRepository, times(2)).haalPersoonCachesOp(Mockito.anyInt(), Mockito.anyInt());
    }
}
