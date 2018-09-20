/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.levering.algemeen.service.AdministratieveHandelingenOverslaanService;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerifieerAdministratieveHandelingStapTest {

    @Mock
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @Mock
    private AdministratieveHandelingModel administratieveHandelingModel;

    @InjectMocks
    private final VerifieerAdministratieveHandelingStap verifieerAdministratieveHandelingStap =
        new VerifieerAdministratieveHandelingStap();

    @Mock
    private AdministratieveHandelingenOverslaanService administratieveHandelingenOverslaanService;

    private final List<Integer>                       personenBijhouding = Arrays.asList(3, 4);
    private final List<SoortAdministratieveHandeling> overslaanAdmHnd    = new ArrayList<>();

    @Before
    public final void pre() {
        when(administratieveHandelingenOverslaanService
            .geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden()).thenReturn(overslaanAdmHnd);
        when(administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(anyLong()))
            .thenReturn(personenBijhouding);
    }

    // Administratieve Handeling met betrokken personen: 3 en 4. Mag verwerkt worden.
    @Test
    @SuppressWarnings("unchecked")
    public final void testVerificatieGelukt() {
        // Arrange
        final Long administratieveHandelingId = 3L;

        when(administratieveHandelingModel.getID()).thenReturn(administratieveHandelingId);
        when(administratieveHandelingVerwerkerRepository.magAdministratieveHandelingVerwerktWorden(
            any(AdministratieveHandelingModel.class), anyList(), anyList())).thenReturn(Boolean.TRUE);

        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        context.setBijgehoudenPersoonIds(new ArrayList<>(Arrays.asList(3, 4)));
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();
        final AdministratieveHandelingMutatie administratieveHandelingMutatie = new AdministratieveHandelingMutatie(
            administratieveHandelingId);

        context.setHuidigeAdministratieveHandeling(administratieveHandelingModel);

        // Act
        final boolean resultaat = verifieerAdministratieveHandelingStap
            .voerStapUit(administratieveHandelingMutatie, context, administratieveHandelingVerwerkingResultaat);

        // Assert
        assertTrue(resultaat);
        Mockito.verify(administratieveHandelingVerwerkerRepository)
            .magAdministratieveHandelingVerwerktWorden(administratieveHandelingModel, personenBijhouding,
                overslaanAdmHnd);
    }

    // Administratieve Handeling met betrokken personen: 3 en 4. Mag niet verwerkt worden.
    @Test
    @SuppressWarnings("unchecked")
    public final void testVerificatieMislukt() {
        // Arrange
        final Long administratieveHandelingId = 4L;

        when(administratieveHandelingModel.getID()).thenReturn(administratieveHandelingId);
        when(administratieveHandelingVerwerkerRepository.magAdministratieveHandelingVerwerktWorden(
            any(AdministratieveHandelingModel.class), anyList(), anyList())).thenReturn(Boolean.FALSE);

        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        context.setBijgehoudenPersoonIds(new ArrayList<>(Arrays.asList(3, 4)));
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();
        final AdministratieveHandelingMutatie administratieveHandelingMutatie = new AdministratieveHandelingMutatie(
            administratieveHandelingId);

        context.setHuidigeAdministratieveHandeling(administratieveHandelingModel);

        // Act
        final boolean resultaat = verifieerAdministratieveHandelingStap
            .voerStapUit(administratieveHandelingMutatie, context, administratieveHandelingVerwerkingResultaat);

        // Assert
        assertFalse("Verwacht dat de verwerking niet gedaan wordt (resultaat false)", resultaat);
        Mockito.verify(administratieveHandelingVerwerkerRepository)
            .magAdministratieveHandelingVerwerktWorden(administratieveHandelingModel, personenBijhouding,
                overslaanAdmHnd);
    }

}
