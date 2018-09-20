/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.List;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;

/**
 * 
 */
public interface MailboxClient {

    /**
     * Log off from the MailBox and disconnect from the MailBoxServer.
     * 
     * @throws SpdProtocolException
     *             protocol exception
     */
    void logOff() throws SpdProtocolException;

    /**
     * Connect via SSL with the MailBoxServer.
     */
    void connect();

    /**
     * Logon to the given MailBox.
     * 
     * @param mailbox
     *            the mailbox to logon to
     * @throws SpdProtocolException
     *             protocol exception
     * @throws MailboxServerPasswordException
     *             invalid protocol
     */
    void logOn(Mailbox mailbox) throws SpdProtocolException, MailboxServerPasswordException;

    /**
     * Sends the given messsage to the MailBoxServer.
     * 
     * @param bericht
     *            the message which will be send
     * @throws SpdProtocolException
     *             protocol exception
     */
    void putMessage(final Bericht bericht) throws SpdProtocolException;

    /**
     * Read a message from the MailBox.
     * 
     * @param msSequenceNumber
     *            number of the message which will be read
     * @return the message which corresponds to the msSequenceNumber
     * @throws SpdProtocolException
     *             wordt gegooid als er een fout optreedt bij het onvangen van een bericht
     */
    Bericht getMessage(final int msSequenceNumber) throws SpdProtocolException;

    /**
     * Changes the password of the MailBox.
     * 
     * @param mailbox
     *            The mailbox which password should be changed
     * @param newPassword
     *            the new password
     * @throws SpdProtocolException
     *             protocol exception
     * @throws MailboxServerPasswordException
     *             wordt gegooid als het nieuwe wachtwoord niet voldoet of als de mailboxserver 1 van de 2 wachtwoorden
     */
    void changePassword(final Mailbox mailbox, final String newPassword) throws SpdProtocolException, MailboxServerPasswordException;

    /**
     * List the messages on the MailBox.
     * 
     * @param nextSequenceNr
     *            the sequence number from which to list.
     * @param sequenceNumbers
     *            A list with sequenceNumbers returned from the MailBoxServer
     * @param listLimitNr
     *            limit for the returning sequenceNumbers
     * @param msStatus
     *            Status filter. Which status the message could have
     * @param prio
     *            Priority filter. Which prio the message could have
     * @return the number of messages listed and added tot the sequenceNumbers attribute
     * @throws SpdProtocolException
     *             protocol exception
     */
    int listMessages(
        final int nextSequenceNr,
        final List<Integer> sequenceNumbers,
        final int listLimitNr,
        final String msStatus,
        final String prio) throws SpdProtocolException;

}
