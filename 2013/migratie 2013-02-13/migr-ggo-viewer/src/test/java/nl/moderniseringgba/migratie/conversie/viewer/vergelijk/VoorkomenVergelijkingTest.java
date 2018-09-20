/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.vergelijk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nl.gba.gbav.impl.lo3.GenericPLData;
import nl.gba.gbav.spontaan.verschilanalyse.VoorkomenMatch;
import nl.ictu.spg.domain.lo3.pl.PersoonGS;

import org.junit.Test;

public class VoorkomenVergelijkingTest {
    @Test
    public void testVoorkomenVergelijking() {
        final GenericPLData plData = new GenericPLData();
        plData.setRubriek(3, 2, "asdf");

        final VoorkomenMatch voorkomenMatch = new VoorkomenMatch(3);
        voorkomenMatch.setDifference(plData);
        voorkomenMatch.setNewVoorkomen(new PersoonGS());
        voorkomenMatch.setOldVoorkomen(new PersoonGS());
        voorkomenMatch.setType(VoorkomenMatch.NEWER_THAN_ACTUAL);

        final VoorkomenVergelijking voorkomenVergelijking = new VoorkomenVergelijking(voorkomenMatch);
        assertEquals(3, voorkomenVergelijking.getCategorie().intValue());
        assertEquals(2, voorkomenVergelijking.getElements().toArray()[0]);
        assertNotNull(voorkomenVergelijking.getNewVoorkomen());
        assertNotNull(voorkomenVergelijking.getOldVoorkomen());
        assertEquals("NEWER_THAN_ACTUAL", voorkomenVergelijking.getType());
    }
}
