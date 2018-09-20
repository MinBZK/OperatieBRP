/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;


public class FlashScopeFilterTest {

    private MockHttpServletRequest  httpServletRequest;
    private MockHttpServletResponse httpServletResponse;
    private MockFilterChain         filterChain;
    private MockHttpSession         httpSession;

    private FlashScopeFilter        flashScopeFilter;

    @Before
    public void setUp() {
        filterChain = new MockFilterChain();
        httpServletRequest = new MockHttpServletRequest();
        httpServletResponse = new MockHttpServletResponse();
        httpSession = new MockHttpSession();

        httpServletRequest.setSession(httpSession);

        flashScopeFilter = new FlashScopeFilter();
    }

    @Test
    public void testDoFilterFlashMetFlashScopeVariable() throws IOException, ServletException {
        // Eerste request

        // Stop flash.parameter op de request
        httpServletRequest.setAttribute("flash.parameter", "parameter waarde");
        // Voer request uit
        flashScopeFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        // Parameter is tijdelijk op de sessie gestopt
        @SuppressWarnings("unchecked")
        Map<String, Object> flashParams = (Map<String, Object>) httpSession.getAttribute("FLASH_SESSION_KEY");
        Assert.assertEquals("attribute is tijdelijk in de sessie gezet", "parameter waarde",
                flashParams.get("parameter"));

        // Tweede request
        httpServletRequest = new MockHttpServletRequest();
        filterChain = new MockFilterChain();
        httpServletRequest.setSession(httpSession);
        // Parameters mogen nog niet in de request staan totdat de filter is uitgevoerd
        Assert.assertNull(httpServletRequest.getAttribute("flash.parameter"));
        Assert.assertNull(httpServletRequest.getAttribute("parameter"));

        // Request wordt uitgevoerd en gaat door de filter heen
        flashScopeFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // parameter staat weer in de request
        Assert.assertEquals("parameter waarde", httpServletRequest.getAttribute("parameter"));

        // parameter is verwijderd uit de sessie
        Assert.assertNull("attribute is verwijderd van de sessie gezet", httpSession.getAttribute("FLASH_SESSION_KEY"));
    }
}
