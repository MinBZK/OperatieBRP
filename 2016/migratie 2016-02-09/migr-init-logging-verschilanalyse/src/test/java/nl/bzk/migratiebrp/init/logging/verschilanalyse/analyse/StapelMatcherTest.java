/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;
import nl.bzk.migratiebrp.init.logging.model.VerschilType;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl.TestUtil;
import org.junit.Test;

public class StapelMatcherTest {

    @Test
    public void testMatchStapelsNullStapels() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = TestUtil.maakStapel();
        final List<Lo3Stapel<Lo3PersoonInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel);

        List<StapelMatch<Lo3PersoonInhoud>> match = StapelMatcher.matchStapels(null, null);
        assertTrue(match.isEmpty());

        match = StapelMatcher.matchStapels(stapels, null);
        assertEquals(1, match.size());
        assertEquals(VerschilType.REMOVED, match.get(0).getVerschilType());

        match = StapelMatcher.matchStapels(null, stapels);
        assertEquals(1, match.size());
        assertEquals(VerschilType.ADDED, match.get(0).getVerschilType());
    }

    @Test
    public void testMatchDezelfdeStapels() {
        final Lo3Stapel<Lo3PersoonInhoud> stapel = TestUtil.maakStapel();
        final List<Lo3Stapel<Lo3PersoonInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel);

        final List<StapelMatch<Lo3PersoonInhoud>> match = StapelMatcher.matchStapels(stapels, stapels);
        assertEquals(1, match.size());
        assertEquals(VerschilType.MATCHED, match.get(0).getVerschilType());
    }

    @Test
    public void testMatchVerschillendeStapels() {
        final Lo3Herkomst anderHerkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 0);

        final Lo3Stapel<Lo3PersoonInhoud> stapel = TestUtil.maakStapel();
        final Lo3Stapel<Lo3PersoonInhoud> stapel2 = TestUtil.maakStapel(true, anderHerkomst);

        final List<Lo3Stapel<Lo3PersoonInhoud>> lo3Stapels = new ArrayList<>();
        lo3Stapels.add(stapel);
        lo3Stapels.add(stapel2);

        final List<Lo3Stapel<Lo3PersoonInhoud>> brpStapels = new ArrayList<>();
        brpStapels.add(stapel);

        final List<StapelMatch<Lo3PersoonInhoud>> match = StapelMatcher.matchStapels(lo3Stapels, brpStapels);
        assertEquals(2, match.size());
        assertEquals(VerschilType.MATCHED, match.get(0).getVerschilType());
        assertEquals(VerschilType.REMOVED, match.get(1).getVerschilType());
    }
}
