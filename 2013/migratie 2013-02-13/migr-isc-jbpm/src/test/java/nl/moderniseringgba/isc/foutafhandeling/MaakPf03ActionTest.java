/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OnbekendBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OngeldigBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf02Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf03Bericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakPf03ActionTest {

    @Test
    public void testPf01() {
        final Lo3Bericht lo3Bericht = new OnbekendBericht("00000000Xx2100000", "onbekend");

        final Map<String, Object> variabelen = new HashMap<String, Object>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, lo3Bericht);

        final Map<String, Object> result = new MaakPfAction().execute(variabelen);
        Assert.assertEquals(2, result.size());
        final Pf01Bericht pfBericht = (Pf01Bericht) result.get(FoutafhandelingConstants.BERICHT_PF);
        Assert.assertNotNull(pfBericht);
        Assert.assertEquals(lo3Bericht.getMessageId(), pfBericht.getCorrelationId());
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_VB01));
    }

    @Test
    public void testPf02() {
        final Lo3Bericht lo3Bericht = new OngeldigBericht("sdas", "ongeldig");

        final Map<String, Object> variabelen = new HashMap<String, Object>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, lo3Bericht);

        final Map<String, Object> result = new MaakPfAction().execute(variabelen);
        Assert.assertEquals(2, result.size());
        final Pf02Bericht pfBericht = (Pf02Bericht) result.get(FoutafhandelingConstants.BERICHT_PF);
        Assert.assertNotNull(pfBericht);
        Assert.assertEquals(lo3Bericht.getMessageId(), pfBericht.getCorrelationId());
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_VB01));
    }

    @Test
    public void testPf03() {
        final Lo3Bericht lo3Bericht = new NullBericht();

        final Map<String, Object> variabelen = new HashMap<String, Object>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, lo3Bericht);

        final Map<String, Object> result = new MaakPfAction().execute(variabelen);
        Assert.assertEquals(2, result.size());
        final Pf03Bericht pf03Bericht = (Pf03Bericht) result.get(FoutafhandelingConstants.BERICHT_PF);
        Assert.assertNotNull(pf03Bericht);
        Assert.assertEquals(lo3Bericht.getMessageId(), pf03Bericht.getCorrelationId());
        Assert.assertEquals(Boolean.TRUE, result.get(FoutafhandelingConstants.INDICATIE_VB01));
    }
}
