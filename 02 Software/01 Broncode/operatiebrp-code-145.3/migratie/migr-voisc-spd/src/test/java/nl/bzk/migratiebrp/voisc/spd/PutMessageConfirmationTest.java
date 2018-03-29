/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;

public class PutMessageConfirmationTest {

    private PutMessageConfirmation subject() {
        return new PutMessageConfirmation.Builder(PutMessageConfirmation.PutMessageResult.OK).dispatchSequenceNumber(1)
                .submissionTime(Instant.parse("2016-11-03T15:10:30Z"))
                .messageId("123456789000")
                .build();
    }

    @Test
    public void length() {
        assertEquals(39, subject().length());
    }

    @Test
    public void lengthField() {
        assertEquals("00039", subject().lengthField());
    }

    @Test
    public void toSpdString() {
        assertEquals("00000000000011611031510Z123456789000", subject().toSpdString());
    }

    @Test
    public void operationCode() {
        assertEquals(SpdConstants.OPC_PUT_MESSAGE_CONFIRMATION, subject().operationCode());
    }

    @Test
    public void lengthForError() {
        assertEquals(7, new PutMessageConfirmation.Builder(PutMessageConfirmation.PutMessageResult.MISSING_ELEMENTS).build().length());
    }

    @Test
    public void toSpdStringForError() {
        assertEquals("1005", new PutMessageConfirmation.Builder(PutMessageConfirmation.PutMessageResult.MISSING_ELEMENTS).build().toSpdString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingFieldsForOk() {
        new PutMessageConfirmation.Builder(PutMessageConfirmation.PutMessageResult.OK).build();
    }

    @Test
    public void fromOperationItemsOk() {
        final PutMessageConfirmation confirmation = PutMessageConfirmation.fromOperationItems("00001234567891611040900Z112233445566");
        assertThat("Should parse string into PutMessageConfirmation", confirmation, instanceOf(PutMessageConfirmation.class));
        assertEquals("Should be OK", true, confirmation.isOk());
        assertThat("DispatchSequenceNumber should have correct value", confirmation.getDispatchSequenceNumber(), is(equalTo(123456789)));
        assertThat(
                "SubmissionTime should have correct value",
                confirmation.getSubmissionTime(),
                is(equalTo(SpdTimeConverter.convertSpdTimeStringToInstant("1611040900Z"))));
        assertThat("MessageId should have correct value", confirmation.getMessageId(), is(equalTo("112233445566")));
    }

    @Test
    public void fromOperationItemsNotOk() {
        assertThat(PutMessageConfirmation.fromOperationItems("1004"), instanceOf(PutMessageConfirmation.class));
    }

    @Test(expected = IllegalStateException.class)
    public void fromOperationItemsOkWithoutRest() {
        PutMessageConfirmation.fromOperationItems("0000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromOperationItemsIllegal() {
        PutMessageConfirmation.fromOperationItems("dit is niet goed");
    }
}
