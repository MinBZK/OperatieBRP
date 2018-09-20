/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import nl.bzk.brp.levering.afnemerindicaties.stappen.AbstractStappenTest;
import nl.bzk.brp.levering.business.toegang.voorkomenfilter.VoorkomenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class VoorkomenFilterStapTest extends AbstractStappenTest {

    @InjectMocks
    private final VoorkomenFilterStap voorkomenFilterStap = new VoorkomenFilterStap();

    @Mock
    private VoorkomenFilterService voorkomenFilterService;

    @Override
    @Before
    public final void setUp() {
        super.setUp();
        maakBericht(987654321, maakDummyLeveringinformatie(), 321, "ZendendeSysteem",
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, new DatumEvtDeelsOnbekendAttribuut(20130101));
        final Dienst dienst = TestDienstBuilder.maker().maak();
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(dienst).maak();

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).metDienstbundels(dienstbundel).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, null);
        getBerichtContext().setLeveringinformatie(leveringAutorisatie);
    }

    @Test
    public final void testVoerStapUit() throws ExpressieExceptie {
        // arrange
        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandelingModel();

        final List<PersoonHisVolledigView> personen = Collections.singletonList(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(),
            HistorieVanafPredikaat
                .geldigOpEnNa(DatumAttribuut.vandaag())));
        final VolledigBericht volledigBericht = new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));
        volledigBericht.getAdministratieveHandeling().setBijgehoudenPersonen(personen);

        getBerichtContext().setVolledigBericht(volledigBericht);

        // act
        final boolean stapResultaat =
            voorkomenFilterStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(true));
        verify(voorkomenFilterService).voerVoorkomenFilterUit(any(PersoonHisVolledigView.class), any(Dienst.class));
    }

    @Test
    public final void testVoerStapUitMetFout() throws ExpressieExceptie {
        // arrange
        doThrow(ExpressieExceptie.class).when(voorkomenFilterService).voerVoorkomenFilterUit(any(PersoonHisVolledigView.class), any(Dienst.class));

        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandelingModel();

        final List<PersoonHisVolledigView> personen = Collections.singletonList(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(),
            HistorieVanafPredikaat
                .geldigOpEnNa(DatumAttribuut.vandaag())));
        final VolledigBericht volledigBericht = new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));
        volledigBericht.getAdministratieveHandeling().setBijgehoudenPersonen(personen);

        getBerichtContext().setVolledigBericht(volledigBericht);

        // act
        final boolean stapResultaat = voorkomenFilterStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());
        Assert.assertFalse(stapResultaat);
    }
}
