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
import nl.bzk.migratiebrp.init.logging.model.VerschilType;
import nl.bzk.migratiebrp.init.logging.model.VoorkomenMatch;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl.TestUtil;
import org.junit.Test;

public class VoorkomenMatchTest {

    @Test
    public void testVerschilTypeAdded() {
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(TestUtil.getHerkomst());
        match.addBrpLo3Voorkomen(TestUtil.maakVoorkomen());
        assertEquals(VerschilType.ADDED, match.getVerschilType());
        assertTrue(match.getLo3Voorkomens().isEmpty());
        assertFalse(match.getBrpLo3Voorkomens().isEmpty());
    }

    @Test
    public void testVerschilTypeRemoved() {
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(TestUtil.getHerkomst());
        match.addLo3Voorkomen(TestUtil.maakVoorkomen());
        assertEquals(VerschilType.REMOVED, match.getVerschilType());
        assertFalse(match.getLo3Voorkomens().isEmpty());
        assertTrue(match.getBrpLo3Voorkomens().isEmpty());
    }

    @Test
    public void testVerschilTypeNonUniqueMatched() {
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(TestUtil.getHerkomst());
        match.addLo3Voorkomen(TestUtil.maakVoorkomen());
        match.addBrpLo3Voorkomen(TestUtil.maakVoorkomen());
        match.addBrpLo3Voorkomen(TestUtil.maakVoorkomen(true));
        assertEquals(VerschilType.NON_UNIQUE_MATCHED, match.getVerschilType());
        assertFalse(match.getLo3Voorkomens().isEmpty());
        assertFalse(match.getBrpLo3Voorkomens().isEmpty());
    }

    @Test
    public void testVerschilTypeNonUniqueMatchedLo3() {
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(TestUtil.getHerkomst());
        match.addLo3Voorkomen(TestUtil.maakVoorkomen());
        match.addLo3Voorkomen(TestUtil.maakVoorkomen(true));
        match.addBrpLo3Voorkomen(TestUtil.maakVoorkomen());
        assertEquals(VerschilType.NON_UNIQUE_MATCHED, match.getVerschilType());
        assertFalse(match.getLo3Voorkomens().isEmpty());
        assertFalse(match.getBrpLo3Voorkomens().isEmpty());
    }

    @Test
    public void testVerschilTypeNonUniqueMatchedAlleenHerkomst() {
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(TestUtil.getHerkomst());
        assertEquals(VerschilType.NON_UNIQUE_MATCHED, match.getVerschilType());
        assertTrue(match.getLo3Voorkomens().isEmpty());
        assertTrue(match.getBrpLo3Voorkomens().isEmpty());
    }

    @Test
    public void testMatched() {
        final Lo3Herkomst herkomst = TestUtil.getHerkomst();
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(herkomst);
        match.addLo3Voorkomen(TestUtil.maakVoorkomen());
        match.addBrpLo3Voorkomen(TestUtil.maakVoorkomen());
        match.setIsLo3Actueel(true);
        match.setIsBrpLo3Actueel(true);
        assertEquals(herkomst, match.getHerkomst());
        assertEquals(VerschilType.MATCHED, match.getVerschilType());
        assertTrue(match.isUniqueMatched());
        assertFalse(match.getLo3Voorkomens().isEmpty());
        assertFalse(match.getBrpLo3Voorkomens().isEmpty());
    }

    @Test
    public void testLo3ActualBrpNotActual() {
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(TestUtil.getHerkomst());
        match.addLo3Voorkomen(TestUtil.maakVoorkomen());
        match.addBrpLo3Voorkomen(TestUtil.maakVoorkomen());
        match.setIsLo3Actueel(true);

        assertTrue(match.isLo3Actueel());
        assertFalse(match.isBrpLo3Actueel());
        assertEquals(VerschilType.NOT_ACTUAL, match.getVerschilType());
        assertTrue(match.isUniqueMatched());
        assertFalse(match.getLo3Voorkomens().isEmpty());
        assertFalse(match.getBrpLo3Voorkomens().isEmpty());
    }

    /**
     * Als LO3 niet actueel is, dan maakt het niet uit of BRP wel of niet actueel is. Status zal in dit geval altijd
     * {@link VerschilType#MATCHED} zijn.
     */
    @Test
    public void testLo3NotActualBrpActual() {
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(TestUtil.getHerkomst());
        match.addLo3Voorkomen(TestUtil.maakVoorkomen());
        match.addBrpLo3Voorkomen(TestUtil.maakVoorkomen());
        match.setIsBrpLo3Actueel(true);

        assertFalse(match.isLo3Actueel());
        assertTrue(match.isBrpLo3Actueel());
        assertEquals(VerschilType.MATCHED, match.getVerschilType());
        assertTrue(match.isUniqueMatched());
        assertFalse(match.getLo3Voorkomens().isEmpty());
        assertFalse(match.getBrpLo3Voorkomens().isEmpty());
    }

    @Test
    public void testEqualsHashCodeToString() {
        final VoorkomenMatch<Lo3PersoonInhoud> match = new VoorkomenMatch<>(TestUtil.getHerkomst());
        final VoorkomenMatch<Lo3PersoonInhoud> match2 = new VoorkomenMatch<>(TestUtil.getHerkomst());
        final VoorkomenMatch<Lo3PersoonInhoud> match3 = new VoorkomenMatch<>(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0));
        final VoorkomenMatch<Lo3PersoonInhoud> match4 = new VoorkomenMatch<>(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));

        assertTrue(match.equals(match2));
        assertFalse(match.equals(match3));
        assertFalse(match.equals(match4));
        assertFalse(match.equals("Testfout"));

        assertTrue(match.toString().contains("Lo3Herkomst"));
    }
}
