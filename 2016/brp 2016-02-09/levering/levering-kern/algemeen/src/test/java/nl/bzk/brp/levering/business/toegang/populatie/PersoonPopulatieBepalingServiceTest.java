/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.levering.business.bepalers.PopulatieBepaler;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PersoonPopulatieBepalingServiceTest {

    private static final String WAAR = "WAAR";

    @Mock
    private PopulatieBepaler populatieTransitieBepaler;

    @Mock
    private PopulatieBepaler populatieBinnenBuitenBepaler;

    @Spy
    private final AdministratieveHandelingSoortService administratieveHandelingSoortService =
        new AdministratieveHandelingSoortServiceImpl();

    @Mock
    private ExpressieService expressieService;

    @InjectMocks
    private final PersoonPopulatieBepalingService persoonPopulatieBepalingService =
        new PersoonPopulatieBepalingServiceImpl();

    @Before
    public final void setUp() {
    }

    @Test
    public final void wordtGeleverdPlaatsenAfnemerIndicatie() throws ExpressieExceptie {
        when(expressieService.geefPopulatiebeperking(anyString(), anyString(), anyString())).thenReturn(BRPExpressies.parse(WAAR).getExpressie());

        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        final Map<Integer, Populatie> resultaat = persoonPopulatieBepalingService
            .geefPersoonPopulatieCorrelatie(maakTestAdministratieveHandeling(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE),
                maakTestBetrokkenPersonen(),
                leveringAutorisatie);

        assertNotNull(administratieveHandelingSoortService);
        assertThat(resultaat.size(), is(1));
        assertThat(resultaat.values(), contains(Populatie.BETREEDT));
    }

    @Test
    public final void wordtNietGeleverdVerwijderenAfnemerIndicatie() throws ExpressieExceptie {
        when(expressieService.geefPopulatiebeperking(anyString(), anyString(), anyString()))
            .thenReturn(BRPExpressies.parse(WAAR).getExpressie());

        final Leveringinformatie leveringAutorisatie =
            maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        final Map<Integer, Populatie> resultaat = persoonPopulatieBepalingService
            .geefPersoonPopulatieCorrelatie(
                maakTestAdministratieveHandeling(SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE),
                maakTestBetrokkenPersonen(),
                leveringAutorisatie);

        assertThat(resultaat.size(), is(1));
        assertThat(resultaat.values(), contains(Populatie.BUITEN));
    }

    @Test
    public final void dienstAttenderingHeeftEigenBepaler() throws ExpressieExceptie {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.ATTENDERING);

        when(expressieService.geefPopulatiebeperking(anyString(), anyString(), anyString())).thenReturn(BRPExpressies.parse(WAAR).getExpressie());
        when(populatieBinnenBuitenBepaler.bepaalInUitPopulatie(any(PersoonHisVolledig.class), any(AdministratieveHandelingModel.class),
            any(Expressie.class), any(Leveringsautorisatie.class))).thenReturn(Populatie.BINNEN);

        // act
        final Map<Integer, Populatie> resultaat = persoonPopulatieBepalingService
            .geefPersoonPopulatieCorrelatie(
                maakTestAdministratieveHandeling(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT),
                maakTestBetrokkenPersonen(),
                leveringAutorisatie);

        assertThat(resultaat.size(), is(1));
        assertThat(resultaat.values(), contains(Populatie.BINNEN));

        verify(populatieBinnenBuitenBepaler).bepaalInUitPopulatie(any(PersoonHisVolledig.class), any(AdministratieveHandelingModel.class),
            any(Expressie.class), any(Leveringsautorisatie.class));
        verify(populatieTransitieBepaler, never()).bepaalInUitPopulatie(any(PersoonHisVolledig.class), any(AdministratieveHandelingModel.class),
            any(Expressie.class), any(Leveringsautorisatie.class));
    }

    @Test
    public final void andereDienstenHebbenDefaultBepaler() throws ExpressieExceptie {
        // arrange
        when(expressieService.geefPopulatiebeperking(anyString(), anyString(), anyString()))
            .thenReturn(BRPExpressies.parse(WAAR).getExpressie());

        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        when(populatieTransitieBepaler.bepaalInUitPopulatie(any(PersoonHisVolledig.class), any(AdministratieveHandelingModel.class),
            any(Expressie.class), any(Leveringsautorisatie.class))).thenReturn(Populatie.VERLAAT);

        // act
        final Map<Integer, Populatie> resultaat = persoonPopulatieBepalingService.geefPersoonPopulatieCorrelatie(
            maakTestAdministratieveHandeling(SoortAdministratieveHandeling.BETWISTING_VAN_STAAT), maakTestBetrokkenPersonen(), leveringAutorisatie);
        // assert
        assertThat(resultaat.size(), is(1));
        assertThat(resultaat.values(), contains(Populatie.VERLAAT));

        verify(populatieTransitieBepaler).bepaalInUitPopulatie(any(PersoonHisVolledig.class), any(AdministratieveHandelingModel.class),
            any(Expressie.class), any(Leveringsautorisatie.class));
        verify(populatieBinnenBuitenBepaler, never()).bepaalInUitPopulatie(any(PersoonHisVolledig.class), any(AdministratieveHandelingModel.class),
            any(Expressie.class), any(Leveringsautorisatie.class));
    }


    @Test(expected = ExpressieExceptie.class)
    public final void fouteExpressieLeidtTotExceptie() throws ExpressieExceptie {
        when(expressieService.geefPopulatiebeperking(anyString(), anyString(), anyString())).thenThrow(new ExpressieExceptie("Test"));

        final Leveringinformatie leveringsautorisatieCacheElement = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        final Map<Integer, Populatie> resultaat =
            persoonPopulatieBepalingService.geefPersoonPopulatieCorrelatie(
                maakTestAdministratieveHandeling(SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE), maakTestBetrokkenPersonen(),
                leveringsautorisatieCacheElement);


    }

    private Leveringinformatie maakLeveringinformatie(final SoortDienst soortDienst) {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(soortDienst).maak();
        TestDienstbundelBuilder.maker().metDiensten(dienst).maak();
        return new Leveringinformatie(tla, dienst);
    }

    private AdministratieveHandelingModel maakTestAdministratieveHandeling(
        final SoortAdministratieveHandeling soortHandeling)
    {
        return new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(soortHandeling),
            new PartijAttribuut(TestPartijBuilder.maker().metNaam("gem").metSoort(SoortPartij.GEMEENTE).metCode(2).maak()),
            new OntleningstoelichtingAttribuut(""),
            DatumTijdAttribuut.nu());
    }

    private List<PersoonHisVolledig> maakTestBetrokkenPersonen() {
        final List<PersoonHisVolledig> resultaat = new ArrayList<>();
        resultaat.add(TestPersoonJohnnyJordaan.maak());
        return resultaat;
    }
}
