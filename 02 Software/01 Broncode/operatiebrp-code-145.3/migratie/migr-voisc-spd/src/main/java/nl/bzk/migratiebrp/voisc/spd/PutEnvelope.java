/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

/**
 * PutEnvelope according to the sPd-protocol.
 */
public final class PutEnvelope implements OperationRecord, Request {

    private static final Pattern PATTERN = Pattern.compile("^((.{7})(.)(.)(.{11})(.))?$");
    private static final int NAME_GROUP = 2;
    private static final int CONTENT_TYPE_GROUP = 3;
    private static final int PRIORITY_GROUP = 4;
    private static final int TIME_GROUP = 5;
    private static final int ATTENTION_GROUP = 6;

    private static final int NAME_LENGTH = 7;

    private final StringField originatorOrName = (StringField) StringField.builder().name("OriginatorORName").length(NAME_LENGTH).optional().build();
    private final ContentTypeField contentType = (ContentTypeField) ContentTypeField.builder().optional().build();
    private final PriorityField priority = (PriorityField) PriorityField.builder().optional().build();
    private final InstantField deferredDeliveryTime = (InstantField) InstantField.builder().name("DeferredDeliveryTime").optional().build();
    private final AttentionField attention = (AttentionField) AttentionField.builder().optional().build();

    /**
     * Constructor.
     */
    public PutEnvelope() {
        invariant();
    }

    /**
     * Constructor.
     * @param originatorOrName originatorOrName
     * @param contentType contentType
     * @param priority priority
     * @param deferredDeliveryTime deferredDeliveryTime
     * @param attention attention
     */
    public PutEnvelope(
            final String originatorOrName,
            final SpdConstants.ContentType contentType,
            final SpdConstants.Priority priority,
            final Instant deferredDeliveryTime,
            final SpdConstants.Attention attention) {
        this.originatorOrName.setRawValue(originatorOrName);
        this.contentType.setValue(contentType);
        this.priority.setValue(priority);
        this.deferredDeliveryTime.setValue(deferredDeliveryTime);
        this.attention.setValue(attention);

        invariant();
    }

    private PutEnvelope(
            final String originatorOrName,
            final String contentType,
            final String priority,
            final String deferredDeliveryTime,
            final String attention) {
        this.originatorOrName.setRawValue(originatorOrName);
        this.contentType.setRawValue(contentType);
        this.priority.setRawValue(priority);
        this.deferredDeliveryTime.setRawValue(deferredDeliveryTime);
        this.attention.setRawValue(attention);

        invariant();
    }

    /**
     * Factory method for creating a PutEnvelope from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return PutEnvelope object
     */
    public static PutEnvelope fromOperationItems(final String operationItems) {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
        return new PutEnvelope(
                matcher.group(NAME_GROUP),
                matcher.group(CONTENT_TYPE_GROUP),
                matcher.group(PRIORITY_GROUP),
                matcher.group(TIME_GROUP),
                matcher.group(ATTENTION_GROUP));
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_PUT_ENVELOPE;
    }

    /**
     * Builder for PutEnvelope objects.
     */
    public static final class Builder {
        private String originatorOrName;
        private SpdConstants.ContentType contentType;
        private SpdConstants.Priority priority;
        private Instant deferredDeliveryTime;
        private SpdConstants.Attention attention;

        /**
         * Set originatorOrName.
         * @param value originatorOrName
         * @return this builder
         */
        public PutEnvelope.Builder originatorOrName(final String value) {
            this.originatorOrName = value;
            return this;
        }

        /**
         * Set contentType.
         * @param value contentType
         * @return this builder
         */
        public PutEnvelope.Builder contentType(final SpdConstants.ContentType value) {
            this.contentType = value;
            return this;
        }

        /**
         * Set priority.
         * @param value priority
         * @return this builder
         */
        public PutEnvelope.Builder priority(final SpdConstants.Priority value) {
            this.priority = value;
            return this;
        }

        /**
         * Set deferredDeliveryTime.
         * @param time deferredDeliveryTime
         * @return this builder
         */
        public PutEnvelope.Builder deferredDeliveryTime(final Instant time) {
            this.deferredDeliveryTime = time;
            return this;
        }

        /**
         * Set attention.
         * @param value attention
         * @return this builder
         */
        public PutEnvelope.Builder attention(final SpdConstants.Attention value) {
            this.attention = value;
            return this;
        }

        /**
         * Build PutEnvelope object.
         * @return PutEnvelope object
         */
        public PutEnvelope build() {
            return new PutEnvelope(originatorOrName, contentType, priority, deferredDeliveryTime, attention);
        }
    }
}
