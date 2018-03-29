/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;
import nl.bzk.migratiebrp.voisc.spd.exception.EmptyOperationException;
import nl.bzk.migratiebrp.voisc.spd.exception.IllegalLengthException;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.ParseException;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.UnknownOpcodeException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.springframework.util.Assert;


/**
 * Parses byte arrays into Operation objects.
 */
public final class OperationParser {

    private static final Pattern RECORD_PATTERN = Pattern.compile("^(.{5})(.{3})(.*)$");
    private static final Pattern TERMINATION_PATTERN = Pattern.compile("^.*(00000)$");
    private static final Pattern LENGTH_PATTERN = Pattern.compile("^(.{5}).*$");

    private static final Map<Integer, Class<? extends OperationRecord>> OPCODE_RECORD_MAPPING;

    static {
        final Map<Integer, Class<? extends OperationRecord>> map = new HashMap<>();
        map.put(SpdConstants.OPC_LOGON_REQUEST, LogonRequest.class);
        map.put(SpdConstants.OPC_LOGON_CONFIRMATION, LogonConfirmation.class);
        map.put(SpdConstants.OPC_LOGOFF_REQUEST, LogoffRequest.class);
        map.put(SpdConstants.OPC_LOGOFF_CONFIRMATION, LogoffConfirmation.class);
        map.put(SpdConstants.OPC_CHANGE_PASSWORD_REQUEST, ChangePasswordRequest.class);
        map.put(SpdConstants.OPC_CHANGE_PASSWORD_CONFIRMATION, ChangePasswordConfirmation.class);
        map.put(SpdConstants.OPC_PUT_ENVELOPE, PutEnvelope.class);
        map.put(SpdConstants.OPC_PUT_MESSAGE_HEADING, PutMessageHeading.class);
        map.put(SpdConstants.OPC_PUT_MESSAGE_BODY, PutMessageBody.class);
        map.put(SpdConstants.OPC_PUT_MESSAGE_CONFIRMATION, PutMessageConfirmation.class);
        map.put(SpdConstants.OPC_GET_MESSAGE, GetMessage.class);
        map.put(SpdConstants.OPC_GET_MESSAGE_BODY, GetMessageBody.class);
        map.put(SpdConstants.OPC_GET_MESSAGE_HEADING, GetMessageHeading.class);
        map.put(SpdConstants.OPC_GET_MESSAGE_CONFIRMATION, GetMessageConfirmation.class);
        map.put(SpdConstants.OPC_GET_ENVELOP, GetEnvelope.class);
        map.put(SpdConstants.OPC_MSENTRY, MSEntry.class);
        map.put(SpdConstants.OPC_STATUS_REPORT, StatusReport.class);
        map.put(SpdConstants.OPC_DELIVERY_REPORT, DeliveryReport.class);
        map.put(SpdConstants.OPC_LIST_MESSAGES, ListMessages.class);
        map.put(SpdConstants.OPC_LIST_RESULT, ListResult.class);
        map.put(SpdConstants.OPC_MS_LIST, MSList.class);
        map.put(SpdConstants.OPC_LIST_MESSAGES_CONFIRMATION, ListMessagesConfirmation.class);
        OPCODE_RECORD_MAPPING = new HashMap<>(map);
    }

    private OperationParser() {
    }

    /**
     * Parses a byte array into an operation.
     * @param operation byte array describing the operation
     * @return the parsed Operation
     * @throws ParseException parse exception
     * @throws VoaException voa exception
     */
    public static Operation parse(final byte[] operation) throws VoaException {
        Assert.notNull(operation, "De operation mag niet null zijn.");
        if (operation.length <= 0) {
            throw new EmptyOperationException();
        }

        final String converted = GBACharacterSet.convertTeletexByteArrayToString(operation);

        final Operation.Builder builder = new Operation.Builder();
        String rest = removeTerminator(converted);

        boolean stop = false;
        while (!stop) {
            final int length = parseLength(rest);
            final int end = length + SpdConstants.LENGTH_LENGTH;
            final String record = rest.substring(0, end);
            rest = rest.substring(end);
            builder.add(parseRecord(record));

            if (rest.isEmpty()) {
                stop = true;
            }
        }

        return builder.build();
    }

    private static String removeTerminator(final String input) {
        final Matcher terminatorMatcher = TERMINATION_PATTERN.matcher(input);
        if (terminatorMatcher.matches()) {
            return input.substring(0, input.length() - terminatorMatcher.group(1).length());
        } else {
            throw new ParseException("No terminator found.");
        }
    }

    private static int parseLength(final String input) throws SpdProtocolException {
        final Matcher lengthMatcher = LENGTH_PATTERN.matcher(input);
        if (lengthMatcher.matches()) {
            final int length;
            try {
                length = Integer.parseInt(lengthMatcher.group(1));
            } catch (final NumberFormatException ex) {
                throw new SpdProtocolException(MessagesCodes.ERRMSG_VOSPG_SPD_NOT_NUMERIC_FIELD, new Object[]{"length", lengthMatcher.group(1)}, ex);
            }
            if (length < SpdConstants.OPCODE_LENGTH || length + SpdConstants.LENGTH_LENGTH > input.length()) {
                throw new IllegalLengthException(length, input.length() - SpdConstants.LENGTH_LENGTH);
            }
            return length;
        } else {
            throw new ParseException(String.format("No length field found for input: %s", input));
        }
    }

    private static OperationRecord parseRecord(final String input) throws SpdProtocolException {
        final Matcher matcher = RECORD_PATTERN.matcher(input);
        if (matcher.matches()) {
            final int length = Integer.parseInt(matcher.group(1));
            final String operationCode = matcher.group(2);
            final String operationItems = matcher.group(3);

            verifyLength(length, operationCode, operationItems);

            return instantiateRecordByOpcode(operationCode, operationItems);
        } else {
            throw new ParseException("Operation record could not be parsed.");
        }
    }

    private static int parseOperationCode(final String opcodeField) {
        try {
            return Integer.parseInt(opcodeField);
        } catch (final NumberFormatException ex) {
            throw new UnknownOpcodeException(opcodeField, ex);
        }
    }

    private static void verifyLength(int expectedLength, final String operationCode, final String operationItems) {
        if (expectedLength != operationCode.length() + operationItems.length()) {
            throw new IllegalLengthException(expectedLength, operationCode.length() + operationItems.length());
        }
    }

    private static OperationRecord instantiateRecordByOpcode(final String operationCode, final String operationItems) throws SpdProtocolException {
        final OperationRecord record;
        final int opcode = parseOperationCode(operationCode);

        final Class<? extends OperationRecord> operationRecordClass = getClassByOpcode(opcode);
        try {
            if (operationItems.isEmpty()) {
                record = operationRecordClass.newInstance();
            } else {
                record = operationRecordClass.cast(operationRecordClass.getMethod("fromOperationItems", String.class).invoke(null, operationItems));
            }
        } catch (final NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new UnknownOpcodeException(operationCode, e);
        } catch (final InvocationTargetException e) {
            if (e.getCause() instanceof SpdProtocolException) {
                throw (SpdProtocolException) e.getCause();
            } else if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            } else {
                throw new UnknownOpcodeException(operationCode, e);
            }
        }

        return record;
    }

    private static Class<? extends OperationRecord> getClassByOpcode(final int opcode) {
        if (OPCODE_RECORD_MAPPING.containsKey(opcode)) {
            return OPCODE_RECORD_MAPPING.get(opcode);
        } else {
            throw new UnknownOpcodeException(String.valueOf(opcode));
        }
    }
}
