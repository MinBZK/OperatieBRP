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
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
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
public class VerwijderAfnemersindicatieServiceTest {

    private static final String PARTIJ_CODE = "059901";
    private static final String BSN = "123456789";

    @Mock
    private Destination gbaAfnemersindicatiesQueue;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private Session jmsSession;

    private VerwijderAfnemersindicatieService subject;

    @Mock
    private TextMessage messageToBrp;

    @Before
    public void setup() {
        subject = new VerwijderAfnemersindicatieService(jmsTemplate, gbaAfnemersindicatiesQueue);
    }

    @Test
    public void test() throws Exception {
        Assert.assertEquals(VerwijderAfnemersindicatieVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final VerwijderAfnemersindicatieVerzoekBericht verzoek = new VerwijderAfnemersindicatieVerzoekBericht();
        verzoek.setMessageId("MSG-ID");
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

        // Execute messageCreator
        messageCreator.getValue().createMessage(jmsSession);

        // Verify messageCreator
        final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(jmsSession).createTextMessage(messageCaptor.capture());
        Mockito.verifyNoMoreInteractions(jmsSession);
        Mockito.verify(messageToBrp).setStringProperty(JMSConstants.BERICHT_REFERENTIE, "MSG-ID");

        final String expectedMessage =
                "{\"effectAfnemerindicatie\":\"VERWIJDERING\",\"partijCode\":\"059901\",\"bsn\":\"123456789\"}";
        final String message = messageCaptor.getValue();

        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(message);
        final JsonNode expectedJsonNode = objectMapper.readTree(expectedMessage);

        Assert.assertEquals(expectedJsonNode, jsonNode);
    }
}
