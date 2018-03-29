/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.assertExceptie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class AantalFunctieTest {

    @Test
    public final void testSignaturen() throws ExpressieException {
        assertExceptie("AANTAL()", "De functie AANTAL kan niet overweg met de argumenten: []");
        assertExceptie("AANTAL(10)", "De functie AANTAL kan niet overweg met de argumenten: [Getal]");
        assertExceptie("AANTAL({10}, {20})", "De functie AANTAL kan niet overweg met de argumenten: [Lijst, Lijst]");
    }

    @Test
    public void testFunctieAANTAL() throws ExpressieException {
        TestUtils.testEvaluatie("AANTAL({1,2,3})", "3");
        TestUtils.testEvaluatie("AANTAL({})", "0");
        TestUtils.testEvaluatie("AANTAL({1, {2, 3}})", "2");
        TestUtils.testEvaluatie("AANTAL(NULL)", "NULL");

        Assert.assertEquals(ExpressieType.GETAL, ExpressieParser.parse("AANTAL({1})").getType(null));
    }
}
