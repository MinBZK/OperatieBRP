/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OngeldigePersoonslijstExceptionTest {

    private OngeldigePersoonslijstException e = new OngeldigePersoonslijstException("error");
    private OngeldigePersoonslijstException e2 = new OngeldigePersoonslijstException("fout");

    @Test
    public void testEquals() throws Exception {
        assertFalse(e.equals(null));
        assertFalse(e.equals(new NullPointerException()));
        assertTrue(e.equals(e2));
    }

    @Test
    public void testHashCode() throws Exception {
        assertTrue(0 < e.hashCode());
    }

    @Test
    public void testGetMessage() throws Exception {
        assertEquals("error", e.getMessage());
    }
}
