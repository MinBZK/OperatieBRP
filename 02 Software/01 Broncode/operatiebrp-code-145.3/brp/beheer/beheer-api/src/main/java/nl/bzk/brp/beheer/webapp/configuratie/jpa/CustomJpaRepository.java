/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

/**
 * Custom JPA functies.
 *
 * @param <T>
 *            entity type
 */
//
/*
 * squid:S1609 @FunctionalInterface annotation should be used to flag Single Abstract Method
 * interfaces
 *
 * False positive, dit hoort niet een functional interface te zijn. Dit is een gewone interface met
 * slechts 1 methode.
 */
public interface CustomJpaRepository<T> {

    /**
     * Finds a given entity. Use the returned instance for further operations as the merge operation might have changed
     * the entity instance completely.
     *
     * @param entity
     *            entity
     * @return the merged or persisted entity
     */
    T findOrPersist(T entity);
}
