/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;

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
public class DienstbundelGroepAttribuutControllerTest {

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
    private static final Dienstbundel DIENSTBUNDEL = new Dienstbundel(LEVERINGSAUTORISATIE);
    private static final Element ATTRIBUUT = Element.ADELLIJKETITEL_CODE;
    private static final DienstbundelGroep DIENSTBUNDELGROEP =
            new DienstbundelGroep(DIENSTBUNDEL, Element.ADELLIJKETITEL, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);

    @Mock
    private ReadonlyRepository<DienstbundelGroep, Integer> dienstbundelGroepRepository;
    @Mock
    private ReadWriteRepository<DienstbundelGroepAttribuut, Integer> dienstbundelGroepAttribuutRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @InjectMocks
    private DienstbundelGroepAttribuutController subject;

    @Before
    public void setup() {
        subject = new DienstbundelGroepAttribuutController(dienstbundelGroepAttribuutRepository, dienstbundelGroepRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
        subject.setTransactionManagerReadonly(transactionManager);
        ReflectionTestUtils.setField(subject, "dienstbundelGroepRepository", dienstbundelGroepRepository);
        DIENSTBUNDELGROEP.setId(1);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderDienstbundel() throws NotFoundException {
        // Setup
        final DienstbundelGroepAttribuut item = new DienstbundelGroepAttribuut(DIENSTBUNDELGROEP, ATTRIBUUT);
        final DienstbundelGroepAttribuut managedItem = new DienstbundelGroepAttribuut(DIENSTBUNDELGROEP, ATTRIBUUT);
        Mockito.when(dienstbundelGroepAttribuutRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelGroepRepository.findOne(DIENSTBUNDELGROEP.getId())).thenReturn(DIENSTBUNDELGROEP);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(dienstbundelGroepAttribuutRepository).save(item);
        Mockito.verifyNoMoreInteractions(dienstbundelGroepAttribuutRepository);
    }

    @Test
    public void wijzigObjectVoorOpslag() throws NotFoundException {
        // Setup
        final DienstbundelGroepAttribuut item = new DienstbundelGroepAttribuut(DIENSTBUNDELGROEP, ATTRIBUUT);
        final DienstbundelGroepAttribuut managedItem = new DienstbundelGroepAttribuut(DIENSTBUNDELGROEP, ATTRIBUUT);

        Mockito.when(dienstbundelGroepAttribuutRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelGroepRepository.findOne(DIENSTBUNDELGROEP.getId())).thenReturn(DIENSTBUNDELGROEP);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(dienstbundelGroepAttribuutRepository, Mockito.times(2)).save(item);
        Mockito.verifyNoMoreInteractions(dienstbundelGroepAttribuutRepository);
    }

}
