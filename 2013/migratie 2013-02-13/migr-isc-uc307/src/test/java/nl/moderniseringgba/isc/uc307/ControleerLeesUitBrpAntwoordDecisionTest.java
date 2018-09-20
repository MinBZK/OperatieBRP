/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerLeesUitBrpAntwoordDecisionTest {

    private final ControleerLeesUitBrpAntwoordDecision controleerQueryResponseDecision =
            new ControleerLeesUitBrpAntwoordDecision();

    @Test
    public void testHappyFlow() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("leesUitBrpAntwoordBericht", createSyncBerichtVoorHappyFlow());

        final String transition = controleerQueryResponseDecision.execute(parameters);
        Assert.assertNull("Bij HappyFlow hoort de transition 'null' te zijn.", transition);
    }

    @Test
    public void testFoutGeenSyncBericht() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final String transition = controleerQueryResponseDecision.execute(parameters);
        Assert.assertEquals("5b-2. Fout", transition);
    }

    private Object createSyncBerichtVoorHappyFlow() {
        return new LeesUitBrpAntwoordBericht();
    }

}
