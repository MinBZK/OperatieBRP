/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class RandomBSNService {

    private final static Logger LOG = Logger.getLogger(RandomBSNService.class);

    private static final int MAX_BSN_NUMBER = 999999999;

    private final int rangeSize;
    private final Integer[] BSN_NUMBERS;

    private int currentBsnFloorNumber;
    private int currentIndex;

    public RandomBSNService(final int threadIndex, final int rangeSize, final int threadBlockSize) {
        this.rangeSize = rangeSize;
        BSN_NUMBERS = new Integer[rangeSize];
        currentBsnFloorNumber = 100000000 + threadIndex * threadBlockSize;
        reinitialize();
    }

    public void reinitialize() {

        LOG.info("-----------REINITIALIZATION------------");

        if (currentBsnFloorNumber >= MAX_BSN_NUMBER) {
            throw new IllegalStateException("You're out of BSN's!");
        }

        List<Integer> list = new ArrayList<Integer>(rangeSize);
        for (int i = 0; i < rangeSize; i++) {
            while (!isValidBsnNummer(currentBsnFloorNumber)) {
                currentBsnFloorNumber++;
            }
            list.add(currentBsnFloorNumber);
            currentBsnFloorNumber++;
        }

        //Schudden
        Collections.shuffle(list);
        list.toArray(BSN_NUMBERS);
        currentIndex = 0;
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

    public Integer randomBsn() {
        if (currentIndex >= rangeSize) {
            reinitialize();
        }
        return BSN_NUMBERS[currentIndex++];
    }
}
