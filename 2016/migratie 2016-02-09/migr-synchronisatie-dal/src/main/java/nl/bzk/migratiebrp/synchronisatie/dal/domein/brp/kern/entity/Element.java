/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * De class voor de SoortElement database tabel.
 *
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
select format(E'/** %s. *\/\n%s((short) %s, "%s", %s, %s),',
        e.naam,
        upper(replace(e.naam, '.', '_')),
        e.id,
        e.naam,
        'SoortElement.' || upper(s.naam),
        case when g.naam is null then 'null' else upper(replace(g.naam, '.', '_')) end)
    from
        kern.element e
        left join kern.element g on e.groep=g.id
        left join kern.srtelement s on e.srt=s.id
    order by
        e.srt, e.naam
 </code>
 *
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 *
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 *
 * Voer daarna bovenstaand SQL statement uit.
 *
 *
 * LET OP: Bij aanpassingen aan de id of entiteit naam voor een tabel, of bij nieuwe tabellen, dan moet deze wijziging
 * ook worden doorgevoerd in de entity class GegevenInOnderzoek.
 */
public enum Element implements Enumeratie {

    /** Aangever. */
    AANGEVER((short) 3294, "Aangever", SoortElement.OBJECTTYPE, null),
    /** Actie. */
    ACTIE((short) 3071, "Actie", SoortElement.OBJECTTYPE, null),
    /** ActieBron. */
    ACTIEBRON((short) 8118, "ActieBron", SoortElement.OBJECTTYPE, null),
    /** AdministratieveHandeling. */
    ADMINISTRATIEVEHANDELING((short) 9018, "AdministratieveHandeling", SoortElement.OBJECTTYPE, null),
    /** AdministratieveHandelingGedeblokkeerdeMelding. */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING((short) 6222, "AdministratieveHandelingGedeblokkeerdeMelding", SoortElement.OBJECTTYPE, null),
    /** Betrokkenheid. */
    BETROKKENHEID((short) 3857, "Betrokkenheid", SoortElement.OBJECTTYPE, null),
    /** Document. */
    DOCUMENT((short) 3135, "Document", SoortElement.OBJECTTYPE, null),
    /** Erkenner. */
    ERKENNER((short) 9316, "Erkenner", SoortElement.OBJECTTYPE, null),
    /** ErkenningOngeborenVrucht. */
    ERKENNINGONGEBORENVRUCHT((short) 9313, "ErkenningOngeborenVrucht", SoortElement.OBJECTTYPE, null),
    /** FamilierechtelijkeBetrekking. */
    FAMILIERECHTELIJKEBETREKKING((short) 9309, "FamilierechtelijkeBetrekking", SoortElement.OBJECTTYPE, null),
    /** GedeblokkeerdeMelding. */
    GEDEBLOKKEERDEMELDING((short) 6216, "GedeblokkeerdeMelding", SoortElement.OBJECTTYPE, null),
    /** GegevenInOnderzoek. */
    GEGEVENINONDERZOEK((short) 3863, "GegevenInOnderzoek", SoortElement.OBJECTTYPE, null),
    /** GegevenInTerugmelding. */
    GEGEVENINTERUGMELDING((short) 10753, "GegevenInTerugmelding", SoortElement.OBJECTTYPE, null),
    /** GeregistreerdPartnerschap. */
    GEREGISTREERDPARTNERSCHAP((short) 9308, "GeregistreerdPartnerschap", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeErkenner. */
    GERELATEERDEERKENNER((short) 13237, "GerelateerdeErkenner", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeErkenner.Persoon. */
    GERELATEERDEERKENNER_PERSOON((short) 13239, "GerelateerdeErkenner.Persoon", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeGeregistreerdePartner. */
    GERELATEERDEGEREGISTREERDEPARTNER((short) 13073, "GerelateerdeGeregistreerdePartner", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeGeregistreerdePartner.Persoon. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON((short) 13075, "GerelateerdeGeregistreerdePartner.Persoon", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeHuwelijkspartner. */
    GERELATEERDEHUWELIJKSPARTNER((short) 12991, "GerelateerdeHuwelijkspartner", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeHuwelijkspartner.Persoon. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON((short) 12993, "GerelateerdeHuwelijkspartner.Persoon", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeInstemmer. */
    GERELATEERDEINSTEMMER((short) 13155, "GerelateerdeInstemmer", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeInstemmer.Persoon. */
    GERELATEERDEINSTEMMER_PERSOON((short) 13157, "GerelateerdeInstemmer.Persoon", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeKind. */
    GERELATEERDEKIND((short) 12920, "GerelateerdeKind", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeKind.Persoon. */
    GERELATEERDEKIND_PERSOON((short) 12922, "GerelateerdeKind.Persoon", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeNaamgever. */
    GERELATEERDENAAMGEVER((short) 13319, "GerelateerdeNaamgever", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeNaamgever.Persoon. */
    GERELATEERDENAAMGEVER_PERSOON((short) 13321, "GerelateerdeNaamgever.Persoon", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeNaamskeuzePartner. */
    GERELATEERDENAAMSKEUZEPARTNER((short) 13401, "GerelateerdeNaamskeuzePartner", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeNaamskeuzePartner.Persoon. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON((short) 13403, "GerelateerdeNaamskeuzePartner.Persoon", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeOuder. */
    GERELATEERDEOUDER((short) 12825, "GerelateerdeOuder", SoortElement.OBJECTTYPE, null),
    /** GerelateerdeOuder.Persoon. */
    GERELATEERDEOUDER_PERSOON((short) 12840, "GerelateerdeOuder.Persoon", SoortElement.OBJECTTYPE, null),
    /** Huwelijk. */
    HUWELIJK((short) 9306, "Huwelijk", SoortElement.OBJECTTYPE, null),
    /** HuwelijkGeregistreerdPartnerschap. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP((short) 9307, "HuwelijkGeregistreerdPartnerschap", SoortElement.OBJECTTYPE, null),
    /** Instemmer. */
    INSTEMMER((short) 9317, "Instemmer", SoortElement.OBJECTTYPE, null),
    /** Kind. */
    KIND((short) 9303, "Kind", SoortElement.OBJECTTYPE, null),
    /** Naamgever. */
    NAAMGEVER((short) 9320, "Naamgever", SoortElement.OBJECTTYPE, null),
    /** NaamskeuzeOngeborenVrucht. */
    NAAMSKEUZEONGEBORENVRUCHT((short) 9315, "NaamskeuzeOngeborenVrucht", SoortElement.OBJECTTYPE, null),
    /** Onderzoek. */
    ONDERZOEK((short) 3167, "Onderzoek", SoortElement.OBJECTTYPE, null),
    /** Ouder. */
    OUDER((short) 9304, "Ouder", SoortElement.OBJECTTYPE, null),
    /** Partij. */
    PARTIJ((short) 3141, "Partij", SoortElement.OBJECTTYPE, null),
    /** Partner. */
    PARTNER((short) 9305, "Partner", SoortElement.OBJECTTYPE, null),
    /** Persoon. */
    PERSOON((short) 3010, "Persoon", SoortElement.OBJECTTYPE, null),
    /** Persoon.Adres. */
    PERSOON_ADRES((short) 3237, "Persoon.Adres", SoortElement.OBJECTTYPE, null),
    /** Persoon.Afnemerindicatie. */
    PERSOON_AFNEMERINDICATIE((short) 10317, "Persoon.Afnemerindicatie", SoortElement.OBJECTTYPE, null),
    /** Persoon.Geslachtsnaamcomponent. */
    PERSOON_GESLACHTSNAAMCOMPONENT((short) 3020, "Persoon.Geslachtsnaamcomponent", SoortElement.OBJECTTYPE, null),
    /** Persoon.Indicatie. */
    PERSOON_INDICATIE((short) 3637, "Persoon.Indicatie", SoortElement.OBJECTTYPE, null),
    /** Persoon.Nationaliteit. */
    PERSOON_NATIONALITEIT((short) 3129, "Persoon.Nationaliteit", SoortElement.OBJECTTYPE, null),
    /** Persoon.Onderzoek. */
    PERSOON_ONDERZOEK((short) 6127, "Persoon.Onderzoek", SoortElement.OBJECTTYPE, null),
    /** Persoon.Reisdocument. */
    PERSOON_REISDOCUMENT((short) 3576, "Persoon.Reisdocument", SoortElement.OBJECTTYPE, null),
    /** Persoon.Verificatie. */
    PERSOON_VERIFICATIE((short) 3775, "Persoon.Verificatie", SoortElement.OBJECTTYPE, null),
    /** Persoon.Verstrekkingsbeperking. */
    PERSOON_VERSTREKKINGSBEPERKING((short) 9344, "Persoon.Verstrekkingsbeperking", SoortElement.OBJECTTYPE, null),
    /** Persoon.Voornaam. */
    PERSOON_VOORNAAM((short) 3022, "Persoon.Voornaam", SoortElement.OBJECTTYPE, null),
    /** Relatie. */
    RELATIE((short) 3184, "Relatie", SoortElement.OBJECTTYPE, null),
    /** Terugmelding. */
    TERUGMELDING((short) 10716, "Terugmelding", SoortElement.OBJECTTYPE, null),
    /** Aangever.Identiteit. */
    AANGEVER_IDENTITEIT((short) 2081, "Aangever.Identiteit", SoortElement.GROEP, null),
    /** Actie.Identiteit. */
    ACTIE_IDENTITEIT((short) 2077, "Actie.Identiteit", SoortElement.GROEP, null),
    /** ActieBron.Identiteit. */
    ACTIEBRON_IDENTITEIT((short) 8119, "ActieBron.Identiteit", SoortElement.GROEP, null),
    /** AdministratieveHandeling.Identiteit. */
    ADMINISTRATIEVEHANDELING_IDENTITEIT((short) 9019, "AdministratieveHandeling.Identiteit", SoortElement.GROEP, null),
    /** AdministratieveHandeling.Levering. */
    ADMINISTRATIEVEHANDELING_LEVERING((short) 10051, "AdministratieveHandeling.Levering", SoortElement.GROEP, null),
    /** AdministratieveHandeling.Standaard. */
    ADMINISTRATIEVEHANDELING_STANDAARD((short) 21582, "AdministratieveHandeling.Standaard", SoortElement.GROEP, null),
    /** AdministratieveHandelingGedeblokkeerdeMelding.Identiteit. */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_IDENTITEIT((short) 6223, "AdministratieveHandelingGedeblokkeerdeMelding.Identiteit", SoortElement.GROEP,
            null),
    /** Betrokkenheid.Identiteit. */
    BETROKKENHEID_IDENTITEIT((short) 2071, "Betrokkenheid.Identiteit", SoortElement.GROEP, null),
    /** Document.Identiteit. */
    DOCUMENT_IDENTITEIT((short) 2078, "Document.Identiteit", SoortElement.GROEP, null),
    /** Document.Standaard. */
    DOCUMENT_STANDAARD((short) 3784, "Document.Standaard", SoortElement.GROEP, null),
    /** Erkenner.Identiteit. */
    ERKENNER_IDENTITEIT((short) 12810, "Erkenner.Identiteit", SoortElement.GROEP, null),
    /** ErkenningOngeborenVrucht.Identiteit. */
    ERKENNINGONGEBORENVRUCHT_IDENTITEIT((short) 12786, "ErkenningOngeborenVrucht.Identiteit", SoortElement.GROEP, null),
    /** ErkenningOngeborenVrucht.Standaard. */
    ERKENNINGONGEBORENVRUCHT_STANDAARD((short) 14347, "ErkenningOngeborenVrucht.Standaard", SoortElement.GROEP, null),
    /** FamilierechtelijkeBetrekking.Identiteit. */
    FAMILIERECHTELIJKEBETREKKING_IDENTITEIT((short) 12774, "FamilierechtelijkeBetrekking.Identiteit", SoortElement.GROEP, null),
    /** FamilierechtelijkeBetrekking.Standaard. */
    FAMILIERECHTELIJKEBETREKKING_STANDAARD((short) 14324, "FamilierechtelijkeBetrekking.Standaard", SoortElement.GROEP, null),
    /** GedeblokkeerdeMelding.Identiteit. */
    GEDEBLOKKEERDEMELDING_IDENTITEIT((short) 6217, "GedeblokkeerdeMelding.Identiteit", SoortElement.GROEP, null),
    /** GegevenInOnderzoek.Identiteit. */
    GEGEVENINONDERZOEK_IDENTITEIT((short) 2075, "GegevenInOnderzoek.Identiteit", SoortElement.GROEP, null),
    /** GegevenInTerugmelding.Identiteit. */
    GEGEVENINTERUGMELDING_IDENTITEIT((short) 10754, "GegevenInTerugmelding.Identiteit", SoortElement.GROEP, null),
    /** GeregistreerdPartnerschap.Identiteit. */
    GEREGISTREERDPARTNERSCHAP_IDENTITEIT((short) 12739, "GeregistreerdPartnerschap.Identiteit", SoortElement.GROEP, null),
    /** GeregistreerdPartnerschap.Standaard. */
    GEREGISTREERDPARTNERSCHAP_STANDAARD((short) 12751, "GeregistreerdPartnerschap.Standaard", SoortElement.GROEP, null),
    /** GerelateerdeErkenner.Identiteit. */
    GERELATEERDEERKENNER_IDENTITEIT((short) 13238, "GerelateerdeErkenner.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeErkenner.Persoon.Geboorte. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE((short) 13273, "GerelateerdeErkenner.Persoon.Geboorte", SoortElement.GROEP, null),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING((short) 13288, "GerelateerdeErkenner.Persoon.Geslachtsaanduiding", SoortElement.GROEP, null),
    /** GerelateerdeErkenner.Persoon.Identificatienummers. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS((short) 13242, "GerelateerdeErkenner.Persoon.Identificatienummers", SoortElement.GROEP, null),
    /** GerelateerdeErkenner.Persoon.Identiteit. */
    GERELATEERDEERKENNER_PERSOON_IDENTITEIT((short) 13240, "GerelateerdeErkenner.Persoon.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM((short) 13255, "GerelateerdeErkenner.Persoon.SamengesteldeNaam", SoortElement.GROEP, null),
    /** GerelateerdeGeregistreerdePartner.Identiteit. */
    GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT((short) 13074, "GerelateerdeGeregistreerdePartner.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE((short) 13109, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte", SoortElement.GROEP, null),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING((short) 13124, "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding",
            SoortElement.GROEP, null),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS((short) 13078, "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers",
            SoortElement.GROEP, null),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identiteit. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTITEIT((short) 13076, "GerelateerdeGeregistreerdePartner.Persoon.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM((short) 13091, "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam",
            SoortElement.GROEP, null),
    /** GerelateerdeHuwelijkspartner.Identiteit. */
    GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT((short) 12992, "GerelateerdeHuwelijkspartner.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE((short) 13027, "GerelateerdeHuwelijkspartner.Persoon.Geboorte", SoortElement.GROEP, null),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING((short) 13042, "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding",
            SoortElement.GROEP, null),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS((short) 12996, "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers",
            SoortElement.GROEP, null),
    /** GerelateerdeHuwelijkspartner.Persoon.Identiteit. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT((short) 12994, "GerelateerdeHuwelijkspartner.Persoon.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM((short) 13009, "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam", SoortElement.GROEP,
            null),
    /** GerelateerdeInstemmer.Identiteit. */
    GERELATEERDEINSTEMMER_IDENTITEIT((short) 13156, "GerelateerdeInstemmer.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeInstemmer.Persoon.Geboorte. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE((short) 13191, "GerelateerdeInstemmer.Persoon.Geboorte", SoortElement.GROEP, null),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING((short) 13206, "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding", SoortElement.GROEP, null),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS((short) 13160, "GerelateerdeInstemmer.Persoon.Identificatienummers", SoortElement.GROEP, null),
    /** GerelateerdeInstemmer.Persoon.Identiteit. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTITEIT((short) 13158, "GerelateerdeInstemmer.Persoon.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM((short) 13173, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam", SoortElement.GROEP, null),
    /** GerelateerdeKind.Identiteit. */
    GERELATEERDEKIND_IDENTITEIT((short) 12921, "GerelateerdeKind.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeKind.Persoon.Geboorte. */
    GERELATEERDEKIND_PERSOON_GEBOORTE((short) 12956, "GerelateerdeKind.Persoon.Geboorte", SoortElement.GROEP, null),
    /** GerelateerdeKind.Persoon.Identificatienummers. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS((short) 12925, "GerelateerdeKind.Persoon.Identificatienummers", SoortElement.GROEP, null),
    /** GerelateerdeKind.Persoon.Identiteit. */
    GERELATEERDEKIND_PERSOON_IDENTITEIT((short) 12923, "GerelateerdeKind.Persoon.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM((short) 12938, "GerelateerdeKind.Persoon.SamengesteldeNaam", SoortElement.GROEP, null),
    /** GerelateerdeNaamgever.Identiteit. */
    GERELATEERDENAAMGEVER_IDENTITEIT((short) 13320, "GerelateerdeNaamgever.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeNaamgever.Persoon.Geboorte. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE((short) 13355, "GerelateerdeNaamgever.Persoon.Geboorte", SoortElement.GROEP, null),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING((short) 13370, "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding", SoortElement.GROEP, null),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS((short) 13324, "GerelateerdeNaamgever.Persoon.Identificatienummers", SoortElement.GROEP, null),
    /** GerelateerdeNaamgever.Persoon.Identiteit. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTITEIT((short) 13322, "GerelateerdeNaamgever.Persoon.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM((short) 13337, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam", SoortElement.GROEP, null),
    /** GerelateerdeNaamskeuzePartner.Identiteit. */
    GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT((short) 13402, "GerelateerdeNaamskeuzePartner.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE((short) 13437, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte", SoortElement.GROEP, null),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING((short) 13452, "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding",
            SoortElement.GROEP, null),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS((short) 13406, "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers",
            SoortElement.GROEP, null),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identiteit. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTITEIT((short) 13404, "GerelateerdeNaamskeuzePartner.Persoon.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM((short) 13419, "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam", SoortElement.GROEP,
            null),
    /** GerelateerdeOuder.Identiteit. */
    GERELATEERDEOUDER_IDENTITEIT((short) 12826, "GerelateerdeOuder.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeOuder.OuderlijkGezag. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG((short) 12828, "GerelateerdeOuder.OuderlijkGezag", SoortElement.GROEP, null),
    /** GerelateerdeOuder.Ouderschap. */
    GERELATEERDEOUDER_OUDERSCHAP((short) 12827, "GerelateerdeOuder.Ouderschap", SoortElement.GROEP, null),
    /** GerelateerdeOuder.Persoon.Geboorte. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE((short) 12874, "GerelateerdeOuder.Persoon.Geboorte", SoortElement.GROEP, null),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING((short) 12889, "GerelateerdeOuder.Persoon.Geslachtsaanduiding", SoortElement.GROEP, null),
    /** GerelateerdeOuder.Persoon.Identificatienummers. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS((short) 12843, "GerelateerdeOuder.Persoon.Identificatienummers", SoortElement.GROEP, null),
    /** GerelateerdeOuder.Persoon.Identiteit. */
    GERELATEERDEOUDER_PERSOON_IDENTITEIT((short) 12841, "GerelateerdeOuder.Persoon.Identiteit", SoortElement.GROEP, null),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM((short) 12856, "GerelateerdeOuder.Persoon.SamengesteldeNaam", SoortElement.GROEP, null),
    /** Huwelijk.Identiteit. */
    HUWELIJK_IDENTITEIT((short) 12692, "Huwelijk.Identiteit", SoortElement.GROEP, null),
    /** Huwelijk.Standaard. */
    HUWELIJK_STANDAARD((short) 13848, "Huwelijk.Standaard", SoortElement.GROEP, null),
    /** HuwelijkGeregistreerdPartnerschap.Identiteit. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_IDENTITEIT((short) 12704, "HuwelijkGeregistreerdPartnerschap.Identiteit", SoortElement.GROEP, null),
    /** HuwelijkGeregistreerdPartnerschap.Standaard. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD((short) 12716, "HuwelijkGeregistreerdPartnerschap.Standaard", SoortElement.GROEP, null),
    /** Instemmer.Identiteit. */
    INSTEMMER_IDENTITEIT((short) 12815, "Instemmer.Identiteit", SoortElement.GROEP, null),
    /** Kind.Identiteit. */
    KIND_IDENTITEIT((short) 12677, "Kind.Identiteit", SoortElement.GROEP, null),
    /** Naamgever.Identiteit. */
    NAAMGEVER_IDENTITEIT((short) 12820, "Naamgever.Identiteit", SoortElement.GROEP, null),
    /** NaamskeuzeOngeborenVrucht.Identiteit. */
    NAAMSKEUZEONGEBORENVRUCHT_IDENTITEIT((short) 12798, "NaamskeuzeOngeborenVrucht.Identiteit", SoortElement.GROEP, null),
    /** NaamskeuzeOngeborenVrucht.Standaard. */
    NAAMSKEUZEONGEBORENVRUCHT_STANDAARD((short) 14370, "NaamskeuzeOngeborenVrucht.Standaard", SoortElement.GROEP, null),
    /** Onderzoek.AfgeleidAdministratief. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF((short) 10841, "Onderzoek.AfgeleidAdministratief", SoortElement.GROEP, null),
    /** Onderzoek.Identiteit. */
    ONDERZOEK_IDENTITEIT((short) 2072, "Onderzoek.Identiteit", SoortElement.GROEP, null),
    /** Onderzoek.Standaard. */
    ONDERZOEK_STANDAARD((short) 3774, "Onderzoek.Standaard", SoortElement.GROEP, null),
    /** Ouder.Identiteit. */
    OUDER_IDENTITEIT((short) 12682, "Ouder.Identiteit", SoortElement.GROEP, null),
    /** Ouder.OuderlijkGezag. */
    OUDER_OUDERLIJKGEZAG((short) 3211, "Ouder.OuderlijkGezag", SoortElement.GROEP, null),
    /** Ouder.Ouderschap. */
    OUDER_OUDERSCHAP((short) 3858, "Ouder.Ouderschap", SoortElement.GROEP, null),
    /** Partij.Bijhouding. */
    PARTIJ_BIJHOUDING((short) 21445, "Partij.Bijhouding", SoortElement.GROEP, null),
    /** Partij.Identiteit. */
    PARTIJ_IDENTITEIT((short) 2154, "Partij.Identiteit", SoortElement.GROEP, null),
    /** Partij.Standaard. */
    PARTIJ_STANDAARD((short) 4618, "Partij.Standaard", SoortElement.GROEP, null),
    /** Partner.Identiteit. */
    PARTNER_IDENTITEIT((short) 12687, "Partner.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Adres.Identiteit. */
    PERSOON_ADRES_IDENTITEIT((short) 2068, "Persoon.Adres.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Adres.Standaard. */
    PERSOON_ADRES_STANDAARD((short) 6063, "Persoon.Adres.Standaard", SoortElement.GROEP, null),
    /** Persoon.AfgeleidAdministratief. */
    PERSOON_AFGELEIDADMINISTRATIEF((short) 3909, "Persoon.AfgeleidAdministratief", SoortElement.GROEP, null),
    /** Persoon.Afnemerindicatie.Identiteit. */
    PERSOON_AFNEMERINDICATIE_IDENTITEIT((short) 10318, "Persoon.Afnemerindicatie.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Afnemerindicatie.Standaard. */
    PERSOON_AFNEMERINDICATIE_STANDAARD((short) 10319, "Persoon.Afnemerindicatie.Standaard", SoortElement.GROEP, null),
    /** Persoon.Bijhouding. */
    PERSOON_BIJHOUDING((short) 3664, "Persoon.Bijhouding", SoortElement.GROEP, null),
    /** Persoon.DeelnameEUVerkiezingen. */
    PERSOON_DEELNAMEEUVERKIEZINGEN((short) 3901, "Persoon.DeelnameEUVerkiezingen", SoortElement.GROEP, null),
    /** Persoon.Geboorte. */
    PERSOON_GEBOORTE((short) 3514, "Persoon.Geboorte", SoortElement.GROEP, null),
    /** Persoon.Geslachtsaanduiding. */
    PERSOON_GESLACHTSAANDUIDING((short) 3554, "Persoon.Geslachtsaanduiding", SoortElement.GROEP, null),
    /** Persoon.Geslachtsnaamcomponent.Identiteit. */
    PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT((short) 2066, "Persoon.Geslachtsnaamcomponent.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Geslachtsnaamcomponent.Standaard. */
    PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD((short) 3598, "Persoon.Geslachtsnaamcomponent.Standaard", SoortElement.GROEP, null),
    /** Persoon.Identificatienummers. */
    PERSOON_IDENTIFICATIENUMMERS((short) 3344, "Persoon.Identificatienummers", SoortElement.GROEP, null),
    /** Persoon.Identiteit. */
    PERSOON_IDENTITEIT((short) 2064, "Persoon.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Indicatie.BehandeldAlsNederlander.Groep. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP((short) 3906, "Persoon.Indicatie.BehandeldAlsNederlander.Groep", SoortElement.GROEP, null),
    /** Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Groep. */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_GROEP((short) 3904, "Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Groep",
            SoortElement.GROEP, null),
    /** Persoon.Indicatie.DerdeHeeftGezag.Groep. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP((short) 3518, "Persoon.Indicatie.DerdeHeeftGezag.Groep", SoortElement.GROEP, null),
    /** Persoon.Indicatie.Identiteit. */
    PERSOON_INDICATIE_IDENTITEIT((short) 2022, "Persoon.Indicatie.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Indicatie.OnderCuratele.Groep. */
    PERSOON_INDICATIE_ONDERCURATELE_GROEP((short) 3900, "Persoon.Indicatie.OnderCuratele.Groep", SoortElement.GROEP, null),
    /** Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Groep. */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP((short) 3907,
            "Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Groep", SoortElement.GROEP, null),
    /** Persoon.Indicatie.Staatloos.Groep. */
    PERSOON_INDICATIE_STAATLOOS_GROEP((short) 2140, "Persoon.Indicatie.Staatloos.Groep", SoortElement.GROEP, null),
    /** Persoon.Indicatie.Standaard. */
    PERSOON_INDICATIE_STANDAARD((short) 3654, "Persoon.Indicatie.Standaard", SoortElement.GROEP, null),
    /** Persoon.Indicatie.VastgesteldNietNederlander.Groep. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP((short) 3905, "Persoon.Indicatie.VastgesteldNietNederlander.Groep", SoortElement.GROEP, null),
    /** Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Groep. */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP((short) 3903, "Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Groep", SoortElement.GROEP,
            null),
    /** Persoon.Inschrijving. */
    PERSOON_INSCHRIJVING((short) 3521, "Persoon.Inschrijving", SoortElement.GROEP, null),
    /** Persoon.Migratie. */
    PERSOON_MIGRATIE((short) 3790, "Persoon.Migratie", SoortElement.GROEP, null),
    /** Persoon.Naamgebruik. */
    PERSOON_NAAMGEBRUIK((short) 3487, "Persoon.Naamgebruik", SoortElement.GROEP, null),
    /** Persoon.Nationaliteit.Identiteit. */
    PERSOON_NATIONALITEIT_IDENTITEIT((short) 2067, "Persoon.Nationaliteit.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Nationaliteit.Standaard. */
    PERSOON_NATIONALITEIT_STANDAARD((short) 3604, "Persoon.Nationaliteit.Standaard", SoortElement.GROEP, null),
    /** Persoon.Nummerverwijzing. */
    PERSOON_NUMMERVERWIJZING((short) 10900, "Persoon.Nummerverwijzing", SoortElement.GROEP, null),
    /** Persoon.Onderzoek.Identiteit. */
    PERSOON_ONDERZOEK_IDENTITEIT((short) 6128, "Persoon.Onderzoek.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Onderzoek.Standaard. */
    PERSOON_ONDERZOEK_STANDAARD((short) 10763, "Persoon.Onderzoek.Standaard", SoortElement.GROEP, null),
    /** Persoon.Overlijden. */
    PERSOON_OVERLIJDEN((short) 3515, "Persoon.Overlijden", SoortElement.GROEP, null),
    /** Persoon.Persoonskaart. */
    PERSOON_PERSOONSKAART((short) 3662, "Persoon.Persoonskaart", SoortElement.GROEP, null),
    /** Persoon.Reisdocument.Identiteit. */
    PERSOON_REISDOCUMENT_IDENTITEIT((short) 2069, "Persoon.Reisdocument.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Reisdocument.Standaard. */
    PERSOON_REISDOCUMENT_STANDAARD((short) 3577, "Persoon.Reisdocument.Standaard", SoortElement.GROEP, null),
    /** Persoon.SamengesteldeNaam. */
    PERSOON_SAMENGESTELDENAAM((short) 3557, "Persoon.SamengesteldeNaam", SoortElement.GROEP, null),
    /** Persoon.UitsluitingKiesrecht. */
    PERSOON_UITSLUITINGKIESRECHT((short) 3519, "Persoon.UitsluitingKiesrecht", SoortElement.GROEP, null),
    /** Persoon.Verblijfsrecht. */
    PERSOON_VERBLIJFSRECHT((short) 3517, "Persoon.Verblijfsrecht", SoortElement.GROEP, null),
    /** Persoon.Verificatie.Identiteit. */
    PERSOON_VERIFICATIE_IDENTITEIT((short) 2076, "Persoon.Verificatie.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Verificatie.Standaard. */
    PERSOON_VERIFICATIE_STANDAARD((short) 3783, "Persoon.Verificatie.Standaard", SoortElement.GROEP, null),
    /** Persoon.Verstrekkingsbeperking.Identiteit. */
    PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT((short) 9347, "Persoon.Verstrekkingsbeperking.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Voornaam.Identiteit. */
    PERSOON_VOORNAAM_IDENTITEIT((short) 2065, "Persoon.Voornaam.Identiteit", SoortElement.GROEP, null),
    /** Persoon.Voornaam.Standaard. */
    PERSOON_VOORNAAM_STANDAARD((short) 3050, "Persoon.Voornaam.Standaard", SoortElement.GROEP, null),
    /** Relatie.Identiteit. */
    RELATIE_IDENTITEIT((short) 2070, "Relatie.Identiteit", SoortElement.GROEP, null),
    /** Relatie.Standaard. */
    RELATIE_STANDAARD((short) 3599, "Relatie.Standaard", SoortElement.GROEP, null),
    /** Terugmelding.Contactpersoon. */
    TERUGMELDING_CONTACTPERSOON((short) 11095, "Terugmelding.Contactpersoon", SoortElement.GROEP, null),
    /** Terugmelding.Identiteit. */
    TERUGMELDING_IDENTITEIT((short) 10717, "Terugmelding.Identiteit", SoortElement.GROEP, null),
    /** Terugmelding.Standaard. */
    TERUGMELDING_STANDAARD((short) 10739, "Terugmelding.Standaard", SoortElement.GROEP, null),
    /** Aangever.Code. */
    AANGEVER_CODE((short) 3480, "Aangever.Code", SoortElement.ATTRIBUUT, AANGEVER_IDENTITEIT),
    /** Aangever.Naam. */
    AANGEVER_NAAM((short) 3298, "Aangever.Naam", SoortElement.ATTRIBUUT, AANGEVER_IDENTITEIT),
    /** Aangever.ObjectSleutel. */
    AANGEVER_OBJECTSLEUTEL((short) 3296, "Aangever.ObjectSleutel", SoortElement.ATTRIBUUT, AANGEVER_IDENTITEIT),
    /** Aangever.Omschrijving. */
    AANGEVER_OMSCHRIJVING((short) 5619, "Aangever.Omschrijving", SoortElement.ATTRIBUUT, AANGEVER_IDENTITEIT),
    /** Actie.AdministratieveHandeling. */
    ACTIE_ADMINISTRATIEVEHANDELING((short) 9023, "Actie.AdministratieveHandeling", SoortElement.ATTRIBUUT, ACTIE_IDENTITEIT),
    /** Actie.DatumAanvangGeldigheid. */
    ACTIE_DATUMAANVANGGELDIGHEID((short) 6175, "Actie.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, ACTIE_IDENTITEIT),
    /** Actie.DatumEindeGeldigheid. */
    ACTIE_DATUMEINDEGELDIGHEID((short) 6265, "Actie.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, ACTIE_IDENTITEIT),
    /** Actie.DatumOntlening. */
    ACTIE_DATUMONTLENING((short) 10183, "Actie.DatumOntlening", SoortElement.ATTRIBUUT, ACTIE_IDENTITEIT),
    /** Actie.ObjectSleutel. */
    ACTIE_OBJECTSLEUTEL((short) 3054, "Actie.ObjectSleutel", SoortElement.ATTRIBUUT, ACTIE_IDENTITEIT),
    /** Actie.PartijCode. */
    ACTIE_PARTIJCODE((short) 3209, "Actie.PartijCode", SoortElement.ATTRIBUUT, ACTIE_IDENTITEIT),
    /** Actie.SoortNaam. */
    ACTIE_SOORTNAAM((short) 3055, "Actie.SoortNaam", SoortElement.ATTRIBUUT, ACTIE_IDENTITEIT),
    /** Actie.TijdstipRegistratie. */
    ACTIE_TIJDSTIPREGISTRATIE((short) 3181, "Actie.TijdstipRegistratie", SoortElement.ATTRIBUUT, ACTIE_IDENTITEIT),
    /** ActieBron.Actie. */
    ACTIEBRON_ACTIE((short) 8122, "ActieBron.Actie", SoortElement.ATTRIBUUT, ACTIEBRON_IDENTITEIT),
    /** ActieBron.Document. */
    ACTIEBRON_DOCUMENT((short) 8123, "ActieBron.Document", SoortElement.ATTRIBUUT, ACTIEBRON_IDENTITEIT),
    /** ActieBron.ObjectSleutel. */
    ACTIEBRON_OBJECTSLEUTEL((short) 8121, "ActieBron.ObjectSleutel", SoortElement.ATTRIBUUT, ACTIEBRON_IDENTITEIT),
    /** ActieBron.RechtsgrondCode. */
    ACTIEBRON_RECHTSGRONDCODE((short) 8124, "ActieBron.RechtsgrondCode", SoortElement.ATTRIBUUT, ACTIEBRON_IDENTITEIT),
    /** ActieBron.Rechtsgrondomschrijving. */
    ACTIEBRON_RECHTSGRONDOMSCHRIJVING((short) 10914, "ActieBron.Rechtsgrondomschrijving", SoortElement.ATTRIBUUT, ACTIEBRON_IDENTITEIT),
    /** AdministratieveHandeling.Bijhoudingsplan. */
    ADMINISTRATIEVEHANDELING_BIJHOUDINGSPLAN((short) 21549, "AdministratieveHandeling.Bijhoudingsplan", SoortElement.ATTRIBUUT,
            ADMINISTRATIEVEHANDELING_STANDAARD),
    /** AdministratieveHandeling.Levering.Tijdstip. */
    ADMINISTRATIEVEHANDELING_LEVERING_TIJDSTIP((short) 10052, "AdministratieveHandeling.Levering.Tijdstip", SoortElement.ATTRIBUUT,
            ADMINISTRATIEVEHANDELING_LEVERING),
    /** AdministratieveHandeling.ObjectSleutel. */
    ADMINISTRATIEVEHANDELING_OBJECTSLEUTEL((short) 9021, "AdministratieveHandeling.ObjectSleutel", SoortElement.ATTRIBUUT,
            ADMINISTRATIEVEHANDELING_IDENTITEIT),
    /** AdministratieveHandeling.PartijCode. */
    ADMINISTRATIEVEHANDELING_PARTIJCODE((short) 9172, "AdministratieveHandeling.PartijCode", SoortElement.ATTRIBUUT, ADMINISTRATIEVEHANDELING_IDENTITEIT),
    /** AdministratieveHandeling.SoortNaam. */
    ADMINISTRATIEVEHANDELING_SOORTNAAM((short) 9208, "AdministratieveHandeling.SoortNaam", SoortElement.ATTRIBUUT, ADMINISTRATIEVEHANDELING_IDENTITEIT),
    /** AdministratieveHandeling.TijdstipRegistratie. */
    ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE((short) 9505, "AdministratieveHandeling.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            ADMINISTRATIEVEHANDELING_IDENTITEIT),
    /** AdministratieveHandeling.ToelichtingOntlening. */
    ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING((short) 6174, "AdministratieveHandeling.ToelichtingOntlening", SoortElement.ATTRIBUUT,
            ADMINISTRATIEVEHANDELING_IDENTITEIT),
    /** AdministratieveHandelingGedeblokkeerdeMelding.AdministratieveHandeling. */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_ADMINISTRATIEVEHANDELING((short) 6226,
            "AdministratieveHandelingGedeblokkeerdeMelding.AdministratieveHandeling", SoortElement.ATTRIBUUT,
            ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_IDENTITEIT),
    /** AdministratieveHandelingGedeblokkeerdeMelding.GedeblokkeerdeMelding. */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_GEDEBLOKKEERDEMELDING((short) 6227,
            "AdministratieveHandelingGedeblokkeerdeMelding.GedeblokkeerdeMelding", SoortElement.ATTRIBUUT,
            ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_IDENTITEIT),
    /** AdministratieveHandelingGedeblokkeerdeMelding.ObjectSleutel. */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_OBJECTSLEUTEL((short) 6225, "AdministratieveHandelingGedeblokkeerdeMelding.ObjectSleutel",
            SoortElement.ATTRIBUUT, ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_IDENTITEIT),
    /** Betrokkenheid.ActieInhoud. */
    BETROKKENHEID_ACTIEINHOUD((short) 14273, "Betrokkenheid.ActieInhoud", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.ActieVerval. */
    BETROKKENHEID_ACTIEVERVAL((short) 14274, "Betrokkenheid.ActieVerval", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.ActieVervalTbvLeveringMutaties. */
    BETROKKENHEID_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18814, "Betrokkenheid.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.Betrokkenheid. */
    BETROKKENHEID_BETROKKENHEID((short) 14269, "Betrokkenheid.Betrokkenheid", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.IndicatieVoorkomenTbvLeveringMutaties. */
    BETROKKENHEID_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18815, "Betrokkenheid.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.NadereAanduidingVerval. */
    BETROKKENHEID_NADEREAANDUIDINGVERVAL((short) 14272, "Betrokkenheid.NadereAanduidingVerval", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.ObjectSleutel. */
    BETROKKENHEID_OBJECTSLEUTEL((short) 6102, "Betrokkenheid.ObjectSleutel", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.Persoon. */
    BETROKKENHEID_PERSOON((short) 3859, "Betrokkenheid.Persoon", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.Relatie. */
    BETROKKENHEID_RELATIE((short) 3860, "Betrokkenheid.Relatie", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.RolCode. */
    BETROKKENHEID_ROLCODE((short) 3861, "Betrokkenheid.RolCode", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.TijdstipRegistratie. */
    BETROKKENHEID_TIJDSTIPREGISTRATIE((short) 14270, "Betrokkenheid.TijdstipRegistratie", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.TijdstipVerval. */
    BETROKKENHEID_TIJDSTIPVERVAL((short) 14271, "Betrokkenheid.TijdstipVerval", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Betrokkenheid.VoorkomenSleutel. */
    BETROKKENHEID_VOORKOMENSLEUTEL((short) 14277, "Betrokkenheid.VoorkomenSleutel", SoortElement.ATTRIBUUT, BETROKKENHEID_IDENTITEIT),
    /** Document.ActieInhoud. */
    DOCUMENT_ACTIEINHOUD((short) 4046, "Document.ActieInhoud", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.ActieVerval. */
    DOCUMENT_ACTIEVERVAL((short) 4047, "Document.ActieVerval", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.ActieVervalTbvLeveringMutaties. */
    DOCUMENT_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18826, "Document.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.Aktenummer. */
    DOCUMENT_AKTENUMMER((short) 3786, "Document.Aktenummer", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.Document. */
    DOCUMENT_DOCUMENT((short) 4043, "Document.Document", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.Identificatie. */
    DOCUMENT_IDENTIFICATIE((short) 3160, "Document.Identificatie", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.IndicatieVoorkomenTbvLeveringMutaties. */
    DOCUMENT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18827, "Document.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            DOCUMENT_STANDAARD),
    /** Document.NadereAanduidingVerval. */
    DOCUMENT_NADEREAANDUIDINGVERVAL((short) 11118, "Document.NadereAanduidingVerval", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.ObjectSleutel. */
    DOCUMENT_OBJECTSLEUTEL((short) 3138, "Document.ObjectSleutel", SoortElement.ATTRIBUUT, DOCUMENT_IDENTITEIT),
    /** Document.Omschrijving. */
    DOCUMENT_OMSCHRIJVING((short) 3162, "Document.Omschrijving", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.PartijCode. */
    DOCUMENT_PARTIJCODE((short) 3139, "Document.PartijCode", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.SoortNaam. */
    DOCUMENT_SOORTNAAM((short) 3157, "Document.SoortNaam", SoortElement.ATTRIBUUT, DOCUMENT_IDENTITEIT),
    /** Document.TijdstipRegistratie. */
    DOCUMENT_TIJDSTIPREGISTRATIE((short) 4044, "Document.TijdstipRegistratie", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.TijdstipVerval. */
    DOCUMENT_TIJDSTIPVERVAL((short) 4045, "Document.TijdstipVerval", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Document.VoorkomenSleutel. */
    DOCUMENT_VOORKOMENSLEUTEL((short) 4515, "Document.VoorkomenSleutel", SoortElement.ATTRIBUUT, DOCUMENT_STANDAARD),
    /** Erkenner.ActieInhoud. */
    ERKENNER_ACTIEINHOUD((short) 14305, "Erkenner.ActieInhoud", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.ActieVerval. */
    ERKENNER_ACTIEVERVAL((short) 14306, "Erkenner.ActieVerval", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.ActieVervalTbvLeveringMutaties. */
    ERKENNER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18916, "Erkenner.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.Betrokkenheid. */
    ERKENNER_BETROKKENHEID((short) 14301, "Erkenner.Betrokkenheid", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.IndicatieVoorkomenTbvLeveringMutaties. */
    ERKENNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18917, "Erkenner.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            ERKENNER_IDENTITEIT),
    /** Erkenner.NadereAanduidingVerval. */
    ERKENNER_NADEREAANDUIDINGVERVAL((short) 14304, "Erkenner.NadereAanduidingVerval", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.ObjectSleutel. */
    ERKENNER_OBJECTSLEUTEL((short) 13799, "Erkenner.ObjectSleutel", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.Persoon. */
    ERKENNER_PERSOON((short) 13608, "Erkenner.Persoon", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.Relatie. */
    ERKENNER_RELATIE((short) 13606, "Erkenner.Relatie", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.RolCode. */
    ERKENNER_ROLCODE((short) 13607, "Erkenner.RolCode", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.TijdstipRegistratie. */
    ERKENNER_TIJDSTIPREGISTRATIE((short) 14302, "Erkenner.TijdstipRegistratie", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.TijdstipVerval. */
    ERKENNER_TIJDSTIPVERVAL((short) 14303, "Erkenner.TijdstipVerval", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** Erkenner.VoorkomenSleutel. */
    ERKENNER_VOORKOMENSLEUTEL((short) 14300, "Erkenner.VoorkomenSleutel", SoortElement.ATTRIBUUT, ERKENNER_IDENTITEIT),
    /** ErkenningOngeborenVrucht.ActieInhoud. */
    ERKENNINGONGEBORENVRUCHT_ACTIEINHOUD((short) 14353, "ErkenningOngeborenVrucht.ActieInhoud", SoortElement.ATTRIBUUT, ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.ActieVerval. */
    ERKENNINGONGEBORENVRUCHT_ACTIEVERVAL((short) 14354, "ErkenningOngeborenVrucht.ActieVerval", SoortElement.ATTRIBUUT, ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.ActieVervalTbvLeveringMutaties. */
    ERKENNINGONGEBORENVRUCHT_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18912, "ErkenningOngeborenVrucht.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.BuitenlandsePlaatsAanvang. */
    ERKENNINGONGEBORENVRUCHT_BUITENLANDSEPLAATSAANVANG((short) 14358, "ErkenningOngeborenVrucht.BuitenlandsePlaatsAanvang", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.BuitenlandsePlaatsEinde. */
    ERKENNINGONGEBORENVRUCHT_BUITENLANDSEPLAATSEINDE((short) 14366, "ErkenningOngeborenVrucht.BuitenlandsePlaatsEinde", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.BuitenlandseRegioAanvang. */
    ERKENNINGONGEBORENVRUCHT_BUITENLANDSEREGIOAANVANG((short) 14359, "ErkenningOngeborenVrucht.BuitenlandseRegioAanvang", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.BuitenlandseRegioEinde. */
    ERKENNINGONGEBORENVRUCHT_BUITENLANDSEREGIOEINDE((short) 14367, "ErkenningOngeborenVrucht.BuitenlandseRegioEinde", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.DatumAanvang. */
    ERKENNINGONGEBORENVRUCHT_DATUMAANVANG((short) 14355, "ErkenningOngeborenVrucht.DatumAanvang", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.DatumEinde. */
    ERKENNINGONGEBORENVRUCHT_DATUMEINDE((short) 14363, "ErkenningOngeborenVrucht.DatumEinde", SoortElement.ATTRIBUUT, ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.GemeenteAanvangCode. */
    ERKENNINGONGEBORENVRUCHT_GEMEENTEAANVANGCODE((short) 14356, "ErkenningOngeborenVrucht.GemeenteAanvangCode", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.GemeenteEindeCode. */
    ERKENNINGONGEBORENVRUCHT_GEMEENTEEINDECODE((short) 14364, "ErkenningOngeborenVrucht.GemeenteEindeCode", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.IndicatieVoorkomenTbvLeveringMutaties. */
    ERKENNINGONGEBORENVRUCHT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18913, "ErkenningOngeborenVrucht.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.LandGebiedAanvangCode. */
    ERKENNINGONGEBORENVRUCHT_LANDGEBIEDAANVANGCODE((short) 14361, "ErkenningOngeborenVrucht.LandGebiedAanvangCode", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.LandGebiedEindeCode. */
    ERKENNINGONGEBORENVRUCHT_LANDGEBIEDEINDECODE((short) 14369, "ErkenningOngeborenVrucht.LandGebiedEindeCode", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.NadereAanduidingVerval. */
    ERKENNINGONGEBORENVRUCHT_NADEREAANDUIDINGVERVAL((short) 14352, "ErkenningOngeborenVrucht.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.ObjectSleutel. */
    ERKENNINGONGEBORENVRUCHT_OBJECTSLEUTEL((short) 13797, "ErkenningOngeborenVrucht.ObjectSleutel", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_IDENTITEIT),
    /** ErkenningOngeborenVrucht.OmschrijvingLocatieAanvang. */
    ERKENNINGONGEBORENVRUCHT_OMSCHRIJVINGLOCATIEAANVANG((short) 14360, "ErkenningOngeborenVrucht.OmschrijvingLocatieAanvang", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.OmschrijvingLocatieEinde. */
    ERKENNINGONGEBORENVRUCHT_OMSCHRIJVINGLOCATIEEINDE((short) 14368, "ErkenningOngeborenVrucht.OmschrijvingLocatieEinde", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.RedenEindeCode. */
    ERKENNINGONGEBORENVRUCHT_REDENEINDECODE((short) 14362, "ErkenningOngeborenVrucht.RedenEindeCode", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.Relatie. */
    ERKENNINGONGEBORENVRUCHT_RELATIE((short) 14349, "ErkenningOngeborenVrucht.Relatie", SoortElement.ATTRIBUUT, ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.SoortCode. */
    ERKENNINGONGEBORENVRUCHT_SOORTCODE((short) 13600, "ErkenningOngeborenVrucht.SoortCode", SoortElement.ATTRIBUUT, ERKENNINGONGEBORENVRUCHT_IDENTITEIT),
    /** ErkenningOngeborenVrucht.TijdstipRegistratie. */
    ERKENNINGONGEBORENVRUCHT_TIJDSTIPREGISTRATIE((short) 14350, "ErkenningOngeborenVrucht.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.TijdstipVerval. */
    ERKENNINGONGEBORENVRUCHT_TIJDSTIPVERVAL((short) 14351, "ErkenningOngeborenVrucht.TijdstipVerval", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.VoorkomenSleutel. */
    ERKENNINGONGEBORENVRUCHT_VOORKOMENSLEUTEL((short) 14348, "ErkenningOngeborenVrucht.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.WoonplaatsnaamAanvang. */
    ERKENNINGONGEBORENVRUCHT_WOONPLAATSNAAMAANVANG((short) 14357, "ErkenningOngeborenVrucht.WoonplaatsnaamAanvang", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** ErkenningOngeborenVrucht.WoonplaatsnaamEinde. */
    ERKENNINGONGEBORENVRUCHT_WOONPLAATSNAAMEINDE((short) 14365, "ErkenningOngeborenVrucht.WoonplaatsnaamEinde", SoortElement.ATTRIBUUT,
            ERKENNINGONGEBORENVRUCHT_STANDAARD),
    /** FamilierechtelijkeBetrekking.ActieInhoud. */
    FAMILIERECHTELIJKEBETREKKING_ACTIEINHOUD((short) 14330, "FamilierechtelijkeBetrekking.ActieInhoud", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.ActieVerval. */
    FAMILIERECHTELIJKEBETREKKING_ACTIEVERVAL((short) 14331, "FamilierechtelijkeBetrekking.ActieVerval", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.ActieVervalTbvLeveringMutaties. */
    FAMILIERECHTELIJKEBETREKKING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18910, "FamilierechtelijkeBetrekking.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.BuitenlandsePlaatsAanvang. */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSAANVANG((short) 14335, "FamilierechtelijkeBetrekking.BuitenlandsePlaatsAanvang",
            SoortElement.ATTRIBUUT, FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.BuitenlandsePlaatsEinde. */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSEINDE((short) 14343, "FamilierechtelijkeBetrekking.BuitenlandsePlaatsEinde", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.BuitenlandseRegioAanvang. */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOAANVANG((short) 14336, "FamilierechtelijkeBetrekking.BuitenlandseRegioAanvang", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.BuitenlandseRegioEinde. */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOEINDE((short) 14344, "FamilierechtelijkeBetrekking.BuitenlandseRegioEinde", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.DatumAanvang. */
    FAMILIERECHTELIJKEBETREKKING_DATUMAANVANG((short) 14332, "FamilierechtelijkeBetrekking.DatumAanvang", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.DatumEinde. */
    FAMILIERECHTELIJKEBETREKKING_DATUMEINDE((short) 14340, "FamilierechtelijkeBetrekking.DatumEinde", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.GemeenteAanvangCode. */
    FAMILIERECHTELIJKEBETREKKING_GEMEENTEAANVANGCODE((short) 14333, "FamilierechtelijkeBetrekking.GemeenteAanvangCode", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.GemeenteEindeCode. */
    FAMILIERECHTELIJKEBETREKKING_GEMEENTEEINDECODE((short) 14341, "FamilierechtelijkeBetrekking.GemeenteEindeCode", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.IndicatieVoorkomenTbvLeveringMutaties. */
    FAMILIERECHTELIJKEBETREKKING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18911,
            "FamilierechtelijkeBetrekking.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.LandGebiedAanvangCode. */
    FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDAANVANGCODE((short) 14338, "FamilierechtelijkeBetrekking.LandGebiedAanvangCode", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.LandGebiedEindeCode. */
    FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDEINDECODE((short) 14346, "FamilierechtelijkeBetrekking.LandGebiedEindeCode", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.NadereAanduidingVerval. */
    FAMILIERECHTELIJKEBETREKKING_NADEREAANDUIDINGVERVAL((short) 14329, "FamilierechtelijkeBetrekking.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.ObjectSleutel. */
    FAMILIERECHTELIJKEBETREKKING_OBJECTSLEUTEL((short) 13796, "FamilierechtelijkeBetrekking.ObjectSleutel", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_IDENTITEIT),
    /** FamilierechtelijkeBetrekking.OmschrijvingLocatieAanvang. */
    FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEAANVANG((short) 14337, "FamilierechtelijkeBetrekking.OmschrijvingLocatieAanvang",
            SoortElement.ATTRIBUUT, FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.OmschrijvingLocatieEinde. */
    FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEEINDE((short) 14345, "FamilierechtelijkeBetrekking.OmschrijvingLocatieEinde", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.RedenEindeCode. */
    FAMILIERECHTELIJKEBETREKKING_REDENEINDECODE((short) 14339, "FamilierechtelijkeBetrekking.RedenEindeCode", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.Relatie. */
    FAMILIERECHTELIJKEBETREKKING_RELATIE((short) 14326, "FamilierechtelijkeBetrekking.Relatie", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.SoortCode. */
    FAMILIERECHTELIJKEBETREKKING_SOORTCODE((short) 13597, "FamilierechtelijkeBetrekking.SoortCode", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_IDENTITEIT),
    /** FamilierechtelijkeBetrekking.TijdstipRegistratie. */
    FAMILIERECHTELIJKEBETREKKING_TIJDSTIPREGISTRATIE((short) 14327, "FamilierechtelijkeBetrekking.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.TijdstipVerval. */
    FAMILIERECHTELIJKEBETREKKING_TIJDSTIPVERVAL((short) 14328, "FamilierechtelijkeBetrekking.TijdstipVerval", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.VoorkomenSleutel. */
    FAMILIERECHTELIJKEBETREKKING_VOORKOMENSLEUTEL((short) 14325, "FamilierechtelijkeBetrekking.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.WoonplaatsnaamAanvang. */
    FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMAANVANG((short) 14334, "FamilierechtelijkeBetrekking.WoonplaatsnaamAanvang", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** FamilierechtelijkeBetrekking.WoonplaatsnaamEinde. */
    FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMEINDE((short) 14342, "FamilierechtelijkeBetrekking.WoonplaatsnaamEinde", SoortElement.ATTRIBUUT,
            FAMILIERECHTELIJKEBETREKKING_STANDAARD),
    /** GedeblokkeerdeMelding.Melding. */
    GEDEBLOKKEERDEMELDING_MELDING((short) 6254, "GedeblokkeerdeMelding.Melding", SoortElement.ATTRIBUUT, GEDEBLOKKEERDEMELDING_IDENTITEIT),
    /** GedeblokkeerdeMelding.ObjectSleutel. */
    GEDEBLOKKEERDEMELDING_OBJECTSLEUTEL((short) 6219, "GedeblokkeerdeMelding.ObjectSleutel", SoortElement.ATTRIBUUT, GEDEBLOKKEERDEMELDING_IDENTITEIT),
    /** GedeblokkeerdeMelding.RegelCode. */
    GEDEBLOKKEERDEMELDING_REGELCODE((short) 6220, "GedeblokkeerdeMelding.RegelCode", SoortElement.ATTRIBUUT, GEDEBLOKKEERDEMELDING_IDENTITEIT),
    /** GegevenInOnderzoek.ElementNaam. */
    GEGEVENINONDERZOEK_ELEMENTNAAM((short) 3866, "GegevenInOnderzoek.ElementNaam", SoortElement.ATTRIBUUT, GEGEVENINONDERZOEK_IDENTITEIT),
    /** GegevenInOnderzoek.ID. */
    GEGEVENINONDERZOEK_ID((short) 10844, "GegevenInOnderzoek.ID", SoortElement.ATTRIBUUT, GEGEVENINONDERZOEK_IDENTITEIT),
    /** GegevenInOnderzoek.ObjectSleutelGegeven. */
    GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN((short) 3649, "GegevenInOnderzoek.ObjectSleutelGegeven", SoortElement.ATTRIBUUT, GEGEVENINONDERZOEK_IDENTITEIT),
    /** GegevenInOnderzoek.Onderzoek. */
    GEGEVENINONDERZOEK_ONDERZOEK((short) 3865, "GegevenInOnderzoek.Onderzoek", SoortElement.ATTRIBUUT, GEGEVENINONDERZOEK_IDENTITEIT),
    /** GegevenInOnderzoek.VoorkomenSleutelGegeven. */
    GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN((short) 14188, "GegevenInOnderzoek.VoorkomenSleutelGegeven", SoortElement.ATTRIBUUT,
            GEGEVENINONDERZOEK_IDENTITEIT),
    /** GegevenInTerugmelding.BetwijfeldeWaarde. */
    GEGEVENINTERUGMELDING_BETWIJFELDEWAARDE((short) 10761, "GegevenInTerugmelding.BetwijfeldeWaarde", SoortElement.ATTRIBUUT,
            GEGEVENINTERUGMELDING_IDENTITEIT),
    /** GegevenInTerugmelding.ElementNaam. */
    GEGEVENINTERUGMELDING_ELEMENTNAAM((short) 10759, "GegevenInTerugmelding.ElementNaam", SoortElement.ATTRIBUUT, GEGEVENINTERUGMELDING_IDENTITEIT),
    /** GegevenInTerugmelding.ObjectSleutel. */
    GEGEVENINTERUGMELDING_OBJECTSLEUTEL((short) 10756, "GegevenInTerugmelding.ObjectSleutel", SoortElement.ATTRIBUUT, GEGEVENINTERUGMELDING_IDENTITEIT),
    /** GegevenInTerugmelding.Terugmelding. */
    GEGEVENINTERUGMELDING_TERUGMELDING((short) 10758, "GegevenInTerugmelding.Terugmelding", SoortElement.ATTRIBUUT, GEGEVENINTERUGMELDING_IDENTITEIT),
    /** GegevenInTerugmelding.VerwachteWaarde. */
    GEGEVENINTERUGMELDING_VERWACHTEWAARDE((short) 10762, "GegevenInTerugmelding.VerwachteWaarde", SoortElement.ATTRIBUUT, GEGEVENINTERUGMELDING_IDENTITEIT),
    /** GeregistreerdPartnerschap.ActieInhoud. */
    GEREGISTREERDPARTNERSCHAP_ACTIEINHOUD((short) 14023, "GeregistreerdPartnerschap.ActieInhoud", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.ActieVerval. */
    GEREGISTREERDPARTNERSCHAP_ACTIEVERVAL((short) 14024, "GeregistreerdPartnerschap.ActieVerval", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.ActieVervalTbvLeveringMutaties. */
    GEREGISTREERDPARTNERSCHAP_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18924, "GeregistreerdPartnerschap.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.BuitenlandsePlaatsAanvang. */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG((short) 13584, "GeregistreerdPartnerschap.BuitenlandsePlaatsAanvang", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.BuitenlandsePlaatsEinde. */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE((short) 13592, "GeregistreerdPartnerschap.BuitenlandsePlaatsEinde", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.BuitenlandseRegioAanvang. */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG((short) 13585, "GeregistreerdPartnerschap.BuitenlandseRegioAanvang", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.BuitenlandseRegioEinde. */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE((short) 13593, "GeregistreerdPartnerschap.BuitenlandseRegioEinde", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.DatumAanvang. */
    GEREGISTREERDPARTNERSCHAP_DATUMAANVANG((short) 13581, "GeregistreerdPartnerschap.DatumAanvang", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.DatumEinde. */
    GEREGISTREERDPARTNERSCHAP_DATUMEINDE((short) 13589, "GeregistreerdPartnerschap.DatumEinde", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.GemeenteAanvangCode. */
    GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE((short) 13582, "GeregistreerdPartnerschap.GemeenteAanvangCode", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.GemeenteEindeCode. */
    GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE((short) 13590, "GeregistreerdPartnerschap.GemeenteEindeCode", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.IndicatieVoorkomenTbvLeveringMutaties. */
    GEREGISTREERDPARTNERSCHAP_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18925, "GeregistreerdPartnerschap.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.LandGebiedAanvangCode. */
    GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE((short) 13587, "GeregistreerdPartnerschap.LandGebiedAanvangCode", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.LandGebiedEindeCode. */
    GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE((short) 13595, "GeregistreerdPartnerschap.LandGebiedEindeCode", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.NadereAanduidingVerval. */
    GEREGISTREERDPARTNERSCHAP_NADEREAANDUIDINGVERVAL((short) 13578, "GeregistreerdPartnerschap.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.ObjectSleutel. */
    GEREGISTREERDPARTNERSCHAP_OBJECTSLEUTEL((short) 13794, "GeregistreerdPartnerschap.ObjectSleutel", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_IDENTITEIT),
    /** GeregistreerdPartnerschap.OmschrijvingLocatieAanvang. */
    GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG((short) 13586, "GeregistreerdPartnerschap.OmschrijvingLocatieAanvang", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.OmschrijvingLocatieEinde. */
    GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE((short) 13594, "GeregistreerdPartnerschap.OmschrijvingLocatieEinde", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.RedenEindeCode. */
    GEREGISTREERDPARTNERSCHAP_REDENEINDECODE((short) 13588, "GeregistreerdPartnerschap.RedenEindeCode", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.Relatie. */
    GEREGISTREERDPARTNERSCHAP_RELATIE((short) 13575, "GeregistreerdPartnerschap.Relatie", SoortElement.ATTRIBUUT, GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.SoortCode. */
    GEREGISTREERDPARTNERSCHAP_SOORTCODE((short) 13573, "GeregistreerdPartnerschap.SoortCode", SoortElement.ATTRIBUUT, GEREGISTREERDPARTNERSCHAP_IDENTITEIT),
    /** GeregistreerdPartnerschap.TijdstipRegistratie. */
    GEREGISTREERDPARTNERSCHAP_TIJDSTIPREGISTRATIE((short) 13576, "GeregistreerdPartnerschap.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.TijdstipVerval. */
    GEREGISTREERDPARTNERSCHAP_TIJDSTIPVERVAL((short) 13577, "GeregistreerdPartnerschap.TijdstipVerval", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.VoorkomenSleutel. */
    GEREGISTREERDPARTNERSCHAP_VOORKOMENSLEUTEL((short) 13795, "GeregistreerdPartnerschap.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.WoonplaatsnaamAanvang. */
    GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG((short) 13583, "GeregistreerdPartnerschap.WoonplaatsnaamAanvang", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GeregistreerdPartnerschap.WoonplaatsnaamEinde. */
    GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE((short) 13591, "GeregistreerdPartnerschap.WoonplaatsnaamEinde", SoortElement.ATTRIBUUT,
            GEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** GerelateerdeErkenner.ActieInhoud. */
    GERELATEERDEERKENNER_ACTIEINHOUD((short) 21190, "GerelateerdeErkenner.ActieInhoud", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.ActieVerval. */
    GERELATEERDEERKENNER_ACTIEVERVAL((short) 21191, "GerelateerdeErkenner.ActieVerval", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEERKENNER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21192, "GerelateerdeErkenner.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.Betrokkenheid. */
    GERELATEERDEERKENNER_BETROKKENHEID((short) 21186, "GerelateerdeErkenner.Betrokkenheid", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEERKENNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21193, "GerelateerdeErkenner.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.NadereAanduidingVerval. */
    GERELATEERDEERKENNER_NADEREAANDUIDINGVERVAL((short) 21189, "GerelateerdeErkenner.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.Persoon.Geboorte.ActieInhoud. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_ACTIEINHOUD((short) 14089, "GerelateerdeErkenner.Persoon.Geboorte.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.ActieVerval. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_ACTIEVERVAL((short) 14090, "GerelateerdeErkenner.Persoon.Geboorte.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18972,
            "GerelateerdeErkenner.Persoon.Geboorte.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.BuitenlandsePlaats. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 13286, "GerelateerdeErkenner.Persoon.Geboorte.BuitenlandsePlaats",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.BuitenlandseRegio. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 13281, "GerelateerdeErkenner.Persoon.Geboorte.BuitenlandseRegio",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.Datum. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_DATUM((short) 13283, "GerelateerdeErkenner.Persoon.Geboorte.Datum", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.GemeenteCode. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_GEMEENTECODE((short) 13284, "GerelateerdeErkenner.Persoon.Geboorte.GemeenteCode", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18973,
            "GerelateerdeErkenner.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.LandGebiedCode. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 13282, "GerelateerdeErkenner.Persoon.Geboorte.LandGebiedCode", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.NadereAanduidingVerval. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 13280, "GerelateerdeErkenner.Persoon.Geboorte.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.OmschrijvingLocatie. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 13287, "GerelateerdeErkenner.Persoon.Geboorte.OmschrijvingLocatie",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.Persoon. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_PERSOON((short) 13274, "GerelateerdeErkenner.Persoon.Geboorte.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.TijdstipRegistratie. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 13275, "GerelateerdeErkenner.Persoon.Geboorte.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.TijdstipVerval. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 13709, "GerelateerdeErkenner.Persoon.Geboorte.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.VoorkomenSleutel. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 13835, "GerelateerdeErkenner.Persoon.Geboorte.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geboorte.Woonplaatsnaam. */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 13285, "GerelateerdeErkenner.Persoon.Geboorte.Woonplaatsnaam", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GEBOORTE),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID((short) 14093,
            "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.ActieInhoud. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD((short) 14091, "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.ActieVerval. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL((short) 14092, "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18974,
            "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.Code. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_CODE((short) 13299, "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.Code", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID((short) 13290,
            "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID((short) 13291,
            "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18975,
            "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.NadereAanduidingVerval. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL((short) 13298,
            "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.Persoon. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_PERSOON((short) 13289, "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.TijdstipRegistratie. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE((short) 13292,
            "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.TijdstipVerval. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL((short) 13710, "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Geslachtsaanduiding.VoorkomenSleutel. */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL((short) 13836, "GerelateerdeErkenner.Persoon.Geslachtsaanduiding.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 14085,
            "GerelateerdeErkenner.Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.ActieInhoud. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 14083, "GerelateerdeErkenner.Persoon.Identificatienummers.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.ActieVerval. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 14084, "GerelateerdeErkenner.Persoon.Identificatienummers.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18968,
            "GerelateerdeErkenner.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.Administratienummer. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 13253,
            "GerelateerdeErkenner.Persoon.Identificatienummers.Administratienummer", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.Burgerservicenummer. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 13254,
            "GerelateerdeErkenner.Persoon.Identificatienummers.Burgerservicenummer", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 13244,
            "GerelateerdeErkenner.Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.DatumEindeGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 13245,
            "GerelateerdeErkenner.Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18969,
            "GerelateerdeErkenner.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.NadereAanduidingVerval. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 13252,
            "GerelateerdeErkenner.Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.Persoon. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 13243, "GerelateerdeErkenner.Persoon.Identificatienummers.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.TijdstipRegistratie. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 13246,
            "GerelateerdeErkenner.Persoon.Identificatienummers.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.TijdstipVerval. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 13707, "GerelateerdeErkenner.Persoon.Identificatienummers.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.Identificatienummers.VoorkomenSleutel. */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 13833,
            "GerelateerdeErkenner.Persoon.Identificatienummers.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 14088,
            "GerelateerdeErkenner.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.ActieInhoud. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 14086, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.ActieVerval. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 14087, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18970,
            "GerelateerdeErkenner.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 13266, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.AdellijkeTitelCode",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 13257,
            "GerelateerdeErkenner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 13258,
            "GerelateerdeErkenner.Persoon.SamengesteldeNaam.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 13269, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.Geslachtsnaamstam",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 14259, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.IndicatieAfgeleid",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 13272,
            "GerelateerdeErkenner.Persoon.SamengesteldeNaam.IndicatieNamenreeks", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18971,
            "GerelateerdeErkenner.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 13265,
            "GerelateerdeErkenner.Persoon.SamengesteldeNaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.Persoon. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_PERSOON((short) 13256, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.PredicaatCode. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 13267, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.PredicaatCode",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.Scheidingsteken. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 13270, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.Scheidingsteken",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 13259,
            "GerelateerdeErkenner.Persoon.SamengesteldeNaam.TijdstipRegistratie", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.TijdstipVerval. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 13708, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 13834, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.Voornamen. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 13268, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.Voornamen",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SamengesteldeNaam.Voorvoegsel. */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 13271, "GerelateerdeErkenner.Persoon.SamengesteldeNaam.Voorvoegsel",
            SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeErkenner.Persoon.SoortCode. */
    GERELATEERDEERKENNER_PERSOON_SOORTCODE((short) 14217, "GerelateerdeErkenner.Persoon.SoortCode", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_PERSOON_IDENTITEIT),
    /** GerelateerdeErkenner.RolCode. */
    GERELATEERDEERKENNER_ROLCODE((short) 21085, "GerelateerdeErkenner.RolCode", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.TijdstipRegistratie. */
    GERELATEERDEERKENNER_TIJDSTIPREGISTRATIE((short) 21187, "GerelateerdeErkenner.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.TijdstipVerval. */
    GERELATEERDEERKENNER_TIJDSTIPVERVAL((short) 21188, "GerelateerdeErkenner.TijdstipVerval", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeErkenner.VoorkomenSleutel. */
    GERELATEERDEERKENNER_VOORKOMENSLEUTEL((short) 21185, "GerelateerdeErkenner.VoorkomenSleutel", SoortElement.ATTRIBUUT, GERELATEERDEERKENNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.ActieInhoud. */
    GERELATEERDEGEREGISTREERDEPARTNER_ACTIEINHOUD((short) 21172, "GerelateerdeGeregistreerdePartner.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.ActieVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_ACTIEVERVAL((short) 21173, "GerelateerdeGeregistreerdePartner.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21174, "GerelateerdeGeregistreerdePartner.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.Betrokkenheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_BETROKKENHEID((short) 21168, "GerelateerdeGeregistreerdePartner.Betrokkenheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21175,
            "GerelateerdeGeregistreerdePartner.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.NadereAanduidingVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_NADEREAANDUIDINGVERVAL((short) 21171, "GerelateerdeGeregistreerdePartner.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.ActieInhoud. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_ACTIEINHOUD((short) 14067, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.ActieVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_ACTIEVERVAL((short) 14068, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18956,
            "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.BuitenlandsePlaats. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 13122,
            "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.BuitenlandsePlaats", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.BuitenlandseRegio. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 13117,
            "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.BuitenlandseRegio", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Datum. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_DATUM((short) 13119, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Datum",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.GemeenteCode. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_GEMEENTECODE((short) 13120, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.GemeenteCode",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18957,
            "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.LandGebiedCode. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 13118, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.LandGebiedCode",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.NadereAanduidingVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 13116,
            "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.OmschrijvingLocatie. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 13123,
            "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.OmschrijvingLocatie", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Persoon. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_PERSOON((short) 13110, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.TijdstipRegistratie. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 13111,
            "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.TijdstipVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 13673, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.VoorkomenSleutel. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 13825,
            "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Woonplaatsnaam. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 13121, "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Woonplaatsnaam",
            SoortElement.ATTRIBUUT, GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID((short) 14071,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.ActieInhoud. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD((short) 14069,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.ActieVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL((short) 14070,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18958,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.Code. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE((short) 13135,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.Code", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID((short) 13126,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID((short) 13127,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18959,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.NadereAanduidingVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL((short) 13134,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.Persoon. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_PERSOON((short) 13125,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.TijdstipRegistratie. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE((short) 13128,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.TijdstipVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL((short) 13674,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.VoorkomenSleutel. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL((short) 13826,
            "GerelateerdeGeregistreerdePartner.Persoon.Geslachtsaanduiding.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 14063,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.ActieInhoud. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 14061,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.ActieVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 14062,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18952,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.Administratienummer. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 13089,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.Administratienummer", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.Burgerservicenummer. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 13090,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.Burgerservicenummer", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 13080,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.DatumEindeGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 13081,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18953,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.NadereAanduidingVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 13088,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.Persoon. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 13079,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.TijdstipRegistratie. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 13082,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.TijdstipVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 13671,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.VoorkomenSleutel. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 13823,
            "GerelateerdeGeregistreerdePartner.Persoon.Identificatienummers.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 14066,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.ActieInhoud. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 14064,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.ActieVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 14065,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18954,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 13102,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.AdellijkeTitelCode", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 13093,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 13094,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 13105,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Geslachtsnaamstam", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 14257,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.IndicatieAfgeleid", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 13108,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.IndicatieNamenreeks", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18955,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 13101,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Persoon. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_PERSOON((short) 13092,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.PredicaatCode. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 13103,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.PredicaatCode", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Scheidingsteken. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 13106,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Scheidingsteken", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 13095,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.TijdstipVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 13672,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 13824,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Voornamen. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 13104,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Voornamen", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Voorvoegsel. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 13107,
            "GerelateerdeGeregistreerdePartner.Persoon.SamengesteldeNaam.Voorvoegsel", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeGeregistreerdePartner.Persoon.SoortCode. */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SOORTCODE((short) 14215, "GerelateerdeGeregistreerdePartner.Persoon.SoortCode", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.RolCode. */
    GERELATEERDEGEREGISTREERDEPARTNER_ROLCODE((short) 21083, "GerelateerdeGeregistreerdePartner.RolCode", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.TijdstipRegistratie. */
    GERELATEERDEGEREGISTREERDEPARTNER_TIJDSTIPREGISTRATIE((short) 21169, "GerelateerdeGeregistreerdePartner.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.TijdstipVerval. */
    GERELATEERDEGEREGISTREERDEPARTNER_TIJDSTIPVERVAL((short) 21170, "GerelateerdeGeregistreerdePartner.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeGeregistreerdePartner.VoorkomenSleutel. */
    GERELATEERDEGEREGISTREERDEPARTNER_VOORKOMENSLEUTEL((short) 21167, "GerelateerdeGeregistreerdePartner.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.ActieInhoud. */
    GERELATEERDEHUWELIJKSPARTNER_ACTIEINHOUD((short) 21163, "GerelateerdeHuwelijkspartner.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.ActieVerval. */
    GERELATEERDEHUWELIJKSPARTNER_ACTIEVERVAL((short) 21164, "GerelateerdeHuwelijkspartner.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21165, "GerelateerdeHuwelijkspartner.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.Betrokkenheid. */
    GERELATEERDEHUWELIJKSPARTNER_BETROKKENHEID((short) 21159, "GerelateerdeHuwelijkspartner.Betrokkenheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21166,
            "GerelateerdeHuwelijkspartner.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.NadereAanduidingVerval. */
    GERELATEERDEHUWELIJKSPARTNER_NADEREAANDUIDINGVERVAL((short) 21162, "GerelateerdeHuwelijkspartner.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.ActieInhoud. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_ACTIEINHOUD((short) 14056, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.ActieVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_ACTIEVERVAL((short) 14057, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18948,
            "GerelateerdeHuwelijkspartner.Persoon.Geboorte.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.BuitenlandsePlaats. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 13040, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.BuitenlandsePlaats",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.BuitenlandseRegio. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 13035, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.BuitenlandseRegio",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.Datum. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM((short) 13037, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.Datum", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.GemeenteCode. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_GEMEENTECODE((short) 13038, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.GemeenteCode",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18949,
            "GerelateerdeHuwelijkspartner.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.LandGebiedCode. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 13036, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.LandGebiedCode",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.NadereAanduidingVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 13034,
            "GerelateerdeHuwelijkspartner.Persoon.Geboorte.NadereAanduidingVerval", SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.OmschrijvingLocatie. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 13041, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.OmschrijvingLocatie",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.Persoon. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_PERSOON((short) 13028, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.TijdstipRegistratie. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 13029, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.TijdstipVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 13655, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.VoorkomenSleutel. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 13820, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geboorte.Woonplaatsnaam. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 13039, "GerelateerdeHuwelijkspartner.Persoon.Geboorte.Woonplaatsnaam",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID((short) 14060,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.ActieInhoud. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD((short) 14058,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.ActieVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL((short) 14059,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18950,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.Code. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE((short) 13053, "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.Code",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID((short) 13044,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID((short) 13045,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18951,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.NadereAanduidingVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL((short) 13052,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.Persoon. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_PERSOON((short) 13043, "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.TijdstipRegistratie. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE((short) 13046,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.TijdstipVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL((short) 13656,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.VoorkomenSleutel. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL((short) 13821,
            "GerelateerdeHuwelijkspartner.Persoon.Geslachtsaanduiding.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 14052,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.ActieInhoud. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 14050,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.ActieVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 14051,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18944,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.Administratienummer. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 13007,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.Administratienummer", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.Burgerservicenummer. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 13008,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.Burgerservicenummer", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 12998,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.DatumEindeGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 12999,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18945,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.NadereAanduidingVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 13006,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.Persoon. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 12997, "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.TijdstipRegistratie. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 13000,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.TijdstipVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 13653,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.VoorkomenSleutel. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 13818,
            "GerelateerdeHuwelijkspartner.Persoon.Identificatienummers.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 14055,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieInhoud. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 14053,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 14054,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18946,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 13020,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.AdellijkeTitelCode", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 13011,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 13012,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 13023,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Geslachtsnaamstam", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 14256,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.IndicatieAfgeleid", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 13026,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.IndicatieNamenreeks", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18947,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 13019,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Persoon. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_PERSOON((short) 13010, "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.PredicaatCode. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 13021,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.PredicaatCode", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Scheidingsteken. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 13024,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Scheidingsteken", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 13013,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.TijdstipVerval. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 13654,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 13819,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voornamen. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 13022, "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voornamen",
            SoortElement.ATTRIBUUT, GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voorvoegsel. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 13025,
            "GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Voorvoegsel", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeHuwelijkspartner.Persoon.SoortCode. */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SOORTCODE((short) 14214, "GerelateerdeHuwelijkspartner.Persoon.SoortCode", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.RolCode. */
    GERELATEERDEHUWELIJKSPARTNER_ROLCODE((short) 21082, "GerelateerdeHuwelijkspartner.RolCode", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.TijdstipRegistratie. */
    GERELATEERDEHUWELIJKSPARTNER_TIJDSTIPREGISTRATIE((short) 21160, "GerelateerdeHuwelijkspartner.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.TijdstipVerval. */
    GERELATEERDEHUWELIJKSPARTNER_TIJDSTIPVERVAL((short) 21161, "GerelateerdeHuwelijkspartner.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeHuwelijkspartner.VoorkomenSleutel. */
    GERELATEERDEHUWELIJKSPARTNER_VOORKOMENSLEUTEL((short) 21158, "GerelateerdeHuwelijkspartner.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT),
    /** GerelateerdeInstemmer.ActieInhoud. */
    GERELATEERDEINSTEMMER_ACTIEINHOUD((short) 21181, "GerelateerdeInstemmer.ActieInhoud", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.ActieVerval. */
    GERELATEERDEINSTEMMER_ACTIEVERVAL((short) 21182, "GerelateerdeInstemmer.ActieVerval", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21183, "GerelateerdeInstemmer.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.Betrokkenheid. */
    GERELATEERDEINSTEMMER_BETROKKENHEID((short) 21177, "GerelateerdeInstemmer.Betrokkenheid", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21184, "GerelateerdeInstemmer.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.NadereAanduidingVerval. */
    GERELATEERDEINSTEMMER_NADEREAANDUIDINGVERVAL((short) 21180, "GerelateerdeInstemmer.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.Persoon.Geboorte.ActieInhoud. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_ACTIEINHOUD((short) 14078, "GerelateerdeInstemmer.Persoon.Geboorte.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.ActieVerval. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_ACTIEVERVAL((short) 14079, "GerelateerdeInstemmer.Persoon.Geboorte.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18964,
            "GerelateerdeInstemmer.Persoon.Geboorte.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.BuitenlandsePlaats. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 13204, "GerelateerdeInstemmer.Persoon.Geboorte.BuitenlandsePlaats",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.BuitenlandseRegio. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 13199, "GerelateerdeInstemmer.Persoon.Geboorte.BuitenlandseRegio",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.Datum. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_DATUM((short) 13201, "GerelateerdeInstemmer.Persoon.Geboorte.Datum", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.GemeenteCode. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_GEMEENTECODE((short) 13202, "GerelateerdeInstemmer.Persoon.Geboorte.GemeenteCode", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18965,
            "GerelateerdeInstemmer.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.LandGebiedCode. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 13200, "GerelateerdeInstemmer.Persoon.Geboorte.LandGebiedCode", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.NadereAanduidingVerval. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 13198, "GerelateerdeInstemmer.Persoon.Geboorte.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.OmschrijvingLocatie. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 13205, "GerelateerdeInstemmer.Persoon.Geboorte.OmschrijvingLocatie",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.Persoon. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_PERSOON((short) 13192, "GerelateerdeInstemmer.Persoon.Geboorte.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.TijdstipRegistratie. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 13193, "GerelateerdeInstemmer.Persoon.Geboorte.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.TijdstipVerval. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 13691, "GerelateerdeInstemmer.Persoon.Geboorte.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.VoorkomenSleutel. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 13830, "GerelateerdeInstemmer.Persoon.Geboorte.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geboorte.Woonplaatsnaam. */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 13203, "GerelateerdeInstemmer.Persoon.Geboorte.Woonplaatsnaam", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GEBOORTE),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID((short) 14082,
            "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.ActieInhoud. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD((short) 14080, "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.ActieVerval. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL((short) 14081, "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18966,
            "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.Code. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_CODE((short) 13217, "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.Code",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID((short) 13208,
            "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID((short) 13209,
            "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18967,
            "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.NadereAanduidingVerval. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL((short) 13216,
            "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.Persoon. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_PERSOON((short) 13207, "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.TijdstipRegistratie. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE((short) 13210,
            "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.TijdstipVerval. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL((short) 13692, "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.VoorkomenSleutel. */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL((short) 13831,
            "GerelateerdeInstemmer.Persoon.Geslachtsaanduiding.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 14074,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.ActieInhoud. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 14072, "GerelateerdeInstemmer.Persoon.Identificatienummers.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.ActieVerval. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 14073, "GerelateerdeInstemmer.Persoon.Identificatienummers.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18960,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.Administratienummer. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 13171,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.Administratienummer", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.Burgerservicenummer. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 13172,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.Burgerservicenummer", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 13162,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.DatumEindeGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 13163,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18961,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.NadereAanduidingVerval. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 13170,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.Persoon. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 13161, "GerelateerdeInstemmer.Persoon.Identificatienummers.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.TijdstipRegistratie. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 13164,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.TijdstipVerval. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 13689, "GerelateerdeInstemmer.Persoon.Identificatienummers.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.Identificatienummers.VoorkomenSleutel. */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 13828,
            "GerelateerdeInstemmer.Persoon.Identificatienummers.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 14077,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.ActieInhoud. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 14075, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.ActieVerval. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 14076, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18962,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 13184,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.AdellijkeTitelCode", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 13175,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 13176,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 13187, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Geslachtsnaamstam",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 14258, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.IndicatieAfgeleid",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 13190,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.IndicatieNamenreeks", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18963,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 13183,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Persoon. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_PERSOON((short) 13174, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.PredicaatCode. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 13185, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.PredicaatCode",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Scheidingsteken. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 13188, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Scheidingsteken",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 13177,
            "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.TijdstipRegistratie", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.TijdstipVerval. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 13690, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 13829, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Voornamen. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 13186, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Voornamen",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Voorvoegsel. */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 13189, "GerelateerdeInstemmer.Persoon.SamengesteldeNaam.Voorvoegsel",
            SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeInstemmer.Persoon.SoortCode. */
    GERELATEERDEINSTEMMER_PERSOON_SOORTCODE((short) 14216, "GerelateerdeInstemmer.Persoon.SoortCode", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_PERSOON_IDENTITEIT),
    /** GerelateerdeInstemmer.RolCode. */
    GERELATEERDEINSTEMMER_ROLCODE((short) 21084, "GerelateerdeInstemmer.RolCode", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.TijdstipRegistratie. */
    GERELATEERDEINSTEMMER_TIJDSTIPREGISTRATIE((short) 21178, "GerelateerdeInstemmer.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.TijdstipVerval. */
    GERELATEERDEINSTEMMER_TIJDSTIPVERVAL((short) 21179, "GerelateerdeInstemmer.TijdstipVerval", SoortElement.ATTRIBUUT, GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeInstemmer.VoorkomenSleutel. */
    GERELATEERDEINSTEMMER_VOORKOMENSLEUTEL((short) 21176, "GerelateerdeInstemmer.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEINSTEMMER_IDENTITEIT),
    /** GerelateerdeKind.ActieInhoud. */
    GERELATEERDEKIND_ACTIEINHOUD((short) 21154, "GerelateerdeKind.ActieInhoud", SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.ActieVerval. */
    GERELATEERDEKIND_ACTIEVERVAL((short) 21155, "GerelateerdeKind.ActieVerval", SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEKIND_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21156, "GerelateerdeKind.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.Betrokkenheid. */
    GERELATEERDEKIND_BETROKKENHEID((short) 21150, "GerelateerdeKind.Betrokkenheid", SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEKIND_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21157, "GerelateerdeKind.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.NadereAanduidingVerval. */
    GERELATEERDEKIND_NADEREAANDUIDINGVERVAL((short) 21153, "GerelateerdeKind.NadereAanduidingVerval", SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.Persoon.Geboorte.ActieInhoud. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_ACTIEINHOUD((short) 14048, "GerelateerdeKind.Persoon.Geboorte.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.ActieVerval. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_ACTIEVERVAL((short) 14049, "GerelateerdeKind.Persoon.Geboorte.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18942, "GerelateerdeKind.Persoon.Geboorte.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.BuitenlandsePlaats. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 12969, "GerelateerdeKind.Persoon.Geboorte.BuitenlandsePlaats", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.BuitenlandseRegio. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 12964, "GerelateerdeKind.Persoon.Geboorte.BuitenlandseRegio", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.Datum. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM((short) 12966, "GerelateerdeKind.Persoon.Geboorte.Datum", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.GemeenteCode. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_GEMEENTECODE((short) 12967, "GerelateerdeKind.Persoon.Geboorte.GemeenteCode", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18943,
            "GerelateerdeKind.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.LandGebiedCode. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 12965, "GerelateerdeKind.Persoon.Geboorte.LandGebiedCode", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.NadereAanduidingVerval. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 12963, "GerelateerdeKind.Persoon.Geboorte.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.OmschrijvingLocatie. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 12970, "GerelateerdeKind.Persoon.Geboorte.OmschrijvingLocatie", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.Persoon. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_PERSOON((short) 12957, "GerelateerdeKind.Persoon.Geboorte.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.TijdstipRegistratie. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 12958, "GerelateerdeKind.Persoon.Geboorte.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.TijdstipVerval. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 13638, "GerelateerdeKind.Persoon.Geboorte.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.VoorkomenSleutel. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 13816, "GerelateerdeKind.Persoon.Geboorte.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Geboorte.Woonplaatsnaam. */
    GERELATEERDEKIND_PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 12968, "GerelateerdeKind.Persoon.Geboorte.Woonplaatsnaam", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_GEBOORTE),
    /** GerelateerdeKind.Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 14044,
            "GerelateerdeKind.Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.ActieInhoud. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 14042, "GerelateerdeKind.Persoon.Identificatienummers.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.ActieVerval. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 14043, "GerelateerdeKind.Persoon.Identificatienummers.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18938,
            "GerelateerdeKind.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.Administratienummer. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 12936, "GerelateerdeKind.Persoon.Identificatienummers.Administratienummer",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.Burgerservicenummer. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 12937, "GerelateerdeKind.Persoon.Identificatienummers.Burgerservicenummer",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 12927,
            "GerelateerdeKind.Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.DatumEindeGeldigheid. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 12928,
            "GerelateerdeKind.Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18939,
            "GerelateerdeKind.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.NadereAanduidingVerval. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 12935,
            "GerelateerdeKind.Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.Persoon. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 12926, "GerelateerdeKind.Persoon.Identificatienummers.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.TijdstipRegistratie. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 12929, "GerelateerdeKind.Persoon.Identificatienummers.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.TijdstipVerval. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 13636, "GerelateerdeKind.Persoon.Identificatienummers.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.Identificatienummers.VoorkomenSleutel. */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 13814, "GerelateerdeKind.Persoon.Identificatienummers.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 14047,
            "GerelateerdeKind.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.ActieInhoud. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 14045, "GerelateerdeKind.Persoon.SamengesteldeNaam.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.ActieVerval. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 14046, "GerelateerdeKind.Persoon.SamengesteldeNaam.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18940,
            "GerelateerdeKind.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 12949, "GerelateerdeKind.Persoon.SamengesteldeNaam.AdellijkeTitelCode",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 12940, "GerelateerdeKind.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 12941, "GerelateerdeKind.Persoon.SamengesteldeNaam.DatumEindeGeldigheid",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 12952, "GerelateerdeKind.Persoon.SamengesteldeNaam.Geslachtsnaamstam",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 14255, "GerelateerdeKind.Persoon.SamengesteldeNaam.IndicatieAfgeleid",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 12955, "GerelateerdeKind.Persoon.SamengesteldeNaam.IndicatieNamenreeks",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18941,
            "GerelateerdeKind.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 12948, "GerelateerdeKind.Persoon.SamengesteldeNaam.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.Persoon. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_PERSOON((short) 12939, "GerelateerdeKind.Persoon.SamengesteldeNaam.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.PredicaatCode. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 12950, "GerelateerdeKind.Persoon.SamengesteldeNaam.PredicaatCode",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.Scheidingsteken. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 12953, "GerelateerdeKind.Persoon.SamengesteldeNaam.Scheidingsteken",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 12942, "GerelateerdeKind.Persoon.SamengesteldeNaam.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.TijdstipVerval. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 13637, "GerelateerdeKind.Persoon.SamengesteldeNaam.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 13815, "GerelateerdeKind.Persoon.SamengesteldeNaam.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.Voornamen. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 12951, "GerelateerdeKind.Persoon.SamengesteldeNaam.Voornamen", SoortElement.ATTRIBUUT,
            GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SamengesteldeNaam.Voorvoegsel. */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 12954, "GerelateerdeKind.Persoon.SamengesteldeNaam.Voorvoegsel",
            SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeKind.Persoon.SoortCode. */
    GERELATEERDEKIND_PERSOON_SOORTCODE((short) 14213, "GerelateerdeKind.Persoon.SoortCode", SoortElement.ATTRIBUUT, GERELATEERDEKIND_PERSOON_IDENTITEIT),
    /** GerelateerdeKind.RolCode. */
    GERELATEERDEKIND_ROLCODE((short) 21081, "GerelateerdeKind.RolCode", SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.TijdstipRegistratie. */
    GERELATEERDEKIND_TIJDSTIPREGISTRATIE((short) 21151, "GerelateerdeKind.TijdstipRegistratie", SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.TijdstipVerval. */
    GERELATEERDEKIND_TIJDSTIPVERVAL((short) 21152, "GerelateerdeKind.TijdstipVerval", SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeKind.VoorkomenSleutel. */
    GERELATEERDEKIND_VOORKOMENSLEUTEL((short) 21149, "GerelateerdeKind.VoorkomenSleutel", SoortElement.ATTRIBUUT, GERELATEERDEKIND_IDENTITEIT),
    /** GerelateerdeNaamgever.ActieInhoud. */
    GERELATEERDENAAMGEVER_ACTIEINHOUD((short) 21199, "GerelateerdeNaamgever.ActieInhoud", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.ActieVerval. */
    GERELATEERDENAAMGEVER_ACTIEVERVAL((short) 21200, "GerelateerdeNaamgever.ActieVerval", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21201, "GerelateerdeNaamgever.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.Betrokkenheid. */
    GERELATEERDENAAMGEVER_BETROKKENHEID((short) 21195, "GerelateerdeNaamgever.Betrokkenheid", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21202, "GerelateerdeNaamgever.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.NadereAanduidingVerval. */
    GERELATEERDENAAMGEVER_NADEREAANDUIDINGVERVAL((short) 21198, "GerelateerdeNaamgever.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.Persoon.Geboorte.ActieInhoud. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_ACTIEINHOUD((short) 14100, "GerelateerdeNaamgever.Persoon.Geboorte.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.ActieVerval. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_ACTIEVERVAL((short) 14101, "GerelateerdeNaamgever.Persoon.Geboorte.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18980,
            "GerelateerdeNaamgever.Persoon.Geboorte.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.BuitenlandsePlaats. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 13368, "GerelateerdeNaamgever.Persoon.Geboorte.BuitenlandsePlaats",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.BuitenlandseRegio. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 13363, "GerelateerdeNaamgever.Persoon.Geboorte.BuitenlandseRegio",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.Datum. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_DATUM((short) 13365, "GerelateerdeNaamgever.Persoon.Geboorte.Datum", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.GemeenteCode. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_GEMEENTECODE((short) 13366, "GerelateerdeNaamgever.Persoon.Geboorte.GemeenteCode", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18981,
            "GerelateerdeNaamgever.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.LandGebiedCode. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 13364, "GerelateerdeNaamgever.Persoon.Geboorte.LandGebiedCode", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.NadereAanduidingVerval. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 13362, "GerelateerdeNaamgever.Persoon.Geboorte.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.OmschrijvingLocatie. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 13369, "GerelateerdeNaamgever.Persoon.Geboorte.OmschrijvingLocatie",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.Persoon. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_PERSOON((short) 13356, "GerelateerdeNaamgever.Persoon.Geboorte.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.TijdstipRegistratie. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 13357, "GerelateerdeNaamgever.Persoon.Geboorte.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.TijdstipVerval. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 13727, "GerelateerdeNaamgever.Persoon.Geboorte.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.VoorkomenSleutel. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 13840, "GerelateerdeNaamgever.Persoon.Geboorte.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geboorte.Woonplaatsnaam. */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 13367, "GerelateerdeNaamgever.Persoon.Geboorte.Woonplaatsnaam", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID((short) 14104,
            "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.ActieInhoud. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD((short) 14102, "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.ActieVerval. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL((short) 14103, "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18982,
            "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.Code. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_CODE((short) 13381, "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.Code",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID((short) 13372,
            "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID((short) 13373,
            "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18983,
            "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.NadereAanduidingVerval. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL((short) 13380,
            "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.Persoon. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_PERSOON((short) 13371, "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.TijdstipRegistratie. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE((short) 13374,
            "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.TijdstipVerval. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL((short) 13728, "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.VoorkomenSleutel. */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL((short) 13841,
            "GerelateerdeNaamgever.Persoon.Geslachtsaanduiding.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 14096,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.ActieInhoud. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 14094, "GerelateerdeNaamgever.Persoon.Identificatienummers.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.ActieVerval. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 14095, "GerelateerdeNaamgever.Persoon.Identificatienummers.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18976,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.Administratienummer. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 13335,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.Administratienummer", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.Burgerservicenummer. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 13336,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.Burgerservicenummer", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 13326,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.DatumEindeGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 13327,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18977,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.NadereAanduidingVerval. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 13334,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.Persoon. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 13325, "GerelateerdeNaamgever.Persoon.Identificatienummers.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.TijdstipRegistratie. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 13328,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.TijdstipVerval. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 13725, "GerelateerdeNaamgever.Persoon.Identificatienummers.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.Identificatienummers.VoorkomenSleutel. */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 13838,
            "GerelateerdeNaamgever.Persoon.Identificatienummers.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 14099,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.ActieInhoud. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 14097, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.ActieVerval. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 14098, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18978,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 13348,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.AdellijkeTitelCode", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 13339,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 13340,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 13351, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Geslachtsnaamstam",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 14260, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.IndicatieAfgeleid",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 13354,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.IndicatieNamenreeks", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18979,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 13347,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Persoon. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_PERSOON((short) 13338, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.PredicaatCode. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 13349, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.PredicaatCode",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Scheidingsteken. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 13352, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Scheidingsteken",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 13341,
            "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.TijdstipRegistratie", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.TijdstipVerval. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 13726, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 13839, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Voornamen. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 13350, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Voornamen",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Voorvoegsel. */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 13353, "GerelateerdeNaamgever.Persoon.SamengesteldeNaam.Voorvoegsel",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamgever.Persoon.SoortCode. */
    GERELATEERDENAAMGEVER_PERSOON_SOORTCODE((short) 14218, "GerelateerdeNaamgever.Persoon.SoortCode", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_PERSOON_IDENTITEIT),
    /** GerelateerdeNaamgever.RolCode. */
    GERELATEERDENAAMGEVER_ROLCODE((short) 21086, "GerelateerdeNaamgever.RolCode", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.TijdstipRegistratie. */
    GERELATEERDENAAMGEVER_TIJDSTIPREGISTRATIE((short) 21196, "GerelateerdeNaamgever.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.TijdstipVerval. */
    GERELATEERDENAAMGEVER_TIJDSTIPVERVAL((short) 21197, "GerelateerdeNaamgever.TijdstipVerval", SoortElement.ATTRIBUUT, GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamgever.VoorkomenSleutel. */
    GERELATEERDENAAMGEVER_VOORKOMENSLEUTEL((short) 21194, "GerelateerdeNaamgever.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMGEVER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.ActieInhoud. */
    GERELATEERDENAAMSKEUZEPARTNER_ACTIEINHOUD((short) 21208, "GerelateerdeNaamskeuzePartner.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.ActieVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_ACTIEVERVAL((short) 21209, "GerelateerdeNaamskeuzePartner.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21210, "GerelateerdeNaamskeuzePartner.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.Betrokkenheid. */
    GERELATEERDENAAMSKEUZEPARTNER_BETROKKENHEID((short) 21204, "GerelateerdeNaamskeuzePartner.Betrokkenheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21211,
            "GerelateerdeNaamskeuzePartner.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.NadereAanduidingVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_NADEREAANDUIDINGVERVAL((short) 21207, "GerelateerdeNaamskeuzePartner.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.ActieInhoud. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_ACTIEINHOUD((short) 14111, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.ActieVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_ACTIEVERVAL((short) 14112, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18988,
            "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.BuitenlandsePlaats. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 13450, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.BuitenlandsePlaats",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.BuitenlandseRegio. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 13445, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.BuitenlandseRegio",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.Datum. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_DATUM((short) 13447, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.Datum", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.GemeenteCode. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_GEMEENTECODE((short) 13448, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.GemeenteCode",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18989,
            "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.LandGebiedCode. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 13446, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.LandGebiedCode",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.NadereAanduidingVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 13444,
            "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.OmschrijvingLocatie. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 13451,
            "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.OmschrijvingLocatie", SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.Persoon. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_PERSOON((short) 13438, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.TijdstipRegistratie. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 13439,
            "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.TijdstipRegistratie", SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.TijdstipVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 13745, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.VoorkomenSleutel. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 13845, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geboorte.Woonplaatsnaam. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 13449, "GerelateerdeNaamskeuzePartner.Persoon.Geboorte.Woonplaatsnaam",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID((short) 14115,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.ActieInhoud. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD((short) 14113,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.ActieVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL((short) 14114,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18990,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.Code. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE((short) 13463, "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.Code",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID((short) 13454,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID((short) 13455,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18991,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.NadereAanduidingVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL((short) 13462,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.Persoon. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_PERSOON((short) 13453, "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.TijdstipRegistratie. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE((short) 13456,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.TijdstipVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL((short) 13746,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.VoorkomenSleutel. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL((short) 13846,
            "GerelateerdeNaamskeuzePartner.Persoon.Geslachtsaanduiding.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 14107,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.ActieInhoud. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 14105,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.ActieVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 14106,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18984,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.Administratienummer. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 13417,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.Administratienummer", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.Burgerservicenummer. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 13418,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.Burgerservicenummer", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 13408,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.DatumEindeGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 13409,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18985,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.NadereAanduidingVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 13416,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.Persoon. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 13407,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.TijdstipRegistratie. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 13410,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.TijdstipVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 13743,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.VoorkomenSleutel. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 13843,
            "GerelateerdeNaamskeuzePartner.Persoon.Identificatienummers.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 14110,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.ActieInhoud. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 14108,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.ActieVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 14109,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18986,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 13430,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.AdellijkeTitelCode", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 13421,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 13422,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 13433,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Geslachtsnaamstam", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 14261,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.IndicatieAfgeleid", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 13436,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.IndicatieNamenreeks", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18987,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 13429,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Persoon. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_PERSOON((short) 13420, "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.PredicaatCode. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 13431,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.PredicaatCode", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Scheidingsteken. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 13434,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Scheidingsteken", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 13423,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.TijdstipVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 13744,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 13844,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Voornamen. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 13432, "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Voornamen",
            SoortElement.ATTRIBUUT, GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Voorvoegsel. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 13435,
            "GerelateerdeNaamskeuzePartner.Persoon.SamengesteldeNaam.Voorvoegsel", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeNaamskeuzePartner.Persoon.SoortCode. */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SOORTCODE((short) 14219, "GerelateerdeNaamskeuzePartner.Persoon.SoortCode", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.RolCode. */
    GERELATEERDENAAMSKEUZEPARTNER_ROLCODE((short) 21087, "GerelateerdeNaamskeuzePartner.RolCode", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.TijdstipRegistratie. */
    GERELATEERDENAAMSKEUZEPARTNER_TIJDSTIPREGISTRATIE((short) 21205, "GerelateerdeNaamskeuzePartner.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.TijdstipVerval. */
    GERELATEERDENAAMSKEUZEPARTNER_TIJDSTIPVERVAL((short) 21206, "GerelateerdeNaamskeuzePartner.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeNaamskeuzePartner.VoorkomenSleutel. */
    GERELATEERDENAAMSKEUZEPARTNER_VOORKOMENSLEUTEL((short) 21203, "GerelateerdeNaamskeuzePartner.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT),
    /** GerelateerdeOuder.ActieInhoud. */
    GERELATEERDEOUDER_ACTIEINHOUD((short) 21145, "GerelateerdeOuder.ActieInhoud", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.ActieVerval. */
    GERELATEERDEOUDER_ACTIEVERVAL((short) 21146, "GerelateerdeOuder.ActieVerval", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEOUDER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21147, "GerelateerdeOuder.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.Betrokkenheid. */
    GERELATEERDEOUDER_BETROKKENHEID((short) 21141, "GerelateerdeOuder.Betrokkenheid", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEOUDER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21148, "GerelateerdeOuder.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.NadereAanduidingVerval. */
    GERELATEERDEOUDER_NADEREAANDUIDINGVERVAL((short) 21144, "GerelateerdeOuder.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.OuderlijkGezag.ActieAanpassingGeldigheid. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_ACTIEAANPASSINGGELDIGHEID((short) 14030, "GerelateerdeOuder.OuderlijkGezag.ActieAanpassingGeldigheid",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.ActieInhoud. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_ACTIEINHOUD((short) 14028, "GerelateerdeOuder.OuderlijkGezag.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.ActieVerval. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_ACTIEVERVAL((short) 14029, "GerelateerdeOuder.OuderlijkGezag.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18928, "GerelateerdeOuder.OuderlijkGezag.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.Betrokkenheid. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_BETROKKENHEID((short) 12829, "GerelateerdeOuder.OuderlijkGezag.Betrokkenheid", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.DatumAanvangGeldigheid. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_DATUMAANVANGGELDIGHEID((short) 12830, "GerelateerdeOuder.OuderlijkGezag.DatumAanvangGeldigheid",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.DatumEindeGeldigheid. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_DATUMEINDEGELDIGHEID((short) 12831, "GerelateerdeOuder.OuderlijkGezag.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.IndicatieOuderHeeftGezag. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG((short) 12839, "GerelateerdeOuder.OuderlijkGezag.IndicatieOuderHeeftGezag",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18929,
            "GerelateerdeOuder.OuderlijkGezag.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.NadereAanduidingVerval. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_NADEREAANDUIDINGVERVAL((short) 12838, "GerelateerdeOuder.OuderlijkGezag.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.TijdstipRegistratie. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_TIJDSTIPREGISTRATIE((short) 12832, "GerelateerdeOuder.OuderlijkGezag.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.TijdstipVerval. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_TIJDSTIPVERVAL((short) 13617, "GerelateerdeOuder.OuderlijkGezag.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.OuderlijkGezag.VoorkomenSleutel. */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_VOORKOMENSLEUTEL((short) 13808, "GerelateerdeOuder.OuderlijkGezag.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERLIJKGEZAG),
    /** GerelateerdeOuder.Ouderschap.ActieAanpassingGeldigheid. */
    GERELATEERDEOUDER_OUDERSCHAP_ACTIEAANPASSINGGELDIGHEID((short) 14027, "GerelateerdeOuder.Ouderschap.ActieAanpassingGeldigheid",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.ActieInhoud. */
    GERELATEERDEOUDER_OUDERSCHAP_ACTIEINHOUD((short) 14025, "GerelateerdeOuder.Ouderschap.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.ActieVerval. */
    GERELATEERDEOUDER_OUDERSCHAP_ACTIEVERVAL((short) 14026, "GerelateerdeOuder.Ouderschap.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEOUDER_OUDERSCHAP_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18926, "GerelateerdeOuder.Ouderschap.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.Betrokkenheid. */
    GERELATEERDEOUDER_OUDERSCHAP_BETROKKENHEID((short) 13872, "GerelateerdeOuder.Ouderschap.Betrokkenheid", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.DatumAanvangGeldigheid. */
    GERELATEERDEOUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID((short) 13873, "GerelateerdeOuder.Ouderschap.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.DatumEindeGeldigheid. */
    GERELATEERDEOUDER_OUDERSCHAP_DATUMEINDEGELDIGHEID((short) 13874, "GerelateerdeOuder.Ouderschap.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.IndicatieOuderUitWieKindIsGeboren. */
    GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN((short) 21253, "GerelateerdeOuder.Ouderschap.IndicatieOuderUitWieKindIsGeboren",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEOUDER_OUDERSCHAP_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18927,
            "GerelateerdeOuder.Ouderschap.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.NadereAanduidingVerval. */
    GERELATEERDEOUDER_OUDERSCHAP_NADEREAANDUIDINGVERVAL((short) 13877, "GerelateerdeOuder.Ouderschap.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.TijdstipRegistratie. */
    GERELATEERDEOUDER_OUDERSCHAP_TIJDSTIPREGISTRATIE((short) 13875, "GerelateerdeOuder.Ouderschap.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.TijdstipVerval. */
    GERELATEERDEOUDER_OUDERSCHAP_TIJDSTIPVERVAL((short) 13876, "GerelateerdeOuder.Ouderschap.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Ouderschap.VoorkomenSleutel. */
    GERELATEERDEOUDER_OUDERSCHAP_VOORKOMENSLEUTEL((short) 13871, "GerelateerdeOuder.Ouderschap.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_OUDERSCHAP),
    /** GerelateerdeOuder.Persoon.Geboorte.ActieInhoud. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_ACTIEINHOUD((short) 14037, "GerelateerdeOuder.Persoon.Geboorte.ActieInhoud", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.ActieVerval. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_ACTIEVERVAL((short) 14038, "GerelateerdeOuder.Persoon.Geboorte.ActieVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18934, "GerelateerdeOuder.Persoon.Geboorte.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.BuitenlandsePlaats. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 12887, "GerelateerdeOuder.Persoon.Geboorte.BuitenlandsePlaats", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.BuitenlandseRegio. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 12882, "GerelateerdeOuder.Persoon.Geboorte.BuitenlandseRegio", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.Datum. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM((short) 12884, "GerelateerdeOuder.Persoon.Geboorte.Datum", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.GemeenteCode. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE((short) 12885, "GerelateerdeOuder.Persoon.Geboorte.GemeenteCode", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18935,
            "GerelateerdeOuder.Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.LandGebiedCode. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 12883, "GerelateerdeOuder.Persoon.Geboorte.LandGebiedCode", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.NadereAanduidingVerval. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 12881, "GerelateerdeOuder.Persoon.Geboorte.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.OmschrijvingLocatie. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 12888, "GerelateerdeOuder.Persoon.Geboorte.OmschrijvingLocatie",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.Persoon. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_PERSOON((short) 12875, "GerelateerdeOuder.Persoon.Geboorte.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.TijdstipRegistratie. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 12876, "GerelateerdeOuder.Persoon.Geboorte.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.TijdstipVerval. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 13620, "GerelateerdeOuder.Persoon.Geboorte.TijdstipVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.VoorkomenSleutel. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 13811, "GerelateerdeOuder.Persoon.Geboorte.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geboorte.Woonplaatsnaam. */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 12886, "GerelateerdeOuder.Persoon.Geboorte.Woonplaatsnaam", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GEBOORTE),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID((short) 14041,
            "GerelateerdeOuder.Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.ActieInhoud. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD((short) 14039, "GerelateerdeOuder.Persoon.Geslachtsaanduiding.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.ActieVerval. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL((short) 14040, "GerelateerdeOuder.Persoon.Geslachtsaanduiding.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18936,
            "GerelateerdeOuder.Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.Code. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE((short) 12900, "GerelateerdeOuder.Persoon.Geslachtsaanduiding.Code", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID((short) 12891,
            "GerelateerdeOuder.Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID((short) 12892,
            "GerelateerdeOuder.Persoon.Geslachtsaanduiding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18937,
            "GerelateerdeOuder.Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.NadereAanduidingVerval. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL((short) 12899,
            "GerelateerdeOuder.Persoon.Geslachtsaanduiding.NadereAanduidingVerval", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.Persoon. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_PERSOON((short) 12890, "GerelateerdeOuder.Persoon.Geslachtsaanduiding.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.TijdstipRegistratie. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE((short) 12893, "GerelateerdeOuder.Persoon.Geslachtsaanduiding.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.TijdstipVerval. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL((short) 13621, "GerelateerdeOuder.Persoon.Geslachtsaanduiding.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Geslachtsaanduiding.VoorkomenSleutel. */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL((short) 13812, "GerelateerdeOuder.Persoon.Geslachtsaanduiding.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /** GerelateerdeOuder.Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 14033,
            "GerelateerdeOuder.Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.ActieInhoud. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 14031, "GerelateerdeOuder.Persoon.Identificatienummers.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.ActieVerval. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 14032, "GerelateerdeOuder.Persoon.Identificatienummers.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18930,
            "GerelateerdeOuder.Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.Administratienummer. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 12854,
            "GerelateerdeOuder.Persoon.Identificatienummers.Administratienummer", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.Burgerservicenummer. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 12855,
            "GerelateerdeOuder.Persoon.Identificatienummers.Burgerservicenummer", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 12845,
            "GerelateerdeOuder.Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.DatumEindeGeldigheid. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 12846,
            "GerelateerdeOuder.Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18931,
            "GerelateerdeOuder.Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.NadereAanduidingVerval. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 12853,
            "GerelateerdeOuder.Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.Persoon. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 12844, "GerelateerdeOuder.Persoon.Identificatienummers.Persoon",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.TijdstipRegistratie. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 12847,
            "GerelateerdeOuder.Persoon.Identificatienummers.TijdstipRegistratie", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.TijdstipVerval. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 13618, "GerelateerdeOuder.Persoon.Identificatienummers.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.Identificatienummers.VoorkomenSleutel. */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 13809, "GerelateerdeOuder.Persoon.Identificatienummers.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 14036,
            "GerelateerdeOuder.Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.ActieInhoud. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 14034, "GerelateerdeOuder.Persoon.SamengesteldeNaam.ActieInhoud",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.ActieVerval. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 14035, "GerelateerdeOuder.Persoon.SamengesteldeNaam.ActieVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18932,
            "GerelateerdeOuder.Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 12867, "GerelateerdeOuder.Persoon.SamengesteldeNaam.AdellijkeTitelCode",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 12858,
            "GerelateerdeOuder.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 12859, "GerelateerdeOuder.Persoon.SamengesteldeNaam.DatumEindeGeldigheid",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 12870, "GerelateerdeOuder.Persoon.SamengesteldeNaam.Geslachtsnaamstam",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 14254, "GerelateerdeOuder.Persoon.SamengesteldeNaam.IndicatieAfgeleid",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 12873, "GerelateerdeOuder.Persoon.SamengesteldeNaam.IndicatieNamenreeks",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18933,
            "GerelateerdeOuder.Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 12866,
            "GerelateerdeOuder.Persoon.SamengesteldeNaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.Persoon. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PERSOON((short) 12857, "GerelateerdeOuder.Persoon.SamengesteldeNaam.Persoon", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.PredicaatCode. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 12868, "GerelateerdeOuder.Persoon.SamengesteldeNaam.PredicaatCode",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.Scheidingsteken. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 12871, "GerelateerdeOuder.Persoon.SamengesteldeNaam.Scheidingsteken",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 12860, "GerelateerdeOuder.Persoon.SamengesteldeNaam.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.TijdstipVerval. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 13619, "GerelateerdeOuder.Persoon.SamengesteldeNaam.TijdstipVerval",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 13810, "GerelateerdeOuder.Persoon.SamengesteldeNaam.VoorkomenSleutel",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.Voornamen. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 12869, "GerelateerdeOuder.Persoon.SamengesteldeNaam.Voornamen", SoortElement.ATTRIBUUT,
            GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SamengesteldeNaam.Voorvoegsel. */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 12872, "GerelateerdeOuder.Persoon.SamengesteldeNaam.Voorvoegsel",
            SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM),
    /** GerelateerdeOuder.Persoon.SoortCode. */
    GERELATEERDEOUDER_PERSOON_SOORTCODE((short) 14212, "GerelateerdeOuder.Persoon.SoortCode", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_PERSOON_IDENTITEIT),
    /** GerelateerdeOuder.RolCode. */
    GERELATEERDEOUDER_ROLCODE((short) 21080, "GerelateerdeOuder.RolCode", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.TijdstipRegistratie. */
    GERELATEERDEOUDER_TIJDSTIPREGISTRATIE((short) 21142, "GerelateerdeOuder.TijdstipRegistratie", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.TijdstipVerval. */
    GERELATEERDEOUDER_TIJDSTIPVERVAL((short) 21143, "GerelateerdeOuder.TijdstipVerval", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_IDENTITEIT),
    /** GerelateerdeOuder.VoorkomenSleutel. */
    GERELATEERDEOUDER_VOORKOMENSLEUTEL((short) 21140, "GerelateerdeOuder.VoorkomenSleutel", SoortElement.ATTRIBUUT, GERELATEERDEOUDER_IDENTITEIT),
    /** Huwelijk.ActieInhoud. */
    HUWELIJK_ACTIEINHOUD((short) 14019, "Huwelijk.ActieInhoud", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.ActieVerval. */
    HUWELIJK_ACTIEVERVAL((short) 14020, "Huwelijk.ActieVerval", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.ActieVervalTbvLeveringMutaties. */
    HUWELIJK_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18922, "Huwelijk.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.BuitenlandsePlaatsAanvang. */
    HUWELIJK_BUITENLANDSEPLAATSAANVANG((short) 13859, "Huwelijk.BuitenlandsePlaatsAanvang", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.BuitenlandsePlaatsEinde. */
    HUWELIJK_BUITENLANDSEPLAATSEINDE((short) 13867, "Huwelijk.BuitenlandsePlaatsEinde", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.BuitenlandseRegioAanvang. */
    HUWELIJK_BUITENLANDSEREGIOAANVANG((short) 13860, "Huwelijk.BuitenlandseRegioAanvang", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.BuitenlandseRegioEinde. */
    HUWELIJK_BUITENLANDSEREGIOEINDE((short) 13868, "Huwelijk.BuitenlandseRegioEinde", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.DatumAanvang. */
    HUWELIJK_DATUMAANVANG((short) 13856, "Huwelijk.DatumAanvang", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.DatumEinde. */
    HUWELIJK_DATUMEINDE((short) 13864, "Huwelijk.DatumEinde", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.GemeenteAanvangCode. */
    HUWELIJK_GEMEENTEAANVANGCODE((short) 13857, "Huwelijk.GemeenteAanvangCode", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.GemeenteEindeCode. */
    HUWELIJK_GEMEENTEEINDECODE((short) 13865, "Huwelijk.GemeenteEindeCode", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.IndicatieVoorkomenTbvLeveringMutaties. */
    HUWELIJK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18923, "Huwelijk.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            HUWELIJK_STANDAARD),
    /** Huwelijk.LandGebiedAanvangCode. */
    HUWELIJK_LANDGEBIEDAANVANGCODE((short) 13862, "Huwelijk.LandGebiedAanvangCode", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.LandGebiedEindeCode. */
    HUWELIJK_LANDGEBIEDEINDECODE((short) 13870, "Huwelijk.LandGebiedEindeCode", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.NadereAanduidingVerval. */
    HUWELIJK_NADEREAANDUIDINGVERVAL((short) 13853, "Huwelijk.NadereAanduidingVerval", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.ObjectSleutel. */
    HUWELIJK_OBJECTSLEUTEL((short) 13791, "Huwelijk.ObjectSleutel", SoortElement.ATTRIBUUT, HUWELIJK_IDENTITEIT),
    /** Huwelijk.OmschrijvingLocatieAanvang. */
    HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG((short) 13861, "Huwelijk.OmschrijvingLocatieAanvang", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.OmschrijvingLocatieEinde. */
    HUWELIJK_OMSCHRIJVINGLOCATIEEINDE((short) 13869, "Huwelijk.OmschrijvingLocatieEinde", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.RedenEindeCode. */
    HUWELIJK_REDENEINDECODE((short) 13863, "Huwelijk.RedenEindeCode", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.Relatie. */
    HUWELIJK_RELATIE((short) 13850, "Huwelijk.Relatie", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.SoortCode. */
    HUWELIJK_SOORTCODE((short) 13546, "Huwelijk.SoortCode", SoortElement.ATTRIBUUT, HUWELIJK_IDENTITEIT),
    /** Huwelijk.TijdstipRegistratie. */
    HUWELIJK_TIJDSTIPREGISTRATIE((short) 13851, "Huwelijk.TijdstipRegistratie", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.TijdstipVerval. */
    HUWELIJK_TIJDSTIPVERVAL((short) 13852, "Huwelijk.TijdstipVerval", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.VoorkomenSleutel. */
    HUWELIJK_VOORKOMENSLEUTEL((short) 13849, "Huwelijk.VoorkomenSleutel", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.WoonplaatsnaamAanvang. */
    HUWELIJK_WOONPLAATSNAAMAANVANG((short) 13858, "Huwelijk.WoonplaatsnaamAanvang", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** Huwelijk.WoonplaatsnaamEinde. */
    HUWELIJK_WOONPLAATSNAAMEINDE((short) 13866, "Huwelijk.WoonplaatsnaamEinde", SoortElement.ATTRIBUUT, HUWELIJK_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.ActieInhoud. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_ACTIEINHOUD((short) 14322, "HuwelijkGeregistreerdPartnerschap.ActieInhoud", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.ActieVerval. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_ACTIEVERVAL((short) 14323, "HuwelijkGeregistreerdPartnerschap.ActieVerval", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.ActieVervalTbvLeveringMutaties. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18908, "HuwelijkGeregistreerdPartnerschap.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.BuitenlandsePlaatsAanvang. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG((short) 13560, "HuwelijkGeregistreerdPartnerschap.BuitenlandsePlaatsAanvang",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.BuitenlandsePlaatsEinde. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE((short) 13568, "HuwelijkGeregistreerdPartnerschap.BuitenlandsePlaatsEinde",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.BuitenlandseRegioAanvang. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG((short) 13561, "HuwelijkGeregistreerdPartnerschap.BuitenlandseRegioAanvang",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.BuitenlandseRegioEinde. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE((short) 13569, "HuwelijkGeregistreerdPartnerschap.BuitenlandseRegioEinde",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.DatumAanvang. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_DATUMAANVANG((short) 13557, "HuwelijkGeregistreerdPartnerschap.DatumAanvang", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.DatumEinde. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_DATUMEINDE((short) 13565, "HuwelijkGeregistreerdPartnerschap.DatumEinde", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.GemeenteAanvangCode. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE((short) 13558, "HuwelijkGeregistreerdPartnerschap.GemeenteAanvangCode", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.GemeenteEindeCode. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE((short) 13566, "HuwelijkGeregistreerdPartnerschap.GemeenteEindeCode", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.IndicatieVoorkomenTbvLeveringMutaties. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18909,
            "HuwelijkGeregistreerdPartnerschap.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.LandGebiedAanvangCode. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE((short) 13563, "HuwelijkGeregistreerdPartnerschap.LandGebiedAanvangCode",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.LandGebiedEindeCode. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE((short) 13571, "HuwelijkGeregistreerdPartnerschap.LandGebiedEindeCode", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.NadereAanduidingVerval. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_NADEREAANDUIDINGVERVAL((short) 13554, "HuwelijkGeregistreerdPartnerschap.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.ObjectSleutel. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_OBJECTSLEUTEL((short) 13792, "HuwelijkGeregistreerdPartnerschap.ObjectSleutel", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_IDENTITEIT),
    /** HuwelijkGeregistreerdPartnerschap.OmschrijvingLocatieAanvang. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG((short) 13562, "HuwelijkGeregistreerdPartnerschap.OmschrijvingLocatieAanvang",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.OmschrijvingLocatieEinde. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE((short) 13570, "HuwelijkGeregistreerdPartnerschap.OmschrijvingLocatieEinde",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.RedenEindeCode. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_REDENEINDECODE((short) 13564, "HuwelijkGeregistreerdPartnerschap.RedenEindeCode", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.Relatie. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_RELATIE((short) 13551, "HuwelijkGeregistreerdPartnerschap.Relatie", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.SoortCode. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_SOORTCODE((short) 13549, "HuwelijkGeregistreerdPartnerschap.SoortCode", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_IDENTITEIT),
    /** HuwelijkGeregistreerdPartnerschap.TijdstipRegistratie. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_TIJDSTIPREGISTRATIE((short) 13552, "HuwelijkGeregistreerdPartnerschap.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.TijdstipVerval. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_TIJDSTIPVERVAL((short) 13553, "HuwelijkGeregistreerdPartnerschap.TijdstipVerval", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.VoorkomenSleutel. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_VOORKOMENSLEUTEL((short) 13793, "HuwelijkGeregistreerdPartnerschap.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.WoonplaatsnaamAanvang. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG((short) 13559, "HuwelijkGeregistreerdPartnerschap.WoonplaatsnaamAanvang",
            SoortElement.ATTRIBUUT, HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** HuwelijkGeregistreerdPartnerschap.WoonplaatsnaamEinde. */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE((short) 13567, "HuwelijkGeregistreerdPartnerschap.WoonplaatsnaamEinde", SoortElement.ATTRIBUUT,
            HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD),
    /** Instemmer.ActieInhoud. */
    INSTEMMER_ACTIEINHOUD((short) 14312, "Instemmer.ActieInhoud", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.ActieVerval. */
    INSTEMMER_ACTIEVERVAL((short) 14313, "Instemmer.ActieVerval", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.ActieVervalTbvLeveringMutaties. */
    INSTEMMER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18918, "Instemmer.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.Betrokkenheid. */
    INSTEMMER_BETROKKENHEID((short) 14308, "Instemmer.Betrokkenheid", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.IndicatieVoorkomenTbvLeveringMutaties. */
    INSTEMMER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18919, "Instemmer.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            INSTEMMER_IDENTITEIT),
    /** Instemmer.NadereAanduidingVerval. */
    INSTEMMER_NADEREAANDUIDINGVERVAL((short) 14311, "Instemmer.NadereAanduidingVerval", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.ObjectSleutel. */
    INSTEMMER_OBJECTSLEUTEL((short) 13800, "Instemmer.ObjectSleutel", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.Persoon. */
    INSTEMMER_PERSOON((short) 13612, "Instemmer.Persoon", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.Relatie. */
    INSTEMMER_RELATIE((short) 13610, "Instemmer.Relatie", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.RolCode. */
    INSTEMMER_ROLCODE((short) 13611, "Instemmer.RolCode", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.TijdstipRegistratie. */
    INSTEMMER_TIJDSTIPREGISTRATIE((short) 14309, "Instemmer.TijdstipRegistratie", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.TijdstipVerval. */
    INSTEMMER_TIJDSTIPVERVAL((short) 14310, "Instemmer.TijdstipVerval", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Instemmer.VoorkomenSleutel. */
    INSTEMMER_VOORKOMENSLEUTEL((short) 14307, "Instemmer.VoorkomenSleutel", SoortElement.ATTRIBUUT, INSTEMMER_IDENTITEIT),
    /** Kind.ActieInhoud. */
    KIND_ACTIEINHOUD((short) 14284, "Kind.ActieInhoud", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.ActieVerval. */
    KIND_ACTIEVERVAL((short) 14285, "Kind.ActieVerval", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.ActieVervalTbvLeveringMutaties. */
    KIND_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18902, "Kind.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.Betrokkenheid. */
    KIND_BETROKKENHEID((short) 14280, "Kind.Betrokkenheid", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.IndicatieVoorkomenTbvLeveringMutaties. */
    KIND_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18903, "Kind.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.NadereAanduidingVerval. */
    KIND_NADEREAANDUIDINGVERVAL((short) 14283, "Kind.NadereAanduidingVerval", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.ObjectSleutel. */
    KIND_OBJECTSLEUTEL((short) 13788, "Kind.ObjectSleutel", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.Persoon. */
    KIND_PERSOON((short) 13536, "Kind.Persoon", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.Relatie. */
    KIND_RELATIE((short) 13534, "Kind.Relatie", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.RolCode. */
    KIND_ROLCODE((short) 13535, "Kind.RolCode", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.TijdstipRegistratie. */
    KIND_TIJDSTIPREGISTRATIE((short) 14281, "Kind.TijdstipRegistratie", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.TijdstipVerval. */
    KIND_TIJDSTIPVERVAL((short) 14282, "Kind.TijdstipVerval", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Kind.VoorkomenSleutel. */
    KIND_VOORKOMENSLEUTEL((short) 14279, "Kind.VoorkomenSleutel", SoortElement.ATTRIBUUT, KIND_IDENTITEIT),
    /** Naamgever.ActieInhoud. */
    NAAMGEVER_ACTIEINHOUD((short) 14319, "Naamgever.ActieInhoud", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.ActieVerval. */
    NAAMGEVER_ACTIEVERVAL((short) 14320, "Naamgever.ActieVerval", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.ActieVervalTbvLeveringMutaties. */
    NAAMGEVER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18920, "Naamgever.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.Betrokkenheid. */
    NAAMGEVER_BETROKKENHEID((short) 14315, "Naamgever.Betrokkenheid", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.IndicatieVoorkomenTbvLeveringMutaties. */
    NAAMGEVER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18921, "Naamgever.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            NAAMGEVER_IDENTITEIT),
    /** Naamgever.NadereAanduidingVerval. */
    NAAMGEVER_NADEREAANDUIDINGVERVAL((short) 14318, "Naamgever.NadereAanduidingVerval", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.ObjectSleutel. */
    NAAMGEVER_OBJECTSLEUTEL((short) 13801, "Naamgever.ObjectSleutel", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.Persoon. */
    NAAMGEVER_PERSOON((short) 13616, "Naamgever.Persoon", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.Relatie. */
    NAAMGEVER_RELATIE((short) 13614, "Naamgever.Relatie", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.RolCode. */
    NAAMGEVER_ROLCODE((short) 13615, "Naamgever.RolCode", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.TijdstipRegistratie. */
    NAAMGEVER_TIJDSTIPREGISTRATIE((short) 14316, "Naamgever.TijdstipRegistratie", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.TijdstipVerval. */
    NAAMGEVER_TIJDSTIPVERVAL((short) 14317, "Naamgever.TijdstipVerval", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** Naamgever.VoorkomenSleutel. */
    NAAMGEVER_VOORKOMENSLEUTEL((short) 14314, "Naamgever.VoorkomenSleutel", SoortElement.ATTRIBUUT, NAAMGEVER_IDENTITEIT),
    /** NaamskeuzeOngeborenVrucht.ActieInhoud. */
    NAAMSKEUZEONGEBORENVRUCHT_ACTIEINHOUD((short) 14376, "NaamskeuzeOngeborenVrucht.ActieInhoud", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.ActieVerval. */
    NAAMSKEUZEONGEBORENVRUCHT_ACTIEVERVAL((short) 14377, "NaamskeuzeOngeborenVrucht.ActieVerval", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.ActieVervalTbvLeveringMutaties. */
    NAAMSKEUZEONGEBORENVRUCHT_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18914, "NaamskeuzeOngeborenVrucht.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.BuitenlandsePlaatsAanvang. */
    NAAMSKEUZEONGEBORENVRUCHT_BUITENLANDSEPLAATSAANVANG((short) 14381, "NaamskeuzeOngeborenVrucht.BuitenlandsePlaatsAanvang", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.BuitenlandsePlaatsEinde. */
    NAAMSKEUZEONGEBORENVRUCHT_BUITENLANDSEPLAATSEINDE((short) 14389, "NaamskeuzeOngeborenVrucht.BuitenlandsePlaatsEinde", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.BuitenlandseRegioAanvang. */
    NAAMSKEUZEONGEBORENVRUCHT_BUITENLANDSEREGIOAANVANG((short) 14382, "NaamskeuzeOngeborenVrucht.BuitenlandseRegioAanvang", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.BuitenlandseRegioEinde. */
    NAAMSKEUZEONGEBORENVRUCHT_BUITENLANDSEREGIOEINDE((short) 14390, "NaamskeuzeOngeborenVrucht.BuitenlandseRegioEinde", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.DatumAanvang. */
    NAAMSKEUZEONGEBORENVRUCHT_DATUMAANVANG((short) 14378, "NaamskeuzeOngeborenVrucht.DatumAanvang", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.DatumEinde. */
    NAAMSKEUZEONGEBORENVRUCHT_DATUMEINDE((short) 14386, "NaamskeuzeOngeborenVrucht.DatumEinde", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.GemeenteAanvangCode. */
    NAAMSKEUZEONGEBORENVRUCHT_GEMEENTEAANVANGCODE((short) 14379, "NaamskeuzeOngeborenVrucht.GemeenteAanvangCode", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.GemeenteEindeCode. */
    NAAMSKEUZEONGEBORENVRUCHT_GEMEENTEEINDECODE((short) 14387, "NaamskeuzeOngeborenVrucht.GemeenteEindeCode", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.IndicatieVoorkomenTbvLeveringMutaties. */
    NAAMSKEUZEONGEBORENVRUCHT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18915, "NaamskeuzeOngeborenVrucht.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.LandGebiedAanvangCode. */
    NAAMSKEUZEONGEBORENVRUCHT_LANDGEBIEDAANVANGCODE((short) 14384, "NaamskeuzeOngeborenVrucht.LandGebiedAanvangCode", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.LandGebiedEindeCode. */
    NAAMSKEUZEONGEBORENVRUCHT_LANDGEBIEDEINDECODE((short) 14392, "NaamskeuzeOngeborenVrucht.LandGebiedEindeCode", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.NadereAanduidingVerval. */
    NAAMSKEUZEONGEBORENVRUCHT_NADEREAANDUIDINGVERVAL((short) 14375, "NaamskeuzeOngeborenVrucht.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.ObjectSleutel. */
    NAAMSKEUZEONGEBORENVRUCHT_OBJECTSLEUTEL((short) 13798, "NaamskeuzeOngeborenVrucht.ObjectSleutel", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_IDENTITEIT),
    /** NaamskeuzeOngeborenVrucht.OmschrijvingLocatieAanvang. */
    NAAMSKEUZEONGEBORENVRUCHT_OMSCHRIJVINGLOCATIEAANVANG((short) 14383, "NaamskeuzeOngeborenVrucht.OmschrijvingLocatieAanvang", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.OmschrijvingLocatieEinde. */
    NAAMSKEUZEONGEBORENVRUCHT_OMSCHRIJVINGLOCATIEEINDE((short) 14391, "NaamskeuzeOngeborenVrucht.OmschrijvingLocatieEinde", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.RedenEindeCode. */
    NAAMSKEUZEONGEBORENVRUCHT_REDENEINDECODE((short) 14385, "NaamskeuzeOngeborenVrucht.RedenEindeCode", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.Relatie. */
    NAAMSKEUZEONGEBORENVRUCHT_RELATIE((short) 14372, "NaamskeuzeOngeborenVrucht.Relatie", SoortElement.ATTRIBUUT, NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.SoortCode. */
    NAAMSKEUZEONGEBORENVRUCHT_SOORTCODE((short) 13603, "NaamskeuzeOngeborenVrucht.SoortCode", SoortElement.ATTRIBUUT, NAAMSKEUZEONGEBORENVRUCHT_IDENTITEIT),
    /** NaamskeuzeOngeborenVrucht.TijdstipRegistratie. */
    NAAMSKEUZEONGEBORENVRUCHT_TIJDSTIPREGISTRATIE((short) 14373, "NaamskeuzeOngeborenVrucht.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.TijdstipVerval. */
    NAAMSKEUZEONGEBORENVRUCHT_TIJDSTIPVERVAL((short) 14374, "NaamskeuzeOngeborenVrucht.TijdstipVerval", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.VoorkomenSleutel. */
    NAAMSKEUZEONGEBORENVRUCHT_VOORKOMENSLEUTEL((short) 14371, "NaamskeuzeOngeborenVrucht.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.WoonplaatsnaamAanvang. */
    NAAMSKEUZEONGEBORENVRUCHT_WOONPLAATSNAAMAANVANG((short) 14380, "NaamskeuzeOngeborenVrucht.WoonplaatsnaamAanvang", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** NaamskeuzeOngeborenVrucht.WoonplaatsnaamEinde. */
    NAAMSKEUZEONGEBORENVRUCHT_WOONPLAATSNAAMEINDE((short) 14388, "NaamskeuzeOngeborenVrucht.WoonplaatsnaamEinde", SoortElement.ATTRIBUUT,
            NAAMSKEUZEONGEBORENVRUCHT_STANDAARD),
    /** Onderzoek.ActieInhoud. */
    ONDERZOEK_ACTIEINHOUD((short) 4071, "Onderzoek.ActieInhoud", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.ActieVerval. */
    ONDERZOEK_ACTIEVERVAL((short) 4072, "Onderzoek.ActieVerval", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.ActieVervalTbvLeveringMutaties. */
    ONDERZOEK_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18830, "Onderzoek.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.AfgeleidAdministratief.ActieInhoud. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ACTIEINHOUD((short) 10926, "Onderzoek.AfgeleidAdministratief.ActieInhoud", SoortElement.ATTRIBUUT,
            ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.ActieVerval. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ACTIEVERVAL((short) 10927, "Onderzoek.AfgeleidAdministratief.ActieVerval", SoortElement.ATTRIBUUT,
            ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.ActieVervalTbvLeveringMutaties. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18832, "Onderzoek.AfgeleidAdministratief.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.AdministratieveHandeling. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ADMINISTRATIEVEHANDELING((short) 10842, "Onderzoek.AfgeleidAdministratief.AdministratieveHandeling",
            SoortElement.ATTRIBUUT, ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.IndicatieVoorkomenTbvLeveringMutaties. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18833,
            "Onderzoek.AfgeleidAdministratief.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.NadereAanduidingVerval. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_NADEREAANDUIDINGVERVAL((short) 11122, "Onderzoek.AfgeleidAdministratief.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.Onderzoek. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ONDERZOEK((short) 10923, "Onderzoek.AfgeleidAdministratief.Onderzoek", SoortElement.ATTRIBUUT,
            ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.TijdstipRegistratie. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_TIJDSTIPREGISTRATIE((short) 10924, "Onderzoek.AfgeleidAdministratief.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.TijdstipVerval. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_TIJDSTIPVERVAL((short) 10925, "Onderzoek.AfgeleidAdministratief.TijdstipVerval", SoortElement.ATTRIBUUT,
            ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.AfgeleidAdministratief.VoorkomenSleutel. */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_VOORKOMENSLEUTEL((short) 10973, "Onderzoek.AfgeleidAdministratief.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            ONDERZOEK_AFGELEIDADMINISTRATIEF),
    /** Onderzoek.DatumAanvang. */
    ONDERZOEK_DATUMAANVANG((short) 3178, "Onderzoek.DatumAanvang", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.DatumEinde. */
    ONDERZOEK_DATUMEINDE((short) 3179, "Onderzoek.DatumEinde", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.IndicatieVoorkomenTbvLeveringMutaties. */
    ONDERZOEK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18831, "Onderzoek.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            ONDERZOEK_STANDAARD),
    /** Onderzoek.NadereAanduidingVerval. */
    ONDERZOEK_NADEREAANDUIDINGVERVAL((short) 11121, "Onderzoek.NadereAanduidingVerval", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.ObjectSleutel. */
    ONDERZOEK_OBJECTSLEUTEL((short) 3169, "Onderzoek.ObjectSleutel", SoortElement.ATTRIBUUT, ONDERZOEK_IDENTITEIT),
    /** Onderzoek.Omschrijving. */
    ONDERZOEK_OMSCHRIJVING((short) 3772, "Onderzoek.Omschrijving", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.Onderzoek. */
    ONDERZOEK_ONDERZOEK((short) 4068, "Onderzoek.Onderzoek", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.StatusNaam. */
    ONDERZOEK_STATUSNAAM((short) 10849, "Onderzoek.StatusNaam", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.TijdstipRegistratie. */
    ONDERZOEK_TIJDSTIPREGISTRATIE((short) 4069, "Onderzoek.TijdstipRegistratie", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.TijdstipVerval. */
    ONDERZOEK_TIJDSTIPVERVAL((short) 4070, "Onderzoek.TijdstipVerval", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.VerwachteAfhandeldatum. */
    ONDERZOEK_VERWACHTEAFHANDELDATUM((short) 10848, "Onderzoek.VerwachteAfhandeldatum", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Onderzoek.VoorkomenSleutel. */
    ONDERZOEK_VOORKOMENSLEUTEL((short) 4521, "Onderzoek.VoorkomenSleutel", SoortElement.ATTRIBUUT, ONDERZOEK_STANDAARD),
    /** Ouder.ActieInhoud. */
    OUDER_ACTIEINHOUD((short) 14291, "Ouder.ActieInhoud", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.ActieVerval. */
    OUDER_ACTIEVERVAL((short) 14292, "Ouder.ActieVerval", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.ActieVervalTbvLeveringMutaties. */
    OUDER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18904, "Ouder.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.Betrokkenheid. */
    OUDER_BETROKKENHEID((short) 14287, "Ouder.Betrokkenheid", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.IndicatieVoorkomenTbvLeveringMutaties. */
    OUDER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18905, "Ouder.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.NadereAanduidingVerval. */
    OUDER_NADEREAANDUIDINGVERVAL((short) 14290, "Ouder.NadereAanduidingVerval", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.ObjectSleutel. */
    OUDER_OBJECTSLEUTEL((short) 13789, "Ouder.ObjectSleutel", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.OuderlijkGezag.ActieAanpassingGeldigheid. */
    OUDER_OUDERLIJKGEZAG_ACTIEAANPASSINGGELDIGHEID((short) 4040, "Ouder.OuderlijkGezag.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.ActieInhoud. */
    OUDER_OUDERLIJKGEZAG_ACTIEINHOUD((short) 4038, "Ouder.OuderlijkGezag.ActieInhoud", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.ActieVerval. */
    OUDER_OUDERLIJKGEZAG_ACTIEVERVAL((short) 4039, "Ouder.OuderlijkGezag.ActieVerval", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.ActieVervalTbvLeveringMutaties. */
    OUDER_OUDERLIJKGEZAG_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18818, "Ouder.OuderlijkGezag.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.Betrokkenheid. */
    OUDER_OUDERLIJKGEZAG_BETROKKENHEID((short) 4033, "Ouder.OuderlijkGezag.Betrokkenheid", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.DatumAanvangGeldigheid. */
    OUDER_OUDERLIJKGEZAG_DATUMAANVANGGELDIGHEID((short) 4034, "Ouder.OuderlijkGezag.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.DatumEindeGeldigheid. */
    OUDER_OUDERLIJKGEZAG_DATUMEINDEGELDIGHEID((short) 4035, "Ouder.OuderlijkGezag.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.IndicatieOuderHeeftGezag. */
    OUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG((short) 3208, "Ouder.OuderlijkGezag.IndicatieOuderHeeftGezag", SoortElement.ATTRIBUUT,
            OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.IndicatieVoorkomenTbvLeveringMutaties. */
    OUDER_OUDERLIJKGEZAG_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18819, "Ouder.OuderlijkGezag.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.NadereAanduidingVerval. */
    OUDER_OUDERLIJKGEZAG_NADEREAANDUIDINGVERVAL((short) 11114, "Ouder.OuderlijkGezag.NadereAanduidingVerval", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.TijdstipRegistratie. */
    OUDER_OUDERLIJKGEZAG_TIJDSTIPREGISTRATIE((short) 4036, "Ouder.OuderlijkGezag.TijdstipRegistratie", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.TijdstipVerval. */
    OUDER_OUDERLIJKGEZAG_TIJDSTIPVERVAL((short) 4037, "Ouder.OuderlijkGezag.TijdstipVerval", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.OuderlijkGezag.VoorkomenSleutel. */
    OUDER_OUDERLIJKGEZAG_VOORKOMENSLEUTEL((short) 4512, "Ouder.OuderlijkGezag.VoorkomenSleutel", SoortElement.ATTRIBUUT, OUDER_OUDERLIJKGEZAG),
    /** Ouder.Ouderschap.ActieAanpassingGeldigheid. */
    OUDER_OUDERSCHAP_ACTIEAANPASSINGGELDIGHEID((short) 6091, "Ouder.Ouderschap.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.ActieInhoud. */
    OUDER_OUDERSCHAP_ACTIEINHOUD((short) 4028, "Ouder.Ouderschap.ActieInhoud", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.ActieVerval. */
    OUDER_OUDERSCHAP_ACTIEVERVAL((short) 4029, "Ouder.Ouderschap.ActieVerval", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.ActieVervalTbvLeveringMutaties. */
    OUDER_OUDERSCHAP_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18816, "Ouder.Ouderschap.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.Betrokkenheid. */
    OUDER_OUDERSCHAP_BETROKKENHEID((short) 4025, "Ouder.Ouderschap.Betrokkenheid", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.DatumAanvangGeldigheid. */
    OUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID((short) 6089, "Ouder.Ouderschap.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.DatumEindeGeldigheid. */
    OUDER_OUDERSCHAP_DATUMEINDEGELDIGHEID((short) 6090, "Ouder.Ouderschap.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.IndicatieOuder. */
    OUDER_OUDERSCHAP_INDICATIEOUDER((short) 6088, "Ouder.Ouderschap.IndicatieOuder", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.IndicatieOuderUitWieKindIsGeboren. */
    OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN((short) 6176, "Ouder.Ouderschap.IndicatieOuderUitWieKindIsGeboren", SoortElement.ATTRIBUUT,
            OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.IndicatieVoorkomenTbvLeveringMutaties. */
    OUDER_OUDERSCHAP_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18817, "Ouder.Ouderschap.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.NadereAanduidingVerval. */
    OUDER_OUDERSCHAP_NADEREAANDUIDINGVERVAL((short) 11113, "Ouder.Ouderschap.NadereAanduidingVerval", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.TijdstipRegistratie. */
    OUDER_OUDERSCHAP_TIJDSTIPREGISTRATIE((short) 4026, "Ouder.Ouderschap.TijdstipRegistratie", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.TijdstipVerval. */
    OUDER_OUDERSCHAP_TIJDSTIPVERVAL((short) 4027, "Ouder.Ouderschap.TijdstipVerval", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Ouderschap.VoorkomenSleutel. */
    OUDER_OUDERSCHAP_VOORKOMENSLEUTEL((short) 4509, "Ouder.Ouderschap.VoorkomenSleutel", SoortElement.ATTRIBUUT, OUDER_OUDERSCHAP),
    /** Ouder.Persoon. */
    OUDER_PERSOON((short) 13540, "Ouder.Persoon", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.Relatie. */
    OUDER_RELATIE((short) 13538, "Ouder.Relatie", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.RolCode. */
    OUDER_ROLCODE((short) 13539, "Ouder.RolCode", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.TijdstipRegistratie. */
    OUDER_TIJDSTIPREGISTRATIE((short) 14288, "Ouder.TijdstipRegistratie", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.TijdstipVerval. */
    OUDER_TIJDSTIPVERVAL((short) 14289, "Ouder.TijdstipVerval", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Ouder.VoorkomenSleutel. */
    OUDER_VOORKOMENSLEUTEL((short) 14286, "Ouder.VoorkomenSleutel", SoortElement.ATTRIBUUT, OUDER_IDENTITEIT),
    /** Partij.ActieInhoud. */
    PARTIJ_ACTIEINHOUD((short) 4623, "Partij.ActieInhoud", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.ActieVerval. */
    PARTIJ_ACTIEVERVAL((short) 4624, "Partij.ActieVerval", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.ActieVervalTbvLeveringMutaties. */
    PARTIJ_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18834, "Partij.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.Bijhouding.ActieInhoud. */
    PARTIJ_BIJHOUDING_ACTIEINHOUD((short) 21497, "Partij.Bijhouding.ActieInhoud", SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.ActieVerval. */
    PARTIJ_BIJHOUDING_ACTIEVERVAL((short) 21498, "Partij.Bijhouding.ActieVerval", SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.ActieVervalTbvLeveringMutaties. */
    PARTIJ_BIJHOUDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 21499, "Partij.Bijhouding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.DatumOvergangNaarBRP. */
    PARTIJ_BIJHOUDING_DATUMOVERGANGNAARBRP((short) 21447, "Partij.Bijhouding.DatumOvergangNaarBRP", SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.IndicatieAutomatischFiatteren. */
    PARTIJ_BIJHOUDING_INDICATIEAUTOMATISCHFIATTEREN((short) 21446, "Partij.Bijhouding.IndicatieAutomatischFiatteren", SoortElement.ATTRIBUUT,
            PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.IndicatieVoorkomenTbvLeveringMutaties. */
    PARTIJ_BIJHOUDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 21500, "Partij.Bijhouding.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.NadereAanduidingVerval. */
    PARTIJ_BIJHOUDING_NADEREAANDUIDINGVERVAL((short) 21496, "Partij.Bijhouding.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.Partij. */
    PARTIJ_BIJHOUDING_PARTIJ((short) 21493, "Partij.Bijhouding.Partij", SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.TijdstipRegistratie. */
    PARTIJ_BIJHOUDING_TIJDSTIPREGISTRATIE((short) 21494, "Partij.Bijhouding.TijdstipRegistratie", SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.TijdstipVerval. */
    PARTIJ_BIJHOUDING_TIJDSTIPVERVAL((short) 21495, "Partij.Bijhouding.TijdstipVerval", SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Bijhouding.VoorkomenSleutel. */
    PARTIJ_BIJHOUDING_VOORKOMENSLEUTEL((short) 21538, "Partij.Bijhouding.VoorkomenSleutel", SoortElement.ATTRIBUUT, PARTIJ_BIJHOUDING),
    /** Partij.Code. */
    PARTIJ_CODE((short) 4601, "Partij.Code", SoortElement.ATTRIBUUT, PARTIJ_IDENTITEIT),
    /** Partij.DatumEinde. */
    PARTIJ_DATUMEINDE((short) 2200, "Partij.DatumEinde", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.DatumIngang. */
    PARTIJ_DATUMINGANG((short) 2199, "Partij.DatumIngang", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.IndicatieVerstrekkingsbeperkingMogelijk. */
    PARTIJ_INDICATIEVERSTREKKINGSBEPERKINGMOGELIJK((short) 2196, "Partij.IndicatieVerstrekkingsbeperkingMogelijk", SoortElement.ATTRIBUUT,
            PARTIJ_STANDAARD),
    /** Partij.IndicatieVoorkomenTbvLeveringMutaties. */
    PARTIJ_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18835, "Partij.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.Naam. */
    PARTIJ_NAAM((short) 3145, "Partij.Naam", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.NadereAanduidingVerval. */
    PARTIJ_NADEREAANDUIDINGVERVAL((short) 11123, "Partij.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.OIN. */
    PARTIJ_OIN((short) 21905, "Partij.OIN", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.ObjectSleutel. */
    PARTIJ_OBJECTSLEUTEL((short) 3143, "Partij.ObjectSleutel", SoortElement.ATTRIBUUT, PARTIJ_IDENTITEIT),
    /** Partij.Partij. */
    PARTIJ_PARTIJ((short) 4620, "Partij.Partij", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.SoortNaam. */
    PARTIJ_SOORTNAAM((short) 2195, "Partij.SoortNaam", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.TijdstipRegistratie. */
    PARTIJ_TIJDSTIPREGISTRATIE((short) 4621, "Partij.TijdstipRegistratie", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.TijdstipVerval. */
    PARTIJ_TIJDSTIPVERVAL((short) 4622, "Partij.TijdstipVerval", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partij.VoorkomenSleutel. */
    PARTIJ_VOORKOMENSLEUTEL((short) 4630, "Partij.VoorkomenSleutel", SoortElement.ATTRIBUUT, PARTIJ_STANDAARD),
    /** Partner.ActieInhoud. */
    PARTNER_ACTIEINHOUD((short) 14298, "Partner.ActieInhoud", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.ActieVerval. */
    PARTNER_ACTIEVERVAL((short) 14299, "Partner.ActieVerval", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.ActieVervalTbvLeveringMutaties. */
    PARTNER_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18906, "Partner.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.Betrokkenheid. */
    PARTNER_BETROKKENHEID((short) 14294, "Partner.Betrokkenheid", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.IndicatieVoorkomenTbvLeveringMutaties. */
    PARTNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18907, "Partner.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PARTNER_IDENTITEIT),
    /** Partner.NadereAanduidingVerval. */
    PARTNER_NADEREAANDUIDINGVERVAL((short) 14297, "Partner.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.ObjectSleutel. */
    PARTNER_OBJECTSLEUTEL((short) 13790, "Partner.ObjectSleutel", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.Persoon. */
    PARTNER_PERSOON((short) 13544, "Partner.Persoon", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.Relatie. */
    PARTNER_RELATIE((short) 13542, "Partner.Relatie", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.RolCode. */
    PARTNER_ROLCODE((short) 13543, "Partner.RolCode", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.TijdstipRegistratie. */
    PARTNER_TIJDSTIPREGISTRATIE((short) 14295, "Partner.TijdstipRegistratie", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.TijdstipVerval. */
    PARTNER_TIJDSTIPVERVAL((short) 14296, "Partner.TijdstipVerval", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Partner.VoorkomenSleutel. */
    PARTNER_VOORKOMENSLEUTEL((short) 14293, "Partner.VoorkomenSleutel", SoortElement.ATTRIBUUT, PARTNER_IDENTITEIT),
    /** Persoon.Adres.AangeverAdreshoudingCode. */
    PERSOON_ADRES_AANGEVERADRESHOUDINGCODE((short) 3301, "Persoon.Adres.AangeverAdreshoudingCode", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.ActieAanpassingGeldigheid. */
    PERSOON_ADRES_ACTIEAANPASSINGGELDIGHEID((short) 6072, "Persoon.Adres.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.ActieInhoud. */
    PERSOON_ADRES_ACTIEINHOUD((short) 6070, "Persoon.Adres.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.ActieVerval. */
    PERSOON_ADRES_ACTIEVERVAL((short) 6071, "Persoon.Adres.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.ActieVervalTbvLeveringMutaties. */
    PERSOON_ADRES_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18872, "Persoon.Adres.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.AfgekorteNaamOpenbareRuimte. */
    PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE((short) 3267, "Persoon.Adres.AfgekorteNaamOpenbareRuimte", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.BuitenlandsAdresRegel1. */
    PERSOON_ADRES_BUITENLANDSADRESREGEL1((short) 3291, "Persoon.Adres.BuitenlandsAdresRegel1", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.BuitenlandsAdresRegel2. */
    PERSOON_ADRES_BUITENLANDSADRESREGEL2((short) 3292, "Persoon.Adres.BuitenlandsAdresRegel2", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.BuitenlandsAdresRegel3. */
    PERSOON_ADRES_BUITENLANDSADRESREGEL3((short) 3293, "Persoon.Adres.BuitenlandsAdresRegel3", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.BuitenlandsAdresRegel4. */
    PERSOON_ADRES_BUITENLANDSADRESREGEL4((short) 3709, "Persoon.Adres.BuitenlandsAdresRegel4", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.BuitenlandsAdresRegel5. */
    PERSOON_ADRES_BUITENLANDSADRESREGEL5((short) 3710, "Persoon.Adres.BuitenlandsAdresRegel5", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.BuitenlandsAdresRegel6. */
    PERSOON_ADRES_BUITENLANDSADRESREGEL6((short) 3711, "Persoon.Adres.BuitenlandsAdresRegel6", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.DatumAanvangAdreshouding. */
    PERSOON_ADRES_DATUMAANVANGADRESHOUDING((short) 3730, "Persoon.Adres.DatumAanvangAdreshouding", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.DatumAanvangGeldigheid. */
    PERSOON_ADRES_DATUMAANVANGGELDIGHEID((short) 6066, "Persoon.Adres.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.DatumEindeGeldigheid. */
    PERSOON_ADRES_DATUMEINDEGELDIGHEID((short) 6067, "Persoon.Adres.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.GemeenteCode. */
    PERSOON_ADRES_GEMEENTECODE((short) 3788, "Persoon.Adres.GemeenteCode", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.Gemeentedeel. */
    PERSOON_ADRES_GEMEENTEDEEL((short) 3265, "Persoon.Adres.Gemeentedeel", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.Huisletter. */
    PERSOON_ADRES_HUISLETTER((short) 3273, "Persoon.Adres.Huisletter", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.Huisnummer. */
    PERSOON_ADRES_HUISNUMMER((short) 3271, "Persoon.Adres.Huisnummer", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.Huisnummertoevoeging. */
    PERSOON_ADRES_HUISNUMMERTOEVOEGING((short) 3275, "Persoon.Adres.Huisnummertoevoeging", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.IdentificatiecodeAdresseerbaarObject. */
    PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT((short) 3284, "Persoon.Adres.IdentificatiecodeAdresseerbaarObject", SoortElement.ATTRIBUUT,
            PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.IdentificatiecodeNummeraanduiding. */
    PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING((short) 3286, "Persoon.Adres.IdentificatiecodeNummeraanduiding", SoortElement.ATTRIBUUT,
            PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.IndicatiePersoonAangetroffenOpAdres. */
    PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES((short) 9540, "Persoon.Adres.IndicatiePersoonAangetroffenOpAdres", SoortElement.ATTRIBUUT,
            PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_ADRES_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18873, "Persoon.Adres.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.LandGebiedCode. */
    PERSOON_ADRES_LANDGEBIEDCODE((short) 3289, "Persoon.Adres.LandGebiedCode", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.LocatieTenOpzichteVanAdres. */
    PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES((short) 3278, "Persoon.Adres.LocatieTenOpzichteVanAdres", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.Locatieomschrijving. */
    PERSOON_ADRES_LOCATIEOMSCHRIJVING((short) 3288, "Persoon.Adres.Locatieomschrijving", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.NaamOpenbareRuimte. */
    PERSOON_ADRES_NAAMOPENBARERUIMTE((short) 3269, "Persoon.Adres.NaamOpenbareRuimte", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.NadereAanduidingVerval. */
    PERSOON_ADRES_NADEREAANDUIDINGVERVAL((short) 11141, "Persoon.Adres.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.ObjectSleutel. */
    PERSOON_ADRES_OBJECTSLEUTEL((short) 3239, "Persoon.Adres.ObjectSleutel", SoortElement.ATTRIBUUT, PERSOON_ADRES_IDENTITEIT),
    /** Persoon.Adres.Persoon. */
    PERSOON_ADRES_PERSOON((short) 3241, "Persoon.Adres.Persoon", SoortElement.ATTRIBUUT, PERSOON_ADRES_IDENTITEIT),
    /** Persoon.Adres.PersoonAdres. */
    PERSOON_ADRES_PERSOONADRES((short) 6065, "Persoon.Adres.PersoonAdres", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.Postcode. */
    PERSOON_ADRES_POSTCODE((short) 3281, "Persoon.Adres.Postcode", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.RedenWijzigingCode. */
    PERSOON_ADRES_REDENWIJZIGINGCODE((short) 3715, "Persoon.Adres.RedenWijzigingCode", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.SoortCode. */
    PERSOON_ADRES_SOORTCODE((short) 3263, "Persoon.Adres.SoortCode", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.TijdstipRegistratie. */
    PERSOON_ADRES_TIJDSTIPREGISTRATIE((short) 6068, "Persoon.Adres.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.TijdstipVerval. */
    PERSOON_ADRES_TIJDSTIPVERVAL((short) 6069, "Persoon.Adres.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.VoorkomenSleutel. */
    PERSOON_ADRES_VOORKOMENSLEUTEL((short) 6075, "Persoon.Adres.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.Adres.Woonplaatsnaam. */
    PERSOON_ADRES_WOONPLAATSNAAM((short) 3282, "Persoon.Adres.Woonplaatsnaam", SoortElement.ATTRIBUUT, PERSOON_ADRES_STANDAARD),
    /** Persoon.AfgeleidAdministratief.ActieInhoud. */
    PERSOON_AFGELEIDADMINISTRATIEF_ACTIEINHOUD((short) 10151, "Persoon.AfgeleidAdministratief.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.ActieVerval. */
    PERSOON_AFGELEIDADMINISTRATIEF_ACTIEVERVAL((short) 10152, "Persoon.AfgeleidAdministratief.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.ActieVervalTbvLeveringMutaties. */
    PERSOON_AFGELEIDADMINISTRATIEF_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18842, "Persoon.AfgeleidAdministratief.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.AdministratieveHandeling. */
    PERSOON_AFGELEIDADMINISTRATIEF_ADMINISTRATIEVEHANDELING((short) 10111, "Persoon.AfgeleidAdministratief.AdministratieveHandeling",
            SoortElement.ATTRIBUUT, PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.IndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig. */
    PERSOON_AFGELEIDADMINISTRATIEF_INDICATIEONVERWERKTBIJHOUDINGSVOORSTELNIETINGEZETENEAANWEZIG((short) 10899,
            "Persoon.AfgeleidAdministratief.IndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig", SoortElement.ATTRIBUUT,
            PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_AFGELEIDADMINISTRATIEF_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18843,
            "Persoon.AfgeleidAdministratief.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.NadereAanduidingVerval. */
    PERSOON_AFGELEIDADMINISTRATIEF_NADEREAANDUIDINGVERVAL((short) 11126, "Persoon.AfgeleidAdministratief.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.Persoon. */
    PERSOON_AFGELEIDADMINISTRATIEF_PERSOON((short) 10148, "Persoon.AfgeleidAdministratief.Persoon", SoortElement.ATTRIBUUT, PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.Sorteervolgorde. */
    PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE((short) 10404, "Persoon.AfgeleidAdministratief.Sorteervolgorde", SoortElement.ATTRIBUUT,
            PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.TijdstipLaatsteWijziging. */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING((short) 3251, "Persoon.AfgeleidAdministratief.TijdstipLaatsteWijziging",
            SoortElement.ATTRIBUUT, PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.TijdstipLaatsteWijzigingGBASystematiek. */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK((short) 10901,
            "Persoon.AfgeleidAdministratief.TijdstipLaatsteWijzigingGBASystematiek", SoortElement.ATTRIBUUT, PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.TijdstipRegistratie. */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPREGISTRATIE((short) 10149, "Persoon.AfgeleidAdministratief.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.TijdstipVerval. */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPVERVAL((short) 10150, "Persoon.AfgeleidAdministratief.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.AfgeleidAdministratief.VoorkomenSleutel. */
    PERSOON_AFGELEIDADMINISTRATIEF_VOORKOMENSLEUTEL((short) 10169, "Persoon.AfgeleidAdministratief.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_AFGELEIDADMINISTRATIEF),
    /** Persoon.Afnemerindicatie.AfnemerCode. */
    PERSOON_AFNEMERINDICATIE_AFNEMERCODE((short) 10343, "Persoon.Afnemerindicatie.AfnemerCode", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_IDENTITEIT),
    /** Persoon.Afnemerindicatie.DatumAanvangMaterielePeriode. */
    PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE((short) 10327, "Persoon.Afnemerindicatie.DatumAanvangMaterielePeriode", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Afnemerindicatie.DatumEindeVolgen. */
    PERSOON_AFNEMERINDICATIE_DATUMEINDEVOLGEN((short) 10328, "Persoon.Afnemerindicatie.DatumEindeVolgen", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Afnemerindicatie.DienstInhoud. */
    PERSOON_AFNEMERINDICATIE_DIENSTINHOUD((short) 11418, "Persoon.Afnemerindicatie.DienstInhoud", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Afnemerindicatie.DienstVerval. */
    PERSOON_AFNEMERINDICATIE_DIENSTVERVAL((short) 11419, "Persoon.Afnemerindicatie.DienstVerval", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Afnemerindicatie.LeveringsAutorisatieIdentificatie. */
    PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE((short) 10323, "Persoon.Afnemerindicatie.LeveringsAutorisatieIdentificatie",
            SoortElement.ATTRIBUUT, PERSOON_AFNEMERINDICATIE_IDENTITEIT),
    /** Persoon.Afnemerindicatie.NadereAanduidingVerval. */
    PERSOON_AFNEMERINDICATIE_NADEREAANDUIDINGVERVAL((short) 11142, "Persoon.Afnemerindicatie.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Afnemerindicatie.ObjectSleutel. */
    PERSOON_AFNEMERINDICATIE_OBJECTSLEUTEL((short) 10321, "Persoon.Afnemerindicatie.ObjectSleutel", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_IDENTITEIT),
    /** Persoon.Afnemerindicatie.Persoon. */
    PERSOON_AFNEMERINDICATIE_PERSOON((short) 10324, "Persoon.Afnemerindicatie.Persoon", SoortElement.ATTRIBUUT, PERSOON_AFNEMERINDICATIE_IDENTITEIT),
    /** Persoon.Afnemerindicatie.PersoonAfnemerindicatie. */
    PERSOON_AFNEMERINDICATIE_PERSOONAFNEMERINDICATIE((short) 10348, "Persoon.Afnemerindicatie.PersoonAfnemerindicatie", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Afnemerindicatie.TijdstipRegistratie. */
    PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE((short) 10331, "Persoon.Afnemerindicatie.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Afnemerindicatie.TijdstipVerval. */
    PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL((short) 10332, "Persoon.Afnemerindicatie.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Afnemerindicatie.VoorkomenSleutel. */
    PERSOON_AFNEMERINDICATIE_VOORKOMENSLEUTEL((short) 10341, "Persoon.Afnemerindicatie.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_AFNEMERINDICATIE_STANDAARD),
    /** Persoon.Bijhouding.ActieAanpassingGeldigheid. */
    PERSOON_BIJHOUDING_ACTIEAANPASSINGGELDIGHEID((short) 4195, "Persoon.Bijhouding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.ActieInhoud. */
    PERSOON_BIJHOUDING_ACTIEINHOUD((short) 4193, "Persoon.Bijhouding.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.ActieVerval. */
    PERSOON_BIJHOUDING_ACTIEVERVAL((short) 4194, "Persoon.Bijhouding.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.ActieVervalTbvLeveringMutaties. */
    PERSOON_BIJHOUDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18856, "Persoon.Bijhouding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.BijhoudingsaardCode. */
    PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE((short) 3568, "Persoon.Bijhouding.BijhoudingsaardCode", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.DatumAanvangGeldigheid. */
    PERSOON_BIJHOUDING_DATUMAANVANGGELDIGHEID((short) 4189, "Persoon.Bijhouding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.DatumEindeGeldigheid. */
    PERSOON_BIJHOUDING_DATUMEINDEGELDIGHEID((short) 4190, "Persoon.Bijhouding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.IndicatieOnverwerktDocumentAanwezig. */
    PERSOON_BIJHOUDING_INDICATIEONVERWERKTDOCUMENTAANWEZIG((short) 3578, "Persoon.Bijhouding.IndicatieOnverwerktDocumentAanwezig", SoortElement.ATTRIBUUT,
            PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_BIJHOUDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18857, "Persoon.Bijhouding.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.NadereAanduidingVerval. */
    PERSOON_BIJHOUDING_NADEREAANDUIDINGVERVAL((short) 11133, "Persoon.Bijhouding.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.NadereBijhoudingsaardCode. */
    PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE((short) 10864, "Persoon.Bijhouding.NadereBijhoudingsaardCode", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.PartijCode. */
    PERSOON_BIJHOUDING_PARTIJCODE((short) 3573, "Persoon.Bijhouding.PartijCode", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.Persoon. */
    PERSOON_BIJHOUDING_PERSOON((short) 4188, "Persoon.Bijhouding.Persoon", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.TijdstipRegistratie. */
    PERSOON_BIJHOUDING_TIJDSTIPREGISTRATIE((short) 4191, "Persoon.Bijhouding.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.TijdstipVerval. */
    PERSOON_BIJHOUDING_TIJDSTIPVERVAL((short) 4192, "Persoon.Bijhouding.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.Bijhouding.VoorkomenSleutel. */
    PERSOON_BIJHOUDING_VOORKOMENSLEUTEL((short) 4551, "Persoon.Bijhouding.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_BIJHOUDING),
    /** Persoon.DeelnameEUVerkiezingen.ActieInhoud. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_ACTIEINHOUD((short) 4182, "Persoon.DeelnameEUVerkiezingen.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.ActieVerval. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_ACTIEVERVAL((short) 4183, "Persoon.DeelnameEUVerkiezingen.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.ActieVervalTbvLeveringMutaties. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18868, "Persoon.DeelnameEUVerkiezingen.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.DatumAanleidingAanpassing. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING((short) 3562, "Persoon.DeelnameEUVerkiezingen.DatumAanleidingAanpassing",
            SoortElement.ATTRIBUUT, PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.DatumVoorzienEindeUitsluiting. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING((short) 3564, "Persoon.DeelnameEUVerkiezingen.DatumVoorzienEindeUitsluiting",
            SoortElement.ATTRIBUUT, PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.IndicatieDeelname. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME((short) 3320, "Persoon.DeelnameEUVerkiezingen.IndicatieDeelname", SoortElement.ATTRIBUUT,
            PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18869,
            "Persoon.DeelnameEUVerkiezingen.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.NadereAanduidingVerval. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_NADEREAANDUIDINGVERVAL((short) 11139, "Persoon.DeelnameEUVerkiezingen.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.Persoon. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_PERSOON((short) 4179, "Persoon.DeelnameEUVerkiezingen.Persoon", SoortElement.ATTRIBUUT, PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.TijdstipRegistratie. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_TIJDSTIPREGISTRATIE((short) 4180, "Persoon.DeelnameEUVerkiezingen.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.TijdstipVerval. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_TIJDSTIPVERVAL((short) 4181, "Persoon.DeelnameEUVerkiezingen.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.DeelnameEUVerkiezingen.VoorkomenSleutel. */
    PERSOON_DEELNAMEEUVERKIEZINGEN_VOORKOMENSLEUTEL((short) 4548, "Persoon.DeelnameEUVerkiezingen.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_DEELNAMEEUVERKIEZINGEN),
    /** Persoon.Geboorte.ActieInhoud. */
    PERSOON_GEBOORTE_ACTIEINHOUD((short) 4135, "Persoon.Geboorte.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.ActieVerval. */
    PERSOON_GEBOORTE_ACTIEVERVAL((short) 4136, "Persoon.Geboorte.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.ActieVervalTbvLeveringMutaties. */
    PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18848, "Persoon.Geboorte.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_GEBOORTE),
    /** Persoon.Geboorte.BuitenlandsePlaats. */
    PERSOON_GEBOORTE_BUITENLANDSEPLAATS((short) 3677, "Persoon.Geboorte.BuitenlandsePlaats", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.BuitenlandseRegio. */
    PERSOON_GEBOORTE_BUITENLANDSEREGIO((short) 3530, "Persoon.Geboorte.BuitenlandseRegio", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.Datum. */
    PERSOON_GEBOORTE_DATUM((short) 3673, "Persoon.Geboorte.Datum", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.GemeenteCode. */
    PERSOON_GEBOORTE_GEMEENTECODE((short) 3675, "Persoon.Geboorte.GemeenteCode", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18849, "Persoon.Geboorte.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.LandGebiedCode. */
    PERSOON_GEBOORTE_LANDGEBIEDCODE((short) 3543, "Persoon.Geboorte.LandGebiedCode", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.NadereAanduidingVerval. */
    PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL((short) 11129, "Persoon.Geboorte.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.OmschrijvingLocatie. */
    PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE((short) 3678, "Persoon.Geboorte.OmschrijvingLocatie", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.Persoon. */
    PERSOON_GEBOORTE_PERSOON((short) 4132, "Persoon.Geboorte.Persoon", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.TijdstipRegistratie. */
    PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE((short) 4133, "Persoon.Geboorte.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.TijdstipVerval. */
    PERSOON_GEBOORTE_TIJDSTIPVERVAL((short) 4134, "Persoon.Geboorte.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.VoorkomenSleutel. */
    PERSOON_GEBOORTE_VOORKOMENSLEUTEL((short) 4536, "Persoon.Geboorte.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geboorte.Woonplaatsnaam. */
    PERSOON_GEBOORTE_WOONPLAATSNAAM((short) 3676, "Persoon.Geboorte.Woonplaatsnaam", SoortElement.ATTRIBUUT, PERSOON_GEBOORTE),
    /** Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid. */
    PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID((short) 4095, "Persoon.Geslachtsaanduiding.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.ActieInhoud. */
    PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD((short) 4093, "Persoon.Geslachtsaanduiding.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.ActieVerval. */
    PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL((short) 4094, "Persoon.Geslachtsaanduiding.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties. */
    PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18850, "Persoon.Geslachtsaanduiding.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.Code. */
    PERSOON_GESLACHTSAANDUIDING_CODE((short) 3031, "Persoon.Geslachtsaanduiding.Code", SoortElement.ATTRIBUUT, PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid. */
    PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID((short) 4089, "Persoon.Geslachtsaanduiding.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.DatumEindeGeldigheid. */
    PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID((short) 4090, "Persoon.Geslachtsaanduiding.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18851, "Persoon.Geslachtsaanduiding.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.NadereAanduidingVerval. */
    PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL((short) 11130, "Persoon.Geslachtsaanduiding.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.Persoon. */
    PERSOON_GESLACHTSAANDUIDING_PERSOON((short) 4088, "Persoon.Geslachtsaanduiding.Persoon", SoortElement.ATTRIBUUT, PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.TijdstipRegistratie. */
    PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE((short) 4091, "Persoon.Geslachtsaanduiding.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.TijdstipVerval. */
    PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL((short) 4092, "Persoon.Geslachtsaanduiding.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsaanduiding.VoorkomenSleutel. */
    PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL((short) 4527, "Persoon.Geslachtsaanduiding.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSAANDUIDING),
    /** Persoon.Geslachtsnaamcomponent.ActieAanpassingGeldigheid. */
    PERSOON_GESLACHTSNAAMCOMPONENT_ACTIEAANPASSINGGELDIGHEID((short) 4308, "Persoon.Geslachtsnaamcomponent.ActieAanpassingGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.ActieInhoud. */
    PERSOON_GESLACHTSNAAMCOMPONENT_ACTIEINHOUD((short) 4306, "Persoon.Geslachtsnaamcomponent.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.ActieVerval. */
    PERSOON_GESLACHTSNAAMCOMPONENT_ACTIEVERVAL((short) 4307, "Persoon.Geslachtsnaamcomponent.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.ActieVervalTbvLeveringMutaties. */
    PERSOON_GESLACHTSNAAMCOMPONENT_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18874, "Persoon.Geslachtsnaamcomponent.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.AdellijkeTitelCode. */
    PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE((short) 3118, "Persoon.Geslachtsnaamcomponent.AdellijkeTitelCode", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.DatumAanvangGeldigheid. */
    PERSOON_GESLACHTSNAAMCOMPONENT_DATUMAANVANGGELDIGHEID((short) 4302, "Persoon.Geslachtsnaamcomponent.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.DatumEindeGeldigheid. */
    PERSOON_GESLACHTSNAAMCOMPONENT_DATUMEINDEGELDIGHEID((short) 4303, "Persoon.Geslachtsnaamcomponent.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_GESLACHTSNAAMCOMPONENT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18875,
            "Persoon.Geslachtsnaamcomponent.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.NadereAanduidingVerval. */
    PERSOON_GESLACHTSNAAMCOMPONENT_NADEREAANDUIDINGVERVAL((short) 11143, "Persoon.Geslachtsnaamcomponent.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.ObjectSleutel. */
    PERSOON_GESLACHTSNAAMCOMPONENT_OBJECTSLEUTEL((short) 3648, "Persoon.Geslachtsnaamcomponent.ObjectSleutel", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT),
    /** Persoon.Geslachtsnaamcomponent.Persoon. */
    PERSOON_GESLACHTSNAAMCOMPONENT_PERSOON((short) 3024, "Persoon.Geslachtsnaamcomponent.Persoon", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT),
    /** Persoon.Geslachtsnaamcomponent.PersoonGeslachtsnaamcomponent. */
    PERSOON_GESLACHTSNAAMCOMPONENT_PERSOONGESLACHTSNAAMCOMPONENT((short) 4301, "Persoon.Geslachtsnaamcomponent.PersoonGeslachtsnaamcomponent",
            SoortElement.ATTRIBUUT, PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.PredicaatCode. */
    PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE((short) 3117, "Persoon.Geslachtsnaamcomponent.PredicaatCode", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.Scheidingsteken. */
    PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN((short) 3069, "Persoon.Geslachtsnaamcomponent.Scheidingsteken", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.Stam. */
    PERSOON_GESLACHTSNAAMCOMPONENT_STAM((short) 3025, "Persoon.Geslachtsnaamcomponent.Stam", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.TijdstipRegistratie. */
    PERSOON_GESLACHTSNAAMCOMPONENT_TIJDSTIPREGISTRATIE((short) 4304, "Persoon.Geslachtsnaamcomponent.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.TijdstipVerval. */
    PERSOON_GESLACHTSNAAMCOMPONENT_TIJDSTIPVERVAL((short) 4305, "Persoon.Geslachtsnaamcomponent.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.Volgnummer. */
    PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER((short) 3029, "Persoon.Geslachtsnaamcomponent.Volgnummer", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT),
    /** Persoon.Geslachtsnaamcomponent.VoorkomenSleutel. */
    PERSOON_GESLACHTSNAAMCOMPONENT_VOORKOMENSLEUTEL((short) 4578, "Persoon.Geslachtsnaamcomponent.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Geslachtsnaamcomponent.Voorvoegsel. */
    PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL((short) 3030, "Persoon.Geslachtsnaamcomponent.Voorvoegsel", SoortElement.ATTRIBUUT,
            PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /** Persoon.Identificatienummers.ActieAanpassingGeldigheid. */
    PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID((short) 4084, "Persoon.Identificatienummers.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.ActieInhoud. */
    PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD((short) 4082, "Persoon.Identificatienummers.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.ActieVerval. */
    PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL((short) 4083, "Persoon.Identificatienummers.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties. */
    PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18844, "Persoon.Identificatienummers.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.Administratienummer. */
    PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER((short) 3013, "Persoon.Identificatienummers.Administratienummer", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.Burgerservicenummer. */
    PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER((short) 3018, "Persoon.Identificatienummers.Burgerservicenummer", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.DatumAanvangGeldigheid. */
    PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID((short) 4078, "Persoon.Identificatienummers.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.DatumEindeGeldigheid. */
    PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID((short) 4079, "Persoon.Identificatienummers.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18845,
            "Persoon.Identificatienummers.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.NadereAanduidingVerval. */
    PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL((short) 11127, "Persoon.Identificatienummers.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.Persoon. */
    PERSOON_IDENTIFICATIENUMMERS_PERSOON((short) 4077, "Persoon.Identificatienummers.Persoon", SoortElement.ATTRIBUUT, PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.TijdstipRegistratie. */
    PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE((short) 4080, "Persoon.Identificatienummers.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.TijdstipVerval. */
    PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL((short) 4081, "Persoon.Identificatienummers.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Identificatienummers.VoorkomenSleutel. */
    PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL((short) 4524, "Persoon.Identificatienummers.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_IDENTIFICATIENUMMERS),
    /** Persoon.Indicatie.ActieAanpassingGeldigheid. */
    PERSOON_INDICATIE_ACTIEAANPASSINGGELDIGHEID((short) 4322, "Persoon.Indicatie.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.ActieInhoud. */
    PERSOON_INDICATIE_ACTIEINHOUD((short) 4320, "Persoon.Indicatie.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.ActieVerval. */
    PERSOON_INDICATIE_ACTIEVERVAL((short) 4321, "Persoon.Indicatie.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.ActieVervalTbvLeveringMutaties. */
    PERSOON_INDICATIE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18876, "Persoon.Indicatie.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.BehandeldAlsNederlander. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER((short) 14399, "Persoon.Indicatie.BehandeldAlsNederlander", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BehandeldAlsNederlander.ActieAanpassingGeldigheid. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_ACTIEAANPASSINGGELDIGHEID((short) 14160,
            "Persoon.Indicatie.BehandeldAlsNederlander.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BehandeldAlsNederlander.ActieInhoud. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_ACTIEINHOUD((short) 14159, "Persoon.Indicatie.BehandeldAlsNederlander.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BehandeldAlsNederlander.ActieVerval. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_ACTIEVERVAL((short) 13929, "Persoon.Indicatie.BehandeldAlsNederlander.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BehandeldAlsNederlander.DatumAanvangGeldigheid. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_DATUMAANVANGGELDIGHEID((short) 14155, "Persoon.Indicatie.BehandeldAlsNederlander.DatumAanvangGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BehandeldAlsNederlander.DatumEindeGeldigheid. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_DATUMEINDEGELDIGHEID((short) 14156, "Persoon.Indicatie.BehandeldAlsNederlander.DatumEindeGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BehandeldAlsNederlander.NadereAanduidingVerval. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_NADEREAANDUIDINGVERVAL((short) 14161, "Persoon.Indicatie.BehandeldAlsNederlander.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BehandeldAlsNederlander.TijdstipRegistratie. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_TIJDSTIPREGISTRATIE((short) 14182, "Persoon.Indicatie.BehandeldAlsNederlander.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BehandeldAlsNederlander.TijdstipVerval. */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_TIJDSTIPVERVAL((short) 14183, "Persoon.Indicatie.BehandeldAlsNederlander.TijdstipVerval",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP),
    /** Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie. */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE((short) 14395, "Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_GROEP),
    /** Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.ActieInhoud. */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_ACTIEINHOUD((short) 14135,
            "Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_GROEP),
    /** Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.ActieVerval. */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_ACTIEVERVAL((short) 13901,
            "Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_GROEP),
    /** Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.NadereAanduidingVerval. */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_NADEREAANDUIDINGVERVAL((short) 14136,
            "Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_GROEP),
    /** Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.TijdstipRegistratie. */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_TIJDSTIPREGISTRATIE((short) 14174,
            "Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_GROEP),
    /** Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.TijdstipVerval. */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_TIJDSTIPVERVAL((short) 14175,
            "Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_GROEP),
    /** Persoon.Indicatie.DatumAanvangGeldigheid. */
    PERSOON_INDICATIE_DATUMAANVANGGELDIGHEID((short) 4316, "Persoon.Indicatie.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.DatumEindeGeldigheid. */
    PERSOON_INDICATIE_DATUMEINDEGELDIGHEID((short) 4317, "Persoon.Indicatie.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.DerdeHeeftGezag. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG((short) 14393, "Persoon.Indicatie.DerdeHeeftGezag", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.DerdeHeeftGezag.ActieAanpassingGeldigheid. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_ACTIEAANPASSINGGELDIGHEID((short) 14124, "Persoon.Indicatie.DerdeHeeftGezag.ActieAanpassingGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.DerdeHeeftGezag.ActieInhoud. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_ACTIEINHOUD((short) 14123, "Persoon.Indicatie.DerdeHeeftGezag.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.DerdeHeeftGezag.ActieVerval. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_ACTIEVERVAL((short) 13887, "Persoon.Indicatie.DerdeHeeftGezag.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.DerdeHeeftGezag.DatumAanvangGeldigheid. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_DATUMAANVANGGELDIGHEID((short) 14119, "Persoon.Indicatie.DerdeHeeftGezag.DatumAanvangGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.DerdeHeeftGezag.DatumEindeGeldigheid. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_DATUMEINDEGELDIGHEID((short) 14120, "Persoon.Indicatie.DerdeHeeftGezag.DatumEindeGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.DerdeHeeftGezag.NadereAanduidingVerval. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_NADEREAANDUIDINGVERVAL((short) 14125, "Persoon.Indicatie.DerdeHeeftGezag.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.DerdeHeeftGezag.TijdstipRegistratie. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_TIJDSTIPREGISTRATIE((short) 14170, "Persoon.Indicatie.DerdeHeeftGezag.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.DerdeHeeftGezag.TijdstipVerval. */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_TIJDSTIPVERVAL((short) 14171, "Persoon.Indicatie.DerdeHeeftGezag.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP),
    /** Persoon.Indicatie.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_INDICATIE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18877, "Persoon.Indicatie.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.MigratieRedenBeeindigenNationaliteit. */
    PERSOON_INDICATIE_MIGRATIEREDENBEEINDIGENNATIONALITEIT((short) 21235, "Persoon.Indicatie.MigratieRedenBeeindigenNationaliteit",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.MigratieRedenOpnameNationaliteit. */
    PERSOON_INDICATIE_MIGRATIEREDENOPNAMENATIONALITEIT((short) 21234, "Persoon.Indicatie.MigratieRedenOpnameNationaliteit", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.NadereAanduidingVerval. */
    PERSOON_INDICATIE_NADEREAANDUIDINGVERVAL((short) 11144, "Persoon.Indicatie.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.ObjectSleutel. */
    PERSOON_INDICATIE_OBJECTSLEUTEL((short) 3656, "Persoon.Indicatie.ObjectSleutel", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_IDENTITEIT),
    /** Persoon.Indicatie.OnderCuratele. */
    PERSOON_INDICATIE_ONDERCURATELE((short) 14394, "Persoon.Indicatie.OnderCuratele", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.OnderCuratele.ActieAanpassingGeldigheid. */
    PERSOON_INDICATIE_ONDERCURATELE_ACTIEAANPASSINGGELDIGHEID((short) 14131, "Persoon.Indicatie.OnderCuratele.ActieAanpassingGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.OnderCuratele.ActieInhoud. */
    PERSOON_INDICATIE_ONDERCURATELE_ACTIEINHOUD((short) 14130, "Persoon.Indicatie.OnderCuratele.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.OnderCuratele.ActieVerval. */
    PERSOON_INDICATIE_ONDERCURATELE_ACTIEVERVAL((short) 13895, "Persoon.Indicatie.OnderCuratele.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.OnderCuratele.DatumAanvangGeldigheid. */
    PERSOON_INDICATIE_ONDERCURATELE_DATUMAANVANGGELDIGHEID((short) 14126, "Persoon.Indicatie.OnderCuratele.DatumAanvangGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.OnderCuratele.DatumEindeGeldigheid. */
    PERSOON_INDICATIE_ONDERCURATELE_DATUMEINDEGELDIGHEID((short) 14127, "Persoon.Indicatie.OnderCuratele.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.OnderCuratele.NadereAanduidingVerval. */
    PERSOON_INDICATIE_ONDERCURATELE_NADEREAANDUIDINGVERVAL((short) 14132, "Persoon.Indicatie.OnderCuratele.NadereAanduidingVerval",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.OnderCuratele.TijdstipRegistratie. */
    PERSOON_INDICATIE_ONDERCURATELE_TIJDSTIPREGISTRATIE((short) 14172, "Persoon.Indicatie.OnderCuratele.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.OnderCuratele.TijdstipVerval. */
    PERSOON_INDICATIE_ONDERCURATELE_TIJDSTIPVERVAL((short) 14173, "Persoon.Indicatie.OnderCuratele.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_ONDERCURATELE_GROEP),
    /** Persoon.Indicatie.Persoon. */
    PERSOON_INDICATIE_PERSOON((short) 3657, "Persoon.Indicatie.Persoon", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_IDENTITEIT),
    /** Persoon.Indicatie.PersoonIndicatie. */
    PERSOON_INDICATIE_PERSOONINDICATIE((short) 4315, "Persoon.Indicatie.PersoonIndicatie", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument. */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT((short) 14400,
            "Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP),
    /** Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.ActieInhoud. */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_ACTIEINHOUD((short) 14164,
            "Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP),
    /** Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.ActieVerval. */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_ACTIEVERVAL((short) 13935,
            "Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP),
    /** Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.NadereAanduidingVerval. */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_NADEREAANDUIDINGVERVAL((short) 14165,
            "Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP),
    /** Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.TijdstipRegistratie. */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_TIJDSTIPREGISTRATIE((short) 14184,
            "Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP),
    /** Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.TijdstipVerval. */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_TIJDSTIPVERVAL((short) 14185,
            "Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP),
    /** Persoon.Indicatie.SoortNaam. */
    PERSOON_INDICATIE_SOORTNAAM((short) 3658, "Persoon.Indicatie.SoortNaam", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_IDENTITEIT),
    /** Persoon.Indicatie.Staatloos. */
    PERSOON_INDICATIE_STAATLOOS((short) 14396, "Persoon.Indicatie.Staatloos", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.Staatloos.ActieAanpassingGeldigheid. */
    PERSOON_INDICATIE_STAATLOOS_ACTIEAANPASSINGGELDIGHEID((short) 14142, "Persoon.Indicatie.Staatloos.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.Staatloos.ActieInhoud. */
    PERSOON_INDICATIE_STAATLOOS_ACTIEINHOUD((short) 14141, "Persoon.Indicatie.Staatloos.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.Staatloos.ActieVerval. */
    PERSOON_INDICATIE_STAATLOOS_ACTIEVERVAL((short) 13908, "Persoon.Indicatie.Staatloos.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.Staatloos.DatumAanvangGeldigheid. */
    PERSOON_INDICATIE_STAATLOOS_DATUMAANVANGGELDIGHEID((short) 14137, "Persoon.Indicatie.Staatloos.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.Staatloos.DatumEindeGeldigheid. */
    PERSOON_INDICATIE_STAATLOOS_DATUMEINDEGELDIGHEID((short) 14138, "Persoon.Indicatie.Staatloos.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.Staatloos.NadereAanduidingVerval. */
    PERSOON_INDICATIE_STAATLOOS_NADEREAANDUIDINGVERVAL((short) 14143, "Persoon.Indicatie.Staatloos.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.Staatloos.TijdstipRegistratie. */
    PERSOON_INDICATIE_STAATLOOS_TIJDSTIPREGISTRATIE((short) 14176, "Persoon.Indicatie.Staatloos.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.Staatloos.TijdstipVerval. */
    PERSOON_INDICATIE_STAATLOOS_TIJDSTIPVERVAL((short) 14177, "Persoon.Indicatie.Staatloos.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_STAATLOOS_GROEP),
    /** Persoon.Indicatie.TijdstipRegistratie. */
    PERSOON_INDICATIE_TIJDSTIPREGISTRATIE((short) 4318, "Persoon.Indicatie.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.TijdstipVerval. */
    PERSOON_INDICATIE_TIJDSTIPVERVAL((short) 4319, "Persoon.Indicatie.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.VastgesteldNietNederlander. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER((short) 14398, "Persoon.Indicatie.VastgesteldNietNederlander", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VastgesteldNietNederlander.ActieAanpassingGeldigheid. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_ACTIEAANPASSINGGELDIGHEID((short) 14153,
            "Persoon.Indicatie.VastgesteldNietNederlander.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VastgesteldNietNederlander.ActieInhoud. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_ACTIEINHOUD((short) 14152, "Persoon.Indicatie.VastgesteldNietNederlander.ActieInhoud",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VastgesteldNietNederlander.ActieVerval. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_ACTIEVERVAL((short) 13921, "Persoon.Indicatie.VastgesteldNietNederlander.ActieVerval",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VastgesteldNietNederlander.DatumAanvangGeldigheid. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_DATUMAANVANGGELDIGHEID((short) 14148,
            "Persoon.Indicatie.VastgesteldNietNederlander.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VastgesteldNietNederlander.DatumEindeGeldigheid. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_DATUMEINDEGELDIGHEID((short) 14149, "Persoon.Indicatie.VastgesteldNietNederlander.DatumEindeGeldigheid",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VastgesteldNietNederlander.NadereAanduidingVerval. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_NADEREAANDUIDINGVERVAL((short) 14154,
            "Persoon.Indicatie.VastgesteldNietNederlander.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VastgesteldNietNederlander.TijdstipRegistratie. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_TIJDSTIPREGISTRATIE((short) 14180, "Persoon.Indicatie.VastgesteldNietNederlander.TijdstipRegistratie",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VastgesteldNietNederlander.TijdstipVerval. */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_TIJDSTIPVERVAL((short) 14181, "Persoon.Indicatie.VastgesteldNietNederlander.TijdstipVerval",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP),
    /** Persoon.Indicatie.VolledigeVerstrekkingsbeperking. */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING((short) 14397, "Persoon.Indicatie.VolledigeVerstrekkingsbeperking", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP),
    /** Persoon.Indicatie.VolledigeVerstrekkingsbeperking.ActieInhoud. */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_ACTIEINHOUD((short) 14146, "Persoon.Indicatie.VolledigeVerstrekkingsbeperking.ActieInhoud",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP),
    /** Persoon.Indicatie.VolledigeVerstrekkingsbeperking.ActieVerval. */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_ACTIEVERVAL((short) 13914, "Persoon.Indicatie.VolledigeVerstrekkingsbeperking.ActieVerval",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP),
    /** Persoon.Indicatie.VolledigeVerstrekkingsbeperking.NadereAanduidingVerval. */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_NADEREAANDUIDINGVERVAL((short) 14147,
            "Persoon.Indicatie.VolledigeVerstrekkingsbeperking.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP),
    /** Persoon.Indicatie.VolledigeVerstrekkingsbeperking.TijdstipRegistratie. */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_TIJDSTIPREGISTRATIE((short) 14178,
            "Persoon.Indicatie.VolledigeVerstrekkingsbeperking.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP),
    /** Persoon.Indicatie.VolledigeVerstrekkingsbeperking.TijdstipVerval. */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_TIJDSTIPVERVAL((short) 14179, "Persoon.Indicatie.VolledigeVerstrekkingsbeperking.TijdstipVerval",
            SoortElement.ATTRIBUUT, PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP),
    /** Persoon.Indicatie.VoorkomenSleutel. */
    PERSOON_INDICATIE_VOORKOMENSLEUTEL((short) 4581, "Persoon.Indicatie.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Indicatie.Waarde. */
    PERSOON_INDICATIE_WAARDE((short) 3659, "Persoon.Indicatie.Waarde", SoortElement.ATTRIBUUT, PERSOON_INDICATIE_STANDAARD),
    /** Persoon.Inschrijving.ActieInhoud. */
    PERSOON_INSCHRIJVING_ACTIEINHOUD((short) 4241, "Persoon.Inschrijving.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.ActieVerval. */
    PERSOON_INSCHRIJVING_ACTIEVERVAL((short) 4242, "Persoon.Inschrijving.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.ActieVervalTbvLeveringMutaties. */
    PERSOON_INSCHRIJVING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18852, "Persoon.Inschrijving.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.Datum. */
    PERSOON_INSCHRIJVING_DATUM((short) 3570, "Persoon.Inschrijving.Datum", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.Datumtijdstempel. */
    PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL((short) 11186, "Persoon.Inschrijving.Datumtijdstempel", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_INSCHRIJVING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18853, "Persoon.Inschrijving.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.NadereAanduidingVerval. */
    PERSOON_INSCHRIJVING_NADEREAANDUIDINGVERVAL((short) 11131, "Persoon.Inschrijving.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.Persoon. */
    PERSOON_INSCHRIJVING_PERSOON((short) 4236, "Persoon.Inschrijving.Persoon", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.TijdstipRegistratie. */
    PERSOON_INSCHRIJVING_TIJDSTIPREGISTRATIE((short) 4239, "Persoon.Inschrijving.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.TijdstipVerval. */
    PERSOON_INSCHRIJVING_TIJDSTIPVERVAL((short) 4240, "Persoon.Inschrijving.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.Versienummer. */
    PERSOON_INSCHRIJVING_VERSIENUMMER((short) 3250, "Persoon.Inschrijving.Versienummer", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Inschrijving.VoorkomenSleutel. */
    PERSOON_INSCHRIJVING_VOORKOMENSLEUTEL((short) 4566, "Persoon.Inschrijving.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_INSCHRIJVING),
    /** Persoon.Migratie.AangeverCode. */
    PERSOON_MIGRATIE_AANGEVERCODE((short) 11278, "Persoon.Migratie.AangeverCode", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.ActieAanpassingGeldigheid. */
    PERSOON_MIGRATIE_ACTIEAANPASSINGGELDIGHEID((short) 4232, "Persoon.Migratie.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.ActieInhoud. */
    PERSOON_MIGRATIE_ACTIEINHOUD((short) 4230, "Persoon.Migratie.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.ActieVerval. */
    PERSOON_MIGRATIE_ACTIEVERVAL((short) 4231, "Persoon.Migratie.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.ActieVervalTbvLeveringMutaties. */
    PERSOON_MIGRATIE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18862, "Persoon.Migratie.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_MIGRATIE),
    /** Persoon.Migratie.BuitenlandsAdresRegel1. */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1((short) 10882, "Persoon.Migratie.BuitenlandsAdresRegel1", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.BuitenlandsAdresRegel2. */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2((short) 10883, "Persoon.Migratie.BuitenlandsAdresRegel2", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.BuitenlandsAdresRegel3. */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3((short) 10884, "Persoon.Migratie.BuitenlandsAdresRegel3", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.BuitenlandsAdresRegel4. */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4((short) 10885, "Persoon.Migratie.BuitenlandsAdresRegel4", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.BuitenlandsAdresRegel5. */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5((short) 10886, "Persoon.Migratie.BuitenlandsAdresRegel5", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.BuitenlandsAdresRegel6. */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6((short) 10887, "Persoon.Migratie.BuitenlandsAdresRegel6", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.DatumAanvangGeldigheid. */
    PERSOON_MIGRATIE_DATUMAANVANGGELDIGHEID((short) 4226, "Persoon.Migratie.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.DatumEindeGeldigheid. */
    PERSOON_MIGRATIE_DATUMEINDEGELDIGHEID((short) 4227, "Persoon.Migratie.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_MIGRATIE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18863, "Persoon.Migratie.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.LandGebiedCode. */
    PERSOON_MIGRATIE_LANDGEBIEDCODE((short) 3579, "Persoon.Migratie.LandGebiedCode", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.NadereAanduidingVerval. */
    PERSOON_MIGRATIE_NADEREAANDUIDINGVERVAL((short) 11136, "Persoon.Migratie.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.Persoon. */
    PERSOON_MIGRATIE_PERSOON((short) 4225, "Persoon.Migratie.Persoon", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.RedenWijzigingCode. */
    PERSOON_MIGRATIE_REDENWIJZIGINGCODE((short) 11277, "Persoon.Migratie.RedenWijzigingCode", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.SoortCode. */
    PERSOON_MIGRATIE_SOORTCODE((short) 10881, "Persoon.Migratie.SoortCode", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.TijdstipRegistratie. */
    PERSOON_MIGRATIE_TIJDSTIPREGISTRATIE((short) 4228, "Persoon.Migratie.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.TijdstipVerval. */
    PERSOON_MIGRATIE_TIJDSTIPVERVAL((short) 4229, "Persoon.Migratie.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Migratie.VoorkomenSleutel. */
    PERSOON_MIGRATIE_VOORKOMENSLEUTEL((short) 4563, "Persoon.Migratie.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_MIGRATIE),
    /** Persoon.Naamgebruik.ActieInhoud. */
    PERSOON_NAAMGEBRUIK_ACTIEINHOUD((short) 4120, "Persoon.Naamgebruik.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.ActieVerval. */
    PERSOON_NAAMGEBRUIK_ACTIEVERVAL((short) 4121, "Persoon.Naamgebruik.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.ActieVervalTbvLeveringMutaties. */
    PERSOON_NAAMGEBRUIK_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18860, "Persoon.Naamgebruik.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.AdellijkeTitelCode. */
    PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE((short) 6113, "Persoon.Naamgebruik.AdellijkeTitelCode", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.Code. */
    PERSOON_NAAMGEBRUIK_CODE((short) 3593, "Persoon.Naamgebruik.Code", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.Geslachtsnaamstam. */
    PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM((short) 3323, "Persoon.Naamgebruik.Geslachtsnaamstam", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.IndicatieAfgeleid. */
    PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID((short) 3633, "Persoon.Naamgebruik.IndicatieAfgeleid", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_NAAMGEBRUIK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18861, "Persoon.Naamgebruik.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.NadereAanduidingVerval. */
    PERSOON_NAAMGEBRUIK_NADEREAANDUIDINGVERVAL((short) 11135, "Persoon.Naamgebruik.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.Persoon. */
    PERSOON_NAAMGEBRUIK_PERSOON((short) 4115, "Persoon.Naamgebruik.Persoon", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.PredicaatCode. */
    PERSOON_NAAMGEBRUIK_PREDICAATCODE((short) 3703, "Persoon.Naamgebruik.PredicaatCode", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.Scheidingsteken. */
    PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN((short) 3580, "Persoon.Naamgebruik.Scheidingsteken", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.TijdstipRegistratie. */
    PERSOON_NAAMGEBRUIK_TIJDSTIPREGISTRATIE((short) 4118, "Persoon.Naamgebruik.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.TijdstipVerval. */
    PERSOON_NAAMGEBRUIK_TIJDSTIPVERVAL((short) 4119, "Persoon.Naamgebruik.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.VoorkomenSleutel. */
    PERSOON_NAAMGEBRUIK_VOORKOMENSLEUTEL((short) 4533, "Persoon.Naamgebruik.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.Voornamen. */
    PERSOON_NAAMGEBRUIK_VOORNAMEN((short) 3319, "Persoon.Naamgebruik.Voornamen", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Naamgebruik.Voorvoegsel. */
    PERSOON_NAAMGEBRUIK_VOORVOEGSEL((short) 3355, "Persoon.Naamgebruik.Voorvoegsel", SoortElement.ATTRIBUUT, PERSOON_NAAMGEBRUIK),
    /** Persoon.Nationaliteit.ActieAanpassingGeldigheid. */
    PERSOON_NATIONALITEIT_ACTIEAANPASSINGGELDIGHEID((short) 4332, "Persoon.Nationaliteit.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.ActieInhoud. */
    PERSOON_NATIONALITEIT_ACTIEINHOUD((short) 4330, "Persoon.Nationaliteit.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.ActieVerval. */
    PERSOON_NATIONALITEIT_ACTIEVERVAL((short) 4331, "Persoon.Nationaliteit.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.ActieVervalTbvLeveringMutaties. */
    PERSOON_NATIONALITEIT_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18878, "Persoon.Nationaliteit.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.DatumAanvangGeldigheid. */
    PERSOON_NATIONALITEIT_DATUMAANVANGGELDIGHEID((short) 4326, "Persoon.Nationaliteit.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.DatumEindeGeldigheid. */
    PERSOON_NATIONALITEIT_DATUMEINDEGELDIGHEID((short) 4327, "Persoon.Nationaliteit.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.IndicatieBijhoudingBeeindigd. */
    PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD((short) 21230, "Persoon.Nationaliteit.IndicatieBijhoudingBeeindigd", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_NATIONALITEIT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18879, "Persoon.Nationaliteit.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.MigratieDatumEindeBijhouding. */
    PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING((short) 21233, "Persoon.Nationaliteit.MigratieDatumEindeBijhouding", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.MigratieRedenBeeindigenNationaliteit. */
    PERSOON_NATIONALITEIT_MIGRATIEREDENBEEINDIGENNATIONALITEIT((short) 21232, "Persoon.Nationaliteit.MigratieRedenBeeindigenNationaliteit",
            SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.MigratieRedenOpnameNationaliteit. */
    PERSOON_NATIONALITEIT_MIGRATIEREDENOPNAMENATIONALITEIT((short) 21231, "Persoon.Nationaliteit.MigratieRedenOpnameNationaliteit",
            SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.NadereAanduidingVerval. */
    PERSOON_NATIONALITEIT_NADEREAANDUIDINGVERVAL((short) 11145, "Persoon.Nationaliteit.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.NationaliteitCode. */
    PERSOON_NATIONALITEIT_NATIONALITEITCODE((short) 3131, "Persoon.Nationaliteit.NationaliteitCode", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_IDENTITEIT),
    /** Persoon.Nationaliteit.ObjectSleutel. */
    PERSOON_NATIONALITEIT_OBJECTSLEUTEL((short) 3652, "Persoon.Nationaliteit.ObjectSleutel", SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_IDENTITEIT),
    /** Persoon.Nationaliteit.Persoon. */
    PERSOON_NATIONALITEIT_PERSOON((short) 3130, "Persoon.Nationaliteit.Persoon", SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_IDENTITEIT),
    /** Persoon.Nationaliteit.PersoonNationaliteit. */
    PERSOON_NATIONALITEIT_PERSOONNATIONALITEIT((short) 4325, "Persoon.Nationaliteit.PersoonNationaliteit", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.RedenVerkrijgingCode. */
    PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE((short) 3229, "Persoon.Nationaliteit.RedenVerkrijgingCode", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.RedenVerliesCode. */
    PERSOON_NATIONALITEIT_REDENVERLIESCODE((short) 3230, "Persoon.Nationaliteit.RedenVerliesCode", SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.TijdstipRegistratie. */
    PERSOON_NATIONALITEIT_TIJDSTIPREGISTRATIE((short) 4328, "Persoon.Nationaliteit.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.TijdstipVerval. */
    PERSOON_NATIONALITEIT_TIJDSTIPVERVAL((short) 4329, "Persoon.Nationaliteit.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nationaliteit.VoorkomenSleutel. */
    PERSOON_NATIONALITEIT_VOORKOMENSLEUTEL((short) 4584, "Persoon.Nationaliteit.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_NATIONALITEIT_STANDAARD),
    /** Persoon.Nummerverwijzing.ActieAanpassingGeldigheid. */
    PERSOON_NUMMERVERWIJZING_ACTIEAANPASSINGGELDIGHEID((short) 10940, "Persoon.Nummerverwijzing.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.ActieInhoud. */
    PERSOON_NUMMERVERWIJZING_ACTIEINHOUD((short) 10938, "Persoon.Nummerverwijzing.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.ActieVerval. */
    PERSOON_NUMMERVERWIJZING_ACTIEVERVAL((short) 10939, "Persoon.Nummerverwijzing.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.ActieVervalTbvLeveringMutaties. */
    PERSOON_NUMMERVERWIJZING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18854, "Persoon.Nummerverwijzing.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.DatumAanvangGeldigheid. */
    PERSOON_NUMMERVERWIJZING_DATUMAANVANGGELDIGHEID((short) 10934, "Persoon.Nummerverwijzing.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.DatumEindeGeldigheid. */
    PERSOON_NUMMERVERWIJZING_DATUMEINDEGELDIGHEID((short) 10935, "Persoon.Nummerverwijzing.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_NUMMERVERWIJZING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18855, "Persoon.Nummerverwijzing.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.NadereAanduidingVerval. */
    PERSOON_NUMMERVERWIJZING_NADEREAANDUIDINGVERVAL((short) 11132, "Persoon.Nummerverwijzing.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.Persoon. */
    PERSOON_NUMMERVERWIJZING_PERSOON((short) 10933, "Persoon.Nummerverwijzing.Persoon", SoortElement.ATTRIBUUT, PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.TijdstipRegistratie. */
    PERSOON_NUMMERVERWIJZING_TIJDSTIPREGISTRATIE((short) 10936, "Persoon.Nummerverwijzing.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.TijdstipVerval. */
    PERSOON_NUMMERVERWIJZING_TIJDSTIPVERVAL((short) 10937, "Persoon.Nummerverwijzing.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.VolgendeAdministratienummer. */
    PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER((short) 3248, "Persoon.Nummerverwijzing.VolgendeAdministratienummer", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.VolgendeBurgerservicenummer. */
    PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER((short) 3136, "Persoon.Nummerverwijzing.VolgendeBurgerservicenummer", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.VoorkomenSleutel. */
    PERSOON_NUMMERVERWIJZING_VOORKOMENSLEUTEL((short) 10976, "Persoon.Nummerverwijzing.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.VorigeAdministratienummer. */
    PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER((short) 3247, "Persoon.Nummerverwijzing.VorigeAdministratienummer", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.Nummerverwijzing.VorigeBurgerservicenummer. */
    PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER((short) 3134, "Persoon.Nummerverwijzing.VorigeBurgerservicenummer", SoortElement.ATTRIBUUT,
            PERSOON_NUMMERVERWIJZING),
    /** Persoon.ObjectSleutel. */
    PERSOON_OBJECTSLEUTEL((short) 3015, "Persoon.ObjectSleutel", SoortElement.ATTRIBUUT, PERSOON_IDENTITEIT),
    /** Persoon.Onderzoek.ActieInhoud. */
    PERSOON_ONDERZOEK_ACTIEINHOUD((short) 10807, "Persoon.Onderzoek.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.ActieVerval. */
    PERSOON_ONDERZOEK_ACTIEVERVAL((short) 10808, "Persoon.Onderzoek.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.ActieVervalTbvLeveringMutaties. */
    PERSOON_ONDERZOEK_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18880, "Persoon.Onderzoek.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_ONDERZOEK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18881, "Persoon.Onderzoek.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.NadereAanduidingVerval. */
    PERSOON_ONDERZOEK_NADEREAANDUIDINGVERVAL((short) 11146, "Persoon.Onderzoek.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.ObjectSleutel. */
    PERSOON_ONDERZOEK_OBJECTSLEUTEL((short) 6130, "Persoon.Onderzoek.ObjectSleutel", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_IDENTITEIT),
    /** Persoon.Onderzoek.Onderzoek. */
    PERSOON_ONDERZOEK_ONDERZOEK((short) 6132, "Persoon.Onderzoek.Onderzoek", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_IDENTITEIT),
    /** Persoon.Onderzoek.Persoon. */
    PERSOON_ONDERZOEK_PERSOON((short) 6131, "Persoon.Onderzoek.Persoon", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_IDENTITEIT),
    /** Persoon.Onderzoek.PersoonOnderzoek. */
    PERSOON_ONDERZOEK_PERSOONONDERZOEK((short) 10804, "Persoon.Onderzoek.PersoonOnderzoek", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.RolNaam. */
    PERSOON_ONDERZOEK_ROLNAAM((short) 10771, "Persoon.Onderzoek.RolNaam", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.TijdstipRegistratie. */
    PERSOON_ONDERZOEK_TIJDSTIPREGISTRATIE((short) 10805, "Persoon.Onderzoek.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.TijdstipVerval. */
    PERSOON_ONDERZOEK_TIJDSTIPVERVAL((short) 10806, "Persoon.Onderzoek.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Onderzoek.VoorkomenSleutel. */
    PERSOON_ONDERZOEK_VOORKOMENSLEUTEL((short) 10833, "Persoon.Onderzoek.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_ONDERZOEK_STANDAARD),
    /** Persoon.Overlijden.ActieInhoud. */
    PERSOON_OVERLIJDEN_ACTIEINHOUD((short) 4148, "Persoon.Overlijden.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.ActieVerval. */
    PERSOON_OVERLIJDEN_ACTIEVERVAL((short) 4149, "Persoon.Overlijden.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.ActieVervalTbvLeveringMutaties. */
    PERSOON_OVERLIJDEN_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18858, "Persoon.Overlijden.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.BuitenlandsePlaats. */
    PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS((short) 3552, "Persoon.Overlijden.BuitenlandsePlaats", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.BuitenlandseRegio. */
    PERSOON_OVERLIJDEN_BUITENLANDSEREGIO((short) 3556, "Persoon.Overlijden.BuitenlandseRegio", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.Datum. */
    PERSOON_OVERLIJDEN_DATUM((short) 3546, "Persoon.Overlijden.Datum", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.GemeenteCode. */
    PERSOON_OVERLIJDEN_GEMEENTECODE((short) 3551, "Persoon.Overlijden.GemeenteCode", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_OVERLIJDEN_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18859, "Persoon.Overlijden.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.LandGebiedCode. */
    PERSOON_OVERLIJDEN_LANDGEBIEDCODE((short) 3558, "Persoon.Overlijden.LandGebiedCode", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.NadereAanduidingVerval. */
    PERSOON_OVERLIJDEN_NADEREAANDUIDINGVERVAL((short) 11134, "Persoon.Overlijden.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.OmschrijvingLocatie. */
    PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE((short) 3555, "Persoon.Overlijden.OmschrijvingLocatie", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.Persoon. */
    PERSOON_OVERLIJDEN_PERSOON((short) 4145, "Persoon.Overlijden.Persoon", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.TijdstipRegistratie. */
    PERSOON_OVERLIJDEN_TIJDSTIPREGISTRATIE((short) 4146, "Persoon.Overlijden.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.TijdstipVerval. */
    PERSOON_OVERLIJDEN_TIJDSTIPVERVAL((short) 4147, "Persoon.Overlijden.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.VoorkomenSleutel. */
    PERSOON_OVERLIJDEN_VOORKOMENSLEUTEL((short) 4539, "Persoon.Overlijden.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Overlijden.Woonplaatsnaam. */
    PERSOON_OVERLIJDEN_WOONPLAATSNAAM((short) 3544, "Persoon.Overlijden.Woonplaatsnaam", SoortElement.ATTRIBUUT, PERSOON_OVERLIJDEN),
    /** Persoon.Persoonskaart.ActieInhoud. */
    PERSOON_PERSOONSKAART_ACTIEINHOUD((short) 4220, "Persoon.Persoonskaart.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.ActieVerval. */
    PERSOON_PERSOONSKAART_ACTIEVERVAL((short) 4221, "Persoon.Persoonskaart.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.ActieVervalTbvLeveringMutaties. */
    PERSOON_PERSOONSKAART_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18870, "Persoon.Persoonskaart.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.IndicatieVolledigGeconverteerd. */
    PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD((short) 3313, "Persoon.Persoonskaart.IndicatieVolledigGeconverteerd", SoortElement.ATTRIBUUT,
            PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_PERSOONSKAART_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18871, "Persoon.Persoonskaart.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.NadereAanduidingVerval. */
    PERSOON_PERSOONSKAART_NADEREAANDUIDINGVERVAL((short) 11140, "Persoon.Persoonskaart.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.PartijCode. */
    PERSOON_PERSOONSKAART_PARTIJCODE((short) 3233, "Persoon.Persoonskaart.PartijCode", SoortElement.ATTRIBUUT, PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.Persoon. */
    PERSOON_PERSOONSKAART_PERSOON((short) 4217, "Persoon.Persoonskaart.Persoon", SoortElement.ATTRIBUUT, PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.TijdstipRegistratie. */
    PERSOON_PERSOONSKAART_TIJDSTIPREGISTRATIE((short) 4218, "Persoon.Persoonskaart.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.TijdstipVerval. */
    PERSOON_PERSOONSKAART_TIJDSTIPVERVAL((short) 4219, "Persoon.Persoonskaart.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_PERSOONSKAART),
    /** Persoon.Persoonskaart.VoorkomenSleutel. */
    PERSOON_PERSOONSKAART_VOORKOMENSLEUTEL((short) 4560, "Persoon.Persoonskaart.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_PERSOONSKAART),
    /** Persoon.Reisdocument.AanduidingInhoudingVermissingCode. */
    PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE((short) 3747, "Persoon.Reisdocument.AanduidingInhoudingVermissingCode", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.ActieInhoud. */
    PERSOON_REISDOCUMENT_ACTIEINHOUD((short) 4341, "Persoon.Reisdocument.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.ActieVerval. */
    PERSOON_REISDOCUMENT_ACTIEVERVAL((short) 4342, "Persoon.Reisdocument.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.ActieVervalTbvLeveringMutaties. */
    PERSOON_REISDOCUMENT_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18882, "Persoon.Reisdocument.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.AutoriteitVanAfgifte. */
    PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE((short) 3744, "Persoon.Reisdocument.AutoriteitVanAfgifte", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.DatumEindeDocument. */
    PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT((short) 3745, "Persoon.Reisdocument.DatumEindeDocument", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.DatumIngangDocument. */
    PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT((short) 6126, "Persoon.Reisdocument.DatumIngangDocument", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.DatumInhoudingVermissing. */
    PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING((short) 3746, "Persoon.Reisdocument.DatumInhoudingVermissing", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.DatumUitgifte. */
    PERSOON_REISDOCUMENT_DATUMUITGIFTE((short) 3742, "Persoon.Reisdocument.DatumUitgifte", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_REISDOCUMENT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18883, "Persoon.Reisdocument.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.NadereAanduidingVerval. */
    PERSOON_REISDOCUMENT_NADEREAANDUIDINGVERVAL((short) 11147, "Persoon.Reisdocument.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.Nummer. */
    PERSOON_REISDOCUMENT_NUMMER((short) 3741, "Persoon.Reisdocument.Nummer", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.ObjectSleutel. */
    PERSOON_REISDOCUMENT_OBJECTSLEUTEL((short) 3751, "Persoon.Reisdocument.ObjectSleutel", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_IDENTITEIT),
    /** Persoon.Reisdocument.Persoon. */
    PERSOON_REISDOCUMENT_PERSOON((short) 3752, "Persoon.Reisdocument.Persoon", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_IDENTITEIT),
    /** Persoon.Reisdocument.PersoonReisdocument. */
    PERSOON_REISDOCUMENT_PERSOONREISDOCUMENT((short) 4336, "Persoon.Reisdocument.PersoonReisdocument", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.SoortCode. */
    PERSOON_REISDOCUMENT_SOORTCODE((short) 3739, "Persoon.Reisdocument.SoortCode", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_IDENTITEIT),
    /** Persoon.Reisdocument.TijdstipRegistratie. */
    PERSOON_REISDOCUMENT_TIJDSTIPREGISTRATIE((short) 4339, "Persoon.Reisdocument.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.TijdstipVerval. */
    PERSOON_REISDOCUMENT_TIJDSTIPVERVAL((short) 4340, "Persoon.Reisdocument.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.Reisdocument.VoorkomenSleutel. */
    PERSOON_REISDOCUMENT_VOORKOMENSLEUTEL((short) 4587, "Persoon.Reisdocument.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_REISDOCUMENT_STANDAARD),
    /** Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid. */
    PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID((short) 4105, "Persoon.SamengesteldeNaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.ActieInhoud. */
    PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD((short) 4103, "Persoon.SamengesteldeNaam.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.ActieVerval. */
    PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL((short) 4104, "Persoon.SamengesteldeNaam.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties. */
    PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18846, "Persoon.SamengesteldeNaam.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.AdellijkeTitelCode. */
    PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE((short) 1968, "Persoon.SamengesteldeNaam.AdellijkeTitelCode", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.DatumAanvangGeldigheid. */
    PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID((short) 4099, "Persoon.SamengesteldeNaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.DatumEindeGeldigheid. */
    PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID((short) 4100, "Persoon.SamengesteldeNaam.DatumEindeGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.Geslachtsnaamstam. */
    PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM((short) 3094, "Persoon.SamengesteldeNaam.Geslachtsnaamstam", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.IndicatieAfgeleid. */
    PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID((short) 3914, "Persoon.SamengesteldeNaam.IndicatieAfgeleid", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.IndicatieNamenreeks. */
    PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS((short) 3592, "Persoon.SamengesteldeNaam.IndicatieNamenreeks", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18847, "Persoon.SamengesteldeNaam.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.NadereAanduidingVerval. */
    PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL((short) 11128, "Persoon.SamengesteldeNaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.Persoon. */
    PERSOON_SAMENGESTELDENAAM_PERSOON((short) 4098, "Persoon.SamengesteldeNaam.Persoon", SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.PredicaatCode. */
    PERSOON_SAMENGESTELDENAAM_PREDICAATCODE((short) 1969, "Persoon.SamengesteldeNaam.PredicaatCode", SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.Scheidingsteken. */
    PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN((short) 3253, "Persoon.SamengesteldeNaam.Scheidingsteken", SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.TijdstipRegistratie. */
    PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE((short) 4101, "Persoon.SamengesteldeNaam.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.TijdstipVerval. */
    PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL((short) 4102, "Persoon.SamengesteldeNaam.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.VoorkomenSleutel. */
    PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL((short) 4530, "Persoon.SamengesteldeNaam.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.Voornamen. */
    PERSOON_SAMENGESTELDENAAM_VOORNAMEN((short) 3092, "Persoon.SamengesteldeNaam.Voornamen", SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SamengesteldeNaam.Voorvoegsel. */
    PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL((short) 3309, "Persoon.SamengesteldeNaam.Voorvoegsel", SoortElement.ATTRIBUUT, PERSOON_SAMENGESTELDENAAM),
    /** Persoon.SoortCode. */
    PERSOON_SOORTCODE((short) 1997, "Persoon.SoortCode", SoortElement.ATTRIBUUT, PERSOON_IDENTITEIT),
    /** Persoon.UitsluitingKiesrecht.ActieInhoud. */
    PERSOON_UITSLUITINGKIESRECHT_ACTIEINHOUD((short) 4174, "Persoon.UitsluitingKiesrecht.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.ActieVerval. */
    PERSOON_UITSLUITINGKIESRECHT_ACTIEVERVAL((short) 4175, "Persoon.UitsluitingKiesrecht.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.ActieVervalTbvLeveringMutaties. */
    PERSOON_UITSLUITINGKIESRECHT_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18866, "Persoon.UitsluitingKiesrecht.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.DatumVoorzienEinde. */
    PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE((short) 3559, "Persoon.UitsluitingKiesrecht.DatumVoorzienEinde", SoortElement.ATTRIBUUT,
            PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.Indicatie. */
    PERSOON_UITSLUITINGKIESRECHT_INDICATIE((short) 3322, "Persoon.UitsluitingKiesrecht.Indicatie", SoortElement.ATTRIBUUT, PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_UITSLUITINGKIESRECHT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18867,
            "Persoon.UitsluitingKiesrecht.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.NadereAanduidingVerval. */
    PERSOON_UITSLUITINGKIESRECHT_NADEREAANDUIDINGVERVAL((short) 11138, "Persoon.UitsluitingKiesrecht.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.Persoon. */
    PERSOON_UITSLUITINGKIESRECHT_PERSOON((short) 4171, "Persoon.UitsluitingKiesrecht.Persoon", SoortElement.ATTRIBUUT, PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.TijdstipRegistratie. */
    PERSOON_UITSLUITINGKIESRECHT_TIJDSTIPREGISTRATIE((short) 4172, "Persoon.UitsluitingKiesrecht.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.TijdstipVerval. */
    PERSOON_UITSLUITINGKIESRECHT_TIJDSTIPVERVAL((short) 4173, "Persoon.UitsluitingKiesrecht.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.UitsluitingKiesrecht.VoorkomenSleutel. */
    PERSOON_UITSLUITINGKIESRECHT_VOORKOMENSLEUTEL((short) 4545, "Persoon.UitsluitingKiesrecht.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_UITSLUITINGKIESRECHT),
    /** Persoon.Verblijfsrecht.AanduidingCode. */
    PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE((short) 3310, "Persoon.Verblijfsrecht.AanduidingCode", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.ActieInhoud. */
    PERSOON_VERBLIJFSRECHT_ACTIEINHOUD((short) 4163, "Persoon.Verblijfsrecht.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.ActieVerval. */
    PERSOON_VERBLIJFSRECHT_ACTIEVERVAL((short) 4164, "Persoon.Verblijfsrecht.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.ActieVervalTbvLeveringMutaties. */
    PERSOON_VERBLIJFSRECHT_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18864, "Persoon.Verblijfsrecht.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.DatumAanvang. */
    PERSOON_VERBLIJFSRECHT_DATUMAANVANG((short) 21315, "Persoon.Verblijfsrecht.DatumAanvang", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.DatumMededeling. */
    PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING((short) 3325, "Persoon.Verblijfsrecht.DatumMededeling", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.DatumVoorzienEinde. */
    PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE((short) 3481, "Persoon.Verblijfsrecht.DatumVoorzienEinde", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_VERBLIJFSRECHT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18865, "Persoon.Verblijfsrecht.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.NadereAanduidingVerval. */
    PERSOON_VERBLIJFSRECHT_NADEREAANDUIDINGVERVAL((short) 11137, "Persoon.Verblijfsrecht.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.Persoon. */
    PERSOON_VERBLIJFSRECHT_PERSOON((short) 4158, "Persoon.Verblijfsrecht.Persoon", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.TijdstipRegistratie. */
    PERSOON_VERBLIJFSRECHT_TIJDSTIPREGISTRATIE((short) 4161, "Persoon.Verblijfsrecht.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.TijdstipVerval. */
    PERSOON_VERBLIJFSRECHT_TIJDSTIPVERVAL((short) 4162, "Persoon.Verblijfsrecht.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verblijfsrecht.VoorkomenSleutel. */
    PERSOON_VERBLIJFSRECHT_VOORKOMENSLEUTEL((short) 4542, "Persoon.Verblijfsrecht.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_VERBLIJFSRECHT),
    /** Persoon.Verificatie.ActieInhoud. */
    PERSOON_VERIFICATIE_ACTIEINHOUD((short) 4355, "Persoon.Verificatie.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.ActieVerval. */
    PERSOON_VERIFICATIE_ACTIEVERVAL((short) 4356, "Persoon.Verificatie.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.ActieVervalTbvLeveringMutaties. */
    PERSOON_VERIFICATIE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18884, "Persoon.Verificatie.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.Datum. */
    PERSOON_VERIFICATIE_DATUM((short) 3778, "Persoon.Verificatie.Datum", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.Geverifieerde. */
    PERSOON_VERIFICATIE_GEVERIFIEERDE((short) 2142, "Persoon.Verificatie.Geverifieerde", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_IDENTITEIT),
    /** Persoon.Verificatie.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_VERIFICATIE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18885, "Persoon.Verificatie.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.NadereAanduidingVerval. */
    PERSOON_VERIFICATIE_NADEREAANDUIDINGVERVAL((short) 11148, "Persoon.Verificatie.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.ObjectSleutel. */
    PERSOON_VERIFICATIE_OBJECTSLEUTEL((short) 3777, "Persoon.Verificatie.ObjectSleutel", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_IDENTITEIT),
    /** Persoon.Verificatie.PartijCode. */
    PERSOON_VERIFICATIE_PARTIJCODE((short) 10915, "Persoon.Verificatie.PartijCode", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_IDENTITEIT),
    /** Persoon.Verificatie.PersoonVerificatie. */
    PERSOON_VERIFICATIE_PERSOONVERIFICATIE((short) 4352, "Persoon.Verificatie.PersoonVerificatie", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.Soort. */
    PERSOON_VERIFICATIE_SOORT((short) 3779, "Persoon.Verificatie.Soort", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_IDENTITEIT),
    /** Persoon.Verificatie.TijdstipRegistratie. */
    PERSOON_VERIFICATIE_TIJDSTIPREGISTRATIE((short) 4353, "Persoon.Verificatie.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.TijdstipVerval. */
    PERSOON_VERIFICATIE_TIJDSTIPVERVAL((short) 4354, "Persoon.Verificatie.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verificatie.VoorkomenSleutel. */
    PERSOON_VERIFICATIE_VOORKOMENSLEUTEL((short) 4590, "Persoon.Verificatie.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_VERIFICATIE_STANDAARD),
    /** Persoon.Verstrekkingsbeperking.ActieInhoud. */
    PERSOON_VERSTREKKINGSBEPERKING_ACTIEINHOUD((short) 9367, "Persoon.Verstrekkingsbeperking.ActieInhoud", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.ActieVerval. */
    PERSOON_VERSTREKKINGSBEPERKING_ACTIEVERVAL((short) 9368, "Persoon.Verstrekkingsbeperking.ActieVerval", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.ActieVervalTbvLeveringMutaties. */
    PERSOON_VERSTREKKINGSBEPERKING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18886, "Persoon.Verstrekkingsbeperking.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.GemeenteVerordeningPartijCode. */
    PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE((short) 10913, "Persoon.Verstrekkingsbeperking.GemeenteVerordeningPartijCode",
            SoortElement.ATTRIBUUT, PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_VERSTREKKINGSBEPERKING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18887,
            "Persoon.Verstrekkingsbeperking.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT, PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.NadereAanduidingVerval. */
    PERSOON_VERSTREKKINGSBEPERKING_NADEREAANDUIDINGVERVAL((short) 11149, "Persoon.Verstrekkingsbeperking.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.ObjectSleutel. */
    PERSOON_VERSTREKKINGSBEPERKING_OBJECTSLEUTEL((short) 9349, "Persoon.Verstrekkingsbeperking.ObjectSleutel", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.OmschrijvingDerde. */
    PERSOON_VERSTREKKINGSBEPERKING_OMSCHRIJVINGDERDE((short) 10912, "Persoon.Verstrekkingsbeperking.OmschrijvingDerde", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.PartijCode. */
    PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE((short) 9352, "Persoon.Verstrekkingsbeperking.PartijCode", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.Persoon. */
    PERSOON_VERSTREKKINGSBEPERKING_PERSOON((short) 9351, "Persoon.Verstrekkingsbeperking.Persoon", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.PersoonVerstrekkingsbeperking. */
    PERSOON_VERSTREKKINGSBEPERKING_PERSOONVERSTREKKINGSBEPERKING((short) 10954, "Persoon.Verstrekkingsbeperking.PersoonVerstrekkingsbeperking",
            SoortElement.ATTRIBUUT, PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.TijdstipRegistratie. */
    PERSOON_VERSTREKKINGSBEPERKING_TIJDSTIPREGISTRATIE((short) 9365, "Persoon.Verstrekkingsbeperking.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.TijdstipVerval. */
    PERSOON_VERSTREKKINGSBEPERKING_TIJDSTIPVERVAL((short) 9366, "Persoon.Verstrekkingsbeperking.TijdstipVerval", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Verstrekkingsbeperking.VoorkomenSleutel. */
    PERSOON_VERSTREKKINGSBEPERKING_VOORKOMENSLEUTEL((short) 9376, "Persoon.Verstrekkingsbeperking.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT),
    /** Persoon.Voornaam.ActieAanpassingGeldigheid. */
    PERSOON_VOORNAAM_ACTIEAANPASSINGGELDIGHEID((short) 4366, "Persoon.Voornaam.ActieAanpassingGeldigheid", SoortElement.ATTRIBUUT,
            PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.ActieInhoud. */
    PERSOON_VOORNAAM_ACTIEINHOUD((short) 4364, "Persoon.Voornaam.ActieInhoud", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.ActieVerval. */
    PERSOON_VOORNAAM_ACTIEVERVAL((short) 4365, "Persoon.Voornaam.ActieVerval", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.ActieVervalTbvLeveringMutaties. */
    PERSOON_VOORNAAM_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18888, "Persoon.Voornaam.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.DatumAanvangGeldigheid. */
    PERSOON_VOORNAAM_DATUMAANVANGGELDIGHEID((short) 4360, "Persoon.Voornaam.DatumAanvangGeldigheid", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.DatumEindeGeldigheid. */
    PERSOON_VOORNAAM_DATUMEINDEGELDIGHEID((short) 4361, "Persoon.Voornaam.DatumEindeGeldigheid", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.IndicatieVoorkomenTbvLeveringMutaties. */
    PERSOON_VOORNAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18889, "Persoon.Voornaam.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.Naam. */
    PERSOON_VOORNAAM_NAAM((short) 3026, "Persoon.Voornaam.Naam", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.NadereAanduidingVerval. */
    PERSOON_VOORNAAM_NADEREAANDUIDINGVERVAL((short) 11150, "Persoon.Voornaam.NadereAanduidingVerval", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.ObjectSleutel. */
    PERSOON_VOORNAAM_OBJECTSLEUTEL((short) 3644, "Persoon.Voornaam.ObjectSleutel", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_IDENTITEIT),
    /** Persoon.Voornaam.Persoon. */
    PERSOON_VOORNAAM_PERSOON((short) 3023, "Persoon.Voornaam.Persoon", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_IDENTITEIT),
    /** Persoon.Voornaam.PersoonVoornaam. */
    PERSOON_VOORNAAM_PERSOONVOORNAAM((short) 4359, "Persoon.Voornaam.PersoonVoornaam", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.TijdstipRegistratie. */
    PERSOON_VOORNAAM_TIJDSTIPREGISTRATIE((short) 4362, "Persoon.Voornaam.TijdstipRegistratie", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.TijdstipVerval. */
    PERSOON_VOORNAAM_TIJDSTIPVERVAL((short) 4363, "Persoon.Voornaam.TijdstipVerval", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Persoon.Voornaam.Volgnummer. */
    PERSOON_VOORNAAM_VOLGNUMMER((short) 3028, "Persoon.Voornaam.Volgnummer", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_IDENTITEIT),
    /** Persoon.Voornaam.VoorkomenSleutel. */
    PERSOON_VOORNAAM_VOORKOMENSLEUTEL((short) 4593, "Persoon.Voornaam.VoorkomenSleutel", SoortElement.ATTRIBUUT, PERSOON_VOORNAAM_STANDAARD),
    /** Relatie.ActieInhoud. */
    RELATIE_ACTIEINHOUD((short) 4372, "Relatie.ActieInhoud", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.ActieVerval. */
    RELATIE_ACTIEVERVAL((short) 4373, "Relatie.ActieVerval", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.ActieVervalTbvLeveringMutaties. */
    RELATIE_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18892, "Relatie.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.BuitenlandsePlaatsAanvang. */
    RELATIE_BUITENLANDSEPLAATSAANVANG((short) 3757, "Relatie.BuitenlandsePlaatsAanvang", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.BuitenlandsePlaatsEinde. */
    RELATIE_BUITENLANDSEPLAATSEINDE((short) 3765, "Relatie.BuitenlandsePlaatsEinde", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.BuitenlandseRegioAanvang. */
    RELATIE_BUITENLANDSEREGIOAANVANG((short) 3759, "Relatie.BuitenlandseRegioAanvang", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.BuitenlandseRegioEinde. */
    RELATIE_BUITENLANDSEREGIOEINDE((short) 3767, "Relatie.BuitenlandseRegioEinde", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.DatumAanvang. */
    RELATIE_DATUMAANVANG((short) 3754, "Relatie.DatumAanvang", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.DatumEinde. */
    RELATIE_DATUMEINDE((short) 3762, "Relatie.DatumEinde", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.GemeenteAanvangCode. */
    RELATIE_GEMEENTEAANVANGCODE((short) 3755, "Relatie.GemeenteAanvangCode", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.GemeenteEindeCode. */
    RELATIE_GEMEENTEEINDECODE((short) 3763, "Relatie.GemeenteEindeCode", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.IndicatieVoorkomenTbvLeveringMutaties. */
    RELATIE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18893, "Relatie.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            RELATIE_STANDAARD),
    /** Relatie.LandGebiedAanvangCode. */
    RELATIE_LANDGEBIEDAANVANGCODE((short) 3760, "Relatie.LandGebiedAanvangCode", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.LandGebiedEindeCode. */
    RELATIE_LANDGEBIEDEINDECODE((short) 3768, "Relatie.LandGebiedEindeCode", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.NadereAanduidingVerval. */
    RELATIE_NADEREAANDUIDINGVERVAL((short) 11153, "Relatie.NadereAanduidingVerval", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.ObjectSleutel. */
    RELATIE_OBJECTSLEUTEL((short) 3186, "Relatie.ObjectSleutel", SoortElement.ATTRIBUUT, RELATIE_IDENTITEIT),
    /** Relatie.OmschrijvingLocatieAanvang. */
    RELATIE_OMSCHRIJVINGLOCATIEAANVANG((short) 3758, "Relatie.OmschrijvingLocatieAanvang", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.OmschrijvingLocatieEinde. */
    RELATIE_OMSCHRIJVINGLOCATIEEINDE((short) 3766, "Relatie.OmschrijvingLocatieEinde", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.RedenEindeCode. */
    RELATIE_REDENEINDECODE((short) 3207, "Relatie.RedenEindeCode", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.Relatie. */
    RELATIE_RELATIE((short) 4369, "Relatie.Relatie", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.SoortCode. */
    RELATIE_SOORTCODE((short) 3198, "Relatie.SoortCode", SoortElement.ATTRIBUUT, RELATIE_IDENTITEIT),
    /** Relatie.TijdstipRegistratie. */
    RELATIE_TIJDSTIPREGISTRATIE((short) 4370, "Relatie.TijdstipRegistratie", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.TijdstipVerval. */
    RELATIE_TIJDSTIPVERVAL((short) 4371, "Relatie.TijdstipVerval", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.VoorkomenSleutel. */
    RELATIE_VOORKOMENSLEUTEL((short) 4596, "Relatie.VoorkomenSleutel", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.WoonplaatsnaamAanvang. */
    RELATIE_WOONPLAATSNAAMAANVANG((short) 3756, "Relatie.WoonplaatsnaamAanvang", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Relatie.WoonplaatsnaamEinde. */
    RELATIE_WOONPLAATSNAAMEINDE((short) 3764, "Relatie.WoonplaatsnaamEinde", SoortElement.ATTRIBUUT, RELATIE_STANDAARD),
    /** Terugmelding.ActieInhoud. */
    TERUGMELDING_ACTIEINHOUD((short) 10814, "Terugmelding.ActieInhoud", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.ActieVerval. */
    TERUGMELDING_ACTIEVERVAL((short) 10815, "Terugmelding.ActieVerval", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.ActieVervalTbvLeveringMutaties. */
    TERUGMELDING_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18894, "Terugmelding.ActieVervalTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            TERUGMELDING_STANDAARD),
    /** Terugmelding.BijhoudingsgemeentePartijCode. */
    TERUGMELDING_BIJHOUDINGSGEMEENTEPARTIJCODE((short) 10738, "Terugmelding.BijhoudingsgemeentePartijCode", SoortElement.ATTRIBUUT,
            TERUGMELDING_IDENTITEIT),
    /** Terugmelding.Contactpersoon.ActieInhoud. */
    TERUGMELDING_CONTACTPERSOON_ACTIEINHOUD((short) 11161, "Terugmelding.Contactpersoon.ActieInhoud", SoortElement.ATTRIBUUT, TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.ActieVerval. */
    TERUGMELDING_CONTACTPERSOON_ACTIEVERVAL((short) 11162, "Terugmelding.Contactpersoon.ActieVerval", SoortElement.ATTRIBUUT, TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.ActieVervalTbvLeveringMutaties. */
    TERUGMELDING_CONTACTPERSOON_ACTIEVERVALTBVLEVERINGMUTATIES((short) 18896, "Terugmelding.Contactpersoon.ActieVervalTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.Email. */
    TERUGMELDING_CONTACTPERSOON_EMAIL((short) 11096, "Terugmelding.Contactpersoon.Email", SoortElement.ATTRIBUUT, TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.Geslachtsnaamstam. */
    TERUGMELDING_CONTACTPERSOON_GESLACHTSNAAMSTAM((short) 11104, "Terugmelding.Contactpersoon.Geslachtsnaamstam", SoortElement.ATTRIBUUT,
            TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.IndicatieVoorkomenTbvLeveringMutaties. */
    TERUGMELDING_CONTACTPERSOON_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18897, "Terugmelding.Contactpersoon.IndicatieVoorkomenTbvLeveringMutaties",
            SoortElement.ATTRIBUUT, TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.NadereAanduidingVerval. */
    TERUGMELDING_CONTACTPERSOON_NADEREAANDUIDINGVERVAL((short) 11163, "Terugmelding.Contactpersoon.NadereAanduidingVerval", SoortElement.ATTRIBUUT,
            TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.Scheidingsteken. */
    TERUGMELDING_CONTACTPERSOON_SCHEIDINGSTEKEN((short) 11103, "Terugmelding.Contactpersoon.Scheidingsteken", SoortElement.ATTRIBUUT,
            TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.Telefoonnummer. */
    TERUGMELDING_CONTACTPERSOON_TELEFOONNUMMER((short) 11097, "Terugmelding.Contactpersoon.Telefoonnummer", SoortElement.ATTRIBUUT,
            TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.Terugmelding. */
    TERUGMELDING_CONTACTPERSOON_TERUGMELDING((short) 11158, "Terugmelding.Contactpersoon.Terugmelding", SoortElement.ATTRIBUUT,
            TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.TijdstipRegistratie. */
    TERUGMELDING_CONTACTPERSOON_TIJDSTIPREGISTRATIE((short) 11159, "Terugmelding.Contactpersoon.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.TijdstipVerval. */
    TERUGMELDING_CONTACTPERSOON_TIJDSTIPVERVAL((short) 11160, "Terugmelding.Contactpersoon.TijdstipVerval", SoortElement.ATTRIBUUT,
            TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.VoorkomenSleutel. */
    TERUGMELDING_CONTACTPERSOON_VOORKOMENSLEUTEL((short) 11174, "Terugmelding.Contactpersoon.VoorkomenSleutel", SoortElement.ATTRIBUUT,
            TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.Voornamen. */
    TERUGMELDING_CONTACTPERSOON_VOORNAMEN((short) 11101, "Terugmelding.Contactpersoon.Voornamen", SoortElement.ATTRIBUUT, TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Contactpersoon.Voorvoegsel. */
    TERUGMELDING_CONTACTPERSOON_VOORVOEGSEL((short) 11102, "Terugmelding.Contactpersoon.Voorvoegsel", SoortElement.ATTRIBUUT, TERUGMELDING_CONTACTPERSOON),
    /** Terugmelding.Identiteit.TijdstipRegistratie. */
    TERUGMELDING_IDENTITEIT_TIJDSTIPREGISTRATIE((short) 10839, "Terugmelding.Identiteit.TijdstipRegistratie", SoortElement.ATTRIBUUT,
            TERUGMELDING_IDENTITEIT),
    /** Terugmelding.IndicatieVoorkomenTbvLeveringMutaties. */
    TERUGMELDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES((short) 18895, "Terugmelding.IndicatieVoorkomenTbvLeveringMutaties", SoortElement.ATTRIBUUT,
            TERUGMELDING_STANDAARD),
    /** Terugmelding.KenmerkMeldendePartij. */
    TERUGMELDING_KENMERKMELDENDEPARTIJ((short) 10741, "Terugmelding.KenmerkMeldendePartij", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.NadereAanduidingVerval. */
    TERUGMELDING_NADEREAANDUIDINGVERVAL((short) 11156, "Terugmelding.NadereAanduidingVerval", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.ObjectSleutel. */
    TERUGMELDING_OBJECTSLEUTEL((short) 10719, "Terugmelding.ObjectSleutel", SoortElement.ATTRIBUUT, TERUGMELDING_IDENTITEIT),
    /** Terugmelding.Onderzoek. */
    TERUGMELDING_ONDERZOEK((short) 10740, "Terugmelding.Onderzoek", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.Persoon. */
    TERUGMELDING_PERSOON((short) 10737, "Terugmelding.Persoon", SoortElement.ATTRIBUUT, TERUGMELDING_IDENTITEIT),
    /** Terugmelding.Standaard.TijdstipRegistratie. */
    TERUGMELDING_STANDAARD_TIJDSTIPREGISTRATIE((short) 10812, "Terugmelding.Standaard.TijdstipRegistratie", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.StatusNaam. */
    TERUGMELDING_STATUSNAAM((short) 10752, "Terugmelding.StatusNaam", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.TerugmeldendePartijCode. */
    TERUGMELDING_TERUGMELDENDEPARTIJCODE((short) 10736, "Terugmelding.TerugmeldendePartijCode", SoortElement.ATTRIBUUT, TERUGMELDING_IDENTITEIT),
    /** Terugmelding.Terugmelding. */
    TERUGMELDING_TERUGMELDING((short) 10811, "Terugmelding.Terugmelding", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.TijdstipVerval. */
    TERUGMELDING_TIJDSTIPVERVAL((short) 10813, "Terugmelding.TijdstipVerval", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.Toelichting. */
    TERUGMELDING_TOELICHTING((short) 11092, "Terugmelding.Toelichting", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD),
    /** Terugmelding.VoorkomenSleutel. */
    TERUGMELDING_VOORKOMENSLEUTEL((short) 10836, "Terugmelding.VoorkomenSleutel", SoortElement.ATTRIBUUT, TERUGMELDING_STANDAARD);

    private static final EnumParser<Element> PARSER = new EnumParser<>(Element.class);

    private final short id;

    private final String naam;

    private final SoortElement soort;

    private final Element groep;

    /**
     * Maak een nieuwe element.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param soort
     *            soort
     * @param groep
     *            groep
     */
    Element(final short id, final String naam, final SoortElement soort, final Element groep) {
        this.id = id;
        this.naam = naam;
        this.soort = soort;
        this.groep = groep;
    }

    /**
     * Geeft een enumeratiewaarde van type Element terug o.b.v. het database-ID.
     *
     * @param id
     *            het database-id van de enumeratie. Mag null zijn, in dat geval wordt ook null geretourneerd.
     * @return Een enumeratiewaarde van type Element, of null.
     * @throws IllegalArgumentException
     *             als de enumeratiewaarde met bijbehorend id niet gevonden kon worden.
     */
    public static Element parseId(final Short id) {
        return PARSER.parseId(id);
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van soort.
     *
     * @return soort
     */
    public SoortElement getSoort() {
        return soort;
    }

    /**
     * Geef de groep van dit element, als dit element een attribuut in een groep is. Anders null.
     *
     * @return groep
     */
    public Element getGroep() {
        return groep;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Dbobject heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef Het laatste deel van de volle naam van dit element.
     *
     * @return Het laatste deel van de volle naam van dit element
     */
    public String getLaatsteNaamDeel() {
        final String[] naamDelen = getNaam().split("\\.");
        return naamDelen[naamDelen.length - 1];
    }
}
