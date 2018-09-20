/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstRepository;
import nl.bzk.brp.model.beheer.autaut.Dienstbundel;
import nl.bzk.brp.model.beheer.autaut.Dienst;
import nl.bzk.brp.model.beheer.autaut.HisDienst;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import org.junit.Assert;
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
public class DienstControllerTest {

    @Mock
    private DienstRepository dienstRepository;
    @Mock
    private LeveringsautorisatieRepository leveringsautorisatieRepository;
    @Mock
    private HistorieVerwerker<Dienst, HisDienst> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;

    private DienstController subject;

    @Before
    public void setup() {
        subject = new DienstController(dienstRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderAbo() throws NotFoundException {
        // Setup
        final Dienst item = new Dienst();
        final Dienst managedItem = new Dienst();
        Mockito.when(dienstRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(dienstRepository).findOrPersist(item);
        Mockito.verify(dienstRepository).save(managedItem);
        Mockito.verifyNoMoreInteractions(dienstRepository, leveringsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetAbo() throws NotFoundException {
        // Setup
        final Dienst item = new Dienst();
        item.setDienstbundel(new Dienstbundel());
        item.getDienstbundel().setID(3333);
        final Dienst managedItem = new Dienst();
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie();
        leveringsautorisatie.setID(3334);
        leveringsautorisatie.getDienstbundels().add(item.getDienstbundel());

        Mockito.when(leveringsautorisatieRepository.getOne(3334)).thenReturn(leveringsautorisatie);
        Mockito.when(dienstRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(leveringsautorisatieRepository).getOne(3334);
        Mockito.verify(dienstRepository).findOrPersist(item);
        Mockito.verify(dienstRepository).save(managedItem);
        Mockito.verifyNoMoreInteractions(dienstRepository, leveringsautorisatieRepository);
        Assert.assertEquals(managedItem, item.getDienstbundel());
    }

}
