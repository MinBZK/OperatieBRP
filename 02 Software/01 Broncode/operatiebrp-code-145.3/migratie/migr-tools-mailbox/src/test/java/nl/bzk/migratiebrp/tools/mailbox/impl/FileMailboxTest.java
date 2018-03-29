/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class FileMailboxTest {

    @Test
    public void testBerichtMetLineFeed() throws Exception {
        final File dataDirectory = new File("target/FileMailboxTest/data");
        dataDirectory.mkdirs();
        final FileMailbox subject = new FileMailbox(new DummyFactory(), dataDirectory, "test");
        try {
            subject.open();
            subject.getEntries().clear();
            subject.save();
            subject.close();

            subject.open();
            Assert.assertEquals(0, subject.getEntries().size());

            final MailboxEntry entry = new MailboxEntry();
            entry.setMessageId("msg-id");
            entry.setMesg("Text met \nenter");

            subject.addEntry(entry);
            subject.save();
            subject.close();

            subject.open();
            Assert.assertEquals(1, subject.getEntries().size());

            final MailboxEntry testEntry = subject.getEntries().values().iterator().next();
            Assert.assertEquals("Text met \nenter", testEntry.getMesg());
            Assert.assertEquals(42, testEntry.getMsSequenceId());
        } finally {
            subject.close();
        }
    }

    private static final class DummyFactory implements MailboxFactory {

        @Override
        public Mailbox getMailbox(final String mailboxnr) {
            throw new UnsupportedOperationException();
        }

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory#getNextMsSequenceNr()
         */
        @Override
        public int getNextMsSequenceNr() {
            return 42;
        }

        @Override
        public void deleteAll() throws MailboxException {
            throw new UnsupportedOperationException();
        }
    }

}
