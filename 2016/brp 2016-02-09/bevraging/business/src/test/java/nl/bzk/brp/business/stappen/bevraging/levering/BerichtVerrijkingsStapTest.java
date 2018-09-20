/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import junit.framework.TestCase;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingSynchronisatiePersoonBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BerichtVerrijkingsStapTest extends TestCase {

    private static final String              ZENDENDE_PARTIJ_CODE      = "01234";
    private static final PartijCodeAttribuut ZENDENDE_PARTIJ_CODE_ATTR = new PartijCodeAttribuut(Integer.parseInt(ZENDENDE_PARTIJ_CODE));

    @InjectMocks
    private final BerichtVerrijkingsStap berichtVerrijkingsStap = new BerichtVerrijkingsStap();

    @Mock
    private LeveringinformatieService leveringinformatieService;

    @Mock
    private ReferentieDataRepository referentieRepository;

    private BevragingResultaat resultaat;

    @Before
    public final void setup() {
        MockitoAnnotations.initMocks(this);
        resultaat = new BevragingResultaat(new ArrayList<Melding>(0));
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public final void testVoerStapUitPartijOnvindbaar() {
        final GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();
        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        bericht.getStuurgegevens().setZendendePartijCode("1234");

        final BevragingBerichtContextBasis context = maakBericht(bericht);

        when(referentieRepository.vindPartijOpCode(new PartijCodeAttribuut(1234)))
                .thenThrow(OnbekendeReferentieExceptie.class);

        final boolean stapResultaat = berichtVerrijkingsStap.voerStapUit(bericht, context, resultaat);

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.BRAL0220, resultaat.getMeldingen().get(0).getRegel());
    }

    @Test
    public final void testVoerStapUitLeveringinformatieServiceGeeftExceptie() {

        final Partij partij = TestPartijBuilder.maker().maak();
        when(referentieRepository.vindPartijOpCode(ZENDENDE_PARTIJ_CODE_ATTR)).thenReturn(partij);

        Mockito.when(leveringinformatieService
            .geefToegangLeveringautorisatie(Mockito.anyInt(),
                    Mockito.anyInt()))
                .thenThrow(OnbekendeReferentieExceptie.class);

        final GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();
        final BevragingBerichtContextBasis context = maakBericht(bericht);
        final BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setZendendePartijCode(ZENDENDE_PARTIJ_CODE);
        bericht.setStuurgegevens(stuurgegevens);
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setLeveringsautorisatieID("12345");
        bericht.setParameters(parameters);


        final boolean stapResultaat = berichtVerrijkingsStap.voerStapUit(bericht,
            context,
            resultaat);

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Regel.BRLV0007, resultaat.getMeldingen().get(0).getRegel());
    }

    protected BevragingBerichtContextBasis maakBericht(final BevragingsBericht bericht) {
        final BerichtZoekcriteriaPersoonGroepBericht criteria = new BerichtZoekcriteriaPersoonGroepBericht();
        bericht.setZoekcriteriaPersoon(criteria);

        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().maak();
        parameters.setLeveringsautorisatie(new LeveringsautorisatieAttribuut(la));
        parameters.setLeveringsautorisatieID(la.getID().toString());
        bericht.setParameters(parameters);

        final AdministratieveHandelingBericht administratieveHandeling = new HandelingSynchronisatiePersoonBericht();
        bericht.getStandaard().setAdministratieveHandeling(administratieveHandeling);

        BevragingBerichtContextBasis berichtContext = new BevragingBerichtContextBasis(new BerichtenIds(null, null), TestPartijBuilder.maker().maak(),
            null, null);
        berichtContext = Mockito.spy(berichtContext);
        berichtContext.setPersoonHisVolledigImpl(TestPersoonJohnnyJordaan.maak());

        return berichtContext;
    }
}
