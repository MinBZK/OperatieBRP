/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import java.util.Calendar;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * Utility class voor datum.
 */
public final class DatumUtil {

    private static final String DATUM_FORMAT = "yyyyMMdd";
    // FastDateFormat gebruikt daar SimpleDateFormat niet thread safe is.
    private static final FastDateFormat DATUM_FORMAAT = FastDateFormat.getInstance(DATUM_FORMAT);

    /**
     * Private constructor.
     */
    private DatumUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Retourneert de huidige datum.
     * @return De huidige datum.
     */
    public static Integer vandaag() {
        final String datumVandaagString = DATUM_FORMAAT.format(Calendar.getInstance().getTime());
        return Integer.parseInt(datumVandaagString);
    }

    /**
     * Retourneert de datum van gisteren.
     * @return De datum van gisteren.
     */
    public static Integer gisteren() {
        final Calendar gisteren = Calendar.getInstance();
        gisteren.add(Calendar.DATE, -1);
        final String gisterenString = DATUM_FORMAAT.format(gisteren.getTime());
        return Integer.parseInt(gisterenString);
    }

    /**
     * Retourneert de datum van morgen.
     * @return De datum van morgen.
     */
    public static Integer morgen() {
        final Calendar morgen = Calendar.getInstance();
        morgen.add(Calendar.DATE, 1);
        final String morgenString = DATUM_FORMAAT.format(morgen.getTime());
        return Integer.parseInt(morgenString);
    }
}
