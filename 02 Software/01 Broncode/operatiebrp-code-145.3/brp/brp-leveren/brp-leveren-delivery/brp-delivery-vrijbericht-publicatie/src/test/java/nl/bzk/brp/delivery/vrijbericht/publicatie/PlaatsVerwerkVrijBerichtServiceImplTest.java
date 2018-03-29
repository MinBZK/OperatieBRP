/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht.publicatie;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.util.LeveringConstanten;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.MessageEOFException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

/**
 * Unit test voor {@link PlaatsVerwerkVrijBerichtServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class PlaatsVerwerkVrijBerichtServiceImplTest {

    @InjectMocks
    private PlaatsVerwerkVrijBerichtServiceImpl service;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private Session sessionMock;
    @Mock
    private MessageProducer messageProducer;
    @Captor
    private ArgumentCaptor<Message> messageCaptor;
    @Mock
    private TextMessage textMessage;

    private final JsonStringSerializer serializer = new JsonStringSerializer();

    @Test
    public void plaatsVrijBericht() throws Exception {
        when(sessionMock.createTextMessage(anyString())).thenReturn(textMessage);
        //@formatter:off
        final VrijBerichtGegevens berichtGegevens = VrijBerichtGegevens.builder()
                .metPartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                .metBrpEndpointUrl("http://ergens")
                .metStelsel(Stelsel.BRP)
                .metArchiveringOpdracht(new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now()))
                .build();
        //@formatter:on
        doAnswer(a -> {
            final ProducerCallback<Object> o = (ProducerCallback<Object>) a.getArguments()[0];
            o.doInJms(sessionMock, messageProducer);
            verify(messageProducer).send(messageCaptor.capture());
            verify(sessionMock).createTextMessage(serializer.serialiseerNaarString(berichtGegevens));
            verify(messageCaptor.getValue())
                    .setStringProperty(LeveringConstanten.JMS_MESSAGEGROUP_HEADER, String.valueOf(berichtGegevens.getPartij().getCode()));
            return null;
        }).when(jmsTemplate).execute(any(ProducerCallback.class));

        service.plaatsVrijBericht(berichtGegevens);
    }

    @Test(expected = BrpServiceRuntimeException.class)
    public void jmsException() throws Exception {
        when(jmsTemplate.execute((ProducerCallback<Object>) any())).thenThrow(new MessageEOFException(null));

        service.plaatsVrijBericht(null);
    }
}
