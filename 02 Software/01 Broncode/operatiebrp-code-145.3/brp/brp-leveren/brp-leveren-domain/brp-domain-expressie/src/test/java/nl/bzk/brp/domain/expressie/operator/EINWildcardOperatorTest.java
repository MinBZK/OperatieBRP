/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import java.util.Arrays;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 */
@RunWith(Parameterized.class)
public class EINWildcardOperatorTest {

    private final String expressie;
    private final String resultaat;

    public EINWildcardOperatorTest(final String expressie, final String resultaat) {
        this.expressie = expressie;
        this.resultaat = resultaat;
    }

    @Parameterized.Parameters(name = "{index}: ({0}) = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{

                // test lege waarden
                {"{} EIN%{\"2346*\"}", ONWAAR},

                // STRING WILDCARD
                {"{\"2345XD\"} EIN%{\"2345*\"}", WAAR},
                {"{\"2345XD\"} EIN%{\"2345??\"}", WAAR},
                {"{\"2345AA\", \"2345BB\"} EIN%{\"2345*\"}", WAAR},
                {"{\"2345AA\", \"2345BB\"} EIN%{\"2345A*\", \"2345B*\"}", WAAR},
                {"{\"2345AA\", \"2345BB\"} EIN%{\"2345A?\", \"2345B?\"}", WAAR},
                {"{\"2345AA\", \"2345AB\"} EIN%{\"2345A*\", \"2345C*\"}", WAAR},

                {"{\"2345XD\"} EIN%{\"2346*\"}", ONWAAR},
                {"{\"2345AA\", \"2345BB\"} EIN%{\"2345A*\", \"2345C*\"}", WAAR},

                // DATUM WILDCARD
                {"{2014/?/?} EIN%{2014/?/?}", WAAR},
                {"{2014/JAN/01} EIN%{2014/?/?}", WAAR},
                {"{2014/JAN/01, 2014/FEB/01} EIN%{2014/?/?}", WAAR},
                {"{2014/JAN/01} EIN%{2014/JAN/?}", WAAR},
                {"{2014/JAN/01,2013/JAN/01} EIN%{2014/?/?,2013/?/?}", WAAR},
                {"{2014/JAN/01} EIN%{2014/?/?,2013/?/?}", WAAR},
                {"{2013/?/?} EIN%{2014/?/?}", ONWAAR},

                // GETAL WILDCARD
                {"{5} EIN%{5}", WAAR},
                {"{5} EIN%{10}", ONWAAR},

                // BOOLEAN WILDCARD
                {"{WAAR} EIN%{WAAR}", WAAR},
                {"{WAAR} EIN%{ONWAAR}", ONWAAR},

                //test de eval op de linker expressie
                {"Persoon.Geboorte.BuitenlandsePlaats EIN% {\"Jak*\"}", WAAR},
                {"{\"Jak*\"} EIN% Persoon.Geboorte.BuitenlandsePlaats", ONWAAR},
        });
    }

    @Test
    public void testVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie(expressie, resultaat, ExpressietaalTestPersoon.PERSOONSLIJST);
    }
}
