/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.mutatielevering;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.mutatielevering.VerwerkHandelingException;
import nl.bzk.brp.service.mutatielevering.VerwerkHandelingService;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittests voor {@link JmsListener}.
 */
@RunWith(MockitoJUnitRunner.class)
public class JmsListenerTest {

    @InjectMocks
    private JmsListener listener;

    @Mock
    private VerwerkHandelingService service;

    @Spy
    private MutatieLeveringInfoBean mutatieLeveringInfoBean;

    @Test
    public final void testOnMessage() throws JMSException, JsonProcessingException {
        final TextMessage message = mock(TextMessage.class);
        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(1L);
        final ObjectMapper mapper = new ObjectMapper();
        when(message.getText()).thenReturn((mapper.writeValueAsString(handelingVoorPublicatie)));

        listener.onMessage(message);

        verify(message).getText();
    }

    @Test
    public final void testVerwerkfout() throws JMSException, JsonProcessingException, VerwerkHandelingException {
        final TextMessage message = mock(TextMessage.class);
        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(1L);
        final ObjectMapper mapper = new ObjectMapper();
        when(message.getText()).thenReturn((mapper.writeValueAsString(handelingVoorPublicatie)));
        Mockito.doThrow(new RuntimeException()).when(service).verwerkAdministratieveHandeling(any());
        listener.onMessage(message);
    }

    @Test
    public final void testOnGeenTextMessage() throws JMSException, StapException {
        final Message message = new ActiveMQObjectMessage();

        listener.onMessage(message);

        Mockito.verifyZeroInteractions(service);
    }

    @Test(expected = RuntimeException.class)
    public final void testOnMessageMetJMSExceptie() throws JMSException {
        final TextMessage message = mock(TextMessage.class);
        when(message.getText()).thenThrow(new JMSException(null));

        listener.onMessage(message);

        Mockito.verifyZeroInteractions(service);
    }

    public final void testOnMessageMetExceptieTijdensVerwerking() throws JsonProcessingException, JMSException, VerwerkHandelingException {
        final TextMessage message = mock(TextMessage.class);
        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(1L);
        final ObjectMapper mapper = new ObjectMapper();
        when(message.getText()).thenReturn((mapper.writeValueAsString(handelingVoorPublicatie)));
        Mockito.doThrow(Exception.class).when(service).verwerkAdministratieveHandeling(any());

        listener.onMessage(message);

        Mockito.verify(service, Mockito.times(1)).markeerHandelingAlsFout(Mockito.any());

    }
}
