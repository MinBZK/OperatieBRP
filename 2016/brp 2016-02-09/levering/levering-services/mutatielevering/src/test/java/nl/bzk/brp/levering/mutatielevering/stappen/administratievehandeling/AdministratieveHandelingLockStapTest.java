/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVergrendelRepository;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdministratieveHandelingLockStapTest {

    @Mock
    private AdministratieveHandelingVergrendelRepository administratieveHandelingLockRepository;

    @InjectMocks
    private AdministratieveHandelingLockStap administratieveHandelingLockStap;

    @Test
    public final void testLockenGelukt() {
        when(administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(anyLong())).thenReturn(true);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();
        final AdministratieveHandelingMutatie administratieveHandelingMutatie = new AdministratieveHandelingMutatie(
            1L);

        final boolean resultaat = administratieveHandelingLockStap
            .voerStapUit(administratieveHandelingMutatie, context, administratieveHandelingVerwerkingResultaat);

        assertTrue(resultaat);
        verify(administratieveHandelingLockRepository).vergrendelAlsNogNietIsVerwerkt(
            administratieveHandelingMutatie.getAdministratieveHandelingId());
    }

    @Test
    public final void testLockenMislukt() {
        when(administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(anyLong())).thenReturn(false);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
            new AdministratieveHandelingVerwerkingResultaat();
        final AdministratieveHandelingMutatie administratieveHandelingMutatie = new AdministratieveHandelingMutatie(
            1L);

        final boolean resultaat = administratieveHandelingLockStap
            .voerStapUit(administratieveHandelingMutatie, context, administratieveHandelingVerwerkingResultaat);

        assertFalse(resultaat);
        verify(administratieveHandelingLockRepository).vergrendelAlsNogNietIsVerwerkt(
            administratieveHandelingMutatie.getAdministratieveHandelingId());
    }

}
