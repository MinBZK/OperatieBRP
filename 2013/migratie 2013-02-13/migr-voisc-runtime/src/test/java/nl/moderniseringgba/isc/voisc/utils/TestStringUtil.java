/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestStringUtil {

    @Test
    public void testFillBefore() {
        final String t = "test";
        final char c = 'a';

        assertEquals("atest", StringUtil.fillBefore(t, c, t.length() + 1));

        try {
            StringUtil.fillBefore(t, c, 3);
        } catch (final IllegalArgumentException re) {
            assertEquals("value too long: test", re.getMessage());
        }
    }

    @Test
    public void testZeroPadding() {
        final long test1 = -1;
        final long test2 = 0;
        final long test3 = 1;
        try {
            StringUtil.zeroPadded(test1, 0);
        } catch (final IllegalArgumentException re) {
            assertEquals("Cannot zero pad negative numeric value", re.getMessage());
        }

        try {
            StringUtil.zeroPadded(test2, 0);
        } catch (final IllegalArgumentException re) {
            assertEquals("Length of zero-padded value should be at least 1", re.getMessage());
        }

        assertEquals("001", StringUtil.zeroPadded(test3, 3));
    }
}
