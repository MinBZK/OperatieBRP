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
public class GenericDomeinServiceImpl<T extends Object> implements GenericDomeinService<T> {
    private BasisDao<T> dao;

    @Override
    public T ophalenObject(final int id) {
        return dao.getById(id);
    }

    @Override
    public PaginatedListImpl<T> zoekObject(final String zoekterm, final PaginatedListImpl<T> paginatedList) {
        int aantalPerPagina = paginatedList.getObjectsPerPage();
        int pagina = (paginatedList.getPageNumber() - 1) * aantalPerPagina;

        paginatedList.setList(dao.findObject(zoekterm, pagina, aantalPerPagina));
        paginatedList.setFullListSize(dao.tellObjecten(zoekterm).intValue());

        return paginatedList;
    }

    @Override
    public T opslaan(final T object) {
        return dao.save(object);
    }

    public void setDao(final BasisDao<T> dao) {
        this.dao = dao;
    }
}
