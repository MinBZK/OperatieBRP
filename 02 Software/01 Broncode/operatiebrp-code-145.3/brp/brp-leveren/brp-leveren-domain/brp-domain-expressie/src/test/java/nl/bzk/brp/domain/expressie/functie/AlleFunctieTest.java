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
public class AlleFunctieTest {

    @Test
    public void testSignaturen() {
        TestUtils.assertExceptie("ALLE(10,5)", "Syntax error \"mismatched input '5' expecting IDENTIFIER\" op positie: 9");
        TestUtils.assertExceptie("ALLE({1,2,3},x)", "Syntax error \"mismatched input ')' expecting ','\" op positie: 15");
    }

    @Test
    public void testFunctieALLE() throws ExpressieException {
        TestUtils.testEvaluatie("ALLE({1,2,3,4}, x, x < 5)", WAAR);
        TestUtils.testEvaluatie("ALLE({1,2,3,4}, x, x < 2)", ONWAAR);
        TestUtils.testEvaluatie("ALLE({}, x, x = 2)", WAAR);
        TestUtils.testEvaluatie("ALLE(NULL, x, x = 2)", "NULL");
        TestUtils.testEvaluatie("ALLE({1}, x, NULL)", "NULL");

        Assert.assertEquals(ExpressieType.BOOLEAN, ExpressieParser.parse("ALLE({}, x, x = 2)").getType(null));
    }
}
