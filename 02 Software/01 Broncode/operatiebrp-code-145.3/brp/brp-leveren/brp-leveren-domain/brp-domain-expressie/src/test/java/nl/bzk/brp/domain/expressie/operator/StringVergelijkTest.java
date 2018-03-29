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
public class StringVergelijkTest {

    @Test
    public void testVergelijkingen() throws ExpressieException {
        TestUtils.testEvaluatie("\"ABC\" = \"ABC\"", WAAR);
        TestUtils.testEvaluatie("\"ABC\" < \"ABC\"", ONWAAR);
        TestUtils.testEvaluatie("\"ABC\" <= \"ABC\"", WAAR);
        TestUtils.testEvaluatie("\"ABC\" > \"ABC\"", ONWAAR);
        TestUtils.testEvaluatie("\"ABC\" >= \"ABC\"", WAAR);

        TestUtils.testEvaluatie("\"AAA\" < \"BBB\"", WAAR);
        TestUtils.testEvaluatie("\"AAA\" <= \"BBB\"", WAAR);
        TestUtils.testEvaluatie("\"BBB\" < \"AAA\"", ONWAAR);
        TestUtils.testEvaluatie("\"BBB\" <= \"AAA\"", ONWAAR);
        TestUtils.testEvaluatie("\"BBB\" > \"AAA\"", WAAR);
        TestUtils.testEvaluatie("\"BBB\" >= \"AAA\"", WAAR);
        TestUtils.testEvaluatie("\"AAA\" > \"BBB\"", ONWAAR);
        TestUtils.testEvaluatie("\"AAA\" >= \"BBB\"", ONWAAR);

        TestUtils.testEvaluatie("\"AAA\" <> \"BBB\"", WAAR);
        TestUtils.testEvaluatie("\"AAA\" <> \"AAA\"",ONWAAR);

        TestUtils.testEvaluatie("\"ABC\" =% \"ABC\"", WAAR);
        TestUtils.testEvaluatie("\"ABC\" =% \"AB?\"", WAAR);
        TestUtils.testEvaluatie("\"ABC\" =% \"A??\"", WAAR);
        TestUtils.testEvaluatie("\"ABC\" =% \"???\"", WAAR);
        TestUtils.testEvaluatie("\"ABC\" =% \"*\"", WAAR);
        TestUtils.testEvaluatie("\"ABC\" =% \"A*\"", WAAR);
        TestUtils.testEvaluatie("\"ABC\" =% \"AB*\"", WAAR);


    }
}
