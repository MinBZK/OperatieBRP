/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.brp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Unit test voor de {@link BerichtException} class.
 */
public class BerichtExceptionTest {

    /**
     * Test de constructor en de getter.
     */
    @Test
    public void testBerichtException() {
        BerichtException e = new BerichtException(2L);
        assertEquals(2L, e.getBerichtId());
    }

}
