/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.ZonedDateTime;
import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.algemeenbrp.util.common.serialisatie.SerialisatieExceptie;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AfnemerQueueMessageListenerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private AfnemerQueueMessageListener verzendingMessageListener;
    @Mock
    private Verzending.VerzendingService verzendingService;

    private String jsonString;

    @Before
    public void voorTest() {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setOntvangendePartijId((short) 1);
        archiveringOpdracht.setReferentienummer("refNr");
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(1L);
        protocolleringOpdracht.setToegangLeveringsautorisatieId(1);
        //@formatter:off
        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .metProtocolleringOpdracht(protocolleringOpdracht)
                .build();
        //@formatter:on
        jsonString = new JsonStringSerializer().serialiseerNaarString(synchronisatieBerichtGegevens);
    }

    @Test
    public void test() throws JMSException, JsonProcessingException {
        final ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText(jsonString);

        verzendingMessageListener.onMessage(message);

        Mockito.verify(verzendingService).verwerkSynchronisatieBericht(Mockito.any());
    }

    @Test
    public void testGeenLeveringBericht() throws JMSException, JsonProcessingException {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setOntvangendePartijId((short) 1);
        archiveringOpdracht.setReferentienummer("refNr");
        //@formatter:off
        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .build();
        //@formatter:on
        jsonString = new JsonStringSerializer().serialiseerNaarString(synchronisatieBerichtGegevens);
        final ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText(jsonString);

        verzendingMessageListener.onMessage(message);

        Mockito.verify(verzendingService).verwerkSynchronisatieBericht(Mockito.any());
    }

    @Test
    public void testJmsFoutBijLezenText() throws JMSException {
        expectedException.expect(VerzendExceptie.class);
        expectedException.expectMessage("Deserialisatiefout : Het ontvangen van een JMS bericht met een afnemerbericht is mislukt");

        final ActiveMQTextMessage message = Mockito.mock(ActiveMQTextMessage.class);
        Mockito.doThrow(JMSException.class).when(message).getText();

        verzendingMessageListener.onMessage(message);
    }

    @Test
    public void testJmsFoutBijLezenProperty() throws JMSException, JsonProcessingException {
        expectedException.expect(VerzendExceptie.class);
        expectedException.expectMessage("Kan JMS property niet lezen");

        final ActiveMQTextMessage message = Mockito.mock(ActiveMQTextMessage.class);
        Mockito.when(message.getText()).thenReturn(jsonString);
        Mockito.doThrow(JMSException.class).when(message).getJMSRedelivered();

        verzendingMessageListener.onMessage(message);
    }

    @Test
    public void testJmsRedelivery() throws JMSException, JsonProcessingException {
        final ActiveMQTextMessage message = Mockito.mock(ActiveMQTextMessage.class);
        Mockito.when(message.getText()).thenReturn(jsonString);
        Mockito.when(message.getJMSRedelivered()).thenReturn(true);

        verzendingMessageListener.onMessage(message);
    }

    @Test
    public void testFoutBijDeserialisatie() throws MessageNotWriteableException {
        expectedException.expect(SerialisatieExceptie.class);
        expectedException.expectMessage("Het deserialiseren van het object is mislukt.");

        final ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("invalid");

        verzendingMessageListener.onMessage(message);
    }

    @Test(expected = VerzendExceptie.class)
    public void testFoutBijVerwerking() throws JsonProcessingException, MessageNotWriteableException {
        final ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText(jsonString);

        Mockito.doThrow(VerzendExceptie.class).when(verzendingService).verwerkSynchronisatieBericht(Mockito.any());

        verzendingMessageListener.onMessage(message);

    }

}
