/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.jms;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
 * TODO: Add documentation
 */

public class JmsSendingTest extends AbstractIntegratieTest {

    @Autowired
    private TestMessageSender sender;

    @Mock
    private AdministratieveHandelingService handelingService;

    @Autowired
    private MutatieActieMessageListener listener;

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
    public void testOne() throws InterruptedException {
        //given
        Long handelingId = 1234L;
        Bericht bericht = new Bericht();
        bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.GEBOORTE);

        when(handelingService.maakBericht(handelingId)).thenReturn(bericht);
        doNothing().when(handelingService).opslaan(bericht);

        //when
        sender.sendMessage(handelingId);

        Thread.sleep(200);

        //then
        verify(handelingService).maakBericht(handelingId);
        verify(handelingService).opslaan(bericht);
    }
}
