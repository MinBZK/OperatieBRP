/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Lo3AanduidingEuropeesKiesrechtTest {

    @Test
    public void testGetIntegerWaarde() throws Exception {
        Lo3AanduidingEuropeesKiesrecht lo3 = new Lo3AanduidingEuropeesKiesrecht(2);
        assertEquals(2, lo3.getIntegerWaarde().intValue());
    }
}
