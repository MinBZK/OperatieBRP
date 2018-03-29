/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.service.selectie.TestSelectie;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
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
 * SelectieLezerServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieLezerServiceImplTest {

    @Mock
    private PersoonCacheSelectieRepository persoonCacheRepository;

    @Mock
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Mock
    private SelectieTaakPublicatieService selectieTaakPublicatieService;

    @Mock
    private ConfiguratieService configuratieService;

    @InjectMocks
    private SelectieLezerServiceImpl selectieLezerService;

    @Before
    public void voorTest() {
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(new SelectieJobRunStatus());
    }

    @Test
    public void testHappyFlow() throws SelectieException, InterruptedException {
        Mockito.when(configuratieService.getMaximaleLooptijdSelectierun()).thenReturn(10L);
        Mockito.when(configuratieService.getPoolSizeBlobBatchProducer()).thenReturn(1);
        Mockito.when(configuratieService.getBatchSizeBatchProducer()).thenReturn(100);
        Mockito.when(configuratieService.getBlobsPerSelectieTaak()).thenReturn(100);
        Mockito.when(configuratieService.getMaxSelectieTaak()).thenReturn(100);
        Mockito.when(configuratieService.getMaxSelectieSchrijfTaak()).thenReturn(100);
        Mockito.when(configuratieService.getAutorisatiesPerSelectieTaak()).thenReturn(100);
        final MinMaxPersoonCacheDTO minMax = new MinMaxPersoonCacheDTO();
        minMax.setMinId(1);
        minMax.setMaxId(100);
        Mockito.when(persoonCacheRepository.selecteerMinMaxIdVoorSelectie()).thenReturn(minMax);

        final PersoonCache persoonCache = new PersoonCache(new Persoon(SoortPersoon.INGESCHREVENE), (short) 1);
        final List<PersoonCache> persoonCaches = Lists.newArrayList(persoonCache);
        Mockito.when(persoonCacheRepository.haalPersoonCachesOp(Mockito.anyInt(), Mockito.anyInt())).thenReturn(persoonCaches);

        final Selectie selectie = TestSelectie.maakSelectieMetSelectietaakInRun();
        selectieLezerService.startLezen(selectie);
    }

    @Test(expected = SelectieException.class)
    public void testFoutFlow() throws SelectieException {
        Mockito.when(configuratieService.getMaximaleLooptijdSelectierun()).thenReturn(0L);
        Mockito.when(configuratieService.getPoolSizeBlobBatchProducer()).thenReturn(1);
        Mockito.when(configuratieService.getBatchSizeBatchProducer()).thenReturn(100);
        Mockito.when(configuratieService.getBlobsPerSelectieTaak()).thenReturn(100);
        Mockito.when(configuratieService.getMaxSelectieTaak()).thenReturn(100);
        Mockito.when(configuratieService.getMaxSelectieSchrijfTaak()).thenReturn(100);
        final MinMaxPersoonCacheDTO minMax = new MinMaxPersoonCacheDTO();
        minMax.setMinId(1);
        minMax.setMaxId(100);
        Mockito.when(persoonCacheRepository.selecteerMinMaxIdVoorSelectie()).thenReturn(minMax);
        final List<PersoonCache> persoonCaches = new ArrayList<>();
        Mockito.when(persoonCacheRepository.haalPersoonCachesOp(Mockito.anyInt(), Mockito.anyInt())).thenReturn(persoonCaches);

        selectieLezerService.startLezen(TestSelectie.maakSelectie());
    }


}
