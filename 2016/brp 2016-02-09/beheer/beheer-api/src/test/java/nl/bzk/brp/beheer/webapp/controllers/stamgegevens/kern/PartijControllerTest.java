/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.SoortPartijRepository;
import nl.bzk.brp.model.beheer.kern.HisPartij;
import nl.bzk.brp.model.beheer.kern.Partij;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class PartijControllerTest {

    @Mock
    private PartijRepository repository;
    @Mock
    private SoortPartijRepository soortPartijRepository;
    @Mock
    private HistorieVerwerker<Partij, HisPartij> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;

    private PartijController subject;

    @Before
    public void setup() {
        subject = new PartijController(repository, soortPartijRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderAbo() throws NotFoundException {
        // Setup
        final Partij item = new Partij();
        final Partij managedItem = new Partij();
        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(repository).findOrPersist(item);
        Mockito.verify(repository).save(managedItem);
        Mockito.verifyNoMoreInteractions(repository);
    }
}
