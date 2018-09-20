/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.generators;


public class AnrGenerator {

    public volatile static long anr =   1062476061L;

    public static long getNextAnr() {

	anr++;

	//long anr = -1;
	while ((!isValidANummer(anr))) {
	    //anr = 1000000000L + (Math.abs(rand.nextLong()) % 8999999999L);
	    anr++;
	}

	//done.add(anr);

	return anr;
    }

    public static boolean isValidANummer(final long possibleANummer) {
	int a0, a1, a2, a3, a4, a5, a6, a7, a8, a9;
	final String nummer = "" + possibleANummer;
	// a0 is altijd ongelijk aan nul "0";
	if (possibleANummer < 1000000000L) {
	    return false;
	}
	final char[] chars = nummer.toCharArray();

	// 2 opeenvolgende cijfers zijn ongelijk;
	for (int i = 0; i < chars.length - 1; i++) {
	    if (chars[i] == chars[i + 1]) {
		return false;
	    }
	}

	// a0 = (int) (possibleANummer / 1000000000L);
	// a1 = (int) (possibleANummer % 1000000000L) / 100000000;
	// a2 = (int) (possibleANummer % 100000000L) / 10000000;
	// a3 = (int) (possibleANummer % 10000000L) / 1000000;
	// a4 = (int) (possibleANummer % 1000000L) / 100000;
	// a5 = (int) (possibleANummer % 100000L) / 10000;
	// a6 = (int) (possibleANummer % 10000L) / 1000;
	// a7 = (int) (possibleANummer % 1000L) / 100;
	// a8 = (int) (possibleANummer % 100L) / 10;
	// a9 = (int) (possibleANummer % 10L);

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
	a9 = chars[9] - offset;

	// a0+a1+...+a9 is deelbaar door 11;
	final int sum = a0 + a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 + a9;
	if (sum % 11 != 0) {
	    return false;
	}

	// (1*a0)+(2*a1)+(4*a2)+...+(512*a9) is deelbaar door 11;
	final int sum2 = a0 + 2 * a1 + 4 * a2 + 8 * a3 + 16 * a4 + 32 * a5 + 64 * a6 + 128 * a7 + 256 * a8 + 512 * a9;
	if (sum2 % 11 != 0) {
	    return false;
	}
	return true;
    }

}
