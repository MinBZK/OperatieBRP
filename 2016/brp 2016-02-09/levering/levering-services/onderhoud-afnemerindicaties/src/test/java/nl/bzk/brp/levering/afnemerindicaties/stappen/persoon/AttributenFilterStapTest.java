/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.levering.afnemerindicaties.stappen.AbstractStappenTest;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.AttributenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class AttributenFilterStapTest extends AbstractStappenTest {

    @InjectMocks
    private final AttributenFilterStap attributenFilterStap = new AttributenFilterStap();

    @Mock
    private AttributenFilterService attributenFilterService;

    private final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(123).maak();

    @Override
    @Before
    public final void setUp() {
        super.setUp();

        final Partij partij = TestPartijBuilder.maker().metId(321).maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().
                metLeveringsautorisatie(leveringsautorisatie).metDummyGeautoriseerde().maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, null);

        maakBericht(987654321, leveringinformatie, partij.getID().intValue(), "ZendendeSysteem",
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, new DatumEvtDeelsOnbekendAttribuut(20130101));


        getBerichtContext().setLeveringinformatie(leveringinformatie);

        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandelingModel();
        final List<PersoonHisVolledigView> personen = Collections.singletonList(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(),
            HistorieVanafPredikaat
                .geldigOpEnNa(DatumAttribuut.vandaag())));
        final VolledigBericht volledigBericht = new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));
        volledigBericht.getAdministratieveHandeling().setBijgehoudenPersonen(personen);

        getBerichtContext().setVolledigBericht(volledigBericht);
    }

    @Test
    public final void testVoerStapUit() throws ExpressieExceptie {
        final boolean stapResultaat = attributenFilterStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
        assertTrue(stapResultaat);
        verify(attributenFilterService).zetMagGeleverdWordenVlaggen(any(PersoonHisVolledigView.class), any(Dienst.class), any(Rol.class));
    }

    @Test
    public final void testVoerStapUitMetExpressieFout() throws ExpressieExceptie {
        Mockito.when(attributenFilterService.zetMagGeleverdWordenVlaggen(any(PersoonHisVolledigView.class), any(Dienst.class),
            any(Rol.class))).thenThrow(ExpressieExceptie.class);

        final boolean stapResultaat = attributenFilterStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        assertFalse(stapResultaat);
    }

}
