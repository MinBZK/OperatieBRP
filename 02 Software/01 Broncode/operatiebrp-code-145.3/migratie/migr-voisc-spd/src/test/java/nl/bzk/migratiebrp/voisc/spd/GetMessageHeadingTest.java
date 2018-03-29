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

public class GetMessageHeadingTest {

    private GetMessageHeading subject() {
        return GetMessageHeading.fromOperationItems("112233445566665544332211usernamrecip010");
    }

    @Test
    public void fromOperationItems() {
        assertThat("should have correct type", subject(), instanceOf(GetMessageHeading.class));
        assertEquals("spdString should be identical", "112233445566665544332211usernamrecip010", subject().toSpdString());
    }

    @Test
    public void fromOperationItemsOptional() {
        final GetMessageHeading record = GetMessageHeading.fromOperationItems("112233445566            usernamrecip01 ");
        assertThat("should have correct type", record, instanceOf(GetMessageHeading.class));
        assertEquals("spdString should be identical", "112233445566            usernamrecip010", record.toSpdString());
    }

    @Test
    public void toSpdString() {
        assertEquals("112233445566665544332211usernamrecip010", subject().toSpdString());
    }

    @Test
    public void notificationRequest() {
        final GetMessageHeading record = GetMessageHeading.fromOperationItems("112233445566665544332211usernamrecip011");
        assertEquals("112233445566665544332211usernamrecip011", record.toSpdString());
    }

    @Test
    public void length() {
        assertEquals(42, subject().length());
    }

    @Test
    public void lengthField() {
        assertEquals("00042", subject().lengthField());
    }

    @Test
    public void operationCode() {
        assertEquals(250, subject().operationCode());
    }
}
