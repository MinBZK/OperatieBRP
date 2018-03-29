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
public class DagFunctieTest {

    @Test
    public void testFouten() {
        assertExceptie("DAG()", "De functie DAG kan niet overweg met de argumenten: []");
        assertExceptie("DAG(\"string\")", "De functie DAG kan niet overweg met de argumenten: [String]");
        assertExceptie("DAG(1990/04/03, 4)", "De functie DAG kan niet overweg met de argumenten: [Datum, Getal]");
        assertExceptie("DAG({1,2})", "De lijst bevat 2 waarden terwijl er één waarde verwacht wordt.");
        assertExceptie("DAG({\"string\"})", "De functie DAG kan niet overweg met de argumenten: [String]");
    }

    @Test
    public void testFunctieDAG() throws ExpressieException {
        TestUtils.testEvaluatie("DAG(2000/JAN/10)", "10");
        TestUtils.testEvaluatie("DAG(1970/03/?)", "NULL");
        TestUtils.testEvaluatie("DAG(1970/03/0)", "0");
        TestUtils.testEvaluatie("DAG(NULL)", "NULL");
        TestUtils.testEvaluatie("DAG({})", "NULL");
        TestUtils.testEvaluatie("DAG({NULL})", "NULL");
        TestUtils.testEvaluatie("DAG({{NULL}})", "NULL");
    }
}
