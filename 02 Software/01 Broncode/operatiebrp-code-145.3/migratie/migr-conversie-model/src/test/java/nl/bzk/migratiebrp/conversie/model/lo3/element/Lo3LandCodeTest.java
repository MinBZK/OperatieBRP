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
 * Test het contract van Lo3LandCode.
 */
public class Lo3LandCodeTest {

    public static final String STRING_1234 = "1234";

    @Test
    public void testHashCode() {
        final Lo3LandCode landCode = new Lo3LandCode(STRING_1234);
        final Lo3LandCode landCode2 = new Lo3LandCode(STRING_1234);
        assertEquals(landCode.hashCode(), landCode2.hashCode());
    }

    @Test
    public void testLo3LandCode() {
        new Lo3LandCode(STRING_1234);
    }

    @Test
    public void testGetCode() {
        final Lo3LandCode landCode = new Lo3LandCode(STRING_1234);
        assertEquals(STRING_1234, landCode.getWaarde());
    }

    @Test
    public void testIsNederlandCode() {
        final Lo3LandCode landCode = new Lo3LandCode("6030");
        assertTrue(landCode.isNederlandCode());
    }

    @Test
    public void testNietIsNederlandCode() {
        final Lo3LandCode landCode = new Lo3LandCode("6040");
        assertFalse(landCode.isNederlandCode());
    }

    @Test
    public void testIsLandCodeBuitenland() {
        final Lo3LandCode landCode = new Lo3LandCode("6040");
        assertTrue(landCode.isLandCodeBuitenland());
    }

    @Test
    public void testIsNietLandCodeBuitenland1() {
        final Lo3LandCode landCode = new Lo3LandCode("6030");
        assertFalse(landCode.isLandCodeBuitenland());
    }

    @Test
    public void testIsNietLandCodeBuitenland2() {
        final Lo3LandCode landCode = new Lo3LandCode("0000");
        assertFalse(landCode.isLandCodeBuitenland());
    }

    @Test
    public void testIsNietLandCodeBuitenland3() {
        final Lo3LandCode landCode = new Lo3LandCode("9999");
        assertFalse(landCode.isLandCodeBuitenland());
    }

    @Test
    public void testEqualsObject() {
        final Lo3LandCode landCode = new Lo3LandCode(STRING_1234);
        final Lo3LandCode landCode2 = new Lo3LandCode(STRING_1234);
        assertTrue(landCode.equals(landCode2));
    }

    @Test
    public void testIsOnbekend() {
        final Lo3LandCode landCode = new Lo3LandCode("0000");
        assertTrue(landCode.isOnbekend());
    }

    @Test
    public void testIsOnbekend2() {
        final Lo3LandCode landCode = new Lo3LandCode("9999");
        assertTrue(landCode.isOnbekend());
    }

    @Test
    public void testIsNietOnbekend() {
        final Lo3LandCode landCode = new Lo3LandCode(STRING_1234);
        assertFalse(landCode.isOnbekend());
    }
}
