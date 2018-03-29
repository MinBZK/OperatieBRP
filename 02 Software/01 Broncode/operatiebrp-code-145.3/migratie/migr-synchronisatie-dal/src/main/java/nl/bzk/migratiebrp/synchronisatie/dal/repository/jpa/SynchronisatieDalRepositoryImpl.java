/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.algemeenbrp.dal.repositories.jpa.BasisRepositoryImpl;

/**
 * Implementatie van {@link BasisRepositoryImpl} specifiek voor de Synchronisatie DAL database.
 * @param <T> het type entiteit voor deze repository
 * @param <I> het type van de primary key van de entiteit van deze repository
 */
class SynchronisatieDalRepositoryImpl<T, I extends Serializable, C> extends nl.bzk.algemeenbrp.dal.repositories.jpa.StamtabelRepositoryImpl<T, I, C> {

    SynchronisatieDalRepositoryImpl(final Class<T> entityType, final nl.bzk.algemeenbrp.dal.repositories.jpa.StamtabelRepositoryImpl<T, I, ?>
            bronRepository, final String... keyField) {
        super(entityType, bronRepository, keyField);
    }

    SynchronisatieDalRepositoryImpl(final Class<T> entityType, final String... keyField) {
        super(entityType, keyField);
    }

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    @Override
    protected void setEntityManager(final EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

}
