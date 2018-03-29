/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht.gba;

import java.util.Collections;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import nl.bzk.brp.service.vrijbericht.VrijBerichtResultaat;
import nl.bzk.brp.service.vrijbericht.VrijBerichtService;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerzoek;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtMessageListenerTest {

    @Mock
    private TextMessage message;

    @Mock
    private BytesMessage bytesMessage;

    @Mock
    private VrijBerichtResultaat vrijBerichtResultaat;

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private VrijBerichtService vrijBerichtService;

    @InjectMocks
    private VrijBerichtMessageListener listener;

    @Test
    public void testOnMessage() throws JMSException {
        final VrijBerichtOpdracht opdracht = new VrijBerichtOpdracht();
        opdracht.setVerzendendePartijCode("001801");
        opdracht.setOntvangendePartijCode("059901");
        opdracht.setBericht("Hallo Rotterdam");
        opdracht.setReferentienummer("REF-WAARDE-001");

        Mockito.when(message.getText()).thenReturn(maakJson(opdracht));
        Mockito.when(message.getStringProperty("iscBerichtReferentie")).thenReturn("isc-123");
        Mockito.when(vrijBerichtService.verwerkVerzoek(Mockito.any(VrijBerichtVerzoek.class))).thenReturn(vrijBerichtResultaat);
        Mockito.when(vrijBerichtResultaat.getMeldingen()).thenReturn(Collections.emptyList());

        listener.onMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verkeerdTypeBericht() throws JMSException {
        listener.onMessage(bytesMessage);
        Assert.fail();
    }

    @Test(expected = nl.bzk.algemeenbrp.util.common.jms.JmsException.class)
    public void berichtNietLeesbaar() throws JMSException {
        final VrijBerichtOpdracht opdracht = new VrijBerichtOpdracht();
        opdracht.setVerzendendePartijCode("001801");
        opdracht.setOntvangendePartijCode("059901");
        opdracht.setBericht("Hallo Rotterdam");
        opdracht.setReferentienummer("REF-WAARDE-001");

        Mockito.when(message.getText()).thenThrow(new JMSException("fout"));

        listener.onMessage(message);
    }

    @Test(expected = org.springframework.jms.JmsException.class)
    public void berichtreferentieNietLeesbaar() throws JMSException {
        final VrijBerichtOpdracht opdracht = new VrijBerichtOpdracht();
        opdracht.setVerzendendePartijCode("001801");
        opdracht.setOntvangendePartijCode("059901");
        opdracht.setBericht("Hallo Rotterdam");
        opdracht.setReferentienummer("REF-WAARDE-001");

        Mockito.when(message.getText()).thenReturn(maakJson(opdracht));
        Mockito.when(message.getStringProperty("iscBerichtReferentie")).thenThrow(new JMSException("fout"));

        listener.onMessage(message);
    }

    private String maakJson(VrijBerichtOpdracht opdracht) throws JMSException {
        final JsonStringSerializer json = new JsonStringSerializer();
        return json.serialiseerNaarString(opdracht);
    }
}
