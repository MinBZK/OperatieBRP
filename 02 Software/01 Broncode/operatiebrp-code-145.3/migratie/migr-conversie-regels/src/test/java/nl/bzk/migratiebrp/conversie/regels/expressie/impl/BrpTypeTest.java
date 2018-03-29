/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test.
 */
public class BrpTypeTest {

    @Test
    public void testBrpType() {
        final BrpType brpType = new BrpType("type", true, false);
        final BrpType brpType2 = new BrpType("type", true, false);
        Assert.assertEquals("type", brpType.getType());
        Assert.assertTrue(brpType.isLijst());
        Assert.assertFalse(brpType.isInverse());
        Assert.assertFalse(brpType.equals(null));
        Assert.assertFalse(brpType.equals("String"));
        Assert.assertTrue(brpType.equals(brpType2));
        Assert.assertEquals(brpType.hashCode(),brpType2.hashCode());
    }
}
