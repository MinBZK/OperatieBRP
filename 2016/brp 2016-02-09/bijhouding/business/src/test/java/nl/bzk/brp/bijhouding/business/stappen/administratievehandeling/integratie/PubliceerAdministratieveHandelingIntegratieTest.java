/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.integratie;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.inject.Inject;
import javax.jms.JMSException;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.PubliceerAdministratieveHandelingStap;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

public class PubliceerAdministratieveHandelingIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PubliceerAdministratieveHandelingStap publiceerAdministratieveHandelingStap;

    @Inject
    private AdministratieveHandelingListener administratieveHandelingListener;

    @DirtiesContext
    @Test
    public void testVoerNabewerkingStapUit() throws JMSException {
        Long verwacht = 12345L;
        BijhoudingBerichtContext testContext = mock(BijhoudingBerichtContext.class);
        when(testContext.getResultaatId()).thenReturn(verwacht);

        publiceerAdministratieveHandelingStap.voerUit(testContext);

        //Geef de listener kans om waarde uit te lezen van queue
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            LOGGER.error("Fout opgetreden: " + e.getLocalizedMessage());
        }

        assertEquals(verwacht, administratieveHandelingListener.getAdministratieveHandelingId());
    }

}
