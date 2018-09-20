/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor de protocol fouten die kunnen worden teruggegeven door de gateway's.
 */
public class Pf02EsbTest extends AbstractEsbTest {

    private final Lo3BerichtFactory lo3BerichtFactory = new Lo3BerichtFactory();

    @Test
    public void testPf02Vospg() throws BerichtSyntaxException, BerichtInhoudException, BerichtOnbekendException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud("swealskufhalhe4larhaewlfehlwffhewwwleihfwalfwhewlewufhewkulfhklr");

        verstuurVospgBericht(bericht);

        final Bericht resultaat = ontvangVospgBericht(bericht.getMessageId());

        Assert.assertNotNull(resultaat);
        final Lo3Bericht pf02 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        Assert.assertEquals("Pf02", pf02.getBerichtType());
    }

    @Test
    public void testPf02Mvi() throws BerichtSyntaxException, BerichtInhoudException, BerichtOnbekendException {
        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud("asfgsdfk");

        verstuurMviBericht(bericht);

        // MVI is fire-and-forget
        // final Bericht resultaat = ontvangMviBericht(bericht.getMessageId());
        //
        // Assert.assertNotNull(resultaat);
        // final Lo3Bericht pf02 = lo3BerichtFactory.getBericht(resultaat.getInhoud());
        // Assert.assertEquals("Pf02", pf02.getBerichtType());
    }
}
