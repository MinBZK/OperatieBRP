/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.algemeen;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class ClosureExpressieTest {

    @Test
    public void testClosures() throws ExpressieException {
        TestUtils.testEvaluatie("x + 2 = 12 WAARBIJ x = 10", WAAR);
        TestUtils.testEvaluatie("(x + 2 = 12 WAARBIJ x = 10) WAARBIJ x = 20", WAAR);
        TestUtils.testEvaluatie("(x + y = 12 WAARBIJ x = 10) WAARBIJ y = 2", WAAR);
        TestUtils.testEvaluatie("x + y = 12 WAARBIJ x = 10, y = 2", WAAR);
        TestUtils.testEvaluatie("x+x+y+z WAARBIJ x=10,y=2,z=4", "26");
        TestUtils.testEvaluatie("(x WAARBIJ x=y) WAARBIJ y=2", "2");
        TestUtils.testEvaluatie("(x+y WAARBIJ x=y,y=z) WAARBIJ y=2,z=4", "6");
        TestUtils.testEvaluatie("y + (y + y WAARBIJ y = 3) + (y + y WAARBIJ y = 5) = 18 WAARBIJ y = 2", WAAR);
        TestUtils.testEvaluatie("(y + y WAARBIJ y = (x+2 WAARBIJ x = 1))", "6");
        TestUtils.testEvaluatie("(y + y WAARBIJ y = (y+2 WAARBIJ y = 1))", "6");
        TestUtils.testEvaluatie("y + (y + y WAARBIJ y = (y+2 WAARBIJ y = 1)) + (y + y WAARBIJ y = 5) WAARBIJ y = 2", "18");
        TestUtils.testEvaluatie("1 < 2 WAARBIJ x = 4", WAAR);
        TestUtils.testEvaluatie("1 < x WAARBIJ x = 4", WAAR);
        TestUtils.testEvaluatie("x < 2 WAARBIJ x = 4", ONWAAR);
        //TestUtils.testEvaluatie("1 EIN xY WAARBIJ xY = {1,2,3}", WAAR);
        //TestUtils.testEvaluatie("4 EIN l WAARBIJ l = {1,2,3}", ONWAAR);
        TestUtils.testEvaluatie("{1,x,3} WAARBIJ x=2", "{1, 2, 3}");
        TestUtils.testEvaluatie("DATUM(j, m, d) WAARBIJ j=1970,m=12,d=5", "1970/12/05");
        TestUtils.testEvaluatie("(((x WAARBIJ x = 10, y = \"Gandalf\") WAARBIJ z=1970/JAN/01) WAARBIJ x = 20)", "10");
        TestUtils.testEvaluatie("(((y WAARBIJ x = 10, y = \"Gandalf\") WAARBIJ z=1970/JAN/01) WAARBIJ x = 20)", "\"Gandalf\"");
        TestUtils.testEvaluatie("(((z WAARBIJ x = 10, y = \"Gandalf\") WAARBIJ z=1970/JAN/01) WAARBIJ x = 20)", "1970/01/01");
    }
}
