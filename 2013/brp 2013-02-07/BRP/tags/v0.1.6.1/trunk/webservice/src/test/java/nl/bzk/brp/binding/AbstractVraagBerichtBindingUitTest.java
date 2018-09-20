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
import nl.bzk.brp.model.groep.impl.usr.BetrokkenheidOuderlijkGezagGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.BetrokkenheidOuderschapGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonAanschrijvingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonAdresStandaardGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonAfgeleidAdministratiefGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonBijhoudingsGemeenteGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonBijhoudingsVerantwoordelijkheidGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonGeboorteGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonGeslachtsAanduidingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonGeslachtsnaamCompStandaardGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonIdentificatieNummersGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonInschrijvingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonNationaliteitStandaardGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonOpschortingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonOverlijdenGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonSamengesteldeNaamGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonVoornaamStandaardGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.RelatieStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.BetrokkenheidMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonAdresMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonGeslachtsnaamComponentMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonIndicatieMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonNationaliteitMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonVoornaamMdl;
import nl.bzk.brp.model.objecttype.impl.usr.RelatieMdl;
import nl.bzk.brp.model.objecttype.interfaces.usr.Betrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.statisch.Verantwoordelijke;
import nl.bzk.brp.model.objecttype.statisch.WijzeGebruikGeslachtsnaam;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Abstracte class bedoeld voor het testen van de binding van antwoordberichten op vraag/informatie berichten. Deze
 * class biedt helper methodes om testdata aan te maken welke veelal gebruikt kan worden in de tests en helper methodes
 * voor het opbouwen van het verwachte antwoord.
 */
public abstract class AbstractVraagBerichtBindingUitTest<T> extends AbstractBerichtBindingUitTest<T> {

    /**
     * Retourneert de naam van het bericht element van het bericht dat in de test wordt getest.
     *
     * @return de naam van het bericht element van het bericht dat in de test wordt getest.
     */
    public abstract String getBerichtElementNaam();

    /**
     * Instantieert een {@link PersoonMdl} en vult deze geheel met test data, welke deels kan worden meegegeven, maar
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
    protected PersoonMdl maakPersoon(final Long id, final Partij gemeente, final Plaats plaats, final Land land,
            final String bsn) throws ParseException
    {
        PersoonMdl pers = new PersoonMdl();
        // Identiteit
        ReflectionTestUtils.setField(pers, "id", id);
        // Identificatienummers
        PersoonIdentificatieNummersGroepMdl identificatieNummersGroep = new PersoonIdentificatieNummersGroepMdl();
        ReflectionTestUtils.setField(identificatieNummersGroep, "burgerServiceNummer", new Burgerservicenummer(bsn));
        ReflectionTestUtils.setField(identificatieNummersGroep, "administratieNummer", new Administratienummer(
                "987654321"));
        ReflectionTestUtils.setField(pers, "identificatieNummers", identificatieNummersGroep);
        // Geslachtsaanduiding
        PersoonGeslachtsAanduidingGroepMdl geslachtsAanduiding = new PersoonGeslachtsAanduidingGroepMdl();
        ReflectionTestUtils.setField(geslachtsAanduiding, "geslachtsAanduiding", GeslachtsAanduiding.MAN);

        ReflectionTestUtils.setField(pers, "geslachtsAanduiding", geslachtsAanduiding);
        // Samengesteldnaam
        PersoonSamengesteldeNaamGroepMdl persoonSamengesteldeNaam = new PersoonSamengesteldeNaamGroepMdl();
        AdellijkeTitel adellijkeTitel = new AdellijkeTitel();
        ReflectionTestUtils.setField(adellijkeTitel, "adellijkeTitelCode", new AdellijkeTitelCode("B"));
        ReflectionTestUtils.setField(persoonSamengesteldeNaam, "adellijkeTitel", adellijkeTitel);
        Predikaat predikaat = new Predikaat();
        ReflectionTestUtils.setField(predikaat, "code", new PredikaatCode("H"));
        ReflectionTestUtils.setField(persoonSamengesteldeNaam, "predikaat", predikaat);
        ReflectionTestUtils.setField(persoonSamengesteldeNaam, "voornamen", new Voornaam("Voornaam"));
        ReflectionTestUtils.setField(persoonSamengesteldeNaam, "voorvoegsel", new Voorvoegsel("voor"));
        ReflectionTestUtils.setField(persoonSamengesteldeNaam, "scheidingsteken", new ScheidingsTeken(","));
        ReflectionTestUtils.setField(persoonSamengesteldeNaam, "geslachtsnaam", new GeslachtsnaamComponent(
                "geslachtsnaam"));
        ReflectionTestUtils.setField(persoonSamengesteldeNaam, "indNamenreeksAlsGeslachtsNaam", JaNee.Nee);
        ReflectionTestUtils.setField(persoonSamengesteldeNaam, "indAlgorithmischAfgeleid", JaNee.Nee);

        ReflectionTestUtils.setField(pers, "samengesteldeNaam", persoonSamengesteldeNaam);
        // Aanschrijving
        PersoonAanschrijvingGroepMdl aanschrijving = new PersoonAanschrijvingGroepMdl();
        ReflectionTestUtils.setField(aanschrijving, "gebruikGeslachtsnaam", WijzeGebruikGeslachtsnaam.EIGEN);
        ReflectionTestUtils.setField(aanschrijving, "indAanschrijvenMetAdellijkeTitel", JaNee.Nee);
        ReflectionTestUtils.setField(aanschrijving, "indAanschrijvingAlgorthmischAfgeleid", JaNee.Nee);
        ReflectionTestUtils.setField(aanschrijving, "predikaat", predikaat);
        ReflectionTestUtils.setField(aanschrijving, "voornamen", new Voornaam("voornaam"));
        ReflectionTestUtils.setField(aanschrijving, "voorvoegsel", new Voorvoegsel("voor"));
        ReflectionTestUtils.setField(aanschrijving, "scheidingsteken", new ScheidingsTeken(","));
        ReflectionTestUtils.setField(aanschrijving, "geslachtsnaam", new GeslachtsnaamComponent("geslachtsnaam"));

        ReflectionTestUtils.setField(pers, "aanschrijving", aanschrijving);
        // Geboorte
        PersoonGeboorteGroepMdl geboorte = new PersoonGeboorteGroepMdl();
        ReflectionTestUtils.setField(geboorte, "datumGeboorte", new Datum(20120101));
        ReflectionTestUtils.setField(geboorte, "gemeenteGeboorte", gemeente);
        ReflectionTestUtils.setField(geboorte, "woonplaatsGeboorte", plaats);
        ReflectionTestUtils.setField(geboorte, "buitenlandseGeboortePlaats", new BuitenlandsePlaats("buitenland"));
        ReflectionTestUtils.setField(geboorte, "buitenlandseRegioGeboorte", new BuitenlandseRegio("bregio"));
        ReflectionTestUtils.setField(geboorte, "landGeboorte", land);
        ReflectionTestUtils.setField(geboorte, "omschrijvingGeboorteLocatie", new Omschrijving("omschrijving"));

        ReflectionTestUtils.setField(pers, "geboorte", geboorte);
        // Overlijden
        PersoonOverlijdenGroepMdl overlijden = new PersoonOverlijdenGroepMdl();
        ReflectionTestUtils.setField(overlijden, "datumOverlijden", new Datum(21000101));
        ReflectionTestUtils.setField(overlijden, "overlijdenGemeente", gemeente);
        ReflectionTestUtils.setField(overlijden, "woonplaatsOverlijden", plaats);
        ReflectionTestUtils.setField(overlijden, "buitenlandsePlaatsOverlijden", new BuitenlandsePlaats("buitenland"));
        ReflectionTestUtils.setField(overlijden, "buitenlandseRegioOverlijden", new BuitenlandseRegio("bregio"));
        ReflectionTestUtils.setField(overlijden, "landOverlijden", land);
        ReflectionTestUtils.setField(overlijden, "omschrijvingLocatieOverlijden", new LocatieOmschrijving(
                "omschrijving"));

        ReflectionTestUtils.setField(pers, "overlijden", overlijden);
        // Bijhoudingsverwantwoordelijke
        PersoonBijhoudingsVerantwoordelijkheidGroepMdl verantwoordelijkheid =
            new PersoonBijhoudingsVerantwoordelijkheidGroepMdl();
        ReflectionTestUtils.setField(verantwoordelijkheid, "verantwoordelijke", Verantwoordelijke.MINISTER);

        ReflectionTestUtils.setField(pers, "bijhoudingVerantwoordelijke", verantwoordelijkheid);
        // Opschorting
        PersoonOpschortingGroepMdl opschorting = new PersoonOpschortingGroepMdl();
        ReflectionTestUtils.setField(opschorting, "redenOpschorting", RedenOpschorting.MINISTER);

        ReflectionTestUtils.setField(pers, "opschorting", opschorting);
        // Bijhoudingsgemeente
        PersoonBijhoudingsGemeenteGroepMdl bijhoudingsGemeente = new PersoonBijhoudingsGemeenteGroepMdl();
        ReflectionTestUtils.setField(bijhoudingsGemeente, "bijhoudingsGemeente", gemeente);
        ReflectionTestUtils.setField(bijhoudingsGemeente, "indOnverwerktDocumentAanwezig", JaNee.Nee);
        ReflectionTestUtils.setField(bijhoudingsGemeente, "datumInschrijvingInGemeente", new Datum(20120101));

        ReflectionTestUtils.setField(pers, "bijhoudenGemeente", bijhoudingsGemeente);
        // Inschrijving
        PersoonInschrijvingGroepMdl inschrijving = new PersoonInschrijvingGroepMdl();
        ReflectionTestUtils.setField(inschrijving, "versienummer", new Versienummer(1L));
        ReflectionTestUtils.setField(inschrijving, "datumInschrijving", new Datum(20101122));

        ReflectionTestUtils.setField(pers, "inschrijving", inschrijving);
        // Afgeleid amdministratief
        PersoonAfgeleidAdministratiefGroepMdl afgeleidAdministratief = new PersoonAfgeleidAdministratiefGroepMdl();
        ReflectionTestUtils.setField(afgeleidAdministratief, "indGegevensInOnderzoek", JaNee.Nee);
        ReflectionTestUtils.setField(afgeleidAdministratief, "tijdstipLaatsteWijziging", new DatumTijd(new Timestamp(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").parse(("2012/06/02 11:04:55:555")).getTime())));

        ReflectionTestUtils.setField(pers, "afgeleidAdministratief", afgeleidAdministratief);
        // Voornamen
        Set<PersoonVoornaamMdl> voornamen = new HashSet<PersoonVoornaamMdl>();
        PersoonVoornaamMdl persoonVoornaam1 = new PersoonVoornaamMdl();
        ReflectionTestUtils.setField(persoonVoornaam1, "volgnummer", new Volgnummer(1));
        PersoonVoornaamStandaardGroepMdl gegevens1 = new PersoonVoornaamStandaardGroepMdl();
        ReflectionTestUtils.setField(gegevens1, "voornaam", new Voornaam("voornaam1"));
        ReflectionTestUtils.setField(persoonVoornaam1, "gegevens", gegevens1);
        voornamen.add(persoonVoornaam1);

        PersoonVoornaamMdl persoonVoornaam2 = new PersoonVoornaamMdl();
        ReflectionTestUtils.setField(persoonVoornaam2, "volgnummer", new Volgnummer(1));
        PersoonVoornaamStandaardGroepMdl gegevens2 = new PersoonVoornaamStandaardGroepMdl();
        ReflectionTestUtils.setField(gegevens2, "voornaam", new Voornaam("voornaam1"));
        ReflectionTestUtils.setField(persoonVoornaam2, "gegevens", gegevens2);
        voornamen.add(persoonVoornaam2);

        ReflectionTestUtils.setField(pers, "persoonVoornaam", voornamen);
        // Geslachtsnaamcomponenten
        Set<PersoonGeslachtsnaamComponentMdl> geslNamen = new HashSet<PersoonGeslachtsnaamComponentMdl>();
        PersoonGeslachtsnaamComponentMdl component1 = new PersoonGeslachtsnaamComponentMdl();
        ReflectionTestUtils.setField(component1, "volgnummer", new Volgnummer(1));
        PersoonGeslachtsnaamCompStandaardGroepMdl gegevensComp1 = new PersoonGeslachtsnaamCompStandaardGroepMdl();
        Predikaat pred = new Predikaat();
        ReflectionTestUtils.setField(pred, "code", new PredikaatCode("H"));
        ReflectionTestUtils.setField(gegevensComp1, "predikaat", pred);
        AdellijkeTitel titel = new AdellijkeTitel();
        ReflectionTestUtils.setField(titel, "adellijkeTitelCode", new AdellijkeTitelCode("H"));
        ReflectionTestUtils.setField(gegevensComp1, "adellijkeTitel", titel);
        ReflectionTestUtils.setField(gegevensComp1, "voorvoegsel", new Voorvoegsel("voorv"));
        ReflectionTestUtils.setField(gegevensComp1, "scheidingsteken", new ScheidingsTeken(";"));
        ReflectionTestUtils.setField(gegevensComp1, "geslachtsnaamComponent", new GeslachtsnaamComponent("comp"));
        ReflectionTestUtils.setField(component1, "persoonGeslachtsnaamCompStandaardGroep", gegevensComp1);
        geslNamen.add(component1);

        PersoonGeslachtsnaamComponentMdl component2 = new PersoonGeslachtsnaamComponentMdl();
        ReflectionTestUtils.setField(component2, "volgnummer", new Volgnummer(1));
        PersoonGeslachtsnaamCompStandaardGroepMdl gegevensComp2 = new PersoonGeslachtsnaamCompStandaardGroepMdl();
        Predikaat pred2 = new Predikaat();
        ReflectionTestUtils.setField(pred2, "code", new PredikaatCode("H"));
        ReflectionTestUtils.setField(gegevensComp2, "predikaat", pred2);
        AdellijkeTitel titel2 = new AdellijkeTitel();
        ReflectionTestUtils.setField(titel2, "adellijkeTitelCode", new AdellijkeTitelCode("H"));
        ReflectionTestUtils.setField(gegevensComp2, "adellijkeTitel", titel2);
        ReflectionTestUtils.setField(gegevensComp2, "voorvoegsel", new Voorvoegsel("voorv"));
        ReflectionTestUtils.setField(gegevensComp2, "scheidingsteken", new ScheidingsTeken(";"));
        ReflectionTestUtils.setField(gegevensComp2, "geslachtsnaamComponent", new GeslachtsnaamComponent("comp"));
        ReflectionTestUtils.setField(component2, "persoonGeslachtsnaamCompStandaardGroep", gegevensComp2);
        geslNamen.add(component2);

        ReflectionTestUtils.setField(pers, "geslachtsnaamcomponenten", geslNamen);
        // Adressen
        Set<PersoonAdresMdl> adressen = new HashSet<PersoonAdresMdl>();
        PersoonAdresMdl adres = new PersoonAdresMdl();
        PersoonAdresStandaardGroepMdl adr = new PersoonAdresStandaardGroepMdl();
        ReflectionTestUtils.setField(adr, "soort", FunctieAdres.BRIEFADRES);
        ReflectionTestUtils.setField(adr, "datumAanvangAdreshouding", new Datum(20120101));
        RedenWijzigingAdres rdn = new RedenWijzigingAdres();
        ReflectionTestUtils.setField(rdn, "redenWijzigingAdresCode", new RedenWijzigingAdresCode("A"));
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

        ReflectionTestUtils.setField(adres, "gegevens", adr);
        adressen.add(adres);

        ReflectionTestUtils.setField(pers, "adressen", adressen);
        // Nationaliteiten
        Set<PersoonNationaliteitMdl> nationaliteiten = new HashSet<PersoonNationaliteitMdl>();
        PersoonNationaliteitMdl persoonNationaliteit = new PersoonNationaliteitMdl();
        Nationaliteit nation = new Nationaliteit();
        ReflectionTestUtils.setField(nation, "naam", new Naam("abcd"));
        ReflectionTestUtils.setField(nation, "nationaliteitCode", new NationaliteitCode("0031"));
        ReflectionTestUtils.setField(persoonNationaliteit, "nationaliteit", nation);

        PersoonNationaliteitStandaardGroepMdl gegevens = new PersoonNationaliteitStandaardGroepMdl();
        ReflectionTestUtils.setField(persoonNationaliteit, "gegevens", gegevens);
        nationaliteiten.add(persoonNationaliteit);

        ReflectionTestUtils.setField(pers, "nationaliteiten", nationaliteiten);
        return pers;
    }

    /**
     * Instantieert een {@link Betrokkenheid} en voegt deze toe aan de opgegeven relatie. De velden van de
     * betrokkenheid
     * worden gezet op basis van de opgegeven waardes. De geinstantieerde betrokkenheid wordt geretourneerd.
     *
     * @param indOuderHeeftGezag boolean die aangeeft of de opgegeven persoon als ouder gezag heeft.
     * @param betrokkene de persoon die als betrokkene fungeert binnen de aan te maken betrokkenheid.
     * @param soort de soort van de betrokkenheid.
     * @param relatie de relatie waar aan de betrokkenheid moet worden toegevoegd.
     * @param datumAanvangOuderschap zet een eventuele datumAanvangOuderschap op de betrokkenheid.
     * @return de aangemaakte betrokkenheid.
     */
    protected BetrokkenheidMdl maakBetrokkenheidEnVoegtoeAanRelatie(final JaNee indOuderHeeftGezag,
            final PersoonMdl betrokkene, final SoortBetrokkenheid soort, final RelatieMdl relatie,
            final Integer datumAanvangOuderschap)
    {
        BetrokkenheidOuderlijkGezagGroepMdl ouderlijkGezagGroep = new BetrokkenheidOuderlijkGezagGroepMdl();
        ReflectionTestUtils.setField(ouderlijkGezagGroep, "indOuderlijkGezag", indOuderHeeftGezag);

        BetrokkenheidOuderschapGroepMdl ouderschapGroep = new BetrokkenheidOuderschapGroepMdl();
        ReflectionTestUtils.setField(ouderschapGroep, "datumAanvangOuderschap", datumAanvangOuderschap);

        BetrokkenheidMdl betrokkenheid = new BetrokkenheidMdl();
        ReflectionTestUtils.setField(betrokkenheid, "betrokkenheidOuderlijkGezag", ouderlijkGezagGroep);
        ReflectionTestUtils.setField(betrokkenheid, "betrokkene", betrokkene);
        ReflectionTestUtils.setField(betrokkenheid, "rol", soort);
        ReflectionTestUtils.setField(betrokkenheid, "relatie", relatie);
        ReflectionTestUtils.setField(betrokkenheid, "betrokkenheidOuderschap", ouderschapGroep);

        if (betrokkene.getBetrokkenheden() == null) {
            ReflectionTestUtils.setField(betrokkene, "betrokkenheden", new HashSet<BetrokkenheidMdl>());
        }
        /*Set<BetrokkenheidMdl> betrokkenheden = new HashSet<BetrokkenheidMdl>();
        betrokkenheden.add(betrokkenheid);*/

        ReflectionTestUtils.invokeMethod(betrokkene.getBetrokkenheden(), "add", betrokkenheid);
        ReflectionTestUtils.invokeMethod(relatie.getBetrokkenheden(), "add", betrokkenheid);
        return betrokkenheid;
    }

    /**
     * Voegt een PersoonIndicatie toe aan de opgegeven persoon, waarbij de indicatie die wordt toegevoegd wordt
     * opgebouwd op basis van de opgegeven soort van de indicatie en de opgegeven waarde.
     *
     * @param persoon de persoon aan wie de indicatie wordt toegevoegd.
     * @param soort de soort van de indicatie die moet worden toegevoegd.
     * @param waarde de waarde van de indicatie die moet worden toegevoegd.
     */
    protected void voegIndicatieToeAanPersoon(final PersoonMdl persoon, final SoortIndicatie soort, final JaNee waarde)
    {
        if (persoon.getIndicaties() == null) {
            SortedSet<PersoonIndicatieMdl> set = new TreeSet<PersoonIndicatieMdl>(
                    new Comparator<PersoonIndicatieMdl>() {

                        @Override
                        public int compare(final PersoonIndicatieMdl a,
                                           final PersoonIndicatieMdl b)
                        {
                            return a.getSoort().compareTo(b.getSoort());
                        }
                    }
            );
            ReflectionTestUtils.setField(persoon, "indicaties", set);
        }
        PersoonIndicatieMdl persoonIndicatie = new PersoonIndicatieMdl();
        ReflectionTestUtils.setField(persoonIndicatie, "persoon", persoon);
        ReflectionTestUtils.setField(persoonIndicatie, "soort", soort);
        ReflectionTestUtils.setField(persoonIndicatie, "waarde", waarde);

        ReflectionTestUtils.invokeMethod(persoon.getIndicaties(), "add", persoonIndicatie);
    }

    /**
     * Bouwt een {@link OpvragenPersoonResultaat} instantie met daarin een compleet persoon, inclusief relaties naar
     * een kind, ouders en een partner. De opgegeven gemeente, land en plaats worden gebruikt voor alle personen, dus
     * zowel voor de op te vragen persoon in het resultaat als voor de betrokkene in zijn/haar relaties.
     *
     * @param gemeente de gemeente die in het antwoordbericht wordt gebruikt voor de personen
     * @param land het land die in het antwoordbericht wordt gebruikt voor de personen
     * @param plaats de woonplaats die in het antwoordbericht wordt gebruikt voor de personen
     * @return het verwachte bericht resultaat
     * @throws ParseException
     */
    protected OpvragenPersoonResultaat bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(
            final Partij gemeente, final Land land, final Plaats plaats, final SoortRelatie relatieSoort) throws ParseException
    {
        Set<PersoonMdl> gevondenPersonen = new HashSet<PersoonMdl>();
        PersoonMdl opTeVragenPersoon = maakPersoon(1L, gemeente, plaats, land, "persoon");
        PersoonMdl kindVanOpTeVragenPersoon = maakPersoon(2L, gemeente, plaats, land, "kind");
        PersoonMdl vader = maakPersoon(3L, gemeente, plaats, land, "vader");
        PersoonMdl moeder = maakPersoon(4L, gemeente, plaats, land, "moeder");
        PersoonMdl partnerVanOpTeVragenPersoon = maakPersoon(5L, gemeente, plaats, land, "partner");

        // Betrokkenheid familie rechtelijk opTeVragenPersoon van vader en moeder
        RelatieMdl relatie = new RelatieMdl();
        ReflectionTestUtils.setField(relatie, "betrokkenheden", new HashSet<Betrokkenheid>());

        // relatie.setDatumAanvang(Integer.valueOf(20110305));
        ReflectionTestUtils.setField(relatie, "soort", SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, vader, SoortBetrokkenheid.OUDER, relatie, 20110305);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, moeder, SoortBetrokkenheid.OUDER, relatie, 20110305);
        BetrokkenheidMdl kindBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, opTeVragenPersoon, SoortBetrokkenheid.KIND, relatie, null);
        opTeVragenPersoon.getBetrokkenheden().add(kindBetr);

        RelatieStandaardGroepMdl gegevens = new RelatieStandaardGroepMdl();
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
        RelatieMdl huwelijk = new RelatieMdl();
        ReflectionTestUtils.setField(huwelijk, "betrokkenheden", new HashSet<Betrokkenheid>());
        ReflectionTestUtils.setField(huwelijk, "soort", relatieSoort);
        ReflectionTestUtils.setField(huwelijk, "gegevens", gegevens);



        BetrokkenheidMdl partnerVanOpTeVragenPersoonBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, partnerVanOpTeVragenPersoon, SoortBetrokkenheid.PARTNER,
                    huwelijk, null);
        BetrokkenheidMdl partnerBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, opTeVragenPersoon, SoortBetrokkenheid.PARTNER, huwelijk, null);
        opTeVragenPersoon.getBetrokkenheden().add(partnerBetr);

        // Betrokkenheid familie rechtelijk kind van Op te vragen persoon en partner
        RelatieMdl kindVanOpTeVragenPersRelatie = new RelatieMdl();
        // kindVanOpTeVragenPersRelatie.setDatumAanvang(Integer.valueOf(20110305));
        ReflectionTestUtils.setField(kindVanOpTeVragenPersRelatie, "betrokkenheden", new HashSet<Betrokkenheid>());
        ReflectionTestUtils.setField(kindVanOpTeVragenPersRelatie, "soort",
                SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, kindVanOpTeVragenPersoon, SoortBetrokkenheid.KIND,
                kindVanOpTeVragenPersRelatie, null);
        maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, partnerVanOpTeVragenPersoon, SoortBetrokkenheid.OUDER,
                kindVanOpTeVragenPersRelatie, 20110305);
        BetrokkenheidMdl optevragenPersoonVaderBetr =
            maakBetrokkenheidEnVoegtoeAanRelatie(JaNee.Nee, opTeVragenPersoon, SoortBetrokkenheid.OUDER,
                    kindVanOpTeVragenPersRelatie, 20110305);
        opTeVragenPersoon.getBetrokkenheden().add(optevragenPersoonVaderBetr);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        gevondenPersonen.add(opTeVragenPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);
        return resultaat;
    }

    /**
     * Bouwt en retourneert een verwacht antwoord bericht met opgeven tijdstip van registratie, maar zonder meldingen
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
     * de content zoals opgenomen in het bestand zoals opgegeven door de 'verwachtAntwoordBerichtBestandsNaam'.
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
     * Voegt een standaard stuurgegevens element toe aan de opgegeven {@link StringBuilder} instantie.
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
     * waardes voor tijdstip registratie en hoogste melding code in het resultaat worden verwerkt.
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
     * Bouwt en retourneert een minimaal persoon (conform XSD eisen opvragen persoon details).
     *
     * @return een persoon met minimale gegevens ingevuld.
     */
    protected PersoonMdl bouwMinimaalPersoon() {
        Land nederland = new Land();
        ReflectionTestUtils.setField(nederland, "landCode", new LandCode("0031"));

        PersoonIdentificatieNummersGroepMdl pid = new PersoonIdentificatieNummersGroepMdl();
        ReflectionTestUtils.setField(pid, "burgerServiceNummer", new Burgerservicenummer("123456789"));

        PersoonGeslachtsAanduidingGroepMdl persGeslAand = new PersoonGeslachtsAanduidingGroepMdl();
        ReflectionTestUtils.setField(persGeslAand, "geslachtsAanduiding", GeslachtsAanduiding.MAN);

        PersoonGeboorteGroepMdl persoonGeboorte = new PersoonGeboorteGroepMdl();
        ReflectionTestUtils.setField(persoonGeboorte, "landGeboorte", nederland);
        ReflectionTestUtils.setField(persoonGeboorte, "datumGeboorte", new Datum(20060325));

        PersoonSamengesteldeNaamGroepMdl samengesteldNaam = new PersoonSamengesteldeNaamGroepMdl();
        ReflectionTestUtils.setField(samengesteldNaam, "geslachtsnaam", new GeslachtsnaamComponent("Test"));

        PersoonAanschrijvingGroepMdl aanschrijving = new PersoonAanschrijvingGroepMdl();
        ReflectionTestUtils.setField(aanschrijving, "geslachtsnaam", new GeslachtsnaamComponent("Test"));
        ReflectionTestUtils.setField(aanschrijving, "indAanschrijvingAlgorthmischAfgeleid", JaNee.Nee);

        PersoonBijhoudingsVerantwoordelijkheidGroepMdl bijhoudingsVerantwoordelijke =
            new PersoonBijhoudingsVerantwoordelijkheidGroepMdl();
        ReflectionTestUtils.setField(bijhoudingsVerantwoordelijke, "verantwoordelijke", Verantwoordelijke.COLLEGE);

        PersoonAfgeleidAdministratiefGroepMdl administratief = new PersoonAfgeleidAdministratiefGroepMdl();
        ReflectionTestUtils.setField(administratief, "tijdstipLaatsteWijziging", new DatumTijd(new Timestamp(new Date().getTime())));

        PersoonInschrijvingGroepMdl inschrijving = new PersoonInschrijvingGroepMdl();
        ReflectionTestUtils.setField(inschrijving, "datumInschrijving", new Datum(20060325));
        ReflectionTestUtils.setField(inschrijving, "versienummer", new Versienummer(2L));

        PersoonMdl minimaalPersoon = new PersoonMdl();
        ReflectionTestUtils.setField(minimaalPersoon, "soort", SoortPersoon.INGESCHREVENE);
        ReflectionTestUtils.setField(minimaalPersoon, "identificatieNummers", pid);
        ReflectionTestUtils.setField(minimaalPersoon, "geslachtsAanduiding", persGeslAand);
        ReflectionTestUtils.setField(minimaalPersoon, "geboorte", persoonGeboorte);
        ReflectionTestUtils.setField(minimaalPersoon, "samengesteldeNaam", samengesteldNaam);
        ReflectionTestUtils.setField(minimaalPersoon, "aanschrijving", aanschrijving);
        ReflectionTestUtils.setField(minimaalPersoon, "bijhoudingVerantwoordelijke", bijhoudingsVerantwoordelijke);
        ReflectionTestUtils.setField(minimaalPersoon, "afgeleidAdministratief", administratief);
        ReflectionTestUtils.setField(minimaalPersoon, "inschrijving", inschrijving);

        PersoonAdresMdl minimaalAdres = new PersoonAdresMdl();
        ReflectionTestUtils.setField(minimaalAdres, "persoon", minimaalPersoon);

        PersoonAdresStandaardGroepMdl gegevens = new PersoonAdresStandaardGroepMdl();
        ReflectionTestUtils.setField(gegevens, "land", nederland);
        ReflectionTestUtils.setField(minimaalAdres, "gegevens", gegevens);

        Set<PersoonAdresMdl> adressen = new HashSet<PersoonAdresMdl>();
        adressen.add(minimaalAdres);

        ReflectionTestUtils.setField(minimaalPersoon, "adressen", adressen);

        return minimaalPersoon;
    }

}
