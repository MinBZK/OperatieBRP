/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * Custom repository factory om een aangepaste SimpleJpaRepository te krijgen {@link CustomSimpleJpaRepository}.
 */
public final class CustomJpaRepositoryFactory extends JpaRepositoryFactory {

    private final int maxCostsQueryPlan;

    /**
     * Constructor.
     * @param entityManager entity manager
     * @param maxCostsQueryPlan maximum cost of a query plan
     */
    CustomJpaRepositoryFactory(final EntityManager entityManager, final int maxCostsQueryPlan) {
        super(entityManager);
        this.maxCostsQueryPlan = maxCostsQueryPlan;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected SimpleJpaRepository<?, ?> getTargetRepository(
            final RepositoryMetadata metadata,
            final EntityManager entityManager) {
        final Class<?> repositoryInterface = metadata.getRepositoryInterface();
        final JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());

        if (isQueryDslSpecificExecutor(repositoryInterface)) {
            throw new IllegalArgumentException("QueryDSL interface niet toegestaan");
        }

        return isMaxedRepository(repositoryInterface)
                ? new CustomSimpleMaxedJpaRepository(entityInformation, entityManager)
                : isQuerycostRepository(repositoryInterface)
                        ? new CustomSimpleQuerycostJpaRepository(entityInformation, entityManager, maxCostsQueryPlan)
                        : new CustomSimpleJpaRepository(entityInformation, entityManager);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
        if (isMaxedRepository(metadata.getRepositoryInterface())) {
            return CustomSimpleMaxedJpaRepository.class;
        } else if (isQuerycostRepository(metadata.getRepositoryInterface())) {
            return CustomSimpleQuerycostJpaRepository.class;
        } else {
            return CustomSimpleJpaRepository.class;
        }
    }

    /**
     * Returns whether the given repository interface requires a QueryDsl specific implementation to be chosen.
     */
    private boolean isQueryDslSpecificExecutor(final Class<?> repositoryInterface) {
        return QueryDslUtils.QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }

    private boolean isMaxedRepository(final Class<?> repositoryInterface) {
        return CustomMaxedJpaRepository.class.isAssignableFrom(repositoryInterface);
    }

    private boolean isQuerycostRepository(final Class<?> repositoryInterface) {
        return CustomQuerycostJpaRepository.class.isAssignableFrom(repositoryInterface);
    }
}
