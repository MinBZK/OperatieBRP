/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import org.junit.Assert;
import org.junit.Test;

public class PlaatsAfnemersindicatieVerzoekBerichtTest extends AbstractSyncBerichtTest {

    @Test
    public void test() throws Exception {
        final PlaatsAfnemersindicatieVerzoekBericht bericht = new PlaatsAfnemersindicatieVerzoekBericht();
        bericht.setPersoonId(123);
        bericht.setToegangLeveringsautorisatieId(565455);
        bericht.setDienstId(532);
        bericht.setReferentie("REF-57756");

        Assert.assertEquals(Integer.valueOf(123), bericht.getPersoonId());
        Assert.assertEquals(Integer.valueOf(565455), bericht.getToegangLeveringsautorisatieId());
        Assert.assertEquals(Integer.valueOf(532), bericht.getDienstId());
        Assert.assertEquals("REF-57756", bericht.getReferentie());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }

}
