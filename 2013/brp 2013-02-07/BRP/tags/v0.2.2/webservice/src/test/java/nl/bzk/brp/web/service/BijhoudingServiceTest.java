/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.ArrayList;

import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verwerkingsresultaat;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.bijhouding.CorrectieAdresNLAntwoordBericht;
import nl.bzk.brp.web.bijhouding.HuwelijkEnGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de {@link nl.bzk.brp.web.service.BijhoudingService} class. */
public class BijhoudingServiceTest extends AbstractWebServiceTest<AbstractBijhoudingsBericht, BijhoudingResultaat> {

    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Test
    public void testInschrijvingGeboorte() {
        initMocks(InschrijvingGeboorteBericht.class, Soortbericht.AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        InschrijvingGeboorteAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).inschrijvingGeboorte((InschrijvingGeboorteBericht) getBericht());

        Assert.assertEquals(Verwerkingsresultaat.GOED, resultaat.getBerichtResultaatGroep().getVerwerkingsresultaat());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testVerhuizing() {
        initMocks(VerhuizingBericht.class, Soortbericht.MIGRATIE_VERHUIZING_BIJHOUDING);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        VerhuizingAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).verhuizing((VerhuizingBericht) getBericht());

        Assert.assertEquals(Verwerkingsresultaat.GOED, resultaat.getBerichtResultaatGroep().getVerwerkingsresultaat());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testCorrectieAdresNL() {
        initMocks(CorrectieAdresBericht.class, Soortbericht.MIGRATIE_CORRECTIEADRESBINNENNL_BIJHOUDING);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        CorrectieAdresNLAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).correctieAdresNL((CorrectieAdresBericht) getBericht());

        Assert.assertEquals(Verwerkingsresultaat.GOED, resultaat.getBerichtResultaatGroep().getVerwerkingsresultaat());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testRegistreerHuwelijkEnPartnerschap() {
        initMocks(HuwelijkEnGeregistreerdPartnerschapBericht.class, Soortbericht.HUWELIJKPARTNERSCHAP_REGISTRATIEHUWELIJK_BIJHOUDING);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        HuwelijkEnGeregistreerdPartnerschapAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerHuwelijkEnPartnerschap((HuwelijkEnGeregistreerdPartnerschapBericht) getBericht());

        Assert.assertEquals(Verwerkingsresultaat.GOED, resultaat.getBerichtResultaatGroep().getVerwerkingsresultaat());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Override
    protected AbstractWebService<AbstractBijhoudingsBericht, BijhoudingResultaat> getNieuweWebServiceVoorTest() {
        BijhoudingServiceImpl bijhoudingService = new BijhoudingServiceImpl();
        ReflectionTestUtils.setField(bijhoudingService, "bijhoudingsBerichtVerwerker", getBerichtVerwerker());
        return bijhoudingService;
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return berichtVerwerker;
    }
}
