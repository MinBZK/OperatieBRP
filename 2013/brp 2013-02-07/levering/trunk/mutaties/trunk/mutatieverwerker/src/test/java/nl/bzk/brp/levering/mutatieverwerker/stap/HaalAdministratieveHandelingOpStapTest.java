/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.mutatieverwerker.AdministratieveHandelingTestBouwer;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HaalAdministratieveHandelingOpStapTest {

    @InjectMocks
    private HaalAdministratieveHandelingOpStap haalAdministratieveHandelingOpStap =
            new HaalAdministratieveHandelingOpStap();

    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    private AdministratieveHandelingModel administratieveHandelingModel;

    @Before
    public void setup() {
        administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        when(administratieveHandelingRepository.haalAdministratieveHandeling(any(Long.class)))
                .thenReturn(administratieveHandelingModel);
    }

    @Test
    public void testVoerStapUitEnControleerSuccesvol() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();

        haalAdministratieveHandelingOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        assertTrue(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    @Test
    public void testVoerStapUitEnControleerRepositoryAanroep() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();

        haalAdministratieveHandelingOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        verify(administratieveHandelingRepository).haalAdministratieveHandeling(administratieveHandelingModel.getID());
    }

    @Test
    public void testVoerStapUitEnControleerContextGevuld() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();

        haalAdministratieveHandelingOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        assertEquals(administratieveHandelingModel, context.getHuidigeAdministratieveHandeling());
    }

    @Test
    public void testVoerStapUitKanAdministratieveHandelingNietOphalen() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();
        when(administratieveHandelingRepository.haalAdministratieveHandeling(any(Long.class)))
                .thenReturn(null);

        final boolean resultaat =
                haalAdministratieveHandelingOpStap.voerStapUit(getAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        assertEquals(AbstractStap.STOPPEN, resultaat);
        assertFalse(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    private AdministratieveHandelingMutatie getAdministratieveHandelingMutatie() {
        return new AdministratieveHandelingMutatie(
                administratieveHandelingModel.getID(), null);
    }

}
