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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class DienstbundelGroepControllerTest {

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
    private static final Dienstbundel DIENSTBUNDEL = new Dienstbundel(LEVERINGSAUTORISATIE);
    @Mock
    private ReadWriteRepository<Dienstbundel, Integer> dienstbundelRepository;
    @Mock
    private ReadWriteRepository<DienstbundelGroep, Integer> dienstbundelGroepRepository;
    @Mock
    private PlatformTransactionManager transactionManager;

    private DienstbundelGroepController subject;

    @Before
    public void setup() {
        subject = new DienstbundelGroepController(dienstbundelGroepRepository, dienstbundelRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
        subject.setTransactionManagerReadonly(transactionManager);
        ReflectionTestUtils.setField(subject, "dienstbundelRepository", dienstbundelRepository);
        DIENSTBUNDEL.setId(1);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderDienstbundel() throws NotFoundException {
        // Setup
        final DienstbundelGroep item = new DienstbundelGroep(DIENSTBUNDEL, Element.ADELLIJKETITEL, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
        final DienstbundelGroep managedItem = new DienstbundelGroep(DIENSTBUNDEL, Element.ADELLIJKETITEL, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
        Mockito.when(dienstbundelGroepRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelRepository.findOne(DIENSTBUNDEL.getId())).thenReturn(DIENSTBUNDEL);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(dienstbundelGroepRepository).save(item);
        Mockito.verifyNoMoreInteractions(dienstbundelGroepRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorie() throws NotFoundException {
        // Setup
        final DienstbundelGroep item = new DienstbundelGroep(DIENSTBUNDEL, Element.ADELLIJKETITEL, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
        item.addDienstbundelGroepAttribuutSet(new DienstbundelGroepAttribuut(item, Element.ADELLIJKETITEL_CODE));
        final DienstbundelGroep managedItem = new DienstbundelGroep(DIENSTBUNDEL, Element.ADELLIJKETITEL, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
        managedItem.addDienstbundelGroepAttribuutSet(new DienstbundelGroepAttribuut(item, Element.ADELLIJKETITEL_CODE));

        Mockito.when(dienstbundelGroepRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelRepository.findOne(DIENSTBUNDEL.getId())).thenReturn(DIENSTBUNDEL);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(dienstbundelGroepRepository, Mockito.times(2)).save(item);
        Mockito.verifyNoMoreInteractions(dienstbundelGroepRepository);
    }

    @Test
    public void testZonderDienstbundel() throws NotFoundException {
        // Setup
        final DienstbundelGroep item = new DienstbundelGroep(DIENSTBUNDEL, Element.ADELLIJKETITEL, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
        ReflectionTestUtils.setField(item, "dienstbundel", null);
        final DienstbundelGroep managedItem = new DienstbundelGroep(DIENSTBUNDEL, Element.ADELLIJKETITEL, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
        Mockito.when(dienstbundelGroepRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelRepository.findOne(DIENSTBUNDEL.getId())).thenReturn(DIENSTBUNDEL);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(dienstbundelGroepRepository).save(item);
        Mockito.verifyNoMoreInteractions(dienstbundelGroepRepository);
    }

}
