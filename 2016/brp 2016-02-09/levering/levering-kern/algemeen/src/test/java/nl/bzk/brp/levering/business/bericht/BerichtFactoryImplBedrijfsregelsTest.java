/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.junit.TestAppender;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.bepalers.impl.SoortSynchronisatieBepalerImpl;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactory;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactoryImpl;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.support.AdministratieveHandelingTestBouwer;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BerichtFactoryImplBedrijfsregelsTest {

    private static final String SOORT_SYNCHRONISATIE_BEPALER = "soortSynchronisatieBepaler";

    @InjectMocks
    private final BerichtFactory berichtFactory = new BerichtFactoryImpl();

    @Mock
    private PartijService partijService;

    @Mock
    private Leveringinformatie leveringAutorisatie;

    @Spy
    private final PersoonViewFactory persoonViewFactory = new PersoonViewFactoryImpl();

    private final TestAppender appender = new TestAppender();

    @Before
    public final void init() {
        Mockito.when(partijService.vindPartijOpCode(Mockito.anyInt())).thenReturn(
                Mockito.mock(Partij.class));
        ReflectionTestUtils.setField(persoonViewFactory, SOORT_SYNCHRONISATIE_BEPALER,
                new SoortSynchronisatieBepalerImpl());
        ReflectionTestUtils
                .setField(berichtFactory, SOORT_SYNCHRONISATIE_BEPALER, new SoortSynchronisatieBepalerImpl());
    }

    @Test
    @Ignore
    public final void steltBerichtenSamenMaaktEenKennisgevingBuitenMetIndicatie() {
        final AdministratieveHandelingModel administratieveHandelingModel =
                AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        final PersoonHisVolledig persoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view1 =
                new PersoonHisVolledigView(persoon1, new AdministratieveHandelingDeltaPredikaat(1L));

        final PersoonHisVolledig persoon2 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view2 =
                new PersoonHisVolledigView(persoon2, new AdministratieveHandelingDeltaPredikaat(
                        administratieveHandelingModel.getID()));

        @SuppressWarnings("unchecked")
        final Map<Integer, Populatie> populatieMap = mock(Map.class);
        when(populatieMap.get(any(Integer.class))).thenReturn(Populatie.BINNEN).thenReturn(Populatie.BUITEN);

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);

        // act
        final List<SynchronisatieBericht> berichten =
                berichtFactory.maakBerichten(Arrays.asList(view1, view2), leveringAutorisatie, populatieMap,
                        administratieveHandelingModel);

        // assert
        assertThat(berichten.size(), is(1));
        assertThat(berichten.get(0), instanceOf(MutatieBericht.class));

        // assert: Test melding BRLV0027
        final MutatieBericht kennisgeving = (MutatieBericht) berichten.get(0);
        assertNotNull(kennisgeving.getMeldingen());
        assertNotNull(kennisgeving.getMeldingen().get(0));
        assertEquals(kennisgeving.getMeldingen().get(0).getRegel().getWaarde(), Regel.BRLV0027);

        // assert: Test VR00071
        assertEquals(1, appender.getEvents().size());
        assertEquals(Level.DEBUG, appender.getEvents().get(0).getLevel());
    }

    @Test
    @Ignore
    public final void steltBerichtenSamenMaaktEenKennisgevingVerlaatMetIndicatie() {
        final PersoonHisVolledig persoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledig persoon2 = TestPersoonJohnnyJordaan.maak();

        final PersoonHisVolledigView view1 =
                new PersoonHisVolledigView(persoon1, new AdministratieveHandelingDeltaPredikaat(1L));
        final PersoonHisVolledigView view2 =
                new PersoonHisVolledigView(persoon2, new AdministratieveHandelingDeltaPredikaat(1L));

        final AdministratieveHandelingModel administratieveHandelingModel =
                AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        @SuppressWarnings("unchecked")
        final Map<Integer, Populatie> populatieMap = mock(Map.class);
        when(populatieMap.get(any(Integer.class))).thenReturn(Populatie.BUITEN).thenReturn(Populatie.VERLAAT);

        when(leveringAutorisatie.getSoortDienst()).thenReturn(
                SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);

        // act
        final List<SynchronisatieBericht> berichten =
                berichtFactory.maakBerichten(Arrays.asList(view1, view2), leveringAutorisatie, populatieMap,
                        administratieveHandelingModel);

        // assert
        assertThat(berichten.size(), is(1));
        assertThat(berichten.get(0), instanceOf(MutatieBericht.class));

        // assert: Test melding BRLV0027
        final MutatieBericht kennisgeving = (MutatieBericht) berichten.get(0);
        assertNotNull(kennisgeving.getMeldingen());
        assertNotNull(kennisgeving.getMeldingen().get(0));
        assertEquals(kennisgeving.getMeldingen().get(0).getRegel().getWaarde(), Regel.BRLV0027);

        // assert: Test VR00071
        assertEquals(2, appender.getEvents().size());
        for (final LogEvent logEvent : appender.getEvents()) {
            assertEquals(Level.DEBUG, logEvent.getLevel());
        }
    }

    @Test
    @Ignore
    public final void steltBerichtenSamenMaaktEenMutatieBerichtVerlaatPopulatie() {
        final AdministratieveHandelingModel administratieveHandelingModel =
                AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        final PersoonHisVolledig persoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view1 =
                new PersoonHisVolledigView(persoon1, new AdministratieveHandelingDeltaPredikaat(1L));

        final PersoonHisVolledig persoon2 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView view2 =
                new PersoonHisVolledigView(persoon2, new AdministratieveHandelingDeltaPredikaat(
                        administratieveHandelingModel.getID()));

        @SuppressWarnings("unchecked")
        final Map<Integer, Populatie> populatieMap = mock(Map.class);
        when(populatieMap.get(any(Integer.class))).thenReturn(Populatie.BINNEN).thenReturn(Populatie.VERLAAT);

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        // act
        final List<SynchronisatieBericht> berichten =
                berichtFactory.maakBerichten(Arrays.asList(view1, view2), leveringAutorisatie, populatieMap,
                        administratieveHandelingModel);

        // assert
        assertThat(berichten.size(), is(1));
        assertThat(berichten.get(0), instanceOf(MutatieBericht.class));

        // assert: Test melding BRLV0028
        final MutatieBericht kennisgeving = (MutatieBericht) berichten.get(0);
        assertNotNull(kennisgeving.getMeldingen());
        assertNotNull(kennisgeving.getMeldingen().get(0));
        assertEquals(kennisgeving.getMeldingen().get(0).getRegel().getWaarde(), Regel.BRLV0028);

        // assert: Test VR00071
        assertEquals(1, appender.getEvents().size());
        assertEquals(Level.DEBUG, appender.getEvents().get(0).getLevel());
    }

    @Test
    public final void maakBerichtMetVerstrekkingsbeperkingMelding() {
        final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        final PersoonHisVolledig persoon =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .voegPersoonIndicatieVolledigeVerstrekkingsbeperkingToe(
                                new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder()
                                        .nieuwStandaardRecord(20120101).waarde(Ja.J).eindeRecord().build()).build();

        final PersoonHisVolledig persoon2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();


        @SuppressWarnings("unchecked")
        final Map<Integer, Populatie> populatieMap = mock(Map.class);
        when(populatieMap.get(any(Integer.class))).thenReturn(Populatie.BINNEN);

        final Leveringsautorisatie leveringsautorisatie =
                TestLeveringsautorisatieBuilder.maker().metNaam("Abo").metPopulatiebeperking("WAAR")
                        .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
                        .metDatumIngang(DatumAttribuut.gisteren()).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(leveringsautorisatie).maak();

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        when(leveringAutorisatie.getToegangLeveringsautorisatie()).thenReturn(toegangLeveringsautorisatie);

        final PersoonHisVolledigView persoonHisVolledigView1 = new PersoonHisVolledigView(persoon, null);
        final PersoonHisVolledigView persoonHisVolledigView2 = new PersoonHisVolledigView(persoon2, null);

        // act
        final List<SynchronisatieBericht> berichten =
                berichtFactory.maakBerichten(Arrays.asList(persoonHisVolledigView1, persoonHisVolledigView2), leveringAutorisatie, populatieMap,
                        administratieveHandelingModel);

        // assert
        assertThat(berichten.size(), is(1));
        assertThat(berichten.get(0), instanceOf(MutatieBericht.class));

        // Test melding BRLV0032
        final MutatieBericht kennisgeving = (MutatieBericht) berichten.get(0);
        assertNotNull(kennisgeving.getMeldingen());
        assertNotNull(kennisgeving.getMeldingen().get(0));
        assertEquals(kennisgeving.getMeldingen().get(0).getRegel().getWaarde(), Regel.BRLV0032);
    }
}
