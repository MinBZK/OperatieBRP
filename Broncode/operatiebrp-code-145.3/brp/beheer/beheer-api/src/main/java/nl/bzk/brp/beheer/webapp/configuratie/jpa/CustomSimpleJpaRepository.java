/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

/**
 * Aangepaste SimpleJpaRepository om case insensitive te sorten op niet alleen strings maar ook Attribuut<String>.
 * @param <T> entity type
 * @param <I> id type
 */
public class CustomSimpleJpaRepository<T, I extends Serializable> extends SimpleJpaRepository<T, I> implements CustomJpaRepository<T> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    /**
     * Creates a new {@link CustomSimpleJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     * @param entityInformation must not be {@literal null}.
     * @param entityManager must not be {@literal null}.
     */
    public CustomSimpleJpaRepository(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        em = entityManager;
    }

    @Override
    public final T findOrPersist(final T entity) {
        if (entityInformation.getId(entity) == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.find(entityInformation.getJavaType(), entityInformation.getId(entity));
        }
    }

    /**
     * Spec op query doen.
     * @param spec spec
     * @param query query
     * @param <S> query type
     * @return root
     */
    protected final <S> Root<T> applySpecificationToQueryCriteria(final Specification<T> spec, final CriteriaQuery<S> query) {

        Assert.notNull(query, "Query mag niet null zijn.");
        final Root<T> root = query.from(getDomainClass());

        if (spec == null) {
            return root;
        }

        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

}
