/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.LandConversietabel;

import org.junit.Assert;
import org.junit.Test;

public class LandConversietabelTest {

    private final LandConversietabel tabel = new LandConversietabel(Arrays.asList(new Integer("1")));

    @Test
    public void test() {
        final BrpLandCode brpCode = new BrpLandCode(Integer.valueOf("1"));
        final Lo3LandCode lo3Code = new Lo3LandCode("0001");

        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(brpCode, tabel.converteerNaarBrp(lo3Code));

        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(lo3Code, tabel.converteerNaarLo3(brpCode));

        Assert.assertTrue(tabel.valideerLo3(new Lo3LandCode("0001")));
        Assert.assertFalse(tabel.valideerLo3(new Lo3LandCode("0002")));
        Assert.assertTrue(tabel.valideerBrp(new BrpLandCode(Integer.valueOf("1"))));
        Assert.assertFalse(tabel.valideerBrp(new BrpLandCode(Integer.valueOf("2"))));
    }
}
