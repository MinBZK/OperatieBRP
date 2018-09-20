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

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;

import org.junit.Test;

/**
 * Test het contract van BrpNationaliteitInhoud.
 * 
 */
public class BrpNationaliteitInhoudTest {

    private final BrpNationaliteitInhoud inhoud1 = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("1234")),
            new BrpRedenVerliesNederlandschapCode(new BigDecimal("1234")));
    private final BrpNationaliteitInhoud inhoud2 = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("1234")),
            new BrpRedenVerliesNederlandschapCode(new BigDecimal("1234")));
    private final BrpNationaliteitInhoud inhoud3 = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("8888")),
            new BrpRedenVerliesNederlandschapCode(new BigDecimal("1234")));
    private final BrpNationaliteitInhoud inhoud4 = new BrpNationaliteitInhoud(null, null,
            new BrpRedenVerliesNederlandschapCode(new BigDecimal("1234")));
    private final BrpNationaliteitInhoud inhoud5 = new BrpNationaliteitInhoud(null, null, null);
    private final BrpNationaliteitInhoud inhoud6 = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("1234")), null);
    private final BrpNationaliteitInhoud inhoud7 = new BrpNationaliteitInhoud(new BrpNationaliteitCode(
            Integer.valueOf("1234")), new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("1235")), null);

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(inhoud1.isLeeg());
        assertTrue(inhoud4.isLeeg());
        assertTrue(inhoud5.isLeeg());
    }

    @Test
    public void testEqualsObject() {
        assertEquals(inhoud1, inhoud2);
        assertNotSame(inhoud1, inhoud3);
    }

    /**
     * Voor de inhoudelijke vergelijking van Nationaliteit moet een uitzondering worden gemaakt. Wanneer nationaliteit X
     * en Y met elkaar worden vergeleken geldt een uitzondering voor de vergelijking van
     * {@link BrpNationaliteitInhoud#getRedenVerliesNederlandschapCode()}. Wanneer bij X of Y de reden verlies nl
     * nationaliteit null is dan wordt deze niet meegenomen in de equals vergelijking van BrpNationaliteitInhoud.
     */
    @Test
    public void testEqualsObjectRedenVerlies() {
        assertEquals(inhoud1, inhoud6);
        assertEquals(inhoud6, inhoud1);
        assertNotSame(inhoud1, inhoud7);
        assertNotSame(inhoud7, inhoud1);
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2.toString());
    }

}
