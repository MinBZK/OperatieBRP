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
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.service.BijhoudingsBerichtVerwerker;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningNaGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGBAGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGBASluitingHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOverlijdenInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingIntergemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerkrijgingNederlandseNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerkrijgingReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVoltrekkingHuwelijkInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingWijzigingGeslachtsnaamBericht;
import nl.bzk.brp.model.bericht.kern.HandelingWijzigingGezagBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresAntwoordBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresBericht;
import nl.bzk.brp.model.bijhouding.RegistreerErkenningAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerErkenningBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAGeboorteAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerMededelingVerzoekAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerMededelingVerzoekBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNaamGeslachtAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNaamGeslachtBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNationaliteitAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNationaliteitBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenBericht;
import nl.bzk.brp.model.bijhouding.RegistreerReisdocumentAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerReisdocumentBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.basis.service.AbstractWebService;
import nl.bzk.brp.webservice.basis.service.AbstractWebServiceTest;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link nl.bzk.brp.web.service.BijhoudingService} class.
 */
public class BijhoudingServiceTest extends AbstractWebServiceTest<BijhoudingsBericht, BijhoudingBerichtContext, BijhoudingResultaat> {

    public static final String OBJECT_SLEUTEL = "mijnObjectSleutel";

    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Test
    public void testInschrijvingGeboorte() {
        initMocks(RegistreerGeboorteBericht.class, SoortBericht.BHG_AFS_REGISTREER_GEBOORTE);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final RegistreerGeboorteAntwoordBericht testBericht = new RegistreerGeboorteAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        final AdministratieveHandelingBericht adminstratieveHandeling = new HandelingGeboorteInNederlandBericht();
        adminstratieveHandeling.setObjectSleutel(OBJECT_SLEUTEL);

        voegAdministratieveHandelingToeAanBericht(adminstratieveHandeling);
        RegistreerGeboorteAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerGeboorte((RegistreerGeboorteBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testRegistreerGBAGeboorte() {
        initMocks(RegistreerGBAGeboorteBericht.class, SoortBericht.ISC_MIG_REGISTREER_GEBOORTE);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final RegistreerGBAGeboorteAntwoordBericht testBericht = new RegistreerGBAGeboorteAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        final AdministratieveHandelingBericht adminstratieveHandeling = new HandelingGBAGeboorteBericht();
        adminstratieveHandeling.setObjectSleutel(OBJECT_SLEUTEL);

        voegAdministratieveHandelingToeAanBericht(adminstratieveHandeling);
        RegistreerGBAGeboorteAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerGBAGeboorte((RegistreerGBAGeboorteBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testRegistreerGBAHuwelijkGeregistreerdPartnerschap() {
        initMocks(RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht.class, SoortBericht.ISC_MIG_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht
            testBericht = new RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        final AdministratieveHandelingBericht adminstratieveHandeling = new HandelingGBASluitingHuwelijkGeregistreerdPartnerschapBericht();
        adminstratieveHandeling.setObjectSleutel(OBJECT_SLEUTEL);

        voegAdministratieveHandelingToeAanBericht(adminstratieveHandeling);
        RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerGBAHuwelijkGeregistreerdPartnerschap(
                (RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testVerhuizing() {
        initMocks(RegistreerVerhuizingBericht.class, SoortBericht.BHG_VBA_REGISTREER_VERHUIZING);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling =
            new HandelingVerhuizingIntergemeentelijkBericht();
        voegAdministratieveHandelingToeAanBericht(adminstratieveHandeling);

        final RegistreerVerhuizingAntwoordBericht testBericht = new RegistreerVerhuizingAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        RegistreerVerhuizingAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerVerhuizing((RegistreerVerhuizingBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testCorrectieAdresNL() {
        initMocks(CorrigeerAdresBericht.class, SoortBericht.BHG_VBA_CORRIGEER_ADRES);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling = new HandelingCorrectieAdresBericht();
        voegAdministratieveHandelingToeAanBericht(adminstratieveHandeling);

        final CorrigeerAdresAntwoordBericht testBericht = new CorrigeerAdresAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        final CorrigeerAdresAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).corrigeerAdres((CorrigeerAdresBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testRegistreerHuwelijkEnPartnerschap() {
        initMocks(RegistreerHuwelijkGeregistreerdPartnerschapBericht.class, SoortBericht.BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling =
            new HandelingVoltrekkingHuwelijkInNederlandBericht();
        voegAdministratieveHandelingToeAanBericht(adminstratieveHandeling);

        final RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht testBericht = new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerHuwelijkPartnerschap((RegistreerHuwelijkGeregistreerdPartnerschapBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testRegistreerOverlijden() {
        initMocks(RegistreerOverlijdenBericht.class, SoortBericht.BHG_OVL_REGISTREER_OVERLIJDEN);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht adminstratieveHandeling =
            new HandelingOverlijdenInNederlandBericht();
        voegAdministratieveHandelingToeAanBericht(adminstratieveHandeling);

        final RegistreerOverlijdenAntwoordBericht testBericht = new RegistreerOverlijdenAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        RegistreerOverlijdenAntwoordBericht resultaat =
            ((BijhoudingService) getWebService()).registreerOverlijden((RegistreerOverlijdenBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testRegistreerNationaliteit() {
        initMocks(RegistreerNationaliteitBericht.class, SoortBericht.BHG_NAT_REGISTREER_NATIONALITEIT);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht ah = new HandelingVerkrijgingNederlandseNationaliteitBericht();
        voegAdministratieveHandelingToeAanBericht(ah);

        RegistreerNationaliteitAntwoordBericht testBericht = new RegistreerNationaliteitAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        RegistreerNationaliteitAntwoordBericht resultaat = ((BijhoudingService) getWebService())
            .registreerNationaliteit((RegistreerNationaliteitBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    private void voegAdministratieveHandelingToeAanBericht(final AdministratieveHandelingBericht ah) {
        final BerichtStandaardGroepBericht standaardGroepBericht = new BerichtStandaardGroepBericht();
        standaardGroepBericht.setAdministratieveHandeling(ah);
        ReflectionTestUtils.setField(getBericht(), "standaard", standaardGroepBericht);
    }

    @Test
    public void testRegistreerNaamGeslacht() {
        initMocks(RegistreerNaamGeslachtBericht.class, SoortBericht.BHG_NMG_REGISTREER_NAAM_GESLACHT);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht ah = new HandelingWijzigingGeslachtsnaamBericht();
        voegAdministratieveHandelingToeAanBericht(ah);

        final RegistreerNaamGeslachtAntwoordBericht testBericht = new RegistreerNaamGeslachtAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        RegistreerNaamGeslachtAntwoordBericht resultaat = ((BijhoudingService) getWebService())
            .registreerNaamGeslacht((RegistreerNaamGeslachtBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testRegistreerErkenning() {
        initMocks(RegistreerErkenningBericht.class, SoortBericht.BHG_AFS_REGISTREER_ERKENNING);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht ah = new HandelingErkenningNaGeboorteBericht();
        voegAdministratieveHandelingToeAanBericht(ah);

        final RegistreerErkenningAntwoordBericht testBericht = new RegistreerErkenningAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        RegistreerErkenningAntwoordBericht resultaat = ((BijhoudingService) getWebService())
            .registreerErkenning((RegistreerErkenningBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testRegistreerReisdocument() {
        initMocks(RegistreerReisdocumentBericht.class, SoortBericht.BHG_RSD_REGISTREER_REISDOCUMENT);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht ah = new HandelingVerkrijgingReisdocumentBericht();
        voegAdministratieveHandelingToeAanBericht(ah);

        final RegistreerReisdocumentAntwoordBericht testBericht = new RegistreerReisdocumentAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        RegistreerReisdocumentAntwoordBericht resultaat = ((BijhoudingService) getWebService())
            .registreerReisdocument((RegistreerReisdocumentBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Test
    public void testRegistratieMededelingVerzoekBericht() {
        initMocks(RegistreerMededelingVerzoekBericht.class, SoortBericht.BHG_DVM_REGISTREER_MEDEDELING_VERZOEK);
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);

        final AdministratieveHandelingBericht ah = new HandelingWijzigingGezagBericht();
        voegAdministratieveHandelingToeAanBericht(ah);

        final RegistreerMededelingVerzoekAntwoordBericht testBericht = new RegistreerMededelingVerzoekAntwoordBericht();
        when(getAntwoordBerichtFactory().bouwAntwoordBericht(any(Bericht.class),
            any(BijhoudingResultaat.class)))
            .thenReturn(testBericht);

        RegistreerMededelingVerzoekAntwoordBericht resultaat = ((BijhoudingService) getWebService())
            .registreerMededelingVerzoek((RegistreerMededelingVerzoekBericht) getBericht());

        assertEquals(testBericht, resultaat);
    }

    @Override
    protected AbstractWebService<BijhoudingsBericht, BijhoudingBerichtContext, BijhoudingResultaat> getNieuweWebServiceVoorTest() {
        BijhoudingServiceImpl bijhoudingService = new BijhoudingServiceImpl();
        BijhoudingsBerichtVerwerker bijhoudingsBerichtVerwerker = Mockito.mock(BijhoudingsBerichtVerwerker.class);
        ReflectionTestUtils.setField(bijhoudingService, "bijhoudingsBerichtVerwerker", bijhoudingsBerichtVerwerker);
        return bijhoudingService;
    }

    @Override
    protected BerichtVerwerker getBerichtVerwerker() {
        return berichtVerwerker;
    }
}
