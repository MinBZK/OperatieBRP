/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import nl.bzk.brp.bevraging.business.service.ArchiveringService;
import nl.bzk.brp.bevraging.domein.ber.Richting;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.message.MessageImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


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

    /**
     * Test dat een lege message geen problemen geeft.
     */
    @Test
    public void testHandleMessageVoorLeegBericht() {
        interceptor.handleMessage(new SoapMessage(getMessage(InputStream.class, null)));
    }

    /**
     * Test dat een lege message geen problemen geeft.
     */
    @Test
    public void testHandleMessageVoorValideBericht() {
        String test = "Test";
        SoapMessage msg = new SoapMessage(getMessage(InputStream.class, new ByteArrayInputStream(test.getBytes())));

        interceptor.handleMessage(msg);

        Mockito.verify(archiveringService).archiveer(test, Richting.INGAAND);
        assertNotNull(msg.get(BerichtIdGeneratorInterceptor.BRP_BERICHT_ID));
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        interceptor = new ArchiveringInInterceptor();

        ReflectionTestUtils.setField(interceptor, "archiveringService", archiveringService);
    }

    private MessageImpl getMessage(final Class<? extends Object> format, final Object content) {
        MessageImpl msg = new MessageImpl();
        msg.setContent(format, content);
        // Indirecte initialisatie van de context voor de message
        msg.getContextualProperty("Test");
        return msg;
    }

}
