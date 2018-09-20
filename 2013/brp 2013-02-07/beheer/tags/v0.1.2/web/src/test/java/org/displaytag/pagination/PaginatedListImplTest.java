/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.displaytag.pagination;

import junit.framework.Assert;
import nl.bzk.brp.domein.kern.Partij;
import org.junit.Before;
import org.junit.Test;


public class PaginatedListImplTest {

    private final PaginatedListImpl<Partij> paginatedList = new PaginatedListImpl<Partij>();

    /**
     * Instellen op standaard waardes voor de paginering
     */
    @Before
    public void setUp() {
        paginatedList.setFullListSize(30);
        paginatedList.setObjectsPerPage(5);
    }

    /**
     * Test de eerst pagina check.
     */
    @Test
    public void testIsFirstPage() {
        paginatedList.setPageNumber(1);
        Assert.assertTrue("Wanneer pageNumber op 1 staat dan moet isFirstPage true teruggeven.",
                paginatedList.isFirstPage());

        paginatedList.setPageNumber(2);
        Assert.assertFalse("Wanneer pageNumber niet op 1 staat dan moet isFirstPage false teruggeven.",
                paginatedList.isFirstPage());

    }

    /**
     * Test de laatste pagina check.
     */
    @Test
    public void testIsLastPage() {
        paginatedList.setPageNumber(6);
        Assert.assertTrue("Wanneer pageNumber op 6 staat bij 30 items dan moet isLasttPage true teruggeven.",
                paginatedList.isLastPage());
    }

    /**
     * Test de eerste eerste item op de 4e pagina.
     */
    @Test
    public void testGetCurrentPageFirstItem() {
        paginatedList.setPageNumber(4);
        Assert.assertEquals("Op pagina 4 is item 16 de eerste item van de pagina.", 16,
                paginatedList.getCurrentPageFirstItem());
    }

    /**
     * Test de laatste item op de 4e pagina.
     */
    @Test
    public void testGetCurrentPageLastItem() {
        paginatedList.setPageNumber(4);
        Assert.assertEquals("Op pagina 4 is item 20 de laatste item van de pagina.", 20,
                paginatedList.getCurrentPageLastItem());
    }

    /**
     * Test de laatste item op de laatste pagina die niet helemaal vol is.
     */
    @Test
    public void testGetCurrentPageLastItemOpLaatstePagina() {
        paginatedList.setFullListSize(28);
        paginatedList.setPageNumber(6);

        Assert.assertEquals("Op laatste pagina is item 28 de laatste item van de pagina bij totaal 28 items.", 28,
                paginatedList.getCurrentPageLastItem());
    }
}
