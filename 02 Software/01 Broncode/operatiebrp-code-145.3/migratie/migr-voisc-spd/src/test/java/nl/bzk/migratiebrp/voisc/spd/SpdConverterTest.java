/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class SpdConverterTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));

    @Test
    public void parseString() {
        final Instant instant = SpdTimeConverter.convertSpdTimeStringToInstant("1607201200Z");
        assertEquals("2016-07-20 12:00:00", formatter.format(instant));
    }

    @Test
    public void parseYearZero() {
        final Instant instant = SpdTimeConverter.convertSpdTimeStringToInstant("0007201200Z");
        assertEquals("2000-07-20 12:00:00", formatter.format(instant));
    }

    @Test
    public void parseYear50() {
        final Instant instant = SpdTimeConverter.convertSpdTimeStringToInstant("5007201200Z");
        assertEquals("2050-07-20 12:00:00", formatter.format(instant));
    }

    @Test
    public void parseYear51() {
        final Instant instant = SpdTimeConverter.convertSpdTimeStringToInstant("5107201200Z");
        assertEquals("2051-07-20 12:00:00", formatter.format(instant));
    }

    @Test
    public void formatInstant() {
        assertEquals("1611031510Z", SpdTimeConverter.convertInstantToSpdTimeString(Instant.parse("2016-11-03T15:10:30Z")));
    }
}
