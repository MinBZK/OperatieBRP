/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.assertExceptie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 */
public class JaarFunctieTest {


    @Test
    public void testFouten() {
        assertExceptie("JAAR()", "De functie JAAR kan niet overweg met de argumenten: []");
        assertExceptie("JAAR(1980/?/?,5)", "De functie JAAR kan niet overweg met de argumenten: [Datum, Getal]");
        assertExceptie("JAAR({1,2})", "De lijst bevat 2 waarden terwijl er één waarde verwacht wordt.");
        assertExceptie("JAAR({\"string\"})", "De functie JAAR kan niet overweg met de argumenten: [String]");
    }

    @Test
    public void testFunctieJAAR() throws ExpressieException {
        TestUtils.testEvaluatie("JAAR(2000/JAN/10)", "2000");
        TestUtils.testEvaluatie("JAAR(1975/08/?)", "1975");
        TestUtils.testEvaluatie("JAAR(?/?/?)", "NULL");
        TestUtils.testEvaluatie("JAAR(NULL)", "NULL");
        TestUtils.testEvaluatie("JAAR({})", "NULL");
        TestUtils.testEvaluatie("JAAR({NULL})", "NULL");
        TestUtils.testEvaluatie("JAAR({{NULL}})", "NULL");

        Assert.isTrue(TestUtils.evalueer("JAAR(Persoon.Geboorte.Datum)", ExpressietaalTestPersoon.PERSOONSLIJST_LEEG).isNull());
    }
}
