/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpAttribuutReferentieExpressie;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroepAttribuutImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelGroepBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AttributenFilterServiceTest {

    @InjectMocks
    private final AttributenFilterService attributenFilterService = new AttributenFilterServiceImpl();

    @Mock
    private ExpressieService expressieService;

    @Mock
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    private final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

    private final PersoonHisVolledigView persoonHisVolledigView =
        new PersoonHisVolledigView(persoonHisVolledig, HistorieVanafPredikaat.geldigOpEnNa(new DatumAttribuut(20000101)));

    private final List<PersoonHisVolledigView> persoonHisVolledigViews = new ArrayList<>(
        Arrays.asList(new PersoonHisVolledigView[]{ persoonHisVolledigView })
    );

    //FIXME geen mocks voor model klassen !!!
    @Mock
    private Partij partij;

    private Attribuut testAttribuut1;

    private Attribuut testAttribuut2;

    private final List<Attribuut> testAttributenLijst = new ArrayList<>();

    @Before
    public final void setup() {
        testAttribuut1 = persoonHisVolledig.getSoort();
        testAttribuut2 = persoonHisVolledig.getPersoonIdentificatienummersHistorie().getHistorie().iterator().next()
            .getBurgerservicenummer();
        for (final BetrokkenheidHisVolledig betrokkenheid : persoonHisVolledig.getBetrokkenheden().iterator().next().getRelatie().getBetrokkenheden()) {
            if (betrokkenheid.getPersoon() != persoonHisVolledig) {
                testAttributenLijst.add(betrokkenheid.getPersoon().getVoornamen().iterator().next()
                    .getPersoonVoornaamHistorie().getHistorie().iterator().next().getNaam());
            }
        }
    }

    @Test
    public final void testZetMagGeleverdWordenVlaggen() throws ExpressieExceptie {
        final Dienst dienst = maakDienst();
        final Expressie testExpressie = mock(Expressie.class);
        when(expressieService.geefAttributenFilterExpressie(dienst, Rol.AFNEMER)).thenReturn(testExpressie);

        final Expressie testExpressieResultaat = getTestExpressie();
        when(expressieService.evalueer(testExpressie, persoonHisVolledigView)).thenReturn(testExpressieResultaat);

        final List<Attribuut> geraakteAttributen = attributenFilterService
            .zetMagGeleverdWordenVlaggen(persoonHisVolledigViews, dienst, Rol.AFNEMER);

        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
        assertEquals(3, geraakteAttributen.size());
        assertThat(geraakteAttributen, hasItems(testAttribuut1, testAttribuut2, testAttributenLijst.get(0)));
    }

    @Test
    public final void testZetMagGeleverdWordenVlaggenMetAttributenMap() throws ExpressieExceptie {
        final String testExpressie1 = "testExpressie1";
        final String testExpressie2 = "testExpressie2";
        final String testExpressie3 = "testExpressie3";
        final List<String> expressieStrings = new ArrayList<>();
        expressieStrings.add(testExpressie1);
        expressieStrings.add(testExpressie2);
        expressieStrings.add(testExpressie3);

        final Dienst dienst = maakDienst();
        final DienstbundelGroep dienstbundelGroep = TestDienstbundelGroepBuilder.maker().maak();
        TestDienstbundelBuilder.maker().metDiensten(dienst).metGroepen(dienstbundelGroep).maak();
        final Set<DienstbundelGroepAttribuutImpl> attributen = new HashSet<>();

        dienstbundelGroep.setAttributen(attributen);

        attributen.add(new DienstbundelGroepAttribuutImpl(dienstbundelGroep, TestElementBuilder.maker().metElementNaam(testExpressie1).metSoort(
            SoortElement.ATTRIBUUT).metExpressie(testExpressie1).maak()));
        attributen.add(new DienstbundelGroepAttribuutImpl(dienstbundelGroep, TestElementBuilder.maker().metElementNaam(testExpressie2).metExpressie
            (testExpressie2).metSoort(
            SoortElement.ATTRIBUUT).maak()));
        attributen.add(new DienstbundelGroepAttribuutImpl(dienstbundelGroep, TestElementBuilder.maker().metElementNaam(testExpressie3).metExpressie
            (testExpressie3).metSoort(
            SoortElement.ATTRIBUUT).maak()));

        final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap = new HashMap<>();
        final Map<String, List<Attribuut>> attributenMap = new HashMap<>();
        persoonAttributenMap.put(persoonHisVolledigView.getID(), attributenMap);
        final List<Attribuut> attributenLijst1 = new ArrayList<>();
        attributenLijst1.add(testAttribuut1);
        final List<Attribuut> attributenLijst2 = new ArrayList<>();
        attributenLijst2.add(testAttribuut2);
        attributenMap.put(testExpressie1, attributenLijst1);
        attributenMap.put(testExpressie2, attributenLijst2);
        attributenMap.put(testExpressie3, testAttributenLijst);

        final List<Attribuut> geraakteAttributen = attributenFilterService
            .zetMagGeleverdWordenVlaggen(persoonHisVolledigViews, dienst, Rol.AFNEMER, persoonAttributenMap);


        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
        assertEquals(3, geraakteAttributen.size());
        assertThat(geraakteAttributen, hasItems(testAttribuut1, testAttribuut2, testAttributenLijst.get(0)));
    }

    @Test
    public final void testZetMagGeleverdWordenVlaggenMetEenPersoon() throws ExpressieExceptie {
        final Dienst dienst = maakDienst();
        final Expressie testExpressie = mock(Expressie.class);
        when(expressieService.geefAttributenFilterExpressie(dienst, Rol.AFNEMER)).thenReturn(testExpressie);

        final Expressie testExpressieResultaat = getTestExpressie();
        when(expressieService.evalueer(testExpressie, persoonHisVolledigView)).thenReturn(testExpressieResultaat);

        final List<Attribuut> geraakteAttributen = attributenFilterService.zetMagGeleverdWordenVlaggen(persoonHisVolledigViews, dienst, Rol.AFNEMER);

        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
        assertEquals(3, geraakteAttributen.size());
        assertThat(geraakteAttributen, hasItems(testAttribuut1, testAttribuut2, testAttributenLijst.get(0)));
    }

    @Test
    public final void testZetMagGeleverdWordenVlaggenMeerderePersonen() throws ExpressieExceptie {
        final Dienst dienst = maakDienst();
        final PersoonHisVolledigImpl testPersoon2 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoonHisVolledigView2 =
            new PersoonHisVolledigView(testPersoon2, HistorieVanafPredikaat.geldigOpEnNa(new DatumAttribuut(20000101)));
        persoonHisVolledigViews.add(persoonHisVolledigView2);

        final Expressie testExpressie = mock(Expressie.class);
        when(expressieService.geefAttributenFilterExpressie(dienst, Rol.AFNEMER)).thenReturn(testExpressie);

        final Expressie testExpressieResultaat = getTestExpressie();
        when(expressieService.evalueer(eq(testExpressie), any(PersoonHisVolledigView.class)))
            .thenReturn(testExpressieResultaat);

        final List<Attribuut> geraakteAttributen =
            attributenFilterService.zetMagGeleverdWordenVlaggen(persoonHisVolledigViews, dienst, Rol.AFNEMER);

        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
        assertEquals(6, geraakteAttributen.size());
        assertThat(geraakteAttributen, hasItems(testAttribuut1, testAttribuut2, testAttributenLijst.get(0)));
    }

    @Test(expected = ExpressieExceptie.class)
    public final void testZetMagGeleverdWordenVlaggenMetExceptieBijParsen() throws ExpressieExceptie {
        final Dienst dienst = maakDienst();
        when(expressieService.geefAttributenFilterExpressie(dienst, Rol.AFNEMER)).thenThrow(new ExpressieExceptie("Fout!"));

        attributenFilterService.zetMagGeleverdWordenVlaggen(persoonHisVolledigViews, dienst, Rol.AFNEMER);
    }

    @Test(expected = ExpressieExceptie.class)
    public final void testZetMagGeleverdWordenVlaggenMetExceptieBijEvalueren() throws ExpressieExceptie {
        final Dienst dienst = maakDienst();
        final Expressie testExpressie = mock(Expressie.class);
        when(expressieService.geefAttributenFilterExpressie(dienst, Rol.AFNEMER)).thenReturn(testExpressie);
        when(expressieService.evalueer(testExpressie, persoonHisVolledigView)).thenThrow(new ExpressieExceptie("Test"));

        attributenFilterService.zetMagGeleverdWordenVlaggen(persoonHisVolledigViews, dienst, Rol.AFNEMER);
    }

    @Test
    public final void testResetMagGeleverdWordenVlaggen() {
        final List<Attribuut> testAttributen = testAttributenLijst;
        testAttributen.add(testAttribuut1);
        testAttributen.add(testAttribuut2);

        for (final Attribuut attribuut : testAttributenLijst) {
            attribuut.setMagGeleverdWorden(true);
        }

        attributenFilterService.resetMagGeleverdWordenVlaggen(testAttributen);

        for (final Attribuut attribuut : testAttributenLijst) {
            assertFalse(attribuut.isMagGeleverdWorden());
        }
    }

    private Expressie getTestExpressie() {
        final BrpAttribuutReferentieExpressie attribuutReferentieExpressie1 =
            new BrpAttribuutReferentieExpressie(testAttribuut1);

        final List<Expressie> expressies = new ArrayList<>();
        for (final Attribuut attribuut : testAttributenLijst) {
            final BrpAttribuutReferentieExpressie attribuutReferentieExpressie = new BrpAttribuutReferentieExpressie(attribuut);
            expressies.add(attribuutReferentieExpressie);
        }

        // Leeg attribuut waar niets mee gedaan wordt (coverage)
        final BrpAttribuutReferentieExpressie attribuutReferentieExpressie = new BrpAttribuutReferentieExpressie(null);
        expressies.add(attribuutReferentieExpressie);

        // Onverwacht type expressie waar niets mee gedaan wordt (coverage)
        final VariabeleExpressie variabeleExpressie = new VariabeleExpressie("bla");
        expressies.add(variabeleExpressie);

        final LijstExpressie lijstExpressieVoornamen = new LijstExpressie(expressies);

        final BrpAttribuutReferentieExpressie attribuutReferentieExpressie2 =
            new BrpAttribuutReferentieExpressie(testAttribuut2);

        final List<Expressie> resultaat = new ArrayList<>();
        resultaat.add(attribuutReferentieExpressie1);
        resultaat.add(lijstExpressieVoornamen);
        resultaat.add(attribuutReferentieExpressie2);

        return new LijstExpressie(resultaat);
    }

    private Dienst maakDienst() {
        final Dienst dummy = TestDienstBuilder.dummy();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().maak();
        TestDienstbundelBuilder.maker().metDiensten(dummy).metLeveringsautoriastie(la).maak();
        return dummy;
    }

}
