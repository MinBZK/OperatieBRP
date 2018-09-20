/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.service.listener;

import static org.junit.Assert.*;

import java.util.Arrays;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.brp.relateren.service.RelateerService;
import nl.bzk.brp.relateren.service.bericht.RelateerPersoonBericht;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Testen voor RelateerListener.
 */
@RunWith(MockitoJUnitRunner.class)
public class RelateerListenerTest {

    @Mock
    private RelateerService relateerService;

    @InjectMocks
    private RelateerListener relateerListener;

    /**
     * Test onMessage afhandeling.
     */
    @Test
    public void testOnMessage() throws JMSException {
        // expected
        final RelateerPersoonBericht bericht = new RelateerPersoonBericht(Arrays.asList(1, 2, 3));
        final String berichtString = bericht.writeValueAsString();

        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getText()).thenReturn(berichtString);

        // execute
        relateerListener.onMessage(message);

        // verify
        final ArgumentCaptor<RelateerPersoonBericht> argument = ArgumentCaptor.forClass(RelateerPersoonBericht.class);
        Mockito.verify(relateerService).verwerkPersoonRelateerBericht(argument.capture());
        assertEquals(bericht.getTeRelaterenPersoonIds(), argument.getValue().getTeRelaterenPersoonIds());
    }

    /**
     * Test onMessage foutafhandeling.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testOnMessageJMSFout() throws JMSException {
        // expected
        final TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getText()).thenThrow(Mockito.mock(JMSException.class));

        // execute
        relateerListener.onMessage(message);
    }

    /**
     * Test onMessage foutafhandeling.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testOnMessageBerichtFout() throws JMSException {
        // expected
        final Message message = Mockito.mock(Message.class);

        // execute
        relateerListener.onMessage(message);
    }
}
