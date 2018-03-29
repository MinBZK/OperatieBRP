/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class DienstbundelControllerTest {

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
    @Mock
    private LeveringsautorisatieRepository leveringsautorisatieRepository;
    @Mock
    private DienstbundelRepository dienstbundelRepository;
    @Mock
    private HistorieVerwerker<Dienst> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;
    @InjectMocks
    private DienstbundelController subject;

    @Before
    public void setup() {
        subject = new DienstbundelController(dienstbundelRepository, leveringsautorisatieRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
        subject.setTransactionManagerReadonly(transactionManager);
        LEVERINGSAUTORISATIE.setId(1);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderLeveringsautorisatie() throws NotFoundException {
        // Setup
        final Dienstbundel item = new Dienstbundel(LEVERINGSAUTORISATIE);
        final Dienstbundel managedItem = new Dienstbundel(LEVERINGSAUTORISATIE);
        Mockito.when(dienstbundelRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(leveringsautorisatieRepository.getOne(LEVERINGSAUTORISATIE.getId())).thenReturn(LEVERINGSAUTORISATIE);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(dienstbundelRepository).findOrPersist(item);
        Mockito.verify(dienstbundelRepository).save(managedItem);
        Mockito.verifyNoMoreInteractions(dienstbundelRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorie() throws NotFoundException {
        // Setup
        final Dienstbundel item = new Dienstbundel(LEVERINGSAUTORISATIE);
        item.setDatumEinde(20110101);
        item.setDatumIngang(20010101);
        item.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(Boolean.FALSE);
        item.setNaam("Ad hoc");
        item.setNaderePopulatiebeperking("WAAR");
        item.setToelichting("Geen");
        item.setIndicatieGeblokkeerd(Boolean.TRUE);
        final Dienstbundel managedItem = new Dienstbundel(LEVERINGSAUTORISATIE);
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(Boolean.FALSE);
        managedItem.setNaam("Ad hoc");
        managedItem.setNaderePopulatiebeperking("WAAR");
        managedItem.setToelichting("Geen");
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);

        Mockito.when(dienstbundelRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(dienstbundelRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(dienstbundelRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(dienstbundelRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagHistorie() throws NotFoundException {
        // Setup
        final Dienstbundel item = new Dienstbundel(LEVERINGSAUTORISATIE);
        item.setDatumIngang(20010101);
        item.setNaam("Ad hoc");
        final Dienstbundel managedItem = new Dienstbundel(LEVERINGSAUTORISATIE);
        managedItem.setDatumIngang(20010101);
        managedItem.setNaam("Ad hoc");

        Mockito.when(dienstbundelRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        item.setIndicatieGeblokkeerd(Boolean.TRUE);
        item.setNaam("Spontaan");

        subject.save(item);

        // Verify
        Mockito.verify(dienstbundelRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(dienstbundelRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(dienstbundelRepository);
    }

}
