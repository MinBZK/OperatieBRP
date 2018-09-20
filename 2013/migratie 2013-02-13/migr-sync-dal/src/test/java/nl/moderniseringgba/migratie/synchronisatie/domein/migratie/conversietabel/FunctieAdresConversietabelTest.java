/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.FunctieAdresConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3FunctieAdresEnum;

import org.junit.Test;

public class FunctieAdresConversietabelTest {

    @Test
    public void testLo3NaarBrp() {
        final FunctieAdresConversietabel tabel = new FunctieAdresConversietabel();
        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(BrpFunctieAdresCode.B, tabel.converteerNaarBrp(Lo3FunctieAdresEnum.BRIEFADRES.asElement()));
        assertEquals(BrpFunctieAdresCode.W, tabel.converteerNaarBrp(Lo3FunctieAdresEnum.WOONADRES.asElement()));
    }

    @Test
    public void testBrpNaarLo3() {
        final FunctieAdresConversietabel tabel = new FunctieAdresConversietabel();
        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(Lo3FunctieAdresEnum.BRIEFADRES.asElement(), tabel.converteerNaarLo3(BrpFunctieAdresCode.B));
        assertEquals(Lo3FunctieAdresEnum.WOONADRES.asElement(), tabel.converteerNaarLo3(BrpFunctieAdresCode.W));
    }

}
