/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.algemeen;

import static org.junit.Assert.*;

import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import org.junit.Test;

public class GetalLiteralTest {

    private final GetalLiteral getalLiteral = new GetalLiteral(131L);

    @Test
    public void getType() throws Exception {
        assertEquals(ExpressieType.GETAL, getalLiteral.getType(new Context()));
    }

    @Test
    public void getWaarde() throws Exception {
        assertEquals(131l, getalLiteral.getWaarde());
    }

    @Test
    public void alsString() throws Exception {
        assertEquals("131", getalLiteral.toString());
    }

    @Test
    public void equals() throws Exception {
        assertTrue(getalLiteral.equals(new GetalLiteral(131L)));
        assertFalse(getalLiteral.equals(new GetalLiteral(1313L)));
        assertFalse(getalLiteral.equals(null));
        assertFalse(getalLiteral.equals(new Integer(131)));
    }

}