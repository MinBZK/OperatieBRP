/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class Lo3LongTest {

    @Test
    public void testGetLongWaarde() throws Exception {
        Lo3Long lo3a = new Lo3Long("101", null);
        Lo3Long lo3b = new Lo3Long(null, null);
        assertEquals(new Long(101), lo3a.getLongWaarde());
        assertNull(lo3b.getLongWaarde());
    }

    @Test
    public void testWrap() throws Exception {
        Lo3Long lo3a = Lo3Long.wrap(103L);
        assertEquals(new Long(103), lo3a.getLongWaarde());
        assertNull(Lo3Long.wrap(null));
    }

    @Test
    public void testUnwrap() throws Exception {
        Lo3Long lo3a = Lo3Long.wrap(104L);
        assertEquals(new Long(104), Lo3Long.unwrap(lo3a));
        assertNull(Lo3Long.unwrap(null));
        Lo3Long lo3b = new Lo3Long(null, null);
        assertNull(Lo3Long.unwrap(lo3b));
    }
}
