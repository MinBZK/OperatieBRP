/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.vergelijk;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.gba.gbav.impl.lo3.GenericPLData;
import nl.gba.gbav.spontaan.verschilanalyse.VoorkomenMatch;
import nl.ictu.spg.domain.lo3.pl.PersoonGS;
import org.junit.Test;

public class VoorkomenVergelijkingTest {
    @Test
    public void testVoorkomenVergelijking() {
        final GenericPLData plData = new GenericPLData();
        plData.setRubriek(3, 210, "asdf");

        final VoorkomenMatch voorkomenMatch = new VoorkomenMatch(3);
        voorkomenMatch.setDifference(plData);
        voorkomenMatch.setNewVoorkomen(new PersoonGS());
        voorkomenMatch.setOldVoorkomen(new PersoonGS());
        voorkomenMatch.setType(VoorkomenMatch.NEWER_THAN_ACTUAL);

        final GgoVoorkomenVergelijking voorkomenVergelijking = new GgoVoorkomenVergelijking(voorkomenMatch, "123456789", "changed");
        assertEquals("02.10", voorkomenVergelijking.getInhoud());
        assertEquals("123456789_3_0_0", voorkomenVergelijking.getNieuweHerkomsten().toArray()[0]);
        assertEquals("123456789_3_0_0", voorkomenVergelijking.getOudeHerkomsten().toArray()[0]);
        assertEquals("changed", voorkomenVergelijking.getType());
    }
}
