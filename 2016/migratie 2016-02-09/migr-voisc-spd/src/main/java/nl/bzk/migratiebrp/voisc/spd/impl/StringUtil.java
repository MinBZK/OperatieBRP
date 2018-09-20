/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.impl;

/**
 * Basic text formatting.
 */
public final class StringUtil {

    /**
     * Private constructor.
     */
    private StringUtil() {
        // Niet instantieerbaar.
    }

    /**
     * Vul string op met tekens.
     *
     * @param value
     *            string
     * @param fillChar
     *            vultekens
     * @param totalLength
     *            totale lengte
     * @return opgevulde string met lengte 'totalLength'
     */
    public static String fillBefore(final String value, final char fillChar, final int totalLength) {
        final String workValue = check(value);
        if (workValue.length() > totalLength) {
            throw new IllegalArgumentException("value too long: " + workValue);
        }
        if (workValue.length() == totalLength) {
            return workValue;
        }
        final StringBuilder sb = new StringBuilder(totalLength);
        for (int i = 0; i < totalLength - workValue.length(); i++) {
            sb.append(fillChar);
        }
        sb.append(workValue);
        return sb.toString();
    }

    private static String check(final String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * Returns a zero-padded string of length n.
     *
     * @param nr
     *            numeric value to be zero-padded
     * @param lengte
     *            lengte waarover er nullen toegevoegd moeten worden.
     * @return zero-padded string representation
     */
    public static String zeroPadded(final long nr, final int lengte) {
        final char[] zeroPadding = new char[] {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };
        final int maxZeroes = zeroPadding.length;

        if (nr < 0) {
            throw new IllegalArgumentException("Cannot zero pad negative numeric value");
        }
        if (lengte < 1) {
            throw new IllegalArgumentException("Length of zero-padded value should be at least 1");
        }

        final StringBuilder buffer = new StringBuilder();
        buffer.append(nr);
        if (buffer.length() < lengte) {
            final int zeroes = lengte - buffer.length();
            if (zeroes <= maxZeroes) {
                buffer.insert(0, zeroPadding, 0, zeroes);
            } else {
                // let's keep it simple
                for (int i = 0; i < zeroes; i++) {
                    buffer.insert(0, zeroPadding, 0, 1);
                }
            }
        }
        return buffer.toString();
    }
}
