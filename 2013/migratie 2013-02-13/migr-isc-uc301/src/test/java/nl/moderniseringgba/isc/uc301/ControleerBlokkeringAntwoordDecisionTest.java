/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerBlokkeringAntwoordDecisionTest {

    private final ControleerBlokkeringAntwoordDecision subject = new ControleerBlokkeringAntwoordDecision();

    @Test
    public void testOk() {
        final BlokkeringAntwoordBericht antwoord = new BlokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.OK);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringAntwoordBericht", antwoord);

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    public void testFoutReedsGeblokkeerd() {
        final BlokkeringAntwoordBericht antwoord = new BlokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.GEBLOKKEERD);
        antwoord.setToelichting("reeds geblokkeerd");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringAntwoordBericht", antwoord);

        Assert.assertEquals("6c. Geblokkeerd", subject.execute(parameters));
    }

    @Test
    public void testAndereFout() {
        final BlokkeringAntwoordBericht antwoord = new BlokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringAntwoordBericht", antwoord);

        Assert.assertEquals("6b. Fout", subject.execute(parameters));
    }

    @Test
    public void testWaarschuwing() {
        final BlokkeringAntwoordBericht antwoord = new BlokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.WAARSCHUWING);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringAntwoordBericht", antwoord);

        Assert.assertEquals("6b. Fout", subject.execute(parameters));
    }
}
