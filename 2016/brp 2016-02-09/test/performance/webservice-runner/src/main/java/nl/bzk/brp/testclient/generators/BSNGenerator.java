/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.generators;

import java.util.Random;
import nl.bzk.brp.testclient.misc.Constanten;

/** BSNGenerator. */
public final class BSNGenerator {

    private static final long START_BSN = 102290295L;

    /**
     * Instantieert een nieuwe BSN generator.
     */
    private BSNGenerator() {
    }

    /** De rand. */
    private static Random rand = new Random();

    /** De bsn. */
    private static volatile long bsn = START_BSN;

    /**
     * Next bsn.
     *
     * @return de string
     */
    public static synchronized String volgendeBSN() {
        bsn++;
        while (!isValideBsnNummer(bsn)) {
            bsn++;
        }
        return Long.toString(bsn);
    }

    /**
     * Controleert of de bsn een valide bsn nummer is.
     *
     * @param potentieelBsnNummer de possible bsn nummer
     * @return true, als het een valide bsn betreft
     */
    public static boolean isValideBsnNummer(final long potentieelBsnNummer) {
        int a0, a1, a2, a3, a4, a5, a6, a7, a8;
        final String nummer = "" + potentieelBsnNummer;
        // a0 is altijd ongelijk aan nul "0";
        if (potentieelBsnNummer < Constanten.HONDERDMILJOEN) {
            return false;
        }
        final char[] chars = nummer.toCharArray();

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

        // (9*s0)+(8*s1)+(7*s2)+...+(2*s7)-(1*s8) is deelbaar door 11.
        final int sum = (Constanten.NEGEN * a0)
                + (Constanten.ACHT * a1)
                + (Constanten.ZEVEN * a2)
                + (Constanten.ZES * a3)
                + (Constanten.VIJF * a4)
                + (Constanten.VIER * a5)
                + (Constanten.DRIE * a6)
                + (Constanten.TWEE * a7)
                - a8;
        return sum % Constanten.ELF == 0;
    }

    /**
     * Next non unique bsn.
     *
     * @return de string
     */
    @SuppressWarnings("unused")
    private static String nextNonUniqueBSN() {

        final double nr9 = Math.floor(rand.nextDouble() * 3);
        double nr8 = Math.floor(rand.nextDouble() * Constanten.TIEN);
        final double nr7 = Math.floor(rand.nextDouble() * Constanten.TIEN);
        final double nr6 = Math.floor(rand.nextDouble() * Constanten.TIEN);
        final double nr5 = Math.floor(rand.nextDouble() * Constanten.TIEN);
        final double nr4 = Math.floor(rand.nextDouble() * Constanten.TIEN);
        final double nr3 = Math.floor(rand.nextDouble() * Constanten.TIEN);
        double nr2 = Math.floor(rand.nextDouble() * Constanten.TIEN);
        double nr1;

        if (nr9 == Constanten.NUL && nr8 == Constanten.NUL && nr7 == Constanten.NUL) {
            nr8 = Constanten.EEN;
        }

        final double sofiNr = Constanten.NEGEN * nr9
                + Constanten.ACHT * nr8
                + Constanten.ZEVEN * nr7
                + Constanten.ZES * nr6
                + Constanten.VIJF * nr5
                + Constanten.VIER * nr4
                + Constanten.DRIE * nr3
                + Constanten.TWEE * nr2;
        nr1 = Math.floor(sofiNr - (Math.floor(sofiNr / Constanten.ELF)) * Constanten.ELF);
        if (nr1 > Constanten.NEGEN) {
            if (nr2 > Constanten.NUL) {
                nr2 -= Constanten.EEN;
                nr1 = Constanten.ACHT;
            } else {
                nr2 += Constanten.EEN;
                nr1 = Constanten.EEN;
            }
        }
        String resultaat = "" + (int) Math.floor(nr9);
        resultaat += (int) Math.floor(nr8);
        resultaat += (int) Math.floor(nr7);
        resultaat += (int) Math.floor(nr6);
        resultaat += (int) Math.floor(nr5);
        resultaat += (int) Math.floor(nr4);
        resultaat += (int) Math.floor(nr3);
        resultaat += (int) Math.floor(nr2);
        resultaat += (int) Math.floor(nr1);
        return resultaat;
    }

    /**
     * Verkrijgt bsn.
     *
     * @return bsn
     */
    public static long getBsn() {
        return bsn;
    }

    /**
     * Zet bsn.
     *
     * @param bsn bsn
     */
    public static void setBsn(final long bsn) {
        BSNGenerator.bsn = bsn;
    }
}
