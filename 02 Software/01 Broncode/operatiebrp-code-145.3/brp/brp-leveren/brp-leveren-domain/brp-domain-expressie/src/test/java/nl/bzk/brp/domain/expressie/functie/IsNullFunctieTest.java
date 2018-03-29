/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.assertExceptie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class IsNullFunctieTest {

    @Test
    public void testFouten() {
        assertExceptie("IS_NULL()", "De functie IS_NULL kan niet overweg met de argumenten: []");
        assertExceptie("IS_NULL(10,5)", "De functie IS_NULL kan niet overweg met de argumenten: [Getal, Getal]");
    }

    @Test
    public void testEval() throws ExpressieException {
        TestUtils.testEvaluatie("IS_NULL(NULL)", WAAR);
        TestUtils.testEvaluatie("IS_NULL(45+NULL)", WAAR);
        TestUtils.testEvaluatie("IS_NULL(1940/10/10 = NULL)", ONWAAR);
        TestUtils.testEvaluatie("IS_NULL(1)", ONWAAR);

        Assert.assertEquals(ExpressieType.BOOLEAN, ExpressieParser.parse("IS_NULL(NULL)").getType(null));
    }

}
