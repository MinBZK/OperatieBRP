/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.text.DecimalFormat;
import java.time.Instant;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.voisc.spd.Operation;
import nl.bzk.migratiebrp.voisc.spd.PutMessageConfirmation;
import nl.bzk.migratiebrp.voisc.spd.PutMessageOperation;

/**
 * Handler for PutMessageOperations.
 */
final class PutMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final MailboxFactory mbFactory;
    private Mailbox mailbox;

    /**
     * Constructor.
     * @param factory mailbox factory
     * @param mailbox mailbox
     */
    PutMessageHandler(final MailboxFactory factory, final Mailbox mailbox) {
        this.mbFactory = factory;
        this.mailbox = mailbox;
    }

    /**
     * Handles a PutMessageOperation.
     * @param putMessage PutMessageOperation
     * @param response Operation.Builder object to which the appropriate response is added
     */
    void handleRequest(final PutMessageOperation putMessage, final Operation.Builder response) {

        final PutMessageConfirmation.Builder builder;

        if (mailbox == null) {
            LOGGER.warn("Put message zonder mailbox");
            builder = new PutMessageConfirmation.Builder(PutMessageConfirmation.PutMessageResult.RECIPIENT_IMPROPERLY_SPECIFIED);
        } else if ("9999999".equals(putMessage.heading().getRecipientOrName())) {
            // SITUATIE VOOR TEST: Indien de geadresseerd mailbox 9999999 is dan geven we
            // foutmelding 1052 Geadresseerde niet bereikbaar vanuit deze mailbox (Security error).
            builder = new PutMessageConfirmation.Builder(PutMessageConfirmation.PutMessageResult.SECURITY_ERROR);
        } else {
            LOGGER.debug("[Mailbox {}]: Receiving PutMessage", mailbox.getMailboxnr());
            LOGGER.debug("Verwerk putMessage: {}", putMessage.body().toSpdString());

            final MailboxEntry sentEntry =
                    maakMailboxEntry(
                            putMessage.heading().getRecipientOrName(),
                            putMessage.heading().getCrossReference(),
                            putMessage.body().toSpdString(),
                            putMessage.heading().getMessageId(),
                            putMessage.heading().getNotificationRequest().code(),
                            MailboxEntry.STATUS_SENT);
            mailbox.addEntry(sentEntry);

            final boolean specialCaseRecipientIsThisMailbox = mailbox.getMailboxnr().equals(putMessage.heading().getRecipientOrName());
            final Mailbox recipientMailbox;
            if (specialCaseRecipientIsThisMailbox) {
                recipientMailbox = mailbox;
            } else {
                recipientMailbox = mbFactory.getMailbox(putMessage.heading().getRecipientOrName());
            }

            PutMessageConfirmation.PutMessageResult putMessageResult;
            try {
                if (!specialCaseRecipientIsThisMailbox) {
                    recipientMailbox.open();
                }

                if (recipientMailbox.getStatus() == Mailbox.MailboxStatus.STATUS_TEMPORARY_BLOCKED) {
                    createDeliveryReport(sentEntry, putMessage, SpdAdapter.DELIVERY_REPORT_CODE_BLOCKED);
                } else if (recipientMailbox.getStatus() == Mailbox.MailboxStatus.STATUS_NOT_PRESENT) {
                    createDeliveryReport(sentEntry, putMessage, SpdAdapter.DELIVERY_REPORT_CODE_UNKNOWN);
                } else {
                    final MailboxEntry entry =
                            maakMailboxEntry(
                                    mailbox.getMailboxnr(),
                                    putMessage.heading().getCrossReference(),
                                    putMessage.body().toSpdString(),
                                    putMessage.heading().getMessageId(),
                                    putMessage.heading().getNotificationRequest().code(),
                                    MailboxEntry.STATUS_NEW);
                    recipientMailbox.addEntry(entry);
                    recipientMailbox.save();
                }
                putMessageResult = PutMessageConfirmation.PutMessageResult.OK;
            } catch (final MailboxException e) {
                LOGGER.error("Fout bij opslaan bericht in mailbox van ontvangende partij.", e);
                putMessageResult = PutMessageConfirmation.PutMessageResult.FATAL_ERROR;
            } finally {
                if (!specialCaseRecipientIsThisMailbox) {
                    recipientMailbox.close();
                }
            }

            builder = new PutMessageConfirmation.Builder(putMessageResult);
            builder.messageId(putMessage.heading().getMessageId()).dispatchSequenceNumber(sentEntry.getMsSequenceId()).submissionTime(Instant.now());
            LOGGER.info("[Mailbox {}]: Put message confirmation (dispatchSequenceNumber {})", mailbox.getMailboxnr(), sentEntry.getMsSequenceId());
        }

        response.add(builder.build());
    }

    private MailboxEntry maakMailboxEntry(
            final String originatorOrRecipient,
            final String crossReference,
            final String message,
            final String messageId,
            final int notificationRequest,
            final int status) {
        final MailboxEntry mailboxEntry = new MailboxEntry();
        mailboxEntry.setOriginatorOrRecipient(originatorOrRecipient);
        mailboxEntry.setCrossReference(crossReference);
        mailboxEntry.setMesg(message);
        mailboxEntry.setMessageId(messageId);
        mailboxEntry.setStatus(status);
        mailboxEntry.setNotificationRequest(notificationRequest);
        return mailboxEntry;
    }

    private void createDeliveryReport(final MailboxEntry sentEntry, final PutMessageOperation putMessage, final String nonDeliveryReason) {
        final DecimalFormat dfSequence = new DecimalFormat("000000000");

        final MailboxEntry entry = new MailboxEntry();
        entry.setOriginatorOrRecipient(putMessage.heading().getRecipientOrName());
        final String message = SpdAdapter.DELIVERY_REPORT + ";" + nonDeliveryReason + ";" + dfSequence.format(sentEntry.getMsSequenceId());
        entry.setMesg(message);
        entry.setStatus(MailboxEntry.STATUS_NEW);

        mailbox.addEntry(entry);
    }
}
