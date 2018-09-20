/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.mutatielevering.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import support.AdministratieveHandelingTestBouwer;

@RunWith(MockitoJUnitRunner.class)
public class HaalAdministratieveHandelingOpStapTest {

    @InjectMocks
    private final HaalAdministratieveHandelingOpStap haalAdministratieveHandelingOpStap =
        new HaalAdministratieveHandelingOpStap();

    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    private AdministratieveHandelingModel administratieveHandelingModel;

    @Before
    public final void setup() {
        administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        when(administratieveHandelingRepository.haalAdministratieveHandeling(any(Long.class)))
            .thenReturn(administratieveHandelingModel);
    }

    @Test
    public final void testVoerStapUitEnControleerSuccesvol() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

        haalAdministratieveHandelingOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
            administratieveHandelingVerwerkingResultaat);

        assertTrue(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    @Test
    public final void testVoerStapUitEnControleerRepositoryAanroep() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

        haalAdministratieveHandelingOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
            administratieveHandelingVerwerkingResultaat);

        verify(administratieveHandelingRepository).haalAdministratieveHandeling(administratieveHandelingModel.getID());
    }

    @Test
    public final void testVoerStapUitEnControleerContextGevuld() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

        haalAdministratieveHandelingOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
            administratieveHandelingVerwerkingResultaat);

        assertEquals(administratieveHandelingModel, context.getHuidigeAdministratieveHandeling());
    }

    @Test(expected = DataNietAanwezigExceptie.class)
    public final void testVoerStapUitKanAdministratieveHandelingNietOphalen() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();
        when(administratieveHandelingRepository.haalAdministratieveHandeling(any(Long.class)))
            .thenReturn(null);

        haalAdministratieveHandelingOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
            administratieveHandelingVerwerkingResultaat);
    }

    private AdministratieveHandelingMutatie getAdministratieveHandelingMutatie() {
        return new AdministratieveHandelingMutatie(
            administratieveHandelingModel.getID());
    }

}
