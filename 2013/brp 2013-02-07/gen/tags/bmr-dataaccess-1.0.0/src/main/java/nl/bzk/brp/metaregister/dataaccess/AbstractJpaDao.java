/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractJpaDao< T extends Serializable > {

    private Class<T> clazz;

    @PersistenceContext
    EntityManager    entityManager;

    public void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T getById(final Long id) {
        return this.entityManager.find(this.clazz, id);
    }

    public List<T> getAll() {
        return this.entityManager.createQuery("from " + this.clazz.getName()).getResultList();
    }

    public void create(final T entity) {
        this.entityManager.persist(entity);
    }

    public void update(final T entity) {
        this.entityManager.merge(entity);
    }

    public void delete(final T entity) {
        this.entityManager.remove(entity);
    }

    public void deleteById(final Long entityId) {
        final T entity = this.getById(entityId);

        this.delete(entity);
    }

}
