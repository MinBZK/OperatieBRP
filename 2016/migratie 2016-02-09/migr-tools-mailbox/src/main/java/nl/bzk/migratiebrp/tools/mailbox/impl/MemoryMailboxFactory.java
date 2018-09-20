/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Factory klasse voor in memory mailbox.
 */
public final class MemoryMailboxFactory implements MailboxFactory {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Map<String, MemoryMailbox> MAILBOXEN = new HashMap<>();
    private final AtomicInteger msSequenceNumber = new AtomicInteger(1);

    @Override
    public Mailbox getMailbox(final String mailboxnr) {
        LOG.info("Starting MEMORY mailbox factory");
        synchronized (MAILBOXEN) {
            if (!MAILBOXEN.containsKey(mailboxnr)) {
                MAILBOXEN.put(mailboxnr, new MemoryMailbox(this, mailboxnr));
            }

            return MAILBOXEN.get(mailboxnr);
        }
    }

    @Override
    public void deleteAll() throws MailboxException {
        MAILBOXEN.clear();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory#getNextMsSequenceNr()
     */
    @Override
    public int getNextMsSequenceNr() {
        return msSequenceNumber.getAndIncrement();
    }

}
