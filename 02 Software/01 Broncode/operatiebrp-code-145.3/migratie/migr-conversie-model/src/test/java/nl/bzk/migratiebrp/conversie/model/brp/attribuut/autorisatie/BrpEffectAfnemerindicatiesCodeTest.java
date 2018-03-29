/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BrpEffectAfnemerindicatiesCodeTest {

    @Test
    public void testEquals() throws Exception {
        assertTrue(BrpEffectAfnemerindicatiesCode.PLAATSEN.equals(BrpEffectAfnemerindicatiesCode.PLAATSEN));
        assertFalse(BrpEffectAfnemerindicatiesCode.PLAATSEN.equals(null));
        assertFalse(BrpEffectAfnemerindicatiesCode.PLAATSEN.equals(BrpEffectAfnemerindicatiesCode.VERWIJDEREN));
    }

    @Test(expected = NullPointerException.class)
    public void testCompareToNull() {

        BrpEffectAfnemerindicatiesCode.PLAATSEN.compareTo(null);
    }

    @Test
    public void testCompareTo() {
        BrpEffectAfnemerindicatiesCode result = BrpEffectAfnemerindicatiesCode.PLAATSEN;
        BrpEffectAfnemerindicatiesCode result2 = new BrpEffectAfnemerindicatiesCode(Short.parseShort("0"));
        BrpEffectAfnemerindicatiesCode result3 = new BrpEffectAfnemerindicatiesCode(Short.parseShort("1"));
        BrpEffectAfnemerindicatiesCode result4 = new BrpEffectAfnemerindicatiesCode(Short.parseShort("432"));
        assertEquals(1, result.compareTo(result2));
        assertEquals(0, result.compareTo(result3));
        assertEquals(-1, result.compareTo(result4));
    }

}
