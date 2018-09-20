/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractWebServiceTest;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test klasse voor de {@link BijhoudingBevragingService}.
 */
public class BijhoudingBevragingServiceTest extends AbstractWebServiceTest<BevragingsBericht, BevragingBerichtContext, BevragingResultaat> {

    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Test
    public final void testGeefDetailPersoon() {
        initMocks(GeefDetailsPersoonBericht.class, SoortBericht.BHG_BVG_GEEF_DETAILS_PERSOON);
        initBerichtVerwerker(new ArrayList<Melding>(), BevragingResultaat.class, true);

        final GeefDetailsPersoonAntwoordBericht testBericht = mock(GeefDetailsPersoonAntwoordBericht.class);
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BevragingResultaat.class)))
            .thenReturn(testBericht);

        final GeefDetailsPersoonAntwoordBericht resultaat =
            ((BijhoudingBevragingService) getWebService()).geefDetailsPersoon((GeefDetailsPersoonBericht) getBericht());
        assertEquals(testBericht, resultaat);
    }

    @Test
    public final void testGeefPersonenOpAdresMetBetrokkenheden() {
        initMocks(GeefPersonenOpAdresMetBetrokkenhedenBericht.class,
            SoortBericht.BHG_BVG_GEEF_PERSONEN_OP_ADRES_MET_BETROKKENHEDEN);
        initBerichtVerwerker(new ArrayList<Melding>(), BevragingResultaat.class, true);

        final GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht testBericht =
            mock(GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht.class);
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BevragingResultaat.class)))
            .thenReturn(testBericht);

        final GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht resultaat =
                ((BijhoudingBevragingService) getWebService())
                        .geefPersonenOpAdresMetBetrokkenheden(
                                (GeefPersonenOpAdresMetBetrokkenhedenBericht) getBericht());
        assertEquals(testBericht, resultaat);
    }

    @Test
    public final void testGetResultaatInstantie() {
        final BijhoudingBevragingServiceImpl bevragingService = new BijhoudingBevragingServiceImpl();
        final List<Melding> meldingen = new ArrayList<>();

        final BevragingResultaat resultaat = bevragingService.getResultaatInstantie(meldingen);

        assertEquals(meldingen, resultaat.getMeldingen());
    }

    @Override
    protected BijhoudingBevragingServiceImpl getNieuweWebServiceVoorTest() {
        final BijhoudingBevragingServiceImpl ws = new BijhoudingBevragingServiceImpl();
        ReflectionTestUtils.setField(ws, "bevragingsBerichtVerwerker", getBerichtVerwerker());
        return ws;
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return berichtVerwerker;
    }
}
