/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakAntwoordActionTest {

    @Test
    public void test() {
        final BrpBericht brpBericht = new VerhuizingVerzoekBericht();

        final Map<String, Object> variabelen = new HashMap<String, Object>();
        variabelen.put(FoutafhandelingConstants.BERICHT_BRP, brpBericht);

        final Map<String, Object> result = new MaakAntwoordAction().execute(variabelen);
        Assert.assertEquals(1, result.size());
        final VerhuizingAntwoordBericht antwoord =
                (VerhuizingAntwoordBericht) result.get(FoutafhandelingConstants.BERICHT_BRP_ANTWOORD);
        Assert.assertNotNull(antwoord);
        Assert.assertEquals(brpBericht.getMessageId(), antwoord.getCorrelationId());
    }
}
