/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OngeldigBericht;

import org.junit.Assert;
import org.junit.Test;

public class OngeldigeBerichtenTest {

    @Test
    public void testOngeldigeHeaderInBericht() {
        final Lo3BerichtFactory factory = new Lo3BerichtFactory();
        final Lo3Bericht bericht = factory.getBericht("00000000Ii01A0003801033011001041902492490120009884369481");
        System.out.println(bericht);

        Assert.assertTrue(bericht instanceof OngeldigBericht);
    }

    @Test(expected = BerichtSyntaxException.class)
    public void testOngeldigeHerhalingInHeader() throws Exception {
        final Lo3Header HEADER =
                new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.HERHALING);

        HEADER.parseHeaders("00000000Ii01A");
    }
}
