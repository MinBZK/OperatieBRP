/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken.AdHocZoekenNaarBrpMessageCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

/**
 * Test voor de VrijBerichtNaarBrpVerzender.
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtNaarBrpVerzenderTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private VrijBerichtNaarBrpVerzender verzender;

    @Test
    public void testVerzenden() throws Exception {
        final VrijBerichtOpdracht vraag = new VrijBerichtOpdracht();
        verzender.verstuurVrijBericht(vraag, "destination1", "verzendId");
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(Mockito.eq("destination1"), Mockito.any(AdHocZoekenNaarBrpMessageCreator.class));
    }
}