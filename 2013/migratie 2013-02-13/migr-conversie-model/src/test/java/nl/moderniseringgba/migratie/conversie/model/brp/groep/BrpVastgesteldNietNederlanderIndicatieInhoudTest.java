/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test het contract van BrpVastgesteldNietNederlanderIndicatieInhoud.
 * 
 */
public class BrpVastgesteldNietNederlanderIndicatieInhoudTest {

    private BrpVastgesteldNietNederlanderIndicatieInhoud inhoud1;
    private BrpVastgesteldNietNederlanderIndicatieInhoud inhoud2;

    @Before
    public void setup() {
        inhoud1 = new BrpVastgesteldNietNederlanderIndicatieInhoud(true);
        inhoud2 = new BrpVastgesteldNietNederlanderIndicatieInhoud(true);
    }

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testHeeftIndicatie() {
        assertTrue(inhoud1.getHeeftIndicatie());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(inhoud1.isLeeg());
    }

    @Test
    public void testEqualsObject() {
        assertEquals(inhoud1, inhoud2);
        assertNotSame(inhoud1, new Object());
        assertEquals(inhoud1, inhoud1);
        assertNotSame(inhoud1, null);
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2.toString());
    }

}
