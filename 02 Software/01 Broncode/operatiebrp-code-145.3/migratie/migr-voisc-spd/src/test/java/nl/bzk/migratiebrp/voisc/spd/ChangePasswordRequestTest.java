/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import org.junit.Test;

public class ChangePasswordRequestTest {

    @Test
    public void fromOperationItems() throws MailboxServerPasswordException {
        final ChangePasswordRequest request = ChangePasswordRequest.fromOperationItems("12345678password");
        assertEquals("12345678password", request.toSpdString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromOperationItemsWrongLength() throws MailboxServerPasswordException {
        ChangePasswordRequest.fromOperationItems("1234567password");
    }

    @Test
    public void toSpdString() throws MailboxServerPasswordException {
        final ChangePasswordRequest request = new ChangePasswordRequest("12345678", "password");
        assertEquals("12345678password", request.toSpdString());
    }

    @Test(expected = MailboxServerPasswordException.class)
    public void newPasswordNull() throws MailboxServerPasswordException {
        new ChangePasswordRequest("12345678", null);
    }

    @Test(expected = MailboxServerPasswordException.class)
    public void newPasswordTooShort() throws MailboxServerPasswordException {
        new ChangePasswordRequest("12345678", "pass");
    }

    @Test(expected = MailboxServerPasswordException.class)
    public void newPasswordTooLong() throws MailboxServerPasswordException {
        new ChangePasswordRequest("12345678", "password123");
    }

    @Test
    public void length() throws MailboxServerPasswordException {
        final ChangePasswordRequest request = new ChangePasswordRequest("12345678", "password");
        assertEquals(19, request.length());
    }

    @Test(expected = MailboxServerPasswordException.class)
    public void lengthWithShortPassword() throws MailboxServerPasswordException {
        new ChangePasswordRequest("1234567", "passwo");
    }

    @Test
    public void operationCode() throws MailboxServerPasswordException {
        final ChangePasswordRequest request = new ChangePasswordRequest("12345678", "password");
        assertEquals(910, request.operationCode());
    }
}
