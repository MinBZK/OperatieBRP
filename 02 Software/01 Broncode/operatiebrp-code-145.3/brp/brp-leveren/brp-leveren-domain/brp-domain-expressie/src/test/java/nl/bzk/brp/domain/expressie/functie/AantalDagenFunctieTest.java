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
public class AantalDagenFunctieTest {

    @Test
    public void testSignaturen() {
        assertExceptie("AANTAL_DAGEN()", "De functie AANTAL_DAGEN kan niet overweg met de argumenten: []");
        assertExceptie("AANTAL_DAGEN({1})", "De functie AANTAL_DAGEN kan niet overweg met de argumenten: [Lijst]");
    }

    @Test
    public void testFunctieAANTALDAGEN() throws ExpressieException {
        TestUtils.testEvaluatie("AANTAL_DAGEN(2000)", "366");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1982)", "365");
        TestUtils.testEvaluatie("AANTAL_DAGEN(2001)", "365");
        TestUtils.testEvaluatie("AANTAL_DAGEN(2000,1)", "31");
        TestUtils.testEvaluatie("AANTAL_DAGEN(2000,2)", "29");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,1)", "31");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,2)", "28");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,3)", "31");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,4)", "30");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,5)", "31");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,6)", "30");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,7)", "31");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,8)", "31");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,9)", "30");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,10)", "31");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,11)", "30");
        TestUtils.testEvaluatie("AANTAL_DAGEN(1950,12)", "31");
        TestUtils.testEvaluatie("AANTAL_DAGEN(NULL,10)", "NULL");
        TestUtils.testEvaluatie("AANTAL_DAGEN(NULL)", "NULL");

        Assert.assertEquals(ExpressieType.GETAL, ExpressieParser.parse("AANTAL_DAGEN(1982)").getType(null));
    }

}
