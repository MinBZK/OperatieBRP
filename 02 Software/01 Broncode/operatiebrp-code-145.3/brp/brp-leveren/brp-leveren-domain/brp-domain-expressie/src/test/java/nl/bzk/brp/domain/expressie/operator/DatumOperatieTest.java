/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * A
 */
@RunWith(Parameterized.class)
public class DatumOperatieTest {


    private final String expressie;
    private final String resultaat;

    public DatumOperatieTest(final String expressie, final String resultaat) {
        this.expressie = expressie;
        this.resultaat = resultaat;
    }

    @Parameterized.Parameters(name = "{index}: ({0}) = {1}")
    public static Iterable<Object[]> data() {
        final List<Object[]> objects = Lists.newArrayList(Arrays.asList(new Object[][]{
                // bekende datum
                {"2013/04/20 + 10", "2013/04/30"},
                {"2013/04/20 + 0", "2013/04/20"},
                {"2013/04/20 + -5", "2013/04/15"},
                {"2013/04/20 + 11", "2013/05/01"},
                {"2013/04/20 - 19", "2013/04/01"},
                {"2013/04/20 - 0", "2013/04/20"},
                {"2013/04/20 - -5", "2013/04/25"},
                {"2013/04/20 - 20", "2013/03/31"},

                //deels onbekende datum
                {"?/04/20 + 10", "0000/04/30"},
                {"?/04/20 - 10", "0000/04/10"},
                {"?/04/20 + 11", "0000/05/01"},
                {"?/04/20 - 20", "0000/03/31"},

                {"JAAR(2015/04/06/00/00/00)", "2015"},
                {"MAAND(2015/04/06/00/00/00)", "4"},
                {"DAG(2015/04/06/00/00/00)", "6"},

                //periodes
                {"^0/0/1 + ^0/1/0", "^0/1/1"},
                {"^110/5/1 + ^20/1/0", "^130/6/1"},
                {"^0/0/1 - ^0/1/0", "^0/-1/1"},
                {"1980/3/1 + ^0/0/6", "1980/03/07"},
                {"1980/3/1 + ^0/1/0", "1980/04/01"},
                {"1980/5/31 + ^0/1/0", "1980/06/30"},
                {"1980/3/1 - ^3/0/0", "1977/03/01"},
                {"1980/3/1 + ^5/0/0", "1985/03/01"},
                {"1980/3/1 + ^0/0/40", "1980/04/10"},
                {"1980/3/1 + 40", "1980/04/10"},
                {"1980/3/1 - 40", "1980/01/21"},

                {"1980/3/1 + ^0/0/?", "1980/03/?"},
                {"1980/3/1 + ^0/?/?", "1980/?/?"},
                {"1980/3/1 + ^?/?/?", "?/?/?"},


                {"1980/MRT/01 + ^0/1/0", "1980/04/01"},
                {"1980/FEB/28 + ^0/1/0", "1980/03/28"},
                {"1980/JAN/31 + ^0/1/0", "1980/02/29"},
                {"1980/MEI/31 + ^0/1/0", "1980/06/30"},
                {"1980/MEI/31 + ^0/0/30", "1980/06/30"},
                {"1980/MEI/31 + ^0/0/31", "1980/07/01"},

                {"2013/04/20 + 100 = 2013/04/20 + ^0/0/100", WAAR},
                {"1993/04/26 - ^35/2/10", "1958/02/16"},
                {"1993/04/26 - ^35/0/0", "1958/04/26"},

                {"2013/02/28 + ^0/0/29", "2013/03/29"},
                {"2013/02/28 + ^0/1/1", "2013/03/29"},
                {"(2013/02/28 + ^0/1/0) + ^0/0/1", "2013/03/29"},

                {"2000/02/28 + ^0/0/2", "2000/03/01"},
                {"2001/02/28 + ^0/0/2", "2001/03/02"},
                {"2000/02/28 + 2", "2000/03/01"},
                {"2001/02/28 + 2", "2001/03/02"},
                {"1950/01/01 + ^23/10/100 = DATUM(1950+23, 1+10, 1+100)", WAAR}
        }));

        for (int j = -10; j < 10; j += 7) {
            for (int m = -20; m < 20; m += 7) {
                for (int d = -40; d < 40; d += 11) {
                    final String exp = String.format("1950/01/01 + ^%d/%d/%d = DATUM(1950+%d, 1+%d, 1+%d)",
                            j, m, d, j, m, d);

                    final Object[] e = {exp, WAAR};
                    objects.add(e);
                }
            }
        }

        return objects;
    }

    @Test
    public void testDatumBerekeningen() throws ExpressieException {
        TestUtils.testEvaluatie(expressie, resultaat);
    }
}
