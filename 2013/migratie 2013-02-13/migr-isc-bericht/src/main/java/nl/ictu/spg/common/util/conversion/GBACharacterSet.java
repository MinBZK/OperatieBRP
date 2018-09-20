/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
package nl.ictu.spg.common.util.conversion;

import java.util.Arrays;

/**
 * Handles the encoding of characters in the GBA characterset. Responsibilities include:
 * <ul>
 * <li>checking whether a character code is permitted
 * <li>converting from Teletex to Unicode and vice versa
 * <li>simple/stripped representation (i.e. "slim") of GBA characters that are diacritical or special
 * ("bijzondere tekens")
 * </ul>
 * There are 293 permitted GBA characters. Additionaly the system also allows the use of three Teletex controlcodes:
 * <ul>
 * <li>0x0A linefeed (LF)
 * <li>0x0C formfeed (FF)
 * <li>0x0D cariage return (CR)
 * <p>
 * <p>
 * In Teletex only characters with diacritic marks have a two byte code. The first byte always lies in the hexadecimal
 * range [C0 .. CF]. Stripping the diacritic marks of letters comes down to removing the first byte.
 * <p>
 * References:
 * <ul>
 * <li>LO 3.2, Bijlage II Teletex
 * <li>http://www.open-std.org/jtc1/sc22/wg20/docs/n871-bienglrv.htm
 * <li>Dossier Diakrieten
 * </ul>
 * 
 * 
 * 
 */
public class GBACharacterSet {
    public final static int GBA_CHARACTER_SET_SIZE = 296;
    public final static char GBA_UNKNOWN_CHARACTER = '?';

    // character code definitions
    private static int[] _teletex = new int[GBA_CHARACTER_SET_SIZE];

    private static int[] _unicode = new int[GBA_CHARACTER_SET_SIZE];

    private static String[] _stripped = new String[GBA_CHARACTER_SET_SIZE];

    private static int _offset = 0;

    /*
     * For better performance pairs of lookup arrays are used to determine the correct conversion. The new code is
     * determined by looking up the old code in the first array using binary search. The index thus found is then used
     * to look up the new code in the second array.
     */
    private static int[] _teletex2unicodeIndex = new int[GBA_CHARACTER_SET_SIZE];

    private static int[] _index2unicode = new int[GBA_CHARACTER_SET_SIZE];

    private static int[] _unicode2teletexIndex = new int[GBA_CHARACTER_SET_SIZE];

    private static int[] _index2teletex = new int[GBA_CHARACTER_SET_SIZE];

    private static int[] _unicode2strippedIndex = new int[GBA_CHARACTER_SET_SIZE];

    private static String[] _index2stripped = new String[GBA_CHARACTER_SET_SIZE];

    static {
        definePermittedGBACharacterSet();
    }

    /**
     * Handles the definition of permitted GBA characters and their encodings
     * 
     */
    private static void definePermittedGBACharacterSet() {
        // three control codes are allowed
        defineGBAChar(0x0A, 0x000A, null); // linefeed
        defineGBAChar(0x0D, 0x000D, null); // carriage return
        defineGBAChar(0x0C, 0x000C, null); // formfeed

        defineGBAChar(0x61, 0x0061, null); // LATIN SMALL LETTER A
        defineGBAChar(0x41, 0x0041, null); // LATIN CAPITAL LETTER A
        defineGBAChar(0x62, 0x0062, null); // LATIN SMALL LETTER B
        defineGBAChar(0x42, 0x0042, null); // LATIN CAPITAL LETTER B
        defineGBAChar(0x63, 0x0063, null); // LATIN SMALL LETTER C
        defineGBAChar(0x43, 0x0043, null); // LATIN CAPITAL LETTER C
        defineGBAChar(0x64, 0x0064, null); // LATIN SMALL LETTER D
        defineGBAChar(0x44, 0x0044, null); // LATIN CAPITAL LETTER D
        defineGBAChar(0x65, 0x0065, null); // LATIN SMALL LETTER E
        defineGBAChar(0x45, 0x0045, null); // LATIN CAPITAL LETTER E
        defineGBAChar(0x66, 0x0066, null); // LATIN SMALL LETTER F
        defineGBAChar(0x46, 0x0046, null); // LATIN CAPITAL LETTER F
        defineGBAChar(0x67, 0x0067, null); // LATIN SMALL LETTER G
        defineGBAChar(0x47, 0x0047, null); // LATIN CAPITAL LETTER G
        defineGBAChar(0x68, 0x0068, null); // LATIN SMALL LETTER H
        defineGBAChar(0x48, 0x0048, null); // LATIN CAPITAL LETTER H
        defineGBAChar(0x69, 0x0069, null); // LATIN SMALL LETTER I
        defineGBAChar(0x49, 0x0049, null); // LATIN CAPITAL LETTER I
        defineGBAChar(0x6A, 0x006A, null); // LATIN SMALL LETTER J
        defineGBAChar(0x4A, 0x004A, null); // LATIN CAPITAL LETTER J
        defineGBAChar(0x6B, 0x006B, null); // LATIN SMALL LETTER K
        defineGBAChar(0x4B, 0x004B, null); // LATIN CAPITAL LETTER K
        defineGBAChar(0x6C, 0x006C, null); // LATIN SMALL LETTER L
        defineGBAChar(0x4C, 0x004C, null); // LATIN CAPITAL LETTER L
        defineGBAChar(0x6D, 0x006D, null); // LATIN SMALL LETTER M
        defineGBAChar(0x4D, 0x004D, null); // LATIN CAPITAL LETTER M
        defineGBAChar(0x6E, 0x006E, null); // LATIN SMALL LETTER N
        defineGBAChar(0x4E, 0x004E, null); // LATIN CAPITAL LETTER N
        defineGBAChar(0x6F, 0x006F, null); // LATIN SMALL LETTER O
        defineGBAChar(0x4F, 0x004F, null); // LATIN CAPITAL LETTER O
        defineGBAChar(0x70, 0x0070, null); // LATIN SMALL LETTER P
        defineGBAChar(0x50, 0x0050, null); // LATIN CAPITAL LETTER P
        defineGBAChar(0x71, 0x0071, null); // LATIN SMALL LETTER Q
        defineGBAChar(0x51, 0x0051, null); // LATIN CAPITAL LETTER Q
        defineGBAChar(0x72, 0x0072, null); // LATIN SMALL LETTER R
        defineGBAChar(0x52, 0x0052, null); // LATIN CAPITAL LETTER R
        defineGBAChar(0x73, 0x0073, null); // LATIN SMALL LETTER S
        defineGBAChar(0x53, 0x0053, null); // LATIN CAPITAL LETTER S
        defineGBAChar(0x74, 0x0074, null); // LATIN SMALL LETTER T
        defineGBAChar(0x54, 0x0054, null); // LATIN CAPITAL LETTER T
        defineGBAChar(0x75, 0x0075, null); // LATIN SMALL LETTER U
        defineGBAChar(0x55, 0x0055, null); // LATIN CAPITAL LETTER U
        defineGBAChar(0x76, 0x0076, null); // LATIN SMALL LETTER V
        defineGBAChar(0x56, 0x0056, null); // LATIN CAPITAL LETTER V
        defineGBAChar(0x77, 0x0077, null); // LATIN SMALL LETTER W
        defineGBAChar(0x57, 0x0057, null); // LATIN CAPITAL LETTER W
        defineGBAChar(0x78, 0x0078, null); // LATIN SMALL LETTER X
        defineGBAChar(0x58, 0x0058, null); // LATIN CAPITAL LETTER X
        defineGBAChar(0x79, 0x0079, null); // LATIN SMALL LETTER Y
        defineGBAChar(0x59, 0x0059, null); // LATIN CAPITAL LETTER Y
        defineGBAChar(0x7A, 0x007A, null); // LATIN SMALL LETTER Z
        defineGBAChar(0x5A, 0x005A, null); // LATIN CAPITAL LETTER Z
        defineGBAChar(0xC261, 0x00E1, null); // LATIN SMALL LETTER A WITH ACUTE
        defineGBAChar(0xC241, 0x00C1, null); // LATIN CAPITAL LETTER A WITH ACUTE
        defineGBAChar(0xC263, 0x0107, null); // LATIN SMALL LETTER C WITH ACUTE
        defineGBAChar(0xC243, 0x0106, null); // LATIN CAPITAL LETTER C WITH ACUTE
        defineGBAChar(0xC265, 0x00E9, null); // LATIN SMALL LETTER E WITH ACUTE
        defineGBAChar(0xC245, 0x00C9, null); // LATIN CAPITAL LETTER E WITH ACUTE
        defineGBAChar(0xC269, 0x00ED, null); // LATIN SMALL LETTER I WITH ACUTE
        defineGBAChar(0xC249, 0x00CD, null); // LATIN CAPITAL LETTER I WITH ACUTE
        defineGBAChar(0xC26C, 0x013A, null); // LATIN SMALL LETTER L WITH ACUTE
        defineGBAChar(0xC24C, 0x0139, null); // LATIN CAPITAL LETTER L WITH ACUTE
        defineGBAChar(0xC26E, 0x0144, null); // LATIN SMALL LETTER N WITH ACUTE
        defineGBAChar(0xC24E, 0x0143, null); // LATIN CAPITAL LETTER N WITH ACUTE
        defineGBAChar(0xC26F, 0x00F3, null); // LATIN SMALL LETTER O WITH ACUTE
        defineGBAChar(0xC24F, 0x00D3, null); // LATIN CAPITAL LETTER O WITH ACUTE
        defineGBAChar(0xC272, 0x0155, null); // LATIN SMALL LETTER R WITH ACUTE
        defineGBAChar(0xC252, 0x0154, null); // LATIN CAPITAL LETTER R WITH ACUTE
        defineGBAChar(0xC273, 0x015B, null); // LATIN SMALL LETTER S WITH ACUTE
        defineGBAChar(0xC253, 0x015A, null); // LATIN CAPITAL LETTER S WITH ACUTE
        defineGBAChar(0xC275, 0x00FA, null); // LATIN SMALL LETTER U WITH ACUTE
        defineGBAChar(0xC255, 0x00DA, null); // LATIN CAPITAL LETTER U WITH ACUTE
        defineGBAChar(0xC279, 0x00FD, null); // LATIN SMALL LETTER Y WITH ACUTE
        defineGBAChar(0xC259, 0x00DD, null); // LATIN CAPITAL LETTER Y WITH ACUTE
        defineGBAChar(0xC27A, 0x017A, null); // LATIN SMALL LETTER Z WITH ACUTE
        defineGBAChar(0xC25A, 0x0179, null); // LATIN CAPITAL LETTER Z WITH ACUTE
        defineGBAChar(0xC161, 0x00E0, null); // LATIN SMALL LETTER A WITH GRAVE
        defineGBAChar(0xC141, 0x00C0, null); // LATIN CAPITAL LETTER A WITH GRAVE
        defineGBAChar(0xC165, 0x00E8, null); // LATIN SMALL LETTER E WITH GRAVE
        defineGBAChar(0xC145, 0x00C8, null); // LATIN CAPITAL LETTER E WITH GRAVE
        defineGBAChar(0xC169, 0x00EC, null); // LATIN SMALL LETTER I WITH GRAVE
        defineGBAChar(0xC149, 0x00CC, null); // LATIN CAPITAL LETTER I WITH GRAVE
        defineGBAChar(0xC16F, 0x00F2, null); // LATIN SMALL LETTER O WITH GRAVE
        defineGBAChar(0xC14F, 0x00D2, null); // LATIN CAPITAL LETTER O WITH GRAVE
        defineGBAChar(0xC175, 0x00F9, null); // LATIN SMALL LETTER U WITH GRAVE
        defineGBAChar(0xC155, 0x00D9, null); // LATIN CAPITAL LETTER U WITH GRAVE
        defineGBAChar(0xC361, 0x00E2, null); // LATIN SMALL LETTER A WITH CIRCUMFLEX
        defineGBAChar(0xC341, 0x00C2, null); // LATIN CAPITAL LETTER A WITH CIRCUMFLEX
        defineGBAChar(0xC363, 0x0109, null); // LATIN SMALL LETTER C WITH CIRCUMFLEX
        defineGBAChar(0xC343, 0x0108, null); // LATIN CAPITAL LETTER C WITH CIRCUMFLEX
        defineGBAChar(0xC365, 0x00EA, null); // LATIN SMALL LETTER E WITH CIRCUMFLEX
        defineGBAChar(0xC345, 0x00CA, null); // LATIN CAPITAL LETTER E WITH CIRCUMFLEX
        defineGBAChar(0xC367, 0x011D, null); // LATIN SMALL LETTER G WITH CIRCUMFLEX
        defineGBAChar(0xC347, 0x011C, null); // LATIN CAPITAL LETTER G WITH CIRCUMFLEX
        defineGBAChar(0xC368, 0x0125, null); // LATIN SMALL LETTER H WITH CIRCUMFLEX
        defineGBAChar(0xC348, 0x0124, null); // LATIN CAPITAL LETTER H WITH CIRCUMFLEX
        defineGBAChar(0xC369, 0x00EE, null); // LATIN SMALL LETTER I WITH CIRCUMFLEX
        defineGBAChar(0xC349, 0x00CE, null); // LATIN CAPITAL LETTER I WITH CIRCUMFLEX
        defineGBAChar(0xC36A, 0x0135, null); // LATIN SMALL LETTER J WITH CIRCUMFLEX
        defineGBAChar(0xC34A, 0x0134, null); // LATIN CAPITAL LETTER J WITH CIRCUMFLEX
        defineGBAChar(0xC36F, 0x00F4, null); // LATIN SMALL LETTER O WITH CIRCUMFLEX
        defineGBAChar(0xC34F, 0x00D4, null); // LATIN CAPITAL LETTER O WITH CIRCUMFLEX
        defineGBAChar(0xC373, 0x015D, null); // LATIN SMALL LETTER S WITH CIRCUMFLEX
        defineGBAChar(0xC353, 0x015C, null); // LATIN CAPITAL LETTER S WITH CIRCUMFLEX
        defineGBAChar(0xC375, 0x00FB, null); // LATIN SMALL LETTER U WITH CIRCUMFLEX
        defineGBAChar(0xC355, 0x00DB, null); // LATIN CAPITAL LETTER U WITH CIRCUMFLEX
        defineGBAChar(0xC377, 0x0175, null); // LATIN SMALL LETTER W WITH CIRCUMFLEX
        defineGBAChar(0xC357, 0x0174, null); // LATIN CAPITAL LETTER W WITH CIRCUMFLEX
        defineGBAChar(0xC379, 0x0177, null); // LATIN SMALL LETTER Y WITH CIRCUMFLEX
        defineGBAChar(0xC359, 0x0176, null); // LATIN CAPITAL LETTER Y WITH CIRCUMFLEX
        defineGBAChar(0xC861, 0x00E4, null); // LATIN SMALL LETTER A WITH DIAERESIS
        defineGBAChar(0xC841, 0x00C4, null); // LATIN CAPITAL LETTER A WITH DIAERESIS
        defineGBAChar(0xC865, 0x00EB, null); // LATIN SMALL LETTER E WITH DIAERESIS
        defineGBAChar(0xC845, 0x00CB, null); // LATIN CAPITAL LETTER E WITH DIAERESIS
        defineGBAChar(0xC869, 0x00EF, null); // LATIN SMALL LETTER I WITH DIAERESIS
        defineGBAChar(0xC849, 0x00CF, null); // LATIN CAPITAL LETTER I WITH DIAERESIS
        defineGBAChar(0xC86F, 0x00F6, null); // LATIN SMALL LETTER O WITH DIAERESIS
        defineGBAChar(0xC84F, 0x00D6, null); // LATIN CAPITAL LETTER O WITH DIAERESIS
        defineGBAChar(0xC875, 0x00FC, null); // LATIN SMALL LETTER U WITH DIAERESIS
        defineGBAChar(0xC855, 0x00DC, null); // LATIN CAPITAL LETTER U WITH DIAERESIS
        defineGBAChar(0xC879, 0x00FF, null); // LATIN SMALL LETTER Y WITH DIAERESIS
        defineGBAChar(0xC859, 0x0178, null); // LATIN CAPITAL LETTER Y WITH DIAERESIS
        defineGBAChar(0xC461, 0x00E3, null); // LATIN SMALL LETTER A WITH TILDE
        defineGBAChar(0xC441, 0x00C3, null); // LATIN CAPITAL LETTER A WITH TILDE
        defineGBAChar(0xC46E, 0x00F1, null); // LATIN SMALL LETTER N WITH TILDE
        defineGBAChar(0xC44E, 0x00D1, null); // LATIN CAPITAL LETTER N WITH TILDE
        defineGBAChar(0xC46F, 0x00F5, null); // LATIN SMALL LETTER O WITH TILDE
        defineGBAChar(0xC44F, 0x00D5, null); // LATIN CAPITAL LETTER O WITH TILDE
        defineGBAChar(0xCF63, 0x010D, null); // LATIN SMALL LETTER C WITH CARON
        defineGBAChar(0xCF43, 0x010C, null); // LATIN CAPITAL LETTER C WITH CARON
        defineGBAChar(0xCF64, 0x010F, null); // LATIN SMALL LETTER D WITH CARON
        defineGBAChar(0xCF44, 0x010E, null); // LATIN CAPITAL LETTER D WITH CARON
        defineGBAChar(0xCF65, 0x011B, null); // LATIN SMALL LETTER E WITH CARON
        defineGBAChar(0xCF45, 0x011A, null); // LATIN CAPITAL LETTER E WITH CARON
        defineGBAChar(0xCF6C, 0x013E, null); // LATIN SMALL LETTER L WITH CARON
        defineGBAChar(0xCF4C, 0x013D, null); // LATIN CAPITAL LETTER L WITH CARON
        defineGBAChar(0xCF6E, 0x0148, null); // LATIN SMALL LETTER N WITH CARON
        defineGBAChar(0xCF4E, 0x0147, null); // LATIN CAPITAL LETTER N WITH CARON
        defineGBAChar(0xCF72, 0x0159, null); // LATIN SMALL LETTER R WITH CARON
        defineGBAChar(0xCF52, 0x0158, null); // LATIN CAPITAL LETTER R WITH CARON
        defineGBAChar(0xCF73, 0x0161, null); // LATIN SMALL LETTER S WITH CARON
        defineGBAChar(0xCF53, 0x0160, null); // LATIN CAPITAL LETTER S WITH CARON
        defineGBAChar(0xCF74, 0x0165, null); // LATIN SMALL LETTER T WITH CARON
        defineGBAChar(0xCF54, 0x0164, null); // LATIN CAPITAL LETTER T WITH CARON
        defineGBAChar(0xCF7A, 0x017E, null); // LATIN SMALL LETTER Z WITH CARON
        defineGBAChar(0xCF5A, 0x017D, null); // LATIN CAPITAL LETTER Z WITH CARON
        defineGBAChar(0xC661, 0x0103, null); // LATIN SMALL LETTER A WITH BREVE
        defineGBAChar(0xC641, 0x0102, null); // LATIN CAPITAL LETTER A WITH BREVE
        defineGBAChar(0xC667, 0x011F, null); // LATIN SMALL LETTER G WITH BREVE
        defineGBAChar(0xC647, 0x011E, null); // LATIN CAPITAL LETTER G WITH BREVE
        defineGBAChar(0xC675, 0x016D, null); // LATIN SMALL LETTER U WITH BREVE
        defineGBAChar(0xC655, 0x016C, null); // LATIN CAPITAL LETTER U WITH BREVE
        defineGBAChar(0xCD6F, 0x0151, null); // LATIN SMALL LETTER O WITH DOUBLE ACUTE
        defineGBAChar(0xCD4F, 0x0150, null); // LATIN CAPITAL LETTER O WITH DOUBLE ACUTE
        defineGBAChar(0xCD75, 0x0171, null); // LATIN SMALL LETTER U WITH DOUBLE ACUTE
        defineGBAChar(0xCD55, 0x0170, null); // LATIN CAPITAL LETTER U WITH DOUBLE ACUTE
        defineGBAChar(0xCA61, 0x00E5, null); // LATIN SMALL LETTER A WITH RING ABOVE
        defineGBAChar(0xCA41, 0x00C5, null); // LATIN CAPITAL LETTER A WITH RING ABOVE
        defineGBAChar(0xCA75, 0x016F, null); // LATIN SMALL LETTER U WITH RING ABOVE
        defineGBAChar(0xCA55, 0x016E, null); // LATIN CAPITAL LETTER U WITH RING ABOVE
        defineGBAChar(0xC763, 0x010B, null); // LATIN SMALL LETTER C WITH DOT ABOVE
        defineGBAChar(0xC743, 0x010A, null); // LATIN CAPITAL LETTER C WITH DOT ABOVE
        defineGBAChar(0xC765, 0x0117, null); // LATIN SMALL LETTER E WITH DOT ABOVE
        defineGBAChar(0xC745, 0x0116, null); // LATIN CAPITAL LETTER E WITH DOT ABOVE
        defineGBAChar(0xC767, 0x0121, null); // LATIN SMALL LETTER G WITH DOT ABOVE
        defineGBAChar(0xC747, 0x0120, null); // LATIN CAPITAL LETTER G WITH DOT ABOVE
        defineGBAChar(0xC749, 0x0130, null); // LATIN CAPITAL LETTER I WITH DOT ABOVE
        defineGBAChar(0xF5, 0x0131, "i"); // LATIN SMALL LETTER DOTLESS I
        defineGBAChar(0xC77A, 0x017C, null); // LATIN SMALL LETTER Z WITH DOT ABOVE
        defineGBAChar(0xC75A, 0x017B, null); // LATIN CAPITAL LETTER Z WITH DOT ABOVE
        defineGBAChar(0xC561, 0x0101, null); // LATIN SMALL LETTER A WITH MACRON
        defineGBAChar(0xC541, 0x0100, null); // LATIN CAPITAL LETTER A WITH MACRON
        defineGBAChar(0xC565, 0x0113, null); // LATIN SMALL LETTER E WITH MACRON
        defineGBAChar(0xC545, 0x0112, null); // LATIN CAPITAL LETTER E WITH MACRON
        defineGBAChar(0xC569, 0x012B, null); // LATIN SMALL LETTER I WITH MACRON
        defineGBAChar(0xC549, 0x012A, null); // LATIN CAPITAL LETTER I WITH MACRON
        defineGBAChar(0xC56F, 0x014D, null); // LATIN SMALL LETTER O WITH MACRON
        defineGBAChar(0xC54F, 0x014C, null); // LATIN CAPITAL LETTER O WITH MACRON
        defineGBAChar(0xC575, 0x016B, null); // LATIN SMALL LETTER U WITH MACRON
        defineGBAChar(0xC555, 0x016A, null); // LATIN CAPITAL LETTER U WITH MACRON
        defineGBAChar(0xF2, 0x0111, "d"); // LATIN SMALL LETTER D WITH STROKE
        defineGBAChar(0xE2, 0x0110, "D"); // LATIN CAPITAL LETTER D WITH STROKE
        defineGBAChar(0xF4, 0x0127, "h"); // LATIN SMALL LETTER H WITH STROKE
        defineGBAChar(0xE4, 0x0126, "H"); // LATIN CAPITAL LETTER H WITH STROKE
        defineGBAChar(0xF8, 0x0142, "l"); // LATIN SMALL LETTER L WITH STROKE
        defineGBAChar(0xE8, 0x0141, "L"); // LATIN CAPITAL LETTER L WITH STROKE
        defineGBAChar(0xF9, 0x00F8, "o"); // LATIN SMALL LETTER O WITH STROKE
        defineGBAChar(0xE9, 0x00D8, "O"); // LATIN CAPITAL LETTER O WITH STROKE
        defineGBAChar(0xFD, 0x0167, "t"); // LATIN SMALL LETTER T WITH STROKE
        defineGBAChar(0xED, 0x0166, "T"); // LATIN CAPITAL LETTER T WITH STROKE
        defineGBAChar(0xCB63, 0x00E7, null); // LATIN SMALL LETTER C WITH CEDILLA
        defineGBAChar(0xCB43, 0x00C7, null); // LATIN CAPITAL LETTER C WITH CEDILLA
        defineGBAChar(0xC267, 0x0123, null); // LATIN SMALL LETTER G WITH CEDILLA
        defineGBAChar(0xCB47, 0x0122, null); // LATIN CAPITAL LETTER G WITH CEDILLA
        defineGBAChar(0xCB6B, 0x0137, null); // LATIN SMALL LETTER K WITH CEDILLA
        defineGBAChar(0xCB4B, 0x0136, null); // LATIN CAPITAL LETTER K WITH CEDILLA
        defineGBAChar(0xCB6C, 0x013C, null); // LATIN SMALL LETTER L WITH CEDILLA
        defineGBAChar(0xCB4C, 0x013B, null); // LATIN CAPITAL LETTER L WITH CEDILLA
        defineGBAChar(0xCB6E, 0x0146, null); // LATIN SMALL LETTER N WITH CEDILLA
        defineGBAChar(0xCB4E, 0x0145, null); // LATIN CAPITAL LETTER N WITH CEDILLA
        defineGBAChar(0xCB72, 0x0157, null); // LATIN SMALL LETTER R WITH CEDILLA
        defineGBAChar(0xCB52, 0x0156, null); // LATIN CAPITAL LETTER R WITH CEDILLA
        defineGBAChar(0xCB73, 0x015F, null); // LATIN SMALL LETTER S WITH CEDILLA
        defineGBAChar(0xCB53, 0x015E, null); // LATIN CAPITAL LETTER S WITH CEDILLA
        defineGBAChar(0xCB74, 0x0163, null); // LATIN SMALL LETTER T WITH CEDILLA
        defineGBAChar(0xCB54, 0x0162, null); // LATIN CAPITAL LETTER T WITH CEDILLA
        defineGBAChar(0xCE61, 0x0105, null); // LATIN SMALL LETTER A WITH OGONEK
        defineGBAChar(0xCE41, 0x0104, null); // LATIN CAPITAL LETTER A WITH OGONEK
        defineGBAChar(0xCE65, 0x0119, null); // LATIN SMALL LETTER E WITH OGONEK
        defineGBAChar(0xCE45, 0x0118, null); // LATIN CAPITAL LETTER E WITH OGONEK
        defineGBAChar(0xCE69, 0x012F, null); // LATIN SMALL LETTER I WITH OGONEK
        defineGBAChar(0xCE49, 0x012E, null); // LATIN CAPITAL LETTER I WITH OGONEK
        defineGBAChar(0xCE75, 0x0173, null); // LATIN SMALL LETTER U WITH OGONEK
        defineGBAChar(0xCE55, 0x0172, null); // LATIN CAPITAL LETTER U WITH OGONEK
        defineGBAChar(0xF1, 0x00E6, "ae"); // LATIN SMALL LETTER AE
        defineGBAChar(0xE1, 0x00C6, "AE"); // LATIN CAPITAL LETTER AE
        defineGBAChar(0xFA, 0x0153, "oe"); // LATIN SMALL LIGATURE O E
        defineGBAChar(0xEA, 0x0152, "OE"); // LATIN CAPITAL LIGATURE O E
        defineGBAChar(0xFB, 0x00DF, "ss"); // LATIN SMALL LETTER SHARP S (German)
        defineGBAChar(0xFE, 0x014B, "ng"); // LATIN SMALL LETTER ENG (Sami)
        defineGBAChar(0xEE, 0x014A, "NG"); // LATIN CAPITAL LETTER ENG (Sami)
        defineGBAChar(0xF3, 0x00F0, "d"); // LATIN SMALL LETTER ETH (Icelandic)
        defineGBAChar(0xFC, 0x00FE, "th"); // LATIN SMALL LETTER THORN (Icelandic)
        defineGBAChar(0xEC, 0x00DE, "TH"); // LATIN CAPITAL LETTER THORN (Icelandic)
        defineGBAChar(0xC469, 0x0129, null); // LATIN SMALL LETTER I WITH TILDE
        defineGBAChar(0xC449, 0x0128, null); // LATIN CAPITAL LETTER I WITH TILDE
        defineGBAChar(0xC475, 0x0169, null); // LATIN SMALL LETTER U WITH TILDE
        defineGBAChar(0xC455, 0x0168, null); // LATIN CAPITAL LETTER U WITH TILDE
        defineGBAChar(0xF0, 0x0138, "q"); // LATIN SMALL LETTER KRA (Greenlandic)
        defineGBAChar(0xF7, 0x0140, "l."); // LATIN SMALL LETTER L WITH MIDDLE DOT
        defineGBAChar(0xE7, 0x013F, "L."); // LATIN CAPITAL LETTER L WITH MIDDLE DOT
        defineGBAChar(0xEF, 0x0149, "'n"); // LATIN SMALL LETTER N PRECEDED BY APOSTROPHE
        defineGBAChar(0x31, 0x0031, null); // DIGIT ONE
        defineGBAChar(0x32, 0x0032, null); // DIGIT TWO
        defineGBAChar(0x33, 0x0033, null); // DIGIT THREE
        defineGBAChar(0x34, 0x0034, null); // DIGIT FOUR
        defineGBAChar(0x35, 0x0035, null); // DIGIT FIVE
        defineGBAChar(0x36, 0x0036, null); // DIGIT SIX
        defineGBAChar(0x37, 0x0037, null); // DIGIT SEVEN
        defineGBAChar(0x38, 0x0038, null); // DIGIT EIGHT
        defineGBAChar(0x39, 0x0039, null); // DIGIT NINE
        defineGBAChar(0x30, 0x0030, null); // DIGIT ZERO
        defineGBAChar(0xB2, 0x00B2, "2"); // SUPERSCRIPT TWO
        defineGBAChar(0xB3, 0x00B3, "3"); // SUPERSCRIPT THREE
        defineGBAChar(0xBD, 0x00BD, "1/2"); // VULGAR FRACTION ONE HALF
        defineGBAChar(0xBC, 0x00BC, "1/4"); // VULGAR FRACTION ONE QUARTER
        defineGBAChar(0xBE, 0x00BE, "3/4"); // VULGAR FRACTION THREE QUARTERS
        defineGBAChar(0x2B, 0x002B, null); // PLUS SIGN
        defineGBAChar(0x3C, 0x003C, null); // LESS-THAN SIGN
        defineGBAChar(0x3D, 0x003D, null); // EQUALS SIGN
        defineGBAChar(0x3E, 0x003E, null); // GREATER-THAN SIGN
        defineGBAChar(0xB1, 0x00B1, "+/-"); // PLUS-MINUS SIGN
        defineGBAChar(0xB8, 0x00F7, ":"); // DIVISION SIGN
        defineGBAChar(0xB4, 0x00D7, "x"); // MULTIPLICATION SIGN
        defineGBAChar(0xA8, 0x00A4, "*"); // CURRENCY SIGN
        defineGBAChar(0xA3, 0x00A3, "L"); // POUND SIGN
        defineGBAChar(0xA4, 0x0024, "$"); // DOLLAR SIGN (GBA and ASCII differ!)
        defineGBAChar(0xA2, 0x00A2, "c"); // CENT SIGN
        defineGBAChar(0xA5, 0x00A5, "Y"); // YEN SIGN
        defineGBAChar(0xA6, 0x0023, "#"); // NUMBER SIGN (GBA and ASCII differ!)
        defineGBAChar(0x25, 0x0025, null); // PERCENT SIGN
        defineGBAChar(0x26, 0x0026, null); // AMPERSAND
        defineGBAChar(0x2A, 0x002A, null); // ASTERISK
        defineGBAChar(0x40, 0x0040, "@"); // COMMERCIAL AT
        defineGBAChar(0x5B, 0x005B, "("); // LEFT SQUARE BRACKET
        defineGBAChar(0x5D, 0x005D, ")"); // RIGHT SQUARE BRACKET
        defineGBAChar(0x7C, 0x007C, "|"); // VERTICAL LINE
        defineGBAChar(0xB5, 0x00B5, "\u00B5"); // MICRO SIGN
        defineGBAChar(0xE0, 0x2126, "Ohm"); // OHM SIGN
        defineGBAChar(0xB0, 0x00B0, "o"); // DEGREE SIGN
        defineGBAChar(0xEB, 0x00BA, "o"); // MASCULINE ORDINAL INDICATOR
        defineGBAChar(0xE3, 0x00AA, "a"); // FEMININE ORDINAL INDICATOR
        defineGBAChar(0xA7, 0x00A7, "\u00A7"); // SECTION SIGN
        defineGBAChar(0xB6, 0x00B6, "\u00B6"); // PILCROW SIGN
        defineGBAChar(0xB7, 0x00B7, "."); // MIDDLE DOT
        defineGBAChar(0x20, 0x0020, null); // SPACE
        defineGBAChar(0x21, 0x0021, null); // EXCLAMATION MARK
        defineGBAChar(0xA1, 0x00A1, "!"); // INVERTED EXCLAMATION MARK
        defineGBAChar(0x22, 0x0022, null); // QUOTATION MARK
        defineGBAChar(0x27, 0x0027, null); // APOSTROPHE
        defineGBAChar(0x28, 0x0028, null); // LEFT PARENTHESIS
        defineGBAChar(0x29, 0x0029, null); // RIGHT PARENTHESIS
        defineGBAChar(0x2C, 0x002C, null); // COMMA
        defineGBAChar(0x5F, 0x005F, "_"); // LOW LINE
        defineGBAChar(0x2D, 0x002D, null); // HYPHEN-MINUS
        defineGBAChar(0x2E, 0x002E, null); // FULL STOP
        defineGBAChar(0x2F, 0x002F, null); // SOLIDUS
        defineGBAChar(0x3A, 0x003A, null); // COLON
        defineGBAChar(0x3B, 0x003B, null); // SEMICOLON
        defineGBAChar(0x3F, 0x003F, null); // QUESTION MARK
        defineGBAChar(0xBF, 0x00BF, "?"); // INVERTED QUESTION MARK
        defineGBAChar(0xAB, 0x00AB, "<<"); // LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
        defineGBAChar(0xBB, 0x00BB, ">>"); // RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK

        processGBACharDefinitions();
    }

    /**
     * The definitions need to be processed for fast lookups.
     * 
     * This routine is to be called once all of the definitions have been made.
     * 
     * @see #defineGBAChar(int, int, String)
     */
    private static void processGBACharDefinitions() {
        if (_offset != GBA_CHARACTER_SET_SIZE) {
            throw new IllegalStateException(
            // the number of definitions is not as expected
                    "incomplete set of GBA character definitions");
        }

        createLookupIndex(_teletex, _unicode, _teletex2unicodeIndex, _index2unicode);
        createLookupIndex(_unicode, _teletex, _unicode2teletexIndex, _index2teletex);
        createLookupIndex(_unicode, _stripped, _unicode2strippedIndex, _index2stripped);
    }

    private static void createLookupIndex(
            final int[] fromCodes,
            final String[] toCodes,
            final int[] sortedCodes,
            final String[] indexedCodes) {
        System.arraycopy(fromCodes, 0, sortedCodes, 0, GBA_CHARACTER_SET_SIZE);
        Arrays.sort(sortedCodes);

        /*
         * Array sortedCode contains the codes in ascending order. The new codes are to be placed in the second array
         * indexedCode.
         * 
         * The following coding makes sure that the new codes have the same index as the corresponding codes in array
         * sortedCode.
         * 
         * Note that this algorithm is O(n*n)! Nevertheless, n is very small :)
         */
        for (int sortedIndex = 0; sortedIndex < GBA_CHARACTER_SET_SIZE; sortedIndex++) {
            final int sortedCode = sortedCodes[sortedIndex];
            for (int definitionIndex = 0; definitionIndex < GBA_CHARACTER_SET_SIZE; definitionIndex++) {
                if (sortedCode == fromCodes[definitionIndex]) {
                    // at this point the index containing the character
                    // definition has been determined
                    // the new code can be found at the same position in array
                    // toCode
                    indexedCodes[sortedIndex] = toCodes[definitionIndex];
                }
            }
        }
    }

    private static void createLookupIndex(
            final int[] fromCodes,
            final int[] toCodes,
            final int[] sortedCodes,
            final int[] indexedCodes) {
        System.arraycopy(fromCodes, 0, sortedCodes, 0, GBA_CHARACTER_SET_SIZE);
        Arrays.sort(sortedCodes);

        /*
         * Array sortedCode contains the codes in ascending order. The new codes are to be placed in the second array
         * indexedCode.
         * 
         * The following coding makes sure that the new codes have the same index as the corresponding codes in array
         * sortedCode.
         * 
         * Note that this algorithm is O(n*n)! Nevertheless, n is very small :)
         */
        for (int sortedIndex = 0; sortedIndex < GBA_CHARACTER_SET_SIZE; sortedIndex++) {
            final int sortedCode = sortedCodes[sortedIndex];
            for (int definitionIndex = 0; definitionIndex < GBA_CHARACTER_SET_SIZE; definitionIndex++) {
                if (sortedCode == fromCodes[definitionIndex]) {
                    // at this point the index containing the character
                    // definition has been determined
                    // the new code can be found at the same position in array
                    // toCode
                    indexedCodes[sortedIndex] = toCodes[definitionIndex];
                }
            }
        }
    }

    /**
     * Defines the GBA character by several encodings
     * <p>
     * 
     * @param teletexCode
     * @param uniCode
     * @param stripped
     *            The stripped representation can be a string of several characters. If the parameter contains the value
     *            null, the canonical representation is determined i.e. rightmost byte of teletexCode based on the
     *            assumption that it is a valid ASCII character.
     */
    private static void defineGBAChar(final int teletexCode, final int uniCode, final String stripped) {
        if (_offset >= GBA_CHARACTER_SET_SIZE) {
            throw new RuntimeException("too many GBA character definitions");
        }

        _teletex[_offset] = teletexCode;
        _unicode[_offset] = uniCode;
        if (stripped != null) {
            _stripped[_offset] = stripped;
        } else {
            _stripped[_offset] = "" + (char) (teletexCode & 0xff);
        }

        _offset++;
    }

    /**
     * Creates an array containing permitted GBA characters in Teletex encoding
     * 
     * @return array with Teletex codes
     */
    public static int[] createGBATeletex() {
        return _teletex.clone();
    }

    /**
     * Creates an array containing permitted GBA characters in Unicode encoding
     * 
     * @return array with Unicode codes
     */
    public static int[] createGBAUnicode() {
        return _unicode.clone();
    }

    /**
     * Creates an array containing permitted GBA characters in stripped ASCII representation with loss of information
     * 
     * @return array with ASCII representation
     */
    public static String[] createGBAStripped() {
        return _stripped.clone();
    }

    /**
     * Converts the Teletex encoding of a GBA character to Unicode.
     * 
     * @param code
     * @return corresponding Unicode, -1 if failed
     */
    public static int convertTeletex2Unicode(final int code) {
        final int index = Arrays.binarySearch(_teletex2unicodeIndex, code);
        return (index >= 0) ? _index2unicode[index] : -1;
    }

    /**
     * Converts the Unicode encoding of a GBA character to Teletex.
     * 
     * @param code
     * @return corresponding Teletex code, -1 if failed
     */
    public static int convertUnicode2Teletex(final int code) {
        final int index = Arrays.binarySearch(_unicode2teletexIndex, code);
        return (index >= 0) ? _index2teletex[index] : -1;
    }

    /**
     * Converts the Unicode encoding of a GBA character to a stripped ASCII representation with loss of information.
     * 
     * @param code
     * @return corresponding ASCII string, null if failed
     */
    public static String convertUnicode2Stripped(final int code) {
        final int index = Arrays.binarySearch(_unicode2strippedIndex, code);
        return index >= 0 ? _index2stripped[index] : null;
    }

    /**
     * Converts a string containing Teletex character codes into a string based on Unicode.
     * 
     * Note: this routine assumes that double byte encodings take up two characters This means that the resulting stream
     * may be shorter than the original
     * 
     * @param s
     *            string to be converted
     * @param firstUnknownCharacter
     *            optional integer array of length > 0 the first element will receive the offset of the first unknown
     *            GBA character, -1 if none
     * 
     * @return converted string based on Unicode
     */
    public static String convertTeletex2Unicode(final String s, final int[] firstUnknownCharacter) {
        int offsetFirstUnknown = -1;
        final int length = s.length();
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int character = s.charAt(i);
            if ((character & 0xF0) == 0xC0) {
                // diacritic mark encoding found
                // combine two Teletex bytes into one code
                character = character << 8;
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
                buffer.append(GBA_UNKNOWN_CHARACTER);
            }
        }

        if (firstUnknownCharacter != null && firstUnknownCharacter.length > 0) {
            firstUnknownCharacter[0] = offsetFirstUnknown;
        }
        return buffer.toString();
    }

    /**
     * Converts a string containing Teletex character codes into a string based on Unicode.
     * 
     * Note: this routine assumes that double byte encodings take up two characters This means that the resulting stream
     * may be shorter than the original
     * 
     * @param s
     *            string to be converted
     * @return converted string based on Unicode
     */
    public static String convertTeletex2Unicode(final String s) {
        return convertTeletex2Unicode(s, null);
    }

    /**
     * Strips a unicoded string of GBA characters from diacritical marks and replaces the so-called special characters
     * by one or more standard characters from ASCII. In the case of diacritics stripping is done by examining whether a
     * unicoded character results in a two-byte encoded Teletex character. If so the unicoded representation of the
     * second byte of the Teletex-encoded character is substituted.
     * 
     * @param s
     *            string to be stripped
     * @param firstUnknownCharacter
     *            optional integer array of length > 0 the first element will receive the offset of the first unknown
     *            GBA character, -1 if none
     * 
     * @return unicoded string stripped from diacritical marks and special characters
     */
    public static String convertUnicode2Stripped(final String s, final int[] firstUnknownCharacter) {
        int offsetFirstUnknown = -1;
        final int length = s.length();
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            final String stripped = convertUnicode2Stripped(s.charAt(i));
            if (stripped == null) {
                // the character can not be converted
                // remember its offset if it is the first character
                // and substitute the default for unknown characters
                if (offsetFirstUnknown == -1) {
                    offsetFirstUnknown = i;
                }
                buffer.append(GBA_UNKNOWN_CHARACTER);
            } else {
                buffer.append(stripped);
            }
        }

        if (firstUnknownCharacter != null && firstUnknownCharacter.length > 0) {
            firstUnknownCharacter[0] = offsetFirstUnknown;
        }
        return buffer.toString();
    }

    /**
     * Strips a unicoded string of GBA characters from diacritical marks and replaces the so-called special characters
     * by one or more standard characters from ASCII. In the case of diacritics stripping is done by examining whether a
     * unicoded character results in a two-byte encoded Teletex character. If so the unicoded representation of the
     * second byte of the Teletex-encoded character is substituted.
     * 
     * @param s
     *            string to be stripped
     * @return unicoded string stripped from diacritical marks and special characters
     */
    public static String convertUnicode2Stripped(final String s) {
        return convertUnicode2Stripped(s, null);
    }

    /**
     * Converts a string with unicoded GBA characters into a string based on Teletex.
     * 
     * Note: this routine assumes that double byte encodings take up two characters This means that the resulting string
     * may be longer than the original.
     * 
     * @param s
     *            string to be converted
     * @param firstUnknownCharacter
     *            optional integer array of length > 0 the first element will receive the offset of the first unknown
     *            GBA character, -1 if none
     * 
     * @return converted string based on Teletex. NB. Unknown characters are converted to {@link #GBA_UNKNOWN_CHARACTER}
     *         .
     */
    public static String convertUnicode2Teletex(final String s, final int[] firstUnknownCharacter) {
        int offsetFirstUnknown = -1;
        final int length = s.length();
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int code = convertUnicode2Teletex(s.charAt(i));
            if (code != -1) {
                if ((code & 0xC000) == 0xC000) {
                    // diacritic mark encoding found
                    // represent Teletex bytes as two separate characters

                    // start with writing the first byte
                    buffer.append((char) (code >> 8));

                    // make sure the second byte is written next
                    code &= 0xff;
                }
                buffer.append((char) code);
            } else {
                // the character can not be converted
                // remember its offset if it is the first character
                // and substitute the default for unknown characters
                if (offsetFirstUnknown == -1) {
                    offsetFirstUnknown = i;
                }
                buffer.append(GBA_UNKNOWN_CHARACTER);
            }
        }

        if (firstUnknownCharacter != null && firstUnknownCharacter.length > 0) {
            firstUnknownCharacter[0] = offsetFirstUnknown;
        }
        return buffer.toString();
    }

    /**
     * Invokes {@link #convertUnicode2Teletex(String, int[]) convertUnicode2Teletex(s, null)}.
     */
    public static String convertUnicode2Teletex(final String s) {
        return convertUnicode2Teletex(s, null);
    }

    /**
     * Simple utility function to replace the characters in a string by their hexadecimal notation for testing purposes.
     * 
     * @param s
     * @return
     */
    public static String toHexString(final String s) {
        final int length = s.length();
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(" 0x" + Integer.toHexString(s.charAt(i)));
        }
        return buffer.toString();
    }

    /**
     * Determines whether a unicoded GBA character string contains diacritical marks or special characters.
     * 
     * @param str
     *            is searched for diacritical marks or special characters
     * @return true if <CODE>str</CODE> contains diacritical marks or special characters, else false.
     */
    public static boolean hasDiacriticsOrSpecialCharacters(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        final String noDiacriticsStr = convertUnicode2Stripped(str);
        return !str.equals(noDiacriticsStr);
    }

    /**
     * Check if the provided String s contains only valid Teletex characters.
     * 
     * @param s
     *            The String to check for teletex validation.
     * @return true if all characters in the String s are valid Teletex characters, false otherwise.
     */
    public static boolean isValidTeletex(final String s) {
        final int length = s.length();
        for (int i = 0; i < length; i++) {
            int character = s.charAt(i);
            if ((character & 0xF0) == 0xC0) {
                // diacritic mark encoding found
                // combine two Teletex bytes into one code
                character = character << 8;
                character |= s.charAt(++i);
            }

            if (Arrays.binarySearch(_teletex2unicodeIndex, character) < 0) {
                // Then the character was not a valid teletex character
                return false;
            }
        }
        // if we arrived here, the whole string was valid teletex
        return true;
    }

    /**
     * Convert an array of bytes containing Teletex into a string. Each character in the string corresponds to one byte
     * in the array.
     * 
     * <P>
     * IMPORTANT NOTICE!<BR>
     * In order to prevent dependencies on the platform's default characterset encoding, we will use a strict bitwise
     * copying of the buffer's contents into Java strings. The encoding of the contents of a string will therefore be
     * identical to the encoding used in the array.
     * 
     * @param buff
     *            array of bytes
     * @return String representation
     */
    public static String convertTeletexByteArrayToString(final byte[] buff) {
        if (buff == null || buff.length == 0) {
            throw new IllegalArgumentException("empty buffer");
        } else {
            final StringBuffer buffer = new StringBuffer(buff.length);
            for (int i = 0; i < buff.length; i++) {
                buffer.append((char) (0xff & buff[i]));
            }
            return buffer.toString();
        }
    }

    /**
     * Convert a string containing Teletex into an array of bytes. Each byte in the array corresponds to one character
     * in the string. The numerical value of each character encoding is expected to be within the range 1 to 255.
     * 
     * <P>
     * IMPORTANT NOTICE!<BR>
     * In order to prevent dependencies on the platform's default characterset encoding, we will use a strict bitwise
     * copying of the buffer's contents into Java strings. The encoding of the contents of a string will therefore be
     * identical to the encoding used in the array.
     * 
     * @param buff
     *            array of bytes
     * @return String representation
     */
    public static byte[] convertTeletexStringToByteArray(final String teletex) {
        if (teletex == null || teletex.length() == 0) {
            throw new IllegalArgumentException("empty string");
        } else {
            final byte[] buffer = new byte[teletex.length()];
            for (int i = 0; i < buffer.length; i++) {
                final char value = teletex.charAt(i);
                if (value < 0xff) {
                    buffer[i] = (byte) value;
                } else {
                    // This is not allowed
                    // The string must contain Teletex data whereby each
                    // character corresponds to a byte; e.g.
                    // Teletex character 'U-umlaut' is encoded using TWO characters
                    // having the values \u00C8 and \u0055.
                    throw new IllegalArgumentException("not a byte value: " + value);
                }
            }
            return buffer;
        }
    }

}
