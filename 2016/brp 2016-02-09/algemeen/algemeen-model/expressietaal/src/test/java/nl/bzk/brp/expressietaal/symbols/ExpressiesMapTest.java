/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.TestPersonen;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpAttribuutReferentieExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.Predicate;
import org.junit.Test;

public class ExpressiesMapTest {

    private static final String FOUT_BIJ_EVALUEREN_EXPRESSIE = "Fout bij evalueren expressie: ";
    private static final String FOUT = " Fout: ";
    private static final String JORDAN = "Jordan";
    private static final String JORDAAN = "Jordaan";

    @Test
    public void testAlleExpressiesInMap() {
        for (final String expressie : ExpressieMap.getMap().values()) {
            System.out.println("expressie: " + expressie);
            final ParserResultaat geparsdeExpressie = BRPExpressies.parse(expressie);
            assertTrue("Foutmelding op parsen expressie: " + expressie + " Melding: "
                    + geparsdeExpressie.getFoutmelding(),
                    geparsdeExpressie.succes());
        }
    }

    @Test
    public void testAlleExpressiesInMapEvalueren() {
        for (final String expressie : ExpressieMap.getMap().values()) {
            final Expressie expressieResultaat = BRPExpressies.evalueer(expressie, maakPersoonHisVolledigView());
            assertFalse(FOUT_BIJ_EVALUEREN_EXPRESSIE + expressie + FOUT + expressieResultaat.alsString(),
                    expressieResultaat.isFout());
        }
    }

    private PersoonHisVolledig maakPersoonHisVolledigView() {
        final PersoonHisVolledigImpl johnny = TestPersoonJohnnyJordaan.maak();
        return new PersoonHisVolledigView(johnny, HistorieVanafPredikaat.geldigOpEnNa(new DatumAttribuut(20000101)));
    }

    @Test
    public void testAlleExpressiesInMapTegenTestPersonen1Voor1() {
        final List<PersoonHisVolledig> testPersonen = TestPersonen.geefAlleTestPersonen();

        evalueerTestPersonenTegenAlleExpressies1Voor1(testPersonen);
    }

    @Test
    public void testAlleExpressiesInMapTegenTestPersonenIn1Keer() {
        final List<PersoonHisVolledig> testPersonen = TestPersonen.geefAlleTestPersonen();

        evalueerTestPersonenTegenAlleExpressiesIn1Keer(testPersonen);
    }

    @Test
    public void testAlleExpressiesInMapTegenTestPersonenInView() {
        final List<PersoonHisVolledig> testPersonen = TestPersonen.geefAlleTestPersonen();
        final List<PersoonHisVolledig> testPersonenViews = new ArrayList<>();
        final HistorieVanafPredikaat tsregPredikaat = HistorieVanafPredikaat.geldigOpEnNa(
            new DatumAttribuut(19000101));

        for (final PersoonHisVolledig testPersoon : testPersonen) {
            final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(testPersoon, tsregPredikaat);
            testPersonenViews.add(persoonHisVolledigView);
        }

        evalueerTestPersonenTegenAlleExpressies1Voor1(testPersonenViews);
    }

    @Test
    public void testOuderHeeftGezag() {
        final String testExpressie = "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.ouder_heeft_gezag))";
        final PersoonHisVolledig testPersoon = TestPersoonJohnnyJordaan.maak();
        final List<Attribuut> ouderlijkGezagAttributen = new ArrayList<>();
        for (final BetrokkenheidHisVolledig betrokkenheid : testPersoon.getBetrokkenheden()) {
            if (betrokkenheid instanceof OuderHisVolledig) {
                final MaterieleHistorieSet<HisOuderOuderlijkGezagModel> ouderlijkgezagHistorie =
                        ((OuderHisVolledig) betrokkenheid).getOuderOuderlijkGezagHistorie();

                if (ouderlijkgezagHistorie != null && !ouderlijkgezagHistorie.isLeeg()) {
                    ouderlijkGezagAttributen.add(
                            ouderlijkgezagHistorie.getActueleRecord().getIndicatieOuderHeeftGezag());
                }
            }
        }

        final LijstExpressie resultaat = (LijstExpressie) BRPExpressies.evalueer(testExpressie, testPersoon);
        final Attribuut attribuut1 = resultaat.getElementen().get(0).getAttribuut();
        final Attribuut attribuut2 = resultaat.getElementen().get(1).getAttribuut();

        assertThat(ouderlijkGezagAttributen, hasItem(attribuut1));
        assertThat(ouderlijkGezagAttributen, hasItem(attribuut2));
        assertEquals(2, resultaat.getElementen().size());
    }

    @Test
    public void testOuderHeeftGezagViaKind() {
        final String testExpressie =
                "PLATTE_LIJST(MAP(OUDERS(), o, MAP(o.betrokkenheden, ob, $ob.ouderlijk_gezag.ouder_heeft_gezag)))";
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();
        final List<Attribuut> ouderlijkGezagAttributen = new ArrayList<>();
        for (final BetrokkenheidHisVolledig kindBetrokkenheid : testPersoon.getBetrokkenheden()) {
            if (kindBetrokkenheid instanceof KindHisVolledig) {
                for (final BetrokkenheidHisVolledig ouderBetrokkenheid : kindBetrokkenheid.getRelatie().getBetrokkenheden()) {
                    if (ouderBetrokkenheid instanceof OuderHisVolledig) {
                        final MaterieleHistorieSet<HisOuderOuderlijkGezagModel> ouderlijkgezagHistorie =
                                ((OuderHisVolledig) ouderBetrokkenheid).getOuderOuderlijkGezagHistorie();

                        if (ouderlijkgezagHistorie != null && !ouderlijkgezagHistorie.isLeeg()) {
                            ouderlijkGezagAttributen.add(
                                    ouderlijkgezagHistorie.getActueleRecord().getIndicatieOuderHeeftGezag());
                        }
                    }
                }
            }
        }

        final LijstExpressie resultaat = (LijstExpressie) BRPExpressies.evalueer(testExpressie, testPersoon);
        assertFalse(resultaat.isFout());

        final Attribuut attribuut1 = resultaat.getElementen().get(0).getAttribuut();
        final Attribuut attribuut2 = resultaat.getElementen().get(1).getAttribuut();

        assertThat(ouderlijkGezagAttributen, hasItem(attribuut1));
        assertThat(ouderlijkGezagAttributen, hasItem(attribuut2));
        assertEquals(2, resultaat.getElementen().size());
    }

    @Test
    public void testGeslachtsnaamComponentHistorie() {
        final PersoonHisVolledig testPersoon = maakPersoonHisVolledigView();

        final Expressie resultaat = BRPExpressies.evalueer("RMAP(geslachtsnaamcomponenten, v, $v.stam)", testPersoon);

        //Er zijn 3 elementen over de historie, eerst Jordan, toen wijziging naar Jordaan
        assertEquals(3, testPersoon.getGeslachtsnaamcomponenten().iterator().next().getPersoonGeslachtsnaamcomponentHistorie().getAantal());
        assertFalse(resultaat.isFout());

        final Expressie lijst = resultaat.getElement(0).getElement(0);
        final String element1 = (String) lijst.getElement(0).getAttribuut().getWaarde();
        final String element2 = (String) lijst.getElement(1).getAttribuut().getWaarde();
        final String element3 = (String) lijst.getElement(2).getAttribuut().getWaarde();
        final List<String> geslachtsnaamcomponenten = Arrays.asList(element1, element2, element3);

        assertTrue(geslachtsnaamcomponenten.contains(JORDAN));
        assertTrue(geslachtsnaamcomponenten.contains(JORDAAN));

        int aantalJordan = 0;
        int aantalJordaan = 0;
        for (final String geslachtsnaamComponent : geslachtsnaamcomponenten) {
            if (JORDAN.equals(geslachtsnaamComponent)) {
                aantalJordan++;
            } else if (JORDAAN.equals(geslachtsnaamComponent)) {
                aantalJordaan++;
            }
        }
        assertEquals(2, aantalJordan);
        assertEquals(1, aantalJordaan);

    }

    @Test
    public void testVoornaamHistorie() {
        final PersoonHisVolledig testPersoon = maakPersoonHisVolledigViewMetTruePredikaat();

        final Expressie resultaat = BRPExpressies.evalueer("RMAP(voornamen, v, $v.naam)", testPersoon);

        final List<Expressie> lijst = new ArrayList<>();
        final Iterator<? extends Expressie> iterator = resultaat.getElementen().iterator();
        while (iterator.hasNext()) {
            final Expressie next = iterator.next();
            lijst.add(next.getElement(0).getElement(0));
        }

        final Expressie element0 = lijst.get(0);
        final Expressie element1 = lijst.get(1);

        assertTrue(element0 instanceof BrpAttribuutReferentieExpressie);
        assertTrue(element1 instanceof BrpAttribuutReferentieExpressie);

        final BrpAttribuutReferentieExpressie attribuutReferentie0 = (BrpAttribuutReferentieExpressie) element0;
        final BrpAttribuutReferentieExpressie attribuutReferentie1 = (BrpAttribuutReferentieExpressie) element1;

        //Ik verwacht hier Johnny en Donny, niet 2 keer hetzelfde attribuut
        assertTrue(attribuutReferentie0.getAttribuut() != attribuutReferentie1.getAttribuut());
    }

    @Test
    public void testKinderenSoortCode() {
        final PersoonHisVolledig testPersoon = maakPersoonHisVolledigViewMetTruePredikaat();
        final SoortPersoonAttribuut expected =
                ((PersoonHisVolledigView) testPersoon).getOuderBetrokkenheden().iterator().next().getRelatie()
                        .getBetrokkenheden().iterator().next().getPersoon().getSoort();

        final Expressie resultaat =
                BRPExpressies.evalueer("RMAP(KINDEREN(), k, $k.soort)", testPersoon);

        final Attribuut attribuut = resultaat.getElement(0).getElement(0).getAttribuut();

        assertEquals(expected, attribuut);
    }

    private PersoonHisVolledig maakPersoonHisVolledigViewMetTruePredikaat() {
        return new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(),
                new Predicate() {
                    @Override
                    public boolean evaluate(final Object o) {
                        return true;
                    }
                });
    }

    private void evalueerTestPersonenTegenAlleExpressies1Voor1(final List<PersoonHisVolledig> testPersonen) {
        for (final PersoonHisVolledig testPersoon : testPersonen) {
            for (final String expressie : ExpressieMap.getMap().values()) {
                final Expressie expressieResultaat = BRPExpressies.evalueer(expressie, testPersoon);

                assertFalse(FOUT_BIJ_EVALUEREN_EXPRESSIE + expressie + FOUT
                        + expressieResultaat.alsString(),
                        expressieResultaat.isFout());
            }
        }
    }

    private void evalueerTestPersonenTegenAlleExpressiesIn1Keer(final List<PersoonHisVolledig> testPersonen) {
        for (final PersoonHisVolledig testPersoon : testPersonen) {
            final String totaleExpressie = maak1ExpressieVanLijstVanExpressies();
            final Expressie expressieResultaat = BRPExpressies.evalueer(totaleExpressie, testPersoon);

            assertFalse(FOUT_BIJ_EVALUEREN_EXPRESSIE + totaleExpressie + FOUT
                    + expressieResultaat.alsString(),
                    expressieResultaat.isFout());
        }
    }

    private String maak1ExpressieVanLijstVanExpressies() {
        final StringBuilder totaleExpressie = new StringBuilder("{");
        for (final String expressieTekst : ExpressieMap.getMap().values()) {
            totaleExpressie.append(expressieTekst);
            totaleExpressie.append(",");
        }
        totaleExpressie.deleteCharAt(totaleExpressie.length() - 1);
        totaleExpressie.append("}");
        return totaleExpressie.toString();
    }

}
