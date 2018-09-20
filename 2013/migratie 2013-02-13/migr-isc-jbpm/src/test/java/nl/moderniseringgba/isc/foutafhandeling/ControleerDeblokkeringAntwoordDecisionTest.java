/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerDeblokkeringAntwoordDecisionTest {

    private final ControleerDeblokkeringAntwoordDecision decision = new ControleerDeblokkeringAntwoordDecision();

    @Test
    public void testOk() {
        final DeblokkeringAntwoordBericht antwoord = new DeblokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.OK);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(FoutafhandelingConstants.BERICHT_ANTWOORD_DEBLOKKERING, antwoord);

        Assert.assertEquals(null, decision.execute(params));
    }

    @Test
    public void testNok() {
        final DeblokkeringAntwoordBericht antwoord = new DeblokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(FoutafhandelingConstants.BERICHT_ANTWOORD_DEBLOKKERING, antwoord);

        Assert.assertEquals("fout", decision.execute(params));
    }
}
