/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.Assert;

/**
 * PutMessageConfirmation: Length [5] Operationcode [3] PutResult [4] DispatchSequenceNumber [9] SubmissionTime [11]
 * MessageID [12].
 */
public final class PutMessageConfirmation implements OperationRecord {

    private static final Pattern PATTERN = Pattern.compile("^(.{4})((.{9})(.{11})(.{12}))?$");
    private static final int RESULT_GROUP = 1;
    private static final int OTHER_GROUP = 2;
    private static final int SEQUENCE_NUMBER_GROUP = 3;
    private static final int SUBMISSION_TIME_GROUP = 4;
    private static final int MESSAGE_ID_GROUP = 5;

    private static final int NUMBER_LENGTH = 9;
    private static final int MESSAGE_ID_LENGTH = 12;
    private final NumericField dispatchSequenceNumber = (NumericField) NumericField.builder().name("DispatchSequenceNumber").length(NUMBER_LENGTH).build();
    private final InstantField submissionTime = (InstantField) InstantField.builder().name("SubmissionTime").build();
    private final StringField messageId = (StringField) StringField.builder().name("MessageId").length(MESSAGE_ID_LENGTH).mayBeShorter().build();
    private PutMessageResult result;

    /**
     * Constructor.
     * @param putResult PutMessageResult
     */
    public PutMessageConfirmation(final PutMessageResult putResult) {
        this.result = putResult;
        invariant();
    }

    private PutMessageConfirmation(final int putResult) {
        final Optional<PutMessageResult> optionalResult = PutMessageResult.fromCode(putResult);
        Assert.isTrue(optionalResult.isPresent(), "Unknown PutMessage result code.");
        if (optionalResult.isPresent()) {
            this.result = optionalResult.get();
        }

        invariant();
    }

    private PutMessageConfirmation(final int putResult, final String dispatchSequenceNumber, final String submissionTime, final String messageId) {
        final Optional<PutMessageResult> optionalResult = PutMessageResult.fromCode(putResult);
        Assert.isTrue(optionalResult.isPresent(), "Unknown PutMessage result code.");
        if (optionalResult.isPresent()) {
            this.result = optionalResult.get();
        }

        if (isOk()) {
            this.dispatchSequenceNumber.setRawValue(dispatchSequenceNumber);
            this.submissionTime.setRawValue(submissionTime);
            this.messageId.setRawValue(messageId);
        }

        invariant();
    }

    private PutMessageConfirmation(
            final PutMessageResult putResult,
            final Integer dispatchSequenceNumber,
            final Instant submissionTime,
            final String messageId) {
        this.result = putResult;

        if (isOk()) {
            this.dispatchSequenceNumber.setValue(dispatchSequenceNumber);
            this.submissionTime.setValue(submissionTime);
            this.messageId.setValue(messageId);
        }

        invariant();
    }

    /**
     * Factory method for creating a LogonConfirmation from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return LogonConfirmation object
     */
    public static PutMessageConfirmation fromOperationItems(final String operationItems) {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
        if (matcher.group(OTHER_GROUP) != null) {
            return new PutMessageConfirmation(
                    Integer.parseInt(matcher.group(RESULT_GROUP)),
                    matcher.group(SEQUENCE_NUMBER_GROUP),
                    matcher.group(SUBMISSION_TIME_GROUP),
                    matcher.group(MESSAGE_ID_GROUP));
        } else {
            return new PutMessageConfirmation(Integer.parseInt(matcher.group(RESULT_GROUP)));
        }
    }

    public boolean isOk() {
        return result == PutMessageResult.OK;
    }

    /**
     * Returns the PutMessageResult.
     * @return logon result
     */
    public PutMessageResult result() {
        return result;
    }

    public Integer getDispatchSequenceNumber() {
        return dispatchSequenceNumber.getValue();
    }

    public Instant getSubmissionTime() {
        return submissionTime.getValue();
    }

    public String getMessageId() {
        return messageId.getValue();
    }

    @Override
    public Collection<Field<?>> fields() {
        if (isOk()) {
            return Stream.of(
                    NumericField.builder().name("Result").length(SpdConstants.RESULT_LENGTH).optional().build(),
                    dispatchSequenceNumber,
                    submissionTime,
                    messageId).collect(Collectors.toList());
        } else {
            return Collections.singletonList(NumericField.builder().name("Result").length(SpdConstants.RESULT_LENGTH).optional().build());
        }
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_PUT_MESSAGE_CONFIRMATION;
    }

    @Override
    public String toSpdString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%04d", result.code()));
        if (isOk()) {
            builder.append(dispatchSequenceNumber.toSpdString())
                    .append(submissionTime.toSpdString())
                    .append(OperationItem.paddedValueOfLength(messageId.getValue(), MESSAGE_ID_LENGTH));
        }
        return builder.toString();
    }

    /**
     * Enumeration of login confirmation error codes.
     */
    public enum PutMessageResult {
        /***/
        OK(0),
        /***/
        SYNTAX_ERROR(1001),
        /***/
        INVALID_VALUE(1004),
        /***/
        MISSING_ELEMENTS(1005),
        /***/
        RECIPIENT_IMPROPERLY_SPECIFIED(1051),
        /***/
        SECURITY_ERROR(1052),
        /***/
        TOO_MANY_RECIPIENTS(1053),
        /***/
        ORIGINATOR_INVALID(1054),
        /***/
        INCONSISTENT_REQUEST(1056),
        /***/
        FATAL_ERROR(1205),
        /***/
        BODY_TOO_LONG(1241);

        private final int code;

        /**
         * @param code error code
         */
        PutMessageResult(final int code) {
            this.code = code;
        }

        /**
         * Creates LogonError from error code.
         * @param errorCode error code
         * @return LogonError or null if there is no corresponding error code
         */
        public static Optional<PutMessageResult> fromCode(final int errorCode) {
            return Arrays.stream(values()).filter(result -> result.code() == errorCode).findFirst();
        }

        /**
         * Error code.
         * @return error code
         */
        public int code() {
            return code;
        }
    }

    /**
     * Builder for PutMessageConfirmation objects.
     */
    public static final class Builder {
        private PutMessageResult putResult;
        private Integer dispatchSequenceNumber;
        private Instant submissionTime;
        private String messageId;

        /**
         * Creates a new builder with the mandatory putResult field.
         * @param putResult result
         */
        public Builder(final PutMessageResult putResult) {
            this.putResult = putResult;
        }

        /**
         * Set dispatchSequenceNumber.
         * @param number dispatchSequenceNumber
         * @return this builder
         */
        public Builder dispatchSequenceNumber(final int number) {
            this.dispatchSequenceNumber = number;
            return this;
        }

        /**
         * Set submissionTime.
         * @param time submissionTime
         * @return this builder
         */
        public Builder submissionTime(final Instant time) {
            this.submissionTime = time;
            return this;
        }

        /**
         * Set message id.
         * @param id message id
         * @return this builder
         */
        public Builder messageId(final String id) {
            this.messageId = id;
            return this;
        }

        /**
         * Build PutMessageConfirmation object.
         * @return PutMessageConfirmation object
         */
        public PutMessageConfirmation build() {
            return new PutMessageConfirmation(putResult, dispatchSequenceNumber, submissionTime, messageId);
        }
    }
}
