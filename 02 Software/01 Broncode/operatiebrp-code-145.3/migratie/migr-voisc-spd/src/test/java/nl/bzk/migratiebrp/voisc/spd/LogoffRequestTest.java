/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LogoffRequestTest {

    @Test
    public void length() {
        assertEquals(3, new LogoffRequest().length());
    }

    @Test
    public void lengthField() {
        assertEquals("00003", new LogoffRequest().lengthField());
    }

    @Test
    public void toSpdString() {
        assertEquals("", new LogoffRequest().toSpdString());
    }

    @Test
    public void operationCode() {
        assertEquals(SpdConstants.OPC_LOGOFF_REQUEST, new LogoffRequest().operationCode());
    }
}
