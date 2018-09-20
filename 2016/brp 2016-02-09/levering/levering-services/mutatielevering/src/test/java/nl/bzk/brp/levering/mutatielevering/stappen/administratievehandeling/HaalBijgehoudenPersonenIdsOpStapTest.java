/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatielevering.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HaalBijgehoudenPersonenIdsOpStapTest {

    private static final long ADMINISRATIEVE_HANDELING_ID = 123456789L;

    @InjectMocks
    private final HaalBijgehoudenPersonenIdsOpStap haalBijgehoudenPersonenIdsOpStap = new HaalBijgehoudenPersonenIdsOpStap();

    @Mock
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    private List<Integer> bijgehoudenPersoonIds;

    @Before
    public final void setup() {
        bijgehoudenPersoonIds = maakTestBijgehoudenPersoonIds();

        when(administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(any(Long.class)))
            .thenReturn(bijgehoudenPersoonIds);
    }

    private List<Integer> maakTestBijgehoudenPersoonIds() {
        return Arrays.asList(1234, 4433, 32453);
    }

    @Test
    public final void testVoerStapUitEnControleerSuccesvol() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

        haalBijgehoudenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                                                   administratieveHandelingVerwerkingResultaat);

        assertTrue(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

    @Test
    public final void testVoerStapUitEnControleerRepositoryAanroep() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

        haalBijgehoudenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                                                   administratieveHandelingVerwerkingResultaat);

        verify(administratieveHandelingVerwerkerRepository)
            .haalAdministratieveHandelingPersoonIds(ADMINISRATIEVE_HANDELING_ID);
    }

    @Test
    public final void testVoerStapUitEnControleerContextGevuld() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

        haalBijgehoudenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                                                   administratieveHandelingVerwerkingResultaat);

        assertArrayEquals(bijgehoudenPersoonIds.toArray(), context.getBijgehoudenPersoonIds().toArray());
    }

    @Test(expected = DataNietAanwezigExceptie.class)
    public final void testVoerStapUitKanAdministratieveHandelingNietOphalen() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();
        when(administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(any(Long.class)))
            .thenReturn(null);

        haalBijgehoudenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                                                   administratieveHandelingVerwerkingResultaat);
    }

    @Test(expected = DataNietAanwezigExceptie.class)
    public final void testVoerStapUitZonderBijgehoudenPersonenIds() {
        when(administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(any(Long.class)))
            .thenReturn(null);

        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

        haalBijgehoudenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                                                   administratieveHandelingVerwerkingResultaat);
    }

    @Test(expected = DataNietAanwezigExceptie.class)
    public final void testVoerStapUitMetLegeBijgehoudenPersonenIds() {
        when(administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(any(Long.class)))
            .thenReturn(new ArrayList<Integer>());

        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();

        haalBijgehoudenPersonenIdsOpStap.voerStapUit(getTestAdministratieveHandelingMutatie(), context,
                                                   administratieveHandelingVerwerkingResultaat);
    }

    private AdministratieveHandelingMutatie getTestAdministratieveHandelingMutatie() {
        return new AdministratieveHandelingMutatie(ADMINISRATIEVE_HANDELING_ID);
    }

}
