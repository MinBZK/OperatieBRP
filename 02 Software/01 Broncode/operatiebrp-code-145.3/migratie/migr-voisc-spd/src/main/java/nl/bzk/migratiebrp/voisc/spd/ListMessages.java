/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

/**
 * PutEnvelope according to the sPd-protocol.
 */
public final class ListMessages implements OperationRecord, Request {

    /**
     * Maximum number of messages that may be retrieved.
     */
    private static final int MAXIMUM = 171;
    /**
     * Minium number of messages that should be retrieved.
     */
    private static final int MINIMUM = 40;

    private static final Pattern PATTERN = Pattern.compile("^((.{3})(.{3})(.)(.{9}).{9})?$");
    private static final int RESULT_GROUP = 1;
    private static final int LIMIT_GROUP = 2;
    private static final int STATUS_GROUP = 3;
    private static final int PRIORITY_GROUP = 4;
    private static final int FROM_GROUP = 5;

    private static final int LIMIT_LENGTH = 3;
    private static final int STATUS_LENGTH = 3;
    private static final int NUMBER_LENGTH = 9;

    private final NumericField limit = (NumericField) NumericField.builder().name("LimitNumber").length(LIMIT_LENGTH).optional().build();
    private final StringField status = (StringField) StringField.builder().name("MSStatus").length(STATUS_LENGTH).optional().build();
    private final PriorityField priority = (PriorityField) PriorityField.builder().optional().build();
    private final NumericField fromMSSequenceNumber =
            (NumericField) NumericField.builder().name("FromMSSequenceNumber").length(NUMBER_LENGTH).optional().build();
    private final NumericField toMSSequenceNumber =
            (NumericField) NumericField.builder().name("ToMSSequenceNumber").length(NUMBER_LENGTH).optional().build();

    private ListMessages() {
        this.fromMSSequenceNumber.setValue(0);
        invariant();
    }

    private ListMessages(final String limit, final String status, final String priority, final String fromMSSequenceNumber) {
        this.limit.setRawValue(limit);
        this.status.setRawValue(status);
        this.priority.setRawValue(priority);
        this.fromMSSequenceNumber.setRawValue(fromMSSequenceNumber);

        if (!this.limit.isEmpty() && this.limit.getValue() < MINIMUM) {
            this.limit.setValue(MINIMUM);
        } else if (!this.limit.isEmpty() && this.limit.getValue() > MAXIMUM) {
            this.limit.setValue(MAXIMUM);
        }

        if (this.fromMSSequenceNumber.isEmpty()) {
            this.fromMSSequenceNumber.setValue(0);
        }
        this.toMSSequenceNumber.setRawValue("");

        invariant();
    }

    /**
     * Factory method for creating a ListMessages from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return ListMessages object
     */
    public static ListMessages fromOperationItems(final String operationItems) {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
        if (matcher.group(RESULT_GROUP) == null) {
            return new ListMessages();
        } else {
            return new ListMessages(matcher.group(LIMIT_GROUP), matcher.group(STATUS_GROUP), matcher.group(PRIORITY_GROUP), matcher.group(FROM_GROUP));
        }
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_LIST_MESSAGES;
    }

    public Integer getLimit() {
        return limit.getValue();
    }

    public String getStatus() {
        return status.getValue();
    }

    public SpdConstants.Priority getPriority() {
        return priority.getValue();
    }

    public Integer getFromMSSequenceNumber() {
        return fromMSSequenceNumber.getValue();
    }

    public Integer getToMSSequenceNumber() {
        return toMSSequenceNumber.getValue();
    }

    /**
     * Builder for ListMessages objects.
     */
    public static final class Builder {
        private Integer limit;
        private String status;
        private Integer priority;
        private Integer fromMSSequenceNumber;

        /**
         * Set limit.
         * @param value limit
         * @return this builder
         */
        public Builder limit(final int value) {
            this.limit = value;
            return this;
        }

        /**
         * Set status.
         * @param value status
         * @return this builder
         */
        public Builder status(final String value) {
            this.status = value;
            return this;
        }

        /**
         * Set priority.
         * @param value priority
         * @return this builder
         */
        public Builder priority(final SpdConstants.Priority value) {
            this.priority = value.code();
            return this;
        }

        /**
         * Set fromMSSequenceNumber.
         * @param value fromMSSequenceNumber
         * @return this builder
         */
        public Builder fromMSSequenceNumber(final int value) {
            this.fromMSSequenceNumber = value;
            return this;
        }

        /**
         * Build PutEnvelope object.
         * @return PutEnvelope object
         */
        public ListMessages build() {
            return new ListMessages(
                    limit == null ? null : limit.toString(),
                    status,
                    priority == null ? null : priority.toString(),
                    fromMSSequenceNumber == null ? null : fromMSSequenceNumber.toString());
        }
    }
}
