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
import nl.bzk.migratiebrp.voisc.spd.ChangePasswordConfirmation;
import nl.bzk.migratiebrp.voisc.spd.ChangePasswordRequest;
import nl.bzk.migratiebrp.voisc.spd.Operation;

/**
 * Handles ChangePasswordRequests.
 */
final class ChangePasswordRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Mailbox mailbox;

    /**
     * Constructor.
     * @param mailbox mailbox
     */
    ChangePasswordRequestHandler(final Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    /**
     * Handles a ChangePasswordRequest.
     * @param request ChangePasswordRequest
     * @param response Operation.Builder object to which the appropriate response is added
     */
    void handleRequest(final ChangePasswordRequest request, final Operation.Builder response) {

        LOGGER.debug("[Mailbox {}]: Receiving ChangePasswordRequest", mailbox.getMailboxnr());

        final ChangePasswordConfirmation confirmation;

        if (!mailbox.checkPassword(request.getOldPassword())) {
            LOGGER.info("Ongeldig wachtwoord bij wijzigen wachtwoord voor mailbox " + mailbox.getMailboxnr());
            confirmation = new ChangePasswordConfirmation(ChangePasswordConfirmation.ChangePasswordResult.OLD_PASSWORD_INVALID);
        } else {
            // Alle andere controles die van toepassing zijn op een wachtwoord, doet deze implementatie niet.
            // Kan later natuurlijk wel worden toegevoegd.
            mailbox.setPassword(request.getNewPassword());
            ChangePasswordConfirmation.ChangePasswordResult changePasswordResult;
            try {
                mailbox.save();
                changePasswordResult = ChangePasswordConfirmation.ChangePasswordResult.OK;
            } catch (final MailboxException e) {
                LOGGER.warn("Probleem bij opslaan mailbox na wijzigen wachtwoord", e);
                changePasswordResult = ChangePasswordConfirmation.ChangePasswordResult.FATAL_ERROR;
            }
            confirmation = new ChangePasswordConfirmation(changePasswordResult);
        }

        LOGGER.info("[Mailbox {}]: Password change confirmation (result={})", mailbox.getMailboxnr(), confirmation.changePasswordResult().code());
        response.add(confirmation);
    }
}
