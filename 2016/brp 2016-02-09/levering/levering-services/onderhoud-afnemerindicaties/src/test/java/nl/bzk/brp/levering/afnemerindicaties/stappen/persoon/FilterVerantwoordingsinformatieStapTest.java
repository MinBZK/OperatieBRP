/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.VerantwoordingsinformatieFilter;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FilterVerantwoordingsinformatieStapTest {

    @InjectMocks
    private final FilterVerantwoordingsinformatieStap stap = new FilterVerantwoordingsinformatieStap();

    @Mock
    private VerantwoordingsinformatieFilter verantwoordingsinformatieFilter;

    @Mock
    private OnderhoudAfnemerindicatiesBerichtContext context;

    @Mock
    private RegistreerAfnemerindicatieBericht registreerAfnemerindicatieBericht;

    @Mock
    private VolledigBericht volledigBericht;

    @Mock
    private AdministratieveHandelingSynchronisatie administratieveHandeling;

    private final OnderhoudAfnemerindicatiesResultaat resultaat = new OnderhoudAfnemerindicatiesResultaat(new ArrayList<Melding>());

    private final PersoonHisVolledigView persoon             = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
    private final Leveringinformatie    leveringAutorisatie = new Leveringinformatie(null, null);

    @Before
    public final void init() {
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();

        bijgehoudenPersonen.add(persoon);

        Mockito.when(context.getVolledigBericht()).thenReturn(volledigBericht);
        Mockito.when(volledigBericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        Mockito.when(administratieveHandeling.getBijgehoudenPersonen()).thenReturn(bijgehoudenPersonen);
        Mockito.when(context.getLeveringinformatie()).thenReturn(leveringAutorisatie);

    }

    @Test
    public final void testVoerStapUit() {
        final boolean stapResultaat = stap.voerStapUit(registreerAfnemerindicatieBericht, context, resultaat);

        Assert.assertTrue(stapResultaat);
        verify(verantwoordingsinformatieFilter).filter(persoon, leveringAutorisatie);
    }
}
