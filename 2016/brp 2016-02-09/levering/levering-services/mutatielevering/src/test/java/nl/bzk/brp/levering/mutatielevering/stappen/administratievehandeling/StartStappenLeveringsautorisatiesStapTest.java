/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.service.bericht.AfleverService;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StartStappenLeveringsautorisatiesStapTest {

    @InjectMocks
    private final StartStappenLeveringsautorisatiesStap startStappenLeveringsautorisatiesStap = new StartStappenLeveringsautorisatiesStap();

    @Mock
    private AfleverService brpAfleverService;

    @Mock
    private AdministratieveHandelingMutatie onderwerp;

    @Mock
    private AdministratieveHandelingVerwerkingContext context;

    private final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();

    private final Map<Leveringinformatie, Map<Integer, Populatie>> leveringsautorisatiePopulatieMap = new ConcurrentHashMap<>();

    @Before
    public final void setup() {


        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, null);

        final Map<Integer, Populatie> persoonPopulatieMap = new HashMap<>();
        persoonPopulatieMap.put(1, Populatie.BINNEN);
        leveringsautorisatiePopulatieMap.put(leveringAutorisatie, persoonPopulatieMap);

        when(context.getLeveringPopulatieMap()).thenReturn(leveringsautorisatiePopulatieMap);
        when(context.getHuidigeAdministratieveHandeling()).thenReturn(new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND),
            null, null, null));
        when(context.getBijgehoudenPersonenVolledig()).thenReturn(Collections.singletonList(mock(PersoonHisVolledig.class)));
        when(context.getBijgehoudenPersoonIds()).thenReturn(Collections.singletonList(1));
    }

    @Test
    public final void testVoerStapUitEnControleerSuccesvol() {
        final LeveringautorisatieVerwerkingResultaat afnemerResultaatMock = mock(LeveringautorisatieVerwerkingResultaat.class);
        when(afnemerResultaatMock.isSuccesvol()).thenReturn(true);
        when(brpAfleverService.leverBerichten(any(AdministratieveHandelingModel.class), anyListOf(PersoonHisVolledig.class), anyMap(), anyMap(), anyMap()))
            .thenReturn(afnemerResultaatMock);

        final boolean resultaatStap = startStappenLeveringsautorisatiesStap.voerStapUit(onderwerp, context, resultaat);

        assertEquals(AbstractStap.DOORGAAN, resultaatStap);
    }

    @Test
    public final void testVoerStapUitVoorPlaatsingAfnemerindicatie() {
        when(context.getHuidigeAdministratieveHandeling()).thenReturn(new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE),
            null, null, null));

        final LeveringautorisatieVerwerkingResultaat afnemerResultaatMock = mock(LeveringautorisatieVerwerkingResultaat.class);
        when(afnemerResultaatMock.isSuccesvol()).thenReturn(true);
        when(brpAfleverService
            .leverBerichten(any(AdministratieveHandelingModel.class), anyListOf(PersoonHisVolledig.class),
                anyMap(), anyMap(), anyMap()))
            .thenReturn(afnemerResultaatMock);

        final boolean resultaatStap = startStappenLeveringsautorisatiesStap.voerStapUit(onderwerp, context, resultaat);

        assertEquals(AbstractStap.DOORGAAN, resultaatStap);
    }

    @Test
    public final void testVoerStapUitEnControleerStoppen() {
        final Melding testMelding = new Melding(SoortMelding.FOUT, Regel.ALG0001, "Test melding.");
        final LeveringautorisatieVerwerkingResultaat afnemerResultaatMock = mock(LeveringautorisatieVerwerkingResultaat.class);
        when(afnemerResultaatMock.isSuccesvol()).thenReturn(false);
        when(afnemerResultaatMock.getMeldingen()).thenReturn(Collections.singletonList(testMelding));
        when(brpAfleverService.leverBerichten(any(AdministratieveHandelingModel.class), anyListOf(PersoonHisVolledig.class), anyMap(), anyMap(), anyMap()))
            .thenReturn(afnemerResultaatMock);

        final boolean resultaatStap = startStappenLeveringsautorisatiesStap.voerStapUit(onderwerp, context, resultaat);

        assertEquals(Stap.STOPPEN, resultaatStap);
    }

    @Test(expected = NullPointerException.class)
    public final void testVoerStapUitEnExceptieTreedtOp() {
        startStappenLeveringsautorisatiesStap.voerStapUit(onderwerp, context, resultaat);
    }

    @Test
    public final void testVoerNabewerkingStapUit() {
        startStappenLeveringsautorisatiesStap.voerNabewerkingStapUit(onderwerp, context, resultaat);
    }

    @Test
    public final void testMeldingenWordenGekopieerdEnProcesStopt() {
        final Melding testMelding = new Melding(SoortMelding.FOUT, Regel.ALG0001, "Test melding2.");
        final LeveringautorisatieVerwerkingResultaat resultaatMetMeldingen = new LeveringautorisatieVerwerkingResultaat();
        resultaatMetMeldingen.voegMeldingToe(testMelding);

        when(brpAfleverService.leverBerichten(any(AdministratieveHandelingModel.class), anyListOf(PersoonHisVolledig.class), anyMap(), anyMap(), anyMap()))
            .thenReturn(resultaatMetMeldingen);

        final boolean resultaatStap = startStappenLeveringsautorisatiesStap.voerStapUit(onderwerp, context, resultaat);

        assertEquals(testMelding, resultaat.getMeldingen().get(0));
        assertFalse(resultaatStap);
    }
}
