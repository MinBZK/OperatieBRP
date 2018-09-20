/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nl.bzk.brp.testdatageneratie.domain.kern.Pers;


public class GenUtil {

    private static final int NU_MIN_18_JAAR = naarBrpDatum(getDatum18JaarTerug());

    private static Date getDatum18JaarTerug() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
        return calendar.getTime();
    }

    public static int naarBrpDatum(final Date date) {
        String randomDate = new SimpleDateFormat("yyyyMMdd").format(date);
        return Integer.parseInt(randomDate);
    }

    public static boolean isMeerderjarig(final Pers pers) {
        return pers.getDatgeboorte() <= NU_MIN_18_JAAR;
    }

}
