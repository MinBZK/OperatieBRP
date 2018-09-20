/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OnbekendBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OngeldigBericht;

import org.junit.Assert;
import org.junit.Test;

public class FoutUtilTest {

    @Test
    public void testNull() {
        Assert.assertNull(FoutUtil.technischeFout(null));
    }

    @Test
    public void testOnbekendBericht() {
        final Lo3Bericht bericht = new OnbekendBericht("inhoud", "toelichting");
        Assert.assertSame(bericht, FoutUtil.technischeFout(bericht));
    }

    @Test
    public void testOngeldigBericht() {
        final Lo3Bericht bericht = new OngeldigBericht("inhoud", "toelichting");
        Assert.assertSame(bericht, FoutUtil.technischeFout(bericht));
    }

    public void testIf01() {
        final Lo3Bericht bericht = new If01Bericht();

        final Lo3Bericht fout = FoutUtil.technischeFout(bericht);
        Assert.assertNotNull(fout);
        Assert.assertEquals(OnbekendBericht.class, fout.getClass());
    }
}
