/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.springframework.util.Assert;

/**
 * GetEnvelope according to the sPd-protocol.
 */
public final class GetEnvelope implements OperationRecord {

    private static final Pattern PATTERN = Pattern.compile("^(.{7})(.)(.)(.{11})(.{11})(.{7})$");
    private static final int ORIGINATOR_NAME_GROUP = 1;
    private static final int CONTENT_TYPE_GROUP = 2;
    private static final int PRIORITY_GROUP = 3;
    private static final int DELIVERY_TIME_GROUP = 4;
    private static final int SUBMISSION_TIME_GROUP = 5;
    private static final int RECIPIENT_NAME_GROUP = 6;

    private static final int NAME_LENGTH = 7;

    private final StringField originatorOrName = (StringField) StringField.builder().name("OriginatorORName").length(NAME_LENGTH).mayBeShorter().build();
    private final ContentTypeField contentType = (ContentTypeField) ContentTypeField.builder().build();
    private final PriorityField priority = (PriorityField) PriorityField.builder().build();
    private final InstantField deliveryTime = (InstantField) InstantField.builder().name("DeliveryTime").build();
    private final InstantField submissionTime = (InstantField) InstantField.builder().name("SubmissionTime").build();
    private final StringField actualRecipientOrName = (StringField) StringField.builder().name("ActualRecipientOrName").length(NAME_LENGTH).build();

    /**
     * Constructor.
     * @param originatorOrName originatorOrName
     * @param contentType contentType
     * @param priority priority
     * @param deliveryTime deliveryTime
     * @param submissionTime submissionTime
     * @param actualRecipientOrName actualRecipientOrName
     */
    public GetEnvelope(
            final String originatorOrName,
            final SpdConstants.ContentType contentType,
            final SpdConstants.Priority priority,
            final Instant deliveryTime,
            final Instant submissionTime,
            final String actualRecipientOrName) {
        this.originatorOrName.setRawValue(originatorOrName);
        this.contentType.setValue(contentType);
        this.priority.setValue(priority);
        this.deliveryTime.setValue(deliveryTime);
        this.submissionTime.setValue(submissionTime);
        this.actualRecipientOrName.setRawValue(actualRecipientOrName);

        invariant();
    }

    private GetEnvelope(
            final String originatorOrName,
            final String contentType,
            final String priority,
            final String deliveryTime,
            final String submissionTime,
            final String actualRecipientOrName) {
        this.originatorOrName.setRawValue(originatorOrName);
        this.contentType.setRawValue(contentType);
        this.priority.setRawValue(priority);
        this.deliveryTime.setRawValue(deliveryTime);
        this.submissionTime.setRawValue(submissionTime);
        this.actualRecipientOrName.setRawValue(actualRecipientOrName);

        invariant();
    }

    /**
     * Factory method for creating a PutEnvelope from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return PutEnvelope object
     */
    public static GetEnvelope fromOperationItems(final String operationItems) {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
        return new GetEnvelope(
                matcher.group(ORIGINATOR_NAME_GROUP),
                matcher.group(CONTENT_TYPE_GROUP),
                matcher.group(PRIORITY_GROUP),
                matcher.group(DELIVERY_TIME_GROUP),
                matcher.group(SUBMISSION_TIME_GROUP),
                matcher.group(RECIPIENT_NAME_GROUP));
    }

    public String getOriginatorOrName() {
        return originatorOrName.getValue();
    }

    public SpdConstants.ContentType getContentType() {
        return contentType.getValue();
    }

    public SpdConstants.Priority getPriority() {
        return priority.getValue();
    }

    public Instant getDeliveryTime() {
        return deliveryTime.getValue();
    }

    public Instant getSubmissionTime() {
        return submissionTime.getValue();
    }

    public String getActualRecipientOrName() {
        return actualRecipientOrName.getValue();
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_GET_ENVELOP;
    }

    @Override
    public void vulBericht(final Bericht bericht) throws SpdProtocolException {
        bericht.setOriginator(getOriginatorOrName());
        bericht.setTijdstipMailbox(Timestamp.from(getDeliveryTime()));
        bericht.setRecipient(getActualRecipientOrName());
    }

    /**
     * Builder for GetEnvelope objects.
     */
    public static final class Builder {
        private String originatorOrName;
        private SpdConstants.ContentType contentType;
        private SpdConstants.Priority priority;
        private Instant deliveryTime;
        private Instant submissionTime;
        private String actualRecipientOrName;

        /**
         * Set originatorOrName.
         * @param value originatorOrName
         * @return this builder
         */
        public GetEnvelope.Builder originatorOrName(final String value) {
            this.originatorOrName = value;
            return this;
        }

        /**
         * Set contentType.
         * @param value contentType
         * @return this builder
         */
        public GetEnvelope.Builder contentType(final SpdConstants.ContentType value) {
            this.contentType = value;
            return this;
        }

        /**
         * Set priority.
         * @param value priority
         * @return this builder
         */
        public GetEnvelope.Builder priority(final SpdConstants.Priority value) {
            this.priority = value;
            return this;
        }

        /**
         * Set deliveryTime.
         * @param time deliveryTime
         * @return this builder
         */
        public GetEnvelope.Builder deliveryTime(final Instant time) {
            this.deliveryTime = time;
            return this;
        }

        /**
         * Set submissionTime.
         * @param time submissionTime
         * @return this builder
         */
        public GetEnvelope.Builder submissionTime(final Instant time) {
            this.submissionTime = time;
            return this;
        }

        /**
         * Set actualRecipientOrName.
         * @param value actualRecipientOrName
         * @return this builder
         */
        public GetEnvelope.Builder actualRecipientOrName(final String value) {
            this.actualRecipientOrName = value;
            return this;
        }

        /**
         * Build GetEnvelope object.
         * @return GetEnvelope object
         */
        public GetEnvelope build() {
            return new GetEnvelope(originatorOrName, contentType, priority, deliveryTime, submissionTime, actualRecipientOrName);
        }
    }
}
