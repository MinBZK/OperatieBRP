/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Basis interface voor repositories waar alleen maar mee gelezen mag worden.
 *
 * @param <T>
 *            entity type
 * @param <I>
 *            id type
 */
@NoRepositoryBean
@Transactional(propagation = Propagation.MANDATORY)
public interface ReadonlyRepository<T, I extends Serializable> extends Repository<T, I> {

    /**
     * Retrieves an entity by its id.
     *
     * @param id
     *            must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    T findOne(I id);

    /**
     * Retrieves an entity by its id (but it can be lazily loaded, only gets a reference).
     *
     * @param id
     *            must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    T getOne(I id);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param spec
     *            query specification
     * @param pageable
     *            pageable
     * @return a page of entities
     */
    Page<T> findAll(Specification<T> spec, Pageable pageable);

}
