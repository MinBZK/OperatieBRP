/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import java.util.List;

/**
 * Mailbox.
 */
public interface Mailbox {

    /** Status: OPEN. */
    int STATUS_OPEN = 0;

    /** Status: TEMP_BLOCKED. */
    int STATUS_TEMPORARY_BLOCKED = 1;

    /** Status: NOT_PRESENT. */
    int STATUS_NOT_PRESENT = 2;

    /**
     * Geef de waarde van mailboxnr.
     *
     * @return mailboxnummer
     */
    String getMailboxnr();

    /**
     * Geef de waarde van status.
     *
     * @return status
     */
    int getStatus();

    /**
     * Zet status.
     *
     * @param status
     *            status
     */
    void setStatus(int status);

    /**
     * Check the password.
     *
     * @param password
     *            password
     * @return true, if password is correct (enough)
     */
    boolean checkPassword(String password);

    /**
     * Zet the password.
     *
     * @param password
     *            password
     */
    void setPassword(String password);

    /**
     * Open the mailbox.
     *
     * @throws MailboxException
     *             bij fouten
     */
    void open() throws MailboxException;

    /**
     * Clear all mailbox entries.
     *
     * @throws MailboxException
     *             bij fouten
     */
    void clear() throws MailboxException;

    /**
     * Save the mailbox.
     *
     * @throws MailboxException
     *             bij fouten
     */
    void save() throws MailboxException;

    /**
     * Close the mailbox.
     */
    void close();

    /**
     * Add an mailbox entry.
     *
     * @param entry
     *            mailbox entry
     */
    void addEntry(final MailboxEntry entry);

    /**
     * Get an mailbox entry.
     *
     * @param msSequenceNr
     *            ms sequence number
     * @return mailbox entry
     */
    MailboxEntry getEntry(final Integer msSequenceNr);

    /**
     * Filter the inbox.
     *
     * @param msStatus
     *            status
     * @param startAtMsSequenceId
     *            start at ms sequence number
     * @param limit
     *            limit
     * @return {@link FilterResult filter result}
     */
    FilterResult filterInbox(final String msStatus, final int startAtMsSequenceId, final int limit);

    /**
     * Filter result.
     */
    interface FilterResult {

        /**
         * Geef de waarde van entries.
         *
         * @return entries
         */
        List<MailboxEntry> getEntries();

        /**
         * Geef de waarde van next ms sequence id.
         *
         * @return Next ms sequence number to search from
         */
        int getNextMsSequenceId();
    }

}
