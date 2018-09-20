/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class GemeenteServiceJMXTest {

    private GemeenteServiceJMX subject;
    private GemeenteService gemeenteService;

    @Before
    public void setup() {
        gemeenteService = Mockito.mock(GemeenteService.class);
        subject = new GemeenteServiceJMX();
        ReflectionTestUtils.setField(subject, "gemeenteService", gemeenteService);
    }

    @Test
    public void refreshRegister() {
        subject.refreshRegister();

        Mockito.verify(gemeenteService).refreshRegister();
        Mockito.verifyNoMoreInteractions(gemeenteService);
    }

    @Test
    public void clearRegister() {
        subject.clearRegister();

        Mockito.verify(gemeenteService).clearRegister();
        Mockito.verifyNoMoreInteractions(gemeenteService);
    }
}
