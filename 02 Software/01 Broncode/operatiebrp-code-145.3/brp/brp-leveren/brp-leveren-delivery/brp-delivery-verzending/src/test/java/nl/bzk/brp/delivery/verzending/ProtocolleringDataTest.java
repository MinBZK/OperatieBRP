/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import org.junit.Assert;
import org.junit.Test;

/**
 * ProtocolleringDataTest.
 */
public class ProtocolleringDataTest {

    @Test
    public void testProtocolleringDataEquals() {
        final ProtocolleringData protocolleringData1 = new ProtocolleringData(Protocolleringsniveau.GEEN_BEPERKINGEN, "1", "2");
        final ProtocolleringData protocolleringData2 = new ProtocolleringData(Protocolleringsniveau.GEEN_BEPERKINGEN, "1", "2");
        final ProtocolleringData protocolleringData3 = new ProtocolleringData(Protocolleringsniveau.GEHEIM, "1", "2");
        final ProtocolleringData protocolleringData4 = new ProtocolleringData(Protocolleringsniveau.GEHEIM, "1", "3");
        final ProtocolleringData protocolleringData5 = new ProtocolleringData(Protocolleringsniveau.GEHEIM, "2", "2");

        Assert.assertEquals(protocolleringData1, protocolleringData1);
        Assert.assertEquals(protocolleringData1, protocolleringData2);
        Assert.assertNotEquals(protocolleringData1, protocolleringData3);
        Assert.assertNotEquals(protocolleringData1, null);
        Assert.assertNotEquals(protocolleringData1, protocolleringData1.getProtocolleringNiveau());
        Assert.assertNotEquals(protocolleringData3, protocolleringData4);
        Assert.assertNotEquals(protocolleringData3, protocolleringData5);
    }

    @Test
    public void testProtocolleringData() {
        final ProtocolleringData protocolleringData1 = new ProtocolleringData(Protocolleringsniveau.GEEN_BEPERKINGEN, "1", "2");
        final ProtocolleringData protocolleringData2 = new ProtocolleringData(Protocolleringsniveau.GEEN_BEPERKINGEN, "1", "2");

        Assert.assertEquals(protocolleringData1, protocolleringData1);
        Assert.assertEquals(protocolleringData1, protocolleringData2);
        Assert.assertEquals("1", protocolleringData1.getLeveringsautorisatieId());
        Assert.assertEquals("2", protocolleringData1.getPartijCode());
        Assert.assertEquals(Protocolleringsniveau.GEEN_BEPERKINGEN, protocolleringData1.getProtocolleringNiveau());
    }
}
