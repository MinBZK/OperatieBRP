/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.spring;

import org.jbpm.svc.Service;
import org.springframework.beans.factory.BeanFactory;

/**
 * Spring integratie.
 */
public final class SpringService implements Service {

    private static final long serialVersionUID = 1L;

    private final transient BeanFactory beanFactory;

    /**
     * Constructor.
     * @param beanFactory the spring bean factory to use.
     */
    public SpringService(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Get a bean from the spring bean factory.
     * @param name bean name
     * @return the bean
     * @see BeanFactory#getBean(String)
     */
    public Object getBean(final String name) {
        return beanFactory.getBean(name);
    }

    /**
     * Get a bean from the spring bean factory.
     * @param <T> bean type
     * @param name bean name
     * @param clazz bean class
     * @return the bean
     * @see BeanFactory#getBean(String, Class)
     */
    public <T> T getBean(final String name, final Class<T> clazz) {
        return beanFactory.getBean(name, clazz);
    }

    /**
     * Get a bean from the spring bean factory.
     * @param <T> bean type
     * @param clazz bean class
     * @return the bean
     * @see BeanFactory#getBean(Class)
     */
    public <T> T getBean(final Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public void close() {
        // NO-OP; spring context is closed outside JBPM.
    }

}
