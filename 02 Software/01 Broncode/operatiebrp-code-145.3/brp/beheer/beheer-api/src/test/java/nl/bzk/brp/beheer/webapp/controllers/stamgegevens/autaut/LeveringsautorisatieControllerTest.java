/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.ReadWriteController;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelGroepAttribuutRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelGroepRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelLo3RubriekRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.conv.Lo3RubriekRepository;
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
    private LeveringsautorisatieRepository leveringsautorisatieRepository;
    @Mock
    private HistorieVerwerker<Leveringsautorisatie> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;

    @Inject
    private ReadWriteController<ToegangLeveringsAutorisatie, Integer> toegangLeveringsautorisatieController;
    @Inject
    private ReadWriteController<Dienst, Integer> dienstController;
    @Inject
    private ReadWriteController<Dienstbundel, Integer> dienstbundelController;
    @Inject
    private DienstbundelRepository dienstbundelRepository;
    @Inject
    private ReadWriteController<DienstbundelLo3Rubriek, Integer> dienstbundelLo3RubriekController;
    @Inject
    private DienstbundelLo3RubriekRepository dienstbundelLo3RubriekRepository;
    @Inject
    private Lo3RubriekRepository conversieLO3RubriekRepository;
    @Inject
    private ReadWriteController<DienstbundelGroep, Integer> dienstbundelGroepController;
    @Inject
    private DienstbundelGroepRepository dienstbundelGroepRepository;
    @Inject
    private DienstbundelGroepAttribuutRepository dienstbundelGroepAttribuutRepository;

    private LeveringsautorisatieController subject;

    @Before
    public void setup() {
        subject = new LeveringsautorisatieController(leveringsautorisatieRepository, toegangLeveringsautorisatieController, dienstController,
                dienstbundelController, dienstbundelRepository, dienstbundelLo3RubriekController, dienstbundelLo3RubriekRepository,
                conversieLO3RubriekRepository, dienstbundelGroepController, dienstbundelGroepRepository, dienstbundelGroepAttribuutRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderLeveringsautorisatie() throws NotFoundException {
        // Setup
        final Leveringsautorisatie item = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
        item.setNaam("Autorisatie");
        item.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        item.setDatumIngang(20110101);
        item.setIndicatieAliasSoortAdministratieveHandelingLeveren(Boolean.TRUE);
        final Leveringsautorisatie managedItem = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
        managedItem.setNaam("Autorisatie");
        managedItem.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        managedItem.setDatumIngang(20110101);
        managedItem.setIndicatieAliasSoortAdministratieveHandelingLeveren(Boolean.TRUE);
        Mockito.when(leveringsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(leveringsautorisatieRepository).findOrPersist(item);
        Mockito.verify(leveringsautorisatieRepository).save(managedItem);
        Mockito.verifyNoMoreInteractions(leveringsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorie() throws NotFoundException {
        // Setup
        final Leveringsautorisatie item = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
        item.setDatumEinde(20110101);
        item.setDatumIngang(20010101);
        item.setIndicatieGeblokkeerd(Boolean.TRUE);
        item.setNaam("Autorisatie");
        item.setPopulatiebeperking("WAAR");
        item.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        item.setToelichting("Geen");
        item.setIndicatieAliasSoortAdministratieveHandelingLeveren(Boolean.TRUE);
        final Leveringsautorisatie managedItem = new Leveringsautorisatie(Stelsel.GBA, Boolean.TRUE);
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);
        managedItem.setNaam("Autorisatie");
        managedItem.setPopulatiebeperking("WAAR");
        managedItem.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        managedItem.setToelichting("Geen");
        managedItem.setIndicatieAliasSoortAdministratieveHandelingLeveren(Boolean.TRUE);

        Mockito.when(leveringsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(leveringsautorisatieRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(leveringsautorisatieRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(leveringsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorieNulls() throws NotFoundException {
        // Setup
        final Leveringsautorisatie item = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
        final Leveringsautorisatie managedItem = new Leveringsautorisatie(Stelsel.GBA, Boolean.TRUE);
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);
        managedItem.setNaam("Autorisatie");
        managedItem.setPopulatiebeperking("WAAR");
        managedItem.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        managedItem.setToelichting("Geen");
        managedItem.setIndicatieAliasSoortAdministratieveHandelingLeveren(Boolean.TRUE);

        Mockito.when(leveringsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(leveringsautorisatieRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(leveringsautorisatieRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(leveringsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagHistorie() throws NotFoundException {
        // Setup
        final Leveringsautorisatie item = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
        item.setNaam("Autorisatie");
        item.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        item.setDatumIngang(20110101);
        item.setIndicatieAliasSoortAdministratieveHandelingLeveren(Boolean.TRUE);
        final Leveringsautorisatie managedItem = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
        managedItem.setNaam("Autorisatie");
        managedItem.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        managedItem.setDatumIngang(20110101);
        managedItem.setIndicatieAliasSoortAdministratieveHandelingLeveren(Boolean.TRUE);

        Mockito.when(leveringsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        item.setIndicatieGeblokkeerd(Boolean.TRUE);
        item.setIndicatieAliasSoortAdministratieveHandelingLeveren(Boolean.FALSE);

        subject.save(item);

        // Verify
        Mockito.verify(leveringsautorisatieRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(leveringsautorisatieRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(leveringsautorisatieRepository);
    }
}
