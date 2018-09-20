/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.business.toegang.populatie.FilterNietTeLeverenPersonenService;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonPopulatieBepalingService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor BepaalLeveringsautorisatiePopulatieStap.
 */
@RunWith(MockitoJUnitRunner.class)
public class BepaalLeveringsautorisatiePopulatieStapTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @InjectMocks
    private final BepaalLeveringsautorisatiePopulatieStap bepaalLeveringsautorisatiePopulatieStap = new BepaalLeveringsautorisatiePopulatieStap();

    @Mock
    private PersoonPopulatieBepalingService persoonPopulatieBepalingService;

    @Mock
    private LeveringinformatieService leveringinformatieService;

    @Mock
    private FilterNietTeLeverenPersonenService filterNietTeLeverenPersonenService;

    @Mock
    private AdministratieveHandelingMutatie onderwerp;

    private final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();

    private final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();

    private final List<Leveringinformatie> autorisaties = new ArrayList<>();

    private final Map<Leveringinformatie, Map<Integer, Populatie>> verwachtResultaat = new HashMap<>();

    private final List<PersoonHisVolledig> personenMocks = Collections.singletonList(mock(PersoonHisVolledig.class));

    private final AdministratieveHandelingModel administratieveHandelingMock = mock(AdministratieveHandelingModel.class);

    @Before
    public final void init() {
        context.setBijgehoudenPersonenVolledig(personenMocks);
        context.setHuidigeAdministratieveHandeling(administratieveHandelingMock);
    }

    @Test
    public final void testVoerStapUit() throws ExpressieExceptie {
        voegTestLeveringsautorisatieToe();
        final boolean resultaatStap = bepaalLeveringsautorisatiePopulatieStap.voerStapUit(onderwerp, context, resultaat);
        Assert.assertTrue(resultaatStap);

        verify(filterNietTeLeverenPersonenService, times(5))
            .filterNietTeLeverenPersonen(eq(personenMocks), anyMapOf(Integer.class, Populatie.class), any(Leveringinformatie.class),
                eq(administratieveHandelingMock));
    }

    @Test(expected = RuntimeException.class)
    public final void testVoerStapUitMetFout() {
        doThrow(RuntimeException.class).when(leveringinformatieService).geefGeldigeLeveringinformaties(Mockito.<SoortDienst[]>anyVararg());
        bepaalLeveringsautorisatiePopulatieStap.voerStapUit(onderwerp, context, resultaat);
    }

    @Test
    public final void testVoerStapUitZonderHuidigeAdministratieveHandeling() throws ExpressieExceptie {
        voegTestLeveringsautorisatieToe();
        context.setHuidigeAdministratieveHandeling(null);
        final boolean resultaatStap = bepaalLeveringsautorisatiePopulatieStap.voerStapUit(onderwerp, context, resultaat);

        Assert.assertTrue(resultaatStap);
    }

    @Test
    public final void testVoerStapUitMetLegeLeveringsautorisatie() {
        autorisaties.add(null);
        final boolean resultaatStap = bepaalLeveringsautorisatiePopulatieStap.voerStapUit(onderwerp, context, resultaat);

        Assert.assertTrue(resultaatStap);
    }

    @Test
    public final void testVoerStapUitZonderBijgehoudenPersonenVolledig() throws ExpressieExceptie {
        voegTestLeveringsautorisatieToe();
        context.setBijgehoudenPersonenVolledig(personenMocks);
        final boolean resultaatStap = bepaalLeveringsautorisatiePopulatieStap.voerStapUit(onderwerp, context, resultaat);

        Assert.assertTrue(resultaatStap);
    }

    @Test
    public final void testVoerStapUitTeLeverenpersonenIsLeeg() throws ExpressieExceptie {
        voegTestLeveringsautorisatieToe();
        when(persoonPopulatieBepalingService.geefPersoonPopulatieCorrelatie(eq(administratieveHandelingMock), eq(personenMocks),
            any(Leveringinformatie.class)))
            .thenReturn(new HashMap<Integer, Populatie>());

        final boolean resultaatStap = bepaalLeveringsautorisatiePopulatieStap.voerStapUit(onderwerp, context, resultaat);

        Assert.assertTrue(resultaatStap);
    }


    /**
     * Voegt test autorisaties toe.
     */
    private void voegTestLeveringsautorisatieToe() throws ExpressieExceptie {
        when(leveringinformatieService.geefGeldigeLeveringinformaties(
            Mockito.<SoortDienst[]>anyVararg())).thenReturn(autorisaties);

        final Map<Integer, Populatie> populatieMapLeveringsautorisatie1 = new HashMap<>();
        populatieMapLeveringsautorisatie1.put(1, Populatie.BETREEDT);
        populatieMapLeveringsautorisatie1.put(2, Populatie.BINNEN);
        voegTestLeveringsautorisatieToe(populatieMapLeveringsautorisatie1);

        final Map<Integer, Populatie> populatieMapLeveringsautorisatie2 = new HashMap<>();
        populatieMapLeveringsautorisatie2.put(3, Populatie.BETREEDT);
        voegTestLeveringsautorisatieToe(populatieMapLeveringsautorisatie2);

        final Map<Integer, Populatie> populatieMapLeveringsautorisatie3 = new HashMap<>();
        populatieMapLeveringsautorisatie3.put(4, Populatie.BETREEDT);
        populatieMapLeveringsautorisatie3.put(5, Populatie.BINNEN);
        populatieMapLeveringsautorisatie3.put(6, Populatie.BUITEN);
        populatieMapLeveringsautorisatie3.put(7, Populatie.BUITEN);
        voegTestLeveringsautorisatieToe(populatieMapLeveringsautorisatie3);

        final Map<Integer, Populatie> populatieMapLeveringsautorisatie4 = new HashMap<>();
        populatieMapLeveringsautorisatie4.put(8, Populatie.VERLAAT);
        voegTestLeveringsautorisatieToe(populatieMapLeveringsautorisatie4);

        final Map<Integer, Populatie> populatieMapLeveringsautorisatie5 = new HashMap<>();
        populatieMapLeveringsautorisatie5.put(9, Populatie.BETREEDT);
        populatieMapLeveringsautorisatie5.put(10, Populatie.BETREEDT);
        voegTestLeveringsautorisatieToe(populatieMapLeveringsautorisatie5);
    }

    /**
     * Voegt test leveringsautorisatie toe.
     *
     * @param populatieMapLeveringsautorisatie5 populatie map leveringsautorisatie 5
     */
    private void voegTestLeveringsautorisatieToe(final Map<Integer, Populatie> populatieMapLeveringsautorisatie5) throws ExpressieExceptie {
        final Leveringinformatie leveringinformatie = maakLeveringinformatie();
        autorisaties.add(leveringinformatie);
        verwachtResultaat.put(leveringinformatie, populatieMapLeveringsautorisatie5);

        when(persoonPopulatieBepalingService.geefPersoonPopulatieCorrelatie(administratieveHandelingMock, personenMocks, leveringinformatie))
            .thenReturn(populatieMapLeveringsautorisatie5);
    }

    /**
     * Maakt test cache element.
     *
     * @return leveringsautorisatie cache element
     */
    private Leveringinformatie maakLeveringinformatie() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("testabo").metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(new DatumAttribuut(20150101)).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metDatumIngang(new DatumAttribuut
            (20150101)).metLeveringsautorisatie
            (leveringsautorisatie).metDummyGeautoriseerde().maak();

        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak();


        return new Leveringinformatie(toegangLeveringsautorisatie, dienst);
    }
}
