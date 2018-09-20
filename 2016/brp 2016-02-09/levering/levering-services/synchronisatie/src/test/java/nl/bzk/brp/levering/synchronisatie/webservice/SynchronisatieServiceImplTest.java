/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.webservice;

import java.util.ArrayList;

import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.levering.synchronisatie.service.SynchronisatieBerichtVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.synchronisatie.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractWebService;
import nl.bzk.brp.webservice.basis.service.AbstractWebServiceTest;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test voor webservice.
 */
public class SynchronisatieServiceImplTest extends AbstractWebServiceTest<AbstractSynchronisatieBericht,
        SynchronisatieBerichtContext, SynchronisatieResultaat>
{
    @Mock
    private SynchronisatieBerichtVerwerker synchronisatiePersoonBerichtVerwerker;

    @Test
    public final void testgeefSynchronisatiePersoon() {
        initMocks(GeefSynchronisatiePersoonBericht.class, SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON);
        initBerichtVerwerker(new ArrayList<Melding>(), SynchronisatieResultaat.class, true);

        final GeefSynchronisatiePersoonAntwoordBericht testBericht =
                Mockito.mock(GeefSynchronisatiePersoonAntwoordBericht.class);
        Mockito.when(getAntwoordBerichtFactory().bouwAntwoordBericht(Mockito.any(Bericht.class),
                                                                     Mockito.any(SynchronisatieResultaat.class)))
                .thenReturn(testBericht);

        final GeefSynchronisatiePersoonAntwoordBericht resultaat =
                ((SynchronisatieService) getWebService())
                        .geefSynchronisatiePersoon((GeefSynchronisatiePersoonBericht) getBericht());
        Assert.assertEquals(testBericht, resultaat);
    }

    @Test
    public final void testGeefSynchronisatieStamgegeven() {
        initMocks(GeefSynchronisatieStamgegevenBericht.class, SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN);
        initBerichtVerwerker(new ArrayList<Melding>(), SynchronisatieResultaat.class, true);

        final GeefSynchronisatieStamgegevenAntwoordBericht testBericht =
                Mockito.mock(GeefSynchronisatieStamgegevenAntwoordBericht.class);
        Mockito.when(getAntwoordBerichtFactory().bouwAntwoordBericht(Mockito.any(Bericht.class),
                                                                     Mockito.any(SynchronisatieResultaat.class)))
                .thenReturn(testBericht);

        final GeefSynchronisatieStamgegevenAntwoordBericht resultaat =
                ((SynchronisatieService) getWebService())
                        .geefSynchronisatieStamgegeven((GeefSynchronisatieStamgegevenBericht) getBericht());
        Assert.assertEquals(testBericht, resultaat);
    }

    @Override
    protected AbstractWebService<AbstractSynchronisatieBericht, SynchronisatieBerichtContext,
            SynchronisatieResultaat> getNieuweWebServiceVoorTest()
    {
        final SynchronisatieServiceImpl ws = new SynchronisatieServiceImpl();
        ReflectionTestUtils.setField(ws, "synchronisatiePersoonBerichtVerwerker", getBerichtVerwerker());
        ReflectionTestUtils.setField(ws, "synchronisatieStamgegevenBerichtVerwerker", getBerichtVerwerker());
        return ws;
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return synchronisatiePersoonBerichtVerwerker;
    }
}
