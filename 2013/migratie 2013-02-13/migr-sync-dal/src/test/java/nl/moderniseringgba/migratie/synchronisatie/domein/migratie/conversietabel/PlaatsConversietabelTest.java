/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.PlaatsConversietabel;

import org.junit.Assert;
import org.junit.Test;

public class PlaatsConversietabelTest {

    private final PlaatsConversietabel tabel = new PlaatsConversietabel(Arrays.asList("Den Haag"));

    @Test
    public void test() {
        final BrpPlaatsCode brpCode = new BrpPlaatsCode("Den Haag");
        final String lo3Code = "Den Haag";

        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(brpCode, tabel.converteerNaarBrp(lo3Code));

        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(lo3Code, tabel.converteerNaarLo3(brpCode));

        Assert.assertTrue(tabel.valideerLo3("Den Haag"));
        Assert.assertFalse(tabel.valideerLo3("Zoetermeer"));
        Assert.assertTrue(tabel.valideerBrp(new BrpPlaatsCode("Den Haag")));
        Assert.assertFalse(tabel.valideerBrp(new BrpPlaatsCode("Zoetermeer")));
    }
}
