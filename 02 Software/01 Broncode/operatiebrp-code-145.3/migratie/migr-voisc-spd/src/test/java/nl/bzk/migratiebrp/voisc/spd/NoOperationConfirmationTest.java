/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.junit.Test;

public class NoOperationConfirmationTest {

    private NoOperationConfirmation subject() throws SpdProtocolException {
        return NoOperationConfirmation.fromOperationItems("1205");
    }

    @Test
    public void length() throws SpdProtocolException {
        assertEquals(7, subject().length());
    }

    @Test
    public void lengthField() throws SpdProtocolException {
        assertEquals("00007", subject().lengthField());
    }

    @Test
    public void operationCodeField() throws SpdProtocolException {
        assertEquals("009", subject().operationCodeField());
    }

    @Test
    public void toSpdString() throws SpdProtocolException {
        assertEquals("1205", subject().toSpdString());
    }

    @Test
    public void operationCode() throws SpdProtocolException {
        assertEquals(9, subject().operationCode());
    }

    @Test(expected = SpdProtocolException.class)
    public void wrongLength() throws SpdProtocolException {
        NoOperationConfirmation.fromOperationItems("1");
    }
}
