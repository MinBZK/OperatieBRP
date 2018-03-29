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
 * MessageHeading according to the sPd-protocol.
 */
public final class GetMessageHeading implements OperationRecord {

    private static final Pattern PATTERN = Pattern.compile("^(.{12})(.{12})(.{7})(.{7})(.)$");
    private static final int MESSAGE_ID_GROUP = 1;
    private static final int CROSS_REFERENCE_GROUP = 2;
    private static final int ORIGINATOR_GROUP = 3;
    private static final int RECIPIENT_GROUP = 4;
    private static final int NOTIFICATION_GROUP = 5;

    private static final int ID_LENGTH = 12;
    private static final int NAME_LENGTH = 7;

    private final StringField messageId = (StringField) StringField.builder().name("MessageId").length(ID_LENGTH).mayBeShorter().build();
    private final StringField crossReference = (StringField) StringField.builder().name("CrossReference").length(ID_LENGTH).optional().build();
    private final StringField originatorUsername = (StringField) StringField.builder().name("OriginatorUsername").length(NAME_LENGTH).build();
    private final StringField actualRecipientOrName = (StringField) StringField.builder().name("ActualRecipientOrName").length(NAME_LENGTH).build();
    private final NotificationRequestField notificationRequest = (NotificationRequestField) NotificationRequestField.builder().optional().build();

    private GetMessageHeading(
            final String messageId,
            final String crossReference,
            final String originatorUsername,
            final String actualRecipientOrName,
            final SpdConstants.NotificationRequest notificationRequest) {
        this.messageId.setRawValue(messageId);
        if ("            ".equals(crossReference)
                || "000000000000".equals(crossReference)
                || "0           ".equals(crossReference)
                || "           0".equals(crossReference)) {
            this.crossReference.setRawValue(null);
        } else {
            this.crossReference.setRawValue(crossReference);
        }
        this.originatorUsername.setRawValue(originatorUsername);
        this.actualRecipientOrName.setRawValue(actualRecipientOrName);
        this.notificationRequest.setValue(notificationRequest);

        invariant();
    }

    private GetMessageHeading(
            final String messageId,
            final String crossReference,
            final String originatorUsername,
            final String actualRecipientOrName,
            final String notificationRequest) {
        this.messageId.setRawValue(messageId);
        if ("            ".equals(crossReference)
                || "000000000000".equals(crossReference)
                || "0           ".equals(crossReference)
                || "           0".equals(crossReference)) {
            this.crossReference.setRawValue(null);
        } else {
            this.crossReference.setRawValue(crossReference);
        }
        this.originatorUsername.setRawValue(originatorUsername);
        this.actualRecipientOrName.setRawValue(actualRecipientOrName);
        this.notificationRequest.setRawValue(notificationRequest);

        invariant();
    }

    /**
     * Factory method for creating a MessageHeading from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return MessageHeading object
     */
    public static GetMessageHeading fromOperationItems(final String operationItems) {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
        return new GetMessageHeading(
                matcher.group(MESSAGE_ID_GROUP),
                matcher.group(CROSS_REFERENCE_GROUP),
                matcher.group(ORIGINATOR_GROUP),
                matcher.group(RECIPIENT_GROUP),
                matcher.group(NOTIFICATION_GROUP));
    }

    public String getMessageId() {
        return messageId.getValue();
    }

    public String getCrossReference() {
        return crossReference.getValue();
    }

    public String getOriginatorUsername() {
        return originatorUsername.getValue();
    }

    public String getActualRecipientOrName() {
        return actualRecipientOrName.getValue();
    }

    public SpdConstants.NotificationRequest getNotificationRequest() {
        return notificationRequest.getValue();
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_GET_MESSAGE_HEADING;
    }

    @Override
    public void vulBericht(final Bericht bericht) throws SpdProtocolException {
        bericht.setMessageId(getMessageId());
        bericht.setCorrelationId(getCrossReference());
        bericht.setRequestNonReceiptNotification(getNotificationRequest() == SpdConstants.NotificationRequest.NON_RECEIPT);
    }

    /**
     * Builder for GetMessageHeading objects.
     */
    public static final class Builder {
        private String messageId;
        private String crossReference;
        private String originatorUsername;
        private String actualRecipientORName;
        private SpdConstants.NotificationRequest actualNotificationRequest;

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
         * Set crossReference.
         * @param value crossReference
         * @return this builder
         */
        public Builder crossReference(final String value) {
            this.crossReference = value;
            return this;
        }

        /**
         * Set originatorUsername.
         * @param value originatorUsername
         * @return this builder
         */
        public Builder originatorUsername(final String value) {
            this.originatorUsername = value;
            return this;
        }

        /**
         * Set actualRecipientORName.
         * @param value actualRecipientORName
         * @return this builder
         */
        public Builder actualRecipientORName(final String value) {
            this.actualRecipientORName = value;
            return this;
        }

        /**
         * Set actualNotificationRequest.
         * @param value actualNotificationRequest
         * @return this builder
         */
        public Builder actualNotificationRequest(final SpdConstants.NotificationRequest value) {
            this.actualNotificationRequest = value;
            return this;
        }

        /**
         * Build GetMessageHeading object.
         * @return GetMessageHeading object
         */
        public GetMessageHeading build() {
            return new GetMessageHeading(messageId, crossReference, originatorUsername, actualRecipientORName, actualNotificationRequest);
        }
    }
}
