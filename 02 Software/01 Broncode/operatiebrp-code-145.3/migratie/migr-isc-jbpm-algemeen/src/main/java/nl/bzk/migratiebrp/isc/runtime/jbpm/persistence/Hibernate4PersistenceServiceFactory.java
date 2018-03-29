/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.persistence;

import org.hibernate.SessionFactory;
import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;

/**
 * JBPM Service factory voor hibernate4 persistence.
 */
public final class Hibernate4PersistenceServiceFactory implements ServiceFactory {
    private static final long serialVersionUID = 1L;

    private SessionFactory sessionFactory;

    /**
     * Zet de hibernate session factory. Aangeroepen door
     * {@link nl.bzk.migratiebrp.isc.runtime.jbpm.configuration.JbpmConfigurationFactoryBean}.
     * @param sessionFactory session factory
     */
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Service openService() {
        if (sessionFactory == null) {
            throw new IllegalStateException("Hibernate4PersistenceServiceFactory.openService() aangeroepen voordat SessionFactory is geconfigureerd.");
        }

        return new Hibernate4PersistenceService(sessionFactory);
    }

    @Override
    public void close() {
        // Interface methode, hoeft niks te sluiten
    }

}
