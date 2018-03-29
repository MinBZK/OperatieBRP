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

public class ListMessagesTest {

    private ListMessages subject() {
        return new ListMessages.Builder().status("001").limit(50).priority(SpdConstants.Priority.URGENT).fromMSSequenceNumber(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void statusTooLong() {
        new ListMessages.Builder().status("toolong").build();
    }

    @Test
    public void fromOperationItems() {
        final ListMessages record = ListMessages.fromOperationItems("0500012000000000         ");
        assertThat("should have correct type", record, instanceOf(ListMessages.class));
        assertEquals("spdString should be identical", "0500012000000000         ", record.toSpdString());
    }

    @Test
    public void fromOperationItemsOptional() {
        final ListMessages record = ListMessages.fromOperationItems("");
        assertEquals("spdString should be identical", "       000000000         ", record.toSpdString());
    }

    @Test
    public void fromOperationItemsEmpty() {
        final ListMessages record = ListMessages.fromOperationItems("                         ");
        assertEquals("spdString should be identical", "      0000000000         ", record.toSpdString());
    }

    @Test
    public void fromOperationItemsSmallerLimit() {
        final ListMessages record = ListMessages.fromOperationItems("001                      ");
        assertEquals("spdString should be identical", "040   0000000000         ", record.toSpdString());
    }

    @Test
    public void fromOperationItemsLargerLimit() {
        final ListMessages record = ListMessages.fromOperationItems("200                      ");
        assertEquals("spdString should be identical", "171   0000000000         ", record.toSpdString());
    }

    @Test
    public void toSpdString() {
        assertEquals("0500012000000000         ", subject().toSpdString());
    }

    @Test
    public void length() {
        assertEquals(28, subject().length());
    }

    @Test
    public void lengthField() {
        assertEquals("00028", subject().lengthField());
    }

    @Test
    public void operationCode() {
        assertEquals(400, subject().operationCode());
    }
}
