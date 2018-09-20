/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Assert;
import org.junit.Test;

public class BrpLongTest {
    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), Lo3Datum.NULL_DATUM, null);

    @Test
    public void testVerwijderOnderzoek() throws Exception {
        BrpLong code = new BrpLong(209L, onderzoek);
        assertNotNull(code.getOnderzoek());
        BrpLong newCode = code.verwijderOnderzoek();
        assertNotNull(code.getOnderzoek());
        assertNull(newCode.getOnderzoek());
        code = new BrpLong(null, onderzoek);
        assertNotNull(code.getOnderzoek());
        assertNull(code.verwijderOnderzoek());
    }

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpLong.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        BrpLong result = BrpLong.wrap(207L, null);
        assertNull(result.getOnderzoek());
        assertEquals(207, result.getWaarde().intValue());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        BrpLong result = BrpLong.wrap(null, onderzoek);
        assertNotNull(result.getOnderzoek());
        assertNull(result.getWaarde());
    }

    @Test
    public void testBrpLong() {
        BrpLong int1 = new BrpLong(1L, null);
        BrpLong int2 = new BrpLong(1L, onderzoek);
        BrpLong int3 = new BrpLong(2L, null);
        Assert.assertEquals(0, int1.compareTo(int2));
        Assert.assertEquals(1, int3.compareTo(int1));
        Assert.assertEquals(-1, int1.compareTo(int3));
    }

    @Test(expected = NullPointerException.class)
    public void testBrpLongNull() {
        BrpLong i = new BrpLong(12L, null);
        i.compareTo(null);
    }

}
