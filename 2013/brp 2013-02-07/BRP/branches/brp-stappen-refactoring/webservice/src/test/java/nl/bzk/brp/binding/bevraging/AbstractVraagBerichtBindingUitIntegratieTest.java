/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.*;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*;
import nl.bzk.brp.model.bericht.kern.*;
import nl.bzk.brp.model.operationeel.kern.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Abstracte class bedoeld voor het testen van de binding van antwoordberichten op vraag/informatie berichten. Deze
 * class biedt helper methodes om testdata aan te maken welke veelal gebruikt kan worden in de tests en helper methodes
 * voor het opbouwen van het verwachte antwoord.
 */

// TODO reflection omzetten naar setters op Web
public abstract class AbstractVraagBerichtBindingUitIntegratieTest<T> extends AbstractBindingUitIntegratieTest<T> {

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP0100_Bevraging_Berichten.xsd";
    }

    /**
     * Retourneert de naa
     * et bericht element van het bericht dat in de test wordt getest.
     *
     * @return de naam van het bericht element van het bericht dat in de test wordt getest.
     */
    public abstract String getBerichtElementNaam();

    /**
     * Instantieert een {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} en vult deze geheel met test
     * data,
     * welke deels kan word
     * egeven, maar
     * grotendeels in deze methode wordt bepaald.
     *
     * @param id de id van de persoon.
     * @param gemeente de gemeente waartoe de persoon behoord
     * @param plaats de plaats waarin deze persoon woont
     * @param land het land waarin deze persoon woont
     * @param bsn het bsn van de persoon
     * @return de aangemaakte persoon
     * @throws ParseException
     */
    protected PersoonModel maakPersoon(final Integer id, final Partij gemeente, final Plaats plaats, final Land land,
            final String bsn) throws ParseException
    {
        Predikaat predikaat = new TestPredikaat(new PredikaatCode("H"), null, null);

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setSoort(SoortPersoon.INGESCHREVENE);

        // Identificatienummers
        PersoonIdentificatienummersGroepBericht identificatieNummersGroep =
            new PersoonIdentificatienummersGroepBericht();
        identificatieNummersGroep.setBurgerservicenummer(new Burgerservicenummer(bsn));
        identificatieNummersGroep.setAdministratienummer(new ANummer("9876543210"));
        persoonBericht.setIdentificatienummers(identificatieNummersGroep);

        // Geslachtsaanduiding
        PersoonGeslachtsaanduidingGroepBericht geslachtsAanduiding = new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsAanduiding.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        persoonBericht.setGeslachtsaanduiding(geslachtsAanduiding);

        // SamenGesteldeNaam
        AdellijkeTitel adellijkeTitel = new TestAdellijkeTitel(new AdellijkeTitelCode("B"), null, null);

        PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaam.setAdellijkeTitel(adellijkeTitel);

        persoonSamengesteldeNaam.setPredikaat(predikaat);
        persoonSamengesteldeNaam.setVoornamen(new Voornamen("Voornaam"));
        persoonSamengesteldeNaam.setVoorvoegsel(new Voorvoegsel("voor"));
        persoonSamengesteldeNaam.setScheidingsteken(new Scheidingsteken(","));
        persoonSamengesteldeNaam.setGeslachtsnaam(new Geslachtsnaam("geslachtsnaam"));
        persoonSamengesteldeNaam.setIndicatieNamenreeks(JaNee.NEE);
        persoonSamengesteldeNaam.setIndicatieAlgoritmischAfgeleid(JaNee.NEE);
        persoonBericht.setSamengesteldeNaam(persoonSamengesteldeNaam);

        // Aanschrijving
        PersoonAanschrijvingGroepBericht aanschrijving = new PersoonAanschrijvingGroepBericht();
        aanschrijving.setNaamgebruik(WijzeGebruikGeslachtsnaam.EIGEN);
        aanschrijving.setIndicatieTitelsPredikatenBijAanschrijven(JaNee.NEE);
        aanschrijving.setIndicatieAanschrijvingAlgoritmischAfgeleid(JaNee.NEE);
        aanschrijving.setPredikaatAanschrijving(predikaat);
        aanschrijving.setVoornamenAanschrijving(new Voornamen("voornaam"));
        aanschrijving.setVoorvoegselAanschrijving(new Voorvoegsel("voor"));
        aanschrijving.setScheidingstekenAanschrijving(new Scheidingsteken(","));
        aanschrijving.setGeslachtsnaamAanschrijving(new Geslachtsnaam("geslachtsnaam"));
        persoonBericht.setAanschrijving(aanschrijving);

        // Geboorte
        PersoonGeboorteGroepBericht persoonGeboorteGroep = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroep.setDatumGeboorte(new Datum(20120101));
        persoonGeboorteGroep.setGemeenteGeboorte(gemeente);
        persoonGeboorteGroep.setWoonplaatsGeboorte(plaats);
        persoonGeboorteGroep.setBuitenlandsePlaatsGeboorte(new BuitenlandsePlaats("buitenland"));
        persoonGeboorteGroep.setBuitenlandseRegioGeboorte(new BuitenlandseRegio("bregio"));
        persoonGeboorteGroep.setLandGeboorte(land);
        persoonGeboorteGroep.setOmschrijvingLocatieGeboorte(new LocatieOmschrijving("omschrijving"));
        persoonBericht.setGeboorte(persoonGeboorteGroep);

        // Bijhoudingsverwantwoordelijke
        PersoonBijhoudingsaardGroepBericht bijhoudingsaard =
            new PersoonBijhoudingsaardGroepBericht();
        bijhoudingsaard.setBijhoudingsaard(Bijhoudingsaard.REGISTER_NIET_INGEZETENEN);
        persoonBericht.setBijhoudingsaard(bijhoudingsaard);

        // Opschorting
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschortingBijhouding(RedenOpschorting.MINISTERIEEL_BESLUIT);
        persoonBericht.setOpschorting(opschorting);

        // Bijhoudingsgemeente
        PersoonBijhoudingsgemeenteGroepBericht bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepBericht();
        bijhoudingsgemeente.setBijhoudingsgemeente(gemeente);
        bijhoudingsgemeente.setIndicatieOnverwerktDocumentAanwezig(JaNee.NEE);
        bijhoudingsgemeente.setDatumInschrijvingInGemeente(new Datum(20120101));
        persoonBericht.setBijhoudingsgemeente(bijhoudingsgemeente);

        // Inschrijving
        PersoonInschrijvingGroepBericht inschrijving = new PersoonInschrijvingGroepBericht();
        inschrijving.setVersienummer(new Versienummer(1L));
        inschrijving.setDatumInschrijving(new Datum(20101122));
        persoonBericht.setInschrijving(inschrijving);

        // Afgeleid amdministratief
        PersoonAfgeleidAdministratiefGroepBericht afgeleidAdministratief =
            new PersoonAfgeleidAdministratiefGroepBericht();
        afgeleidAdministratief.setIndicatieGegevensInOnderzoek(JaNee.NEE);
        afgeleidAdministratief.setTijdstipLaatsteWijziging(new DatumTijd(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").parse(("2012/06/02 11:04:55:555"))));
        persoonBericht.setAfgeleidAdministratief(afgeleidAdministratief);

        // Voornamen
        PersoonVoornaamStandaardGroepBericht persoonVoornaamStandaardGroepBericht1 =
            new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamStandaardGroepBericht1.setNaam(new Voornaam("voornaam1"));

        PersoonVoornaamBericht persoonVoornaam1 = new PersoonVoornaamBericht();
        persoonVoornaam1.setVolgnummer(new Volgnummer(1));
        persoonVoornaam1.setStandaard(persoonVoornaamStandaardGroepBericht1);

        PersoonVoornaamStandaardGroepBericht persoonVoornaamStandaardGroepBericht2 =
            new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamStandaardGroepBericht2.setNaam(new Voornaam("voornaam2"));

        PersoonVoornaamBericht persoonVoornaam2 = new PersoonVoornaamBericht();
        persoonVoornaam2.setVolgnummer(new Volgnummer(2));
        persoonVoornaam2.setStandaard(persoonVoornaamStandaardGroepBericht2);

        persoonBericht.setVoornamen(Arrays.asList(persoonVoornaam1, persoonVoornaam2));

        // Adressen
        RedenWijzigingAdres rdn = new TestRedenWijzigingAdres(new RedenWijzigingAdresCode("A"), null);

        PersoonAdresStandaardGroepBericht adr = new PersoonAdresStandaardGroepBericht();
        adr.setSoort(FunctieAdres.BRIEFADRES);
        adr.setDatumAanvangAdreshouding(new Datum(20120101));
        adr.setRedenWijziging(rdn);
        adr.setAangeverAdreshouding(new AangeverAdreshouding(new AangeverAdreshoudingCode("G"), null, null)); // GEZAGHOUDER
        adr.setAdresseerbaarObject(new AanduidingAdresseerbaarObject("acd"));
        adr.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("idcode"));
        adr.setGemeente(gemeente);
        adr.setNaamOpenbareRuimte(new NaamOpenbareRuimte("opNaam"));
        adr.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("afg"));
        adr.setGemeentedeel(new Gemeentedeel("gemdeel"));
        adr.setHuisnummer(new Huisnummer("12"));
        adr.setHuisletter(new Huisletter("A"));
        adr.setPostcode(new Postcode("1234AB"));
        adr.setHuisnummertoevoeging(new Huisnummertoevoeging("b"));
        adr.setWoonplaats(plaats);
        adr.setLocatietovAdres(AanduidingBijHuisnummer.BY);
        adr.setLocatieOmschrijving(new LocatieOmschrijving("omschr"));
        adr.setBuitenlandsAdresRegel1(new Adresregel("buitregel1"));
        adr.setBuitenlandsAdresRegel2(new Adresregel("buitregel2"));
        adr.setBuitenlandsAdresRegel3(new Adresregel("buitregel3"));
        adr.setBuitenlandsAdresRegel4(new Adresregel("buitregel4"));
        adr.setBuitenlandsAdresRegel5(new Adresregel("buitregel15"));
        adr.setBuitenlandsAdresRegel6(new Adresregel("buitregel16"));
        adr.setLand(land);
        adr.setDatumVertrekUitNederland(new Datum(20500101));

        PersoonAdresBericht adres = new PersoonAdresBericht();
        adres.setStandaard(adr);
        persoonBericht.setAdressen(Arrays.asList(adres));

        // Nationaliteiten
        Nationaliteit nation =
            new TestNationaliteit(new Nationaliteitcode("31"), new NaamEnumeratiewaarde("abcd"), null, null);

        PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        gegevens.setRedenVerkrijging(new RedenVerkrijgingNLNationaliteit(new RedenVerkrijgingCode("1"), null, null,
                null));

        PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
        persoonNationaliteit.setNationaliteit(nation);
        persoonNationaliteit.setStandaard(gegevens);
        persoonBericht.setNationaliteiten(Arrays.asList(persoonNationaliteit));

        // Geslachtsnaamcomponenten
        AdellijkeTitel titel = new TestAdellijkeTitel(new AdellijkeTitelCode("H"), null, null);

        PersoonGeslachtsnaamcomponentStandaardGroepBericht gegevensComp1 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        gegevensComp1.setPredikaat(predikaat);
        gegevensComp1.setAdellijkeTitel(titel);
        gegevensComp1.setVoorvoegsel(new Voorvoegsel("voorv"));
        gegevensComp1.setScheidingsteken(new Scheidingsteken(";"));
        gegevensComp1.setNaam(new Geslachtsnaamcomponent("comp"));

        PersoonGeslachtsnaamcomponentBericht component1 = new PersoonGeslachtsnaamcomponentBericht();
        component1.setVolgnummer(new Volgnummer(1));
        component1.setStandaard(gegevensComp1);

        /*
         * Volgens nieuwe XSD mag wordt er op dit moment maar 1 geslachtsnaamcomponent ondersteunt
         * PersoonGeslachtsnaamcomponentBericht component2 = new PersoonGeslachtsnaamcomponentBericht();
         * component2.setVolgnummer(new Volgnummer(1));
         */

        /*
         * Predikaat pred2 = new Predikaat(new PredikaatCode("H"), null, null);
         *
         * AdellijkeTitel titel2 = new TestAdellijkeTitel(new AdellijkeTitelCode("H"), null, null);
         * PersoonGeslachtsnaamcomponentStandaardGroepBericht gegevensComp2 =
         * new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
         * gegevensComp2.setPredikaat(pred2);
         * gegevensComp2.setAdellijkeTitel(titel2);
         * gegevensComp2.setVoorvoegsel(new Voorvoegsel("voorv"));
         * gegevensComp2.setScheidingsteken(new Scheidingsteken(";"));
         * gegevensComp2.setNaam(new Geslachtsnaamcomponent("comp"));
         * component2.setStandaard(gegevensComp2);
         */
        persoonBericht.setGeslachtsnaamcomponenten(Arrays.asList(component1/* , component2 */));

        // Overlijden
        PersoonOverlijdenGroepBericht overlijdenGroepBericht = new PersoonOverlijdenGroepBericht();
        overlijdenGroepBericht.setDatumOverlijden(new Datum(21000101));
        overlijdenGroepBericht.setGemeenteOverlijden(gemeente);
        overlijdenGroepBericht.setWoonplaatsOverlijden(plaats);
        overlijdenGroepBericht.setBuitenlandsePlaatsOverlijden(new BuitenlandsePlaats("buitenland"));
        overlijdenGroepBericht.setBuitenlandseRegioOverlijden(new BuitenlandseRegio("bregio"));
        overlijdenGroepBericht.setLandOverlijden(land);
        overlijdenGroepBericht.setOmschrijvingLocatieOverlijden(new LocatieOmschrijving("omschrijving"));
        persoonBericht.setOverlijden(overlijdenGroepBericht);

        PersoonModel pers = new PersoonModel(persoonBericht);
        for (PersoonAdresBericht adresBericht : persoonBericht.getAdressen()) {
            pers.getAdressen().add(new PersoonAdresModel(adresBericht, pers));
        }
        for (PersoonVoornaamBericht voornaamBericht : persoonBericht.getVoornamen()) {
            pers.getVoornamen().add(new PersoonVoornaamModel(voornaamBericht, pers));
        }
        for (PersoonGeslachtsnaamcomponentBericht geslachtsnaamBericht : persoonBericht.getGeslachtsnaamcomponenten()) {
            pers.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(geslachtsnaamBericht, pers));
        }
        for (PersoonNationaliteitBericht nationaliteitBericht : persoonBericht.getNationaliteiten()) {
            pers.getNationaliteiten().add(new PersoonNationaliteitModel(nationaliteitBericht, pers));
        }

        // Identiteit
        ReflectionTestUtils.setField(pers, "iD", id);
        return pers;
    }

    /**
     * Instantieert een {@link nl.bzk.brp.model.logisch.kern.Betrokkenheid} en voegt deze toe aan de opgegeven
     * relatie. De velden van de
     * betrokkenheid
     * worden gezet op basis van de opge
     * aardes. De geinstantieerde betrokkenheid wordt geretourneerd.
     *
     * @param indOuderHeeftGezag boolean die aangeeft of de opgegeven persoon als ouder gezag heeft.
     * @param betrokkene de persoon die als betrokkene fungeert binnen de aan te maken betrokkenheid.
     * @param soort de soort van de betrokkenheid.
     * @param relatie de relatie waar aan de betrokkenheid moet worden toegevoegd.
     * @return de aangemaakte betrokkenheid.
     */
    protected BetrokkenheidModel maakBetrokkenheidEnVoegtoeAanRelatie(final JaNee indOuderHeeftGezag,
            final PersoonModel betrokkene, final SoortBetrokkenheid soort, final RelatieModel relatie)
    {
        BetrokkenheidModel betrokkenheid = null;

        if (SoortBetrokkenheid.OUDER == soort) {
            OuderOuderlijkGezagGroepBericht ouderlijkGezagGroep = new OuderOuderlijkGezagGroepBericht();
            ouderlijkGezagGroep.setIndicatieOuderHeeftGezag(indOuderHeeftGezag);

            OuderOuderschapGroepBericht ouderschapGroep = new OuderOuderschapGroepBericht();

            OuderBericht ouder = new OuderBericht();
            ouder.setOuderlijkGezag(ouderlijkGezagGroep);
            ouder.setOuderschap(ouderschapGroep);

            betrokkenheid = new OuderModel(ouder, relatie, betrokkene);
        } else if (SoortBetrokkenheid.KIND == soort) {
            betrokkenheid = new KindModel(new KindBericht(), relatie, betrokkene);
        } else if (SoortBetrokkenheid.PARTNER == soort) {
            betrokkenheid = new PartnerModel(new PartnerBericht(), relatie, betrokkene);
        }

        betrokkene.getBetrokkenheden().add(betrokkenheid);
        relatie.getBetrokkenheden().add(betrokkenheid);

        return betrokkenheid;
    }

    /**
     * Voegt een PersoonIndicatie toe aan de opgegeven persoon, waarbij de indicatie die wordt toegevoegd wordt
     * opgebouwd op ba
     * de opgegeven soort van de indicatie en de opgegeven waarde.
     *
     * @param persoon de persoon aan wie de indicatie wordt toegevoegd.
     * @param soort de soort van de indicatie die moet worden toegevoegd.
     * @param waarde de waarde van de indicatie die moet worden toegevoegd.
     */
    protected void voegIndicatieToeAanPersoon(final PersoonModel persoon, final SoortIndicatie soort, final Ja waarde) {
        if (persoon.getIndicaties().size() == 0) {
            // Vervang de HashSet door SortedSet anders wordt de uitkomst hier niet voorspelbaar
            SortedSet<PersoonIndicatieModel> set =
                new TreeSet<PersoonIndicatieModel>(new Comparator<PersoonIndicatieModel>() {

                    @Override
                    public int compare(final PersoonIndicatieModel a, final PersoonIndicatieModel b) {
                        return a.getSoort().compareTo(b.getSoort());
                    }
                });
            ReflectionTestUtils.setField(persoon, "indicaties", set);
        }

        PersoonIndicatieStandaardGroepBericht gegevensBericht = new PersoonIndicatieStandaardGroepBericht();
        gegevensBericht.setWaarde(waarde);

        PersoonIndicatieBericht persoonIndicatieBericht = new PersoonIndicatieBericht(soort);
        persoonIndicatieBericht.setStandaard(gegevensBericht);

        PersoonIndicatieModel persoonIndicatie = new PersoonIndicatieModel(persoonIndicatieBericht, persoon);
        ReflectionTestUtils.setField(persoonIndicatie, "persoonIndicatieStatusHis", StatusHistorie.A);

        ReflectionTestUtils.invokeMethod(persoon.getIndicaties(), "add", persoonIndicatie);
    }

    /**
     * Bouwt een {@link OpvragenPersoonResultaat} instantie met daarin een compleet persoon, inclusief relaties naar
     * een kind, ouders en een partner. De opgegeven gemeente, land en plaats worden gebruikt voor alle personen, dus
     * zowel voor de op te vragen pers
     * het resultaat als voor de betrokkene in zijn/haar relaties.
     *
     * @param gemeente de gemeente die in het antwoordbericht wordt gebruikt voor de personen
     * @param land het land die in het antwoordbericht wordt gebruikt voor de personen
     * @param plaats de woonplaats die in het antwoordbericht wordt gebruikt voor de personen
     * @return het verwachte bericht resultaat
     * @throws ParseException
     */
    protected OpvragenPersoonResultaat bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(
            final Partij gemeente, final Land land, final Plaats plaats, final SoortRelatie relatieSoort)
            throws ParseException
    {
        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        PersoonModel opTeVragenPersoon = maakPersoon(1, gemeente, plaats, land, "123456");
        PersoonModel kindVanOpTeVragenPersoon = maakPersoon(2, gemeente, plaats, land, "654321");
        PersoonModel vader = maakPersoon(3, gemeente, plaats, land, "8736487");
        PersoonModel moeder = maakPersoon(4, gemeente, plaats, land, "5874487");
        PersoonModel partnerVanOpTeVragenPersoon = maakPersoon(5, gemeente, plaats, land, "17354578");

        // Betrokkenheid familie rechtelijk opTeVragenPersoon van vader en moeder
        FamilierechtelijkeBetrekkingBericht relatieBericht = new FamilierechtelijkeBetrekkingBericht();
        FamilierechtelijkeBetrekkingModel relatie = new FamilierechtelijkeBetrekkingModel(relatieBericht);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.NEE, vader, SoortBetrokkenheid.OUDER, relatie);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.NEE, moeder, SoortBetrokkenheid.OUDER, relatie);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.NEE, opTeVragenPersoon, SoortBetrokkenheid.KIND, relatie);

        // Huwelijk
        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht gegevens =
            new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht();
        gegevens.setOmschrijvingLocatieAanvang(new LocatieOmschrijving("Timboektoe"));
        gegevens.setOmschrijvingLocatieEinde(new LocatieOmschrijving("blakalafm"));
        gegevens.setDatumAanvang(new Datum(19830404));
        gegevens.setDatumEinde(new Datum(19830404));
        gegevens.setLandAanvang(land);
        gegevens.setLandEinde(land);
        gegevens.setBuitenlandsePlaatsAanvang(new BuitenlandsePlaats("Verweggiestan"));
        gegevens.setBuitenlandsePlaatsEinde(new BuitenlandsePlaats("Tajikistan"));
        gegevens.setBuitenlandseRegioAanvang(new BuitenlandseRegio("In het bos"));
        gegevens.setBuitenlandseRegioEinde(new BuitenlandseRegio("In de woestijn"));
        gegevens.setGemeenteAanvang(gemeente);
        gegevens.setGemeenteEinde(gemeente);
        gegevens.setWoonplaatsAanvang(plaats);
        gegevens.setWoonplaatsEinde(plaats);

        HuwelijkBericht huwelijkBericht = new HuwelijkBericht();
        huwelijkBericht.setStandaard(gegevens);
        HuwelijkModel huwelijk = new HuwelijkModel(huwelijkBericht);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.NEE, partnerVanOpTeVragenPersoon, SoortBetrokkenheid.PARTNER,
                huwelijk);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.NEE, opTeVragenPersoon, SoortBetrokkenheid.PARTNER, huwelijk);

        // Betrokkenheid familie rechtelijk kind van Op te vragen persoon en partner
        FamilierechtelijkeBetrekkingBericht kindVanOpTeVragenPersRelatieBericht =
            new FamilierechtelijkeBetrekkingBericht();

        FamilierechtelijkeBetrekkingModel kindVanOpTeVragenPersRelatie =
            new FamilierechtelijkeBetrekkingModel(kindVanOpTeVragenPersRelatieBericht);
        // kindVanOpTeVragenPersRelatie.setDatumAanvang(Integer.valueOf(20110305));
        //ReflectionTestUtils.setField(kindVanOpTeVragenPersRelatie, "betrokkenheden", new HashSet<Betrokkenheid>());

        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.NEE, kindVanOpTeVragenPersoon, SoortBetrokkenheid.KIND, kindVanOpTeVragenPersRelatie);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.NEE, partnerVanOpTeVragenPersoon, SoortBetrokkenheid.OUDER, kindVanOpTeVragenPersRelatie);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.NEE, opTeVragenPersoon, SoortBetrokkenheid.OUDER, kindVanOpTeVragenPersRelatie);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        gevondenPersonen.add(opTeVragenPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);
        return resultaat;
    }

    /**
     * Bouwt en retourneert een verwacht antwoord bericht met opgeven tijdstip van registratie, maar zonder m
     * n
     * en zonder werkelijke data in het antwoordbericht.
     *
     * @param tijdstipRegistratie het tijdstip van registratie dat gebruikt dient te worden in het antwoordbericht.
     * @return het verwachte antwoordbericht.
     */
    protected String bouwVerwachteAntwoordBericht(final String tijdstipRegistratie) {
        return bouwVerwachteAntwoordBericht(tijdstipRegistratie, null, null, null, null);
    }

    /**
     * Bouwt en retourneert een verwacht antwoord bericht met opgeven tijdstip van registratie en een melding, waarbij
     * de melding de opgegeven melding code, regelcode en melding omschrijving heeft. Het antwoordbericht bevat verder
     * de content zoals opgenomen in het bestan
     * opgegeven door de 'verwachtAntwoordBerichtBestandsNaam'.
     *
     * @param tijdstipRegistratie het tijdstip van registratie dat gebruikt dient te worden in het antwoordbericht.
     * @param meldingCode de code van de melding soort.
     * @param regelCode de regel code van de melding.
     * @param verwachtAntwoordBerichtBestandsNaam de bestandsnaam van het bestand met daarin de verwachte content van
     *            het antwoord.
     * @return het verwachte antwoordbericht.
     */
    protected String bouwVerwachteAntwoordBericht(final String tijdstipRegistratie, final String meldingCode,
            final String regelCode, final String meldingOmschrijving, final String verwachtAntwoordBerichtBestandsNaam)
    {
        StringBuilder verwachteWaarde =
            new StringBuilder()
                    .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                    .append(String
                            .format("<brp:%s xmlns:brp=\"http://www.bprbzk.nl/BRP/0002\" xmlns:stuf=\"http://www.kinggemeenten.nl/StUF/StUF0302\" xmlns:xsi=\"http://www.w3"
                                + ".org/2001/XMLSchema-instance\">", getBerichtElementNaam()));

        bouwStuurgegevensElement(verwachteWaarde);
        bouwResultaatElement(verwachteWaarde, tijdstipRegistratie, meldingCode);
        if (meldingCode != null && regelCode != null) {
            bouwMeldingenElement(verwachteWaarde, meldingCode, regelCode, meldingOmschrijving);
        }
        if (verwachtAntwoordBerichtBestandsNaam != null) {
            bouwAntwoordElement(verwachteWaarde, verwachtAntwoordBerichtBestandsNaam);
        }
        verwachteWaarde.append(String.format("</brp:%s>", getBerichtElementNaam()));
        return verwachteWaarde.toString();
    }

    /**
     * Voegt een standaard stuurgegeven
     * nt toe aan de opgegeven {@link StringBuilder} instantie.
     *
     * @param stringBuilder de stringbuilder waar het element aan toegevoegd dient te worden.
     */
    private void bouwStuurgegevensElement(final StringBuilder stringBuilder) {
        stringBuilder.append("<brp:stuurgegevens>").append(bouwElement("stuf", "berichtcode", "Du02"))
                .append("<stuf:zender>").append(bouwElement("stuf", "organisatie", "Ministerie BZK"))
                .append(bouwElement("stuf", "applicatie", "BRP")).append("</stuf:zender>")
                .append(bouwElement("stuf", "referentienummer", "OnbekendeID"))
                .append(bouwElement("stuf", "crossRefnummer", "OnbekendeID")).append("</brp:stuurgegevens>");
    }

    /**
     * Voegt een standaard resultaat element toe aan de opgegeven {@link StringBuilder} instantie, waarbij de opgegeven
     * waardes voor tijdstip registr
     * hoogste melding code in het resultaat worden verwerkt.
     *
     * @param stringBuilder de stringbuilder waar het element aan toegevoegd dient te worden.
     * @param tijdstipRegistratie het tijdstip van registratie wat in het resultaat opgenomen dient te worden.
     * @param hoogsteMeldingCode de hoogste melding code die in het resultaat opgenomen dient te worden.
     */
    private void bouwResultaatElement(final StringBuilder stringBuilder, final String tijdstipRegistratie,
            final String hoogsteMeldingCode)
    {
        stringBuilder.append("<brp:resultaat stuf:entiteittype=\"Resultaat\">").append(bouwElement("verwerkingCode", "G"));
        if (hoogsteMeldingCode != null) {
            stringBuilder.append(bouwElement("hoogsteMeldingsniveauCode", hoogsteMeldingCode));
        }
        // stringBuilder.append(bouwElement("peilmomentMaterieel", tijdstipRegistratie));
        stringBuilder.append("</brp:resultaat>");
    }

    /**
     * Voegt een standaard meldingen element toe aan de opgegeven {@link StringBuilder} instantie, met daarin een
     * enkele melding met de opgegeven regelcode en melding.
     *
     * @param stringBuilder de stringbuilder waar het element aan toegevoegd dient te worden.
     * @param regelCode de regelcode van de melding die in het meldingen element dient te zitten.
     * @param melding de melding omschrijving van de melding die in het meldingen elment dient te zitten.
     */
    private void bouwMeldingenElement(final StringBuilder stringBuilder, final String meldingCode,
            final String regelCode, final String melding)
    {
        stringBuilder.append("<brp:meldingen><brp:melding stuf:entiteittype=\"Melding\">").append(bouwElement("regelCode", regelCode))
                .append(bouwElement("soortCode", meldingCode)).append(bouwElement("melding", melding))
                .append("</brp:melding></brp:meldingen>");
    }

    private String bouwElement(final String prefix, final String naam, final String waarde) {
        return String.format("<%1$s:%2$s>%3$s</%1$s:%2$s>", prefix, naam, waarde);
    }

    private String bouwElement(final String naam, final String waarde) {
        return String.format("<brp:%1$s>%2$s</brp:%1$s>", naam, waarde);
    }

    /**
     * Bouwt en retourneert een mi
     * persoon (conform XSD eisen opvragen persoon details).
     *
     * @return een persoon met minimale gegevens ingevuld.
     */
    protected PersoonModel bouwMinimaalPersoon() {
        Land nederland = new Land(new Landcode("31"), null, null, null, null);

        PersoonIdentificatienummersGroepBericht persoonIdentificatieNummersGroepBericht =
            new PersoonIdentificatienummersGroepBericht();
        persoonIdentificatieNummersGroepBericht.setBurgerservicenummer(new Burgerservicenummer("123456789"));

        PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsAanduidingGroepBericht =
            new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsAanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);

        PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setLandGeboorte(nederland);
        persoonGeboorteGroepBericht.setDatumGeboorte(new Datum(20060325));

        PersoonSamengesteldeNaamGroepBericht samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldNaam.setIndicatieAlgoritmischAfgeleid(JaNee.NEE);
        samengesteldNaam.setIndicatieNamenreeks(JaNee.NEE);
        samengesteldNaam.setGeslachtsnaam(new Geslachtsnaam("Test"));

        PersoonAanschrijvingGroepBericht aanschrijving = new PersoonAanschrijvingGroepBericht();
        aanschrijving.setNaamgebruik(WijzeGebruikGeslachtsnaam.EIGEN);
        aanschrijving.setIndicatieAanschrijvingAlgoritmischAfgeleid(JaNee.NEE);

        PersoonBijhoudingsaardGroepBericht bijhoudingsVerantwoordelijke =
            new PersoonBijhoudingsaardGroepBericht();
        bijhoudingsVerantwoordelijke.setBijhoudingsaard(Bijhoudingsaard.INGEZETENE);

        PersoonAfgeleidAdministratiefGroepBericht administratief = new PersoonAfgeleidAdministratiefGroepBericht();
        administratief.setTijdstipLaatsteWijziging(new DatumTijd(new Date()));

        PersoonInschrijvingGroepBericht inschrijving = new PersoonInschrijvingGroepBericht();
        inschrijving.setDatumInschrijving(new Datum(20060325));
        inschrijving.setVersienummer(new Versienummer(2L));

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setSoort(SoortPersoon.INGESCHREVENE);
        persoonBericht.setIdentificatienummers(persoonIdentificatieNummersGroepBericht);
        persoonBericht.setGeslachtsaanduiding(persoonGeslachtsAanduidingGroepBericht);
        persoonBericht.setGeboorte(persoonGeboorteGroepBericht);
        persoonBericht.setSamengesteldeNaam(samengesteldNaam);
        persoonBericht.setAanschrijving(aanschrijving);
        persoonBericht.setBijhoudingsaard(bijhoudingsVerantwoordelijke);
        persoonBericht.setAfgeleidAdministratief(administratief);
        persoonBericht.setInschrijving(inschrijving);

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setLand(nederland);

        PersoonModel persoon = new PersoonModel(persoonBericht);

        PersoonAdresBericht minimaalAdres = new PersoonAdresBericht();
        minimaalAdres.setPersoon(persoonBericht);
        minimaalAdres.setStandaard(gegevens);

        persoon.getAdressen().add(new PersoonAdresModel(minimaalAdres, persoon));

        return persoon;
    }

    /**
     * Bouw minimaal Objectype_PersoonBetrokkene_AntwoordA.
     *
     * @return PersoonModel
     */
    protected PersoonModel bouwMinimaal_Objectype_PersoonBetrokkene_AntwoordA() {
        Land nederland = new Land(new Landcode("31"), null, null, null, null);

        PersoonSamengesteldeNaamGroepBericht samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldNaam.setIndicatieAlgoritmischAfgeleid(JaNee.NEE);
        samengesteldNaam.setIndicatieNamenreeks(JaNee.NEE);
        samengesteldNaam.setGeslachtsnaam(new Geslachtsnaam("Test"));

        PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setLandGeboorte(nederland);
        persoonGeboorteGroepBericht.setDatumGeboorte(new Datum(20060325));

        PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsAanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
            persoonGeslachtsAanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setSoort(SoortPersoon.INGESCHREVENE);
        persoonBericht.setSamengesteldeNaam(samengesteldNaam);
        persoonBericht.setGeboorte(persoonGeboorteGroepBericht);
        persoonBericht.setGeslachtsaanduiding(persoonGeslachtsAanduidingGroepBericht);

        PersoonModel persoonModel = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoonModel, "iD", 2);
        return persoonModel;
    }
}
