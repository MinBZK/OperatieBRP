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
public class AINOperatorTest {

    private final String expressie;
    private final String resultaat;

    public AINOperatorTest(final String expressie, final String resultaat) {
        this.expressie = expressie;
        this.resultaat = resultaat;
    }

    @Parameterized.Parameters(name = "{index}: ({0}) = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{

                // test lege waarden
                {"{} AIN {}", WAAR},
                {"{} AIN {1}", ONWAAR},
                {"{1} AIN {}", ONWAAR},

                // null waarden
                {"{NULL} AIN {1}", ONWAAR},
                {"{1} AIN {NULL}", ONWAAR},
                {"{NULL} AIN {NULL}", WAAR},

                // GETAL
                {"{1} AIN {1}", WAAR},
                {"{1} AIN {1,2}", WAAR},
                {"{1,2} AIN {1,2}", WAAR},
                {"{1,2} AIN {1,2,3}", WAAR},
                {"{1,1} AIN {1,1,1,1}", WAAR},
                {"{1} AIN {2}", ONWAAR},
                {"{1,2} AIN {1}", ONWAAR},

                // STRING
                {"{\"a\", \"b\"} AIN {\"a\", \"b\"}", WAAR},
                {"{\"b\", \"a\"} AIN {\"b\", \"a\"}", WAAR},
                {"{\"a\", \"a\",\"a\"} AIN {\"a\", \"a\"}", WAAR},
                {"{\"a\"} AIN {\"b\"}", ONWAAR},
                {"{\"a\", \"b\",\"c\"} AIN {\"a\", \"b\"}", ONWAAR},
                {"{} AIN {\"a\"}", ONWAAR},

                // DATUM
                {"{2014/FEB/01} AIN {2014/FEB/01}", WAAR},
                {"{2014/FEB/01} AIN {2014/FEB/01}", WAAR},
                {"{2014/FEB/01} AIN {2014/FEB/01/12/00/00}", WAAR},
                {"{2014/FEB/01} AIN {2014/FEB/01, 2014/FEB/01}", WAAR},
                {"{2014/FEB/01, 2015/FEB/01, 2016/FEB/01} AIN {2014/FEB/01, 2015/FEB/01, 2016/FEB/01}", WAAR},
                {"{2014/FEB/01} AIN {2014/MRT/01}", ONWAAR},

                // BOOLEAN
                {"{WAAR} AIN {WAAR}", WAAR},
                {"{ONWAAR} AIN {ONWAAR}", WAAR},
                {"{WAAR,ONWAAR} AIN {WAAR,ONWAAR}", WAAR},
                {"{WAAR,ONWAAR} AIN {ONWAAR,WAAR}", WAAR},
                {"{ONWAAR} AIN {WAAR}", ONWAAR},

                //test de eval op de linker expressie
                {"Persoon.Identificatienummers.Burgerservicenummer AIN {\"987654321\"}", WAAR},
                {"Persoon.Identificatienummers.Burgerservicenummer AIN Persoon.Identificatienummers.Burgerservicenummer", WAAR},
        });
    }

    @Test
    public void testVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie(expressie, resultaat, ExpressietaalTestPersoon.PERSOONSLIJST);

    }
}
