/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
package nl.moderniseringgba.isc.voisc.utils;

/**
 * Basic text formatting
 * 
 */
public abstract class StringUtil {

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
    public static String fillBefore(String value, final char fillChar, final int totalLength) {
        value = check(value);
        if (value.length() > totalLength) {
            throw new IllegalArgumentException("value too long: " + value);
        }
        if (value.length() == totalLength) {
            return value;
        }
        final StringBuffer sb = new StringBuffer(totalLength);
        for (int i = 0; i < totalLength - value.length(); i++) {
            sb.append(fillChar);
        }
        sb.append(value);
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
     * @param n
     * @return zero-padded string representation
     */
    public static String zeroPadded(final long nr, final int n) {
        final char[] ZERO_PADDING =
                new char[] { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };
        final int MAX_ZEROES = ZERO_PADDING.length;

        if (nr < 0) {
            throw new IllegalArgumentException("Cannot zero pad negative numeric value");
        }
        if (n < 1) {
            throw new IllegalArgumentException("Length of zero-padded value should be at least 1");
        }

        final StringBuffer buffer = new StringBuffer();
        buffer.append(nr);
        if (buffer.length() < n) {
            final int zeroes = n - buffer.length();
            if (zeroes <= MAX_ZEROES) {
                buffer.insert(0, ZERO_PADDING, 0, zeroes);
            } else {
                // let's keep it simple
                for (int i = 0; i < zeroes; i++) {
                    buffer.insert(0, ZERO_PADDING, 0, 1);
                }
            }
        }
        return buffer.toString();
    }
}
