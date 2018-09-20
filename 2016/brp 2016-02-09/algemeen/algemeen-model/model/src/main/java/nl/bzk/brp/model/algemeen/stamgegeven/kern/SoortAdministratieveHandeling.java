/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * De mogelijke soort administratieve handeling.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortAdministratieveHandeling implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", null, null, null, null),
    /**
     * Geboorte in Nederland.
     */
    GEBOORTE_IN_NEDERLAND("01001", "Geboorte in Nederland", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Geboorte in Nederland"),
    /**
     * Erkenning voor geboorte (vervallen).
     */
    ERKENNING_VOOR_GEBOORTE_VERVALLEN("01016", "Erkenning voor geboorte (vervallen)", null, Koppelvlak.BIJHOUDING, null,
            "Erkenning voor geboorte (vervallen)"),
    /**
     * Geboorte in Nederland met erkenning.
     */
    GEBOORTE_IN_NEDERLAND_MET_ERKENNING("01002", "Geboorte in Nederland met erkenning", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.AFSTAMMING, "Geboorte in Nederland met erkenning"),
    /**
     * Erkenning na geboorte.
     */
    ERKENNING_NA_GEBOORTE("01003", "Erkenning na geboorte", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Erkenning na geboorte"),
    /**
     * Vernietiging erkenning.
     */
    VERNIETIGING_ERKENNING("01004", "Vernietiging erkenning", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Vernietiging erkenning"),
    /**
     * Adoptie ingezetene.
     */
    ADOPTIE_INGEZETENE("01007", "Adoptie ingezetene", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Adoptie ingezetene"),
    /**
     * Omzetting adoptie.
     */
    OMZETTING_ADOPTIE("01008", "Omzetting adoptie", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.AFSTAMMING,
            "Omzetting adoptie"),
    /**
     * Herroeping adoptie.
     */
    HERROEPING_ADOPTIE("01009", "Herroeping adoptie", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Herroeping adoptie"),
    /**
     * Vaststelling ouderschap.
     */
    VASTSTELLING_OUDERSCHAP("01005", "Vaststelling ouderschap", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Vaststelling ouderschap"),
    /**
     * Toevoeging geboorteakte.
     */
    TOEVOEGING_GEBOORTEAKTE("01010", "Toevoeging geboorteakte", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Toevoeging geboorteakte"),
    /**
     * Verbetering geboorteakte.
     */
    VERBETERING_GEBOORTEAKTE("01011", "Verbetering geboorteakte", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Verbetering geboorteakte"),
    /**
     * Wijziging kindgegevens.
     */
    WIJZIGING_KINDGEGEVENS("01014", "Wijziging kindgegevens", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Wijziging kindgegevens"),
    /**
     * Ontkenning ouderschap.
     */
    ONTKENNING_OUDERSCHAP("01006", "Ontkenning ouderschap", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Ontkenning ouderschap"),
    /**
     * Inroeping van staat.
     */
    INROEPING_VAN_STAAT("01013", "Inroeping van staat", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Inroeping van staat"),
    /**
     * Betwisting van staat.
     */
    BETWISTING_VAN_STAAT("01012", "Betwisting van staat", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Betwisting van staat"),
    /**
     * Correctie afstamming.
     */
    CORRECTIE_AFSTAMMING("01015", "Correctie afstamming", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.AFSTAMMING, "Correctie afstamming"),
    /**
     * Wijziging geslachtsnaam.
     */
    WIJZIGING_GESLACHTSNAAM("02001", "Wijziging geslachtsnaam", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.NAAM_GESLACHT, "Wijziging geslachtsnaam"),
    /**
     * Voltrekking huwelijk in Nederland.
     */
    VOLTREKKING_HUWELIJK_IN_NEDERLAND("04001", "Voltrekking huwelijk in Nederland", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Voltrekking huwelijk in Nederland"),
    /**
     * Ontbinding huwelijk in Nederland.
     */
    ONTBINDING_HUWELIJK_IN_NEDERLAND("04002", "Ontbinding huwelijk in Nederland", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Ontbinding huwelijk in Nederland"),
    /**
     * Aangaan geregistreerd partnerschap in Nederland.
     */
    AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND("04005", "Aangaan geregistreerd partnerschap in Nederland",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            "Aangaan geregistreerd partnerschap in Nederland"),
    /**
     * Beeindiging geregistreerd partnerschap in Nederland.
     */
    BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND("04006", "Beeindiging geregistreerd partnerschap in Nederland",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            "Beeindiging geregistreerd partnerschap in Nederland"),
    /**
     * Voltrekking huwelijk in buitenland.
     */
    VOLTREKKING_HUWELIJK_IN_BUITENLAND("04003", "Voltrekking huwelijk in buitenland", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Voltrekking huwelijk in buitenland"),
    /**
     * Ontbinding huwelijk in buitenland.
     */
    ONTBINDING_HUWELIJK_IN_BUITENLAND("04004", "Ontbinding huwelijk in buitenland", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Ontbinding huwelijk in buitenland"),
    /**
     * Nietigverklaring huwelijk.
     */
    NIETIGVERKLARING_HUWELIJK("04010", "Nietigverklaring huwelijk", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Nietigverklaring huwelijk"),
    /**
     * Nietigverklaring geregistreerd partnerschap.
     */
    NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP("04011", "Nietigverklaring geregistreerd partnerschap", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Nietigverklaring geregistreerd partnerschap"),
    /**
     * Omzetting geregistreerd partnerschap in huwelijk.
     */
    OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK("04009", "Omzetting geregistreerd partnerschap in huwelijk",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            "Omzetting geregistreerd partnerschap in huwelijk"),
    /**
     * Correctie huwelijk.
     */
    CORRECTIE_HUWELIJK("04014", "Correctie huwelijk", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Correctie huwelijk"),
    /**
     * Correctie geregistreerd partnerschap.
     */
    CORRECTIE_GEREGISTREERD_PARTNERSCHAP("04015", "Correctie geregistreerd partnerschap", CategorieAdministratieveHandeling.CORRECTIE,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Correctie geregistreerd partnerschap"),
    /**
     * Verhuizing binnengemeentelijk.
     */
    VERHUIZING_BINNENGEMEENTELIJK("05004", "Verhuizing binnengemeentelijk", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Verhuizing binnengemeentelijk"),
    /**
     * Verhuizing intergemeentelijk.
     */
    VERHUIZING_INTERGEMEENTELIJK("05005", "Verhuizing intergemeentelijk", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Verhuizing intergemeentelijk"),
    /**
     * Wijziging adres infrastructureel.
     */
    WIJZIGING_ADRES_INFRASTRUCTUREEL("05006", "Wijziging adres infrastructureel", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Wijziging adres infrastructureel"),
    /**
     * Verhuizing naar buitenland.
     */
    VERHUIZING_NAAR_BUITENLAND("05007", "Verhuizing naar buitenland", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Verhuizing naar buitenland"),
    /**
     * Correctie adres.
     */
    CORRECTIE_ADRES("05008", "Correctie adres", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING, BurgerzakenModule.VERBLIJF_EN_ADRES,
            "Correctie adres"),
    /**
     * Correctie migratie.
     */
    CORRECTIE_MIGRATIE("05009", "Correctie migratie", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Correctie migratie"),
    /**
     * Overlijden in Nederland.
     */
    OVERLIJDEN_IN_NEDERLAND("09001", "Overlijden in Nederland", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.OVERLIJDEN, "Overlijden in Nederland"),
    /**
     * Overlijden in buitenland.
     */
    OVERLIJDEN_IN_BUITENLAND("09002", "Overlijden in buitenland", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.OVERLIJDEN, "Overlijden in buitenland"),
    /**
     * GBA - Initiele vulling.
     */
    G_B_A_INITIELE_VULLING("99999", "GBA - Initiele vulling", CategorieAdministratieveHandeling.G_B_A_INITIELE_VULLING, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Initiele vulling"),
    /**
     * GBA - Bijhouding actueel.
     */
    G_B_A_BIJHOUDING_ACTUEEL("99998", "GBA - Bijhouding actueel", CategorieAdministratieveHandeling.G_B_A_SYNCHRONISATIE, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Bijhouding actueel"),
    /**
     * Vestiging niet-ingeschrevene.
     */
    VESTIGING_NIET_INGESCHREVENE("05001", "Vestiging niet-ingeschrevene", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Vestiging niet-ingeschrevene"),
    /**
     * Wijziging verblijfsrecht.
     */
    WIJZIGING_VERBLIJFSRECHT("05010", "Wijziging verblijfsrecht", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Wijziging verblijfsrecht"),
    /**
     * Constatering verblijf kind.
     */
    CONSTATERING_VERBLIJF_KIND("05002", "Constatering verblijf kind", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Constatering verblijf kind"),
    /**
     * Vestiging niet-ingezetene.
     */
    VESTIGING_NIET_INGEZETENE("05003", "Vestiging niet-ingezetene", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERBLIJF_EN_ADRES, "Vestiging niet-ingezetene"),
    /**
     * Verkrijging Nederlandse nationaliteit.
     */
    VERKRIJGING_NEDERLANDSE_NATIONALITEIT("06001", "Verkrijging Nederlandse nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.NATIONALITEIT, "Verkrijging Nederlandse nationaliteit"),
    /**
     * Ongedaanmaking huwelijk.
     */
    ONGEDAANMAKING_HUWELIJK("04012", "Ongedaanmaking huwelijk", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Ongedaanmaking huwelijk"),
    /**
     * Ongedaanmaking geregistreerd partnerschap.
     */
    ONGEDAANMAKING_GEREGISTREERD_PARTNERSCHAP("04013", "Ongedaanmaking geregistreerd partnerschap", CategorieAdministratieveHandeling.CORRECTIE,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, "Ongedaanmaking geregistreerd partnerschap"),
    /**
     * Aangaan geregistreerd partnerschap in buitenland.
     */
    AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND("04007", "Aangaan geregistreerd partnerschap in buitenland",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            "Aangaan geregistreerd partnerschap in buitenland"),
    /**
     * Beeindiging geregistreerd partnerschap in buitenland.
     */
    BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND("04008", "Beeindiging geregistreerd partnerschap in buitenland",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            "Beeindiging geregistreerd partnerschap in buitenland"),
    /**
     * Plaatsing afnemerindicatie.
     */
    PLAATSING_AFNEMERINDICATIE("31001", "Plaatsing afnemerindicatie", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.LEVERING,
            BurgerzakenModule.SYNCHRONISATIE, "Plaatsing afnemerindicatie"),
    /**
     * Verwijdering afnemerindicatie.
     */
    VERWIJDERING_AFNEMERINDICATIE("31002", "Verwijdering afnemerindicatie", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.LEVERING,
            BurgerzakenModule.SYNCHRONISATIE, "Verwijdering afnemerindicatie"),
    /**
     * Synchronisatie persoon.
     */
    SYNCHRONISATIE_PERSOON("31003", "Synchronisatie persoon", CategorieAdministratieveHandeling.SYNCHRONISATIE, Koppelvlak.LEVERING,
            BurgerzakenModule.SYNCHRONISATIE, "Synchronisatie persoon"),
    /**
     * Ongedaanmaking overlijden.
     */
    ONGEDAANMAKING_OVERLIJDEN("09003", "Ongedaanmaking overlijden", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.OVERLIJDEN, "Ongedaanmaking overlijden"),
    /**
     * Correctie overlijden.
     */
    CORRECTIE_OVERLIJDEN("09004", "Correctie overlijden", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.OVERLIJDEN, "Correctie overlijden"),
    /**
     * Verlies Nederlandse nationaliteit.
     */
    VERLIES_NEDERLANDSE_NATIONALITEIT("06002", "Verlies Nederlandse nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.NATIONALITEIT, "Verlies Nederlandse nationaliteit"),
    /**
     * Verkrijging vreemde nationaliteit.
     */
    VERKRIJGING_VREEMDE_NATIONALITEIT("06003", "Verkrijging vreemde nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.NATIONALITEIT, "Verkrijging vreemde nationaliteit"),
    /**
     * Beeindiging vreemde nationaliteit.
     */
    BEEINDIGING_VREEMDE_NATIONALITEIT("06004", "Beeindiging vreemde nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.NATIONALITEIT, "Beeindiging vreemde nationaliteit"),
    /**
     * Wijziging indicatie nationaliteit.
     */
    WIJZIGING_INDICATIE_NATIONALITEIT("06005", "Wijziging indicatie nationaliteit", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.NATIONALITEIT, "Wijziging indicatie nationaliteit"),
    /**
     * Wijziging voornaam.
     */
    WIJZIGING_VOORNAAM("02002", "Wijziging voornaam", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.NAAM_GESLACHT, "Wijziging voornaam"),
    /**
     * Wijziging geslachtsaanduiding.
     */
    WIJZIGING_GESLACHTSAANDUIDING("02003", "Wijziging geslachtsaanduiding", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.NAAM_GESLACHT, "Wijziging geslachtsaanduiding"),
    /**
     * Wijziging naamgebruik.
     */
    WIJZIGING_NAAMGEBRUIK("02004", "Wijziging naamgebruik", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.NAAM_GESLACHT, "Wijziging naamgebruik"),
    /**
     * Correctie naamsgegevens.
     */
    CORRECTIE_NAAMSGEGEVENS("02005", "Correctie naamsgegevens", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.NAAM_GESLACHT, "Correctie naamsgegevens"),
    /**
     * Correctie geslachtsaanduiding.
     */
    CORRECTIE_GESLACHTSAANDUIDING("02006", "Correctie geslachtsaanduiding", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.NAAM_GESLACHT, "Correctie geslachtsaanduiding"),
    /**
     * Correctie naamgebruik.
     */
    CORRECTIE_NAAMGEBRUIK("02007", "Correctie naamgebruik", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.NAAM_GESLACHT, "Correctie naamgebruik"),
    /**
     * Wijziging bijzondere verblijfsrechtelijke positie.
     */
    WIJZIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE("05011", "Wijziging bijzondere verblijfsrechtelijke positie",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.VERBLIJF_EN_ADRES,
            "Wijziging bijzondere verblijfsrechtelijke positie"),
    /**
     * Wijziging verstrekkingsbeperking.
     */
    WIJZIGING_VERSTREKKINGSBEPERKING("03001", "Wijziging verstrekkingsbeperking", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, "Wijziging verstrekkingsbeperking"),
    /**
     * Wijziging gezag.
     */
    WIJZIGING_GEZAG("03002", "Wijziging gezag", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, "Wijziging gezag"),
    /**
     * Wijziging curatele.
     */
    WIJZIGING_CURATELE("03004", "Wijziging curatele", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, "Wijziging curatele"),
    /**
     * Verkrijging reisdocument.
     */
    VERKRIJGING_REISDOCUMENT("07001", "Verkrijging reisdocument", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.REISDOCUMENTEN, "Verkrijging reisdocument"),
    /**
     * Onttrekking reisdocument.
     */
    ONTTREKKING_REISDOCUMENT("07002", "Onttrekking reisdocument", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.REISDOCUMENTEN, "Onttrekking reisdocument"),
    /**
     * Signalering reisdocument.
     */
    SIGNALERING_REISDOCUMENT("07003", "Signalering reisdocument", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.REISDOCUMENTEN, "Signalering reisdocument"),
    /**
     * Correctie reisdocument.
     */
    CORRECTIE_REISDOCUMENT("07004", "Correctie reisdocument", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.REISDOCUMENTEN, "Correctie reisdocument"),
    /**
     * Wijziging uitsluiting kiesrecht.
     */
    WIJZIGING_UITSLUITING_KIESRECHT("13001", "Wijziging uitsluiting kiesrecht", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERKIEZINGEN, "Wijziging uitsluiting kiesrecht"),
    /**
     * Wijziging deelname EU-verkiezingen.
     */
    WIJZIGING_DEELNAME_E_U_VERKIEZINGEN("13002", "Wijziging deelname EU-verkiezingen", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.VERKIEZINGEN, "Wijziging deelname EU-verkiezingen"),
    /**
     * Correctie kiesrecht.
     */
    CORRECTIE_KIESRECHT("13003", "Correctie kiesrecht", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.VERKIEZINGEN, "Correctie kiesrecht"),
    /**
     * Naamskeuze voor geboorte (vervallen).
     */
    NAAMSKEUZE_VOOR_GEBOORTE_VERVALLEN("01017", "Naamskeuze voor geboorte (vervallen)", null, Koppelvlak.BIJHOUDING, null,
            "Naamskeuze voor geboorte (vervallen)"),
    /**
     * Correctie gezag.
     */
    CORRECTIE_GEZAG("03003", "Correctie gezag", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, "Correctie gezag"),
    /**
     * Correctie curatele.
     */
    CORRECTIE_CURATELE("03005", "Correctie curatele", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, "Correctie curatele"),
    /**
     * Aanvang onderzoek.
     */
    AANVANG_ONDERZOEK("11001", "Aanvang onderzoek", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.ONDERZOEK,
            "Aanvang onderzoek"),
    /**
     * Voeging partij bij onderzoek (vervallen).
     */
    VOEGING_PARTIJ_BIJ_ONDERZOEK_VERVALLEN("11010", "Voeging partij bij onderzoek (vervallen)", null, Koppelvlak.BIJHOUDING, null,
            "Voeging partij bij onderzoek (vervallen)"),
    /**
     * Ontvoeging partij uit onderzoek (vervallen).
     */
    ONTVOEGING_PARTIJ_UIT_ONDERZOEK_VERVALLEN("11011", "Ontvoeging partij uit onderzoek (vervallen)", null, Koppelvlak.BIJHOUDING, null,
            "Ontvoeging partij uit onderzoek (vervallen)"),
    /**
     * Toevoeging terugmelding aan onderzoek (vervallen).
     */
    TOEVOEGING_TERUGMELDING_AAN_ONDERZOEK_VERVALLEN("11012", "Toevoeging terugmelding aan onderzoek (vervallen)", null, Koppelvlak.BIJHOUDING, null,
            "Toevoeging terugmelding aan onderzoek (vervallen)"),
    /**
     * Verwijdering terugmelding van onderzoek (vervallen).
     */
    VERWIJDERING_TERUGMELDING_VAN_ONDERZOEK_VERVALLEN("11013", "Verwijdering terugmelding van onderzoek (vervallen)", null, Koppelvlak.BIJHOUDING, null,
            "Verwijdering terugmelding van onderzoek (vervallen)"),
    /**
     * Registratie tussenresultaat onderzoek.
     */
    REGISTRATIE_TUSSENRESULTAAT_ONDERZOEK("11002", "Registratie tussenresultaat onderzoek", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.ONDERZOEK, "Registratie tussenresultaat onderzoek"),
    /**
     * Beeindiging onderzoek.
     */
    BEEINDIGING_ONDERZOEK("11003", "Beeindiging onderzoek", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.ONDERZOEK, "Beeindiging onderzoek"),
    /**
     * Wijziging onderzoek.
     */
    WIJZIGING_ONDERZOEK("11004", "Wijziging onderzoek", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING, BurgerzakenModule.ONDERZOEK,
            "Wijziging onderzoek"),
    /**
     * Selectie met plaatsen afnemerindicatie.
     */
    SELECTIE_MET_PLAATSEN_AFNEMERINDICATIE("31011", "Selectie met plaatsen afnemerindicatie", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.LEVERING, BurgerzakenModule.SYNCHRONISATIE, "Selectie met plaatsen afnemerindicatie"),
    /**
     * Selectie met verwijderen afnemerindicatie.
     */
    SELECTIE_MET_VERWIJDEREN_AFNEMERINDICATIE("31012", "Selectie met verwijderen afnemerindicatie", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.LEVERING, BurgerzakenModule.SYNCHRONISATIE, "Selectie met verwijderen afnemerindicatie"),
    /**
     * Attendering met plaatsen afnemerindicatie.
     */
    ATTENDERING_MET_PLAATSEN_AFNEMERINDICATIE("31010", "Attendering met plaatsen afnemerindicatie", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.LEVERING, BurgerzakenModule.ATTENDERING, "Attendering met plaatsen afnemerindicatie"),
    /**
     * Synchronisatie stamgegeven.
     */
    SYNCHRONISATIE_STAMGEGEVEN("31004", "Synchronisatie stamgegeven", CategorieAdministratieveHandeling.SYNCHRONISATIE, Koppelvlak.LEVERING,
            BurgerzakenModule.SYNCHRONISATIE, "Synchronisatie stamgegeven"),
    /**
     * Geef details persoon.
     */
    GEEF_DETAILS_PERSOON("30003", "Geef details persoon", null, Koppelvlak.LEVERING, BurgerzakenModule.BEVRAGING, "Geef details persoon"),
    /**
     * GBA - Bijhouding overig.
     */
    G_B_A_BIJHOUDING_OVERIG("99997", "GBA - Bijhouding overig", CategorieAdministratieveHandeling.G_B_A_SYNCHRONISATIE, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Bijhouding overig"),
    /**
     * GBA - Infrastructurele wijziging.
     */
    G_B_A_INFRASTRUCTURELE_WIJZIGING("99996", "GBA - Infrastructurele wijziging", CategorieAdministratieveHandeling.G_B_A_SYNCHRONISATIE,
            Koppelvlak.I_S_C, BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Infrastructurele wijziging"),
    /**
     * GBA - A-nummer wijziging.
     */
    G_B_A_A_NUMMER_WIJZIGING("99995", "GBA - A-nummer wijziging", CategorieAdministratieveHandeling.G_B_A_SYNCHRONISATIE, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - A-nummer wijziging"),
    /**
     * GBA - Afvoeren PL.
     */
    G_B_A_AFVOEREN_P_L("99994", "GBA - Afvoeren PL", CategorieAdministratieveHandeling.G_B_A_SYNCHRONISATIE, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Afvoeren PL"),
    /**
     * GBA - Geboorte.
     */
    G_B_A_GEBOORTE("99901", "GBA - Geboorte", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.I_S_C, BurgerzakenModule.MIGRATIEVOORZIENINGEN,
            "GBA - Geboorte"),
    /**
     * GBA - Registratie ouderschap.
     */
    G_B_A_REGISTRATIE_OUDERSCHAP("99902", "GBA - Registratie ouderschap", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Registratie ouderschap"),
    /**
     * GBA - Ontkenning ouderschap.
     */
    G_B_A_ONTKENNING_OUDERSCHAP("99903", "GBA - Ontkenning ouderschap", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Ontkenning ouderschap"),
    /**
     * GBA - Sluiting huwelijk geregistreerd partnerschap.
     */
    G_B_A_SLUITING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("99904", "GBA - Sluiting huwelijk geregistreerd partnerschap",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.I_S_C, BurgerzakenModule.MIGRATIEVOORZIENINGEN,
            "GBA - Sluiting huwelijk geregistreerd partnerschap"),
    /**
     * GBA - Ontbinding huwelijk geregistreerd partnerschap.
     */
    G_B_A_ONTBINDING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("99905", "GBA - Ontbinding huwelijk geregistreerd partnerschap",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.I_S_C, BurgerzakenModule.MIGRATIEVOORZIENINGEN,
            "GBA - Ontbinding huwelijk geregistreerd partnerschap"),
    /**
     * GBA - Omzetting huwelijk geregistreerd partnerschap.
     */
    G_B_A_OMZETTING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("99906", "GBA - Omzetting huwelijk geregistreerd partnerschap",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.I_S_C, BurgerzakenModule.MIGRATIEVOORZIENINGEN,
            "GBA - Omzetting huwelijk geregistreerd partnerschap"),
    /**
     * GBA - Overlijden.
     */
    G_B_A_OVERLIJDEN("99907", "GBA - Overlijden", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Overlijden"),
    /**
     * GBA - Wijziging naam geslacht.
     */
    G_B_A_WIJZIGING_NAAM_GESLACHT("99908", "GBA - Wijziging naam geslacht", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.I_S_C,
            BurgerzakenModule.MIGRATIEVOORZIENINGEN, "GBA - Wijziging naam geslacht"),
    /**
     * Verwijdering partnergegevens na geslachtswijziging.
     */
    VERWIJDERING_PARTNERGEGEVENS_NA_GESLACHTSWIJZIGING("03006", "Verwijdering partnergegevens na geslachtswijziging",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.NAAM_GESLACHT,
            "Verwijdering partnergegevens na geslachtswijziging"),
    /**
     * Verwijdering kindgegevens na geslachtswijziging.
     */
    VERWIJDERING_KINDGEGEVENS_NA_GESLACHTSWIJZIGING("03007", "Verwijdering kindgegevens na geslachtswijziging",
            CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING, BurgerzakenModule.AFSTAMMING,
            "Verwijdering kindgegevens na geslachtswijziging"),
    /**
     * Verwijdering kindgegevens na adoptie.
     */
    VERWIJDERING_KINDGEGEVENS_NA_ADOPTIE("03008", "Verwijdering kindgegevens na adoptie", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.AFSTAMMING, "Verwijdering kindgegevens na adoptie"),
    /**
     * Verwijdering oudergegevens na adoptie.
     */
    VERWIJDERING_OUDERGEGEVENS_NA_ADOPTIE("03009", "Verwijdering oudergegevens na adoptie", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.AFSTAMMING, "Verwijdering oudergegevens na adoptie"),
    /**
     * Correctie nationaliteit.
     */
    CORRECTIE_NATIONALITEIT("06006", "Correctie nationaliteit", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.NATIONALITEIT, "Correctie nationaliteit"),
    /**
     * Wijziging identificatienummers.
     */
    WIJZIGING_IDENTIFICATIENUMMERS("10001", "Wijziging identificatienummers", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Wijziging identificatienummers"),
    /**
     * Verwijdering persoon.
     */
    VERWIJDERING_PERSOON("10002", "Verwijdering persoon", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Verwijdering persoon"),
    /**
     * Wijziging verantwoording.
     */
    WIJZIGING_VERANTWOORDING("10003", "Wijziging verantwoording", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Wijziging verantwoording"),
    /**
     * Correctie dubbele inschrijving.
     */
    CORRECTIE_DUBBELE_INSCHRIJVING("10004", "Correctie dubbele inschrijving", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Correctie dubbele inschrijving"),
    /**
     * Correctie hinkende relatie.
     */
    CORRECTIE_HINKENDE_RELATIE("10005", "Correctie hinkende relatie", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Correctie hinkende relatie"),
    /**
     * Correctie overige persoonsgegevens.
     */
    CORRECTIE_OVERIGE_PERSOONSGEGEVENS("10006", "Correctie overige persoonsgegevens", CategorieAdministratieveHandeling.CORRECTIE, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Correctie overige persoonsgegevens"),
    /**
     * Wijziging gemeentenaam.
     */
    WIJZIGING_GEMEENTENAAM("10007", "Wijziging gemeentenaam", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Wijziging gemeentenaam"),
    /**
     * Wijziging postcode woonplaatsnaam.
     */
    WIJZIGING_POSTCODE_WOONPLAATSNAAM("10008", "Wijziging postcode woonplaatsnaam", CategorieAdministratieveHandeling.ACTUALISERING,
            Koppelvlak.BIJHOUDING, BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Wijziging postcode woonplaatsnaam"),
    /**
     * Wijziging naam openbare ruimte.
     */
    WIJZIGING_NAAM_OPENBARE_RUIMTE("10009", "Wijziging naam openbare ruimte", CategorieAdministratieveHandeling.ACTUALISERING, Koppelvlak.BIJHOUDING,
            BurgerzakenModule.BIJZONDERE_BIJHOUDING, "Wijziging naam openbare ruimte"),
    /**
     * Attendering.
     */
    ATTENDERING("31009", "Attendering", CategorieAdministratieveHandeling.SYNCHRONISATIE, Koppelvlak.LEVERING, BurgerzakenModule.ATTENDERING,
            "Attendering"),
    /**
     * Mutatielevering op basis van afnemerindicatie.
     */
    MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE("31005", "Mutatielevering op basis van afnemerindicatie",
            CategorieAdministratieveHandeling.SYNCHRONISATIE, Koppelvlak.LEVERING, BurgerzakenModule.SYNCHRONISATIE,
            "Mutatielevering op basis van afnemerindicatie"),
    /**
     * Mutatielevering op basis van doelbinding.
     */
    MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING("31006", "Mutatielevering op basis van doelbinding", CategorieAdministratieveHandeling.SYNCHRONISATIE,
            Koppelvlak.LEVERING, BurgerzakenModule.SYNCHRONISATIE, "Mutatielevering op basis van doelbinding"),
    /**
     * Mutatielevering stamgegeven.
     */
    MUTATIELEVERING_STAMGEGEVEN("31007", "Mutatielevering stamgegeven", CategorieAdministratieveHandeling.SYNCHRONISATIE, Koppelvlak.LEVERING,
            BurgerzakenModule.SYNCHRONISATIE, "Mutatielevering stamgegeven"),
    /**
     * Melding wijziging medebewoning.
     */
    MELDING_WIJZIGING_MEDEBEWONING("31008", "Melding wijziging medebewoning", CategorieAdministratieveHandeling.SYNCHRONISATIE, Koppelvlak.LEVERING,
            BurgerzakenModule.SYNCHRONISATIE, "Melding wijziging medebewoning"),
    /**
     * Notificatie bijhoudingsplan.
     */
    NOTIFICATIE_BIJHOUDINGSPLAN("15001", "Notificatie bijhoudingsplan", null, Koppelvlak.BIJHOUDING, BurgerzakenModule.FIATTERING,
            "Notificatie bijhoudingsplan"),
    /**
     * Notificatie BRP bijhouding.
     */
    NOTIFICATIE_B_R_P_BIJHOUDING("17001", "Notificatie BRP bijhouding", null, Koppelvlak.BIJHOUDING, BurgerzakenModule.VRIJE_BERICHTEN,
            "Notificatie BRP bijhoudingmod"),
    /**
     * Zoek persoon.
     */
    ZOEK_PERSOON("30001", "Zoek persoon", null, Koppelvlak.LEVERING, BurgerzakenModule.BEVRAGING, "Zoek persoon"),
    /**
     * Zoek persoon op adresgegevens.
     */
    ZOEK_PERSOON_OP_ADRESGEGEVENS("30002", "Zoek persoon op adresgegevens", null, Koppelvlak.LEVERING, BurgerzakenModule.BEVRAGING,
            "Zoek persoon op adresgegevens"),
    /**
     * Geef medebewoners.
     */
    GEEF_MEDEBEWONERS("30004", "Geef medebewoners", null, Koppelvlak.LEVERING, BurgerzakenModule.BEVRAGING, "Geef medebewoners"),
    /**
     * Bepaal kandidaat ouder.
     */
    BEPAAL_KANDIDAAT_OUDER("30050", "Bepaal kandidaat ouder", null, Koppelvlak.LEVERING, BurgerzakenModule.BEVRAGING, "Bepaal kandidaat ouder"),
    /**
     * Geef relatiegegevens GBA.
     */
    GEEF_RELATIEGEGEVENS_G_B_A("30051", "Geef relatiegegevens GBA", null, Koppelvlak.LEVERING, BurgerzakenModule.BEVRAGING, "Geef relatiegegevens GBA"),
    /**
     * Selectie.
     */
    SELECTIE("32001", "Selectie", null, Koppelvlak.LEVERING, BurgerzakenModule.SELECTIE, "Selectie"),
    /**
     * Verkiezingsselectie.
     */
    VERKIEZINGSSELECTIE("32002", "Verkiezingsselectie", null, Koppelvlak.LEVERING, BurgerzakenModule.SELECTIE, "Verkiezingsselectie"),
    /**
     * Notificatie BRP levering.
     */
    NOTIFICATIE_B_R_P_LEVERING("33001", "Notificatie BRP levering", null, Koppelvlak.LEVERING, BurgerzakenModule.VRIJE_BERICHTEN,
            "Notificatie BRP levering");

    private final String code;
    private final String naam;
    private final CategorieAdministratieveHandeling categorieAdministratieveHandeling;
    private final Koppelvlak koppelvlak;
    private final BurgerzakenModule module;
    private final String alias;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor SoortAdministratieveHandeling
     * @param naam Naam voor SoortAdministratieveHandeling
     * @param categorieAdministratieveHandeling CategorieAdministratieveHandeling voor SoortAdministratieveHandeling
     * @param koppelvlak Koppelvlak voor SoortAdministratieveHandeling
     * @param module Module voor SoortAdministratieveHandeling
     * @param alias Alias voor SoortAdministratieveHandeling
     */
    private SoortAdministratieveHandeling(
        final String code,
        final String naam,
        final CategorieAdministratieveHandeling categorieAdministratieveHandeling,
        final Koppelvlak koppelvlak,
        final BurgerzakenModule module,
        final String alias)
    {
        this.code = code;
        this.naam = naam;
        this.categorieAdministratieveHandeling = categorieAdministratieveHandeling;
        this.koppelvlak = koppelvlak;
        this.module = module;
        this.alias = alias;
    }

    /**
     * Retourneert Code van Soort administratieve handeling.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Soort administratieve handeling.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Categorie administratieve handeling van Soort administratieve handeling.
     *
     * @return Categorie administratieve handeling.
     */
    public CategorieAdministratieveHandeling getCategorieAdministratieveHandeling() {
        return categorieAdministratieveHandeling;
    }

    /**
     * Retourneert Koppelvlak van Soort administratieve handeling.
     *
     * @return Koppelvlak.
     */
    public Koppelvlak getKoppelvlak() {
        return koppelvlak;
    }

    /**
     * Retourneert Module van Soort administratieve handeling.
     *
     * @return Module.
     */
    public BurgerzakenModule getModule() {
        return module;
    }

    /**
     * Retourneert Alias van Soort administratieve handeling.
     *
     * @return Alias.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.SOORTADMINISTRATIEVEHANDELING;
    }

}
