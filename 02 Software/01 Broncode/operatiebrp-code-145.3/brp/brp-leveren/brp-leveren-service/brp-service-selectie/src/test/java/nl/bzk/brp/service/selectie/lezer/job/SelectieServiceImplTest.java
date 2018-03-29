/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakStatusHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.dalapi.SelectieRepository;
import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectietaakAutorisatie;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
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

/**
 * SelectieServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieServiceImplTest {

    static {
        BrpNu.set();
    }

    private SelectieJobRunStatus status;

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;
    @Mock
    private SelectieRepository selectieRepository;
    @Mock
    private SelectieAutorisatieService selectieAutorisatieService;
    @Mock
    private SelectieJobRunStatusService selectieJobRunStatusService;
    @InjectMocks
    private SelectieServiceImpl selectieService;

    @Captor
    private ArgumentCaptor<Selectierun> selectierunArgumentCaptor;

    @Before
    public void voorTest() {
        status = new SelectieJobRunStatus();
        status.setError(false);
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(status);
    }

    @Test
    public void testHappyFlow() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));
        final Selectie selectie = selectieService.bepaalSelectie();

        final List<SelectietaakAutorisatie> selectietaakAutorisatieList = selectie.getSelectietaakAutorisatieList();
        assertEquals(1, selectietaakAutorisatieList.size());
        final SelectietaakAutorisatie selectietaakAutorisatie = selectietaakAutorisatieList.stream().findAny().get();
        assertEquals(2, selectietaak.getSelectietaakHistorieSet().size());
        assertEquals(2, selectietaak.getSelectietaakStatusHistorieSet().size());
        assertEquals(SelectietaakStatus.IN_UITVOERING.getId(), (int) selectietaak.getStatus());

        //einde
        selectieService.eindeSelectie(selectie);
        final Selectietaak selectietaakNaRun = selectie.getSelectierun().getSelectieTaken().stream().findAny().get();
        assertEquals(3, selectietaakNaRun.getSelectietaakHistorieSet().size());
        assertEquals(SelectietaakStatus.TE_LEVEREN.getId(), (int) selectietaakNaRun.getStatus());
    }


    @Test
    public void testBepaalSelectie_peilmomentFormeelResultaatGevuld() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        final Timestamp ts = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.now());
        selectietaak.setPeilmomentFormeelResultaat(ts);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        selectieService.bepaalSelectie();

        Mockito.verify(selectieRepository).slaSelectieOp(selectierunArgumentCaptor.capture());
        final Selectierun selectierun = selectierunArgumentCaptor.getValue();
        final Selectietaak selTaak = Iterables.getOnlyElement(selectierun.getSelectieTaken());
        assertEquals(ts, selTaak.getPeilmomentFormeelResultaat());
    }

    @Test
    public void testBepaalSelectie_peilmomentMaterieelResultaatGevuld() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        selectietaak.setPeilmomentMaterieelResultaat(20101111);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        selectieService.bepaalSelectie();

        Mockito.verify(selectieRepository).slaSelectieOp(selectierunArgumentCaptor.capture());
        final Selectierun selectierun = selectierunArgumentCaptor.getValue();
        final Selectietaak selTaak = Iterables.getOnlyElement(selectierun.getSelectieTaken());
        assertEquals(20101111, selTaak.getPeilmomentMaterieelResultaat().intValue());
    }

    @Test
    public void testBepaalSelectie_HistorievormselectieGevuld() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        final int historievormSelectieId = 1;
        selectietaak.getDienst().setHistorievormSelectie(historievormSelectieId);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        selectieService.bepaalSelectie();

        Mockito.verify(selectieRepository).slaSelectieOp(selectierunArgumentCaptor.capture());
        final Selectierun selectierun = selectierunArgumentCaptor.getValue();
        final Selectietaak selTaak = Iterables.getOnlyElement(selectierun.getSelectieTaken());
        assertEquals(historievormSelectieId, selTaak.getDienst().getHistorievormSelectie().intValue());
    }

    @Test
    public void testEindeSelectie() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        selectieService.eindeSelectie(selectie);

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        Assert.assertNotNull(selectie.getSelectierun().getTijdstipGereed());
    }

    @Test
    public void testEindeSelectieStandaardMetControle() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        selectietaak.getDienst().setIndicatieSelectieresultaatControleren(true);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        selectieService.eindeSelectie(selectie);

        selectie.getSelectierun().getSelectieTaken();

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        Assert.assertNotNull(selectie.getSelectierun().getTijdstipGereed());

        controleerEindStatusTaak(selectie, SelectietaakStatus.CONTROLEREN);
    }

    @Test
    public void testEindeSelectieStandaardMetLeverwijzeBericht() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        selectietaak.getDienst().setLeverwijzeSelectie(LeverwijzeSelectie.BERICHT.getId());
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        selectieService.eindeSelectie(selectie);

        selectie.getSelectierun().getSelectieTaken();

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        Assert.assertNotNull(selectie.getSelectierun().getTijdstipGereed());

        controleerEindStatusTaak(selectie, SelectietaakStatus.SELECTIE_UITGEVOERD);
    }

    @Test
    public void testEindeSelectieStandaardMetLeverwijzeStandaard() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        selectietaak.getDienst().setIndicatieSelectieresultaatControleren(false);
        selectietaak.getDienst().setLeverwijzeSelectie(LeverwijzeSelectie.STANDAARD.getId());
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        selectieService.eindeSelectie(selectie);

        selectie.getSelectierun().getSelectieTaken();

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        Assert.assertNotNull(selectie.getSelectierun().getTijdstipGereed());

        controleerEindStatusTaak(selectie, SelectietaakStatus.TE_LEVEREN);
    }

    @Test
    public void testEindeSelectiePlaatsAfnemerindicatieMetControle() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE);
        //controle doet niks hier
        selectietaak.getDienst().setIndicatieSelectieresultaatControleren(true);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        selectieService.eindeSelectie(selectie);

        selectie.getSelectierun().getSelectieTaken();

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        Assert.assertNotNull(selectie.getSelectierun().getTijdstipGereed());

        controleerEindStatusTaak(selectie, SelectietaakStatus.SELECTIE_UITGEVOERD);
    }

    @Test
    public void testEindeSelectieVerwijderenAfnemerindicatieMetControle() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE);
        //controle doet niks hier
        selectietaak.getDienst().setIndicatieSelectieresultaatControleren(true);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        selectieService.eindeSelectie(selectie);

        selectie.getSelectierun().getSelectieTaken();

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        Assert.assertNotNull(selectie.getSelectierun().getTijdstipGereed());

        controleerEindStatusTaak(selectie, SelectietaakStatus.SELECTIE_UITGEVOERD);
    }

    @Test
    public void testEindeSelectiePlaatsAfnemerindicatieMetFout() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE);
        //controle doet niks hier
        selectietaak.getDienst().setIndicatieSelectieresultaatControleren(true);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        status.setError(true);
        selectieService.eindeSelectie(selectie);

        selectie.getSelectierun().getSelectieTaken();

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        Assert.assertNotNull(selectie.getSelectierun().getTijdstipGereed());

        controleerEindStatusTaak(selectie, SelectietaakStatus.UITVOERING_MISLUKT);
    }

    @Test
    public void testEindeSelectieMetFout() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        status.setError(true);
        selectieService.eindeSelectie(selectie);

        selectie.getSelectierun().getSelectieTaken();

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        Assert.assertNotNull(selectie.getSelectierun().getTijdstipGereed());

        controleerEindStatusTaak(selectie, SelectietaakStatus.UITVOERING_MISLUKT);
    }

    @Test(expected = NullPointerException.class)
    public void testVervalSelectietaak_GeenHisRecords() {
        final Selectietaak selectietaak = maakSelectieTaakZonderHistorie(SoortSelectie.STANDAARD_SELECTIE);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));
        final Selectie selectie = selectieService.bepaalSelectie();

        selectieService.eindeSelectie(selectie);
    }

    @Test
    public void testOngeldigetaak_uitvoeringAfgebroken() {
        final Selectietaak selectietaak = maakSelectieTaak(SoortSelectie.STANDAARD_SELECTIE);
        final SelectieJobRunStatus status = new SelectieJobRunStatus();
        status.voegOngeldigeSelectietakenToe(Sets.newHashSet(selectietaak.getId()));
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(status);
        Mockito.when(selectieRepository.getTakenGeplandVoorVandaag()).thenReturn(Lists.newArrayList(selectietaak));

        final Selectie selectie = selectieService.bepaalSelectie();
        selectieService.eindeSelectie(selectie);

        Mockito.verify(selectieRepository).werkSelectieBij(selectie.getSelectierun());
        controleerEindStatusTaak(selectie, SelectietaakStatus.UITVOERING_AFGEBROKEN);
    }

    private void controleerEindStatusTaak(Selectie selectie, SelectietaakStatus status) {
        final Selectietaak selectietaakNaRun = selectie.getSelectierun().getSelectieTaken().stream().findAny().get();
        assertEquals(status.getId(), (int) selectietaakNaRun.getStatus());
    }

    private Selectietaak maakSelectieTaak(final SoortSelectie soortSelectie) {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(soortSelectie.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        Mockito.doReturn(true).when(selectieAutorisatieService).isAutorisatieGeldig(Mockito.any(), Mockito.eq(dienst));
        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisaties())
                .thenReturn(Lists.newArrayList(autorisatiebundel.getToegangLeveringsautorisatie()));

        final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
        final Selectietaak selectietaak = new Selectietaak(dienst, autorisatiebundel.getToegangLeveringsautorisatie(), 1);
        selectietaak.setStatus((short) SelectietaakStatus.UITVOERBAAR.getId());
        selectietaak.setDatumPlanning(DatumUtil.vandaag());
        selectietaak.setDienst(autorisatiebundel.getDienst());

        final SelectietaakHistorie selectietaakHistorie = new SelectietaakHistorie(selectietaak);
        selectietaakHistorie.setDatumTijdRegistratie(nuTijd);
        selectietaak.addSelectietaakHistorieSet(selectietaakHistorie);

        final SelectietaakStatusHistorie selectietaakStatusHistorie = new SelectietaakStatusHistorie(selectietaak);
        selectietaakStatusHistorie.setDatumTijdRegistratie(nuTijd);
        selectietaakStatusHistorie.setStatus(selectietaak.getStatus());
        selectietaak.addSelectietaakStatusHistorieSet(selectietaakStatusHistorie);
        return selectietaak;
    }

    private Selectietaak maakSelectieTaakZonderHistorie(final SoortSelectie soortSelectie) {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(soortSelectie.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        Mockito.doReturn(true).when(selectieAutorisatieService).isAutorisatieGeldig(Mockito.any(), Mockito.eq(dienst));
        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisaties())
                .thenReturn(Lists.newArrayList(autorisatiebundel.getToegangLeveringsautorisatie()));

        final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
        final Selectietaak selectietaak = new Selectietaak(dienst, autorisatiebundel.getToegangLeveringsautorisatie(), 1);
        selectietaak.setStatus((short) SelectietaakStatus.UITVOERBAAR.getId());
        selectietaak.setDatumPlanning(DatumUtil.vandaag());
        selectietaak.setDienst(autorisatiebundel.getDienst());

        return selectietaak;
    }
}
