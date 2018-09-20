/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking;

import static org.mockito.Mockito.doThrow;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.TestData;
import nl.bzk.brp.protocollering.verwerking.exceptie.ProtocolleringFout;
import nl.bzk.brp.protocollering.verwerking.service.ProtocolleringVerwerkingService;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProtocolleringBerichtVerwerkerTest {

    @InjectMocks
    private ProtocolleringBerichtVerwerker protocolleringBerichtVerwerker = new ProtocolleringBerichtVerwerker();

    @Mock
    private ProtocolleringVerwerkingService protocolleringVerwerkingService;

    @Mock
    private TextMessage message;

    @Mock
    private Message fouteMessage;

    private JsonStringSerializer<ProtocolleringOpdracht> serialiseerder =
        new JsonStringSerializer<>(ProtocolleringOpdracht.class);

    @Before
    public void setUp() throws JMSException {
        final ProtocolleringOpdracht protocolleringOpdracht = TestData.geefTestProtocolleringOpdracht();

        final String geserializeerdeGegevens = serialiseerder.serialiseerNaarString(protocolleringOpdracht);

        Mockito.when(message.getText()).thenReturn(geserializeerdeGegevens);
    }

    @Test
    public void testOnMessage() {
        protocolleringBerichtVerwerker.onMessage(message);
    }

    @Test(expected = ProtocolleringFout.class)
    public void testOnMessageOpslaanMislukt() {
        doThrow(ProtocolleringFout.class).when(
            protocolleringVerwerkingService).slaProtocolleringOp(Matchers.any(ProtocolleringOpdracht.class));
        protocolleringBerichtVerwerker.onMessage(message);
    }

    @Test(expected = ProtocolleringFout.class)
    public void testOnMessageJmsException() {
        doThrow(JMSException.class).when(protocolleringVerwerkingService)
            .slaProtocolleringOp(Matchers.any(ProtocolleringOpdracht.class));
        protocolleringBerichtVerwerker.onMessage(message);
    }

    @Test(expected = ProtocolleringFout.class)
    public void testOnMessageJsonMappingException() {
        doThrow(JsonMappingException.class).when(protocolleringVerwerkingService)
                .slaProtocolleringOp(Matchers.any(ProtocolleringOpdracht.class));
        protocolleringBerichtVerwerker.onMessage(message);
    }

    @Test(expected = ProtocolleringFout.class)
    public void testOnMessageIoException() {
        doThrow(IOException.class).when(protocolleringVerwerkingService)
                .slaProtocolleringOp(Matchers.any(ProtocolleringOpdracht.class));
        protocolleringBerichtVerwerker.onMessage(message);
    }

    @Test
    public void testOnMessageFouteMessage() {
        protocolleringBerichtVerwerker.onMessage(fouteMessage);
    }
}
