/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Random bsn helper.
 */
public class RandomBsnHelper {

    private static final Logger LOG = Logger.getLogger(RandomBsnHelper.class);

    private static final int MAX_BSN_NUMBER = 999999999;

    private final int threadIndex;
    private final int rangeSize;
    private final Integer[] bsnNumbers;

    private int currentBsnFloorNumber;
    private int currentIndex;

    /**
     * Instantieert Random bsn helper.
     *
     * @param threadIndex thread index
     * @param rangeSize range size
     * @param threadBsnBlockSize thread bsn block size
     */
    public RandomBsnHelper(final int threadIndex, final int rangeSize, final int threadBsnBlockSize) {
        this.rangeSize = rangeSize;
        this.threadIndex = threadIndex;
        bsnNumbers = new Integer[rangeSize];
        currentBsnFloorNumber = 100000000 + threadIndex * threadBsnBlockSize;
        reinitialize();
    }

    /**
     * Reinitializeer.
     */
    public void reinitialize() {

        LOG.info("--------REINITIALIZATION-[" + threadIndex + "] vanaf:" + currentBsnFloorNumber + " aantal:" +
                         rangeSize + "---------");

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
        list.toArray(bsnNumbers);
        currentIndex = 0;
    }

    /**
     * Controleert of de waarde gelijk is aan valid bsn nummer.
     *
     * @param possibleBsnNummer de possible bsn nummer
     * @return true, als waarde gelijk is aan valid bsn nummer
     */
    public static boolean isValidBsnNummer(final long possibleBsnNummer) {
        boolean isValid = true;
        int a0, a1, a2, a3, a4, a5, a6, a7, a8;
        final String nummer = "" + possibleBsnNummer;
        // a0 is altijd ongelijk aan nul "0";
        if (possibleBsnNummer < 100000000L) {
            isValid = false;
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
        final int sum = 9 * a0 + 8 * a1 + 7 * a2 + 6 * a3 + 5 * a4 + 4 * a5 + 3 * a6 + 2 * a7 - a8;
        if (sum % Constanten.ELF != 0) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Geeft random bsn.
     *
     * @return the integer
     */
    public Integer randomBsn() {
        if (currentIndex >= rangeSize) {
            reinitialize();
        }
        return bsnNumbers[currentIndex++];
    }

    private static final int ANUMMER_LENGTE = 10;
    private static final int ELF = Constanten.ELF;
    private static final int REST_NUL = 0;
    private static final int REST_VIJF = 5;

    /**
     * Random int anders dan vorig int.
     *
     * @param i i
     * @return the int
     */
    private int randomIntAndersDanVorigInt(final int i) {
        int n = i;
        while (i == (n = random.nextInt(Constanten.TIEN))) {
            ;
        }
        return n;
    }

    /**
     * Build anr offset.
     *
     * @return long
     */
    private long buildAnrOffset() {
        int[] nrs = new int[Constanten.TIEN];
        nrs[0] = 0;
        for (int i = 0; i < Constanten.TIEN; i++) {
            nrs[i] = randomIntAndersDanVorigInt(i == 0 ? 0 : nrs[i - 1]);
        }
        long l = nrs[0];
        for (int i = 1; i < Constanten.TIEN; i++) {
            l = l * 10 + nrs[i];
        }
        return l;
    }

    /**
     * Random anr.
     *
     * @return long
     */
    public Long randomAnr() {
        Long l = buildAnrOffset();
        int i = 0;
        boolean valid = false;
        // DIT WERKT NIET, rule 2 zegt: a[i] <> a[i+1] ==> alle nummers moeten net even iets anders zijn.
        while (!valid && ++i <= Constanten.VEERTIG) {
            if (!(valid = isGeldigeAnummer(l))) {
                l++;
            }
        }
        return valid ? null : l;
    }

    /**
     * Sorry, dit is code die ge copy/paste is van de brp AdministratienummerValidator; zoek zelf maar uit wat het doet.
     * @param l
     * @return .
     */
    public boolean isGeldigeAnummer(final Long l) {
        final String waarde = StringUtils.leftPad(l.toString(), ANUMMER_LENGTE, '0');
        return waarde.length() == ANUMMER_LENGTE  && voldoetAanRegel1(waarde)
                && voldoetAanRegel2(waarde) && voldoetAanRegel3(waarde) && voldoetAanRegel4(waarde);
    }
    /**
     * 1) a[0] <> 0.
     *
     * @param waarde de A-nummer
     * @return true als aan de regel voldoet
     */
    private boolean voldoetAanRegel1(final String waarde) {
        return Character.getNumericValue(waarde.charAt(0)) != 0;
    }

    /**
     * 2) a[i] <> a[i+1].
     *
     * @param waarde de A-nummer
     * @return true als aan de regel voldoet
     */
    private boolean voldoetAanRegel2(final String waarde) {
        boolean resultaat = true;

        for (int i = 0; i < waarde.length() - 1; i++) {
            if (Character.getNumericValue(waarde.charAt(i)) == Character.getNumericValue(waarde.charAt(i + 1))) {
                resultaat = false;
                break;
            }
        }

        return resultaat;
    }

    /**
     * 3) a[0]+a[1]+...+a[9] is deelbaar door 11 of a[0]+a[1]+...+a[9] deelbaar door 11 geeft rest 0 of rest 5.
     *
     * @param waarde de A-nummer
     * @return true als aan de regel voldoet
     */
    private boolean voldoetAanRegel3(final String waarde) {
        int som = 0;

        // a[0]+a[1]+...+a[9] is deelbaar door 11
        for (int i = 0; i < waarde.length(); i++) {
            som += Character.getNumericValue(waarde.charAt(i));
        }

        return som % ELF == REST_NUL || som % ELF == REST_VIJF;
    }

    /**
     * 4) (1*a[0])+(2*a[1])+(4*a[2])+...+(512*a[9]) is deelbaar door 11.
     *
     * @param waarde de A-nummer
     * @return true als aan de regel voldoet
     */
    private boolean voldoetAanRegel4(final String waarde) {
        int som = 0;
        int factor = 1;
        for (int i = 0; i < waarde.length(); i++) {
            som += Character.getNumericValue(waarde.charAt(i)) * factor;

            factor *= 2;
        }

        return som % ELF == REST_NUL;
    }

}
