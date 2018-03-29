/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.assertExceptie;
import static nl.bzk.brp.domain.expressie.util.TestUtils.testEvaluatie;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * FunctieMaandTest.
 */
public class MaandFunctieTest {

    @Test
    public void testSignaturen() {
        assertExceptie("MAAND()", "De functie MAAND kan niet overweg met de argumenten: []");
        assertExceptie("MAAND(10)", "De functie MAAND kan niet overweg met de argumenten: [Getal]");
        assertExceptie("MAAND(\"string\")", "De functie MAAND kan niet overweg met de argumenten: [String]");
    }

    @Test
    public void testFunctieMAAND() throws ExpressieException {
        testEvaluatie("MAAND(2000/JAN/10)", "1");
        testEvaluatie("MAAND(1975/08/?)", "8");
        testEvaluatie("MAAND(1980/?/?)", "NULL");
        testEvaluatie("MAAND(NULL)", "NULL");
        TestUtils.testEvaluatie("MAAND({})", "NULL");
        TestUtils.testEvaluatie("MAAND({NULL})", "NULL");
    }

    @Test
    public void testMaand() throws ExpressieException {
        final GetalLiteral expressie = (GetalLiteral) TestUtils.evalueer("MAAND(LAATSTE_DAG(2010))");
        Assert.assertEquals(12, expressie.getWaarde());
    }

    @Test
    public void testGetType() throws ExpressieException {
        final Expressie expressie = TestUtils.evalueer("MAAND(LAATSTE_DAG(2010))");
        Assert.assertEquals(ExpressieType.GETAL, expressie.getType(null));
    }
}
