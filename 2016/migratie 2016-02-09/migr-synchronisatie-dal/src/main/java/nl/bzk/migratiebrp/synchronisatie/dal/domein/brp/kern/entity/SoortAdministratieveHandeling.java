/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
 select format(E'/** %s. *\/\n%s ((short) %s, "%s", "%s", %s, (short) %s, "%s", (short) %s),',
 s.naam, regexp_replace(regexp_replace(upper(s.naam), ' |-', '_', 'g'), '\(|\)', '', 'g'), s.id, code, s.naam,
 CASE WHEN c.naam is NULL THEN 'null' ELSE 'CategorieAdministratieveHandeling.' || regexp_replace(replace(upper(c.naam), ' ', '_'), '\(|\)', '', 'g') END,
 module, alias, koppelvlak)
 from kern.srtadmhnd s left join kern.categorieadmhnd c on s.categorieadmhnd = c.id
 </code>
 *
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 *
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 *
 * Voer daarna bovenstaand SQL statement uit.
 */
@SuppressWarnings({"checkstyle:designforextension", "checkstyle:multiplestringliterals" })
public enum SoortAdministratieveHandeling implements Enumeratie {

    /** Geboorte in Nederland. */
    GEBOORTE_IN_NEDERLAND((short) 1, "01001", "Geboorte in Nederland", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1,
            "Geboorte in Nederland", (short) 1),
    /** Erkenning voor geboorte (vervallen). */
    ERKENNING_VOOR_GEBOORTE_VERVALLEN((short) 2, "01016", "Erkenning voor geboorte (vervallen)", null, (short) 1, "Erkenning voor geboorte (vervallen)",
            (short) 1),
    /** Geboorte in Nederland met erkenning. */
    GEBOORTE_IN_NEDERLAND_MET_ERKENNING((short) 3, "01002", "Geboorte in Nederland met erkenning", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 1, "Geboorte in Nederland met erkenning", (short) 1),
    /** Erkenning na geboorte. */
    ERKENNING_NA_GEBOORTE((short) 4, "01003", "Erkenning na geboorte", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1,
            "Erkenning na geboorte", (short) 1),
    /** Vernietiging erkenning. */
    VERNIETIGING_ERKENNING((short) 5, "01004", "Vernietiging erkenning", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1,
            "Vernietiging erkenning", (short) 1),
    /** Adoptie ingezetene. */
    ADOPTIE_INGEZETENE((short) 6, "01007", "Adoptie ingezetene", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1, "Adoptie ingezetene",
            (short) 1),
    /** Omzetting adoptie. */
    OMZETTING_ADOPTIE((short) 7, "01008", "Omzetting adoptie", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1, "Omzetting adoptie", (short) 1),
    /** Herroeping adoptie. */
    HERROEPING_ADOPTIE((short) 8, "01009", "Herroeping adoptie", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1, "Herroeping adoptie",
            (short) 1),
    /** Vaststelling ouderschap. */
    VASTSTELLING_OUDERSCHAP((short) 9, "01005", "Vaststelling ouderschap", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1,
            "Vaststelling ouderschap", (short) 1),
    /** Toevoeging geboorteakte. */
    TOEVOEGING_GEBOORTEAKTE((short) 10, "01010", "Toevoeging geboorteakte", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1,
            "Toevoeging geboorteakte", (short) 1),
    /** Verbetering geboorteakte. */
    VERBETERING_GEBOORTEAKTE((short) 11, "01011", "Verbetering geboorteakte", CategorieAdministratieveHandeling.CORRECTIE, (short) 1,
            "Verbetering geboorteakte", (short) 1),
    /** Wijziging kindgegevens. */
    WIJZIGING_KINDGEGEVENS((short) 12, "01014", "Wijziging kindgegevens", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1,
            "Wijziging kindgegevens", (short) 1),
    /** Ontkenning ouderschap. */
    ONTKENNING_OUDERSCHAP((short) 13, "01006", "Ontkenning ouderschap", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1,
            "Ontkenning ouderschap", (short) 1),
    /** Inroeping van staat. */
    INROEPING_VAN_STAAT((short) 14, "01013", "Inroeping van staat", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1, "Inroeping van staat",
            (short) 1),
    /** Betwisting van staat. */
    BETWISTING_VAN_STAAT((short) 15, "01012", "Betwisting van staat", CategorieAdministratieveHandeling.ACTUALISERING, (short) 1, "Betwisting van staat",
            (short) 1),
    /** Correctie afstamming. */
    CORRECTIE_AFSTAMMING((short) 16, "01015", "Correctie afstamming", CategorieAdministratieveHandeling.CORRECTIE, (short) 1, "Correctie afstamming",
            (short) 1),
    /** Wijziging geslachtsnaam. */
    WIJZIGING_GESLACHTSNAAM((short) 17, "02001", "Wijziging geslachtsnaam", CategorieAdministratieveHandeling.ACTUALISERING, (short) 7,
            "Wijziging geslachtsnaam", (short) 1),
    /** Voltrekking huwelijk in Nederland. */
    VOLTREKKING_HUWELIJK_IN_NEDERLAND((short) 18, "04001", "Voltrekking huwelijk in Nederland", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 2, "Voltrekking huwelijk in Nederland", (short) 1),
    /** Ontbinding huwelijk in Nederland. */
    ONTBINDING_HUWELIJK_IN_NEDERLAND((short) 19, "04002", "Ontbinding huwelijk in Nederland", CategorieAdministratieveHandeling.ACTUALISERING, (short) 2,
            "Ontbinding huwelijk in Nederland", (short) 1),
    /** Aangaan geregistreerd partnerschap in Nederland. */
    AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND((short) 20, "04005", "Aangaan geregistreerd partnerschap in Nederland",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 2, "Aangaan geregistreerd partnerschap in Nederland", (short) 1),
    /** Beeindiging geregistreerd partnerschap in Nederland. */
    BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND((short) 21, "04006", "Beeindiging geregistreerd partnerschap in Nederland",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 2, "Beeindiging geregistreerd partnerschap in Nederland", (short) 1),
    /** Voltrekking huwelijk in buitenland. */
    VOLTREKKING_HUWELIJK_IN_BUITENLAND((short) 22, "04003", "Voltrekking huwelijk in buitenland", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 2, "Voltrekking huwelijk in buitenland", (short) 1),
    /** Ontbinding huwelijk in buitenland. */
    ONTBINDING_HUWELIJK_IN_BUITENLAND((short) 23, "04004", "Ontbinding huwelijk in buitenland", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 2, "Ontbinding huwelijk in buitenland", (short) 1),
    /** Nietigverklaring huwelijk. */
    NIETIGVERKLARING_HUWELIJK((short) 24, "04010", "Nietigverklaring huwelijk", CategorieAdministratieveHandeling.ACTUALISERING, (short) 2,
            "Nietigverklaring huwelijk", (short) 1),
    /** Nietigverklaring geregistreerd partnerschap. */
    NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP((short) 25, "04011", "Nietigverklaring geregistreerd partnerschap",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 2, "Nietigverklaring geregistreerd partnerschap", (short) 1),
    /** Omzetting geregistreerd partnerschap in huwelijk. */
    OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK((short) 26, "04009", "Omzetting geregistreerd partnerschap in huwelijk",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 2, "Omzetting geregistreerd partnerschap in huwelijk", (short) 1),
    /** Correctie huwelijk. */
    CORRECTIE_HUWELIJK((short) 27, "04014", "Correctie huwelijk", CategorieAdministratieveHandeling.CORRECTIE, (short) 2, "Correctie huwelijk", (short) 1),
    /** Correctie geregistreerd partnerschap. */
    CORRECTIE_GEREGISTREERD_PARTNERSCHAP((short) 28, "04015", "Correctie geregistreerd partnerschap", CategorieAdministratieveHandeling.CORRECTIE,
            (short) 2, "Correctie geregistreerd partnerschap", (short) 1),
    /** Verhuizing binnengemeentelijk. */
    VERHUIZING_BINNENGEMEENTELIJK((short) 29, "05004", "Verhuizing binnengemeentelijk", CategorieAdministratieveHandeling.ACTUALISERING, (short) 3,
            "Verhuizing binnengemeentelijk", (short) 1),
    /** Verhuizing intergemeentelijk. */
    VERHUIZING_INTERGEMEENTELIJK((short) 30, "05005", "Verhuizing intergemeentelijk", CategorieAdministratieveHandeling.ACTUALISERING, (short) 3,
            "Verhuizing intergemeentelijk", (short) 1),
    /** Wijziging adres infrastructureel. */
    WIJZIGING_ADRES_INFRASTRUCTUREEL((short) 31, "05006", "Wijziging adres infrastructureel", CategorieAdministratieveHandeling.ACTUALISERING, (short) 3,
            "Wijziging adres infrastructureel", (short) 1),
    /** Verhuizing naar buitenland. */
    VERHUIZING_NAAR_BUITENLAND((short) 32, "05007", "Verhuizing naar buitenland", CategorieAdministratieveHandeling.ACTUALISERING, (short) 3,
            "Verhuizing naar buitenland", (short) 1),
    /** Correctie adres. */
    CORRECTIE_ADRES((short) 33, "05008", "Correctie adres", CategorieAdministratieveHandeling.CORRECTIE, (short) 3, "Correctie adres", (short) 1),
    /** Correctie migratie. */
    CORRECTIE_MIGRATIE((short) 34, "05009", "Correctie migratie", CategorieAdministratieveHandeling.CORRECTIE, (short) 3, "Correctie migratie", (short) 1),
    /** Overlijden in Nederland. */
    OVERLIJDEN_IN_NEDERLAND((short) 35, "09001", "Overlijden in Nederland", CategorieAdministratieveHandeling.ACTUALISERING, (short) 4,
            "Overlijden in Nederland", (short) 1),
    /** Overlijden in buitenland. */
    OVERLIJDEN_IN_BUITENLAND((short) 36, "09002", "Overlijden in buitenland", CategorieAdministratieveHandeling.ACTUALISERING, (short) 4,
            "Overlijden in buitenland", (short) 1),
    /** GBA - Initiele vulling. */
    GBA_INITIELE_VULLING((short) 37, "99999", "GBA - Initiele vulling", CategorieAdministratieveHandeling.GBA_INITIELE_VULLING, (short) 16,
            "GBA - Initiele vulling", (short) 3),
    /** GBA - Bijhouding actueel. */
    GBA_BIJHOUDING_ACTUEEL((short) 38, "99998", "GBA - Bijhouding actueel", CategorieAdministratieveHandeling.GBA_SYNCHRONISATIE, (short) 16,
            "GBA - Bijhouding actueel", (short) 3),
    /** Vestiging niet-ingeschrevene. */
    VESTIGING_NIET_INGESCHREVENE((short) 39, "05001", "Vestiging niet-ingeschrevene", CategorieAdministratieveHandeling.ACTUALISERING, (short) 3,
            "Vestiging niet-ingeschrevene", (short) 1),
    /** Wijziging verblijfsrecht. */
    WIJZIGING_VERBLIJFSRECHT((short) 40, "05010", "Wijziging verblijfsrecht", CategorieAdministratieveHandeling.ACTUALISERING, (short) 3,
            "Wijziging verblijfsrecht", (short) 1),
    /** Constatering verblijf kind. */
    CONSTATERING_VERBLIJF_KIND((short) 41, "05002", "Constatering verblijf kind", CategorieAdministratieveHandeling.ACTUALISERING, (short) 3,
            "Constatering verblijf kind", (short) 1),
    /** Vestiging niet-ingezetene. */
    VESTIGING_NIET_INGEZETENE((short) 42, "05003", "Vestiging niet-ingezetene", CategorieAdministratieveHandeling.CORRECTIE, (short) 3,
            "Vestiging niet-ingezetene", (short) 1),
    /** Verkrijging Nederlandse nationaliteit. */
    VERKRIJGING_NEDERLANDSE_NATIONALITEIT((short) 43, "06001", "Verkrijging Nederlandse nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 6, "Verkrijging Nederlandse nationaliteit", (short) 1),
    /** Ongedaanmaking huwelijk. */
    ONGEDAANMAKING_HUWELIJK((short) 44, "04012", "Ongedaanmaking huwelijk", CategorieAdministratieveHandeling.CORRECTIE, (short) 2,
            "Ongedaanmaking huwelijk", (short) 1),
    /** Ongedaanmaking geregistreerd partnerschap. */
    ONGEDAANMAKING_GEREGISTREERD_PARTNERSCHAP((short) 45, "04013", "Ongedaanmaking geregistreerd partnerschap",
            CategorieAdministratieveHandeling.CORRECTIE, (short) 2, "Ongedaanmaking geregistreerd partnerschap", (short) 1),
    /** Aangaan geregistreerd partnerschap in buitenland. */
    AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND((short) 46, "04007", "Aangaan geregistreerd partnerschap in buitenland",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 2, "Aangaan geregistreerd partnerschap in buitenland", (short) 1),
    /** Beeindiging geregistreerd partnerschap in buitenland. */
    BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND((short) 47, "04008", "Beeindiging geregistreerd partnerschap in buitenland",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 2, "Beeindiging geregistreerd partnerschap in buitenland", (short) 1),
    /** Plaatsing afnemerindicatie. */
    PLAATSING_AFNEMERINDICATIE((short) 48, "31001", "Plaatsing afnemerindicatie", CategorieAdministratieveHandeling.ACTUALISERING, (short) 8,
            "Plaatsing afnemerindicatie", (short) 2),
    /** Verwijdering afnemerindicatie. */
    VERWIJDERING_AFNEMERINDICATIE((short) 49, "31002", "Verwijdering afnemerindicatie", CategorieAdministratieveHandeling.ACTUALISERING, (short) 8,
            "Verwijdering afnemerindicatie", (short) 2),
    /** Synchronisatie persoon. */
    SYNCHRONISATIE_PERSOON((short) 50, "31003", "Synchronisatie persoon", CategorieAdministratieveHandeling.SYNCHRONISATIE, (short) 8,
            "Synchronisatie persoon", (short) 2),
    /** Ongedaanmaking overlijden. */
    ONGEDAANMAKING_OVERLIJDEN((short) 51, "09003", "Ongedaanmaking overlijden", CategorieAdministratieveHandeling.CORRECTIE, (short) 4,
            "Ongedaanmaking overlijden", (short) 1),
    /** Correctie overlijden. */
    CORRECTIE_OVERLIJDEN((short) 52, "09004", "Correctie overlijden", CategorieAdministratieveHandeling.CORRECTIE, (short) 4, "Correctie overlijden",
            (short) 1),
    /** Verlies Nederlandse nationaliteit. */
    VERLIES_NEDERLANDSE_NATIONALITEIT((short) 53, "06002", "Verlies Nederlandse nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 6, "Verlies Nederlandse nationaliteit", (short) 1),
    /** Verkrijging vreemde nationaliteit. */
    VERKRIJGING_VREEMDE_NATIONALITEIT((short) 54, "06003", "Verkrijging vreemde nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 6, "Verkrijging vreemde nationaliteit", (short) 1),
    /** Beeindiging vreemde nationaliteit. */
    BEEINDIGING_VREEMDE_NATIONALITEIT((short) 55, "06004", "Beeindiging vreemde nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 6, "Beeindiging vreemde nationaliteit", (short) 1),
    /** Wijziging indicatie nationaliteit. */
    WIJZIGING_INDICATIE_NATIONALITEIT((short) 56, "06005", "Wijziging indicatie nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 6, "Wijziging indicatie nationaliteit", (short) 1),
    /** Wijziging voornaam. */
    WIJZIGING_VOORNAAM((short) 57, "02002", "Wijziging voornaam", CategorieAdministratieveHandeling.ACTUALISERING, (short) 7, "Wijziging voornaam",
            (short) 1),
    /** Wijziging geslachtsaanduiding. */
    WIJZIGING_GESLACHTSAANDUIDING((short) 58, "02003", "Wijziging geslachtsaanduiding", CategorieAdministratieveHandeling.ACTUALISERING, (short) 7,
            "Wijziging geslachtsaanduiding", (short) 1),
    /** Wijziging naamgebruik. */
    WIJZIGING_NAAMGEBRUIK((short) 59, "02004", "Wijziging naamgebruik", CategorieAdministratieveHandeling.ACTUALISERING, (short) 7,
            "Wijziging naamgebruik", (short) 1),
    /** Correctie naamsgegevens. */
    CORRECTIE_NAAMSGEGEVENS((short) 60, "02005", "Correctie naamsgegevens", CategorieAdministratieveHandeling.CORRECTIE, (short) 7,
            "Correctie naamsgegevens", (short) 1),
    /** Correctie geslachtsaanduiding. */
    CORRECTIE_GESLACHTSAANDUIDING((short) 61, "02006", "Correctie geslachtsaanduiding", CategorieAdministratieveHandeling.CORRECTIE, (short) 7,
            "Correctie geslachtsaanduiding", (short) 1),
    /** Correctie naamgebruik. */
    CORRECTIE_NAAMGEBRUIK((short) 62, "02007", "Correctie naamgebruik", CategorieAdministratieveHandeling.CORRECTIE, (short) 7, "Correctie naamgebruik",
            (short) 1),
    /** Wijziging bijzondere verblijfsrechtelijke positie. */
    WIJZIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE((short) 63, "05011", "Wijziging bijzondere verblijfsrechtelijke positie",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 3, "Wijziging bijzondere verblijfsrechtelijke positie", (short) 1),
    /** Wijziging verstrekkingsbeperking. */
    WIJZIGING_VERSTREKKINGSBEPERKING((short) 64, "03001", "Wijziging verstrekkingsbeperking", CategorieAdministratieveHandeling.ACTUALISERING, (short) 9,
            "Wijziging verstrekkingsbeperking", (short) 1),
    /** Wijziging gezag. */
    WIJZIGING_GEZAG((short) 65, "03002", "Wijziging gezag", CategorieAdministratieveHandeling.ACTUALISERING, (short) 9, "Wijziging gezag", (short) 1),
    /** Wijziging curatele. */
    WIJZIGING_CURATELE((short) 66, "03004", "Wijziging curatele", CategorieAdministratieveHandeling.ACTUALISERING, (short) 9, "Wijziging curatele",
            (short) 1),
    /** Verkrijging reisdocument. */
    VERKRIJGING_REISDOCUMENT((short) 67, "07001", "Verkrijging reisdocument", CategorieAdministratieveHandeling.ACTUALISERING, (short) 10,
            "Verkrijging reisdocument", (short) 1),
    /** Onttrekking reisdocument. */
    ONTTREKKING_REISDOCUMENT((short) 68, "07002", "Onttrekking reisdocument", CategorieAdministratieveHandeling.ACTUALISERING, (short) 10,
            "Onttrekking reisdocument", (short) 1),
    /** Signalering reisdocument. */
    SIGNALERING_REISDOCUMENT((short) 69, "07003", "Signalering reisdocument", CategorieAdministratieveHandeling.ACTUALISERING, (short) 10,
            "Signalering reisdocument", (short) 1),
    /** Correctie reisdocument. */
    CORRECTIE_REISDOCUMENT((short) 70, "07004", "Correctie reisdocument", CategorieAdministratieveHandeling.CORRECTIE, (short) 10,
            "Correctie reisdocument", (short) 1),
    /** Wijziging uitsluiting kiesrecht. */
    WIJZIGING_UITSLUITING_KIESRECHT((short) 71, "13001", "Wijziging uitsluiting kiesrecht", CategorieAdministratieveHandeling.ACTUALISERING, (short) 11,
            "Wijziging uitsluiting kiesrecht", (short) 1),
    /** Wijziging deelname EU-verkiezingen. */
    WIJZIGING_DEELNAME_EU_VERKIEZINGEN((short) 72, "13002", "Wijziging deelname EU-verkiezingen", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 11, "Wijziging deelname EU-verkiezingen", (short) 1),
    /** Correctie kiesrecht. */
    CORRECTIE_KIESRECHT((short) 73, "13003", "Correctie kiesrecht", CategorieAdministratieveHandeling.CORRECTIE, (short) 11, "Correctie kiesrecht",
            (short) 1),
    /** Naamskeuze voor geboorte (vervallen). */
    NAAMSKEUZE_VOOR_GEBOORTE_VERVALLEN((short) 74, "01017", "Naamskeuze voor geboorte (vervallen)", null, (short) 1,
            "Naamskeuze voor geboorte (vervallen)", (short) 1),
    /** Correctie gezag. */
    CORRECTIE_GEZAG((short) 75, "03003", "Correctie gezag", CategorieAdministratieveHandeling.CORRECTIE, (short) 9, "Correctie gezag", (short) 1),
    /** Correctie curatele. */
    CORRECTIE_CURATELE((short) 76, "03005", "Correctie curatele", CategorieAdministratieveHandeling.CORRECTIE, (short) 9, "Correctie curatele", (short) 1),
    /** Aanvang onderzoek. */
    AANVANG_ONDERZOEK((short) 77, "11001", "Aanvang onderzoek", CategorieAdministratieveHandeling.ACTUALISERING, (short) 14, "Aanvang onderzoek",
            (short) 1),
    /** Voeging partij bij onderzoek (vervallen). */
    VOEGING_PARTIJ_BIJ_ONDERZOEK_VERVALLEN((short) 78, "11010", "Voeging partij bij onderzoek (vervallen)", null, (short) 1,
            "Voeging partij bij onderzoek (vervallen)", (short) 1),
    /** Ontvoeging partij uit onderzoek (vervallen). */
    ONTVOEGING_PARTIJ_UIT_ONDERZOEK_VERVALLEN((short) 79, "11011", "Ontvoeging partij uit onderzoek (vervallen)", null, (short) 1,
            "Ontvoeging partij uit onderzoek (vervallen)", (short) 1),
    /** Toevoeging terugmelding aan onderzoek (vervallen). */
    TOEVOEGING_TERUGMELDING_AAN_ONDERZOEK_VERVALLEN((short) 80, "11012", "Toevoeging terugmelding aan onderzoek (vervallen)", null, (short) 1,
            "Toevoeging terugmelding aan onderzoek (vervallen)", (short) 1),
    /** Verwijdering terugmelding van onderzoek (vervallen). */
    VERWIJDERING_TERUGMELDING_VAN_ONDERZOEK_VERVALLEN((short) 81, "11013", "Verwijdering terugmelding van onderzoek (vervallen)", null, (short) 1,
            "Verwijdering terugmelding van onderzoek (vervallen)", (short) 1),
    /** Registratie tussenresultaat onderzoek. */
    REGISTRATIE_TUSSENRESULTAAT_ONDERZOEK((short) 82, "11002", "Registratie tussenresultaat onderzoek", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 14, "Registratie tussenresultaat onderzoek", (short) 1),
    /** Beeindiging onderzoek. */
    BEEINDIGING_ONDERZOEK((short) 83, "11003", "Beeindiging onderzoek", CategorieAdministratieveHandeling.ACTUALISERING, (short) 14,
            "Beeindiging onderzoek", (short) 1),
    /** Wijziging onderzoek. */
    WIJZIGING_ONDERZOEK((short) 84, "11004", "Wijziging onderzoek", CategorieAdministratieveHandeling.CORRECTIE, (short) 14, "Wijziging onderzoek",
            (short) 1),
    /** Selectie met plaatsen afnemerindicatie. */
    SELECTIE_MET_PLAATSEN_AFNEMERINDICATIE((short) 85, "31011", "Selectie met plaatsen afnemerindicatie", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 8, "Selectie met plaatsen afnemerindicatie", (short) 2),
    /** Selectie met verwijderen afnemerindicatie. */
    SELECTIE_MET_VERWIJDEREN_AFNEMERINDICATIE((short) 86, "31012", "Selectie met verwijderen afnemerindicatie",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 8, "Selectie met verwijderen afnemerindicatie", (short) 2),
    /** Attendering met plaatsen afnemerindicatie. */
    ATTENDERING_MET_PLAATSEN_AFNEMERINDICATIE((short) 87, "31010", "Attendering met plaatsen afnemerindicatie",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 19, "Attendering met plaatsen afnemerindicatie", (short) 2),
    /** Synchronisatie stamgegeven. */
    SYNCHRONISATIE_STAMGEGEVEN((short) 88, "31004", "Synchronisatie stamgegeven", CategorieAdministratieveHandeling.SYNCHRONISATIE, (short) 8,
            "Synchronisatie stamgegeven", (short) 2),
    /** Geef details persoon. */
    GEEF_DETAILS_PERSOON((short) 89, "30003", "Geef details persoon", null, (short) 12, "Geef details persoon", (short) 2),
    /** GBA - Bijhouding overig. */
    GBA_BIJHOUDING_OVERIG((short) 90, "99997", "GBA - Bijhouding overig", CategorieAdministratieveHandeling.GBA_SYNCHRONISATIE, (short) 16,
            "GBA - Bijhouding overig", (short) 3),
    /** GBA - Infrastructurele wijziging. */
    GBA_INFRASTRUCTURELE_WIJZIGING((short) 91, "99996", "GBA - Infrastructurele wijziging", CategorieAdministratieveHandeling.GBA_SYNCHRONISATIE,
            (short) 16, "GBA - Infrastructurele wijziging", (short) 3),
    /** GBA - A-nummer wijziging. */
    GBA_A_NUMMER_WIJZIGING((short) 92, "99995", "GBA - A-nummer wijziging", CategorieAdministratieveHandeling.GBA_SYNCHRONISATIE, (short) 16,
            "GBA - A-nummer wijziging", (short) 3),
    /** GBA - Afvoeren PL. */
    GBA_AFVOEREN_PL((short) 93, "99994", "GBA - Afvoeren PL", CategorieAdministratieveHandeling.GBA_SYNCHRONISATIE, (short) 16, "GBA - Afvoeren PL",
            (short) 3),
    /** GBA - Geboorte. */
    GBA_GEBOORTE((short) 94, "99901", "GBA - Geboorte", CategorieAdministratieveHandeling.ACTUALISERING, (short) 16, "GBA - Geboorte", (short) 3),
    /** GBA - Registratie ouderschap. */
    GBA_REGISTRATIE_OUDERSCHAP((short) 95, "99902", "GBA - Registratie ouderschap", CategorieAdministratieveHandeling.ACTUALISERING, (short) 16,
            "GBA - Registratie ouderschap", (short) 3),
    /** GBA - Ontkenning ouderschap. */
    GBA_ONTKENNING_OUDERSCHAP((short) 96, "99903", "GBA - Ontkenning ouderschap", CategorieAdministratieveHandeling.ACTUALISERING, (short) 16,
            "GBA - Ontkenning ouderschap", (short) 3),
    /** GBA - Sluiting huwelijk geregistreerd partnerschap. */
    GBA_SLUITING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP((short) 97, "99904", "GBA - Sluiting huwelijk geregistreerd partnerschap",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 16, "GBA - Sluiting huwelijk geregistreerd partnerschap", (short) 3),
    /** GBA - Ontbinding huwelijk geregistreerd partnerschap. */
    GBA_ONTBINDING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP((short) 98, "99905", "GBA - Ontbinding huwelijk geregistreerd partnerschap",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 16, "GBA - Ontbinding huwelijk geregistreerd partnerschap", (short) 3),
    /** GBA - Omzetting huwelijk geregistreerd partnerschap. */
    GBA_OMZETTING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP((short) 99, "99906", "GBA - Omzetting huwelijk geregistreerd partnerschap",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 16, "GBA - Omzetting huwelijk geregistreerd partnerschap", (short) 3),
    /** GBA - Overlijden. */
    GBA_OVERLIJDEN((short) 100, "99907", "GBA - Overlijden", CategorieAdministratieveHandeling.ACTUALISERING, (short) 16, "GBA - Overlijden", (short) 3),
    /** GBA - Wijziging naam geslacht. */
    GBA_WIJZIGING_NAAM_GESLACHT((short) 101, "99908", "GBA - Wijziging naam geslacht", CategorieAdministratieveHandeling.ACTUALISERING, (short) 16,
            "GBA - Wijziging naam geslacht", (short) 3),
    /** Verwijdering partnergegevens na geslachtswijziging. */
    VERWIJDERING_PARTNERGEGEVENS_NA_GESLACHTSWIJZIGING((short) 102, "03006", "Verwijdering partnergegevens na geslachtswijziging",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 7, "Verwijdering partnergegevens na geslachtswijziging", (short) 1),
    /** Verwijdering kindgegevens na geslachtswijziging. */
    VERWIJDERING_KINDGEGEVENS_NA_GESLACHTSWIJZIGING((short) 103, "03007", "Verwijdering kindgegevens na geslachtswijziging",
            CategorieAdministratieveHandeling.ACTUALISERING, (short) 1, "Verwijdering kindgegevens na geslachtswijziging", (short) 1),
    /** Verwijdering kindgegevens na adoptie. */
    VERWIJDERING_KINDGEGEVENS_NA_ADOPTIE((short) 104, "03008", "Verwijdering kindgegevens na adoptie", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 1, "Verwijdering kindgegevens na adoptie", (short) 1),
    /** Verwijdering oudergegevens na adoptie. */
    VERWIJDERING_OUDERGEGEVENS_NA_ADOPTIE((short) 105, "03009", "Verwijdering oudergegevens na adoptie", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 1, "Verwijdering oudergegevens na adoptie", (short) 1),
    /** Correctie nationaliteit. */
    CORRECTIE_NATIONALITEIT((short) 106, "06006", "Correctie nationaliteit", CategorieAdministratieveHandeling.CORRECTIE, (short) 6,
            "Correctie nationaliteit", (short) 1),
    /** Wijziging identificatienummers. */
    WIJZIGING_IDENTIFICATIENUMMERS((short) 107, "10001", "Wijziging identificatienummers", CategorieAdministratieveHandeling.ACTUALISERING, (short) 17,
            "Wijziging identificatienummers", (short) 1),
    /** Verwijdering persoon. */
    VERWIJDERING_PERSOON((short) 108, "10002", "Verwijdering persoon", CategorieAdministratieveHandeling.CORRECTIE, (short) 17, "Verwijdering persoon",
            (short) 1),
    /** Wijziging verantwoording. */
    WIJZIGING_VERANTWOORDING((short) 109, "10003", "Wijziging verantwoording", CategorieAdministratieveHandeling.ACTUALISERING, (short) 17,
            "Wijziging verantwoording", (short) 1),
    /** Correctie dubbele inschrijving. */
    CORRECTIE_DUBBELE_INSCHRIJVING((short) 110, "10004", "Correctie dubbele inschrijving", CategorieAdministratieveHandeling.CORRECTIE, (short) 17,
            "Correctie dubbele inschrijving", (short) 1),
    /** Correctie hinkende relatie. */
    CORRECTIE_HINKENDE_RELATIE((short) 111, "10005", "Correctie hinkende relatie", CategorieAdministratieveHandeling.CORRECTIE, (short) 17,
            "Correctie hinkende relatie", (short) 1),
    /** Correctie overige persoonsgegevens. */
    CORRECTIE_OVERIGE_PERSOONSGEGEVENS((short) 112, "10006", "Correctie overige persoonsgegevens", CategorieAdministratieveHandeling.CORRECTIE,
            (short) 17, "Correctie overige persoonsgegevens", (short) 1),
    /** Wijziging gemeentenaam. */
    WIJZIGING_GEMEENTENAAM((short) 113, "10007", "Wijziging gemeentenaam", CategorieAdministratieveHandeling.ACTUALISERING, (short) 17,
            "Wijziging gemeentenaam", (short) 1),
    /** Wijziging postcode woonplaatsnaam. */
    WIJZIGING_POSTCODE_WOONPLAATSNAAM((short) 114, "10008", "Wijziging postcode woonplaatsnaam", CategorieAdministratieveHandeling.ACTUALISERING,
            (short) 17, "Wijziging postcode woonplaatsnaam", (short) 1),
    /** Wijziging naam openbare ruimte. */
    WIJZIGING_NAAM_OPENBARE_RUIMTE((short) 115, "10009", "Wijziging naam openbare ruimte", CategorieAdministratieveHandeling.ACTUALISERING, (short) 17,
            "Wijziging naam openbare ruimte", (short) 1),
    /** Attendering. */
    ATTENDERING((short) 116, "31009", "Attendering", CategorieAdministratieveHandeling.SYNCHRONISATIE, (short) 19, "Attendering", (short) 2),
    /** Mutatielevering op basis van afnemerindicatie. */
    MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE((short) 117, "31005", "Mutatielevering op basis van afnemerindicatie",
            CategorieAdministratieveHandeling.SYNCHRONISATIE, (short) 8, "Mutatielevering op basis van afnemerindicatie", (short) 2),
    /** Mutatielevering op basis van doelbinding. */
    MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING((short) 118, "31006", "Mutatielevering op basis van doelbinding",
            CategorieAdministratieveHandeling.SYNCHRONISATIE, (short) 8, "Mutatielevering op basis van doelbinding", (short) 2),
    /** Mutatielevering stamgegeven. */
    MUTATIELEVERING_STAMGEGEVEN((short) 119, "31007", "Mutatielevering stamgegeven", CategorieAdministratieveHandeling.SYNCHRONISATIE, (short) 8,
            "Mutatielevering stamgegeven", (short) 2),
    /** Melding wijziging medebewoning. */
    MELDING_WIJZIGING_MEDEBEWONING((short) 120, "31008", "Melding wijziging medebewoning", CategorieAdministratieveHandeling.SYNCHRONISATIE, (short) 8,
            "Melding wijziging medebewoning", (short) 2),
    /** Notificatie bijhoudingsplan. */
    NOTIFICATIE_BIJHOUDINGSPLAN((short) 121, "15001", "Notificatie bijhoudingsplan", null, (short) 18, "Notificatie bijhoudingsplan", (short) 1),
    /** Notificatie BRP bijhouding. */
    NOTIFICATIE_BRP_BIJHOUDING((short) 122, "17001", "Notificatie BRP bijhouding", null, (short) 20, "Notificatie BRP bijhoudingmod", (short) 1),
    /** Zoek persoon. */
    ZOEK_PERSOON((short) 123, "30001", "Zoek persoon", null, (short) 12, "Zoek persoon", (short) 2),
    /** Zoek persoon op adresgegevens. */
    ZOEK_PERSOON_OP_ADRESGEGEVENS((short) 124, "30002", "Zoek persoon op adresgegevens", null, (short) 12, "Zoek persoon op adresgegevens", (short) 2),
    /** Geef medebewoners. */
    GEEF_MEDEBEWONERS((short) 125, "30004", "Geef medebewoners", null, (short) 12, "Geef medebewoners", (short) 2),
    /** Bepaal kandidaat ouder. */
    BEPAAL_KANDIDAAT_OUDER((short) 126, "30050", "Bepaal kandidaat ouder", null, (short) 12, "Bepaal kandidaat ouder", (short) 2),
    /** Geef relatiegegevens GBA. */
    GEEF_RELATIEGEGEVENS_GBA((short) 127, "30051", "Geef relatiegegevens GBA", null, (short) 12, "Geef relatiegegevens GBA", (short) 2),
    /** Selectie. */
    SELECTIE((short) 128, "32001", "Selectie", null, (short) 15, "Selectie", (short) 2),
    /** Verkiezingsselectie. */
    VERKIEZINGSSELECTIE((short) 129, "32002", "Verkiezingsselectie", null, (short) 15, "Verkiezingsselectie", (short) 2),
    /** Notificatie BRP levering. */
    NOTIFICATIE_BRP_LEVERING((short) 130, "33001", "Notificatie BRP levering", null, (short) 20, "Notificatie BRP levering", (short) 2);

    private static final EnumParser<SoortAdministratieveHandeling> PARSER = new EnumParser<>(SoortAdministratieveHandeling.class);

    private final short id;
    private final String code;
    private final String naam;
    private final CategorieAdministratieveHandeling categorie;
    private final short moduleId;
    private final String alias;
    private final short koppelvlakId;

    /**
     * (ID, Code, Naam, CategorieAdmHnd, Module).
     *
     * @param id
     *            id in de database
     * @param code
     *            code
     * @param naam
     *            naam
     * @param categorie
     *            categorie AH
     * @param moduleId
     *            module id
     * @param alias
     *            alias
     * @param koppelvlakId
     *            koppelvlak id
     */
    SoortAdministratieveHandeling(
        final short id,
        final String code,
        final String naam,
        final CategorieAdministratieveHandeling categorie,
        final short moduleId,
        final String alias,
        final short koppelvlakId)
    {
        this.id = id;
        this.code = code;
        this.naam = naam;
        this.categorie = categorie;
        this.moduleId = moduleId;
        this.alias = alias;
        this.koppelvlakId = koppelvlakId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
     */
    @Override
    public short getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id van soort AH
     * @return de enumeratie waarde die bij het id hoort
     */
    public static SoortAdministratieveHandeling parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code van soort AH
     * @return de enumeratie waarde die bij de code hoort
     */
    public static SoortAdministratieveHandeling parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return true;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    public CategorieAdministratieveHandeling getCategorie() {
        return categorie;
    }

    /**
     * Geef de waarde van module.
     *
     * @return module
     */
    public BurgerzakenModule getModule() {
        return BurgerzakenModule.parseId(moduleId);
    }

    /**
     * Geef de waarde van alias.
     *
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Geef de waarde van koppelvlak.
     *
     * @return koppelvlak
     */
    public Koppelvlak getKoppelvlak() {
        return Koppelvlak.parseId(koppelvlakId);
    }
}
