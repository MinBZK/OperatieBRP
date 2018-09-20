/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.spring;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.JbpmContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * Abstracte handler om iets te doen in spring. Deze handler zorgt voor het opzetten van de application context
 * (bpm-application-context.xml).
 */
public class SpringHandler {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Zoek de bean met een bepaalde naam en klasse.
     *
     * @param <T>
     *            klasse
     * @param name
     *            naam
     * @param clazz
     *            klasse
     * @return bean
     */
    protected final <T> T getBean(final String name, final Class<T> clazz) {
        final SpringService springService = (SpringService) JbpmContext.getCurrentJbpmContext().getServices().getService("spring");

        try {
            return springService.getBean(name, clazz);
        } catch (final NoSuchBeanDefinitionException e) {
            LOG.info("Error in finding bean: {}", name);

            throw e;
        }
    }

}
