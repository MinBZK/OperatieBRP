/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.model.beheer.autaut.HisLeveringsautorisatie;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class LeveringsautorisatieControllerTest {

    @Mock
    private LeveringsautorisatieRepository repository;
    @Mock
    private HistorieVerwerker<Leveringsautorisatie, HisLeveringsautorisatie> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;

    private LeveringsautorisatieController subject;

    @Before
    public void setup() {
        subject = new LeveringsautorisatieController(repository);
        subject.setTransactionManagerReadWrite(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderAbo() throws NotFoundException {
        // Setup
        final Leveringsautorisatie item = new Leveringsautorisatie();
        final Leveringsautorisatie managedItem = new Leveringsautorisatie();
        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(repository).findOrPersist(item);
        Mockito.verify(repository).save(managedItem);
        Mockito.verifyNoMoreInteractions(repository);
    }
}
