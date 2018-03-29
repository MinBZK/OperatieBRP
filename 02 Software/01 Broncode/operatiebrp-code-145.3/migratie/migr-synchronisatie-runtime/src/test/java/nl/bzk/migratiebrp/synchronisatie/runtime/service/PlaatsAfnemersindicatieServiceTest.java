/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import static org.mockito.Matchers.same;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
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

@RunWith(MockitoJUnitRunner.class)
public class PlaatsAfnemersindicatieServiceTest {

    private static final String PARTIJ_CODE = "059901";
    private static final String BSN = "123456789";

    @Mock(name = "gbaAfnemersindicaties")
    private Destination gbaAfnemersindicatiesQueue;
    @Mock(name = "brpQueueJmsTemplate")
    private JmsTemplate jmsTemplate;
    @Mock
    private Session jmsSession;

    private PlaatsAfnemersindicatieService subject;

    @Mock
    private TextMessage messageToBrp;

    @Before
    public void setup() {
        subject = new PlaatsAfnemersindicatieService(jmsTemplate, gbaAfnemersindicatiesQueue);
    }

    @Test
    public void test() throws Exception {
        Assert.assertEquals(PlaatsAfnemersindicatieVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final PlaatsAfnemersindicatieVerzoekBericht verzoek = new PlaatsAfnemersindicatieVerzoekBericht();
        verzoek.setReferentie("REFERENTIE");
        verzoek.setBsn(BSN);
        verzoek.setPartijCode(PARTIJ_CODE);

        // Execute voor subject
        subject.verwerkBericht(verzoek);

        // Verify voor subject
        final ArgumentCaptor<MessageCreator> messageCreator = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate).send(same(gbaAfnemersindicatiesQueue), messageCreator.capture());
        Mockito.verifyNoMoreInteractions(jmsTemplate);

        // Expect voor messageCreator
        Mockito.when(jmsSession.createTextMessage(Matchers.anyString())).thenReturn(messageToBrp);

        messageCreator.getValue().createMessage(jmsSession);
        final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(jmsSession).createTextMessage(messageCaptor.capture());
        Mockito.verifyNoMoreInteractions(jmsSession);

        final String
                expectedMessage =
                "{\"effectAfnemerindicatie\":\"PLAATSING\",\"partijCode\":\"059901\",\"bsn\":\"123456789\",\"referentienummer\":\"REFERENTIE\"}";
        final String message = messageCaptor.getValue();

        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(message);
        final JsonNode expectedJsonNode = objectMapper.readTree(expectedMessage);

        Assert.assertEquals(expectedJsonNode, jsonNode);
    }
}
