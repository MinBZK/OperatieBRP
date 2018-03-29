/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.BijhouderFiatteringsuitzonderingRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRolRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class BijhouderFiatteringsuitzonderingControllerTest {

    private static final String ROTTERDAM_ID = "000363";
    private static final String AMSTERDAM_ID = "000626";
    private static final Partij PARTIJ = new Partij("Gemeente Amsterdam", AMSTERDAM_ID);
    private static final Partij PARTIJ_BIJHOUDINGSVOORSTEL = new Partij("Gemeente Rottedam", ROTTERDAM_ID);
    private static final PartijRol PARTIJROL = new PartijRol(PARTIJ, Rol.BIJHOUDINGSORGAAN_COLLEGE);
    private static final PartijRol PARTIJROL_BIJHOUDINGSVOORSTEL = new PartijRol(PARTIJ_BIJHOUDINGSVOORSTEL, Rol.BIJHOUDINGSORGAAN_COLLEGE);

    @Mock
    private BijhouderFiatteringsuitzonderingRepository bijhouderFiatteringsuitzonderingRepository;
    @Mock
    private PartijRolRepository partijRolRepository;
    @Mock
    private HistorieVerwerker<BijhouderFiatteringsuitzondering> historieVerwerker;
    @Mock
    private PlatformTransactionManager transactionManager;

    private BijhouderFiatteringsuitzonderingController subject;

    @Before
    public void setup() {
        subject = new BijhouderFiatteringsuitzonderingController(bijhouderFiatteringsuitzonderingRepository, partijRolRepository);
        subject.setTransactionManagerReadWrite(transactionManager);
        subject.setTransactionManagerReadonly(transactionManager);
        ReflectionTestUtils.setField(subject, "partijRolRepository", partijRolRepository);
        PARTIJROL.setId(Integer.valueOf(AMSTERDAM_ID));
        PARTIJROL_BIJHOUDINGSVOORSTEL.setId(Integer.valueOf(ROTTERDAM_ID));
        Mockito.when(partijRolRepository.getOne(Integer.valueOf(AMSTERDAM_ID))).thenReturn(PARTIJROL);
        Mockito.when(partijRolRepository.getOne(Integer.valueOf(ROTTERDAM_ID))).thenReturn(PARTIJROL_BIJHOUDINGSVOORSTEL);
    }

    @Test
    public void wijzigObjectVoorOpslagZonderLeveringsautorisatie() throws NotFoundException {
        // Setup
        final BijhouderFiatteringsuitzondering item = new BijhouderFiatteringsuitzondering(PARTIJROL);
        item.setBijhouder(PARTIJROL);
        item.setBijhouderBijhoudingsvoorstel(PARTIJROL_BIJHOUDINGSVOORSTEL);
        item.setDatumIngang(20110101);
        final BijhouderFiatteringsuitzondering managedItem = new BijhouderFiatteringsuitzondering(PARTIJROL);
        managedItem.setBijhouder(PARTIJROL);
        managedItem.setBijhouderBijhoudingsvoorstel(PARTIJROL_BIJHOUDINGSVOORSTEL);
        managedItem.setDatumIngang(20110101);
        Mockito.when(bijhouderFiatteringsuitzonderingRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        // Verify
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository).findOrPersist(item);
        Mockito.verify(partijRolRepository).getOne(item.getBijhouder().getId());
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository).findOrPersist(item);
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository).save(managedItem);
        Mockito.verifyNoMoreInteractions(bijhouderFiatteringsuitzonderingRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorie() throws NotFoundException {
        // Setup
        final BijhouderFiatteringsuitzondering item = new BijhouderFiatteringsuitzondering(PARTIJROL);
        item.setDatumEinde(20110101);
        item.setDatumIngang(20010101);
        item.setIndicatieGeblokkeerd(Boolean.TRUE);
        item.setBijhouderBijhoudingsvoorstel(PARTIJROL_BIJHOUDINGSVOORSTEL);
        final BijhouderFiatteringsuitzondering managedItem = new BijhouderFiatteringsuitzondering(PARTIJROL);
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);
        item.setBijhouderBijhoudingsvoorstel(PARTIJROL_BIJHOUDINGSVOORSTEL);

        Mockito.when(bijhouderFiatteringsuitzonderingRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(bijhouderFiatteringsuitzonderingRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagMetHistorieNulls() throws NotFoundException {
        // Setup
        final BijhouderFiatteringsuitzondering item = new BijhouderFiatteringsuitzondering(PARTIJROL);
        item.setDatumIngang(20100101);
        final BijhouderFiatteringsuitzondering managedItem = new BijhouderFiatteringsuitzondering(PARTIJROL);
        managedItem.setDatumEinde(20110101);
        managedItem.setDatumIngang(20010101);
        managedItem.setIndicatieGeblokkeerd(Boolean.TRUE);
        managedItem.setBijhouderBijhoudingsvoorstel(PARTIJROL_BIJHOUDINGSVOORSTEL);

        Mockito.when(bijhouderFiatteringsuitzonderingRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);
        subject.save(item);

        // Verify
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(bijhouderFiatteringsuitzonderingRepository);
    }

    @Test
    public void wijzigObjectVoorOpslagHistorie() throws NotFoundException {
        // Setup
        final SoortDocument soortDocument = new SoortDocument("Testakte", "Akte voor test");
        final BijhouderFiatteringsuitzondering item = new BijhouderFiatteringsuitzondering(PARTIJROL);
        item.setDatumIngang(20110101);
        item.setSoortAdministratieveHandeling(SoortAdministratieveHandeling.ACTUALISERING_PERSOON);
        item.setSoortDocument(soortDocument);
        final BijhouderFiatteringsuitzondering managedItem = new BijhouderFiatteringsuitzondering(PARTIJROL);
        managedItem.setBijhouderBijhoudingsvoorstel(PARTIJROL_BIJHOUDINGSVOORSTEL);
        managedItem.setDatumIngang(20110101);

        Mockito.when(bijhouderFiatteringsuitzonderingRepository.findOrPersist(item)).thenReturn(managedItem);

        // Execute
        subject.save(item);

        item.setIndicatieGeblokkeerd(Boolean.TRUE);

        subject.save(item);

        // Verify
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository, Mockito.times(2)).findOrPersist(item);
        Mockito.verify(bijhouderFiatteringsuitzonderingRepository, Mockito.times(2)).save(managedItem);
        Mockito.verifyNoMoreInteractions(bijhouderFiatteringsuitzonderingRepository);
    }
}
