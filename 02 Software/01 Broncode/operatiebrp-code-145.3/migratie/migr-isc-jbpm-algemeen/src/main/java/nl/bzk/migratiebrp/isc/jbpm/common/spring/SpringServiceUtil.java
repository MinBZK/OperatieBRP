/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.spring;

import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Spring Service utilities.
 */
public final class SpringServiceUtil {

    private SpringServiceUtil() {
        // Niet instantieerbaar
    }

    /**
     * Get the SpringService from the current execution context (fallback to JbpmContext.getCurrentJbpmContext if
     * execution context is null).
     * @return spring service
     */
    public static SpringService getSpringService() {
        final JbpmContext jbpmContext;
        if (ExecutionContext.currentExecutionContext() != null) {
            jbpmContext = ExecutionContext.currentExecutionContext().getJbpmContext();

        } else {
            jbpmContext = JbpmContext.getCurrentJbpmContext();
        }

        if (jbpmContext == null) {
            throw new IllegalArgumentException("Kan geen JBPM Context bepalen om SpringService mee op te halen.");
        }

        return (SpringService) jbpmContext.getServices().getService(SpringServiceFactory.SERVICE_NAME);
    }

    /**
     * Get a bean from the spring bean factory.
     * @param <T> bean type
     * @param name bean name
     * @param clazz bean class
     * @return the bean
     * @see org.springframework.beans.factory.BeanFactory#getBean(String, Class)
     */
    public static <T> T getBean(final String name, final Class<T> clazz) {
        return getSpringService().getBean(name, clazz);
    }

    /**
     * Get a bean from the spring bean factory.
     * @param <T> bean type
     * @param clazz bean class
     * @return the bean
     * @see org.springframework.beans.factory.BeanFactory#getBean(Class)
     */
    public static <T> T getBean(final Class<T> clazz) {
        return getSpringService().getBean(clazz);
    }
}
