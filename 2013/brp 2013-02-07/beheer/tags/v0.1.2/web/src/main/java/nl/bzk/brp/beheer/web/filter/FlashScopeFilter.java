/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Zorgt ervoor dat elk request parameter naam die begint met flash. beschikbaar is voor de volgende request. Dit
 * simuleert een FlashScope.
 *
 */
public class FlashScopeFilter implements Filter {

    private static final String FLASH_SESSION_KEY = "FLASH_SESSION_KEY";
    private static final String FLASH_KEY_PREFIX  = "flash.";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
        throws IOException, ServletException
    {

        // herlaad flash scoped parameters van gebruiker sessie en verwijder het daarna
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession(false);
            if (session != null) {
                Map<String, Object> flashParams = (Map<String, Object>) session.getAttribute(FLASH_SESSION_KEY);
                if (flashParams != null) {
                    for (Map.Entry<String, Object> flashEntry : flashParams.entrySet()) {
                        request.setAttribute(flashEntry.getKey(), flashEntry.getValue());
                    }
                    session.removeAttribute(FLASH_SESSION_KEY);
                }
            }
        }

        // process de chain
        chain.doFilter(request, response);

        // Sla flash scoped parameters in de gebruiker sessie voor de volgende request
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Map<String, Object> flashParams = new HashMap<String, Object>();
            @SuppressWarnings("rawtypes")
            Enumeration e = httpRequest.getAttributeNames();
            while (e.hasMoreElements()) {
                String paramName = (String) e.nextElement();
                if (paramName.startsWith(FLASH_KEY_PREFIX)) {
                    Object value = request.getAttribute(paramName);
                    paramName = paramName.substring(FLASH_KEY_PREFIX.length(), paramName.length());
                    flashParams.put(paramName, value);
                }
            }
            if (flashParams.size() > 0) {
                HttpSession session = httpRequest.getSession(false);
                session.setAttribute(FLASH_SESSION_KEY, flashParams);
            }
        }
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // no-op
    }

    @Override
    public void destroy() {
        // no-op
    }
}
