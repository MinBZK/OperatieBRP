/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ArchiveringVerzoekBericht;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.BerichtRepository;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

    @Mock
    private Destination voiscOntvangst;

    @Mock
    private Destination archivering;

    @Mock
    private MailboxRepository mailboxRepo;

    @Mock
    private BerichtRepository berichtRepository;

    @Mock
    private Session session;

    @Mock
    private TextMessage textMessage;

    private VoiscQueueImpl subject;

    @Before
    public void setup() {
        subject = new VoiscQueueImpl(jmsTemplate, voiscOntvangst, archivering, mailboxRepo);
    }

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
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(eq(voiscOntvangst), messageCreatorCaptor.capture());

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();

        // Setup (part 2)
        final Mailbox mailbox0518010 = new Mailbox();
        mailbox0518010.setPartijcode("051801");

        final Mailbox mailbox3000250 = new Mailbox();
        mailbox3000250.setPartijcode("199902");

        Mockito.when(mailboxRepo.getMailboxByNummer("0518010")).thenReturn(mailbox0518010);
        Mockito.when(mailboxRepo.getMailboxByNummer("3000250")).thenReturn(mailbox3000250);

        Mockito.when(session.createTextMessage("BerichtInhoud")).thenReturn(textMessage);

        // Execute (part 2)
        final Message result = messageCreator.createMessage(session);

        // Verify (part 2)
        Assert.assertSame(textMessage, result);

        Mockito.verify(session, Mockito.times(1)).createTextMessage("BerichtInhoud");
        Mockito.verify(textMessage, Mockito.times(1)).setStringProperty(JMSConstants.BERICHT_RECIPIENT, "051801");
        Mockito.verify(textMessage, Mockito.times(1)).setStringProperty(JMSConstants.BERICHT_ORIGINATOR, "199902");
        Mockito.verify(textMessage, Mockito.times(1)).setStringProperty(JMSConstants.BERICHT_REFERENTIE, "BerichtRef");
        Mockito.verify(textMessage, Mockito.times(1)).setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, "CorrelatieRef");
    }

    @Test
    public void testLegeVelden() throws JMSException, VoiscQueueException {
        // Setup (part 1)
        final Bericht bericht = new Bericht();
        bericht.setBerichtInhoud("BerichtInhoud");

        // Execute (part 1)
        subject.verstuurBerichtNaarIsc(bericht);

        // Verify (part 1)
        final ArgumentCaptor<MessageCreator> messageCreatorCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(eq(voiscOntvangst), messageCreatorCaptor.capture());

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();

        Mockito.when(session.createTextMessage("BerichtInhoud")).thenReturn(textMessage);

        // Execute (part 2)
        final Message result = messageCreator.createMessage(session);

        // Verify (part 2)
        Assert.assertSame(textMessage, result);

        Mockito.verify(session, Mockito.times(1)).createTextMessage("BerichtInhoud");
        Mockito.verify(textMessage, Mockito.never()).setStringProperty(eq(JMSConstants.BERICHT_ORIGINATOR), anyString());
        Mockito.verify(textMessage, Mockito.never()).setStringProperty(eq(JMSConstants.BERICHT_RECIPIENT), anyString());
        Mockito.verify(textMessage, Mockito.never()).setStringProperty(eq(JMSConstants.BERICHT_REFERENTIE), anyString());
        Mockito.verify(textMessage, Mockito.never()).setStringProperty(eq(JMSConstants.CORRELATIE_REFERENTIE), anyString());
    }

    @Test
    public void testGeenMailboxen() throws JMSException, VoiscQueueException {
        // Setup (part 1)
        final Bericht bericht = new Bericht();
        bericht.setBerichtInhoud("BerichtInhoud");
        bericht.setRecipient("0518010");
        bericht.setOriginator("3000250");

        // Execute (part 1)
        subject.verstuurBerichtNaarIsc(bericht);

        // Verify (part 1)
        final ArgumentCaptor<MessageCreator> messageCreatorCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(eq(voiscOntvangst), messageCreatorCaptor.capture());

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();

        Mockito.when(session.createTextMessage("BerichtInhoud")).thenReturn(textMessage);

        Mockito.when(mailboxRepo.getMailboxByNummer("0518010")).thenReturn(null);
        Mockito.when(mailboxRepo.getMailboxByNummer("3000250")).thenReturn(null);

        // Execute (part 2)
        final Message result = messageCreator.createMessage(session);

        // Verify (part 2)
        Assert.assertSame(textMessage, result);

        Mockito.verify(session, Mockito.times(1)).createTextMessage("BerichtInhoud");
        Mockito.verify(textMessage, Mockito.never()).setStringProperty(eq(JMSConstants.BERICHT_ORIGINATOR), anyString());
        Mockito.verify(textMessage, Mockito.never()).setStringProperty(eq(JMSConstants.BERICHT_RECIPIENT), anyString());
    }

    @Test(expected = VoiscQueueException.class)
    public void testJmsException() throws VoiscQueueException {
        Mockito.doThrow(new MyJmsException("Test")).when(jmsTemplate).send(eq(voiscOntvangst), Matchers.any(MessageCreator.class));

        // Execute
        subject.verstuurBerichtNaarIsc(new Bericht());
    }

    @Test
    public void testArchiveerUitgaand() throws JMSException, VoiscQueueException {
        // Setup (part 1)
        final Bericht bericht = new Bericht();
        bericht.setMessageId("BerichtRef");
        bericht.setCorrelationId("CorrelatieRef");
        bericht.setRecipient("0518010");
        bericht.setOriginator("3000250");
        bericht.setBerichtInhoud("BerichtInhoud");

        // Execute (part 1)
        subject.archiveerBericht(bericht, RichtingType.UITGAAND);

        // Verify (part 1)
        final ArgumentCaptor<MessageCreator> messageCreatorCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(eq(archivering), messageCreatorCaptor.capture());

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();

        final ArchiveringVerzoekBericht verzoek = new ArchiveringVerzoekBericht();
        verzoek.setSoortBericht("Onbekend");
        verzoek.setRichting(RichtingType.UITGAAND);
        verzoek.setZendendePartij("199902");
        verzoek.setOntvangendePartij("051801");
        verzoek.setReferentienummer("BerichtRef");
        verzoek.setCrossReferentienummer("CorrelatieRef");
        verzoek.setData("BerichtInhoud");

        Mockito.when(session.createTextMessage(verzoek.format())).thenReturn(textMessage);

        // Execute (part 2)
        final Mailbox mailbox0518010 = new Mailbox();
        mailbox0518010.setPartijcode("051801");

        final Mailbox mailbox3000250 = new Mailbox();
        mailbox3000250.setPartijcode("199902");

        Mockito.when(mailboxRepo.getMailboxByNummer("0518010")).thenReturn(mailbox0518010);
        Mockito.when(mailboxRepo.getMailboxByNummer("3000250")).thenReturn(mailbox3000250);

        final Message result = messageCreator.createMessage(session);

        // Verify (part 2)
        Assert.assertSame(textMessage, result);

        Mockito.verify(session, Mockito.times(1)).createTextMessage(verzoek.format());
    }

    @Test
    public void testArchiveerIngaand() throws JMSException, VoiscQueueException {
        // Setup (part 1)
        final Bericht bericht = new Bericht();
        bericht.setMessageId("BerichtRef");
        bericht.setCorrelationId("CorrelatieRef");
        bericht.setRecipient("3000250");
        bericht.setOriginator("0518010");
        bericht.setBerichtInhoud("BerichtInhoud");

        // Execute (part 1)
        subject.archiveerBericht(bericht, RichtingType.INGAAND);

        // Verify (part 1)
        final ArgumentCaptor<MessageCreator> messageCreatorCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(eq(archivering), messageCreatorCaptor.capture());

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();

        final ArchiveringVerzoekBericht verzoek = new ArchiveringVerzoekBericht();
        verzoek.setSoortBericht("Onbekend");
        verzoek.setRichting(RichtingType.INGAAND);
        verzoek.setOntvangendePartij("199902");
        verzoek.setZendendePartij("051801");
        verzoek.setReferentienummer("BerichtRef");
        verzoek.setCrossReferentienummer("CorrelatieRef");
        verzoek.setData("BerichtInhoud");

        Mockito.when(session.createTextMessage(anyString())).thenReturn(textMessage);

        // Execute (part 2)
        final Mailbox mailbox0518010 = new Mailbox();
        mailbox0518010.setPartijcode("051801");

        final Mailbox mailbox3000250 = new Mailbox();
        mailbox3000250.setPartijcode("199902");

        Mockito.when(mailboxRepo.getMailboxByNummer("0518010")).thenReturn(mailbox0518010);
        Mockito.when(mailboxRepo.getMailboxByNummer("3000250")).thenReturn(mailbox3000250);

        final Message result = messageCreator.createMessage(session);

        // Verify (part 2)
        Assert.assertSame(textMessage, result);

        Mockito.verify(session, Mockito.times(1)).createTextMessage(verzoek.format());
    }

    private static final class MyJmsException extends JmsException {
        private static final long serialVersionUID = 1L;

        MyJmsException(final String message) {
            super(message);
        }
    }
}
