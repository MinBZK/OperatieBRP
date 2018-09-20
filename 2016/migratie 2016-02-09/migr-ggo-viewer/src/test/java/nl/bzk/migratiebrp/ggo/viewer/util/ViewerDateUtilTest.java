/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import org.junit.Test;

public class ViewerDateUtilTest {

    @Test
    public void formatDatumSucces() {
        final int datumYYYYMMDD = 20100101;

        assertEquals("Moet gelijk zijn.", ViewerDateUtil.formatDatum(datumYYYYMMDD), "01-01-2010");
    }

    @Test
    public void formatInvalidDatum() {
        final int datumYYYYMMDD = 00000000;

        assertEquals("Moet gelijk zijn.", ViewerDateUtil.formatDatum(datumYYYYMMDD), "00-00-0000");
    }

    @Test
    public void formatInvalidTekorteDatum() {
        final int datumYYYYMMDD = 2010000;

        assertEquals("Moet gelijk zijn.", ViewerDateUtil.formatDatum(datumYYYYMMDD), "00-00-0201");
    }

    @Test
    public void formatInvalidGeenMaandEnDagDatum() {
        final int datumYYYYMMDD = 20100000;

        assertEquals("Moet gelijk zijn.", ViewerDateUtil.formatDatum(datumYYYYMMDD), "00-00-2010");
    }

    @Test
    public void formatDatumTijdSucces() {
        final long datumTijdYYYYMMDDHHMMSSMMM = 20100101010101001L;
        ViewerDateUtil.formatDatumTijd(datumTijdYYYYMMDDHHMMSSMMM);

        assertEquals("Moet gelijk zijn.", ViewerDateUtil.formatDatumTijd(datumTijdYYYYMMDDHHMMSSMMM), "01-01-2010 01:01:01");
    }

    @Test
    public void formatDatumTijdGeenMaandEnDag() {
        final long datumTijdYYYYMMDDHHMMSSMMM = 20100000000000000L;
        ViewerDateUtil.formatDatumTijd(datumTijdYYYYMMDDHHMMSSMMM);

        assertEquals("Moet gelijk zijn.", ViewerDateUtil.formatDatumTijd(datumTijdYYYYMMDDHHMMSSMMM), "00-00-2010 00:00:00");
    }

    @Test
    public void formatDatumTijdGeenDatum() {
        final long datumTijdYYYYMMDDHHMMSSMMM = 00000000000000000L;
        ViewerDateUtil.formatDatumTijd(datumTijdYYYYMMDDHHMMSSMMM);

        assertEquals("Moet gelijk zijn.", ViewerDateUtil.formatDatumTijd(datumTijdYYYYMMDDHHMMSSMMM), "00-00-0000 00:00:00");
    }

    @Test
    public void intToDateSucces() {
        final int date = 20100101;
        final boolean lenient = false;
        final Date datum = ViewerDateUtil.intToDate(date, lenient);

        assertNotNull("Mag niet null zijn.", datum);
    }

    @Test(expected = IllegalArgumentException.class)
    public void intToDateGeenValideDatum() {
        final int date = 20100000;
        final boolean lenient = false;
        ViewerDateUtil.intToDate(date, lenient);

        fail("Er zou een fout op moeten treden.");
    }

    @Test
    public void isValidDateSucces() {
        final int yyyymmddDate = 20100101;

        assertTrue("Zou een valide datum moeten zijn.", ViewerDateUtil.isValidDate(yyyymmddDate));
    }

    @Test
    public void isValidDateInvalid() {
        final int yyyymmddDate = 20100000;

        assertFalse("Zou geen valide datum moeten zijn.", ViewerDateUtil.isValidDate(yyyymmddDate));
    }

    @Test
    public void isValidDateTimeSucces() {
        final long yyymmddhhmmssmmm = 20100101010101001L;

        assertTrue("Zou een valide datum moeten zijn.", ViewerDateUtil.isValidDateTime(yyymmddhhmmssmmm));
    }

    @Test
    public void isValidDateTimeInvalid() {
        final long yyymmddhhmmssmmm = 20100000000000000L;

        assertFalse("Zou geen valide datum moeten zijn.", ViewerDateUtil.isValidDateTime(yyymmddhhmmssmmm));
    }

    @Test
    public void isValidTimeSucces1() {
        final int hhMMss = 10101;

        assertTrue("Zou een valide datum moeten zijn.", ViewerDateUtil.isValidTime(hhMMss));
    }

    @Test
    public void isValidTimeSucces2() {
        final int hhMMss = 0;

        assertTrue("Zou een valide datum moeten zijn.", ViewerDateUtil.isValidTime(hhMMss));
    }

    @Test
    public void isValidTimeSucces3() {
        final int hhMMss = 10;

        assertTrue("Zou een valide datum moeten zijn.", ViewerDateUtil.isValidTime(hhMMss));
    }

    @Test
    public void longToDateSucces() {
        final long longValueOfDate = 20100101010101001L;

        assertNotNull("Omzetten zou moeten lukken.", ViewerDateUtil.longToDate(longValueOfDate));
    }

    @Test
    public void longToDateInvalid() {
        final long longValueOfDate = 0L;

        assertNull("Omzetten zou moeten lukken.", ViewerDateUtil.longToDate(longValueOfDate));
    }

    @Test
    public void longToDateInvalidDate() {
        final long longValueOfDate = 20104101010101001L;

        assertNotNull("Omzetten zou moeten lukken.", ViewerDateUtil.longToDate(longValueOfDate));
    }

}
