/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

/**
 * BerichtUtil.
 */
final class BerichtUtil {

    private BerichtUtil() {
    }

    /**
     * Lowercased de gegeven string.
     * @param input de text
     * @return de gelowercasede text
     */
    static String lowercaseFirst(final String input) {
        final char[] chars = input.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
