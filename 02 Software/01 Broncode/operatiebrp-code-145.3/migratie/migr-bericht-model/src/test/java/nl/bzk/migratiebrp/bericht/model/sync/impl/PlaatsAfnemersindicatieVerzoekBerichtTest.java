/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import org.junit.Assert;
import org.junit.Test;

public class PlaatsAfnemersindicatieVerzoekBerichtTest extends AbstractSyncBerichtTestBasis {

    private static final String BSN = "123456789";
    private static final String PARTIJ_CODE = "059901";

    @Test
    public void test() throws Exception {
        final PlaatsAfnemersindicatieVerzoekBericht bericht = new PlaatsAfnemersindicatieVerzoekBericht();
        bericht.setBsn(BSN);
        bericht.setPartijCode(PARTIJ_CODE);
        bericht.setReferentie("REF-57756");

        Assert.assertEquals(BSN, bericht.getBsn());
        bericht.setPartijCode(PARTIJ_CODE);
        Assert.assertEquals("REF-57756", bericht.getReferentie());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }

}
