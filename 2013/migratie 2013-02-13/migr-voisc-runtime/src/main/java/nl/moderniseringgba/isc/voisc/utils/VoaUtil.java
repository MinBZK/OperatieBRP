/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
package nl.moderniseringgba.isc.voisc.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public final class VoaUtil {

    /**
     * 
     */
    private VoaUtil() {

    }

    /**
     * Convert a TimeString from the sPd-protocol to a Date object.
     * 
     * @param datumtijd
     *            String with Format YYMMDDHHMMZ (Z stands for Zulu or GMT)
     * @return date representatie van de opgegeven waarde
     */
    public static Date convertSpdTimeStringToDate(final String datumtijd) {
        final Calendar cal = Calendar.getInstance();

        // convert year
        int year = Integer.parseInt(datumtijd.substring(0, 2));
        year = year > 50 ? year + 1900 : year + 2000;

        cal.set(year, // year
                Integer.parseInt(datumtijd.substring(2, 4)) - 1, // month (0-based)
                Integer.parseInt(datumtijd.substring(4, 6)), // day
                Integer.parseInt(datumtijd.substring(6, 8)), // hour
                Integer.parseInt(datumtijd.substring(8, 10)) // minute
        );
        return cal.getTime();
    }
}
// CHECKSTYLE:ON
