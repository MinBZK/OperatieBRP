/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import nl.bzk.brp.beheer.webapp.configuratie.CorsFilter;
import nl.bzk.brp.beheer.webapp.configuratie.LogFilter;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.SecurityConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.logging.BeheerVeld;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Webapplication initializer.
 */
public final class BeheerInitializer extends AbstractSecurityWebApplicationInitializer {

    private static final String MAPPING_ALLES = "/*";

    static {
        org.slf4j.MDC.put(BeheerVeld.MDC_APPLICATIE_NAAM.getVeld(), ApplicationConstants.APPLICATIE_NAAM);
    }

    /**
     * Constructor.
     */
    public BeheerInitializer() {
        // The super class with create the 'root' Spring application context
        super(RepositoryConfiguratie.class, SecurityConfiguratie.class);
    }

    @Override
    public void afterSpringSecurityFilterChain(final ServletContext container) {
        // Create the dispatcher servlet's Spring application context
        final AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(WebConfiguratie.class);

        // Register and map the dispatcher servlet
        final ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    @Override
    public void beforeSpringSecurityFilterChain(final ServletContext container) {
        final FilterRegistration.Dynamic logFilter = container.addFilter("logFilter", LogFilter.class);
        logFilter.addMappingForUrlPatterns(null, false, MAPPING_ALLES);
        final FilterRegistration.Dynamic corsFilter = container.addFilter("corsFilter", CorsFilter.class);
        corsFilter.addMappingForUrlPatterns(null, false, MAPPING_ALLES);
    }
}
