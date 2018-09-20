/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.dao;

import java.util.List;


/**
 * Interface voor een generieke dao.
 *
 */
public interface GenericDao {
    /**
     * Haal het object op van de opgegeven class met de id.
     *
     * @param <T> type van de te op te halen entity
     * @param entityClass
     *            de gewenste type object
     * @param id
     *            id van het object
     * @return het opgevraagde object
     *         wanneer de object niet gevonden kan worden
     */
    <T> Object getById(Class<T> entityClass, Integer id);

    /**
     * Haal alle objecten op van de gegeven class.
     *
     * @param <T> type van te op te halen entity
     *
     * @param entityClass
     *            de gewenste type object
     * @return complete lijst van de opgevraagde entity type
     */
    <T> List<T> findAll(Class<T> entityClass);

    /**
     * Slaat het op object op.
     *
     * @param <T> type van de te op te halen entity
     * @param entity
     *            het object
     * @return een nieuwe referentie naar het object
     */
    <T> Object save(T entity);
}
