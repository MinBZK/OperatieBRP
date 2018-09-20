/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.service;

import java.util.ArrayList;

import junit.framework.Assert;
import nl.bzk.brp.beheer.web.dao.BasisDao;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.persistent.PersistentPartij;
import org.displaytag.pagination.PaginatedListImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class GenericDomeinServiceImplTest {

    @Mock
    private BasisDao<Partij>  partijDao;

    private GenericDomeinService<Partij> partijService;

    @Before
    public void setUp() {
        partijService = new GenericDomeinServiceImpl<Partij>();
        partijService.setDao(partijDao);
    }

    /**
     * Test zoek partij.
     */
    @Test
    public void testFindPartij() {
        String zoekwaarde = "abc";
        Mockito.when(partijDao.findObject(zoekwaarde, 30, 15)).thenReturn(new ArrayList<Partij>());
        Mockito.when(partijDao.tellObjecten(zoekwaarde)).thenReturn(10L);

        PaginatedListImpl<Partij> paginatedList = new PaginatedListImpl<Partij>();
        paginatedList.setObjectsPerPage(15);
        paginatedList.setPageNumber(3);

        Assert.assertNull("Voordat er gezocht wordt zou de lijst leeg moeten zijn", paginatedList.getList());

        paginatedList = partijService.zoekObject(zoekwaarde, paginatedList);

        Mockito.verify(partijDao, Mockito.times(1)).findObject(zoekwaarde, 30, 15);
        Mockito.verify(partijDao, Mockito.times(1)).tellObjecten(zoekwaarde);

        Assert.assertNotNull("Lijst mag niet leeg zijn na het zoeken.", paginatedList.getList());
        Assert.assertEquals("Verwachte wordt dat de gehele lijst 10 resultaten bevat", 10,
                paginatedList.getFullListSize());

    }

    /**
     * Test ophalen partij met id.
     */
    @Test
    public void testOphalenPartij() {
        Mockito.when(partijDao.getById(1)).thenReturn(new PersistentPartij());

        Assert.assertNotNull("De service moet een partij teruggeven.", partijService.ophalenObject(1));

        Mockito.verify(partijDao, Mockito.times(1)).getById(1);
    }

    /**
     * Test opslaan van partij.
     */
    @Test
    public void testOpslaan() {
        Partij partij = new PersistentPartij();

        partijService.opslaan(partij);

        Mockito.verify(partijDao, Mockito.times(1)).save(partij);
    }
}
