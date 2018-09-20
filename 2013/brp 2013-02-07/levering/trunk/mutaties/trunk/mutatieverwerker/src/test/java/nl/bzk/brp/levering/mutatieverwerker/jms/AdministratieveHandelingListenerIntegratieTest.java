/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.jms;

import nl.bzk.brp.levering.mutatieverwerker.AbstractIntegratieTest;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unittests (integratie tests) voor {@link AdministratieveHandelingListener}.
 */
public class AdministratieveHandelingListenerIntegratieTest extends AbstractIntegratieTest {

    @Autowired
    private TestMessageSender sender;

    @Autowired
    @InjectMocks
    private AdministratieveHandelingListener listener;

    @Mock
    private AdministratieveHandelingVerwerkerService administratieveHandelingVerwerkerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        Mockito.validateMockitoUsage();
    }

    @Test
    public void kanEenJmsBerichtOntvangen() throws InterruptedException {
        final Long handelingId = 1234L;

        sender.sendMessage(handelingId);
    }
}
