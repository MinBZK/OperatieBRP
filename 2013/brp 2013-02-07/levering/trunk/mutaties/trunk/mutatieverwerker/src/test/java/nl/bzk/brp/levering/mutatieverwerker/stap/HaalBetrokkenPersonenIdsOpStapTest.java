/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.repository.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HaalBetrokkenPersonenIdsOpStapTest {

    @InjectMocks
    private final HaalBetrokkenPersonenIdsOpStap haalBetrokkenPersonenIdsOpStap =
            new HaalBetrokkenPersonenIdsOpStap();

    @Mock
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    private List<Integer> betrokkenPersonenIds;

    private static final long ADMINISRATIEVE_HANDELING_ID = 123456789L;

    @Before
    public void setup() {
        betrokkenPersonenIds = maakTestBetrokkenPersonenIds();

        when(administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(any(Long.class)))
                .thenReturn(betrokkenPersonenIds);
    }

    private List<Integer> maakTestBetrokkenPersonenIds() {
        return Arrays.asList(new Integer[]{1234, 4433, 32453});
    }

    @Test
    public void testVoerStapUitEnControleerSuccesvol() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();

        haalBetrokkenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        assertTrue(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    @Test
    public void testVoerStapUitEnControleerRepositoryAanroep() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();

        haalBetrokkenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        verify(administratieveHandelingVerwerkerRepository).haalAdministratieveHandelingPersoonIds(ADMINISRATIEVE_HANDELING_ID);
    }

    @Test
    public void testVoerStapUitEnControleerContextGevuld() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();

        haalBetrokkenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        assertArrayEquals(betrokkenPersonenIds.toArray(), context.getBetrokkenPersonenIds().toArray());
    }

    @Test
    public void testVoerStapUitKanAdministratieveHandelingNietOphalen() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();
        when(administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(any(Long.class)))
                .thenReturn(null);

        final boolean resultaat =
                haalBetrokkenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                administratieveHandelingVerwerkingResultaat);

        assertEquals(AbstractStap.STOPPEN, resultaat);
        assertFalse(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    private AdministratieveHandelingMutatie getTestAdministratieveHandelingMutatie() {
        return new AdministratieveHandelingMutatie(ADMINISRATIEVE_HANDELING_ID, null);
    }

}
