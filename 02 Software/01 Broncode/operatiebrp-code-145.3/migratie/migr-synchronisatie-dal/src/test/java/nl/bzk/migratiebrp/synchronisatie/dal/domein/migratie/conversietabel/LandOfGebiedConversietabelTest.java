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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.LandConversietabel;
import org.junit.Test;

public class LandOfGebiedConversietabelTest {

    private final LandConversietabel tabel = new LandConversietabel(Collections.singletonList("0001"));

    @Test
    public void test() {
        final BrpLandOfGebiedCode brpCode = new BrpLandOfGebiedCode("0001");
        final Lo3LandCode lo3Code = new Lo3LandCode("0001");

        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(brpCode, tabel.converteerNaarBrp(lo3Code));

        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(lo3Code, tabel.converteerNaarLo3(brpCode));

        assertTrue(tabel.valideerLo3(new Lo3LandCode("0001")));
        assertFalse(tabel.valideerLo3(new Lo3LandCode("0002")));
        assertTrue(tabel.valideerBrp(new BrpLandOfGebiedCode("0001")));
        assertFalse(tabel.valideerBrp(new BrpLandOfGebiedCode("0002")));
    }
}
