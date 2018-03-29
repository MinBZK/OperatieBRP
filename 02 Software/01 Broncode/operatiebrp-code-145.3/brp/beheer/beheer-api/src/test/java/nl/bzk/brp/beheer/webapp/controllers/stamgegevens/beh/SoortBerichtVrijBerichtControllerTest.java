/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.beh;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import nl.bzk.brp.beheer.webapp.controllers.AbstractBaseReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Unit test voor {@link SoortBerichtVrijBerichtController}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SoortBerichtVrijBerichtControllerTest {

    @Mock
    private ReadonlyRepository<SoortBerichtVrijBericht, Integer> repository;
    @Mock
    private PlatformTransactionManager transactionManager;

    private SoortBerichtVrijBerichtController controller;

    @Before
    public void setup() {
        controller = new SoortBerichtVrijBerichtController(repository);
        controller.setTransactionManagerReadonly(transactionManager);
        when(repository.findAll(any(), any())).thenReturn(new AbstractBaseReadonlyController.PageAdapter<>(
                asList(SoortBerichtVrijBericht.STUUR_VRIJ_BERICHT, SoortBerichtVrijBericht.VERWERK_VRIJ_BERICHT), null));
    }

    @Test
    public void testFindAll() throws Exception {
        final Page<SoortBerichtVrijBericht> pages = controller.list(emptyMap(), new PageRequest(1, 1));

        verify(repository).findAll(any(), any());
        verifyNoMoreInteractions(repository);
        assertThat(pages.getContent(), containsInAnyOrder(SoortBerichtVrijBericht.STUUR_VRIJ_BERICHT, SoortBerichtVrijBericht.VERWERK_VRIJ_BERICHT));
    }

}