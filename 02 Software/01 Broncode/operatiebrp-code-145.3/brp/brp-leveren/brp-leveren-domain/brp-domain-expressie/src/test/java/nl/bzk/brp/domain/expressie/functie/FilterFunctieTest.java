/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class FilterFunctieTest {

    @Test
    public void testSignaturen() {
        TestUtils.assertExceptie("FILTER(10,5)", "Syntax error \"mismatched input '5' expecting IDENTIFIER\" op positie: 11");
        TestUtils.assertExceptie("FILTER({1,2,3},x)", "Syntax error \"mismatched input ')' expecting ','\" op positie: 17");
    }

    @Test
    public void testFunctieFILTER() throws ExpressieException {
        TestUtils.testEvaluatie("FILTER({1,2,3,4}, x, x = 2)", "{2}");
        TestUtils.testEvaluatie("FILTER({}, x, x > 0)", "{}");
        TestUtils.testEvaluatie("FILTER({1,2,3,4}, x, x <= 2)", "{1, 2}");
        TestUtils.testEvaluatie("FILTER({1,2,3,4}, x, x > 2)", "{3, 4}");
    }
}
