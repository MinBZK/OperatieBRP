/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.stamgegevens.service;

import java.util.List;

import nl.bzk.brp.beheer.model.Rol;
import nl.bzk.brp.beheer.model.Sector;
import nl.bzk.brp.beheer.model.SoortPartij;
import nl.bzk.brp.beheer.web.beheer.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service om stamgegevens op te halen.
 *
 */
@Service
public class StamgegevensServiceImpl implements StamgegevensService {

    @Autowired
    private GenericDao genericDao;

    @Override
    public List<SoortPartij> getSoortPartij() {
        return genericDao.findAll(SoortPartij.class);
    }

    @Override
    public List<Sector> getSector() {
        return genericDao.findAll(Sector.class);
    }

    @Override
    public List<Rol> getRollen() {
        return genericDao.findAll(Rol.class);
    }

    @Override
    public <T> Object find(final Class<T> entityClass, final int id) {
        return genericDao.getById(entityClass, id);
    }

    public void setGenericDao(final GenericDao genericDao) {
        this.genericDao = genericDao;
    }
}
