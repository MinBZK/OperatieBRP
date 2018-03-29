/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.ReadWriteController;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.BijhoudingsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class BijhoudingsautorisatieControllerTest {

    @Mock
    private BijhoudingsautorisatieRepository bijhoudingsautorisatieRepository;
    @Mock
    private HistorieVerwerker<Bijhoudingsautorisatie> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;

    @Inject
    private BijhoudingsautorisatieSoortAdministratieveHandelingRepository bijhoudingsautorisatieSoortAdministratieveHandelingRepository;
    @Inject
    private ReadWriteController<ToegangBijhoudingsautorisatie, Integer> toegangBijhoudingsautorisatieController;
    @Inject
    private BijhoudingsautorisatieSoortAdministratieveHandelingController bijhoudingsautorisatieSoortAdministratieveHandelingController;

    private BijhoudingsautorisatieController subject;

    @Before
    public void setup() {
        subject = new BijhoudingsautorisatieController(bijhoudingsautorisatieRepository, bijhoudingsautorisatieSoortAdministratieveHandelingRepository,
                toegangBijhoudingsautorisatieController, bijhoudingsautorisatieSoortAdministratieveHandelingController);
        subject.setTransactionManagerReadWrite(transactionManager);
        subject.setTransactionManagerReadonly(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderLeveringsautorisatie() throws NotFoundException {
        // Setup
        final Bijhoudingsautorisatie item = new Bijhoudingsautorisatie(Boolean.FALSE);
        item.setNaam("Autorisatie");
        item.setDatumIngang(20110101);
        final Bijhoudingsautorisatie managedItem = new Bijhoudingsautorisatie(Boolean.FALSE);
        managedItem.setNaam("Autorisatie");
        managedItem.setDatumIngang(20110101);
        Mockito.when(bijhoudingsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(bijhoudingsautorisatieRepository).findOrPersist(item);
        Mockito.verify(bijhoudingsautorisatieRepository).save(managedItem);
        Mockito.verifyNoMoreInteractions(bijhoudingsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorie() throws NotFoundException {
        // Setup
        final Bijhoudingsautorisatie item = new Bijhoudingsautorisatie(Boolean.FALSE);
        item.setDatumEinde(20110101);
        item.setDatumIngang(20010101);
        item.setIndicatieGeblokkeerd(Boolean.TRUE);
        item.setNaam("Autorisatie");
        final Bijhoudingsautorisatie managedItem = new Bijhoudingsautorisatie(Boolean.TRUE);
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);
        managedItem.setNaam("Autorisatie");

        Mockito.when(bijhoudingsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(bijhoudingsautorisatieRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(bijhoudingsautorisatieRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(bijhoudingsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorieNulls() throws NotFoundException {
        // Setup
        final Bijhoudingsautorisatie item = new Bijhoudingsautorisatie(Boolean.FALSE);
        final Bijhoudingsautorisatie managedItem = new Bijhoudingsautorisatie(Boolean.TRUE);
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);
        managedItem.setNaam("Autorisatie");

        Mockito.when(bijhoudingsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(bijhoudingsautorisatieRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(bijhoudingsautorisatieRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(bijhoudingsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagHistorie() throws NotFoundException {
        // Setup
        final Bijhoudingsautorisatie item = new Bijhoudingsautorisatie(Boolean.FALSE);
        item.setNaam("Autorisatie");
        item.setDatumIngang(20110101);
        final Bijhoudingsautorisatie managedItem = new Bijhoudingsautorisatie(Boolean.FALSE);
        managedItem.setNaam("Autorisatie");
        managedItem.setDatumIngang(20110101);

        Mockito.when(bijhoudingsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        item.setIndicatieGeblokkeerd(Boolean.TRUE);

        subject.save(item);

        // Verify
        Mockito.verify(bijhoudingsautorisatieRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(bijhoudingsautorisatieRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(bijhoudingsautorisatieRepository);
    }
}
