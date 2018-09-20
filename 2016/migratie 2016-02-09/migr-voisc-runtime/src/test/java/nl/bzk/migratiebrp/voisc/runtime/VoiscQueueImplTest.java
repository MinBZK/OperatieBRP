/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.BerichtRepository;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class VoiscQueueImplTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock(name = "vospgOntvangst")
    // Moet naam van variabele zijn
    private Destination vospgOntvangst;

    @Mock(name = "archivering")
    // Moet naam van variabele zijn
    private Destination archivering;

    @Mock
    private MailboxRepository mailboxRepo;
    @Mock
    protected BerichtRepository berichtRepository;

    @Mock
    private Session session;
    @Mock
    private TextMessage textMessage;

    @InjectMocks
    private VoiscQueueImpl subject;

    @Test
    public void test() throws JMSException, VoiscQueueException {
        // Setup (part 1)
        final Bericht bericht = new Bericht();
        bericht.setMessageId("BerichtRef");
        bericht.setCorrelationId("CorrelatieRef");
        bericht.setRecipient("0518010");
        bericht.setOriginator("3000250");
        bericht.setBerichtInhoud("BerichtInhoud");

        // Execute (part 1)
        subject.verstuurBerichtNaarIsc(bericht);

        // Verify (part 1)
        final ArgumentCaptor<MessageCreator> messageCreatorCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(Matchers.eq(vospgOntvangst), messageCreatorCaptor.capture());

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();

        // Setup (part 2)
        final Mailbox mailbox0518010 = new Mailbox();
        mailbox0518010.setInstantietype(Mailbox.INSTANTIETYPE_GEMEENTE);
        mailbox0518010.setInstantiecode(518);

        final Mailbox mailbox3000250 = new Mailbox();
        mailbox3000250.setInstantietype(Mailbox.INSTANTIETYPE_CENTRALE_VOORZIENING);
        mailbox3000250.setInstantiecode(3000250);

        Mockito.when(mailboxRepo.getMailboxByNummer("0518010")).thenReturn(mailbox0518010);
        Mockito.when(mailboxRepo.getMailboxByNummer("3000250")).thenReturn(mailbox3000250);

        Mockito.when(session.createTextMessage("BerichtInhoud")).thenReturn(textMessage);

        // Execute (part 2)
        final Message result = messageCreator.createMessage(session);

        // Verify (part 2)
        Assert.assertSame(textMessage, result);

        Mockito.verify(session, Mockito.times(1)).createTextMessage("BerichtInhoud");
        Mockito.verify(textMessage, Mockito.times(1)).setStringProperty(JMSConstants.BERICHT_ORIGINATOR, "3000250");
        Mockito.verify(textMessage, Mockito.times(1)).setStringProperty(JMSConstants.BERICHT_RECIPIENT, "0518");
        Mockito.verify(textMessage, Mockito.times(1)).setStringProperty(JMSConstants.BERICHT_REFERENTIE, "BerichtRef");
        Mockito.verify(textMessage, Mockito.times(1)).setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, "CorrelatieRef");
    }

    @Test(expected = VoiscQueueException.class)
    public void testJmsException() throws VoiscQueueException {
        Mockito.doThrow(new MyJmsException("Test")).when(jmsTemplate).send(Matchers.eq(vospgOntvangst), Matchers.any(MessageCreator.class));

        // Execute
        subject.verstuurBerichtNaarIsc(new Bericht());
    }

    private static final class MyJmsException extends JmsException {
        private static final long serialVersionUID = 1L;

        public MyJmsException(final String message) {
            super(message);
        }
    }
}
