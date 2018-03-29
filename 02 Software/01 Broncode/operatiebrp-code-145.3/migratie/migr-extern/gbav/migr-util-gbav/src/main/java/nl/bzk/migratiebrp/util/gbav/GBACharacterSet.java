/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.gbav;

import java.util.Arrays;

/**
 * Verzort de codering van characters in de GBA characterset. Verantwoordelijkheden omvatten Handles the encoding of
 * characters in the GBA characterset. Responsibilities include:
 * <ul>
 * <li>checking of een character code toegestaan is
 * <li>converteren van Teletex naar Unicode en omgekeerd
 * <li>simple/gestripte weergave van GBA characters die diacritisch of speciaal zijn ("bijzondere tekens")
 * </ul>
 * Er zijn 293 toegestane GBA characters. Daarnaast staat het systeem ook de volgende drie Teletex control codes toe:
 * <ul>
 * <li>0x0A linefeed (LF)
 * <li>0x0C formfeed (FF)
 * <li>0x0D cariage return (CR)
 * </ul>
 * <p>
 * In Teletex hebben alleen characters met diacritische tekens een twee byte code. De eerste byte is altijd in de
 * hexadecimale range [C0 .. CF]. Het strippen van diacritische tekens van letters komt neer op het verwijderen van de
 * eerste byte.
 * <p>
 * Referenties:
 * <ul>
 * <li>LO 3.2, Bijlage II Teletex
 * <li>http://www.open-std.org/jtc1/sc22/wg20/docs/n871-bienglrv.htm
 * <li>Dossier Diakrieten
 * </ul>
 */
public final class GBACharacterSet {
    private static final int GBA_CHARACTER_SET_GROOTTE = 296;
    private static final char GBA_ONBEKEND_CHARACTER = '?';

    private static final int MASK_LAATSTE_BYTE = 0xff;
    private static final int MASK_TOP_HALF_LAATSTE_BYTE = 0xF0;
    private static final int DIACRITICAL_MARKER = 0xC0;
    private static final int BYTE_LENGTE = 8;

    // character code definities
    private static final int[] TELETEX_CHARS = new int[GBA_CHARACTER_SET_GROOTTE];

    private static final int[] UNICODE_CHARS = new int[GBA_CHARACTER_SET_GROOTTE];

    private static final String[] STRIPPED_CHARS = new String[GBA_CHARACTER_SET_GROOTTE];

    /*
     * Voor betere performance wordt een paar arrays gebruikt om de juiste conversie te bepalen. De nieuwe code wordt
     * bepaald door de oude code op te zoeken in de eerste array met behulp van binary search. De gevonden index wordt
     * dan gebruikt om de nieuwe code op te zoeken in de tweede array.
     */
    private static final int[] TELETEX_NAAR_UNICODE_INDEX = new int[GBA_CHARACTER_SET_GROOTTE];

    private static final int[] INDEX_UNICODE = new int[GBA_CHARACTER_SET_GROOTTE];

    private static final int[] UNICODE_NAAR_TELETEX_INDEX = new int[GBA_CHARACTER_SET_GROOTTE];

    private static final int[] INDEX_TELETEX = new int[GBA_CHARACTER_SET_GROOTTE];

    private static final int[] UNICODE_NAAR_STRIPPED_INDEX = new int[GBA_CHARACTER_SET_GROOTTE];

    private static final String[] INDEX_STRIPPED = new String[GBA_CHARACTER_SET_GROOTTE];

    static {
        int offset = 0;
        offset = GBACharacterSetData.definieerGBACharacters(offset);

        if (offset != GBA_CHARACTER_SET_GROOTTE) {
            // het aantal definities is niet zoals verwacht
            throw new IllegalStateException("incomplete set van GBA character definities");
        }

        bouwOpzoekIndex(TELETEX_CHARS, UNICODE_CHARS, TELETEX_NAAR_UNICODE_INDEX, INDEX_UNICODE);
        bouwOpzoekIndex(UNICODE_CHARS, TELETEX_CHARS, UNICODE_NAAR_TELETEX_INDEX, INDEX_TELETEX);
        bouwOpzoekIndex(UNICODE_CHARS, STRIPPED_CHARS, UNICODE_NAAR_STRIPPED_INDEX, INDEX_STRIPPED);
    }

    private GBACharacterSet() {
    }

    private static void bouwOpzoekIndex(final int[] vanCodes, final String[] naarCodes, final int[] gesorteerdeCodes, final String[] geindexeerdeCodes) {
        System.arraycopy(vanCodes, 0, gesorteerdeCodes, 0, GBA_CHARACTER_SET_GROOTTE);
        Arrays.sort(gesorteerdeCodes);

        /*
         * Array gesorteerdeCodes bevat de codes in oplopende volgorde. De nieuwe codes worden geplaaatst in de array
         * geindexeerdeCodes.
         * 
         * De volgende code zorgt ervoor dat de nieuwe codes dezelfde index hebben als de corresponderende codes in de
         * array gesorteerdeCodes.
         * 
         * Dit algorithme is O(n^2) maar n is klein.
         */
        for (int gesorteerdeIndex = 0; gesorteerdeIndex < GBA_CHARACTER_SET_GROOTTE; gesorteerdeIndex++) {
            final int gesorteerdeCode = gesorteerdeCodes[gesorteerdeIndex];
            for (int definitieIndex = 0; definitieIndex < GBA_CHARACTER_SET_GROOTTE; definitieIndex++) {
                if (gesorteerdeCode == vanCodes[definitieIndex]) {
                    geindexeerdeCodes[gesorteerdeIndex] = naarCodes[definitieIndex];
                }
            }
        }
    }

    private static void bouwOpzoekIndex(final int[] vanCodes, final int[] naarCodes, final int[] gesorteerdeCodes, final int[] geindexeerdeCodes) {
        System.arraycopy(vanCodes, 0, gesorteerdeCodes, 0, GBA_CHARACTER_SET_GROOTTE);
        Arrays.sort(gesorteerdeCodes);

        /*
         * Array gesorteerdeCodes bevat de codes in oplopende volgorde. De nieuwe codes worden geplaaatst in de array
         * geindexeerdeCodes.
         * 
         * De volgende code zorgt ervoor dat de nieuwe codes dezelfde index hebben als de corresponderende codes in de
         * array gesorteerdeCodes.
         * 
         * Dit algorithme is O(n^2) maar n is klein.
         */
        for (int gesorteerdeIndex = 0; gesorteerdeIndex < GBA_CHARACTER_SET_GROOTTE; gesorteerdeIndex++) {
            final int gesorteerdeCode = gesorteerdeCodes[gesorteerdeIndex];
            for (int definitieIndex = 0; definitieIndex < GBA_CHARACTER_SET_GROOTTE; definitieIndex++) {
                if (gesorteerdeCode == vanCodes[definitieIndex]) {
                    geindexeerdeCodes[gesorteerdeIndex] = naarCodes[definitieIndex];
                }
            }
        }
    }

    /**
     * Definieer een GBA character in Teletex en Unicode codering.
     * @param teletex de teletex character code
     * @param unicode de unicode character code
     * @param stripped de unicode text zonder diacriticals
     * @param offset de offset in de data arrays
     * @return de nieuwe offset in de data arrays
     */
    static int definieerGBAChar(final int teletex, final int unicode, final String stripped, final int offset) {
        if (offset >= GBA_CHARACTER_SET_GROOTTE) {
            throw new IllegalArgumentException("too many GBA character definitions");
        }

        TELETEX_CHARS[offset] = teletex;
        UNICODE_CHARS[offset] = unicode;
        if (stripped != null) {
            STRIPPED_CHARS[offset] = stripped;
        } else {
            STRIPPED_CHARS[offset] = "" + (char) (teletex & MASK_LAATSTE_BYTE);
        }

        return offset + 1;
    }

    /**
     * Converteer de Teletex codering van een GBA character naar Unicode.
     * @param code Teletex code
     * @return corresponderende Unicode, -1 als die niet bestaat
     */
    public static int converteerTeletexNaarUnicode(final int code) {
        final int index = Arrays.binarySearch(TELETEX_NAAR_UNICODE_INDEX, code);
        return index >= 0 ? INDEX_UNICODE[index] : -1;
    }

    /**
     * Converteer de Unicode codering van een GBA character naar Teletex.
     * @param code Unicode code
     * @return corresponderende Teletex code, -1 als die niet bestaat
     */
    public static int converteerUnicodeNaarTeletex(final int code) {
        final int index = Arrays.binarySearch(UNICODE_NAAR_TELETEX_INDEX, code);
        return index >= 0 ? INDEX_TELETEX[index] : -1;
    }

    /**
     * Converteert een String met Teletex character codes naar een String gebaseerd op Unicode met behoud van onbekende
     * characters.
     *
     * Let op: deze method gaat ervan uit dat dubble-byte encodings van Teletex diacriticals in twee characters in
     * beslag nemen. Dit betekend dat de uitvoer String korter kan zijn dan het origineel.
     * @param teletex de Teletex string om te converteren
     * @return de geconverteerde String gebaseerd op Unicode
     */
    public static String converteerTeletexNaarUnicodeBehoudOnbekend(final String teletex) {
        final int lengte = teletex.length();
        final StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < lengte) {
            int character = teletex.charAt(i);
            boolean gecombineerd = false;

            if ((character & MASK_TOP_HALF_LAATSTE_BYTE) == DIACRITICAL_MARKER) {
                // diacritisch teken codering gevonden.
                // combineer twee Teletex bytes tot een code
                character = character << BYTE_LENGTE;
                character |= teletex.charAt(++i);
                gecombineerd = true;
            }
            final int unicode = converteerTeletexNaarUnicode(character);
            if (unicode >= 0) {
                builder.append((char) unicode);
            } else {
                if (gecombineerd) {
                    // doe 1 stap terug om combinerend character in de volgende iteratie te verwerken
                    i--;
                    builder.append((char) (character >> BYTE_LENGTE));
                } else {
                    // Zet onbekende characters intact over
                    builder.append((char) character);
                }
            }
            i++;
        }

        return builder.toString();
    }

    /**
     * Converteert een String met Unicode gebaseerde GBA characters naar een String gebaseerd op Teletex.
     *
     * Let op: deze method gaat ervan uit dat dubble-byte encodings van Teletex diacriticals in twee characters in
     * beslag nemen. Dit betekend dat de uitvoer String langer kan zijn dan het origineel.
     * @param unicode string om te converteren
     * @param eersteOnbekendeCharacter optionele integer array met lengthe > 0. Het eerste element wordt gezet naar de offset van het eerste onbekende GBA
     * character. -1 als er geen onbekend character is.
     * @return geconverteerde String gebaseerd op Teletex.
     */
    public static String converteerUnicodeNaarTeletex(final String unicode, final int[] eersteOnbekendeCharacter) {
        int offsetEersteOnbekende = -1;
        final int lengte = unicode.length();
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lengte; i++) {
            int code = converteerUnicodeNaarTeletex(unicode.charAt(i));
            if (code != -1) {
                if ((code & DIACRITICAL_MARKER << BYTE_LENGTE) == DIACRITICAL_MARKER << BYTE_LENGTE) {
                    // diacritisch teken codering gevonden.
                    // gebruik twee Teletex bytes voor een code
                    builder.append((char) (code >> BYTE_LENGTE));
                    code &= MASK_LAATSTE_BYTE;
                }
                builder.append((char) code);
            } else {
                // het character kan niet worden geconverteerd.
                // onthoud de offset als dit het eerste onbekende character is
                // en substitueer de standaardwaarde voor onbekende characters.
                if (offsetEersteOnbekende == -1) {
                    offsetEersteOnbekende = i;
                }
                builder.append(GBA_ONBEKEND_CHARACTER);
            }
        }

        if (eersteOnbekendeCharacter != null && eersteOnbekendeCharacter.length > 0) {
            eersteOnbekendeCharacter[0] = offsetEersteOnbekende;
        }
        return builder.toString();
    }

    /**
     * Converteert een String met Unicode gebaseerde GBA characters naar een String gebaseerd op Teletex.
     *
     * Let op: deze method gaat ervan uit dat dubble-byte encodings van Teletex diacriticals in twee characters in
     * beslag nemen. Dit betekend dat de uitvoer String langer kan zijn dan het origineel.
     * @param unicode string om te converteren
     * @return geconverteerde String gebaseerd op Teletex.
     */
    public static String converteerUnicodeNaarTeletex(final String unicode) {
        return converteerUnicodeNaarTeletex(unicode, null);
    }

    /**
     * Check of de gegeven String s alleen valide Teletex characters bevat.
     * @param s De String om te checken voor Teletex validatie
     * @return <code>true</code> als alle characters valide Teletex characters zijn, anders <code>false</code>
     */
    public static boolean isValideTeletex(final String s) {
        final int length = s.length();
        int i = 0;
        while (i < length) {
            int character = s.charAt(i);
            if ((character & MASK_TOP_HALF_LAATSTE_BYTE) == DIACRITICAL_MARKER) {
                // diacritisch teken codering gevonden.
                // combineer twee Teletex bytes tot een code
                character = character << BYTE_LENGTE;
                character |= s.charAt(++i);
            }

            if (Arrays.binarySearch(TELETEX_NAAR_UNICODE_INDEX, character) < 0) {
                // Dit character was niet een valide teletex character
                return false;
            }
            i++;
        }
        // de hele string is valide teletex
        return true;
    }

    /**
     * Check of de gegeven String s alleen valide GBAV Unicode characters bevat.
     * @param s De String om te checken voor GBAV Unicode validatie
     * @return <code>true</code> als alle characters valide GBAV Unicode characters zijn, anders <code>false</code>
     */
    public static boolean isValideUnicode(final String s) {
        final int length = s.length();
        int i = 0;
        while (i < length) {
            final int character = s.charAt(i);

            if (Arrays.binarySearch(UNICODE_NAAR_TELETEX_INDEX, character) < 0) {
                // Dit character was niet een valide unicode character
                return false;
            }
            i++;
        }
        // de hele string is valide teletex
        return true;
    }

    /**
     * Converteert een array van Teletex bytes naar een Teletex String. Elk character in de string correspondeerd met
     * een byte in de array.
     * <P>
     * BELANGRIJK!<BR>
     * Om afhandelijkheden van de Java characterset encoding te voorkomen gebruiken we een stricte bit-voor-bit kopie
     * van de array inhoud naar de java string characters. De codering van de String zal daarom hetzelfde zijn als de
     * codering in de array.
     * @param buff array met Teletex bytes
     * @return Teletex String weergave
     */
    public static String convertTeletexByteArrayToString(final byte[] buff) {
        if (buff == null || buff.length == 0) {
            throw new IllegalArgumentException("lege buffer");
        } else {
            final StringBuilder builder = new StringBuilder(buff.length);
            for (final byte b : buff) {
                builder.append((char) (MASK_LAATSTE_BYTE & b));
            }
            return builder.toString();
        }
    }

    /**
     * Converteert een Teletex String naar een array van Teletex bytes. Elk character in de string correspondeerd met
     * een byte in de array.
     * <P>
     * BELANGRIJK!<BR>
     * Om afhandelijkheden van de Java characterset encoding te voorkomen gebruiken we een stricte bit-voor-bit kopie
     * van de java string characters naar de array inhoud. De codering van de array zal daarom hetzelfde zijn als de
     * codering in de String.
     * @param teletex Teletex String weergave
     * @return array met Teletex bytes
     */
    public static byte[] convertTeletexStringToByteArray(final String teletex) {
        if (teletex == null || teletex.length() == 0) {
            throw new IllegalArgumentException("lege string");
        } else {
            final byte[] buffer = new byte[teletex.length()];
            for (int i = 0; i < buffer.length; i++) {
                final char waarde = teletex.charAt(i);
                if (waarde < MASK_LAATSTE_BYTE) {
                    buffer[i] = (byte) waarde;
                } else {
                    // Dit is niet toegestaand.
                    // De string moet Teletex data bevatten waarbij elk character correspondeerd met een byte.
                    // Bijvoorbeeld Teletex character 'U-umlaut' wordt gecodeerd in TWEE characters
                    // met de waarden \u00C8 en \u0055.
                    throw new IllegalArgumentException("niet een byte waarde: " + waarde);
                }
            }
            return buffer;
        }
    }

    /**
     * Converts a string containing Teletex character codes into a string based on Unicode.
     *
     * Note: this routine assumes that double byte encodings take up two characters This means that the resulting stream
     * may be shorter than the original
     * @param s string to be converted
     * @param firstUnknownCharacter optional integer array of length > 0 the first element will receive the offset of the first unknown GBA character, -1 if
     * none
     * @return converted string based on Unicode
     */
    public static String convertTeletex2Unicode(final String s, final int[] firstUnknownCharacter) {
        int offsetFirstUnknown = -1;
        final int length = s.length();
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int character = s.charAt(i);
            if ((character & MASK_TOP_HALF_LAATSTE_BYTE) == DIACRITICAL_MARKER) {
                // diacritic mark encoding found
                // combine two Teletex bytes into one code
                character = character << BYTE_LENGTE;
                character |= s.charAt(++i);
            }
            final int unicode = convertTeletex2Unicode(character);
            if (unicode >= 0) {
                buffer.append((char) unicode);
            } else {
                // the character can not be converted
                // remember its offset if it is the first character
                // and substitute the default for unknown characters
                if (offsetFirstUnknown == -1) {
                    offsetFirstUnknown = i;
                }
                buffer.append(GBA_ONBEKEND_CHARACTER);
            }
        }

        if (firstUnknownCharacter != null && firstUnknownCharacter.length > 0) {
            firstUnknownCharacter[0] = offsetFirstUnknown;
        }
        return buffer.toString();
    }

    /**
     * Converts the Teletex encoding of a GBA character to Unicode.
     * @param code de code die geconverteerd moet worden
     * @return corresponding Unicode, -1 if failed
     */
    public static int convertTeletex2Unicode(final int code) {
        final int index = Arrays.binarySearch(TELETEX_NAAR_UNICODE_INDEX, code);
        return index >= 0 ? INDEX_UNICODE[index] : -1;
    }

    /**
     * Converts a string containing Teletex character codes into a string based on Unicode.
     *
     * Note: this routine assumes that double byte encodings take up two characters This means that the resulting stream
     * may be shorter than the original
     * @param s string to be converted
     * @return converted string based on Unicode
     */
    public static String convertTeletex2Unicode(final String s) {
        return convertTeletex2Unicode(s, null);
    }
}
