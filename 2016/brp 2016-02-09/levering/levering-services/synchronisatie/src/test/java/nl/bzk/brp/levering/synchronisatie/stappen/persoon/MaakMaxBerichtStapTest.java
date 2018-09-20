/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.business.bericht.BerichtFactoryImpl;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.stappen.AbstractStappenTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
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

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = TestLeveringsautorisatieBuilder.maker().maak();
    private static final String               AGV                  = "AGV";

    @InjectMocks
    private MaakMaxBerichtStap maakMaxBerichtStap = new MaakMaxBerichtStap();

    @Mock
    private BerichtFactory berichtFactory = new BerichtFactoryImpl();

    @Test
    public final void eenBerichtWordtGoedGemaakt() {
        // arrange
        maakBericht(123550394, LEVERINGSAUTORISATIE, 123, AGV);

        final Leveringinformatie leveringAutorisatie = Mockito.mock(Leveringinformatie.class);
        getBerichtContext().setLeveringinformatie(leveringAutorisatie);

        final PersoonHisVolledig persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        getBerichtContext().setPersoonHisVolledig(persoon);
        getBerichtContext().setLeveringinformatie(leveringAutorisatie);

        final VolledigBericht mockVolledigBericht = mock(VolledigBericht.class);
        when(berichtFactory.maakVolledigBericht(eq(persoon), eq(leveringAutorisatie),
            any(AdministratieveHandelingModel.class), any(DatumAttribuut.class))).thenReturn(mockVolledigBericht);

        // act
        final boolean stapResultaat = maakMaxBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(),
            getResultaat());

        // assert
        assertThat(stapResultaat, is(true));
        assertThat(getBerichtContext().getVolledigBericht(), equalTo(mockVolledigBericht));
    }

    @Test
    public final void eenBerichtWordtNietGoedGemaakt() {
        // arrange
        maakBericht(123550394, LEVERINGSAUTORISATIE, 123, AGV);

        final Leveringinformatie leveringAutorisatie = Mockito.mock(Leveringinformatie.class);
        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.GEEF_DETAILS_PERSOON);

        final PersoonHisVolledig persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        getBerichtContext().setPersoonHisVolledig(persoon);
        getBerichtContext().setLeveringinformatie(leveringAutorisatie);

        when(berichtFactory.maakVolledigBericht(eq(persoon), eq(leveringAutorisatie),
                                                any(AdministratieveHandelingModel.class), any(DatumAttribuut.class))).thenReturn(null);

        // act
        final boolean stapResultaat = maakMaxBerichtStap.voerStapUit(getOnderwerp(), getBerichtContext(),
            getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getBerichtContext().getVolledigBericht(), nullValue());
    }
}
