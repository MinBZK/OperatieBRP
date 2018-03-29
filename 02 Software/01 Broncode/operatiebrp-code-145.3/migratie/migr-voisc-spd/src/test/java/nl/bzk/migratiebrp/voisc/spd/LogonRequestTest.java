/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LogonRequestTest {

    @Test(expected = IllegalArgumentException.class)
    public void usernameNull() {
        new LogonRequest(null, "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void usernameTooShort() {
        new LogonRequest("12345", "password");
    }

    @Test
    public void toSpdString() {
        final LogonRequest request = new LogonRequest("1234567", "password");
        assertEquals("1234567password", request.toSpdString());
    }

    @Test
    public void passwordNull() {
        final LogonRequest request = new LogonRequest("1234567", null);
        assertEquals("1234567        ", request.toSpdString());
    }

    @Test
    public void passwordTooShort() {
        final LogonRequest request = new LogonRequest("1234567", "pass");
        assertEquals("1234567    pass", request.toSpdString());
    }

    @Test
    public void length() {
        final LogonRequest request = new LogonRequest("1234567", "password");
        assertEquals(18, request.length());
    }

    @Test
    public void lengthWithoutPassword() {
        final LogonRequest request = new LogonRequest("1234567", null);
        assertEquals(18, request.length());
    }

    @Test
    public void lengthWithoutShortPassword() {
        final LogonRequest request = new LogonRequest("1234567", "pass");
        assertEquals(18, request.length());
    }

    @Test
    public void operationCode() {
        final LogonRequest request = new LogonRequest("1234567", "password");
        assertEquals(SpdConstants.OPC_LOGON_REQUEST, request.operationCode());
    }
}
