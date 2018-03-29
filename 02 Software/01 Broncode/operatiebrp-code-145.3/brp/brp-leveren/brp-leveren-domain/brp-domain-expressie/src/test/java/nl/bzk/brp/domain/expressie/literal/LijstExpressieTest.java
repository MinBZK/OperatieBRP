/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.literal;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class LijstExpressieTest {


    @Test
    public void testLijsten() throws ExpressieException {
        TestUtils.testEvaluatie("{1}", "{1}");
        TestUtils.testEvaluatie("{1, 2, 3}", "{1, 2, 3}");
        TestUtils.testEvaluatie("{}", "{}");
        TestUtils.testEvaluatie("{1+1}", "{2}");
        TestUtils.testEvaluatie("{0,1+1,1+2}", "{0, 2, 3}");
    }

    @Test
    public void testLijstVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie("{} = 1", ONWAAR);
        TestUtils.testEvaluatie("{1} = 1", WAAR);
        TestUtils.testEvaluatie("1 = {}", ONWAAR);
        TestUtils.testEvaluatie("1 = {1}", WAAR);
//        TestUtils.assertExceptie("{1} EIN {\"1\"}", "Operand vergelijking niet mogelijk [links: Getal, rechts: String]");

        TestUtils.testEvaluatie("{} = {}", WAAR);
        TestUtils.testEvaluatie("{0} = {0}", WAAR);
        TestUtils.testEvaluatie("{1} = {2}", WAAR);
        TestUtils.testEvaluatie("{1,2} = {1,2}", WAAR);
        TestUtils.testEvaluatie("{1,2} = {2,1}", WAAR);
        TestUtils.testEvaluatie("{1,2,3,4} = {1,2,3,4}", WAAR);
        TestUtils.testEvaluatie("{1,2,3,4} = {3,4,1,2}", WAAR);
        TestUtils.testEvaluatie("{1,2,3,4} = {4,3,2,1}", WAAR);
        TestUtils.testEvaluatie("{1,1,1,3} = {1,3,1,1}", WAAR);
        TestUtils.testEvaluatie("{1,2,3,4} = {1,2,3}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} = {3,5,1,2}", WAAR);
        TestUtils.testEvaluatie("{1,1,1,3} = {1,3,1}", ONWAAR);

        TestUtils.testEvaluatie("{0} <> {0}", ONWAAR);
        TestUtils.testEvaluatie("{1} <> {2}", ONWAAR);
        TestUtils.testEvaluatie("{1,2} <> {1,2}", ONWAAR);
        TestUtils.testEvaluatie("{1,2} <> {2,1}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} <> {1,2,3,4}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} <> {3,4,1,2}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} <> {4,3,2,1}", ONWAAR);
        TestUtils.testEvaluatie("{1,1,1,3} <> {1,3,1,1}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} <> {1,2,3}", WAAR);
        TestUtils.testEvaluatie("{1,2,3,4} <> {3,5,1,2}", ONWAAR);
        TestUtils.testEvaluatie("{1,1,1,3} <> {1,3,1}", WAAR);

        TestUtils.testEvaluatie("{1,2,3} < {1,2,3,4}", WAAR);
        TestUtils.testEvaluatie("{1,2,3,4} < {3,4,1,2}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} <= {4,3,2,1}", WAAR);
        TestUtils.testEvaluatie("{1,3} <= {1,3,1,2}", WAAR);
        TestUtils.testEvaluatie("{} < {1,2,3}", WAAR);
        TestUtils.testEvaluatie("{1,2,3,4} <= {}", ONWAAR);

        TestUtils.testEvaluatie("{1,2,3,4} < {1,2,3}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} > {3,4,1,2}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} >= {4,3,2,1}", WAAR);
        TestUtils.testEvaluatie("{1,3,1,2} >= {1,1}", WAAR);
        TestUtils.testEvaluatie("{} > {1,2,3}", ONWAAR);
        TestUtils.testEvaluatie("{1,2,3,4} >= {}", WAAR);
    }
}
