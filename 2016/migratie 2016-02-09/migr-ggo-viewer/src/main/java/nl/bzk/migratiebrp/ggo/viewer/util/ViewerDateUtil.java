/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

/**
 * Date utils voor de Viewer.
 */
public final class ViewerDateUtil {
    private static final int DAY_PRECISION = 100;
    private static final int MONTH_PRECISION = 10000;
    private static final int DATE_PRECISION = 1000000000;
    private static final int TIME_PRECISION = 1000;
    private static final int MINIMUM_DATUM_LENGTE = 8;
    private static final int MINIMUM_DATUMTIJD_LENGTE = 17;

    private static final int AANTAL_UREN_PER_DAG = 24;
    private static final int AANTAL_MINUTEN_PER_UUR = 60;
    private static final int AANTAL_SECONDEN_PER_MINUUT = 60;
    private static final int OMREKENFACTOR_DATUM_CONTROLE_SECONDEN = 100;
    private static final int OMREKENFACTOR_DATUM_CONTROLE_MODULO = 100;
    private static final int OMREKENFACTOR_DATUM_CONTROLE_UREN = 10000;

    private static final int POSITIE_EINDE_JAAR = 4;
    private static final int POSITIE_EINDE_MAAND = 6;
    private static final int POSITIE_EINDE_UUR = 10;
    private static final int POSITIE_EINDE_MINUUT = 12;
    private static final int POSITIE_EINDE_SECONDE = 14;

    private static final String DATUM_SCHEIDINGSTEKEN = "-";
    private static final String TIJD_SCHEIDINGSTEKEN = ":";
    private static final String DATUM_TIJD_UITVULTEKEN = "0";

    private static final String DATUM_FORMAAT_STRING = "dd-MM-yyyy";
    private static final String DATUMTIJD_FORMAAT_STRING = "dd-MM-yyyy HH:mm:ss";
    private static final String UTC = "UTC";

    /**
     * Private constructor.
     */
    private ViewerDateUtil() {
        // Niet instantieerbaar.
    }

    /**
     * Geef de waarde van utc date format.
     *
     * @return utc date format
     */
    private static DateFormat getUtcDateFormat() {

        final DateFormat format = new SimpleDateFormat(DATUMTIJD_FORMAAT_STRING);
        format.setTimeZone(TimeZone.getTimeZone(UTC));
        return format;
    }

    private static int getDay(final int date) {
        return date % DAY_PRECISION;
    }

    private static int getMonth(final int date) {
        return date % MONTH_PRECISION / DAY_PRECISION;
    }

    private static int getYear(final int date) {
        return date / MONTH_PRECISION;
    }

    /**
     * Constructs a Calendar object from an int 'yyyymmdd'.
     *
     * @param date
     *            Given date to convert.
     * @param lenient
     *            true: treat given date leniently, false: don't. See Calendar#setLenient.
     * @return Calendar object with its time set to given date. NB. hours, minutes, secondes and milliseconds are set to
     *         0.
     */
    private static Calendar intToCalendar(final int date, final boolean lenient) {
        final Calendar cal = Calendar.getInstance();
        cal.setLenient(lenient);

        cal.clear();
        cal.set(Calendar.YEAR, getYear(date));

        // NB. Calendar.MONTH months start at 0.
        cal.set(Calendar.MONTH, getMonth(date) - 1);

        // NB. Calendar.DAY_OF_MONTH days starts at 1.
        cal.set(Calendar.DAY_OF_MONTH, getDay(date));

        return cal;
    }

    /**
     * Like {@link #intToCalendar(int, boolean)} but now returns a Date object instead.
     *
     * @param date
     *            Given date to convert.
     * @param lenient
     *            true: treat given date leniently, false: don't. See Calendar#setLenient.
     * @return date
     */
    public static Date intToDate(final int date, final boolean lenient) {
        return intToCalendar(date, lenient).getTime();
    }

    /**
     * This method takes a Long that contains a date (format YYYYMMDDHHMMSSMMM) and converts it to a Date object. If the
     * format is not correct, the method will return null.
     *
     * @param longValueOfDate
     *            Long
     * @return dateResult a Date
     */
    public static Date longToDate(final Long longValueOfDate) {

        // Initialisation
        Date dateResult;
        final Calendar c = Calendar.getInstance();
        c.clear();

        // Convert date value in Long object to string
        final String s = longValueOfDate.toString();

        try {
            // Extract years, months, days, hours, minutes, seconds and milliseconds
            final Integer dateYears = Integer.valueOf(s.substring(0, 4));
            final Integer dateMonths = Integer.valueOf(s.substring(4, 6));
            final Integer dateDays = Integer.valueOf(s.substring(6, 8));
            final Integer dateHours = Integer.valueOf(s.substring(8, 10));
            final Integer dateMinutes = Integer.valueOf(s.substring(10, 12));
            final Integer dateSeconds = Integer.valueOf(s.substring(12, 14));
            final Integer dateMilliSeconds = Integer.valueOf(s.substring(14, 17));

            // Set date fields (notice that the month parameter is zero-based)
            c.set(dateYears, dateMonths - 1, dateDays, dateHours, dateMinutes, dateSeconds);
            c.add(Calendar.MILLISECOND, dateMilliSeconds);

            // Set date in result object
            dateResult = c.getTime();

        } catch (final IndexOutOfBoundsException iobe) {

            // return null
            dateResult = null;

        } catch (final NumberFormatException nfe) {

            // return null
            dateResult = null;
        }

        return dateResult;

    }

    /**
     * Return true if the int parameter is a valid representation of an existing date.
     *
     * @param yyyymmddDate
     *            An int representation of a date in the format YYYYMMDD, i.e. "20061231"
     * @return true if the date is a valid date, false otherwise.
     */
    public static boolean isValidDate(final int yyyymmddDate) {
        try {
            intToDate(yyyymmddDate, false);
        } catch (final IllegalArgumentException iae) {
            // when computing the time, the int was not a valid date
            return false;
        }
        return true;
    }

    /**
     * Return true if the int parameter is a valid representation of a time of day.
     *
     * @param hhMMss
     *            An int representation of a time of day in the format hhMMss, e.g. "145312"
     * @return true if the time is a valid time of day, false otherwise.
     */
    public static boolean isValidTime(final int hhMMss) {
        return !(
                // negative
                hhMMss < 0
                ||
                // seconds not in range [0,60)
                hhMMss % OMREKENFACTOR_DATUM_CONTROLE_SECONDEN >= AANTAL_SECONDEN_PER_MINUUT
                ||
                // minutes not in range [0,60)
                hhMMss / OMREKENFACTOR_DATUM_CONTROLE_SECONDEN % OMREKENFACTOR_DATUM_CONTROLE_MODULO >= AANTAL_MINUTEN_PER_UUR
                ||
                // hours not in range [0,24)
                hhMMss / OMREKENFACTOR_DATUM_CONTROLE_UREN >= AANTAL_UREN_PER_DAG);
    }

    /**
     * Return true if the long parameter is a valid representation of an existing date and a time of day.
     *
     * @param yyymmddhhmmss
     *            long
     * @return true if the datetime is valid, false otherwise.
     */
    public static boolean isValidDateTime(final long yyymmddhhmmss) {
        final boolean isValidDatePart = ViewerDateUtil.isValidDate((int) (yyymmddhhmmss / DATE_PRECISION));
        final boolean isValidTimePart = ViewerDateUtil.isValidTime((int) (yyymmddhhmmss % DATE_PRECISION / TIME_PRECISION));
        return isValidDatePart && isValidTimePart;
    }

    /**
     * Format een datum notatie (int yyyymmdd) naar een String notatie (dd-MM-yyyy).
     *
     * @param datumYYYYMMDD
     *            int
     * @return formatted datum String (dd-MM-yyyy)
     */
    public static String formatDatum(final int datumYYYYMMDD) {
        String datum;
        if (datumYYYYMMDD == 0) {
            datum = "00-00-0000";
        } else if (ViewerDateUtil.isValidDate(datumYYYYMMDD)) {
            // format
            final Date formattedDate = ViewerDateUtil.intToDate(datumYYYYMMDD, false);
            datum = new SimpleDateFormat(DATUM_FORMAAT_STRING).format(formattedDate);
        } else {
            datum = formatInvalidDatum(StringUtils.leftPad(String.valueOf(datumYYYYMMDD), MINIMUM_DATUM_LENGTE, DATUM_TIJD_UITVULTEKEN));
        }
        return datum;
    }

    /**
     * Format een datum-tijd notatie op basis van een Timestamp.
     *
     * @param timestamp
     *            timestamp
     * @return formatted datum String (dd-MM-yyyy HH:mm:ss)
     */
    public static String formatDatumTijd(final Timestamp timestamp) {
        return new SimpleDateFormat(DATUMTIJD_FORMAAT_STRING).format(new Date(timestamp.getTime()));
    }

    /**
     * Format een datum-tijd notatie op basis van een Timestamp. Ga ervan uit dat de opgegeven datumtijd in UTC is.
     *
     * @param timestamp
     *            timestamp
     * @return formatted datum String (dd-MM-yyyy HH:mm:ss)
     */
    public static String formatDatumTijdUtc(final Timestamp timestamp) {
        return getUtcDateFormat().format(new Date(timestamp.getTime()));
    }

    /**
     * Format een datum-tijd notatie (long yyyymmddhhmmssµµµ) naar een String notatie (dd-MM-yyyy hh:mm:ss).
     *
     * @param datumTijdYYYYMMDDHHMMSSMMM
     *            long
     * @return formatted datum String (dd-MM-yyyy HH:mm:ss)
     */
    public static String formatDatumTijd(final long datumTijdYYYYMMDDHHMMSSMMM) {
        String datumTijd;
        if (datumTijdYYYYMMDDHHMMSSMMM == 0) {
            datumTijd = "00-00-0000 00:00:00";
        } else if (ViewerDateUtil.isValidDateTime(datumTijdYYYYMMDDHHMMSSMMM)) {
            // format
            final Date brpDatumTijd = ViewerDateUtil.longToDate(datumTijdYYYYMMDDHHMMSSMMM);
            datumTijd = new SimpleDateFormat(DATUMTIJD_FORMAAT_STRING).format(brpDatumTijd);
        } else {
            datumTijd =
                    formatInvalidDatumTijd(StringUtils.leftPad(
                        String.valueOf(datumTijdYYYYMMDDHHMMSSMMM),
                        MINIMUM_DATUMTIJD_LENGTE,
                        DATUM_TIJD_UITVULTEKEN));
        }
        return datumTijd;
    }

    private static String formatInvalidDatum(final String datum) {
        return datum.substring(POSITIE_EINDE_MAAND, MINIMUM_DATUM_LENGTE)
               + DATUM_SCHEIDINGSTEKEN
               + datum.substring(POSITIE_EINDE_JAAR, POSITIE_EINDE_MAAND)
               + DATUM_SCHEIDINGSTEKEN
               + datum.substring(0, POSITIE_EINDE_JAAR);
    }

    private static String formatInvalidDatumTijd(final String datumTijd) {
        return formatInvalidDatum(datumTijd)
               + " "
               + datumTijd.substring(MINIMUM_DATUM_LENGTE, POSITIE_EINDE_UUR)
               + TIJD_SCHEIDINGSTEKEN
               + datumTijd.substring(POSITIE_EINDE_UUR, POSITIE_EINDE_MINUUT)
               + TIJD_SCHEIDINGSTEKEN
               + datumTijd.substring(POSITIE_EINDE_MINUUT, POSITIE_EINDE_SECONDE);
    }

}
