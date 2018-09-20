/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Lo3DatumtijdstempelTest {
    Lo3Datumtijdstempel lo3a = new Lo3Datumtijdstempel(2L);
    Lo3Datumtijdstempel lo3b = new Lo3Datumtijdstempel(3L);
    Lo3Datumtijdstempel lo3c = new Lo3Datumtijdstempel("2", null);
    Lo3Datumtijdstempel lo3d = new Lo3Datumtijdstempel(null, null);

    @Test
    public void testCompareTo() throws Exception {

        try {
            lo3a.compareTo(null);
            fail();
        } catch (NullPointerException n) {
            // OK
        }
        assertEquals(-1, lo3a.compareTo(lo3b));
        assertEquals(0, lo3a.compareTo(lo3c));
        assertEquals(1, lo3b.compareTo(lo3c));
        try {
            assertEquals(1, lo3b.compareTo(lo3d));
            fail();
        } catch (NullPointerException n) {
            // OK
        }
        try {
            assertEquals(1, lo3d.compareTo(lo3b));
            fail();
        } catch (NullPointerException n) {
            // OK
        }
    }

    @Test
    public void testGetLongWaarde() throws Exception {
        Lo3Datumtijdstempel lo3c = new Lo3Datumtijdstempel("2", null);
        assertEquals(2, lo3c.getLongWaarde().longValue());
    }

}
