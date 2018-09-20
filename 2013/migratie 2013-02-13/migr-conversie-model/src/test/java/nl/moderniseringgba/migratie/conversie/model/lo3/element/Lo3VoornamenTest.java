/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test het contract van Lo3Voornamen.
 * 
 */
public class Lo3VoornamenTest {

    @Test
    public void testHashCode() {
        final Lo3Voornamen voornamen = new Lo3Voornamen("Anne Chris Jean-Jopie");
        final Lo3Voornamen voornamen2 = new Lo3Voornamen("Anne Chris Jean-Jopie");
        assertEquals(voornamen.hashCode(), voornamen2.hashCode());
    }

    @Test
    public void testLo3Voornamen() {
        new Lo3Voornamen("Anne Chris Jean-Jopie");
    }

    @Test
    public void testLo3Voorname2() {
        new Lo3Voornamen("A");
    }

    @Test
    public void testLo3Voorname3() {
        new Lo3Voornamen(maakTestStringMetLengte(200));
    }

    @Test(expected = NullPointerException.class)
    public void testLo3VoornamenFout1() {
        new Lo3Voornamen(null);
    }

    @Test
    public void testGetVoornamen() {
        final Lo3Voornamen voornamen = new Lo3Voornamen("Anne Chris Jean-Jopie");
        assertEquals("Anne Chris Jean-Jopie", voornamen.getVoornamen());
    }

    @Test
    public void testGetVoornamenAsArray() {
        final Lo3Voornamen voornamen = new Lo3Voornamen("Anne Chris Jean-Jopie");
        assertArrayEquals(new String[] { "Anne", "Chris", "Jean-Jopie" }, voornamen.getVoornamenAsArray());
    }

    @Test
    public void testGetVoornamenMetDubbeleSpatiesAsArray() {
        final Lo3Voornamen voornamen = new Lo3Voornamen("Anne Chris  Jean-Jopie");
        assertArrayEquals(new String[] { "Anne", "Chris", "Jean-Jopie" }, voornamen.getVoornamenAsArray());
    }

    @Test
    public void testEqualsObject() {
        final Lo3Voornamen voornamen = new Lo3Voornamen("Anne Chris Jean-Jopie");
        final Lo3Voornamen voornamen2 = new Lo3Voornamen("Anne Chris Jean-Jopie");
        assertTrue(voornamen.equals(voornamen2));
    }

    private String maakTestStringMetLengte(final int lengte) {
        final char[] chars = new char[lengte];
        for (int index = 0; index < lengte; index++) {
            chars[index] = 'a';
        }
        return new String(chars);
    }
}
