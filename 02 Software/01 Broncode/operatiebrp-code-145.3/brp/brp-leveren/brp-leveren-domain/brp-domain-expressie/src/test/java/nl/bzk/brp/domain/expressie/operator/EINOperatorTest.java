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
public class EINOperatorTest {

    private final String expressie;
    private final String resultaat;

    public EINOperatorTest(final String expressie, final String resultaat) {
        this.expressie = expressie;
        this.resultaat = resultaat;
    }

    @Parameterized.Parameters(name = "{index}: ({0}) = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{

                // test lege waarden
                {"{} EIN {}", WAAR},
                {"{} EIN {1}", ONWAAR},
                {"{1} EIN {}", ONWAAR},

                // test NULL waarden
                {"{} EIN {NULL}", ONWAAR},
                {"{NULL} EIN {NULL}", WAAR},
                {"{NULL} EIN {1}", ONWAAR},
                {"{1} EIN {NULL}", ONWAAR},

                // GETAL
                {"{1} EIN {1}", WAAR},
                {"{1} EIN {1, 2}", WAAR},
                {"{1,2} EIN {1,2}", WAAR},
                {"{1,2} EIN {2,1}", WAAR},
                {"{1,1,1,1} EIN {1,1}", WAAR},
                {"{1,1,1,1} EIN {1,2}", WAAR},
                {"{1,2,3} EIN {1,2}", WAAR},

                // STRING
                {"{\"a\", \"b\"} EIN {\"a\", \"b\"}", WAAR},
                {"{\"a\", \"a\",\"a\"} EIN {\"a\", \"a\"}", WAAR},
                {"{\"a\"} EIN {\"b\"}", ONWAAR},
                {"{\"a\", \"b\",\"c\"} EIN {\"a\", \"b\"}", WAAR},

                // DATUM
                {"{2014/FEB/01} EIN {2014/FEB/01}", WAAR},
                {"{2014/FEB/01} EIN {2014/FEB/01}", WAAR},
                {"{2014/FEB/01} EIN {2014/FEB/01/12/00/00}", WAAR},
                {"{2014/FEB/01} EIN {2014/FEB/01, 2014/FEB/01}", WAAR},
                {"{2014/FEB/01,2015/FEB/01, 2016/FEB/01} EIN {2014/FEB/01,2015/FEB/01, 2016/FEB/01}", WAAR},
                {"{} EIN {2014/MRT/01}", ONWAAR},
                {"{2014/FEB/01} EIN {2014/MRT/01}", ONWAAR},

                // BOOLEAN
                {"{WAAR} EIN {WAAR}", WAAR},
                {"{WAAR,WAAR,WAAR} EIN {WAAR}", WAAR},
                {"{ONWAAR} EIN {ONWAAR}", WAAR},
                {"{WAAR,ONWAAR} EIN {WAAR,ONWAAR}", WAAR},
                {"{ONWAAR} EIN {WAAR}", ONWAAR},
                {"{} EIN {WAAR}", ONWAAR},

                //test de eval op de linker expressie
                {"Persoon.Identificatienummers.Burgerservicenummer EIN {\"987654321\"}", WAAR},
                {"Persoon.Identificatienummers.Burgerservicenummer EIN Persoon.Identificatienummers.Burgerservicenummer", WAAR},
                {"Persoon.Identificatienummers.Burgerservicenummer EIN {\"456\"}", ONWAAR},
        });
    }

    @Test
    public void testVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie(expressie, resultaat, ExpressietaalTestPersoon.PERSOONSLIJST);
    }
}
