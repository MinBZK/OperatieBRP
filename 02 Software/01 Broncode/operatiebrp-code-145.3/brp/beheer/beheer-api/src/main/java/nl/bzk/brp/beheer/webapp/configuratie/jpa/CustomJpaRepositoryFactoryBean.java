/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * Custom JPA Repository factory bean om een aangepaste JpaRepositoryFactory te krijgen
 * {@link CustomJpaRepositoryFactory}.
 * @param <T> the type of the repository
 * @param <S> the type of the entity
 * @param <I> the type of the id
 */
public final class CustomJpaRepositoryFactoryBean<T extends JpaRepository<S, I>, S, I extends Serializable> extends JpaRepositoryFactoryBean<T, S, I> {

    @Value("${brp.beheer.zoekpersoon.max.costs.queryplan:250}")
    private int maxCostsQueryPlan;

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
        return new CustomJpaRepositoryFactory(entityManager, maxCostsQueryPlan);
    }
}
