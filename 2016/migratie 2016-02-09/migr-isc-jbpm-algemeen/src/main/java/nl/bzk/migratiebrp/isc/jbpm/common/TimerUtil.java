/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import java.util.Date;
import org.jbpm.calendar.BusinessCalendar;
import org.jbpm.calendar.Duration;
import org.jbpm.util.Clock;

/**
 * Helper class for Jbpm timers.
 */
public final class TimerUtil {

    private TimerUtil() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Geef een due date op basis van de huidige datumtijd en de gegeven duration.
     * 
     * @param duration
     *            duration
     * @param herhaling
     *            herhaling
     * @return due date
     */
    public static Date getDueDate(final Duration duration, final Integer herhaling) {
        if (duration == null) {
            return null;
        }

        Date date = Clock.getCurrentTime();
        final int aantal = herhaling == null ? 1 : herhaling < 1 ? 1 : herhaling;
        for (int i = 0; i < aantal; i++) {
            date = new BusinessCalendar().add(date, duration);
        }

        return date;
    }
}
