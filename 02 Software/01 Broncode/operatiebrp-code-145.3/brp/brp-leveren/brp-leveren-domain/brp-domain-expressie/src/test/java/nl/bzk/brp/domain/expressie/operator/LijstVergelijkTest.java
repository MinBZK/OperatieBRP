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

public class LijstVergelijkTest {

    @Test
    public void testGetalVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie("{10,11} = {10}", ONWAAR);
        TestUtils.testEvaluatie("{10} = {10}", WAAR);

        TestUtils.testEvaluatie("{10,11} < {10}", ONWAAR);
        TestUtils.testEvaluatie("{10} < {10}", ONWAAR);
        TestUtils.testEvaluatie("{10} < {10,11}", WAAR);

        TestUtils.testEvaluatie("{10,11} > {10}", WAAR);
        TestUtils.testEvaluatie("{10} > {10}", ONWAAR);
        TestUtils.testEvaluatie("{10} > {10,11}", ONWAAR);

        TestUtils.testEvaluatie("{10,11} >= {10}", WAAR);
        TestUtils.testEvaluatie("{10} >= {10}", WAAR);
        TestUtils.testEvaluatie("{10} >= {10,11}", ONWAAR);

        TestUtils.testEvaluatie("{10,11} <= {10}", ONWAAR);
        TestUtils.testEvaluatie("{10} <= {10}", WAAR);
        TestUtils.testEvaluatie("{10} <= {10,11}", WAAR);

        TestUtils.testEvaluatie("{10,11} <> {10}", WAAR);
        TestUtils.testEvaluatie("{10} <> {10}", ONWAAR);

        //lijst wildcard vergelijking is (nog) niet supported
        TestUtils.testEvaluatie("{10} =% {10}", "NULL");
    }

}
