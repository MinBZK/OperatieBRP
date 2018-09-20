/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;

import org.junit.Before;
import org.junit.Test;

public class BrpGeslachtsnaamcomponentInhoudTest {

    private BrpGeslachtsnaamcomponentInhoud testInhoud;

    @Before
    public void setup() {
        testInhoud =
                new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, new BrpAdellijkeTitelCode("AT"),
                        1);
    }

    @Test
    public void testHashCode() {
        BrpGeslachtsnaamcomponentInhoud inhoud1 =
                new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, new BrpAdellijkeTitelCode("AT"),
                        1);
        BrpGeslachtsnaamcomponentInhoud inhoud2 =
                new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, new BrpAdellijkeTitelCode("AT"),
                        1);
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testBrpGeslachtsnaamcomponentInhoud() {
        new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, new BrpAdellijkeTitelCode("AT"), 1);
    }

    /**
     * Naam mag niet null zijn.
     */
    @Test(expected = NullPointerException.class)
    public void testBrpGeslachtsnaamcomponentInhoudFout1() {
        new BrpGeslachtsnaamcomponentInhoud("van", ' ', null, null, new BrpAdellijkeTitelCode("AT"), 1);
    }

    /**
     * Lengte van naam moet groter of gelijk zijn aan 1
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBrpGeslachtsnaamcomponentInhoudFout2() {
        new BrpGeslachtsnaamcomponentInhoud("van", ' ', "", null, new BrpAdellijkeTitelCode("AT"), 1);
    }

    @Test
    public void testGetVoorvoegsel() {
        assertEquals("van", testInhoud.getVoorvoegsel());
    }

    @Test
    public void testGetScheidingsteken() {
        assertEquals(Character.valueOf(' '), testInhoud.getScheidingsteken());
    }

    @Test
    public void testGetNaam() {
        assertEquals("HorenZeggen", testInhoud.getNaam());
    }

    @Test
    public void testGetPredikaatCode() {
        assertNull(testInhoud.getPredikaatCode());
    }

    @Test
    public void testGetAdellijkeTitelCode() {
        assertEquals(new BrpAdellijkeTitelCode("AT"), testInhoud.getAdellijkeTitelCode());
    }

    @Test
    public void testGetVolgnummer() {
        assertEquals(1, testInhoud.getVolgnummer());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(testInhoud.isLeeg());
    }

    @Test
    public void testEqualsObject() {
        BrpGeslachtsnaamcomponentInhoud inhoud1 =
                new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, new BrpAdellijkeTitelCode("AT"),
                        1);
        BrpGeslachtsnaamcomponentInhoud inhoud2 =
                new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, new BrpAdellijkeTitelCode("AT"),
                        1);
        assertEquals(inhoud1, inhoud2);
    }

    @Test
    public void testToString() {
        BrpGeslachtsnaamcomponentInhoud inhoud1 =
                new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, new BrpAdellijkeTitelCode("AT"),
                        1);
        BrpGeslachtsnaamcomponentInhoud inhoud2 =
                new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, new BrpAdellijkeTitelCode("AT"),
                        1);
        assertEquals(inhoud1.toString(), inhoud2.toString());
    }

}
