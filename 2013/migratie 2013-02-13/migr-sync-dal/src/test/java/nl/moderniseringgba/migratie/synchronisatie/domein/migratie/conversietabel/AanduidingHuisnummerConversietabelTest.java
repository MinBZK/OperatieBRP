/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.AanduidingHuisnummerConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingHuisnummerEnum;

import org.junit.Test;

public class AanduidingHuisnummerConversietabelTest {

    @Test
    public void testNaarBrp() {
        final AanduidingHuisnummerConversietabel tabel = new AanduidingHuisnummerConversietabel();
        assertNull(tabel.converteerNaarBrp(null));
        assertEquals("BY", tabel.converteerNaarBrp(Lo3AanduidingHuisnummerEnum.BY.asElement()).getCode());
        assertEquals("TO", tabel.converteerNaarBrp(Lo3AanduidingHuisnummerEnum.TEGENOVER.asElement()).getCode());
    }

    @Test
    public void testNaarLo3() {
        final AanduidingHuisnummerConversietabel tabel = new AanduidingHuisnummerConversietabel();
        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(Lo3AanduidingHuisnummerEnum.BY.asElement(),
                tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("BY")));
        assertEquals(Lo3AanduidingHuisnummerEnum.BY.asElement(),
                tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("by")));
        assertEquals(Lo3AanduidingHuisnummerEnum.BY.asElement(),
                tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("bij")));
        assertEquals(Lo3AanduidingHuisnummerEnum.BY.asElement(),
                tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("BIJ")));

        assertEquals(Lo3AanduidingHuisnummerEnum.TEGENOVER.asElement(),
                tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("to")));
        assertEquals(Lo3AanduidingHuisnummerEnum.TEGENOVER.asElement(),
                tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("tO")));
        assertEquals(Lo3AanduidingHuisnummerEnum.TEGENOVER.asElement(),
                tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("TO")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarLo3IAE() {
        new AanduidingHuisnummerConversietabel().converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("IAE"));
    }
}
