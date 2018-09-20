/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nl.bzk.brp.testdatageneratie.domain.kern.Pers;


/**
 * Utility klasse tbv het generatie proces.
 */
public final class GenUtil {
    private static final int ACHTTIEN = 18;
    private static final int ACHT = 8;
    private static final int NU_MIN_18_JAAR = naarBrpDatum(getDatum18JaarTerug());

    /**
     * Private constructor.
     */
    private GenUtil() {

    }

    /**
     * Geeft datum terug die 18 jaar voor huidige datum is.
     *
     * @return datum 18 jaar terug
     */
    private static Date getDatum18JaarTerug() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - ACHTTIEN);
        return calendar.getTime();
    }

    /**
     * Geeft Date object op basis van BRP integer datum (yyyyMMdd).
     *
     * @param datum datum als integer
     * @return date
     */
    public static Date vanBrpDatum(final Integer datum) {
        try {
            String uitgevuld = "0000000" + datum.toString();
            String afgeknipt = uitgevuld.substring(uitgevuld.length() - ACHT);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            return simpleDateFormat.parse(afgeknipt);
        } catch (ParseException e) {
            throw new RuntimeException(datum + "?", e);
        }
    }

    /**
     * Geeft integer datum (yyyyMMdd) op basis van Date object.
     *
     * @param date the date
     * @return the int
     */
    public static int naarBrpDatum(final Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String randomDate = simpleDateFormat.format(date);
        return Integer.parseInt(randomDate);
    }

    /**
     * Controleert of persoon meerderjarig is.
     *
     * @param pers pers
     * @return true als persoon geboortedatum heeft die eerder is dan datum 18 jaar geleden.
     */
    public static boolean isMeerderjarig(final Pers pers) {
        return pers.getDatgeboorte() <= NU_MIN_18_JAAR;
    }

}
