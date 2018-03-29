/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationItemTest {
    @Test(expected = IllegalArgumentException.class)
    public void paddedValueLength0() {
        assertEquals("", OperationItem.paddedValueOfLength(0));
    }

    @Test
    public void paddedValueLength1() {
        assertEquals(" ", OperationItem.paddedValueOfLength(1));
    }

    @Test
    public void paddedValueLength2() {
        assertEquals("  ", OperationItem.paddedValueOfLength(2));
    }

    @Test
    public void paddedValue() {
        assertEquals("  value", OperationItem.paddedValueOfLength("value", 7));
    }

    @Test
    public void paddedEmptyValue() {
        assertEquals("    ", OperationItem.paddedValueOfLength("", 4));
    }

    @Test
    public void paddedNullValue() {
        assertEquals("    ", OperationItem.paddedValueOfLength(null, 4));
    }
}
