/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONBEKEND;
import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import java.util.Arrays;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DatumVergelijkTest {

    private final String operandLinks;
    private final String operandRechts;
    private final String resultaatGelijk;
    private final String kleiner;
    private final String kleinerGelijk;
    private final String groter;
    private final String groterGelijk;

    public DatumVergelijkTest(final String op1, final String op2, final String resultaatGelijk,
                              final String kleiner, final String kleinerGelijk, final String groter, final String groterGelijk) {
        operandLinks = op1;
        operandRechts = op2;
        this.resultaatGelijk = resultaatGelijk;
        this.kleiner = kleiner;
        this.kleinerGelijk = kleinerGelijk;
        this.groter = groter;
        this.groterGelijk = groterGelijk;
    }


    @Parameterized.Parameters(name = "{index}: {0} vergelijk {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // bekende datum
                {"2014/FEB/01", "2014/FEB/01", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/FEB/01", "2014/JAN/31", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/FEB/01", "2014/FEB/10", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},

                // onbekende dag vs. bekend
                {"2014/FEB/0", "2014/FEB/01", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/FEB/?", "2014/FEB/01", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/SEP/0", "2014/SEP/19", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/SEP/?", "2014/SEP/19", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/FEB/0", "2014/JAN/31", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/FEB/?", "2014/JAN/31", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/FEB/0", "2014/MRT/01", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/FEB/?", "2014/MRT/01", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},

                // onbekende dag vs. onbekende dag
                {"2014/FEB/0", "2014/FEB/0", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/FEB/?", "2014/FEB/?", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/FEB/0", "2014/JAN/0", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/FEB/?", "2014/JAN/?", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/FEB/0", "2014/MRT/0", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/FEB/?", "2014/MRT/?", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},

                // onbekende maand/dag vs. bekend
                {"2014/0/0", "2014/JAN/01", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/?/?", "2014/JAN/01", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/0/0", "2014/SEP/19", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/?/?", "2014/SEP/19", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/0/0", "2015/JAN/01", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/?/?", "2015/JAN/01", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/0/0", "2013/DEC/31", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/?/?", "2013/DEC/31", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},

                // onbekende maand/dag vs. onbekende dag
                {"2014/0/0", "2014/JAN/0", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/?/?", "2014/JAN/?", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/0/0", "2015/JAN/0", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/?/?", "2015/JAN/?", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/0/0", "2013/DEC/0", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/?/?", "2013/DEC/?", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},

                // onbekende maand/dag vs. onbekende maand/dag
                {"2014/0/0", "2014/0/0", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/?/?", "2014/?/?", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/0/0", "2013/0/0", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/?/?", "2013/?/?", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/0/0", "2015/0/0", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/?/?", "2015/?/?", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},

                {"2014/10/05", "?/?/10", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/10/05", "2013/?/10", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},

                // onbekende datum
                {"0/0/0", "2014/JAN/01", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"?/?/?", "2014/JAN/01", WAAR, WAAR, WAAR, WAAR, WAAR},
                {"0/0/0", "2014/JAN/0", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"?/?/?", "2014/JAN/?", WAAR, WAAR, WAAR, WAAR, WAAR},
                {"0/0/0", "2014/0/0", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"?/?/?", "2014/?/?", WAAR, WAAR, WAAR, WAAR, WAAR},
                {"0/0/0", "0/0/0", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"?/?/?", "?/?/?", WAAR, WAAR, WAAR, WAAR, WAAR},

                // met null
                {"NULL", "2014/FEB/01", ONWAAR, ONWAAR, ONWAAR, ONWAAR, ONWAAR},
                {"NULL", "2014/FEB/0", ONWAAR, ONWAAR, ONWAAR, ONWAAR, ONWAAR},
                {"NULL", "2014/0/0", ONWAAR, ONWAAR, ONWAAR, ONWAAR, ONWAAR},
                {"NULL", "0/0/0", ONWAAR, ONWAAR, ONWAAR, ONWAAR, ONWAAR},

                // bekende datum met datumtijd
                {"2014/FEB/01", "2014/FEB/01/12/00/00", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2014/FEB/01", "2014/JAN/31/12/00/00", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/FEB/01", "2014/FEB/10/12/00/00", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},

                //datum tijd
                {"2013/04/05/12/00/30", "2013/04/05/12/00/30", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2013/04/05/12/00/30", "2013/04/05/12/00/00", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2013/04/05/12/00/30", "2013/04/05/12/00/00", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2013/04/05", "2013/04/05/12/00/00", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2013/04/05", "2013/04/05/00/00/00", WAAR, ONWAAR, WAAR, ONWAAR, WAAR},
                {"2013/04/06", "2013/04/05/00/00/00", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/04/?", "2013/04/06/00/00/00", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"2014/04/?", "2015/04/06/00/00/00", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2014/?/?", "2015/04/06/00/00/00", ONWAAR, WAAR, WAAR, ONWAAR, ONWAAR},
                {"2018/?/?", "2015/04/06/00/00/00", ONWAAR, ONWAAR, ONWAAR, WAAR, WAAR},
                {"?/?/?", "2015/04/06/00/00/00", WAAR, WAAR, WAAR, WAAR, WAAR},

        });
    }


    @Test
    public void testGelijk() throws ExpressieException {
        final String expressie = String.format("%1$s %3$s %2$s", operandLinks, operandRechts, OperatorType.GELIJK);
        TestUtils.testEvaluatie(expressie, resultaatGelijk);
    }

    @Test
    public void testOngelijk() throws ExpressieException {
        final String expressie = String.format("%1$s %3$s %2$s", operandLinks, operandRechts, OperatorType.ONGELIJK);
        TestUtils.testEvaluatie(expressie, WAAR.equals(resultaatGelijk) ? ONWAAR : ONBEKEND.equals(resultaatGelijk) ? ONBEKEND : WAAR);
    }

    @Test
    public void testKleiner() throws ExpressieException {
        final String expressie = String.format("%1$s %3$s %2$s", operandLinks, operandRechts, OperatorType.KLEINER);
        TestUtils.testEvaluatie(expressie, kleiner);
    }

    @Test
    public void testKleinerOfGelijk() throws ExpressieException {
        final String expressie = String.format("%1$s %3$s %2$s", operandLinks, operandRechts, OperatorType.KLEINER_OF_GELIJK);
        TestUtils.testEvaluatie(expressie, kleinerGelijk);
    }

    @Test
    public void testGroter() throws ExpressieException {
        final String expressie = String.format("%1$s %3$s %2$s", operandLinks, operandRechts, OperatorType.GROTER);
        TestUtils.testEvaluatie(expressie, groter);
    }

    @Test
    public void testGroterOfGelijk() throws ExpressieException {
        final String expressie = String.format("%1$s %3$s %2$s", operandLinks, operandRechts, OperatorType.GROTER_OF_GELIJK);
        TestUtils.testEvaluatie(expressie, groterGelijk);
    }


}
