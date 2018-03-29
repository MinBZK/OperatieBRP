/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import java.util.Arrays;
import java.util.List;

/**
 * Mailbox.
 */
public interface Mailbox {

    /**
     * Geef de waarde van mailboxnr.
     * @return mailboxnummer
     */
    String getMailboxnr();

    /**
     * Geef de waarde van status.
     * @return status
     */
    MailboxStatus getStatus();

    /**
     * Zet status.
     * @param status status
     */
    void setStatus(MailboxStatus status);

    /**
     * Check the password.
     * @param password password
     * @return true, if password is correct (enough)
     */
    boolean checkPassword(String password);

    /**
     * Zet the password.
     * @param password password
     */
    void setPassword(String password);

    /**
     * Open the mailbox.
     * @throws MailboxException bij fouten
     */
    void open() throws MailboxException;

    /**
     * Clear all mailbox entries.
     * @throws MailboxException bij fouten
     */
    void clear() throws MailboxException;

    /**
     * Save the mailbox.
     * @throws MailboxException bij fouten
     */
    void save() throws MailboxException;

    /**
     * Close the mailbox.
     */
    void close();

    /**
     * Add an mailbox entry.
     * @param entry mailbox entry
     */
    void addEntry(MailboxEntry entry);

    /**
     * Get an mailbox entry.
     * @param msSequenceNr ms sequence number
     * @return mailbox entry
     */
    MailboxEntry getEntry(Integer msSequenceNr);

    /**
     * Filter the inbox.
     * @param msStatus status
     * @param startAtMsSequenceId start at ms sequence number
     * @param limit limit
     * @return {@link FilterResult filter result}
     */
    FilterResult filterInbox(String msStatus, int startAtMsSequenceId, int limit);

    /**
     * Enumeratie van mailbox statusses.
     */
    enum MailboxStatus {
        STATUS_OPEN(0),
        STATUS_TEMPORARY_BLOCKED(1),
        STATUS_NOT_PRESENT(2);

        final int value;

        MailboxStatus(final int value) {
            this.value = value;
        }

        /**
         * Geeft een enum waarde terug aan de hand van een integer value.
         * @param value integer value
         * @return enum waarde
         */
        public static MailboxStatus fromValue(final int value) {
            return Arrays.stream(MailboxStatus.values()).filter(v -> v.getValue() == value).findFirst().orElseThrow(IllegalArgumentException::new);
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Filter result.
     */
    interface FilterResult {

        /**
         * Geef de waarde van entries.
         * @return entries
         */
        List<MailboxEntry> getEntries();

        /**
         * Geef de waarde van next ms sequence id.
         * @return Next ms sequence number to search from
         */
        int getNextMsSequenceId();
    }

}
