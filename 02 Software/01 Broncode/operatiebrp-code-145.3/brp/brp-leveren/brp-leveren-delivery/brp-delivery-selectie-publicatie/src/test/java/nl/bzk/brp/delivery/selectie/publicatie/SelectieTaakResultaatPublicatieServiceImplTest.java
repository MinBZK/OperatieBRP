/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.publicatie;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.IllegalStateException;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;

/**
 * SelectieTaakResultaatPublicatieServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieTaakResultaatPublicatieServiceImplTest {

    @Mock
    private JmsOperations jmsOperations;

    @InjectMocks
    private SelectieTaakResultaatPublicatieServiceImpl service;

    @Test
    public void publiceerSelectieTaakResultaat() throws JMSException {
        SelectieTaakResultaat resultaat = new SelectieTaakResultaat();
        service.publiceerSelectieTaakResultaat(resultaat);

        final ArgumentCaptor<ProducerCallback> producerCallbackArgumentCaptor = ArgumentCaptor.forClass(ProducerCallback.class);
        Mockito.verify(jmsOperations, Mockito.times(1)).execute(producerCallbackArgumentCaptor.capture());

        final ProducerCallback value = producerCallbackArgumentCaptor.getValue();
        final Session sessionMock = Mockito.mock(Session.class);
        final MessageProducer messageProducerMock = Mockito.mock(MessageProducer.class);
        final TextMessage testMessageMock = Mockito.mock(TextMessage.class);
        final String serializedMessage = new JsonStringSerializer().serialiseerNaarString(resultaat);
        Mockito.when(sessionMock.createTextMessage(serializedMessage)).thenReturn(testMessageMock);

        value.doInJms(sessionMock, messageProducerMock);

        Mockito.verify(sessionMock).createTextMessage(serializedMessage);
        Mockito.verify(messageProducerMock).send(testMessageMock);
    }

    @Test
    public void publiceerFout() throws JMSException {

        final SelectieTaakResultaat foutResultaat = new SelectieTaakResultaat();
        foutResultaat.setType(TypeResultaat.FOUT);

        service.publiceerFout();

        final ArgumentCaptor<ProducerCallback> producerCallbackArgumentCaptor = ArgumentCaptor.forClass(ProducerCallback.class);
        Mockito.verify(jmsOperations, Mockito.times(1)).execute(producerCallbackArgumentCaptor.capture());
        final ProducerCallback value = producerCallbackArgumentCaptor.getValue();
        final Session sessionMock = Mockito.mock(Session.class);
        final MessageProducer messageProducerMock = Mockito.mock(MessageProducer.class);
        final TextMessage testMessageMock = Mockito.mock(TextMessage.class);
        final String foutmelding = new JsonStringSerializer().serialiseerNaarString(foutResultaat);
        Mockito.when(sessionMock.createTextMessage(foutmelding)).thenReturn(testMessageMock);

        value.doInJms(sessionMock, messageProducerMock);


        Mockito.verify(sessionMock).createTextMessage(foutmelding);
        Mockito.verify(messageProducerMock).send(testMessageMock);
    }

    @Test(expected = BrpServiceRuntimeException.class)
    public void testFoutFlow() {
        Mockito.when(jmsOperations.execute(Matchers.<ProducerCallback<Object>>any())).thenThrow(new IllegalStateException(null));
        SelectieTaakResultaat resultaat = new SelectieTaakResultaat();
        service.publiceerSelectieTaakResultaat(resultaat);
    }
}
