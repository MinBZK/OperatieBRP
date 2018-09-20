/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import static org.junit.Assert.*;
import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;

import org.junit.Test;

/**
 * Test het contract van BrpVoornaamInhoud.
 * 
 */
public class BrpVoornaamInhoudTest {

    @Test
    public void testHashCode() {
        final BrpVoornaamInhoud inhoud1 = new BrpVoornaamInhoud("Jan", 1);
        final BrpVoornaamInhoud inhoud2 = new BrpVoornaamInhoud("Jan", 1);
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testBrpVoornaamInhoud() {
        new BrpVoornaamInhoud("Jan", 1);
    }

    @Test
    public void testBrpVoornaamInhoud2() {
        new BrpVoornaamInhoud(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBrpVoornaamInhoudFout1() {
        new BrpVoornaamInhoud("", 1);
    }

    @Test
    @Preconditie(Precondities.PRE019)
    public void testBrpVoornaamInhoudFout2() {
        try {
            new BrpVoornaamInhoud("Jan ", 1);
            fail("Exceptie verwacht omdat er een spatie in de voornaam voorkomt.");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE019.name()));
        }
    }

    @Test
    @Preconditie(Precondities.PRE019)
    public void testBrpVoornaamInhoudFout3() {
        try {
            new BrpVoornaamInhoud(" Jan", 1);
            fail("Exceptie verwacht omdat er een spatie in de voornaam voorkomt.");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE019.name()));
        }
    }

    @Test
    @Preconditie(Precondities.PRE019)
    public void testBrpVoornaamInhoudFout4() {
        try {
            new BrpVoornaamInhoud(" ", 1);
            fail("Exceptie verwacht omdat er een spatie in de voornaam voorkomt.");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE019.name()));
        }
    }

    @Test
    @Preconditie(Precondities.PRE019)
    public void testBrpVoornaamInhoudFout5() {
        try {
            new BrpVoornaamInhoud("Ja n", 1);
            fail("Exceptie verwacht omdat er een spatie in de voornaam voorkomt.");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE019.name()));
        }
    }

    @Test
    public void testIsLeeg() {
        final BrpVoornaamInhoud leeg = new BrpVoornaamInhoud(null, 1);
        final BrpVoornaamInhoud nietLeeg = new BrpVoornaamInhoud("Jan", 1);
        assertTrue(leeg.isLeeg());
        assertFalse(nietLeeg.isLeeg());
    }

    @Test
    public void testGetVoornaam() {
        final BrpVoornaamInhoud inhoud = new BrpVoornaamInhoud("Jan", 1);
        assertEquals("Jan", inhoud.getVoornaam());
    }

    @Test
    public void testGetVolgnummer() {
        final BrpVoornaamInhoud inhoud = new BrpVoornaamInhoud("Jan", 1);
        assertEquals(1, inhoud.getVolgnummer());
    }

    @Test
    public void testEqualsObject() {
        final BrpVoornaamInhoud inhoud1 = new BrpVoornaamInhoud("Jan", 1);
        final BrpVoornaamInhoud inhoud2 = new BrpVoornaamInhoud("Jan", 1);
        final BrpVoornaamInhoud inhoud3 = new BrpVoornaamInhoud("Jantje", 1);
        final BrpVoornaamInhoud inhoud4 = new BrpVoornaamInhoud("Jan", 2);

        assertTrue(inhoud1.equals(inhoud2));
        assertFalse(inhoud1.equals(inhoud3));
        assertFalse(inhoud1.equals(inhoud4));
    }

}
