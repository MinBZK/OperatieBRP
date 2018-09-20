/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.service;

import nl.bzk.brp.beheer.web.dao.BasisDao;
import org.displaytag.pagination.PaginatedListImpl;

/**
 * Generieke service om object te zoeken en te bewerken.
 *
 * @param <T> type dat opgezocht en opgeslagen moet worden
 */
public interface GenericDomeinService<T> {
    /**
     * Haal object op met de id.
     *
     * @param id de id van object
     * @return het object van de gegeven type T
     */
    T ophalenObject(final int id);

    /**
     * Zoek object op met de opgegeven waarde.
     *
     * @param zoekterm zoek waarde
     * @param paginatedList PaginatedList
     * @return PaginatedList met resultaten
     */
    PaginatedListImpl<T> zoekObject(final String zoekterm, final PaginatedListImpl<T> paginatedList);

    /**
      *
      * @param object Object van het type T
      * @return nieuwe referentie naar de opgeslagen object
       */
    T opslaan(final T object);

    /**
     * Settter voor een generieke dao.
     * @param dao BasisDao<T>
     */
    void setDao(final BasisDao<T> dao);
}
