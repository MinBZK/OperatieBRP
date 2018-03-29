/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.junit.Before;
import org.junit.Test;

public class BrpGeslachtsnaamcomponentInhoudTest {

    public static final String VAN = "van";
    public static final String HOREN_ZEGGEN = "HorenZeggen";
    public static final String AT = "AT";
    private BrpGeslachtsnaamcomponentInhoud testInhoud;

    public static BrpGeslachtsnaamcomponentInhoud createInhoud() {
        return createInhoud(1);
    }
    public static BrpGeslachtsnaamcomponentInhoud createInhoud(final int volgnummer) {
        return new BrpGeslachtsnaamcomponentInhoud(
                new BrpString(VAN),
                new BrpCharacter(' '),
                new BrpString(HOREN_ZEGGEN),
                null,
                new BrpAdellijkeTitelCode(AT),
                new BrpInteger(volgnummer));
    }

    public static BrpStapel<BrpGeslachtsnaamcomponentInhoud> createStapel() {
        List<BrpGroep<BrpGeslachtsnaamcomponentInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpGeslachtsnaamcomponentInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    public static List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> createList() {
        List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> lijst = new ArrayList<>();
        lijst.add(createStapel());
        return lijst;
    }

    @Before
    public void setup() {
        testInhoud =
                new BrpGeslachtsnaamcomponentInhoud(
                        new BrpString(VAN),
                        new BrpCharacter(' '),
                        new BrpString(HOREN_ZEGGEN),
                        null,
                        new BrpAdellijkeTitelCode(AT),
                        new BrpInteger(1));
    }

    @Test
    public void testHashCode() {
        final BrpGeslachtsnaamcomponentInhoud inhoud1 =
                new BrpGeslachtsnaamcomponentInhoud(
                        new BrpString(VAN),
                        new BrpCharacter(' '),
                        new BrpString(HOREN_ZEGGEN),
                        null,
                        new BrpAdellijkeTitelCode(AT),
                        new BrpInteger(1));
        final BrpGeslachtsnaamcomponentInhoud inhoud2 =
                new BrpGeslachtsnaamcomponentInhoud(
                        new BrpString(VAN),
                        new BrpCharacter(' '),
                        new BrpString(HOREN_ZEGGEN),
                        null,
                        new BrpAdellijkeTitelCode(AT),
                        new BrpInteger(1));
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testBrpGeslachtsnaamcomponentInhoud() {
        new BrpGeslachtsnaamcomponentInhoud(
                new BrpString(VAN),
                new BrpCharacter(' '),
                new BrpString(HOREN_ZEGGEN),
                null,
                new BrpAdellijkeTitelCode(AT),
                new BrpInteger(1));
    }

    /**
     * Naam mag niet null zijn.
     */
    @Test(expected = NullPointerException.class)
    public void testBrpGeslachtsnaamcomponentInhoudFout1() {
        new BrpGeslachtsnaamcomponentInhoud(new BrpString(VAN), new BrpCharacter(' '), null, null, new BrpAdellijkeTitelCode(AT), new BrpInteger(1));
    }

    /**
     * Lengte van naam moet groter of gelijk zijn aan 1
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBrpGeslachtsnaamcomponentInhoudFout2() {
        new BrpGeslachtsnaamcomponentInhoud(
                new BrpString(VAN),
                new BrpCharacter(' '),
                new BrpString(""),
                null,
                new BrpAdellijkeTitelCode(AT),
                new BrpInteger(1));
    }

    @Test
    public void testGetVoorvoegsel() {
        assertEquals(VAN, BrpString.unwrap(testInhoud.getVoorvoegsel()));
    }

    @Test
    public void testGetScheidingsteken() {
        assertEquals(Character.valueOf(' '), BrpCharacter.unwrap(testInhoud.getScheidingsteken()));
    }

    @Test
    public void testGetStam() {
        assertEquals(HOREN_ZEGGEN, BrpString.unwrap(testInhoud.getStam()));
    }

    @Test
    public void testGetPredicaatCode() {
        assertNull(testInhoud.getPredicaatCode());
    }

    @Test
    public void testGetAdellijkeTitelCode() {
        assertEquals(new BrpAdellijkeTitelCode(AT), testInhoud.getAdellijkeTitelCode());
    }

    @Test
    public void testGetVolgnummer() {
        assertEquals(new BrpInteger(1), testInhoud.getVolgnummer());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(testInhoud.isLeeg());
    }

    @Test
    public void testEqualsObject() {
        final BrpGeslachtsnaamcomponentInhoud inhoud1 =
                new BrpGeslachtsnaamcomponentInhoud(
                        new BrpString(VAN),
                        new BrpCharacter(' '),
                        new BrpString(HOREN_ZEGGEN),
                        null,
                        new BrpAdellijkeTitelCode(AT),
                        new BrpInteger(1));
        final BrpGeslachtsnaamcomponentInhoud inhoud2 =
                new BrpGeslachtsnaamcomponentInhoud(
                        new BrpString(VAN),
                        new BrpCharacter(' '),
                        new BrpString(HOREN_ZEGGEN),
                        null,
                        new BrpAdellijkeTitelCode(AT),
                        new BrpInteger(1));
        assertEquals(inhoud1, inhoud2);
    }

    @Test
    public void testToString() {
        final BrpGeslachtsnaamcomponentInhoud inhoud1 =
                new BrpGeslachtsnaamcomponentInhoud(
                        new BrpString(VAN),
                        new BrpCharacter(' '),
                        new BrpString(HOREN_ZEGGEN),
                        null,
                        new BrpAdellijkeTitelCode(AT),
                        new BrpInteger(1));
        final BrpGeslachtsnaamcomponentInhoud inhoud2 =
                new BrpGeslachtsnaamcomponentInhoud(
                        new BrpString(VAN),
                        new BrpCharacter(' '),
                        new BrpString(HOREN_ZEGGEN),
                        null,
                        new BrpAdellijkeTitelCode(AT),
                        new BrpInteger(1));
        assertEquals(inhoud1.toString(), inhoud2.toString());
    }
}
