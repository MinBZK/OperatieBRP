/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */


package nl.bzk.brp.beheer.webapp.configuratie;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CorsFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    /**
     * Test of doFilterInternal method, of class CorsFilter.
     * @throws java.lang.Exception kan stuk gaan
     */
    @Test
    public final void testDoFilterInternal() throws Exception {
        final CorsFilter filter = new CorsFilter();
        final HttpServletResponse testResponse = new TestResponse(response);

        Mockito.when(request.getHeader("Access-Control-Request-Method")).thenReturn("GET");
        Mockito.when(request.getMethod()).thenReturn("OPTIONS");
        Mockito.when(request.getHeader("Origin")).thenReturn("http://blabla.com?bla=bla%0D%0Ablabla");
        filter.doFilterInternal(request, testResponse, chain);
        Assert.assertEquals("http://blabla.com?bla=bla", testResponse.getHeader("Access-Control-Allow-Origin"));
    }

    /**
     * Om te zien of header goed wordt weggeschreven.
     */
    private class TestResponse extends HttpServletResponseWrapper {
        private final Map<String, String> headers;

        TestResponse(final HttpServletResponse response) {
            super(response);
            this.headers = new HashMap<>();
        }

        @Override
        public final void setHeader(final String key, final String value) {
            headers.put(key, value);
        }

        @Override
        public final String getHeader(final String key) {
            return headers.get(key);
        }
    }
}
