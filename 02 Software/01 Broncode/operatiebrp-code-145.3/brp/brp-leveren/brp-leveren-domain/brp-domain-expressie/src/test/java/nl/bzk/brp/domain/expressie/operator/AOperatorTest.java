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
public class AOperatorTest {

    private final String expressie;
    private final String resultaat;

    public AOperatorTest(final String expressie, final String resultaat) {
        this.expressie = expressie;
        this.resultaat = resultaat;
    }

    @Parameterized.Parameters(name = "{index}: ({0}) = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{

                //null specials
                {"{1, NULL} A= NULL", ONWAAR},
                {"{NULL, 1} A= NULL", ONWAAR},
                {"{NULL, 1} A= 1", ONWAAR},
                {"{\"de\", NULL} A= {\"de\"}", ONWAAR},

                //A=
                {"{1} A= 1", WAAR},
                {"{1} A= 2", ONWAAR},
                {"{1,1,1} A= 1", WAAR},
                {"{1,2,3} A= 4", ONWAAR},
                {"{10} A= NULL", ONWAAR},
                {"{NULL} A= NULL", WAAR},

                //A>
                {"{1} A> 0", WAAR},
                {"{1} A> 1", ONWAAR},
                {"{1,2,3} A> 0", WAAR},
                {"{1,2,3} A> 4", ONWAAR},
                {"{10} A> NULL", ONWAAR},
                {"{NULL} A> NULL", ONWAAR},

                //A>=
                {"{1} A>= 1", WAAR},
                {"{1} A>= 2", ONWAAR},
                {"{1,2,3} A>= 1", WAAR},
                {"{1,2,3} A>= 4", ONWAAR},
                {"{10} A>= NULL", ONWAAR},
                {"{NULL} A>= NULL", WAAR},

                //A<
                {"{1} A< 2", WAAR},
                {"{1} A< 1", ONWAAR},
                {"{1,2,3} A< 4", WAAR},
                {"{1,2,3} A< 1", ONWAAR},
                {"{10} A< NULL", ONWAAR},
                {"{NULL} A< NULL", ONWAAR},

                //A<=
                {"{1} A<= 2", WAAR},
                {"{1} A<= 0", ONWAAR},
                {"{1,2,3} A<= 4", WAAR},
                {"{1,2,3} A<= 0", ONWAAR},
                {"{10} A<= NULL", ONWAAR},
                {"{NULL} A<= NULL", WAAR},

                //A<>
                {"{1} A<> 2", WAAR},
                {"{1} A<> 1", ONWAAR},
                {"{1,2,3} A<> 0", WAAR},
                {"{4,4,4} A<> 4", ONWAAR},
                {"{10} A<> NULL", WAAR},
                {"{NULL} A<> NULL", ONWAAR},

                //A=%
                {"{1} A=% 1", WAAR},
                {"{1} A=% 2", ONWAAR},
                {"{1,1,1} A=% 1", WAAR},
                {"{1,2,3} A=% 4", ONWAAR},
                {"{\"11\",\"62\",\"99\"} A=% \"??\"", WAAR},
                {"{\"11\",\"62\",\"99\"} A=% \"?*\"", WAAR},
                {"{\"11\",\"62\",\"99\"} A=% \"?\"", ONWAAR},
                {"{\"11\",\"62\",\"99\"} A=% \"***\"", WAAR},
                {"{\"11\",\"62\",\"99\"} A=% \"*\"", WAAR},

                //special case met non-literal
                {"{2} A= 1+1", WAAR},
                {"{2010/05/03, 2008/05/03} A< 2011/05/03", WAAR},
                {"{2010/05/03, 2008/05/03} A< 2009/05/03", ONWAAR},
                {"{2010/05/03, 2008/05/03} A< 2007/05/03 + ^5/0/0", WAAR},

                //special case met leeg
                {"{} A= 1", ONWAAR},
                //test de eval op de linker expressie
                {"Persoon.Identificatienummers.Burgerservicenummer A= \"987654321\"", WAAR},

                //realistische scenario's
                {"Persoon.Identificatienummers.Burgerservicenummer A= \"987654321\"", WAAR},
                {"Persoon.Identificatienummers.Burgerservicenummer A= Persoon.Identificatienummers.Burgerservicenummer", WAAR},
                {"MAP(Persoon.Identificatienummers.Burgerservicenummer, x, x A= \"987654321\")", "{WAAR}"},
                {"FILTER(Persoon.Identificatienummers.Burgerservicenummer, x, x A= \"987654321\")", "{\"987654321\"}"},
        });
    }

    @Test
    public void testBooleanVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie(expressie, resultaat, ExpressietaalTestPersoon.PERSOONSLIJST);

    }
}
