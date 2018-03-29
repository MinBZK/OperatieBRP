/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unittests voor {@link AnummerUtil}.
 */
public class AnummerUtilTest {

    @Test
    public void testValideerANummerOK() {
        assertTrue(AnummerUtil.isAnummerValide("1231231234"));
        assertTrue(AnummerUtil.isAnummerValide("1745105604"));
        assertTrue(AnummerUtil.isAnummerValide("6139365949"));
        assertTrue(AnummerUtil.isAnummerValide("7159878730"));
        assertTrue(AnummerUtil.isAnummerValide("4368978532"));
    }


    @Test
    public void testValideerANummerFOUT() {
        assertFalse(AnummerUtil.isAnummerValide(""));
        assertFalse(AnummerUtil.isAnummerValide("-284289241"));
        assertFalse(AnummerUtil.isAnummerValide("28428924141"));
        assertFalse(AnummerUtil.isAnummerValide("363570648A"));
        assertFalse(AnummerUtil.isAnummerValide("0465823130"));
        assertFalse(AnummerUtil.isAnummerValide("5565431828"));
        assertFalse(AnummerUtil.isAnummerValide("7159878731"));
    }
}
