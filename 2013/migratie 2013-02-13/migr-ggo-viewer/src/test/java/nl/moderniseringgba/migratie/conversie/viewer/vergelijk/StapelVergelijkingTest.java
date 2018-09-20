/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.vergelijk;

import static org.junit.Assert.assertEquals;
import nl.gba.gbav.spontaan.verschilanalyse.StapelMatch;
import nl.gba.gbav.spontaan.verschilanalyse.VoorkomenMatch;
import nl.ictu.spg.domain.lo3.pl.PersoonStapelGS;

import org.junit.Test;

public class StapelVergelijkingTest {
    @Test
    public void testStapelVergelijking() {
        final StapelMatch stapelMatch = new StapelMatch();
        stapelMatch.setCategorie(42);
        stapelMatch.setType(StapelMatch.CHANGED);

        stapelMatch.setIdxVoorkomenMatchWithVA(1);
        stapelMatch.addVoorkomenMatch(new VoorkomenMatch(42));
        stapelMatch.addOldStapel(new PersoonStapelGS(null, 0, 0));
        stapelMatch.addNewStapel(new PersoonStapelGS(null, 0, 0));

        final StapelVergelijking stapelVergelijking = new StapelVergelijking(stapelMatch);

        assertEquals(42, stapelVergelijking.getCategorie());
        assertEquals(1, stapelVergelijking.getOldStapels().size());
        assertEquals(1, stapelVergelijking.getNewStapels().size());
        assertEquals(1, stapelVergelijking.getVoorkomenMatches().size());
        assertEquals("CHANGED", stapelVergelijking.getType());
    }
}
