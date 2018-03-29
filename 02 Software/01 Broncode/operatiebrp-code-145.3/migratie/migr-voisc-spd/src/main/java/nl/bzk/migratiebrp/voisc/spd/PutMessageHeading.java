/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import org.springframework.util.Assert;

/**
 * MessageHeading according to the sPd-protocol. NB: can only handle one recipient at this time!
 */
public final class PutMessageHeading implements OperationRecord {

    private static final Pattern PATTERN = Pattern.compile("^(.{12})(.{12})(.{7})(.{3})(.{7})(.)$");
    private static final int MESSAGE_ID_GROUP = 1;
    private static final int CROSS_REFERENCE_GROUP = 2;
    private static final int ORIGINATOR_GROUP = 3;
    private static final int NR_RECIP_GROUP = 4;
    private static final int RECIPIENT_GROUP = 5;
    private static final int NOTIFICATION_GROUP = 6;

    private static final int NAME_LENGTH = 7;
    private static final int ID_LENGTH = 12;
    private static final int NR_OF_RECIP_LENGTH = 3;

    private final StringField messageId = (StringField) StringField.builder().name("MessageId").length(ID_LENGTH).mayBeShorter().build();
    private final StringField crossReference = (StringField) StringField.builder().name("CrossReference").length(ID_LENGTH).optional().build();
    private final StringField originatorOrName = (StringField) StringField.builder().name("OriginatorOrName").length(NAME_LENGTH).optional().build();
    private final NumericField numberOfRecipients = (NumericField) NumericField.builder().name("NumberOfRecipients").length(NR_OF_RECIP_LENGTH).build();
    private final StringField recipientOrName = (StringField) StringField.builder().name("RecipientOrName").length(NAME_LENGTH).build();
    private final NotificationRequestField notificationRequest = (NotificationRequestField) NotificationRequestField.builder().build();

    /**
     * Constructor.
     * @param messageId messageId
     * @param crossReference crossReference
     * @param originatorOrName originatorOrName
     * @param numberOfRecipients numberOfRecipients
     * @param recipientOrName recipientOrName
     * @param notificationRequest notificationRequest
     */
    public PutMessageHeading(
            final String messageId,
            final String crossReference,
            final String originatorOrName,
            final int numberOfRecipients,
            final String recipientOrName,
            final SpdConstants.NotificationRequest notificationRequest) {
        this.messageId.setRawValue(messageId);
        this.crossReference.setRawValue(crossReference);
        this.originatorOrName.setRawValue(originatorOrName);
        this.numberOfRecipients.setValue(numberOfRecipients);
        this.recipientOrName.setRawValue(recipientOrName);
        this.notificationRequest.setValue(notificationRequest);

        invariant();
    }

    private PutMessageHeading(
            final String messageId,
            final String crossReference,
            final String originatorOrName,
            final String numberOfRecipients,
            final String recipientOrName,
            final String notificationRequest) {
        this.messageId.setRawValue(messageId);
        this.crossReference.setRawValue(crossReference);
        this.originatorOrName.setRawValue(originatorOrName);
        this.numberOfRecipients.setRawValue(numberOfRecipients);
        this.recipientOrName.setRawValue(recipientOrName);
        this.notificationRequest.setRawValue(notificationRequest);

        invariant();
    }

    /**
     * Factory method to create MessageHeading from a Bericht object.
     * @param bericht bericht
     * @return message heading operation record
     */
    public static PutMessageHeading fromBericht(final Bericht bericht) {
        Assert.notNull(bericht, "Bericht mag niet null zijn.");
        return new PutMessageHeading(
                bericht.getMessageId(),
                bericht.getCorrelationId(),
                bericht.getOriginator(),
                1,
                bericht.getRecipient(),
                bericht.getRequestNonReceiptNotification() ? SpdConstants.NotificationRequest.NON_RECEIPT : SpdConstants.NotificationRequest.NONE);
    }

    /**
     * Factory method for creating a MessageHeading from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return MessageHeading object
     */
    public static PutMessageHeading fromOperationItems(final String operationItems) {
        final Matcher matcher = PATTERN.matcher(operationItems);
        Assert.isTrue(matcher.matches(), String.format("operationItems: '%s' does not match message structure.", operationItems));
        return new PutMessageHeading(
                matcher.group(MESSAGE_ID_GROUP),
                matcher.group(CROSS_REFERENCE_GROUP),
                matcher.group(ORIGINATOR_GROUP),
                matcher.group(NR_RECIP_GROUP),
                matcher.group(RECIPIENT_GROUP),
                matcher.group(NOTIFICATION_GROUP));
    }

    public String getMessageId() {
        return messageId.getValue();
    }

    public String getCrossReference() {
        return crossReference.getValue();
    }

    public String getOriginatorOrName() {
        return originatorOrName.getValue();
    }

    public Integer getNumberOfRecipients() {
        return numberOfRecipients.getValue();
    }

    public String getRecipientOrName() {
        return recipientOrName.getValue();
    }

    public SpdConstants.NotificationRequest getNotificationRequest() {
        return notificationRequest.getValue();
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_PUT_MESSAGE_HEADING;
    }
}
