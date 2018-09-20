/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.List;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;

/**
 * Wrapper voor bewerkingen op de mailbox.
 */
public interface VoiscMailbox {

    /**
     * Maakt verbinding via SSL met de mailboxserver.
     *
     * @throws VoiscMailboxException
     *             when the connect fails
     */
    void connectToMailboxServer() throws VoiscMailboxException;

    /**
     * Login to mailbox.
     *
     * @param mailbox
     *            De mailbox waarop ingelogd gaat worden.
     * @throws VoiscMailboxException
     *             when the login fails
     *
     */
    void login(Mailbox mailbox) throws VoiscMailboxException;

    /**
     * send all messages to mailbox, which need to be sent.
     *
     * @param mailbox
     *            Mailbox from which the messages are received and stored
     * @param messages
     *            Een lijst van alle berichten die verstuurd moeten worden
     */
    void sendMessagesToMailbox(Mailbox mailbox, List<Bericht> messages);

    /**
     * Receive and store the messages from mailbox.
     *
     * @param mailbox
     *            Mailbox from which the messages are received and stored
     * @throws VoiscMailboxException
     *             bij fouten bij het opvragen van de lijst van berichten
     */
    void receiveMessagesFromMailbox(Mailbox mailbox) throws VoiscMailboxException;

    /**
     * Logout mailbox and shut down SSL-connection.
     *
     */
    void logout();

}
