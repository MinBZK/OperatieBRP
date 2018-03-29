/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;
import nl.bzk.migratiebrp.init.logging.model.VerschilType;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl.TestUtil;
import org.junit.Test;

public class StapelMatchTest {

    @Test
    public void testVerschilTypeAdded() {
        final StapelMatch<Lo3PersoonInhoud> match = new StapelMatch<>(TestUtil.getHerkomst());
        match.addBrpLo3Stapel(TestUtil.maakStapel());
        assertEquals(VerschilType.ADDED, match.getVerschilType());
        assertTrue(match.getLo3Stapels().isEmpty());
        assertFalse(match.getBrpLo3Stapels().isEmpty());
    }

    @Test
    public void testVerschilTypeRemoved() {
        final StapelMatch<Lo3PersoonInhoud> match = new StapelMatch<>(TestUtil.getHerkomst());
        match.addLo3Stapel(TestUtil.maakStapel());
        assertEquals(VerschilType.REMOVED, match.getVerschilType());
        assertFalse(match.getLo3Stapels().isEmpty());
        assertTrue(match.getBrpLo3Stapels().isEmpty());
    }

    @Test
    public void testVerschilTypeNonUniqueMatched() {
        final StapelMatch<Lo3PersoonInhoud> match = new StapelMatch<>(TestUtil.getHerkomst());
        match.addLo3Stapel(TestUtil.maakStapel());
        match.addBrpLo3Stapel(TestUtil.maakStapel());
        match.addBrpLo3Stapel(TestUtil.maakStapel(true));
        assertEquals(VerschilType.NON_UNIQUE_MATCHED, match.getVerschilType());
        assertFalse(match.getLo3Stapels().isEmpty());
        assertFalse(match.getBrpLo3Stapels().isEmpty());
    }

    @Test
    public void testVerschilTypeNonUniqueMatchedLo3() {
        final StapelMatch<Lo3PersoonInhoud> match = new StapelMatch<>(TestUtil.getHerkomst());
        match.addLo3Stapel(TestUtil.maakStapel());
        match.addLo3Stapel(TestUtil.maakStapel(true));
        match.addBrpLo3Stapel(TestUtil.maakStapel());
        assertEquals(VerschilType.NON_UNIQUE_MATCHED, match.getVerschilType());
        assertFalse(match.getLo3Stapels().isEmpty());
        assertFalse(match.getBrpLo3Stapels().isEmpty());
    }

    @Test
    public void testVerschilTypeNonUniqueMatchedAlleenHerkomst() {
        final StapelMatch<Lo3PersoonInhoud> match = new StapelMatch<>(TestUtil.getHerkomst());
        assertEquals(VerschilType.NON_UNIQUE_MATCHED, match.getVerschilType());
        assertTrue(match.getLo3Stapels().isEmpty());
        assertTrue(match.getBrpLo3Stapels().isEmpty());
    }

    @Test
    public void testMatched() {
        final Lo3Herkomst herkomst = TestUtil.getHerkomst();
        final StapelMatch<Lo3PersoonInhoud> match = new StapelMatch<>(herkomst);
        match.addLo3Stapel(TestUtil.maakStapel());
        match.addBrpLo3Stapel(TestUtil.maakStapel());
        assertEquals(VerschilType.MATCHED, match.getVerschilType());
        assertEquals(herkomst, match.getHerkomst());
        assertTrue(match.isUniqueMatched());
        assertFalse(match.getLo3Stapels().isEmpty());
        assertFalse(match.getBrpLo3Stapels().isEmpty());
    }

    @Test
    public void testEqualsHashCodeToString() {
        final StapelMatch<Lo3PersoonInhoud> match = new StapelMatch<>(TestUtil.getHerkomst());
        final StapelMatch<Lo3PersoonInhoud> match2 = new StapelMatch<>(TestUtil.getHerkomst());
        final StapelMatch<Lo3PersoonInhoud> match3 = new StapelMatch<>(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0));
        final StapelMatch<Lo3PersoonInhoud> match4 = new StapelMatch<>(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));

        assertTrue(match.equals(match2));
        assertFalse(match.equals(match3));
        // Verschillende voorkomens, maar dezelfde stapel en categorie
        assertTrue(match.equals(match4));
        assertFalse(match.equals("Testfout"));

        assertTrue(match.toString().contains("Lo3Herkomst"));
    }
}
