/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

public class Lo3BerichtTest {

    private static final String PARTIJ_1 = "TEMP";
    private static final String PARTIJ_2 = "TEMP2";
    private static final String REFERENTIE_BERICHT = "referentie";
    private static final String PLACEHOLDER_BERICHT = "PLACEHOLDER";

    private Persoon persoon;
    private Timestamp now;

    @Before
    public void setup() {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setAdministratienummer(123456789L);
        now = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Test of een bericht wat typisch ontstaat na conversie en een bericht wat ontstaat na een service aanroep van de
     * sync service, correct worden gemerged.
     */
    @Test
    public void testMergeVoorkomens() {
        createConversieBericht();
        final Lo3Bericht ontvangenBericht = createLoggingBericht();

        assertEquals(1, persoon.getLo3Berichten().size());
        ontvangenBericht.mergeVoorkomensUitAnderBericht(persoon.getLo3Berichten().iterator().next());

        verifieerUitkomstVanMerge();
    }

    private void verifieerUitkomstVanMerge() {
        assertEquals(1, persoon.getLo3Berichten().size());
        final Lo3Bericht ontvangenBericht = persoon.getLo3Berichten().iterator().next();
        assertEquals(2, ontvangenBericht.getVoorkomens().size());
        final Iterator<Lo3Voorkomen> voorkomenIterator = ontvangenBericht.getVoorkomens().iterator();
        final Lo3Voorkomen voorkomen1 = voorkomenIterator.next();
        final Lo3Voorkomen voorkomen2 = voorkomenIterator.next();
        assertNotNull(voorkomen1.getActie());
        assertNotNull(voorkomen2.getActie());
        assertEquals(PARTIJ_1, voorkomen1.getActie().getPartij().getNaam());
        assertEquals(PARTIJ_2, voorkomen2.getActie().getPartij().getNaam());
        assertEquals(REFERENTIE_BERICHT, voorkomen1.getBericht().getReferentie());
        assertEquals(REFERENTIE_BERICHT, voorkomen2.getBericht().getReferentie());
        assertEquals(2, voorkomen1.getMeldingen().size());
        assertEquals(1, voorkomen2.getMeldingen().size());
        final Iterator<Lo3Melding> meldingenIterator1 = voorkomen1.getMeldingen().iterator();
        final Iterator<Lo3Melding> meldingenIterator2 = voorkomen2.getMeldingen().iterator();
        final Lo3Melding melding1 = meldingenIterator1.next();
        final Lo3Melding melding2 = meldingenIterator1.next();
        final Lo3Melding melding3 = meldingenIterator2.next();
        assertEquals(Lo3SoortMelding.PRE001, melding1.getSoortMelding());
        assertEquals(Lo3SoortMelding.PRE002, melding2.getSoortMelding());
        assertEquals(Lo3SoortMelding.BIJZ_CONV_LB001, melding3.getSoortMelding());
    }

    private Lo3Bericht createLoggingBericht() {
        final Lo3Bericht result = new Lo3Bericht(REFERENTIE_BERICHT, Lo3BerichtenBron.INITIELE_VULLING, now, "bericht_inhoud", true);
        final Lo3Voorkomen loggingVoorkomen1 = new Lo3Voorkomen(result, "01");
        loggingVoorkomen1.setStapelvolgnummer(0);
        loggingVoorkomen1.setVoorkomenvolgnummer(0);
        final Lo3Melding loggingMelding1 = new Lo3Melding(loggingVoorkomen1, Lo3SoortMelding.PRE001, Lo3Severity.ERROR);
        final Lo3Melding loggingMelding2 = new Lo3Melding(loggingVoorkomen1, Lo3SoortMelding.PRE002, Lo3Severity.ERROR);
        loggingVoorkomen1.addMelding(loggingMelding1);
        loggingVoorkomen1.addMelding(loggingMelding2);

        final Lo3Voorkomen loggingVoorkomen2 = new Lo3Voorkomen(result, "02");
        loggingVoorkomen2.setStapelvolgnummer(0);
        loggingVoorkomen2.setVoorkomenvolgnummer(1);
        final Lo3Melding loggingMelding3 = new Lo3Melding(loggingVoorkomen2, Lo3SoortMelding.BIJZ_CONV_LB001, Lo3Severity.INFO);
        loggingVoorkomen2.addMelding(loggingMelding3);

        result.addMelding("01", 0, 0, null, Lo3SoortMelding.PRE001, Lo3Severity.ERROR, null, null);
        result.addMelding("01", 0, 0, null, Lo3SoortMelding.PRE002, Lo3Severity.ERROR, null, null);
        result.addMelding("02", 0, 1, null, Lo3SoortMelding.BIJZ_CONV_LB001, Lo3Severity.INFO, null, null);
        return result;
    }

    private void createConversieBericht() {
        final Partij partij1 = new Partij(PARTIJ_1, 1);
        final Partij partij2 = new Partij(PARTIJ_2, 2);
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(partij1, SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        final Lo3Bericht result = new Lo3Bericht(PLACEHOLDER_BERICHT, Lo3BerichtenBron.INITIELE_VULLING, now, PLACEHOLDER_BERICHT, true);
        final BRPActie actie1 = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, partij1, now);
        final BRPActie actie2 = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, partij2, now);

        // voorkomens toevoegen zoals bij conversie. Dus zonder meldingen.
        result.addVoorkomen("01", 0, 0, actie1);
        result.addVoorkomen("02", 0, 1, actie2);
        persoon.addLo3Bericht(result);
    }

}
