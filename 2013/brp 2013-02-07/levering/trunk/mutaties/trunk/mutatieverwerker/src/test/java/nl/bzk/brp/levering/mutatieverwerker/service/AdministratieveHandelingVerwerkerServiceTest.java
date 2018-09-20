/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.repository.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatieverwerker.service.impl.AdministratieveHandelingVerwerkerServiceImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdministratieveHandelingVerwerkerServiceTest {

    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Mock
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @Mock
    private AdministratieveHandelingStappenVerwerker administratieveHandelingStappenVerwerker;

    @InjectMocks
    private AdministratieveHandelingVerwerkerService administratieveHandelingVerwerkerService =
            new AdministratieveHandelingVerwerkerServiceImpl();

    @Before
    public void setup() {
        final AdministratieveHandelingModel administratieveHandelingModel = mock(AdministratieveHandelingModel.class);
        final Partij partij = mock(Partij.class);
        when(partij.getNaam()).thenReturn(new NaamEnumeratiewaarde("testPartij"));

        when(administratieveHandelingModel.getPartij()).thenReturn(partij);
        when(administratieveHandelingModel.getSoort()).thenReturn(SoortAdministratieveHandeling.DUMMY);

        when(administratieveHandelingRepository.haalAdministratieveHandeling(any(Long.class))).thenReturn(
                administratieveHandelingModel);
    }

    @Test
    public void testVerwerkAdministratieveHandeling() {
        final Long admHandelingId = 12345L;

        administratieveHandelingVerwerkerService.verwerkAdministratieveHandeling(admHandelingId);

        verify(administratieveHandelingVerwerkerRepository).haalAdministratieveHandelingPersoonBsns(admHandelingId);
        verify(administratieveHandelingStappenVerwerker).verwerk(
                any(AdministratieveHandelingMutatie.class), any(AdministratieveHandelingVerwerkingContext.class));
    }


}
