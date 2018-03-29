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
public class EOperatorTest {

    private final String expressie;
    private final String resultaat;

    public EOperatorTest(final String expressie, final String resultaat) {
        this.expressie = expressie;
        this.resultaat = resultaat;
    }

    @Parameterized.Parameters(name = "{index}: ({0}) = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{

                //null specials
                {"{1, NULL} E= NULL", WAAR},
                {"{NULL, 1} E= NULL", WAAR},
                {"{NULL, 1} E= 1", WAAR},
                {"{\"de\", NULL} E= {\"de\"}", WAAR},

                //E=
                {"{1} E= 1", WAAR},
                {"{1} E= 2", ONWAAR},
                {"{1,2,3} E= 1", WAAR},
                {"{1,2,3} E= 4", ONWAAR},
                {"{NULL} E= 0", ONWAAR},
                {"{NULL} E= NULL", WAAR},

                //E>
                {"{1} E> 0", WAAR},
                {"{1} E> 1", ONWAAR},
                {"{1,2,3} E> 1", WAAR},
                {"{1,2,3} E> 4", ONWAAR},
                {"{NULL} E> 0", ONWAAR},
                {"{NULL} E> NULL", ONWAAR},

                //E>=
                {"{1} E>= 1", WAAR},
                {"{1} E>= 2", ONWAAR},
                {"{1,2,3} E>= 1", WAAR},
                {"{1,2,3} E>= 4", ONWAAR},
                {"{NULL} E>= 0", ONWAAR},
                {"{NULL} E>= NULL", WAAR},

                //E<
                {"{1} E< 2", WAAR},
                {"{1} E< 1", ONWAAR},
                {"{1,2,3} E< 4", WAAR},
                {"{1,2,3} E< 1", ONWAAR},
                {"{NULL} E< 0", ONWAAR},
                {"{NULL} E< NULL", ONWAAR},

                //E<=
                {"{1} E<= 2", WAAR},
                {"{1} E<= 0", ONWAAR},
                {"{1,2,3} E<= 4", WAAR},
                {"{1,2,3} E<= 0", ONWAAR},
                {"{NULL} E<= 0", ONWAAR},
                {"{NULL} E<= NULL", WAAR},

                //E<>
                {"{1} E<> 2", WAAR},
                {"{1} E<> 1", ONWAAR},
                {"{1,2,3} E<> 0", WAAR},
                {"{4,4,4} E<> 4", ONWAAR},
                {"{10} E<> NULL", WAAR},
                {"{NULL} E<> NULL", ONWAAR},
                {"{NULL} E<> 5", WAAR},

                //E=%
                {"{1} E=% 1", WAAR},
                {"{1} E=% 2", ONWAAR},
                {"{1,2,3} E=% 1", WAAR},
                {"{1,2,3} E=% 4", ONWAAR},
                {"{\"11\",\"622\",\"9\"} E=% \"??\"", WAAR},
                {"{\"112\",\"62\",\"979\"} E=% \"?*\"", WAAR},
                {"{\"11\",\"62\",\"99\"} E=% \"?\"", ONWAAR},
                {"{\"11\",\"62\",\"99\"} E=% \"***\"", WAAR},
                {"{\"11\",\"629\",\"9779\"} E=% \"*\"", WAAR},

                // leeg
                {"{} E= 1", ONWAAR},
                {"{1} E= {}", ONWAAR},

                //special case met non-literal
                {"{2} E= 1+1", WAAR},
                {"{2010/05/03, 2008/05/03} E< 2009/05/03", WAAR},
                {"{2010/05/03, 2008/05/03} E< {}", ONWAAR},
                {"{2010/05/03, 2008/05/03} E< 2007/05/03 + ^5/0/0", WAAR},


                //realistische scenario's
                {"Persoon.Identificatienummers.Burgerservicenummer E= \"987654321\"", WAAR},
                {"Persoon.Identificatienummers.Burgerservicenummer E= Persoon.Identificatienummers.Burgerservicenummer", WAAR},
                {"MAP(Persoon.Identificatienummers.Burgerservicenummer, x, x E= \"987654321\")", "{WAAR}"},
                {"FILTER(Persoon.Identificatienummers.Burgerservicenummer, x, x E= \"987654321\")", "{\"987654321\"}"},


        });
    }

    @Test
    public void testBooleanVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie(expressie, resultaat, ExpressietaalTestPersoon.PERSOONSLIJST);

    }
}
