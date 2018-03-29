/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GetMessageTest {
    @Test
    public void fromOperationItems() {
        final GetMessage record = GetMessage.fromOperationItems("123456789");
        assertThat("should have correct type", record, instanceOf(GetMessage.class));
        assertEquals("spdString should be identical", "123456789", record.toSpdString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void msSequenceNumber() {
        new GetMessage(0);
    }

    @Test
    public void length() {
        assertEquals(12, new GetMessage(123456789).length());
    }

    @Test
    public void lengthField() {
        assertEquals("00012", new GetMessage(123456789).lengthField());
    }

    @Test
    public void toSpdString() {
        assertEquals("123456789", new GetMessage(123456789).toSpdString());
    }

    @Test
    public void operationCode() {
        assertEquals(200, new GetMessage(123456789).operationCode());
    }
}
