/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import static org.mockito.Matchers.same;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerErkenningMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerNaamGeslachtMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerOverlijdenMigVrz;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class ToevalligeGebeurtenisVerzenderTest {

    private static final String VERZOEK_BERICHT_ID = "3513531ab";

    @Mock(name = "brpQueueJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Mock(name = "gbaToevalligeGebeurtenissen")
    private Destination gbaToevalligeGebeurtenissenQueue;

    @Mock
    private Session jmsSession;

    @Mock
    private TextMessage messageToBrp;

    @InjectMocks
    private ToevalligeGebeurtenisVerzender subject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNaamGeslacht() throws JMSException {
        final BijhoudingRegistreerNaamGeslachtMigVrz bijhouding = new BijhoudingRegistreerNaamGeslachtMigVrz();
        subject.verstuurBrpToevalligeGebeurtenisOpdracht(bijhouding, VERZOEK_BERICHT_ID);

        verifyCreateMessage();
    }

    private void verifyCreateMessage() throws JMSException {
        // Verify voor subject
        final ArgumentCaptor<MessageCreator> messageCreator = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate).send(same(gbaToevalligeGebeurtenissenQueue), messageCreator.capture());
        Mockito.verifyNoMoreInteractions(jmsTemplate);

        // Expect voor messageCreator
        Mockito.when(jmsSession.createTextMessage(Matchers.anyString())).thenReturn(messageToBrp);

        messageCreator.getValue().createMessage(jmsSession);
        final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(jmsSession).createTextMessage(messageCaptor.capture());
        Mockito.verifyNoMoreInteractions(jmsSession);
    }

    @Test
    public void testHuwelijkEnGeregistreerdPartnerschap() throws JMSException {
        final BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz bijhouding =
                new BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz();
        subject.verstuurBrpToevalligeGebeurtenisOpdracht(bijhouding, VERZOEK_BERICHT_ID);

        verifyCreateMessage();
    }

    @Test
    public void testOverlijden() throws JMSException {
        final BijhoudingRegistreerOverlijdenMigVrz bijhouding = new BijhoudingRegistreerOverlijdenMigVrz();
        subject.verstuurBrpToevalligeGebeurtenisOpdracht(bijhouding, VERZOEK_BERICHT_ID);

        verifyCreateMessage();
    }

    @Test(expected = IllegalStateException.class)
    public void testOnbekendeBijhouding() throws JMSException {
        final BijhoudingRegistreerErkenningMigVrz bijhouding = new BijhoudingRegistreerErkenningMigVrz();
        subject.verstuurBrpToevalligeGebeurtenisOpdracht(bijhouding, VERZOEK_BERICHT_ID);

        verifyCreateMessage();
    }

    @Test
    public void testGeenBericht() throws JMSException {
        subject.verstuurBrpToevalligeGebeurtenisOpdracht(null, VERZOEK_BERICHT_ID);
    }
}
