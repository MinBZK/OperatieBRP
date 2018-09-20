/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class VoegStuurgegevensToeStapTest {

    private final LeveringsautorisatieVerwerkingContext context = new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null, null);

    private final LeveringautorisatieVerwerkingResultaat afnemerVerwerkingResultaat = new LeveringautorisatieVerwerkingResultaat();

    @Mock
    private BerichtFactory berichtFactory;

    private LeveringautorisatieStappenOnderwerp onderwerp;

    private MutatieBericht kennisgevingBericht;

    @InjectMocks
    private final VoegStuurgegevensToeStap voegStuurgegevensToeStap = new VoegStuurgegevensToeStap();

    private final Partij geinteresseerdeAfnemer =
        TestPartijBuilder.maker().metNaam("gem").metSoort(SoortPartij.GEMEENTE).metCode(34).maak();

    @Before
    public final void setup() {
        onderwerp = mock(LeveringautorisatieStappenOnderwerp.class);
        kennisgevingBericht = mock(MutatieBericht.class);

        ReflectionTestUtils.setField(geinteresseerdeAfnemer, "iD", (short) 6);

        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metGeautoriseerde(new PartijRol
            (geinteresseerdeAfnemer, Rol.AFNEMER, null, null)).maak();

        final Leveringinformatie leveringAutorisatie = mock(Leveringinformatie.class);
        when(leveringAutorisatie.getToegangLeveringsautorisatie()).thenReturn(toegangLeveringsautorisatie);
        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        when(onderwerp.getLeveringinformatie()).thenReturn(leveringAutorisatie);

        context.setLeveringBerichten(new ArrayList<SynchronisatieBericht>());
        context.getLeveringBerichten().add(kennisgevingBericht);

        final ReferentienummerAttribuut referentienummerAttribuut = new ReferentienummerAttribuut("refNr");

        final BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setReferentienummer(referentienummerAttribuut);
        when(berichtFactory.maakStuurgegevens(any(Partij.class)))
            .thenReturn(stuurgegevens);
        when(berichtFactory.maakParameters(any(Leveringinformatie.class), any(SoortSynchronisatie.class)))
            .thenReturn(new BerichtParametersGroepBericht());

    }

    @Test
    public final void testVoerStapUit() {
        final boolean resultaat =
            voegStuurgegevensToeStap.voerStapUit(onderwerp, context, afnemerVerwerkingResultaat);

        Assert.assertEquals(AbstractStap.DOORGAAN, resultaat);
        verify(kennisgevingBericht).setStuurgegevens(any(BerichtStuurgegevensGroepBericht.class));
    }


    @Test(expected = NullPointerException.class)
    public final void testNullKennisgevingBerichtAanwezig() {
        // Null waarde
        context.setLeveringBerichten(null);

        voegStuurgegevensToeStap.voerStapUit(onderwerp, context, afnemerVerwerkingResultaat);
    }

    @Test
    public final void testGeenKennisgevingBerichtAanwezig() {
        // Lege lijst
        context.setLeveringBerichten(new ArrayList<SynchronisatieBericht>());

        final boolean resultaat =
            voegStuurgegevensToeStap.voerStapUit(onderwerp, context, afnemerVerwerkingResultaat);

        Assert.assertEquals(AbstractStap.DOORGAAN, resultaat);
    }

    @Test
    public final void testVoerStapUitMetNabewerking() {
        voegStuurgegevensToeStap.voerStapUit(onderwerp, context, afnemerVerwerkingResultaat);
        voegStuurgegevensToeStap.voerNabewerkingStapUit(onderwerp, context, afnemerVerwerkingResultaat);

        verify(kennisgevingBericht).setStuurgegevens(null);
    }
}
