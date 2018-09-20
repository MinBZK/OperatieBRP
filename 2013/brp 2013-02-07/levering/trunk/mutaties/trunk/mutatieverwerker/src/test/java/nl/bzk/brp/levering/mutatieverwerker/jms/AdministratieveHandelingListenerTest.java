/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.jms;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import javax.jms.JMSException;
import javax.jms.Message;

import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittests voor {@link nl.bzk.brp.levering.mutatieverwerker.jms.AdministratieveHandelingListener}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AdministratieveHandelingListenerTest {

    @InjectMocks
    private AdministratieveHandelingListener listener;

    @Mock
    private AdministratieveHandelingVerwerkerService administratieveHandelingVerwerkerService;

    @Test
    public void testOnMessage() throws JMSException {
        final Message message = mock(Message.class);
        when(message.getLongProperty(anyString())).thenReturn(123L);

        listener.onMessage(message);

        verify(message).getLongProperty(anyString());
    }

    @Test
    public void testOnMessageMetJMSExceptie() throws JMSException {
        final Message message = mock(Message.class);
        when(message.getLongProperty(anyString())).thenThrow(new JMSException(null));

        listener.onMessage(message);

        verifyZeroInteractions(administratieveHandelingVerwerkerService);
    }

}
