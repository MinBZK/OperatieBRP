/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelLo3RubriekRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class DienstbundelLO3RubriekControllerTest {

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
    private static final Dienstbundel DIENSTBUNDEL = new Dienstbundel(LEVERINGSAUTORISATIE);
    private static final Lo3Rubriek RUBRIEK = new Lo3Rubriek("01.10.10");
    @Mock
    private DienstbundelLo3RubriekRepository repository;
    @Mock
    private DienstbundelRepository dienstbundelRepository;
    @Mock
    private ReadonlyRepository<Lo3Rubriek, Integer> conversieLo3Repository;
    @Mock
    private PlatformTransactionManager transactionManager;

    @InjectMocks
    private DienstbundelLo3RubriekController subject;

    @Before
    public void setup() {
        subject = new DienstbundelLo3RubriekController(repository, dienstbundelRepository, conversieLo3Repository);
        subject.setTransactionManagerReadWrite(transactionManager);
        subject.setTransactionManagerReadonly(transactionManager);
        ReflectionTestUtils.setField(subject, "dienstbundelRepository", dienstbundelRepository);
        ReflectionTestUtils.setField(subject, "conversieLo3Repository", conversieLo3Repository);
        DIENSTBUNDEL.setId(1);
        LEVERINGSAUTORISATIE.setId(1);
        RUBRIEK.setId(1);
        Mockito.when(dienstbundelRepository.findOne(DIENSTBUNDEL.getId())).thenReturn(DIENSTBUNDEL);
        Mockito.when(conversieLo3Repository.findOne(RUBRIEK.getId())).thenReturn(RUBRIEK);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderDienstbundel() throws NotFoundException {
        // Setup
        final DienstbundelLo3Rubriek item = new DienstbundelLo3Rubriek(DIENSTBUNDEL, RUBRIEK);
        item.setId(1);
        final DienstbundelLo3Rubriek managedItem = new DienstbundelLo3Rubriek(DIENSTBUNDEL, RUBRIEK);
        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);
        managedItem.setId(1);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(repository).save(item);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void wijzigObjectVoorOpslag() throws NotFoundException {
        // Setup
        final DienstbundelLo3Rubriek item = new DienstbundelLo3Rubriek(DIENSTBUNDEL, RUBRIEK);
        item.setId(1);
        final DienstbundelLo3Rubriek managedItem = new DienstbundelLo3Rubriek(DIENSTBUNDEL, RUBRIEK);
        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);
        managedItem.setId(1);

        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(repository, Mockito.times(2)).save(item);
        Mockito.verifyNoMoreInteractions(repository);
    }
}
