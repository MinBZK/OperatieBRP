/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * ANummerValidatorTest.
 */
public class ANummerValidatorTest {

    private static final String A_NR_ONGELDIGE_LENGTE = "123456789";
    private static final String A_NR_EERSTE_CIJFER_O = "0123456789";
    private static final String A_NR_ONGELDIG_FORMAAT = "a123456789";

    private static final List<String> invalideAnrsMbtDeelbaarheid =
            Lists.newArrayList(
                    "3612086034",
                    "3318160914",
                    "4163524370",
                    "4061039378",
                    "9719842578",
                    "8579717906",
                    "3869598226",
                    "4686946834",
                    "9340625682");

    private static final List<String> invalideAnrsOpeenvolgendeGetallenGelijk =
            Lists.newArrayList(
                    "1123456789",
                    "1223456879",
                    "1233456879",
                    "1234456789",
                    "1234556789",
                    "1234566789",
                    "1234567789",
                    "1234567889",
                    "1234567899");

    private static final List<String> valideAnrs =
            Lists.newArrayList(
                    "9376096018",
                    "9361408274",
                    "3471617810",
                    "8951247634",
                    "7257905938",
                    "1724809490",
                    "9630907154",
                    "4536075026",
                    "8232580370",
                    "2817162386",
                    "6031865618",
                    "1320893426");


    @Test
    public void aNummerIsGeldig() {
        List<Boolean> result = new ArrayList<>();
        for (String anr : valideAnrs) {
            assertTrue(ANummerValidator.isGeldigANummer(anr));
        }
        assertFalse(result.contains(Boolean.FALSE));
    }

    @Test
    public void invalideANrOngeldigLengte() {
        assertFalse(ANummerValidator.isGeldigANummer(A_NR_ONGELDIGE_LENGTE));
    }

    @Test
    public void invalideANrGeenNumeriekFormaat() {
        assertFalse(ANummerValidator.isGeldigANummer(A_NR_ONGELDIG_FORMAAT));
    }

    @Test
    public void invalideANrEersteCijferIs0() {
        assertFalse(ANummerValidator.isGeldigANummer(A_NR_EERSTE_CIJFER_O));
    }

    @Test
    public void invalideANrMbtDeelbaarheid() {
        List<Boolean> result = new ArrayList<>();
        for (String anr : invalideAnrsMbtDeelbaarheid) {
            result.add(ANummerValidator.isGeldigANummer(anr));
        }
        assertFalse(result.contains(Boolean.TRUE));
    }

    @Test
    public void invalideANrOpeenvolgendeCijfersGelijk() {
        List<Boolean> result = new ArrayList<>();
        for (String anr : invalideAnrsOpeenvolgendeGetallenGelijk) {
            result.add(ANummerValidator.isGeldigANummer(anr));
        }
        assertFalse(result.contains(Boolean.TRUE));
    }


}