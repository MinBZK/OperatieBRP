/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class waarmee een datum omgezet kan worden naar een Postgres integer waarde en andersom.
 */
public final class DatumUtil {

    /**
     * Private constructor omdat dit een utility class is.
     */
    private DatumUtil() {
    }

    private static final SimpleDateFormat DATUM_FORMAAT_INT = new SimpleDateFormat("yyyyMMdd");

    /**
     * Geeft de datum van vandaag terug als integer.
     *
     * @return De datum van vandaag als integer.
     */
    public static Integer getDatumVandaagInteger() {
        final String date = DATUM_FORMAAT_INT.format(Calendar.getInstance().getTime());
        return Integer.valueOf(date);
    }

    /**
     * Zet een datum om in een integer.
     *
     * @param datum De om te zetten datum.
     * @return De datum als integer.
     */
    public static Integer zetDatumOmInInteger(final Date datum) {
        final String date = DATUM_FORMAAT_INT.format(datum);
        return Integer.valueOf(date);
    }

    /**
     * Zet een integer om in een datum.
     *
     * @param datum De om te zetten integer datum.
     * @return De datum.
     * @throws ParseException Als de datum niet om te zetten is.
     */
    public static Date zetIntegerOmInDatum(final Integer datum) throws ParseException {
        return DATUM_FORMAAT_INT.parse(String.valueOf(datum));
    }
}
