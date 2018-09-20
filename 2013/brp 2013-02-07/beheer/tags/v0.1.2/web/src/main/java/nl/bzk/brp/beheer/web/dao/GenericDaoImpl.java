/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Generieke data access layer.
 *
 */
@Repository
@Transactional
public class GenericDaoImpl implements GenericDao {
    @PersistenceContext
    private EntityManager       entityManager;

    @Override
    public <T> Object getById(final Class<T> entityClass, final Long id) {
        T object = entityManager.find(entityClass, id);

        if (object == null) {
            throw new ObjectNotFoundException(id, entityClass.toString());
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findAll(final Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        criteriaQuery.from(entityClass);

        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public <T> Object save(final T entity) {
        return entityManager.merge(entity);
    }
}
