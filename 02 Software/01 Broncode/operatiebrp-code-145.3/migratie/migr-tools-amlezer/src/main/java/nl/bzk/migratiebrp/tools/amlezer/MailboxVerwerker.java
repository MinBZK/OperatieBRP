/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.mailbox.client.exception.ConnectionException;
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
     * @param mailbox mailbox
     * @param berichtCallback bericht callback
     */
    public void verwerkBerichten(final Mailbox mailbox, final BerichtCallback berichtCallback) {
        // Verwerk mailbox
        if (mailbox.getMailboxpwd() == null || "".equals(mailbox.getMailboxpwd())) {
            mailbox.setMailboxpwd(STANDAARD_MAILBOX_PASSWORD);
        }

        // SSL verbinding opbouwen
        try (final Connection mailboxConnection = mailboxServerProxy.connect()) {

            try {
                // Inloggen op de Mailbox
                mailboxServerProxy.logOn(mailboxConnection, mailbox);

                // Ontvangen berichten
                final List<Integer> seqNummers = new ArrayList<>();
                final int nextSequenceNr =
                        mailboxServerProxy.listMessages(
                                mailboxConnection,
                                0,
                                seqNummers,
                                mailbox.getLimitNumber(),
                                STATUS_NEW_AND_LISTED,
                                SpdConstants.Priority.defaultValue());

                if (!seqNummers.isEmpty()) {
                    berichtCallback.start();

                    verwerkBerichten(mailboxConnection, mailbox, berichtCallback, seqNummers, nextSequenceNr);

                    berichtCallback.end();
                }

            } catch (final
            VoaException
                    | BerichtCallbackException e) {
                LOG.error(
                        "Fout bij ontvangen berichten voor mailbox {} (partijcode {})",
                        new Object[]{mailbox.getMailboxnr(), mailbox.getPartijcode(),},
                        e);
            } finally {
                mailboxServerProxy.logOff(mailboxConnection);
            }
        } catch (final VoaException e) {
            LOG.error("Fout bij afmelden voor mailbox {} (partijcode {})", new Object[]{mailbox.getMailboxnr(), mailbox.getPartijcode(),}, e);
        } catch (final ConnectionException e) {
            LOG.error("Fout bij connectie voor mailbox {} (partijcode {})", new Object[]{mailbox.getMailboxnr(), mailbox.getPartijcode(),}, e);
            ;
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
            final Connection mailboxConnection,
            final Mailbox mailbox,
            final BerichtCallback berichtCallback,
            final List<Integer> seqNummers,
            final int nextSequenceNr) throws BerichtCallbackException, VoaException {

        for (final Integer seqNr : seqNummers) {
            berichtCallback.onBericht(mailboxServerProxy.getMessage(mailboxConnection, seqNr));
        }

        seqNummers.clear();
        if (nextSequenceNr > 0) {
            final int nextNextsequenceNr =
                    mailboxServerProxy.listMessages(
                            mailboxConnection,
                            nextSequenceNr,
                            seqNummers,
                            mailbox.getLimitNumber(),
                            STATUS_NEW_AND_LISTED,
                            SpdConstants.Priority.defaultValue());
            verwerkBerichten(mailboxConnection, mailbox, berichtCallback, seqNummers, nextNextsequenceNr);
        }
    }
}
