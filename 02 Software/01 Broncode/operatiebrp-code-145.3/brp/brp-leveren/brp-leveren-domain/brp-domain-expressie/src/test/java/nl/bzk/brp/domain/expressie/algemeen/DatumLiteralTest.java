/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.algemeen;

import static org.junit.Assert.*;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Datumdeel;
import nl.bzk.brp.domain.expressie.DatumtijdLiteral;
import org.junit.Test;

public class DatumLiteralTest {

    @Test
    public void testConstructorDatumAlsInteger() {
        final DatumLiteral datumLiteral = new DatumLiteral(20171231);
        assertEquals(2017, datumLiteral.getJaar().getWaarde());
        assertEquals(12, datumLiteral.getMaand().getWaarde());
        assertEquals(31, datumLiteral.getDag().getWaarde());
    }

    @Test
    public void testConstructorDatumAlsZonedDateTime() {
        final DatumLiteral datumLiteral = new DatumLiteral(
                ZonedDateTime.of(2017, 12, 31, 0, 0,0, 0, DatumUtil.NL_ZONE_ID));
        assertEquals(2017, datumLiteral.getJaar().getWaarde());
        assertEquals(12, datumLiteral.getMaand().getWaarde());
        assertEquals(31, datumLiteral.getDag().getWaarde());
    }

    @Test
    public void testAlsDateTimeVolledigBekendeDatum() {
        final DatumLiteral datumLiteral = new DatumLiteral(20171231);
        final ZonedDateTime zdt =
                ZonedDateTime.of(2017, 12, 31, 0, 0,0, 0, DatumUtil.NL_ZONE_ID);

        assertEquals(zdt, datumLiteral.alsDateTime());
    }

    @Test
    public void testAlsDateTimeNietVolledigBekendeDatum() {
        final DatumLiteral datumLiteral = new DatumLiteral(20170000);
        assertNull(datumLiteral.alsDateTime());
    }

    @Test
    public void testAlsIntegerVolledigBekend() {
        final DatumLiteral datumLiteral = new DatumLiteral(20171231);
        assertEquals(20171231, datumLiteral.alsInteger());
    }

    @Test
    public void testAlsIntegerNietVolledigBekend() {
        final DatumLiteral datumLiteral = new DatumLiteral(20170000);
        assertEquals(20170000, datumLiteral.alsInteger());
    }

    @Test
    public void testDagenInMaand() {
        assertEquals(31, DatumLiteral.dagenInMaand(2017, 01));
        assertEquals(28, DatumLiteral.dagenInMaand(2017, 02));
        assertEquals(30, DatumLiteral.dagenInMaand(2017, 04));
    }

    @Test
    public void testEquals() {
        final DatumLiteral datumLiteral1 = new DatumLiteral(20171231);
        final DatumLiteral datumLiteral2 = new DatumLiteral(20171231);
        final DatumLiteral datumLiteralAfwijkendJaar = new DatumLiteral(20161231);
        final DatumLiteral datumLiteralAfwijkendeMaand = new DatumLiteral(20171131);
        final DatumLiteral datumLiteralAfwijkendeDag = new DatumLiteral(20171201);
        final DatumtijdLiteral datumtijdLiteral =
                new DatumtijdLiteral(Datumdeel.valueOf(2017), Datumdeel.valueOf(12), Datumdeel.valueOf(31), 12, 21, 11);


        assertTrue(datumLiteral1.equals(datumLiteral1));
        assertTrue(datumLiteral1.equals(datumLiteral2));
        assertFalse(datumLiteral1.equals(datumLiteralAfwijkendJaar));
        assertFalse(datumLiteral1.equals(datumLiteralAfwijkendeMaand));
        assertFalse(datumLiteral1.equals(datumLiteralAfwijkendeDag));
        assertFalse(datumLiteral1.equals(null));
        assertFalse(datumLiteral1.equals(datumtijdLiteral));
    }

    @Test
    public void testAlsString() {
        final DatumLiteral datumLiteral = new DatumLiteral(20171231);
        assertEquals("2017/12/31", datumLiteral.alsString());
    }
}