/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * FunctieVandaagTest.
 */
public class VandaagFunctieTest {

    @Test
    public final void testFunctieAanroepen() throws ExpressieException {
        TestUtils.assertExceptie("VANDAAG(10, 5)", "De functie VANDAAG kan niet overweg met de argumenten: [Getal, Getal]");
    }

    @Test
    public void testFunctieVANDAAG() throws ExpressieException {
        TestUtils.testEvaluatie("VANDAAG()", getDatumString(ZonedDateTime.now(DatumUtil.NL_ZONE_ID)));
        TestUtils.testEvaluatie("VANDAAG(0)", getDatumString(ZonedDateTime.now(DatumUtil.NL_ZONE_ID)));
        TestUtils.testEvaluatie("VANDAAG(1)", getDatumString(ZonedDateTime.now(DatumUtil.NL_ZONE_ID).plusYears(1)));
        TestUtils.testEvaluatie("VANDAAG(-1)", getDatumString(ZonedDateTime.now(DatumUtil.NL_ZONE_ID).minusYears(1)));
        TestUtils.testEvaluatie("VANDAAG(100)", getDatumString(ZonedDateTime.now(DatumUtil.NL_ZONE_ID).plusYears(100)));
    }

    @Test
    public void testVandaag() throws ExpressieException {
        final DatumLiteral expressie = (DatumLiteral) TestUtils.evalueer("VANDAAG()");
        Assert.assertEquals(DatumUtil.vandaag(), expressie.alsInteger());
    }

    @Test
    public void testGetType() throws ExpressieException {
        final Expressie expressie = TestUtils.evalueer("VANDAAG()");
        Assert.assertEquals(ExpressieType.DATUM, expressie.getType(null));
    }

    /**
     * Geeft de stringrepresentatie van een DateTime-object, zoals gebruikt in de expressietaal.
     *
     * @param dt Datum.
     * @return Stringrepresentatie van datum.
     */
    private static String getDatumString(final ZonedDateTime dt) {
        final DatumLiteral exp = new DatumLiteral(dt);
        return exp.toString();
    }
}
