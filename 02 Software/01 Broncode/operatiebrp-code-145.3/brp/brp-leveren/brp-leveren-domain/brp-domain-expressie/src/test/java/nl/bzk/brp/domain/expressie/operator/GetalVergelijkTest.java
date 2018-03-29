/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class GetalVergelijkTest {

    @Test
    public void testGetalVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie("10 > 2", WAAR);
        TestUtils.testEvaluatie("12 = 12", WAAR);
        TestUtils.testEvaluatie("-2 < -1", WAAR);
        TestUtils.testEvaluatie("0 < -5", ONWAAR);
        TestUtils.testEvaluatie("1 = 1", WAAR);
        TestUtils.testEvaluatie("1 <> 2", WAAR);
        TestUtils.testEvaluatie("10+20=30", WAAR);
        TestUtils.testEvaluatie("10+5=15", WAAR);
        TestUtils.testEvaluatie("5-10=-5", WAAR);

    }

    @Test
    public void smoke() throws ExpressieException {
        TestUtils.testEvaluatie("10+20=30", WAAR);
    }
}
