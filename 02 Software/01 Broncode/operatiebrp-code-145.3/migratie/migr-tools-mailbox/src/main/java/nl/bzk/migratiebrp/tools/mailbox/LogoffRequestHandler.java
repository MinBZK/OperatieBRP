/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.voisc.spd.LogoffConfirmation;
import nl.bzk.migratiebrp.voisc.spd.Operation;

/**
 * Handles LogoffRequests.
 */
final class LogoffRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Mailbox mailbox;

    /**
     * Constructor.
     * @param mailbox mailbox
     */
    LogoffRequestHandler(final Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    /**
     * Handles a LogoffRequest.
     * @param response Operation.Builder object to which the appropriate response is added
     * @return the mailbox when logging out failed, or null
     */
    Mailbox handleRequest(final Operation.Builder response) {
        LOGGER.debug("[Mailbox {}]: Receiving LogoffRequest", mailbox == null ? "<null>" : mailbox.getMailboxnr());

        if (mailbox != null) {
            try {
                mailbox.save();
                mailbox.close();
                mailbox = null;
            } catch (final MailboxException e) {
                LOGGER.error("Probleem bij opslaan en afsluiten mailbox na uitloggen.", e);
            }
        }

        response.add(new LogoffConfirmation());

        return mailbox;
    }
}
