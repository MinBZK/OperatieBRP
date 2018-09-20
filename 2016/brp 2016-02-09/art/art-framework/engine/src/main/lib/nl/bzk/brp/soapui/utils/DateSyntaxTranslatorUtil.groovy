package nl.bzk.brp.soapui.utils

import java.text.SimpleDateFormat
import java.util.regex.Pattern

/**
 * Vervangt in een text, bekende placeholders refererend aan datum.
 */
class DateSyntaxTranslatorUtil {

    static final String SEPARATOR_REGEXP = '\\(|,|\\)';

    static def PATTERNS = [
            (~/\[vandaagsql(\(([+-]?\d+,?){3}\))?\]/): 'yyyyMMdd',
            (~/\[vandaag(\(([+-]?\d+,?){3}\))?\]/): 'yyyy-MM-dd',
            (~/\[moment(\(([+-]?\d+,?){5}\))?(\(([+-]?\d+,?){3}\))?\]/): 'yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'',
            (~/\[moment_volgen(\(([+-]?\d+,?){3}\))?\]/): 'yyyy-MM-dd\'T\'HH:mm:ss.SSS'
    ]

    /**
     * Vervangt alle bekende datum placeholders in een tekst.
     *
     * @param timestamp de te gebruiken timestamp in het vervangen
     * @param text tekst waarin placeholders vervangen worden
     * @return tekst met vervangen placeholders
     */
    public static String parseString(String timestamp, String text) {
        vervangPlaceholders(text, timestamp as long)
    }

    /**
     * Vervangt alle bekende datum placeholders in een tekst.
     *
     * @param timestamp de te gebruiken timestamp in het vervangen
     * @param text tekst waarin placeholders vervangen worden
     * @return tekst met vervangen placeholders
     */
    public static String formatTijd(Long timestamp, String text) {
        vervangPlaceholders(text, timestamp)
    }

    /**
     * Vervangt alle bekende datum placeholders in een tekst.
     *
     * @param text de tekst met placeholders
     * @param timestamp de tijd die geformatteerd wordt ingevuld in de placeholders
     * @return de tekst met vervangen placeholders
     */
    public static vervangPlaceholders(String text, Long timestamp) {
        def result = text
        PATTERNS.each { Pattern p, String format ->
            result = result.replaceAll(p) { Object[] it ->
                def placeholder = ((String) it[0]).replaceAll(~/\[/, '').replaceAll(~/\]/, '')
                convertDatumTijdreis(timestamp, placeholder, format)
            }
        }

        result
    }

    /**
     * Berekent een nieuwe datum afhankelijk van de opgegen string, deze kan 0 of 3 of 5 (bij moment) parameters bevatten.
     * De code's moeten 'vandaag' of 'moment' zijn. In geval van 'vandaag' wordt slechts de datum teruggeven, anders datum en tijd.
     * bv. "vandaag, "vandaag()", "vandaag(0,0,0)" geeft het systeem datum terug.
     *     "vandaag (-2, +5, 0)" geeft de datum terug van vandaag 2 jaar geleden en 5 maanden later (maw. 12+7 = 19 maanden geleden)
     * Bij 5 paramters kan ook het uur en de minuut beinvloed worden, bijvoorbeeld moment(0,0,0,-1,-30) voor 1,5 uur geleden.
     * De datum wordt terug gegeven in de formaat yyyyMMdd
     * De datum + tijd wordt terug gegeven in de formaat yyyyMMddHHmmssSS
     *
     * @param waarde de formule
     * @param currentTimeStamp de timestamp
     * @param pattern {@link SimpleDateFormat} pattern
     *
     * @return de berekende datum
     */
    private static String convertDatumTijdreis(long currentTimeStamp,
                                               final String waarde, final String pattern) throws Exception {
        String[] segments = waarde.split(SEPARATOR_REGEXP)
        segments[0] = segments[0].toLowerCase().trim()

        Calendar cal = Calendar.instance
        cal.timeInMillis = currentTimeStamp

        if (segments.length > 1) {
            int year = segments[1].replace('+', ' ').trim() as int
            int month = segments[2].replace('+', ' ').trim() as int
            int day = segments[3].replace('+', ' ').trim() as int
            cal.add(Calendar.YEAR, year);
            cal.add(Calendar.MONTH, month);
            cal.add(Calendar.DATE, day);
        }
        
        if (segments.length > 4) {
            int hour = segments[4].replace('+', ' ').trim() as int
            int minute = segments[5].replace('+', ' ').trim() as int
            cal.add(Calendar.HOUR_OF_DAY, hour);
            cal.add(Calendar.MINUTE, minute);
        }

        SimpleDateFormat df = new SimpleDateFormat(pattern)
        df.setLenient(false)

        switch (segments[0]) {
            case 'vandaag':
            case 'vandaagsql':
                cal.set(Calendar.HOUR, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                return df.format(cal.time)
            case 'moment':
                return df.format(cal.time).substring(0, 24)
            case 'moment_volgen':
                return df.format(cal.time)
            default:
                return ''
        }
    }
}
