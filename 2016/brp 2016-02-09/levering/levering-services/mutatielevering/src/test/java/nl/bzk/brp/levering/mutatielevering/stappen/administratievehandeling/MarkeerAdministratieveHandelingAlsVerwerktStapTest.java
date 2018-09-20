/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MarkeerAdministratieveHandelingAlsVerwerktStapTest {

    @Mock
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @InjectMocks
    private MarkeerAdministratieveHandelingAlsVerwerktStap markeerAdministratieveHandelingAlsVerwerktStap;

    private final long                                        administratieveHandelingId                  = 1L;
    private final AdministratieveHandelingVerwerkingContext   context                                     =
        new AdministratieveHandelingVerwerkingContextImpl();
    private final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
        new AdministratieveHandelingVerwerkingResultaat();
    private final AdministratieveHandelingMutatie             administratieveHandelingMutatie             =
        new AdministratieveHandelingMutatie(
            administratieveHandelingId);

    @Test
    public final void testVoerStapUit() {
        final boolean resultaat = markeerAdministratieveHandelingAlsVerwerktStap
            .voerStapUit(administratieveHandelingMutatie, context, administratieveHandelingVerwerkingResultaat);

        assertTrue(resultaat);
        verify(administratieveHandelingVerwerkerRepository)
            .markeerAdministratieveHandelingAlsVerwerkt(administratieveHandelingId);
    }

    @Test(expected = RuntimeException.class)
    public final void testVoerStapUitMetExceptie() {
        doThrow(RuntimeException.class).when(administratieveHandelingVerwerkerRepository)
            .markeerAdministratieveHandelingAlsVerwerkt(administratieveHandelingId);

        markeerAdministratieveHandelingAlsVerwerktStap
            .voerStapUit(administratieveHandelingMutatie, context, administratieveHandelingVerwerkingResultaat);
    }

}
