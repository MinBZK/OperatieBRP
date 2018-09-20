/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BrpDatumTest {

    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), Lo3Datum.NULL_DATUM, null);

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpDatum.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        BrpDatum result = BrpDatum.wrap(20010101, null);
        assertNull(result.getOnderzoek());
        assertEquals(20010101, result.getWaarde().intValue());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        BrpDatum result = BrpDatum.wrap(null, onderzoek);
        assertNotNull(result.getOnderzoek());
        assertNull(result.getWaarde());
    }

    @Test
    public void testConversieLo3Datum() {
        Lo3Datum datum = new Lo3Datum(20050607);
        BrpDatum bDatum = BrpDatum.fromLo3Datum(datum);
        assertEquals(datum, bDatum.converteerNaarLo3Datum());
        datum = new Lo3Datum("20050607", onderzoek);
        bDatum = BrpDatum.fromLo3Datum(datum);
        assertEquals(datum, bDatum.converteerNaarLo3Datum());
        assertNull(bDatum.converteerNaarLo3Datum(true).getOnderzoek());
    }

    @Test(expected = NullPointerException.class)
    public void testCompareNull() {
        BrpDatum datum1 = new BrpDatum(20050607, null);
        datum1.compareTo(null);
    }

    @Test
    public void testCompare() {
        BrpDatum datum1 = new BrpDatum(20050607, null);
        BrpDatum datum2 = new BrpDatum(20050607, null);
        BrpDatum datum3 = new BrpDatum(20050608, null);
        BrpDatum datum4 = new BrpDatum(20050606, null);
        BrpDatum datum5 = new BrpDatum(20050607, onderzoek);
        assertEquals(0, datum1.compareTo(datum2));
        assertEquals(-1, datum1.compareTo(datum3));
        assertEquals(1, datum1.compareTo(datum4));
        assertEquals(0, datum1.compareTo(datum5));
    }

    @Test(expected = NullPointerException.class)
    public void isOnbekendWaardeNull() {
        BrpDatum datum2 = new BrpDatum(null, onderzoek);
        datum2.isOnbekend();
    }

    @Test
    public void isOnbekend() {
        BrpDatum datum1 = new BrpDatum(new Integer("20050607"), null);
        BrpDatum datum2 = new BrpDatum(new Integer("00000607"), null);
        BrpDatum datum7 = new BrpDatum(new Integer("00000600"), null);
        BrpDatum datum8 = new BrpDatum(new Integer("00000007"), null);
        BrpDatum datum3 = new BrpDatum(new Integer("20010007"), null);
        BrpDatum datum4 = new BrpDatum(new Integer("00000000"), null);
        BrpDatum datum5 = new BrpDatum(new Integer("20010000"), null);
        BrpDatum datum6 = new BrpDatum(new Integer("20010600"), null);
        BrpDatum datum9 = new BrpDatum(new Integer("330601"), null);
        BrpDatum datum10 = new BrpDatum(new Integer("030601"), null);

        assertFalse(datum1.isOnbekend());
        assertTrue(datum2.isOnbekend());
        assertTrue(datum3.isOnbekend());
        assertTrue(datum4.isOnbekend());
        assertTrue(datum5.isOnbekend());
        assertTrue(datum6.isOnbekend());
        assertTrue(datum7.isOnbekend());
        assertTrue(datum8.isOnbekend());
        assertFalse(datum9.isOnbekend());
        assertFalse(datum10.isOnbekend());
    }
}
