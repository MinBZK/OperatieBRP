/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.service;

import java.util.ArrayList;

import junit.framework.Assert;
import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.web.beheer.partijen.dao.PartijDao;
import org.displaytag.pagination.PaginatedListImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PartijServiceImplTest {

    @Mock
    private PartijDao         partijDao;

    private PartijServiceImpl partijService;

    @Before
    public void setUp() {
        partijService = new PartijServiceImpl();
        partijService.setPartijDao(partijDao);
    }

    /**
     * Test zoek partij.
     */
    @Test
    public void testFindPartij() {
        Mockito.when(partijDao.findPartij("zoekWaarde", 30, 15)).thenReturn(new ArrayList<Partij>());
        Mockito.when(partijDao.tellPartijen("zoekWaarde")).thenReturn(10L);

        PaginatedListImpl<Partij> paginatedList = new PaginatedListImpl<Partij>();
        paginatedList.setObjectsPerPage(15);
        paginatedList.setPageNumber(3);

        Assert.assertNull("Voordat er gezocht wordt zou de lijst leeg moeten zijn", paginatedList.getList());

        paginatedList = partijService.zoekPartij("zoekWaarde", paginatedList);

        Mockito.verify(partijDao, Mockito.times(1)).findPartij("zoekWaarde", 30, 15);
        Mockito.verify(partijDao, Mockito.times(1)).tellPartijen("zoekWaarde");

        Assert.assertNotNull("Lijst mag niet leeg zijn na het zoeken.", paginatedList.getList());
        Assert.assertEquals("Verwachte wordt dat de gehele lijst 10 resultaten bevat", 10,
                paginatedList.getFullListSize());

    }

    /**
     * Test ophalen partij met id.
     */
    @Test
    public void testOphalenPartij() {
        Mockito.when(partijDao.getById(1)).thenReturn(new Partij(null));

        Assert.assertNotNull("De service moet een partij teruggeven.", partijService.ophalenPartij(1));

        Mockito.verify(partijDao, Mockito.times(1)).getById(1);
    }

    /**
     * Test opslaan van partij.
     */
    @Test
    public void testOpslaan() {
        Partij partij = new Partij(null);

        partijService.opslaan(partij);

        Mockito.verify(partijDao, Mockito.times(1)).save(partij);
    }
}
