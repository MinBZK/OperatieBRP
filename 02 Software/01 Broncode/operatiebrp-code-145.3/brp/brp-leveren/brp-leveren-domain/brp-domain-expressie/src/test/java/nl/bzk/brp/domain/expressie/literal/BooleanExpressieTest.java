/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.literal;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class BooleanExpressieTest {


    @Test
    public void testBooleanBerekeningen() throws ExpressieException {
        TestUtils.testEvaluatie(WAAR, WAAR);
        TestUtils.testEvaluatie("TRUE", WAAR);
        TestUtils.testEvaluatie(ONWAAR, ONWAAR);
        TestUtils.testEvaluatie("FALSE", ONWAAR);
        TestUtils.testEvaluatie("WAAR EN WAAR", WAAR);
        TestUtils.testEvaluatie("WAAR EN ONWAAR", ONWAAR);
        TestUtils.testEvaluatie("ONWAAR EN WAAR", ONWAAR);
        TestUtils.testEvaluatie("ONWAAR EN ONWAAR", ONWAAR);
        TestUtils.testEvaluatie("WAAR OF ONWAAR", WAAR);
        TestUtils.testEvaluatie("ONWAAR OF ONWAAR", ONWAAR);
        TestUtils.testEvaluatie("NIET WAAR", ONWAAR);
        TestUtils.testEvaluatie("NIET ONWAAR", WAAR);
    }
}
