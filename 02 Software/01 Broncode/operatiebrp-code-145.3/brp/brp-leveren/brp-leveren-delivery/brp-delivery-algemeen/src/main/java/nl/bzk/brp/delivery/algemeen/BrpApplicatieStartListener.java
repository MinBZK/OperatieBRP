/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import java.util.Locale;
import java.util.TimeZone;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;


/**
 * Deze listener zorgt voor een logmelding wanneer de applicatie context klaar is met opstarten. Hiermee kan worden
 * gezien of de applicatie succesvol is gedeployed en opgestart.
 */
@Service
public class BrpApplicatieStartListener implements ApplicationListener<ApplicationContextEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final void onApplicationEvent(final ApplicationContextEvent event) {
        try {
            final ApplicationContext context = event.getApplicationContext();
            if (context instanceof XmlWebApplicationContext) {
                final XmlWebApplicationContext ctx = (XmlWebApplicationContext) context;
                String webContextName = ctx.getServletContext().getServletContextName();
                if (webContextName.contains("/")) {
                    webContextName = webContextName.substring(webContextName.indexOf('/'));
                }
                ctx.setDisplayName(webContextName);
                MDC.put(LeveringVeld.MDC_APPLICATIE_NAAM, webContextName).close();
            }
            LOGGER.debug("Context event '{}'; id='{}', displayName='{}'", event.getClass().getSimpleName(), event
                    .getApplicationContext().getId(), event.getApplicationContext().getDisplayName());

            if (event instanceof ContextClosedEvent) {
                LOGGER.info("==> Context voor applicatie '{}' is gestopt", event.getApplicationContext().getId());
            }
            /* Wanneer een applicatie is gestart, dan wordt een ContextRefreshedEvent gestuurt **/
            if (event instanceof ContextRefreshedEvent) {
                LOGGER.info("==> Context voor applicatie '{}' is gestart/herstart", event.getApplicationContext()
                        .getId());
                logJvmSettings();
            }
        } catch (final ApplicationContextException ex) {
            LOGGER.error("Fout bij het opstarten van de applicatiecontext bij event {}", event.toString(), ex);
        }


    }

    /**
     * Log de jvm settings.
     */
    private void logJvmSettings() {
        LOGGER.debug("Systeem taal: {}", Locale.getDefault().getDisplayName());
        LOGGER.debug("Systeem tijdzone: {}", TimeZone.getDefault().getDisplayName());
        LOGGER.debug("Java versie: {}", System.getProperty("java.version"));
    }
}
