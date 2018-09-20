/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.jms;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.JMSException;
import javax.jms.Message;

import nl.bzk.brp.preview.integratie.AbstractIntegratieTest;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import nl.bzk.brp.preview.service.AdministratieveHandelingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unittests voor {@link AdministratieveHandelingListener}.
 */
public class AdministratieveHandelingListenerTest extends AbstractIntegratieTest {

    @Autowired
    private TestMessageSender sender;

    @Mock
    private AdministratieveHandelingService handelingService;

    @Autowired
    private AdministratieveHandelingListener listener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        listener.setAdministratieveHandelingService(handelingService);
    }

    @After
    public void tearDown() {
        Mockito.validateMockitoUsage();
    }

    @Test
    public void kanEenBerichtVerwerken() throws JMSException {
        // given
        Message message = mock(Message.class);
        when(message.getLongProperty(anyString())).thenReturn(123L);

        // when
        listener.onMessage(message);

        // then
        verify(message).getLongProperty(anyString());
    }

    @Test
    public void kanEenJmsBerichtOntvangen() throws InterruptedException {
        //given
        Long handelingId = 1234L;
        Bericht bericht = new Bericht();
        bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.GEBOORTE);

        when(handelingService.maakBericht(handelingId)).thenReturn(bericht);
        doNothing().when(handelingService).opslaan(bericht);

        //when
        sender.sendMessage(handelingId);

        //then
        verify(handelingService, timeout(200)).maakBericht(handelingId);
        verify(handelingService, timeout(200)).opslaan(bericht);
    }
}
