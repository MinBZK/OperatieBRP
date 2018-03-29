/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.assertExceptie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class DatumFunctieTest {

    @Test
    public void testSignaturen() {
        assertExceptie("DATUM()", "De functie DATUM kan niet overweg met de argumenten: []");
        assertExceptie("DATUM(1900, 12)", "De functie DATUM kan niet overweg met de argumenten: [Getal, Getal]");
        assertExceptie("DATUM(1900, 12, 14, 16)", "De functie DATUM kan niet overweg met de argumenten: [Getal, Getal, Getal, Getal]");
    }

    @Test
    public void testFunctieDATUM() throws ExpressieException {
        TestUtils.testEvaluatie("DATUM(1920, 7, 5)", "1920/07/05");
        TestUtils.testEvaluatie("DATUM(1920, 6, 62)", "1920/08/01");
        TestUtils.testEvaluatie("DATUM(1970, 4, 0)", "1970/03/31");
        TestUtils.testEvaluatie("DATUM(1970, 4, -6)", "1970/03/25");
        TestUtils.testEvaluatie("DATUM(1970, 1, 0)", "1969/12/31");
        TestUtils.testEvaluatie("DATUM(1970, 3, 32)", "1970/04/01");
        TestUtils.testEvaluatie("DATUM(1970, 12, 32)", "1971/01/01");
        TestUtils.testEvaluatie("DATUM(1970, 13, 1)", "1971/01/01");
        TestUtils.testEvaluatie("DATUM(1970, 26, 1)", "1972/02/01");
        TestUtils.testEvaluatie("DATUM(1970, -1, 1)", "1969/11/01");
        TestUtils.testEvaluatie("DATUM(1970, 14, 29)", "1971/03/01");
        TestUtils.testEvaluatie("DATUM(1971, 14, 29)", "1972/02/29");

        // d + ^p^ == DATUM(d.j+p.j, d.m+p.m, d.d+p.d)
    }
}
