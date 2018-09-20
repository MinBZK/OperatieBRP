/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.jms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.repository.MailboxRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class QueueServiceTest {

    private static final String RECIPIENT = "1904010";
    private static final String ORIGINATOR = "1901010";
    private static final String ESB_CORR_ID = "712190a1-38d9-11e2-81c1-0800200c9a66";
    private static final String BODY_INHOUD = "blablabla";

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private Destination vospgOntvangst;

    @Mock
    private MailboxRepository mailboxRepo;

    @InjectMocks
    private QueueServiceImpl queueService;

    private Bericht bericht;

    @Before
    public void setUp() {
        bericht = new Bericht();
        bericht.setEsbMessageId("438057349857349");
        bericht.setRecipient(RECIPIENT);
        bericht.setOriginator(ORIGINATOR);
        bericht.setEsbCorrelationId(ESB_CORR_ID);
        bericht.setBerichtInhoud(BODY_INHOUD);
    }

    @Test
    public void fillMessage() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Message mesg = new TestOkMessage();
        final String origGemeentecode = ORIGINATOR.substring(0, 4);
        final String recipGemeentecode = RECIPIENT.substring(0, 4);

        final Mailbox origMb = new Mailbox();
        origMb.setGemeentecode(origGemeentecode);
        final Mailbox recipMb = new Mailbox();
        recipMb.setGemeentecode(recipGemeentecode);

        Mockito.when(mailboxRepo.getMailboxByNummer(RECIPIENT)).thenReturn(recipMb);
        Mockito.when(mailboxRepo.getMailboxByNummer(ORIGINATOR)).thenReturn(origMb);

        final Method method = queueService.getClass().getDeclaredMethod("fillMessage", Bericht.class, Message.class);
        method.setAccessible(true);
        method.invoke(queueService, bericht, mesg);
        try {
            Assert.assertNotNull(bericht.getEsbMessageId());
            final String esbMesgId = bericht.getEsbMessageId();
            Assert.assertEquals(origGemeentecode, mesg.getStringProperty(JMSConstants.BERICHT_ORIGINATOR));
            Assert.assertEquals(recipGemeentecode, mesg.getStringProperty(JMSConstants.BERICHT_RECIPIENT));
            Assert.assertEquals(esbMesgId, mesg.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
            Assert.assertEquals(ESB_CORR_ID, mesg.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));
        } catch (final JMSException e) {
            Assert.fail("Geen JMSException verwacht");
        }
    }

    @Test
    public void sendMessage() {
        queueService.sendMessage(bericht);
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(Matchers.any(Destination.class),
                Matchers.any(MessageCreator.class));
    }
}
