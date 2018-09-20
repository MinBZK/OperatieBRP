/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public final class OnbekendeGeboortedatumExpressieTest {

    private static final String GEBOORTE_DATUM_VANAF_NIET_GREEDY = "geboorte.datum < 1984/07/15";
    private static final String GEBOORTE_DATUM_VANAF_WEL_GREEDY  = "IS_NULL(persoon.geboorte.datum < 1984/07/15) OF geboorte.datum < 1984/07/15";

    private static final int GEBOORTE_DATUM_DAG_ONBEKEND          = 19840700;
    private static final int GEBOORTE_DATUM_MAAND_EN_DAG_ONBEKEND = 19840000;
    private static final int GEBOORTE_DATUM_VOLLEDIG_ONBEKEND     = 00000000;

    private PersoonHisVolledigImpl testPersoonVolledig = TestPersoonJohnnyJordaan.maak();

    @Test
    public void testDagOnbekendNietGreedy() {
        final PersoonView testPersoon = geefTestPersoonMetGeboortedatum(testPersoonVolledig, GEBOORTE_DATUM_DAG_ONBEKEND);

        final Expressie expressieResultaat = BRPExpressies
            .evalueer(GEBOORTE_DATUM_VANAF_NIET_GREEDY, testPersoon);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testDagOnbekendWelGreedy() {
        final PersoonView testPersoon = geefTestPersoonMetGeboortedatum(testPersoonVolledig, GEBOORTE_DATUM_DAG_ONBEKEND);

        final Expressie expressieResultaat = BRPExpressies
            .evalueer(GEBOORTE_DATUM_VANAF_WEL_GREEDY, testPersoon);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.alsBoolean());
    }

    @Test
    public void testDagOnbekendNietGreedyMaandEerder() {
        final PersoonView testPersoon = geefTestPersoonMetGeboortedatum(testPersoonVolledig, GEBOORTE_DATUM_DAG_ONBEKEND - 100);

        final Expressie expressieResultaat = BRPExpressies
            .evalueer(GEBOORTE_DATUM_VANAF_NIET_GREEDY, testPersoon);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.alsBoolean());
    }

    @Test
    public void testMaandOnbekendNietGreedy() {
        final PersoonView testPersoon = geefTestPersoonMetGeboortedatum(testPersoonVolledig, GEBOORTE_DATUM_MAAND_EN_DAG_ONBEKEND);

        final Expressie expressieResultaat = BRPExpressies
            .evalueer(GEBOORTE_DATUM_VANAF_NIET_GREEDY, testPersoon);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.isNull());
    }

    @Test
    public void testMaandOnbekendNietGreedyJaarEerder() {
        final PersoonView testPersoon = geefTestPersoonMetGeboortedatum(testPersoonVolledig, GEBOORTE_DATUM_MAAND_EN_DAG_ONBEKEND - 10000);

        final Expressie expressieResultaat = BRPExpressies
            .evalueer(GEBOORTE_DATUM_VANAF_NIET_GREEDY, testPersoon);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.alsBoolean());
    }

    @Test
    public void testMaandOnbekendWelGreedy() {
        final PersoonView testPersoon = geefTestPersoonMetGeboortedatum(testPersoonVolledig, GEBOORTE_DATUM_MAAND_EN_DAG_ONBEKEND);

        final Expressie expressieResultaat = BRPExpressies
            .evalueer(GEBOORTE_DATUM_VANAF_WEL_GREEDY, testPersoon);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.alsBoolean());
    }

    @Test
    public void testVolledigOnbekendNietGreedy() {
        final PersoonView testPersoon = geefTestPersoonMetGeboortedatum(testPersoonVolledig, GEBOORTE_DATUM_VOLLEDIG_ONBEKEND);

        final Expressie expressieResultaat = BRPExpressies
            .evalueer(GEBOORTE_DATUM_VANAF_NIET_GREEDY, testPersoon);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.isNull());
    }

    @Test
    public void testVolledigOnbekendWelGreedy() {
        final PersoonView testPersoon = geefTestPersoonMetGeboortedatum(testPersoonVolledig, GEBOORTE_DATUM_VOLLEDIG_ONBEKEND);

        final Expressie expressieResultaat = BRPExpressies
            .evalueer(GEBOORTE_DATUM_VANAF_WEL_GREEDY, testPersoon);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.alsBoolean());
    }

    private PersoonView geefTestPersoonMetGeboortedatum(final PersoonHisVolledigImpl persoonVolledigZonderOuderPersonen, final int deelsOnbekendeDatum) {
        ReflectionTestUtils.setField(persoonVolledigZonderOuderPersonen.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(), "waarde",
            deelsOnbekendeDatum);

        return new PersoonView(persoonVolledigZonderOuderPersonen);
    }

}
