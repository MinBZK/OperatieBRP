/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;

import org.junit.Test;

public class ControleerQueryResponseDecisionTest {

    private final ControleerLeesUitBrpAntwoordDecision subject = new ControleerLeesUitBrpAntwoordDecision();

    @Test
    public void testNull() {
        final String result = subject.execute(null);
        Assert.assertEquals("8b-2. Fout", result);
    }

    @Test
    public void testLeeg() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final String result = subject.execute(parameters);
        Assert.assertEquals("8b-2. Fout", result);
    }

    @Test
    public void testOk() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht();
        queryResponse.setStatus(StatusType.OK);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        final String result = subject.execute(parameters);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testFout() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht();
        queryResponse.setStatus(StatusType.FOUT);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        final String result = subject.execute(parameters);
        Assert.assertEquals("8b-2. Fout", result);
    }

}
