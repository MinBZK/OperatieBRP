/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.stamgegevens.service;

import nl.bzk.brp.beheer.model.Rol;
import nl.bzk.brp.beheer.model.Sector;
import nl.bzk.brp.beheer.model.SoortPartij;
import nl.bzk.brp.beheer.web.beheer.dao.GenericDaoImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class StamgegevensServiceImplTest {

    @Mock
    private GenericDaoImpl          genericDao;

    private StamgegevensServiceImpl stamgegevensService;

    @Before
    public void setup() {
        stamgegevensService = new StamgegevensServiceImpl();
        stamgegevensService.setGenericDao(genericDao);
    }

    @Test
    public void testGetSoortPartij() {
        stamgegevensService.getSoortPartij();
        Mockito.verify(genericDao, Mockito.times(1)).findAll(SoortPartij.class);
    }

    @Test
    public void testGetSector() {
        stamgegevensService.getSector();
        Mockito.verify(genericDao, Mockito.times(1)).findAll(Sector.class);
    }

    @Test
    public void testGetRollen() {
        stamgegevensService.getRollen();
        Mockito.verify(genericDao, Mockito.times(1)).findAll(Rol.class);
    }

    @Test
    public void testFind() {
        stamgegevensService.find(Rol.class, 1);
        Mockito.verify(genericDao, Mockito.times(1)).getById(Rol.class, 1);
    }
}
