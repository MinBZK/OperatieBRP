/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.generators;

import java.util.Random;

/**
 * De Class BSNGenerator.
 */
public class BSNGenerator {

    /** De rand. */
    private static Random rand = new Random();

    //private static final List<Long> done = new TreeList();

    /** De bsn. */
//    private static volatile long bsn = 100000000L;
    public static volatile long bsn =   102290295L;


    /**
     * Next bsn.
     *
     * @return de string
     */
    public synchronized static String nextBSN() {

//	String nr;
//	while (done.contains(nr = nextNonUniqueBSN())) {
//	}
//
//	return nr;

	bsn++;

	while (!isValidBsnNummer(bsn)) {
	    bsn++;
	}

	//done.add(bsn);

	return "" + bsn;

    }

    /**
     * Controleert of de waarde gelijk is aan valid bsn nummer.
     *
     * @param possibleBsnNummer de possible bsn nummer
     * @return true, als waarde gelijk is aan valid bsn nummer
     */
    public static boolean isValidBsnNummer(final long possibleBsnNummer) {
	int a0, a1, a2, a3, a4, a5, a6, a7, a8;
	final String nummer = "" + possibleBsnNummer;
	// a0 is altijd ongelijk aan nul "0";
	if (possibleBsnNummer < 100000000L) {
	    return false;
	}
	final char[] chars = nummer.toCharArray();

	final char offset = '0';
	a0 = chars[0] - offset;
	a1 = chars[1] - offset;
	a2 = chars[2] - offset;
	a3 = chars[3] - offset;
	a4 = chars[4] - offset;
	a5 = chars[5] - offset;
	a6 = chars[6] - offset;
	a7 = chars[7] - offset;
	a8 = chars[8] - offset;

	// (9*s0)+(8*s1)+(7*s2)+...+(2*s7)-(1*s8) is deelbaar door 11.
	final int sum = (9 * a0) + (8 * a1) + (7 * a2) + (6 * a3) + (5 * a4) + (4 * a5) + (3 * a6) + (2 * a7) - a8;
	if (sum % 11 != 0) {
	    return false;
	}

	return true;
    }

    /**
     * Next non unique bsn.
     *
     * @return de string
     */
    @SuppressWarnings("unused")
    private static String nextNonUniqueBSN() {

	final double Nr9 = Math.floor(rand.nextDouble() * 3);
	double Nr8 = Math.floor(rand.nextDouble() * 10);
	final double Nr7 = Math.floor(rand.nextDouble() * 10);
	final double Nr6 = Math.floor(rand.nextDouble() * 10);
	final double Nr5 = Math.floor(rand.nextDouble() * 10);
	final double Nr4 = Math.floor(rand.nextDouble() * 10);
	final double Nr3 = Math.floor(rand.nextDouble() * 10);
	double Nr2 = Math.floor(rand.nextDouble() * 10);
	double Nr1 = 0;
	if ((Nr9 == 0) && (Nr8 == 0) && (Nr7 == 0)) {
	    Nr8 = 1;
	};
	final double SofiNr = 9 * Nr9 + 8 * Nr8 + 7 * Nr7 + 6 * Nr6 + 5 * Nr5 + 4 * Nr4 + 3 * Nr3 + 2 * Nr2;
	Nr1 = Math.floor(SofiNr - (Math.floor(SofiNr / 11)) * 11);
	if (Nr1 > 9) {
	    if (Nr2 > 0) {
		Nr2 -= 1;
		Nr1 = 8;
	    } else {
		Nr2 += 1;
		Nr1 = 1;
	    }
	}
	String Result1 = "" + (int) Math.floor(Nr9);
	Result1 += (int) Math.floor(Nr8);
	Result1 += (int) Math.floor(Nr7);
	Result1 += (int) Math.floor(Nr6);
	Result1 += (int) Math.floor(Nr5);
	Result1 += (int) Math.floor(Nr4);
	Result1 += (int) Math.floor(Nr3);
	Result1 += (int) Math.floor(Nr2);
	Result1 += (int) Math.floor(Nr1);
	return Result1;
    }
}
