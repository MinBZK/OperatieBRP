/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.publicatie;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import org.junit.Before;
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
import org.springframework.test.util.ReflectionTestUtils;

/**
 * MaakSelectieResultaatTaakPublicatieServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakSelectieResultaatTaakPublicatieServiceImplTest {

    @Mock(name = "maakSelectieResultaatTaakJmsTemplate")
    private JmsOperations maakSelectieResultaatTaakJmsTemplate;

    @Mock(name = "maakSelectieGeenResultaatNetwerkTaakJmsTemplate")
    private JmsOperations maakSelectieGeenResultaatNetwerkTaakJmsTemplate;

    @InjectMocks
    private MaakSelectieResultaatTaakPublicatieServiceImpl service;

    @Before
    public void setupService() {
        ReflectionTestUtils.setField(service, "maakSelectieResultaatTemplate", maakSelectieResultaatTaakJmsTemplate);
        ReflectionTestUtils.setField(service, "maakSelectieGeenResultaatNetwerkTemplate", maakSelectieGeenResultaatNetwerkTaakJmsTemplate);
    }

    @Test
    public void publiceerMaakSelectieResultaatTaken() throws JMSException {

        final MaakSelectieResultaatTaak taak = new MaakSelectieResultaatTaak();
        service.publiceerMaakSelectieResultaatTaken(Lists.newArrayList(taak));

        final ArgumentCaptor<ProducerCallback> producerCallbackArgumentCaptor = ArgumentCaptor.forClass(ProducerCallback.class);
        Mockito.verify(maakSelectieResultaatTaakJmsTemplate, Mockito.times(1)).execute(producerCallbackArgumentCaptor.capture());

        final ProducerCallback value = producerCallbackArgumentCaptor.getValue();
        final Session sessionMock = Mockito.mock(Session.class);
        final MessageProducer messageProducerMock = Mockito.mock(MessageProducer.class);
        final TextMessage testMessageMock = Mockito.mock(TextMessage.class);
        final String serializedMessage = new JsonStringSerializer().serialiseerNaarString(taak);
        Mockito.when(sessionMock.createTextMessage(serializedMessage)).thenReturn(testMessageMock);

        value.doInJms(sessionMock, messageProducerMock);

        Mockito.verify(sessionMock).createTextMessage(serializedMessage);
        Mockito.verify(messageProducerMock).send(testMessageMock);
    }

    @Test(expected = BrpServiceRuntimeException.class)
    public void testFoutFlow() {
        List<MaakSelectieResultaatTaak> taken = new ArrayList<>();
        Mockito.when(maakSelectieResultaatTaakJmsTemplate.execute(Matchers.<ProducerCallback<Object>>any())).thenThrow(new IllegalStateException(null));

        service.publiceerMaakSelectieResultaatTaken(taken);

    }

    @Test
    public void publiceerMaakSelectieGeenResultaatTakenNetwerk() throws JMSException {

        final MaakSelectieResultaatTaak taak = new MaakSelectieResultaatTaak();
        service.publiceerMaakSelectieGeenResultaatNetwerkTaak(Lists.newArrayList(taak));

        final ArgumentCaptor<ProducerCallback> producerCallbackArgumentCaptor = ArgumentCaptor.forClass(ProducerCallback.class);
        Mockito.verify(maakSelectieGeenResultaatNetwerkTaakJmsTemplate, Mockito.times(1)).execute(producerCallbackArgumentCaptor.capture());

        final ProducerCallback value = producerCallbackArgumentCaptor.getValue();
        final Session sessionMock = Mockito.mock(Session.class);
        final MessageProducer messageProducerMock = Mockito.mock(MessageProducer.class);
        final TextMessage testMessageMock = Mockito.mock(TextMessage.class);
        final String serializedMessage = new JsonStringSerializer().serialiseerNaarString(taak);
        Mockito.when(sessionMock.createTextMessage(serializedMessage)).thenReturn(testMessageMock);

        value.doInJms(sessionMock, messageProducerMock);

        Mockito.verify(sessionMock).createTextMessage(serializedMessage);
        Mockito.verify(messageProducerMock).send(testMessageMock);
    }

    @Test(expected = BrpServiceRuntimeException.class)
    public void testFoutFlowGba() {
        List<MaakSelectieResultaatTaak> taken = new ArrayList<>();
        Mockito.when(maakSelectieGeenResultaatNetwerkTaakJmsTemplate.execute(Matchers.<ProducerCallback<Object>>any()))
                .thenThrow(new IllegalStateException(null));

        service.publiceerMaakSelectieGeenResultaatNetwerkTaak(taken);

    }
}

