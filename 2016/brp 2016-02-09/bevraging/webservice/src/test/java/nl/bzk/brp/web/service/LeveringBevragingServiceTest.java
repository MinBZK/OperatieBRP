/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht;
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
public class LeveringBevragingServiceTest extends AbstractWebServiceTest<BevragingsBericht, BevragingBerichtContext, BevragingResultaat> {

    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Test
    public final void testOpvragenDetailsPersoon() {
        initMocks(GeefDetailsPersoonBericht.class, SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON);
        initBerichtVerwerker(new ArrayList<Melding>(), BevragingResultaat.class, true);

        final GeefDetailsPersoonAntwoordBericht testBericht = new GeefDetailsPersoonAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BevragingResultaat.class)))
            .thenReturn(testBericht);

        final GeefDetailsPersoonAntwoordBericht resultaat =
            ((LeveringBevragingService) getWebService())
                .geefDetailsPersoon((GeefDetailsPersoonBericht) getBericht());
        assertEquals(testBericht, resultaat);
    }

    @Test
    public final void testZoekPersoon() {
        initMocks(ZoekPersoonBericht.class, SoortBericht.LVG_BVG_ZOEK_PERSOON);
        initBerichtVerwerker(new ArrayList<Melding>(), BevragingResultaat.class, true);

        final ZoekPersoonAntwoordBericht testBericht = new ZoekPersoonAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BevragingResultaat.class)))
            .thenReturn(testBericht);

        final ZoekPersoonAntwoordBericht resultaat =
            ((LeveringBevragingService) getWebService())
                .zoekPersoon((ZoekPersoonBericht) getBericht());
        assertEquals(testBericht, resultaat);
    }

    @Test
    public final void testGetResultaatInstantie() {
        final LeveringBevragingServiceImpl bevragingLeveringenService = new LeveringBevragingServiceImpl();
        final List<Melding> meldingen = new ArrayList<>();

        final BevragingResultaat resultaat = bevragingLeveringenService.getResultaatInstantie(meldingen);

        assertEquals(meldingen, resultaat.getMeldingen());
    }

    @Override
    protected LeveringBevragingServiceImpl getNieuweWebServiceVoorTest() {
        final LeveringBevragingServiceImpl ws = new LeveringBevragingServiceImpl();
        ReflectionTestUtils.setField(ws, "bevragingsBerichtVerwerker", getBerichtVerwerker());
        return ws;
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return berichtVerwerker;
    }
}
