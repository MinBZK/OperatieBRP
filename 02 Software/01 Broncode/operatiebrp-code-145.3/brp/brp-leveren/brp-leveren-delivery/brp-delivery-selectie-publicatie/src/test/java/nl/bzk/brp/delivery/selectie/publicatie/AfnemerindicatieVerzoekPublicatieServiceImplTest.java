/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.publicatie;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
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
 */
@RunWith(MockitoJUnitRunner.class)
public class AfnemerindicatieVerzoekPublicatieServiceImplTest {

    @Mock
    private JmsOperations jmsOperations;

    @InjectMocks
    private AfnemerindicatieVerzoekPublicatieServiceImpl service;

    @Test
    public void publiceerAfnemerindicatieVerzoeken() throws JMSException {
        final SelectieAfnemerindicatieTaak taak = new SelectieAfnemerindicatieTaak();
        final ArrayList<SelectieAfnemerindicatieTaak> taakList = Lists.newArrayList(taak);
        service.publiceerAfnemerindicatieVerzoeken(taakList);

        final ArgumentCaptor<ProducerCallback> producerCallbackArgumentCaptor = ArgumentCaptor.forClass(ProducerCallback.class);
        Mockito.verify(jmsOperations, Mockito.times(1)).execute(producerCallbackArgumentCaptor.capture());

        final ProducerCallback value = producerCallbackArgumentCaptor.getValue();
        final Session sessionMock = Mockito.mock(Session.class);
        final MessageProducer messageProducerMock = Mockito.mock(MessageProducer.class);
        final TextMessage testMessageMock = Mockito.mock(TextMessage.class);
        final String serializedMessage = new JsonStringSerializer().serialiseerNaarString(taakList);
        Mockito.when(sessionMock.createTextMessage(serializedMessage)).thenReturn(testMessageMock);

        value.doInJms(sessionMock, messageProducerMock);

        Mockito.verify(sessionMock).createTextMessage(serializedMessage);
        Mockito.verify(messageProducerMock).send(testMessageMock);
    }

    @Test
    public void publiceerAfnemerindicatieVerzoeken_GeenVerzoeken() {
        service.publiceerAfnemerindicatieVerzoeken(Lists.newArrayList());

        Mockito.verifyZeroInteractions( jmsOperations);
    }

    @Test(expected = BrpServiceRuntimeException.class)
    public void testFoutFlow() {
        final SelectieAfnemerindicatieTaak taak = new SelectieAfnemerindicatieTaak();
        final ArrayList<SelectieAfnemerindicatieTaak> taakList = Lists.newArrayList(taak);
        Mockito.when(jmsOperations.execute(Matchers.<ProducerCallback<Object>>any())).thenThrow(new IllegalStateException(null));

        service.publiceerAfnemerindicatieVerzoeken(taakList);

    }
}
