/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.gbav;

/**
 * De data definities voor de GBACharacterSet.
 */
final class GBACharacterSetData {
    private GBACharacterSetData() {
    }

    /**
     * Bevat de character definities voor de GBA Teletex character set.
     * @param startOffset de start offset in de GBACharacterSet data arrays.
     * @return de nieuwe offset in de GBACharacterSet data arrays.
     */
    static int definieerGBACharacters(final int startOffset) {
        int offset = startOffset;

        // three control codes are allowed
        offset = GBACharacterSet.definieerGBAChar(0x0A, 0x000A, null, offset); // linefeed
        offset = GBACharacterSet.definieerGBAChar(0x0D, 0x000D, null, offset); // carriage return
        offset = GBACharacterSet.definieerGBAChar(0x0C, 0x000C, null, offset); // formfeed

        offset = GBACharacterSet.definieerGBAChar(0x61, 0x0061, null, offset); // LATIN SMALL LETTER A
        offset = GBACharacterSet.definieerGBAChar(0x41, 0x0041, null, offset); // LATIN CAPITAL LETTER A
        offset = GBACharacterSet.definieerGBAChar(0x62, 0x0062, null, offset); // LATIN SMALL LETTER B
        offset = GBACharacterSet.definieerGBAChar(0x42, 0x0042, null, offset); // LATIN CAPITAL LETTER B
        offset = GBACharacterSet.definieerGBAChar(0x63, 0x0063, null, offset); // LATIN SMALL LETTER C
        offset = GBACharacterSet.definieerGBAChar(0x43, 0x0043, null, offset); // LATIN CAPITAL LETTER C
        offset = GBACharacterSet.definieerGBAChar(0x64, 0x0064, null, offset); // LATIN SMALL LETTER D
        offset = GBACharacterSet.definieerGBAChar(0x44, 0x0044, null, offset); // LATIN CAPITAL LETTER D
        offset = GBACharacterSet.definieerGBAChar(0x65, 0x0065, null, offset); // LATIN SMALL LETTER E
        offset = GBACharacterSet.definieerGBAChar(0x45, 0x0045, null, offset); // LATIN CAPITAL LETTER E
        offset = GBACharacterSet.definieerGBAChar(0x66, 0x0066, null, offset); // LATIN SMALL LETTER F
        offset = GBACharacterSet.definieerGBAChar(0x46, 0x0046, null, offset); // LATIN CAPITAL LETTER F
        offset = GBACharacterSet.definieerGBAChar(0x67, 0x0067, null, offset); // LATIN SMALL LETTER G
        offset = GBACharacterSet.definieerGBAChar(0x47, 0x0047, null, offset); // LATIN CAPITAL LETTER G
        offset = GBACharacterSet.definieerGBAChar(0x68, 0x0068, null, offset); // LATIN SMALL LETTER H
        offset = GBACharacterSet.definieerGBAChar(0x48, 0x0048, null, offset); // LATIN CAPITAL LETTER H
        offset = GBACharacterSet.definieerGBAChar(0x69, 0x0069, null, offset); // LATIN SMALL LETTER I
        offset = GBACharacterSet.definieerGBAChar(0x49, 0x0049, null, offset); // LATIN CAPITAL LETTER I
        offset = GBACharacterSet.definieerGBAChar(0x6A, 0x006A, null, offset); // LATIN SMALL LETTER J
        offset = GBACharacterSet.definieerGBAChar(0x4A, 0x004A, null, offset); // LATIN CAPITAL LETTER J
        offset = GBACharacterSet.definieerGBAChar(0x6B, 0x006B, null, offset); // LATIN SMALL LETTER K
        offset = GBACharacterSet.definieerGBAChar(0x4B, 0x004B, null, offset); // LATIN CAPITAL LETTER K
        offset = GBACharacterSet.definieerGBAChar(0x6C, 0x006C, null, offset); // LATIN SMALL LETTER L
        offset = GBACharacterSet.definieerGBAChar(0x4C, 0x004C, null, offset); // LATIN CAPITAL LETTER L
        offset = GBACharacterSet.definieerGBAChar(0x6D, 0x006D, null, offset); // LATIN SMALL LETTER M
        offset = GBACharacterSet.definieerGBAChar(0x4D, 0x004D, null, offset); // LATIN CAPITAL LETTER M
        offset = GBACharacterSet.definieerGBAChar(0x6E, 0x006E, null, offset); // LATIN SMALL LETTER N
        offset = GBACharacterSet.definieerGBAChar(0x4E, 0x004E, null, offset); // LATIN CAPITAL LETTER N
        offset = GBACharacterSet.definieerGBAChar(0x6F, 0x006F, null, offset); // LATIN SMALL LETTER O
        offset = GBACharacterSet.definieerGBAChar(0x4F, 0x004F, null, offset); // LATIN CAPITAL LETTER O
        offset = GBACharacterSet.definieerGBAChar(0x70, 0x0070, null, offset); // LATIN SMALL LETTER P
        offset = GBACharacterSet.definieerGBAChar(0x50, 0x0050, null, offset); // LATIN CAPITAL LETTER P
        offset = GBACharacterSet.definieerGBAChar(0x71, 0x0071, null, offset); // LATIN SMALL LETTER Q
        offset = GBACharacterSet.definieerGBAChar(0x51, 0x0051, null, offset); // LATIN CAPITAL LETTER Q
        offset = GBACharacterSet.definieerGBAChar(0x72, 0x0072, null, offset); // LATIN SMALL LETTER R
        offset = GBACharacterSet.definieerGBAChar(0x52, 0x0052, null, offset); // LATIN CAPITAL LETTER R
        offset = GBACharacterSet.definieerGBAChar(0x73, 0x0073, null, offset); // LATIN SMALL LETTER S
        offset = GBACharacterSet.definieerGBAChar(0x53, 0x0053, null, offset); // LATIN CAPITAL LETTER S
        offset = GBACharacterSet.definieerGBAChar(0x74, 0x0074, null, offset); // LATIN SMALL LETTER T
        offset = GBACharacterSet.definieerGBAChar(0x54, 0x0054, null, offset); // LATIN CAPITAL LETTER T
        offset = GBACharacterSet.definieerGBAChar(0x75, 0x0075, null, offset); // LATIN SMALL LETTER U
        offset = GBACharacterSet.definieerGBAChar(0x55, 0x0055, null, offset); // LATIN CAPITAL LETTER U
        offset = GBACharacterSet.definieerGBAChar(0x76, 0x0076, null, offset); // LATIN SMALL LETTER V
        offset = GBACharacterSet.definieerGBAChar(0x56, 0x0056, null, offset); // LATIN CAPITAL LETTER V
        offset = GBACharacterSet.definieerGBAChar(0x77, 0x0077, null, offset); // LATIN SMALL LETTER W
        offset = GBACharacterSet.definieerGBAChar(0x57, 0x0057, null, offset); // LATIN CAPITAL LETTER W
        offset = GBACharacterSet.definieerGBAChar(0x78, 0x0078, null, offset); // LATIN SMALL LETTER X
        offset = GBACharacterSet.definieerGBAChar(0x58, 0x0058, null, offset); // LATIN CAPITAL LETTER X
        offset = GBACharacterSet.definieerGBAChar(0x79, 0x0079, null, offset); // LATIN SMALL LETTER Y
        offset = GBACharacterSet.definieerGBAChar(0x59, 0x0059, null, offset); // LATIN CAPITAL LETTER Y
        offset = GBACharacterSet.definieerGBAChar(0x7A, 0x007A, null, offset); // LATIN SMALL LETTER Z
        offset = GBACharacterSet.definieerGBAChar(0x5A, 0x005A, null, offset); // LATIN CAPITAL LETTER Z
        offset = GBACharacterSet.definieerGBAChar(0xC261, 0x00E1, null, offset); // LATIN SMALL LETTER A WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC241, 0x00C1, null, offset); // LATIN CAPITAL LETTER A WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC263, 0x0107, null, offset); // LATIN SMALL LETTER C WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC243, 0x0106, null, offset); // LATIN CAPITAL LETTER C WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC265, 0x00E9, null, offset); // LATIN SMALL LETTER E WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC245, 0x00C9, null, offset); // LATIN CAPITAL LETTER E WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC269, 0x00ED, null, offset); // LATIN SMALL LETTER I WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC249, 0x00CD, null, offset); // LATIN CAPITAL LETTER I WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC26C, 0x013A, null, offset); // LATIN SMALL LETTER L WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC24C, 0x0139, null, offset); // LATIN CAPITAL LETTER L WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC26E, 0x0144, null, offset); // LATIN SMALL LETTER N WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC24E, 0x0143, null, offset); // LATIN CAPITAL LETTER N WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC26F, 0x00F3, null, offset); // LATIN SMALL LETTER O WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC24F, 0x00D3, null, offset); // LATIN CAPITAL LETTER O WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC272, 0x0155, null, offset); // LATIN SMALL LETTER R WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC252, 0x0154, null, offset); // LATIN CAPITAL LETTER R WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC273, 0x015B, null, offset); // LATIN SMALL LETTER S WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC253, 0x015A, null, offset); // LATIN CAPITAL LETTER S WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC275, 0x00FA, null, offset); // LATIN SMALL LETTER U WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC255, 0x00DA, null, offset); // LATIN CAPITAL LETTER U WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC279, 0x00FD, null, offset); // LATIN SMALL LETTER Y WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC259, 0x00DD, null, offset); // LATIN CAPITAL LETTER Y WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC27A, 0x017A, null, offset); // LATIN SMALL LETTER Z WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC25A, 0x0179, null, offset); // LATIN CAPITAL LETTER Z WITH ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xC161, 0x00E0, null, offset); // LATIN SMALL LETTER A WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC141, 0x00C0, null, offset); // LATIN CAPITAL LETTER A WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC165, 0x00E8, null, offset); // LATIN SMALL LETTER E WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC145, 0x00C8, null, offset); // LATIN CAPITAL LETTER E WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC169, 0x00EC, null, offset); // LATIN SMALL LETTER I WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC149, 0x00CC, null, offset); // LATIN CAPITAL LETTER I WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC16F, 0x00F2, null, offset); // LATIN SMALL LETTER O WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC14F, 0x00D2, null, offset); // LATIN CAPITAL LETTER O WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC175, 0x00F9, null, offset); // LATIN SMALL LETTER U WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC155, 0x00D9, null, offset); // LATIN CAPITAL LETTER U WITH GRAVE
        offset = GBACharacterSet.definieerGBAChar(0xC361, 0x00E2, null, offset); // LATIN SMALL LETTER A WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC341, 0x00C2, null, offset); // LATIN CAPITAL LETTER A WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC363, 0x0109, null, offset); // LATIN SMALL LETTER C WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC343, 0x0108, null, offset); // LATIN CAPITAL LETTER C WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC365, 0x00EA, null, offset); // LATIN SMALL LETTER E WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC345, 0x00CA, null, offset); // LATIN CAPITAL LETTER E WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC367, 0x011D, null, offset); // LATIN SMALL LETTER G WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC347, 0x011C, null, offset); // LATIN CAPITAL LETTER G WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC368, 0x0125, null, offset); // LATIN SMALL LETTER H WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC348, 0x0124, null, offset); // LATIN CAPITAL LETTER H WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC369, 0x00EE, null, offset); // LATIN SMALL LETTER I WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC349, 0x00CE, null, offset); // LATIN CAPITAL LETTER I WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC36A, 0x0135, null, offset); // LATIN SMALL LETTER J WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC34A, 0x0134, null, offset); // LATIN CAPITAL LETTER J WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC36F, 0x00F4, null, offset); // LATIN SMALL LETTER O WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC34F, 0x00D4, null, offset); // LATIN CAPITAL LETTER O WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC373, 0x015D, null, offset); // LATIN SMALL LETTER S WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC353, 0x015C, null, offset); // LATIN CAPITAL LETTER S WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC375, 0x00FB, null, offset); // LATIN SMALL LETTER U WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC355, 0x00DB, null, offset); // LATIN CAPITAL LETTER U WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC377, 0x0175, null, offset); // LATIN SMALL LETTER W WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC357, 0x0174, null, offset); // LATIN CAPITAL LETTER W WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC379, 0x0177, null, offset); // LATIN SMALL LETTER Y WITH CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC359, 0x0176, null, offset); // LATIN CAPITAL LETTER Y WITH
        // CIRCUMFLEX
        offset = GBACharacterSet.definieerGBAChar(0xC861, 0x00E4, null, offset); // LATIN SMALL LETTER A WITH DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC841, 0x00C4, null, offset); // LATIN CAPITAL LETTER A WITH
        // DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC865, 0x00EB, null, offset); // LATIN SMALL LETTER E WITH DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC845, 0x00CB, null, offset); // LATIN CAPITAL LETTER E WITH
        // DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC869, 0x00EF, null, offset); // LATIN SMALL LETTER I WITH DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC849, 0x00CF, null, offset); // LATIN CAPITAL LETTER I WITH
        // DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC86F, 0x00F6, null, offset); // LATIN SMALL LETTER O WITH DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC84F, 0x00D6, null, offset); // LATIN CAPITAL LETTER O WITH
        // DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC875, 0x00FC, null, offset); // LATIN SMALL LETTER U WITH DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC855, 0x00DC, null, offset); // LATIN CAPITAL LETTER U WITH
        // DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC879, 0x00FF, null, offset); // LATIN SMALL LETTER Y WITH DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC859, 0x0178, null, offset); // LATIN CAPITAL LETTER Y WITH
        // DIAERESIS
        offset = GBACharacterSet.definieerGBAChar(0xC461, 0x00E3, null, offset); // LATIN SMALL LETTER A WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xC441, 0x00C3, null, offset); // LATIN CAPITAL LETTER A WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xC46E, 0x00F1, null, offset); // LATIN SMALL LETTER N WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xC44E, 0x00D1, null, offset); // LATIN CAPITAL LETTER N WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xC46F, 0x00F5, null, offset); // LATIN SMALL LETTER O WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xC44F, 0x00D5, null, offset); // LATIN CAPITAL LETTER O WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xCF63, 0x010D, null, offset); // LATIN SMALL LETTER C WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF43, 0x010C, null, offset); // LATIN CAPITAL LETTER C WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF64, 0x010F, null, offset); // LATIN SMALL LETTER D WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF44, 0x010E, null, offset); // LATIN CAPITAL LETTER D WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF65, 0x011B, null, offset); // LATIN SMALL LETTER E WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF45, 0x011A, null, offset); // LATIN CAPITAL LETTER E WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF6C, 0x013E, null, offset); // LATIN SMALL LETTER L WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF4C, 0x013D, null, offset); // LATIN CAPITAL LETTER L WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF6E, 0x0148, null, offset); // LATIN SMALL LETTER N WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF4E, 0x0147, null, offset); // LATIN CAPITAL LETTER N WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF72, 0x0159, null, offset); // LATIN SMALL LETTER R WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF52, 0x0158, null, offset); // LATIN CAPITAL LETTER R WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF73, 0x0161, null, offset); // LATIN SMALL LETTER S WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF53, 0x0160, null, offset); // LATIN CAPITAL LETTER S WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF74, 0x0165, null, offset); // LATIN SMALL LETTER T WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF54, 0x0164, null, offset); // LATIN CAPITAL LETTER T WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF7A, 0x017E, null, offset); // LATIN SMALL LETTER Z WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xCF5A, 0x017D, null, offset); // LATIN CAPITAL LETTER Z WITH CARON
        offset = GBACharacterSet.definieerGBAChar(0xC661, 0x0103, null, offset); // LATIN SMALL LETTER A WITH BREVE
        offset = GBACharacterSet.definieerGBAChar(0xC641, 0x0102, null, offset); // LATIN CAPITAL LETTER A WITH BREVE
        offset = GBACharacterSet.definieerGBAChar(0xC667, 0x011F, null, offset); // LATIN SMALL LETTER G WITH BREVE
        offset = GBACharacterSet.definieerGBAChar(0xC647, 0x011E, null, offset); // LATIN CAPITAL LETTER G WITH BREVE
        offset = GBACharacterSet.definieerGBAChar(0xC675, 0x016D, null, offset); // LATIN SMALL LETTER U WITH BREVE
        offset = GBACharacterSet.definieerGBAChar(0xC655, 0x016C, null, offset); // LATIN CAPITAL LETTER U WITH BREVE
        offset = GBACharacterSet.definieerGBAChar(0xCD6F, 0x0151, null, offset); // LATIN SMALL LETTER O WITH DOUBLE
        // ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xCD4F, 0x0150, null, offset); // LATIN CAPITAL LETTER O WITH DOUBLE
        // ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xCD75, 0x0171, null, offset); // LATIN SMALL LETTER U WITH DOUBLE
        // ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xCD55, 0x0170, null, offset); // LATIN CAPITAL LETTER U WITH DOUBLE
        // ACUTE
        offset = GBACharacterSet.definieerGBAChar(0xCA61, 0x00E5, null, offset); // LATIN SMALL LETTER A WITH RING ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xCA41, 0x00C5, null, offset); // LATIN CAPITAL LETTER A WITH RING
        // ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xCA75, 0x016F, null, offset); // LATIN SMALL LETTER U WITH RING ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xCA55, 0x016E, null, offset); // LATIN CAPITAL LETTER U WITH RING
        // ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC763, 0x010B, null, offset); // LATIN SMALL LETTER C WITH DOT ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC743, 0x010A, null, offset); // LATIN CAPITAL LETTER C WITH DOT
        // ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC765, 0x0117, null, offset); // LATIN SMALL LETTER E WITH DOT ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC745, 0x0116, null, offset); // LATIN CAPITAL LETTER E WITH DOT
        // ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC767, 0x0121, null, offset); // LATIN SMALL LETTER G WITH DOT ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC747, 0x0120, null, offset); // LATIN CAPITAL LETTER G WITH DOT
        // ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC749, 0x0130, null, offset); // LATIN CAPITAL LETTER I WITH DOT
        // ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xF5, 0x0131, "i", offset); // LATIN SMALL LETTER DOTLESS I
        offset = GBACharacterSet.definieerGBAChar(0xC77A, 0x017C, null, offset); // LATIN SMALL LETTER Z WITH DOT ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC75A, 0x017B, null, offset); // LATIN CAPITAL LETTER Z WITH DOT
        // ABOVE
        offset = GBACharacterSet.definieerGBAChar(0xC561, 0x0101, null, offset); // LATIN SMALL LETTER A WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC541, 0x0100, null, offset); // LATIN CAPITAL LETTER A WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC565, 0x0113, null, offset); // LATIN SMALL LETTER E WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC545, 0x0112, null, offset); // LATIN CAPITAL LETTER E WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC569, 0x012B, null, offset); // LATIN SMALL LETTER I WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC549, 0x012A, null, offset); // LATIN CAPITAL LETTER I WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC56F, 0x014D, null, offset); // LATIN SMALL LETTER O WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC54F, 0x014C, null, offset); // LATIN CAPITAL LETTER O WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC575, 0x016B, null, offset); // LATIN SMALL LETTER U WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xC555, 0x016A, null, offset); // LATIN CAPITAL LETTER U WITH MACRON
        offset = GBACharacterSet.definieerGBAChar(0xF2, 0x0111, "d", offset); // LATIN SMALL LETTER D WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xE2, 0x0110, "D", offset); // LATIN CAPITAL LETTER D WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xF4, 0x0127, "h", offset); // LATIN SMALL LETTER H WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xE4, 0x0126, "H", offset); // LATIN CAPITAL LETTER H WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xF8, 0x0142, "l", offset); // LATIN SMALL LETTER L WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xE8, 0x0141, "L", offset); // LATIN CAPITAL LETTER L WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xF9, 0x00F8, "o", offset); // LATIN SMALL LETTER O WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xE9, 0x00D8, "O", offset); // LATIN CAPITAL LETTER O WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xFD, 0x0167, "t", offset); // LATIN SMALL LETTER T WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xED, 0x0166, "T", offset); // LATIN CAPITAL LETTER T WITH STROKE
        offset = GBACharacterSet.definieerGBAChar(0xCB63, 0x00E7, null, offset); // LATIN SMALL LETTER C WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB43, 0x00C7, null, offset); // LATIN CAPITAL LETTER C WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xC267, 0x0123, null, offset); // LATIN SMALL LETTER G WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB47, 0x0122, null, offset); // LATIN CAPITAL LETTER G WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB6B, 0x0137, null, offset); // LATIN SMALL LETTER K WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB4B, 0x0136, null, offset); // LATIN CAPITAL LETTER K WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB6C, 0x013C, null, offset); // LATIN SMALL LETTER L WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB4C, 0x013B, null, offset); // LATIN CAPITAL LETTER L WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB6E, 0x0146, null, offset); // LATIN SMALL LETTER N WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB4E, 0x0145, null, offset); // LATIN CAPITAL LETTER N WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB72, 0x0157, null, offset); // LATIN SMALL LETTER R WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB52, 0x0156, null, offset); // LATIN CAPITAL LETTER R WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB73, 0x015F, null, offset); // LATIN SMALL LETTER S WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB53, 0x015E, null, offset); // LATIN CAPITAL LETTER S WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB74, 0x0163, null, offset); // LATIN SMALL LETTER T WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCB54, 0x0162, null, offset); // LATIN CAPITAL LETTER T WITH CEDILLA
        offset = GBACharacterSet.definieerGBAChar(0xCE61, 0x0105, null, offset); // LATIN SMALL LETTER A WITH OGONEK
        offset = GBACharacterSet.definieerGBAChar(0xCE41, 0x0104, null, offset); // LATIN CAPITAL LETTER A WITH OGONEK
        offset = GBACharacterSet.definieerGBAChar(0xCE65, 0x0119, null, offset); // LATIN SMALL LETTER E WITH OGONEK
        offset = GBACharacterSet.definieerGBAChar(0xCE45, 0x0118, null, offset); // LATIN CAPITAL LETTER E WITH OGONEK
        offset = GBACharacterSet.definieerGBAChar(0xCE69, 0x012F, null, offset); // LATIN SMALL LETTER I WITH OGONEK
        offset = GBACharacterSet.definieerGBAChar(0xCE49, 0x012E, null, offset); // LATIN CAPITAL LETTER I WITH OGONEK
        offset = GBACharacterSet.definieerGBAChar(0xCE75, 0x0173, null, offset); // LATIN SMALL LETTER U WITH OGONEK
        offset = GBACharacterSet.definieerGBAChar(0xCE55, 0x0172, null, offset); // LATIN CAPITAL LETTER U WITH OGONEK
        offset = GBACharacterSet.definieerGBAChar(0xF1, 0x00E6, "ae", offset); // LATIN SMALL LETTER AE
        offset = GBACharacterSet.definieerGBAChar(0xE1, 0x00C6, "AE", offset); // LATIN CAPITAL LETTER AE
        offset = GBACharacterSet.definieerGBAChar(0xFA, 0x0153, "oe", offset); // LATIN SMALL LIGATURE O E
        offset = GBACharacterSet.definieerGBAChar(0xEA, 0x0152, "OE", offset); // LATIN CAPITAL LIGATURE O E
        offset = GBACharacterSet.definieerGBAChar(0xFB, 0x00DF, "ss", offset); // LATIN SMALL LETTER SHARP S (German)
        offset = GBACharacterSet.definieerGBAChar(0xFE, 0x014B, "ng", offset); // LATIN SMALL LETTER ENG (Sami)
        offset = GBACharacterSet.definieerGBAChar(0xEE, 0x014A, "NG", offset); // LATIN CAPITAL LETTER ENG (Sami)
        offset = GBACharacterSet.definieerGBAChar(0xF3, 0x00F0, "d", offset); // LATIN SMALL LETTER ETH (Icelandic)
        offset = GBACharacterSet.definieerGBAChar(0xFC, 0x00FE, "th", offset); // LATIN SMALL LETTER THORN (Icelandic)
        offset = GBACharacterSet.definieerGBAChar(0xEC, 0x00DE, "TH", offset); // LATIN CAPITAL LETTER THORN (Icelandic)
        offset = GBACharacterSet.definieerGBAChar(0xC469, 0x0129, null, offset); // LATIN SMALL LETTER I WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xC449, 0x0128, null, offset); // LATIN CAPITAL LETTER I WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xC475, 0x0169, null, offset); // LATIN SMALL LETTER U WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xC455, 0x0168, null, offset); // LATIN CAPITAL LETTER U WITH TILDE
        offset = GBACharacterSet.definieerGBAChar(0xF0, 0x0138, "q", offset); // LATIN SMALL LETTER KRA (Greenlandic)
        offset = GBACharacterSet.definieerGBAChar(0xF7, 0x0140, "l.", offset); // LATIN SMALL LETTER L WITH MIDDLE DOT
        offset = GBACharacterSet.definieerGBAChar(0xE7, 0x013F, "L.", offset); // LATIN CAPITAL LETTER L WITH MIDDLE DOT
        offset = GBACharacterSet.definieerGBAChar(0xEF, 0x0149, "'n", offset); // LATIN SMALL LETTER N PRECEDED BY
        // APOSTROPHE
        offset = GBACharacterSet.definieerGBAChar(0x31, 0x0031, null, offset); // DIGIT ONE
        offset = GBACharacterSet.definieerGBAChar(0x32, 0x0032, null, offset); // DIGIT TWO
        offset = GBACharacterSet.definieerGBAChar(0x33, 0x0033, null, offset); // DIGIT THREE
        offset = GBACharacterSet.definieerGBAChar(0x34, 0x0034, null, offset); // DIGIT FOUR
        offset = GBACharacterSet.definieerGBAChar(0x35, 0x0035, null, offset); // DIGIT FIVE
        offset = GBACharacterSet.definieerGBAChar(0x36, 0x0036, null, offset); // DIGIT SIX
        offset = GBACharacterSet.definieerGBAChar(0x37, 0x0037, null, offset); // DIGIT SEVEN
        offset = GBACharacterSet.definieerGBAChar(0x38, 0x0038, null, offset); // DIGIT EIGHT
        offset = GBACharacterSet.definieerGBAChar(0x39, 0x0039, null, offset); // DIGIT NINE
        offset = GBACharacterSet.definieerGBAChar(0x30, 0x0030, null, offset); // DIGIT ZERO
        offset = GBACharacterSet.definieerGBAChar(0xB2, 0x00B2, "2", offset); // SUPERSCRIPT TWO
        offset = GBACharacterSet.definieerGBAChar(0xB3, 0x00B3, "3", offset); // SUPERSCRIPT THREE
        offset = GBACharacterSet.definieerGBAChar(0xBD, 0x00BD, "1/2", offset); // VULGAR FRACTION ONE HALF
        offset = GBACharacterSet.definieerGBAChar(0xBC, 0x00BC, "1/4", offset); // VULGAR FRACTION ONE QUARTER
        offset = GBACharacterSet.definieerGBAChar(0xBE, 0x00BE, "3/4", offset); // VULGAR FRACTION THREE QUARTERS
        offset = GBACharacterSet.definieerGBAChar(0x2B, 0x002B, null, offset); // PLUS SIGN
        offset = GBACharacterSet.definieerGBAChar(0x3C, 0x003C, null, offset); // LESS-THAN SIGN
        offset = GBACharacterSet.definieerGBAChar(0x3D, 0x003D, null, offset); // EQUALS SIGN
        offset = GBACharacterSet.definieerGBAChar(0x3E, 0x003E, null, offset); // GREATER-THAN SIGN
        offset = GBACharacterSet.definieerGBAChar(0xB1, 0x00B1, "+/-", offset); // PLUS-MINUS SIGN
        offset = GBACharacterSet.definieerGBAChar(0xB8, 0x00F7, ":", offset); // DIVISION SIGN
        offset = GBACharacterSet.definieerGBAChar(0xB4, 0x00D7, "x", offset); // MULTIPLICATION SIGN
        offset = GBACharacterSet.definieerGBAChar(0xA8, 0x00A4, "*", offset); // CURRENCY SIGN
        offset = GBACharacterSet.definieerGBAChar(0xA3, 0x00A3, "L", offset); // POUND SIGN
        offset = GBACharacterSet.definieerGBAChar(0xA4, 0x0024, "$", offset); // DOLLAR SIGN (GBA and ASCII differ!)
        offset = GBACharacterSet.definieerGBAChar(0xA2, 0x00A2, "c", offset); // CENT SIGN
        offset = GBACharacterSet.definieerGBAChar(0xA5, 0x00A5, "Y", offset); // YEN SIGN
        offset = GBACharacterSet.definieerGBAChar(0xA6, 0x0023, "#", offset); // NUMBER SIGN (GBA and ASCII differ!)
        offset = GBACharacterSet.definieerGBAChar(0x25, 0x0025, null, offset); // PERCENT SIGN
        offset = GBACharacterSet.definieerGBAChar(0x26, 0x0026, null, offset); // AMPERSAND
        offset = GBACharacterSet.definieerGBAChar(0x2A, 0x002A, null, offset); // ASTERISK
        offset = GBACharacterSet.definieerGBAChar(0x40, 0x0040, "@", offset); // COMMERCIAL AT
        offset = GBACharacterSet.definieerGBAChar(0x5B, 0x005B, "(", offset); // LEFT SQUARE BRACKET
        offset = GBACharacterSet.definieerGBAChar(0x5D, 0x005D, ")", offset); // RIGHT SQUARE BRACKET
        offset = GBACharacterSet.definieerGBAChar(0x7C, 0x007C, "|", offset); // VERTICAL LINE
        offset = GBACharacterSet.definieerGBAChar(0xB5, 0x00B5, "\u00B5", offset); // MICRO SIGN
        offset = GBACharacterSet.definieerGBAChar(0xE0, 0x2126, "Ohm", offset); // OHM SIGN
        offset = GBACharacterSet.definieerGBAChar(0xB0, 0x00B0, "o", offset); // DEGREE SIGN
        offset = GBACharacterSet.definieerGBAChar(0xEB, 0x00BA, "o", offset); // MASCULINE ORDINAL INDICATOR
        offset = GBACharacterSet.definieerGBAChar(0xE3, 0x00AA, "a", offset); // FEMININE ORDINAL INDICATOR
        offset = GBACharacterSet.definieerGBAChar(0xA7, 0x00A7, "\u00A7", offset); // SECTION SIGN
        offset = GBACharacterSet.definieerGBAChar(0xB6, 0x00B6, "\u00B6", offset); // PILCROW SIGN
        offset = GBACharacterSet.definieerGBAChar(0xB7, 0x00B7, "", offset); // MIDDLE DOT
        offset = GBACharacterSet.definieerGBAChar(0x20, 0x0020, null, offset); // SPACE
        offset = GBACharacterSet.definieerGBAChar(0x21, 0x0021, null, offset); // EXCLAMATION MARK
        offset = GBACharacterSet.definieerGBAChar(0xA1, 0x00A1, "!", offset); // INVERTED EXCLAMATION MARK
        offset = GBACharacterSet.definieerGBAChar(0x22, 0x0022, null, offset); // QUOTATION MARK
        offset = GBACharacterSet.definieerGBAChar(0x27, 0x0027, null, offset); // APOSTROPHE
        offset = GBACharacterSet.definieerGBAChar(0x28, 0x0028, null, offset); // LEFT PARENTHESIS
        offset = GBACharacterSet.definieerGBAChar(0x29, 0x0029, null, offset); // RIGHT PARENTHESIS
        offset = GBACharacterSet.definieerGBAChar(0x2C, 0x002C, null, offset); // COMMA
        offset = GBACharacterSet.definieerGBAChar(0x5F, 0x005F, "_", offset); // LOW LINE
        offset = GBACharacterSet.definieerGBAChar(0x2D, 0x002D, null, offset); // HYPHEN-MINUS
        offset = GBACharacterSet.definieerGBAChar(0x2E, 0x002E, null, offset); // FULL STOP
        offset = GBACharacterSet.definieerGBAChar(0x2F, 0x002F, null, offset); // SOLIDUS
        offset = GBACharacterSet.definieerGBAChar(0x3A, 0x003A, null, offset); // COLON
        offset = GBACharacterSet.definieerGBAChar(0x3B, 0x003B, null, offset); // SEMICOLON
        offset = GBACharacterSet.definieerGBAChar(0x3F, 0x003F, null, offset); // QUESTION MARK
        offset = GBACharacterSet.definieerGBAChar(0xBF, 0x00BF, "?", offset); // INVERTED QUESTION MARK
        offset = GBACharacterSet.definieerGBAChar(0xAB, 0x00AB, "<<", offset); // LEFT-POINTING DOUBLE ANGLE QUOTATION
        // MARK
        offset = GBACharacterSet.definieerGBAChar(0xBB, 0x00BB, ">>", offset); // RIGHT-POINTING DOUBLE ANGLE QUOTATION
        // MARK

        return offset;
    }
}
