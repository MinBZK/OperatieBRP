/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.herkomst;

import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;

/**
 * Enumeratie van alle LO3 elementen.
 */
public enum Lo3ElementEnum {

    /**
     * A-Nummer.
     */
    ELEMENT_0110(Lo3GroepEnum.GROEP01, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 10, "A-nummer"),

    /**
     * Burgerservicenummer.
     */
    ELEMENT_0120(Lo3GroepEnum.GROEP01, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 9, "Burgerservicenummer"),

    /**
     * Voornamen.
     */
    ELEMENT_0210(Lo3GroepEnum.GROEP02, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, 200, true, "Voornamen"),

    /**
     * Adellijke titel/predikaat.
     */
    ELEMENT_0220(Lo3GroepEnum.GROEP02, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, 2, false, "Adellijke titel/predikaat"),

    /**
     * Voorvoegsel geslachtsnaam.
     */
    ELEMENT_0230(Lo3GroepEnum.GROEP02, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.ALPHANUMERIEK, 1, 10, false, "Voorvoegsel geslachtsnaam"),

    /**
     * Geslachtsnaam.
     */
    ELEMENT_0240(Lo3GroepEnum.GROEP02, Lo3RubriekVolgnummerEnum.VOLGNR_40, Type.ALPHANUMERIEK, 1, 200, true, "Geslachtsnaam"),

    /**
     * Geboortedatum.
     */
    ELEMENT_0310(Lo3GroepEnum.GROEP03, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Geboortedatum"),

    /**
     * Geboorteplaats.
     */
    ELEMENT_0320(Lo3GroepEnum.GROEP03, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, 40, true, "Geboorteplaats"),

    /**
     * Geboorteland.
     */
    ELEMENT_0330(Lo3GroepEnum.GROEP03, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 4, "Geboorteland"),

    /**
     * Geslachtsaanduiding.
     */
    ELEMENT_0410(Lo3GroepEnum.GROEP04, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, "Geslachtsaanduiding"),

    /**
     * Nationaliteit.
     */
    ELEMENT_0510(Lo3GroepEnum.GROEP05, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "Nationaliteit"),

    /**
     * Datum huwelijkssluiting/aangaan geregistreerd partnerschap.
     */
    ELEMENT_0610(Lo3GroepEnum.GROEP06, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8,
            "Datum huwelijkssluiting / aangaan geregistreerd partnerschap"),

    /**
     * Plaats huwelijkssluiting/aangaan geregistreerd partnerschap.
     */
    ELEMENT_0620(Lo3GroepEnum.GROEP06, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, 40, true,
            "Plaats huwelijkssluiting / aangaan geregistreerd partnerschap"),

    /**
     * Land huwelijkssluiting/aangaan geregistreerd partnerschap.
     */
    ELEMENT_0630(Lo3GroepEnum.GROEP06, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 4,
            "Land huwelijkssluiting / aangaan geregistreerd partnerschap"),

    /**
     * Datum ontbinding huwelijkssluiting/geregistreerd partnerschap.
     */
    ELEMENT_0710(Lo3GroepEnum.GROEP07, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Datum ontbinding huwelijk / geregistreerd partnerschap"),

    /**
     * Plaats ontbinding huwelijkssluiting/geregistreerd partnerschap.
     */
    ELEMENT_0720(Lo3GroepEnum.GROEP07, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, 40, true,
            "Plaats ontbinding huwelijk / geregistreerd partnerschap"),

    /**
     * Land ontbinding huwelijkssluiting/geregistreerd partnerschap.
     */
    ELEMENT_0730(Lo3GroepEnum.GROEP07, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 4, "Land ontbinding huwelijk / geregistreerd partnerschap"),

    /**
     * Reden ontbinding huwelijkssluiting/geregistreerd partnerschap.
     */
    ELEMENT_0740(Lo3GroepEnum.GROEP07, Lo3RubriekVolgnummerEnum.VOLGNR_40, Type.ALPHANUMERIEK, 1,
            "Reden ontbinding huwelijk / geregistreerd partnerschap"),

    /**
     * Datum overlijden.
     */
    ELEMENT_0810(Lo3GroepEnum.GROEP08, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Datum overlijden"),

    /**
     * Plaats overlijden.
     */
    ELEMENT_0820(Lo3GroepEnum.GROEP08, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, 40, true, "Plaats overlijden"),

    /**
     * Land overlijden.
     */
    ELEMENT_0830(Lo3GroepEnum.GROEP08, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 4, "Land overlijden"),

    /**
     * Gemeente van inschrijving.
     */
    ELEMENT_0910(Lo3GroepEnum.GROEP09, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "Gemeente van inschrijving"),

    /**
     * Datum inschrijving.
     */
    ELEMENT_0920(Lo3GroepEnum.GROEP09, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Datum inschrijving"),

    /**
     * Functie adres.
     */
    ELEMENT_1010(Lo3GroepEnum.GROEP10, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, "Functie adres"),

    /**
     * Gemeentedeel.
     */
    ELEMENT_1020(Lo3GroepEnum.GROEP10, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, 24, true, "Gemeentedeel"),

    /**
     * Datum aanvang adreshouding.
     */
    ELEMENT_1030(Lo3GroepEnum.GROEP10, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 8, "Datum aanvang adreshouding"),

    /**
     * Straatnaam.
     */
    ELEMENT_1110(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, 24, true, "Straatnaam"),

    /**
     * Naam Openbare ruimte.
     */
    ELEMENT_1115(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_15, Type.ALPHANUMERIEK, 1, 80, true, "Naam openbare ruimte"),

    /**
     * Huisnummer.
     */
    ELEMENT_1120(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 1, 5, false, "Huisnummer"),

    /**
     * Huisletter.
     */
    ELEMENT_1130(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.ALPHANUMERIEK, 1, "Huisletter"),

    /**
     * Huisnummertoevoeging.
     */
    ELEMENT_1140(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_40, Type.ALPHANUMERIEK, 1, 4, false, "Huisnummertoevoeging"),

    /**
     * Aanduiding bij huisnummer.
     */
    ELEMENT_1150(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_50, Type.ALPHANUMERIEK, 2, "Aanduiding bij huisnummer"),

    /**
     * Postcode.
     */
    ELEMENT_1160(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_60, Type.ALPHANUMERIEK, 6, "Postcode"),

    /**
     * Woonplaatsnaam.
     */
    ELEMENT_1170(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_70, Type.ALPHANUMERIEK, 1, 80, true, "Woonplaatsnaam"),

    /**
     * Identificatiecode verblijfplaats.
     */
    ELEMENT_1180(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_80, Type.ALPHANUMERIEK, 16, "Identificatiecode verblijfplaats"),

    /**
     * Identificatiecode nummeraanduiding.
     */
    ELEMENT_1190(Lo3GroepEnum.GROEP11, Lo3RubriekVolgnummerEnum.VOLGNR_90, Type.ALPHANUMERIEK, 16, "Identificatiecode nummeraanduiding"),

    /**
     * Locatiebeschrijving.
     */
    ELEMENT_1210(Lo3GroepEnum.GROEP12, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, 35, true, "Locatiebeschrijving"),

    /**
     * Land adres buitenland.
     */
    ELEMENT_1310(Lo3GroepEnum.GROEP13, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "Land adres buitenland"),

    /**
     * Datum aanvang adres buitenland.
     */
    ELEMENT_1320(Lo3GroepEnum.GROEP13, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Datum aanvang adres buitenland"),

    /**
     * Regel 1 adres buitenland.
     */
    ELEMENT_1330(Lo3GroepEnum.GROEP13, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.ALPHANUMERIEK, 1, 35, true, "Regel 1 adres buitenland"),

    /**
     * Regel 2 adres buitenland.
     */
    ELEMENT_1340(Lo3GroepEnum.GROEP13, Lo3RubriekVolgnummerEnum.VOLGNR_40, Type.ALPHANUMERIEK, 1, 35, true, "Regel 2 adres buitenland"),

    /**
     * Regel 3 adres buitenland.
     */
    ELEMENT_1350(Lo3GroepEnum.GROEP13, Lo3RubriekVolgnummerEnum.VOLGNR_50, Type.ALPHANUMERIEK, 1, 35, true, "Regel 3 adres buitenland"),

    /**
     * Land vanwaar ingeschreven.
     */
    ELEMENT_1410(Lo3GroepEnum.GROEP14, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "Land vanwaar ingeschreven"),

    /**
     * Datum vestiging in Nederland.
     */
    ELEMENT_1420(Lo3GroepEnum.GROEP14, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Datum vestiging in Nederland"),

    /**
     * Soort verbintenis.
     */
    ELEMENT_1510(Lo3GroepEnum.GROEP15, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, "Soort verbintenis"),

    /**
     * Vorig A-nummer.
     */
    ELEMENT_2010(Lo3GroepEnum.GROEP20, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 10, "Vorig A-nummer"),

    /**
     * Volgend A-nummer.
     */
    ELEMENT_2020(Lo3GroepEnum.GROEP20, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 10, "Volgend A-nummer"),

    /**
     * Aanduiding Europees kiesrecht.
     */
    ELEMENT_3110(Lo3GroepEnum.GROEP31, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 1, "Aanduiding Europees kiesrecht"),

    /**
     * Datum verzoek of mededeling Europees kiesrecht.
     */
    ELEMENT_3120(Lo3GroepEnum.GROEP31, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Datum verzoek of mededeling Europees kiesrecht"),

    /**
     * Einddatum uitsluiting Europees kiesrecht.
     */
    ELEMENT_3130(Lo3GroepEnum.GROEP31, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 8, "Einddatum uitsluiting Europees kiesrecht"),

    /**
     * Indicatie gezag minderjarige.
     */
    ELEMENT_3210(Lo3GroepEnum.GROEP32, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, 2, false, "Indicatie gezag minderjarige"),

    /**
     * Indicate curateleregister.
     */
    ELEMENT_3310(Lo3GroepEnum.GROEP33, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 1, "Indicatie curateleregister"),

    /**
     * Soort Nederlands reisdocument.
     */
    ELEMENT_3510(Lo3GroepEnum.GROEP35, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 2, "Soort Nederlands reisdocument"),

    /**
     * Nummer Nederlands reisdocument.
     */
    ELEMENT_3520(Lo3GroepEnum.GROEP35, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 9, "Nummer Nederlands reisdocument"),

    /**
     * Datum uitgifte Nederlands reisdocument.
     */
    ELEMENT_3530(Lo3GroepEnum.GROEP35, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 8, "Datum uitgifte Nederlands reisdocument"),

    /**
     * Autoriteit van afgifte Nederlands reisdocument.
     */
    ELEMENT_3540(Lo3GroepEnum.GROEP35, Lo3RubriekVolgnummerEnum.VOLGNR_40, Type.ALPHANUMERIEK, 2, 6, false,
            "Autoriteit van afgifte Nederlands reisdocument"),

    /**
     * Datum einde geldigheid Nederlands reisdocument.
     */
    ELEMENT_3550(Lo3GroepEnum.GROEP35, Lo3RubriekVolgnummerEnum.VOLGNR_50, Type.NUMERIEK, 8, "Datum einde geldigheid Nederlands reisdocument"),

    /**
     * Datum inhouding dan wel vermissing Nederlands reisdocument.
     */
    ELEMENT_3560(Lo3GroepEnum.GROEP35, Lo3RubriekVolgnummerEnum.VOLGNR_60, Type.NUMERIEK, 8, "Datum inhouding dan wel vermissing Nederlands reisdocument"),

    /**
     * Aanduiding inhouding dan wel vermissing Nederlands reisdocument.
     */
    ELEMENT_3570(Lo3GroepEnum.GROEP35, Lo3RubriekVolgnummerEnum.VOLGNR_70, Type.ALPHANUMERIEK, 1,
            "Aanduiding inhouding dan wel vermissing Nederlands reisdocument"),

    /**
     * Lengte houder.
     */
    ELEMENT_3580(Lo3GroepEnum.GROEP35, Lo3RubriekVolgnummerEnum.VOLGNR_80, Type.NUMERIEK, 3, "Lengte houder"),

    /**
     * Signalering met betrekking tot het verstrekken van een Nederlands reisdocument.
     */
    ELEMENT_3610(Lo3GroepEnum.GROEP36, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 1,
            "Signalering met betrekking tot verstrekken Nederlands reisdocument"),

    /**
     * Aanduiding bezit buitenlands reisdocument.
     */
    ELEMENT_3710(Lo3GroepEnum.GROEP37, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 1, "Aanduiding bezit buitenlands reisdocument"),

    /**
     * Aanduiding uitgesloten kiesrecht.
     */
    ELEMENT_3810(Lo3GroepEnum.GROEP38, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, "Aanduiding uitgesloten kiesrecht"),

    /**
     * Einddatum uitsluiting kiesrecht.
     */
    ELEMENT_3820(Lo3GroepEnum.GROEP38, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Einddatum uitsluiting kiesrecht"),

    /**
     * Aanduiding verblijfstitel.
     */
    ELEMENT_3910(Lo3GroepEnum.GROEP39, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 2, "Aanduiding verblijfstitel"),

    /**
     * Datum einde verblijfstitel.
     */
    ELEMENT_3920(Lo3GroepEnum.GROEP39, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Datum einde verblijfstitel"),

    /**
     * Ingangsdatum verblijfstitel.
     */
    ELEMENT_3930(Lo3GroepEnum.GROEP39, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 8, "Ingangsdatum verblijfstitel"),

    /**
     * Afnemersindicatie.
     */
    ELEMENT_4010(Lo3GroepEnum.GROEP40, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 6, EnumNaam.AFNEMERSINDICATIE),
    /**
     * Aantekening.
     */
    ELEMENT_4210(Lo3GroepEnum.GROEP42, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, 24, true, "Aantekening"),

    /**
     * Aanduiding naamgebruik.
     */
    ELEMENT_6110(Lo3GroepEnum.GROEP61, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, "Aanduiding naamgebruik"),

    /**
     * Datum ingang familierechtelijke betrekking.
     */
    ELEMENT_6210(Lo3GroepEnum.GROEP62, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Datum ingang familierechtelijke betrekking"),

    /**
     * Reden verkrijging Nederlandse nationaliteit.
     */
    ELEMENT_6310(Lo3GroepEnum.GROEP63, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 3, "Reden verkrijging Nederlandse nationaliteit"),

    /**
     * Reden verlies Nederlandse nationaliteit.
     */
    ELEMENT_6410(Lo3GroepEnum.GROEP64, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 3, "Reden verlies Nederlandse nationaliteit"),

    /**
     * Aanduiding bijzonder Nederlanderschap.
     */
    ELEMENT_6510(Lo3GroepEnum.GROEP65, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, "Aanduiding bijzonder Nederlanderschap"),

    /**
     * Datum ingang blokkering PL.
     */
    ELEMENT_6620(Lo3GroepEnum.GROEP66, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Datum ingang blokkering PL"),

    /**
     * Datum opschorting bijhouding.
     */
    ELEMENT_6710(Lo3GroepEnum.GROEP67, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Datum opschorting bijhouding"),

    /**
     * Omschrijving reden opschorting bijhouding.
     */
    ELEMENT_6720(Lo3GroepEnum.GROEP67, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, "Omschrijving reden opschorting bijhouding"),

    /**
     * Datum eerste inschrijving GBA/RNI.
     */
    ELEMENT_6810(Lo3GroepEnum.GROEP68, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Datum eerste inschrijving GBA/RNI"),

    /**
     * Gemeente waar de PK zich bevindt.
     */
    ELEMENT_6910(Lo3GroepEnum.GROEP69, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "Gemeente waar de PK zich bevindt"),

    /**
     * Indicatie geheim.
     */
    ELEMENT_7010(Lo3GroepEnum.GROEP70, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 1, "Indicatie geheim"),

    /**
     * Datum verificatie (RNI element).
     */
    ELEMENT_7110(Lo3GroepEnum.GROEP71, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Datum verificatie"),

    /**
     * Omschrijving verificatie (RNI element).
     */
    ELEMENT_7120(Lo3GroepEnum.GROEP71, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, 50, true, "Omschrijving verificatie"),

    /**
     * Omschrijving van de aangifte adreshouding.
     */
    ELEMENT_7210(Lo3GroepEnum.GROEP72, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, "Omschrijving van de aangifte adreshouding"),

    /**
     * EU-persoonsnummer.
     */
    ELEMENT_7310(Lo3GroepEnum.GROEP73, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, 40, false, "EU-persoonsnummer"),
    /**
     * Indicatie document.
     */
    ELEMENT_7510(Lo3GroepEnum.GROEP75, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 1, "Indicatie document"),

    /**
     * Versienummer.
     */
    ELEMENT_8010(Lo3GroepEnum.GROEP80, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "Versienummer"),

    /**
     * Datumtijdstempel.
     */
    ELEMENT_8020(Lo3GroepEnum.GROEP80, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 17, "Datumtijdstempel"),

    /**
     * Registergemeente akte.
     */
    ELEMENT_8110(Lo3GroepEnum.GROEP81, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "Registergemeente akte"),

    /**
     * Aktenummer.
     */
    ELEMENT_8120(Lo3GroepEnum.GROEP81, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 7, "Aktenummer"),

    /**
     * Gemeente document.
     */
    ELEMENT_8210(Lo3GroepEnum.GROEP82, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "Gemeente document"),

    /**
     * Datum document.
     */
    ELEMENT_8220(Lo3GroepEnum.GROEP82, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Datum document"),

    /**
     * Beschrijving document.
     */
    ELEMENT_8230(Lo3GroepEnum.GROEP82, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.ALPHANUMERIEK, 1, 40, true, "Beschrijving document"),

    /**
     * Aanduiding gegevens in onderzoek.
     */
    ELEMENT_8310(Lo3GroepEnum.GROEP83, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 6, "Aanduiding gegevens in onderzoek"),

    /**
     * Datum ingang onderzoek.
     */
    ELEMENT_8320(Lo3GroepEnum.GROEP83, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.NUMERIEK, 8, "Datum ingang onderzoek"),

    /**
     * Datum einde onderzoek.
     */
    ELEMENT_8330(Lo3GroepEnum.GROEP83, Lo3RubriekVolgnummerEnum.VOLGNR_30, Type.NUMERIEK, 8, "Datum einde onderzoek"),

    /**
     * Indicatie onjuist, dan wel strijdigheid met de openbare orde.
     */
    ELEMENT_8410(Lo3GroepEnum.GROEP84, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1,
            "Indicatie onjuist, dan wel strijdigheid met de openbare orde"),

    /**
     * Ingangsdatum geldigheid.
     */
    ELEMENT_8510(Lo3GroepEnum.GROEP85, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Ingangsdatum geldigheid"),

    /**
     * Datum van opneming.
     */
    ELEMENT_8610(Lo3GroepEnum.GROEP86, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 8, "Datum van opneming"),

    /**
     * PK-gegevens volledig meegeconverteerd.
     */
    ELEMENT_8710(Lo3GroepEnum.GROEP87, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.ALPHANUMERIEK, 1, "PK-gegevens volledig meegeconverteerd"),

    /**
     * RNI Deelnemer (RNI element).
     */
    ELEMENT_8810(Lo3GroepEnum.GROEP88, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 4, "RNI-deelnemer"),

    /**
     * Omschrijving verdrag (RNI element).
     */
    ELEMENT_8820(Lo3GroepEnum.GROEP88, Lo3RubriekVolgnummerEnum.VOLGNR_20, Type.ALPHANUMERIEK, 1, 50, true, "Omschrijving verdrag"),

    /**
     * Afnemersindicatie (Tabel 35).
     */
    ELEMENT_9510(Lo3GroepEnum.GROEP95, Lo3RubriekVolgnummerEnum.VOLGNR_10, Type.NUMERIEK, 6, EnumNaam.AFNEMERSINDICATIE);

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** LEESBARE NAMEN *************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * 01.10 A-nummer.
     */
    public static final Lo3ElementEnum ANUMMER = ELEMENT_0110;
    /**
     * 01.20 Burgerservicenummer.
     */
    public static final Lo3ElementEnum BURGERSERVICENUMMER = ELEMENT_0120;
    /**
     * 02.10 Voornamen.
     */
    public static final Lo3ElementEnum VOORNAMEN = ELEMENT_0210;
    /**
     * 02.30 Voorvoegsel.
     */
    public static final Lo3ElementEnum VOORVOEGSEL = ELEMENT_0230;
    /**
     * 02.40 Geslachtsnaam.
     */
    public static final Lo3ElementEnum GESLACHTSNAAM = ELEMENT_0240;
    /**
     * 03.10 Geboortedatum.
     */
    public static final Lo3ElementEnum GEBOORTEDATUM = ELEMENT_0310;
    /**
     * 03.20 Geboorteplaats.
     */
    public static final Lo3ElementEnum GEBOORTEPLAATS = ELEMENT_0320;
    /**
     * 03.30 Geboorteland.
     */
    public static final Lo3ElementEnum GEBOORTELAND = ELEMENT_0330;
    /**
     * 04.10 Geslachtsaanduiding.
     */
    public static final Lo3ElementEnum GESLACHTSAANDUIDING = ELEMENT_0410;
    /**
     * 09.10 Gemeente van inschrijving.
     */
    public static final Lo3ElementEnum GEMEENTE_VAN_INSCHRIJVING = ELEMENT_0910;

    /* ************************************************************************************************************* */
 /* ************************************************************************************************************* */
 /* ************************************************************************************************************* */
 /* ************************************************************************************************************* */
 /* ************************************************************************************************************* */
    private final Lo3GroepEnum groep;
    private final Lo3RubriekVolgnummerEnum rubriekVolgnummer;
    private final Type type;
    private final int minimumLengte;
    private final int maximumLengte;
    private final boolean afkappen;
    private final String label;

    /**
     * Constructor.
     * @param groep groep
     * @param rubriekVolgnummer rubriek volgnummer
     * @param type type
     * @param lengte lengte
     * @param label label
     */
    Lo3ElementEnum(
            final Lo3GroepEnum groep,
            final Lo3RubriekVolgnummerEnum rubriekVolgnummer,
            final Type type,
            final int lengte,
            final String label) {
        this(groep, rubriekVolgnummer, type, lengte, lengte, false, label);
    }

    /**
     * Constructor.
     * @param groep groep
     * @param rubriekVolgnummer rubriek volgnummer
     * @param type type
     * @param minimumLengte minimum lengte
     * @param maximumLengte maximum lengte
     * @param afkappen afkappen
     * @param label label
     */
    Lo3ElementEnum(
            final Lo3GroepEnum groep,
            final Lo3RubriekVolgnummerEnum rubriekVolgnummer,
            final Type type,
            final int minimumLengte,
            final int maximumLengte,
            final boolean afkappen,
            final String label) {
        this.groep = groep;
        this.rubriekVolgnummer = rubriekVolgnummer;
        this.type = type;
        this.minimumLengte = minimumLengte;
        this.maximumLengte = maximumLengte;
        this.afkappen = afkappen;
        this.label = label;
    }

    /**
     * Geef de waarde van element nummer.
     * @return het elementnummer
     */
    public String getElementNummer() {
        return this.getElementNummer(false);
    }

    /**
     * Geeft het elementnummer terug.
     * @param leesbaar Indien true dan leesbaar (xx.xx) anders (xxxx).
     * @return elementnummer als String.
     */
    public String getElementNummer(final boolean leesbaar) {
        final StringBuilder sb = new StringBuilder();
        sb.append(groep.toString());
        if (leesbaar) {
            sb.append(".");
        }
        sb.append(rubriekVolgnummer.toString());
        return sb.toString();
    }

    /**
     * Geef de waarde van groep.
     * @return groep
     */
    public Lo3GroepEnum getGroep() {
        return groep;
    }

    /**
     * Geef de waarde van rubriek.
     * @return rubriek
     */
    public Lo3RubriekVolgnummerEnum getRubriek() {
        return rubriekVolgnummer;
    }

    /**
     * Geef de waarde van type.
     * @return type
     */
    public Type getType() {
        return type;
    }

    /**
     * Geef de waarde van minimum lengte.
     * @return minimum lengte
     */
    public int getMinimumLengte() {
        return minimumLengte;
    }

    /**
     * Geef de waarde van maximum lengte.
     * @return maximum lengte
     */
    public int getMaximumLengte() {
        return maximumLengte;
    }

    /**
     * Geef de waarde van afkappen.
     * @return afkappen
     */
    public boolean getAfkappen() {
        return afkappen;
    }

    /**
     * Geef de waarde van label.
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param element het element nummer
     * @return de corresponderende Lo3ElementEnum: ELEMENT_[element]
     * @throws Lo3SyntaxException Wordt gegooid als de gevraagde waarde niet voorkomt binnen de enumeratie
     */
    public static Lo3ElementEnum getLO3Element(final String element) throws Lo3SyntaxException {
        try {
            return Lo3ElementEnum.valueOf("ELEMENT_" + element);
        } catch (final IllegalArgumentException iae) {
            // Gevraagde waarde niet gevonden in enumeratie.
            throw new Lo3SyntaxException(iae);
        }
    }

    /**
     * Element type.
     */
    public enum Type {
        /**
         * Een element heeft een waarde met uitsluitend numerieke karakters.
         */
        NUMERIEK,
        /**
         * Een element heeft een waarde met alphanumerieke karakters.
         */
        ALPHANUMERIEK
    }

    /**
     * Voor dubbele namen in de enum.
     */
    private static class EnumNaam {
        public static final String AFNEMERSINDICATIE = "Afnemersindicatie";
    }
}
