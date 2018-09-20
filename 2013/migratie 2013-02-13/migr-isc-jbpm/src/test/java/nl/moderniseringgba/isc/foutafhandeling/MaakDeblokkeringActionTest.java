/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakDeblokkeringActionTest {

    @Test
    public void test() {
        final BlokkeringVerzoekBericht blokkering = new BlokkeringVerzoekBericht();
        blokkering.setANummer("2349634257934");

        final Map<String, Object> variabelen = new HashMap<String, Object>();
        variabelen.put(FoutafhandelingConstants.BERICHT_BLOKKERING, blokkering);

        final Map<String, Object> result = new MaakDeblokkeringAction().execute(variabelen);
        Assert.assertEquals(1, result.size());
        final DeblokkeringVerzoekBericht deblokkering =
                (DeblokkeringVerzoekBericht) result.get(FoutafhandelingConstants.BERICHT_VERZOEK_DEBLOKKERING);
        Assert.assertNotNull(deblokkering);
        Assert.assertEquals(blokkering.getMessageId(), deblokkering.getCorrelationId());
        Assert.assertEquals(blokkering.getANummer(), deblokkering.getANummer());
    }
}
