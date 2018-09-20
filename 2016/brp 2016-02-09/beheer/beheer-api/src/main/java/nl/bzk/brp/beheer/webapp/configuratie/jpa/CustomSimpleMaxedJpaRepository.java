/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.ParameterTranslations;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.internal.AbstractQueryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

/**
 * Aangepaste CustomSimpleJpaRepository om (count) queries te maximaliseren op een maximum aantal records.
 *
 * @param <T> entity type
 * @param <I> id type
 */
public final class CustomSimpleMaxedJpaRepository<T, I extends Serializable> extends CustomSimpleJpaRepository<T, I> implements CustomMaxedJpaRepository {

    /**
     * Default maximum aantal records.
     */
    public static final int DEFAULT_MAX_RECORDS = 10000;

    private static final String ERROR_MESSAGE = "Waarschuwing: er zijn meer dan %1$d resultaten gevonden. Verfijn uw zoekcriteria.";

    private final EntityManager em;
    private final int maximumRecords;

    /**
     * Creates a new {@link CustomSimpleMaxedJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager     must not be {@literal null}.
     */
    public CustomSimpleMaxedJpaRepository(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {
        this(entityInformation, entityManager, DEFAULT_MAX_RECORDS);
    }

    /**
     * Creates a new {@link CustomSimpleMaxedJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager     must not be {@literal null}.
     * @param maximumRecords    maximum aantal records
     */
    public CustomSimpleMaxedJpaRepository(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager, final int maximumRecords) {
        super(entityInformation, entityManager);
        em = entityManager;
        this.maximumRecords = maximumRecords;
    }

    @Override
    protected Page<T> readPage(final TypedQuery<T> query, final Pageable pageable, final Specification<T> spec) {
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        final Long total = QueryUtils.executeCountQuery(getCountQuery(spec));
        final List<T> content = total > pageable.getOffset() ? query.getResultList() : Collections.<T>emptyList();

        if (total > maximumRecords) {
            return new CustomPageImpl<T>(content, pageable, maximumRecords, String.format(ERROR_MESSAGE, maximumRecords));
        } else {
            return new PageImpl<T>(content, pageable, total);
        }
    }

    @Override
    protected TypedQuery<Long> getCountQuery(final Specification<T> spec) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<T> criteria = builder.createQuery(getDomainClass());
        final Root<T> root = applySpecificationToCriteria(spec, criteria);
        criteria.select(root);
        final TypedQuery<T> query = em.createQuery(criteria);

        @SuppressWarnings("checkstyle:illegaltype")
        final AbstractQueryImpl hibernateQuery = query.unwrap(AbstractQueryImpl.class);
        @SuppressWarnings("unchecked")
        final Map<String, TypedValue> pNamedParameters = (Map<String, TypedValue>) getField(hibernateQuery, AbstractQueryImpl.class, "namedParameters");

        final String hql = hibernateQuery.getQueryString();

        final ASTQueryTranslatorFactory queryTranslatorFactory = new ASTQueryTranslatorFactory();
        final SessionImplementor hibernateSession = em.unwrap(SessionImplementor.class);
        final QueryTranslator queryTranslator =
                queryTranslatorFactory.createQueryTranslator("", hql, java.util.Collections.EMPTY_MAP, hibernateSession.getFactory(), null);
        queryTranslator.compile(java.util.Collections.EMPTY_MAP, false);
        final String sql = queryTranslator.getSQLString();
        final ParameterTranslations paramTranslations = queryTranslator.getParameterTranslations();

        final String countSql = String.format("select count(*) from (%s limit %d) as sqry", sql, maximumRecords + 1);

        final Query nativeQuery = em.createNativeQuery(countSql);

        @SuppressWarnings("checkstyle:illegaltype")
        final AbstractQueryImpl resultQuery = nativeQuery.unwrap(AbstractQueryImpl.class);

        if (pNamedParameters != null) {
            for (final Entry<String, TypedValue> entry : pNamedParameters.entrySet()) {
                for (final int index : paramTranslations.getNamedParameterSqlLocations(entry.getKey())) {

                    resultQuery.setParameter(index, entry.getValue().getValue(), entry.getValue().getType());
                }
            }
        }

        return new CustomCountQueryWrapper(nativeQuery);
    }

    private Object getField(final Object object, final Class<?> clazz, final String fieldName) {
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
