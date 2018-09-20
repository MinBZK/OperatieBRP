/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.impl;

import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public final class VoaUtil {

    private static final int EIND_INDEX_MINUTEN = 10;
    private static final int EIND_INDEX_UREN = 8;
    private static final int EIND_INDEX_DAG = 6;
    private static final int EIND_INDEX_MAAND = 4;
    private static final int HALVE_EEUW = 50;
    private static final int JAAR_1900 = 1900;
    private static final int JAAR_2000 = 2000;

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
        year = year > HALVE_EEUW ? year + JAAR_1900 : year + JAAR_2000;

        cal.set(
        // jaar
            year,
            // maanden (0-based)
            Integer.parseInt(datumtijd.substring(2, EIND_INDEX_MAAND)) - 1,
            // dagen
            Integer.parseInt(datumtijd.substring(EIND_INDEX_MAAND, EIND_INDEX_DAG)),
            // uren
            Integer.parseInt(datumtijd.substring(EIND_INDEX_DAG, EIND_INDEX_UREN)),
            // minuten
            Integer.parseInt(datumtijd.substring(EIND_INDEX_UREN, EIND_INDEX_MINUTEN)));
        return cal.getTime();
    }
}
