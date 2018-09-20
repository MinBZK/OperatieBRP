/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor;

import nl.bzk.brp.bevraging.business.service.BerichtIdGenerator;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.message.MessageImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link BerichtIdGeneratorInterceptor} class.
 */
public class BerichtIdGeneratorInterceptorTest {

    @Mock
    private BerichtIdGenerator berichtIdGenerator;
    @Mock
    private MessageImpl message;

    private BerichtIdGeneratorInterceptor berichtIdGeneratorInterceptor = null;

    @Test
    public void testHandleMessage() {
        berichtIdGeneratorInterceptor.handleMessage(new SoapMessage(message));
        Mockito.verify(berichtIdGenerator).volgendeId();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        berichtIdGeneratorInterceptor = new BerichtIdGeneratorInterceptor();
        ReflectionTestUtils.setField(berichtIdGeneratorInterceptor, "berichtIdGenerator", berichtIdGenerator);
    }


}
