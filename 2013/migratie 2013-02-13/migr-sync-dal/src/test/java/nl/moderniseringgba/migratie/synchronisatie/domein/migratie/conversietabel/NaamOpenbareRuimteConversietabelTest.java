/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.NaamOpenbareRuimteConversietabel;

import org.junit.Assert;
import org.junit.Test;

public class NaamOpenbareRuimteConversietabelTest {

    private final NaamOpenbareRuimteConversietabel tabel = new NaamOpenbareRuimteConversietabel();

    @Test
    public void test() {
        final String brpCode = "Plein 1813";
        final String lo3Code = "Plein 1813";

        assertNull(tabel.converteerNaarBrp(null));
        assertEquals(brpCode, tabel.converteerNaarBrp(lo3Code));

        assertNull(tabel.converteerNaarLo3(null));
        assertEquals(lo3Code, tabel.converteerNaarLo3(brpCode));

        Assert.assertTrue(tabel.valideerLo3("Willekeurig"));
        Assert.assertTrue(tabel.valideerBrp("Willekeurig"));
    }
}
