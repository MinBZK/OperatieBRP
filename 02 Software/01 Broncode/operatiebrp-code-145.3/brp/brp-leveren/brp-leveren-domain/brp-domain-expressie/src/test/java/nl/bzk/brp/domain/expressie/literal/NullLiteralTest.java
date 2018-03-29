/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.literal;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;
import static org.junit.Assert.assertEquals;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class NullLiteralTest {

    @Test
    public void testNullExpressie() throws ExpressieException {
        final Expressie aNull = ExpressieParser.parse("NULL");
        Assert.assertTrue(aNull instanceof NullLiteral);
    }

    @Test
    public void testNullWaarden() throws ExpressieException {
        TestUtils.testEvaluatie("NULL", "NULL");
        TestUtils.testEvaluatie("NIET(NULL)", "NULL");
        TestUtils.testEvaluatie("WAAR OF NULL", WAAR);
        TestUtils.testEvaluatie("WAAR EN NULL", "NULL");
        TestUtils.testEvaluatie("WAAR <> NULL", WAAR);
        TestUtils.testEvaluatie("ONWAAR OF NULL", "NULL");
        TestUtils.testEvaluatie("ONWAAR EN NULL", ONWAAR);
        TestUtils.testEvaluatie("NULL OF NULL", "NULL");
        TestUtils.testEvaluatie("NULL EN NULL", "NULL");
        TestUtils.testEvaluatie("NULL <> NULL", ONWAAR);
        TestUtils.testEvaluatie("2 + NULL", "NULL");
        TestUtils.testEvaluatie("4 > NULL", ONWAAR);
        TestUtils.testEvaluatie("NULL = NULL", WAAR);
        TestUtils.testEvaluatie("NIET(NULL = NULL)", ONWAAR);
    }

    @Test
    public void testVeilgeExpressie() throws ExpressieException {
        final Expressie aNull = ExpressieParser.parse("NULL");
        assertEquals(NullLiteral.INSTANCE, NullLiteral.veiligeExpressie(null));
        assertEquals(aNull, NullLiteral.veiligeExpressie(aNull));

    }
}
