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
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.voisc.spd.LogonConfirmation;
import nl.bzk.migratiebrp.voisc.spd.LogonRequest;
import nl.bzk.migratiebrp.voisc.spd.Operation;

/**
 * Handles LogonRequests.
 */
final class LogonRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final MailboxFactory mbFactory;
    private Mailbox mailbox;

    /**
     * Constructor.
     * @param factory mailbox factory
     * @param mailbox mailbox
     */
    LogonRequestHandler(final MailboxFactory factory, final Mailbox mailbox) {
        this.mbFactory = factory;
        this.mailbox = mailbox;
    }

    /**
     * Handles a LogonRequest.
     * @param request LogonRequest
     * @param response Operation.Builder object to which the appropriate response is added
     * @return the mailbox when logging out failed, or null
     */
    Mailbox handleRequest(final LogonRequest request, final Operation.Builder response) {
        LOGGER.debug("[Mailbox {}]: Receiving LogonRequest (username={})", mailbox == null ? "<null>" : mailbox.getMailboxnr(), request.getUsername());

        final Mailbox vorigeMailbox = mailbox;

        LogonConfirmation.LogonResult resultcode = openMailbox(request);

        // Alleen doorgaan met controles als mailbox geopend is (en dus wachtwoord
        if (mailbox != null) {
            if (mailbox.getStatus() == Mailbox.MailboxStatus.STATUS_NOT_PRESENT) {
                LOGGER.warn("Geen mailbox aanwezig. Spd-foutcode 1036");
                mailbox.close();
                mailbox = null;
                resultcode = LogonConfirmation.LogonResult.MAILBOX_NOT_PRESENT;
            } else if (mailbox.getStatus() == Mailbox.MailboxStatus.STATUS_TEMPORARY_BLOCKED) {
                LOGGER.warn("Mailbox tijdelijk geblokkeerd. Spd-foutcode 1037");
                mailbox.close();
                mailbox = null;
                resultcode = LogonConfirmation.LogonResult.MAILBOX_TEMPORARILY_BLOCKED;
            } else if (!mailbox.checkPassword(request.getPassword())) {
                LOGGER.info("Ongeldig wachtwoord bij inloggen voor mailbox " + mailbox.getMailboxnr());
                mailbox.close();
                mailbox = null;
                resultcode = LogonConfirmation.LogonResult.SECURITY_FAILURE;
            }
        }

        if (mailbox == null) {
            // Foutsituatie en dan moet de vorige mailbox open blijven
            mailbox = vorigeMailbox;
        } else {
            if (vorigeMailbox != null) {
                try {
                    vorigeMailbox.save();
                } catch (final MailboxException e) {
                    LOGGER.error("Probleem bij opslaan en afsluiten mailbox na inloggen op andere mailbox.", e);
                } finally {
                    vorigeMailbox.close();
                }
            }
        }

        response.add(new LogonConfirmation(resultcode));

        return mailbox;
    }

    private LogonConfirmation.LogonResult openMailbox(final LogonRequest request) {
        LogonConfirmation.LogonResult resultcode;

        LOGGER.debug("Opening mailbox for " + request.getUsername());
        mailbox = mbFactory.getMailbox(request.getUsername());

        try {
            mailbox.open();
            resultcode = LogonConfirmation.LogonResult.OK;
        } catch (final MailboxException e) {
            LOGGER.error("Probleem bij opnenen mailbox", e);
            mailbox.close();
            mailbox = null;
            resultcode = null;
        }
        return resultcode;
    }
}
