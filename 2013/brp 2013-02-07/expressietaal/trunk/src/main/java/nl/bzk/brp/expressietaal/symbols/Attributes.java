/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidRolGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkBuitenlandsePlaatsAanvangGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkBuitenlandsePlaatsEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkBuitenlandseRegioAanvangGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkBuitenlandseRegioEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkDatumAanvangGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkDatumEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkGemeenteAanvangGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkGemeenteEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkLandAanvangGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkLandEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkOmschrijvingLocatieAanvangGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkOmschrijvingLocatieEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkRedenEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkSoortGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkWoonplaatsAanvangGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkWoonplaatsEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingAdellijkeTitelGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingAlgoritmischAfgeleidGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingGeslachtsnaamGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingNaamgebruikGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingPredikaatGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingScheidingstekenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingTitelsPredikatenBijAanschrijvenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingVoornamenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAanschrijvingVoorvoegselGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdressenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAfgeleidadministratiefGegevensInOnderzoekGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonAfgeleidadministratiefTijdstipLaatsteWijzigingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonBetrokkenhedenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingsaardGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingsgemeenteDatumInschrijvingInGemeenteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingsgemeenteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingsgemeenteOnverwerktDocumentAanwezigGetter;
import nl.bzk.brp.expressietaal.symbols.solvers
        .PersoonBijzondereverblijfsrechtelijkepositieBijzondereVerblijfsrechtelijkePositieGetter;
import nl.bzk.brp.expressietaal.symbols.solvers
        .PersoonEuverkiezingenDatumAanleidingAanpassingDeelnameEuVerkiezingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonEuverkiezingenDatumEindeUitsluitingEuKiesrechtGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonEuverkiezingenDeelnameEuVerkiezingenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteBuitenlandsePlaatsGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteBuitenlandseRegioGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteDatumGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteGemeenteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteLandGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteOmschrijvingLocatieGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteWoonplaatsGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsnaamcomponentenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersAdministratienummerGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersBurgerservicenummerGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonImmigratieDatumVestigingInNederlandGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonImmigratieLandVanwaarGevestigdGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonIndicatiesGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingDatumGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVersienummerGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonNationaliteitenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonOpschortingRedenbijhoudingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenBuitenlandsePlaatsGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenBuitenlandseRegioGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenDatumGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenGemeenteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenLandGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenOmschrijvingLocatieGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenWoonplaatsGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartGemeenteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVolledigGeconverteerdGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonReisdocumentenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldenaamAdellijkeTitelGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldenaamAlgoritmischAfgeleidGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldenaamGeslachtsnaamGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldenaamNamenreeksGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldenaamPredikaatGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldenaamScheidingstekenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldenaamVoornamenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldenaamVoorvoegselGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonSoortGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingnlkiesrechtDatumEindeUitsluitingNlKiesrechtGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingnlkiesrechtUitsluitingNlKiesrechtGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfstitelDatumAanvangGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfstitelDatumVoorzienEindeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfstitelGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonVoornamenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresAangeverAdreshoudingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresAdresseerbaarObjectGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresAfgekorteNaamOpenbareRuimteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel1Getter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel2Getter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel3Getter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel4Getter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel5Getter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel6Getter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresDatumAanvangAdreshoudingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresDatumVertrekUitNederlandGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresGemeenteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresGemeentedeelGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresHuisletterGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresHuisnummerGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresHuisnummertoevoegingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresIdentificatiecodeNummeraanduidingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresLandGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresLocatieOmschrijvingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresLocatieTovAdresGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresNaamOpenbareRuimteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresPersoonNietAangetroffenOpAdresGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresPostcodeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresRedenWijzigingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresSoortGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresWoonplaatsGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentAdellijkeTitelGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentNaamGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentPredikaatGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentScheidingstekenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVolgnummerGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVoorvoegselGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonindicatieSoortGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonindicatieWaardeGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitNationaliteitGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitRedenVerkrijgingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitRedenVerliesGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentAutoriteitVanAfgifteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumIngangDocumentGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumInhoudingvermissingGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumUitgifteGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumVoorzieneEindeGeldigheidGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentLengteHouderGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentNummerGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentRedenVervallenGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentSoortGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamNaamGetter;
import nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVolgnummerGetter;
import nl.bzk.brp.model.RootObject;


/**
 * Opsomming van alle attributen zoals die in het BMR voorkomen en zoals die gebruikt kunnen worden in expressies.
 * <p/>
 * Generator: nl.bzk.brp.generatoren.java.SymbolTableGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-31 14:31:48.
 * Gegenereerd op: Thu Jan 31 15:38:23 CET 2013.
 */
public enum Attributes {

    /**
     * Attribuut HUWELIJK_SOORT.
     * BMR-attribuut 'Soort' van objecttype 'Relatie'.
     */
    HUWELIJK_SOORT("soort", ExpressieType.STRING, ExpressieType.HUWELIJK, new HuwelijkSoortGetter()),
    /**
     * Attribuut HUWELIJK_DATUM_AANVANG.
     * BMR-attribuut 'Datum aanvang' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_DATUM_AANVANG("datum_aanvang", ExpressieType.DATE, ExpressieType.HUWELIJK,
            new HuwelijkDatumAanvangGetter()),
    /**
     * Attribuut HUWELIJK_GEMEENTE_AANVANG.
     * BMR-attribuut 'Gemeente aanvang' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_GEMEENTE_AANVANG("gemeente_aanvang", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkGemeenteAanvangGetter()),
    /**
     * Attribuut HUWELIJK_WOONPLAATS_AANVANG.
     * BMR-attribuut 'Woonplaats aanvang' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_WOONPLAATS_AANVANG("woonplaats_aanvang", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkWoonplaatsAanvangGetter()),
    /**
     * Attribuut HUWELIJK_BUITENLANDSE_PLAATS_AANVANG.
     * BMR-attribuut 'Buitenlandse plaats aanvang' van objecttype 'Huwelijk / Geregistreerd
     * partnerschap'.
     */
    HUWELIJK_BUITENLANDSE_PLAATS_AANVANG("buitenlandse_plaats_aanvang", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkBuitenlandsePlaatsAanvangGetter()),
    /**
     * Attribuut HUWELIJK_BUITENLANDSE_REGIO_AANVANG.
     * BMR-attribuut 'Buitenlandse regio aanvang' van objecttype 'Huwelijk / Geregistreerd
     * partnerschap'.
     */
    HUWELIJK_BUITENLANDSE_REGIO_AANVANG("buitenlandse_regio_aanvang", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkBuitenlandseRegioAanvangGetter()),
    /**
     * Attribuut HUWELIJK_OMSCHRIJVING_LOCATIE_AANVANG.
     * BMR-attribuut 'Omschrijving locatie aanvang' van objecttype 'Huwelijk / Geregistreerd
     * partnerschap'.
     */
    HUWELIJK_OMSCHRIJVING_LOCATIE_AANVANG("omschrijving_locatie_aanvang", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkOmschrijvingLocatieAanvangGetter()),
    /**
     * Attribuut HUWELIJK_LAND_AANVANG.
     * BMR-attribuut 'Land aanvang' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_LAND_AANVANG("land_aanvang", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkLandAanvangGetter()),
    /**
     * Attribuut HUWELIJK_REDEN_EINDE.
     * BMR-attribuut 'Reden einde' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_REDEN_EINDE("reden_einde", ExpressieType.STRING, ExpressieType.HUWELIJK, new HuwelijkRedenEindeGetter()),
    /**
     * Attribuut HUWELIJK_DATUM_EINDE.
     * BMR-attribuut 'Datum einde' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_DATUM_EINDE("datum_einde", ExpressieType.DATE, ExpressieType.HUWELIJK, new HuwelijkDatumEindeGetter()),
    /**
     * Attribuut HUWELIJK_GEMEENTE_EINDE.
     * BMR-attribuut 'Gemeente einde' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_GEMEENTE_EINDE("gemeente_einde", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkGemeenteEindeGetter()),
    /**
     * Attribuut HUWELIJK_WOONPLAATS_EINDE.
     * BMR-attribuut 'Woonplaats einde' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_WOONPLAATS_EINDE("woonplaats_einde", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkWoonplaatsEindeGetter()),
    /**
     * Attribuut HUWELIJK_BUITENLANDSE_PLAATS_EINDE.
     * BMR-attribuut 'Buitenlandse plaats einde' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_BUITENLANDSE_PLAATS_EINDE("buitenlandse_plaats_einde", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkBuitenlandsePlaatsEindeGetter()),
    /**
     * Attribuut HUWELIJK_BUITENLANDSE_REGIO_EINDE.
     * BMR-attribuut 'Buitenlandse regio einde' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_BUITENLANDSE_REGIO_EINDE("buitenlandse_regio_einde", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkBuitenlandseRegioEindeGetter()),
    /**
     * Attribuut HUWELIJK_OMSCHRIJVING_LOCATIE_EINDE.
     * BMR-attribuut 'Omschrijving locatie einde' van objecttype 'Huwelijk / Geregistreerd
     * partnerschap'.
     */
    HUWELIJK_OMSCHRIJVING_LOCATIE_EINDE("omschrijving_locatie_einde", ExpressieType.STRING, ExpressieType.HUWELIJK,
            new HuwelijkOmschrijvingLocatieEindeGetter()),
    /**
     * Attribuut HUWELIJK_LAND_EINDE.
     * BMR-attribuut 'Land einde' van objecttype 'Huwelijk / Geregistreerd partnerschap'.
     */
    HUWELIJK_LAND_EINDE("land_einde", ExpressieType.STRING, ExpressieType.HUWELIJK, new HuwelijkLandEindeGetter()),
    /**
     * Attribuut PERSOON_SOORT.
     * BMR-attribuut 'Soort' van objecttype 'Persoon'.
     */
    PERSOON_SOORT("soort", ExpressieType.STRING, ExpressieType.PERSOON, new PersoonSoortGetter()),
    /**
     * Attribuut PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIP_LAATSTE_WIJZIGING.
     * BMR-attribuut 'Tijdstip laatste wijziging' van objecttype 'Persoon'.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIP_LAATSTE_WIJZIGING("afgeleidadministratief.tijdstip_laatste_wijziging",
            ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonAfgeleidadministratiefTijdstipLaatsteWijzigingGetter()),
    /**
     * Attribuut PERSOON_AFGELEIDADMINISTRATIEF_GEGEVENS_IN_ONDERZOEK.
     * BMR-attribuut 'Gegevens in onderzoek?' van objecttype 'Persoon'.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_GEGEVENS_IN_ONDERZOEK("afgeleidadministratief.gegevens_in_onderzoek",
            ExpressieType.BOOLEAN, ExpressieType.PERSOON, new PersoonAfgeleidadministratiefGegevensInOnderzoekGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.
     * BMR-attribuut 'Burgerservicenummer' van objecttype 'Persoon'.
     */
    PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER("identificatienummers.burgerservicenummer", ExpressieType.NUMBER,
            ExpressieType.PERSOON, new PersoonIdentificatienummersBurgerservicenummerGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.
     * BMR-attribuut 'Administratienummer' van objecttype 'Persoon'.
     */
    PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER("identificatienummers.administratienummer", ExpressieType.NUMBER,
            ExpressieType.PERSOON, new PersoonIdentificatienummersAdministratienummerGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDENAAM_ALGORITMISCH_AFGELEID.
     * BMR-attribuut 'Algoritmisch afgeleid?' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDENAAM_ALGORITMISCH_AFGELEID("samengesteldenaam.algoritmisch_afgeleid", ExpressieType.BOOLEAN,
            ExpressieType.PERSOON, new PersoonSamengesteldenaamAlgoritmischAfgeleidGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDENAAM_NAMENREEKS.
     * BMR-attribuut 'Namenreeks?' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDENAAM_NAMENREEKS("samengesteldenaam.namenreeks", ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new PersoonSamengesteldenaamNamenreeksGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDENAAM_PREDIKAAT.
     * BMR-attribuut 'Predikaat' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDENAAM_PREDIKAAT("samengesteldenaam.predikaat", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonSamengesteldenaamPredikaatGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDENAAM_VOORNAMEN.
     * BMR-attribuut 'Voornamen' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDENAAM_VOORNAMEN("samengesteldenaam.voornamen", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonSamengesteldenaamVoornamenGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDENAAM_ADELLIJKE_TITEL.
     * BMR-attribuut 'Adellijke titel' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDENAAM_ADELLIJKE_TITEL("samengesteldenaam.adellijke_titel", ExpressieType.STRING,
            ExpressieType.PERSOON, new PersoonSamengesteldenaamAdellijkeTitelGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.
     * BMR-attribuut 'Voorvoegsel' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL("samengesteldenaam.voorvoegsel", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonSamengesteldenaamVoorvoegselGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.
     * BMR-attribuut 'Scheidingsteken' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN("samengesteldenaam.scheidingsteken", ExpressieType.STRING,
            ExpressieType.PERSOON, new PersoonSamengesteldenaamScheidingstekenGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAM.
     * BMR-attribuut 'Geslachtsnaam' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAM("samengesteldenaam.geslachtsnaam", ExpressieType.STRING,
            ExpressieType.PERSOON, new PersoonSamengesteldenaamGeslachtsnaamGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_DATUM.
     * BMR-attribuut 'Datum geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_DATUM("geboorte.datum", ExpressieType.DATE, ExpressieType.PERSOON,
            new PersoonGeboorteDatumGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_GEMEENTE.
     * BMR-attribuut 'Gemeente geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_GEMEENTE("geboorte.gemeente", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonGeboorteGemeenteGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_WOONPLAATS.
     * BMR-attribuut 'Woonplaats geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_WOONPLAATS("geboorte.woonplaats", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonGeboorteWoonplaatsGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_BUITENLANDSE_PLAATS.
     * BMR-attribuut 'Buitenlandse plaats geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_BUITENLANDSE_PLAATS("geboorte.buitenlandse_plaats", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonGeboorteBuitenlandsePlaatsGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_BUITENLANDSE_REGIO.
     * BMR-attribuut 'Buitenlandse regio geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_BUITENLANDSE_REGIO("geboorte.buitenlandse_regio", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonGeboorteBuitenlandseRegioGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_OMSCHRIJVING_LOCATIE.
     * BMR-attribuut 'Omschrijving locatie geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_OMSCHRIJVING_LOCATIE("geboorte.omschrijving_locatie", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonGeboorteOmschrijvingLocatieGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_LAND.
     * BMR-attribuut 'Land geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_LAND("geboorte.land", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonGeboorteLandGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING.
     * BMR-attribuut 'Geslachtsaanduiding' van objecttype 'Persoon'.
     */
    PERSOON_GESLACHTSAANDUIDING("geslachtsaanduiding", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonGeslachtsaanduidingGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_DATUM.
     * BMR-attribuut 'Datum inschrijving' van objecttype 'Persoon'.
     */
    PERSOON_INSCHRIJVING_DATUM("inschrijving.datum", ExpressieType.DATE, ExpressieType.PERSOON,
            new PersoonInschrijvingDatumGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERSIENUMMER.
     * BMR-attribuut 'Versienummer' van objecttype 'Persoon'.
     */
    PERSOON_INSCHRIJVING_VERSIENUMMER("inschrijving.versienummer", ExpressieType.NUMBER, ExpressieType.PERSOON,
            new PersoonInschrijvingVersienummerGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDINGSAARD.
     * BMR-attribuut 'Bijhoudingsaard' van objecttype 'Persoon'.
     */
    PERSOON_BIJHOUDINGSAARD("bijhoudingsaard", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonBijhoudingsaardGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDINGSGEMEENTE.
     * BMR-attribuut 'Bijhoudingsgemeente' van objecttype 'Persoon'.
     */
    PERSOON_BIJHOUDINGSGEMEENTE("bijhoudingsgemeente", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonBijhoudingsgemeenteGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDINGSGEMEENTE_DATUM_INSCHRIJVING_IN_GEMEENTE.
     * BMR-attribuut 'Datum inschrijving in gemeente' van objecttype
     * 'Persoon'.
     */
    PERSOON_BIJHOUDINGSGEMEENTE_DATUM_INSCHRIJVING_IN_GEMEENTE("bijhoudingsgemeente.datum_inschrijving_in_gemeente",
            ExpressieType.DATE, ExpressieType.PERSOON,
            new PersoonBijhoudingsgemeenteDatumInschrijvingInGemeenteGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDINGSGEMEENTE_ONVERWERKT_DOCUMENT_AANWEZIG.
     * BMR-attribuut 'Onverwerkt document aanwezig?' van objecttype 'Persoon'.
     */
    PERSOON_BIJHOUDINGSGEMEENTE_ONVERWERKT_DOCUMENT_AANWEZIG("bijhoudingsgemeente.onverwerkt_document_aanwezig",
            ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new PersoonBijhoudingsgemeenteOnverwerktDocumentAanwezigGetter()),
    /**
     * Attribuut PERSOON_OPSCHORTING_REDENBIJHOUDING.
     * BMR-attribuut 'Reden opschorting bijhouding' van objecttype 'Persoon'.
     */
    PERSOON_OPSCHORTING_REDENBIJHOUDING("opschorting.redenbijhouding", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonOpschortingRedenbijhoudingGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_DATUM.
     * BMR-attribuut 'Datum overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_DATUM("overlijden.datum", ExpressieType.DATE, ExpressieType.PERSOON,
            new PersoonOverlijdenDatumGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_GEMEENTE.
     * BMR-attribuut 'Gemeente overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_GEMEENTE("overlijden.gemeente", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonOverlijdenGemeenteGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_WOONPLAATS.
     * BMR-attribuut 'Woonplaats overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_WOONPLAATS("overlijden.woonplaats", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonOverlijdenWoonplaatsGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_BUITENLANDSE_PLAATS.
     * BMR-attribuut 'Buitenlandse plaats overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_BUITENLANDSE_PLAATS("overlijden.buitenlandse_plaats", ExpressieType.STRING,
            ExpressieType.PERSOON, new PersoonOverlijdenBuitenlandsePlaatsGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_BUITENLANDSE_REGIO.
     * BMR-attribuut 'Buitenlandse regio overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_BUITENLANDSE_REGIO("overlijden.buitenlandse_regio", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonOverlijdenBuitenlandseRegioGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_OMSCHRIJVING_LOCATIE.
     * BMR-attribuut 'Omschrijving locatie overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_OMSCHRIJVING_LOCATIE("overlijden.omschrijving_locatie", ExpressieType.STRING,
            ExpressieType.PERSOON, new PersoonOverlijdenOmschrijvingLocatieGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_LAND.
     * BMR-attribuut 'Land overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_LAND("overlijden.land", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonOverlijdenLandGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_NAAMGEBRUIK.
     * BMR-attribuut 'Naamgebruik' van objecttype 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_NAAMGEBRUIK("aanschrijving.naamgebruik", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonAanschrijvingNaamgebruikGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_TITELS_PREDIKATEN_BIJ_AANSCHRIJVEN.
     * BMR-attribuut 'Titels predikaten bij aanschrijven?' van objecttype
     * 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_TITELS_PREDIKATEN_BIJ_AANSCHRIJVEN("aanschrijving.titels_predikaten_bij_aanschrijven",
            ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new PersoonAanschrijvingTitelsPredikatenBijAanschrijvenGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_ALGORITMISCH_AFGELEID.
     * BMR-attribuut 'Aanschrijving algoritmisch afgeleid?' van objecttype 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_ALGORITMISCH_AFGELEID("aanschrijving.algoritmisch_afgeleid", ExpressieType.BOOLEAN,
            ExpressieType.PERSOON, new PersoonAanschrijvingAlgoritmischAfgeleidGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_PREDIKAAT.
     * BMR-attribuut 'Predikaat aanschrijving' van objecttype 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_PREDIKAAT("aanschrijving.predikaat", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonAanschrijvingPredikaatGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_VOORNAMEN.
     * BMR-attribuut 'Voornamen aanschrijving' van objecttype 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_VOORNAMEN("aanschrijving.voornamen", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonAanschrijvingVoornamenGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_ADELLIJKE_TITEL.
     * BMR-attribuut 'Adellijke titel aanschrijving' van objecttype 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_ADELLIJKE_TITEL("aanschrijving.adellijke_titel", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonAanschrijvingAdellijkeTitelGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_VOORVOEGSEL.
     * BMR-attribuut 'Voorvoegsel aanschrijving' van objecttype 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_VOORVOEGSEL("aanschrijving.voorvoegsel", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonAanschrijvingVoorvoegselGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_SCHEIDINGSTEKEN.
     * BMR-attribuut 'Scheidingsteken aanschrijving' van objecttype 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_SCHEIDINGSTEKEN("aanschrijving.scheidingsteken", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonAanschrijvingScheidingstekenGetter()),
    /**
     * Attribuut PERSOON_AANSCHRIJVING_GESLACHTSNAAM.
     * BMR-attribuut 'Geslachtsnaam aanschrijving' van objecttype 'Persoon'.
     */
    PERSOON_AANSCHRIJVING_GESLACHTSNAAM("aanschrijving.geslachtsnaam", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonAanschrijvingGeslachtsnaamGetter()),
    /**
     * Attribuut PERSOON_IMMIGRATIE_LAND_VANWAAR_GEVESTIGD.
     * BMR-attribuut 'Land vanwaar gevestigd' van objecttype 'Persoon'.
     */
    PERSOON_IMMIGRATIE_LAND_VANWAAR_GEVESTIGD("immigratie.land_vanwaar_gevestigd", ExpressieType.STRING,
            ExpressieType.PERSOON, new PersoonImmigratieLandVanwaarGevestigdGetter()),
    /**
     * Attribuut PERSOON_IMMIGRATIE_DATUM_VESTIGING_IN_NEDERLAND.
     * BMR-attribuut 'Datum vestiging in Nederland' van objecttype 'Persoon'.
     */
    PERSOON_IMMIGRATIE_DATUM_VESTIGING_IN_NEDERLAND("immigratie.datum_vestiging_in_nederland", ExpressieType.DATE,
            ExpressieType.PERSOON, new PersoonImmigratieDatumVestigingInNederlandGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSTITEL.
     * BMR-attribuut 'Verblijfstitel' van objecttype 'Persoon'.
     */
    PERSOON_VERBLIJFSTITEL("verblijfstitel", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonVerblijfstitelGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSTITEL_DATUM_AANVANG.
     * BMR-attribuut 'Datum aanvang verblijfstitel' van objecttype 'Persoon'.
     */
    PERSOON_VERBLIJFSTITEL_DATUM_AANVANG("verblijfstitel.datum_aanvang", ExpressieType.DATE, ExpressieType.PERSOON,
            new PersoonVerblijfstitelDatumAanvangGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSTITEL_DATUM_VOORZIEN_EINDE.
     * BMR-attribuut 'Datum voorzien einde verblijfstitel' van objecttype 'Persoon'.
     */
    PERSOON_VERBLIJFSTITEL_DATUM_VOORZIEN_EINDE("verblijfstitel.datum_voorzien_einde", ExpressieType.DATE,
            ExpressieType.PERSOON, new PersoonVerblijfstitelDatumVoorzienEindeGetter()),
    /**
     * Attribuut PERSOON_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE.
     * BMR-attribuut 'Bijzondere verblijfsrechtelijke
     * positie' van objecttype 'Persoon'.
     */
    PERSOON_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE(
            "bijzondereverblijfsrechtelijkepositie.bijzondere_verblijfsrechtelijke_positie", ExpressieType.STRING,
            ExpressieType.PERSOON,
            new PersoonBijzondereverblijfsrechtelijkepositieBijzondereVerblijfsrechtelijkePositieGetter()),
    /**
     * Attribuut PERSOON_UITSLUITINGNLKIESRECHT_UITSLUITING_NL_KIESRECHT.
     * BMR-attribuut 'Uitsluiting NL kiesrecht?' van objecttype 'Persoon'.
     */
    PERSOON_UITSLUITINGNLKIESRECHT_UITSLUITING_NL_KIESRECHT("uitsluitingnlkiesrecht.uitsluiting_nl_kiesrecht",
            ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new PersoonUitsluitingnlkiesrechtUitsluitingNlKiesrechtGetter()),
    /**
     * Attribuut PERSOON_UITSLUITINGNLKIESRECHT_DATUM_EINDE_UITSLUITING_NL_KIESRECHT.
     * BMR-attribuut 'Datum einde uitsluiting NL kiesrecht'
     * van objecttype 'Persoon'.
     */
    PERSOON_UITSLUITINGNLKIESRECHT_DATUM_EINDE_UITSLUITING_NL_KIESRECHT(
            "uitsluitingnlkiesrecht.datum_einde_uitsluiting_nl_kiesrecht", ExpressieType.DATE, ExpressieType.PERSOON,
            new PersoonUitsluitingnlkiesrechtDatumEindeUitsluitingNlKiesrechtGetter()),
    /**
     * Attribuut PERSOON_EUVERKIEZINGEN_DEELNAME_EU_VERKIEZINGEN.
     * BMR-attribuut 'Deelname EU verkiezingen?' van objecttype 'Persoon'.
     */
    PERSOON_EUVERKIEZINGEN_DEELNAME_EU_VERKIEZINGEN("euverkiezingen.deelname_eu_verkiezingen", ExpressieType.BOOLEAN,
            ExpressieType.PERSOON, new PersoonEuverkiezingenDeelnameEuVerkiezingenGetter()),
    /**
     * Attribuut PERSOON_EUVERKIEZINGEN_DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZING.
     * BMR-attribuut 'Datum aanleiding aanpassing deelname
     * EU verkiezing' van objecttype 'Persoon'.
     */
    PERSOON_EUVERKIEZINGEN_DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZING(
            "euverkiezingen.datum_aanleiding_aanpassing_deelname_eu_verkiezing", ExpressieType.DATE,
            ExpressieType.PERSOON, new PersoonEuverkiezingenDatumAanleidingAanpassingDeelnameEuVerkiezingGetter()),
    /**
     * Attribuut PERSOON_EUVERKIEZINGEN_DATUM_EINDE_UITSLUITING_EU_KIESRECHT.
     * BMR-attribuut 'Datum einde uitsluiting EU kiesrecht' van objecttype
     * 'Persoon'.
     */
    PERSOON_EUVERKIEZINGEN_DATUM_EINDE_UITSLUITING_EU_KIESRECHT("euverkiezingen.datum_einde_uitsluiting_eu_kiesrecht",
            ExpressieType.DATE, ExpressieType.PERSOON,
            new PersoonEuverkiezingenDatumEindeUitsluitingEuKiesrechtGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_GEMEENTE.
     * BMR-attribuut 'Gemeente persoonskaart' van objecttype 'Persoon'.
     */
    PERSOON_PERSOONSKAART_GEMEENTE("persoonskaart.gemeente", ExpressieType.STRING, ExpressieType.PERSOON,
            new PersoonPersoonskaartGemeenteGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VOLLEDIG_GECONVERTEERD.
     * BMR-attribuut 'Persoonskaart volledig geconverteerd?' van objecttype 'Persoon'.
     */
    PERSOON_PERSOONSKAART_VOLLEDIG_GECONVERTEERD("persoonskaart.volledig_geconverteerd", ExpressieType.BOOLEAN,
            ExpressieType.PERSOON, new PersoonPersoonskaartVolledigGeconverteerdGetter()),
    /**
     * Attribuut PERSOON_VOORNAMEN.
     * BMR-attribuut 'Persoon' van objecttype 'Persoon \ Voornaam'.
     */
    PERSOON_VOORNAMEN("voornamen", ExpressieType.INDEXED, ExpressieType.PERSOON, new PersoonVoornamenGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VOLGNUMMER.
     * BMR-attribuut 'Volgnummer' van objecttype 'Persoon \ Voornaam'.
     */
    PERSOONVOORNAAM_VOLGNUMMER("volgnummer", ExpressieType.NUMBER, ExpressieType.PERSOON, Attributes.PERSOON_VOORNAMEN,
            new PersoonvoornaamVolgnummerGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_NAAM.
     * BMR-attribuut 'Naam' van objecttype 'Persoon \ Voornaam'.
     */
    PERSOONVOORNAAM_NAAM("naam", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_VOORNAMEN,
            new PersoonvoornaamNaamGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSNAAMCOMPONENTEN.
     * BMR-attribuut 'Persoon' van objecttype 'Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENTEN("geslachtsnaamcomponenten", ExpressieType.INDEXED, ExpressieType.PERSOON,
            new PersoonGeslachtsnaamcomponentenGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VOLGNUMMER.
     * BMR-attribuut 'Volgnummer' van objecttype 'Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VOLGNUMMER("volgnummer", ExpressieType.NUMBER, ExpressieType.PERSOON,
            Attributes.PERSOON_GESLACHTSNAAMCOMPONENTEN, new PersoongeslachtsnaamcomponentVolgnummerGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_PREDIKAAT.
     * BMR-attribuut 'Predikaat' van objecttype 'Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_PREDIKAAT("predikaat", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_GESLACHTSNAAMCOMPONENTEN, new PersoongeslachtsnaamcomponentPredikaatGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_ADELLIJKE_TITEL.
     * BMR-attribuut 'Adellijke titel' van objecttype 'Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_ADELLIJKE_TITEL("adellijke_titel", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_GESLACHTSNAAMCOMPONENTEN, new PersoongeslachtsnaamcomponentAdellijkeTitelGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VOORVOEGSEL.
     * BMR-attribuut 'Voorvoegsel' van objecttype 'Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VOORVOEGSEL("voorvoegsel", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_GESLACHTSNAAMCOMPONENTEN, new PersoongeslachtsnaamcomponentVoorvoegselGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN.
     * BMR-attribuut 'Scheidingsteken' van objecttype 'Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN("scheidingsteken", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_GESLACHTSNAAMCOMPONENTEN, new PersoongeslachtsnaamcomponentScheidingstekenGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_NAAM.
     * BMR-attribuut 'Naam' van objecttype 'Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_NAAM("naam", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_GESLACHTSNAAMCOMPONENTEN, new PersoongeslachtsnaamcomponentNaamGetter()),
    /**
     * Attribuut PERSOON_NATIONALITEITEN.
     * BMR-attribuut 'Persoon' van objecttype 'Persoon \ Nationaliteit'.
     */
    PERSOON_NATIONALITEITEN("nationaliteiten", ExpressieType.INDEXED, ExpressieType.PERSOON,
            new PersoonNationaliteitenGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_NATIONALITEIT.
     * BMR-attribuut 'Nationaliteit' van objecttype 'Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_NATIONALITEIT("nationaliteit", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_NATIONALITEITEN, new PersoonnationaliteitNationaliteitGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_REDEN_VERKRIJGING.
     * BMR-attribuut 'Reden verkrijging' van objecttype 'Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_REDEN_VERKRIJGING("reden_verkrijging", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_NATIONALITEITEN, new PersoonnationaliteitRedenVerkrijgingGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_REDEN_VERLIES.
     * BMR-attribuut 'Reden verlies' van objecttype 'Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_REDEN_VERLIES("reden_verlies", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_NATIONALITEITEN, new PersoonnationaliteitRedenVerliesGetter()),
    /**
     * Attribuut PERSOON_ADRESSEN.
     * BMR-attribuut 'Persoon' van objecttype 'Persoon \ Adres'.
     */
    PERSOON_ADRESSEN("adressen", ExpressieType.INDEXED, ExpressieType.PERSOON, new PersoonAdressenGetter()),
    /**
     * Attribuut PERSOONADRES_SOORT.
     * BMR-attribuut 'Soort' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_SOORT("soort", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresSoortGetter()),
    /**
     * Attribuut PERSOONADRES_REDEN_WIJZIGING.
     * BMR-attribuut 'Reden wijziging' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_REDEN_WIJZIGING("reden_wijziging", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresRedenWijzigingGetter()),
    /**
     * Attribuut PERSOONADRES_AANGEVER_ADRESHOUDING.
     * BMR-attribuut 'Aangever adreshouding' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_AANGEVER_ADRESHOUDING("aangever_adreshouding", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresAangeverAdreshoudingGetter()),
    /**
     * Attribuut PERSOONADRES_DATUM_AANVANG_ADRESHOUDING.
     * BMR-attribuut 'Datum aanvang adreshouding' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_DATUM_AANVANG_ADRESHOUDING("datum_aanvang_adreshouding", ExpressieType.DATE, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresDatumAanvangAdreshoudingGetter()),
    /**
     * Attribuut PERSOONADRES_ADRESSEERBAAR_OBJECT.
     * BMR-attribuut 'Adresseerbaar object' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_ADRESSEERBAAR_OBJECT("adresseerbaar_object", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresAdresseerbaarObjectGetter()),
    /**
     * Attribuut PERSOONADRES_IDENTIFICATIECODE_NUMMERAANDUIDING.
     * BMR-attribuut 'Identificatiecode nummeraanduiding' van objecttype 'Persoon
     * \ Adres'.
     */
    PERSOONADRES_IDENTIFICATIECODE_NUMMERAANDUIDING("identificatiecode_nummeraanduiding", ExpressieType.STRING,
            ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresIdentificatiecodeNummeraanduidingGetter()),
    /**
     * Attribuut PERSOONADRES_GEMEENTE.
     * BMR-attribuut 'Gemeente' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_GEMEENTE("gemeente", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresGemeenteGetter()),
    /**
     * Attribuut PERSOONADRES_NAAM_OPENBARE_RUIMTE.
     * BMR-attribuut 'Naam openbare ruimte' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_NAAM_OPENBARE_RUIMTE("naam_openbare_ruimte", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresNaamOpenbareRuimteGetter()),
    /**
     * Attribuut PERSOONADRES_AFGEKORTE_NAAM_OPENBARE_RUIMTE.
     * BMR-attribuut 'Afgekorte Naam Openbare Ruimte' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_AFGEKORTE_NAAM_OPENBARE_RUIMTE("afgekorte_naam_openbare_ruimte", ExpressieType.STRING,
            ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN, new PersoonadresAfgekorteNaamOpenbareRuimteGetter()),
    /**
     * Attribuut PERSOONADRES_GEMEENTEDEEL.
     * BMR-attribuut 'Gemeentedeel' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_GEMEENTEDEEL("gemeentedeel", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresGemeentedeelGetter()),
    /**
     * Attribuut PERSOONADRES_HUISNUMMER.
     * BMR-attribuut 'Huisnummer' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_HUISNUMMER("huisnummer", ExpressieType.NUMBER, ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresHuisnummerGetter()),
    /**
     * Attribuut PERSOONADRES_HUISLETTER.
     * BMR-attribuut 'Huisletter' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_HUISLETTER("huisletter", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresHuisletterGetter()),
    /**
     * Attribuut PERSOONADRES_HUISNUMMERTOEVOEGING.
     * BMR-attribuut 'Huisnummertoevoeging' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_HUISNUMMERTOEVOEGING("huisnummertoevoeging", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresHuisnummertoevoegingGetter()),
    /**
     * Attribuut PERSOONADRES_POSTCODE.
     * BMR-attribuut 'Postcode' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_POSTCODE("postcode", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresPostcodeGetter()),
    /**
     * Attribuut PERSOONADRES_WOONPLAATS.
     * BMR-attribuut 'Woonplaats' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_WOONPLAATS("woonplaats", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresWoonplaatsGetter()),
    /**
     * Attribuut PERSOONADRES_LOCATIE_TOV_ADRES.
     * BMR-attribuut 'Locatie t.o.v. adres' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_LOCATIE_TOV_ADRES("locatie_tov_adres", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresLocatieTovAdresGetter()),
    /**
     * Attribuut PERSOONADRES_LOCATIE_OMSCHRIJVING.
     * BMR-attribuut 'Locatie omschrijving' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_LOCATIE_OMSCHRIJVING("locatie_omschrijving", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresLocatieOmschrijvingGetter()),
    /**
     * Attribuut PERSOONADRES_DATUM_VERTREK_UIT_NEDERLAND.
     * BMR-attribuut 'Datum vertrek uit Nederland' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_DATUM_VERTREK_UIT_NEDERLAND("datum_vertrek_uit_nederland", ExpressieType.DATE, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresDatumVertrekUitNederlandGetter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_1.
     * BMR-attribuut 'Buitenlands adres regel 1' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_1("buitenlands_adres_regel_1", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresBuitenlandsAdresRegel1Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_2.
     * BMR-attribuut 'Buitenlands adres regel 2' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_2("buitenlands_adres_regel_2", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresBuitenlandsAdresRegel2Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_3.
     * BMR-attribuut 'Buitenlands adres regel 3' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_3("buitenlands_adres_regel_3", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresBuitenlandsAdresRegel3Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_4.
     * BMR-attribuut 'Buitenlands adres regel 4' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_4("buitenlands_adres_regel_4", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresBuitenlandsAdresRegel4Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_5.
     * BMR-attribuut 'Buitenlands adres regel 5' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_5("buitenlands_adres_regel_5", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresBuitenlandsAdresRegel5Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_6.
     * BMR-attribuut 'Buitenlands adres regel 6' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_6("buitenlands_adres_regel_6", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_ADRESSEN, new PersoonadresBuitenlandsAdresRegel6Getter()),
    /**
     * Attribuut PERSOONADRES_LAND.
     * BMR-attribuut 'Land' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_LAND("land", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN,
            new PersoonadresLandGetter()),
    /**
     * Attribuut PERSOONADRES_PERSOON_NIET_AANGETROFFEN_OP_ADRES.
     * BMR-attribuut 'Persoon niet aangetroffen op adres?' van objecttype 'Persoon
     * \ Adres'.
     */
    PERSOONADRES_PERSOON_NIET_AANGETROFFEN_OP_ADRES("persoon_niet_aangetroffen_op_adres", ExpressieType.BOOLEAN,
            ExpressieType.PERSOON, Attributes.PERSOON_ADRESSEN, new PersoonadresPersoonNietAangetroffenOpAdresGetter()),
    /**
     * Attribuut PERSOON_INDICATIES.
     * BMR-attribuut 'Persoon' van objecttype 'Persoon \ Indicatie'.
     */
    PERSOON_INDICATIES("indicaties", ExpressieType.INDEXED, ExpressieType.PERSOON, new PersoonIndicatiesGetter()),
    /**
     * Attribuut PERSOONINDICATIE_SOORT.
     * BMR-attribuut 'Soort' van objecttype 'Persoon \ Indicatie'.
     */
    PERSOONINDICATIE_SOORT("soort", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_INDICATIES,
            new PersoonindicatieSoortGetter()),
    /**
     * Attribuut PERSOONINDICATIE_WAARDE.
     * BMR-attribuut 'Waarde' van objecttype 'Persoon \ Indicatie'.
     */
    PERSOONINDICATIE_WAARDE("waarde", ExpressieType.BOOLEAN, ExpressieType.PERSOON, Attributes.PERSOON_INDICATIES,
            new PersoonindicatieWaardeGetter()),
    /**
     * Attribuut PERSOON_REISDOCUMENTEN.
     * BMR-attribuut 'Persoon' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOON_REISDOCUMENTEN("reisdocumenten", ExpressieType.INDEXED, ExpressieType.PERSOON,
            new PersoonReisdocumentenGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_SOORT.
     * BMR-attribuut 'Soort' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_SOORT("soort", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_REISDOCUMENTEN,
            new PersoonreisdocumentSoortGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_NUMMER.
     * BMR-attribuut 'Nummer' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_NUMMER("nummer", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_REISDOCUMENTEN, new PersoonreisdocumentNummerGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_LENGTE_HOUDER.
     * BMR-attribuut 'Lengte houder' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_LENGTE_HOUDER("lengte_houder", ExpressieType.NUMBER, ExpressieType.PERSOON,
            Attributes.PERSOON_REISDOCUMENTEN, new PersoonreisdocumentLengteHouderGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_AUTORITEIT_VAN_AFGIFTE.
     * BMR-attribuut 'Autoriteit van afgifte' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_AUTORITEIT_VAN_AFGIFTE("autoriteit_van_afgifte", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_REISDOCUMENTEN, new PersoonreisdocumentAutoriteitVanAfgifteGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_INGANG_DOCUMENT.
     * BMR-attribuut 'Datum ingang document' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_INGANG_DOCUMENT("datum_ingang_document", ExpressieType.DATE, ExpressieType.PERSOON,
            Attributes.PERSOON_REISDOCUMENTEN, new PersoonreisdocumentDatumIngangDocumentGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_UITGIFTE.
     * BMR-attribuut 'Datum uitgifte' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_UITGIFTE("datum_uitgifte", ExpressieType.DATE, ExpressieType.PERSOON,
            Attributes.PERSOON_REISDOCUMENTEN, new PersoonreisdocumentDatumUitgifteGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_VOORZIENE_EINDE_GELDIGHEID.
     * BMR-attribuut 'Datum voorziene einde geldigheid' van objecttype 'Persoon
     * \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_VOORZIENE_EINDE_GELDIGHEID("datum_voorziene_einde_geldigheid", ExpressieType.DATE,
            ExpressieType.PERSOON, Attributes.PERSOON_REISDOCUMENTEN,
            new PersoonreisdocumentDatumVoorzieneEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_INHOUDINGVERMISSING.
     * BMR-attribuut 'Datum inhouding/vermissing' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_INHOUDINGVERMISSING("datum_inhoudingvermissing", ExpressieType.DATE,
            ExpressieType.PERSOON, Attributes.PERSOON_REISDOCUMENTEN,
            new PersoonreisdocumentDatumInhoudingvermissingGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_REDEN_VERVALLEN.
     * BMR-attribuut 'Reden vervallen' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_REDEN_VERVALLEN("reden_vervallen", ExpressieType.STRING, ExpressieType.PERSOON,
            Attributes.PERSOON_REISDOCUMENTEN, new PersoonreisdocumentRedenVervallenGetter()),
    /**
     * Attribuut PERSOON_BETROKKENHEDEN.
     * BMR-attribuut 'Persoon' van objecttype 'Betrokkenheid'.
     */
    PERSOON_BETROKKENHEDEN("betrokkenheden", ExpressieType.INDEXED, ExpressieType.PERSOON,
            new PersoonBetrokkenhedenGetter()),
    /**
     * Attribuut BETROKKENHEID_ROL.
     * BMR-attribuut 'Rol' van objecttype 'Betrokkenheid'.
     */
    BETROKKENHEID_ROL("rol", ExpressieType.STRING, ExpressieType.PERSOON, Attributes.PERSOON_BETROKKENHEDEN,
            new BetrokkenheidRolGetter());

    private final String attributeSyntax;
    private final ExpressieType type;
    private final ExpressieType objectType;
    private final Attributes indexedAttribute;
    private final AttributeGetter getter;

    /**
     * Constructor.
     *
     * @param attributeSyntax  Naam (syntax) van het attribuut.
     * @param type             Type van het attribuut.
     * @param objectType       Type waartoe het attribuut behoort.
     * @param indexedAttribute Indexed attribute waartoe het attribute behoort.
     * @param getter           Getter voor het attribuut.
     */
    private Attributes(final String attributeSyntax, final ExpressieType type, final ExpressieType objectType,
                       final Attributes indexedAttribute, final AttributeGetter getter)
    {
        this.attributeSyntax = attributeSyntax.toUpperCase();
        this.type = type;
        this.objectType = objectType;
        this.indexedAttribute = indexedAttribute;
        this.getter = getter;
    }

    /**
     * Constructor.
     *
     * @param attributeSyntax  Naam (syntax) van het attribuut.
     * @param type             Type van het attribuut.
     * @param objectType       Type waartoe het attribuut behoort.
     * @param indexedAttribute Indexed attribute waartoe het attribute behoort.
     */
    private Attributes(final String attributeSyntax, final ExpressieType type, final ExpressieType objectType,
                       final Attributes indexedAttribute)
    {
        this(attributeSyntax, type, objectType, indexedAttribute, null);
    }

    /**
     * Constructor.
     *
     * @param attributeSyntax Naam (syntax) van het attribuut.
     * @param type            Type van het attribuut.
     * @param objectType      Type waartoe het attribuut behoort.
     * @param getter          Getter voor het attribuut.
     */
    private Attributes(final String attributeSyntax, final ExpressieType type, final ExpressieType objectType,
                       final AttributeGetter getter)
    {
        this(attributeSyntax, type, objectType, null, getter);
    }

    /**
     * Constructor.
     *
     * @param attributeSyntax Naam (syntax) van het attribuut.
     * @param type            Type van het attribuut.
     * @param objectType      Type waartoe het attribuut behoort.
     */
    private Attributes(final String attributeSyntax, final ExpressieType type, final ExpressieType objectType) {
        this(attributeSyntax, type, objectType, null, null);
    }

    public String getSyntax() {
        return attributeSyntax;
    }

    public ExpressieType getType() {
        return type;
    }

    public ExpressieType getObjectType() {
        return objectType;
    }

    public Attributes getIndexedAttribute() {
        return indexedAttribute;
    }

    /**
     * Geeft de waarde van het attribuut voor het betreffende (root)object terug.
     *
     * @param rootObject Object waarvan de attribuutwaarde bepaald moet worden.
     * @return Waarde van het attribuut of NULL indien niet gevonden.
     */
    public Expressie getAttribuutWaarde(final RootObject rootObject) {
        if (getter != null) {
            return getter.getAttribuutWaarde(rootObject);
        } else {
            return null;
        }
    }

    /**
     * Geeft de waarde van het attribuut voor het betreffende geïndiceerde (root)object terug.
     *
     * @param rootObject Object waarvan de attribuutwaarde bepaald moet worden.
     * @param index      Index van het object waarvoor de waarde bepaald moet worden.
     * @return Waarde van het attribuut of NULL indien niet gevonden.
     */
    public Expressie getAttribuutWaarde(final RootObject rootObject, final int index) {
        if (getter != null) {
            return getter.getAttribuutWaarde(rootObject, index);
        } else {
            return null;
        }
    }

    /**
     * Geeft de maximale indexwaarde voor een geïndiceerd attribuut.
     *
     * @param rootObject Object waarvan de maximale indexwaarde bepaald worden.
     * @return Maximale indexwaarde of 0 indien een fout is opgetreden.
     */
    public int getMaxIndex(final RootObject rootObject) {
        if (getter != null) {
            return getter.getMaxIndex(rootObject);
        } else {
            return 0;
        }
    }

    /**
     * Geeft TRUE als het attribuut tot het geïndiceerd attribuut behoort (bijvoorbeeld Postcode als onderdeel van
     * een Adres[]).
     *
     * @param attribuut Het te testen attribuut.
     * @return TRUE als het attribuut tot het geïndiceerd attribuut behoort.
     */
    public boolean belongsToIndexedAttribute(final Attributes attribuut) {
        return (attribuut.getType() == ExpressieType.INDEXED && getIndexedAttribute() == attribuut);
    }

    @Override
    public String toString() {
        return getObjectType().toString() + "." + getSyntax() + " (" + getType().toString() + ")";
    }
}
