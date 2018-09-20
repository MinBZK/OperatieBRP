/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

import java.util.Random;


/**
 * De Class RandomUtil.
 */
public class RandomUtil {

    /**
     * Random.
     */
    public static Random     random    = new Random();

    /**
     * Next long.
     *
     * @param n de n
     * @return de long
     */
    public static long nextLong(final long n) {
        Random rng = new Random();

        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }

}
