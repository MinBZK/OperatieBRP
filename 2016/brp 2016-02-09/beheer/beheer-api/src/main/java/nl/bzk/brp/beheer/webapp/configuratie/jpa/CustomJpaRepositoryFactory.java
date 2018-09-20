/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
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

    /**
     * Constructor.
     *
     * @param entityManager entity manager
     */
    public CustomJpaRepositoryFactory(final EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected <T, I extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
            final RepositoryMetadata metadata,
            final EntityManager entityManager)
    {
        final Class<?> repositoryInterface = metadata.getRepositoryInterface();
        final JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());

        if (isQueryDslExecutor(repositoryInterface)) {
            throw new IllegalArgumentException("QueryDSL interface niet toegestaan");
        }

        final SimpleJpaRepository<?, ?> repo =
                isMaxedRepository(repositoryInterface) ? new CustomSimpleMaxedJpaRepository(entityInformation, entityManager)
                        : new CustomSimpleJpaRepository(entityInformation, entityManager);

        return repo;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
        if (isMaxedRepository(metadata.getRepositoryInterface())) {
            return CustomSimpleMaxedJpaRepository.class;
        } else {
            return CustomSimpleJpaRepository.class;
        }
    }

    /**
     * Returns whether the given repository interface requires a QueryDsl specific implementation to be chosen.
     *
     * @param repositoryInterface
     * @return
     */
    private boolean isQueryDslExecutor(final Class<?> repositoryInterface) {
        return QueryDslUtils.QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }

    private boolean isMaxedRepository(final Class<?> repositoryInterface) {
        return CustomMaxedJpaRepository.class.isAssignableFrom(repositoryInterface);
    }
}
