/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * FunctieLaatsteDagTest.
 */
public class LaatsteDagFunctieTest {

    @Test
    public void testFunctieLAATSTEDAG() throws ExpressieException {
        TestUtils.testEvaluatie("LAATSTE_DAG(2000)", "2000/12/31");
        TestUtils.testEvaluatie("LAATSTE_DAG(1940)", "1940/12/31");
        TestUtils.testEvaluatie("LAATSTE_DAG(2000,1)", "2000/01/31");
        TestUtils.testEvaluatie("LAATSTE_DAG(2000,2)", "2000/02/29");
        TestUtils.testEvaluatie("LAATSTE_DAG(1950,1)", "1950/01/31");
        TestUtils.testEvaluatie("LAATSTE_DAG(1950,2)", "1950/02/28");
        TestUtils.testEvaluatie("LAATSTE_DAG(1950,6)", "1950/06/30");
        TestUtils.testEvaluatie("LAATSTE_DAG(NULL, 2)", "NULL");
        TestUtils.testEvaluatie("LAATSTE_DAG(1970, NULL)", "NULL");
        TestUtils.testEvaluatie("DATUM(1975, 1, AANTAL_DAGEN(1975)) = LAATSTE_DAG(1975)", WAAR);
        TestUtils.testEvaluatie("DATUM(1975, 1, AANTAL_DAGEN(1975, 1)) = LAATSTE_DAG(1975, 1)", WAAR);
    }

    @Test
    public void testLaatsteDagInJaar() throws ExpressieException {
        final DatumLiteral expressie = (DatumLiteral) TestUtils.evalueer("LAATSTE_DAG(2010)", ExpressietaalTestPersoon.PERSOONSLIJST_LEEG);
        Assert.assertEquals(20101231, expressie.alsInteger());
    }

    @Test
    public void testLaatsteDagInMaand() throws ExpressieException {
        final DatumLiteral expressie = (DatumLiteral) TestUtils.evalueer("LAATSTE_DAG(2010, 12)", ExpressietaalTestPersoon.PERSOONSLIJST_LEEG);
        Assert.assertEquals(20101231, expressie.alsInteger());
    }

    @Test(expected = ExpressieException.class)
    public void testOngeldigeLaatsteDag() throws ExpressieException {
        TestUtils.evalueer("LAATSTE_DAG(2010, 99)", ExpressietaalTestPersoon.PERSOONSLIJST_LEEG);
    }


    @Test
    public void testGetType() throws ExpressieException {
        final Expressie expressie = TestUtils.evalueer("LAATSTE_DAG(2010, 12)", ExpressietaalTestPersoon.PERSOONSLIJST_LEEG);
        Assert.assertEquals(ExpressieType.DATUM, expressie.getType(null));
    }
}
