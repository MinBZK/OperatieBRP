/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.test.dal;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Util class voor timstamp dingetjes.
 */
public final class TimestampUtil {

    private TimestampUtil() {
        // Niet instantieerbaar.
    }

    /**
     * Maak een timestamp met de default gregorian calendar.
     *
     * @param year
     *            year - the value used to set the YEAR calendar field in the calendar.
     * @param month
     *            month - the value used to set the MONTH calendar field in the calendar. Month value is 0-based. e.g.,
     *            0 for January.
     * @param date
     *            dayOfMonth - the value used to set the DAY_OF_MONTH calendar field in the calendar.
     * @param hour
     *            hourOfDay - the value used to set the HOUR_OF_DAY calendar field in the calendar.
     * @param minute
     *            minute - the value used to set the MINUTE calendar field in the calendar.
     * @param second
     *            second - the value used to set the SECOND calendar field in the calendar.
     * @return Timestamp
     */
    public static Timestamp maakTimestamp(final int year, final int month, final int date, final int hour, final int minute, final int second) {
        final Calendar cal = new GregorianCalendar(year, month, date, hour, minute, second);
        return new Timestamp(cal.getTimeInMillis());
    }
}
