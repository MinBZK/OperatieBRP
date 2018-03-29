/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzonderingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRolHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.model.AbstractBmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElementSoort;
import nl.bzk.brp.bijhouding.bericht.model.BetrokkenheidElement;
import nl.bzk.brp.bijhouding.bericht.model.BetrokkenheidElementSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBerichtImpl;
import nl.bzk.brp.bijhouding.bericht.model.ElementBuilder;
import nl.bzk.brp.bijhouding.bericht.model.FamilierechtelijkeBetrekkingElement;
import nl.bzk.brp.bijhouding.bericht.model.ObjectSleutelIndex;
import nl.bzk.brp.bijhouding.bericht.model.OuderschapElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonGegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonRelatieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieIdentificatienummersGerelateerdeActieElement;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.FiatteringsuitzonderingRepository;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * Unittests voor {@link BijhoudingsplanServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingsplanServiceImplTest {

    private static final String ZENDENDE_PARTIJ_CODE = "053001";
    @Mock
    private ObjectSleutelService objectSleutelService;
    @Mock
    private ApplicationContext context;
    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    @Mock
    private ObjectSleutelIndex objectSleutelIndex;
    @Mock
    private FiatteringsuitzonderingRepository fiatteringsuitzonderingRepository;
    @Mock
    private BijhoudingVerzoekBericht berichtVerhuizingNaarBuitenland;


    private BijhoudingsplanService bijhoudingsplanService;

    private static final String BIJHOUDING_BERICHT = "voltrekkingHuwelijkNederlandMetBZVUBericht.xml";
    private static final String BIJHOUDING_BERICHT_VERHUIZING = "verhuizingIntergemeentelijk.xml";
    private static final String HOOFD_PERSOON_ANUMMER = "5826752801";
    private static final String HOOFD_PERSOON_BSN = "489482569";
    private static final String HOOFD_PERSOON_OBJECT_SLEUTEL = "212121";
    private static final String GERELATEERDE_PERSOON_ANUMMER = "1347652961";
    private static final String GERELATEERDE_PERSOON_BSN = "641304201";
    private static final String GERELATEERDE_PERSOON_OBJECT_SLEUTEL = "A12BC";

    private BijhoudingVerzoekBericht bericht;
    private Partij partij;
    private Partij anderePartij;
    private BijhoudingPersoon hoofdPersoon;
    private BijhoudingPersoon gerelateerdePersoon;
    private Timestamp datumTijdLaatsteWijziging = Timestamp.from(Instant.now());
    private SoortDocument soortDocument;
    private BijhoudingVerzoekBericht berichtVerhuizing;


    @Before
    public void setUp() throws ParseException, OngeldigeObjectSleutelException {
        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        when(context.getBean(PersoonRepository.class)).thenReturn(persoonRepository);
        when(context.getBean(ObjectSleutelService.class)).thenReturn(objectSleutelService);
        when(context.getBean(FiatteringsuitzonderingRepository.class)).thenReturn(fiatteringsuitzonderingRepository);
        when(context.getBean(DynamischeStamtabelRepository.class)).thenReturn(dynamischeStamtabelRepository);
        partij = new Partij("Gemeente Hellevoetsluis", ZENDENDE_PARTIJ_CODE);
        anderePartij = new Partij("Gemeente Amsterdam", "036301");
        when(dynamischeStamtabelRepository.getPartijByCode(ZENDENDE_PARTIJ_CODE)).thenReturn(partij);
        soortDocument = new SoortDocument("Huwelijksakte", "Huwelijksakte");
        when(dynamischeStamtabelRepository.getSoortDocumentByNaam(anyString())).thenReturn(soortDocument);
        bericht = new BijhoudingVerzoekBerichtParser().parse(this.getClass().getResourceAsStream(BIJHOUDING_BERICHT));
        bericht.setObjectSleutelIndex(objectSleutelIndex);
        berichtVerhuizing = new BijhoudingVerzoekBerichtParser().parse(this.getClass().getResourceAsStream(BIJHOUDING_BERICHT_VERHUIZING));
        berichtVerhuizing.setObjectSleutelIndex(objectSleutelIndex);
        setupHoofdPersoon(false, false);
        setupGerelateerdePersoon();
        bijhoudingsplanService = new BijhoudingsplanServiceImpl(fiatteringsuitzonderingRepository);

    }

    private void setupHoofdPersoon(final boolean isVerhuizing, final boolean isVerhuizingBuitenland) throws OngeldigeObjectSleutelException {
        final Persoon hoofdPersoonEntiteit = new Persoon(SoortPersoon.INGESCHREVENE);
        hoofdPersoonEntiteit.setId(212121L);
        hoofdPersoonEntiteit.setBurgerservicenummer(HOOFD_PERSOON_BSN);

        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(hoofdPersoonEntiteit, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        hoofdPersoonEntiteit.addPersoonBijhoudingHistorie(bijhoudingHistorie);
        final SoortAdministratieveHandeling soortAdministratieveHandeling;
        if (isVerhuizing) {
            soortAdministratieveHandeling = SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK;
        } else if (isVerhuizingBuitenland) {
            soortAdministratieveHandeling = SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND;
        } else {
            soortAdministratieveHandeling = SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND;
        }
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(partij, soortAdministratieveHandeling, datumTijdLaatsteWijziging);
        final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, hoofdPersoonEntiteit, administratieveHandeling, datumTijdLaatsteWijziging);
        hoofdPersoonEntiteit.addPersoonAfgeleidAdministratiefHistorie(afgeleidAdministratiefHistorie);

        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(hoofdPersoonEntiteit);
        idHistorie.setAdministratienummer(HOOFD_PERSOON_ANUMMER);
        idHistorie.setBurgerservicenummer(HOOFD_PERSOON_BSN);
        hoofdPersoonEntiteit.addPersoonIDHistorie(idHistorie);

        hoofdPersoon = BijhoudingPersoon.decorate(hoofdPersoonEntiteit);

        when(objectSleutelIndex.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, HOOFD_PERSOON_OBJECT_SLEUTEL)).thenReturn(hoofdPersoon);
    }

    private void setupGerelateerdePersoon() throws OngeldigeObjectSleutelException {
        final Persoon gerelateerdePersoonEntiteit = new Persoon(SoortPersoon.INGESCHREVENE);
        gerelateerdePersoonEntiteit.setId(2L);
        gerelateerdePersoonEntiteit.setBurgerservicenummer(GERELATEERDE_PERSOON_BSN);

        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(gerelateerdePersoonEntiteit, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        gerelateerdePersoonEntiteit.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, datumTijdLaatsteWijziging);
        final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, gerelateerdePersoonEntiteit, administratieveHandeling, datumTijdLaatsteWijziging);
        gerelateerdePersoonEntiteit.addPersoonAfgeleidAdministratiefHistorie(afgeleidAdministratiefHistorie);

        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(gerelateerdePersoonEntiteit);
        idHistorie.setAdministratienummer(GERELATEERDE_PERSOON_ANUMMER);
        idHistorie.setBurgerservicenummer(GERELATEERDE_PERSOON_BSN);
        gerelateerdePersoonEntiteit.addPersoonIDHistorie(idHistorie);

        gerelateerdePersoon = BijhoudingPersoon.decorate(gerelateerdePersoonEntiteit);
    }

    @Test
    public void geenBezienVanuitPersoonAanwezig() throws OngeldigeObjectSleutelException {
        bericht.getAdministratieveHandeling().getBezienVanuitPersonen().clear();

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);
        final Set<BijhoudingPersoon> betrokkenPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();

        assertNotNull(bijhoudingsplanContext);
        assertNotNull(betrokkenPersonen);
        assertEquals(1, betrokkenPersonen.size());

        assertEquals(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testBijhoudingSituatieVerhuizingBrpNaarBrp() throws OngeldigeObjectSleutelException {
        final Partij nieuweBrpPartij = new Partij("nieuwe partij", "000000");
        nieuweBrpPartij.setDatumOvergangNaarBrp(DatumUtil.vandaag());
        final Gemeente nieuweGemeente = new Gemeente((short) 0, "nieuwe nieuweGemeente", "1234", nieuweBrpPartij);
        when(dynamischeStamtabelRepository.getGemeenteByGemeentecode("1234")).thenReturn(nieuweGemeente);

        partij.setDatumOvergangNaarBrp(DatumUtil.vandaag());
        setupHoofdPersoon(true, false);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(berichtVerhuizing);
        final Set<BijhoudingPersoon> betrokkenPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanContext);
        assertNotNull(betrokkenPersonen);
        assertEquals(1, betrokkenPersonen.size());

        assertEquals(BijhoudingSituatie.MEDEDELING, hoofdPersoon.getBijhoudingSituatie());
        assertEquals(partij, hoofdPersoon.getBijhoudingspartijVoorBijhoudingsplan());
    }

    @Test
    public void testBijhoudingSituatieVerhuizingGbaNaarBrp() throws OngeldigeObjectSleutelException {
        final Partij nieuweBrpPartij = new Partij("nieuwe partij", "000000");
        nieuweBrpPartij.setDatumOvergangNaarBrp(DatumUtil.vandaag());
        final Gemeente nieuweGemeente = new Gemeente((short) 0, "nieuwe nieuweGemeente", "1234", nieuweBrpPartij);
        when(dynamischeStamtabelRepository.getGemeenteByGemeentecode("1234")).thenReturn(nieuweGemeente);

        partij.setDatumOvergangNaarBrp(null);
        setupHoofdPersoon(true, false);

        final AdministratieveHandelingElement
                gbaVerhuizingHandeling =
                new AdministratieveHandelingElement(berichtVerhuizing.getAdministratieveHandeling().getAttributen(),
                        AdministratieveHandelingElementSoort.GBA_VERHUIZING_INTERGEMEENTELIJK_GBA_NAAR_BRP,
                        berichtVerhuizing.getAdministratieveHandeling().getPartijCode(),
                        berichtVerhuizing.getAdministratieveHandeling().getToelichtingOntlening(),
                        berichtVerhuizing.getAdministratieveHandeling().getBezienVanuitPersonen(),
                        berichtVerhuizing.getAdministratieveHandeling().getGedeblokkeerdeMeldingen(),
                        berichtVerhuizing.getAdministratieveHandeling().getBronnen(),
                        berichtVerhuizing.getAdministratieveHandeling().getActies());

        final BijhoudingVerzoekBericht
                gbaBerichtVerhuizing =
                new BijhoudingVerzoekBerichtImpl(berichtVerhuizing.getAttributen(), berichtVerhuizing.getSoort(), berichtVerhuizing.getStuurgegevens(),
                        berichtVerhuizing.getParameters(), gbaVerhuizingHandeling);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(gbaBerichtVerhuizing);
        final Set<BijhoudingPersoon> betrokkenPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanContext);
        assertNotNull(betrokkenPersonen);
        assertEquals(1, betrokkenPersonen.size());

        assertEquals(BijhoudingSituatie.MEDEDELING, hoofdPersoon.getBijhoudingSituatie());
        assertEquals(nieuweBrpPartij, hoofdPersoon.getBijhoudingspartijVoorBijhoudingsplan());
    }


    @Test
    public void testBijhoudingSituatieVerhuizingBuitenland() throws OngeldigeObjectSleutelException {
        setupHoofdPersoon(false, true);
        AdministratieveHandelingElement orgineel = berichtVerhuizing.getAdministratieveHandeling();
        ElementBuilder elementBuilder = new ElementBuilder();
        ElementBuilder.AdministratieveHandelingParameters
                params =
                new ElementBuilder.AdministratieveHandelingParameters().acties(orgineel.getActies()).bezienVanuitPersonen(orgineel.getBezienVanuitPersonen())
                        .bronnen(orgineel.getBronnen()).partijCode(orgineel.getPartijCode().getWaarde())
                        .soort(AdministratieveHandelingElementSoort.VERHUIZING_NAAR_BUITENLAND);

        final AdministratieveHandelingElement
                administratieveHandelingElement =
                elementBuilder.maakAdministratieveHandelingElement(orgineel.getCommunicatieId(), params);
        administratieveHandelingElement.setVerzoekBericht(berichtVerhuizingNaarBuitenland);
        when(berichtVerhuizingNaarBuitenland.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(berichtVerhuizingNaarBuitenland);
        final Set<BijhoudingPersoon> betrokkenPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanContext);
        assertNotNull(betrokkenPersonen);
        assertEquals(1, betrokkenPersonen.size());

        assertEquals(BijhoudingSituatie.MEDEDELING, hoofdPersoon.getBijhoudingSituatie());

    }

    private void voegPersoonToeAanBezienVanuitPersoon(final String commIdBzuPersoon1, final String objectSleutelHoofdPersoon) {
        final PersoonGegevensElement bzvuPersoon1 = maakBezienVanuitElement(objectSleutelHoofdPersoon, commIdBzuPersoon1);
        bericht.getAdministratieveHandeling().getBezienVanuitPersonen().add(bzvuPersoon1);
    }

    @Test
    public void testVerzamelenAlleBetrokkenGerelateerdenGerelateerdePseeudoPersoon() {
        final Persoon pseudoPersoon = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        voegGerelateerdeToeAanHoofdPersoon(pseudoPersoon);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);
        assertEquals(ZENDENDE_PARTIJ_CODE, String.valueOf(bericht.getZendendePartij().getCode()));

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());
    }

    @Test
    public void testVerzamelenAlleBetrokkenGerelateerdenBetrokkenheidHeeftGeenPersoon() {
        voegGerelateerdeToeAanHoofdPersoon(null);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);
        assertEquals(ZENDENDE_PARTIJ_CODE, String.valueOf(bericht.getZendendePartij().getCode()));

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());
    }

    @Test
    public void testBijhoudingSituatieUitgeslotenGerelateerde() throws OngeldigeObjectSleutelException {
        voegPersoonToeAanBezienVanuitPersoon("comId.bzvu.persoon1", HOOFD_PERSOON_OBJECT_SLEUTEL);
        voegGerelateerdeToeAanHoofdPersoon(gerelateerdePersoon);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);
        assertEquals(ZENDENDE_PARTIJ_CODE, String.valueOf(bericht.getZendendePartij().getCode()));

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(2, bijhoudingsplanPersonen.size());

        assertEquals(
                BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ,
                getBijhoudingPersoonUitContext(bijhoudingsplanContext, HOOFD_PERSOON_BSN).getBijhoudingSituatie());
        assertEquals(
                BijhoudingSituatie.UITGESLOTEN_PERSOON,
                getBijhoudingPersoonUitContext(bijhoudingsplanContext, GERELATEERDE_PERSOON_BSN).getBijhoudingSituatie());
    }

    @Test
    public void testAdministratieveHandelingAsymmetrisch_gerelateerdeIsIngeschrevene() {
        final BijhoudingVerzoekBericht verzoekBericht = maakBerichtVoorAsymmetrischeHandeling(true);

        final BijhoudingsplanContext context = bijhoudingsplanService.maakBijhoudingsplan(verzoekBericht);

        assertNotNull(context);
        final Set<BijhoudingPersoon> personenUitHetBijhoudingsplan = context.getPersonenUitHetBijhoudingsplan();
        assertEquals(2, personenUitHetBijhoudingsplan.size());
        assertEquals(1, context.getPersonenUitHetBijhoudingsplanDieVerwerktMoetenWorden().size());
        for (final BijhoudingPersoon bijhoudingPersoon : personenUitHetBijhoudingsplan) {
            if (bijhoudingPersoon.getId() == 1L) {
                assertEquals(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ, bijhoudingPersoon.getBijhoudingSituatie());
            } else if (bijhoudingPersoon.getId() == 2L) {
                assertEquals(BijhoudingSituatie.UITGESLOTEN_PERSOON, bijhoudingPersoon.getBijhoudingSituatie());
            } else {
                fail("Onbekende persoon in het bijhoudingsplan");
            }
        }
    }

    @Test
    public void testAdministratieveHandelingAsymmetrisch_gerelateerdeIsPseudo() {
        final BijhoudingVerzoekBericht verzoekBericht = maakBerichtVoorAsymmetrischeHandeling(false);

        final BijhoudingsplanContext context = bijhoudingsplanService.maakBijhoudingsplan(verzoekBericht);

        assertNotNull(context);
        final Set<BijhoudingPersoon> personenUitHetBijhoudingsplan = context.getPersonenUitHetBijhoudingsplan();
        assertEquals(1, personenUitHetBijhoudingsplan.size());
        assertEquals(1, context.getPersonenUitHetBijhoudingsplanDieVerwerktMoetenWorden().size());
        for (final BijhoudingPersoon bijhoudingPersoon : personenUitHetBijhoudingsplan) {
            if (bijhoudingPersoon.getId() == 1L) {
                assertEquals(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ, bijhoudingPersoon.getBijhoudingSituatie());
            } else {
                fail("Onbekende persoon in het bijhoudingsplan");
            }
        }
    }

    private BijhoudingVerzoekBericht maakBerichtVoorAsymmetrischeHandeling(final boolean isPartnerIngeschrevene) {
        final ElementBuilder builder = new ElementBuilder();
        final BijhoudingVerzoekBericht verzoekBericht = mock(BijhoudingVerzoekBericht.class);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();

        final String persoonObjectSleutel = "123";
        final PersoonRelatieElement persoonRelatie =
                builder.maakPersoonRelatieElement("persoonRelatie", null, persoonObjectSleutel, Collections.emptyList());
        persoonRelatie.setVerzoekBericht(verzoekBericht);
        final RegistratieIdentificatienummersGerelateerdeActieElement actie =
                builder.maakRegistratieIdentificatienummersGerelateerdeActieElement("actie", 2016_01_01, persoonRelatie);
        ahParams.acties(Collections.singletonList(actie));
        ahParams.soort(AdministratieveHandelingElementSoort.WIJZIGING_PARTNERGEGEVENS_HUWELIJK);
        ahParams.partijCode(ZENDENDE_PARTIJ_CODE);
        final AdministratieveHandelingElement administratieveHandeling = builder.maakAdministratieveHandelingElement("ah", ahParams);
        when(verzoekBericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        when(verzoekBericht.getZendendePartij()).thenReturn(partij);

        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final Betrokkenheid partnerBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        relatie.addBetrokkenheid(ikBetrokkenheid);
        relatie.addBetrokkenheid(partnerBetrokkenheid);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId(1L);
        persoon.addBetrokkenheid(ikBetrokkenheid);
        persoon.addPersoonBijhoudingHistorie(new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        final PersoonIDHistorie persoonIDHistorie = new PersoonIDHistorie(persoon);
        persoonIDHistorie.setBurgerservicenummer("123456789");
        persoon.addPersoonIDHistorie(persoonIDHistorie);

        final Persoon partner = isPartnerIngeschrevene ? new Persoon(SoortPersoon.INGESCHREVENE) : new Persoon(SoortPersoon.PSEUDO_PERSOON);
        partner.setId(2L);
        partner.addPersoonBijhoudingHistorie(new PersoonBijhoudingHistorie(partner, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        partner.addBetrokkenheid(partnerBetrokkenheid);
        final PersoonIDHistorie partnerIDHistorie = new PersoonIDHistorie(partner);
        partnerIDHistorie.setBurgerservicenummer("987654321");
        partner.addPersoonIDHistorie(partnerIDHistorie);

        when(verzoekBericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonObjectSleutel)).thenReturn(BijhoudingPersoon.decorate(persoon));
        when(verzoekBericht.isPrevalidatie()).thenReturn(false);
        return verzoekBericht;
    }

    private void voegGerelateerdeToeAanHoofdPersoon(final Persoon anderePersoon) {
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid hoofdPersoonBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final Betrokkenheid gerelateerdePersoonBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);

        relatie.addBetrokkenheid(gerelateerdePersoonBetrokkenheid);
        relatie.addBetrokkenheid(hoofdPersoonBetrokkenheid);

        hoofdPersoon.addBetrokkenheid(hoofdPersoonBetrokkenheid);

        if (anderePersoon != null) {
            anderePersoon.addBetrokkenheid(gerelateerdePersoonBetrokkenheid);
        }
    }

    @Test
    public void persoonIsOverledenEnGerelateerdePersoon() {
        voegPersoonToeAanBezienVanuitPersoon("comId.bzvu.person2", GERELATEERDE_PERSOON_OBJECT_SLEUTEL);
        voegGerelateerdeToeAanHoofdPersoon(gerelateerdePersoon);
        gerelateerdePersoon.getPersoonBijhoudingHistorieSet().iterator().next().setNadereBijhoudingsaard(NadereBijhoudingsaard.OVERLEDEN);
        gerelateerdePersoon.addPersoonOverlijdenHistorie(
                new PersoonOverlijdenHistorie(gerelateerdePersoon, 20160321, new LandOfGebied("6030", "Nederland")));
        when(objectSleutelIndex.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, GERELATEERDE_PERSOON_OBJECT_SLEUTEL)).thenReturn(
                gerelateerdePersoon);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);
        assertEquals(ZENDENDE_PARTIJ_CODE, String.valueOf(bericht.getZendendePartij().getCode()));

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(2, bijhoudingsplanPersonen.size());

        assertEquals(
                BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ,
                getBijhoudingPersoonUitContext(bijhoudingsplanContext, HOOFD_PERSOON_BSN).getBijhoudingSituatie());
        assertEquals(
                BijhoudingSituatie.OPGESCHORT,
                getBijhoudingPersoonUitContext(bijhoudingsplanContext, GERELATEERDE_PERSOON_BSN).getBijhoudingSituatie());
    }

    @Test
    public void persoonIsOverledenEnGerelateerdePersoonLaterOverledenDanPeildatum() {
        voegPersoonToeAanBezienVanuitPersoon("comId.bzvu.person2", GERELATEERDE_PERSOON_OBJECT_SLEUTEL);
        voegGerelateerdeToeAanHoofdPersoon(gerelateerdePersoon);
        gerelateerdePersoon.getPersoonBijhoudingHistorieSet().iterator().next().setNadereBijhoudingsaard(NadereBijhoudingsaard.OVERLEDEN);
        gerelateerdePersoon.addPersoonOverlijdenHistorie(
                new PersoonOverlijdenHistorie(gerelateerdePersoon, 20160421, new LandOfGebied("6030", "Nederland")));
        when(objectSleutelIndex.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, GERELATEERDE_PERSOON_OBJECT_SLEUTEL)).thenReturn(
                gerelateerdePersoon);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);
        assertEquals(ZENDENDE_PARTIJ_CODE, String.valueOf(bericht.getZendendePartij().getCode()));

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(2, bijhoudingsplanPersonen.size());

        assertEquals(
                BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ,
                getBijhoudingPersoonUitContext(bijhoudingsplanContext, HOOFD_PERSOON_BSN).getBijhoudingSituatie());
        assertEquals(
                BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ,
                getBijhoudingPersoonUitContext(bijhoudingsplanContext, GERELATEERDE_PERSOON_BSN).getBijhoudingSituatie());
    }

    @Test
    public void persoonIsOverledenEnHoofdPersoon() {
        voegPersoonToeAanBezienVanuitPersoon("comId.bzvu.person2", GERELATEERDE_PERSOON_OBJECT_SLEUTEL);
        voegGerelateerdeToeAanHoofdPersoon(gerelateerdePersoon);
        when(objectSleutelIndex.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, GERELATEERDE_PERSOON_OBJECT_SLEUTEL)).thenReturn(
                gerelateerdePersoon);
        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setNadereBijhoudingsaard(NadereBijhoudingsaard.OVERLEDEN);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);
        assertEquals(ZENDENDE_PARTIJ_CODE, String.valueOf(bericht.getZendendePartij().getCode()));

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(2, bijhoudingsplanPersonen.size());

        assertEquals(
                BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ,
                getBijhoudingPersoonUitContext(bijhoudingsplanContext, HOOFD_PERSOON_BSN).getBijhoudingSituatie());
        assertEquals(
                BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ,
                getBijhoudingPersoonUitContext(bijhoudingsplanContext, GERELATEERDE_PERSOON_BSN).getBijhoudingSituatie());
    }

    @Test
    public void testPartijVanPersoon() {
        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);
        assertEquals(ZENDENDE_PARTIJ_CODE, String.valueOf(bericht.getZendendePartij().getCode()));

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.GBA, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testStelselBijhoudingsPartijGBAMorgenNaarBrp() {
        final LocalDate vandaag = LocalDate.now();
        final LocalDate morgen = vandaag.plusDays(1);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        final Integer datumOvergangNaarBrp = Integer.valueOf(formatter.format(morgen));

        anderePartij.setDatumOvergangNaarBrp(datumOvergangNaarBrp);

        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.GBA, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testStelselBijhoudingsPartijGBAVandaagNaarBrp() {
        anderePartij.setDatumOvergangNaarBrp(DatumUtil.vandaag());

        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.AUTOMATISCHE_FIAT, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testStelselBijhoudingsPartijGBAGisterenNaarBrp() {
        final LocalDate vandaag = LocalDate.now();
        final LocalDate morgen = vandaag.plusDays(-1);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        final Integer datumOvergangNaarBrp = Integer.valueOf(formatter.format(morgen));
        anderePartij.setDatumOvergangNaarBrp(datumOvergangNaarBrp);

        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.AUTOMATISCHE_FIAT, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testAutomatischeFiatInstellingIsFalse() {
        anderePartij.setDatumOvergangNaarBrp(DatumUtil.vandaag());
        anderePartij.setIndicatieAutomatischFiatteren(false);

        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.OPNIEUW_INDIENEN, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testAutomatischeFiatInstellingIsTrue() {
        anderePartij.setDatumOvergangNaarBrp(DatumUtil.vandaag());
        anderePartij.setIndicatieAutomatischFiatteren(true);

        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.AUTOMATISCHE_FIAT, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testFiaterringsuitzonderingOpnieuwIndienen() {
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen =
                getBijhouderFiatteringsuitzonderingen(
                        anderePartij,
                        partij,
                        null,
                        soortDocument,
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND);
        when(fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(any())).thenReturn(bijhouderFiatteringsuitzonderingen);
        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.OPNIEUW_INDIENEN, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testFiaterringsuitzonderingGeenBijhouderGevonden() {
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen =
                getBijhouderFiatteringsuitzonderingen(
                        partij,
                        partij,
                        null,
                        soortDocument,
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND);
        when(fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(partij)).thenReturn(bijhouderFiatteringsuitzonderingen);
        anderePartij.setDatumOvergangNaarBrp(01012016);
        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.AUTOMATISCHE_FIAT, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testFiaterringsuitzonderingSoortDocKomtnietovereen() {
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen =
                getBijhouderFiatteringsuitzonderingen(
                        anderePartij,
                        partij,
                        null,
                        new SoortDocument("someDoc", "someDoc"),
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND);
        when(fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(any())).thenReturn(bijhouderFiatteringsuitzonderingen);
        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.AUTOMATISCHE_FIAT, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testFiaterringsuitzonderingSoortAdmHand() {
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen =
                getBijhouderFiatteringsuitzonderingen(anderePartij, partij, null, soortDocument, SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING);
        when(fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(any())).thenReturn(bijhouderFiatteringsuitzonderingen);
        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.AUTOMATISCHE_FIAT, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testFiaterringsuitzonderingGeblokkeerd() {
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen =
                getBijhouderFiatteringsuitzonderingen(
                        anderePartij,
                        partij,
                        Boolean.TRUE,
                        soortDocument,
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND);
        when(fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(any())).thenReturn(bijhouderFiatteringsuitzonderingen);
        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.AUTOMATISCHE_FIAT, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testFiaterringsuitzonderingSoortAdmHandSoortDocbijhoudervoorstelLeeg() {
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen =
                getBijhouderFiatteringsuitzonderingen(anderePartij, null, null, null, null);
        when(fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(any())).thenReturn(bijhouderFiatteringsuitzonderingen);
        hoofdPersoon.getPersoonBijhoudingHistorieSet().iterator().next().setPartij(anderePartij);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

        assertNotNull(bijhoudingsplanContext);

        final Set<BijhoudingPersoon> bijhoudingsplanPersonen = bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan();
        assertNotNull(bijhoudingsplanPersonen);
        assertEquals(1, bijhoudingsplanPersonen.size());

        assertEquals(BijhoudingSituatie.OPNIEUW_INDIENEN, hoofdPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testGeboorteInschrijving_Ok() {
        final String gemeenteCode = "1234";
        final Gemeente nieuweGemeente = new Gemeente((short) 0, "nieuwe nieuweGemeente", gemeenteCode, partij);
        when(dynamischeStamtabelRepository.getGemeenteByGemeentecode("1234")).thenReturn(nieuweGemeente);
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);

        final ElementBuilder builder = new ElementBuilder();

        final Persoon ouderEntiteit = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonBijhoudingHistorie
                bijhoudingHistorie =
                new PersoonBijhoudingHistorie(ouderEntiteit, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        ouderEntiteit.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final String objectsleutel = "1234";
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(BijhoudingPersoon.decorate(ouderEntiteit));

        final PersoonGegevensElement ouder = builder.maakPersoonGegevensElement("ouder", objectsleutel);
        ouder.setVerzoekBericht(bericht);
        final OuderschapElement ouderschap = builder.maakOuderschapElement("ouderschap", true);
        final BetrokkenheidElement
                ouderBetrokkenheid =
                builder.maakBetrokkenheidElement("ouderBetrokkenheid", BetrokkenheidElementSoort.OUDER, ouder, ouderschap);

        final ElementBuilder.GeboorteParameters geboorteParams = new ElementBuilder.GeboorteParameters();
        geboorteParams.gemeenteCode(String.valueOf(gemeenteCode));
        geboorteParams.datum(2016_01_01);
        final ElementBuilder.PersoonParameters kindParams = new ElementBuilder.PersoonParameters();
        kindParams.geboorte(builder.maakGeboorteElement("geboorte", geboorteParams));
        kindParams.geslachtsnaamcomponenten(
                Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("geslachtsnaamcomp", null, null, null, null, "stam")));
        final PersoonGegevensElement kind = builder.maakPersoonGegevensElement("kind", null, null, kindParams);
        final BetrokkenheidElement kindBetrokkenheid = builder.maakBetrokkenheidElement("kindBetrokkenheid", BetrokkenheidElementSoort.KIND, kind, null);

        final FamilierechtelijkeBetrekkingElement
                fbrElement =
                builder.maakFamilierechtelijkeBetrekkingElement("fbr", null, Arrays.asList(kindBetrokkenheid, ouderBetrokkenheid));

        final ActieElement geboreneActie = builder.maakRegistratieGeboreneActieElement("geborene", 2016_01_01, fbrElement);
        final ElementBuilder.PersoonParameters identPersParams = new ElementBuilder.PersoonParameters();
        identPersParams.identificatienummers(builder.maakIdentificatienummersElement("identNrs", "123456789", "1234567890"));
        final PersoonGegevensElement identPersElement = builder.maakPersoonGegevensElement("identPers", null, "kind", identPersParams);
        final ActieElement identNummersActie = builder.maakRegistratieIdentificatienummersActieElement("identnrs", 2016_01_01, identPersElement);

        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        ahParams.partijCode(ZENDENDE_PARTIJ_CODE);
        ahParams.acties(Arrays.asList(geboreneActie, identNummersActie));
        final AdministratieveHandelingElement administratieveHandeling = builder.maakAdministratieveHandelingElement("ah", ahParams);

        when(bericht.getZendendePartij()).thenReturn(partij);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);
        assertEquals(partij, bijhoudingsplanContext.getGbaGeboortePartij());
        assertEquals(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ, kind.getPersoonEntiteit().getBijhoudingSituatie());
    }

    @Test
    public void testGeboorteInschrijving_OpnieuwIndienen() {
        final String gemeenteCode = "1234";
        final Gemeente nieuweGemeente = new Gemeente((short) 0, "nieuwe nieuweGemeente", gemeenteCode, partij);
        partij.setDatumOvergangNaarBrp(2015_01_01);
        when(dynamischeStamtabelRepository.getGemeenteByGemeentecode("1234")).thenReturn(nieuweGemeente);
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);

        final ElementBuilder builder = new ElementBuilder();

        final Persoon ouderEntiteit = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonBijhoudingHistorie
                bijhoudingHistorie =
                new PersoonBijhoudingHistorie(ouderEntiteit, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        ouderEntiteit.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final String objectsleutel = "1234";
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(BijhoudingPersoon.decorate(ouderEntiteit));

        final PersoonGegevensElement ouder = builder.maakPersoonGegevensElement("ouder", objectsleutel);
        ouder.setVerzoekBericht(bericht);
        final OuderschapElement ouderschap = builder.maakOuderschapElement("ouderschap", true);
        final BetrokkenheidElement ouderBetrokkenheid =
                builder.maakBetrokkenheidElement("ouderBetrokkenheid", BetrokkenheidElementSoort.OUDER, ouder, ouderschap);

        final ElementBuilder.GeboorteParameters geboorteParams = new ElementBuilder.GeboorteParameters();
        geboorteParams.gemeenteCode(String.valueOf(gemeenteCode));
        geboorteParams.datum(2016_01_01);

        final ElementBuilder.PersoonParameters kindParams = new ElementBuilder.PersoonParameters();
        kindParams.geboorte(builder.maakGeboorteElement("geboorte", geboorteParams));
        kindParams.geslachtsnaamcomponenten(
                Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("geslachtsnaamcomp", null, null, null, null, "stam")));

        final PersoonGegevensElement kind = builder.maakPersoonGegevensElement("kind", null, null, kindParams);
        final BetrokkenheidElement kindBetrokkenheid = builder.maakBetrokkenheidElement("kindBetrokkenheid", BetrokkenheidElementSoort.KIND, kind, null);

        final FamilierechtelijkeBetrekkingElement
                fbrElement =
                builder.maakFamilierechtelijkeBetrekkingElement("fbr", null, Arrays.asList(kindBetrokkenheid, ouderBetrokkenheid));

        final ActieElement geboreneActie = builder.maakRegistratieGeboreneActieElement("geborene", 2016_01_01, fbrElement);

        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        ahParams.partijCode(ZENDENDE_PARTIJ_CODE);
        ahParams.acties(Collections.singletonList(geboreneActie));
        final AdministratieveHandelingElement administratieveHandeling = builder.maakAdministratieveHandelingElement("ah", ahParams);

        when(bericht.getZendendePartij()).thenReturn(partij);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);

        final BijhoudingsplanContext bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);
        assertNull(bijhoudingsplanContext.getGbaGeboortePartij());
        assertEquals(BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN, kind.getPersoonEntiteit().getBijhoudingSituatie());
    }

    private List<BijhouderFiatteringsuitzondering> getBijhouderFiatteringsuitzonderingen(
            final Partij bijhouder,
            final Partij bijhouderVoorstel,
            final Boolean indicatieGeblokkeerd,
            final SoortDocument soortDocument,
            final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        bijhouder.setDatumOvergangNaarBrp(01012016);
        final PartijRol partijRolBijhouder = new PartijRol(bijhouder, Rol.BIJHOUDINGSORGAAN_COLLEGE);
        partijRolBijhouder.addPartijBijhoudingHistorie(new PartijRolHistorie(partijRolBijhouder, new Timestamp(System.currentTimeMillis()), 20150101));
        bijhouder.addPartijRol(partijRolBijhouder);
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen = new ArrayList<>();
        BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering = new BijhouderFiatteringsuitzondering(partijRolBijhouder);
        final BijhouderFiatteringsuitzonderingHistorie bijhouderFiatteringsuitzonderingHistorie =
                new BijhouderFiatteringsuitzonderingHistorie(bijhouderFiatteringsuitzondering, new Timestamp(System.currentTimeMillis()), 20150101);
        if (bijhouderVoorstel != null) {
            final PartijRol partijRolBijhouderVoorstel = new PartijRol(bijhouderVoorstel, Rol.BIJHOUDINGSORGAAN_COLLEGE);
            partijRolBijhouderVoorstel.addPartijBijhoudingHistorie(
                    new PartijRolHistorie(partijRolBijhouderVoorstel, new Timestamp(System.currentTimeMillis()), 20150101));
            bijhouderVoorstel.addPartijRol(partijRolBijhouderVoorstel);
            bijhouderFiatteringsuitzonderingHistorie.setBijhouderBijhoudingsvoorstel(partijRolBijhouderVoorstel);
        }
        bijhouderFiatteringsuitzonderingHistorie.setIndicatieGeblokkeerd(indicatieGeblokkeerd);
        bijhouderFiatteringsuitzonderingHistorie.setSoortAdministratieveHandeling(soortAdministratieveHandeling);
        bijhouderFiatteringsuitzonderingHistorie.setSoortDocument(soortDocument);
        final Set<BijhouderFiatteringsuitzonderingHistorie> bijhouderFiatteringsuitzonderingHistorieSet = new HashSet<>();
        bijhouderFiatteringsuitzondering.setBijhouderFiatteringsuitzonderingHistorieSet(bijhouderFiatteringsuitzonderingHistorieSet);
        bijhouderFiatteringsuitzondering.addBijhouderFiatteringsuitzonderingHistorie(bijhouderFiatteringsuitzonderingHistorie);
        bijhouderFiatteringsuitzonderingen.add(bijhouderFiatteringsuitzondering);
        return bijhouderFiatteringsuitzonderingen;
    }

    private PersoonGegevensElement maakBezienVanuitElement(final String objectSleutel, final String commId) {
        final Map<String, String> bzvuElementAttributen =
                new AbstractBmrGroep.AttributenBuilder().objecttype("BezienVanuitPersoon").objectSleutel(objectSleutel).communicatieId(commId).build();
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        return builder.maakPersoonGegevensElement(bzvuElementAttributen, params);
    }

    private BijhoudingPersoon getBijhoudingPersoonUitContext(final BijhoudingsplanContext bijhoudingsplanContext, final String bsn) {
        for (BijhoudingPersoon bijhoudingPersoon : bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan()) {
            if (bsn.equals(bijhoudingPersoon.getBurgerservicenummer())) {
                return bijhoudingPersoon;
            }
        }
        return null;
    }
}
