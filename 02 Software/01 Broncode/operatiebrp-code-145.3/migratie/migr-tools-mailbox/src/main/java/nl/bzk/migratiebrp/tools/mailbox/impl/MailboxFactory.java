/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

/**
 * Mailbox factory.
 */
public interface MailboxFactory {

    /**
     * Retrieve the mailbox for the given mailboxnr.
     * @param mailboxnr mailboxnr
     * @return mailbox
     */
    Mailbox getMailbox(String mailboxnr);

    /**
     * Get the next ms sequence nr.
     * @return MsSequenceNumber
     */
    int getNextMsSequenceNr();

    /**
     * Clean up all the mailboxes.
     * @throws MailboxException bij fouten
     */
    void deleteAll() throws MailboxException;

}
