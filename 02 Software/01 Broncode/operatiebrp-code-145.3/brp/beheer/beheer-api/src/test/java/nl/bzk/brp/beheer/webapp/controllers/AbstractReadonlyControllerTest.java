/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.PlatformTransactionManager;

public class AbstractReadonlyControllerTest {

    private static final Short ID = 1;
    private ReadonlyControllerImpl instance;

    /**
     * test.
     */
    public AbstractReadonlyControllerTest() {
        instance = null;
    }

    /**
     * test.
     */
    @Before
    public final void setup() {
        final ReadonlyRepository mockReadOnlyRepository = mock(ReadonlyRepository.class);
        final Partij een = mock(Partij.class);
        when(een.getId()).thenReturn(ID);
        when(mockReadOnlyRepository.findOne(ID)).thenReturn(een);
        final List<Partij> partijLijst = mock(List.class);
        when(partijLijst.get(0)).thenReturn(een);
        when(partijLijst.get(1)).thenThrow(new RuntimeException());
        final Page page = mock(Page.class);
        when(page.getContent()).thenReturn(partijLijst);
        when(page.getTotalElements()).thenReturn(1L);
        when(mockReadOnlyRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        instance = new ReadonlyControllerImpl(mockReadOnlyRepository, Collections.<Filter<?>>emptyList());
        instance.setTransactionManagerReadonly(mock(PlatformTransactionManager.class));
    }

    /**
     * Test of get method, of class AbstractReadonlyController.
     *
     * @throws java.lang.Exception
     *             test exception
     */
    @Test
    public final void testGet() throws Exception {
        final Partij partij = instance.get(ID);
        assertNotNull(partij);
        assertEquals(ID, partij.getId());
    }

    /**
     * Test of list method, of class AbstractReadonlyController.
     */
    @Test
    public final void testList() {
        final Page page = instance.list(null, null);
        assertFalse(page.getContent().isEmpty());
        assertEquals(1, page.getTotalElements());
    }

    /**
     * Test.
     */
    public static class ReadonlyControllerImpl extends AbstractReadonlyController<Partij, Short> {

        /**
         * test.
         *
         * @param repository
         *            repo
         * @param filters
         *            filter
         */
        public ReadonlyControllerImpl(final ReadonlyRepository<Partij, Short> repository, final List<Filter<?>> filters) {
            super(repository, filters);
        }
    }
}
