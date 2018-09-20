/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
package nl.moderniseringgba.isc.voisc.mailbox.impl;

import nl.ictu.spg.common.util.conversion.GBACharacterSet;
import junit.framework.TestCase;

/**
 * Class to test @link nl.ictu.spg.common.util.conversion.GBACharacterSet
 * 
 * 
 * 
 */
public class TestGBACharacterSet extends TestCase {
    public final static int GBA_CHARACTER_SET_SIZE = 293;
    public final static String VRIJ_BERICHT_TELETEX_TEKENS =
            "            +-------------------------------------------------+\n"
                    + "            | *****   Vrij bericht Teletex-tekens       ***** |\n"
                    + "            | *****   versie 3.0    februari 2001       ***** |\n"
                    + "            +-------------------------------------------------+\n" + "\n"
                    + "      Dit  bericht  is  uitsluitend  bedoeld  om   de   ontvanger   de\n"
                    + "      gelegenheid  te geven na te gaan of alle vereiste TELETEX tekens\n"
                    + "      op de juiste wijze  worden  gerepresenteerd  op  terminal  en/of\n"
                    + "      printer.   Ondanks  het  feit  dat  u dit bericht tot zover hebt\n"
                    + "      kunnen  lezen,  hetgeen  uiteraard  op  een  zekere   mate   van\n"
                    + "      betrouwbaarheid  wijst,  kan  een wat systematischer aanpak geen\n"
                    + "      kwaad.  We beginnen derhalve met de gebruikelijke  alfanumerieke\n"
                    + "      tekenset.\n" + "\n"
                    + "         ---- Het alfabet in hoofdletters in beide richtingen ----\n" + "\n"
                    + "                         ABCDEFGHIJKLMNOPQRSTUVWXYZ\n"
                    + "                         ZYXWVUTSRQPONMLKJIHGFEDCBA\n" + "\n"
                    + "         --- Het alfabet in kleine letters in beide richtingen ---\n" + "\n"
                    + "                         abcdefghijklmnopqrstuvwxyz\n"
                    + "                         zyxwvutsrqponmlkjihgfedcba\n" + "\n"
                    + "         ------------ Alle cijfers in beide richtingen -----------\n" + "\n"
                    + "                                 0123456789\n"
                    + "                                 9876543210\n" + "\n"
                    + "      Ingeval u zowel deze tekensets als alle voorgaande tekst  netjes\n"
                    + "      gecentreerd  hebt  kunnen  lezen,  worden  naast de spatie  <sp>\n"
                    + "      tevens de \"carriage return\"  <cr> en de  \"line feed\"  <lf>  goed\n"
                    + "      afgehandeld.  Twee van de drie besturingskarakters uit par. II.5\n"
                    + "      van het LO, blz. 321, zijn daarmee reeds aan  de  orde  geweest.\n"
                    + "      De  derde, de \"form feed\"  <ff>, zullen we impliciet testen door\n"
                    + "      de nu volgende tabellen telkens  op  een  nieuwe  pagina  af  te\n" + "      beelden.\n"
                    + "\n\u000C" + "      In onderstaande  tabel,  ontleend  aan  par. II.3  van  het  LO,\n"
                    + "      blz. 371-377, zijn alle nog niet eerder opgesomde TELETEX tekens\n"
                    + "      uit  de  linkerkant  van  de  tabel  in  par. II.2  van  het  LO\n"
                    + "      opgenomen.\n" + "\n"
                    + "          +-----------------------------------------------------+\n"
                    + "          |      Naam          Graph |      Naam          Graph |\n"
                    + "          |--------------------------+--------------------------|\n"
                    + "          |  exclamation mark ... !  |  solidus ............ /  |\n"
                    + "          |  quotation mark ..... \"  |  colon .............. :  |\n"
                    + "          |  percent sign ....... %  |  semicolon .......... ;  |\n"
                    + "          |  ampersand .......... &  |  less-than sign ..... <  |\n"
                    + "          |  apostrophe ......... '  |  equals sign ........ =  |\n"
                    + "          |  left parenthesis ... (  |  greater than sign .. >  |\n"
                    + "          |  right parenthesis .. )  |  question mark ...... ?  |\n"
                    + "          |  asterisk ........... *  |  commercial at ...... @  |\n"
                    + "          |  plus sign .......... +  |  left  square bracket [  |\n"
                    + "          |  comma .............. ,  |  right square bracket ]  |\n"
                    + "          |  hyphen,minus sign .. -  |  low line ........... _  |\n"
                    + "          |  full stop, period .. .  |  vertical bar ....... |  |\n"
                    + "          +-----------------------------------------------------+\n" + "\n\u000C"
                    + "      In de nu volgende tabel, ontleend  aan  par. II.3  van  het  LO,\n"
                    + "      blz. 371-373, vindt u alle TELETEX tekens uit de rechterkant van\n"
                    + "      de tabel in par. II.2 van het  LO,  uitgezonderd  de  diakrieten\n"
                    + "      (kolom C).\n" + "\n"
                    + "        +----------------------------------------------------------+\n"
                    + "        |        Naam          Graph |        Naam           Graph |\n"
                    + "        |----------------------------+-----------------------------|\n"
                    + "        |  inv.exclamation mark . \u00A1  |  ohm sign .............. \u00E0  |\n"
                    + "        |  cent sign ............ \u00A2  |  capital AE diphtong ... \u00E1  |\n"
                    + "        |  pound sign ........... \u00A3  |  capital D with stroke . \u00E2  |\n"
                    + "        |  dollar sign .......... \u00A4  |  ord.ind.,feminine ..... \u00E3  |\n"
                    + "        |  yen sign ............. \u00A5  |  capital H with stroke . \u00E4  |\n"
                    + "        |  number sign .......... \u00A6  |                             |\n"
                    + "        |  section sign ......... \u00A7  |  capital L with mid.dot  \u00E7  |\n"
                    + "        |  currency symbol ...... \u00A8  |  capital L with stroke . \u00E8  |\n"
                    + "        |  angle quot.mark left . \u00AB  |  capital O with slash .. \u00E9  |\n"
                    + "        |  degree sign .......... \u00B0  |  capital OE ligature ... \u00EA  |\n"
                    + "        |  plus/minus sign ...... \u00B1  |  ord.ind.,masculine .... \u00EB  |\n"
                    + "        |  superscript 2 ........ \u00B2  |  capital thorn,Icelandic \u00EC  |\n"
                    + "        |  superscript 3 ........ \u00B3  |  capital T with stroke . \u00ED  |\n"
                    + "        |  multiply sign ........ \u00B4  |  capital eng, Lapp ..... \u00EE  |\n"
                    + "        |  micro sign ........... \u00B5  |  small n with apostrophe \u00EF  |\n"
                    + "        |  paragraph sign ....... \u00B6  |  small k, Greenlandic .. \u00F0  |\n"
                    + "        |  middle dot ........... \u00B7  |  small ae diphtong ..... \u00F1  |\n"
                    + "        |  divide sign .......... \u00B8  |  small d with stroke ... \u00F2  |\n"
                    + "        |  angle quot.mark right. \u00BB  |  small eth, Icelandic .. \u00F3  |\n"
                    + "        |  fraction one quarter . \u00BC  |  small h with stroke ... \u00F4  |\n"
                    + "        |  fraction one half .... \u00BD  |  small i without dot ... \u00F5  |\n"
                    + "        |  fract.three quarters . \u00BE  |                             |\n"
                    + "        |  inverted question mark \u00BF  |  small l with middle dot \u00F7  |\n"
                    + "        |                            |  small l with stroke ... \u00F8  |\n"
                    + "        |                            |  small o with slash .... \u00F9  |\n"
                    + "        |                            |  small oe ligature ..... \u00FA  |\n"
                    + "        |                            |  small sharp s, German . \u00FB  |\n"
                    + "        |                            |  small thorn,Icelandic . \u00FC  |\n"
                    + "        |                            |  small t with stroke ... \u00FD  |\n"
                    + "        |                            |  small eng, Lapp ....... \u00FE  |\n"
                    + "        +----------------------------------------------------------+\n" + "\n\u000C"
                    + "      In deze voorlaatste tabel, ontleend aan par. II.4  van  het  LO,\n"
                    + "      blz. 374-377,  vindt  u  een opsomming van de verplichte letter-\n"
                    + "      diakriet combinaties van A t/m N die moeten worden ondersteund.\n" + "\n"
                    + "          +-------------------------------------------------+\n"
                    + "          |     Naam         H/K   |     Naam         H/K   |\n"
                    + "          |------------------------+------------------------|\n"
                    + "          |   A acute ...... \u00C2A/\u00C2a |   G acute ......  /\u00C2g  |\n"
                    + "          |   A grave ...... \u00C1A/\u00C1a |   G circumflex . \u00C3G/\u00C3g |\n"
                    + "          |   A circumflex . \u00C3A/\u00C3a |   G breve ...... \u00C6G/\u00C6g |\n"
                    + "          |   A diaeresis .. \u00C8A/\u00C8a |   G dot ........ \u00C7G/\u00C7g |\n"
                    + "          |   A tilde ...... \u00C4A/\u00C4a |   G cedilla .... \u00CBG/   |\n"
                    + "          |   A breve ...... \u00C6A/\u00C6a |                        |\n"
                    + "          |   A ring ....... \u00CAA/\u00CAa |   H circumflex . \u00C3H/\u00C3h |\n"
                    + "          |   A macron ..... \u00C5A/\u00C5a |                        |\n"
                    + "          |   A ogonek ..... \u00CEA/\u00CEa |   I acute ...... \u00C2I/\u00C2i |\n"
                    + "          |                        |   I grave ...... \u00C1I/\u00C1i |\n"
                    + "          |   C acute ...... \u00C2C/\u00C2c |   I circumflex . \u00C3I/\u00C3i |\n"
                    + "          |   C circumflex . \u00C3C/\u00C3c |   I diaeresis .. \u00C8I/\u00C8i |\n"
                    + "          |   C caron ...... \u00CFC/\u00CFc |   I tilde ...... \u00C4I/\u00C4i |\n"
                    + "          |   C dot ........ \u00C7C/\u00C7c |   I dot ........ \u00C7I/   |\n"
                    + "          |   C cedilla .... \u00CBC/\u00CBc |   I macron ..... \u00C5I/\u00C5i |\n"
                    + "          |                        |   I ogonek ..... \u00CEI/\u00CEi |\n"
                    + "          |   D caron ...... \u00CFD/\u00CFd |                        |\n"
                    + "          |                        |   J circumflex . \u00C3J/\u00C3j |\n"
                    + "          |   E acute ...... \u00C2E/\u00C2e |                        |\n"
                    + "          |   E grave ...... \u00C1E/\u00C1e |   K cedilla .... \u00CBK/\u00CBk |\n"
                    + "          |   E circumflex . \u00C3E/\u00C3e |                        |\n"
                    + "          |   E diaeresis .. \u00C8E/\u00C8e |   L acute ...... \u00C2L/\u00C2l |\n"
                    + "          |   E caron ...... \u00CFE/\u00CFe |   L caron ...... \u00CFL/\u00CFl |\n"
                    + "          |   E dot ........ \u00C7E/\u00C7e |   L cedilla .... \u00CBL/\u00CBl |\n"
                    + "          |   E macron ..... \u00C5E/\u00C5e |                        |\n"
                    + "          |   E ogonek ..... \u00CEE/\u00CEe |   N acute ...... \u00C2N/\u00C2n |\n"
                    + "          |                        |   N tilde ...... \u00C4N/\u00C4n |\n"
                    + "          |                        |   N caron ...... \u00CFN/\u00CFn |\n"
                    + "          |                        |   N cedilla .... \u00CBN/\u00CBn |\n"
                    + "          +-------------------------------------------------+\n" + "\n\u000C"
                    + "      In deze laatste  tabel,  ontleend  aan  par. II.4  van  het  LO,\n"
                    + "      blz. 374-377,  vindt  u  een opsomming van de verplichte letter-\n"
                    + "      diakriet combinaties van O t/m Z die moeten worden ondersteund.\n" + "\n"
                    + "          +-------------------------------------------------+\n"
                    + "          |     Naam         H/K   |     Naam         H/K   |\n"
                    + "          |------------------------+------------------------|\n"
                    + "          |   O acute ...... \u00C2O/\u00C2o |   U acute ...... \u00C2U/\u00C2u |\n"
                    + "          |   O grave ...... \u00C1O/\u00C1o |   U grave ...... \u00C1U/\u00C1u |\n"
                    + "          |   O circumflex . \u00C3O/\u00C3o |   U circumflex . \u00C3U/\u00C3u |\n"
                    + "          |   O diaeresis .. \u00C8O/\u00C8o |   U diaeresis .. \u00C8U/\u00C8u |\n"
                    + "          |   O tilde ...... \u00C4O/\u00C4o |   U tilde ...... \u00C4U/\u00C4u |\n"
                    + "          |   O double acute \u00CDO/\u00CDo |   U breve ...... \u00C6U/\u00C6u |\n"
                    + "          |   O macron ..... \u00C5O/\u00C5o |   U double acute \u00CDU/\u00CDu |\n"
                    + "          |                        |   U ring ....... \u00CAU/\u00CAu |\n"
                    + "          |   R acute ...... \u00C2R/\u00C2r |   U macron ..... \u00C5U/\u00C5u |\n"
                    + "          |   R caron ...... \u00CFR/\u00CFr |   U ogonek ..... \u00CEU/\u00CEu |\n"
                    + "          |   R cedilla .... \u00CBR/\u00CBr |                        |\n"
                    + "          |                        |   W circumflex . \u00C3W/\u00C3w |\n"
                    + "          |   S acute ...... \u00C2S/\u00C2s |                        |\n"
                    + "          |   S circumflex . \u00C3S/\u00C3s |   Y acute ...... \u00C2Y/\u00C2y |\n"
                    + "          |   S caron ...... \u00CFS/\u00CFs |   Y circumflex . \u00C3Y/\u00C3y |\n"
                    + "          |   S cedilla .... \u00CBS/\u00CBs |   Y diaeresis .. \u00C8Y/\u00C8y |\n"
                    + "          |                        |                        |\n"
                    + "          |   T caron ...... \u00CFT/\u00CFt |   Z acute ...... \u00C2Z/\u00C2z |\n"
                    + "          |   T cedilla .... \u00CBT/\u00CBt |   Z caron ...... \u00CFZ/\u00CFz |\n"
                    + "          |                        |   Z dot ........ \u00C7Z/\u00C7z |\n"
                    + "          +-------------------------------------------------+\n" + "\n"
                    + "      Indien u alle TELETEX tekens juist hebt kunnen afbeelden, mag u\n"
                    + "      dit deel van de test VRY als geslaagd beschouwen.\n" + "\n"
                    + "             --------------------------------------------------\n";

    /**
     * Assert correct handling of Vrij bericht with Teletex characters.
     * 
     * @see #VRIJ_BERICHT_TELETEX_TEKENS
     */
    public void testVrijBerichtTeletexTekens() {
        final int[] firstUnknownCharacter = new int[1];
        System.out.println("\nVrij bericht met alle Teletex tekens:\n" + VRIJ_BERICHT_TELETEX_TEKENS); //
        final String unicodeString =
                GBACharacterSet.convertTeletex2Unicode(VRIJ_BERICHT_TELETEX_TEKENS, firstUnknownCharacter);

        if (firstUnknownCharacter[0] == -1) {
            // no errors found
        } else {
            System.err.print("Error at offset " + firstUnknownCharacter[0] + ": [");
            System.err.println(VRIJ_BERICHT_TELETEX_TEKENS.charAt(firstUnknownCharacter[0]) + "]");
            fail();
        }

        assertEquals(GBACharacterSet.convertUnicode2Teletex(unicodeString), VRIJ_BERICHT_TELETEX_TEKENS);
    }

    /**
     * For testing purposes create a file containing a Vrij Bericht with all of the Teletex characters according to the
     * sPd PutMessage format. <br>
     * TODO AJPvR Deze test wordt nergens aangeroepen en gebruikt een klasse (StringUtil) die naar migr-isc-voa is
     * verhuisd. Misschien deze test ook tzt die kant op verhuizen.
     */
    // public void createVrijBerichtTeletexTekensPutMessageFile() throws IOException {
    // final String PUT_ENVELOPE = "00024" + "120" + "3000200" + "2" + "0" + "1009271437Z" + "0";
    // final String MESSAGE_HEADING =
    // "00045" + "150" + "128559102213" + "000000000000" + "3000200" + "001" + "3000200" + "0";
    // final String VB01_BODY = "00000000" + // random key
    // "Vb01" + StringUtil.zeroPadded(VRIJ_BERICHT_TELETEX_TEKENS.length(), 5) + VRIJ_BERICHT_TELETEX_TEKENS;
    // final String PUT_MESSAGE =
    // PUT_ENVELOPE + MESSAGE_HEADING + StringUtil.zeroPadded(VB01_BODY.length() + 3, 5) + "180" + VB01_BODY
    // + "00000";
    //
    // final FileOutputStream fos = new FileOutputStream(new File("teletexPutMessage.dat"));
    // for (int i = 0; i < PUT_MESSAGE.length(); i++) {
    // fos.write((byte) PUT_MESSAGE.charAt(i));
    // }
    // fos.close();
    // }

    /**
     * test the Teletex2UnicodeConversionTables
     * 
     */
    public void testTeletex2UnicodeConversionTables() {
        final int[] teletex = GBACharacterSet.createGBATeletex();

        System.out.println("GBA teletex to unicode:");
        for (int i = 0; i < teletex.length; i++) {
            final int code = teletex[i];
            System.out.print(i + 1 + "   0x" + Integer.toHexString(code));
            System.out.println("   0x" + Integer.toHexString(GBACharacterSet.convertTeletex2Unicode(code)));
        }

        final long startTijd = System.currentTimeMillis();
        int times = 0;
        for (int teller = 0; teller < 1000; teller++) {
            for (int i = 0; i < teletex.length; i++) {
                final int code = teletex[i];
                assertTrue(code == GBACharacterSet.convertUnicode2Teletex(GBACharacterSet
                        .convertTeletex2Unicode(code)));
                times += 2;
            }
        }
        System.out.println(times + " conversions in " + (System.currentTimeMillis() - startTijd) + " ms");
        System.out.println("\n");
    }

    /**
     * test the Unicode2TeletexStringConversion with: aABbCcYy\u00E0!3z\u00C0\u0143\u00BC\u00E3\u0106
     * 
     */
    public void testUnicode2TeletexStringConversion() {
        // unicode to teletex
        final String unicodeString = "aABbCcYy\u00E0!3z\u00C0\u0143\u00BC\u00E3\u0106";
        System.out.println("unicode: " + unicodeString); // aABbCcYyà!3zÀ?¼ã?
        final String teletexString = GBACharacterSet.convertUnicode2Teletex(unicodeString);
        System.out.println("unicode --> teletex: " + teletexString);

        assertTrue(teletexString
                .equals("\u0061\u0041\u0042\u0062\u0043\u0063\u0059\u0079\u00c1\u0061\u0021\u0033\u007a\u00c1\u0041\u00c2\u004e\u00bc\u00c4\u0061\u00c2\u0043"));
        System.out.println("hex representation: "
                + GBACharacterSet.toHexString(GBACharacterSet.convertUnicode2Teletex(unicodeString)));

        final String backAgainString =
                GBACharacterSet.convertTeletex2Unicode(GBACharacterSet.convertUnicode2Teletex(unicodeString));

        assertTrue(backAgainString.equals(unicodeString));

        System.out.println("and back again: " + GBACharacterSet.toHexString(backAgainString));

        System.out.println("\n");
    }

    /**
     * test the Teletex2UnicodeStringConversion with: \u0031\u0032\u00C1\u0061\u00C2\u0043
     * 
     */
    public void testTeletex2UnicodeStringConversion() {
        // teletex to unicode
        final String teletexString = "\u0031\u0032\u00C1\u0061\u00C2\u0043";
        System.out.println("teletex: " + teletexString); // 12ÁaÂC
        final String unicodeString = GBACharacterSet.convertTeletex2Unicode(teletexString);

        assertTrue(unicodeString.equals("\u0031\u0032\u00e0\u0106"));
        System.out.println("teletex -> unicode: " + unicodeString);

        System.out.println("hex representation: "
                + GBACharacterSet.toHexString(GBACharacterSet.convertTeletex2Unicode(teletexString)));

        final String backAgainString =
                GBACharacterSet.convertUnicode2Teletex(GBACharacterSet.convertTeletex2Unicode(teletexString));
        assertTrue(backAgainString.equals(teletexString));
        System.out.println("and back again: " + GBACharacterSet.toHexString(backAgainString));
    }

    /**
     * test the ConversionErrorDetection with String: abc$\u00A4#\u00A6
     * 
     */
    public void testConversionErrorDetection() {
        final int[] firstUnknownCharacter = new int[1];
        final String teletexString = "abc$\u00A4#\u00A6";
        System.out.println("\n");

        System.out.println("teletex: " + teletexString); //
        String unicodeString = GBACharacterSet.convertTeletex2Unicode(teletexString, firstUnknownCharacter);
        assertTrue(unicodeString.equals("abc\u003f\u0024\u003f\u0023"));
        assertTrue(firstUnknownCharacter[0] == 3); // ascii '$' is not permitted

        unicodeString = GBACharacterSet.convertTeletex2Unicode("no errors", firstUnknownCharacter);
        assertTrue(firstUnknownCharacter[0] == -1);
    }

    /**
     * Test stripping of a unicoded String containing diacritical marks. The diacritical marks in the resulting string
     * should be replaced by a normal character in the range [a..z,A..Z]. <BR>
     * In addition also check whether invalid GBA characters are detected and converted to '?'.
     * <P>
     * The testset contains:
     * <UL>
     * <LI>SMALL A WITH ACUTE - expected conversion 00E1 ==> 61
     * <LI>CAPITAL LETTER A WITH ACUTE - expected conversion 00C1 ==> 41
     * <LI>ascii $ - expected conversion 0024 ($)
     * <LI>SMALL LETTER C WITH ACUTE - expected conversion 0107 ==> 63
     * <LI>CAPITAL LETTER C WITH ACUTE - expected conversion 0106 ==> 43
     * <LI>invalid 0xffff - expected conversion '?'
     * </UL>
     */
    public void testStripUnicode() {
        final int[] offset_invalid_char = new int[1];
        System.out.println("testStripUnicode - start");
        final String unicodeStringWithDiacriticalMarks = "\u00E1\u00C1\u0024\u0107\u0106\uffff";
        final String unicodeStringStripped =
                GBACharacterSet.convertUnicode2Stripped(unicodeStringWithDiacriticalMarks, offset_invalid_char);
        System.out.println("stripped hex representation: " + GBACharacterSet.toHexString(unicodeStringStripped));
        assertTrue(offset_invalid_char[0] == 5);
        assertTrue(unicodeStringStripped.equals("\u0061\u0041\u0024\u0063\u0043?"));
        System.out.println("testStripUnicode - end");
    }

    /**
     * test GBAAsciiConversionExceptions with: $\u00A4#\u00A6 expected outcome: \u003f\u0024\u003f\u0023
     */
    public void testGBAAsciiConversionExceptions() {
        final String teletexString = "$\u00A4#\u00A6";
        System.out.println("\n");

        System.out.println("teletex: " + teletexString); //
        final String unicodeString = GBACharacterSet.convertTeletex2Unicode(teletexString);
        System.out.println("teletex -> unicode: " + unicodeString);
        System.out.println("hex representation: " + GBACharacterSet.toHexString(unicodeString));

        assertTrue(unicodeString.equals("\u003f\u0024\u003f\u0023"));
    }

    /**
     * Test whether special characters are stripped correctly according to the mapping table in 'Dossier Diakrieten'.
     */
    public void testStrippingSpecialCharacters() {
        final String charsFromDossierDiakrieten = "\u00a1" + // inverted exclamation mark
                "\u00a2" + // cent sign
                "\u00a3" + // pound sign
                "$" + // dollar sign
                "\u00a5" + // yen sign
                "#" + // number sign
                "\u00a7" + // section sign
                "\u00a4" + // currency sign
                "\u00ab" + // angle quotation mark left
                "\u00b0" + // degree sign
                "\u00b1" + // plus minus sign
                "\u00b2" + // superscript 2
                "\u00b3" + // superscript 3
                "\u00d7" + // multiply sign
                "\u00b5" + // micro sign
                "\u00b6" + // paragraph/pilcrow sign
                "\u00b7" + // middle dot
                "\u00f7" + // divide sign
                "\u00bb" + // angle quotation mark right
                "\u00bc" + // fraction one quarter
                "\u00bd" + // fraction one half
                "\u00be" + // fraction three quarter
                "\u00bf" + // inverted question mark
                "\u2126" + // Ohm sign
                "\u00c6" + // capital AE diphtong
                "\u0110" + // capital D with stroke
                "\u00aa" + // ordinal indicator feminine
                "\u0126" + // capital H with stroke
                "\u013f" + // capital L with middle dot
                "\u0141" + // capital L with stroke
                "\u00d8" + // capital O with slash
                "\u0152" + // capital OE ligature
                "\u00ba" + // ordinal indicator masculine
                "\u00de" + // capital thorn, icelandic
                "\u0166" + // capital T with stroke
                "\u014a" + // capital eng, lapp
                "\u0149" + // small n with apostrophe
                "\u0138" + // small k, greenlandic
                "\u00e6" + // small ae diphtong
                "\u0111" + // small d with stroke
                "\u00f0" + // small eth, icelandic
                "\u0127" + // small h with stroke
                "\u0131" + // small i without dot
                "\u0140" + // small l with middle dot
                "\u0142" + // small l with stroke
                "\u00f8" + // small o with slash
                "\u0153" + // small oe ligature
                "\u00df" + // small sharp s, german
                "\u00fe" + // small thorn, icelandic
                "\u0167" + // small t with stroke
                "\u014b"; // small eng, lapp

        System.out.println("Input characters from table 1 in Dossier Diakrieten: " + charsFromDossierDiakrieten);
        final String asciiString = GBACharacterSet.convertUnicode2Stripped(charsFromDossierDiakrieten);
        System.out.println("Slim representation: " + asciiString);
        assertEquals("!cL$Y#§*<<o+/-23xµ¶.:>>1/41/23/4?OhmAEDaHL.LOOEoTHTNG'nqaeddhil.looessthtng", asciiString);
    }

    /**
     * Test reversibility of several strings
     */
    public void testConversionReversibility() {
        final String EXAMPLE0 = "÷";
        final String EXAMPLE1 = "ÇZÂaÈi÷ÈeÄnùÂr ÊAøÂeÁeËc âÈeËlýÊa";

        System.out.println("\nTEST CONVERSION REVERSIBILITY:");
        doTestConversionReversibility(EXAMPLE0);
        doTestConversionReversibility(EXAMPLE1);
    }

    private void doTestConversionReversibility(final String input) {
        String converted;
        String backagain;

        System.out.println("Conversion of " + input);
        System.out.println("original hex   : " + GBACharacterSet.toHexString(input));

        converted = GBACharacterSet.convertTeletex2Unicode(input);
        System.out.println("converted hex  : " + GBACharacterSet.toHexString(converted));

        backagain = GBACharacterSet.convertUnicode2Teletex(converted);
        System.out.println("back again hex : " + GBACharacterSet.toHexString(backagain));

        assertEquals(input, backagain);
    }

    // /**
    // * Test the Postgres COPY command, to see if all GBA-characters are correctly imported. (The data to import comes
    // * from a SQL Server.) The imported characters are compared with a defined set of GBA-characters.
    // *
    // * This test needs a table "unicode_chars". This table should be created with the file
    // * "create_GBA_character_table.sql" in the \sql dir. To fill this table with the COPY commando you should copy the
    // * files from the \sql dir to the database system (for example 10.20.178.160) - copy_unicode.sql -
    // teletex_utf8.txt
    // *
    // * On the database system use the following command on the prompt psql -h 10.20.178.160 database_name -f
    // * copy_unicode.sql
    // */
    // public void manualTestResultCopyCommand() {
    // System.out.println("Start Test...");
    // ((TransactionAssistant) ServiceLocator.getInstance().getService("TXAssistant")).invokeWithinNewTx(this,
    // "implResultCopyCommand");
    // System.out.println("End Test");
    // }
    //
    // public void implResultCopyCommand() {
    // final int[] teletex = GBACharacterSet.createGBATeletex();
    // final String[] importedUnicodeChars = new String[GBA_CHARACTER_SET_SIZE];
    // final String[] convertedTeletexChars = new String[GBA_CHARACTER_SET_SIZE];
    // final String[] convertedHex = new String[GBA_CHARACTER_SET_SIZE];
    // String sTmp, sOriginalHex;
    // int i = 0;
    // try {
    // final String query = "SELECT u_char FROM unicode_chars";
    // final ResultSet result = getSession().connection().createStatement().executeQuery(query);
    // while (result.next()) {
    // convertedTeletexChars[i] = GBACharacterSet.convertUnicode2Teletex(result.getString(1));
    // convertedHex[i] = GBACharacterSet.toHexString(convertedTeletexChars[i]);
    // importedUnicodeChars[i] = result.getString(1);
    // i++;
    // }
    // } catch (final SQLException e1) {
    // e1.printStackTrace();
    // }
    //
    // // print the defined hex set and the imported hex representation
    // System.out.println("Defined teletex and converted teletex(from table unicode_chars):");
    // sOriginalHex = "";
    // sTmp = "";
    // for (i = 0; i < teletex.length; i++) {
    // final int code = teletex[i];
    // sOriginalHex = Integer.toHexString(code);
    // sTmp = convertedHex[i];
    // sTmp = sTmp.replaceAll(" 0x", "");
    //
    // // print the defined content and table content and the unicode character
    // System.out.print((i + 1) + " orig_Hex  0x" + sOriginalHex);
    // System.out.print("\t\t conv_Hex: 0x" + sTmp);
    // System.out.println("\t\t Unicode: " + convertedTeletexChars[i]);
    //
    // }
    //
    // // print the characters that are different from the original characters
    // System.out.println();
    // System.out.println("Characters that are not correct converted:");
    // for (i = 0; i < teletex.length; i++) {
    // final int code = teletex[i];
    // sOriginalHex = Integer.toHexString(code);
    // sTmp = convertedHex[i];
    // sTmp = sTmp.replaceAll(" 0x", "");
    //
    // // test if the conversion is correct
    // if (!sOriginalHex.equals(sTmp)) {
    // System.out.print((i + 1) + " orig_Hex  0x" + sOriginalHex);
    // System.out.println("\t conv_Hex: 0x" + sTmp);
    // }
    // }
    // }
    //
    // /**
    // * Test to test if table lo3_akte_aand is correctly filled The middle dot is in the BPR-database not correct. It
    // is
    // * stored as a bullet When the table is correctly filled it should be possible to convert the lo3_akte_aand to
    // * Teletex. When not on the place of the middle dot a ? will shown
    // */
    // public void manualTestResultConvertBulletToTeletex() {
    // System.out.println("Start Test...");
    // ((TransactionAssistant) ServiceLocator.getInstance().getService("TXAssistant")).invokeWithinNewTx(this,
    // "implResultConvertBulletToTeletex");
    // System.out.println("End Test");
    // }
    //
    // public void implResultConvertBulletToTeletex() {
    // final String[] akteAanduidingen_unicode = new String[47];
    // final String[] akteAanduidingen_hex = new String[47];
    // int i = 0;
    // try {
    // final String query = "SELECT akte_aand FROM lo3_akte_aand";
    // final ResultSet result = getSession().connection().createStatement().executeQuery(query);
    // while (result.next()) {
    // akteAanduidingen_unicode[i] = result.getString(1);
    // akteAanduidingen_hex[i] = GBACharacterSet.convertUnicode2Teletex(result.getString(1));
    // i++;
    // }
    // } catch (final SQLException e1) {
    // e1.printStackTrace();
    // }
    //
    // System.out.println("Akte aanduidingen:");
    // for (i = 0; i < akteAanduidingen_unicode.length; i++) {
    // System.out.println((i + 1) + " " + akteAanduidingen_unicode[i] + " Teletex: " + akteAanduidingen_hex[i]);
    // }
    //
    // System.out.println("Try to convert Akte aanduiding to Teletex");
    // try {
    // GBACharacterSet.convertUnicode2Teletex(akteAanduidingen_unicode[1]);
    // } catch (final Exception e) {
    // e.printStackTrace();
    // }
    //
    // }

    /**
     * Test the method isValidTeletex(String s) with strings containing only valid teletex characters and strings with
     * non-teletex characters.
     */
    public void testIsValidTeletex() {
        final String incorrectTeletex1 = "abc$\u00A4#\u00A6";
        final String incorrectTeletex2 = "abc\u9999";
        final String correctTeletex1 = "abc\u00A4";
        System.out.println("\n");
        doTestIsValidTeletex(incorrectTeletex1, false);
        doTestIsValidTeletex(incorrectTeletex2, false);
        doTestIsValidTeletex(correctTeletex1, true);

        System.out.println("Now test all teletexCharacters");
        final int[] allTeletex = GBACharacterSet.createGBATeletex();
        final char[] chars = new char[allTeletex.length];
        for (int i = 0; i < allTeletex.length; i++) {
            chars[i] = (char) allTeletex[i];
        }
        final String str = new String(chars);
        doTestIsValidTeletex(str, true);
    }

    /**
     * Test one String <tt>teletex</tt> if the <tt>isValidTeletex()</tt> method returns <tt>expected</tt>.
     * 
     * @param teletex
     *            String with characters.
     * @param expected
     *            true if the String teletex should contain only valid teletex characters.
     */
    public void doTestIsValidTeletex(final String teletex, final boolean expected) {
        System.out.print("Test for valid teletex: '" + teletex + "'"); //
        final boolean isValid = GBACharacterSet.isValidTeletex(teletex);
        System.out.println(" ==> " + isValid);
        assertEquals("isValidTeletex(" + teletex + ") hex(" + GBACharacterSet.toHexString(teletex)
                + ") is not as expected.", isValid, expected);
    }
}
