/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.service;

import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.web.beheer.partijen.dao.PartijDao;
import org.displaytag.pagination.PaginatedListImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service partij te zoeken en te bewerken.
 *
 */
@Service
public class PartijServiceImpl implements PartijService {

    @Autowired
    private PartijDao partijDao;

    @Override
    public Partij ophalenPartij(final int id) {
        return partijDao.getById(id);
    }

    @Override
    public PaginatedListImpl<Partij> zoekPartij(final String waarde, final PaginatedListImpl<Partij> paginatedList) {
        int aantalPerPagina = paginatedList.getObjectsPerPage();
        int pagina = (paginatedList.getPageNumber() - 1) * aantalPerPagina;

        paginatedList.setList(partijDao.findPartij(waarde, pagina, aantalPerPagina));
        paginatedList.setFullListSize(partijDao.tellPartijen(waarde).intValue());

        return paginatedList;
    }

    @Override
    public Partij opslaan(final Partij partij) {
        return partijDao.save(partij);
    }

    public void setPartijDao(final PartijDao partijDao) {
        this.partijDao = partijDao;
    }
}
