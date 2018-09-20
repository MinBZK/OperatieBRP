/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.BrpPlType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarBrpAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerConverteerNaarBrpAntwoordDecisionTest {

    private final ControleerConverteerNaarBrpAntwoordDecision sut = new ControleerConverteerNaarBrpAntwoordDecision();

    @Test
    public void testHappyFlow() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("syncBericht", createSyncBerichtVoorHappyFlow());

        final String transition = sut.execute(parameters);
        Assert.assertNull("Bij HappyFlow hoort de transition 'null' te zijn.", transition);
    }

    @Test
    public void testFoutGeenSyncBericht() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final String transition = sut.execute(parameters);
        Assert.assertEquals("Conversie mislukt", transition);
    }

    @Test
    public void testGeenStatusOK() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("syncBericht", createSyncBerichtVoorGeenStatusOK());

        final String transition = sut.execute(parameters);
        Assert.assertEquals("Conversie mislukt", transition);
    }

    private Object createSyncBerichtVoorHappyFlow() {
        final ConverteerNaarBrpAntwoordType type = new ConverteerNaarBrpAntwoordType();
        type.setStatus(StatusType.OK);
        final BrpPlType brpPlType = new BrpPlType();

        // normaal zouden we hier brpPlType.getAny().add( ... ) doen, maar daar wordt niet op gecontroleerd...
        type.setBrpPl(brpPlType);

        return new ConverteerNaarBrpAntwoordBericht(type);
    }

    private Object createSyncBerichtVoorGeenStatusOK() {
        final ConverteerNaarBrpAntwoordType type = new ConverteerNaarBrpAntwoordType();
        type.setStatus(StatusType.FOUT);

        return new ConverteerNaarBrpAntwoordBericht(type);
    }

}
