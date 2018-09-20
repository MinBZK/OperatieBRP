/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor voor CORS requests.
 */
public class CorsInterceptor extends HandlerInterceptorAdapter {

    @Override
    public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        // InputStream in = getClass().getResourceAsStream("/META-INF/app.properties");
        // Properties prop = new Properties();
        // prop.load(in);
        // in.close();
        //
        // Set<String> allowedOrigins = new
        // HashSet<String>(Arrays.asList(prop.getProperty("allowed.origins").split(",")));
        //
        // String origin = request.getHeader("Origin");
        // if (allowedOrigins.contains(origin)) {
        // response.addHeader("Access-Control-Allow-Origin", origin);
        // return true;
        // } else {
        // return false;
        // }
        response.addHeader("Access-Control-Allow-Origin", "*");
        return true;
    }
}
