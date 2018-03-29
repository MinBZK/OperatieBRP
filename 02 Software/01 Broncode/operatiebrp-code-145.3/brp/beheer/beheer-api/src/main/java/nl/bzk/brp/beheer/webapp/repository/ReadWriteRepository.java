/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository;

import java.io.Serializable;
import nl.bzk.brp.beheer.webapp.configuratie.jpa.CustomJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Basis repository interface waarmee een gegeven onderhouden kan worden.
 *
 * @param <T> entity type
 * @param <I> id type
 */
@NoRepositoryBean
@Transactional(propagation = Propagation.MANDATORY)
public interface ReadWriteRepository<T, I extends Serializable> extends ReadonlyRepository<T, I>, CustomJpaRepository<T> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed
     * the entity instance completely.
     *
     * @param entity entity
     * @param <S> saved entity type
     * @return the saved entity
     */
    <S extends T> S save(S entity);

}
