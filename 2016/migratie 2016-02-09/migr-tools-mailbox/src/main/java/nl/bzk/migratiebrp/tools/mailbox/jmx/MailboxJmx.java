/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.jmx;

import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;

/**
 * JMX-interface om de mailbox in te kunnen stellen.
 */
public interface MailboxJmx {

    /**
     * Leegt alle mailboxen.
     * 
     * @throws MailboxException
     *             als er een fout optreedt in de mailboxen.
     */
    void mailboxenLegen() throws MailboxException;

    /**
     * Leegt de meegegeven mailbox.
     *
     * @param mailboxnr
     *            mailbox nummer welke geleegd moet worden
     * @throws MailboxException
     *             als er een fout optreedt in de mailboxen.
     */
    void leegMailbox(String mailboxnr) throws MailboxException;

    /**
     * Zet de mailbox op een bepaalde status.
     * 
     * @param mailboxnr
     *            mailbox nummer welke geleegd moet worden
     *
     * @param status
     *            de status waar de mailbox op ingesteld moet worden
     * @throws MailboxException
     *             als er een fout optreedt in de mailboxen.
     */
    void setMailboxStatus(String mailboxnr, String status) throws MailboxException;

    /**
     * Zet het wachtwoord van de meegegeven mailbox.
     * 
     * @param mailboxnr
     *            mailbox nummer welke geleegd moet worden
     * @param wachtwoord
     *            het wachtwoord dat ingesteld moet worden
     * @throws MailboxException
     *             als er een fout optreedt in de mailboxen.
     */
    void setMailboxWachtwoord(String mailboxnr, String wachtwoord) throws MailboxException;

}
