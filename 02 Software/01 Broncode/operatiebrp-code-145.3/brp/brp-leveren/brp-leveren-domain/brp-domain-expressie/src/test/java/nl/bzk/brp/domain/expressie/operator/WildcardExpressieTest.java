/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class WildcardExpressieTest {


    @Test
    public void testWildcards() throws ExpressieException {
        TestUtils.testEvaluatie("1 =% 1", WAAR);
        TestUtils.testEvaluatie("TRUE =% TRUE", WAAR);
        TestUtils.testEvaluatie("2010/10/10 =% 2010/10/10", WAAR);

        TestUtils.testEvaluatie("\"Regex\" =% \"Reg*\"", WAAR);
        TestUtils.testEvaluatie("\"Regex\" =% \"*\"", WAAR);
        TestUtils.testEvaluatie("\"Regex\" =% \"?egex\"", WAAR);
        TestUtils.testEvaluatie("\"Regex\" =% \"Re?*ex\"", WAAR);
        TestUtils.testEvaluatie("\"Regex\" =% \"Regzz*\"", ONWAAR);
        TestUtils.testEvaluatie("\"\" =% \"?*\"", ONWAAR);
        TestUtils.testEvaluatie("\"Regex\" =% \"Re??ex\"", ONWAAR);

        TestUtils.testEvaluatie("\"Jan\" =% \"J*\"", WAAR);
        TestUtils.testEvaluatie("\"Jan\" =% \"J?n\"", WAAR);
        TestUtils.testEvaluatie("\"Jan\" =% \"Jan\"", WAAR);
        TestUtils.testEvaluatie("\"Jansen\" =% \"Jan*\"", WAAR);

        TestUtils.testEvaluatie("\"Reg.ex\" =% \"Reg.*\"", WAAR);
        TestUtils.testEvaluatie("\"{Reg.ex\" =% \"{Reg.*\"", WAAR);
        TestUtils.testEvaluatie("\"Reg^.ex=\" =% \"Reg^.*=\"", WAAR);
    }
}
