/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc311;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerLeesUitBrpAntwoordDecisionTest {
    private final ControleerLeesUitBrpAntwoordDecision subject = new ControleerLeesUitBrpAntwoordDecision();

    @Test
    public void testNull() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        Assert.assertEquals("2c. Fout", subject.execute(parameters));
    }

    @Test
    public void testOk() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        final LeesUitBrpAntwoordBericht antwoord = new LeesUitBrpAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        parameters.put("leesUitBrpAntwoordBericht", antwoord);

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    public void testNok() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        final LeesUitBrpAntwoordBericht antwoord = new LeesUitBrpAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);
        parameters.put("leesUitBrpAntwoordBericht", antwoord);

        Assert.assertEquals("2c. Fout", subject.execute(parameters));
    }
}
