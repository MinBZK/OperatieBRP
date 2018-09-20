/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import nl.bzk.brp.bevraging.business.service.ArchiveringService;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit tests voor de {@link ArchiveringServiceLogger} class.
 */
public class ArchiveringServiceLoggerTest {

    @Mock
    private ArchiveringService service;

    private Logger             archiveringServiceLogger = null;

    @Test
    public void testArchiveringServiceLogger() {
        archiveringServiceLogger.log(Level.WARNING, "Test");
        Mockito.verify(service).archiveer("Test", SoortBericht.OPVRAGEN_PERSOON_ANTWOORD);
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        archiveringServiceLogger = new UitgaandeArchiveringServiceLogger();
        ReflectionTestUtils.setField(archiveringServiceLogger, "archiveringService", service);
    }

}
