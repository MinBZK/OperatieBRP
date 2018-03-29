/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruikSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.OnderzoekRepository;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;

/**
 * Abstract test class voor de unittests van de elementen.
 */
@RunWith(MockitoJUnitRunner.class)
abstract class AbstractElementTest {
    static final String SOORT_DOC_NAAM_HUWELIJK = "Huwelijksakte";

    static final Partij Z_PARTIJ = new Partij("partij", "000001");
    static final Nationaliteit NEDERLANDS = new Nationaliteit("NL", "0001");
    static final String COMM_ID_KIND = "kind";
    static final String OBJECT_SLEUTEL_OUDER = "123456";


    @Mock
    private ApplicationContext context;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private OnderzoekRepository onderzoekRepository;
    @Mock
    private ObjectSleutelService objectSleutelService;
    @Mock
    private ObjectSleutelIndex objectSleutelIndex;

    @Mock
    private BijhoudingVerzoekBericht berichtPrevalidatie;
    @Mock
    private BijhoudingVerzoekBericht bericht;
    @Mock
    private BijhoudingVerzoekBericht uitgebreidBericht;

    private BRPActie actie;
    private AdministratieveHandeling administratieveHandeling;
    private StuurgegevensElement stuurgegevensElement;

    @Before
    public void setupAbstractElementTest() {
        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        when(context.getBean(DynamischeStamtabelRepository.class)).thenReturn(dynamischeStamtabelRepository);
        when(context.getBean(ObjectSleutelService.class)).thenReturn(objectSleutelService);
        when(context.getBean(PersoonRepository.class)).thenReturn(persoonRepository);
        when(context.getBean(OnderzoekRepository.class)).thenReturn(onderzoekRepository);

        administratieveHandeling =
                new AdministratieveHandeling(new Partij("test partij", "053001"), SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, new Timestamp(
                    System.currentTimeMillis()));
        actie =
                new BRPActie(
                    SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                    administratieveHandeling,
                    administratieveHandeling.getPartij(),
                    administratieveHandeling.getDatumTijdRegistratie());

        when(berichtPrevalidatie.getAdministratieveHandeling()).thenReturn(maakAdministratieveHandelingElement(berichtPrevalidatie));
        when(bericht.getAdministratieveHandeling()).thenReturn(maakAdministratieveHandelingElement(bericht));
        when(uitgebreidBericht.getAdministratieveHandeling()).thenReturn(maakAdministratieveHandelingElement(uitgebreidBericht));

        final DatumTijdElement tijdstipOntvangst = new DatumTijdElement(ZonedDateTime.now());
        when(berichtPrevalidatie.getTijdstipOntvangst()).thenReturn(tijdstipOntvangst);
        when(bericht.getTijdstipOntvangst()).thenReturn(tijdstipOntvangst);
        when(uitgebreidBericht.getTijdstipOntvangst()).thenReturn(tijdstipOntvangst);

        final ElementBuilder builder = new ElementBuilder();
        stuurgegevensElement =
                builder.maakStuurgegevensElement("ci_stuurgevens",
                        new ElementBuilder.StuurgegevensParameters().zendendePartij("053001").zendendeSysteem("BZM Leverancier A")
                                .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234+02:00"));

        when(berichtPrevalidatie.getStuurgegevens()).thenReturn(stuurgegevensElement);
        when(bericht.getStuurgegevens()).thenReturn(stuurgegevensElement);
        when(uitgebreidBericht.getStuurgegevens()).thenReturn(stuurgegevensElement);

        final Answer<RootEntiteit> linkNaarObjectSleutelIndexMock = invocationOnMock -> {
            Class<? extends RootEntiteit> entiteitType = (Class<? extends RootEntiteit>) invocationOnMock.getArguments()[0];
            final String objectSleutel = (String) invocationOnMock.getArguments()[1];
            return getObjectSleutelIndex().getEntiteitVoorObjectSleutel(entiteitType, objectSleutel);
        };

        when(berichtPrevalidatie.getEntiteitVoorObjectSleutel(any(Class.class), anyString())).then(linkNaarObjectSleutelIndexMock);
        when(bericht.getEntiteitVoorObjectSleutel(any(Class.class), anyString())).then(linkNaarObjectSleutelIndexMock);
        when(uitgebreidBericht.getEntiteitVoorObjectSleutel(any(Class.class), anyString())).then(linkNaarObjectSleutelIndexMock);
        definieerWhenStappenVoorAanDuidingVerblijfsRecht();
    }

    ApplicationContext getContext() {
        return context;
    }

    DynamischeStamtabelRepository getDynamischeStamtabelRepository() {
        return dynamischeStamtabelRepository;
    }

    PersoonRepository getPersoonRepository() {
        return persoonRepository;
    }

    OnderzoekRepository getOnderzoekRepository() {
        return onderzoekRepository;
    }

    ObjectSleutelService getObjectSleutelService() {
        return objectSleutelService;
    }

    ObjectSleutelIndex getObjectSleutelIndex() {
        return objectSleutelIndex;
    }

    BijhoudingVerzoekBericht getBericht() {
        return bericht;
    }

    BijhoudingVerzoekBericht getBerichtPrevalidatie() {
        return berichtPrevalidatie;
    }

    BijhoudingVerzoekBericht getUitgebreidBericht() {
        return uitgebreidBericht;
    }

    StuurgegevensElement getStuurgegevensElement() {
        return stuurgegevensElement;
    }

    BRPActie getActie() {
        return actie;
    }

    /**
     * Controleert of het aantal meldingen overeenkomt met het aantal regels dat is meegegeven en of de regel ook in de
     * lijst van meldingen terug komt.
     *
     * @param meldingen de lijst van meldingen uit het systeem
     * @param regels de regels die verwacht worden
     */
    final void controleerRegels(final List<MeldingElement> meldingen, final Regel... regels) {
        final List<Regel> regelList = Arrays.asList(regels);
        String regelCodes = meldingen.stream().map(meldingElement -> meldingElement.getRegel().getCode()).collect(Collectors.joining(","));
        assertEquals(regelCodes,regels.length, meldingen.size());
        for (final MeldingElement melding : meldingen) {
            assertTrue("Melding bevat regel die niet verwacht is: "+regelCodes, regelList.contains(melding.getRegel()));
        }
    }

    final void definieerWhenStappenVoorAanDuidingVerblijfsRecht() {
        final Verblijfsrecht verblijfsRecht = new Verblijfsrecht( "21","Vw 2000 art. 8, onder a, vergunning regulier bepaalde tijd, arbeid vrij");
        when(dynamischeStamtabelRepository.getVerblijfsrechtByCode(any(String.class))).thenReturn(verblijfsRecht);
    }


    final void definieerWhenStappenVoorSoortDocument() {
        final SoortDocument soortDocument = new SoortDocument(SOORT_DOC_NAAM_HUWELIJK, "omschrijving");
        final SoortActieBrongebruikSleutel sleutelHuwelijk =
                new SoortActieBrongebruikSleutel(
                    SoortActie.REGISTRATIE_HUWELIJK,
                    SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                    soortDocument);
        final SoortActieBrongebruikSleutel sleutelRegistratieGeslachtsnaam =
                new SoortActieBrongebruikSleutel(
                    SoortActie.REGISTRATIE_GESLACHTSNAAM,
                    SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                    soortDocument);

        when(dynamischeStamtabelRepository.getSoortDocumentByNaam(SOORT_DOC_NAAM_HUWELIJK)).thenReturn(soortDocument);
        when(dynamischeStamtabelRepository.getSoortActieBrongebruikBySoortActieBrongebruikSleutel(sleutelHuwelijk)).thenReturn(
            new SoortActieBrongebruik(sleutelHuwelijk));
        when(dynamischeStamtabelRepository.getSoortActieBrongebruikBySoortActieBrongebruikSleutel(sleutelRegistratieGeslachtsnaam)).thenReturn(
            new SoortActieBrongebruik(sleutelRegistratieGeslachtsnaam));

    }

    final void assertEntiteitMetFormeleHistorie(final FormeleHistorie entiteit) {
        assertNull(entiteit.getId());
        assertEquals(getActie().getDatumTijdRegistratie(), entiteit.getDatumTijdRegistratie());
        assertEquals(getActie(), entiteit.getActieInhoud());
        assertNull(entiteit.getActieVerval());
        assertNull(entiteit.getDatumTijdVerval());
    }

    final void assertEntiteitMetMaterieleHistorie(final MaterieleHistorie entiteit, int verwachtteDatumAanvang) {
        assertEntiteitMetFormeleHistorie(entiteit);
        assertEquals(verwachtteDatumAanvang, entiteit.getDatumAanvangGeldigheid().intValue());
    }

    AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    private AdministratieveHandelingElement maakAdministratieveHandelingElement(final BijhoudingVerzoekBericht bericht) {
        final ElementBuilder builder = new ElementBuilder();

        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("com_persoon", "objSleutel");
        persoonElement.setVerzoekBericht(bericht);
        BetrokkenheidElement betrokkenheidElement = builder.maakBetrokkenheidElement("com_betrokkenheid", BetrokkenheidElementSoort.PARTNER, persoonElement,
                null);
        RelatieGroepElement
                relatieGroepElement =
                builder.maakRelatieGroepElement("com_relGroep", new ElementBuilder.RelatieGroepParameters().datumAanvang(20160101));
        HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("com_huwelijk", relatieGroepElement, Collections.singletonList(betrokkenheidElement));
        RegistratieAanvangHuwelijkActieElement
                registratieAanvangHuwelijkActieElement =
                builder.maakRegistratieAanvangHuwelijkActieElement("com_ra_huwelijk", 20160101, Collections.emptyList(), huwelijkElement);
        final AdministratieveHandelingElement
                result =
                builder.maakAdministratieveHandelingElement("ci_ah",
                        new ElementBuilder.AdministratieveHandelingParameters().soort(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND)
                                .partijCode("053001").toelichtingOntlening("Test toelichting op de ontlening").bronnen(Collections.emptyList())
                                .acties(Collections.singletonList(registratieAanvangHuwelijkActieElement)));
        result.setVerzoekBericht(bericht);

        return result;
    }

    final RegistratieGeboreneActieElement maakActieGeboreneMetAlleenOuwkig(final int geboorteDatum,final ElementBuilder builder) {
        final ElementBuilder.RelatieGroepParameters relgroepPara = new ElementBuilder.RelatieGroepParameters();
        final RelatieGroepElement relatie = builder.maakRelatieGroepElement("rel"+geboorteDatum, relgroepPara);

        final ElementBuilder.PersoonParameters kindParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters kindGeboorteParameters = new ElementBuilder.GeboorteParameters();
        kindGeboorteParameters.datum(geboorteDatum);
        kindGeboorteParameters.gemeenteCode("2000");
        kindGeboorteParameters.landGebiedCode(LandOfGebied.CODE_NEDERLAND);
        kindParameters.geboorte(builder.maakGeboorteElement("kind_geb"+geboorteDatum, kindGeboorteParameters));
        kindParameters.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("kind_gesl"+geboorteDatum,"M"));

        final ElementBuilder.NaamParameters samenGesteldenaamParameters = new ElementBuilder.NaamParameters();
        samenGesteldenaamParameters.indicatieNamenreeks(false);
        kindParameters.samengesteldeNaam(builder.maakSamengesteldeNaamElement("kind_samen"+geboorteDatum,samenGesteldenaamParameters));

        final GeslachtsnaamcomponentElement geslachtsnaamComponent = builder.maakGeslachtsnaamcomponentElement("kind_ges"+geboorteDatum,null,null,null,null,"Achternaam");
        kindParameters.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamComponent));

        final PersoonGegevensElement kind = builder.maakPersoonGegevensElement(COMM_ID_KIND+geboorteDatum, null, null, kindParameters);
        kind.getPersoonEntiteit().setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        kind.getPersoonEntiteit().setBijhoudingspartijVoorBijhoudingsplan(Z_PARTIJ);

        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        betrokkenheden.add(builder.maakBetrokkenheidElement("b1"+geboorteDatum, BetrokkenheidElementSoort.KIND, kind, null));

        final PersoonGegevensElement ouder = builder.maakPersoonGegevensElement("ouder"+geboorteDatum, OBJECT_SLEUTEL_OUDER);
        ouder.setVerzoekBericht(bericht);

        final OuderschapElement ouderschap = builder.maakOuderschapElement("ouderschap1"+geboorteDatum, true);
        betrokkenheden.add(builder.maakBetrokkenheidElement("o1"+geboorteDatum, BetrokkenheidElementSoort.OUDER, ouder, ouderschap));

        final FamilierechtelijkeBetrekkingElement famBetr = builder.maakFamilierechtelijkeBetrekkingElement("fam"+geboorteDatum, relatie, betrokkenheden);

        return builder.maakRegistratieGeboreneActieElement("geb"+geboorteDatum, geboorteDatum, famBetr);
    }

    RegistratieOuderActieElement maakRegistratieOuderActie(final Integer datumAanvangGeldigheid, final String objectsleutelKind,
                                                           final String referentieSleutelKind) {
        final ElementBuilder builder = new ElementBuilder();
        final PersoonGegevensElement ouder = builder.maakPersoonGegevensElement("ouder", "ouderSleutel");
        ouder.setVerzoekBericht(bericht);
        final BetrokkenheidElement ouderBetrokkenheid = builder.maakBetrokkenheidElement("ouderBetr", BetrokkenheidElementSoort.OUDER, ouder, null);
        ouderBetrokkenheid.setVerzoekBericht(bericht);
        final FamilierechtelijkeBetrekkingElement fbrElement = builder.maakFamilierechtelijkeBetrekkingElement("fbrElement", null, Collections.singletonList(ouderBetrokkenheid));
        final BetrokkenheidElement betrokkenheid = builder.maakBetrokkenheidElement("kindBetr", null, null, BetrokkenheidElementSoort.KIND, fbrElement);
        final PersoonRelatieElement persoonRelatieElement = builder.maakPersoonRelatieElement("pers_relatie_actie", referentieSleutelKind, objectsleutelKind, Collections.singletonList(betrokkenheid));
        persoonRelatieElement.setVerzoekBericht(bericht);
        final RegistratieOuderActieElement ouderActie = builder.maakRegistratieOuderActieElement("ouderActie", datumAanvangGeldigheid, persoonRelatieElement);
        ouderActie.setVerzoekBericht(bericht);
        return ouderActie;
    }

    public BRPActie maakActie() {
        AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(new Partij("test partij", "053001"), SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, new Timestamp(
                        System.currentTimeMillis()));
        return new BRPActie(
                SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                administratieveHandeling,
                administratieveHandeling.getPartij(),
                administratieveHandeling.getDatumTijdRegistratie());
    }

    public void voegBijhoudingsHistorieToe(final Persoon ingeschrevene, final Integer... datumAanvangGeldigheid) {
        int count = 1;
        for (Integer dag : datumAanvangGeldigheid) {
            final Timestamp datumTijdRegistratie = new Timestamp(DatumUtil.nu().getTime()-100000+(count*5));
            final PersoonBijhoudingHistorie
                    historie =
                    new PersoonBijhoudingHistorie(ingeschrevene, Z_PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
            historie.setDatumAanvangGeldigheid(dag);
            historie.setDatumTijdRegistratie(datumTijdRegistratie);
            historie.setActieInhoud(maakActie());
            MaterieleHistorie.voegNieuweActueleToe(historie, ingeschrevene.getPersoonBijhoudingHistorieSet());
            count++;
        }
    }
}
