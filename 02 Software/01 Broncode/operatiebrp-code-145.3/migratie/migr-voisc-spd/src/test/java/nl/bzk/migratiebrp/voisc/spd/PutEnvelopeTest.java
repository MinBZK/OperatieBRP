/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;

public class PutEnvelopeTest {

    @Test(expected = IllegalArgumentException.class)
    public void originatorOrNameTooLong() {
        new PutEnvelope.Builder().originatorOrName("originator").build();
    }

    @Test
    public void build() {
        final PutEnvelope record = new PutEnvelope.Builder().originatorOrName("name")
                .attention(SpdConstants.Attention.ATTENTION)
                .contentType(SpdConstants.ContentType.P2)
                .priority(SpdConstants.Priority.URGENT)
                .deferredDeliveryTime(Instant.parse("2016-11-03T15:10:30Z"))
                .build();
        assertEquals("   name221611031510Z1", record.toSpdString());
    }

    @Test
    public void fromOperationItems() {
        final PutEnvelope record = PutEnvelope.fromOperationItems("thename221611031510Z1");
        assertThat("should have correct type", record, instanceOf(PutEnvelope.class));
        assertEquals("spdString should be identical", "thename221611031510Z1", record.toSpdString());
    }

    @Test
    public void fromOperationItemsDefaults() {
        final PutEnvelope record = PutEnvelope.fromOperationItems("       20           0");
        assertThat("should have correct type", record, instanceOf(PutEnvelope.class));
        assertEquals("spdString should be identical", "       20           0", record.toSpdString());
    }

    @Test
    public void toSpdString() {
        final PutEnvelope envelope = new PutEnvelope.Builder().build();
        assertEquals("                     ", envelope.toSpdString());
    }

    @Test
    public void length() {
        final PutEnvelope envelope = new PutEnvelope.Builder().build();
        assertEquals(24, envelope.length());
    }

    @Test
    public void lengthField() {
        final PutEnvelope envelope = new PutEnvelope.Builder().build();
        assertEquals("00024", envelope.lengthField());
    }

    @Test
    public void operationCode() {
        final PutEnvelope envelope = new PutEnvelope.Builder().build();
        assertEquals(SpdConstants.OPC_PUT_ENVELOPE, envelope.operationCode());
    }


}
