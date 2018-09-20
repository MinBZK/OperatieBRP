/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsgemeenteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsverantwoordelijkheidGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonIndicatieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Abstracte class bedoeld voor het testen van de binding van antwoordberichten op vraag/informatie berichten. Deze
 * class biedt helper methodes om testdata aan te maken welke veelal gebruikt kan worden in de tests en helper methodes
 * voor het opbouwen van het verwachte antwoord.
 */

// TODO reflection omzetten naar setters op Web
public abstract class AbstractVraagBerichtBindingUitIntegratieTest<T> extends AbstractBindingUitIntegratieTest<T> {

    /**
     * Retourneert de naa
     * et bericht element van het bericht dat in de test wordt getest.
     *
     * @return de naam van het bericht element van het bericht dat in de test wordt getest.
     */
    public abstract String getBerichtElementNaam();

    /**
     * Instantieert een {@link nl.bzk.brp.model.objecttype.operationeel.PersoonModel} en vult deze geheel met test
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
        Predikaat predikaat = new Predikaat();
        predikaat.setCode(new PredikaatCode("H"));

        PersoonBericht persoonBericht = new PersoonBericht();

        // Identificatienummers
        PersoonIdentificatienummersGroepBericht identificatieNummersGroep =
            new PersoonIdentificatienummersGroepBericht();
        identificatieNummersGroep.setBurgerservicenummer(new Burgerservicenummer(bsn));
        identificatieNummersGroep.setAdministratienummer(new Administratienummer(Long.valueOf(987654321L)));
        persoonBericht.setIdentificatienummers(identificatieNummersGroep);

        // Geslachtsaanduiding
        PersoonGeslachtsaanduidingGroepBericht geslachtsAanduiding = new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsAanduiding.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        persoonBericht.setGeslachtsaanduiding(geslachtsAanduiding);

        // SamenGesteldeNaam
        AdellijkeTitel adellijkeTitel = new AdellijkeTitel();
        adellijkeTitel.setAdellijkeTitelCode(new AdellijkeTitelCode("B"));

        PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaam.setAdellijkeTitel(adellijkeTitel);

        persoonSamengesteldeNaam.setPredikaat(predikaat);
        persoonSamengesteldeNaam.setVoornamen(new Voornaam("Voornaam"));
        persoonSamengesteldeNaam.setVoorvoegsel(new Voorvoegsel("voor"));
        persoonSamengesteldeNaam.setScheidingsteken(new Scheidingsteken(","));
        persoonSamengesteldeNaam.setGeslachtsnaam(new Geslachtsnaamcomponent("geslachtsnaam"));
        persoonSamengesteldeNaam.setIndNamenreeksAlsGeslachtsNaam(JaNee.Nee);
        persoonSamengesteldeNaam.setIndAlgorithmischAfgeleid(JaNee.Nee);
        persoonBericht.setSamengesteldeNaam(persoonSamengesteldeNaam);

        // Aanschrijving
        PersoonAanschrijvingGroepBericht aanschrijving = new PersoonAanschrijvingGroepBericht();
        aanschrijving.setGebruikGeslachtsnaam(WijzeGebruikGeslachtsnaam.EIGEN);
        aanschrijving.setIndAanschrijvenMetAdellijkeTitel(JaNee.Nee);
        aanschrijving.setIndAanschrijvingAlgorthmischAfgeleid(JaNee.Nee);
        aanschrijving.setPredikaat(predikaat);
        aanschrijving.setVoornamen(new Voornaam("voornaam"));
        aanschrijving.setVoorvoegsel(new Voorvoegsel("voor"));
        aanschrijving.setScheidingsteken(new Scheidingsteken(","));
        aanschrijving.setGeslachtsnaam(new Geslachtsnaamcomponent("geslachtsnaam"));
        persoonBericht.setAanschrijving(aanschrijving);

        // Geboorte
        PersoonGeboorteGroepBericht persoonGeboorteGroep = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroep.setDatumGeboorte(new Datum(20120101));
        persoonGeboorteGroep.setGemeenteGeboorte(gemeente);
        persoonGeboorteGroep.setWoonplaatsGeboorte(plaats);
        persoonGeboorteGroep.setBuitenlandseGeboortePlaats(new BuitenlandsePlaats("buitenland"));
        persoonGeboorteGroep.setBuitenlandseRegioGeboorte(new BuitenlandseRegio("bregio"));
        persoonGeboorteGroep.setLandGeboorte(land);
        persoonGeboorteGroep.setOmschrijvingGeboorteLocatie(new Omschrijving("omschrijving"));
        persoonBericht.setGeboorte(persoonGeboorteGroep);

        // Bijhoudingsverwantwoordelijke
        PersoonBijhoudingsverantwoordelijkheidGroepBericht verantwoordelijkheid =
            new PersoonBijhoudingsverantwoordelijkheidGroepBericht();
        verantwoordelijkheid.setVerantwoordelijke(Verantwoordelijke.MINISTER);
        persoonBericht.setBijhoudingsverantwoordelijkheid(verantwoordelijkheid);

        // Opschorting
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschorting(RedenOpschorting.MINISTER);
        persoonBericht.setOpschorting(opschorting);

        // Bijhoudingsgemeente
        PersoonBijhoudingsgemeenteGroepBericht bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepBericht();
        bijhoudingsgemeente.setBijhoudingsgemeente(gemeente);
        bijhoudingsgemeente.setIndOnverwerktDocumentAanwezig(JaNee.Nee);
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
        afgeleidAdministratief.setIndGegevensInOnderzoek(JaNee.Nee);
        afgeleidAdministratief.setTijdstipLaatsteWijziging(
            new DatumTijd(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").parse(("2012/06/02 11:04:55:555"))));
        persoonBericht.setAfgeleidAdministratief(afgeleidAdministratief);


        // Voornamen
        PersoonVoornaamStandaardGroepBericht persoonVoornaamStandaardGroepBericht1 =
            new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamStandaardGroepBericht1.setVoornaam(new Voornaam("voornaam1"));

        PersoonVoornaamBericht persoonVoornaam1 = new PersoonVoornaamBericht();
        persoonVoornaam1.setVolgnummer(new Volgnummer(1));
        persoonVoornaam1.setGegevens(persoonVoornaamStandaardGroepBericht1);

        PersoonVoornaamStandaardGroepBericht persoonVoornaamStandaardGroepBericht2 =
            new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamStandaardGroepBericht2.setVoornaam(new Voornaam("voornaam1"));

        PersoonVoornaamBericht persoonVoornaam2 = new PersoonVoornaamBericht();
        persoonVoornaam2.setVolgnummer(new Volgnummer(1));
        persoonVoornaam2.setGegevens(persoonVoornaamStandaardGroepBericht2);

        persoonBericht.setPersoonVoornaam(Arrays.asList(persoonVoornaam1, persoonVoornaam2));

        // Adressen
        RedenWijzigingAdres rdn = new RedenWijzigingAdres();
        rdn.setRedenWijzigingAdresCode(new RedenWijzigingAdresCode("A"));

        PersoonAdresStandaardGroepBericht adr = new PersoonAdresStandaardGroepBericht();
        adr.setSoort(FunctieAdres.BRIEFADRES);
        adr.setDatumAanvangAdreshouding(new Datum(20120101));
        adr.setRedenwijziging(rdn);
        adr.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.GEZAGHOUDER);
        adr.setAdresseerbaarObject(new Adresseerbaarobject("acd"));
        adr.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("idcode"));
        adr.setGemeente(gemeente);
        adr.setNaamOpenbareRuimte(new NaamOpenbareRuimte("opNaam"));
        adr.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("afg"));
        adr.setGemeentedeel(new Gemeentedeel("gemdeel"));
        adr.setHuisnummer(new Huisnummer(12));
        adr.setHuisletter(new Huisletter("A"));
        adr.setPostcode(new Postcode("1234AB"));
        adr.setHuisnummertoevoeging(new Huisnummertoevoeging("b"));
        adr.setWoonplaats(plaats);
        adr.setLocatietovAdres(new LocatieTovAdres("TO"));
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
        adres.setGegevens(adr);
        persoonBericht.setAdressen(Arrays.asList(adres));

        // Nationaliteiten
        Nationaliteit nation = new Nationaliteit();
        nation.setNaam(new Naam("abcd"));
        nation.setNationaliteitcode(new Nationaliteitcode((short) 31));

        PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();

        PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
        persoonNationaliteit.setNationaliteit(nation);
        persoonNationaliteit.setGegevens(gegevens);
        persoonBericht.setNationaliteiten(Arrays.asList(persoonNationaliteit));

        // Geslachtsnaamcomponenten
        AdellijkeTitel titel = new AdellijkeTitel();
        titel.setAdellijkeTitelCode(new AdellijkeTitelCode("H"));

        PersoonGeslachtsnaamcomponentStandaardGroepBericht gegevensComp1 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        gegevensComp1.setPredikaat(predikaat);
        gegevensComp1.setAdellijkeTitel(titel);
        gegevensComp1.setVoorvoegsel(new Voorvoegsel("voorv"));
        gegevensComp1.setScheidingsteken(new Scheidingsteken(";"));
        gegevensComp1.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("comp"));

        PersoonGeslachtsnaamcomponentBericht component1 = new PersoonGeslachtsnaamcomponentBericht();
        component1.setVolgnummer(new Volgnummer(1));
        component1.setGegevens(gegevensComp1);

        PersoonGeslachtsnaamcomponentBericht component2 = new PersoonGeslachtsnaamcomponentBericht();
        component2.setVolgnummer(new Volgnummer(1));

        Predikaat pred2 = new Predikaat();
        pred2.setCode(new PredikaatCode("H"));

        AdellijkeTitel titel2 = new AdellijkeTitel();
        titel2.setAdellijkeTitelCode(new AdellijkeTitelCode("H"));

        PersoonGeslachtsnaamcomponentStandaardGroepBericht gegevensComp2 =
            new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        gegevensComp2.setPredikaat(pred2);
        gegevensComp2.setAdellijkeTitel(titel2);
        gegevensComp2.setVoorvoegsel(new Voorvoegsel("voorv"));
        gegevensComp2.setScheidingsteken(new Scheidingsteken(";"));
        gegevensComp2.setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("comp"));
        component2.setGegevens(gegevensComp2);
        persoonBericht.setGeslachtsnaamcomponenten(Arrays.asList(component1, component2));

        // Overlijden
        PersoonOverlijdenGroepBericht overlijdenGroepBericht = new PersoonOverlijdenGroepBericht();
        overlijdenGroepBericht.setDatumOverlijden(new Datum(21000101));
        overlijdenGroepBericht.setOverlijdenGemeente(gemeente);
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
        for (PersoonVoornaamBericht voornaamBericht : persoonBericht.getPersoonVoornaam()) {
            pers.getPersoonVoornaam().add(new PersoonVoornaamModel(voornaamBericht, pers));
        }
        for (PersoonGeslachtsnaamcomponentBericht geslachtsnaamBericht : persoonBericht.getGeslachtsnaamcomponenten()) {
            pers.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentModel(geslachtsnaamBericht, pers));
        }
        for (PersoonNationaliteitBericht nationaliteitBericht : persoonBericht.getNationaliteiten()) {
            pers.getNationaliteiten().add(new PersoonNationaliteitModel(nationaliteitBericht, pers));
        }

        // Identiteit
        ReflectionTestUtils.setField(pers, "id", id);
        return pers;
    }

    /**
     * Instantieert een {@link nl.bzk.brp.model.objecttype.logisch.Betrokkenheid} en voegt deze toe aan de opgegeven
     * relatie. De velden van de
     * betrokkenheid
     * worden gezet op basis van de opge
     * aardes. De geinstantieerde betrokkenheid wordt geretourneerd.
     *
     * @param indOuderHeeftGezag boolean die aangeeft of de opgegeven persoon als ouder gezag heeft.
     * @param betrokkene de persoon die als betrokkene fungeert binnen de aan te maken betrokkenheid.
     * @param soort de soort van de betrokkenheid.
     * @param relatie de relatie waar aan de betrokkenheid moet worden toegevoegd.
     * @param datumAanvangOuderschap zet een eventuele datumAanvangOuderschap op de betrokkenheid.
     * @return de aangemaakte betrokkenheid.
     */
    protected BetrokkenheidModel maakBetrokkenheidEnVoegtoeAanRelatie(final JaNee indOuderHeeftGezag,
        final PersoonModel betrokkene, final SoortBetrokkenheid soort, final RelatieModel relatie,
        final Integer datumAanvangOuderschap)
    {
        BetrokkenheidOuderlijkGezagGroepBericht ouderlijkGezagGroep = new BetrokkenheidOuderlijkGezagGroepBericht();
        ouderlijkGezagGroep.setIndOuderlijkGezag(indOuderHeeftGezag);

        BetrokkenheidOuderschapGroepBericht ouderschapGroep = new BetrokkenheidOuderschapGroepBericht();
        ouderschapGroep.setDatumAanvang(new Datum(datumAanvangOuderschap));

        BetrokkenheidBericht betrokkenheidBericht = new BetrokkenheidBericht();
        betrokkenheidBericht.setBetrokkenheidOuderlijkGezag(ouderlijkGezagGroep);
        betrokkenheidBericht.setRol(soort);
        betrokkenheidBericht.setBetrokkenheidOuderschap(ouderschapGroep);

        BetrokkenheidModel betrokkenheid = new BetrokkenheidModel(betrokkenheidBericht, betrokkene, relatie);
        if (betrokkene.getBetrokkenheden() == null) {
            ReflectionTestUtils.setField(betrokkene, "betrokkenheden", new HashSet<BetrokkenheidBericht>());
        }
        ReflectionTestUtils.setField(betrokkenheid.getBetrokkenheidOuderschap(), "datumAanvangOuderschap",
            datumAanvangOuderschap);
        /*
         * Set<BetrokkenheidMdl> betrokkenheden = new HashSet<BetrokkenheidMdl>();
         * betrokkenheden.add(betrokkenheid);
         */

        ReflectionTestUtils.invokeMethod(betrokkene.getBetrokkenheden(), "add", betrokkenheid);
        ReflectionTestUtils.invokeMethod(relatie.getBetrokkenheden(), "add", betrokkenheid);

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
                        return a.getGegevens().getSoort().compareTo(b.getGegevens().getSoort());
                    }
                });
            ReflectionTestUtils.setField(persoon, "indicaties", set);
        }

        PersoonIndicatieStandaardGroepBericht gegevensBericht = new PersoonIndicatieStandaardGroepBericht();
        gegevensBericht.setSoort(soort);
        gegevensBericht.setWaarde(waarde);

        PersoonIndicatieBericht persoonIndicatieBericht = new PersoonIndicatieBericht();
        persoonIndicatieBericht.setGegevens(gegevensBericht);

        PersoonIndicatieModel persoonIndicatie = new PersoonIndicatieModel(persoonIndicatieBericht);
        ReflectionTestUtils.setField(persoonIndicatie, "persoon", persoon);

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
        final Partij gemeente, final Land land, final Plaats plaats, final SoortRelatie relatieSoort) throws
        ParseException
    {
        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        PersoonModel opTeVragenPersoon = maakPersoon(1, gemeente, plaats, land, "persoon");
        PersoonModel kindVanOpTeVragenPersoon = maakPersoon(2, gemeente, plaats, land, "kind");
        PersoonModel vader = maakPersoon(3, gemeente, plaats, land, "vader");
        PersoonModel moeder = maakPersoon(4, gemeente, plaats, land, "moeder");
        PersoonModel partnerVanOpTeVragenPersoon = maakPersoon(5, gemeente, plaats, land, "partner");

        // Betrokkenheid familie rechtelijk opTeVragenPersoon van vader en moeder
        RelatieBericht relatieBericht = new RelatieBericht();
        relatieBericht.setGegevens(new RelatieStandaardGroepBericht());
        relatieBericht.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        RelatieModel relatie = new RelatieModel(relatieBericht);
        ReflectionTestUtils.setField(relatie, "betrokkenheden", new HashSet<Betrokkenheid>());

        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, vader, SoortBetrokkenheid.OUDER, relatie, 20110305);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, moeder, SoortBetrokkenheid.OUDER, relatie, 20110305);
        BetrokkenheidModel kindBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, opTeVragenPersoon, SoortBetrokkenheid.KIND, relatie, null);
        opTeVragenPersoon.getBetrokkenheden().add(kindBetr);

        RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();
        gegevens.setOmschrijvingLocatieAanvang(new Omschrijving("Timboektoe"));
        gegevens.setOmschrijvingLocatieEinde(new Omschrijving("blakalafm"));
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
        gegevens.setWoonPlaatsAanvang(plaats);
        gegevens.setWoonPlaatsEinde(plaats);

        // Huwelijk
        RelatieBericht huwelijkBericht = new RelatieBericht();
        huwelijkBericht.setGegevens(gegevens);
        huwelijkBericht.setSoort(relatieSoort);

        RelatieModel huwelijk = new RelatieModel(huwelijkBericht);
        ReflectionTestUtils.setField(huwelijk, "betrokkenheden", new HashSet<Betrokkenheid>());

        BetrokkenheidModel partnerVanOpTeVragenPersoonBetr = maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee,
            partnerVanOpTeVragenPersoon, SoortBetrokkenheid.PARTNER, huwelijk, null);
        BetrokkenheidModel partnerBetr = maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, opTeVragenPersoon,
            SoortBetrokkenheid.PARTNER, huwelijk, null);
        opTeVragenPersoon.getBetrokkenheden().add(partnerBetr);

        // Betrokkenheid familie rechtelijk kind van Op te vragen persoon en partner
        RelatieBericht kindVanOpTeVragenPersRelatieBericht = new RelatieBericht();
        kindVanOpTeVragenPersRelatieBericht.setGegevens(new RelatieStandaardGroepBericht());
        kindVanOpTeVragenPersRelatieBericht.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        RelatieModel kindVanOpTeVragenPersRelatie = new RelatieModel(kindVanOpTeVragenPersRelatieBericht);
        // kindVanOpTeVragenPersRelatie.setDatumAanvang(Integer.valueOf(20110305));
        ReflectionTestUtils.setField(kindVanOpTeVragenPersRelatie, "betrokkenheden", new HashSet<Betrokkenheid>());

        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, kindVanOpTeVragenPersoon, SoortBetrokkenheid.KIND,
            kindVanOpTeVragenPersRelatie, null);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, partnerVanOpTeVragenPersoon, SoortBetrokkenheid.OUDER,
            kindVanOpTeVragenPersRelatie, 20110305);
        BetrokkenheidModel optevragenPersoonVaderBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, opTeVragenPersoon, SoortBetrokkenheid.OUDER,
                kindVanOpTeVragenPersRelatie, 20110305);
        opTeVragenPersoon.getBetrokkenheden().add(optevragenPersoonVaderBetr);

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
     * het antwoord.
     * @return het verwachte antwoordbericht.
     */
    protected String bouwVerwachteAntwoordBericht(final String tijdstipRegistratie, final String meldingCode,
        final String regelCode, final String meldingOmschrijving, final String verwachtAntwoordBerichtBestandsNaam)
    {
        StringBuilder verwachteWaarde =
            new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .append(String.format("<brp:%s xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" xmlns:xsi=\"http://www.w3"
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
        stringBuilder.append("<brp:stuurgegevens>").append(bouwElement("organisatie", "Ministerie BZK"))
                     .append(bouwElement("applicatie", "BRP")).append(bouwElement("referentienummer", "OnbekendeID"))
                     .append(bouwElement("crossReferentienummer", "OnbekendeID")).append("</brp:stuurgegevens>");
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
        stringBuilder.append("<brp:resultaat>").append(bouwElement("verwerkingCode", "G"));
        if (hoogsteMeldingCode == null) {
            stringBuilder.append("<brp:hoogsteMeldingsniveauCode xsi:nil=\"true\"/>");
        } else {
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
        stringBuilder.append("<brp:meldingen><brp:melding>").append(bouwElement("regelCode", regelCode))
                     .append(bouwElement("soortCode", meldingCode)).append(bouwElement("melding", melding))
                     .append("</brp:melding></brp:meldingen>");
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
        Land nederland = new Land();
        nederland.setCode(new Landcode((short) 31));

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
        samengesteldNaam.setGeslachtsnaam(new Geslachtsnaamcomponent("Test"));

        PersoonAanschrijvingGroepBericht aanschrijving = new PersoonAanschrijvingGroepBericht();
        aanschrijving.setGeslachtsnaam(new Geslachtsnaamcomponent("Test"));
        aanschrijving.setIndAanschrijvingAlgorthmischAfgeleid(JaNee.Nee);

        PersoonBijhoudingsverantwoordelijkheidGroepBericht bijhoudingsVerantwoordelijke =
            new PersoonBijhoudingsverantwoordelijkheidGroepBericht();
        bijhoudingsVerantwoordelijke.setVerantwoordelijke(Verantwoordelijke.COLLEGE);

        PersoonAfgeleidAdministratiefGroepBericht administratief = new PersoonAfgeleidAdministratiefGroepBericht();
        administratief.setTijdstipLaatsteWijziging(new DatumTijd(new Date()));

        PersoonInschrijvingGroepBericht inschrijving = new PersoonInschrijvingGroepBericht();
        inschrijving.setDatumInschrijving(new Datum(20060325));
        inschrijving.setVersienummer(new Versienummer(2L));

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(persoonIdentificatieNummersGroepBericht);
        persoonBericht.setGeslachtsaanduiding(persoonGeslachtsAanduidingGroepBericht);
        persoonBericht.setGeboorte(persoonGeboorteGroepBericht);
        persoonBericht.setSamengesteldeNaam(samengesteldNaam);
        persoonBericht.setAanschrijving(aanschrijving);
        persoonBericht.setBijhoudingsverantwoordelijkheid(bijhoudingsVerantwoordelijke);
        persoonBericht.setAfgeleidAdministratief(administratief);
        persoonBericht.setInschrijving(inschrijving);

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setLand(nederland);

        PersoonModel persoon = new PersoonModel(persoonBericht);

        PersoonAdresBericht minimaalAdres = new PersoonAdresBericht();
        minimaalAdres.setPersoon(persoonBericht);
        minimaalAdres.setGegevens(gegevens);

        persoon.getAdressen().add(new PersoonAdresModel(minimaalAdres, persoon));

        return persoon;
    }
}
