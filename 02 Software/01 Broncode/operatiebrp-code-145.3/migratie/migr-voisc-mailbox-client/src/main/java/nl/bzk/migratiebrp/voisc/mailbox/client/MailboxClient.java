/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client;

import java.util.List;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

/**
 * Mailbox client.
 */
public interface MailboxClient {

    /**
     * Log off from the MailBox and disconnect from the MailBoxServer.
     * @param connection De SSL connectie met de mailboxserver
     * @throws VoaException protocol exception
     */
    void logOff(Connection connection) throws VoaException;

    /**
     * Connect via SSL with the MailBoxServer.
     * @return De gemaakte SSL connectie met de mailboxserver
     */
    Connection connect();

    /**
     * Logon to the given MailBox.
     * @param connection De SSL connectie met de mailboxserver
     * @param mailbox the mailbox to logon to
     * @throws VoaException protocol exception
     */
    void logOn(Connection connection, Mailbox mailbox) throws VoaException;

    /**
     * Sends the given messsage to the MailBoxServer.
     * @param connection De SSL connectie met de mailboxserver
     * @param bericht the message which will be send
     * @throws VoaException protocol exception
     */
    void putMessage(Connection connection, Bericht bericht) throws VoaException;

    /**
     * Read a message from the MailBox.
     * @param connection De SSL connectie met de mailboxserver
     * @param msSequenceNumber number of the message which will be read
     * @return the message which corresponds to the msSequenceNumber
     * @throws VoaException wordt gegooid als er een fout optreedt bij het onvangen van een bericht
     */
    Bericht getMessage(Connection connection, int msSequenceNumber) throws VoaException;

    /**
     * Changes the password of the MailBox.
     * @param connection De SSL connectie met de mailboxserver
     * @param mailbox The mailbox which password should be changed
     * @param newPassword the new password
     * @throws VoaException protocol exception
     */
    void changePassword(Connection connection, Mailbox mailbox, String newPassword) throws VoaException;

    /**
     * List the messages on the MailBox.
     * @param connection De SSL connectie met de mailboxserver
     * @param nextSequenceNr the sequence number from which to list.
     * @param sequenceNumbers A list with sequenceNumbers returned from the MailBoxServer
     * @param listLimitNr limit for the returning sequenceNumbers
     * @param msStatus Status filter. Which status the message could have
     * @param prio Priority filter. Which prio the message could have
     * @return the number of messages listed and added tot the sequenceNumbers attribute
     * @throws VoaException protocol exception
     */
    int listMessages(
            Connection connection,
            int nextSequenceNr,
            List<Integer> sequenceNumbers,
            int listLimitNr,
            String msStatus,
            SpdConstants.Priority prio) throws VoaException;

}
