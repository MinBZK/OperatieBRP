/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.ArrayList;

import nl.bzk.brp.business.dto.bevraging.AbstractBevragingsBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoordBericht;
import nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test klasse voor de {@link BevragingService}. */
public class BevragingServiceTest extends AbstractWebServiceTest<AbstractBevragingsBericht, OpvragenPersoonResultaat> {

    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Test
    public void testOpvragenDetailPersoon() {
        initMocks(VraagDetailsPersoonBericht.class, SoortBericht.A_L_G_GEEF_DETAILS_PERSOON_V);
        initBerichtVerwerker(new ArrayList<Melding>(), OpvragenPersoonResultaat.class, true);

        VraagDetailsPersoonAntwoordBericht resultaat =
            ((BevragingService) getWebService()).opvragenDetailPersoon((VraagDetailsPersoonBericht) getBericht());
        Assert.assertNull(resultaat.getMeldingen());
        Assert.assertNull(resultaat.getPersoon());
    }

    @Test
    public void testOpvragenPersonenOpAdresInclusiefBetrokkenheden() {
        initMocks(VraagPersonenOpAdresInclusiefBetrokkenhedenBericht.class, SoortBericht.A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI);
        initBerichtVerwerker(new ArrayList<Melding>(), OpvragenPersoonResultaat.class, true);

        VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht resultaat =
            ((BevragingService) getWebService())
                    .opvragenPersonenOpAdresInclusiefBetrokkenheden((VraagPersonenOpAdresInclusiefBetrokkenhedenBericht) getBericht());
        Assert.assertNull(resultaat.getMeldingen());
        Assert.assertNull(resultaat.getPersonen());
    }

    @Override
    protected AbstractWebService<AbstractBevragingsBericht, OpvragenPersoonResultaat> getNieuweWebServiceVoorTest() {
        final BevragingServiceImpl ws = new BevragingServiceImpl();
        ReflectionTestUtils.setField(ws, "bevragingsBerichtVerwerker", getBerichtVerwerker());
        return ws;
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return berichtVerwerker;
    }
}
