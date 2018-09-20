/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.Arrays;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.MutatieLeveringVerantwoordingsinformatieFilter;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.VerantwoordingsinformatieFilter;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.VerantwoordingTestUtil;
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
    private MutatieLeveringVerantwoordingsinformatieFilter mutatieLeveringVerantwoordingsinformatieFilter;

    private final LeveringsautorisatieVerwerkingContext  context   = new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null, null);
    private final LeveringautorisatieVerwerkingResultaat resultaat = new LeveringautorisatieVerwerkingResultaat();
    private final LeveringautorisatieStappenOnderwerp    onderwerp = new LeveringautorisatieStappenOnderwerpImpl(null, null, null);

    @Before
    public final void init() {
        final AdministratieveHandelingModel admhnd = VerantwoordingTestUtil.bouwAdministratieveHandeling(
            SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND,
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM, new OntleningstoelichtingAttribuut("tl"), DatumTijdAttribuut.nu());
        final AdministratieveHandelingSynchronisatie administratieveHandelingSynchronisatie = new AdministratieveHandelingSynchronisatie(admhnd);
        final SynchronisatieBericht volledigbericht = new VolledigBericht(null);
        final SynchronisatieBericht mutatieBericht = new MutatieBericht(null);

        context.setLeveringBerichten(Arrays.asList(volledigbericht, mutatieBericht));

        volledigbericht.setAdministratieveHandeling(administratieveHandelingSynchronisatie);
        mutatieBericht.setAdministratieveHandeling(administratieveHandelingSynchronisatie);

        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        administratieveHandelingSynchronisatie.setBijgehoudenPersonen(Arrays.asList(persoonHisVolledigView));
    }

    @Test
    public final void testVoerStapUit() {
        final boolean stapResultaat = stap.voerStapUit(onderwerp, context, resultaat);

        Assert.assertTrue(stapResultaat);
        Mockito.verify(mutatieLeveringVerantwoordingsinformatieFilter, Mockito.times(1)).filter(Mockito.any(PersoonHisVolledigView.class),
            Mockito.anyLong(), Mockito.any(Leveringinformatie.class));
        Mockito.verify(verantwoordingsinformatieFilter, Mockito.times(1))
            .filter(Mockito.any(PersoonHisVolledigView.class), Mockito.any(Leveringinformatie.class));
    }
}
