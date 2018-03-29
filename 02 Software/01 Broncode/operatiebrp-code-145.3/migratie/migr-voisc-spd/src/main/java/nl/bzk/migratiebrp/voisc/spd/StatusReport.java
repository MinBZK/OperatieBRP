/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.springframework.util.Assert;

/**
 * StatusReport according to the sPd-protocol.
 */
public final class StatusReport implements OperationRecord {

    private static final Pattern PATTERN = Pattern.compile("^(.{7})(.)(.{12})(.)$");
    private static final int RECIPIENT_GROUP = 1;
    private static final int NOTIFICATION_TYPE_GROUP = 2;
    private static final int MESSAGE_ID_GROUP = 3;
    private static final int NON_RECEIPT_REASON_GROUP = 4;

    private static final int ID_LENGTH = 12;
    private static final int NAME_LENGTH = 7;

    private final StringField actualRecipientOrName = (StringField) StringField.builder().name("ActualRecipientOrName").length(NAME_LENGTH).build();
    private final NotificationTypeField notificationType = (NotificationTypeField) NotificationTypeField.builder().build();
    private final StringField messageId = (StringField) StringField.builder().name("MessageId").length(ID_LENGTH).mayBeShorter().build();
    private final NonReceiptReasonField nonReceiptReason = (NonReceiptReasonField) NonReceiptReasonField.builder().build();

    private StatusReport(
            final String actualRecipientOrName,
            final SpdConstants.NotificationType notificationType,
            final String messageId,
            final SpdConstants.NonReceiptReason nonReceiptReason) {
        this.actualRecipientOrName.setRawValue(actualRecipientOrName);
        this.notificationType.setValue(notificationType);
        this.messageId.setRawValue(messageId);
        this.nonReceiptReason.setValue(nonReceiptReason);

        invariant();
    }

    private StatusReport(final String actualRecipientOrName, final String notificationType, final String messageId, final String nonReceiptReason) {
        this.actualRecipientOrName.setRawValue(actualRecipientOrName);
        this.notificationType.setRawValue(notificationType);
        this.messageId.setRawValue(messageId);
        this.nonReceiptReason.setRawValue(nonReceiptReason);

        invariant();
    }

    /**
     * Factory method for creating a StatusReport from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return StatusReport object
     */
    public static StatusReport fromOperationItems(final String operationItems) {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
        return new StatusReport(
                matcher.group(RECIPIENT_GROUP),
                matcher.group(NOTIFICATION_TYPE_GROUP),
                matcher.group(MESSAGE_ID_GROUP),
                matcher.group(NON_RECEIPT_REASON_GROUP));
    }

    public String getActualRecipientOrName() {
        return actualRecipientOrName.getValue();
    }

    public SpdConstants.NotificationType getNotificationType() {
        return notificationType.getValue();
    }

    public String getMessageId() {
        return messageId.getValue();
    }

    public SpdConstants.NonReceiptReason getNonReceiptReason() {
        return nonReceiptReason.getValue();
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_STATUS_REPORT;
    }

    @Override
    public void vulBericht(final Bericht bericht) throws SpdProtocolException {
        bericht.setCorrelationId(getMessageId());
        bericht.setNotificationType(String.valueOf(getNotificationType().code()));
    }

    /**
     * Builder for StatusReport objects.
     */
    public static final class Builder {
        private String actualRecipientOrName;
        private SpdConstants.NotificationType notificationType;
        private String messageId;
        private SpdConstants.NonReceiptReason nonReceiptReason;

        /**
         * Set actualRecipientOrName.
         * @param value actualRecipientOrName
         * @return this builder
         */
        public Builder actualRecipientOrName(final String value) {
            this.actualRecipientOrName = value;
            return this;
        }

        /**
         * Set notificationType.
         * @param value notificationType
         * @return this builder
         */
        public Builder notificationType(final SpdConstants.NotificationType value) {
            this.notificationType = value;
            return this;
        }

        /**
         * Set messageId.
         * @param value messageId
         * @return this builder
         */
        public Builder messageId(final String value) {
            this.messageId = value;
            return this;
        }

        /**
         * Set nonReceiptReason.
         * @param value nonReceiptReason
         * @return this builder
         */
        public Builder nonReceiptReason(final SpdConstants.NonReceiptReason value) {
            this.nonReceiptReason = value;
            return this;
        }

        /**
         * Build StatusReport object.
         * @return StatusReport object
         */
        public StatusReport build() {
            return new StatusReport(actualRecipientOrName, notificationType, messageId, nonReceiptReason);
        }
    }
}
