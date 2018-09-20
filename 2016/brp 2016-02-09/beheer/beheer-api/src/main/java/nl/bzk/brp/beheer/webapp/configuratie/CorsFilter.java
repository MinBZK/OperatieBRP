/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter voor CORS requests.
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected final void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
        throws ServletException, IOException
    {
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", sanitize(request.getHeader("Origin")));
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader(
                "Access-Control-Allow-Headers",
                "Host, User-Agent, X-Requested-With, Accept, Accept-Language, Accept-Encoding, Authorization, Referer, Connection, Content-Type");
        }
        filterChain.doFilter(request, response);
    }

    private String sanitize(final String url) {
        return url.replaceAll("%0D.*$", "").replaceAll("%0A.*$", "");
    }
}
