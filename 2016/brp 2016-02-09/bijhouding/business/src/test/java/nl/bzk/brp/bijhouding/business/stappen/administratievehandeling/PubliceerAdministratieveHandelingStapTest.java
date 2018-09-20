/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.administratievehandeling;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import nl.bzk.brp.business.stappen.AbstractStappenResultaat;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.integratie.TestResultaat;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


@RunWith(MockitoJUnitRunner.class)
public class PubliceerAdministratieveHandelingStapTest {

    @InjectMocks
    private final PubliceerAdministratieveHandelingStap
        publiceerAdministratieveHandelingStap =
        new PubliceerAdministratieveHandelingStap();

    @Mock
    private JmsTemplate administratieveHandelingJmsTemplate;

    @Mock
    private BijhoudingsBericht onderwerp;

    @Mock
    private BijhoudingBerichtContext context;

    @Before
    public void init() {
        Destination testDestination = new ActiveMQQueue("testQueue");
        when(administratieveHandelingJmsTemplate.getDefaultDestination()).thenReturn(testDestination);

        ConnectionFactory testConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        when(administratieveHandelingJmsTemplate.getConnectionFactory()).thenReturn(testConnectionFactory);
    }

    @Test
    public void testVoerNabewerkingStapUit() {
        Destination testDestination = new ActiveMQQueue("testQueue");
        when(administratieveHandelingJmsTemplate.getDefaultDestination()).thenReturn(testDestination);

        ConnectionFactory testConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        when(administratieveHandelingJmsTemplate.getConnectionFactory()).thenReturn(testConnectionFactory);

        publiceerAdministratieveHandelingStap.voerUit(context);

        verify(administratieveHandelingJmsTemplate).send(any(MessageCreator.class));
    }

    @Test
    public void testVoerNabewerkingStapUitEnGeenConnectionFactory() {
        when(administratieveHandelingJmsTemplate.getConnectionFactory()).thenReturn(null);

        AbstractStappenResultaat resultaat = new TestResultaat();

        when(context.getResultaatId()).thenReturn(null);

        assertTrue(resultaat.getMeldingen().size() == 0);

        publiceerAdministratieveHandelingStap.voerUit(context);

        verify(administratieveHandelingJmsTemplate, Mockito.times(0)).send(any(MessageCreator.class));
    }

    @Test
    public void testVoerNabewerkingStapUitEnGeenDefaultDestination() {
        when(administratieveHandelingJmsTemplate.getDefaultDestination()).thenReturn(null);

        AbstractStappenResultaat resultaat = new TestResultaat();

        when(context.getResultaatId()).thenReturn(null);

        assertTrue(resultaat.getMeldingen().size() == 0);

        publiceerAdministratieveHandelingStap.voerUit(context);

        verify(administratieveHandelingJmsTemplate, Mockito.times(0)).send(any(MessageCreator.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoerNabewerkingStapUitEnOnbekendeDefaultDestination() {
        Destination testDestination = new ActiveMQQueue("");
        when(administratieveHandelingJmsTemplate.getDefaultDestination()).thenReturn(testDestination);

        AbstractStappenResultaat resultaat = new TestResultaat();

        when(context.getResultaatId()).thenReturn(null);

        assertTrue(resultaat.getMeldingen().size() == 0);

        publiceerAdministratieveHandelingStap.voerUit(context);

        verify(administratieveHandelingJmsTemplate, Mockito.times(0)).send(any(MessageCreator.class));
    }

    @Test
    public void testVoerNabewerkingStapUitEnKanAdministratieveHandelingNietOphalen() {
        AbstractStappenResultaat resultaat = new TestResultaat();

        when(context.getResultaatId()).thenReturn(null);

        assertTrue(resultaat.getMeldingen().size() == 0);

        publiceerAdministratieveHandelingStap.voerUit(context);

        assertTrue(resultaat.getMeldingen().size() == 0);
        verify(administratieveHandelingJmsTemplate, Mockito.times(0)).send(any(MessageCreator.class));
    }


    @Test
    public void testPublicerenAdministratieveHandelingIdMislukt() {
        AbstractStappenResultaat resultaat = new TestResultaat();
        JmsException exceptie = mock(JmsException.class);
        doThrow(exceptie).when(administratieveHandelingJmsTemplate).send(any(MessageCreator.class));

        assertTrue(resultaat.getMeldingen().size() == 0);

        publiceerAdministratieveHandelingStap.voerUit(context);

        assertTrue(resultaat.getMeldingen().size() == 0);
    }


    @Test
    public void testPublicerenAdministratieveMagNietBlokkerendZijn() {
        AbstractStappenResultaat resultaat = new TestResultaat();
        RuntimeException exceptie = mock(RuntimeException.class);
        doThrow(exceptie).when(administratieveHandelingJmsTemplate).send(any(MessageCreator.class));

        publiceerAdministratieveHandelingStap.voerUit(context);

        assertTrue(resultaat.getMeldingen().size() == 0);
    }


}
