/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.AanduidingHuisnummerConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingHuisnummerEnum;

import org.junit.Test;

public class AanduidingHuisnummerConversietabelTest {

    @Test
    public void testNaarBrp() {
        final AanduidingHuisnummerConversietabel tabel = new AanduidingHuisnummerConversietabel();
        assertNull(tabel.converteerNaarBrp(null));
        assertEquals("by", tabel.converteerNaarBrp(Lo3AanduidingHuisnummerEnum.BY.asElement()).getWaarde());
        assertEquals("to", tabel.converteerNaarBrp(Lo3AanduidingHuisnummerEnum.TEGENOVER.asElement()).getWaarde());
    }

    @Test
    public void testNaarLo3() {
        final AanduidingHuisnummerConversietabel tabel = new AanduidingHuisnummerConversietabel();
        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(Lo3AanduidingHuisnummerEnum.BY.asElement(), tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("by")));
        assertEquals(Lo3AanduidingHuisnummerEnum.TEGENOVER.asElement(), tabel.converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("to")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarLo3IAE_BY() {
        new AanduidingHuisnummerConversietabel().converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("BY"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarLo3IAE_bij() {
        new AanduidingHuisnummerConversietabel().converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("bij"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarLo3IAE_BIJ() {
        new AanduidingHuisnummerConversietabel().converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("BIJ"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarLo3IAE_tO() {
        new AanduidingHuisnummerConversietabel().converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("tO"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarLo3IAE_TO() {
        new AanduidingHuisnummerConversietabel().converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("TO"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaarLo3IAE() {
        new AanduidingHuisnummerConversietabel().converteerNaarLo3(new BrpAanduidingBijHuisnummerCode("IAE"));
    }
}
