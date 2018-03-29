/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.postgres.QueryPlan;
import nl.bzk.algemeenbrp.util.common.postgres.QueryPlanParser;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.ParameterTranslations;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.internal.AbstractQueryImpl;
import org.hibernate.internal.QueryImpl;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

/**
 * Aangepaste SimpleJpaRepository om ....
 * @param <T> entity type
 * @param <I> id type
 */
public class CustomSimpleQuerycostJpaRepository<T, I extends Serializable> extends CustomSimpleJpaRepository<T, I> implements CustomQuerycostJpaRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String ERROR_MESSAGE = "Waarschuwing: zoekopdracht levert teveel resultaten op en is afgebroken. Verfijn uw zoekcriteria.";

    private final EntityManager em;
    private final int maxCostsQueryPlan;

    /**
     * Creates a new {@link CustomSimpleQuerycostJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     * @param entityInformation must not be {@literal null}.
     * @param entityManager must not be {@literal null}.
     * @param maxCostsQueryPlan maximum cost of a query plan
     */
    CustomSimpleQuerycostJpaRepository(
            final JpaEntityInformation<T, ?> entityInformation,
            final EntityManager entityManager,
            final int maxCostsQueryPlan) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.maxCostsQueryPlan = maxCostsQueryPlan;
    }

    @Override
    public Page<T> findAll(final Specification<T> spec, final Pageable pageable) {
        final QueryPlanWork work = specificationToWork(spec, pageable);
        final Session session = em.unwrap(Session.class);

        return session.doReturningWork(work)
                .filter(queryPlan -> queryPlan.getPlan().getTotalCost() > maxCostsQueryPlan)
                .map(queryPlan -> createQueryTooExpensiveWarning(work.sql, pageable))
                .orElseGet(() -> super.findAll(spec, pageable));
    }

    private Page<T> createQueryTooExpensiveWarning(final String sql, final Pageable pageable) {
        final String message = String.format("Query met sql %s afgewezen op basis van query plan", sql);
        LOGGER.debug(message);
        return new CustomPageImpl<>(Collections.emptyList(), pageable, 0, ERROR_MESSAGE);
    }

    private QueryPlanWork specificationToWork(Specification<T> spec, Pageable pageable) {
        // Unwrap to Hibernate
        final QueryImpl query = getQuery(spec, pageable).unwrap(QueryImpl.class);
        final String hql = query.getQueryString();

        // Create translator
        final QueryTranslator queryTranslator =
                new ASTQueryTranslatorFactory().createQueryTranslator("", hql, Collections.emptyMap(), em.unwrap(SessionImplementor.class).getFactory(), null);
        queryTranslator.compile(Collections.emptyMap(), false);

        // Get translated query and parameters
        final String sql = queryTranslator.getSQLString();
        final Object[] parameters = translateParameters(query, queryTranslator);

        // Done
        return new QueryPlanWork(sql, parameters);
    }

    private Object[] translateParameters(final QueryImpl query, final QueryTranslator queryTranslator) {
        final ParameterTranslations parameterTranslations = queryTranslator.getParameterTranslations();
        Object[] result = new Object[]{};

        // Named
        final Map<String, TypedValue> namedParameterValues = getNamedParameterValues(query);
        for (final String namedParameter : (Set<String>) parameterTranslations.getNamedParameterNames()) {
            final int[] positions = parameterTranslations.getNamedParameterSqlLocations(namedParameter);
            for (final int position : positions) {
                result = ensureLength(result, position + 1);
                result[position] = namedParameterValues.get(namedParameter).getValue();
            }
        }

        // Positional
        final List<Object> positionalParameterValues = getPositionalParameterValues(query);
        for (int i = 0; i < parameterTranslations.getOrdinalParameterCount(); i++) {
            final int position = parameterTranslations.getOrdinalParameterSqlLocation(i);
            result = ensureLength(result, position + 1);
            result[position] = positionalParameterValues.get(i);
        }

        // Done
        return result;
    }

    private <U> U[] ensureLength(final U[] array, final int length) {
        if (array.length < length) {
            final U[] result = (U[]) new Object[length];
            System.arraycopy(array, 0, result, 0, array.length);
            return result;
        } else {
            return array;
        }
    }

    private Map<String, TypedValue> getNamedParameterValues(final QueryImpl query) {
        try {
            final Method accessor = AbstractQueryImpl.class.getDeclaredMethod("getNamedParams");
            accessor.setAccessible(true);
            return (Map<String, TypedValue>) accessor.invoke(query);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Could not get named parameter values from query", e);
        }
    }

    private List<Object> getPositionalParameterValues(final QueryImpl query) {
        try {
            final Method accessor = AbstractQueryImpl.class.getDeclaredMethod("getValues");
            accessor.setAccessible(true);
            return (List<Object>) accessor.invoke(query);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Could not get positional parameter values from query", e);
        }
    }

    private static class QueryPlanWork implements ReturningWork<Optional<QueryPlan>> {
        private String sql;
        private Object[] parameters;

        QueryPlanWork(final String sql, final Object[] parameters) {
            this.sql = sql;
            this.parameters = parameters;
        }

        @Override
        public Optional<QueryPlan> execute(final Connection connection) {
            if (isPostgres(connection)) {
                return getQueryPlan(connection);
            } else {
                return Optional.empty();
            }
        }

        private Optional<QueryPlan> getQueryPlan(final Connection connection) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement("EXPLAIN (FORMAT JSON) " + sql)) {
                if (parameters != null) {
                    for (int i = 0; i < parameters.length; i++) {
                        preparedStatement.setObject(i + 1, parameters[i]);
                    }
                }
                final ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    final String queryPlanJson = rs.getString(1);
                    return Optional.of(new QueryPlanParser(queryPlanJson).parse());
                } else {
                    throw new IllegalStateException("No explain plan returned");
                }
            } catch (final SQLException | IOException e) {
                throw new IllegalStateException(e);
            }
        }

        private boolean isPostgres(final Connection connection) {
            try {
                return connection.getMetaData().getURL().contains("postgres");
            } catch (final SQLException e) {
                LOGGER.error("Unable to get connection metadata", e);
            }
            return false;
        }
    }
}
