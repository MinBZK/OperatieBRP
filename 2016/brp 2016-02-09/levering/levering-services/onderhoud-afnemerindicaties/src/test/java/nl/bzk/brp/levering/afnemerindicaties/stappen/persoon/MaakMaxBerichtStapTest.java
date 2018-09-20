/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.levering.afnemerindicaties.stappen.AbstractStappenTest;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.business.bericht.BerichtFactoryImpl;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MaakMaxBerichtStapTest extends AbstractStappenTest {

    private static final int    BSN                   = 123550394;
    private static final int    ZENDENDE_PARTIJ_ID    = 123;
    private static final String ZENDENDE_SYSTEEM_NAAM = "AGV";

    @InjectMocks
    private MaakMaxBerichtStap maakMaxBerichtStap = new MaakMaxBerichtStap();

    @Mock
    private BerichtFactory berichtFactory = new BerichtFactoryImpl();

    @Test
    public final void eenBerichtWordtGoedGemaakt() {
        // arrange
        maakBericht(BSN, maakDummyLeveringinformatie(), ZENDENDE_PARTIJ_ID, ZENDENDE_SYSTEEM_NAAM,
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));

        final Leveringinformatie leveringAutorisatie = Mockito.mock(Leveringinformatie.class);
        getBerichtContext().setLeveringinformatie(leveringAutorisatie);

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.GEEF_DETAILS_PERSOON);

        final PersoonHisVolledig persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        getBerichtContext().setPersoonHisVolledig(persoon);
        getBerichtContext().setLeveringinformatie(leveringAutorisatie);

        final VolledigBericht mockVolledigBericht = mock(VolledigBericht.class);
        when(berichtFactory.maakVolledigBericht(eq(persoon), eq(leveringAutorisatie),
            any(AdministratieveHandelingModel.class),
            any(DatumAttribuut.class))).thenReturn(mockVolledigBericht);

        // act
        final boolean stapResultaat =
            maakMaxBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(true));
        assertThat(getBerichtContext().getVolledigBericht(), equalTo(mockVolledigBericht));
    }

    @Test
    public final void eenBerichtWordtNietGemaaktVoorVerwijderenIndicatie() {
        // arrange
        maakBericht(BSN, maakDummyLeveringinformatie(), ZENDENDE_PARTIJ_ID, ZENDENDE_SYSTEEM_NAAM,
            SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));

        // act
        final boolean stapResultaat =
            maakMaxBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getBerichtContext().getVolledigBericht(), nullValue());
    }

    @Test
    public final void eenBerichtWordtNietGoedGemaakt() {
        // arrange
        maakBericht(BSN, maakDummyLeveringinformatie(), ZENDENDE_PARTIJ_ID, ZENDENDE_SYSTEEM_NAAM,
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));

        final Leveringinformatie leveringAutorisatie = Mockito.mock(Leveringinformatie.class);
        getBerichtContext().setLeveringinformatie(leveringAutorisatie);
        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.GEEF_DETAILS_PERSOON);

        final PersoonHisVolledig persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        getBerichtContext().setPersoonHisVolledig(persoon);

        when(berichtFactory.maakVolledigBericht(eq(persoon), eq(leveringAutorisatie),
            any(AdministratieveHandelingModel.class), any(DatumAttribuut.class)))
            .thenReturn(null);

        // act
        final boolean stapResultaat =
            maakMaxBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getBerichtContext().getVolledigBericht(), nullValue());
    }

    @Test
    public final void eenBerichtZonderDatAanvMatPeriode() {
        // arrange
        maakBericht(BSN, maakDummyLeveringinformatie(), ZENDENDE_PARTIJ_ID, ZENDENDE_SYSTEEM_NAAM,
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            null);

        final Leveringinformatie leveringAutorisatie = Mockito.mock(Leveringinformatie.class);
        getBerichtContext().setLeveringinformatie(leveringAutorisatie);
        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.GEEF_DETAILS_PERSOON);

        final PersoonHisVolledig persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        getBerichtContext().setPersoonHisVolledig(persoon);

        final VolledigBericht mockVolledigBericht = mock(VolledigBericht.class);
        when(berichtFactory.maakVolledigBericht(eq(persoon), eq(leveringAutorisatie),
            any(AdministratieveHandelingModel.class),
            any(DatumAttribuut.class))).thenReturn(mockVolledigBericht);

        // act
        final boolean stapResultaat =
            maakMaxBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(true));
        assertThat(getBerichtContext().getVolledigBericht(), equalTo(mockVolledigBericht));
    }
}
