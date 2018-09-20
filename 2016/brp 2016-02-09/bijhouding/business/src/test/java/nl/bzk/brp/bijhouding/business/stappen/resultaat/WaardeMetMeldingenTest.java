/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.resultaat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class WaardeMetMeldingenTest {
    @Test
    public void testCreeerMetWaarde() {
        WaardeMetMeldingen<String> resultaat = new WaardeMetMeldingen<>("aap");
        // moet lege lijst opleveren
        assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testCreeerMetMelding() {
        WaardeMetMeldingen<String> resultaat = new WaardeMetMeldingen<>(ResultaatMelding.builder().build());
        assertEquals(1, resultaat.getMeldingen().size());
        assertNull(resultaat.getWaarde());
    }
}
