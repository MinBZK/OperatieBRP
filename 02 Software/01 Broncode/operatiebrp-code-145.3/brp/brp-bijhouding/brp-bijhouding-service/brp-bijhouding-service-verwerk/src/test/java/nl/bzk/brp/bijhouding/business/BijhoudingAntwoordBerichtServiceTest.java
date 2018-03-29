/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.brp.bijhouding.bericht.model.AbstractBmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingAntwoordElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElementSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBerichtImpl;
import nl.bzk.brp.bijhouding.bericht.model.GedeblokkeerdeMeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.IdentificatienummersElement;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.ParametersElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonGegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.ResultaatElement;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * Unittests voor de {@link BijhoudingAntwoordBerichtService}
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingAntwoordBerichtServiceTest {
    private static final String BIJHOUDING_BERICHT = "voltrekkingHuwelijkNederlandMetBZVUBericht.xml";
    private static final String BURGERSERVICENUMMER = "681856841";
    private static Regel GEDEBLOKKEERDE_MELDING = Regel.R1292;

    @Mock
    private ApplicationContext context;
    @Mock
    private ValidatieService validatieService;
    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private ObjectSleutelService objectSleutelService;

    private BijhoudingAntwoordBerichtService service;

    private static final Partij PARTIJ = new Partij("Gemeente Hellevoetsluis", "053001");
    private BijhoudingVerzoekBericht verzoekBericht;
    private Persoon persoon;
    private AdministratieveHandeling administratieveHandeling;

    @Before
    public void setup() throws ParseException, OngeldigeObjectSleutelException {
        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        when(context.getBean(PersoonRepository.class)).thenReturn(persoonRepository);
        when(context.getBean(ObjectSleutelService.class)).thenReturn(objectSleutelService);

        verzoekBericht = new BijhoudingVerzoekBerichtParser().parse(this.getClass().getResourceAsStream(BIJHOUDING_BERICHT));
        administratieveHandeling = maakAdministratieveHandeling();

        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId(1L);
        final PersoonIDHistorie persoonIDHistorie = new PersoonIDHistorie(persoon);
        persoonIDHistorie.setBurgerservicenummer(BURGERSERVICENUMMER);
        persoonIDHistorie.setAdministratienummer("4829083154");
        persoon.addPersoonIDHistorie(persoonIDHistorie);

        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, persoon, administratieveHandeling, Timestamp.from(Instant.now()));
        persoon.addPersoonAfgeleidAdministratiefHistorie(afgeleidAdministratiefHistorie);

        when(objectSleutelService.maakPersoonObjectSleutel("212121"))
                .thenReturn(new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(persoon.getId(), 1));
        when(persoonRepository.findById(persoon.getId())).thenReturn(persoon);
        service = new BijhoudingAntwoordBerichtServiceImpl(validatieService);
    }

    @Test
    public void antwoordBerichtBijhoudingGeslaagdEnVerwerkt() {
        final List<MeldingElement> meldingen = maakMeldingen(Regel.R1572);
        when(validatieService.bepaalHoogsteMeldingNiveau(meldingen)).thenReturn(SoortMelding.WAARSCHUWING);
        when(validatieService.kanVerwerkingDoorgaan(meldingen)).thenReturn(true);

        final BijhoudingAntwoordBericht bijhoudingAntwoordBericht =
                service.maakAntwoordBericht(
                        verzoekBericht,
                        meldingen,
                        administratieveHandeling,
                        maakBijhoudingsplanContext(verzoekBericht, BijhoudingSituatie.AUTOMATISCHE_FIAT));
        assertNotNull(bijhoudingAntwoordBericht);
        assertNotNull(bijhoudingAntwoordBericht.getXml());
        assertNotNull(bijhoudingAntwoordBericht.getAdministratieveHandelingID());

        assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD, bijhoudingAntwoordBericht.getSoort());
        final StuurgegevensElement stuurgegevens = bijhoudingAntwoordBericht.getStuurgegevens();
        assertNotNull(stuurgegevens);

        // Controle stuurgegevens
        assertStringElement("199901", stuurgegevens.getZendendePartij());
        assertStringElement("BRP", stuurgegevens.getZendendeSysteem());
        assertNotNull(stuurgegevens.getReferentienummer());
        assertStringElement(verzoekBericht.getStuurgegevens().getReferentienummer(), stuurgegevens.getCrossReferentienummer());
        assertNotNull(stuurgegevens.getTijdstipVerzending());

        // Controle resultaat
        final ResultaatElement resultaat = bijhoudingAntwoordBericht.getResultaat();
        assertNotNull(resultaat);
        assertStringElement("Geslaagd", resultaat.getVerwerking());
        assertStringElement("Verwerkt", resultaat.getBijhouding());
        assertStringElement("Waarschuwing", resultaat.getHoogsteMeldingsniveau());

        // Controle meldingen
        final List<MeldingElement> antwoordMeldingen = bijhoudingAntwoordBericht.getMeldingen();
        assertNotNull(antwoordMeldingen);
        assertEquals(1, antwoordMeldingen.size());
        final MeldingElement antwoordMeldingElement = antwoordMeldingen.get(0);
        assertNotNull(antwoordMeldingElement.getReferentieId());
        assertStringElement("R1572", antwoordMeldingElement.getRegelCode());
        assertStringElement("Waarschuwing", antwoordMeldingElement.getSoortNaam());

        // Controle administratieve handelingen
        final AdministratieveHandelingAntwoordElement administratieveHandelingAntwoord = bijhoudingAntwoordBericht.getAdministratieveHandelingAntwoord();
        assertNotNull(administratieveHandelingAntwoord);
        assertEquals(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND, administratieveHandelingAntwoord.getSoort());
        assertStringElement(PARTIJ.getCode(), administratieveHandelingAntwoord.getPartijCode());
        assertNotNull(administratieveHandelingAntwoord.getTijdstipRegistratie());

        final List<PersoonGegevensElement> bezienVanuitPersonen = administratieveHandelingAntwoord.getBezienVanuitPersonen();
        assertNotNull(bezienVanuitPersonen);
        assertEquals(1, bezienVanuitPersonen.size());
        final PersoonElement bezienVanuitPersoon = bezienVanuitPersonen.get(0);
        final IdentificatienummersElement identificatienummers = bezienVanuitPersoon.getIdentificatienummers();
        assertNotNull(identificatienummers);
        assertStringElement("" + BURGERSERVICENUMMER, identificatienummers.getBurgerservicenummer());

        final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen = administratieveHandelingAntwoord.getGedeblokkeerdeMeldingen();
        assertNotNull(gedeblokkeerdeMeldingen);
        assertEquals(1, gedeblokkeerdeMeldingen.size());
        final GedeblokkeerdeMeldingElement antwoordBerichtGedeblokkeerdeMelding = gedeblokkeerdeMeldingen.get(0);
        final GedeblokkeerdeMeldingElement verzoekBerichgGedeblokkeerdeMelding = verzoekBericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen()
                .get(0);
        assertEquals(verzoekBerichgGedeblokkeerdeMelding.getCommunicatieId(), antwoordBerichtGedeblokkeerdeMelding.getReferentieId());
        assertStringElement(verzoekBerichgGedeblokkeerdeMelding.getRegelCode(), antwoordBerichtGedeblokkeerdeMelding.getRegelCode());
        assertStringElement(GEDEBLOKKEERDE_MELDING.getMelding(), antwoordBerichtGedeblokkeerdeMelding.getMelding());

        final List<PersoonGegevensElement> bijgehoudenPersonen = administratieveHandelingAntwoord.getBijgehoudenPersonen();
        assertNotNull(bijgehoudenPersonen);
        assertEquals(1, bijgehoudenPersonen.size());
        assertNotNull(administratieveHandelingAntwoord.getBijhoudingsplan());
    }

    @Test
    public void antwoordBerichtBijhoudingGeslaagdEnDeelsUitgesteldOpnieuwIndienen() {
        final List<MeldingElement> meldingen = maakMeldingen(Regel.R1572);
        when(validatieService.bepaalHoogsteMeldingNiveau(meldingen)).thenReturn(SoortMelding.WAARSCHUWING);
        when(validatieService.kanVerwerkingDoorgaan(meldingen)).thenReturn(true);

        final BijhoudingAntwoordBericht bijhoudingAntwoordBericht =
                service.maakAntwoordBericht(
                        verzoekBericht,
                        meldingen,
                        administratieveHandeling,
                        maakBijhoudingsplanContext(verzoekBericht, BijhoudingSituatie.OPNIEUW_INDIENEN));
        assertNotNull(bijhoudingAntwoordBericht);
        assertNotNull(bijhoudingAntwoordBericht.getXml());
        assertNotNull(bijhoudingAntwoordBericht.getAdministratieveHandelingID());

        assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD, bijhoudingAntwoordBericht.getSoort());
        final StuurgegevensElement stuurgegevens = bijhoudingAntwoordBericht.getStuurgegevens();
        assertNotNull(stuurgegevens);

        // Controle resultaat
        final ResultaatElement resultaat = bijhoudingAntwoordBericht.getResultaat();
        assertNotNull(resultaat);
        assertStringElement("Geslaagd", resultaat.getVerwerking());
        assertStringElement("(Deels) uitgesteld", resultaat.getBijhouding());
        assertStringElement("Waarschuwing", resultaat.getHoogsteMeldingsniveau());
    }

    @Test
    public void antwoordBerichtBijhoudingGeslaagdEnDeelsUitgesteldAanvullenEnOpnieuwIndienen() {
        final List<MeldingElement> meldingen = maakMeldingen(Regel.R1572);
        when(validatieService.bepaalHoogsteMeldingNiveau(meldingen)).thenReturn(SoortMelding.WAARSCHUWING);
        when(validatieService.kanVerwerkingDoorgaan(meldingen)).thenReturn(true);

        final BijhoudingAntwoordBericht bijhoudingAntwoordBericht =
                service.maakAntwoordBericht(
                        verzoekBericht,
                        meldingen,
                        administratieveHandeling,
                        maakBijhoudingsplanContext(verzoekBericht, BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN));
        assertNotNull(bijhoudingAntwoordBericht);
        assertNotNull(bijhoudingAntwoordBericht.getXml());
        assertNotNull(bijhoudingAntwoordBericht.getAdministratieveHandelingID());

        assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD, bijhoudingAntwoordBericht.getSoort());
        final StuurgegevensElement stuurgegevens = bijhoudingAntwoordBericht.getStuurgegevens();
        assertNotNull(stuurgegevens);

        // Controle resultaat
        final ResultaatElement resultaat = bijhoudingAntwoordBericht.getResultaat();
        assertNotNull(resultaat);
        assertStringElement("Geslaagd", resultaat.getVerwerking());
        assertStringElement("(Deels) uitgesteld", resultaat.getBijhouding());
        assertStringElement("Waarschuwing", resultaat.getHoogsteMeldingsniveau());
    }

    @Test
    public void antwoordBerichtBijhoudingGeslaagdEnDeelsUitgesteldGBA() {
        final List<MeldingElement> meldingen = maakMeldingen(Regel.R1572);
        when(validatieService.bepaalHoogsteMeldingNiveau(meldingen)).thenReturn(SoortMelding.WAARSCHUWING);
        when(validatieService.kanVerwerkingDoorgaan(meldingen)).thenReturn(true);

        final BijhoudingAntwoordBericht bijhoudingAntwoordBericht =
                service.maakAntwoordBericht(
                        verzoekBericht,
                        meldingen,
                        administratieveHandeling,
                        maakBijhoudingsplanContext(verzoekBericht, BijhoudingSituatie.GBA));
        assertNotNull(bijhoudingAntwoordBericht);
        assertNotNull(bijhoudingAntwoordBericht.getXml());
        assertNotNull(bijhoudingAntwoordBericht.getAdministratieveHandelingID());

        assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD, bijhoudingAntwoordBericht.getSoort());
        final StuurgegevensElement stuurgegevens = bijhoudingAntwoordBericht.getStuurgegevens();
        assertNotNull(stuurgegevens);

        // Controle resultaat
        final ResultaatElement resultaat = bijhoudingAntwoordBericht.getResultaat();
        assertNotNull(resultaat);
        assertStringElement("Geslaagd", resultaat.getVerwerking());
        assertStringElement("(Deels) uitgesteld", resultaat.getBijhouding());
        assertStringElement("Waarschuwing", resultaat.getHoogsteMeldingsniveau());
    }

    @Test
    public void antwoordBerichtBijhoudingGeslaagdEnVerwerktIndienerIsBijhoudingsPartij() {
        final List<MeldingElement> meldingen = maakMeldingen(Regel.R1572);
        when(validatieService.bepaalHoogsteMeldingNiveau(meldingen)).thenReturn(SoortMelding.WAARSCHUWING);
        when(validatieService.kanVerwerkingDoorgaan(meldingen)).thenReturn(true);

        final BijhoudingAntwoordBericht bijhoudingAntwoordBericht =
                service.maakAntwoordBericht(
                        verzoekBericht,
                        meldingen,
                        administratieveHandeling,
                        maakBijhoudingsplanContext(verzoekBericht, BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ));
        assertNotNull(bijhoudingAntwoordBericht);
        assertNotNull(bijhoudingAntwoordBericht.getXml());
        assertNotNull(bijhoudingAntwoordBericht.getAdministratieveHandelingID());

        assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD, bijhoudingAntwoordBericht.getSoort());
        final StuurgegevensElement stuurgegevens = bijhoudingAntwoordBericht.getStuurgegevens();
        assertNotNull(stuurgegevens);

        // Controle resultaat
        final ResultaatElement resultaat = bijhoudingAntwoordBericht.getResultaat();
        assertNotNull(resultaat);
        assertStringElement("Geslaagd", resultaat.getVerwerking());
        assertStringElement("Verwerkt", resultaat.getBijhouding());
        assertStringElement("Waarschuwing", resultaat.getHoogsteMeldingsniveau());
    }

    @Test
    public void antwoordBerichtPrevalidatieGeslaagd() {
        final ParametersElement parameters = new ParametersElement(
                new AbstractBmrGroep.AttributenBuilder().communicatieId(verzoekBericht.getParameters().getCommunicatieId()).build(),
                new StringElement("Prevalidatie"));
        final BijhoudingVerzoekBericht prevalidatieVerzoek = new BijhoudingVerzoekBerichtImpl(verzoekBericht.getAttributen(), verzoekBericht.getSoort(),
                verzoekBericht.getStuurgegevens(), parameters, verzoekBericht.getAdministratieveHandeling());

        final List<MeldingElement> meldingen = maakMeldingen(Regel.R1572);

        when(validatieService.bepaalHoogsteMeldingNiveau(meldingen)).thenReturn(SoortMelding.WAARSCHUWING);
        when(validatieService.kanVerwerkingDoorgaan(meldingen)).thenReturn(true);

        final BijhoudingAntwoordBericht bijhoudingAntwoordBericht =
                service.maakAntwoordBericht(
                        prevalidatieVerzoek,
                        meldingen,
                        administratieveHandeling,
                        maakBijhoudingsplanContext(prevalidatieVerzoek, BijhoudingSituatie.AUTOMATISCHE_FIAT));
        assertNotNull(bijhoudingAntwoordBericht);
        assertNotNull(bijhoudingAntwoordBericht.getXml());
        assertNotNull(bijhoudingAntwoordBericht.getAdministratieveHandelingID());

        assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD, bijhoudingAntwoordBericht.getSoort());
        final StuurgegevensElement stuurgegevens = bijhoudingAntwoordBericht.getStuurgegevens();
        assertNotNull(stuurgegevens);

        // Controle stuurgegevens
        assertStringElement("199901", stuurgegevens.getZendendePartij());
        assertStringElement("BRP", stuurgegevens.getZendendeSysteem());
        assertNotNull(stuurgegevens.getReferentienummer());
        assertStringElement(verzoekBericht.getStuurgegevens().getReferentienummer(), stuurgegevens.getCrossReferentienummer());
        assertNotNull(stuurgegevens.getTijdstipVerzending());

        // Controle resultaat
        final ResultaatElement resultaat = bijhoudingAntwoordBericht.getResultaat();
        assertNotNull(resultaat);
        assertStringElement("Geslaagd", resultaat.getVerwerking());
        assertStringElement("Verwerkt", resultaat.getBijhouding());
        assertStringElement("Waarschuwing", resultaat.getHoogsteMeldingsniveau());

        // Controle meldingen
        final List<MeldingElement> antwoordMeldingen = bijhoudingAntwoordBericht.getMeldingen();
        assertNotNull(antwoordMeldingen);
        assertEquals(1, antwoordMeldingen.size());
        final MeldingElement antwoordMeldingElement = antwoordMeldingen.get(0);
        assertNotNull(antwoordMeldingElement.getReferentieId());
        assertStringElement("R1572", antwoordMeldingElement.getRegelCode());
        assertStringElement("Waarschuwing", antwoordMeldingElement.getSoortNaam());

        // Controle administratieve handelingen
        final AdministratieveHandelingAntwoordElement administratieveHandelingAntwoord = bijhoudingAntwoordBericht.getAdministratieveHandelingAntwoord();
        assertNotNull(administratieveHandelingAntwoord);
        assertEquals(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND, administratieveHandelingAntwoord.getSoort());
        assertStringElement(PARTIJ.getCode(), administratieveHandelingAntwoord.getPartijCode());
        assertNotNull(administratieveHandelingAntwoord.getTijdstipRegistratie());

        final List<PersoonGegevensElement> bezienVanuitPersonen = administratieveHandelingAntwoord.getBezienVanuitPersonen();
        assertNotNull(bezienVanuitPersonen);
        assertEquals(1, bezienVanuitPersonen.size());
        final PersoonElement bezienVanuitPersoon = bezienVanuitPersonen.get(0);
        final IdentificatienummersElement identificatienummers = bezienVanuitPersoon.getIdentificatienummers();
        assertNotNull(identificatienummers);
        assertStringElement("" + BURGERSERVICENUMMER, identificatienummers.getBurgerservicenummer());

        final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen = administratieveHandelingAntwoord.getGedeblokkeerdeMeldingen();
        assertNotNull(gedeblokkeerdeMeldingen);
        assertEquals(1, gedeblokkeerdeMeldingen.size());
        final GedeblokkeerdeMeldingElement antwoordBerichtGedeblokkeerdeMelding = gedeblokkeerdeMeldingen.get(0);
        final GedeblokkeerdeMeldingElement verzoekBerichgGedeblokkeerdeMelding = verzoekBericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen()
                .get(0);
        assertEquals(verzoekBerichgGedeblokkeerdeMelding.getCommunicatieId(), antwoordBerichtGedeblokkeerdeMelding.getReferentieId());
        assertStringElement(verzoekBerichgGedeblokkeerdeMelding.getRegelCode(), antwoordBerichtGedeblokkeerdeMelding.getRegelCode());
        assertStringElement(GEDEBLOKKEERDE_MELDING.getMelding(), antwoordBerichtGedeblokkeerdeMelding.getMelding());

        final List<PersoonGegevensElement> bijgehoudenPersonen = administratieveHandelingAntwoord.getBijgehoudenPersonen();
        assertNotNull(bijgehoudenPersonen);
        assertEquals(0, bijgehoudenPersonen.size());
    }

    @Test
    public void antwoordBerichtPrevalidatieFoutief() {
        final ParametersElement parameters = new ParametersElement(
                new AbstractBmrGroep.AttributenBuilder().communicatieId(verzoekBericht.getParameters().getCommunicatieId()).build(),
                new StringElement("Prevalidatie"));
        final BijhoudingVerzoekBericht prevalidatieVerzoek = new BijhoudingVerzoekBerichtImpl(verzoekBericht.getAttributen(), verzoekBericht.getSoort(),
                verzoekBericht.getStuurgegevens(), parameters, verzoekBericht.getAdministratieveHandeling());
        final List<MeldingElement> meldingen = maakMeldingen(Regel.R1831);

        when(validatieService.bepaalHoogsteMeldingNiveau(meldingen)).thenReturn(SoortMelding.FOUT);
        when(validatieService.kanVerwerkingDoorgaan(meldingen)).thenReturn(false);

        final BijhoudingAntwoordBericht bijhoudingAntwoordBericht =
                service.maakAntwoordBericht(
                        prevalidatieVerzoek,
                        meldingen,
                        null,
                        new BijhoudingsplanContext(prevalidatieVerzoek));
        assertNotNull(bijhoudingAntwoordBericht);
        assertNotNull(bijhoudingAntwoordBericht.getXml());
        assertNull(bijhoudingAntwoordBericht.getAdministratieveHandelingID());

        assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD, bijhoudingAntwoordBericht.getSoort());
        final StuurgegevensElement stuurgegevens = bijhoudingAntwoordBericht.getStuurgegevens();
        assertNotNull(stuurgegevens);

        // Controle stuurgegevens
        assertStringElement("199901", stuurgegevens.getZendendePartij());
        assertStringElement("BRP", stuurgegevens.getZendendeSysteem());
        assertNotNull(stuurgegevens.getReferentienummer());
        assertStringElement(verzoekBericht.getStuurgegevens().getReferentienummer(), stuurgegevens.getCrossReferentienummer());
        assertNotNull(stuurgegevens.getTijdstipVerzending());

        // Controle resultaat
        final ResultaatElement resultaat = bijhoudingAntwoordBericht.getResultaat();
        assertNotNull(resultaat);
        assertStringElement("Foutief", resultaat.getVerwerking());
        assertNull(resultaat.getBijhouding());
        assertStringElement("Fout", resultaat.getHoogsteMeldingsniveau());

        // Controle meldingen
        final List<MeldingElement> antwoordMeldingen = bijhoudingAntwoordBericht.getMeldingen();
        assertNotNull(antwoordMeldingen);
        assertEquals(1, antwoordMeldingen.size());
        final MeldingElement antwoordMeldingElement = antwoordMeldingen.get(0);
        assertNotNull(antwoordMeldingElement.getReferentieId());
        assertStringElement("R1831", antwoordMeldingElement.getRegelCode());
        assertStringElement("Fout", antwoordMeldingElement.getSoortNaam());

        // Controle administratieve handelingen
        final AdministratieveHandelingAntwoordElement administratieveHandelingAntwoord = bijhoudingAntwoordBericht.getAdministratieveHandelingAntwoord();
        assertNotNull(administratieveHandelingAntwoord);
        assertEquals(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND, administratieveHandelingAntwoord.getSoort());
        assertStringElement(PARTIJ.getCode(), administratieveHandelingAntwoord.getPartijCode());
        assertNotNull(administratieveHandelingAntwoord.getTijdstipRegistratie());

        final List<PersoonGegevensElement> bezienVanuitPersonen = administratieveHandelingAntwoord.getBezienVanuitPersonen();
        assertNotNull(bezienVanuitPersonen);
        assertEquals(1, bezienVanuitPersonen.size());
        final PersoonElement bezienVanuitPersoon = bezienVanuitPersonen.get(0);
        final IdentificatienummersElement identificatienummers = bezienVanuitPersoon.getIdentificatienummers();
        assertNotNull(identificatienummers);
        assertStringElement("" + BURGERSERVICENUMMER, identificatienummers.getBurgerservicenummer());

        final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen = administratieveHandelingAntwoord.getGedeblokkeerdeMeldingen();
        assertNotNull(gedeblokkeerdeMeldingen);
        assertEquals(1, gedeblokkeerdeMeldingen.size());
        final GedeblokkeerdeMeldingElement antwoordBerichtGedeblokkeerdeMelding = gedeblokkeerdeMeldingen.get(0);
        final GedeblokkeerdeMeldingElement verzoekBerichgGedeblokkeerdeMelding = verzoekBericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen()
                .get(0);
        assertEquals(verzoekBerichgGedeblokkeerdeMelding.getCommunicatieId(), antwoordBerichtGedeblokkeerdeMelding.getReferentieId());
        assertStringElement(verzoekBerichgGedeblokkeerdeMelding.getRegelCode(), antwoordBerichtGedeblokkeerdeMelding.getRegelCode());
        assertStringElement(GEDEBLOKKEERDE_MELDING.getMelding(), antwoordBerichtGedeblokkeerdeMelding.getMelding());

        final List<PersoonGegevensElement> bijgehoudenPersonen = administratieveHandelingAntwoord.getBijgehoudenPersonen();
        assertNotNull(bijgehoudenPersonen);
        assertEquals(0, bijgehoudenPersonen.size());
    }

    private List<MeldingElement> maakMeldingen(final Regel regel) {
        final MeldingElement meldingElement = MeldingElement.getInstance(regel, verzoekBericht.getAdministratieveHandeling());
        return Collections.singletonList(meldingElement);
    }

    private AdministratieveHandeling maakAdministratieveHandeling() {
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(PARTIJ, SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, Timestamp.from(Instant.now()));
        administratieveHandeling.setId(1L);
        return administratieveHandeling;
    }

    private BijhoudingsplanContext maakBijhoudingsplanContext(final BijhoudingVerzoekBericht verzoekBericht, final BijhoudingSituatie bijhoudingSituatie) {
        BijhoudingsplanContext context = new BijhoudingsplanContext(verzoekBericht);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        bijhoudingPersoon.setBijhoudingspartijVoorBijhoudingsplan(
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet()).getPartij());
        context.addBijhoudingSituatieVoorPersoon(bijhoudingPersoon, bijhoudingSituatie);
        return context;
    }

    private void assertStringElement(final StringElement expected, final StringElement actual) {
        assertStringElement(expected.getWaarde(), actual);
    }

    private void assertStringElement(final String expected, final StringElement actual) {
        assertEquals(expected, actual.getWaarde());
    }
}
