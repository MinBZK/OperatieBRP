/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.neoload;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

/**
 * Mailbox facade.
 */
public class MailboxFacade {

    private final ClientFactory clientFactory;

    /**
     * Constructor.
     * @param hostname mailbox host
     * @param portNumber mailbox port
     */
    public MailboxFacade(final String hostname, final int portNumber) {
        this.clientFactory = new ClientFactory(hostname, portNumber);
    }

    /**
     * Verstuur een bericht (van {#link BerichtFacade.getVerzendendeMailbox} naar {#link BerichtFacade.getOntvangendeMailbox}).
     *
     * @param mailboxpwd wachtwoord van {#link BerichtFacade.getVerzendendeMailbox}
     * @param bericht bericht
     * @throws VoaException bij fouten
     */
    public void verstuur(final String mailboxpwd, final BerichtFacade bericht) throws VoaException {
        final MailboxClient mailboxClient = clientFactory.getMailboxClient();
        Connection connection = null;
        try {
            connection = mailboxClient.connect();

            final Mailbox mailbox = new Mailbox();
            mailbox.setMailboxnr(bericht.getVerzendendeMailbox());
            mailbox.setMailboxpwd(mailboxpwd);

            mailboxClient.logOn(connection, mailbox);

            final Bericht mailboxBericht = new Bericht();
            mailboxBericht.setOriginator(bericht.getVerzendendeMailbox());
            mailboxBericht.setRecipient(bericht.getOntvangendeMailbox());
            mailboxBericht.setMessageId(bericht.getMessageId());
            mailboxBericht.setCorrelationId(bericht.getCorrelationId());
            mailboxBericht.setBerichtInhoud(bericht.getInhoud());
            mailboxClient.putMessage(connection, mailboxBericht);

        } finally {
            if (connection != null) {
                mailboxClient.logOff(connection);
            }
        }
    }

    /**
     * Ontvang een bericht.
     * @param mailboxnr mailbox nummer
     * @param mailboxpwd mailbox wachtwoord
     * @return bericht, kan null zijn
     * @throws VoaException bij fouten
     */
    public BerichtFacade ontvang(final String mailboxnr, final String mailboxpwd) throws VoaException {
        final MailboxClient mailboxClient = clientFactory.getMailboxClient();
        Connection connection = null;
        try {
            connection = mailboxClient.connect();

            final Mailbox mailbox = new Mailbox();
            mailbox.setMailboxnr(mailboxnr);
            mailbox.setMailboxpwd(mailboxpwd);

            mailboxClient.logOn(connection, mailbox);

            final List<Integer> sequenceNumbers = new ArrayList<>();
            mailboxClient.listMessages(connection, 0, sequenceNumbers, 1, "001", SpdConstants.Priority.NORMAL);

            if(sequenceNumbers.isEmpty()) {
                return null;
            }

            final Bericht mailboxBericht = mailboxClient.getMessage(connection, sequenceNumbers.get(0));
            final BerichtFacade resultaat = new BerichtFacade();
            resultaat.setMessageId(mailboxBericht.getMessageId());
            resultaat.setCorrelationId(mailboxBericht.getCorrelationId());
            resultaat.setVerzendendeMailbox(mailboxBericht.getOriginator());
            resultaat.setOntvangendeMailbox(mailboxBericht.getRecipient());
            resultaat.setInhoud(mailboxBericht.getBerichtInhoud());

            return resultaat;
        } finally {
            if (connection != null) {
                mailboxClient.logOff(connection);
            }
        }
    }
}
