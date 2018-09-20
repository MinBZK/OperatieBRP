/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;
import nl.moderniseringgba.isc.voisc.mailbox.VoiscDbProxy;
import nl.moderniseringgba.isc.voisc.mailbox.impl.SslConnectionImpl;
import nl.moderniseringgba.isc.voisc.mailbox.simulation.Mailbox;
import nl.moderniseringgba.isc.voisc.mailbox.simulation.MailboxEntry;
import nl.moderniseringgba.isc.voisc.mailbox.simulation.MailboxFactory;
import nl.moderniseringgba.isc.voisc.mailbox.simulation.MailboxServer;
import nl.moderniseringgba.isc.voisc.mailbox.simulation.MailboxServerWrapper;
import nl.moderniseringgba.isc.voisc.quartz.ToQueueJob;
import nl.moderniseringgba.isc.voisc.quartz.VoaJob;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockrunner.mock.jms.MockQueue;
import com.mockrunner.mock.jms.MockTextMessage;

/**
 * Testcase waarbij we willen controleren dat bij het ontvangen van twee (verschillende) berichten van twee
 * (verschillende) gemeenten met hetzelfde BREF nummer, de VOA hier unieke esb message id's aan toe kent. Bij het
 * ontvangen van herhaalberichten van de gemeenten dienen echter wel dezelfde esb message id's gebruikt te worden.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "MessageIdVoaServiceTest-server.xml", "MessageIdVoaServiceTest-beans.xml" })
public class MessageIdVoaServiceTest {

    @Inject
    private MailboxServerWrapper mailboxServerWrapper;

    @Inject
    private VoiscDbProxy voiscDbProxy;

    @Inject
    private GemeenteRepository gemeenteRepository;

    @Inject
    private VoaService voaService;

    @Inject
    private SslConnectionImpl sslConnection;

    @Inject
    @Named("vospgOntvangstQueue")
    private MockQueue vospgOntvangstQueue;

    @Test
    public void testConnect() throws Exception {
        // Setup message on 1703
        final MailboxServer mailboxServer = mailboxServerWrapper.getMailboxServer();
        final MailboxFactory mailboxFactory = mailboxServer.getMailboxFactory();
        final Mailbox mailbox1703 = mailboxFactory.loadMailbox("1703010", false);
        final MailboxEntry messageTo1703 = new MailboxEntry();
        messageTo1703.setMsSequenceNr(1);
        messageTo1703.setOriginatorOrRecipient("1901010");
        messageTo1703.setMessageId("000000000001");
        messageTo1703.setMesg("00000000Lq010000190101701100105054783237");
        messageTo1703.setStatus(MailboxEntry.STATUS_NEW);
        mailbox1703.getInbox().put(messageTo1703.getMsSequenceId(), messageTo1703);
        mailboxFactory.storeMailbox(mailbox1703);

        // Setup message on 1704
        final Mailbox mailbox1704 = mailboxFactory.loadMailbox("1704010", false);
        final MailboxEntry messageTo1704 = new MailboxEntry();
        messageTo1704.setMsSequenceNr(1);
        messageTo1704.setOriginatorOrRecipient("1902010");
        messageTo1704.setMessageId("000000000001");
        messageTo1704.setMesg("00000000Lq010000190101701100105054783237");
        messageTo1704.setStatus(MailboxEntry.STATUS_NEW);
        mailbox1704.getInbox().put(messageTo1704.getMsSequenceId(), messageTo1704);
        mailboxFactory.storeMailbox(mailbox1704);

        // Setup job
        final JobExecutionContext jec = Mockito.mock(JobExecutionContext.class);
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("voiscDbProxy", voiscDbProxy);
        jobDataMap.put("gemeenteRepo", gemeenteRepository);
        jobDataMap.put("voiscService", voaService);
        Mockito.when(jec.getMergedJobDataMap()).thenReturn(jobDataMap);

        // Execute job om mailbox uit te lezen
        final VoaJob voaJob = new VoaJob();
        voaJob.execute(jec);

        final ToQueueJob queueJob = new ToQueueJob();
        queueJob.execute(jec);

        // Check messages on queue
        final Set<String> messageIds = new HashSet<String>();
        Assert.assertEquals(2, vospgOntvangstQueue.getCurrentMessageList().size());
        System.out.println("\n\nONTVANGST-QUEUE:");
        for (final Object o : vospgOntvangstQueue.getCurrentMessageList()) {
            final MockTextMessage message = (MockTextMessage) o;
            final String messageId = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            messageIds.add(messageId);
            System.out.println("Message id: " + messageId);
        }
        vospgOntvangstQueue.clear();

        Assert.assertEquals("De berichten hebben niet verschillende UUIDs gekregen", 2, messageIds.size());

        // Herhalingsbericht van gemeente 1703
        mailbox1703.getInbox().clear();
        final MailboxEntry herhaalMessageTo1703 = new MailboxEntry();
        herhaalMessageTo1703.setMsSequenceNr(2);
        herhaalMessageTo1703.setOriginatorOrRecipient("1901010");
        herhaalMessageTo1703.setMessageId("000000000001");
        herhaalMessageTo1703.setMesg("00000000Lq010000190101701100105054783237");
        herhaalMessageTo1703.setStatus(MailboxEntry.STATUS_NEW);
        mailbox1703.getInbox().put(herhaalMessageTo1703.getMsSequenceId(), herhaalMessageTo1703);
        mailboxFactory.storeMailbox(mailbox1703);

        // Mailbox van 1704 leegmaken
        mailbox1704.getInbox().clear();
        mailboxFactory.storeMailbox(mailbox1704);

        // Execute job om mailbox uit te lezen
        voaJob.execute(jec);
        queueJob.execute(jec);

        // Check messages on queue
        Assert.assertEquals(1, vospgOntvangstQueue.getCurrentMessageList().size());
        System.out.println("\n\nONTVANGST-QUEUE:");
        for (final Object o : vospgOntvangstQueue.getCurrentMessageList()) {
            final MockTextMessage message = (MockTextMessage) o;
            final String messageId = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            messageIds.add(messageId);
            System.out.println("Message id: " + messageId);
        }

        Assert.assertEquals("Het herhaalbericht heeft niet hetzelfde UUID gekregen.", 2, messageIds.size());
    }

    @Before
    public void createPassw() throws IOException {
        sslConnection.setPassword("changeit");
    }

}
