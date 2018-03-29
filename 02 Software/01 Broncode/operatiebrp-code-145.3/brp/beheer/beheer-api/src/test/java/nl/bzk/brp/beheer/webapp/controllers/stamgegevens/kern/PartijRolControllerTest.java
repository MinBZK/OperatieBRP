/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRolRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class PartijRolControllerTest {

    private static final String TESTPARTIJ_CODE = "001234";
    private static final String TESTPARTIJ = "testpartij";
    private static final Partij PARTIJ = new Partij(TESTPARTIJ, TESTPARTIJ_CODE);

    @Mock
    private PartijRolRepository repository;
    @Mock
    private PlatformTransactionManager transactionManager;
    @InjectMocks
    private PartijRolController subject;

    @Before
    public void setup() {
        subject = new PartijRolController(repository);
        subject.setTransactionManagerReadWrite(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslag() throws NotFoundException {

        // Setup
        final PartijRol item = new PartijRol(PARTIJ, Rol.BIJHOUDINGSORGAAN_COLLEGE);
        final PartijRol managedItem = new PartijRol(PARTIJ, Rol.BIJHOUDINGSORGAAN_COLLEGE);
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
    public void wijzigObjectVoorOpslagHistorie() throws NotFoundException {

        // Setup
        final PartijRol item = new PartijRol(PARTIJ, Rol.BIJHOUDINGSORGAAN_COLLEGE);
        final PartijRol managedItem = new PartijRol(PARTIJ, Rol.BIJHOUDINGSORGAAN_COLLEGE);
        item.setDatumIngang(19000101);
        managedItem.setDatumIngang(19000101);
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
    public void testMaakHistoryLeeg() {
        final PartijRolController.PartijRolHistorieVerwerker historieVerwerker = new PartijRolController.PartijRolHistorieVerwerker();
        final PartijRol partijRol = new PartijRol(new Partij("naam", "000123"), Rol.AFNEMER);
        Assert.assertNull("historie moet leeg zijn", historieVerwerker.maakHistorie(partijRol));
    }

}
