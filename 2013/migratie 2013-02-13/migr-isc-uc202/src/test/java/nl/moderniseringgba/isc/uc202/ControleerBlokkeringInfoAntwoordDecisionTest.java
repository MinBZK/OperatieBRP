/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerBlokkeringInfoAntwoordDecisionTest {

    private final ControleerBlokkeringInfoAntwoordDecision subject = new ControleerBlokkeringInfoAntwoordDecision();

    @Test
    public void testOkLeeg() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.OK);
        blokkeringInfoAntwoord.setPersoonsaanduiding(null);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringInfoAntwoordBericht", blokkeringInfoAntwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testOkVerhuizendNaarGba() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.OK);
        blokkeringInfoAntwoord.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringInfoAntwoordBericht", blokkeringInfoAntwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testOkVerhuizendNaarRni() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.OK);
        blokkeringInfoAntwoord.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_RNI);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringInfoAntwoordBericht", blokkeringInfoAntwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testNokVerhuizendNaarBrp() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.OK);
        blokkeringInfoAntwoord.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringInfoAntwoordBericht", blokkeringInfoAntwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals("7c. Blokkeringssituatie fout", result);
    }

    @Test
    public void testNokFout() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.FOUT);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("blokkeringInfoAntwoordBericht", blokkeringInfoAntwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals("7b. Fout", result);
    }
}
