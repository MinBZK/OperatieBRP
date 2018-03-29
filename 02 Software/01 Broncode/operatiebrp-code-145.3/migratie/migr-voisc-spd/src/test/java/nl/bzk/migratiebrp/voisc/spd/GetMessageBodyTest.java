/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GetMessageBodyTest {

    @Test
    public void toSpdString() {
        final GetMessageBody record = new GetMessageBody();
        assertEquals("", record.toSpdString());
    }

    @Test
    public void length() {
        final GetMessageBody record = new GetMessageBody();
        assertEquals(3, record.length());
    }

    @Test
    public void lengthField() {
        final GetMessageBody record = new GetMessageBody();
        assertEquals("00003", record.lengthField());
    }

    @Test
    public void toSpdStringWithBody() {
        final GetMessageBody record = new GetMessageBody("this is the message body");
        assertEquals("this is the message body", record.toSpdString());
    }

    @Test
    public void lengthWithBody() {
        final GetMessageBody record = new GetMessageBody("this is the message body");
        assertEquals(27, record.length());
    }

    @Test
    public void lengthFieldWithBody() {
        final GetMessageBody record = new GetMessageBody("this is the message body");
        assertEquals("00027", record.lengthField());
    }

    @Test
    public void operationCode() {
        final GetMessageBody record = new GetMessageBody();
        assertEquals(280, record.operationCode());
    }
}
