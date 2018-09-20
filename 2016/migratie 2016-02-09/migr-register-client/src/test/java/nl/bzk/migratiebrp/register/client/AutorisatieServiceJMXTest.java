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

public class AutorisatieServiceJMXTest {

    private AutorisatieServiceJMX subject;
    private AutorisatieService autorisatieService;

    @Before
    public void setup() {
        autorisatieService = Mockito.mock(AutorisatieService.class);
        subject = new AutorisatieServiceJMX();
        ReflectionTestUtils.setField(subject, "autorisatieService", autorisatieService);
    }

    @Test
    public void refreshRegister() {
        subject.refreshRegister();

        Mockito.verify(autorisatieService).refreshRegister();
        Mockito.verifyNoMoreInteractions(autorisatieService);
    }

    @Test
    public void clearRegister() {
        subject.clearRegister();

        Mockito.verify(autorisatieService).clearRegister();
        Mockito.verifyNoMoreInteractions(autorisatieService);
    }
}
