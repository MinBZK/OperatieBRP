/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

/**
 * Mapping van id's van gegevenselementen uit BMR (sync-id) op BRP-expressies.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public final class ExpressieMap {

    /**
     * Versienummer van de expressietaal.
     */
    public static final String VERSIE_EXPRESSIETAAL = "1.7.5-SNAPSHOT";

    /**
     * Versienummer van de generator.
     */
    public static final String VERSIE_GENERATOR = "3.36.0-SNAPSHOT";

    /**
     * Private constructor voor utility class.
     *
     */
    private ExpressieMap() {
    }

    /**
     * Geeft de mapping van id's van gegevenselementen uit BMR op BRP-expressies.
     *
     * @return Mapping van id's van gegevenselementen op BRP-expressies
     */
    public static Map<Integer, String> getMap() {
        final Map<Integer, String> expressieMap = new HashMap<Integer, String>();
        final int idPersoonPersoonIdentiteitSoort = 1997;
        expressieMap.put(idPersoonPersoonIdentiteitSoort, "$soort");
        final int idPersoonPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging = 3251;
        expressieMap.put(idPersoonPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging, "$administratief.tijdstip_laatste_wijziging");
        final int idPersoonPersoonAfgeleidAdministratiefSorteervolgorde = 10404;
        expressieMap.put(idPersoonPersoonAfgeleidAdministratiefSorteervolgorde, "$administratief.sorteervolgorde");
        final int idPersoonPersoonAfgeleidAdministratiefIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = 10899;
        expressieMap.put(
            idPersoonPersoonAfgeleidAdministratiefIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
            "$administratief.onverwerkt_bijhoudingsvoorstel_nietingezetene_aanwezig");
        final int idPersoonPersoonAfgeleidAdministratiefTijdstipLaatsteWijzigingGBASystematiek = 10901;
        expressieMap.put(
            idPersoonPersoonAfgeleidAdministratiefTijdstipLaatsteWijzigingGBASystematiek,
            "$administratief.tijdstip_laatste_wijziging_gbasystematiek");
        final int idPersoonPersoonIdentificatienummersBurgerservicenummer = 3018;
        expressieMap.put(idPersoonPersoonIdentificatienummersBurgerservicenummer, "$identificatienummers.burgerservicenummer");
        final int idPersoonPersoonIdentificatienummersAdministratienummer = 3013;
        expressieMap.put(idPersoonPersoonIdentificatienummersAdministratienummer, "$identificatienummers.administratienummer");
        final int idPersoonPersoonSamengesteldeNaamIndicatieAfgeleid = 3914;
        expressieMap.put(idPersoonPersoonSamengesteldeNaamIndicatieAfgeleid, "$samengestelde_naam.afgeleid");
        final int idPersoonPersoonSamengesteldeNaamIndicatieNamenreeks = 3592;
        expressieMap.put(idPersoonPersoonSamengesteldeNaamIndicatieNamenreeks, "$samengestelde_naam.namenreeks");
        final int idPersoonPersoonSamengesteldeNaamPredicaat = 1969;
        expressieMap.put(idPersoonPersoonSamengesteldeNaamPredicaat, "$samengestelde_naam.predicaat");
        final int idPersoonPersoonSamengesteldeNaamVoornamen = 3092;
        expressieMap.put(idPersoonPersoonSamengesteldeNaamVoornamen, "$samengestelde_naam.voornamen");
        final int idPersoonPersoonSamengesteldeNaamAdellijkeTitel = 1968;
        expressieMap.put(idPersoonPersoonSamengesteldeNaamAdellijkeTitel, "$samengestelde_naam.adellijke_titel");
        final int idPersoonPersoonSamengesteldeNaamVoorvoegsel = 3309;
        expressieMap.put(idPersoonPersoonSamengesteldeNaamVoorvoegsel, "$samengestelde_naam.voorvoegsel");
        final int idPersoonPersoonSamengesteldeNaamScheidingsteken = 3253;
        expressieMap.put(idPersoonPersoonSamengesteldeNaamScheidingsteken, "$samengestelde_naam.scheidingsteken");
        final int idPersoonPersoonSamengesteldeNaamGeslachtsnaamstam = 3094;
        expressieMap.put(idPersoonPersoonSamengesteldeNaamGeslachtsnaamstam, "$samengestelde_naam.geslachtsnaamstam");
        final int idPersoonPersoonGeboorteDatumGeboorte = 3673;
        expressieMap.put(idPersoonPersoonGeboorteDatumGeboorte, "$geboorte.datum");
        final int idPersoonPersoonGeboorteGemeenteGeboorte = 3675;
        expressieMap.put(idPersoonPersoonGeboorteGemeenteGeboorte, "$geboorte.gemeente");
        final int idPersoonPersoonGeboorteWoonplaatsnaamGeboorte = 3676;
        expressieMap.put(idPersoonPersoonGeboorteWoonplaatsnaamGeboorte, "$geboorte.woonplaatsnaam");
        final int idPersoonPersoonGeboorteBuitenlandsePlaatsGeboorte = 3677;
        expressieMap.put(idPersoonPersoonGeboorteBuitenlandsePlaatsGeboorte, "$geboorte.buitenlandse_plaats");
        final int idPersoonPersoonGeboorteBuitenlandseRegioGeboorte = 3530;
        expressieMap.put(idPersoonPersoonGeboorteBuitenlandseRegioGeboorte, "$geboorte.buitenlandse_regio");
        final int idPersoonPersoonGeboorteOmschrijvingLocatieGeboorte = 3678;
        expressieMap.put(idPersoonPersoonGeboorteOmschrijvingLocatieGeboorte, "$geboorte.omschrijving_locatie");
        final int idPersoonPersoonGeboorteLandGebiedGeboorte = 3543;
        expressieMap.put(idPersoonPersoonGeboorteLandGebiedGeboorte, "$geboorte.land_gebied");
        final int idPersoonPersoonGeslachtsaanduidingGeslachtsaanduiding = 3031;
        expressieMap.put(idPersoonPersoonGeslachtsaanduidingGeslachtsaanduiding, "$geslachtsaanduiding.geslachtsaanduiding");
        final int idPersoonPersoonInschrijvingDatumInschrijving = 3570;
        expressieMap.put(idPersoonPersoonInschrijvingDatumInschrijving, "$inschrijving.datum");
        final int idPersoonPersoonInschrijvingVersienummer = 3250;
        expressieMap.put(idPersoonPersoonInschrijvingVersienummer, "$inschrijving.versienummer");
        final int idPersoonPersoonInschrijvingDatumtijdstempel = 11186;
        expressieMap.put(idPersoonPersoonInschrijvingDatumtijdstempel, "$inschrijving.datumtijdstempel");
        final int idPersoonPersoonNummerverwijzingVorigeBurgerservicenummer = 3134;
        expressieMap.put(idPersoonPersoonNummerverwijzingVorigeBurgerservicenummer, "$nummerverwijzing.vorige_burgerservicenummer");
        final int idPersoonPersoonNummerverwijzingVolgendeBurgerservicenummer = 3136;
        expressieMap.put(idPersoonPersoonNummerverwijzingVolgendeBurgerservicenummer, "$nummerverwijzing.volgende_burgerservicenummer");
        final int idPersoonPersoonNummerverwijzingVorigeAdministratienummer = 3247;
        expressieMap.put(idPersoonPersoonNummerverwijzingVorigeAdministratienummer, "$nummerverwijzing.vorige_administratienummer");
        final int idPersoonPersoonNummerverwijzingVolgendeAdministratienummer = 3248;
        expressieMap.put(idPersoonPersoonNummerverwijzingVolgendeAdministratienummer, "$nummerverwijzing.volgende_administratienummer");
        final int idPersoonPersoonBijhoudingBijhoudingspartij = 3573;
        expressieMap.put(idPersoonPersoonBijhoudingBijhoudingspartij, "$bijhouding.bijhoudingspartij");
        final int idPersoonPersoonBijhoudingBijhoudingsaard = 3568;
        expressieMap.put(idPersoonPersoonBijhoudingBijhoudingsaard, "$bijhouding.bijhoudingsaard");
        final int idPersoonPersoonBijhoudingNadereBijhoudingsaard = 10864;
        expressieMap.put(idPersoonPersoonBijhoudingNadereBijhoudingsaard, "$bijhouding.nadere_bijhoudingsaard");
        final int idPersoonPersoonBijhoudingIndicatieOnverwerktDocumentAanwezig = 3578;
        expressieMap.put(idPersoonPersoonBijhoudingIndicatieOnverwerktDocumentAanwezig, "$bijhouding.onverwerkt_document_aanwezig");
        final int idPersoonPersoonOverlijdenDatumOverlijden = 3546;
        expressieMap.put(idPersoonPersoonOverlijdenDatumOverlijden, "$overlijden.datum");
        final int idPersoonPersoonOverlijdenGemeenteOverlijden = 3551;
        expressieMap.put(idPersoonPersoonOverlijdenGemeenteOverlijden, "$overlijden.gemeente");
        final int idPersoonPersoonOverlijdenWoonplaatsnaamOverlijden = 3544;
        expressieMap.put(idPersoonPersoonOverlijdenWoonplaatsnaamOverlijden, "$overlijden.woonplaatsnaam");
        final int idPersoonPersoonOverlijdenBuitenlandsePlaatsOverlijden = 3552;
        expressieMap.put(idPersoonPersoonOverlijdenBuitenlandsePlaatsOverlijden, "$overlijden.buitenlandse_plaats");
        final int idPersoonPersoonOverlijdenBuitenlandseRegioOverlijden = 3556;
        expressieMap.put(idPersoonPersoonOverlijdenBuitenlandseRegioOverlijden, "$overlijden.buitenlandse_regio");
        final int idPersoonPersoonOverlijdenOmschrijvingLocatieOverlijden = 3555;
        expressieMap.put(idPersoonPersoonOverlijdenOmschrijvingLocatieOverlijden, "$overlijden.omschrijving_locatie");
        final int idPersoonPersoonOverlijdenLandGebiedOverlijden = 3558;
        expressieMap.put(idPersoonPersoonOverlijdenLandGebiedOverlijden, "$overlijden.land_gebied");
        final int idPersoonPersoonNaamgebruikNaamgebruik = 3593;
        expressieMap.put(idPersoonPersoonNaamgebruikNaamgebruik, "$naamgebruik.naamgebruik");
        final int idPersoonPersoonNaamgebruikIndicatieNaamgebruikAfgeleid = 3633;
        expressieMap.put(idPersoonPersoonNaamgebruikIndicatieNaamgebruikAfgeleid, "$naamgebruik.afgeleid");
        final int idPersoonPersoonNaamgebruikPredicaatNaamgebruik = 3703;
        expressieMap.put(idPersoonPersoonNaamgebruikPredicaatNaamgebruik, "$naamgebruik.predicaat");
        final int idPersoonPersoonNaamgebruikVoornamenNaamgebruik = 3319;
        expressieMap.put(idPersoonPersoonNaamgebruikVoornamenNaamgebruik, "$naamgebruik.voornamen");
        final int idPersoonPersoonNaamgebruikAdellijkeTitelNaamgebruik = 6113;
        expressieMap.put(idPersoonPersoonNaamgebruikAdellijkeTitelNaamgebruik, "$naamgebruik.adellijke_titel");
        final int idPersoonPersoonNaamgebruikVoorvoegselNaamgebruik = 3355;
        expressieMap.put(idPersoonPersoonNaamgebruikVoorvoegselNaamgebruik, "$naamgebruik.voorvoegsel");
        final int idPersoonPersoonNaamgebruikScheidingstekenNaamgebruik = 3580;
        expressieMap.put(idPersoonPersoonNaamgebruikScheidingstekenNaamgebruik, "$naamgebruik.scheidingsteken");
        final int idPersoonPersoonNaamgebruikGeslachtsnaamstamNaamgebruik = 3323;
        expressieMap.put(idPersoonPersoonNaamgebruikGeslachtsnaamstamNaamgebruik, "$naamgebruik.geslachtsnaamstam");
        final int idPersoonPersoonMigratieSoortMigratie = 10881;
        expressieMap.put(idPersoonPersoonMigratieSoortMigratie, "$migratie.soort");
        final int idPersoonPersoonMigratieRedenWijzigingMigratie = 11277;
        expressieMap.put(idPersoonPersoonMigratieRedenWijzigingMigratie, "$migratie.reden_wijziging");
        final int idPersoonPersoonMigratieAangeverMigratie = 11278;
        expressieMap.put(idPersoonPersoonMigratieAangeverMigratie, "$migratie.aangever");
        final int idPersoonPersoonMigratieLandGebiedMigratie = 3579;
        expressieMap.put(idPersoonPersoonMigratieLandGebiedMigratie, "$migratie.land_gebied");
        final int idPersoonPersoonMigratieBuitenlandsAdresRegel1Migratie = 10882;
        expressieMap.put(idPersoonPersoonMigratieBuitenlandsAdresRegel1Migratie, "$migratie.buitenlands_adres_regel_1");
        final int idPersoonPersoonMigratieBuitenlandsAdresRegel2Migratie = 10883;
        expressieMap.put(idPersoonPersoonMigratieBuitenlandsAdresRegel2Migratie, "$migratie.buitenlands_adres_regel_2");
        final int idPersoonPersoonMigratieBuitenlandsAdresRegel3Migratie = 10884;
        expressieMap.put(idPersoonPersoonMigratieBuitenlandsAdresRegel3Migratie, "$migratie.buitenlands_adres_regel_3");
        final int idPersoonPersoonMigratieBuitenlandsAdresRegel4Migratie = 10885;
        expressieMap.put(idPersoonPersoonMigratieBuitenlandsAdresRegel4Migratie, "$migratie.buitenlands_adres_regel_4");
        final int idPersoonPersoonMigratieBuitenlandsAdresRegel5Migratie = 10886;
        expressieMap.put(idPersoonPersoonMigratieBuitenlandsAdresRegel5Migratie, "$migratie.buitenlands_adres_regel_5");
        final int idPersoonPersoonMigratieBuitenlandsAdresRegel6Migratie = 10887;
        expressieMap.put(idPersoonPersoonMigratieBuitenlandsAdresRegel6Migratie, "$migratie.buitenlands_adres_regel_6");
        final int idPersoonPersoonVerblijfsrechtAanduidingVerblijfsrecht = 3310;
        expressieMap.put(idPersoonPersoonVerblijfsrechtAanduidingVerblijfsrecht, "$verblijfsrecht.aanduiding");
        final int idPersoonPersoonVerblijfsrechtDatumAanvangVerblijfsrecht = 21315;
        expressieMap.put(idPersoonPersoonVerblijfsrechtDatumAanvangVerblijfsrecht, "$verblijfsrecht.datum_aanvang");
        final int idPersoonPersoonVerblijfsrechtDatumMededelingVerblijfsrecht = 3325;
        expressieMap.put(idPersoonPersoonVerblijfsrechtDatumMededelingVerblijfsrecht, "$verblijfsrecht.datum_mededeling");
        final int idPersoonPersoonVerblijfsrechtDatumVoorzienEindeVerblijfsrecht = 3481;
        expressieMap.put(idPersoonPersoonVerblijfsrechtDatumVoorzienEindeVerblijfsrecht, "$verblijfsrecht.datum_voorzien_einde");
        final int idPersoonPersoonUitsluitingKiesrechtIndicatieUitsluitingKiesrecht = 3322;
        expressieMap.put(idPersoonPersoonUitsluitingKiesrechtIndicatieUitsluitingKiesrecht, "$uitsluiting_kiesrecht.uitsluiting_kiesrecht");
        final int idPersoonPersoonUitsluitingKiesrechtDatumVoorzienEindeUitsluitingKiesrecht = 3559;
        expressieMap.put(
            idPersoonPersoonUitsluitingKiesrechtDatumVoorzienEindeUitsluitingKiesrecht,
            "$uitsluiting_kiesrecht.datum_voorzien_einde_uitsluiting_kiesrecht");
        final int idPersoonPersoonDeelnameEUVerkiezingenIndicatieDeelnameEUVerkiezingen = 3320;
        expressieMap.put(idPersoonPersoonDeelnameEUVerkiezingenIndicatieDeelnameEUVerkiezingen, "$deelname_eu_verkiezingen.deelname_eu_verkiezingen");
        final int idPersoonPersoonDeelnameEUVerkiezingenDatumAanleidingAanpassingDeelnameEUVerkiezingen = 3562;
        expressieMap.put(
            idPersoonPersoonDeelnameEUVerkiezingenDatumAanleidingAanpassingDeelnameEUVerkiezingen,
            "$deelname_eu_verkiezingen.datum_aanleiding_aanpassing_deelname_eu_verkiezingen");
        final int idPersoonPersoonDeelnameEUVerkiezingenDatumVoorzienEindeUitsluitingEUVerkiezingen = 3564;
        expressieMap.put(
            idPersoonPersoonDeelnameEUVerkiezingenDatumVoorzienEindeUitsluitingEUVerkiezingen,
            "$deelname_eu_verkiezingen.datum_voorzien_einde_uitsluiting_eu_verkiezingen");
        final int idPersoonPersoonPersoonskaartGemeentePersoonskaart = 3233;
        expressieMap.put(idPersoonPersoonPersoonskaartGemeentePersoonskaart, "$persoonskaart.gemeente");
        final int idPersoonPersoonPersoonskaartIndicatiePersoonskaartVolledigGeconverteerd = 3313;
        expressieMap.put(idPersoonPersoonPersoonskaartIndicatiePersoonskaartVolledigGeconverteerd, "$persoonskaart.volledig_geconverteerd");
        final int idPersoonVoornaamPersoonIdentiteitVolgnummer = 3028;
        expressieMap.put(idPersoonVoornaamPersoonIdentiteitVolgnummer, "RMAP(voornamen, v, $v.volgnummer)");
        final int idPersoonVoornaamPersoonStandaardNaam = 3026;
        expressieMap.put(idPersoonVoornaamPersoonStandaardNaam, "RMAP(voornamen, v, $v.naam)");
        final int idPersoonGeslachtsnaamcomponentPersoonIdentiteitVolgnummer = 3029;
        expressieMap.put(idPersoonGeslachtsnaamcomponentPersoonIdentiteitVolgnummer, "RMAP(geslachtsnaamcomponenten, v, $v.volgnummer)");
        final int idPersoonGeslachtsnaamcomponentPersoonStandaardPredicaat = 3117;
        expressieMap.put(idPersoonGeslachtsnaamcomponentPersoonStandaardPredicaat, "RMAP(geslachtsnaamcomponenten, v, $v.predicaat)");
        final int idPersoonGeslachtsnaamcomponentPersoonStandaardAdellijkeTitel = 3118;
        expressieMap.put(idPersoonGeslachtsnaamcomponentPersoonStandaardAdellijkeTitel, "RMAP(geslachtsnaamcomponenten, v, $v.adellijke_titel)");
        final int idPersoonGeslachtsnaamcomponentPersoonStandaardVoorvoegsel = 3030;
        expressieMap.put(idPersoonGeslachtsnaamcomponentPersoonStandaardVoorvoegsel, "RMAP(geslachtsnaamcomponenten, v, $v.voorvoegsel)");
        final int idPersoonGeslachtsnaamcomponentPersoonStandaardScheidingsteken = 3069;
        expressieMap.put(idPersoonGeslachtsnaamcomponentPersoonStandaardScheidingsteken, "RMAP(geslachtsnaamcomponenten, v, $v.scheidingsteken)");
        final int idPersoonGeslachtsnaamcomponentPersoonStandaardStam = 3025;
        expressieMap.put(idPersoonGeslachtsnaamcomponentPersoonStandaardStam, "RMAP(geslachtsnaamcomponenten, v, $v.stam)");
        final int idPersoonVerificatiePersoonIdentiteitPartij = 10915;
        expressieMap.put(idPersoonVerificatiePersoonIdentiteitPartij, "RMAP(verificaties, v, $v.partij)");
        final int idPersoonVerificatiePersoonIdentiteitSoort = 3779;
        expressieMap.put(idPersoonVerificatiePersoonIdentiteitSoort, "RMAP(verificaties, v, $v.soort)");
        final int idPersoonVerificatiePersoonStandaardDatum = 3778;
        expressieMap.put(idPersoonVerificatiePersoonStandaardDatum, "RMAP(verificaties, v, $v.datum)");
        final int idPersoonNationaliteitPersoonIdentiteitNationaliteit = 3131;
        expressieMap.put(idPersoonNationaliteitPersoonIdentiteitNationaliteit, "RMAP(nationaliteiten, v, $v.nationaliteit)");
        final int idPersoonNationaliteitPersoonStandaardRedenVerkrijging = 3229;
        expressieMap.put(idPersoonNationaliteitPersoonStandaardRedenVerkrijging, "RMAP(nationaliteiten, v, $v.reden_verkrijging)");
        final int idPersoonNationaliteitPersoonStandaardRedenVerlies = 3230;
        expressieMap.put(idPersoonNationaliteitPersoonStandaardRedenVerlies, "RMAP(nationaliteiten, v, $v.reden_verlies)");
        final int idPersoonNationaliteitPersoonStandaardIndicatieBijhoudingBeeindigd = 21230;
        expressieMap.put(idPersoonNationaliteitPersoonStandaardIndicatieBijhoudingBeeindigd, "RMAP(nationaliteiten, v, $v.bijhouding_beeindigd)");
        final int idPersoonNationaliteitPersoonStandaardMigratieRedenOpnameNationaliteit = 21231;
        expressieMap.put(
            idPersoonNationaliteitPersoonStandaardMigratieRedenOpnameNationaliteit,
            "RMAP(nationaliteiten, v, $v.migratie_reden_opname_nationaliteit)");
        final int idPersoonNationaliteitPersoonStandaardMigratieRedenBeeindigenNationaliteit = 21232;
        expressieMap.put(
            idPersoonNationaliteitPersoonStandaardMigratieRedenBeeindigenNationaliteit,
            "RMAP(nationaliteiten, v, $v.migratie_reden_beeindigen_nationaliteit)");
        final int idPersoonNationaliteitPersoonStandaardMigratieDatumEindeBijhouding = 21233;
        expressieMap.put(
            idPersoonNationaliteitPersoonStandaardMigratieDatumEindeBijhouding,
            "RMAP(nationaliteiten, v, $v.migratie_datum_einde_bijhouding)");
        final int idPersoonAdresPersoonStandaardSoort = 3263;
        expressieMap.put(idPersoonAdresPersoonStandaardSoort, "RMAP(adressen, v, $v.soort)");
        final int idPersoonAdresPersoonStandaardRedenWijziging = 3715;
        expressieMap.put(idPersoonAdresPersoonStandaardRedenWijziging, "RMAP(adressen, v, $v.reden_wijziging)");
        final int idPersoonAdresPersoonStandaardAangeverAdreshouding = 3301;
        expressieMap.put(idPersoonAdresPersoonStandaardAangeverAdreshouding, "RMAP(adressen, v, $v.aangever_adreshouding)");
        final int idPersoonAdresPersoonStandaardDatumAanvangAdreshouding = 3730;
        expressieMap.put(idPersoonAdresPersoonStandaardDatumAanvangAdreshouding, "RMAP(adressen, v, $v.datum_aanvang_adreshouding)");
        final int idPersoonAdresPersoonStandaardIdentificatiecodeAdresseerbaarObject = 3284;
        expressieMap.put(
            idPersoonAdresPersoonStandaardIdentificatiecodeAdresseerbaarObject,
            "RMAP(adressen, v, $v.identificatiecode_adresseerbaar_object)");
        final int idPersoonAdresPersoonStandaardIdentificatiecodeNummeraanduiding = 3286;
        expressieMap.put(idPersoonAdresPersoonStandaardIdentificatiecodeNummeraanduiding, "RMAP(adressen, v, $v.identificatiecode_nummeraanduiding)");
        final int idPersoonAdresPersoonStandaardGemeente = 3788;
        expressieMap.put(idPersoonAdresPersoonStandaardGemeente, "RMAP(adressen, v, $v.gemeente)");
        final int idPersoonAdresPersoonStandaardNaamOpenbareRuimte = 3269;
        expressieMap.put(idPersoonAdresPersoonStandaardNaamOpenbareRuimte, "RMAP(adressen, v, $v.naam_openbare_ruimte)");
        final int idPersoonAdresPersoonStandaardAfgekorteNaamOpenbareRuimte = 3267;
        expressieMap.put(idPersoonAdresPersoonStandaardAfgekorteNaamOpenbareRuimte, "RMAP(adressen, v, $v.afgekorte_naam_openbare_ruimte)");
        final int idPersoonAdresPersoonStandaardGemeentedeel = 3265;
        expressieMap.put(idPersoonAdresPersoonStandaardGemeentedeel, "RMAP(adressen, v, $v.gemeentedeel)");
        final int idPersoonAdresPersoonStandaardHuisnummer = 3271;
        expressieMap.put(idPersoonAdresPersoonStandaardHuisnummer, "RMAP(adressen, v, $v.huisnummer)");
        final int idPersoonAdresPersoonStandaardHuisletter = 3273;
        expressieMap.put(idPersoonAdresPersoonStandaardHuisletter, "RMAP(adressen, v, $v.huisletter)");
        final int idPersoonAdresPersoonStandaardHuisnummertoevoeging = 3275;
        expressieMap.put(idPersoonAdresPersoonStandaardHuisnummertoevoeging, "RMAP(adressen, v, $v.huisnummertoevoeging)");
        final int idPersoonAdresPersoonStandaardPostcode = 3281;
        expressieMap.put(idPersoonAdresPersoonStandaardPostcode, "RMAP(adressen, v, $v.postcode)");
        final int idPersoonAdresPersoonStandaardWoonplaatsnaam = 3282;
        expressieMap.put(idPersoonAdresPersoonStandaardWoonplaatsnaam, "RMAP(adressen, v, $v.woonplaatsnaam)");
        final int idPersoonAdresPersoonStandaardLocatieTenOpzichteVanAdres = 3278;
        expressieMap.put(idPersoonAdresPersoonStandaardLocatieTenOpzichteVanAdres, "RMAP(adressen, v, $v.locatie_ten_opzichte_van_adres)");
        final int idPersoonAdresPersoonStandaardLocatieomschrijving = 3288;
        expressieMap.put(idPersoonAdresPersoonStandaardLocatieomschrijving, "RMAP(adressen, v, $v.locatieomschrijving)");
        final int idPersoonAdresPersoonStandaardBuitenlandsAdresRegel1 = 3291;
        expressieMap.put(idPersoonAdresPersoonStandaardBuitenlandsAdresRegel1, "RMAP(adressen, v, $v.buitenlands_adres_regel_1)");
        final int idPersoonAdresPersoonStandaardBuitenlandsAdresRegel2 = 3292;
        expressieMap.put(idPersoonAdresPersoonStandaardBuitenlandsAdresRegel2, "RMAP(adressen, v, $v.buitenlands_adres_regel_2)");
        final int idPersoonAdresPersoonStandaardBuitenlandsAdresRegel3 = 3293;
        expressieMap.put(idPersoonAdresPersoonStandaardBuitenlandsAdresRegel3, "RMAP(adressen, v, $v.buitenlands_adres_regel_3)");
        final int idPersoonAdresPersoonStandaardBuitenlandsAdresRegel4 = 3709;
        expressieMap.put(idPersoonAdresPersoonStandaardBuitenlandsAdresRegel4, "RMAP(adressen, v, $v.buitenlands_adres_regel_4)");
        final int idPersoonAdresPersoonStandaardBuitenlandsAdresRegel5 = 3710;
        expressieMap.put(idPersoonAdresPersoonStandaardBuitenlandsAdresRegel5, "RMAP(adressen, v, $v.buitenlands_adres_regel_5)");
        final int idPersoonAdresPersoonStandaardBuitenlandsAdresRegel6 = 3711;
        expressieMap.put(idPersoonAdresPersoonStandaardBuitenlandsAdresRegel6, "RMAP(adressen, v, $v.buitenlands_adres_regel_6)");
        final int idPersoonAdresPersoonStandaardLandGebied = 3289;
        expressieMap.put(idPersoonAdresPersoonStandaardLandGebied, "RMAP(adressen, v, $v.land_gebied)");
        final int idPersoonAdresPersoonStandaardIndicatiePersoonAangetroffenOpAdres = 9540;
        expressieMap.put(idPersoonAdresPersoonStandaardIndicatiePersoonAangetroffenOpAdres, "RMAP(adressen, v, $v.persoon_aangetroffen_op_adres)");
        final int idPersoonIndicatiePersoonIdentiteitSoort = 3658;
        expressieMap.put(idPersoonIndicatiePersoonIdentiteitSoort, "RMAP(indicaties, v, $v.soort)");
        final int idPersoonIndicatiePersoonStandaardWaarde = 3659;
        expressieMap.put(idPersoonIndicatiePersoonStandaardWaarde, "RMAP(indicaties, v, $v.waarde)");
        final int idPersoonIndicatiePersoonStandaardMigratieRedenOpnameNationaliteit = 21234;
        expressieMap.put(idPersoonIndicatiePersoonStandaardMigratieRedenOpnameNationaliteit, "RMAP(indicaties, v, $v.migratie_reden_opname_nationaliteit)");
        final int idPersoonIndicatiePersoonStandaardMigratieRedenBeeindigenNationaliteit = 21235;
        expressieMap.put(
            idPersoonIndicatiePersoonStandaardMigratieRedenBeeindigenNationaliteit,
            "RMAP(indicaties, v, $v.migratie_reden_beeindigen_nationaliteit)");
        final int idindicatiederdeheeftgezag = -1;
        expressieMap.put(idindicatiederdeheeftgezag, "$indicatie.derde_heeft_gezag");
        final int idindicatieondercuratele = -2;
        expressieMap.put(idindicatieondercuratele, "$indicatie.onder_curatele");
        final int idindicatievolledigeverstrekkingsbeperking = -3;
        expressieMap.put(idindicatievolledigeverstrekkingsbeperking, "$indicatie.volledige_verstrekkingsbeperking");
        final int idindicatievastgesteldnietnederlander = -4;
        expressieMap.put(idindicatievastgesteldnietnederlander, "$indicatie.vastgesteld_niet_nederlander");
        final int idindicatiebehandeldalsnederlander = -5;
        expressieMap.put(idindicatiebehandeldalsnederlander, "$indicatie.behandeld_als_nederlander");
        final int idindicatiesignaleringmetbetrekkingtotverstrekkenreisdocument = -6;
        expressieMap.put(
            idindicatiesignaleringmetbetrekkingtotverstrekkenreisdocument,
            "$indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument");
        final int idindicatiestaatloos = -7;
        expressieMap.put(idindicatiestaatloos, "$indicatie.staatloos");
        final int idindicatiebijzondereverblijfsrechtelijkepositie = -8;
        expressieMap.put(idindicatiebijzondereverblijfsrechtelijkepositie, "$indicatie.bijzondere_verblijfsrechtelijke_positie");
        final int idPersoonReisdocumentPersoonIdentiteitSoort = 3739;
        expressieMap.put(idPersoonReisdocumentPersoonIdentiteitSoort, "RMAP(reisdocumenten, v, $v.soort)");
        final int idPersoonReisdocumentPersoonStandaardNummer = 3741;
        expressieMap.put(idPersoonReisdocumentPersoonStandaardNummer, "RMAP(reisdocumenten, v, $v.nummer)");
        final int idPersoonReisdocumentPersoonStandaardAutoriteitVanAfgifte = 3744;
        expressieMap.put(idPersoonReisdocumentPersoonStandaardAutoriteitVanAfgifte, "RMAP(reisdocumenten, v, $v.autoriteit_van_afgifte)");
        final int idPersoonReisdocumentPersoonStandaardDatumIngangDocument = 6126;
        expressieMap.put(idPersoonReisdocumentPersoonStandaardDatumIngangDocument, "RMAP(reisdocumenten, v, $v.datum_ingang_document)");
        final int idPersoonReisdocumentPersoonStandaardDatumEindeDocument = 3745;
        expressieMap.put(idPersoonReisdocumentPersoonStandaardDatumEindeDocument, "RMAP(reisdocumenten, v, $v.datum_einde_document)");
        final int idPersoonReisdocumentPersoonStandaardDatumUitgifte = 3742;
        expressieMap.put(idPersoonReisdocumentPersoonStandaardDatumUitgifte, "RMAP(reisdocumenten, v, $v.datum_uitgifte)");
        final int idPersoonReisdocumentPersoonStandaardDatumInhoudingVermissing = 3746;
        expressieMap.put(idPersoonReisdocumentPersoonStandaardDatumInhoudingVermissing, "RMAP(reisdocumenten, v, $v.datum_inhouding_vermissing)");
        final int idPersoonReisdocumentPersoonStandaardAanduidingInhoudingVermissing = 3747;
        expressieMap.put(idPersoonReisdocumentPersoonStandaardAanduidingInhoudingVermissing, "RMAP(reisdocumenten, v, $v.aanduiding_inhouding_vermissing)");
        final int idBetrokkenheidPersoonIdentiteitRol = 3861;
        expressieMap.put(idBetrokkenheidPersoonIdentiteitRol, "RMAP(betrokkenheden, v, $v.rol)");
        final int idPersoonVerstrekkingsbeperkingPersoonIdentiteitPartij = 9352;
        expressieMap.put(idPersoonVerstrekkingsbeperkingPersoonIdentiteitPartij, "RMAP(verstrekkingsbeperkingen, v, $v.partij)");
        final int idPersoonVerstrekkingsbeperkingPersoonIdentiteitOmschrijvingDerde = 10912;
        expressieMap.put(idPersoonVerstrekkingsbeperkingPersoonIdentiteitOmschrijvingDerde, "RMAP(verstrekkingsbeperkingen, v, $v.omschrijving_derde)");
        final int idPersoonVerstrekkingsbeperkingPersoonIdentiteitGemeenteVerordening = 10913;
        expressieMap.put(idPersoonVerstrekkingsbeperkingPersoonIdentiteitGemeenteVerordening, "RMAP(verstrekkingsbeperkingen, v, $v.gemeente_verordening)");
        final int idOuderOuderOuderschapIndicatieOuder = 6088;
        expressieMap.put(idOuderOuderOuderschapIndicatieOuder, "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderschap.ouder))");
        final int idOuderOuderOuderschapIndicatieOuderUitWieKindIsGeboren = 6176;
        expressieMap.put(
            idOuderOuderOuderschapIndicatieOuderUitWieKindIsGeboren,
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderschap.ouder_uit_wie_het_kind_is_geboren))");
        final int idOuderOuderOuderlijkGezagIndicatieOuderHeeftGezag = 3208;
        expressieMap.put(
            idOuderOuderOuderlijkGezagIndicatieOuderHeeftGezag,
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.ouder_heeft_gezag))");
        final int idRelatieHuwelijkIdentiteitSoort = 3198;
        expressieMap.put(idRelatieHuwelijkIdentiteitSoort, "RMAP(HUWELIJKEN(), h, $h.soort)");
        final int idRelatieHuwelijkStandaardDatumAanvang = 3754;
        expressieMap.put(idRelatieHuwelijkStandaardDatumAanvang, "RMAP(HUWELIJKEN(), h, $h.datum_aanvang)");
        final int idRelatieHuwelijkStandaardGemeenteAanvang = 3755;
        expressieMap.put(idRelatieHuwelijkStandaardGemeenteAanvang, "RMAP(HUWELIJKEN(), h, $h.gemeente_aanvang)");
        final int idRelatieHuwelijkStandaardWoonplaatsnaamAanvang = 3756;
        expressieMap.put(idRelatieHuwelijkStandaardWoonplaatsnaamAanvang, "RMAP(HUWELIJKEN(), h, $h.woonplaatsnaam_aanvang)");
        final int idRelatieHuwelijkStandaardBuitenlandsePlaatsAanvang = 3757;
        expressieMap.put(idRelatieHuwelijkStandaardBuitenlandsePlaatsAanvang, "RMAP(HUWELIJKEN(), h, $h.buitenlandse_plaats_aanvang)");
        final int idRelatieHuwelijkStandaardBuitenlandseRegioAanvang = 3759;
        expressieMap.put(idRelatieHuwelijkStandaardBuitenlandseRegioAanvang, "RMAP(HUWELIJKEN(), h, $h.buitenlandse_regio_aanvang)");
        final int idRelatieHuwelijkStandaardOmschrijvingLocatieAanvang = 3758;
        expressieMap.put(idRelatieHuwelijkStandaardOmschrijvingLocatieAanvang, "RMAP(HUWELIJKEN(), h, $h.omschrijving_locatie_aanvang)");
        final int idRelatieHuwelijkStandaardLandGebiedAanvang = 3760;
        expressieMap.put(idRelatieHuwelijkStandaardLandGebiedAanvang, "RMAP(HUWELIJKEN(), h, $h.land_gebied_aanvang)");
        final int idRelatieHuwelijkStandaardRedenEinde = 3207;
        expressieMap.put(idRelatieHuwelijkStandaardRedenEinde, "RMAP(HUWELIJKEN(), h, $h.reden_einde)");
        final int idRelatieHuwelijkStandaardDatumEinde = 3762;
        expressieMap.put(idRelatieHuwelijkStandaardDatumEinde, "RMAP(HUWELIJKEN(), h, $h.datum_einde)");
        final int idRelatieHuwelijkStandaardGemeenteEinde = 3763;
        expressieMap.put(idRelatieHuwelijkStandaardGemeenteEinde, "RMAP(HUWELIJKEN(), h, $h.gemeente_einde)");
        final int idRelatieHuwelijkStandaardWoonplaatsnaamEinde = 3764;
        expressieMap.put(idRelatieHuwelijkStandaardWoonplaatsnaamEinde, "RMAP(HUWELIJKEN(), h, $h.woonplaatsnaam_einde)");
        final int idRelatieHuwelijkStandaardBuitenlandsePlaatsEinde = 3765;
        expressieMap.put(idRelatieHuwelijkStandaardBuitenlandsePlaatsEinde, "RMAP(HUWELIJKEN(), h, $h.buitenlandse_plaats_einde)");
        final int idRelatieHuwelijkStandaardBuitenlandseRegioEinde = 3767;
        expressieMap.put(idRelatieHuwelijkStandaardBuitenlandseRegioEinde, "RMAP(HUWELIJKEN(), h, $h.buitenlandse_regio_einde)");
        final int idRelatieHuwelijkStandaardOmschrijvingLocatieEinde = 3766;
        expressieMap.put(idRelatieHuwelijkStandaardOmschrijvingLocatieEinde, "RMAP(HUWELIJKEN(), h, $h.omschrijving_locatie_einde)");
        final int idRelatieHuwelijkStandaardLandGebiedEinde = 3768;
        expressieMap.put(idRelatieHuwelijkStandaardLandGebiedEinde, "RMAP(HUWELIJKEN(), h, $h.land_gebied_einde)");
        final int idRelatieGeregistreerdpartnerschapIdentiteitSoort = 3198;
        expressieMap.put(idRelatieGeregistreerdpartnerschapIdentiteitSoort, "RMAP(PARTNERSCHAPPEN(), h, $h.soort)");
        final int idRelatieGeregistreerdpartnerschapStandaardDatumAanvang = 3754;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardDatumAanvang, "RMAP(PARTNERSCHAPPEN(), h, $h.datum_aanvang)");
        final int idRelatieGeregistreerdpartnerschapStandaardGemeenteAanvang = 3755;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardGemeenteAanvang, "RMAP(PARTNERSCHAPPEN(), h, $h.gemeente_aanvang)");
        final int idRelatieGeregistreerdpartnerschapStandaardWoonplaatsnaamAanvang = 3756;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardWoonplaatsnaamAanvang, "RMAP(PARTNERSCHAPPEN(), h, $h.woonplaatsnaam_aanvang)");
        final int idRelatieGeregistreerdpartnerschapStandaardBuitenlandsePlaatsAanvang = 3757;
        expressieMap.put(
            idRelatieGeregistreerdpartnerschapStandaardBuitenlandsePlaatsAanvang,
            "RMAP(PARTNERSCHAPPEN(), h, $h.buitenlandse_plaats_aanvang)");
        final int idRelatieGeregistreerdpartnerschapStandaardBuitenlandseRegioAanvang = 3759;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardBuitenlandseRegioAanvang, "RMAP(PARTNERSCHAPPEN(), h, $h.buitenlandse_regio_aanvang)");
        final int idRelatieGeregistreerdpartnerschapStandaardOmschrijvingLocatieAanvang = 3758;
        expressieMap.put(
            idRelatieGeregistreerdpartnerschapStandaardOmschrijvingLocatieAanvang,
            "RMAP(PARTNERSCHAPPEN(), h, $h.omschrijving_locatie_aanvang)");
        final int idRelatieGeregistreerdpartnerschapStandaardLandGebiedAanvang = 3760;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardLandGebiedAanvang, "RMAP(PARTNERSCHAPPEN(), h, $h.land_gebied_aanvang)");
        final int idRelatieGeregistreerdpartnerschapStandaardRedenEinde = 3207;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardRedenEinde, "RMAP(PARTNERSCHAPPEN(), h, $h.reden_einde)");
        final int idRelatieGeregistreerdpartnerschapStandaardDatumEinde = 3762;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardDatumEinde, "RMAP(PARTNERSCHAPPEN(), h, $h.datum_einde)");
        final int idRelatieGeregistreerdpartnerschapStandaardGemeenteEinde = 3763;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardGemeenteEinde, "RMAP(PARTNERSCHAPPEN(), h, $h.gemeente_einde)");
        final int idRelatieGeregistreerdpartnerschapStandaardWoonplaatsnaamEinde = 3764;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardWoonplaatsnaamEinde, "RMAP(PARTNERSCHAPPEN(), h, $h.woonplaatsnaam_einde)");
        final int idRelatieGeregistreerdpartnerschapStandaardBuitenlandsePlaatsEinde = 3765;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardBuitenlandsePlaatsEinde, "RMAP(PARTNERSCHAPPEN(), h, $h.buitenlandse_plaats_einde)");
        final int idRelatieGeregistreerdpartnerschapStandaardBuitenlandseRegioEinde = 3767;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardBuitenlandseRegioEinde, "RMAP(PARTNERSCHAPPEN(), h, $h.buitenlandse_regio_einde)");
        final int idRelatieGeregistreerdpartnerschapStandaardOmschrijvingLocatieEinde = 3766;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardOmschrijvingLocatieEinde, "RMAP(PARTNERSCHAPPEN(), h, $h.omschrijving_locatie_einde)");
        final int idRelatieGeregistreerdpartnerschapStandaardLandGebiedEinde = 3768;
        expressieMap.put(idRelatieGeregistreerdpartnerschapStandaardLandGebiedEinde, "RMAP(PARTNERSCHAPPEN(), h, $h.land_gebied_einde)");
        final int idRelatieFamilierechtelijkebetrekkingIdentiteitSoort = 3198;
        expressieMap.put(idRelatieFamilierechtelijkebetrekkingIdentiteitSoort, "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.soort)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardDatumAanvang = 3754;
        expressieMap.put(idRelatieFamilierechtelijkebetrekkingStandaardDatumAanvang, "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.datum_aanvang)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardGemeenteAanvang = 3755;
        expressieMap.put(idRelatieFamilierechtelijkebetrekkingStandaardGemeenteAanvang, "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.gemeente_aanvang)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardWoonplaatsnaamAanvang = 3756;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardWoonplaatsnaamAanvang,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.woonplaatsnaam_aanvang)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardBuitenlandsePlaatsAanvang = 3757;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardBuitenlandsePlaatsAanvang,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.buitenlandse_plaats_aanvang)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardBuitenlandseRegioAanvang = 3759;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardBuitenlandseRegioAanvang,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.buitenlandse_regio_aanvang)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardOmschrijvingLocatieAanvang = 3758;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardOmschrijvingLocatieAanvang,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.omschrijving_locatie_aanvang)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardLandGebiedAanvang = 3760;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardLandGebiedAanvang,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.land_gebied_aanvang)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardRedenEinde = 3207;
        expressieMap.put(idRelatieFamilierechtelijkebetrekkingStandaardRedenEinde, "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.reden_einde)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardDatumEinde = 3762;
        expressieMap.put(idRelatieFamilierechtelijkebetrekkingStandaardDatumEinde, "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.datum_einde)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardGemeenteEinde = 3763;
        expressieMap.put(idRelatieFamilierechtelijkebetrekkingStandaardGemeenteEinde, "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.gemeente_einde)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardWoonplaatsnaamEinde = 3764;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardWoonplaatsnaamEinde,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.woonplaatsnaam_einde)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardBuitenlandsePlaatsEinde = 3765;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardBuitenlandsePlaatsEinde,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.buitenlandse_plaats_einde)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardBuitenlandseRegioEinde = 3767;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardBuitenlandseRegioEinde,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.buitenlandse_regio_einde)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardOmschrijvingLocatieEinde = 3766;
        expressieMap.put(
            idRelatieFamilierechtelijkebetrekkingStandaardOmschrijvingLocatieEinde,
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.omschrijving_locatie_einde)");
        final int idRelatieFamilierechtelijkebetrekkingStandaardLandGebiedEinde = 3768;
        expressieMap.put(idRelatieFamilierechtelijkebetrekkingStandaardLandGebiedEinde, "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.land_gebied_einde)");
        return expressieMap;
    }

}
