/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.tools.mailbox.MailboxServerWrapper;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import nl.bzk.migratiebrp.voisc.mailbox.client.impl.SslConnectionFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Let op: deze test is afhankelijk van de functionaliteit die in de mailbox simulator is gebouwd.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-mailbox.xml", "/voisc-mailbox-client.xml"})
public class DelRepTest {

    @Inject
    private MailboxServerWrapper mailboxServerWrapper;

    @Inject
    private MailboxClient client;

    @Inject
    private SslConnectionFactory sslConnectionFactory;

    @Before
    public void setupSsl() throws Exception {
        sslConnectionFactory.setKeyPassword("changeit");
        mailboxServerWrapper.getMailboxServer().getMailboxFactory().deleteAll();
    }

    @Test
    public void testDelRep() throws VoaException {
        final nl.bzk.migratiebrp.voisc.database.entities.Mailbox mailbox1703 = new nl.bzk.migratiebrp.voisc.database.entities.Mailbox();
        mailbox1703.setMailboxnr("1703010");
        mailbox1703.setMailboxpwd("qwer1234");

        // Connect
        final Connection connection = client.connect();

        // Logon 1703
        client.logOn(connection, mailbox1703);

        // Geen berichten voor 1703
        List<Integer> messageIds = new ArrayList<>();
        client.listMessages(connection, 0, messageIds, 141, "001", SpdConstants.Priority.defaultValue());
        Assert.assertEquals(0, messageIds.size());

        // Stuur bericht van 1703 naar 1704
        final Bericht bericht = new Bericht();
        bericht.setBerichtInhoud("INHOUD");
        bericht.setMessageId("BERICHT-0001");
        bericht.setCorrelationId(null);
        bericht.setRecipient("1704010");

        client.putMessage(connection, bericht);

        // Logon 1704
        final nl.bzk.migratiebrp.voisc.database.entities.Mailbox mailbox1704 = new nl.bzk.migratiebrp.voisc.database.entities.Mailbox();
        mailbox1704.setMailboxnr("1704010");
        mailbox1704.setMailboxpwd("qwer1234");
        client.logOn(connection, mailbox1704);

        // Stuur starep
        final Bericht starep = new Bericht();
        starep.setBerichtInhoud("DelRep;1247;" + bericht.getDispatchSequenceNumber());
        starep.setMessageId("BERICHT-0002");
        starep.setCorrelationId("BERICHT-0001");
        starep.setRecipient("1703010");

        client.putMessage(connection, starep);

        // Logon 1703
        client.logOn(connection, mailbox1703);

        // Een bericht voor 1703
        messageIds = new ArrayList<>();
        client.listMessages(connection, 0, messageIds, 141, "001", SpdConstants.Priority.defaultValue());
        Assert.assertEquals(1, messageIds.size());

        // Get message
        final Bericht ontvangen = client.getMessage(connection, messageIds.get(0));
        Assert.assertEquals(null, ontvangen.getBerichtInhoud());
        Assert.assertEquals(null, ontvangen.getCorrelationId());
        // DispatchSequenceNumber moet wijzen naar het msSequenceNumber van het bericht wat 'niet' verstuurd kon worden
        Assert.assertEquals(bericht.getDispatchSequenceNumber(), ontvangen.getDispatchSequenceNumber());
        Assert.assertEquals(null, ontvangen.getId());
        Assert.assertEquals(null, ontvangen.getMessageId());
        Assert.assertEquals("1247", ontvangen.getNonDeliveryReason());
        Assert.assertEquals(null, ontvangen.getNotificationType());
        Assert.assertEquals("1704010", ontvangen.getOriginator());
        Assert.assertEquals(null, ontvangen.getRecipient());
        Assert.assertEquals(false, ontvangen.getRequestNonReceiptNotification());
        Assert.assertEquals(null, ontvangen.getStatus());
        Assert.assertEquals(null, ontvangen.getTijdstipInVerwerking());
        Assert.assertNotNull(ontvangen.getTijdstipMailbox());
        Assert.assertEquals(null, ontvangen.getTijdstipOntvangst());
        Assert.assertEquals(null, ontvangen.getTijdstipVerzonden());
        Assert.assertEquals(null, ontvangen.getVersion());
        Assert.assertEquals(null, ontvangen.getVerwerkingsCode());

        client.logOff(connection);
    }
}
