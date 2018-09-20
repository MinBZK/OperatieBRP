/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox.FilterResult;
import org.junit.Assert;
import org.junit.Test;

public class MailboxTest {

    @Test
    public void testFileMailbox() throws MailboxException, InterruptedException {
        testMailbox(new FileMailboxFactory("target/MailboxTest/csv"));
    }

    @Test
    public void testHsqldbMailbox() throws MailboxException, InterruptedException {
        final HsqldbMailboxFactory factory = new HsqldbMailboxFactory("target/MailboxTest/db");
        testMailbox(factory);
        factory.destroy();
    }

    @Test
    public void testMemoryMailbox() throws MailboxException, InterruptedException {
        Thread.sleep(1000l);
        testMailbox(new MemoryMailboxFactory());
        Thread.sleep(1000l);
    }

    private void testMailbox(final MailboxFactory mailboxFactory) throws MailboxException {
        mailboxFactory.deleteAll();

        final Mailbox mailbox1234567 = mailboxFactory.getMailbox("1234567");
        Assert.assertEquals(0, mailbox1234567.getStatus());
        final MailboxEntry entry = new MailboxEntry();
        entry.setMessageId("MSG-ID-1");
        entry.setOriginatorOrRecipient("Piet");
        entry.setMesg("Berichtinhoud");
        entry.setStatus(MailboxEntry.STATUS_NEW);

        mailbox1234567.open();
        mailbox1234567.addEntry(entry);
        mailbox1234567.save();
        mailbox1234567.close();

        Assert.assertNotNull(entry.getMsSequenceId());

        mailbox1234567.open();
        final FilterResult filter = mailbox1234567.filterInbox("012", 0, 10);
        mailbox1234567.save();
        mailbox1234567.close();
        Assert.assertEquals(1, filter.getEntries().size());
        Assert.assertEquals(entry, filter.getEntries().get(0));

        mailbox1234567.open();
        final MailboxEntry readEntry = mailbox1234567.getEntry(entry.getMsSequenceId());
        mailbox1234567.save();
        mailbox1234567.close();
        Assert.assertEquals(entry, readEntry);

    }
}
