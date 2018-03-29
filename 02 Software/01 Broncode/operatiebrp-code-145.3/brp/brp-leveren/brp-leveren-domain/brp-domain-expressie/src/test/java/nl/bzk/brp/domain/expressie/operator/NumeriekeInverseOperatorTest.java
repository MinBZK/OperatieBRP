/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.StringLiteral;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class NumeriekeInverseOperatorTest {

    @Test
    public void test() throws ExpressieException {

        TestUtils.testEvaluatie("-DAG(1999/01/05)", "-5");
        TestUtils.testEvaluatie("-DAG(1970/03/?)", "NULL");

        TestUtils.testEvaluatie("(3-2)", "1");
        TestUtils.testEvaluatie("(2-3)", "-1");

        // TestUtils.testEvaluatie("-\"A\"", "-5");
        // moet onderstaande ondersteund worden?
        // TestUtils.testEvaluatie("-(Persoon.Geboorte.GemeenteCode)", "-5", ExpressietaalTestPersoon.PERSOON);
    }

    @Test
    public void typeBepaling() {
        final NumeriekeInverseOperator numeriekeInverseOperatorGetal = new NumeriekeInverseOperator(new GetalLiteral(13L));
        Assert.assertEquals(ExpressieType.GETAL, numeriekeInverseOperatorGetal.getType(new Context()));

        final NumeriekeInverseOperator numeriekeInverseOperatorString = new NumeriekeInverseOperator(new StringLiteral("String"));
        Assert.assertEquals(ExpressieType.ONBEKEND_TYPE, numeriekeInverseOperatorString.getType(new Context()));
    }
}
