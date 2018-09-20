/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BmrElementSoortTest {

    @Test
    public void testGetBmrElementSoortBijCode() throws Exception {
        assertEquals(BmrElementSoort.TUPLE, BmrElementSoort.getBmrElementSoortBijCode("AC"));
    }

    @Test
    public void testHoortBij() throws Exception {
        assertTrue(BmrElementSoort.TUPLE.hoortBijCode("AC"));
        assertFalse(BmrElementSoort.TUPLE.hoortBijCode("OT"));
        assertFalse(BmrElementSoort.TUPLE.hoortBijCode("XX"));
    }

    @Test
    public void testGetUniekeCode() {
        assertEquals("OT", BmrElementSoort.OBJECTTYPE.getUniekeCode());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetUniekeCodeIndienMeerdereCodes() {
        BmrElementSoort.TUPLE.getUniekeCode();
    }
}
