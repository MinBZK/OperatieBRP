/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.bepalers.impl.SoortSynchronisatieBepalerImpl;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactory;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactoryImpl;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.support.AdministratieveHandelingTestBouwer;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BerichtFactoryImplTest {

    private static final String BRP                          = "BRP";
    private static final String SOORT_SYNCHRONISATIE_BEPALER = "soortSynchronisatieBepaler";
    private static final String LEVERINGSYSTEEM              = "Leveringsysteem";

    @InjectMocks
    private final BerichtFactory berichtFactory = new BerichtFactoryImpl();

    @Mock
    private PartijService partijService;

    @Spy
    private PersoonViewFactory persoonViewFactory = new PersoonViewFactoryImpl();

    @Before
    public final void init() {

        ReflectionTestUtils.setField(persoonViewFactory, SOORT_SYNCHRONISATIE_BEPALER, new SoortSynchronisatieBepalerImpl());
        ReflectionTestUtils.setField(berichtFactory, SOORT_SYNCHRONISATIE_BEPALER, new SoortSynchronisatieBepalerImpl());
    }

    @Test
    public final void testStelBerichtenSamenTijdstipRegistratieDeltaPredikaat() {
        final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        @SuppressWarnings("unchecked")
        final Map<Integer, Populatie> populatieMap = mock(Map.class);
        when(populatieMap.get(any(Integer.class))).thenReturn(Populatie.BINNEN);

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(null,
            TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak());

        final PersoonHisVolledig persoon1 = mock(PersoonHisVolledig.class);
        final PersoonHisVolledig persoon2 = mock(PersoonHisVolledig.class);
        final PersoonHisVolledigView persoonHisVolledigView1 = new PersoonHisVolledigView(persoon1, mock(AdministratieveHandelingDeltaPredikaat.class));
        final PersoonHisVolledigView persoonHisVolledigView2 = new PersoonHisVolledigView(persoon2, mock(AdministratieveHandelingDeltaPredikaat.class));

        when(persoon1.getPersoonAfgeleidAdministratiefHistorie())
            .thenReturn(new FormeleHistorieSetImpl<>(Collections.<HisPersoonAfgeleidAdministratiefModel>emptySet()));
        when(persoon2.getPersoonAfgeleidAdministratiefHistorie())
            .thenReturn(new FormeleHistorieSetImpl<>(Collections.<HisPersoonAfgeleidAdministratiefModel>emptySet()));

        // act
        final List<SynchronisatieBericht> resultaat =
            berichtFactory.maakBerichten(Arrays.asList(persoonHisVolledigView1, persoonHisVolledigView2), leveringAutorisatie, populatieMap,
                administratieveHandelingModel);

        // assert
        assertNotNull(resultaat);
        assertEquals(1, resultaat.size());
    }

    @Test
    public final void testStelBerichtenSamenMaterieleHistorieVanafPredikaat() {
        final AdministratieveHandelingModel administratieveHandelingModel =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        @SuppressWarnings("unchecked") final Map<Integer, Populatie> populatieMap = mock(Map.class);
        when(populatieMap.get(any(Integer.class))).thenReturn(Populatie.BETREEDT);

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(null,
            TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak());
        final PersoonHisVolledig persoon3 = mock(PersoonHisVolledig.class);
        final PersoonHisVolledig persoon4 = mock(PersoonHisVolledig.class);
        final PersoonHisVolledigView persoonHisVolledigView3 = new PersoonHisVolledigView(persoon3, mock(AdministratieveHandelingDeltaPredikaat.class));
        final PersoonHisVolledigView persoonHisVolledigView4 = new PersoonHisVolledigView(persoon4, mock(AdministratieveHandelingDeltaPredikaat.class));

        when(persoon3.getPersoonAfgeleidAdministratiefHistorie()).thenReturn(
            new FormeleHistorieSetImpl<>(
                Collections.<HisPersoonAfgeleidAdministratiefModel>emptySet()));
        when(persoon4.getPersoonAfgeleidAdministratiefHistorie()).thenReturn(
            new FormeleHistorieSetImpl<>(
                Collections.<HisPersoonAfgeleidAdministratiefModel>emptySet()));
        // act
        final List<SynchronisatieBericht> resultaat =
            berichtFactory.maakBerichten(Arrays.asList(persoonHisVolledigView3, persoonHisVolledigView4), leveringAutorisatie, populatieMap,
                administratieveHandelingModel);

        // assert
        assertNotNull(resultaat);
        assertEquals(1, resultaat.size());
    }

    @Test
    public final void testStelBerichtenSamenMeerderePredikaten() {
        final AdministratieveHandelingModel administratieveHandelingModel =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        @SuppressWarnings("unchecked") final Map<Integer, Populatie> populatieMap = mock(Map.class);


        final PersoonHisVolledig persoon1 = mock(PersoonHisVolledig.class);
        final PersoonHisVolledig persoon2 = mock(PersoonHisVolledig.class);
        final PersoonHisVolledig persoon3 = mock(PersoonHisVolledig.class);
        final PersoonHisVolledig persoon4 = mock(PersoonHisVolledig.class);

        Mockito.when(persoon1.getID()).thenReturn(1);
        Mockito.when(persoon2.getID()).thenReturn(2);
        Mockito.when(persoon3.getID()).thenReturn(3);
        Mockito.when(persoon4.getID()).thenReturn(4);

        Mockito.when(populatieMap.get(1)).thenReturn(Populatie.BINNEN);
        Mockito.when(populatieMap.get(2)).thenReturn(Populatie.BINNEN);
        Mockito.when(populatieMap.get(3)).thenReturn(Populatie.BETREEDT);
        Mockito.when(populatieMap.get(4)).thenReturn(Populatie.BETREEDT);

        final PersoonHisVolledigView persoonHisVolledigView1 = new PersoonHisVolledigView(persoon1, mock(AdministratieveHandelingDeltaPredikaat.class));
        final PersoonHisVolledigView persoonHisVolledigView2 = new PersoonHisVolledigView(persoon2, mock(AdministratieveHandelingDeltaPredikaat.class));
        final PersoonHisVolledigView persoonHisVolledigView3 = new PersoonHisVolledigView(persoon3, mock(AdministratieveHandelingDeltaPredikaat.class));
        final PersoonHisVolledigView persoonHisVolledigView4 = new PersoonHisVolledigView(persoon4, mock(AdministratieveHandelingDeltaPredikaat.class));

        Mockito.when(persoon1.getPersoonAfgeleidAdministratiefHistorie()).thenReturn(
            new FormeleHistorieSetImpl<>(
                Collections.<HisPersoonAfgeleidAdministratiefModel>emptySet()));
        Mockito.when(persoon2.getPersoonAfgeleidAdministratiefHistorie()).thenReturn(
            new FormeleHistorieSetImpl<>(
                Collections.<HisPersoonAfgeleidAdministratiefModel>emptySet()));
        Mockito.when(persoon3.getPersoonAfgeleidAdministratiefHistorie()).thenReturn(
            new FormeleHistorieSetImpl<>(
                Collections.<HisPersoonAfgeleidAdministratiefModel>emptySet()));
        Mockito.when(persoon4.getPersoonAfgeleidAdministratiefHistorie()).thenReturn(
            new FormeleHistorieSetImpl<>(
                Collections.<HisPersoonAfgeleidAdministratiefModel>emptySet()));

        // act
        final Leveringinformatie leveringAutorisatie2 = new Leveringinformatie(null, TestDienstBuilder.dummy());
        final List<SynchronisatieBericht> resultaat =
            berichtFactory.maakBerichten(Arrays.asList(persoonHisVolledigView1, persoonHisVolledigView2, persoonHisVolledigView3, persoonHisVolledigView4),
                                         leveringAutorisatie2, populatieMap,
                administratieveHandelingModel);

        // assert
        assertNotNull(resultaat);
        assertEquals(2, resultaat.size());
    }

    @Test
    public final void testMaakStuurgegevens() {
        final Partij ontvangendePartij = TestPartijBuilder.maker().metCode(new PartijCodeAttribuut(123)).maak();
        final Partij verzendendePartij = TestPartijBuilder.maker().maak();
        when(partijService.vindPartijOpCode(anyInt())).thenReturn(verzendendePartij);

        final BerichtStuurgegevensGroepBericht stuurgegevens = berichtFactory.maakStuurgegevens(ontvangendePartij);

        assertNotNull(stuurgegevens);
        assertEquals(verzendendePartij, stuurgegevens.getZendendePartij().getWaarde());
        assertEquals(BRP, stuurgegevens.getZendendeSysteem().getWaarde());
        assertEquals(Integer.valueOf(123),
            stuurgegevens.getOntvangendePartij().getWaarde().getCode().getWaarde());
        assertEquals(LEVERINGSYSTEEM, stuurgegevens.getOntvangendeSysteem().getWaarde());
        assertNotNull(stuurgegevens.getReferentienummer().getWaarde());
        assertNotNull(stuurgegevens.getDatumTijdVerzending());
        assertNull(stuurgegevens.getCrossReferentienummer());
        assertNull(stuurgegevens.getDatumTijdOntvangst());
    }

    @Test
    public final void testMaakStuurgegevensMetCrossReferentie() {
        final Partij ontvangendePartij = TestPartijBuilder.maker().metCode(new PartijCodeAttribuut(123)).maak();
        final Partij verzendendePartij = TestPartijBuilder.maker().maak();
        when(partijService.vindPartijOpCode(anyInt())).thenReturn(verzendendePartij);
        final String referentie = "0000ABCD-0000-0000-0000-000000000000";

        final BerichtStuurgegevensGroepBericht stuurgegevens = berichtFactory.maakStuurgegevens(ontvangendePartij,
            referentie);

        assertNotNull(stuurgegevens);
        assertEquals(verzendendePartij, stuurgegevens.getZendendePartij().getWaarde());
        assertEquals(BRP, stuurgegevens.getZendendeSysteem().getWaarde());
        assertEquals(Integer.valueOf(123), stuurgegevens.getOntvangendePartij().getWaarde().getCode().getWaarde());
        assertEquals(LEVERINGSYSTEEM, stuurgegevens.getOntvangendeSysteem().getWaarde());
        assertNotNull(stuurgegevens.getReferentienummer().getWaarde());
        assertNotNull(stuurgegevens.getDatumTijdVerzending());
        assertEquals(referentie, stuurgegevens.getCrossReferentienummer().getWaarde());
        assertNull(stuurgegevens.getDatumTijdOntvangst());
    }

    @Test
    public final void testMaakBerichtParameters() throws Exception {
        final SoortSynchronisatie soortSynchronisatie = SoortSynchronisatie.VOLLEDIGBERICHT;
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(
            TestLeveringsautorisatieBuilder.maker().maak()).maak();
        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(tla,
            TestDienstBuilder.maker().metSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON).maak());

        // act
        final BerichtParametersGroepBericht parameters = berichtFactory.maakParameters(leveringAutorisatie, soortSynchronisatie);

        // assert
        assertThat(parameters, org.hamcrest.Matchers.notNullValue());
        assertThat(parameters.getSoortSynchronisatie().getWaarde(), is(soortSynchronisatie));
    }

    @Test
    public final void testMaakBerichtParametersMetEffectAfnemerIndicatie() throws Exception {
        final SoortSynchronisatie soortSynchronisatie = SoortSynchronisatie.VOLLEDIGBERICHT;
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(
            TestLeveringsautorisatieBuilder.maker().maak()).maak();
        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(tla,
            TestDienstBuilder.maker().metSoortDienst(SoortDienst.ATTENDERING).metEffectAfnemerindicaties(EffectAfnemerindicaties.PLAATSING).maak());

        // act
        final BerichtParametersGroepBericht parameters = berichtFactory.maakParameters(leveringAutorisatie, soortSynchronisatie);

        // assert
        assertThat(parameters.getEffectAfnemerindicatie().getWaarde(), is(EffectAfnemerindicaties.PLAATSING));
    }

    @Test
    public final void steltBerichtSamen() {
        final PersoonHisVolledig persoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view1 = new PersoonHisVolledigView(persoon1, HistorieVanafPredikaat.geldigOpEnNa(DatumAttribuut.vandaag()));

        final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(null,
            TestDienstBuilder.maker().metSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON).maak());

        doReturn(view1).when(persoonViewFactory).maakView(eq(persoon1), eq(leveringAutorisatie), any(Populatie.class), eq(administratieveHandelingModel));

        // act
        final SynchronisatieBericht bericht = berichtFactory.maakVolledigBericht(persoon1, leveringAutorisatie, administratieveHandelingModel, null);

        final VolledigBericht volledigBericht = (VolledigBericht) bericht;
        assertThat(volledigBericht.getAdministratieveHandeling().getPartij().getCode().getWaarde(),
            equalTo(34));
    }

    // Onderstaande testen komen uit oude testklasse
    @Test
    public final void steltBerichtenSamenMaaktKennisgevingEnVolledigBericht() {
        final PersoonHisVolledig persoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view1 = new PersoonHisVolledigView(persoon1, HistorieVanafPredikaat.geldigOpEnNa(DatumAttribuut.vandaag()));

        final PersoonHisVolledig persoon2 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view2 = new PersoonHisVolledigView(persoon2, new AdministratieveHandelingDeltaPredikaat(1L));

        final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        @SuppressWarnings("unchecked") final Map<Integer, Populatie> populatieMap = mock(Map.class);
        when(populatieMap.get(any(Integer.class))).thenReturn(Populatie.BETREEDT).thenReturn(Populatie.VERLAAT);

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(null,
            TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak());

        // act
        final List<SynchronisatieBericht> berichten = berichtFactory.maakBerichten(Arrays.asList(view1, view2), leveringAutorisatie, populatieMap,
            administratieveHandelingModel);

        // assert
        assertThat(berichten.size(), is(2));

        // assert: Test melding BRLV0028
        final MutatieBericht kennisgeving = (MutatieBericht) berichten.get(1);
        assertNotNull(kennisgeving.getMeldingen());
        assertNotNull(kennisgeving.getMeldingen().get(0));
        assertEquals(kennisgeving.getMeldingen().get(0).getRegel().getWaarde(), Regel.BRLV0028);
    }

    @Test
    public final void steltBerichtenSamenMaaktEenVolledigBericht() {
        final PersoonHisVolledig persoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view1 = new PersoonHisVolledigView(persoon1,
            HistorieVanafPredikaat.geldigOpEnNa(
                DatumAttribuut.vandaag())
        );

        final PersoonHisVolledig persoon2 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view2 = new PersoonHisVolledigView(persoon2, HistorieVanafPredikaat.geldigOpEnNa(DatumAttribuut.gisteren()));

        final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        @SuppressWarnings("unchecked") final Map<Integer, Populatie> populatieMap = mock(Map.class);
        when(populatieMap.get(any(Integer.class))).thenReturn(Populatie.BETREEDT);

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(null,
            TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak());

        // act
        final List<SynchronisatieBericht> berichten = berichtFactory.maakBerichten(Arrays.asList(view1, view2), leveringAutorisatie, populatieMap,
            administratieveHandelingModel);

        // assert
        assertThat(berichten.size(), is(1));
        assertThat(berichten.get(0), instanceOf(VolledigBericht.class));
    }
}
