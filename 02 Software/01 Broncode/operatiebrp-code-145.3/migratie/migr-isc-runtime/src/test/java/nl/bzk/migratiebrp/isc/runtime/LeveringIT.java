/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Gv01Bericht;
import org.junit.Assert;
import org.junit.Test;

public class LeveringIT extends AbstractIT {

    @Test
    public void testLevering() throws BerichtSyntaxException, BerichtInhoudException {
        final Gv01Bericht gvBericht = new Gv01Bericht();
        gvBericht.setDoelPartijCode("0599");
        gvBericht.parse("00000000Gv011234567890MEUK");

        putMessage(LEVERING_QUEUE, gvBericht, null, 1234L);

        // Verwacht: bericht op VOISC
        final Gv01Bericht result = expectMessage(VOISC_VERZENDEN_QUEUE, Gv01Bericht.class);
        Assert.assertNotNull("Gv01 verwacht", result);
        Assert.assertNotNull("Verzendende partij verwacht", result.getBronPartijCode());
        Assert.assertEquals(gvBericht.format(), result.format());
    }

}
