/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;
import nl.bzk.migratiebrp.voisc.spd.exception.EmptyOperationException;
import nl.bzk.migratiebrp.voisc.spd.exception.IllegalLengthException;
import nl.bzk.migratiebrp.voisc.spd.exception.ParseException;
import nl.bzk.migratiebrp.voisc.spd.exception.UnknownOpcodeException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

import org.junit.Test;

public class OperationParserTest {

    @Test(expected = EmptyOperationException.class)
    public void emptyByteArray() throws ParseException, VoaException {
        OperationParser.parse(new byte[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullByteArray() throws ParseException, VoaException {
        OperationParser.parse(null);
    }

    @Test(expected = ParseException.class)
    public void illegalMessage() throws ParseException, VoaException {
        final String message = "abcd";
        OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
    }

    @Test(expected = ParseException.class)
    public void missingTerminator() throws ParseException, VoaException {
        final String message = "00003" + "990";
        OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
    }

    @Test(expected = IllegalLengthException.class)
    public void wrongLengthTooShort() throws ParseException, VoaException {
        final String message = "00001" + "909" + "0000000000000000" + "00000";
        OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
    }

    @Test(expected = IllegalLengthException.class)
    public void wrongLength() throws ParseException, VoaException {
        final String message = "00019" + "900" + "user123password" + "00000";
        OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
    }

    @Test
    public void wrongLengthLongerMessage() throws ParseException, VoaException {
        final String message = "00019" + "900" + "user123password" + "00000";
        try {
            OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        } catch (final IllegalLengthException ex) {
            assertEquals(18, ex.getActualLength());
            assertEquals(19, ex.getExpectedLength());
        }
    }

    @Test(expected = ParseException.class)
    public void wrongLengthShorter() throws ParseException, VoaException {
        final String message = "00017" + "900" + "user123password" + "00000";
        OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
    }

    @Test(expected = UnknownOpcodeException.class)
    public void unknownOpcode() throws ParseException, VoaException {
        final String message = "00018" + "111" + "user123password" + "00000";
        OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
    }

    @Test(expected = UnknownOpcodeException.class)
    public void illegalOpcode() throws ParseException, VoaException {
        final String message = "0001899aUser123password" + "00000";
        OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
    }

    @Test
    public void testLogonRequest() throws ParseException, VoaException {
        final String message = "00018" + "900" + "user123password" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals(true, operation.containsRecordOfType(LogonRequest.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testLogonConfirmation() throws ParseException, VoaException {
        final String message = "00038" + "909" + "1033MessagentrySystemManagerMessage" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals(true, operation.containsRecordOfType(LogonConfirmation.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testLogoffRequest() throws ParseException, VoaException {
        final String message = "00003" + "990" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals(true, operation.containsRecordOfType(LogoffRequest.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testLogoffConfirmation() throws ParseException, VoaException {
        final String message = "00007" + "999" + "0000" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals(true, operation.containsRecordOfType(LogoffConfirmation.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testPutEnvelope() throws ParseException, VoaException {
        final String putEnvelope = "00024" + "120" + "       20           0";
        final String putMessageHeading = "00045" + "150" + "112233445566665544332211       00176543210";
        final String putMessageBody = "00017" + "180" + "dit is de body";
        final String message = putEnvelope + putMessageHeading + putMessageBody + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain PutEnvelope", true, operation.containsRecordOfType(PutEnvelope.class));
        assertEquals(3, operation.records().size());
    }

    @Test
    public void testPutMessageHeading() throws ParseException, VoaException {
        final String message = "00045" + "150" + "112233445566665544332211       0017654321" + "000000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain MessageHeading", true, operation.containsRecordOfType(PutMessageHeading.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testPutMessageBody() throws ParseException, VoaException {
        final String message = "00017" + "180" + "dit is de body" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain MessageBody", true, operation.containsRecordOfType(PutMessageBody.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testPutMessage() throws ParseException, VoaException {
        final String message = "00024" + "120" + "       20           000045150112233445566665544332211       0017654321000017180dit is de body" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain PutEnvelope", true, operation.containsRecordOfType(PutEnvelope.class));
        assertEquals("Operation should contain MessageHeading", true, operation.containsRecordOfType(PutMessageHeading.class));
        assertEquals("Operation should contain MessageBody", true, operation.containsRecordOfType(PutMessageBody.class));
        assertEquals("Operation should contain 3 records", 3, operation.records().size());
    }

    @Test
    public void testPutMessageConfirmation() throws ParseException, VoaException {
        final String message = "00039" + "190" + "00000000000011611031510Z123456789000" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain PutMessageConfirmation", true, operation.containsRecordOfType(PutMessageConfirmation.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testMSEntry() throws ParseException, VoaException {
        final String message = "00012" + "210" + "123456789" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain MSEntry", true, operation.containsRecordOfType(MSEntry.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testGetEnvelope() throws ParseException, VoaException {
        final String message = "00041" + "220" + "1234567" + "2" + "1" + "0408121045Z" + "0408121045Z" + "1831010" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain GetEnvelope", true, operation.containsRecordOfType(GetEnvelope.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testGetMessage() throws ParseException, VoaException {
        final String message = "00012" + "200" + "123456789" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain GetMessage", true, operation.containsRecordOfType(GetMessage.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testGetMessageBody() throws ParseException, VoaException {
        final String message = "00027" + "280" + "this is the message body" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain GetMessageBody", true, operation.containsRecordOfType(GetMessageBody.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testGetMessageHeading() throws ParseException, VoaException {
        final String message = "00042" + "250" + "112233445566665544332211usernamrecip010" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain GetMessageHeading", true, operation.containsRecordOfType(GetMessageHeading.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testGetMessageConfirmation() throws ParseException, VoaException {
        final String message = "00007" + "290" + "1072" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain GetMessageConfirmation", true, operation.containsRecordOfType(GetMessageConfirmation.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testGetMessageResultWithMessageResult() throws ParseException, VoaException {
        final String msEntry = "00012" + "210" + "123456789";
        final String getEnvelope = "00041" + "220" + "1234567" + "2" + "1" + "0408121045Z" + "0408121045Z" + "1831010";
        final String messageHeading = "00042" + "250" + "112233445566665544332211usernamrecip010";
        final String messageBody = "00027" + "280" + "this is the message body";
        final String messageResult = msEntry + getEnvelope + messageHeading + messageBody + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(messageResult));
        assertEquals("Operation should contain MSEntry", true, operation.containsRecordOfType(MSEntry.class));
        assertEquals("Operation should contain GetEnvelope", true, operation.containsRecordOfType(GetEnvelope.class));
        assertEquals("Operation should contain GetMessageHeading", true, operation.containsRecordOfType(GetMessageHeading.class));
        assertEquals("Operation should contain GetMessageBody", true, operation.containsRecordOfType(GetMessageBody.class));
        assertEquals(4, operation.records().size());
    }

    @Test
    public void testGetMessageResultWithStatusResult() throws ParseException, VoaException {
        final String msEntry = "00012" + "210" + "123456789";
        final String getEnvelope = "00041" + "220" + "1234567" + "2" + "1" + "0408121045Z" + "0408121045Z" + "1831010";
        final String statusReport = "00024" + "270" + "1831010" + "1" + "YY0000000004" + "0";
        final String messageResult = msEntry + getEnvelope + statusReport + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(messageResult));
        assertEquals("Operation should contain MSEntry", true, operation.containsRecordOfType(MSEntry.class));
        assertEquals("Operation should contain GetEnvelope", true, operation.containsRecordOfType(GetEnvelope.class));
        assertEquals("Operation should contain StatusReport", true, operation.containsRecordOfType(StatusReport.class));
        assertEquals(3, operation.records().size());
    }

    @Test
    public void testGetMessageResultWithDeliveryReport() throws ParseException, VoaException {
        final String msEntry = "00012" + "210" + "123456789";
        final String deliveryReport = "00048" + "260" + "0408231236Z" + "081200200" + "001" + "1234567" + "           " + "0001";
        final String messageResult = msEntry + deliveryReport + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(messageResult));
        assertEquals("Operation should contain MSEntry", true, operation.containsRecordOfType(MSEntry.class));
        assertEquals("Operation should contain DeliveryReport", true, operation.containsRecordOfType(DeliveryReport.class));
        assertEquals(2, operation.records().size());
    }

    @Test
    public void testStatusReport() throws ParseException, VoaException {
        final String message = "00024" + "270" + "1831010" + "1" + "YY0000000004" + "0" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain StatusReport", true, operation.containsRecordOfType(StatusReport.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testDeliveryReport() throws ParseException, VoaException {
        final String message = "00048" + "260" + "0408231236Z" + "081200200" + "001" + "1234567" + "           " + "0001" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain DeliveryReport", true, operation.containsRecordOfType(DeliveryReport.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testListMessages() throws ParseException, VoaException {
        final String message = "00028" + "400" + "0500012000000000         " + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain DeliveryReport", true, operation.containsRecordOfType(ListMessages.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testListResult() throws ParseException, VoaException {
        final String message = "00012" + "410" + "123456789" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain DeliveryReport", true, operation.containsRecordOfType(ListResult.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testMSList() throws ParseException, VoaException {
        final String message = "00066" + "411" + "00002123456789001611031510Zorigina234567890221612031510Zusernam" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain DeliveryReport", true, operation.containsRecordOfType(MSList.class));
        assertEquals(1, operation.records().size());
    }

    @Test
    public void testListMessagesConfirmation() throws ParseException, VoaException {
        final String message = "00007" + "419" + "1111" + "00000";
        final Operation operation = OperationParser.parse(GBACharacterSet.convertTeletexStringToByteArray(message));
        assertEquals("Operation should contain DeliveryReport", true, operation.containsRecordOfType(ListMessagesConfirmation.class));
        assertEquals(1, operation.records().size());
    }
}
