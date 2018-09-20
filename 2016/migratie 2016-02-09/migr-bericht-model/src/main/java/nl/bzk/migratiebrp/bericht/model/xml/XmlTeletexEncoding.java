/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.xml;

/**
 * Ondersteunende class om teletex strings te coderen en decoderen om ze in XML te kunnen gebruiken. Dit ivm het feit
 * dat in XML de meeste control characters (0x00 t/m 0x1F) niet zijn toegestaan, ook niet als entity encoding of cdata.
 * 
 * De control characters worden gecodeerd als unicode characters 0x1000 t/m 0x101F.
 */
public final class XmlTeletexEncoding {

    private static final char MAX_CONTROL_CHARACTER = 0x1F;
    private static final char ENCODING_OFFSET = 0x1000;

    private static final int EXTRA_ENCODING_BUFFER = 10;

    private XmlTeletexEncoding() {
    }

    /**
     * Codeer een teletex string voor XML.
     * 
     * @param teletex
     *            de teletex string
     * @return de gecodeerde teletex string
     */
    public static String codeer(final String teletex) {
        if (teletex == null) {
            return null;
        }

        final int lengte = teletex.length();
        final StringBuilder sb = new StringBuilder(lengte + EXTRA_ENCODING_BUFFER);

        for (int index = 0; index < lengte; index++) {
            final char c = teletex.charAt(index);
            if (c <= MAX_CONTROL_CHARACTER) {
                sb.append((char) (c + ENCODING_OFFSET));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Decodeer een teletex string uit XML.
     * 
     * @param encodedTeletex
     *            de gecodeerde teletex string
     * @return de teletex string
     */
    public static String decodeer(final String encodedTeletex) {
        if (encodedTeletex == null) {
            return null;
        }

        final int lengte = encodedTeletex.length();
        final StringBuilder sb = new StringBuilder(lengte);

        for (int index = 0; index < lengte; index++) {
            final char c = encodedTeletex.charAt(index);
            if (c >= ENCODING_OFFSET && c <= ENCODING_OFFSET + MAX_CONTROL_CHARACTER) {
                sb.append((char) (c - ENCODING_OFFSET));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
