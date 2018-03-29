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

public class PartijServiceJMXTest {

    private PartijServiceJMX subject;
    private PartijService partijService;

    @Before
    public void setup() {
        partijService = Mockito.mock(PartijService.class);
        subject = new PartijServiceJMX();
        ReflectionTestUtils.setField(subject, "partijService", partijService);
    }

    @Test
    public void refreshRegister() {
        subject.refreshRegister();

        Mockito.verify(partijService).refreshRegister();
        Mockito.verifyNoMoreInteractions(partijService);
    }

    @Test
    public void clearRegister() {
        subject.clearRegister();

        Mockito.verify(partijService).clearRegister();
        Mockito.verifyNoMoreInteractions(partijService);
    }
}
