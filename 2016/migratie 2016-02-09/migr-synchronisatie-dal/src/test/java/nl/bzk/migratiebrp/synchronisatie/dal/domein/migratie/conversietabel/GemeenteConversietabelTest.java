/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.GemeenteConversietabel;
import org.junit.Assert;
import org.junit.Test;

public class GemeenteConversietabelTest {

    private final GemeenteConversietabel tabel = new GemeenteConversietabel(Arrays.asList(new Short("1")));

    @Test
    public void test() {
        final BrpGemeenteCode brpCode = new BrpGemeenteCode(Short.parseShort("1"));
        final Lo3GemeenteCode lo3Code = new Lo3GemeenteCode("0001");

        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(brpCode, tabel.converteerNaarBrp(lo3Code));

        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(lo3Code, tabel.converteerNaarLo3(brpCode));

        Assert.assertTrue(tabel.valideerLo3(new Lo3GemeenteCode("0001")));
        Assert.assertFalse(tabel.valideerLo3(new Lo3GemeenteCode("0002")));
        Assert.assertTrue(tabel.valideerBrp(new BrpGemeenteCode(Short.parseShort("1"))));
        Assert.assertFalse(tabel.valideerBrp(new BrpGemeenteCode(Short.parseShort("2"))));
    }
}
