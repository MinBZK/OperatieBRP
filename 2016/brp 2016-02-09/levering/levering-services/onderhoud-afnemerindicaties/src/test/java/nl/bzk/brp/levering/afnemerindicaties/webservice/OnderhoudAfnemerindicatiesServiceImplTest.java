/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.webservice;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtVerwerker;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.HandelingPlaatsingAfnemerindicatieBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractWebService;
import nl.bzk.brp.webservice.basis.service.AbstractWebServiceTest;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test voor de webservice.
 */
@Ignore
public class OnderhoudAfnemerindicatiesServiceImplTest extends AbstractWebServiceTest<RegistreerAfnemerindicatieBericht,
    OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    @Mock
    private OnderhoudAfnemerindicatiesBerichtVerwerker onderhoudAfnemerindicatiesBerichtVerwerker;

    @Test
    public final void testRegistreerAfnemerindicatie() {
        initMocks(RegistreerAfnemerindicatieBericht.class, SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE);
        initBerichtVerwerker(new ArrayList<Melding>(), OnderhoudAfnemerindicatiesResultaat.class, true);

        final RegistreerAfnemerindicatieAntwoordBericht testBericht = Mockito.mock(RegistreerAfnemerindicatieAntwoordBericht.class);
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(Mockito.any(Bericht.class), Mockito.any(OnderhoudAfnemerindicatiesResultaat.class)))
            .thenReturn(testBericht);

        final RegistreerAfnemerindicatieBericht bericht = (RegistreerAfnemerindicatieBericht) getBericht();
        final HandelingPlaatsingAfnemerindicatieBericht administratieveHandeling = new HandelingPlaatsingAfnemerindicatieBericht();
        administratieveHandeling.setActies(Arrays.asList(new ActieBericht[]{new ActieRegistratieAfnemerindicatieBericht()}));
        getBericht().getStandaard().setAdministratieveHandeling(administratieveHandeling);

        final RegistreerAfnemerindicatieAntwoordBericht resultaat =
            ((OnderhoudAfnemerindicatiesService) getWebService()).registreerAfnemerindicatie(bericht);
        Assert.assertEquals(testBericht, resultaat);
    }

    @Test
    public final void testRegistreerAfnemerindicatieMetOnverwachteSoortBericht() {
        initMocks(RegistreerAfnemerindicatieBericht.class, SoortBericht.DUMMY);
        initBerichtVerwerker(new ArrayList<Melding>(), OnderhoudAfnemerindicatiesResultaat.class, true);

        final RegistreerAfnemerindicatieAntwoordBericht testBericht =
            Mockito.mock(RegistreerAfnemerindicatieAntwoordBericht.class);
        when(getAntwoordBerichtFactory()
            .bouwAntwoordBericht(Mockito.any(Bericht.class),
                Mockito.any(OnderhoudAfnemerindicatiesResultaat.class)))
            .thenReturn(testBericht);

        final RegistreerAfnemerindicatieBericht bericht = (RegistreerAfnemerindicatieBericht) getBericht();
        final HandelingPlaatsingAfnemerindicatieBericht administratieveHandeling =
            new HandelingPlaatsingAfnemerindicatieBericht();
        administratieveHandeling
            .setActies(Arrays.asList(new ActieBericht[]{ new ActieRegistratieAfnemerindicatieBericht() }));
        getBericht().getStandaard().setAdministratieveHandeling(administratieveHandeling);

        final RegistreerAfnemerindicatieAntwoordBericht resultaat =
            ((OnderhoudAfnemerindicatiesService) getWebService())
                .registreerAfnemerindicatie(bericht);
        Assert.assertEquals(testBericht, resultaat);
    }

    @Override
    protected AbstractWebService<RegistreerAfnemerindicatieBericht, OnderhoudAfnemerindicatiesBerichtContext,
        OnderhoudAfnemerindicatiesResultaat> getNieuweWebServiceVoorTest()
    {
        final OnderhoudAfnemerindicatiesServiceImpl ws = new OnderhoudAfnemerindicatiesServiceImpl();
        ReflectionTestUtils.setField(ws, "onderhoudAfnemerindicatiesBerichtVerwerker", getBerichtVerwerker());
        return ws;
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return onderhoudAfnemerindicatiesBerichtVerwerker;
    }

}
