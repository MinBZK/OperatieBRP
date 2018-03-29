/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.listener;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.runtime.VoiscDatabase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.JmsException;

@RunWith(MockitoJUnitRunner.class)
public class IscBerichtListenerTest {

    @Mock
    private VoiscDatabase voiscDatabase;

    @InjectMocks
    private IscBerichtListener subject;

    @Test
    public void testHappyFlow() throws JMSException {
        // Prepare
        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE)).thenReturn("message-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenReturn("correlatie-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR)).thenReturn("051801");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_RECIPIENT)).thenReturn("199902");
        Mockito.when(message.getText()).thenReturn("message-body");

        Mailbox originatorMailbox = new Mailbox();
        originatorMailbox.setMailboxnr("0518010");
        originatorMailbox.setVerzender("3000250");
        Mockito.when(voiscDatabase.getMailboxByPartijcode("051801")).thenReturn(originatorMailbox);

        Mailbox centraleVoorziening3000250Mailbox = new Mailbox();
        centraleVoorziening3000250Mailbox.setMailboxnr("3000250");
        Mockito.when(voiscDatabase.getMailboxByPartijcode("199902")).thenReturn(centraleVoorziening3000250Mailbox);

        // Execute
        subject.onMessage(message);

        // Verify
        final ArgumentCaptor<Bericht> berichtCaptor = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(voiscDatabase, Mockito.times(1)).saveBericht(berichtCaptor.capture());
        Mockito.verify(voiscDatabase, Mockito.times(1)).getMailboxByPartijcode("199902");
        Mockito.verify(voiscDatabase, Mockito.times(1)).getMailboxByPartijcode("051801");
        Mockito.verifyNoMoreInteractions(voiscDatabase);

        final Bericht bericht = berichtCaptor.getValue();
        Assert.assertEquals("message-body", bericht.getBerichtInhoud());
        Assert.assertEquals("message-id-001", bericht.getMessageId());
        Assert.assertEquals("correlatie-id-001", bericht.getCorrelationId());
        Assert.assertEquals("0518010", bericht.getOriginator());
        Assert.assertEquals("3000250", bericht.getRecipient());
        Assert.assertEquals(StatusEnum.RECEIVED_FROM_ISC, bericht.getStatus());
        Assert.assertNotNull(bericht.getTijdstipOntvangst());
    }

    @Test
    public void testNonReceiptNotification() throws JMSException {
        // Prepare
        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE)).thenReturn("message-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenReturn("correlatie-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR)).thenReturn("051801");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_RECIPIENT)).thenReturn("199902");
        Mockito.when(message.propertyExists(JMSConstants.REQUEST_NON_RECEIPT_NOTIFICATION)).thenReturn(true);
        Mockito.when(message.getText()).thenReturn("message-body");


        Mailbox originatorMailbox = new Mailbox();
        originatorMailbox.setMailboxnr("0518010");
        originatorMailbox.setVerzender("3000250");
        Mockito.when(voiscDatabase.getMailboxByPartijcode("051801")).thenReturn(originatorMailbox);

        Mailbox recipientMailbox = new Mailbox();
        recipientMailbox.setMailboxnr("3000250");
        Mockito.when(voiscDatabase.getMailboxByPartijcode("199902")).thenReturn(recipientMailbox);

        // Execute
        subject.onMessage(message);

        // Verify
        final ArgumentCaptor<Bericht> berichtCaptor = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(voiscDatabase, Mockito.times(1)).saveBericht(berichtCaptor.capture());
        Mockito.verify(voiscDatabase, Mockito.times(1)).getMailboxByPartijcode("051801");
        Mockito.verify(voiscDatabase, Mockito.times(1)).getMailboxByPartijcode("199902");
        Mockito.verifyNoMoreInteractions(voiscDatabase);

        final Bericht bericht = berichtCaptor.getValue();
        Assert.assertEquals(true, bericht.getRequestNonReceiptNotification());
    }

    @Test(expected = IscBerichtException.class)
    public void testUnknownRecipient() throws JMSException {
        // Prepare
        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE)).thenReturn("message-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenReturn("correlatie-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR)).thenReturn("0518");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_RECIPIENT)).thenReturn("3000250");
        Mockito.when(message.getText()).thenReturn("message-body");

        final Mailbox originatorMailbox = new Mailbox();
        originatorMailbox.setMailboxnr("0518010");
        Mockito.when(voiscDatabase.getMailboxByPartijcode("051801")).thenReturn(originatorMailbox);
        Mockito.when(voiscDatabase.getMailboxByPartijcode("3000250")).thenReturn(null);

        // Execute
        subject.onMessage(message);
    }

    @Test(expected = IscBerichtException.class)
    public void testInvalidRecipient() throws JMSException {
        // Prepare
        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE)).thenReturn("message-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenReturn("correlatie-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR)).thenReturn("0518");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_RECIPIENT)).thenReturn("abc");
        Mockito.when(message.getText()).thenReturn("message-body");

        final Mailbox originatorMailbox = new Mailbox();
        originatorMailbox.setMailboxnr("0518010");
        Mockito.when(voiscDatabase.getMailboxByPartijcode("051801")).thenReturn(originatorMailbox);

        // Execute
        subject.onMessage(message);
    }

    @Test(expected = IscBerichtException.class)
    public void testUnknownOriginator() throws JMSException {
        // Prepare
        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE)).thenReturn("message-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenReturn("correlatie-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR)).thenReturn("0518");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_RECIPIENT)).thenReturn("3000250");
        Mockito.when(message.getText()).thenReturn("message-body");

        Mockito.when(voiscDatabase.getMailboxByPartijcode("051801")).thenReturn(null);
        final Mailbox recipientMailbox = new Mailbox();
        recipientMailbox.setMailboxnr("3000250");
        Mockito.when(voiscDatabase.getMailboxByPartijcode("3000250")).thenReturn(recipientMailbox);

        // Execute
        subject.onMessage(message);
    }

    @Test(expected = IscBerichtException.class)
    public void testInvalidOriginator() throws JMSException {
        // Prepare
        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE)).thenReturn("message-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenReturn("correlatie-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR)).thenReturn("abcd");
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_RECIPIENT)).thenReturn("3000250");
        Mockito.when(message.getText()).thenReturn("message-body");

        final Mailbox recipientMailbox = new Mailbox();
        recipientMailbox.setMailboxnr("3000250");
        Mockito.when(voiscDatabase.getMailboxByPartijcode("3000250")).thenReturn(recipientMailbox);

        // Execute
        subject.onMessage(message);
    }

    @Test(expected = IscBerichtException.class)
    public void testBadFlowException() throws JMSException {
        // Prepare
        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE)).thenReturn("message-id-001");
        Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenThrow(new MyJmsException("Fout in de JMS"));

        // Execute
        subject.onMessage(message);
    }


    private static final class MyJmsException extends JmsException {
        private static final long serialVersionUID = 1L;

        MyJmsException(final String message) {
            super(message);
        }
    }
}
