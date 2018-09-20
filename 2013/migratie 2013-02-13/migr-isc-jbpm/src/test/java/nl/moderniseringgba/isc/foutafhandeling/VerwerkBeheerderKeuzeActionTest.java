/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.moderniseringgba.isc.jbpm.jsf.FoutafhandelingPaden;

import org.junit.Test;

public class VerwerkBeheerderKeuzeActionTest {

    private FoutafhandelingPaden maakPaden() {
        final FoutafhandelingPaden result = new FoutafhandelingPaden();
        result.put("end", "Afbreken", true, true, true);
        result.put("restart", "Opnieuw", false, false, false);
        return result;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenInput() {
        new VerwerkBeheerderKeuzeAction().execute(Collections.<String, Object>emptyMap());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenPaden() {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("restart", "end");
        new VerwerkBeheerderKeuzeAction().execute(params);
    }

    @Test
    public void testKeuzeEnd() {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("restart", "end");
        params.put("foutafhandelingPaden", maakPaden());

        final Map<String, Object> result = new VerwerkBeheerderKeuzeAction().execute(params);

        Assert.assertEquals(3, result.size());
        Assert.assertEquals(Boolean.TRUE, result.get(FoutafhandelingConstants.INDICATIE_PF));
        Assert.assertEquals(Boolean.TRUE, result.get(FoutafhandelingConstants.INDICATIE_DEBLOKKERING));
        Assert.assertEquals(Boolean.TRUE, result.get(FoutafhandelingConstants.INDICATIE_ANTWOORD));
    }

    @Test
    public void testKeuzeRestart() {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("restart", "restart");
        params.put("foutafhandelingPaden", maakPaden());

        final Map<String, Object> result = new VerwerkBeheerderKeuzeAction().execute(params);

        Assert.assertEquals(3, result.size());
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_PF));
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_DEBLOKKERING));
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_ANTWOORD));
    }

}
