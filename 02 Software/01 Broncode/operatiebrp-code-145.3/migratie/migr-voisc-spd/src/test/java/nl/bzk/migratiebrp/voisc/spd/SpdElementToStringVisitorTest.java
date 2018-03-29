/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SpdElementToStringVisitorTest {

    @Test
    public void testLogonRequest() {
        SpdElementToStringVisitor visitor = new SpdElementToStringVisitor();
        LogonRequest logonRequest = new LogonRequest("user123", "password");
        assertEquals("00018900user123password", visitor.visit(logonRequest).toString());
    }

    @Test
    public void testLogonRequestWithoutPassword() {
        SpdElementToStringVisitor visitor = new SpdElementToStringVisitor();
        LogonRequest logonRequest = new LogonRequest("user123", null);
        assertEquals("00018900user123        ", visitor.visit(logonRequest).toString());
    }

    @Test
    public void testLogonRequestWithShortPassword() {
        SpdElementToStringVisitor visitor = new SpdElementToStringVisitor();
        LogonRequest logonRequest = new LogonRequest("user123", "pass");
        assertEquals("00018900user123    pass", visitor.visit(logonRequest).toString());
    }

    @Test
    public void testLogonOperation() {
        SpdElementToStringVisitor visitor = new SpdElementToStringVisitor();
        LogonRequest logonRequest = new LogonRequest("user123", "password");
        Operation operation = new Operation.Builder().add(logonRequest).build();
        assertEquals("00018900user123password00000", visitor.visit(operation).toString());
    }
}
