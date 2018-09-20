/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.mapping;

import org.junit.Assert;
import org.junit.Test;

public class RubriekMappingTest {

    @Test
    public void test() {
        Assert.assertNull(RubriekMapping.getExpressiesVoorRubriek("99.99.99"));
        Assert.assertEquals(1, RubriekMapping.getExpressiesVoorRubriek("01.01.10").size());
        Assert.assertEquals(2, RubriekMapping.getExpressiesVoorRubriek("01.02.20").size());

        Assert.assertNull(RubriekMapping.getExpressiesVoorRubriek("01.01.10").get(0).getParent());
        Assert.assertNull(RubriekMapping.getExpressiesVoorRubriek("01.02.20").get(0).getParent());
        Assert.assertNull(RubriekMapping.getExpressiesVoorRubriek("01.02.20").get(1).getParent());
        Assert.assertEquals("nationaliteiten", RubriekMapping.getExpressiesVoorRubriek("04.05.10").get(0).getParent());
        Assert.assertNotNull(RubriekMapping.getExpressiesVoorRubriek("05.06.30").get(0).getParent());
        Assert.assertNotNull(RubriekMapping.getExpressiesVoorRubriek("05.06.30").get(1).getParent());
    }
}
