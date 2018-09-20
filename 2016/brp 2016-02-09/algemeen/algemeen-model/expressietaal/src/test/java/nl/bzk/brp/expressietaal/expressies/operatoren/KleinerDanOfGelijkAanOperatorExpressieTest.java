/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.ONBEKEND;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.ONWAAR;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.WAAR;

import java.util.Arrays;
import nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class KleinerDanOfGelijkAanOperatorExpressieTest {

    private static final String OPERATOR = "<=";

    private final String operandLinks;
    private final String operandRechts;
    private final String resultaat;
    private final String inverseResultaat;

    public KleinerDanOfGelijkAanOperatorExpressieTest(final String op1, final String op2, final String resultaat, final String inverseResultaat) {
        operandLinks = op1;
        operandRechts = op2;
        this.resultaat = resultaat;
        this.inverseResultaat = inverseResultaat;
    }

    @Parameterized.Parameters(name = "{index}: {0} <= {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
            // bekende datum
            { "2014/FEB/01", "2014/FEB/01", WAAR, WAAR },
            { "2014/FEB/01", "2014/JAN/31", ONWAAR, WAAR },
            { "2014/FEB/01", "2014/FEB/10", WAAR, ONWAAR },

            // bekende datum met datumtijd
            { "2014/FEB/01", "2014/FEB/01/12/00/00", WAAR, WAAR },
            { "2014/FEB/01", "2014/JAN/31/12/00/00", ONWAAR, WAAR },
            { "2014/FEB/01", "2014/FEB/10/12/00/00", WAAR, ONWAAR },

            // onbekende dag vs. bekend
            { "2014/FEB/?", "2014/FEB/01", ONBEKEND, WAAR },
            { "2014/SEP/?", "2014/SEP/19", ONBEKEND, ONBEKEND },
            { "2014/FEB/?", "2014/JAN/31", ONWAAR, WAAR },
            { "2014/FEB/?", "2014/MRT/01", WAAR, ONWAAR },

            // onbekende dag vs. onbekende dag
            { "2014/FEB/?", "2014/FEB/?", ONBEKEND, ONBEKEND },
            { "2014/FEB/?", "2014/JAN/?", ONWAAR, WAAR },
            { "2014/FEB/?", "2014/MRT/?", WAAR, ONWAAR },

            // onbekende maand/dag vs. bekend
            { "2014/?/?", "2014/FEB/01", ONBEKEND, ONBEKEND },
            { "2014/?/?", "2014/SEP/19", ONBEKEND, ONBEKEND },
            { "2014/?/?", "2015/JAN/01", WAAR, ONWAAR },
            { "2014/?/?", "2013/DEC/31", ONWAAR, WAAR },

            // onbekende maand/dag vs. onbekende dag
            { "2014/?/?", "2014/FEB/?", ONBEKEND, ONBEKEND },
            { "2014/?/?", "2015/JAN/?", WAAR, ONWAAR },
            { "2014/?/?", "2013/DEC/?", ONWAAR, WAAR },

            // onbekende maand/dag vs. onbekende maand/dag
            { "2014/?/?", "2014/?/?", ONBEKEND, ONBEKEND },
            { "2014/?/?", "2013/?/?", ONWAAR, WAAR },
            { "2014/?/?", "2015/?/?", WAAR, ONWAAR },

            // onbekende datum
            { "?/?/?", "2014/JAN/01", ONBEKEND, ONBEKEND },
            { "?/?/?", "2014/JAN/?", ONBEKEND, ONBEKEND },
            { "?/?/?", "2014/?/?", ONBEKEND, ONBEKEND },
            { "?/?/?", "?/?/?", ONBEKEND, ONBEKEND },

            // met null
            { "NULL", "2014/FEB/01", ONBEKEND, ONBEKEND },
            { "NULL", "2014/FEB/?", ONBEKEND, ONBEKEND },
            { "NULL", "2014/?/?", ONBEKEND, ONBEKEND },
            { "NULL", "?/?/?", ONBEKEND, ONBEKEND },

            // grens gevallen ivm mathematische logica
            { "2014/?/?", "2014/JAN/01", ONBEKEND, WAAR },
            { "2014/?/?", "2014/DEC/31", ONBEKEND, ONBEKEND },

            { "2014/FEB/?", "2014/FEB/28", ONBEKEND, ONBEKEND },
            { "2012/FEB/?", "2012/FEB/28", ONBEKEND, ONBEKEND },
            { "2012/FEB/?", "2012/FEB/29", ONBEKEND, ONBEKEND },

            // praktijk gevallen
            { "1997/11/?", "1999/09/01", WAAR, ONWAAR },
            { "1997/11/?", "1999/11/01", WAAR, ONWAAR },
            { "1997/11/?", "1999/12/01", WAAR, ONWAAR },
        });
    }

    @Test
    public void testStandaardExpressie() {
        final String expressie = String.format("%1$s %3$s %2$s", operandLinks, operandRechts, OPERATOR);
        final String verwachtResultaat = resultaat;

        ExpressieEvaluator.testEvaluatie(expressie, verwachtResultaat);
    }

    @Test
    public void testOperandsOmgedraaid() {
        final String expressie = String.format("%1$s %3$s %2$s", operandRechts, operandLinks, OPERATOR);
        final String verwachtResultaat = inverseResultaat;

        ExpressieEvaluator.testEvaluatie(expressie, verwachtResultaat);
    }

    @Test
    public void testBooleanCombinatieVanKleinerDanEnGelijk() {
        final String expressie = String.format("(%1$s < %2$s) OF (%1$s = %2$s)", operandLinks, operandRechts);
        final String verwachtResultaat = resultaat;

        ExpressieEvaluator.testEvaluatie(expressie, verwachtResultaat);
    }

    @Test
    public void testBooleanCombinatieVanKleinerDanOfGelijkEnGroterDan() {
        Assume.assumeFalse(ONBEKEND.equals(resultaat));

        final String expressie = String.format("(%1$s <= %2$s) <> (%1$s > %2$s)", operandLinks, operandRechts);

        ExpressieEvaluator.testEvaluatie(expressie, "WAAR");
    }

    @Test
    public void testOmgedraaidBooleanCombinatieVanKleinerDanOfGelijkEnGroterDan() {
        Assume.assumeFalse(ONBEKEND.equals(inverseResultaat));

        final String expressie = String.format("(%1$s <= %2$s) <> (%1$s > %2$s)", operandRechts, operandLinks);

        ExpressieEvaluator.testEvaluatie(expressie, "WAAR");
    }
}
