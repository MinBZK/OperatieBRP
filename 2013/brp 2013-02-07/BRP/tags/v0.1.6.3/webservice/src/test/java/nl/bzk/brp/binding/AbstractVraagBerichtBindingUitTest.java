/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsGemeenteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsVerantwoordelijkheidGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsAanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamCompStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonOverlijdenGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.RelatieStandaardGroepModel;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamComponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonIndicatieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamComponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;
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
public abstract class AbstractVraagBerichtBindingUitTest<T> extends AbstractBerichtBindingUitTest<T> {

    /**
     * Retourneert de naa
     * et bericht element van het bericht dat in de test wordt getest.
     *
     * @return de naam van het bericht element van het bericht dat in de test wordt getest.
     */
    public abstract String getBerichtElementNaam();

    /**
     * Instantieert een {@link nl.bzk.brp.model.objecttype.operationeel.PersoonModel} en vult deze geheel met test data,
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
    protected PersoonModel maakPersoon(final Long id, final Partij gemeente, final Plaats plaats, final Land land,
            final String bsn) throws ParseException
    {
        Predikaat predikaat = new Predikaat();
        ReflectionTestUtils.setField(predikaat, "code", new PredikaatCode("H"));

        PersoonBericht persWeb = new PersoonBericht();

        // Identificatienummers
        PersoonIdentificatieNummersGroepBericht identificatieNummersGroep =
            new PersoonIdentificatieNummersGroepBericht();
        identificatieNummersGroep.setBurgerServiceNummer(new Burgerservicenummer(bsn));
        identificatieNummersGroep.setAdministratieNummer(new Administratienummer("987654321"));
        persWeb.setIdentificatieNummers(identificatieNummersGroep);

        // Geslachtsaanduiding
        PersoonGeslachtsAanduidingGroepBericht geslachtsAanduiding = new PersoonGeslachtsAanduidingGroepBericht();
        geslachtsAanduiding.setGeslachtsAanduiding(GeslachtsAanduiding.MAN);
        persWeb.setGeslachtsAanduiding(geslachtsAanduiding);

        // SamenGesteldeNaam
        AdellijkeTitel adellijkeTitel = new AdellijkeTitel();
        ReflectionTestUtils.setField(adellijkeTitel, "adellijkeTitelCode", new AdellijkeTitelCode("B"));

        PersoonSamengesteldeNaamGroepBericht persoonSamengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
        persoonSamengesteldeNaam.setAdellijkeTitel(adellijkeTitel);

        persoonSamengesteldeNaam.setPredikaat(predikaat);
        persoonSamengesteldeNaam.setVoornamen(new Voornaam("Voornaam"));
        persoonSamengesteldeNaam.setVoorvoegsel(new Voorvoegsel("voor"));
        persoonSamengesteldeNaam.setScheidingsteken(new ScheidingsTeken(","));
        persoonSamengesteldeNaam.setGeslachtsnaam(new GeslachtsnaamComponent("geslachtsnaam"));
        persoonSamengesteldeNaam.setIndNamenreeksAlsGeslachtsNaam(JaNee.Nee);
        persoonSamengesteldeNaam.setIndAlgorithmischAfgeleid(JaNee.Nee);
        persWeb.setSamengesteldeNaam(persoonSamengesteldeNaam);

        // Aanschrijving
        PersoonAanschrijvingGroepBericht aanschrijving = new PersoonAanschrijvingGroepBericht();
        aanschrijving.setGebruikGeslachtsnaam(WijzeGebruikGeslachtsnaam.EIGEN);
        aanschrijving.setIndAanschrijvenMetAdellijkeTitel(JaNee.Nee);
        aanschrijving.setIndAanschrijvingAlgorthmischAfgeleid(JaNee.Nee);
        aanschrijving.setPredikaat(predikaat);
        aanschrijving.setVoornamen(new Voornaam("voornaam"));
        aanschrijving.setVoorvoegsel(new Voorvoegsel("voor"));
        aanschrijving.setScheidingsteken(new ScheidingsTeken(","));
        aanschrijving.setGeslachtsnaam(new GeslachtsnaamComponent("geslachtsnaam"));
        persWeb.setAanschrijving(aanschrijving);

        // Geboorte
        PersoonGeboorteGroepBericht persoonGeboorteGroep = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroep.setDatumGeboorte(new Datum(20120101));
        persoonGeboorteGroep.setGemeenteGeboorte(gemeente);
        persoonGeboorteGroep.setWoonplaatsGeboorte(plaats);
        persoonGeboorteGroep.setBuitenlandseGeboortePlaats(new BuitenlandsePlaats("buitenland"));
        persoonGeboorteGroep.setBuitenlandseRegioGeboorte(new BuitenlandseRegio("bregio"));
        persoonGeboorteGroep.setLandGeboorte(land);
        persoonGeboorteGroep.setOmschrijvingGeboorteLocatie(new Omschrijving("omschrijving"));
        persWeb.setGeboorte(persoonGeboorteGroep);

        // Bijhoudingsverwantwoordelijke
        PersoonBijhoudingsVerantwoordelijkheidGroepBericht verantwoordelijkheid =
            new PersoonBijhoudingsVerantwoordelijkheidGroepBericht();
        ReflectionTestUtils.setField(verantwoordelijkheid, "verantwoordelijke", Verantwoordelijke.MINISTER);
        persWeb.setBijhoudingVerantwoordelijke(verantwoordelijkheid);

        // Opschorting
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        ReflectionTestUtils.setField(opschorting, "redenOpschorting", RedenOpschorting.MINISTER);
        persWeb.setOpschorting(opschorting);

        // Bijhoudingsgemeente
        PersoonBijhoudingsGemeenteGroepBericht bijhoudingsGemeente = new PersoonBijhoudingsGemeenteGroepBericht();
        ReflectionTestUtils.setField(bijhoudingsGemeente, "bijhoudingsGemeente", gemeente);
        ReflectionTestUtils.setField(bijhoudingsGemeente, "indOnverwerktDocumentAanwezig", JaNee.Nee);
        ReflectionTestUtils.setField(bijhoudingsGemeente, "datumInschrijvingInGemeente", new Datum(20120101));
        persWeb.setBijhoudenGemeente(bijhoudingsGemeente);

        // Inschrijving
        PersoonInschrijvingGroepBericht inschrijving = new PersoonInschrijvingGroepBericht();
        ReflectionTestUtils.setField(inschrijving, "versienummer", new Versienummer(1L));
        ReflectionTestUtils.setField(inschrijving, "datumInschrijving", new Datum(20101122));
        persWeb.setInschrijving(inschrijving);

        // Afgeleid amdministratief
        PersoonAfgeleidAdministratiefGroepBericht afgeleidAdministratief =
            new PersoonAfgeleidAdministratiefGroepBericht();
        ReflectionTestUtils.setField(afgeleidAdministratief, "indGegevensInOnderzoek", JaNee.Nee);
        ReflectionTestUtils.setField(afgeleidAdministratief, "tijdstipLaatsteWijziging", new DatumTijd(new Timestamp(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").parse(("2012/06/02 11:04:55:555")).getTime())));
        persWeb.setAfgeleidAdministratief(afgeleidAdministratief);

        PersoonModel pers = new PersoonModel(persWeb);

        // Identiteit
        ReflectionTestUtils.setField(pers, "id", id);

        // Voornamen
        PersoonVoornaamStandaardGroepBericht persoonVoornaamStandaardGroepWeb1 =
            new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamStandaardGroepWeb1.setVoornaam(new Voornaam("voornaam1"));

        PersoonVoornaamBericht persoonVoornaam1 = new PersoonVoornaamBericht();
        ReflectionTestUtils.setField(persoonVoornaam1, "volgnummer", new Volgnummer(1));
        ReflectionTestUtils.setField(persoonVoornaam1, "gegevens", persoonVoornaamStandaardGroepWeb1);

        pers.getPersoonVoornaam().add(new PersoonVoornaamModel(persoonVoornaam1, pers));

        PersoonVoornaamStandaardGroepBericht persoonVoornaamStandaardGroepWeb2 =
            new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamStandaardGroepWeb2.setVoornaam(new Voornaam("voornaam1"));

        PersoonVoornaamBericht persoonVoornaam2 = new PersoonVoornaamBericht();
        ReflectionTestUtils.setField(persoonVoornaam2, "volgnummer", new Volgnummer(1));
        ReflectionTestUtils.setField(persoonVoornaam2, "gegevens", persoonVoornaamStandaardGroepWeb2);

        pers.getPersoonVoornaam().add(new PersoonVoornaamModel(persoonVoornaam2, pers));

        // Adressen
        RedenWijzigingAdres rdn = new RedenWijzigingAdres();
        ReflectionTestUtils.setField(rdn, "redenWijzigingAdresCode", new RedenWijzigingAdresCode("A"));

        PersoonAdresStandaardGroepBericht adr = new PersoonAdresStandaardGroepBericht();
        ReflectionTestUtils.setField(adr, "soort", FunctieAdres.BRIEFADRES);
        ReflectionTestUtils.setField(adr, "datumAanvangAdreshouding", new Datum(20120101));
        ReflectionTestUtils.setField(adr, "redenwijziging", rdn);
        ReflectionTestUtils.setField(adr, "aangeverAdreshouding", AangeverAdreshoudingIdentiteit.GEZAGHOUDER);
        ReflectionTestUtils.setField(adr, "adresseerbaarObject", new Adresseerbaarobject("acd"));
        ReflectionTestUtils.setField(adr, "identificatiecodeNummeraanduiding", new IdentificatiecodeNummerAanduiding(
                "idcode"));
        ReflectionTestUtils.setField(adr, "gemeente", gemeente);
        ReflectionTestUtils.setField(adr, "naamOpenbareRuimte", new NaamOpenbareRuimte("opNaam"));
        ReflectionTestUtils.setField(adr, "afgekorteNaamOpenbareRuimte", new AfgekorteNaamOpenbareRuimte("afg"));
        ReflectionTestUtils.setField(adr, "gemeentedeel", new Gemeentedeel("gemdeel"));
        ReflectionTestUtils.setField(adr, "huisnummer", new Huisnummer("12"));
        ReflectionTestUtils.setField(adr, "huisletter", new Huisletter("A"));
        ReflectionTestUtils.setField(adr, "postcode", new Postcode("1234AB"));
        ReflectionTestUtils.setField(adr, "huisnummertoevoeging", new Huisnummertoevoeging("b"));
        ReflectionTestUtils.setField(adr, "woonplaats", plaats);
        ReflectionTestUtils.setField(adr, "locatietovAdres", new LocatieTovAdres("TO"));
        ReflectionTestUtils.setField(adr, "locatieOmschrijving", new LocatieOmschrijving("omschr"));
        ReflectionTestUtils.setField(adr, "buitenlandsAdresRegel1", new Adresregel("buitregel1"));
        ReflectionTestUtils.setField(adr, "buitenlandsAdresRegel2", new Adresregel("buitregel2"));
        ReflectionTestUtils.setField(adr, "buitenlandsAdresRegel3", new Adresregel("buitregel3"));
        ReflectionTestUtils.setField(adr, "buitenlandsAdresRegel4", new Adresregel("buitregel4"));
        ReflectionTestUtils.setField(adr, "buitenlandsAdresRegel5", new Adresregel("buitregel15"));
        ReflectionTestUtils.setField(adr, "buitenlandsAdresRegel6", new Adresregel("buitregel16"));
        ReflectionTestUtils.setField(adr, "land", land);
        ReflectionTestUtils.setField(adr, "datumVertrekUitNederland", new Datum(20500101));

        PersoonAdresBericht adres = new PersoonAdresBericht();
        ReflectionTestUtils.setField(adres, "gegevens", adr);

        pers.getAdressen().add(new PersoonAdresModel(adres, pers));

        // Nationaliteiten
        Nationaliteit nation = new Nationaliteit();
        ReflectionTestUtils.setField(nation, "naam", new Naam("abcd"));
        ReflectionTestUtils.setField(nation, "nationaliteitCode", new NationaliteitCode("0031"));

        PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();

        PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
        ReflectionTestUtils.setField(persoonNationaliteit, "nationaliteit", nation);
        ReflectionTestUtils.setField(persoonNationaliteit, "gegevens", gegevens);

        pers.getNationaliteiten().add(new PersoonNationaliteitModel(persoonNationaliteit, pers));

        // Geslachtsnaamcomponenten
        AdellijkeTitel titel = new AdellijkeTitel();
        ReflectionTestUtils.setField(titel, "adellijkeTitelCode", new AdellijkeTitelCode("H"));

        PersoonGeslachtsnaamCompStandaardGroepBericht gegevensComp1 =
            new PersoonGeslachtsnaamCompStandaardGroepBericht();
        ReflectionTestUtils.setField(gegevensComp1, "predikaat", predikaat);
        ReflectionTestUtils.setField(gegevensComp1, "adellijkeTitel", titel);
        ReflectionTestUtils.setField(gegevensComp1, "voorvoegsel", new Voorvoegsel("voorv"));
        ReflectionTestUtils.setField(gegevensComp1, "scheidingsteken", new ScheidingsTeken(";"));
        ReflectionTestUtils.setField(gegevensComp1, "geslachtsnaamComponent", new GeslachtsnaamComponent("comp"));

        PersoonGeslachtsnaamComponentBericht component1 = new PersoonGeslachtsnaamComponentBericht();
        ReflectionTestUtils.setField(component1, "volgnummer", new Volgnummer(1));
        ReflectionTestUtils.setField(component1, "persoonGeslachtsnaamCompStandaardGroep", gegevensComp1);

        pers.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(component1, pers));

        PersoonGeslachtsnaamComponentBericht component2 = new PersoonGeslachtsnaamComponentBericht();
        ReflectionTestUtils.setField(component2, "volgnummer", new Volgnummer(1));

        Predikaat pred2 = new Predikaat();
        ReflectionTestUtils.setField(pred2, "code", new PredikaatCode("H"));

        AdellijkeTitel titel2 = new AdellijkeTitel();
        ReflectionTestUtils.setField(titel2, "adellijkeTitelCode", new AdellijkeTitelCode("H"));

        PersoonGeslachtsnaamCompStandaardGroepBericht gegevensComp2 =
            new PersoonGeslachtsnaamCompStandaardGroepBericht();
        ReflectionTestUtils.setField(gegevensComp2, "predikaat", pred2);
        ReflectionTestUtils.setField(gegevensComp2, "adellijkeTitel", titel2);
        ReflectionTestUtils.setField(gegevensComp2, "voorvoegsel", new Voorvoegsel("voorv"));
        ReflectionTestUtils.setField(gegevensComp2, "scheidingsteken", new ScheidingsTeken(";"));
        ReflectionTestUtils.setField(gegevensComp2, "geslachtsnaamComponent", new GeslachtsnaamComponent("comp"));
        ReflectionTestUtils.setField(component2, "persoonGeslachtsnaamCompStandaardGroep", gegevensComp2);

        pers.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamComponentModel(component2, pers));

        // Overlijden
        PersoonOverlijdenGroepBericht overlijdenGroepBericht = new PersoonOverlijdenGroepBericht();
        overlijdenGroepBericht.setDatumOverlijden(new Datum(21000101));
        overlijdenGroepBericht.setOverlijdenGemeente(gemeente);
        overlijdenGroepBericht.setWoonplaatsOverlijden(plaats);
        overlijdenGroepBericht.setBuitenlandsePlaatsOverlijden(new BuitenlandsePlaats("buitenland"));
        overlijdenGroepBericht.setBuitenlandseRegioOverlijden(new BuitenlandseRegio("bregio"));
        overlijdenGroepBericht.setLandOverlijden(land);
        overlijdenGroepBericht.setOmschrijvingLocatieOverlijden(new LocatieOmschrijving("omschrijving"));
        PersoonOverlijdenGroepModel overlijden = new PersoonOverlijdenGroepModel(overlijdenGroepBericht);

        ReflectionTestUtils.setField(pers, "overlijden", overlijden);

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
        ReflectionTestUtils.setField(ouderlijkGezagGroep, "indOuderlijkGezag", indOuderHeeftGezag);

        BetrokkenheidOuderschapGroepBericht ouderschapGroep = new BetrokkenheidOuderschapGroepBericht();

        BetrokkenheidBericht betrokkenheidWeb = new BetrokkenheidBericht();
        ReflectionTestUtils.setField(betrokkenheidWeb, "betrokkenheidOuderlijkGezag", ouderlijkGezagGroep);
        ReflectionTestUtils.setField(betrokkenheidWeb, "rol", soort);
        ReflectionTestUtils.setField(betrokkenheidWeb, "betrokkenheidOuderschap", ouderschapGroep);

        BetrokkenheidModel betrokkenheid = new BetrokkenheidModel(betrokkenheidWeb, betrokkene, relatie);
        ReflectionTestUtils.setField(betrokkenheid, "betrokkene", betrokkene);
        ReflectionTestUtils.setField(betrokkenheid.getBetrokkenheidOuderschap(), "datumAanvangOuderschap",
                datumAanvangOuderschap);

        if (betrokkene.getBetrokkenheden() == null) {
            ReflectionTestUtils.setField(betrokkene, "betrokkenheden", new HashSet<BetrokkenheidBericht>());
        }
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
    protected void voegIndicatieToeAanPersoon(final PersoonModel persoon, final SoortIndicatie soort, final JaNee waarde)
    {
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

        PersoonIndicatieStandaardGroepBericht gegevensWeb = new PersoonIndicatieStandaardGroepBericht();
        gegevensWeb.setSoort(soort);
        gegevensWeb.setWaarde(waarde);

        PersoonIndicatieBericht persoonIndicatieWeb = new PersoonIndicatieBericht();
        persoonIndicatieWeb.setGegevens(gegevensWeb);

        PersoonIndicatieModel persoonIndicatie = new PersoonIndicatieModel(persoonIndicatieWeb);
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
            final Partij gemeente, final Land land, final Plaats plaats, final SoortRelatie relatieSoort)
            throws ParseException
    {
        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        PersoonModel opTeVragenPersoon = maakPersoon(1L, gemeente, plaats, land, "persoon");
        PersoonModel kindVanOpTeVragenPersoon = maakPersoon(2L, gemeente, plaats, land, "kind");
        PersoonModel vader = maakPersoon(3L, gemeente, plaats, land, "vader");
        PersoonModel moeder = maakPersoon(4L, gemeente, plaats, land, "moeder");
        PersoonModel partnerVanOpTeVragenPersoon = maakPersoon(5L, gemeente, plaats, land, "partner");

        // Betrokkenheid familie rechtelijk opTeVragenPersoon van vader en moeder
        RelatieBericht relatieWeb = new RelatieBericht();
        ReflectionTestUtils.setField(relatieWeb, "gegevens", new RelatieStandaardGroepBericht());

        RelatieModel relatie = new RelatieModel(relatieWeb);
        ReflectionTestUtils.setField(relatie, "betrokkenheden", new HashSet<Betrokkenheid>());
        ReflectionTestUtils.setField(relatie, "gegevens", new RelatieStandaardGroepModel(
                new RelatieStandaardGroepBericht()));

        // relatie.setDatumAanvang(Integer.valueOf(20110305));
        ReflectionTestUtils.setField(relatie, "soort", SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, vader, SoortBetrokkenheid.OUDER, relatie, 20110305);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, moeder, SoortBetrokkenheid.OUDER, relatie, 20110305);
        BetrokkenheidModel kindBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, opTeVragenPersoon, SoortBetrokkenheid.KIND, relatie, null);
        opTeVragenPersoon.getBetrokkenheden().add(kindBetr);

        RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();
        ReflectionTestUtils.setField(gegevens, "omschrijvingLocatieAanvang", new Omschrijving("Timboektoe"));
        ReflectionTestUtils.setField(gegevens, "omschrijvingLocatieEinde", new Omschrijving("blakalafm"));
        ReflectionTestUtils.setField(gegevens, "datumAanvang", new Datum(19830404));
        ReflectionTestUtils.setField(gegevens, "datumEinde", new Datum(19830404));
        ReflectionTestUtils.setField(gegevens, "landAanvang", land);
        ReflectionTestUtils.setField(gegevens, "landEinde", land);
        ReflectionTestUtils.setField(gegevens, "buitenlandsePlaatsAanvang", new BuitenlandsePlaats("Verweggiestan"));
        ReflectionTestUtils.setField(gegevens, "buitenlandsePlaatsEinde", new BuitenlandsePlaats("Tajikistan"));
        ReflectionTestUtils.setField(gegevens, "buitenlandseRegioAanvang", new BuitenlandseRegio("In het bos"));
        ReflectionTestUtils.setField(gegevens, "buitenlandseRegioEinde", new BuitenlandseRegio("In de woestijn"));
        ReflectionTestUtils.setField(gegevens, "gemeenteAanvang", gemeente);
        ReflectionTestUtils.setField(gegevens, "gemeenteEinde", gemeente);
        ReflectionTestUtils.setField(gegevens, "woonPlaatsAanvang", plaats);
        ReflectionTestUtils.setField(gegevens, "woonPlaatsEinde", plaats);

        // Huwelijk
        RelatieBericht huwelijkWeb = new RelatieBericht();
        ReflectionTestUtils.setField(huwelijkWeb, "gegevens", gegevens);

        RelatieModel huwelijk = new RelatieModel(huwelijkWeb);
        ReflectionTestUtils.setField(huwelijk, "betrokkenheden", new HashSet<Betrokkenheid>());
        ReflectionTestUtils.setField(huwelijk, "soort", relatieSoort);

        BetrokkenheidModel partnerVanOpTeVragenPersoonBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, partnerVanOpTeVragenPersoon, SoortBetrokkenheid.PARTNER,
                    huwelijk, null);
        BetrokkenheidModel partnerBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, opTeVragenPersoon, SoortBetrokkenheid.PARTNER, huwelijk,
                    null);
        opTeVragenPersoon.getBetrokkenheden().add(partnerBetr);

        // Betrokkenheid familie rechtelijk kind van Op te vragen persoon en partner
        RelatieBericht kindVanOpTeVragenPersRelatieWeb = new RelatieBericht();
        ReflectionTestUtils.setField(kindVanOpTeVragenPersRelatieWeb, "gegevens", new RelatieStandaardGroepBericht());

        RelatieModel kindVanOpTeVragenPersRelatie = new RelatieModel(kindVanOpTeVragenPersRelatieWeb);
        // kindVanOpTeVragenPersRelatie.setDatumAanvang(Integer.valueOf(20110305));
        ReflectionTestUtils.setField(kindVanOpTeVragenPersRelatie, "betrokkenheden", new HashSet<Betrokkenheid>());
        ReflectionTestUtils.setField(kindVanOpTeVragenPersRelatie, "soort", SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

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
                            .format("<brp:%s xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">",
                                    getBerichtElementNaam()));

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
                .append(bouwElement("crossReferentienummer", "onbekend")).append("</brp:stuurgegevens>");
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
        ReflectionTestUtils.setField(nederland, "landCode", new LandCode("0031"));

        PersoonIdentificatieNummersGroepBericht PersoonIdentificatieNummersGroepWeb =
            new PersoonIdentificatieNummersGroepBericht();
        PersoonIdentificatieNummersGroepWeb.setBurgerServiceNummer(new Burgerservicenummer("123456789"));

        PersoonGeslachtsAanduidingGroepBericht persoonGeslachtsAanduidingGroepWeb =
            new PersoonGeslachtsAanduidingGroepBericht();
        persoonGeslachtsAanduidingGroepWeb.setGeslachtsAanduiding(GeslachtsAanduiding.MAN);

        PersoonGeboorteGroepBericht persoonGeboorteGroepWeb = new PersoonGeboorteGroepBericht();
        ReflectionTestUtils.setField(persoonGeboorteGroepWeb, "landGeboorte", nederland);
        ReflectionTestUtils.setField(persoonGeboorteGroepWeb, "datumGeboorte", new Datum(20060325));

        PersoonSamengesteldeNaamGroepBericht samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        ReflectionTestUtils.setField(samengesteldNaam, "geslachtsnaam", new GeslachtsnaamComponent("Test"));

        PersoonAanschrijvingGroepBericht aanschrijving = new PersoonAanschrijvingGroepBericht();
        ReflectionTestUtils.setField(aanschrijving, "geslachtsnaam", new GeslachtsnaamComponent("Test"));
        ReflectionTestUtils.setField(aanschrijving, "indAanschrijvingAlgorthmischAfgeleid", JaNee.Nee);

        PersoonBijhoudingsVerantwoordelijkheidGroepBericht bijhoudingsVerantwoordelijke =
            new PersoonBijhoudingsVerantwoordelijkheidGroepBericht();
        ReflectionTestUtils.setField(bijhoudingsVerantwoordelijke, "verantwoordelijke", Verantwoordelijke.COLLEGE);

        PersoonAfgeleidAdministratiefGroepBericht administratief = new PersoonAfgeleidAdministratiefGroepBericht();
        ReflectionTestUtils.setField(administratief, "tijdstipLaatsteWijziging",
                new DatumTijd(new Timestamp(new Date().getTime())));

        PersoonInschrijvingGroepBericht inschrijving = new PersoonInschrijvingGroepBericht();
        ReflectionTestUtils.setField(inschrijving, "datumInschrijving", new Datum(20060325));
        ReflectionTestUtils.setField(inschrijving, "versienummer", new Versienummer(2L));

        PersoonBericht persoonWeb = new PersoonBericht();
        persoonWeb.setIdentificatieNummers(PersoonIdentificatieNummersGroepWeb);
        persoonWeb.setGeslachtsAanduiding(persoonGeslachtsAanduidingGroepWeb);
        persoonWeb.setGeboorte(persoonGeboorteGroepWeb);
        persoonWeb.setSamengesteldeNaam(samengesteldNaam);
        persoonWeb.setAanschrijving(aanschrijving);
        persoonWeb.setBijhoudingVerantwoordelijke(bijhoudingsVerantwoordelijke);
        persoonWeb.setAfgeleidAdministratief(administratief);
        persoonWeb.setInschrijving(inschrijving);

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        ReflectionTestUtils.setField(gegevens, "land", nederland);

        PersoonModel persoonMdl = new PersoonModel(persoonWeb);

        PersoonAdresBericht minimaalAdres = new PersoonAdresBericht();
        ReflectionTestUtils.setField(minimaalAdres, "persoon", persoonWeb);
        ReflectionTestUtils.setField(minimaalAdres, "gegevens", gegevens);

        persoonMdl.getAdressen().add(new PersoonAdresModel(minimaalAdres, persoonMdl));

        return persoonMdl;
    }
}
