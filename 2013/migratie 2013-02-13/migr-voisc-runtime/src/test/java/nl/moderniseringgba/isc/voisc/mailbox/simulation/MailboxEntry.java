/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.simulation;

public class MailboxEntry implements Comparable<MailboxEntry> {

    public static final int STATUS_NEW = 0;
    public static final int STATUS_LISTED = 1;
    public static final int STATUS_PROCESSED = 2;
    private static final String DEFAULT_MESG_ID_CROSS_REF = "            ";

    private String originatorOrRecipient;
    private int msSequenceId;
    private String mesg;
    private int status;
    private String messageId = DEFAULT_MESG_ID_CROSS_REF;
    private String crossReference = DEFAULT_MESG_ID_CROSS_REF;

    public String getOriginatorOrRecipient() {
        return originatorOrRecipient;
    }

    public void setOriginatorOrRecipient(final String originator) {
        originatorOrRecipient = originator;
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(final String mesg) {
        this.mesg = mesg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public int getMsSequenceId() {
        return msSequenceId;
    }

    public void setMsSequenceNr(final int msSequenceId) {
        this.msSequenceId = msSequenceId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String messageId) {
        if (messageId != null && !messageId.isEmpty()) {
            this.messageId = messageId;
        }
    }

    public String getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(final String crossReference) {
        if (crossReference != null && !crossReference.isEmpty()) {
            this.crossReference = crossReference;
        }
    }

    @Override
    public int compareTo(final MailboxEntry o) {
        if (o.getMsSequenceId() == getMsSequenceId()) {
            return 0;
        }
        return -1;
    }
}
