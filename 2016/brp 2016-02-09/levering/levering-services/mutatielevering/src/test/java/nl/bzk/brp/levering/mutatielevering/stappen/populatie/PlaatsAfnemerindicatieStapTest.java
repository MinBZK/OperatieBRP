/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.afnemerindicaties.model.AfnemerindicatieReedsAanwezigExceptie;
import nl.bzk.brp.levering.afnemerindicaties.service.AfnemerindicatiesZonderRegelsService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.functors.TruePredicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import support.AdministratieveHandelingTestBouwer;

@RunWith(MockitoJUnitRunner.class)
public class PlaatsAfnemerindicatieStapTest {

    @InjectMocks
    private final PlaatsAfnemerindicatieStap plaatsAfnemerindicatieStap = new PlaatsAfnemerindicatieStap();

    @Mock
    private AfnemerindicatiesZonderRegelsService afnemerindicatiesZonderRegelsService;

    @Mock
    private LeveringautorisatieStappenOnderwerp leveringautorisatieStappenOnderwerp;

    private Leveringinformatie leveringinformatie;


    @Mock
    private LeveringautorisatieVerwerkingResultaat resultaat;

    private LeveringsautorisatieVerwerkingContext context;
    private final List<SynchronisatieBericht> leveringBerichten = new ArrayList<>();

    private AdministratieveHandelingModel administratieveHandeling;

    int nieuwLeveringsautorisatieId = 999;

    @Before
    public final void init() {
        administratieveHandeling = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        context = new LeveringsautorisatieVerwerkingContextImpl(administratieveHandeling, new ArrayList<PersoonHisVolledig>(), null, null, null);

        final Partij partij = TestPartijBuilder.maker().metCode(123).metNaam("Afnemernaam").maak();
        final Leveringinformatie leveringinformatie = testLeveringsautorisatie(nieuwLeveringsautorisatieId, partij);

        zetTestDataKlaar(leveringinformatie);
    }

    /**
     * Zet de testdata klaar.
     *
     * @param leveringinformatie De leveringinformatie dat gebruikt moet worden.
     */
    private void zetTestDataKlaar(final Leveringinformatie leveringinformatie) {
        when(leveringautorisatieStappenOnderwerp.getLeveringinformatie()).thenReturn(leveringinformatie);

        context.setLeveringBerichten(leveringBerichten);
        this.leveringinformatie = leveringinformatie;

    }

    @Test
    public final void testVoerStapUit() throws BrpLockerExceptie {
        final AdministratieveHandelingModel administratieveHandeling =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));

        voegPersonenToeAanBericht(volledigBericht, 2);
        leveringBerichten.add(volledigBericht);

        final boolean resultaatVanStap =
            plaatsAfnemerindicatieStap.voerStapUit(leveringautorisatieStappenOnderwerp, context, resultaat);

        assertEquals(AbstractStap.DOORGAAN, resultaatVanStap);
        verify(afnemerindicatiesZonderRegelsService).plaatsAfnemerindicatie(leveringinformatie.getToegangLeveringsautorisatie(),
                1, leveringinformatie.getDienst().getID(), null, null,
            context.getAdministratieveHandeling().getTijdstipRegistratie());
        verify(afnemerindicatiesZonderRegelsService).plaatsAfnemerindicatie(leveringinformatie.getToegangLeveringsautorisatie(),
                2, leveringinformatie.getDienst().getID(), null, null, context.getAdministratieveHandeling().getTijdstipRegistratie());
    }

    @Test
    public final void testVoerStapUitMetMutatieBericht() {
        final AdministratieveHandelingModel administratieveHandeling =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        final MutatieBericht mutatieBericht =
            new MutatieBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));

        voegPersonenToeAanBericht(mutatieBericht, 2);
        leveringBerichten.add(mutatieBericht);

        final boolean resultaatVanStap =
            plaatsAfnemerindicatieStap.voerStapUit(leveringautorisatieStappenOnderwerp, context, resultaat);

        assertEquals(AbstractStap.DOORGAAN, resultaatVanStap);
        verifyZeroInteractions(afnemerindicatiesZonderRegelsService);
    }

    @Test(expected = RuntimeException.class)
    public final void testVoerStapUitMetException() throws BrpLockerExceptie {
        final AdministratieveHandelingModel administratieveHandeling =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));

        voegPersonenToeAanBericht(volledigBericht, 2);
        leveringBerichten.add(volledigBericht);

        doThrow(new BrpLockerExceptie("Locker is naar de knoppen!"))
            .when(afnemerindicatiesZonderRegelsService).plaatsAfnemerindicatie(any(ToegangLeveringsautorisatie.class),
            anyInt(), anyInt(), any(DatumEvtDeelsOnbekendAttribuut.class), any(DatumAttribuut.class), any(DatumTijdAttribuut.class));

        plaatsAfnemerindicatieStap.voerStapUit(leveringautorisatieStappenOnderwerp, context, resultaat);
    }

    @Test
    public final void testVoerStapUitMetAfnemerindicatieReedsAanwezig() throws BrpLockerExceptie {
        final AdministratieveHandelingModel administratieveHandeling =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));

        voegPersonenToeAanBericht(volledigBericht, 2);
        leveringBerichten.add(volledigBericht);

        doThrow(new AfnemerindicatieReedsAanwezigExceptie("Afnemerindicatie bestaat al!"))
            .when(afnemerindicatiesZonderRegelsService).plaatsAfnemerindicatie(any(ToegangLeveringsautorisatie.class),
                anyInt(), anyInt(), any(DatumEvtDeelsOnbekendAttribuut.class), any(DatumAttribuut.class), any(DatumTijdAttribuut.class));

        plaatsAfnemerindicatieStap.voerStapUit(leveringautorisatieStappenOnderwerp, context, resultaat);
    }

    @Test
    public final void testVoerStapUitMetBestaandeAfnemerindicatieDusDoetNiets() {
        final Partij partij = TestPartijBuilder.maker().metNaam("testPartij").metCode(1).maak();

        final Leveringinformatie leveringinformatie = testLeveringsautorisatie(TestPersoonJohnnyJordaan.LEVERINGAUTORISATIE_ID_2, partij);

        zetTestDataKlaar(leveringinformatie);

        final AdministratieveHandelingModel administratieveHandeling =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));

        voegPersonenToeAanBericht(volledigBericht, 1);
        leveringBerichten.add(volledigBericht);

        final boolean resultaatVanStap =
            plaatsAfnemerindicatieStap.voerStapUit(leveringautorisatieStappenOnderwerp, context, resultaat);

        assertEquals(AbstractStap.DOORGAAN, resultaatVanStap);
        verifyZeroInteractions(afnemerindicatiesZonderRegelsService);
    }

    @Test
    public final void testVoerStapUitAndereAfnemer() throws BrpLockerExceptie {
        final Partij partij = TestPartijBuilder.maker().metNaam("testPartijAnder").metCode(2).maak();
        final Leveringinformatie leveringinformatie = testLeveringsautorisatie(nieuwLeveringsautorisatieId, partij);


        zetTestDataKlaar(leveringinformatie);

        final AdministratieveHandelingModel administratieveHandeling =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));

        voegPersonenToeAanBericht(volledigBericht, 1);
        leveringBerichten.add(volledigBericht);

        final boolean resultaatVanStap =
            plaatsAfnemerindicatieStap.voerStapUit(leveringautorisatieStappenOnderwerp, context, resultaat);

        assertEquals(AbstractStap.DOORGAAN, resultaatVanStap);
        verify(afnemerindicatiesZonderRegelsService).plaatsAfnemerindicatie(leveringinformatie.getToegangLeveringsautorisatie(), 1,
                leveringinformatie.getDienst().getID(), null, null, context.getAdministratieveHandeling().getTijdstipRegistratie());
    }

    /**
     * Voegt personen toe aan bericht.
     *
     * @param synchronisatieBericht Het synchronisatie bericht.
     * @param aantal                Het aantal
     */
    private void voegPersonenToeAanBericht(final SynchronisatieBericht synchronisatieBericht, final int aantal) {
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();

        for (int i = 0; i < aantal; i++) {
            final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
            ReflectionTestUtils.setField(persoon, "iD", i + 1);

            final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, TruePredicate.getInstance());

            bijgehoudenPersonen.add(persoonView);
            context.getBijgehoudenPersonenVolledig().add(persoon);
        }

        synchronisatieBericht.getAdministratieveHandeling().setBijgehoudenPersonen(bijgehoudenPersonen);
    }

    private Leveringinformatie testLeveringsautorisatie(final int leveringsautorisatieId, final Partij partij) {
        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.ATTENDERING).metEffectAfnemerindicaties(EffectAfnemerindicaties
            .PLAATSING).maak();
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(dienst).maak();

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metDienstbundels(dienstbundel).metId(leveringsautorisatieId)
            .metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(new DatumAttribuut(20150101)).maak();

        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).metGeautoriseerde(new PartijRol(partij, null, null, null)).maak();

        return new Leveringinformatie(toegangLeveringsautorisatie, dienst);
    }
}
