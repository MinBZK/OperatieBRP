/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class Lo3IntegerTest {

    @Test
    public void testGetIntegerWaarde() throws Exception {
        Lo3Integer lo3a = new Lo3Integer("101", null);
        Lo3Integer lo3b = new Lo3Integer(null, null);
        assertEquals(new Integer(101), lo3a.getIntegerWaarde());
        assertNull(lo3b.getIntegerWaarde());
    }

    @Test
    public void testWrap() throws Exception {
        Lo3Integer lo3a = Lo3Integer.wrap(103);
        assertEquals(new Integer(103), lo3a.getIntegerWaarde());
        assertNull(Lo3Integer.wrap(null));
    }

    @Test
    public void testUnwrap() throws Exception {
        Lo3Integer lo3a = Lo3Integer.wrap(104);
        assertEquals(new Integer(104), Lo3Integer.unwrap(lo3a));
        assertNull(Lo3Integer.unwrap(null));
        Lo3Integer lo3b = new Lo3Integer(null, null);
        assertNull(Lo3Integer.unwrap(lo3b));
    }
}
