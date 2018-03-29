/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRolRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class ToegangLeveringsautorisatieControllerTest {
    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
    private static final Partij GEAUTORISEERDE = new Partij("geautoriseerde", "001234");
    private static final Partij ONDERTEKENAAR = new Partij("ondertekenaar", "004343");
    private static final Partij TRANSPORTEUR = new Partij("transporteur", "009546");
    private static final PartijRol PARTIJROL = new PartijRol(GEAUTORISEERDE, Rol.BIJHOUDINGSORGAAN_COLLEGE);

    @Mock
    private ToegangLeveringsautorisatieRepository toegangLeveringsautorisatieRepository;
    @Mock
    private LeveringsautorisatieRepository leveringsautorisatieRepository;
    @Mock
    private PartijRepository partijRepository;
    @Mock
    private PartijRolRepository partijRolRepository;
    @Mock
    private HistorieVerwerker<Dienst> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;
    @InjectMocks
    private ToegangLeveringsautorisatieController subject;

    @Before
    public void setup() {
        LEVERINGSAUTORISATIE.setId(1);
        Mockito.when(leveringsautorisatieRepository.getOne(Matchers.anyInt())).thenReturn(LEVERINGSAUTORISATIE);
        Mockito.when(partijRepository.getOne((short) 1234)).thenReturn(GEAUTORISEERDE);
        Mockito.when(partijRepository.getOne((short) 4343)).thenReturn(ONDERTEKENAAR);
        Mockito.when(partijRepository.getOne((short) 9546)).thenReturn(TRANSPORTEUR);
        Mockito.when(partijRolRepository.getOne(Matchers.anyInt())).thenReturn(PARTIJROL);
        subject = new ToegangLeveringsautorisatieController(toegangLeveringsautorisatieRepository, leveringsautorisatieRepository, partijRepository,
                partijRolRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
        subject.setTransactionManagerReadonly(transactionManager);
        ReflectionTestUtils.setField(subject, "partijRepository", partijRepository);
        ReflectionTestUtils.setField(subject, "partijRolRepository", partijRolRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderLeveringsautorisatie() throws NotFoundException {
        // Setup
        final ToegangLeveringsAutorisatie item = new ToegangLeveringsAutorisatie(PARTIJROL, LEVERINGSAUTORISATIE);
        final ToegangLeveringsAutorisatie managedItem = new ToegangLeveringsAutorisatie(PARTIJROL, LEVERINGSAUTORISATIE);
        Mockito.when(toegangLeveringsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(toegangLeveringsautorisatieRepository).findOrPersist(item);
        Mockito.verify(toegangLeveringsautorisatieRepository).save(managedItem);
        Mockito.verifyNoMoreInteractions(toegangLeveringsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorie() throws NotFoundException {
        // Setup
        final ToegangLeveringsAutorisatie item = new ToegangLeveringsAutorisatie(PARTIJROL, LEVERINGSAUTORISATIE);
        item.setAfleverpunt("https://afleverpunt");
        item.setDatumEinde(20110101);
        item.setDatumIngang(20010101);
        item.setIndicatieGeblokkeerd(Boolean.TRUE);
        item.setNaderePopulatiebeperking("WAAR");
        item.setOndertekenaar(ONDERTEKENAAR);
        item.setTransporteur(TRANSPORTEUR);
        final ToegangLeveringsAutorisatie managedItem = new ToegangLeveringsAutorisatie(PARTIJROL, LEVERINGSAUTORISATIE);
        managedItem.setAfleverpunt("https://afleverpunt");
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);
        managedItem.setNaderePopulatiebeperking("WAAR");
        managedItem.setOndertekenaar(ONDERTEKENAAR);
        managedItem.setTransporteur(TRANSPORTEUR);

        Mockito.when(toegangLeveringsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(toegangLeveringsautorisatieRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(toegangLeveringsautorisatieRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(toegangLeveringsautorisatieRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagHistorie() throws NotFoundException {
        // Setup
        final ToegangLeveringsAutorisatie item = new ToegangLeveringsAutorisatie(PARTIJROL, LEVERINGSAUTORISATIE);
        final ToegangLeveringsAutorisatie managedItem = new ToegangLeveringsAutorisatie(PARTIJROL, LEVERINGSAUTORISATIE);
        managedItem.setDatumIngang(20010101);
        managedItem.setDatumEinde(20993112);

        Mockito.when(toegangLeveringsautorisatieRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        item.setIndicatieGeblokkeerd(Boolean.TRUE);

        subject.save(item);

        // Verify
        Mockito.verify(toegangLeveringsautorisatieRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(toegangLeveringsautorisatieRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(toegangLeveringsautorisatieRepository);
    }

}
