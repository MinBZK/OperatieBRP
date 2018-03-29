/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

public class DatumFormatterUtilTest {

    @Test
    public void testVanXsdDatumTijdNaarZonedDateTime() {
        final ZonedDateTime zonedDateTime = DatumFormatterUtil.vanXsdDatumTijdNaarZonedDateTime("2016-08-23T10:04:55.144Z");

        Assert.assertEquals(2016, zonedDateTime.getYear());
        Assert.assertEquals(8, zonedDateTime.getMonthValue());
        Assert.assertEquals(23, zonedDateTime.getDayOfMonth());
        Assert.assertEquals(10, zonedDateTime.getHour());
        Assert.assertEquals(4, zonedDateTime.getMinute());
        Assert.assertEquals(55, zonedDateTime.getSecond());
        Assert.assertEquals(144000000, zonedDateTime.getNano());
        Assert.assertNull(DatumFormatterUtil.vanXsdDatumTijdNaarZonedDateTime(null));
    }

    @Test
    public void testVanXsdDatumNaarLocalDate() {
        final LocalDate localDate = DatumFormatterUtil.vanXsdDatumNaarLocalDate("1981-01-22");

        Assert.assertEquals(1981, localDate.getYear());
        Assert.assertEquals(1, localDate.getMonthValue());
        Assert.assertEquals(22, localDate.getDayOfMonth());
        Assert.assertNull(DatumFormatterUtil.vanXsdDatumNaarLocalDate(null));
    }

    @Test
    public void testVanZonedDateTimeNaarXsdString() {
        final ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1980, 2, 1, 1, 1, 1), DatumUtil.BRP_ZONE_ID);

        Assert.assertEquals("1980-02-01T01:01:01.000Z", DatumFormatterUtil.vanZonedDateTimeNaarXsdDateTime(zonedDateTime));
        Assert.assertNull(DatumFormatterUtil.vanZonedDateTimeNaarXsdDateTime(null));
    }


    @Test
    public void testVanLocalDateNaarXsdDate() {
        final LocalDate localDate = LocalDate.of(1980, 2, 1);

        Assert.assertEquals("1980-02-01", DatumFormatterUtil.vanLocalDateNaarXsdDate(localDate));
        Assert.assertNull(DatumFormatterUtil.vanLocalDateNaarXsdDate(null));
    }

    @Test
    public void testVanLocalDateNaarInteger() {
        final LocalDate localDate = LocalDate.of(1980, 2, 1);

        Assert.assertEquals(19800201, (int) DatumFormatterUtil.vanLocalDateNaarInteger(localDate));
        Assert.assertNull(DatumFormatterUtil.vanLocalDateNaarInteger(null));
    }

    @Test
    public void testDatumAlsGetalNaarDatumAlsString() {
        Assert.assertEquals("2014-01-01", DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(20140101));
        Assert.assertEquals("2014-01", DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(20140100));
        Assert.assertEquals("2014", DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(20140000));
        Assert.assertEquals("2014", DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(2014));
        Assert.assertEquals("0014", DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(14));
        Assert.assertEquals("0000", DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(0));
        Assert.assertNull(DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(null));
    }

    @Test
    public void testDeelsOnbekendeDatumAlsStringNaarGetal() {
        Assert.assertEquals(20140101, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-01-01").intValue());
        Assert.assertEquals(20140100, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-01-00").intValue());
        Assert.assertEquals(20140100, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-01").intValue());
        Assert.assertEquals(20140000, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-00-00").intValue());
        Assert.assertEquals(20140000, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-00").intValue());
        Assert.assertEquals(20140000, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014").intValue());
        Assert.assertEquals(0, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("0000").intValue());
        Assert.assertEquals(0, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("0").intValue());
        Assert.assertEquals(190000, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("19").intValue());
        Assert.assertEquals(0, DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("0000-00-00").intValue());

        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal(null));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal(""));
        //foutief gebruik delimiter
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("20140101"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("201402-31"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-1301"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-01-01-01"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-01--01"));
        //foutief gebruik van nullen
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-00-01"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("0000-01-01"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("0000-01-00"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("0000-00-01"));
        //geen geldige kalenderdatum
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-01-99"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-02-29"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-13-31"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-13"));
        // Foute hoeveelheden karakters
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("201-13"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-1"));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal("2014-10-1"));
    }

    @Test
    public void testDeelsOnbekendeDatumAlsGetalNaarString() {
        Assert.assertEquals("2014-01-01", DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(20140101));
        Assert.assertEquals("2014-01-00", DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(20140100));
        Assert.assertEquals("0314-00-00", DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(3140000));
        Assert.assertEquals("0014-00-00", DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(140000));
        Assert.assertEquals("0004-00-00", DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(40000));
        Assert.assertEquals("0000-00-00", DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(0));

        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(201401011));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(20140133));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(20141301));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(20141));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(201401));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(201400));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(2014));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(2014112));
        Assert.assertNull(DatumFormatterUtil.deelsOnbekendeDatumAlsGetalNaarString(null));
    }

    @Test
    public void testBepaalBovengrensDeelsOnbekendeDatum() {
        Assert.assertEquals(Integer.valueOf(20140101), DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(20140101));
        Assert.assertEquals(Integer.valueOf(20140199), DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(20140100));
        Assert.assertEquals(Integer.valueOf(149999), DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(140000));
        Assert.assertEquals(Integer.valueOf(99999999), DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(0));

        Assert.assertNull(DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(201401011));
        Assert.assertNull(DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(20140133));
        Assert.assertNull(DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(20141301));
        Assert.assertNull(DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(20141));
        Assert.assertNull(DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(2014112));
        Assert.assertNull(DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum(null));
    }

}
