/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;

/**
 * Enumeratie van alle BRP elementen. Gebruikt om labels van de BRP elementen vast te leggen welke in de viewer getoond
 * kunnen worden.
 */
public enum GgoBrpElementEnum {
    /* Algemeen */
    /**
     * Adellijke titel.
     */
    ADELLIJKE_TITEL("Adellijke titel", Element.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE,
            Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE,
            Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE),
    /**
     * Gemeente.
     */
    GEMEENTE("Gemeente", Element.PERSOON_ADRES_GEMEENTECODE, Element.PERSOON_OVERLIJDEN_GEMEENTECODE),
    /**
     * Geslachtsnaamstam.
     */
    GESLACHTSNAAMSTAM("Geslachtsnaamstam", Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM,
            Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM,
            Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, Element.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM),
    /**
     * Indicatie afgeleid.
     */
    INDICATIE_AFGELEID("Indicatie afgeleid", Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID,
            Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID,
            Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, Element.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID),
    /**
     * Land/gebied.
     */
    LAND_OF_GEBIED("Land/gebied", Element.PERSOON_ADRES_LANDGEBIEDCODE, Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE),
    /**
     * Rechtsgrondomschrijving.
     */
    RECHTSGRONDOMSCHRIJVING("Rechtsgrondomschrijving", Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING),
    /**
     * Plaats.
     */
    WOONPLAATS("Woonplaatsnaam", Element.PERSOON_ADRES_WOONPLAATSNAAM, Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM),
    /**
     * Predicaat.
     */
    PREDICAAT("Predicaat", Element.PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE,
            Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE,
            Element.PERSOON_NAAMGEBRUIK_PREDICAATCODE),

    /**
     * Scheidingsteken.
     */
    SCHEIDINGSTEKEN("Scheidingsteken", Element.PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN,
            Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN,
            Element.PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN),
    /**
     * Volgnummer.
     */
    VOLGNUMMER("Volgnummer", Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER, Element.PERSOON_VOORNAAM_VOLGNUMMER),
    /**
     * Voornamen.
     */
    VOORNAMEN("Voornamen", Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN, Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN,
            Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORNAMEN, Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN, Element.PERSOON_NAAMGEBRUIK_VOORNAMEN),
    /**
     * Voorvoegsel.
     */
    VOORVOEGSEL("Voorvoegsel", Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL,
            Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL,
            Element.PERSOON_NAAMGEBRUIK_VOORVOEGSEL),

    /**
     * Heeft indicatie.
     */
    HEEFT_INDICATIE("Heeft indicatie", Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_WAARDE,
            Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_WAARDE, Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE,
            Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE, Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE,
            Element.PERSOON_INDICATIE_STAATLOOS_WAARDE, Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_WAARDE,
            Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE),

    /* Naamgebruik */
    /**
     * Naamgebruik.
     */
    NAAMGEBRUIK("Naamgebruik", Element.PERSOON_NAAMGEBRUIK_CODE),

    /* Adres */
    /**
     * Soort adres.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud#getSoortAdresCode()}
     */
    SOORT_ADRES("Soort adres", Element.PERSOON_ADRES_SOORTCODE),
    /**
     * Reden wijziging adres.
     */
    REDEN_WIJZIGING_ADRES("Reden wijziging adres", Element.PERSOON_ADRES_REDENWIJZIGINGCODE),
    /**
     * Reden wijziging migratie.
     */
    REDEN_WIJZIGING_MIGRATIE("Reden wijziging migratie", Element.PERSOON_MIGRATIE_REDENWIJZIGINGCODE),
    /**
     * Aangever adreshouding.
     */
    AANGEVER_ADRESHOUDING("Aangever adreshouding", Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE),
    /**
     * Aangever migratie.
     */
    AANGEVER_MIGRATIE("Aangever migratie", Element.PERSOON_MIGRATIE_AANGEVERCODE),
    /**
     * Datum aanvang adreshouding.
     */
    DATUM_AANVANG_ADRESHOUDING("Datum aanvang adreshouding", Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING),
    /**
     * Identificatiecode adresseerbaar object.
     */
    IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT("Identificatiecode adresseerbaar object", Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT),
    /**
     * Identificatiecode nummeraanduiding.
     */
    IDENTIFICATIECODE_NUMMERAANDUIDING("Identificatiecode nummeraanduiding", Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING),
    /**
     * Indicatie persoon aangetroffen op adres.
     */
    INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES("Indicatie persoon aangetroffen op adres", Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES),
    /**
     * Naam openbare ruimte.
     */
    NAAM_OPENBARE_RUIMTE("Naam openbare ruimte", Element.PERSOON_ADRES_NAAMOPENBARERUIMTE),
    /**
     * Afgekorte naam openbare ruimte.
     */
    AFGEKORTE_NAAM_OPENBARE_RUIMTE("Afgekorte naam openbare ruimte", Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE),
    /**
     * Gemeentedeel.
     */
    GEMEENTEDEEL("Gemeentedeel", Element.PERSOON_ADRES_GEMEENTEDEEL),
    /**
     * Huisnummer.
     */
    HUISNUMMER("Huisnummer", Element.PERSOON_ADRES_HUISNUMMER),
    /**
     * Huisletter.
     */
    HUISLETTER("Huisletter", Element.PERSOON_ADRES_HUISLETTER),
    /**
     * Huisnummertoevoeging.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud#getHuisnummertoevoeging()}
     */
    HUISNUMMERTOEVOEGING("Huisnummertoevoeging", Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING),
    /**
     * Postcode.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud#getPostcode()}
     */
    POSTCODE("Postcode", Element.PERSOON_ADRES_POSTCODE),
    /**
     * Locatie tov adres.
     */
    LOCATIE_TOV_ADRES("Locatie t.o.v. adres", Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES),
    /**
     * Locatie omschrijving.
     */
    LOCATIE_OMSCHRIJVING("Locatie omschrijving", Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING),
    /**
     * Buitenlands Adres Regel 1.
     */
    BUITENLANDS_ADRES_REGEL_1("Buitenlands adres regel 1", Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1),
    /**
     * Buitenlands Adres Regel 2.
     */
    BUITENLANDS_ADRES_REGEL_2("Buitenlands adres regel 2", Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2),
    /**
     * Buitenlands Adres Regel 3.
     */
    BUITENLANDS_ADRES_REGEL_3("Buitenlands adres regel 3", Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3),
    /**
     * Buitenlands Adres Regel 4.
     */
    BUITENLANDS_ADRES_REGEL_4("Buitenlands adres regel 4", Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4),
    /**
     * Buitenlands Adres Regel 5.
     */
    BUITENLANDS_ADRES_REGEL_5("Buitenlands adres regel 5", Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5),
    /**
     * Buitenlands Adres Regel 6.
     */
    BUITENLANDS_ADRES_REGEL_6("Buitenlands adres regel 6", Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6),

    /**
     * Bijhoudingsaard.
     */
    BIJHOUDINGSAARD("Bijhoudingsaard", Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE),
    /**
     * Nadere bijhoudingsaard.
     */
    NADERE_BIJHOUDINGSAARD("Nadere bijhoudingsaard", Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE),
    /**
     * Bijhoudingspartij.
     */
    BIJHOUDINGSPARTIJ("Bijhoudingspartij", Element.PERSOON_BIJHOUDING_PARTIJCODE),
    /**
     * Onverwerkt document aanwezig.
     */
    ONVERWERKT_DOC_AANWEZIG("Onverwerkt document aanwezig", Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG),

    /* Deelname EU verkiezingen */
    /**
     * Indicatie deelname EU verkiezingen.
     */
    INDICATIE_DEELNAME_EU_VERKIEZINGEN("Deelname EU verkiezingen", Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME),

    /**
     * Datum aanleiding aanpassing deelname Europese verkiezingen.
     */
    DATUM_AANL_AANP_DEELNAME_EU_VERKIEZINGEN("Datum aanleiding aanpassing deelname EU verkiezingen",
            Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING),

    /**
     * Datum einde uitsluiting Europees kiesrecht.
     */
    DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN("Datum voorzien einde uitsluiting EU verkiezingen",
            Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING),

    /* Geboorte */
    /**
     * Geboorte datum.
     */
    DATUM_GEBOORTE("Datum geboorte", Element.PERSOON_GEBOORTE_DATUM, Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_DATUM,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM, Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM,
            Element.GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM),
    /**
     * Geboorte gemeente.
     */
    GEMEENTE_GEBOORTE("Gemeente geboorte", Element.PERSOON_GEBOORTE_GEMEENTECODE, Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_GEMEENTECODE,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_GEMEENTECODE, Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE,
            Element.GERELATEERDEKIND_PERSOON_GEBOORTE_GEMEENTECODE),
    /**
     * Geboorte plaats.
     */
    WOONPLAATSNAAM_GEBOORTE("Woonplaatsnaam geboorte", Element.PERSOON_GEBOORTE_WOONPLAATSNAAM,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM, Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM,
            Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_WOONPLAATSNAAM, Element.GERELATEERDEKIND_PERSOON_GEBOORTE_WOONPLAATSNAAM),
    /**
     * Buitenlandse geboorteplaats.
     */
    BUITENLANDSE_PLAATS_GEBOORTE("Buitenlandse plaats geboorte", Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS, Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS,
            Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEPLAATS),
    /**
     * Buitenlandse regio geboorte.
     */
    BUITENLANDSE_REGIO_GEBOORTE("Buitenlandse regio geboorte", Element.PERSOON_GEBOORTE_BUITENLANDSEREGIO,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO, Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEREGIO,
            Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEREGIO),
    /**
     * Geboorte land/gebied.
     */
    LAND_OF_GEBIED_GEBOORTE("Land/gebied geboorte", Element.PERSOON_GEBOORTE_LANDGEBIEDCODE,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE, Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE,
            Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE, Element.GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE),
    /**
     * Omschrijving geboorte locatie.
     */
    OMSCHRIJVING_GEBOORTELOCATIE("Omschrijving geboorte locatie", Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE,
            Element.GERELATEERDEKIND_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE),

    /* Geslachtsaanduiding */
    /**
     * Geslachtsaanduiding.
     */
    GESLACHTSAANDUIDING("Geslachtsaanduiding", Element.PERSOON_GESLACHTSAANDUIDING_CODE,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE, Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE,
            Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE),

    /* Geslachtsnaamcomponent */
    /**
     * Naam.
     */
    STAM("Stam", Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM),

    /* Identificatienummers */
    /**
     * Administratienummer.
     */
    ADMINISTRATIENUMMER("Administratienummer", Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER,
            Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER,
            Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
    /**
     * Burgerservicenummer.
     */
    BURGERSERVICENUMMER("Burgerservicenummer", Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER,
            Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER,
            Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),

    /* Migratie */
    /**
     * Soort migratie.
     */
    SOORT_MIGRATIE("Soort migratie", Element.PERSOON_MIGRATIE_SOORTCODE),
    /**
     * Land/gebied migratie.
     */
    LAND_OF_GEBIED_MIGRATIE("Land/gebied migratie", Element.PERSOON_MIGRATIE_LANDGEBIEDCODE),
    /**
     * Buitenlandse adres regel 1 migratie.
     */
    BUITENLANDSE_ADRES_REGEL_1("Buitenlandse adres regel 1 migratie", Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1),
    /**
     * Buitenlandse adres regel 2 migratie.
     */
    BUITENLANDSE_ADRES_REGEL_2("Buitenlandse adres regel 2 migratie", Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2),
    /**
     * Buitenlandse adres regel 3 migratie.
     */
    BUITENLANDSE_ADRES_REGEL_3("Buitenlandse adres regel 3 migratie", Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3),
    /**
     * Buitenlandse adres regel 4 migratie.
     */
    BUITENLANDSE_ADRES_REGEL_4("Buitenlandse adres regel 4 migratie", Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4),
    /**
     * Buitenlandse adres regel 5 migratie.
     */
    BUITENLANDSE_ADRES_REGEL_5("Buitenlandse adres regel 5 migratie", Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5),
    /**
     * Buitenlandse adres regel 6 migratie.
     */
    BUITENLANDSE_ADRES_REGEL_6("Buitenlandse adres regel 6 migratie", Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6),

    /* Inschrijving */
    /**
     * Datum inschrijving.
     */
    DATUM_INSCHRIJVING("Datum inschrijving", Element.PERSOON_INSCHRIJVING_DATUM),
    /**
     * Versienummer.
     */
    VERSIENUMMER("Versienummer", Element.PERSOON_INSCHRIJVING_VERSIENUMMER),
    /**
     * Datumtijdstempel.
     */
    DATUMTIJDSTEMPEL("Datumtijdstempel", Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL),

    /* Nationaliteit */
    /**
     * Nationaliteit.
     */
    NATIONALITEIT("Nationaliteit", Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE),

    /**
     * Reden verkrijging Nederlandschap.
     */
    REDEN_VERKRIJGING_NEDERLANDSCHAP("Reden verkrijging Nederlandschap", Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE),

    /**
     * Reden verlies Nederlandschap.
     */
    REDEN_VERLIES_NEDERLANDSCHAP("Reden verlies Nederlandschap", Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE),

    /**
     * Migratie datum einde bijhouding.
     */
    DATUM_MIGRATIE_EINDE_BIJHOUDING("Migratie datum einde bijhouding", Element.PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING),

    /**
     * Migratie reden opname nationaliteit.
     */
    REDEN_OPNAME_NATIONALITEIT_MIGRATIE("Migratie reden opname nationaliteit", Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE),

    /**
     * Migratie reden beeindigen nationaliteit.
     */
    REDEN_BEEINDIGEN_NATIONALITEIT_MIGRATIE("Migratie reden beeindigen nationaliteit", Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE),

    /**
     * Indicatie bijhouding beeindigd.
     */
    INDICATIE_BIJHOUDING_BEEINDIGD("Indicatie bijhouding beeindigd", Element.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD),

    /* Nummerverwijzing */
    /**
     * Vorig administratienummer.
     */
    VORIG_ADMINISTRATIENUMMER("Vorig administratienummer", Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER),
    /**
     * Volgend administratienummer.
     */
    VOLGEND_ADMINISTRATIENUMMER("Volgend administratienummer", Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER),
    /**
     * Vorig burgerservicenummer.
     */
    VORIG_BURGERSERVICENUMMER("Vorig burgerservicenummer", Element.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER),
    /**
     * Volgend burgerservicenummmmmmer.
     */
    VOLGEND_BURGERSERVICENUMMER("Volgend burgerservicenummer", Element.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER),

    /* Overlijden */
    /**
     * Datum.
     */
    DATUM_OVERLIJDEN("Datum overlijden", Element.PERSOON_OVERLIJDEN_DATUM),
    /**
     * Woonplaatsnaam.
     */
    WOONPLAATSNAAM_OVERLIJDEN("Woonplaatsnaam overlijden", Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM),
    /**
     * Buitenlandse plaats.
     */
    BUITENLANDSE_PLAATS("Buitenlandse plaats", Element.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS),
    /**
     * Buitenlandse regio.
     */
    BUITENLANDSE_REGIO("Buitenlandse regio", Element.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO),
    /**
     * Omschrijving locatie.
     */
    OMSCHRIJVING_LOCATIE("Omschrijving locatie", Element.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE),

    /* Persoonskaart */
    /**
     * Gemeente PK.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud#getGemeentePKCode()}
     */
    GEMEENTE_PK("Gemeente persoonskaart", Element.PERSOON_PERSOONSKAART_PARTIJCODE),

    /**
     * Indicatie PK volledig geconverteerd.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud#getIndicatiePKVolledigGeconverteerd()}
     */
    INDICATIE_PK_VOLLEDIG_GECONVERTEERD("Indicatie PK volledig geconverteerd", Element.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD),

    /* Reisdocument */
    /**
     * Soort.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud#getSoort()}
     */
    SOORT(Constants.SOORT_STRING, Element.PERSOON_REISDOCUMENT_SOORTCODE),

    /**
     * Nummer.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud#getNummer()}
     */
    NUMMER("Nummer", Element.PERSOON_REISDOCUMENT_NUMMER),
    /**
     * Datum ingang document.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud#getDatumIngangDocument()}
     */
    DATUM_INGANG_DOCUMENT("Datum ingang document", Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT),
    /**
     * Datum uitgifte.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud#getDatumUitgifte()}
     */
    DATUM_UITGIFTE("Datum uitgifte", Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE),
    /**
     * Autoriteit van afgifte.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud#getAutoriteitVanAfgifte()}
     */
    AUTORITEIT_VAN_AFGIFTE("Autoriteit van afgifte", Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE),
    /**
     * Datum einde document.
     *
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud#getDatumEindeDocument()}
     */
    DATUM_EINDE_DOCUMENT("Datum einde document", Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT),
    /**
     * Datum inhouding vermissing.
     */
    DATUM_INHOUDING_OF_VERMISSING("Datum inhouding/vermissing", Element.PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING),
    /**
     * Reden ontbreken.
     */
    AANDUIDING_INHOUDING_OF_VERMISSING("Aanduiding inhouding/vermissing", Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE),

    /* Samengestelde naam */
    /**
     * Indicatie namenreeks.
     */
    INDICATIE_NAMENREEKS("Namenreeks", Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS,
            Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS),

    /* Uitsluiting kiesrecht */
    /**
     * Indicatie uitsluiting kiesrecht.
     */
    INDICATIE_UITSLUITING_KIESRECHT("Indicatie uitsluiting kiesrecht", Element.PERSOON_UITSLUITINGKIESRECHT_INDICATIE),

    /**
     * Datum voorzien einde uitsluiting kiesrecht.
     */
    DATUM_EINDE_UITSLUITING_KIESRECHT("Datum voorzien einde uitsluiting kiesrecht", Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE),

    /* Verblijfsrecht */
    /**
     * Verblijfsrecht.
     */
    AANDUIDING_VERBLIJFSRECHT("Aanduiding verblijfsrecht", Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE),
    /**
     * Datum mededeling verblijfsrecht.
     */
    DATUM_MEDEDELING_VERBLIJFSRECHT("Datum mededeling verblijfsrecht", Element.PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING),
    /**
     * Datum voorziene einde verblijfsrecht.
     */
    DATUM_VOORZIEN_EINDE_VERBLIJFSRECHT("Datum voorzien einde verblijfsrecht", Element.PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE),
    /**
     * Datum aanvang verblijfsrecht.
     */
    DATUM_AANVANG_VERBLIJFSRECHT("Datum aanvang verblijfsrecht", Element.PERSOON_VERBLIJFSRECHT_DATUMAANVANG),

    /* Verificatie */
    /**
     * Soort verificatie. Nb heet eigenlijk 'Soort' maar dit veroorzaakt een name/ type clash met soort reisdocument.
     */
    SOORT_VERIFICATIE(Constants.SOORT_STRING, Element.PERSOON_VERIFICATIE_SOORT),

    /* Voornaam */
    /**
     * Voornaam.
     */
    VOORNAAM("Voornaam", Element.PERSOON_VOORNAAM_NAAM),

    /* Relatie */
    /**
     * Datum aanvang.
     */
    DATUM_AANVANG("Datum aanvang", Element.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG, Element.HUWELIJK_DATUMAANVANG),
    /**
     * Gemeente aanvang.
     */
    GEMEENTE_AANVANG("Gemeente aanvang", Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE, Element.HUWELIJK_GEMEENTEAANVANGCODE),
    /**
     * Woonplaatsnaam aanvang.
     */
    WOONPLAATSNAAM_AANVANG("Woonplaatsnaam aanvang", Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG, Element.HUWELIJK_WOONPLAATSNAAMAANVANG),
    /**
     * Buitenlandse plaats aanvang.
     */
    BUITENLANDSE_PLAATS_AANVANG("Buitenlandse plaats aanvang", Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG,
            Element.HUWELIJK_BUITENLANDSEPLAATSAANVANG),
    /**
     * Buitenlandse regio aanvang.
     */
    BUITENLANDSE_REGIO_AANVANG("Buitenlandse regio aanvang", Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG,
            Element.HUWELIJK_BUITENLANDSEREGIOAANVANG),
    /**
     * Land/gebied aanvang.
     */
    LAND_OF_GEBIED_AANVANG("Land/gebied aanvang", Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE, Element.HUWELIJK_LANDGEBIEDAANVANGCODE),
    /**
     * Omschrijving locatie aanvang.
     */
    OMSCHRIJVING_LOCATIE_AANVANG("Omschrijving locatie aanvang", Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG,
            Element.HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG),
    /**
     * Datum einde relatie.
     */
    DATUM_EINDE("Datum einde", Element.GEREGISTREERDPARTNERSCHAP_DATUMEINDE, Element.HUWELIJK_DATUMEINDE),
    /**
     * Gemeente einde relatie.
     */
    GEMEENTE_EINDE("Gemeente einde", Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE, Element.HUWELIJK_GEMEENTEEINDECODE),
    /**
     * Woonplaatsnaam einde.
     */
    WOONPLAATSNAAM_EINDE("Woonplaatsnaam einde", Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE, Element.HUWELIJK_WOONPLAATSNAAMEINDE),
    /**
     * Buitenlandse plaats einde.
     */
    BUITENLANDSE_PLAATS_EINDE("Buitenlandse plaats einde", Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE, Element.HUWELIJK_BUITENLANDSEPLAATSEINDE),
    /**
     * Buitenlandse regio einde.
     */
    BUITENLANDSE_REGIO_EINDE("Buitenlandse regio einde", Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE, Element.HUWELIJK_BUITENLANDSEREGIOEINDE),
    /**
     * Land/gebied einde.
     */
    LAND_OF_GEBIED_EINDE("Land/gebied einde", Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE, Element.HUWELIJK_LANDGEBIEDEINDECODE),
    /**
     * Omschrijving locatie einde.
     */
    OMSCHRIJVING_LOCATIE_EINDE("Omschrijving locatie einde", Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE,
            Element.HUWELIJK_OMSCHRIJVINGLOCATIEEINDE),

    /* Document */
    /**
     * Document.
     */
    SOORT_DOCUMENT("Soort document", Element.DOCUMENT_SOORTNAAM),
    /**
     * Aktenummer.
     */
    AKTENUMMER("Aktenummer", Element.DOCUMENT_AKTENUMMER),
    /**
     * Omschrijving.
     */
    OMSCHRIJVING("Omschrijving", Element.DOCUMENT_OMSCHRIJVING),

    /* Ouderlijk Gezag */
    /**
     * Ouder heeft gezag.
     */
    OUDER_HEEFT_GEZAG("Ouder heeft gezag", Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG),

    /* Historie */
    /* N.b. deze zijn in Dbobject voor elk gegeven uitgeschreven. Hier deze referenties niet! */
    /**
     * Geldigheid (Datum aanvang geldigheid - Datum einde geldigheid).
     */
    DATUM_GELDIGHEID("Geldigheid", (Element) null),
    /**
     * Registratie - verval (Datumtijd registratie - Datumtijd verval).
     */
    DATUM_TIJD_REGISTRATIE_VERVAL("Registratie - verval", (Element) null),
    /**
     * Nadere aanduiding verval.
     */
    NADERE_AANDUIDING_VERVAL("Nadere aanduiding verval", (Element) null),

    /* Actie */
    /**
     * Soort actie.
     */
    SOORT_ACTIE("Soort actie", Element.ACTIE_SOORTNAAM),
    /**
     * Partij.
     */
    PARTIJ("Partij", Element.DOCUMENT_PARTIJCODE, Element.PERSOON_VERIFICATIE_PARTIJCODE, Element.ACTIE_PARTIJCODE,
            Element.ADMINISTRATIEVEHANDELING_PARTIJCODE),
    /**
     * Datum.
     */
    DATUM("Datum", Element.PERSOON_VERIFICATIE_DATUM),
    /**
     * Tijdstip registratie.
     */
    TIJDSTIP_REGISTRATIE("Tijdstip registratie", Element.ACTIE_TIJDSTIPREGISTRATIE, Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE),

    /**
     * Datum ontlening actie.
     */
    DATUM_ONTLENING("Datum ontlening", Element.ACTIE_DATUMONTLENING),

    /**
     * IST Datum document.
     */
    DATUM_DOCUMENT("82.20 Datum document", (Element) null),

    /**
     * IST Aanduiding gegevens in onderzoek.
     */
    AANDUIDING_GEGEVENS_IN_ONDERZOEK("83.10 Aanduiding gegevens in onderzoek", (Element) null),

    /**
     * IST Datum ingang onderzoek.
     */
    DATUM_INGANG_ONDERZOEK("83.20 Datum ingang onderzoek", (Element) null),

    /**
     * IST Datum einde onderzoek.
     */
    DATUM_EINDE_ONDERZOEK("83.30 Datum einde onderzoek", (Element) null),

    /**
     * IST Onjuist of strijdig met openbare orde.
     */
    ONJUIST_OF_STRIJDIG_MET_OPENBARE_ORDE("84.10 Onjuist of strijdig met openbare orde", (Element) null),

    /**
     * IST Ingangsdatum geldigheid.
     */
    INGANGSDATUM_GELDIGHEID("85.10 Ingangsdatum geldigheid", (Element) null),

    /**
     * IST Datum van opneming.
     */
    DATUM_VAN_OPNEMING("86.10 Datum van opneming", (Element) null),

    /**
     * IST Datum ingang familierechtelijke betrekking.
     */
    DATUM_INGANG_FAMILIERECHTELIJKE_BETREKKING("62.10 Datum ingang familierechtelijke betrekking", (Element) null),

    /**
     * IST Indicatie Ouder1 heeft gezag.
     */
    INDICATIE_OUDER1_HEEFT_GEZAG("indicatie Ouder1 heeft gezag", (Element) null),

    /**
     * IST Indicatie Ouder2 heeft gezag.
     */
    INDICATIE_OUDER2_HEEFT_GEZAG("indicatie Ouder2 heeft gezag", (Element) null),

    /**
     * IST Indicatie derde heeft gezag.
     */
    INDICATIE_DERDE_HEEFT_GEZAG("indicatie derde heeft gezag", (Element) null),

    /**
     * IST Indicatie onder curatele.
     */
    INDICATIE_ONDER_CURATELE("indicatie onder curatele", (Element) null),

    /**
     * IST Reden einde relatie.
     */
    REDEN_EINDE_RELATIE("Reden einde relatie", Element.GEREGISTREERDPARTNERSCHAP_REDENEINDECODE, Element.HUWELIJK_REDENEINDECODE),

    /**
     * Indicatie Ouder Uit Wie Kind Is Geboren.
     */
    INDICATIE_OUDERUITWIEKINDISGEBOREN("Indicatie ouder uit wie kind is geboren", Element.GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN),

    // Admin. handeling
    /**
     * ID.
     */
    ID("ID", Element.ACTIE_IDENTITEIT, Element.ADMINISTRATIEVEHANDELING_IDENTITEIT),
    /**
     * Soort administratieve handeling.
     */
    SOORT_ADMINISTRATIEVE_HANDELING(Constants.SOORT_STRING, Element.ADMINISTRATIEVEHANDELING_SOORTNAAM),
    /**
     * Toelichting ontlening.
     */
    TOELICHTING_ONTLENING("Toelichting ontlening", Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING),
    /**
     * Tijdstip levering.
     */
    TIJDSTIP_LEVERING("Tijdstip levering", Element.ADMINISTRATIEVEHANDELING_LEVERING_TIJDSTIP),
    /**
     * Datum/tijd laatste wijziging.
     */
    DATUM_TIJD_LAATSTE_WIJZIGING("Datum/tijd laatste wijziging", Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING),
    /**
     * Datum/tijd laatste wijziging GBA.
     */
    DATUM_TIJD_LAATSTE_WIJZIGING_GBA("Datum/tijd laatste wijziging GBA", Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK),
    /**
     * Autoriteit van afgifte buitenlands persoonsnummer.
     */
    AUTORITEIT_VAN_AFGIFTE_BUITENLANDS_PERSOONSNUMMER("Autoriteit van afgifte", Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE);

    private final String label;
    private final Element[] elementen;

    /**
     * GgoBrpElementEnum.
     * @param label label
     * @param elementen elementen
     */
    GgoBrpElementEnum(final String label, final Element... elementen) {
        this.label = label;
        this.elementen = elementen;
    }

    /**
     * Vind het juiste GgoBrpElementEnum voor het gegeven Element, zodat het label ervan kan worden getoond.
     * @param element Het Element
     * @return Het GgoBrpElementEnum
     */
    public static GgoBrpElementEnum findByElement(final Element element) {
        for (final GgoBrpElementEnum en : GgoBrpElementEnum.values()) {
            for (final Element obj : en.elementen) {
                if (element.equals(obj)) {
                    return en;
                }
            }
        }

        return null;
    }

    /**
     * Geef de waarde van label.
     * @return de label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Private inner class voor constanten gebruikt binnen de enum.
     */
    private static final class Constants {
        private static final String SOORT_STRING = "Soort";

        private Constants() {
        }
    }
}
