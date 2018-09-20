/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.jms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.levering.mutatielevering.service.AdministratieveHandelingVerwerkerService;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.internbericht.AdministratieveHandelingVerwerktOpdracht;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import support.TestMessageSender;

/**
 * Unittests (integratie tests) voor {@link nl.bzk.brp.levering.mutatielevering.jms.AdministratieveHandelingListener}.
 */
public class AdministratieveHandelingListenerIntegratieTest extends AbstractIntegratieTest {

    private static final Long HANDELING_ID = 1234L;

    @Autowired
    private TestMessageSender sender;

    @Autowired
    private AdministratieveHandelingListener listener;

    private final AdministratieveHandelingVerwerkerService administratieveHandelingVerwerkerService = mock(AdministratieveHandelingVerwerkerService.class);

    @Test
    public final void kanEenJmsBerichtOntvangen() throws InterruptedException {
        final AdministratieveHandelingVerwerktOpdracht opdracht = new AdministratieveHandelingVerwerktOpdracht(HANDELING_ID, new PartijCodeAttribuut(123),
            null);

        ReflectionTestUtils.setField(listener, "administratieveHandelingVerwerkerService", administratieveHandelingVerwerkerService);

        sender.sendMessage(opdracht);

        Thread.sleep(2000);

        verify(administratieveHandelingVerwerkerService).verwerkAdministratieveHandeling(HANDELING_ID);
    }
}
