/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor AdHocZoekPersoonAntwoordBericht.
 */
public class VrijBerichtAntwoordBerichtTest extends AbstractSyncBerichtTestBasis {

    @Test
    public void testGeslaagd() throws Exception {
        VrijBerichtAntwoordBericht bericht = new VrijBerichtAntwoordBericht();
        bericht.setReferentienummer("12345");
        bericht.setStatus(true);

        Assert.assertEquals("Ok", bericht.getStatus());
        Assert.assertEquals("12345", bericht.getReferentienummer());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }

    @Test
    public void testNietGeslaagd() throws Exception {
        VrijBerichtAntwoordBericht bericht = new VrijBerichtAntwoordBericht();
        bericht.setReferentienummer("12345");
        bericht.setStatus(false);

        Assert.assertEquals("Fout", bericht.getStatus());
        Assert.assertEquals("12345", bericht.getReferentienummer());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }

}
