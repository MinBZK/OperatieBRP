/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudAntwoord;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieFoutcodeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AfnemersindicatiesMessageHandlerTest {

    @Mock
    private Destination destination;
    @Mock
    private JmsTemplate jmsTemplate;

    private AfnemerindicatiesMessageHandler subject;

    @Mock
    private Session session;
    @Mock
    private TextMessage syncAntwoordMessage;

    @Before
    public void setup() {
        subject = new AfnemerindicatiesMessageHandler(destination, Mockito.mock(ConnectionFactory.class));
        ReflectionTestUtils.setField(subject, AbstractMessageHandler.class, "jmsTemplate", jmsTemplate, JmsTemplate.class);
    }

    @Test
    public void testOk() throws JMSException, IOException {
        final Message brpAntwoordMessage = maakMessage("ref-1", null);

        subject.onMessage(brpAntwoordMessage);

        // Capture message creator
        final ArgumentCaptor<MessageCreator> messageCreatorCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate).send(Matchers.same(destination), messageCreatorCaptor.capture());

        // Execute message creator
        Mockito.when(session.createTextMessage(Matchers.anyString())).thenReturn(syncAntwoordMessage);

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();
        messageCreator.createMessage(session);

        final ArgumentCaptor<String> syncTextCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> syncMsgIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> syncCorrIdCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(session).createTextMessage(syncTextCaptor.capture());
        Mockito.verify(syncAntwoordMessage).setStringProperty(Matchers.eq(JMSConstants.BERICHT_REFERENTIE), syncMsgIdCaptor.capture());
        Mockito.verify(syncAntwoordMessage).setStringProperty(Matchers.eq(JMSConstants.CORRELATIE_REFERENTIE), syncCorrIdCaptor.capture());

        Assert.assertNotNull(syncMsgIdCaptor.getValue());
        Assert.assertEquals("ref-1", syncCorrIdCaptor.getValue());
        final VerwerkAfnemersindicatieAntwoordBericht bericht =
                (VerwerkAfnemersindicatieAntwoordBericht) SyncBerichtFactory.SINGLETON.getBericht(syncTextCaptor.getValue());
        Assert.assertEquals(StatusType.OK, bericht.getStatus());
        Assert.assertNull(bericht.getFoutcode());
    }

    @Test
    public void testFunctioneelFout() throws JMSException, IOException {
        final Message brpAntwoordMessage = maakMessage("ref-2", 'I');

        subject.onMessage(brpAntwoordMessage);

        // Capture message creator
        final ArgumentCaptor<MessageCreator> messageCreatorCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate).send(Matchers.same(destination), messageCreatorCaptor.capture());

        // Execute message creator
        Mockito.when(session.createTextMessage(Matchers.anyString())).thenReturn(syncAntwoordMessage);

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();
        messageCreator.createMessage(session);

        final ArgumentCaptor<String> syncTextCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> syncMsgIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> syncCorrIdCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(session).createTextMessage(syncTextCaptor.capture());
        Mockito.verify(syncAntwoordMessage).setStringProperty(Matchers.eq(JMSConstants.BERICHT_REFERENTIE), syncMsgIdCaptor.capture());
        Mockito.verify(syncAntwoordMessage).setStringProperty(Matchers.eq(JMSConstants.CORRELATIE_REFERENTIE), syncCorrIdCaptor.capture());

        Assert.assertNotNull(syncMsgIdCaptor.getValue());
        Assert.assertEquals("ref-2", syncCorrIdCaptor.getValue());
        final VerwerkAfnemersindicatieAntwoordBericht bericht =
                (VerwerkAfnemersindicatieAntwoordBericht) SyncBerichtFactory.SINGLETON.getBericht(syncTextCaptor.getValue());
        Assert.assertEquals(StatusType.FOUT, bericht.getStatus());
        Assert.assertEquals(AfnemersindicatieFoutcodeType.I, bericht.getFoutcode());
    }

    @Test(expected = ServiceException.class)
    public void testTechnischeFout() throws JMSException, IOException {
        final Message brpAntwoordMessage = maakMessage(null, null);

        subject.onMessage(brpAntwoordMessage);
    }

    private Message maakMessage(final String refNummer, final Character foutcode) throws JMSException, IOException {
        final AfnemerindicatieOnderhoudAntwoord antwoord = new AfnemerindicatieOnderhoudAntwoord();
        antwoord.setReferentienummer(refNummer);
        antwoord.setFoutcode(foutcode);

        final String tekst = new ObjectMapper().writeValueAsString(antwoord);
        System.out.println("Antwoord: " + tekst);

        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getText()).thenReturn(tekst);
        if (refNummer != null) {
            Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenReturn(refNummer);
        } else {
            Mockito.when(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE)).thenThrow(JMSException.class);
        }

        return message;
    }

}
