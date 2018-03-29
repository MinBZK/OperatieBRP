/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.business;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Testen van RelateerPersoonImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class RelateerPersoonImplTest {

//    @Mock
//    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @InjectMocks
    private RelateerPersoon relateerder = new RelateerPersoonImpl();

    @Test
    public void testRelateerPersoon(){
//        when(persoonHisVolledigRepository.leesGenormalizeerdModel(anyInt())).thenReturn(mock(PersoonHisVolledig.class));
        assertFalse(relateerder.relateerOpBasisVanID(1));
    }
}
