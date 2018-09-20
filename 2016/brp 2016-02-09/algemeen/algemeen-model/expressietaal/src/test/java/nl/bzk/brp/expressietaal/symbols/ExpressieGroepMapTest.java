/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.TestPersonen;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;

public class ExpressieGroepMapTest {

    private static final String FOUTMELDING_OP_PARSEN_EXPRESSIE = "Foutmelding op parsen expressie: ";
    private static final String MELDING = " Melding: ";
    private static final String FOUT_BIJ_EVALUEREN_EXPRESSIE = "Fout bij evalueren expressie: ";
    private static final String FOUT = " Fout: ";

    @Test
    public void testAlleExpressiesInVerantwoordingMap() {
        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepVerantwoordingMap().values()) {
            for (final String expressie : expressieLijst) {
                final ParserResultaat geparsdeExpressie = BRPExpressies.parse(expressie);
                assertTrue(FOUTMELDING_OP_PARSEN_EXPRESSIE + expressie + MELDING
                        + geparsdeExpressie.getFoutmelding(),
                        geparsdeExpressie.succes());
            }
        }
    }

    @Test
    public void testAlleExpressiesInFormeleHistorieMap() {
        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepFormeleHistorieMap().values()) {
            for (final String expressie : expressieLijst) {
                final ParserResultaat geparsdeExpressie = BRPExpressies.parse(expressie);
                assertTrue(FOUTMELDING_OP_PARSEN_EXPRESSIE + expressie + MELDING
                        + geparsdeExpressie.getFoutmelding(),
                        geparsdeExpressie.succes());
            }
        }
    }

    @Test
    public void testAlleExpressiesInMaterieleHistorieMap() {
        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepMaterieleHistorieMap().values()) {
            for (final String expressie : expressieLijst) {
                final ParserResultaat geparsdeExpressie = BRPExpressies.parse(expressie);
                assertTrue(FOUTMELDING_OP_PARSEN_EXPRESSIE + expressie + MELDING
                        + geparsdeExpressie.getFoutmelding(),
                        geparsdeExpressie.succes());
            }
        }
    }

    @Test
    public void testAlleExpressiesInVerantwoordingMapEvalueren() {
        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepVerantwoordingMap().values()) {
            for (final String expressie : expressieLijst) {
                final Expressie expressieResultaat = BRPExpressies.evalueer(expressie, maakPersoonHisVolledigView());
                assertFalse(FOUT_BIJ_EVALUEREN_EXPRESSIE + expressie + FOUT + expressieResultaat.alsString(),
                        expressieResultaat.isFout());
            }
        }
    }

    @Test
    public void testAlleExpressiesInFormeleHistorieMapEvalueren() {
        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepFormeleHistorieMap().values()) {
            for (final String expressie : expressieLijst) {
                final Expressie expressieResultaat = BRPExpressies.evalueer(expressie, maakPersoonHisVolledigView());
                assertFalse(FOUT_BIJ_EVALUEREN_EXPRESSIE + expressie + FOUT + expressieResultaat.alsString(),
                        expressieResultaat.isFout());
            }
        }
    }

    @Test
    public void testAlleExpressiesInMaterieleHistorieMapEvalueren() {
        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepMaterieleHistorieMap().values()) {
            for (final String expressie : expressieLijst) {
                final Expressie expressieResultaat = BRPExpressies.evalueer(expressie, maakPersoonHisVolledigView());
                assertFalse(FOUT_BIJ_EVALUEREN_EXPRESSIE + expressie + FOUT + expressieResultaat.alsString(),
                        expressieResultaat.isFout());
            }
        }
    }

    private PersoonHisVolledig maakPersoonHisVolledigView() {
        return new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(),
                HistorieVanafPredikaat.geldigOpEnNa(
                    new DatumAttribuut(20000101)));
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
    public void testExpressieAttributenActiesOphalenMetGroepMap() {
        final List<String> uitTeVoerenExpressies =
                ExpressieGroepMap.getGroepVerantwoordingMap().get(ElementEnum.PERSOON_GEBOORTE);

        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final List<Attribuut> geraakteAttributen = new ArrayList<>();
        for (final String expressieString : uitTeVoerenExpressies) {
            final Expressie resultaat =
                    BRPExpressies.evalueer(expressieString, persoon);

            assertNotNull(resultaat);
            assertFalse(resultaat.isFout());
            final Attribuut attribuut = resultaat.getElement(0).getAttribuut();
            if (attribuut != null) {
                geraakteAttributen.add(attribuut);
            }
        }

        assertEquals(3, geraakteAttributen.size());

        final ActieModel verantwoordingInhoud = persoon.getPersoonGeboorteHistorie().getActueleRecord()
                .getVerantwoordingInhoud();
        assertTrue(verantwoordingInhoud.getSoort() == geraakteAttributen.get(0));
        assertTrue(verantwoordingInhoud.getPartij() == geraakteAttributen.get(1));
        assertTrue(verantwoordingInhoud.getTijdstipRegistratie() == geraakteAttributen.get(2));
    }

    private List<String> getAlleExpressiesIn1Lijst() {
        final List<String> resultaat = new ArrayList<>();

        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepMaterieleHistorieMap().values()) {
            resultaat.addAll(expressieLijst);
        }
        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepFormeleHistorieMap().values()) {
            resultaat.addAll(expressieLijst);
        }
        for (final List<String> expressieLijst : ExpressieGroepMap.getGroepVerantwoordingMap().values()) {
            resultaat.addAll(expressieLijst);
        }

        return resultaat;
    }

    private void evalueerTestPersonenTegenAlleExpressies1Voor1(final List<PersoonHisVolledig> testPersonen) {
        for (final PersoonHisVolledig testPersoon : testPersonen) {
            for (final String expressie : getAlleExpressiesIn1Lijst()) {
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
        for (final String expressieTekst : getAlleExpressiesIn1Lijst()) {
            totaleExpressie.append(expressieTekst);
            totaleExpressie.append(",");
        }
        totaleExpressie.deleteCharAt(totaleExpressie.length() - 1);
        totaleExpressie.append("}");
        return totaleExpressie.toString();
    }

}
