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

public class GetEnvelopeTest {

    private GetEnvelope subject() {
        return new GetEnvelope.Builder().originatorOrName("name")
                .contentType(SpdConstants.ContentType.P2)
                .priority(SpdConstants.Priority.URGENT)
                .deliveryTime(Instant.parse("2016-11-03T15:10:30Z"))
                .submissionTime(Instant.parse("2016-12-03T15:10:30Z"))
                .actualRecipientOrName("recip01")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void originatorOrNameTooLong() {
        new GetEnvelope.Builder().originatorOrName("originator").build();
    }

    @Test
    public void fromOperationItems() {
        final GetEnvelope record = GetEnvelope.fromOperationItems("thename221611031510Z1612031510Zrecip01");
        assertThat("should have correct type", record, instanceOf(GetEnvelope.class));
        assertEquals("spdString should be identical", "thename221611031510Z1612031510Zrecip01", record.toSpdString());
    }

    @Test
    public void toSpdString() {
        assertEquals("   name221611031510Z1612031510Zrecip01", subject().toSpdString());
    }

    @Test
    public void length() {
        assertEquals(41, subject().length());
    }

    @Test
    public void lengthField() {
        assertEquals("00041", subject().lengthField());
    }

    @Test
    public void operationCode() {
        assertEquals(SpdConstants.OPC_GET_ENVELOP, subject().operationCode());
    }
}
