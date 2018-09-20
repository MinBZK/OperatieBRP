/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc;

import java.util.List;

import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;

/**
 * Interface which defines all methods that are needed to connect to the MailboxServer, to Send and Receive messages
 * from the MailboxServer and to change the password of the MailboxServer.
 * 
 */
public interface VoaService {

    /**
     * Maakt verbinding via SSL met de mailboxserver.
     */
    void connectToMailboxServer();

    /**
     * Login to mailbox.
     * 
     * @param regel
     *            De logboekregel voor deze mailbox
     * @param mailbox
     *            De mailbox waarop ingelogd gaat worden.
     * @throws VoaException
     *             when the login fails a VoaException is thrown
     * 
     */
    void login(LogboekRegel regel, Mailbox mailbox) throws VoaException;

    /**
     * Logout mailbox and shut down SSL-connection.
     * 
     */
    void logout();

    /**
     * send all messages to mailbox, which need to be sended.
     * 
     * @param regel
     *            logregel waarin opgeslagen wordt hoeveel berichten er zijn verstuurd
     * @param messages
     *            Een lijst van alle berichten die verstuurd moeten worden
     * 
     * @throws VoaException
     *             thrown when the database is not reachable
     * 
     */
    void sendMessagesToMailbox(LogboekRegel regel, List<Bericht> messages) throws VoaException;

    /**
     * Receive and store the messages from mailbox.
     * 
     * @param mailbox
     *            Mailbox from which the messages are received and stored
     * @param regel
     *            logregel waarin opgeslagen wordt hoeveel berichten er zijn binnen gekomen
     * @throws VoaException
     *             thrown when either the listMessages contains errors or the database is not reachable
     */
    void receiveAndStoreMessagesFromMailbox(Mailbox mailbox, LogboekRegel regel) throws VoaException;

    /**
     * Haalt de berichten op uit de database die verstuurd moeten worden.
     * 
     * @param mailbox
     *            Mailbox van een gemeente waar de berichten van verzameld moeten worden
     * @return Lijst met berichten die verstuurd moeten worden
     */
    List<Bericht> getMessagesToSend(Mailbox mailbox);

    /**
     * Verstuurd de ontvangen berichten naar de ESB.
     * 
     * @param messagesToSend
     *            de berichten die naar de ESB verstuurd moeten worden
     */
    void sendMessagesToEsb(final List<Bericht> messagesToSend);

    /**
     * Haalt de berichten op uit de database die naar de ESB gestuurd moeten worden.
     * 
     * @param limit
     *            Limit het aantal berichten dat verzonden moet worden.
     * @return berichten De berichten die naar de ESB verstuurd moeten worden.
     */
    List<Bericht> getMessagesToSendToQueue(final int limit);
}
