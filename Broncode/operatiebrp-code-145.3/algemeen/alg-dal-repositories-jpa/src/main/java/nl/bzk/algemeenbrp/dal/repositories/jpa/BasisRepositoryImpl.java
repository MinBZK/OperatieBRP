/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.repositories.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import nl.bzk.algemeenbrp.dal.repositories.BasisRepository;


/**
 * JPA implementatie van basis methodes.
 *
 * @param <T> het type entiteit voor deze repository
 * @param <I> het type van de primary key van de entiteit van deze repository
 */
public class BasisRepositoryImpl<T, I extends Serializable> implements BasisRepository<T, I> {

    private Class<T> entityType;
    private EntityManager entityManager;

    /**
     * Maakt een nieuw AbstractBasisRepository object.
     *
     * @param entityType entity type
     */
    protected BasisRepositoryImpl(final Class<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public final void save(final T entity) { getEntityManager().persist(entity); }

    @Override
    public final T merge(final T entity) { return getEntityManager().merge(entity); }

    @Override
    public final T findById(final I id) { return getEntityManager().find(getEntityType(), id); }

    /**
     * Geef de waarde van entityManager.
     *
     * @return entityManager
     */
    protected final EntityManager getEntityManager() { return entityManager; }

    /**
     * Zet de waarde van entityManager.
     *
     * @param EntityManager entityManager
     */
    protected void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Geef het type van de entity van deze repository.
     *
     * @return het entity type
     */
    protected final Class<T> getEntityType() {
        return entityType;
    }

}
