/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import javax.jms.Destination;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

/**
 * Test voor AdHocZoekenNaarIscVerzender
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtAntwoordNaarIscVerzenderTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private Destination destination;

    @Mock
    private AdHocZoekPersoonAntwoordBericht bericht;

    @InjectMocks
    private VrijBerichtAntwoordNaarIscVerzender verzender;

    @Test
    public void testVerzenden() {
        verzender.verstuurVrijBerichtAntwoord(bericht);
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(Mockito.any(Destination.class), Mockito.any(VrijBerichtAntwoordNaarIscMessageCreator.class));
    }
}