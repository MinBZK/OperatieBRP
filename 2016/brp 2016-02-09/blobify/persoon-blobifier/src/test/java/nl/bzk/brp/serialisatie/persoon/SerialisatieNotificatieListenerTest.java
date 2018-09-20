/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.brp.vergrendeling.VergrendelFout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SerialisatieNotificatieListenerTest {

    @Mock
    private PersoonSerialiseerder persoonSerialiseerder;

    @InjectMocks
    private SerialisatieNotificatieListener listener;

    @Test
    public void testOnMessage() throws JMSException {
        final Message message = mock(Message.class);
        when(message.getIntProperty(anyString())).thenReturn(123);

        listener.onMessage(message);

        verify(message).getIntProperty(anyString());
    }

    @Test
    public void testOnMessageMetJMSExceptie() throws JMSException {
        final Message message = mock(Message.class);
        when(message.getIntProperty(anyString())).thenThrow(new JMSException(null));

        listener.onMessage(message);

        verifyZeroInteractions(persoonSerialiseerder);
    }

    @Test
    public void testOnMessageMetVergrendelFout() throws VergrendelFout {
        final Message message = mock(Message.class);
        doThrow(new VergrendelFout(null)).when(persoonSerialiseerder).serialiseerPersoon(Matchers.any(Integer.class));

        listener.onMessage(message);

    }

}
