/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf03Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Vb01Bericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakVb01ActionTest {

    @Test
    public void test() {

        final Lo3Bericht pf03Bericht = new Pf03Bericht();
        pf03Bericht.setBronGemeente("0399");
        pf03Bericht.setDoelGemeente("0499");

        final String fout = "U";
        final String foutmelding = "De gezochte PL is niet uniek. Controleer de zoekparameters.";

        final Map<String, Object> variabelen = new HashMap<String, Object>();
        variabelen.put(FoutafhandelingConstants.BERICHT_PF, pf03Bericht);
        variabelen.put(FoutafhandelingConstants.FOUT, fout);
        variabelen.put(FoutafhandelingConstants.FOUTMELDING, foutmelding);

        final Map<String, Object> result = new MaakVb01Action().execute(variabelen);
        Assert.assertEquals(1, result.size());
        final Vb01Bericht vb01Bericht = (Vb01Bericht) result.get(FoutafhandelingConstants.BERICHT_VB01);
        Assert.assertNotNull(vb01Bericht);
        Assert.assertEquals("Correlatie komt niet overeen", pf03Bericht.getMessageId(),
                vb01Bericht.getCorrelationId());
        Assert.assertEquals("Brongemeente komt niet overeen", pf03Bericht.getBronGemeente(),
                vb01Bericht.getBronGemeente());
        Assert.assertEquals("Doelgemeente komt niet overeen", pf03Bericht.getDoelGemeente(),
                vb01Bericht.getDoelGemeente());
    }
}
