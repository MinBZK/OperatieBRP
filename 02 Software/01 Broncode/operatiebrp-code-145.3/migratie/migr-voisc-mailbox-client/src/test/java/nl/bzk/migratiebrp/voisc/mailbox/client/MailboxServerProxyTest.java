/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.tools.mailbox.MailboxServer;
import nl.bzk.migratiebrp.tools.mailbox.MailboxServerWrapper;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxEntry;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import nl.bzk.migratiebrp.voisc.mailbox.client.impl.SslConnectionFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-mailbox.xml", "/voisc-mailbox-client.xml"})
public class MailboxServerProxyTest {

    @Inject
    private MailboxServerWrapper mailboxServerWrapper;

    @Inject
    private MailboxClient client;

    @Inject
    private SslConnectionFactory sslConnectionFactory;

    @Before
    public void setupSsl() throws IOException {
        sslConnectionFactory.setKeyPassword("changeit");
    }

    @Before
    public void setupMailbox() throws MailboxException {
        final MailboxServer mailboxServer = mailboxServerWrapper.getMailboxServer();
        final MailboxFactory mailboxFactory = mailboxServer.getMailboxFactory();
        mailboxFactory.deleteAll();

        // Setup message on 1703
        final Mailbox mailbox1703 = mailboxFactory.getMailbox("1703010");
        try {
            mailbox1703.open();

            mailbox1703.setPassword("qwer1234");
            final MailboxEntry messageTo1703 = new MailboxEntry();
            messageTo1703.setOriginatorOrRecipient("1901010");
            messageTo1703.setMessageId("000000000001");
            messageTo1703.setMesg("00000000Lq010000190101701100105054783237");
            messageTo1703.setStatus(MailboxEntry.STATUS_NEW);
            mailbox1703.addEntry(messageTo1703);

            mailbox1703.save();
        } finally {
            mailbox1703.close();
        }

        // Setup message on 1704
        final Mailbox mailbox1704 = mailboxFactory.getMailbox("1704010");
        mailbox1704.open();

        mailbox1704.setPassword("qwer1234");
        final MailboxEntry messageTo1704 = new MailboxEntry();
        messageTo1704.setOriginatorOrRecipient("1902010");
        messageTo1704.setMessageId("000000000001");
        messageTo1704.setMesg("00000000Lq010000190101701100105054783237");
        messageTo1704.setStatus(MailboxEntry.STATUS_NEW);
        mailbox1704.addEntry(messageTo1704);

        mailbox1704.save();
        mailbox1704.close();
    }

    /**
     * Happy flow.
     */
    @Test
    public void test() throws VoaException {
        final nl.bzk.migratiebrp.voisc.database.entities.Mailbox mailbox1703 = new nl.bzk.migratiebrp.voisc.database.entities.Mailbox();
        mailbox1703.setMailboxnr("1703010");
        mailbox1703.setMailboxpwd("qwer1234");

        // Connect
        final Connection connection = client.connect();

        // Logon
        client.logOn(connection, mailbox1703);

        // List messages (expected 1)
        List<Integer> messageIds = new ArrayList<>();
        client.listMessages(connection, 0, messageIds, 141, "001", SpdConstants.Priority.defaultValue());
        Assert.assertEquals(1, messageIds.size());

        // Get message
        final Bericht bericht = client.getMessage(connection, messageIds.get(0));
        Assert.assertEquals("00000000Lq010000190101701100105054783237", bericht.getBerichtInhoud());

        // List messages (expected 0)
        messageIds = new ArrayList<>();
        client.listMessages(connection, 0, messageIds, 141, "001", SpdConstants.Priority.defaultValue());
        Assert.assertEquals(0, messageIds.size());

        // Log off
        client.logOff(connection);
    }
}
