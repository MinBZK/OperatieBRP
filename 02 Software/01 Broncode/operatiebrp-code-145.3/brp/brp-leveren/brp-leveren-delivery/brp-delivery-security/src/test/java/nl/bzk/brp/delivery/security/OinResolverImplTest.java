/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.security;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import nl.bzk.brp.service.algemeen.request.OIN;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class OinResolverImplTest {

    private OinResolverImpl resolver = new OinResolverImpl();

    @Test
    public void testGet() {
        final String ondertekenaar = "ondertekenaar";
        final String transporteur = "transporteur";
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getHeader(OIN.OIN_ONDERTEKENAAR)).thenReturn(ondertekenaar);
        Mockito.when(httpServletRequest.getHeader(OIN.OIN_TRANSPORTEUR)).thenReturn(transporteur);

        final Message message = mock(Message.class);
        Mockito.when(message.get(AbstractHTTPDestination.HTTP_REQUEST)).thenReturn(httpServletRequest);

        setCurrentMessage(message);

        when(message.get(any())).thenReturn(httpServletRequest);

        final OIN oin = resolver.get();

        Assert.assertEquals(ondertekenaar, oin.getOinWaardeOndertekenaar());
        Assert.assertEquals(transporteur, oin.getOinWaardeTransporteur());
    }

    private static void setCurrentMessage(Message message) {
        ThreadLocal<Message> currentMessage = (ThreadLocal<Message>) ReflectionTestUtils.getField(PhaseInterceptorChain.class, "CURRENT_MESSAGE");
        currentMessage.set(message);
    }
}
