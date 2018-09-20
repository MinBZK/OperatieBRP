/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.generators;


import nl.bzk.brp.testclient.misc.Constanten;

/**
 * De Anr generator.
 */
public final class AnrGenerator {

    private static final long START_ANR = 1062476061L;

    /**
     * Instantieert een nieuwe Anr generator.
     */
    private AnrGenerator() {
    }

    /**
     * Anummer.
     */
    private static volatile long anr = START_ANR;

    /**
     * Verkrijgt volgende anr.
     *
     * @return volgende anr
     */
    public static long getVolgendeAnr() {
        anr++;
        while ((!isValideANummer(anr))) {
            anr++;
        }
        return anr;
    }

    /**
     * Controleert of nummer een valide aadministratienummer is.
     *
     * @param potentieelANummer mogelijk geldig a-nummer
     * @return boolean
     */
    public static boolean isValideANummer(final long potentieelANummer) {
        boolean isValideANummer = true;

        int a0, a1, a2, a3, a4, a5, a6, a7, a8, a9;
        final String nummer = "" + potentieelANummer;

        // a0 is altijd ongelijk aan nul "0";
        if (potentieelANummer < Constanten.MILJARD) {
            isValideANummer = false;
        } else {
            final char[] chars = nummer.toCharArray();

            // 2 opeenvolgende cijfers zijn ongelijk;
            for (int i = 0; i < chars.length - 1; i++) {
                if (chars[i] == chars[i + 1]) {
                    isValideANummer = false;
                }
            }

            final char offset = '0';
            a0 = chars[Constanten.NUL] - offset;
            a1 = chars[Constanten.EEN] - offset;
            a2 = chars[Constanten.TWEE] - offset;
            a3 = chars[Constanten.DRIE] - offset;
            a4 = chars[Constanten.VIER] - offset;
            a5 = chars[Constanten.VIJF] - offset;
            a6 = chars[Constanten.ZES] - offset;
            a7 = chars[Constanten.ZEVEN] - offset;
            a8 = chars[Constanten.ACHT] - offset;
            a9 = chars[Constanten.NEGEN] - offset;

            // a0+a1+...+a9 is deelbaar door 11;
            final int sum = a0 + a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 + a9;
            if (sum % Constanten.ELF != 0) {
                isValideANummer = false;
            }

            // (1*a0)+(2*a1)+(4*a2)+...+(512*a9) is deelbaar door 11;
            final int sum2 = a0
                    + Constanten.TWEE * a1
                    + Constanten.VIER * a2
                    + Constanten.ACHT * a3
                    + Constanten.ZESTIEN * a4
                    + Constanten.TWEEENDERTIG * a5
                    + Constanten.VIERENZESTIG * a6
                    + Constanten.HONDERD_ACHTENTWINTIG * a7
                    + Constanten.TWEEHONDERD_ZESENVIJFTIG * a8
                    + Constanten.VIJHONDERD_TWAALF * a9;
            if (sum2 % Constanten.ELF != 0) {
                isValideANummer = false;
            }
        }
        return isValideANummer;
    }

    /**
     * Verkrijgt anr.
     *
     * @return anr
     */
    public static long getAnr() {
        return anr;
    }

    /**
     * Zet anr.
     *
     * @param anr anr
     */
    public static void setAnr(final long anr) {
        AnrGenerator.anr = anr;
    }

}
