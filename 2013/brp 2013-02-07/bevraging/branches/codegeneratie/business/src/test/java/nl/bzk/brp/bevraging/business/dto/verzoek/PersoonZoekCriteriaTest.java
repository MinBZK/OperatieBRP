/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.verzoek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.domein.ber.SoortBericht;

import org.junit.Test;


/**
 * Unit test voor de {@link PersoonZoekCriteria} class.
 */
public class PersoonZoekCriteriaTest {

    @Test
    public void testGettersEnSetters() {
        PersoonZoekCriteria zoek = new PersoonZoekCriteria();

        assertEquals(SoortBericht.OPVRAGENPERSOONVRAAG, zoek.getSoortBericht());
        assertEquals(PersoonZoekCriteriaAntwoord.class, zoek.getAntwoordClass());

        Calendar beschouwing = Calendar.getInstance();
        zoek.setBeschouwing(beschouwing);
        zoek.setBsn("123456789");
        assertEquals("123456789", zoek.getBsn());
        assertEquals(beschouwing, zoek.getBeschouwing());

        assertEquals(1, zoek.getReadBsnLocks().size());
        assertEquals("123456789", zoek.getReadBsnLocks().iterator().next());
        assertNull(zoek.getWriteBsnLocks());
    }

}
