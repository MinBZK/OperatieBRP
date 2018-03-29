/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Mailbox entry.
 */
public final class MailboxEntry implements Comparable<MailboxEntry> {

    /**
     * Status: NEW.
     */
    public static final int STATUS_NEW = 0;
    /**
     * Status: LISTED.
     */
    public static final int STATUS_LISTED = 1;
    /**
     * Status: PROCESSED.
     */
    public static final int STATUS_PROCESSED = 2;
    /**
     * Status: SENT.
     */
    public static final int STATUS_SENT = 3;

    /**
     * Notification request: none.
     */
    public static final int NOTIFICATION_REQUEST_NONE = 0;
    /**
     * Notification request: non-receipt.
     */
    public static final int NOTIFICATION_REQUEST_NON_RECEIPT = 1;

    private static final String DEFAULT_MESG_ID_CROSS_REF = "            ";

    private String originatorOrRecipient;
    private int msSequenceId;
    private String mesg;
    private int status;
    private String messageId = DEFAULT_MESG_ID_CROSS_REF;
    private String crossReference = DEFAULT_MESG_ID_CROSS_REF;
    private int notificationRequest = NOTIFICATION_REQUEST_NONE;

    /**
     * Geef de waarde van originator or recipient.
     * @return originator or recipient
     */
    public String getOriginatorOrRecipient() {
        return originatorOrRecipient;
    }

    /**
     * Zet de waarde van originator or recipient.
     * @param originatorOrRecipient originator or recipient
     */
    public void setOriginatorOrRecipient(final String originatorOrRecipient) {
        this.originatorOrRecipient = originatorOrRecipient;
    }

    /**
     * Geef de waarde van ms sequence id.
     * @return ms sequence id
     */
    public int getMsSequenceId() {
        return msSequenceId;
    }

    /**
     * Zet de waarde van ms sequence nr.
     * @param msSequenceNr ms sequence nr
     */
    public void setMsSequenceNr(final int msSequenceNr) {
        msSequenceId = msSequenceNr;
    }

    /**
     * Geef de waarde van mesg.
     * @return mesg
     */
    public String getMesg() {
        return mesg;
    }

    /**
     * Zet de waarde van mesg.
     * @param mesg mesg
     */
    public void setMesg(final String mesg) {
        this.mesg = mesg;
    }

    /**
     * Geef de waarde van status.
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Zet de waarde van status.
     * @param status status
     */
    public void setStatus(final int status) {
        this.status = status;
    }

    /**
     * Geef de waarde van message id.
     * @return message id
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Zet messageId.
     * @param messageId messageId
     */
    public void setMessageId(final String messageId) {
        if (messageId != null && !messageId.isEmpty()) {
            this.messageId = messageId;
        }
    }

    /**
     * Geef de waarde van cross reference.
     * @return cross reference
     */
    public String getCrossReference() {
        return crossReference;
    }

    /**
     * Zet crossReference.
     * @param crossReference crossReference
     */
    public void setCrossReference(final String crossReference) {
        if (crossReference != null && !crossReference.isEmpty()) {
            this.crossReference = crossReference;
        }
    }

    /**
     * Geef de waarde van notification request.
     * @return notification request
     */
    public int getNotificationRequest() {
        return notificationRequest;
    }

    /**
     * Zet de waarde van notification request.
     * @param notificationRequest notification request
     */
    public void setNotificationRequest(final int notificationRequest) {
        this.notificationRequest = notificationRequest;
    }

    @Override
    public int compareTo(final MailboxEntry o) {
        if (o.msSequenceId == msSequenceId) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final MailboxEntry rhs = (MailboxEntry) obj;
        return new EqualsBuilder().append(msSequenceId, rhs.msSequenceId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(msSequenceId).toHashCode();
    }
}
