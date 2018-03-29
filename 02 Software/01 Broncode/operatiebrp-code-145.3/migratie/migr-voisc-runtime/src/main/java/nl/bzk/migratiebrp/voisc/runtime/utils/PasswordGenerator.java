/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.utils;

import java.security.SecureRandom;

/**
 * Password generator.
 */
public final class PasswordGenerator {

    private static final int PWD_LENGTH = 8;

    private static final char[] UPPER_CHARS = new char[]{'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',};
    private static final char[] LOWER_CHARS = new char[]{'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',};
    private static final char[] NUM_CHARS = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private static final char[] SPEC_CHARS = new char[]{'%', '&', '#', '(', ')', '+', '@'};

    private static final int UC_INDEX = 0;
    private static final int LC_INDEX = 1;
    private static final int NC_INDEX = 2;
    private static final int SC_INDEX = 3;

    private PasswordGenerator() {

    }

    /**
     * 1. Een wachtwoord heeft een lengte van minimaal 6 en maximaal 8 tekens (bytes).<br>
     * Als een wachtwoord minder dan 8 tekens heeft wordt het door de server met spaties aangevuld tot 8 tekens.<br>
     * Ieder teken kan alle mogelijke waarden hebben (van 0 tot 255 decimaal). <br>
     * 2. Een teken mag maximaal twee maal voorkomen.<br>
     * 3. De decimale waarden van een opeenvolgende reeks van 3 tekens mag niet met 1 oplopen (bijvoorbeeld ABC) of
     * afdalen (bijvoorbeeld 876). <br>
     * 4. Spaties (decimale waarde 32) mogen alleen voorkomen in de 7e en 8e byte. <br>
     * 5. Een aaneengesloten reeks letters mag alleen een lengte van 1 of 3 hebben. Letters zijn de tekens A...Z
     * (decimaal 65...90) en a...z (97...122). <br>
     * 6. Een aaneengesloten reeks cijfers mag alleen een lengte van 1 of 3 hebben. Cijfers zijn de tekens 0...9
     * (decimaal 48...57). <br>
     * 7. Een wachtwoord met tenminste 3 tekens anders dan letters, cijfers en spaties wordt beschouwd als door een
     * computer gegenereerd. De regels 4, 5 en 6 zijn in dat geval niet van toepassing.
     * @return Het gegenereerde wachtwoord
     */
    public static String generate() {
        int nrOfSpecChars = 0;
        final SecureRandom random = new SecureRandom();
        final char[] pwd = new char[PWD_LENGTH];
        for (int i = 0; i < pwd.length; i++) {
            final int caseIndex = random.nextInt(4);
            int index;
            switch (caseIndex) {
                case UC_INDEX:
                    index = random.nextInt(UPPER_CHARS.length);
                    pwd[i] = UPPER_CHARS[index];
                    break;
                case LC_INDEX:
                    index = random.nextInt(LOWER_CHARS.length);
                    pwd[i] = LOWER_CHARS[index];
                    break;
                case NC_INDEX:
                    index = random.nextInt(NUM_CHARS.length);
                    pwd[i] = NUM_CHARS[index];
                    break;
                case SC_INDEX:
                    nrOfSpecChars++;
                    index = random.nextInt(SPEC_CHARS.length);
                    pwd[i] = SPEC_CHARS[index];
                    break;
                default:
            }
        }
        // Chars vervangen die niet uit de spec-reeks komt totdat er 3 zijn. Zie regel 7.
        final int minNrOfSpecChars = 3;
        while (nrOfSpecChars < minNrOfSpecChars) {
            final int index = random.nextInt(SPEC_CHARS.length);
            final int replaceIndex = random.nextInt(pwd.length);
            if (replacePossible(pwd[replaceIndex])) {
                pwd[replaceIndex] = SPEC_CHARS[index];
                nrOfSpecChars++;
            }
        }
        return new String(pwd);
    }

    private static boolean replacePossible(final char karakter) {
        for (final char specChar : SPEC_CHARS) {
            if (karakter == specChar) {
                return false;
            }
        }
        return true;
    }
}
