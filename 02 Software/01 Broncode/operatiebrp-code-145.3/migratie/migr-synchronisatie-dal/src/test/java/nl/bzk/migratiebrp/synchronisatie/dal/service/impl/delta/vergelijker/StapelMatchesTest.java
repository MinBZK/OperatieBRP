/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.StapelMatchType;

import org.junit.Test;

/**
 * Unittests voor {@link StapelMatchesTest}.
 */
public class StapelMatchesTest {

    @Test
    public void testToevoegenMatch() {
        final String waardeA = "WaardeA";
        final String waardeB = "WaardeB";
        final String waardeC = "WaardeC";

        final StapelMatches<String> matches = new StapelMatches<>();
        assertTrue(matches.getBestaandeStapels().isEmpty());
        assertTrue(matches.getNieuweStapels().isEmpty());

        matches.toevoegenMatch(waardeA, null);
        matches.toevoegenMatch(null, waardeB);

        assertFalse(matches.getBestaandeStapels().isEmpty());
        assertFalse(matches.getNieuweStapels().isEmpty());

        assertEquals(StapelMatchType.STAPEL_VERWIJDERD, matches.bepaalMatchType(waardeA, true));
        assertEquals(StapelMatchType.STAPEL_NIEUW, matches.bepaalMatchType(waardeB, false));

        assertTrue(matches.bevatKoppelingVoorStapel(waardeA, true));
        assertTrue(matches.bevatKoppelingVoorStapel(waardeB, false));
        assertFalse(matches.bevatKoppelingVoorStapel(waardeC, false));

        matches.toevoegenMatch(waardeA, waardeB);

        assertFalse(matches.getMatchesVoorBestaandeStapel(waardeA).isEmpty());
        assertNull(matches.getMatchesVoorNieuweStapel(waardeA));
        assertNull(matches.getMatchesVoorBestaandeStapel(waardeB));
        assertFalse(matches.getMatchesVoorNieuweStapel(waardeB).isEmpty());

        assertEquals(StapelMatchType.MATCHED, matches.bepaalMatchType(waardeA, true));
        assertEquals(StapelMatchType.MATCHED, matches.bepaalMatchType(waardeB, false));

        matches.toevoegenMatch(waardeA, waardeC);

        assertTrue(matches.bevatKoppelingVoorStapel(waardeC, false));
        assertEquals(StapelMatchType.NON_UNIQUE_MATCH, matches.bepaalMatchType(waardeA, true));
        assertEquals(StapelMatchType.NON_UNIQUE_MATCH, matches.bepaalMatchType(waardeC, false));
    }

    @Test(expected = IllegalStateException.class)
    public void testException() {
        final StapelMatches<String> matches = new StapelMatches<>();
        matches.bepaalMatchType("waardeA", true);
    }

    @Test
    public void testGetMatchingStapel() {
        final String waardeA = "WaardeA";
        final String waardeB = "WaardeB";
        final StapelMatches<String> matches = new StapelMatches<>();
        matches.toevoegenMatch(waardeA, waardeB);
        assertEquals(waardeB, matches.getMatchingStapel(waardeA, true));
        assertEquals(waardeA, matches.getMatchingStapel(waardeB, false));
        assertNull(matches.getMatchingStapel(waardeA, false));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetMatchingStapelMeerDan1Match() {
        final String waardeA = "WaardeA";
        final String waardeB = "WaardeB";
        final String waardeC = "WaardeC";

        final StapelMatches<String> matches = new StapelMatches<>();
        matches.toevoegenMatch(waardeA, waardeB);
        matches.toevoegenMatch(waardeA, waardeC);
        matches.getMatchingStapel(waardeA, true);
    }
}
