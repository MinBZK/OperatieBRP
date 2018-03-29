/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandelingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRolHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.model.AbstractBmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElementSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBerichtImpl;
import nl.bzk.brp.bijhouding.bericht.model.BronElement;
import nl.bzk.brp.bijhouding.bericht.model.DatumTijdElement;
import nl.bzk.brp.bijhouding.bericht.model.DocumentElement;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.ParametersElement;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;
import nl.bzk.brp.bijhouding.dal.ToegangBijhoudingsautorisatieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Testen voor AutorisatieService.
 */
@RunWith(MockitoJUnitRunner.class)
public class AutorisatieServiceTest {

    private static final String OIN_ONDERTEKENAAR = "oinOndertekenaar";
    private static final String OIN_TRANSPORTEUR = "oinTransporteur";

    @Mock
    private DynamischeStamtabelRepository stamtabelRepository;
    @Mock
    private ToegangBijhoudingsautorisatieRepository toegangBijhoudingsautorisatieRepository;

    private AutorisatieService service;

    private Partij geautoriseerdePartij;
    private PartijHistorie geautoriseerdePartijHistorie;
    private PartijRol geautoriseerdePartijRol;
    private Partij ondertekenaar;
    private PartijHistorie ondertekenaarHistorie;
    private Partij transporteur;
    private PartijHistorie transporteurHistorie;

    @Before
    public void setup() {
        geautoriseerdePartij = new Partij("geautoriseerde partij", "000000");

        geautoriseerdePartijHistorie = new PartijHistorie(geautoriseerdePartij, Timestamp.from(Instant.now()), 19800101, false, geautoriseerdePartij.getNaam());
        geautoriseerdePartij.addHisPartij(geautoriseerdePartijHistorie);

        geautoriseerdePartijRol = new PartijRol(geautoriseerdePartij, Rol.BIJHOUDINGSORGAAN_COLLEGE);
        final PartijRolHistorie history = new PartijRolHistorie(geautoriseerdePartijRol, Timestamp.from(Instant.now()), 19800101);
        geautoriseerdePartijRol.addPartijBijhoudingHistorie(history);
        geautoriseerdePartij.addPartijRol(geautoriseerdePartijRol);

        ondertekenaar = new Partij("ondertekenaar", "000001");
        ondertekenaar.setOin(OIN_ONDERTEKENAAR);
        ondertekenaarHistorie = new PartijHistorie(ondertekenaar, Timestamp.from(Instant.now()), 19800101, false, ondertekenaar.getNaam());
        ondertekenaar.addHisPartij(ondertekenaarHistorie);

        transporteur = new Partij("transporteur", "000002");
        transporteur.setOin(OIN_TRANSPORTEUR);
        transporteurHistorie = new PartijHistorie(transporteur, Timestamp.from(Instant.now()), 19800101, false, transporteur.getNaam());
        transporteur.addHisPartij(transporteurHistorie);

        when(stamtabelRepository.getPartijByCode(String.valueOf(geautoriseerdePartij.getCode()))).thenReturn(geautoriseerdePartij);
        service = new AutorisatieServiceImpl(stamtabelRepository, toegangBijhoudingsautorisatieRepository);
    }

    @Test
    public void testZendendePartijHeeftGeenHistorie() {
        geautoriseerdePartij.getHisPartijen().clear();
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testZendendePartijNogNietGeldig() {
        geautoriseerdePartijHistorie.setDatumIngang(21990101);
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testZendendePartijNietMeerGeldig() {
        geautoriseerdePartijHistorie.setDatumEinde(19900101);
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testZendendePartijGeldigEinddatumInToekomst() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        geautoriseerdePartijHistorie.setDatumEinde(21990101);
        assertAutorisatieOK(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testOndertekenaarPartijHeeftGeenHistorie() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        ondertekenaar.getHisPartijen().clear();
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testOndertekenaarPartijNogNietGeldig() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        ondertekenaarHistorie.setDatumIngang(21990101);
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testOndertekenaarPartijNietMeerGeldig() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        ondertekenaarHistorie.setDatumEinde(19900101);
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testOndertekenaarPartijGeldigEinddatumInToekomst() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        ondertekenaarHistorie.setDatumEinde(21990101);
        assertAutorisatieOK(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testTransporteurPartijHeeftGeenHistorie() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        transporteur.getHisPartijen().clear();
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testTransporteurPartijNogNietGeldig() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        transporteurHistorie.setDatumIngang(21990101);
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testTransporteurPartijNietMeerGeldig() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        transporteurHistorie.setDatumEinde(19900101);
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testTransporteurPartijGeldigEinddatumInToekomst() throws ParseException {
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        transporteurHistorie.setDatumEinde(21990101);
        assertAutorisatieOK(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijk() throws ParseException {
        // setup
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        // execute
        assertAutorisatieOK(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkMetOnbekendePartij() throws ParseException {
        // setup
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        final BijhoudingVerzoekBericht bericht = maakBerichtVoorVoltrekkingHuwelijkInNl();
        final StuurgegevensElement oudeStuurgegevens = bericht.getStuurgegevens();
        final StuurgegevensElement nieuweStuurgegevens =
                new StuurgegevensElement(
                        oudeStuurgegevens.getAttributen(),
                        new StringElement("1"),
                        oudeStuurgegevens.getZendendeSysteem(),
                        null,
                        oudeStuurgegevens.getReferentienummer(),
                        oudeStuurgegevens.getCrossReferentienummer(),
                        oudeStuurgegevens.getTijdstipVerzending());
        final BijhoudingVerzoekBericht nieuwBericht =
                new BijhoudingVerzoekBerichtImpl(
                        bericht.getAttributen(),
                        bericht.getSoort(),
                        nieuweStuurgegevens,
                        bericht.getParameters(),
                        bericht.getAdministratieveHandeling());
        // execute
        assertAutorisatieFout(service.autoriseer(nieuwBericht));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkZonderOndertekenaarEnTransporteur() throws ParseException {
        // setup
        final ToegangBijhoudingsautorisatie autorisatie =
                maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false);
        autorisatie.setOndertekenaar(null);
        autorisatie.setTransporteur(null);
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(Collections.singletonList(autorisatie));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkMetZendendePartijAlsTransporteurEnOndertekenaar() throws ParseException {
        // setup
        final ToegangBijhoudingsautorisatie autorisatie =
                maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false);
        geautoriseerdePartij.setOin(OIN_ONDERTEKENAAR);
        autorisatie.setOndertekenaar(null);
        autorisatie.setTransporteur(null);
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(Collections.singletonList(autorisatie));
        final BijhoudingVerzoekBericht bericht = maakBerichtVoorVoltrekkingHuwelijkInNl();
        bericht.setOinWaardeOndertekenaar(geautoriseerdePartij.getOin());
        bericht.setOinWaardeTransporteur(geautoriseerdePartij.getOin());
        // execute
        assertAutorisatieOK(service.autoriseer(bericht));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkWaarvanToegangNietMeerGeldigIs() throws ParseException {
        // setup
        final ToegangBijhoudingsautorisatie toegangAutorisatie =
                maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false);
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(toegangAutorisatie.getToegangBijhoudingsautorisatieHistorieSet()).setDatumEinde(
                toegangAutorisatie.getDatumIngang());
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(Collections.singletonList(toegangAutorisatie));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkWaarvanAutorisatieNietMeerGeldigIs() throws ParseException {
        // setup
        final ToegangBijhoudingsautorisatie toegangAutorisatie =
                maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false);
        final BijhoudingsautorisatieHistorie actueelAutorisatieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(toegangAutorisatie.getBijhoudingsautorisatie()
                        .getBijhoudingsautorisatieHistorieSet());
        actueelAutorisatieVoorkomen.setDatumEinde(actueelAutorisatieVoorkomen.getDatumIngang());
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(Collections.singletonList(toegangAutorisatie));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkDieNietMeerGeldigIsVanwegeOntbrekenHistorieToegangBijhoudingAutorisatie() throws ParseException {
        // setup
        final ToegangBijhoudingsautorisatie toegangAutorisatie =
                maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false);
        toegangAutorisatie.getToegangBijhoudingsautorisatieHistorieSet().clear();
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(Collections.singletonList(toegangAutorisatie));
        // execute
        // verify
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkDieNietMeerGeldigIsVanwegeOntbrekenHistorieBijhoudingAutorisatie() throws ParseException {
        // setup
        final ToegangBijhoudingsautorisatie toegangAutorisatie =
                maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false);
        toegangAutorisatie.getBijhoudingsautorisatie().getBijhoudingsautorisatieHistorieSet().clear();
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(Collections.singletonList(toegangAutorisatie));
        // execute
        // verify
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkDieNietGeldigIsBijOntbrekenHistorieOpSoort() throws ParseException {
        // setup
        final ToegangBijhoudingsautorisatie toegangAutorisatie =
                maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, false, false);
        for (final BijhoudingsautorisatieSoortAdministratieveHandeling soortAutorisatie : toegangAutorisatie.getBijhoudingsautorisatie()
                .getBijhoudingsautorisatieSoortAdministratieveHandelingSet()) {
            soortAutorisatie.getBijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet().clear();
        }
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(Collections.singletonList(toegangAutorisatie));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVoltrekkingHuwelijkEnAnder() throws ParseException {
        // setup
        List<ToegangBijhoudingsautorisatie> autorisaties = new ArrayList<>();
        autorisaties.add(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, false, false));
        autorisaties.add(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false));
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(autorisaties);
        // execute
        assertAutorisatieOK(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerVerkeerdeHandeling() throws ParseException {
        // setup
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections.singletonList(maakSpecifiekeAutorsiatieVoorHandeling(
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND,
                        false,
                        false)));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerAlleHandelingen() throws ParseException {
        // setup
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections.singletonList(maakAutorisatie(false, false)));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerToegangGeblokkeerd() throws ParseException {
        // setup
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections.singletonList(maakAutorisatie(true, false)));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testAutoriseerAlleHandelingenGeblokkeerd() throws ParseException {
        // setup
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections.singletonList(maakAutorisatie(false, true)));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testGeenAutorisatieVoorNietBijhouder() throws ParseException {
        // setup
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(
                Collections
                        .singletonList(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false)));
        geautoriseerdePartij.getPartijRolSet().clear();
        geautoriseerdePartij.addPartijRol(new PartijRol(geautoriseerdePartij, Rol.AFNEMER));
        // execute
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testPartijRolOngeldigAlleenHisHeefteind() throws ParseException {
        geautoriseerdePartijRol.getPartijBijhoudingHistorieSet().clear();
        final PartijRolHistorie history = new PartijRolHistorie(geautoriseerdePartijRol, Timestamp.from(Instant.now()), 20160101);
        history.setDatumEinde(20160102);
        geautoriseerdePartijRol.getPartijBijhoudingHistorieSet().add(history);
        List<ToegangBijhoudingsautorisatie> autorisaties = new ArrayList<>();
        autorisaties.add(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, false, false));
        autorisaties.add(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false));
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(autorisaties);
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    @Test
    public void testPartijRolOngeldigGeenHis() throws ParseException {
        geautoriseerdePartijRol.getPartijBijhoudingHistorieSet().clear();
        List<ToegangBijhoudingsautorisatie> autorisaties = new ArrayList<>();
        autorisaties.add(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, false, false));
        autorisaties.add(maakSpecifiekeAutorsiatieVoorHandeling(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, false, false));
        when(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(geautoriseerdePartij)).thenReturn(autorisaties);
        assertAutorisatieFout(service.autoriseer(maakBerichtVoorVoltrekkingHuwelijkInNl()));
    }

    private void assertAutorisatieFout(final List<MeldingElement> meldingen) {
        if (meldingen.isEmpty()) {
            fail("Autorisatie fout verwacht.");
        }
    }

    private void assertAutorisatieOK(final List<MeldingElement> meldingen) {
        if (!meldingen.isEmpty()) {
            fail("Autorisatie OK verwacht.");
        }
    }

    private BijhoudingVerzoekBericht maakBerichtVoorVoltrekkingHuwelijkInNl() {
        final Map<String, String> attributen = new AbstractBmrGroep.AttributenBuilder().objecttype("leeg").communicatieId("ci_test").build();
        final StuurgegevensElement stuurgegevens =
                new StuurgegevensElement(
                        attributen,
                        new StringElement(String.valueOf(geautoriseerdePartij.getCode())),
                        new StringElement("systeem"),
                        null,
                        new StringElement("r1"),
                        null,
                        new DatumTijdElement(DatumUtil.nuAlsZonedDateTime()));
        final ParametersElement parameters = new ParametersElement(attributen, new StringElement("Bijhouding"));
        final DocumentElement document =
                new DocumentElement(
                        attributen,
                        new StringElement("soort"),
                        new StringElement(""),
                        new StringElement(""),
                        new StringElement(String.valueOf(geautoriseerdePartij.getCode())));
        final BronElement bron = new BronElement(attributen, document, null);
        final List<BronElement> bronnen = Collections.singletonList(bron);
        final AdministratieveHandelingElement administratieveHandeling =
                new AdministratieveHandelingElement(
                        attributen,
                        AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                        stuurgegevens.getZendendePartij(),
                        null,
                        null,
                        null,
                        bronnen,
                        null);
        final BijhoudingVerzoekBericht result =
                new BijhoudingVerzoekBerichtImpl(attributen, BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP, stuurgegevens, parameters,
                        administratieveHandeling);
        result.setOinWaardeOndertekenaar(OIN_ONDERTEKENAAR);
        result.setOinWaardeTransporteur(OIN_TRANSPORTEUR);
        return result;
    }

    private ToegangBijhoudingsautorisatie maakSpecifiekeAutorsiatieVoorHandeling(
            final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final boolean isToegangAutorisatieGeblokkeerd,
            final boolean isAutorisatieGeblokkeerd) throws ParseException {
        final ToegangBijhoudingsautorisatie toegangAutorisatie = maakAutorisatie(isToegangAutorisatieGeblokkeerd, isAutorisatieGeblokkeerd);
        final BijhoudingsautorisatieSoortAdministratieveHandeling soortAutorisatie =
                new BijhoudingsautorisatieSoortAdministratieveHandeling(toegangAutorisatie.getBijhoudingsautorisatie(), soortAdministratieveHandeling);
        final BijhoudingsautorisatieSoortAdministratieveHandelingHistorie actueelVoorkomen =
                new BijhoudingsautorisatieSoortAdministratieveHandelingHistorie(soortAutorisatie, new Timestamp(System.currentTimeMillis()));
        soortAutorisatie.addBijhoudingsautorisatieSoortAdministratieveHandelingHistorie(actueelVoorkomen);
        toegangAutorisatie.getBijhoudingsautorisatie().addBijhoudingsautorisatieSoortAdministratieveHandeling(soortAutorisatie);
        return toegangAutorisatie;
    }

    private ToegangBijhoudingsautorisatie maakAutorisatie(final boolean isToegangAutorisatieGeblokkeerd, final boolean isAutorisatieGeblokkeerd)
            throws ParseException {
        final int datumIngang = DatumUtil.vanDatumNaarInteger(DatumUtil.vanStringNaarDatum("20120101"));
        final Bijhoudingsautorisatie autorisatie = new Bijhoudingsautorisatie(false);
        final BijhoudingsautorisatieHistorie actueelAutorisatieVoorkomen =
                new BijhoudingsautorisatieHistorie(autorisatie, new Timestamp(System.currentTimeMillis()), datumIngang, "naam");
        autorisatie.addBijhoudingsautorisatieHistorieSet(actueelAutorisatieVoorkomen);
        final ToegangBijhoudingsautorisatie toegangAutorisatie = new ToegangBijhoudingsautorisatie(geautoriseerdePartijRol, autorisatie);
        toegangAutorisatie.setDatumIngang(datumIngang);
        final ToegangBijhoudingsautorisatieHistorie actueelToegangAutorisatieVoorkomen =
                new ToegangBijhoudingsautorisatieHistorie(toegangAutorisatie, new Timestamp(System.currentTimeMillis()), datumIngang);
        toegangAutorisatie.addToegangBijhoudingsautorisatieHistorieSet(actueelToegangAutorisatieVoorkomen);
        if (isToegangAutorisatieGeblokkeerd) {
            actueelToegangAutorisatieVoorkomen.setIndicatieGeblokkeerd(isToegangAutorisatieGeblokkeerd);
        }
        if (isAutorisatieGeblokkeerd) {
            actueelAutorisatieVoorkomen.setIndicatieGeblokkeerd(isAutorisatieGeblokkeerd);
        }
        toegangAutorisatie.setOndertekenaar(ondertekenaar);
        toegangAutorisatie.setTransporteur(transporteur);
        return toegangAutorisatie;
    }
}
