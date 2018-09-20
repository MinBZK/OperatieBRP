/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.service;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatielevering.service.impl.AdministratieveHandelingVerwerkerServiceImpl;
import nl.bzk.brp.levering.mutatielevering.stappen.AdministratieveHandelingStappenVerwerker;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdministratieveHandelingVerwerkerServiceTest {

    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Mock
    private AdministratieveHandelingStappenVerwerker administratieveHandelingStappenVerwerker;

    @InjectMocks
    private final AdministratieveHandelingVerwerkerService administratieveHandelingVerwerkerService =
        new AdministratieveHandelingVerwerkerServiceImpl();

    @Mock
    private AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat;

    @Before
    public final void setup() {
        final AdministratieveHandelingModel administratieveHandelingModel = mock(AdministratieveHandelingModel.class);
        final Partij partij = TestPartijBuilder.maker().metNaam("testPartij").maak();

        when(administratieveHandelingModel.getPartij()).thenReturn(new PartijAttribuut(partij));
        when(administratieveHandelingModel.getSoort())
            .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.DUMMY));

        when(administratieveHandelingRepository.haalAdministratieveHandeling(any(Long.class))).thenReturn(
            administratieveHandelingModel);

        when(administratieveHandelingStappenVerwerker
            .verwerk(Matchers.any(AdministratieveHandelingMutatie.class),
                Matchers.any(AdministratieveHandelingVerwerkingContext.class)))
            .thenReturn(administratieveHandelingVerwerkingResultaat);
        when(administratieveHandelingVerwerkingResultaat.isSuccesvol()).thenReturn(true);
    }

    @Test
    public final void testVerwerkAdministratieveHandeling() {
        final Long admHandelingId = 12345L;

        administratieveHandelingVerwerkerService.verwerkAdministratieveHandeling(admHandelingId);

        verify(administratieveHandelingStappenVerwerker).verwerk(
            any(AdministratieveHandelingMutatie.class), any(AdministratieveHandelingVerwerkingContext.class));
    }

    @Test
    public final void testVerwerkAdministratieveHandelingControleerOnderwerp() {
        final Long admHandelingId = 12345L;

        administratieveHandelingVerwerkerService.verwerkAdministratieveHandeling(admHandelingId);

        final ArgumentCaptor<AdministratieveHandelingMutatie> onderwerpCaptor =
            ArgumentCaptor.forClass(AdministratieveHandelingMutatie.class);

        verify(administratieveHandelingStappenVerwerker).verwerk(
            onderwerpCaptor.capture(), any(AdministratieveHandelingVerwerkingContext.class));

        assertEquals(admHandelingId, onderwerpCaptor.getValue().getAdministratieveHandelingId());
    }

    @Test
    public final void testVerwerkingNietSuccesvol() {
        final Long admHandelingId = 12345L;

        when(administratieveHandelingStappenVerwerker
            .verwerk(Matchers.any(AdministratieveHandelingMutatie.class),
                Matchers.any(AdministratieveHandelingVerwerkingContext.class)))
            .thenReturn(administratieveHandelingVerwerkingResultaat);
        when(administratieveHandelingVerwerkingResultaat.isSuccesvol()).thenReturn(false);
        when(administratieveHandelingVerwerkingResultaat.getMeldingen()).thenReturn(Arrays.asList(new Melding(
            SoortMelding.FOUT, Regel.ALG0001)));

        // act
        administratieveHandelingVerwerkerService.verwerkAdministratieveHandeling(admHandelingId);

        //assert
        verify(administratieveHandelingVerwerkingResultaat).getMeldingen();
    }
}
