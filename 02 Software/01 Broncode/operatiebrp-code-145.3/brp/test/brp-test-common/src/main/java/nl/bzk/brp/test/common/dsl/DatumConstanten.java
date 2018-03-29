/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl;

import com.google.common.collect.Maps;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import org.springframework.util.Assert;

/**
 * Bevat datumconstanten.
 */
public final class DatumConstanten {

    private static final Map<String, DateValue> DATE_MAP = Maps.newHashMap();

    static {
        DATE_MAP.put("eergisteren", new DateValue(-2));
        DATE_MAP.put("gisteren", new DateValue(-1));
        DATE_MAP.put("vandaag", new DateValue(0));
        DATE_MAP.put("morgen", new DateValue(1));
        DATE_MAP.put("overmorgen", new DateValue(2));
    }

    public static LocalDate getPredefinedLocalDateOrNull(final String value) {
        final DateValue dateValue = getDateValue(value);
        if (dateValue != null) {
            return dateValue.getLocalDate();
        }
        return null;
    }

    public static LocalDate getLocalDate(final String value) {
        final LocalDate dateValue = getPredefinedLocalDateOrNull(value);
        if (dateValue != null) {
            return dateValue;
        }
        return LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
    }

    public static ZonedDateTime getDateTime(final String value) {
        final ZonedDateTime dateValue1 = getPredefinedDateTimeOrNull(value);
        if (dateValue1 != null) {
            return dateValue1;
        }
        return DatumFormatterUtil.vanXsdDatumTijdNaarZonedDateTime(value);
    }

    public static ZonedDateTime getPredefinedDateTimeOrNull(final String value) {
        if ("nu".equals(value)) {
            return DatumUtil.nuAlsZonedDateTime();
        }
        final DateValue dateValue = getDateValue(value);
        if (dateValue != null) {
            return dateValue.getZonedDateTime();
        }
        return null;
    }

    public static ZonedDateTime getDate(final String value) {
        final DateValue dateValue = getDateValue(value);
        if (dateValue != null) {
            return dateValue.getZonedDateTime();
        }
        final LocalDate parsedDate = LocalDate.parse(value, DateTimeFormatter.BASIC_ISO_DATE);
        return parsedDate.atStartOfDay(ZoneId.of("Europe/Amsterdam"));
    }

    public static String getBasicIsoDateString(final String value) {
        final DateValue dateValue = getDateValue(value);
        if (dateValue != null) {
            return dateValue.getDateString();
        }
        //parse om te verifieren dat het voldoet aan het basic iso formaat
        LocalDate.parse(value, DateTimeFormatter.BASIC_ISO_DATE);
        return value;
    }

    public static int getBasicIsoDateInt(final String value) {
        final DateValue dateValue = getDateValue(value);
        if (dateValue != null) {
            return dateValue.getDateInt();
        }
        //parse om te verifieren dat het voldoet aan het basic iso formaat
        LocalDate.parse(value, DateTimeFormatter.BASIC_ISO_DATE);
        return Integer.parseInt(value);
    }

    private static DateValue getDateValue(final String value) {
        Assert.notNull(value);
        return DATE_MAP.get(value.toLowerCase());
    }

    private static final class DateValue {

        private ZonedDateTime zonedDateTime;
        private Date date;
        private String dateString;
        private int dateInt;

        DateValue(final int dayOffset) {
            this.zonedDateTime = DatumUtil.nuAlsZonedDateTime().plus(dayOffset, ChronoUnit.DAYS);
            this.date = Date.from(zonedDateTime.withZoneSameInstant(DatumUtil.NL_ZONE_ID).toInstant());
            this.dateString = DatumUtil.vanZonedDateTimeNaarLocalDateNederland(zonedDateTime).format(DateTimeFormatter.BASIC_ISO_DATE);
            this.dateInt = Integer.parseInt(this.dateString);
        }

        ZonedDateTime getZonedDateTime() {
            return zonedDateTime;
        }

        LocalDate getLocalDate() {
            return DatumUtil.vanZonedDateTimeNaarLocalDateNederland(zonedDateTime);
        }

        Date getDate() {
            return date;
        }

        String getDateString() {
            return dateString;
        }

        public int getDateInt() {
            return dateInt;
        }
    }
}
