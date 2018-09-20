/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerTv01AntwoordDecisionTest {

    private static final String BERICHT_NAAM = "verzendenTv01AntwoordBericht";

    private final ControleerTv01AntwoordDecision controleerTv01AntwoordDecision =
            new ControleerTv01AntwoordDecision();

    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BERICHT_NAAM, new NullBericht());

        final String transition = controleerTv01AntwoordDecision.execute(parameters);
        Assert.assertNull("Bij HappyFlow hoort de transition 'null' te zijn.", transition);
    }

    @Test
    public void testTf11Bericht() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BERICHT_NAAM, new Tf11Bericht());

        final String transition = controleerTv01AntwoordDecision.execute(parameters);
        Assert.assertEquals("Bij een Tf11 bericht hoort de alternatieve flow gevolgd te worden.",
                UC307Constants.TF11_BERICHT, transition);
    }
}
