/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Comparator;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelDecorator;

import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link IstStapelMatchTest}.
 */
public class IstStapelMatchTest {

    private Persoon persoon;
    private StapelDecorator bestaandeStapelDecorator;
    private StapelDecorator nieuweStapelDecorator;

    @Before
    public void setUp() {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        bestaandeStapelDecorator = StapelDecorator.decorate(new Stapel(persoon, "02", 0));
        nieuweStapelDecorator = StapelDecorator.decorate(new Stapel(persoon, "03", 0));
    }

    @Test
    public void testConstructor() {
        final IstStapelMatch match =
                new IstStapelMatch(bestaandeStapelDecorator, Collections.singletonList(nieuweStapelDecorator), StapelMatchType.MATCHED);
        assertEquals(bestaandeStapelDecorator, match.getStapel());
        assertFalse(match.getMatchingStapels().isEmpty());
        assertTrue(match.getMatchingStapels().size() == 1);
        assertEquals(nieuweStapelDecorator, match.getMatchingStapel());
        assertEquals(StapelMatchType.MATCHED, match.getMatchType());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetMatchingStapel() {
        final IstStapelMatch match =
                new IstStapelMatch(bestaandeStapelDecorator, Collections.<StapelDecorator>emptyList(), StapelMatchType.STAPEL_VERWIJDERD);
        assertEquals(bestaandeStapelDecorator, match.getStapel());
        assertTrue(match.getMatchingStapels().isEmpty());
        match.getMatchingStapel();
    }

    @Test
    public void testMaakMatchNonUnique() {
        final IstStapelMatch match =
                new IstStapelMatch(bestaandeStapelDecorator, Collections.<StapelDecorator>emptyList(), StapelMatchType.STAPEL_VERWIJDERD);
        assertEquals(StapelMatchType.STAPEL_VERWIJDERD, match.getMatchType());
        match.makeMatchNonUnique();
        assertEquals(StapelMatchType.NON_UNIQUE_MATCH, match.getMatchType());
    }

    @Test
    public void testGetSorteerder() {
        final StapelDecorator stapelDecorator = StapelDecorator.decorate(new Stapel(persoon, "03", 1));
        final IstStapelMatch match1 =
                new IstStapelMatch(bestaandeStapelDecorator, Collections.<StapelDecorator>emptyList(), StapelMatchType.STAPEL_VERWIJDERD);
        final IstStapelMatch match2 =
                new IstStapelMatch(nieuweStapelDecorator, Collections.<StapelDecorator>emptyList(), StapelMatchType.STAPEL_VERWIJDERD);
        final IstStapelMatch match3 = new IstStapelMatch(stapelDecorator, Collections.<StapelDecorator>emptyList(), StapelMatchType.STAPEL_VERWIJDERD);

        final Comparator<IstStapelMatch> comparator = IstStapelMatch.getSorteerder();
        assertNotNull(comparator);
        assertTrue(comparator.compare(match1, match1) == 0);
        assertTrue(comparator.compare(match1, match2) == -1);
        assertTrue(comparator.compare(match1, match3) == -1);
        assertTrue(comparator.compare(match2, match1) == 1);
        assertTrue(comparator.compare(match2, match2) == 0);
        assertTrue(comparator.compare(match2, match3) == -1);
        assertTrue(comparator.compare(match3, match1) == 1);
        assertTrue(comparator.compare(match3, match2) == 1);
        assertTrue(comparator.compare(match3, match3) == 0);
    }
}
