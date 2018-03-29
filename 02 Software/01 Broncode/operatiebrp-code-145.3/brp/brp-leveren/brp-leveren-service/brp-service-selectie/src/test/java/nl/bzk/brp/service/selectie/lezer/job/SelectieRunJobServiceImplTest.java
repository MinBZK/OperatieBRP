/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.service.selectie.TestSelectie;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import nl.bzk.brp.service.selectie.lezer.SelectieLezerService;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
import nl.bzk.brp.service.selectie.verwerker.SelectieSchrijfTaakPublicatieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * SelectieRunJobServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieRunJobServiceImplTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private SelectieService selectieService;

    @Mock
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Mock
    private SelectieSchrijfTaakPublicatieService selectieSchrijfTaakPublicatieService;

    @Mock
    private MaakSelectieResultaatTaakPublicatieService maakSelectieResultaatTaakPublicatieService;

    @Mock
    private ConfiguratieService configuratieService;

    @Mock
    private SelectieLezerService selectieLezerService;

    @InjectMocks
    private SelectieRunJobServiceImpl selectieRunJobService;

    @Captor
    private ArgumentCaptor<List<MaakSelectieResultaatTaak>> maakSelectietaakResultaatArgCaptor;

    @Before
    public void voorTest() throws Exception {
        final SelectieJobRunStatus status = new SelectieJobRunStatus();
        Mockito.when(selectieJobRunStatusService.newStatus()).thenReturn(status);
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(status);
    }

    @Test
    public void test_isRunning_running() {
        assertTrue(selectieRunJobService.isRunning());
        Mockito.verify(selectieJobRunStatusService, times(1)).getStatus();
    }

    @Test
    public void test_isRunning_stopped() {
        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();
        selectieJobRunStatus.setEindeDatum(new Date());
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(selectieJobRunStatus);

        assertFalse(selectieRunJobService.isRunning());
        Mockito.verify(selectieJobRunStatusService, times(1)).getStatus();
    }

    @Test
    public void test_stop() {
        selectieRunJobService.stop();

        Mockito.verify(applicationContext, times(1)).publishEvent(Mockito.any());
    }


    @Test
    public void testFlowGeenTaken() {
        final Selectie selectie = TestSelectie.maakLegeSelectie();
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(selectie);

        selectieRunJobService.start();

        Mockito.verify(selectieService, times(1)).eindeSelectie(Mockito.any());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getStartDatum());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getEindeDatum());
    }

    @Test(expected = NullPointerException.class)
    public void test_start_selectieisNull() {
        final Timestamp tsNu = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now());
        final Selectie selectie = TestSelectie.maakSelectieMetSelectietaakInRun();
        selectie.getSelectierun().setId(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setHistorievormSelectie(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentFormeelResultaat(tsNu);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentMaterieelResultaat(20140101);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setId(2);
        selectie.getSelectietaakAutorisatieList().iterator().next().getToegangLeveringsAutorisatie().setId(3);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setId(4);
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(null);

        selectieRunJobService.start();

        Mockito.verify(selectieService, times(0)).eindeSelectie(Mockito.any());
    }

    @Test(expected = RuntimeException.class)
    public void test_start_bepaalSelectieException() {
        Mockito.doThrow(RuntimeException.class).when(selectieService).bepaalSelectie();

        selectieRunJobService.start();

        Mockito.verify(selectieService, times(0)).eindeSelectie(Mockito.any());
    }

    @Test
    public void testHappyFlow_GeenSelectietaakInRun() {
        final Selectie selectie = TestSelectie.maakSelectie();
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(selectie);

        selectieRunJobService.start();

        Mockito.verify(selectieService, times(1)).eindeSelectie(Mockito.any());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getStartDatum());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getEindeDatum());
    }

    @Test
    public void testHappyFlow_RunIsUitvoerbaar() {
        final Timestamp tsNu = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now());
        final Selectie selectie = TestSelectie.maakSelectieMetSelectietaakInRun();
        selectie.getSelectierun().setId(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setHistorievormSelectie(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentFormeelResultaat(tsNu);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentMaterieelResultaat(20140101);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setId(2);
        selectie.getSelectietaakAutorisatieList().iterator().next().getToegangLeveringsAutorisatie().setId(3);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setId(4);
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(selectie);

        selectieRunJobService.start();

        Mockito.verify(selectieService, times(1)).eindeSelectie(Mockito.any());
        Mockito.verify(maakSelectieResultaatTaakPublicatieService, times(1))
                .publiceerMaakSelectieResultaatTaken(maakSelectietaakResultaatArgCaptor.capture());
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = Iterables.getOnlyElement(maakSelectietaakResultaatArgCaptor.getValue());
        Assert.assertEquals(tsNu, maakSelectieResultaatTaak.getPeilmomentFormeelResultaat());
        Assert.assertEquals(20140101, maakSelectieResultaatTaak.getPeilmomentMaterieelResultaat().intValue());
        Assert.assertEquals(1, maakSelectieResultaatTaak.getSelectieRunId().intValue());
        Assert.assertEquals(2, maakSelectieResultaatTaak.getSelectietaakId().intValue());
        Assert.assertEquals(3, maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId().intValue());
        Assert.assertEquals(4, maakSelectieResultaatTaak.getDienstId().intValue());
        assertFalse(maakSelectieResultaatTaak.isOngeldig());
        Assert.assertEquals(SoortSelectie.STANDAARD_SELECTIE, maakSelectieResultaatTaak.getSoortSelectie());
        Assert.assertNotNull(maakSelectieResultaatTaak.getDatumUitvoer());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getStartDatum());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getEindeDatum());
    }

    @Test
    public void testHappyFlow_RunIsUitvoerbaar_PlaatsingAfnIndicatie() {
        final Timestamp tsNu = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now());
        final int selectietaakid = 2;
        final Selectie selectie = TestSelectie.maakSelectieMetSelectietaakInRun();
        selectie.getSelectierun().setId(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setHistorievormSelectie(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentFormeelResultaat(tsNu);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentMaterieelResultaat(20140101);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setId(selectietaakid);
        selectie.getSelectietaakAutorisatieList().iterator().next().getToegangLeveringsAutorisatie().setId(3);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setId(4);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setSoortSelectie(
                SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId());
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(selectie);

        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();
        ReflectionTestUtils.invokeMethod(selectieJobRunStatus, "incrementAfnemerindicatieTaakVerwerkt", 2);
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(selectieJobRunStatus);

        selectieRunJobService.start();

        Mockito.verify(maakSelectieResultaatTaakPublicatieService, times(1))
                .publiceerMaakSelectieResultaatTaken(maakSelectietaakResultaatArgCaptor.capture());
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = Iterables.getOnlyElement(maakSelectietaakResultaatArgCaptor.getValue());
        Assert.assertEquals(1, maakSelectieResultaatTaak.getAantalPersonen().intValue());
    }

    @Test
    public void testHappyFlow_RunIsUitvoerbaar_VerwijderingAfnIndicatie() {
        final Timestamp tsNu = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now());
        final Selectie selectie = TestSelectie.maakSelectieMetSelectietaakInRun();
        selectie.getSelectierun().setId(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setHistorievormSelectie(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentFormeelResultaat(tsNu);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentMaterieelResultaat(20140101);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setId(2);
        selectie.getSelectietaakAutorisatieList().iterator().next().getToegangLeveringsAutorisatie().setId(3);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setId(4);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setSoortSelectie(
                SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE.getId());
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(selectie);


        final SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();
        ReflectionTestUtils.invokeMethod(selectieJobRunStatus, "incrementAfnemerindicatieTaakVerwerkt", 2);
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(selectieJobRunStatus);

        selectieRunJobService.start();

        Mockito.verify(maakSelectieResultaatTaakPublicatieService, times(1))
                .publiceerMaakSelectieResultaatTaken(maakSelectietaakResultaatArgCaptor.capture());
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = Iterables.getOnlyElement(maakSelectietaakResultaatArgCaptor.getValue());
        Assert.assertEquals(1, maakSelectieResultaatTaak.getAantalPersonen().intValue());
    }


    @Test
    public void testFlowOngeldigeTaak() {
        final SelectieJobRunStatus status = new SelectieJobRunStatus();
        Mockito.when(selectieJobRunStatusService.newStatus()).thenReturn(status);
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(status);

        final Timestamp tsNu = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now());
        final Selectie selectie = TestSelectie.maakSelectieMetSelectietaakInRun();
        selectie.getSelectierun().setId(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setHistorievormSelectie(1);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentFormeelResultaat(tsNu);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentMaterieelResultaat(20140101);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setId(2);
        selectie.getSelectietaakAutorisatieList().iterator().next().getToegangLeveringsAutorisatie().setId(3);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setId(4);
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(selectie);

        //zet de taak op ongeldig
        status.voegOngeldigeSelectietakenToe(Sets.newHashSet(selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getId()));

        selectieRunJobService.start();

        Mockito.verify(selectieService, times(1)).eindeSelectie(Mockito.any());
        Mockito.verify(maakSelectieResultaatTaakPublicatieService, times(1))
                .publiceerMaakSelectieResultaatTaken(maakSelectietaakResultaatArgCaptor.capture());
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = Iterables.getOnlyElement(maakSelectietaakResultaatArgCaptor.getValue());
        Assert.assertEquals(tsNu, maakSelectieResultaatTaak.getPeilmomentFormeelResultaat());
        Assert.assertEquals(20140101, maakSelectieResultaatTaak.getPeilmomentMaterieelResultaat().intValue());
        Assert.assertEquals(1, maakSelectieResultaatTaak.getSelectieRunId().intValue());
        Assert.assertEquals(2, maakSelectieResultaatTaak.getSelectietaakId().intValue());
        Assert.assertEquals(3, maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId().intValue());
        Assert.assertEquals(4, maakSelectieResultaatTaak.getDienstId().intValue());
        assertTrue(maakSelectieResultaatTaak.isOngeldig());
        Assert.assertEquals(SoortSelectie.STANDAARD_SELECTIE, maakSelectieResultaatTaak.getSoortSelectie());
        Assert.assertNotNull(maakSelectieResultaatTaak.getDatumUitvoer());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getStartDatum());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getEindeDatum());
    }

    @Test
    public void test_GeenHistorieVormSelectieInDienst_BeidePeilmomentenLeeg() {
        final Selectie selectie = TestSelectie.maakSelectieMetSelectietaakInRun();
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().getDienst().setHistorievormSelectie(null);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentFormeelResultaat(null);
        selectie.getSelectietaakAutorisatieList().iterator().next().getSelectietaak().setPeilmomentMaterieelResultaat(null);
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(selectie);

        selectieRunJobService.start();

        Mockito.verify(maakSelectieResultaatTaakPublicatieService, times(1))
                .publiceerMaakSelectieResultaatTaken(maakSelectietaakResultaatArgCaptor.capture());
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = Iterables.getOnlyElement(maakSelectietaakResultaatArgCaptor.getValue());
        Assert.assertNull(maakSelectieResultaatTaak.getPeilmomentFormeelResultaat());
        Assert.assertNull(maakSelectieResultaatTaak.getPeilmomentMaterieelResultaat());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getEindeDatum());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getStartDatum());
    }


    @Test
    public void testErrorFlow() throws SelectieException {
        final Selectie selectie = TestSelectie.maakSelectie();
        Mockito.when(selectieService.bepaalSelectie()).thenReturn(selectie);
        Mockito.doThrow(new SelectieException(new IllegalArgumentException())).when(selectieLezerService).startLezen(selectie);

        selectieRunJobService.start();

        Mockito.verify(selectieService, times(1)).eindeSelectie(Mockito.any());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getStartDatum());
        Assert.assertNotNull(selectieJobRunStatusService.getStatus().getEindeDatum());

    }

}
