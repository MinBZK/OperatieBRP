/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import org.junit.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeSyntaxBericht;
import org.junit.Test;

public class FoutUtilTest {

    @Test
    public void testNull() {
        Assert.assertTrue(FoutUtil.bepaalIndicatieCyclusFout(null));
        Assert.assertEquals(FoutUtil.ONVERWACHT_BERICHT_MELDING, FoutUtil.bepaalFoutmelding(null));
    }

    @Test
    public void testOnbekendBericht() {
        final Lo3Bericht bericht = new OnbekendBericht("inhoud", "toelichting");
        Assert.assertFalse(FoutUtil.bepaalIndicatieCyclusFout(bericht));
        Assert.assertSame(((OnbekendBericht) bericht).getMelding(), FoutUtil.bepaalFoutmelding(bericht));
    }

    @Test
    public void testOngeldigBericht() {
        final Lo3Bericht bericht = new OngeldigeSyntaxBericht("inhoud", "toelichting");
        Assert.assertFalse(FoutUtil.bepaalIndicatieCyclusFout(bericht));
        Assert.assertSame(((OngeldigeSyntaxBericht) bericht).getMelding(), FoutUtil.bepaalFoutmelding(bericht));
    }

    @Test
    public void testIf01() {
        final Lo3Bericht bericht = new If01Bericht();
        Assert.assertTrue(FoutUtil.bepaalIndicatieCyclusFout(bericht));
        Assert.assertEquals(FoutUtil.ONVERWACHT_BERICHT_MELDING, FoutUtil.bepaalFoutmelding(bericht));
    }

    @Test
    public void beperkFoutmelding() {
        Assert.assertNull(FoutUtil.beperkFoutmelding(null));
        final String test = "Test";
        Assert.assertEquals(test, FoutUtil.beperkFoutmelding(test));
        final StringBuilder sb4000 = new StringBuilder(4000);
        for (int i = 0; i < 4000; i++) {
            sb4000.append("x");
        }
        final String test4000 = sb4000.toString();
        Assert.assertEquals(test4000, FoutUtil.beperkFoutmelding(test4000));

        final String test4010 = test4000 + "1234567890";
        final String result4010 = FoutUtil.beperkFoutmelding(test4010);
        Assert.assertFalse(test4010.equals(result4010));
        Assert.assertEquals(4000, result4010.length());

    }
}
