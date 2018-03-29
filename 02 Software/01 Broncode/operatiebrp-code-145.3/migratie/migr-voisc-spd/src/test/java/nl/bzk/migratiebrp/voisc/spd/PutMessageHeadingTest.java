/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.voisc.database.entities.Bericht;

import org.junit.Test;

public class PutMessageHeadingTest {

    private Bericht bericht() {
        final Bericht bericht = new Bericht();
        bericht.setMessageId("112233445566");
        bericht.setCorrelationId("665544332211");
        bericht.setRecipient("7654321");
        return bericht;
    }

    @Test(expected = IllegalArgumentException.class)
    public void messageIdMandatory() {
        final Bericht bericht = new Bericht();
        bericht.setCorrelationId("665544332211");
        PutMessageHeading.fromBericht(bericht);
    }

    @Test(expected = IllegalArgumentException.class)
    public void recipientOrNameMandatory() {
        final Bericht bericht = new Bericht();
        bericht.setMessageId("112233445566");
        PutMessageHeading.fromBericht(bericht);
    }

    @Test
    public void fromOperationItems() {
        final PutMessageHeading record = PutMessageHeading.fromOperationItems("112233445566665544332211       00176543210");
        assertThat("should have correct type", record, instanceOf(PutMessageHeading.class));
        assertEquals("spdString should be identical", "112233445566665544332211       00176543210", record.toSpdString());
    }

    @Test
    public void fromOperationItemsOptional() {
        final PutMessageHeading record = PutMessageHeading.fromOperationItems("112233445566                   00176543210");
        assertThat("should have correct type", record, instanceOf(PutMessageHeading.class));
        assertEquals("spdString should be identical", "112233445566                   00176543210", record.toSpdString());
    }

    @Test
    public void toSpdString() {
        final PutMessageHeading record = PutMessageHeading.fromBericht(bericht());
        assertEquals("112233445566665544332211       00176543210", record.toSpdString());
    }

    @Test
    public void notificationRequest() {
        final Bericht bericht = bericht();
        bericht.setRequestNonReceiptNotification(true);
        final PutMessageHeading record = PutMessageHeading.fromBericht(bericht);
        assertEquals("112233445566665544332211       00176543211", record.toSpdString());
    }

    @Test
    public void length() {
        final PutMessageHeading record = PutMessageHeading.fromBericht(bericht());
        assertEquals(45, record.length());
    }

    @Test
    public void lengthField() {
        final PutMessageHeading record = PutMessageHeading.fromBericht(bericht());
        assertEquals("00045", record.lengthField());
    }

    @Test
    public void operationCode() {
        final PutMessageHeading record = PutMessageHeading.fromBericht(bericht());
        assertEquals(150, record.operationCode());
    }
}
