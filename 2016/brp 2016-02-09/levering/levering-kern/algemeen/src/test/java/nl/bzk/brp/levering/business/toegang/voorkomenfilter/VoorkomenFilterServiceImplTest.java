/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.voorkomenfilter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import nl.bzk.brp.levering.algemeen.service.DienstFilterExpressiesService;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.algemeen.service.impl.DienstFilterExpressiesServiceImpl;
import nl.bzk.brp.levering.business.bepalers.LegeBerichtBepaler;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.business.expressietaal.MagGeleverdWordenVlaggenZetter;
import nl.bzk.brp.levering.business.expressietaal.impl.ExpressieServiceImpl;
import nl.bzk.brp.levering.business.expressietaal.impl.MagGeleverdWordenVlaggenZetterImpl;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroepAttribuutImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelGroepBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.MagHistorieTonenPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class VoorkomenFilterServiceImplTest {

    private static final String IS_MUTATIELEVERING                        = "isMutatieLevering";
    private static final String PREDIKATEN                                = "predikaten";
    private static final String PREDIKAAT_GEVONDEN                        = "Er zou een predikaat toegevoegd moeten zijn aan de PersoonHisVolledigView.";
    private static final String MAGGELEVERDWORDENVLAGGENZETTER            = "magGeleverdWordenVlaggenZetter";
    private static final String MAGGELEVERDWORDEN                         = "magGeleverdWorden";
    private static final String AANTAL_HITS_VAN_VERANTWOORDING_KLOPT_NIET = "Aantal hits van verantwoording klopt niet";
    private static final String TEST                                      = "Test";

    @Mock
    private DienstFilterExpressiesService dienstFilterExpressiesServiceMock;

    @Mock
    private ExpressieService expressieServiceMock;

    @Mock
    private VerplichtLeverenVerantwoordingVoorAboHandelingen verplichtLeverenVerantwoordingVoorAboHandelingen;

    @Mock
    private LegeBerichtBepaler legeBerichtBepaler;

    @InjectMocks
    private final VoorkomenFilterService voorkomenFilterService = new VoorkomenFilterServiceImpl();

    private final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

    private final PersoonHisVolledigView persoonHisVolledigView =
        new PersoonHisVolledigView(persoonHisVolledig, HistorieVanafPredikaat.geldigOpEnNa(new DatumAttribuut(20000101)));

    private final List<PersoonHisVolledigView> persoonHisVolledigViews = new ArrayList<>(
        Arrays.asList(new PersoonHisVolledigView[]{ persoonHisVolledigView })
    );

    private Dienst dienst;

    private Attribuut testAttribuut1;

    private Attribuut testAttribuut2;

    private final List<Attribuut> testAttributenLijst = new ArrayList<>();

    private final Expressie resetExpressie = mock(Expressie.class);

    @Before
    public final void setup() throws ExpressieExceptie {


        final Element groep = TestElementBuilder.maker().metNaam(ElementEnum.GERELATEERDEERKENNER_PERSOON).maak();
        final DienstbundelGroep dienstbundelGroep = TestDienstbundelGroepBuilder.maker()
            .metGroep(groep).metIndicatieFormeleHistorie(false).metIndicatieMaterieleHistorie(false).metIndicatieVerantwoording(false).maak();

        dienst = TestDienstBuilder.maker().maak();

        TestDienstbundelBuilder.maker().metDiensten(dienst).metGroepen(dienstbundelGroep).maak();

        final Set<DienstbundelGroepAttribuutImpl> attributen = new HashSet<>();

        testAttribuut1 = persoonHisVolledig.getSoort();
        testAttribuut2 = persoonHisVolledig.getPersoonIdentificatienummersHistorie().getHistorie().iterator().next()
            .getBurgerservicenummer();
        for (final BetrokkenheidHisVolledig betrokkenheid : persoonHisVolledig.getBetrokkenheden().iterator().next().getRelatie().getBetrokkenheden()) {
            if (betrokkenheid.getPersoon() != persoonHisVolledig) {
                testAttributenLijst.add(betrokkenheid.getPersoon().getVoornamen().iterator().next()
                    .getPersoonVoornaamHistorie().getHistorie().iterator().next()
                    .getNaam());
                Element element = TestElementBuilder.maker().metElementGroep(groep)
                    .metElementNaam(betrokkenheid.getPersoon().getVoornamen().iterator().next()
                        .getPersoonVoornaamHistorie().getHistorie().iterator().next()
                        .getNaam().getWaarde()).maak();
                attributen.add(new DienstbundelGroepAttribuutImpl(dienstbundelGroep, element));
            }
        }
        dienstbundelGroep.setAttributen(attributen);
        when(dienstFilterExpressiesServiceMock.geefAllExpressiesVoorHistorieEnVerantwoordingAttributen()).thenReturn(resetExpressie);
        when(expressieServiceMock.evalueer(eq(resetExpressie), any(PersoonHisVolledigView.class))).thenReturn(mock(Expressie.class));
    }

    @Test
    public final void testZetMagGeleverdWordenVlaggen() throws ExpressieExceptie {
        final Expressie testExpressie = mock(Expressie.class);
        when(dienstFilterExpressiesServiceMock.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst)).thenReturn(testExpressie);

        final Expressie testExpressieResultaat = getTestExpressie();
        when(expressieServiceMock.evalueer(testExpressie, persoonHisVolledigView)).thenReturn(testExpressieResultaat);

        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            voorkomenFilterService.voerVoorkomenFilterUit(hisVolledigView, dienst);
        }

        // Test dat het predikaat op de hisvolledigviews is gezet:
        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            final List<Predicate> predikaten = (List<Predicate>) ReflectionTestUtils.getField(hisVolledigView, PREDIKATEN);
            boolean predikaatGevonden = false;
            MagHistorieTonenPredikaat predikaat = null;
            for (final Predicate predicate : predikaten) {
                if (predicate instanceof MagHistorieTonenPredikaat) {
                    predikaatGevonden = true;
                    predikaat = (MagHistorieTonenPredikaat) predicate;
                }
            }
            assertTrue(PREDIKAAT_GEVONDEN, predikaatGevonden);
            assertFalse("Het predikaat moet geen mutatie levering predikaat zijn!",
                (Boolean) ReflectionTestUtils.getField(predikaat, IS_MUTATIELEVERING));
        }

        assertNotNull(dienst);
        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
    }

    @Test
    /**
     * test het  geoptimaliseerde mutatuelevering pad
     */
    public final void testZetMagGeleverdWordenVlaggenMutatieLeveringPad() throws ExpressieExceptie {
        final String testExpressie1 = "testExpressie1";
        final String testExpressie2 = "testExpressie2";
        final String testExpressie3 = "testExpressie3";
        final List<String> testExpressieLijst = new ArrayList<>();
        testExpressieLijst.add(testExpressie1);
        testExpressieLijst.add(testExpressie2);
        testExpressieLijst.add(testExpressie3);
        when(dienstFilterExpressiesServiceMock.geefExpressiesVoorHistorieEnVerantwoordingAttributenLijst(dienst))
            .thenReturn(testExpressieLijst);


        final Map<String, List<Attribuut>> expressieAttributenMap = new HashMap<>();
        final List<Attribuut> attributen1 = new ArrayList<>();
        attributen1.add(testAttribuut1);
        final List<Attribuut> attributen2 = new ArrayList<>();
        attributen1.add(testAttribuut2);
        expressieAttributenMap.put(testExpressie1, attributen1);
        expressieAttributenMap.put(testExpressie2, attributen2);
        expressieAttributenMap.put(testExpressie3, testAttributenLijst);

        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            voorkomenFilterService.voerVoorkomenFilterUit(hisVolledigView, dienst, expressieAttributenMap);
        }

        // Test dat het predikaat op de hisvolledigviews is gezet:
        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            final List<Predicate> predikaten = (List<Predicate>) ReflectionTestUtils.getField(hisVolledigView, PREDIKATEN);
            boolean predikaatGevonden = false;
            MagHistorieTonenPredikaat predikaat = null;
            for (final Predicate predicate : predikaten) {
                if (predicate instanceof MagHistorieTonenPredikaat) {
                    predikaatGevonden = true;
                    predikaat = (MagHistorieTonenPredikaat) predicate;
                }
            }
            assertTrue(PREDIKAAT_GEVONDEN, predikaatGevonden);
            assertFalse("Het predikaat moet geen mutatie levering predikaat zijn!",
                (Boolean) ReflectionTestUtils.getField(predikaat, IS_MUTATIELEVERING));
        }

        assertNotNull(dienst);
        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
    }

    @Test
    public final void testZetMagGeleverdWordenVlaggenMetEenPersoon() throws ExpressieExceptie {
        final Expressie testExpressie = mock(Expressie.class);
        when(dienstFilterExpressiesServiceMock.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst)).thenReturn(testExpressie);

        final Expressie testExpressieResultaat = getTestExpressie();
        when(expressieServiceMock.evalueer(testExpressie, persoonHisVolledigView)).thenReturn(testExpressieResultaat);

        voorkomenFilterService.voerVoorkomenFilterUit(persoonHisVolledigViews.get(0), dienst);

        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
    }

    @Test
    public final void testZetMagGeleverdWordenVlaggenMeerderePersonen() throws ExpressieExceptie {
        final PersoonHisVolledigImpl testPersoon2 = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoonHisVolledigView2 =
            new PersoonHisVolledigView(testPersoon2,
                HistorieVanafPredikaat.geldigOpEnNa(
                    new DatumAttribuut(20000101)));
        persoonHisVolledigViews.add(persoonHisVolledigView2);

        final Expressie testExpressie = mock(Expressie.class);
        when(dienstFilterExpressiesServiceMock.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst)).thenReturn(testExpressie);

        final Expressie testExpressieResultaat = getTestExpressie();
        when(expressieServiceMock.evalueer(eq(testExpressie), any(PersoonHisVolledigView.class))).thenReturn(testExpressieResultaat);

        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            voorkomenFilterService.voerVoorkomenFilterUit(hisVolledigView, dienst);
        }

        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
    }

    @Test
    public final void testVoerGroepenFilterUitVoorMutatieLevering() throws ExpressieExceptie {
        final Expressie testExpressie = mock(Expressie.class);
        when(dienstFilterExpressiesServiceMock.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst))
            .thenReturn(testExpressie);

        final Expressie testExpressieResultaat = getTestExpressie();
        when(expressieServiceMock.evalueer(testExpressie, persoonHisVolledigView)).thenReturn(testExpressieResultaat);

        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            voorkomenFilterService.voerVoorkomenFilterUitVoorMutatieLevering(hisVolledigView, dienst);
        }

        // Test dat het predikaat op de hisvolledigviews is gezet:
        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            final List<Predicate> predikaten = (List<Predicate>) ReflectionTestUtils.getField(hisVolledigView, PREDIKATEN);
            boolean predikaatGevonden = false;
            MagHistorieTonenPredikaat predikaat = null;
            for (final Predicate predicate : predikaten) {
                if (predicate instanceof MagHistorieTonenPredikaat) {
                    predikaatGevonden = true;
                    predikaat = (MagHistorieTonenPredikaat) predicate;
                }
            }
            assertTrue(PREDIKAAT_GEVONDEN, predikaatGevonden);
            assertTrue("Het predikaat moet een mutatie levering predikaat zijn!",
                (Boolean) ReflectionTestUtils.getField(predikaat, IS_MUTATIELEVERING));

        }

        assertTrue(testAttribuut1.isMagGeleverdWorden());
        assertTrue(testAttribuut2.isMagGeleverdWorden());
        for (final Attribuut attribuut : testAttributenLijst) {
            assertTrue(attribuut.isMagGeleverdWorden());
        }
    }

    @Test(expected = ExpressieExceptie.class)
    public final void testZetMagGeleverdWordenVlaggenMetExceptieBijParsen() throws ExpressieExceptie {
        when(dienstFilterExpressiesServiceMock.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst))
            .thenThrow(new ExpressieExceptie("Fout!"));

        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            voorkomenFilterService.voerVoorkomenFilterUit(hisVolledigView, dienst);
        }
    }

    @Test(expected = ExpressieExceptie.class)
    public final void testZetMagGeleverdWordenVlaggenMetExceptieBijEvalueren() throws ExpressieExceptie {
        final Expressie testExpressie = mock(Expressie.class);
        when(dienstFilterExpressiesServiceMock.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst))
            .thenReturn(testExpressie);
        when(expressieServiceMock.evalueer(testExpressie, persoonHisVolledigView)).thenThrow(new ExpressieExceptie(TEST));

        for (final PersoonHisVolledigView hisVolledigView : persoonHisVolledigViews) {
            voorkomenFilterService.voerVoorkomenFilterUit(hisVolledigView, dienst);
        }
    }

    @Test
    public final void testResetOudeVlaggenEerst() throws ExpressieExceptie {
        final MagGeleverdWordenVlaggenZetter magGeleverdWordenVlaggenZetter =
            mock(MagGeleverdWordenVlaggenZetter.class);
        ReflectionTestUtils
            .setField(voorkomenFilterService, MAGGELEVERDWORDENVLAGGENZETTER, magGeleverdWordenVlaggenZetter);

        final Expressie testExpressie = mock(Expressie.class);
        when(dienstFilterExpressiesServiceMock.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst))
            .thenReturn(testExpressie);

        final Expressie testExpressieResultaat = getTestExpressie();
        when(expressieServiceMock.evalueer(testExpressie, persoonHisVolledigView)).thenReturn(testExpressieResultaat);
        when(expressieServiceMock.evalueer(resetExpressie, persoonHisVolledigView)).thenReturn(testExpressieResultaat);

        voorkomenFilterService.voerVoorkomenFilterUit(persoonHisVolledigViews.get(0), dienst);

        verify(expressieServiceMock).evalueer(resetExpressie, persoonHisVolledigViews.get(0));
        verify(magGeleverdWordenVlaggenZetter).zetMagGeleverdWordenVlaggenOpWaarde(testExpressieResultaat, false);
    }

    //SNAPT IEMAND DEZE TEST NOG?
    @Test
    @Ignore
    public void testResetMechanismeMetMinderMocks() throws ExpressieExceptie {
        final MagGeleverdWordenVlaggenZetter magGeleverdWordenVlaggenZetter = new MagGeleverdWordenVlaggenZetterImpl();
        ReflectionTestUtils.setField(voorkomenFilterService, MAGGELEVERDWORDENVLAGGENZETTER, magGeleverdWordenVlaggenZetter);

        final DienstFilterExpressiesService dienstFilterExpressiesService = new DienstFilterExpressiesServiceImpl();
        final ExpressieService expressieService = new ExpressieServiceImpl();
        ReflectionTestUtils.setField(voorkomenFilterService, "dienstFilterExpressiesService", dienstFilterExpressiesService);
        ReflectionTestUtils.setField(voorkomenFilterService, "expressieService", expressieService);
        final StamTabelService stamTabelService = mock(StamTabelService.class);
        ReflectionTestUtils.setField(dienstFilterExpressiesService, "stamTabelService", stamTabelService);

        //Zorg voor een vervallen record op de identificatie nummers
        final PersoonIdentificatienummersGroepBericht identificatienummersGroepBericht = new PersoonIdentificatienummersGroepBericht();
        identificatienummersGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20140101));
        identificatienummersGroepBericht.setBurgerservicenummer(new BurgerservicenummerAttribuut(1234));
        persoonHisVolledig.getPersoonIdentificatienummersHistorie().voegToe(
            new HisPersoonIdentificatienummersModel(persoonHisVolledig, identificatienummersGroepBericht,
                identificatienummersGroepBericht,
                new ActieModel(null, null, null, null, null, DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1), null))
        );

        // Zet het magGeleverdWorden vlaggetje van alle actie veldjes op TRUE
        int hitCounter = 0;
        for (final HisPersoonIdentificatienummersModel hisPersoonIdentificatienummersModel : persoonHisVolledig.getPersoonIdentificatienummersHistorie()
            .getHistorie()) {
            hisPersoonIdentificatienummersModel.getVerantwoordingInhoud().setMagGeleverdWorden(true);
            if (hisPersoonIdentificatienummersModel.getVerantwoordingVerval() != null) {
                hisPersoonIdentificatienummersModel.getVerantwoordingVerval().setMagGeleverdWorden(true);
                hitCounter++;
            }

            if (hisPersoonIdentificatienummersModel.getVerantwoordingAanpassingGeldigheid() != null) {
                hisPersoonIdentificatienummersModel.getVerantwoordingAanpassingGeldigheid().setMagGeleverdWorden(true);
                hitCounter++;
            }
        }
        assertEquals(AANTAL_HITS_VAN_VERANTWOORDING_KLOPT_NIET, 3, hitCounter);

        //Zorg voor een vervallen record op de identificatie nummers
        final PersoonGeboorteGroepBericht geboorteGroepBericht = new PersoonGeboorteGroepBericht();
        geboorteGroepBericht.setDatumTijdRegistratie(DatumTijdAttribuut.nu());
        geboorteGroepBericht.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(19840101));
        persoonHisVolledig.getPersoonGeboorteHistorie().voegToe(
            new HisPersoonGeboorteModel(persoonHisVolledig, geboorteGroepBericht,
                new ActieModel(null, null, null, null, null, DatumTijdAttribuut.bouwDatumTijd(2014, 1, 1), null)));

        // Zet het magGeleverdWorden vlaggetje van alle actie veldjes op TRUE
        int hitCounterGeboorte = 0;
        for (final HisPersoonGeboorteModel hisPersoonGeboorteModel : persoonHisVolledig.getPersoonGeboorteHistorie().getHistorie()) {
            hisPersoonGeboorteModel.getVerantwoordingInhoud().setMagGeleverdWorden(true);
            if (hisPersoonGeboorteModel.getVerantwoordingVerval() != null) {
                hisPersoonGeboorteModel.getVerantwoordingVerval().setMagGeleverdWorden(true);
                hitCounterGeboorte++;
            }
        }
        assertEquals(AANTAL_HITS_VAN_VERANTWOORDING_KLOPT_NIET, 1, hitCounterGeboorte);

        // Check of de vlaggetjes ge-reset zijn
        hitCounter = 0;
        for (final HisPersoonIdentificatienummersModel hisPersoonIdentificatienummersModel : persoonHisVolledig
            .getPersoonIdentificatienummersHistorie().getHistorie()) {
            final Boolean magGeleverdWorden = (Boolean) ReflectionTestUtils
                .getField(hisPersoonIdentificatienummersModel.getVerantwoordingInhoud(), MAGGELEVERDWORDEN);
            assertFalse(magGeleverdWorden);

            if (hisPersoonIdentificatienummersModel.getVerantwoordingVerval() != null) {
                final Boolean magGeleverdWorden2 = (Boolean) ReflectionTestUtils
                    .getField(hisPersoonIdentificatienummersModel.getVerantwoordingVerval(), MAGGELEVERDWORDEN);
                assertFalse(magGeleverdWorden2);
                hitCounter++;
            }
            if (hisPersoonIdentificatienummersModel.getVerantwoordingAanpassingGeldigheid() != null) {
                final Boolean magGeleverdWorden3 = (Boolean) ReflectionTestUtils
                    .getField(hisPersoonIdentificatienummersModel.getVerantwoordingAanpassingGeldigheid(),
                        MAGGELEVERDWORDEN);
                assertFalse(magGeleverdWorden3);
                hitCounter++;
            }
        }
        assertEquals("De coverage is niet goed!", 3, hitCounter);
    }

    private Expressie getTestExpressie() {
        final BrpAttribuutReferentieExpressie attribuutReferentieExpressie1 = new BrpAttribuutReferentieExpressie(testAttribuut1);

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

        final BrpAttribuutReferentieExpressie attribuutReferentieExpressie2 = new BrpAttribuutReferentieExpressie(testAttribuut2);

        final List<Expressie> resultaat = new ArrayList<>();
        resultaat.add(attribuutReferentieExpressie1);
        resultaat.add(lijstExpressieVoornamen);
        resultaat.add(attribuutReferentieExpressie2);

        return new LijstExpressie(resultaat);
    }

}
