/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LogonConfirmationTest {

    private LogonConfirmation subject() {
        return LogonConfirmation.fromOperationItems("0000");
    }

    @Test
    public void length() {
        assertEquals(7, subject().length());
    }

    @Test
    public void lengthField() {
        assertEquals("00007", subject().lengthField());
    }

    @Test
    public void toSpdString() {
        assertEquals("0000", subject().toSpdString());
    }

    @Test
    public void operationCode() {
        assertEquals(SpdConstants.OPC_LOGON_CONFIRMATION, subject().operationCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownLogonResult() {
        LogonConfirmation.fromOperationItems("0001");
    }

    @Test
    public void isLoggedOn() {
        assertEquals(true, subject().isLoggedOn());
    }

    @Test
    public void isNotLoggedOn() {
        assertEquals(false, LogonConfirmation.fromOperationItems("1033").isLoggedOn());
    }

    @Test
    public void logonResult() {
        assertEquals(LogonConfirmation.LogonResult.OK, subject().result());
    }

    @Test
    public void logonResultSecurityFailure() {
        assertEquals(LogonConfirmation.LogonResult.SECURITY_FAILURE, LogonConfirmation.fromOperationItems("1033").result());
    }
}
