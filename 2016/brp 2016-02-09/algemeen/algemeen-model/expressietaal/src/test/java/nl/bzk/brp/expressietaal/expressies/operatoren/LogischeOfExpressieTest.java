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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LogischeOfExpressieTest {

    private static final String OPERATOR = "OF";

    private final String operandLinks;
    private final String operandRechts;
    private final String resultaat;
    private final String inverseResultaat;

    public LogischeOfExpressieTest(final String op1, final String op2, final String resultaat, final String inverseResultaat) {
        operandLinks = op1;
        operandRechts = op2;
        this.resultaat = resultaat;
        this.inverseResultaat = inverseResultaat;
    }

    @Parameterized.Parameters(name = "{index}: {0} OF {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
            { "WAAR", "WAAR", WAAR, WAAR },
            { "WAAR", "ONWAAR", WAAR, WAAR },
            { "ONWAAR", "ONWAAR", ONWAAR, ONWAAR },

            // onbekend
            { "WAAR", "NULL", WAAR, WAAR },
            { "ONWAAR", "NULL", ONBEKEND, ONBEKEND },
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
}
