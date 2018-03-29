/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.SoortDienstRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class SoortDienstControllerTest {

    private final SoortDienstRepository repository = new SoortDienstRepository();
    @Mock
    private PlatformTransactionManager transactionManager;
    @InjectMocks
    private SoortDienstController subject;

    @Before
    public void setup() {
        subject = new SoortDienstController(repository);
        subject.setTransactionManagerReadonly(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslag() throws NotFoundException {

        // Execute
        final SoortDienst result = subject.get(2);
        Assert.assertEquals(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, result);

    }

}
