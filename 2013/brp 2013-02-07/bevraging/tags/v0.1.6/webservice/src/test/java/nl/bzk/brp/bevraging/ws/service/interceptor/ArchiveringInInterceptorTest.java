/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor;

import nl.bzk.brp.bevraging.business.service.ArchiveringService;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


/**
 * Unit test voor de {@link ArchiveringInInterceptor} class.
 */
public class ArchiveringInInterceptorTest {

    @Mock
    private ArchiveringService       archiveringService;
    private ArchiveringInInterceptor interceptor = null;

    /**
     * Test van de constructor en test of deze constructor in de juist volgorde is gezet.
     */
    @Test
    public void testArchiveringInInterceptorVolgorde() {
        assertTrue(interceptor.getAfter().contains(BerichtIdGeneratorInterceptor.class.getName()));
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        interceptor = new ArchiveringInInterceptor();
    }
}
