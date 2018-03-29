/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstRepository;
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
public class DienstControllerTest {

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
    private static final Dienstbundel DIENSTBUNDEL = new Dienstbundel(LEVERINGSAUTORISATIE);

    @Mock
    private DienstRepository dienstRepository;
    @Mock
    private ReadonlyRepository<Dienstbundel, Integer> dienstbundelRepository;
    @Mock
    private HistorieVerwerker<Dienst> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;
    @InjectMocks
    private DienstController subject;

    @Before
    public void setup() {
        subject = new DienstController(dienstRepository, dienstbundelRepository);
        DIENSTBUNDEL.setId(1);
        ReflectionTestUtils.setField(subject, "dienstbundelRepository", dienstbundelRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
        subject.setTransactionManagerReadonly(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderDienstbundel() throws NotFoundException {
        // Setup
        final Dienst item = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        item.setDatumIngang(20120101);
        final Dienst managedItem = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        managedItem.setDatumIngang(20120101);
        Mockito.when(dienstRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelRepository.findOne(DIENSTBUNDEL.getId())).thenReturn(DIENSTBUNDEL);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(dienstRepository).findOrPersist(item);
        Mockito.verify(dienstRepository).save(managedItem);
        Mockito.verifyNoMoreInteractions(dienstRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorie() throws NotFoundException {
        // Setup
        final Dienst item = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        item.setAttenderingscriterium("1");
        item.setDatumEinde(20110101);
        item.setDatumIngang(20010101);
        item.setEersteSelectieDatum(20100101);
        item.setEffectAfnemerindicaties(EffectAfnemerindicaties.PLAATSING);
        item.setIndicatieGeblokkeerd(Boolean.TRUE);
        final Dienst managedItem = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        managedItem.setAttenderingscriterium("1");
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setEersteSelectieDatum(20100101);
        managedItem.setEffectAfnemerindicaties(EffectAfnemerindicaties.PLAATSING);
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);

        Mockito.when(dienstRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelRepository.findOne(DIENSTBUNDEL.getId())).thenReturn(DIENSTBUNDEL);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(dienstRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(dienstRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(dienstRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagHistorie() throws NotFoundException {
        // Setup
        final Dienst item = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        item.setDatumIngang(20010101);
        final Dienst managedItem = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        managedItem.setDatumIngang(20010101);

        Mockito.when(dienstRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelRepository.findOne(DIENSTBUNDEL.getId())).thenReturn(DIENSTBUNDEL);

        // Execute
        subject.save(item);

        item.setAttenderingscriterium("1");

        subject.save(item);

        // Verify
        Mockito.verify(dienstRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(dienstRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(dienstRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagHistorieNullChecks() throws NotFoundException {
        // Setup
        final Dienst item = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        final Dienst managedItem = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        managedItem.setDatumIngang(20010101);

        Mockito.when(dienstRepository.findOrPersist(item)).thenReturn(managedItem);
        Mockito.when(dienstbundelRepository.findOne(DIENSTBUNDEL.getId())).thenReturn(DIENSTBUNDEL);

        // Execute
        subject.save(item);

    }

}
