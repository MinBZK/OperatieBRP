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
import nl.bzk.brp.business.dto.bijhouding.HuwelijkBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.RegistratieOverlijdenBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingInschrijvingDoorGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieIntergemeentelijkeVerhuizingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieOverlijdenNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingSluitingHuwelijkNederlandBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.bijhouding.CorrectieAdresNLAntwoordBericht;
import nl.bzk.brp.web.bijhouding.HuwelijkAntwoordBericht;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import nl.bzk.brp.web.bijhouding.RegistratieOverlijdenAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de {@link nl.bzk.brp.web.service.BijhoudingService} class. */
public class BijhoudingServiceTest extends AbstractWebServiceTest<AbstractBijhoudingsBericht, BijhoudingResultaat> {

    public static final String TECHNISCHE_SLEUTEL = "mijnTechnischeSleutel";
    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Test
    public void testInschrijvingGeboorte() {
        initMocks(InschrijvingGeboorteBericht.class, SoortBericht.A_F_S_REGISTREER_GEBOORTE_B);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling = new HandelingInschrijvingDoorGeboorteBericht();
        adminstratieveHandeling.setTechnischeSleutel(TECHNISCHE_SLEUTEL);

        when(getBericht().getAdministratieveHandeling()).thenReturn(adminstratieveHandeling);
        InschrijvingGeboorteAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).inschrijvingGeboorte((InschrijvingGeboorteBericht) getBericht());

        assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD, resultaat.getResultaat().getVerwerking());
        assertEquals(TECHNISCHE_SLEUTEL, resultaat.getAdministratieveHandeling().getTechnischeSleutel());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testVerhuizing() {
        initMocks(VerhuizingBericht.class, SoortBericht.M_I_G_REGISTREER_VERHUIZING_B);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling =
            new HandelingRegistratieIntergemeentelijkeVerhuizingBericht();
        when(getBericht().getAdministratieveHandeling()).thenReturn(adminstratieveHandeling);

        VerhuizingAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).verhuizing((VerhuizingBericht) getBericht());

        assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD, resultaat.getResultaat().getVerwerking());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testCorrectieAdresNL() {
        initMocks(CorrectieAdresBericht.class, SoortBericht.M_I_G_CORRIGEER_ADRES_B);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling = new HandelingCorrectieAdresNederlandBericht();
        when(getBericht().getAdministratieveHandeling()).thenReturn(adminstratieveHandeling);

        CorrectieAdresNLAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).correctieAdresNL((CorrectieAdresBericht) getBericht());

        assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD, resultaat.getResultaat().getVerwerking());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testRegistreerHuwelijkEnPartnerschap() {
        initMocks(HuwelijkBericht.class, SoortBericht.H_G_P_REGISTREER_HUWELIJK_B);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling =
            new HandelingSluitingHuwelijkNederlandBericht();
        when(getBericht().getAdministratieveHandeling()).thenReturn(adminstratieveHandeling);
        HuwelijkAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerHuwelijkEnPartnerschap((HuwelijkBericht) getBericht());

        assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD, resultaat.getResultaat().getVerwerking());
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testRegistreerOverlijden() {
        initMocks(RegistratieOverlijdenBericht.class, SoortBericht.O_V_L_REGISTREER_OVERLIJDEN_B);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling =
            new HandelingRegistratieOverlijdenNederlandBericht();
        when(getBericht().getAdministratieveHandeling()).thenReturn(adminstratieveHandeling);

        RegistratieOverlijdenAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerOverlijden((RegistratieOverlijdenBericht) getBericht());

        assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD, resultaat.getResultaat().getVerwerking());
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
