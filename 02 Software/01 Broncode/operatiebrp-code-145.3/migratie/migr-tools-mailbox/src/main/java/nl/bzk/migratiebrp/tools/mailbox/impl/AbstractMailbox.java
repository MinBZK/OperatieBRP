/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Basis functionaliteit van een mailbox.
 */
public abstract class AbstractMailbox implements Mailbox {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final MailboxFactory factory;
    private final String mailboxnr;
    private MailboxStatus status;
    private String password;

    private SortedMap<Integer, MailboxEntry> entries = new ConcurrentSkipListMap<>();

    /**
     * Constructor.
     * @param mailboxnr mailbox nummer
     * @param factory mailbox factory
     */
    protected AbstractMailbox(final MailboxFactory factory, final String mailboxnr) {
        this.factory = factory;
        this.mailboxnr = mailboxnr;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox#getMailboxnr()
     */
    @Override
    public final String getMailboxnr() {
        return mailboxnr;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox#getStatus()
     */
    @Override
    public final MailboxStatus getStatus() {
        return status;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox#setStatus(int)
     */
    @Override
    public final void setStatus(final MailboxStatus status) {
        this.status = status;
    }

    @Override
    public final boolean checkPassword(final String givenPassword) {
        return password == null || "".equals(password) || password.equals(givenPassword);
    }

    /**
     * Geef de waarde van password.
     * @return password
     */
    protected final String getPassword() {
        return password == null ? "" : password;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox#setPassword(java.lang.String)
     */
    @Override
    public final void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public final void addEntry(final MailboxEntry entry) {
        final int msSequenceNr = factory.getNextMsSequenceNr();
        entry.setMsSequenceNr(msSequenceNr);
        entries.put(msSequenceNr, entry);
    }

    /**
     * Geef de waarde van entries.
     * @return entries
     */
    protected final Map<Integer, MailboxEntry> getEntries() {
        return entries;
    }

    /**
     * Zet de entries van de mailbox.
     * @param entries map met als key de MsSequenceNr en als value de MailboxEntry
     */
    protected final void setEntries(final SortedMap<Integer, MailboxEntry> entries) {
        this.entries = entries;
    }

    @Override
    public final void clear() {
        entries.clear();
    }

    /**
     * Haal een bericht op uit de mailbox.
     * @param msSequenceNr sequence nr
     * @return entry, null als niet gevonden
     */
    @Override
    public final MailboxEntry getEntry(final Integer msSequenceNr) {
        if (entries.containsKey(msSequenceNr)) {
            final MailboxEntry entry = entries.get(msSequenceNr);
            entry.setStatus(MailboxEntry.STATUS_PROCESSED);
            return entry;
        }
        return null;
    }

    /**
     * Filter mailbox obv status.
     * @param msStatus status
     * @param startAtMsSequenceId zoeken vanaf (inclusief) MsSequenceId
     * @param limit maximum aantal
     * @return mailbox entries
     */
    @Override
    public final FilterResult filterInbox(final String msStatus, final int startAtMsSequenceId, final int limit) {
        LOGGER.debug("[Mailbox {}] filterInbox(msStatus={}, startAtMsSequence={}, limit={}) ", new Object[]{mailboxnr,
                msStatus,
                startAtMsSequenceId,
                limit,});
        final List<MailboxEntry> filteredList = new ArrayList<>();
        final boolean fetchNew = msStatus.contains(Integer.toString(MailboxEntry.STATUS_NEW));
        final boolean fetchListed = msStatus.contains(Integer.toString(MailboxEntry.STATUS_LISTED));
        final boolean fetchProcessed = msStatus.contains(Integer.toString(MailboxEntry.STATUS_PROCESSED));

        int nextFilteredMSSequenceId = 0;
        for (final MailboxEntry entry : getEntries().values()) {
            if (entry.getMsSequenceId() >= startAtMsSequenceId) {
                if (filteredList.size() == limit) {
                    nextFilteredMSSequenceId = entry.getMsSequenceId();
                    break;
                }

                if (fetchNew && MailboxEntry.STATUS_NEW == entry.getStatus() || (fetchProcessed && MailboxEntry.STATUS_PROCESSED == entry.getStatus())) {
                    filteredList.add(entry);
                    entry.setStatus(MailboxEntry.STATUS_LISTED);
                } else if (fetchListed && MailboxEntry.STATUS_LISTED == entry.getStatus()) {
                    filteredList.add(entry);
                }
            }
        }

        LOGGER.debug("[Mailbox {}] filtering: size={}, nextSequenceId={} ", mailboxnr, filteredList.size(), nextFilteredMSSequenceId);
        return new FilterResultImpl(filteredList, nextFilteredMSSequenceId);
    }

    /**
     * FilterResult implementatie.
     */
    public static final class FilterResultImpl implements FilterResult {
        private final List<MailboxEntry> entries;
        private final int nextMsSequenceId;

        /**
         * Constructor.
         * @param entries gevonden entries
         * @param nextMsSequenceId volgende MsSequenceId om te gebruiken om te zoeken
         */
        protected FilterResultImpl(final List<MailboxEntry> entries, final int nextMsSequenceId) {
            super();
            this.entries = entries;
            this.nextMsSequenceId = nextMsSequenceId;
        }

        /* (non-Javadoc)
         * @see nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox.FilterResult#getEntries()
         */
        @Override
        public List<MailboxEntry> getEntries() {
            return entries;
        }

        /* (non-Javadoc)
         * @see nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox.FilterResult#getNextMsSequenceId()
         */
        @Override
        public int getNextMsSequenceId() {
            return nextMsSequenceId;
        }

    }

}
