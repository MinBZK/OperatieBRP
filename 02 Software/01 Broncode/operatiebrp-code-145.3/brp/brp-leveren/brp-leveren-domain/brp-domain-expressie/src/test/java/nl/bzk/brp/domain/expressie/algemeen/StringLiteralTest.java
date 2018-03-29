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
import nl.bzk.brp.domain.expressie.StringLiteral;
import org.junit.Test;

public class StringLiteralTest {

    private final String waarde = "string/literal.literal";
    private final StringLiteral stringLiteral = new StringLiteral(waarde);

    @Test
    public void getType() throws Exception {
        assertEquals(ExpressieType.STRING, stringLiteral.getType(new Context()));
    }

    @Test
    public void alsString() throws Exception {
        assertEquals(waarde, stringLiteral.alsString());
    }

    @Test
    public void getStringWaardeAlsReguliereExpressie() throws Exception {
        assertEquals("string/literal\\.literal" , stringLiteral.getStringWaardeAlsReguliereExpressie());
    }

    @Test
    public void equals() throws Exception {
        final StringLiteral stringLiteral = new StringLiteral(waarde);
        final StringLiteral stringLiteral1 = new StringLiteral(waarde);
        final StringLiteral stringLiteral2 = new StringLiteral(waarde.concat(waarde));

        assertTrue(stringLiteral.equals(stringLiteral));
        assertTrue(stringLiteral.equals(stringLiteral1));
        assertFalse(stringLiteral.equals(stringLiteral2));
        assertFalse(stringLiteral.equals(null));
        assertFalse(stringLiteral.equals(new GetalLiteral(12L)));
    }

}