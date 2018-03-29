/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.mockrunner.mock.jms.MockTextMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.gba.domain.bevraging.Persoonsantwoord;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.JmsException;

public class MessageParserTest {

    private TextMessage message = mock(TextMessage.class);
    private MessageParser subject;

    @Before
    public void setup() {
        subject = new MessageParser(message);
    }

    @Test
    public void getBerichtReferentie() throws JMSException {
        when(message.getStringProperty(anyString())).thenReturn("referentie");
        assertEquals("referentie", subject.getBerichtReferentie());
    }

    @Test(expected = MessageParser.AdhocVraagException.class)
    public void getBerichtReferentieExceptie() throws JMSException {
        when(message.getStringProperty(anyString())).thenThrow(new JMSException("reason"));
        subject.getBerichtReferentie();
    }

    @Test
    public void parseVerzoek() throws JMSException {
        when(message.getText()).thenReturn("{\"partijCode\":\"1234\"}");
        assertThat(subject.parseVerzoek(Persoonsvraag.class), isA(Persoonsvraag.class));
    }

    @Test(expected = MessageParser.AdhocVraagException.class)
    public void parseVerzoekOngeldigBericht() throws JMSException {
        when(message.getText()).thenReturn("{\"partijCode\"\"1234\"}");
        assertThat(subject.parseVerzoek(Persoonsvraag.class), isA(Persoonsvraag.class));
    }

    @Test(expected = MessageParser.AdhocVraagException.class)
    public void parseVerzoekBerichtExceptie() throws JMSException {
        when(message.getText()).thenThrow(new JMSException("reason"));
        subject.parseVerzoek(Persoonsvraag.class);
    }

    @Test
    public void composeAntwoord() throws JMSException {
        when(message.getStringProperty(anyString())).thenReturn("referentie1");

        MockTextMessage textMessage = new MockTextMessage();
        Session session = mock(Session.class);
        when(session.createTextMessage("{\"foutreden\":\"X\"}")).thenReturn(textMessage);
        Persoonsantwoord antwoord = new Persoonsantwoord();
        antwoord.setFoutreden("X");

        Message antwoordMessage = subject.composeAntwoord(antwoord, session);
        assertEquals(false, antwoordMessage.getStringProperty("iscBerichtReferentie").isEmpty());
        assertEquals("referentie1", antwoordMessage.getStringProperty("iscCorrelatieReferentie"));
    }

    @Test(expected = JmsException.class)
    public void composeAntwoordExceptie() throws JMSException {
        when(message.getStringProperty(anyString())).thenReturn("referentie1");

        Session session = mock(Session.class);
        when(session.createTextMessage("{\"foutreden\":\"X\"}")).thenThrow(new JMSException("reason"));
        Persoonsantwoord antwoord = new Persoonsantwoord();
        antwoord.setFoutreden("X");

        subject.composeAntwoord(antwoord, session);
    }
}
