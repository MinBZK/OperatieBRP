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
public class AlsFunctieTest {

    @Test
    public final void testFunctieAanroepen() throws ExpressieException {
        TestUtils.assertExceptie("ALS(10, 1, 2)", "De functie ALS kan niet overweg met de argumenten: [Getal, Getal, Getal]");
    }

    @Test
    public void testSignaturen() {
        assertExceptie("ALS()", "De functie ALS kan niet overweg met de argumenten: []");
        assertExceptie("ALS(10,5)", "De functie ALS kan niet overweg met de argumenten: [Getal, Getal]");
        assertExceptie("ALS({1,2,3}, {})", "De functie ALS kan niet overweg met de argumenten: [Lijst, Lijst]");
    }

    @Test
    public void testFunctieALS() throws ExpressieException {
        TestUtils.testEvaluatie("ALS(NULL, 1, 2)", "NULL");
        TestUtils.testEvaluatie("ALS(WAAR, 1, 2)", "1");
        TestUtils.testEvaluatie("ALS(WAAR, 1, 1)", "1");
        TestUtils.testEvaluatie("ALS(WAAR, {1,2}, {1,2})", "{1, 2}");
        TestUtils.testEvaluatie("ALS(ONWAAR, 1, 2)", "2");
        TestUtils.testEvaluatie("ALS(WAAR, {1}, {1,2})", "{1}");
        TestUtils.testEvaluatie("ALS(ONWAAR, {1}, {1,2})", "{1, 2}");
        TestUtils.testEvaluatie("ALS(WAAR, NULL, {1,2})", "NULL");
        TestUtils.testEvaluatie("ALS(ONWAAR, NULL, {1,2})", "{1, 2}");
        TestUtils.testEvaluatie("ALS(WAAR, {1}, NULL)", "{1}");
        TestUtils.testEvaluatie("ALS(ONWAAR, {1}, NULL)", "NULL");
        TestUtils.testEvaluatie("AANTAL(ALS(WAAR, {1}, {1,2}))", "1");
        TestUtils.testEvaluatie("AANTAL(ALS(ONWAAR, {1}, {1,2}))", "2");
        TestUtils.testEvaluatie("AANTAL(ALS(WAAR, NULL, {1,2}))", "NULL");
        TestUtils.testEvaluatie("AANTAL(ALS(ONWAAR, NULL, {1,2}))", "2");
        TestUtils.testEvaluatie("AANTAL(ALS(WAAR, {1}, NULL))", "1");
        TestUtils.testEvaluatie("AANTAL(ALS(ONWAAR, {1}, NULL))", "NULL");
    }

    @Test(expected = ExpressieException.class)
    public void testFunctieALS_fout() throws ExpressieException {
        TestUtils.testEvaluatie("ALS(FOUT, 1, 2)", "NULL");
    }
}
