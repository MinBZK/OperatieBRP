/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc;

import java.io.IOException;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;

import org.junit.Assert;
import org.junit.Test;

public class Pf03EsbTest extends AbstractEsbTest {

    private final Lo3BerichtFactory lo3BerichtFactory = new Lo3BerichtFactory();

    @Test
    public void testPf03Lg01FoutVospg() throws BerichtSyntaxException, BerichtInhoudException,
            BerichtOnbekendException, IOException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud("00000000Lg01201206141458177408172387435000000000000000");

        verstuurVospgBericht(bericht);

        final Bericht resultaat = ontvangVospgBericht(bericht.getMessageId());

        Assert.assertNotNull(resultaat);
        final Lo3Bericht pf03 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        Assert.assertEquals("Pf03", pf03.getBerichtType());
    }

    @Test
    public void testPf03Lg01FoutMvi() throws BerichtSyntaxException, BerichtInhoudException,
            BerichtOnbekendException, IOException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud("00000000Lg01201206141458177408172387435000000000000000");

        verstuurMviBericht(bericht);

        // MVI is fire-and-forget
        // final Bericht resultaat = ontvangMviBericht(bericht.getMessageId());
        //
        // Assert.assertNotNull(resultaat);
        // final Lo3Bericht pf03 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        // Assert.assertEquals("Pf03", pf03.getBerichtType());
    }
}
