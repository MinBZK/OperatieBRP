package nl.bzk.brp.datataal.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import org.apache.commons.lang.time.FastDateFormat

/**
 * Created by operatiebrp on 02/10/15.
 */
class DateFormatUtil {

    // FastDateFormat gebruikt daar SimpleDateFormat niet thread safe is.
    private static final String DATUM_FORMAT = "yyyyMMdd";
    private static final FastDateFormat FAST_DATUM_FORMAAT = FastDateFormat.getInstance(DATUM_FORMAT);
    private static final DateFormat DATUM_FORMAAT = new SimpleDateFormat(DATUM_FORMAT);

    /**
     * Format a date to it's string representation.
     *
     * Use groovy's "as Integer" to cast to integer.
     *
     * @param date
     * @return
     */
    public static Integer format(Date date) {
        return FAST_DATUM_FORMAAT.format(date)
    }
    /**
     * Format a date to it's string representation.
     *
     * Use groovy's "as Integer" to cast to integer.
     *
     * @param date
     * @return
     */
    public static Date parse(String date) {
        return DATUM_FORMAAT.parse(date)
    }
}
