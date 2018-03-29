/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ErIsFunctieTest {

    @Test
    public void testSignaturen() {
        TestUtils.assertExceptie("ER_IS(k, WAAR)", "Syntax error \"mismatched input 'WAAR' expecting IDENTIFIER\" op positie: 10");
    }

    @Test
    public void testFunctieERIS() throws ExpressieException {
        TestUtils.testEvaluatie("ER_IS({1,2,3,4}, x, 1 + x = 2)", WAAR);
        TestUtils.testEvaluatie("ER_IS({1,2,3,4}, x, x = 5)", ONWAAR);
        TestUtils.testEvaluatie("ER_IS({1,2,3,4}, x, x + x = 8)", WAAR);
        TestUtils.testEvaluatie("ER_IS({}, x, x=2)", ONWAAR);
        TestUtils.testEvaluatie("ER_IS(NULL, x, x=2)", "NULL");
        TestUtils.testEvaluatie("ER_IS({1}, x, NULL)", "NULL");

        Assert.assertEquals(ExpressieType.BOOLEAN, ExpressieParser.parse("ER_IS({1,2,3,4}, x, x = 5)").getType(null));
    }
}
