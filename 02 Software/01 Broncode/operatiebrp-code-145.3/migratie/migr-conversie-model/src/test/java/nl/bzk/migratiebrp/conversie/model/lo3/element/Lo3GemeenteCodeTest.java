/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test het contract van Lo3GemeenteCode.
 */
public class Lo3GemeenteCodeTest {

    public static final String TEST_HASH_CODE = "testHashCode";

    @Test
    public void testHashCode() {
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode(TEST_HASH_CODE);
        final Lo3GemeenteCode gemeenteCode2 = new Lo3GemeenteCode(TEST_HASH_CODE);
        assertEquals(gemeenteCode.hashCode(), gemeenteCode2.hashCode());
    }

    @Test
    public void testLo3GemeenteCode() {
        new Lo3GemeenteCode("testLo3GemeenteCode");
    }

    @Test
    public void testGetCode() {
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode("testGetCode");
        assertEquals("testGetCode", gemeenteCode.getWaarde());
    }

    @Test
    public void testIsValideNederlandsGemeenteCode() {
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode("1234");
        assertTrue(gemeenteCode.isValideNederlandseGemeenteCode());
    }

    @Test
    public void testIsValideNederlandsGemeenteCode2() {
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode("0000");
        assertTrue(gemeenteCode.isValideNederlandseGemeenteCode());
    }

    @Test
    public void testIsNietValideNederlandsGemeenteCode() {
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode("a123");
        assertFalse(gemeenteCode.isValideNederlandseGemeenteCode());
    }

    @Test
    public void testIsNietValideNederlandsGemeenteCode2() {
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode("123a");
        assertFalse(gemeenteCode.isValideNederlandseGemeenteCode());
    }

    @Test
    public void testIsNietValideNederlandsGemeenteCode3() {
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode("123");
        assertFalse(gemeenteCode.isValideNederlandseGemeenteCode());
    }

    @Test
    public void testEqualsObject() {
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode(TEST_HASH_CODE);
        final Lo3GemeenteCode gemeenteCode2 = new Lo3GemeenteCode(TEST_HASH_CODE);
        assertTrue(gemeenteCode.equals(gemeenteCode2));
    }
}
