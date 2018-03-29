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
public class LiteralExpressieTest {


    @Test
    public void testNumberLiterals() throws ExpressieException {
        TestUtils.testEvaluatie("10", "10");
        TestUtils.testEvaluatie("0", "0");
        TestUtils.testEvaluatie("-2", "-2");
    }

    @Test
    public void testBooleanLiterals() throws ExpressieException {
        TestUtils.testEvaluatie(WAAR, WAAR);
        TestUtils.testEvaluatie(ONWAAR, ONWAAR);
    }

    @Test
    public void testStringLiterALS() throws ExpressieException {
        TestUtils.testEvaluatie("\"\"", "\"\"");
        TestUtils.testEvaluatie("\"abc\"", "\"abc\"");
        TestUtils.testEvaluatie("\"     \"", "\"     \"");
    }
}
