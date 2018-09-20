/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.AttributenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.stappen.AbstractStappenTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class AttributenFilterStapTest extends AbstractStappenTest {

    @InjectMocks
    private final AttributenFilterStap attributenFilterStap = new AttributenFilterStap();

    @Mock
    private AttributenFilterService attributenFilterService;

    @Override
    @Before
    public final void setUp() {
        super.setUp();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        maakBericht(987654321, la, 321, "ZendendeSysteem");
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().
            metDummyGeautoriseerde().metLeveringsautorisatie(la).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, la.geefDiensten().iterator().next());
        getBerichtContext().setLeveringinformatie(leveringinformatie);
    }

    @Test
    public final void testVoerStapUit() throws ExpressieExceptie {
        // arrange
        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandelingModel();

        final List<PersoonHisVolledigView> personen =
            Collections.singletonList(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(),
                                                                 HistorieVanafPredikaat.geldigOpEnNa(DatumAttribuut.vandaag())));
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));
        volledigBericht.getAdministratieveHandeling().setBijgehoudenPersonen(personen);

        getBerichtContext().setVolledigBericht(volledigBericht);

        // act
        final boolean stapResultaat =
            attributenFilterStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(true));
        verify(attributenFilterService).zetMagGeleverdWordenVlaggen(any(PersoonHisVolledigView.class), any(Dienst.class), any(Rol.class));
    }

    @Test
    public final void testVoerStapUitMetFout() throws ExpressieExceptie {
        // arrange
        when(attributenFilterService.zetMagGeleverdWordenVlaggen(any(PersoonHisVolledigView.class), any(Dienst.class), any(Rol.class)))
            .thenThrow(new ExpressieExceptie("Test"));

        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandelingModel();

        final List<PersoonHisVolledigView> personen =
            Collections.singletonList(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(),
                                                                 HistorieVanafPredikaat.geldigOpEnNa(DatumAttribuut.vandaag())));
        final VolledigBericht volledigBericht = new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));
        volledigBericht.getAdministratieveHandeling().setBijgehoudenPersonen(personen);

        getBerichtContext().setVolledigBericht(volledigBericht);

        // act
        final boolean stapResultaat = attributenFilterStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
        Assert.assertFalse(stapResultaat);
    }
}
