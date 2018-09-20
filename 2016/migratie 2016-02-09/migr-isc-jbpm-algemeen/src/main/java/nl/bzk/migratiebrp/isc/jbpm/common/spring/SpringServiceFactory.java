/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.spring;

import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;
import org.springframework.beans.factory.BeanFactory;

/**
 * Spring service factory.
 */
public final class SpringServiceFactory implements ServiceFactory {

    /** Name the service is configured under in JBPM. */
    public static final String SERVICE_NAME = "spring";

    private static final long serialVersionUID = 1L;

    private transient BeanFactory beanFactory;

    /**
     * Zet de bean factory. Aangeroepen door {@link
     * nl.bzk.migratiebrp.isc.runtime.jbpm.configuration..JbpmConfigurationFactoryBean}.
     *
     * @param beanFactory
     *            bean factory
     */
    public void setBeanFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Service openService() {
        if (beanFactory == null) {
            throw new IllegalStateException("SpringServiceFactory.openService() aangeroepen voordat BeanFactory is geconfigureerd.");
        }

        return new SpringService(beanFactory);
    }

    @Override
    public void close() {
    }

}
