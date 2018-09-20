/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.spd.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.constants.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

/**
 * Verwerk alle inkomende berichten voor een mailbox.
 */
public final class MailboxVerwerker {

    private static final String STATUS_NEW_AND_LISTED = "001";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String STANDAARD_MAILBOX_PASSWORD = "GbaGemWw";

    @Inject
    private MailboxClient mailboxServerProxy;

    /**
     * Verwerk de berichten voor de gegeven mailbox door de gegeven bericht callback aan te roepen.
     * 
     * @param mailbox
     *            mailbox
     * @param berichtCallback
     *            bericht callback
     */
    public void verwerkBerichten(final Mailbox mailbox, final BerichtCallback berichtCallback) {
        // Verwerk mailbox
        if (mailbox.getMailboxpwd() == null || "".equals(mailbox.getMailboxpwd())) {
            mailbox.setMailboxpwd(STANDAARD_MAILBOX_PASSWORD);
        }

        // SSL verbinding opbouwen
        mailboxServerProxy.connect();

        try {
            // Inloggen op de Mailbox
            mailboxServerProxy.logOn(mailbox);

            // Ontvangen berichten
            final List<Integer> seqNummers = new ArrayList<>();
            final int nextSequenceNr =
                    mailboxServerProxy.listMessages(0, seqNummers, mailbox.getLimitNumber(), STATUS_NEW_AND_LISTED, SpdConstants.PRIORITY);

            if (!seqNummers.isEmpty()) {
                berichtCallback.start();

                verwerkBerichten(mailbox, berichtCallback, seqNummers, nextSequenceNr);

                berichtCallback.end();
            }

        } catch (final
            VoaException
            | BerichtCallbackException e)
        {
            LOG.error(
                "Fout bij ontvangen berichten voor mailbox {} (instantie {})",
                new Object[] {mailbox.getMailboxnr(), mailbox.getInstantiecode(), },
                e);
        } finally {
            // Logout
            try {
                mailboxServerProxy.logOff();
            } catch (final SpdProtocolException e) {
                LOG.error(
                    "Fout bij afmelden voor mailbox {} (instantie {})",
                    new Object[] {mailbox.getMailboxnr(), mailbox.getInstantiecode(), },
                    e);
            }
        }
    }

    /**
     * @param mailbox
     * @param berichtCallback
     * @param seqNummers
     * @param nextSequenceNr
     * @return
     * @throws BerichtCallbackException
     * @throws SpdProtocolException
     */
    private void verwerkBerichten(
        final Mailbox mailbox,
        final BerichtCallback berichtCallback,
        final List<Integer> seqNummers,
        final int nextSequenceNr) throws BerichtCallbackException, SpdProtocolException
    {

        for (final Integer seqNr : seqNummers) {
            berichtCallback.onBericht(mailboxServerProxy.getMessage(seqNr));
        }

        seqNummers.clear();
        if (nextSequenceNr > 0) {
            final int nextNextsequenceNr =
                    mailboxServerProxy.listMessages(
                        nextSequenceNr,
                        seqNummers,
                        mailbox.getLimitNumber(),
                        STATUS_NEW_AND_LISTED,
                        SpdConstants.PRIORITY);
            verwerkBerichten(mailbox, berichtCallback, seqNummers, nextNextsequenceNr);
        }
    }
}
