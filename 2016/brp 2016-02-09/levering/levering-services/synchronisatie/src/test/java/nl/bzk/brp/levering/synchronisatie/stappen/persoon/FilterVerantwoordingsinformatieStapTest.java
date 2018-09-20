/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.VerantwoordingsinformatieFilter;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FilterVerantwoordingsinformatieStapTest {

    @InjectMocks
    private final FilterVerantwoordingsinformatieStap stap = new FilterVerantwoordingsinformatieStap();

    @Mock
    private VerantwoordingsinformatieFilter verantwoordingsinformatieFilter;

    @Mock
    private SynchronisatieBerichtContext context;

    @Mock
    private VolledigBericht volledigBericht;

    @Mock
    private AdministratieveHandelingSynchronisatie administratieveHandeling;

    private final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(new ArrayList<Melding>());

    private final PersoonHisVolledigView persoon = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);

    @Before
    public final void init() {
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();
        bijgehoudenPersonen.add(persoon);

        Mockito.when(context.getVolledigBericht()).thenReturn(volledigBericht);
        Mockito.when(volledigBericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        Mockito.when(administratieveHandeling.getBijgehoudenPersonen()).thenReturn(bijgehoudenPersonen);
    }

    @Test
    public final void testVoerStapUit() {
        final boolean stapResultaat = stap.voerStapUit(null, context, resultaat);

        Assert.assertTrue(stapResultaat);
        verify(verantwoordingsinformatieFilter).filter(persoon, null);
    }
}
