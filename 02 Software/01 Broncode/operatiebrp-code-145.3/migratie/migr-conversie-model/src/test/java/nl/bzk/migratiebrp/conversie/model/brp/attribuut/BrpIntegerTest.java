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

public class BrpIntegerTest {
    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);

    @Test
    public void testVerwijderOnderzoek() throws Exception {
        BrpInteger code = new BrpInteger(209, onderzoek);
        assertNotNull(code.getOnderzoek());
        final BrpInteger newCode = code.verwijderOnderzoek();
        assertNotNull(code.getOnderzoek());
        assertNull(newCode.getOnderzoek());
        code = new BrpInteger(null, onderzoek);
        assertNotNull(code.getOnderzoek());
        assertNull(code.verwijderOnderzoek());
    }

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpInteger.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        final BrpInteger result = BrpInteger.wrap(207, null);
        assertNull(result.getOnderzoek());
        assertEquals(207, result.getWaarde().intValue());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        final BrpInteger result = BrpInteger.wrap(null, onderzoek);
        assertNotNull(result.getOnderzoek());
        assertNull(result.getWaarde());
    }

    @Test
    public void testBrpInteger() {
        final BrpInteger int1 = new BrpInteger(1, null);
        final BrpInteger int2 = new BrpInteger(1, onderzoek);
        final BrpInteger int3 = new BrpInteger(2, null);
        Assert.assertEquals(0, int1.compareTo(int2));
        Assert.assertEquals(1, int3.compareTo(int1));
        Assert.assertEquals(-1, int1.compareTo(int3));
    }

    @Test(expected = NullPointerException.class)
    public void testBrpIntegerNull() {
        final BrpInteger i = new BrpInteger(12, null);
        i.compareTo(null);
    }

}
