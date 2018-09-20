/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.vergelijk;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Vergelijk waarden.
 */
public final class Vergelijk {

    private static final String NORMALIZE_LINEFEED_PATTERN = "\r\n";
    private static final String NORMALIZE_LINEFEED_REPLACEMENT = "\n";

    private static final String DECIMAL = "\\{decimal(:\\d*)?\\}";
    private static final Pattern DECIMAL_PATTERN = Pattern.compile(DECIMAL);
    private static final String MESSAGE_ID = "\\{messageId\\}";
    private static final Pattern SPLIT_PATTERN = Pattern.compile(DECIMAL + "|" + MESSAGE_ID);

    private static final String MESSAGEID_REGEX =
            "[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}";
    private static final String DECIMAL_REGEX = "\\d*";

    private Vergelijk() {
        // niet instantieerbaar
    }

    /**
     * Vergelijk.
     * 
     * @param expected
     *            verwachte string (kan {decimal(:x)} en {messageId} bevatten.
     * @param actual
     *            actuele string
     * @return true als de actuele string vergelijkbaar is met de verwachte string
     */
    public static boolean vergelijk(final String expected, final String actual) {
        return vergelijk(new VergelijkingContext(), expected, actual);
    }

    /**
     * Vergelijk (externe context). {decimal:1} moet altijd dezelfde waarde bevatten als dezelfde context wordt gebruikt
     * (ook al zijn het verschillende methode aanroepen).
     * 
     * @param vergelijkingContext
     *            context
     * @param expected
     *            verwachte string (kan {decimal(:x)} en {messageId} bevatten.
     * @param actual
     *            actuele string
     * @return true als de actuele string vergelijkbaar is met de verwachte string
     */
    public static boolean vergelijk(
            final VergelijkingContext vergelijkingContext,
            final String expected,
            final String actual) {
        final String normExpected = expected.replaceAll(NORMALIZE_LINEFEED_PATTERN, NORMALIZE_LINEFEED_REPLACEMENT);
        final String normActual = actual.replaceAll(NORMALIZE_LINEFEED_PATTERN, NORMALIZE_LINEFEED_REPLACEMENT);

        final List<Vergelijking> vergelijkingen = splitExpected(normExpected);
        final String regex = setupRegex(vergelijkingen);
        final Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(normActual);

        boolean result = true;

        if (!matcher.matches()) {
            result = false;
        } else {

            // Checks per vergelijk
            for (int index = 0; index < vergelijkingen.size(); index++) {
                final String part = matcher.group(index + 1);
                if (!vergelijkingen.get(index).check(vergelijkingContext, part)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private static String setupRegex(final List<Vergelijking> vergelijkingen) {
        final StringBuilder regex = new StringBuilder();
        for (final Vergelijking vergelijking : vergelijkingen) {
            regex.append('(').append(vergelijking.getRegex()).append(')');
        }

        return regex.toString();
    }

    private static List<Vergelijking> splitExpected(final String expected) {
        final List<Vergelijking> result = new ArrayList<Vergelijking>();

        final Matcher splitMatcher = SPLIT_PATTERN.matcher(expected);
        int index = 0;

        while (splitMatcher.find()) {
            final int groupStartIndex = splitMatcher.start();
            final int groupEndIndex = splitMatcher.end();

            if (groupStartIndex != index) {
                result.add(new ExactVergelijking(expected.substring(index, groupStartIndex)));
            }

            // Handle group
            result.add(maakVergelijking(splitMatcher.group()));

            index = groupEndIndex;
        }

        if (index != expected.length()) {
            result.add(new ExactVergelijking(expected.substring(index)));
        }

        return result;
    }

    private static Vergelijking maakVergelijking(final String group) {
        Vergelijking result;

        if (Pattern.matches(MESSAGE_ID, group)) {
            result = new RegexVergelijking(MESSAGEID_REGEX);
        } else {
            final Matcher decimalMatcher = DECIMAL_PATTERN.matcher(group);
            if (decimalMatcher.matches()) {
                final String ident = decimalMatcher.group(1);
                if (ident == null || "".equals(ident)) {
                    result = new RegexVergelijking(DECIMAL_REGEX);
                } else {
                    result = new ConstantVariableVergelijking(group, DECIMAL_REGEX);
                }
            } else {
                result = null;
            }
        }

        if (result == null) {
            throw new IllegalStateException();
        }

        return result;
    }
}
