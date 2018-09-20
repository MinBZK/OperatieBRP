/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPartij;

import org.junit.Test;

public class SoortPartijTest {

    private static final int[] IDS = new int[] { 1, 2, 3, 4, 5, 6, };
    private static final String[] OMSCHRIJVINGEN = new String[] { "Wetgever", "Vertegenwoordiger Regering",
            "Gemeente", "Overheidsorgaan", "Derde", "Samenwerkingsverband", };
    private static final SoortPartij[] VERWACHTE_PARTIJEN = new SoortPartij[] { SoortPartij.WETGEVER,
            SoortPartij.VERTEGENWOORDIGER_REGERING, SoortPartij.GEMEENTE, SoortPartij.OVERHEIDSORGAAN,
            SoortPartij.DERDE, SoortPartij.SAMENWERKINGSVERBAND, };

    @Test
    public void testSoortPartijen() {
        for (int index = 0; index < 6; index++) {
            final int id = IDS[index];
            final String omschrijving = OMSCHRIJVINGEN[index];
            final SoortPartij verwachtePartij = VERWACHTE_PARTIJEN[index];
            assertEquals(verwachtePartij, SoortPartij.parseId(id));
            assertEquals(omschrijving, SoortPartij.parseId(id).getOmschrijving());
            assertEquals(id, SoortPartij.parseId(id).getId());
            assertFalse(verwachtePartij.heeftCode());
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCode() {
        VERWACHTE_PARTIJEN[0].getCode();
    }

}
