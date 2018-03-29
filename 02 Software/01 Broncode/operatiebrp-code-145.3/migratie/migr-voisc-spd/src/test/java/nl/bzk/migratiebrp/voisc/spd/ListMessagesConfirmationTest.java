/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;

import org.junit.Test;

public class ListMessagesConfirmationTest {

    private ListMessagesConfirmation subject() throws SpdProtocolException {
        return ListMessagesConfirmation.fromOperationItems("1111");
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
    public void toSpdString() throws SpdProtocolException {
        assertEquals("1111", subject().toSpdString());
    }

    @Test
    public void operationCode() throws SpdProtocolException {
        assertEquals(419, subject().operationCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownGetError() throws SpdProtocolException {
        ListMessagesConfirmation.fromOperationItems("0001");
    }

    @Test
    public void getError() throws SpdProtocolException {
        assertEquals(ListMessagesConfirmation.ListError.INVALID_LIMIT, subject().listError());
    }

    @Test
    public void getErrorInvalidMSSequenceNumber() throws SpdProtocolException {
        assertEquals(ListMessagesConfirmation.ListError.INVALID_RANGE, ListMessagesConfirmation.fromOperationItems("1112").listError());
    }
}
