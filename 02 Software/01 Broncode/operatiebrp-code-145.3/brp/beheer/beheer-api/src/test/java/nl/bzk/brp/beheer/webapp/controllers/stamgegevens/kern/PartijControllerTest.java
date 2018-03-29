/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.SoortPartijRepository;
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
public class PartijControllerTest {

    private static final String TESTPARTIJ_CODE = "001234";
    private static final String TESTPARTIJ = "testpartij";
    @Mock
    private PartijRepository repository;
    @Mock
    private SoortPartijRepository soortPartijRepository;
    @Mock
    private PartijRolController partijRolController;
    @Mock
    private HistorieVerwerker<Partij> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;
    @InjectMocks
    private PartijController subject;

    @Before
    public void setup() {
        subject = new PartijController(repository, soortPartijRepository, partijRolController);
        subject.setTransactionManagerReadWrite(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderAbo() throws NotFoundException {

        // Setup
        final Partij item = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);
        final Partij managedItem = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);
        item.setDatumIngang(19000101);
        managedItem.setDatumIngang(19000101);
        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(repository).findOrPersist(item);
        Mockito.verify(repository).save(managedItem);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorie() throws NotFoundException {

        // Setup
        final Partij item = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);
        final Partij managedItem = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);
        item.setDatumIngang(19000101);
        item.getPartijRolSet().add(new PartijRol(item, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        item.setOin("");
        managedItem.setDatumIngang(19000101);
        managedItem.getPartijRolSet().add(new PartijRol(item, Rol.BIJHOUDINGSORGAAN_COLLEGE));

        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(repository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(repository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void wijzigObjectVoorOpslagHistorie() throws NotFoundException {

        final SoortPartij soortPartij = new SoortPartij("TEST");
        // soortPartij.setId(Short.valueOf("1"));

        // Setup
        final Partij item = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);
        final Partij managedItem = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);
        item.setDatumIngang(19000101);
        item.setIndicatieAutomatischFiatteren(Boolean.TRUE);
        ReflectionTestUtils.setField(managedItem, "naam", null);
        item.setSoortPartij(soortPartij);
        item.setNaam("VOORLOPIGE NAAM");
        item.setOin("OIN");
        item.setDatumEinde(20010101);
        item.setDatumOvergangNaarBrp(19990101);
        item.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.TRUE);
        item.getPartijRolSet().add(new PartijRol(item, Rol.BIJHOUDINGSORGAAN_COLLEGE));

        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(repository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(repository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void wijzigObjectVoorOpslagGeenBijhouderHistorie() throws NotFoundException {

        final SoortPartij soortPartij = new SoortPartij("TEST");
        // soortPartij.setId(Short.valueOf("1"));

        // Setup
        final Partij item = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);
        final Partij managedItem = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);
        managedItem.setSoortPartij(soortPartij);
        managedItem.setOin("OIN");
        managedItem.setDatumIngang(19000101);
        managedItem.setDatumEinde(20010101);
        managedItem.setDatumOvergangNaarBrp(19990101);
        managedItem.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.TRUE);
        managedItem.getPartijRolSet().add(new PartijRol(item, Rol.AFNEMER));

        Mockito.when(repository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(repository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(repository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(repository);
    }

}
