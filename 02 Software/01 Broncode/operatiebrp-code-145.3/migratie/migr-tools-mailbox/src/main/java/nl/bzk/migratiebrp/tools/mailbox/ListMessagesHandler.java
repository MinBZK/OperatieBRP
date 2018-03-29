/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.time.Instant;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.voisc.spd.ListMessages;
import nl.bzk.migratiebrp.voisc.spd.ListMessagesConfirmation;
import nl.bzk.migratiebrp.voisc.spd.ListResult;
import nl.bzk.migratiebrp.voisc.spd.MSList;
import nl.bzk.migratiebrp.voisc.spd.Operation;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;

/**
 * Handles ListMessages.
 */
final class ListMessagesHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Mailbox mailbox;

    /**
     * Constructor.
     * @param mailbox mailbox
     */
    ListMessagesHandler(final Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    /**
     * Handles a ListMessages.
     * @param request ListMessages
     * @param response Operation.Builder object to which the appropriate response is added
     */
    void handleRequest(final ListMessages request, final Operation.Builder response) {

        LOGGER.debug("[Mailbox {}]: Receiving ListMessages", mailbox.getMailboxnr());

        final Mailbox.FilterResult filterResult = mailbox.filterInbox(request.getStatus(), request.getFromMSSequenceNumber(), request.getLimit());
        final List<MailboxEntry> filteredEntries = filterResult.getEntries();
        final int nextFilteredMSSequenceNr = filterResult.getNextMsSequenceId();

        final int nrOfEntries = filteredEntries.size();

        if (nrOfEntries > 0) {
            LOGGER.info(
                    "[Mailbox {}]: Listing {} messages (status={}, startAt={})",
                    mailbox.getMailboxnr(),
                    nrOfEntries,
                    request.getStatus(),
                    request.getFromMSSequenceNumber());

            final MSList.Builder listBuilder = new MSList.Builder();

            for (final MailboxEntry entry : filteredEntries) {
                listBuilder.addEntry()
                        .msSequenceNumber(entry.getMsSequenceId())
                        .msStatus(SpdConstants.MSStatus.fromCode(entry.getStatus()).orElse(null))
                        .priority(SpdConstants.Priority.NORMAL)
                        .deliveryTime(Instant.now())
                        .originatorOrName(entry.getOriginatorOrRecipient())
                        .build();
            }
            response.add(new ListResult(nextFilteredMSSequenceNr)).add(listBuilder.build());
        } else {
            LOGGER.debug(
                    "[Mailbox {}]: Listing no messages (status={}, startAt={})",
                    mailbox.getMailboxnr(),
                    request.getStatus(),
                    request.getFromMSSequenceNumber());

            response.add(new ListMessagesConfirmation(ListMessagesConfirmation.ListError.NO_ENTRIES));
        }
    }
}
