/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testen voor DatumTijdElement.
 */
public class DatumTijdElementTest {

    private final static String REGEXP_STRING = "[0-9]{4}(-(0[1-9]{1}|1[0-2]{1})(-(0[1-9]{1}|[1-2]{1}[0-9]{1}|3[0-1]{1})))(T(("
        + "([0-1]{1}[0-9]{1}|[2]{1}[0-3]{1})"
        + "(:[0-5]{1}[0-9]{1}(:[0-5]{1}[0-9]{1}\\.[0-9]{3}))))((\\+|\\-)([0-1]{1}[0-9]{1}|[2]{1}[0-3]{1}):[0-5]{1}[0-9]{1}|Z))";

    @Test
    public void testTijdzone() throws OngeldigeWaardeException {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(DatumUtil.BRP_TIJDZONE);
        final ZonedDateTime datum = ZonedDateTime.ofInstant(calendar.toInstant(), DatumUtil.BRP_ZONE_ID);
        final DatumTijdElement datumTijdElement = DatumTijdElement.parseWaarde("2016-03-12T00:00:00.000+00:00");
        assertEquals(datum, datumTijdElement.getWaarde());
    }

    @Test
    public void testParseWaarde() throws OngeldigeWaardeException {
        DatumTijdElement.parseWaarde("2016-03-21T09:32:03.234+02:00");
    }

    @Test
    public void testParseWaardeUTC() throws OngeldigeWaardeException {
        final DatumTijdElement datumTijdElementUTC = new DatumTijdElement(ZonedDateTime.parse("2016-03-21T09:32:03.234Z[UTC]"));
        assertEquals(datumTijdElementUTC, DatumTijdElement.parseWaarde("2016-03-21T09:32:03.234Z"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseWaardeNull() throws OngeldigeWaardeException {
        DatumTijdElement.parseWaarde(null);
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void testParseWaardeFout1() throws OngeldigeWaardeException {
        DatumTijdElement.parseWaarde("2016-03-33T09:32:03.234+02:00");
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void testParseWaardeFout2() throws OngeldigeWaardeException {
        DatumTijdElement.parseWaarde("2016-03-01T09:32:03.+02:00");
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void testParseWaardeFout3() throws OngeldigeWaardeException {
        DatumTijdElement.parseWaarde("2016-03-01T09:32:03+02:00");
    }

    @Test(expected = OngeldigeWaardeException.class)
    public void testParseWaardeFout4() throws OngeldigeWaardeException {
        DatumTijdElement.parseWaarde("2016-03-01T09:32:03.234");
    }

    @Test
    public void testEquals() throws OngeldigeWaardeException {
        final DatumTijdElement dt1 = DatumTijdElement.parseWaarde("2016-03-21T09:32:03.234+02:00");
        final DatumTijdElement dt2 = DatumTijdElement.parseWaarde("2016-03-21T09:32:03.234+02:00");
        final DatumTijdElement dt3 = DatumTijdElement.parseWaarde("2016-03-21T09:32:03.235+02:00");
        final DatumTijdElement dt4 = DatumTijdElement.parseWaarde("2016-03-21T07:32:03.234Z");
        assertEquals(dt1, dt2);
        assertEquals(dt1, dt4);
        Assert.assertNotEquals(dt1, dt3);
    }

    @Test
    public void testRegularExpressionTimezoneGMT() {
        final Pattern pattern = Pattern.compile(REGEXP_STRING);
        assertTrue(pattern.matcher("2016-03-21T08:32:03.234+01:00").matches());
    }

    @Test
    public void testRegularExpressionUTC() {
        assertTrue(Pattern.compile(REGEXP_STRING).matcher("2016-03-21T08:32:03.234Z").matches());
    }

    @Test
    public void testToString() {
        final ZonedDateTime datum = ZonedDateTime.parse("2016-09-29T19:00Z[Europe/Amsterdam]");
        final String expectedDatumTijdString = "2016-09-29T17:00:00.000Z";
        assertEquals(expectedDatumTijdString, new DatumTijdElement(datum).toString());
        assertTrue(Pattern.compile(REGEXP_STRING).matcher(expectedDatumTijdString).matches());
    }

    @Test
    public void testToTimestamp() {
        final ZonedDateTime datum = ZonedDateTime.parse("2016-09-29T19:00Z[Europe/Amsterdam]");
        assertEquals("2016-09-29 19:00:00.0", new DatumTijdElement(datum).toTimestamp().toString());
    }

    @Test
    public void testNewWithTimestamp() {
        final ZonedDateTime datum = ZonedDateTime.parse("2016-09-29T19:00Z[Europe/Amsterdam]");
        final DatumTijdElement expectedDatumTijdElement = new DatumTijdElement(datum);
        final Timestamp timestamp = expectedDatumTijdElement.toTimestamp();
        final DatumTijdElement datumTijdElement = new DatumTijdElement(timestamp);
        assertEquals(expectedDatumTijdElement, datumTijdElement);
    }

    @Test
    public void testDifferentZonesSameTime() {
        final DatumTijdElement datumTijdElementAmsterdam = new DatumTijdElement(ZonedDateTime.parse("2016-09-29T19:00Z[Europe/Amsterdam]"));
        final DatumTijdElement datumTijdElementUTC = new DatumTijdElement(ZonedDateTime.parse("2016-09-29T17:00Z[UTC]"));
        assertEquals(datumTijdElementAmsterdam, datumTijdElementUTC);
    }
}
