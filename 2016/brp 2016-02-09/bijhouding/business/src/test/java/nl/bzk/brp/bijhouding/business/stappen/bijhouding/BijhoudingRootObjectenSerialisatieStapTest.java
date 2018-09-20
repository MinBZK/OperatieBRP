/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BijhoudingRootObjectenSerialisatieStapTest {

    @InjectMocks
    private BijhoudingRootObjectenSerialisatieStap stap;

    @Mock
    private BlobifierService blobifierService;

    private BijhoudingsBericht       bijhoudingsBericht;
    private BijhoudingBerichtContext berichtContext;
    private PersoonHisVolledigImpl   persoon;

    @Before
    public void setUp() {
        bijhoudingsBericht = new RegistreerNationaliteitBericht();

        berichtContext = Mockito.mock(BijhoudingBerichtContext.class);
        final List<PersoonHisVolledigImpl> bijgehoudenPersonen = new ArrayList<>();
        persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        bijgehoudenPersonen.add(persoon);
        when(berichtContext.getBijgehoudenPersonen(true)).thenReturn(bijgehoudenPersonen);
    }

    @Test
    public void testVoerStapUit() {
        stap.voerStapUit(bijhoudingsBericht, berichtContext);
        verify(blobifierService).blobify(persoon, false);
    }

    @Test
    public void testVoerStapUitIndienGeenBijgehoudenPersonen() {
        berichtContext = Mockito.mock(BijhoudingBerichtContext.class);
        stap.voerStapUit(bijhoudingsBericht, berichtContext);
        verify(blobifierService, never()).blobify(persoon, false);
    }

}
