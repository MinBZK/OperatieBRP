/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


/**
 * Abstracte base class voor alle DAO's die elementen ophalen uit de lokale BMR afslag.
 *
 * @param <T> de class van het element dat door de DAO kan worden opgehaald.
 */
public abstract class AbstractJpaDao<T extends Serializable> {

    @PersistenceContext
    private EntityManager entityManager;

    protected abstract Class<T> getModelClass();

    protected String getModelClassName() {
        return getModelClass().getName();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected List<T> getResultList(final String qlString) {
        TypedQuery<T> query = getEntityManager().createQuery(qlString, getModelClass());
        return query.getResultList();
    }

    protected T getSingleResult(final String qlString) {
        TypedQuery<T> query = getEntityManager().createQuery(qlString, getModelClass());
        return query.getSingleResult();
    }

    public List<T> getAll() {
        String qlString = String.format("from %s", getModelClassName());
        return getResultList(qlString);
    }

    protected T getOptionalResult(final String qlString) {
        TypedQuery<T> query = getEntityManager().createQuery(qlString, getModelClass());
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
