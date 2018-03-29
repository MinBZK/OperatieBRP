/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.voisc.spd.DeliveryReport;
import nl.bzk.migratiebrp.voisc.spd.GetEnvelope;
import nl.bzk.migratiebrp.voisc.spd.GetMessage;
import nl.bzk.migratiebrp.voisc.spd.GetMessageBody;
import nl.bzk.migratiebrp.voisc.spd.GetMessageHeading;
import nl.bzk.migratiebrp.voisc.spd.MSEntry;
import nl.bzk.migratiebrp.voisc.spd.Operation;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.StatusReport;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;

/**
 * Handles ChangePasswordRequests.
 */
final class GetMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int STATUS_REPORT_AANTAL_PARAMETERS = 2;
    private static final int DELIVERY_REPORT_AANTAL_PARAMETERS = 3;
    private static final DecimalFormat DF_RESULT_CODE = new DecimalFormat("0000");

    /**
     * Status report.
     */
    private static final String STATUS_REPORT = "StaRep";

    private Mailbox mailbox;

    /**
     * Constructor.
     * @param mailbox mailbox
     */
    GetMessageHandler(final Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    /**
     * Handles a GetMessage.
     * @param request GetMessage
     * @param response Operation.Builder object to which the appropriate response is added
     */
    void handleRequest(final GetMessage request, final Operation.Builder response) {

        final MailboxEntry entry = mailbox.getEntry(request.getMsSequenceNumber());

        LOGGER.debug("[Mailbox {}]: Receiving GetMessage {}", mailbox.getMailboxnr(), request.getMsSequenceNumber());

        if (entry != null) {
            final Instant now = Instant.now();
            response.add(new MSEntry(request.getMsSequenceNumber()));

            final String mesg = entry.getMesg();
            if (mesg.startsWith(SpdAdapter.DELIVERY_REPORT)) {
                response.add(createDeliveryReport(entry, now));
            } else {
                response.add(new GetEnvelope.Builder().submissionTime(now)
                        .deliveryTime(now)
                        .actualRecipientOrName(mailbox.getMailboxnr())
                        .originatorOrName(entry.getOriginatorOrRecipient())
                        .priority(SpdConstants.Priority.NORMAL)
                        .contentType(SpdConstants.ContentType.P2)
                        .build());
                if (mesg.startsWith(STATUS_REPORT)) {
                    response.add(createStatusReport(entry));
                } else {
                    response.add(new GetMessageHeading.Builder().messageId(entry.getMessageId())
                            .crossReference(entry.getCrossReference())
                            .originatorUsername(entry.getOriginatorOrRecipient())
                            .actualRecipientORName(mailbox.getMailboxnr())
                            .actualNotificationRequest(
                                    SpdConstants.NotificationRequest.fromCode(entry.getNotificationRequest()).orElse(
                                            SpdConstants.NotificationRequest.defaultValue()))
                            .build())
                            .add(new GetMessageBody(mesg));
                }
            }
        }
    }

    private StatusReport createStatusReport(final MailboxEntry entry) {

        final StatusReport.Builder builder =
                new StatusReport.Builder().actualRecipientOrName(entry.getOriginatorOrRecipient())
                        .messageId(entry.getCrossReference())
                        .nonReceiptReason(SpdConstants.NonReceiptReason.EXPIRED);

        final String mesg = entry.getMesg();
        final String[] params = mesg.split(SpdAdapter.DELIVERY_REPORT_SEPARATOR, -1);

        if (params.length == 1) {
            builder.notificationType(SpdConstants.NotificationType.NON_RECEIPT);
        } else if (params.length == STATUS_REPORT_AANTAL_PARAMETERS) {
            builder.notificationType(
                    SpdConstants.NotificationType.fromCode(Integer.parseInt(params[1])).orElse(SpdConstants.NotificationType.NON_RECEIPT));
        } else {
            throw new IllegalArgumentException("Ongeldig StaRep bericht (" + mesg + ")");
        }

        return builder.build();
    }

    private DeliveryReport createDeliveryReport(final MailboxEntry entry, final Instant now) {

        final String mesg = entry.getMesg();
        final String[] params = mesg.split(SpdAdapter.DELIVERY_REPORT_SEPARATOR, -1);
        if (params.length != DELIVERY_REPORT_AANTAL_PARAMETERS) {
            throw new IllegalArgumentException("Ongeldig DelRep bericht (" + mesg + ")");
        }

        final String delReportType = DF_RESULT_CODE.format(Integer.parseInt(params[1]));

        final DeliveryReport.Builder builder = new DeliveryReport.Builder().reportDeliveryTime(now)
                .dispatchSequenceNumber(params[2])
                .numberOfRecipients(1)
                .reportedRecipientORName(entry.getOriginatorOrRecipient())
                .nonDeliveryReason(delReportType);
        if (SpdAdapter.DELIVERY_REPORT_CODE_BLOCKED.equals(delReportType)) {
            final int sixHours = 6;
            builder.messageDeliveryTime(now.plus(sixHours, ChronoUnit.HOURS));
        } else {
            builder.messageDeliveryTime(now);
        }

        try {
            return builder.build();
        } catch (final SpdProtocolException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
