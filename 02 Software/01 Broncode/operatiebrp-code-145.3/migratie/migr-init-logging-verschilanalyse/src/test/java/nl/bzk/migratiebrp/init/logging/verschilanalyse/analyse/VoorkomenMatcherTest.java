/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;
import nl.bzk.migratiebrp.init.logging.model.VoorkomenMatch;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl.TestUtil;
import org.junit.Test;

public class VoorkomenMatcherTest {

    @Test
    public void testVoorkomenMatcherNullVoorkomens() {
        final List<VoorkomenMatch<Lo3PersoonInhoud>> match = VoorkomenMatcher.matchVoorkomens(null);
        assertTrue(match.isEmpty());
    }

    @Test
    public void testVoorkomenMatchDezelfdeVoorkomens() {
        final List<VoorkomenMatch<Lo3PersoonInhoud>> matches = VoorkomenMatcher.matchVoorkomens(maakStapelMatch(false));
        assertEquals(1, matches.size());
        final VoorkomenMatch<Lo3PersoonInhoud> match = matches.get(0);
        assertTrue(match.isBrpLo3Actueel());
        assertTrue(match.isLo3Actueel());
    }

    @Test
    public void testVoorkomenMatchVerschillendeVoorkomens() {
        final List<VoorkomenMatch<Lo3PersoonInhoud>> matches = VoorkomenMatcher.matchVoorkomens(maakStapelMatch(true));
        assertEquals(2, matches.size());

        // Eerst wordt er vanaf de LO3 kant gekeken.

        // Deze match bevat geen BRP-LO3 voorkomen, maar wel een LO3-voorkomen(en was actueel bij heenconversie)
        VoorkomenMatch<Lo3PersoonInhoud> match = matches.get(0);
        assertFalse(match.isBrpLo3Actueel());
        assertTrue(match.isLo3Actueel());

        // Deze match bevat wel een BRP-LO3 voorkomen (en was niet actueel bij heenconversie), maar geen LO3-voorkomen
        match = matches.get(1);
        assertFalse(match.isBrpLo3Actueel());
        assertFalse(match.isLo3Actueel());
    }

    @Test
    public void testVoorkomenMatchMeerdereBRPVoorkomensNietActueel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> voorkomens = new ArrayList<>();
        voorkomens.add(TestUtil.maakVoorkomen());
        voorkomens.add(TestUtil.maakVoorkomen(true, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1)));

        final Lo3Stapel<Lo3PersoonInhoud> brpLo3Stapel = new Lo3Stapel<>(voorkomens);
        final Lo3Stapel<Lo3PersoonInhoud> lo3Stapel = TestUtil.maakStapel();

        final StapelMatch<Lo3PersoonInhoud> stapelMatch = new StapelMatch<>(TestUtil.getHerkomst());
        stapelMatch.addLo3Stapel(lo3Stapel);
        stapelMatch.addBrpLo3Stapel(brpLo3Stapel);

        final List<VoorkomenMatch<Lo3PersoonInhoud>> matches = VoorkomenMatcher.matchVoorkomens(stapelMatch);
        assertEquals(2, matches.size());

        // Eerst wordt er vanaf de LO3 kant gekeken.

        // Voorkomen met herkomst Cat01, Stapel 0, Voorkomen 0
        VoorkomenMatch<Lo3PersoonInhoud> match = matches.get(0);
        assertFalse(match.isBrpLo3Actueel());
        assertTrue(match.isLo3Actueel());

        // Voorkomen met herkomst Cat01, Stapel 0, Voorkomen 1
        match = matches.get(1);
        assertFalse(match.isBrpLo3Actueel());
        assertFalse(match.isLo3Actueel());
    }

    private StapelMatch<Lo3PersoonInhoud> maakStapelMatch(final boolean verschillendeVoorkomens) {
        final Lo3Herkomst herkomst = TestUtil.getHerkomst();
        final StapelMatch<Lo3PersoonInhoud> stapelMatch = new StapelMatch<>(herkomst);

        if (!verschillendeVoorkomens) {
            final Lo3Stapel<Lo3PersoonInhoud> stapel = TestUtil.maakStapel();
            stapelMatch.addLo3Stapel(stapel);
            stapelMatch.addBrpLo3Stapel(stapel);
        } else {
            final Lo3Stapel<Lo3PersoonInhoud> stapel = TestUtil.maakStapel();
            final Lo3Stapel<Lo3PersoonInhoud> stapel2 = TestUtil.maakStapel(true, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
            stapelMatch.addLo3Stapel(stapel);
            stapelMatch.addBrpLo3Stapel(stapel2);
        }

        return stapelMatch;
    }
}
