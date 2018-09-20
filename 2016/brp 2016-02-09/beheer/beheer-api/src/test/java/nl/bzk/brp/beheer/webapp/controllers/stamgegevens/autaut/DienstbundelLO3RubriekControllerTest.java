/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelLO3RubriekRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.conv.ConversieLO3RubriekRepository;
import nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek;
import nl.bzk.brp.model.beheer.autaut.HisDienstbundelLO3Rubriek;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class DienstbundelLO3RubriekControllerTest {

    @Mock
    private DienstbundelLO3RubriekRepository repository;
    @Mock
    private LeveringsautorisatieRepository leveringsautorisatieRepository;
    @Mock
    private ConversieLO3RubriekRepository rubriekRepository;
    @Mock
    private HistorieVerwerker<DienstbundelLO3Rubriek, HisDienstbundelLO3Rubriek> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;

    private DienstbundelLO3RubriekController subject;

    @Before
    public void setup() {
        subject = new DienstbundelLO3RubriekController(repository, leveringsautorisatieRepository, rubriekRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslag() throws NotFoundException {
        // Setup
        final DienstbundelLO3Rubriek item = new DienstbundelLO3Rubriek();
        final DienstbundelLO3Rubriek managedItem = new DienstbundelLO3Rubriek();
        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(repository).findOrPersist(item);
        Mockito.verify(repository).save(managedItem);
        Mockito.verifyNoMoreInteractions(repository);
    }
}
