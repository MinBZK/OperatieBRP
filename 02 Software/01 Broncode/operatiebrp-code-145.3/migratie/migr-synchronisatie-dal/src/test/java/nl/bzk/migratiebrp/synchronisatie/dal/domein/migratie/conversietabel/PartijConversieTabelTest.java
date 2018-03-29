/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.PartijConversietabel;
import org.junit.Test;

public class PartijConversieTabelTest {

    private final PartijConversietabel tabel = new PartijConversietabel(Collections.singletonList(new Gemeente(
            (short) 123,
            "Test",
            "0001",
            new Partij("Test", "000101"))));

    @Test
    public void test() {
        final BrpPartijCode brpCode = new BrpPartijCode("000101");
        final Lo3GemeenteCode lo3Code = new Lo3GemeenteCode("0001");

        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(brpCode, tabel.converteerNaarBrp(lo3Code));

        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(lo3Code, tabel.converteerNaarLo3(brpCode));

        assertTrue(tabel.valideerLo3(new Lo3GemeenteCode("0001")));
        assertFalse(tabel.valideerLo3(new Lo3GemeenteCode("0002")));
        assertTrue(tabel.valideerBrp(new BrpPartijCode("000101")));
        assertFalse(tabel.valideerBrp(new BrpPartijCode("000002")));

        assertEquals(BrpPartijCode.MINISTER, tabel.converteerNaarBrp(Lo3GemeenteCode.RNI));
        assertEquals(Lo3GemeenteCode.RNI, tabel.converteerNaarLo3(BrpPartijCode.MINISTER));
    }
}
