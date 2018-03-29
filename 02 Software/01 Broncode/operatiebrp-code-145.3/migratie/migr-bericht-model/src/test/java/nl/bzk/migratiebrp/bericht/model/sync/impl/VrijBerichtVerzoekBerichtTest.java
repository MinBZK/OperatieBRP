/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import org.junit.Assert;
import org.junit.Test;

public class VrijBerichtVerzoekBerichtTest extends AbstractSyncBerichtTestBasis {

    @Test
    public void test() throws Exception {
        final VrijBerichtVerzoekBericht bericht = new VrijBerichtVerzoekBericht();
        bericht.setVerzendendePartij("062601");
        bericht.setOntvangendePartij("059901");
        bericht.setReferentienummer("12345");
        bericht.setBericht("BERICHT");

        Assert.assertEquals("062601", bericht.getVerzendendePartij());
        Assert.assertEquals("059901", bericht.getOntvangendePartij());
        Assert.assertEquals("12345", bericht.getReferentienummer());
        Assert.assertEquals("BERICHT", bericht.getBericht());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }
}
