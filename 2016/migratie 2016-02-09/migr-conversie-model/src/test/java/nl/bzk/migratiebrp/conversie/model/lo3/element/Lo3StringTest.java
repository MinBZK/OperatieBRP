/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class Lo3StringTest {

    @Test
    public void testWrap() throws Exception {
        Lo3String result = Lo3String.wrap("tekst");
        assertEquals("tekst", result.getWaarde());
        assertNull(result.getOnderzoek());
        assertNull(Lo3String.wrap(null));
    }

    @Test
    public void testUnwrap() throws Exception {
        Lo3String start = new Lo3String("tekst", new Lo3Onderzoek(new Lo3Integer(1), new Lo3Datum(99999911), null));
        assertEquals("tekst", Lo3String.unwrap(start));
        assertNull(Lo3String.unwrap(null));
    }
}
