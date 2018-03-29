/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PutMessageBodyTest {

    @Test
    public void toSpdString() {
        final PutMessageBody record = new PutMessageBody();
        assertEquals("", record.toSpdString());
    }

    @Test
    public void length() {
        final PutMessageBody record = new PutMessageBody();
        assertEquals(3, record.length());
    }

    @Test
    public void lengthField() {
        final PutMessageBody record = new PutMessageBody();
        assertEquals("00003", record.lengthField());
    }

    @Test
    public void toSpdStringWithBody() {
        final PutMessageBody record = new PutMessageBody("this is the message body");
        assertEquals("this is the message body", record.toSpdString());
    }

    @Test
    public void lengthWithBody() {
        final PutMessageBody record = new PutMessageBody("this is the message body");
        assertEquals(27, record.length());
    }

    @Test
    public void lengthFieldWithBody() {
        final PutMessageBody record = new PutMessageBody("this is the message body");
        assertEquals("00027", record.lengthField());
    }

    @Test
    public void operationCode() {
        final PutMessageBody record = new PutMessageBody();
        assertEquals(180, record.operationCode());
    }
}
