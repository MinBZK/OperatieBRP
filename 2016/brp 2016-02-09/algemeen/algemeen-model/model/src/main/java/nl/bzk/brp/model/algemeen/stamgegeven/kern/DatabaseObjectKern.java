/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;

import nl.bzk.brp.model.jpa.usertypes.PersistentEnum;

/**
 * Een in de Database voorkomend object, waarvan kennis noodzakelijk is voor het kunnen uitvoeren van de
 * functionaliteit.
 *
 * In een database is er geen sprake van objecttypen, groepen en attributen, maar van tabellen, kolommen, indexen,
 * constraints e.d. Deze worden hier opgesomd, voor zover het nodig is voor de functionaliteit, bijvoorbeeld doordat er
 * een onderzoek kan zijn gestart naar een gegeven dat daarin staat, of omdat er verantwoording over is vastgelegd.
 *
 * De populatie wordt beperkt tot de historie tabellen en de kolommen daarbinnen; over indexen, constraint e.d. is nog
 * geen informatiebehoefte. Deze worden derhalve in deze tabel 'niet gekend'. Ook andere tabellen (zoals de actual-tabel
 * in de A-laag) wordt niet opgenomen. De reden hiervoor is dat het vullen van de database-object tabel weliswaar
 * gebeurd door gegenereerde code; voor elk soort database-object dan wel elke extra populatie (naast historie tabellen
 * ook actual tabellen? Extra code!) inspanning kost. Nu is het criterium eenvoudig: pas als er een behoefte is, wordt
 * het erin gestopt.
 *
 *
 * Deze enum is voor alle database objecten uit het Kern schema.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.DatabaseObjectGenerator")
public enum DatabaseObjectKern implements DatabaseObject, PersistentEnum {

    /**
     * Representeert de tabel: 'AandInhingVermissingReisdoc'.
     */
    AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT(3813, "Aanduiding inhouding/vermissing reisdocument", "AandInhingVermissingReisdoc", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AandInhingVermissingReisdoc'.
     */
    AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT__I_D(3818, "Aanduiding inhouding/vermissing reisdocument - ID", "AandInhingVermissingReisdoc.ID",
            AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'AandInhingVermissingReisdoc'.
     */
    AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT__CODE(3819, "Aanduiding inhouding/vermissing reisdocument - Code", "AandInhingVermissingReisdoc.Code",
            AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'AandInhingVermissingReisdoc'.
     */
    AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT__NAAM(3820, "Aanduiding inhouding/vermissing reisdocument - Naam", "AandInhingVermissingReisdoc.Naam",
            AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT),
    /**
     * Representeert de tabel: 'AandVerblijfsr'.
     */
    AANDUIDING_VERBLIJFSRECHT(3302, "Aanduiding verblijfsrecht", "AandVerblijfsr", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AandVerblijfsr'.
     */
    AANDUIDING_VERBLIJFSRECHT__I_D(3304, "Aanduiding verblijfsrecht - ID", "AandVerblijfsr.ID", AANDUIDING_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'AandVerblijfsr'.
     */
    AANDUIDING_VERBLIJFSRECHT__CODE(9191, "Aanduiding verblijfsrecht - Code", "AandVerblijfsr.Code", AANDUIDING_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'AandVerblijfsr'.
     */
    AANDUIDING_VERBLIJFSRECHT__OMSCHRIJVING(3306, "Aanduiding verblijfsrecht - Omschrijving", "AandVerblijfsr.Oms", AANDUIDING_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'AandVerblijfsr'.
     */
    AANDUIDING_VERBLIJFSRECHT__DATUM_AANVANG_GELDIGHEID(4018, "Aanduiding verblijfsrecht - Datum aanvang geldigheid", "AandVerblijfsr.DatAanvGel",
            AANDUIDING_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'AandVerblijfsr'.
     */
    AANDUIDING_VERBLIJFSRECHT__DATUM_EINDE_GELDIGHEID(4019, "Aanduiding verblijfsrecht - Datum einde geldigheid", "AandVerblijfsr.DatEindeGel",
            AANDUIDING_VERBLIJFSRECHT),
    /**
     * Representeert de tabel: 'Aang'.
     */
    AANGEVER(3294, "Aangever", "Aang", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Aang'.
     */
    AANGEVER__I_D(3296, "Aangever - ID", "Aang.ID", AANGEVER),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Aang'.
     */
    AANGEVER__CODE(3480, "Aangever - Code", "Aang.Code", AANGEVER),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Aang'.
     */
    AANGEVER__NAAM(3298, "Aangever - Naam", "Aang.Naam", AANGEVER),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Aang'.
     */
    AANGEVER__OMSCHRIJVING(5619, "Aangever - Omschrijving", "Aang.Oms", AANGEVER),
    /**
     * Representeert de tabel: 'Actie'.
     */
    ACTIE(3071, "Actie", "Actie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Actie'.
     */
    ACTIE__I_D(3054, "Actie - ID", "Actie.ID", ACTIE),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Actie'.
     */
    ACTIE__SOORT(3055, "Actie - Soort", "Actie.Srt", ACTIE),
    /**
     * Representeert de kolom: 'AdmHnd' van de tabel: 'Actie'.
     */
    ACTIE__ADMINISTRATIEVE_HANDELING(9023, "Actie - Administratieve handeling", "Actie.AdmHnd", ACTIE),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'Actie'.
     */
    ACTIE__PARTIJ(3209, "Actie - Partij", "Actie.Partij", ACTIE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'Actie'.
     */
    ACTIE__TIJDSTIP_REGISTRATIE(3181, "Actie - Tijdstip registratie", "Actie.TsReg", ACTIE),
    /**
     * Representeert de kolom: 'DatOntlening' van de tabel: 'Actie'.
     */
    ACTIE__DATUM_ONTLENING(10183, "Actie - Datum ontlening", "Actie.DatOntlening", ACTIE),
    /**
     * Representeert de tabel: 'ActieBron'.
     */
    ACTIE_BRON(8118, "Actie \\ Bron", "ActieBron", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'ActieBron'.
     */
    ACTIE_BRON__I_D(8121, "Actie \\ Bron - ID", "ActieBron.ID", ACTIE_BRON),
    /**
     * Representeert de kolom: 'Actie' van de tabel: 'ActieBron'.
     */
    ACTIE_BRON__ACTIE(8122, "Actie \\ Bron - Actie", "ActieBron.Actie", ACTIE_BRON),
    /**
     * Representeert de kolom: 'Doc' van de tabel: 'ActieBron'.
     */
    ACTIE_BRON__DOCUMENT(8123, "Actie \\ Bron - Document", "ActieBron.Doc", ACTIE_BRON),
    /**
     * Representeert de kolom: 'Rechtsgrond' van de tabel: 'ActieBron'.
     */
    ACTIE_BRON__RECHTSGROND(8124, "Actie \\ Bron - Rechtsgrond", "ActieBron.Rechtsgrond", ACTIE_BRON),
    /**
     * Representeert de kolom: 'Rechtsgrondoms' van de tabel: 'ActieBron'.
     */
    ACTIE_BRON__RECHTSGRONDOMSCHRIJVING(10914, "Actie \\ Bron - Rechtsgrondomschrijving", "ActieBron.Rechtsgrondoms", ACTIE_BRON),
    /**
     * Representeert de tabel: 'AdellijkeTitel'.
     */
    ADELLIJKE_TITEL(3096, "Adellijke titel", "AdellijkeTitel", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AdellijkeTitel'.
     */
    ADELLIJKE_TITEL__I_D(3098, "Adellijke titel - ID", "AdellijkeTitel.ID", ADELLIJKE_TITEL),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'AdellijkeTitel'.
     */
    ADELLIJKE_TITEL__CODE(3345, "Adellijke titel - Code", "AdellijkeTitel.Code", ADELLIJKE_TITEL),
    /**
     * Representeert de kolom: 'NaamMannelijk' van de tabel: 'AdellijkeTitel'.
     */
    ADELLIJKE_TITEL__NAAM_MANNELIJK(3101, "Adellijke titel - Naam mannelijk", "AdellijkeTitel.NaamMannelijk", ADELLIJKE_TITEL),
    /**
     * Representeert de kolom: 'NaamVrouwelijk' van de tabel: 'AdellijkeTitel'.
     */
    ADELLIJKE_TITEL__NAAM_VROUWELIJK(3102, "Adellijke titel - Naam vrouwelijk", "AdellijkeTitel.NaamVrouwelijk", ADELLIJKE_TITEL),
    /**
     * Representeert de tabel: 'AdmHnd'.
     */
    ADMINISTRATIEVE_HANDELING(9018, "Administratieve handeling", "AdmHnd", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AdmHnd'.
     */
    ADMINISTRATIEVE_HANDELING__I_D(9021, "Administratieve handeling - ID", "AdmHnd.ID", ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'AdmHnd'.
     */
    ADMINISTRATIEVE_HANDELING__SOORT(9208, "Administratieve handeling - Soort", "AdmHnd.Srt", ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'AdmHnd'.
     */
    ADMINISTRATIEVE_HANDELING__PARTIJ(9172, "Administratieve handeling - Partij", "AdmHnd.Partij", ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'ToelichtingOntlening' van de tabel: 'AdmHnd'.
     */
    ADMINISTRATIEVE_HANDELING__TOELICHTING_ONTLENING(6174, "Administratieve handeling - Toelichting ontlening", "AdmHnd.ToelichtingOntlening",
            ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'AdmHnd'.
     */
    ADMINISTRATIEVE_HANDELING__TIJDSTIP_REGISTRATIE(9505, "Administratieve handeling - Tijdstip registratie", "AdmHnd.TsReg", ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'TsLev' van de tabel: 'AdmHnd'.
     */
    ADMINISTRATIEVE_HANDELING__TIJDSTIP_LEVERING(10052, "Administratieve handeling - Tijdstip levering", "AdmHnd.TsLev", ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de tabel: 'AdmHndGedeblokkeerdeMelding'.
     */
    ADMINISTRATIEVE_HANDELING_GEDEBLOKKEERDE_MELDING(6222, "Administratieve handeling \\ Gedeblokkeerde melding", "AdmHndGedeblokkeerdeMelding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AdmHndGedeblokkeerdeMelding'.
     */
    ADMINISTRATIEVE_HANDELING_GEDEBLOKKEERDE_MELDING__I_D(6225, "Administratieve handeling \\ Gedeblokkeerde melding - ID",
            "AdmHndGedeblokkeerdeMelding.ID", ADMINISTRATIEVE_HANDELING_GEDEBLOKKEERDE_MELDING),
    /**
     * Representeert de kolom: 'AdmHnd' van de tabel: 'AdmHndGedeblokkeerdeMelding'.
     */
    ADMINISTRATIEVE_HANDELING_GEDEBLOKKEERDE_MELDING__ADMINISTRATIEVE_HANDELING(6226,
            "Administratieve handeling \\ Gedeblokkeerde melding - Administratieve handeling", "AdmHndGedeblokkeerdeMelding.AdmHnd",
            ADMINISTRATIEVE_HANDELING_GEDEBLOKKEERDE_MELDING),
    /**
     * Representeert de kolom: 'GedeblokkeerdeMelding' van de tabel: 'AdmHndGedeblokkeerdeMelding'.
     */
    ADMINISTRATIEVE_HANDELING_GEDEBLOKKEERDE_MELDING__GEDEBLOKKEERDE_MELDING(6227,
            "Administratieve handeling \\ Gedeblokkeerde melding - Gedeblokkeerde melding", "AdmHndGedeblokkeerdeMelding.GedeblokkeerdeMelding",
            ADMINISTRATIEVE_HANDELING_GEDEBLOKKEERDE_MELDING),
    /**
     * Representeert de tabel: 'AuttypeVanAfgifteReisdoc'.
     */
    AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT(3802, "Autoriteittype van afgifte reisdocument", "AuttypeVanAfgifteReisdoc", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AuttypeVanAfgifteReisdoc'.
     */
    AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT__I_D(3805, "Autoriteittype van afgifte reisdocument - ID", "AuttypeVanAfgifteReisdoc.ID",
            AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'AuttypeVanAfgifteReisdoc'.
     */
    AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT__CODE(3808, "Autoriteittype van afgifte reisdocument - Code", "AuttypeVanAfgifteReisdoc.Code",
            AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'AuttypeVanAfgifteReisdoc'.
     */
    AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT__NAAM(3809, "Autoriteittype van afgifte reisdocument - Naam", "AuttypeVanAfgifteReisdoc.Naam",
            AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'AuttypeVanAfgifteReisdoc'.
     */
    AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT__DATUM_AANVANG_GELDIGHEID(3920, "Autoriteittype van afgifte reisdocument - Datum aanvang geldigheid",
            "AuttypeVanAfgifteReisdoc.DatAanvGel", AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'AuttypeVanAfgifteReisdoc'.
     */
    AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT__DATUM_EINDE_GELDIGHEID(3921, "Autoriteittype van afgifte reisdocument - Datum einde geldigheid",
            "AuttypeVanAfgifteReisdoc.DatEindeGel", AUTORITEITTYPE_VAN_AFGIFTE_REISDOCUMENT),
    /**
     * Representeert de tabel: 'Betr'.
     */
    BETROKKENHEID(3857, "Betrokkenheid", "Betr", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Betr'.
     */
    BETROKKENHEID__I_D(6102, "Betrokkenheid - ID", "Betr.ID", BETROKKENHEID),
    /**
     * Representeert de kolom: 'Relatie' van de tabel: 'Betr'.
     */
    BETROKKENHEID__RELATIE(3860, "Betrokkenheid - Relatie", "Betr.Relatie", BETROKKENHEID),
    /**
     * Representeert de kolom: 'Rol' van de tabel: 'Betr'.
     */
    BETROKKENHEID__ROL(3861, "Betrokkenheid - Rol", "Betr.Rol", BETROKKENHEID),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'Betr'.
     */
    BETROKKENHEID__PERSOON(3859, "Betrokkenheid - Persoon", "Betr.Pers", BETROKKENHEID),
    /**
     * Representeert de kolom: 'IndOuder' van de tabel: 'Betr'.
     */
    BETROKKENHEID__INDICATIE_OUDER(6088, "Betrokkenheid - Ouder?", "Betr.IndOuder", BETROKKENHEID),
    /**
     * Representeert de kolom: 'IndAdresgevendeOuder' van de tabel: 'Betr'.
     */
    BETROKKENHEID__INDICATIE_ADRESGEVENDE_OUDER(6176, "Betrokkenheid - Adresgevende ouder?", "Betr.IndAdresgevendeOuder", BETROKKENHEID),
    /**
     * Representeert de kolom: 'IndOuderHeeftGezag' van de tabel: 'Betr'.
     */
    BETROKKENHEID__INDICATIE_OUDER_HEEFT_GEZAG(3208, "Betrokkenheid - Ouder heeft gezag?", "Betr.IndOuderHeeftGezag", BETROKKENHEID),
    /**
     * Representeert de tabel: 'Bijhaard'.
     */
    BIJHOUDINGSAARD(3522, "Bijhoudingsaard", "Bijhaard", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Bijhaard'.
     */
    BIJHOUDINGSAARD__I_D(3535, "Bijhoudingsaard - ID", "Bijhaard.ID", BIJHOUDINGSAARD),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Bijhaard'.
     */
    BIJHOUDINGSAARD__CODE(11178, "Bijhoudingsaard - Code", "Bijhaard.Code", BIJHOUDINGSAARD),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Bijhaard'.
     */
    BIJHOUDINGSAARD__NAAM(3536, "Bijhoudingsaard - Naam", "Bijhaard.Naam", BIJHOUDINGSAARD),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Bijhaard'.
     */
    BIJHOUDINGSAARD__OMSCHRIJVING(10918, "Bijhoudingsaard - Omschrijving", "Bijhaard.Oms", BIJHOUDINGSAARD),
    /**
     * Representeert de tabel: 'BurgerzakenModule'.
     */
    BURGERZAKEN_MODULE(9510, "Burgerzaken module", "BurgerzakenModule", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'BurgerzakenModule'.
     */
    BURGERZAKEN_MODULE__I_D(9513, "Burgerzaken module - ID", "BurgerzakenModule.ID", BURGERZAKEN_MODULE),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'BurgerzakenModule'.
     */
    BURGERZAKEN_MODULE__NAAM(9514, "Burgerzaken module - Naam", "BurgerzakenModule.Naam", BURGERZAKEN_MODULE),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'BurgerzakenModule'.
     */
    BURGERZAKEN_MODULE__OMSCHRIJVING(9535, "Burgerzaken module - Omschrijving", "BurgerzakenModule.Oms", BURGERZAKEN_MODULE),
    /**
     * Representeert de tabel: 'CategorieAdmHnd'.
     */
    CATEGORIE_ADMINISTRATIEVE_HANDELING(9866, "Categorie administratieve handeling", "CategorieAdmHnd", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'CategorieAdmHnd'.
     */
    CATEGORIE_ADMINISTRATIEVE_HANDELING__I_D(9878, "Categorie administratieve handeling - ID", "CategorieAdmHnd.ID", CATEGORIE_ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'CategorieAdmHnd'.
     */
    CATEGORIE_ADMINISTRATIEVE_HANDELING__NAAM(9868, "Categorie administratieve handeling - Naam", "CategorieAdmHnd.Naam",
            CATEGORIE_ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de tabel: 'CategoriePersonen'.
     */
    CATEGORIE_PERSONEN(6159, "Categorie personen", "CategoriePersonen", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'CategoriePersonen'.
     */
    CATEGORIE_PERSONEN__I_D(6162, "Categorie personen - ID", "CategoriePersonen.ID", CATEGORIE_PERSONEN),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'CategoriePersonen'.
     */
    CATEGORIE_PERSONEN__NAAM(6163, "Categorie personen - Naam", "CategoriePersonen.Naam", CATEGORIE_PERSONEN),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'CategoriePersonen'.
     */
    CATEGORIE_PERSONEN__OMSCHRIJVING(6164, "Categorie personen - Omschrijving", "CategoriePersonen.Oms", CATEGORIE_PERSONEN),
    /**
     * Representeert de tabel: 'DbObject'.
     */
    DATABASE_OBJECT(5639, "Database object", "DbObject", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__I_D(5642, "Database object - ID", "DbObject.ID", DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__NAAM(5645, "Database object - Naam", "DbObject.Naam", DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__SOORT(5644, "Database object - Soort", "DbObject.Srt", DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'Ouder' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__OUDER(5661, "Database object - Ouder", "DbObject.Ouder", DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'JavaIdentifier' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__JAVA_IDENTIFIER(5672, "Database object - Java identifier", "DbObject.JavaIdentifier", DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'IndOnderdeelPL' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__INDICATIE_ONDERDEEL_PERSOONSLIJST(11340, "Database object - Onderdeel persoonslijst?", "DbObject.IndOnderdeelPL", DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'IndOnderdeelPLOuder' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__INDICATIE_ONDERDEEL_PERSOONSLIJST_OUDER(11341, "Database object - Onderdeel persoonslijst ouder?", "DbObject.IndOnderdeelPLOuder",
            DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'IndOnderdeelPLKind' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__INDICATIE_ONDERDEEL_PERSOONSLIJST_KIND(11342, "Database object - Onderdeel persoonslijst kind?", "DbObject.IndOnderdeelPLKind",
            DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'IndOnderdeelPLPartner' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__INDICATIE_ONDERDEEL_PERSOONSLIJST_PARTNER(11343, "Database object - Onderdeel persoonslijst partner?",
            "DbObject.IndOnderdeelPLPartner", DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'IndVerantwoordingPL' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__INDICATIE_VERANTWOORDING_PERSOONSLIJST(11345, "Database object - Verantwoording persoonslijst?", "DbObject.IndVerantwoordingPL",
            DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__DATUM_AANVANG_GELDIGHEID(5654, "Database object - Datum aanvang geldigheid", "DbObject.DatAanvGel", DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'DbObject'.
     */
    DATABASE_OBJECT__DATUM_EINDE_GELDIGHEID(5655, "Database object - Datum einde geldigheid", "DbObject.DatEindeGel", DATABASE_OBJECT),
    /**
     * Representeert de tabel: 'Doc'.
     */
    DOCUMENT(3135, "Document", "Doc", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Doc'.
     */
    DOCUMENT__I_D(3138, "Document - ID", "Doc.ID", DOCUMENT),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Doc'.
     */
    DOCUMENT__SOORT(3157, "Document - Soort", "Doc.Srt", DOCUMENT),
    /**
     * Representeert de kolom: 'Ident' van de tabel: 'Doc'.
     */
    DOCUMENT__IDENTIFICATIE(3160, "Document - Identificatie", "Doc.Ident", DOCUMENT),
    /**
     * Representeert de kolom: 'Aktenr' van de tabel: 'Doc'.
     */
    DOCUMENT__AKTENUMMER(3786, "Document - Aktenummer", "Doc.Aktenr", DOCUMENT),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Doc'.
     */
    DOCUMENT__OMSCHRIJVING(3162, "Document - Omschrijving", "Doc.Oms", DOCUMENT),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'Doc'.
     */
    DOCUMENT__PARTIJ(3139, "Document - Partij", "Doc.Partij", DOCUMENT),
    /**
     * Representeert de tabel: 'Element'.
     */
    ELEMENT(3171, "Element", "Element", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Element'.
     */
    ELEMENT__I_D(3173, "Element - ID", "Element.ID", ELEMENT),
    /**
     * Representeert de kolom: 'Volgnr' van de tabel: 'Element'.
     */
    ELEMENT__VOLGNUMMER(14262, "Element - Volgnummer", "Element.Volgnr", ELEMENT),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Element'.
     */
    ELEMENT__SOORT(3721, "Element - Soort", "Element.Srt", ELEMENT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Element'.
     */
    ELEMENT__NAAM(13495, "Element - Naam", "Element.Naam", ELEMENT),
    /**
     * Representeert de kolom: 'ElementNaam' van de tabel: 'Element'.
     */
    ELEMENT__ELEMENT_NAAM(3175, "Element - Element naam", "Element.ElementNaam", ELEMENT),
    /**
     * Representeert de kolom: 'Objecttype' van de tabel: 'Element'.
     */
    ELEMENT__OBJECTTYPE(5663, "Element - Objecttype", "Element.Objecttype", ELEMENT),
    /**
     * Representeert de kolom: 'Groep' van de tabel: 'Element'.
     */
    ELEMENT__GROEP(10485, "Element - Groep", "Element.Groep", ELEMENT),
    /**
     * Representeert de kolom: 'HisTabel' van de tabel: 'Element'.
     */
    ELEMENT__HIS_TABEL(13496, "Element - Historie tabel", "Element.HisTabel", ELEMENT),
    /**
     * Representeert de kolom: 'AliasVan' van de tabel: 'Element'.
     */
    ELEMENT__ALIAS_VAN(13497, "Element - Alias van", "Element.AliasVan", ELEMENT),
    /**
     * Representeert de kolom: 'Expressie' van de tabel: 'Element'.
     */
    ELEMENT__EXPRESSIE(13500, "Element - Expressie", "Element.Expressie", ELEMENT),
    /**
     * Representeert de kolom: 'Autorisatie' van de tabel: 'Element'.
     */
    ELEMENT__AUTORISATIE(13528, "Element - Autorisatie", "Element.Autorisatie", ELEMENT),
    /**
     * Representeert de kolom: 'IdentDb' van de tabel: 'Element'.
     */
    ELEMENT__IDENTIFICATIE_DATABASE(13498, "Element - Identificatie database", "Element.IdentDb", ELEMENT),
    /**
     * Representeert de kolom: 'HisIdentDB' van de tabel: 'Element'.
     */
    ELEMENT__HIS_IDENTIFIER_DATABASE(13499, "Element - Historie identificatie database", "Element.HisIdentDB", ELEMENT),
    /**
     * Representeert de kolom: 'DbObject' van de tabel: 'Element'.
     */
    ELEMENT__DATABASE_OBJECT(13529, "Element - Database object", "Element.DbObject", ELEMENT),
    /**
     * Representeert de kolom: 'HisDbObject' van de tabel: 'Element'.
     */
    ELEMENT__HIS_DATABASE_OBJECT(13530, "Element - Historie database object", "Element.HisDbObject", ELEMENT),
    /**
     * Representeert de kolom: 'LeverenAlsStamgegeven' van de tabel: 'Element'.
     */
    ELEMENT__LEVEREN_ALS_STAMGEGEVEN(14263, "Element - Leveren als stamgegeven", "Element.LeverenAlsStamgegeven", ELEMENT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'Element'.
     */
    ELEMENT__DATUM_AANVANG_GELDIGHEID(3930, "Element - Datum aanvang geldigheid", "Element.DatAanvGel", ELEMENT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'Element'.
     */
    ELEMENT__DATUM_EINDE_GELDIGHEID(3931, "Element - Datum einde geldigheid", "Element.DatEindeGel", ELEMENT),
    /**
     * Representeert de tabel: 'FunctieAdres'.
     */
    FUNCTIE_ADRES(3256, "Functie adres", "FunctieAdres", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'FunctieAdres'.
     */
    FUNCTIE_ADRES__I_D(3258, "Functie adres - ID", "FunctieAdres.ID", FUNCTIE_ADRES),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'FunctieAdres'.
     */
    FUNCTIE_ADRES__CODE(3486, "Functie adres - Code", "FunctieAdres.Code", FUNCTIE_ADRES),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'FunctieAdres'.
     */
    FUNCTIE_ADRES__NAAM(3260, "Functie adres - Naam", "FunctieAdres.Naam", FUNCTIE_ADRES),
    /**
     * Representeert de tabel: 'GedeblokkeerdeMelding'.
     */
    GEDEBLOKKEERDE_MELDING(6216, "Gedeblokkeerde melding", "GedeblokkeerdeMelding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'GedeblokkeerdeMelding'.
     */
    GEDEBLOKKEERDE_MELDING__I_D(6219, "Gedeblokkeerde melding - ID", "GedeblokkeerdeMelding.ID", GEDEBLOKKEERDE_MELDING),
    /**
     * Representeert de kolom: 'Regel' van de tabel: 'GedeblokkeerdeMelding'.
     */
    GEDEBLOKKEERDE_MELDING__REGEL(6220, "Gedeblokkeerde melding - Regel", "GedeblokkeerdeMelding.Regel", GEDEBLOKKEERDE_MELDING),
    /**
     * Representeert de kolom: 'Melding' van de tabel: 'GedeblokkeerdeMelding'.
     */
    GEDEBLOKKEERDE_MELDING__MELDING(6254, "Gedeblokkeerde melding - Melding", "GedeblokkeerdeMelding.Melding", GEDEBLOKKEERDE_MELDING),
    /**
     * Representeert de tabel: 'GegevenInOnderzoek'.
     */
    GEGEVEN_IN_ONDERZOEK(3863, "Gegeven in onderzoek", "GegevenInOnderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'GegevenInOnderzoek'.
     */
    GEGEVEN_IN_ONDERZOEK__I_D(10844, "Gegeven in onderzoek - ID", "GegevenInOnderzoek.ID", GEGEVEN_IN_ONDERZOEK),
    /**
     * Representeert de kolom: 'Onderzoek' van de tabel: 'GegevenInOnderzoek'.
     */
    GEGEVEN_IN_ONDERZOEK__ONDERZOEK(3865, "Gegeven in onderzoek - Onderzoek", "GegevenInOnderzoek.Onderzoek", GEGEVEN_IN_ONDERZOEK),
    /**
     * Representeert de kolom: 'Element' van de tabel: 'GegevenInOnderzoek'.
     */
    GEGEVEN_IN_ONDERZOEK__ELEMENT(3866, "Gegeven in onderzoek - Element", "GegevenInOnderzoek.Element", GEGEVEN_IN_ONDERZOEK),
    /**
     * Representeert de kolom: 'ObjectSleutel' van de tabel: 'GegevenInOnderzoek'.
     */
    GEGEVEN_IN_ONDERZOEK__OBJECT_SLEUTEL(3649, "Gegeven in onderzoek - Object sleutel", "GegevenInOnderzoek.ObjectSleutel", GEGEVEN_IN_ONDERZOEK),
    /**
     * Representeert de kolom: 'VoorkomenSleutel' van de tabel: 'GegevenInOnderzoek'.
     */
    GEGEVEN_IN_ONDERZOEK__VOORKOMEN_SLEUTEL(14188, "Gegeven in onderzoek - Voorkomen sleutel", "GegevenInOnderzoek.VoorkomenSleutel", GEGEVEN_IN_ONDERZOEK),
    /**
     * Representeert de tabel: 'GegevenInTerugmelding'.
     */
    GEGEVEN_IN_TERUGMELDING(10753, "Gegeven in terugmelding", "GegevenInTerugmelding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'GegevenInTerugmelding'.
     */
    GEGEVEN_IN_TERUGMELDING__I_D(10756, "Gegeven in terugmelding - ID", "GegevenInTerugmelding.ID", GEGEVEN_IN_TERUGMELDING),
    /**
     * Representeert de kolom: 'Terugmelding' van de tabel: 'GegevenInTerugmelding'.
     */
    GEGEVEN_IN_TERUGMELDING__TERUGMELDING(10758, "Gegeven in terugmelding - Terugmelding", "GegevenInTerugmelding.Terugmelding", GEGEVEN_IN_TERUGMELDING),
    /**
     * Representeert de kolom: 'Element' van de tabel: 'GegevenInTerugmelding'.
     */
    GEGEVEN_IN_TERUGMELDING__ELEMENT(10759, "Gegeven in terugmelding - Element", "GegevenInTerugmelding.Element", GEGEVEN_IN_TERUGMELDING),
    /**
     * Representeert de kolom: 'BetwijfeldeWaarde' van de tabel: 'GegevenInTerugmelding'.
     */
    GEGEVEN_IN_TERUGMELDING__BETWIJFELDE_WAARDE(10761, "Gegeven in terugmelding - Betwijfelde waarde", "GegevenInTerugmelding.BetwijfeldeWaarde",
            GEGEVEN_IN_TERUGMELDING),
    /**
     * Representeert de kolom: 'VerwachteWaarde' van de tabel: 'GegevenInTerugmelding'.
     */
    GEGEVEN_IN_TERUGMELDING__VERWACHTE_WAARDE(10762, "Gegeven in terugmelding - Verwachte waarde", "GegevenInTerugmelding.VerwachteWaarde",
            GEGEVEN_IN_TERUGMELDING),
    /**
     * Representeert de tabel: 'Gem'.
     */
    GEMEENTE(9558, "Gemeente", "Gem", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Gem'.
     */
    GEMEENTE__I_D(9561, "Gemeente - ID", "Gem.ID", GEMEENTE),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Gem'.
     */
    GEMEENTE__NAAM(9562, "Gemeente - Naam", "Gem.Naam", GEMEENTE),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Gem'.
     */
    GEMEENTE__CODE(9563, "Gemeente - Code", "Gem.Code", GEMEENTE),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'Gem'.
     */
    GEMEENTE__PARTIJ(9564, "Gemeente - Partij", "Gem.Partij", GEMEENTE),
    /**
     * Representeert de kolom: 'VoortzettendeGem' van de tabel: 'Gem'.
     */
    GEMEENTE__VOORTZETTENDE_GEMEENTE(2005, "Gemeente - Voortzettende gemeente", "Gem.VoortzettendeGem", GEMEENTE),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'Gem'.
     */
    GEMEENTE__DATUM_AANVANG_GELDIGHEID(9566, "Gemeente - Datum aanvang geldigheid", "Gem.DatAanvGel", GEMEENTE),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'Gem'.
     */
    GEMEENTE__DATUM_EINDE_GELDIGHEID(9567, "Gemeente - Datum einde geldigheid", "Gem.DatEindeGel", GEMEENTE),
    /**
     * Representeert de tabel: 'Geslachtsaand'.
     */
    GESLACHTSAANDUIDING(1973, "Geslachtsaanduiding", "Geslachtsaand", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Geslachtsaand'.
     */
    GESLACHTSAANDUIDING__I_D(1976, "Geslachtsaanduiding - ID", "Geslachtsaand.ID", GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Geslachtsaand'.
     */
    GESLACHTSAANDUIDING__CODE(1978, "Geslachtsaanduiding - Code", "Geslachtsaand.Code", GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Geslachtsaand'.
     */
    GESLACHTSAANDUIDING__NAAM(1981, "Geslachtsaanduiding - Naam", "Geslachtsaand.Naam", GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Geslachtsaand'.
     */
    GESLACHTSAANDUIDING__OMSCHRIJVING(1982, "Geslachtsaanduiding - Omschrijving", "Geslachtsaand.Oms", GESLACHTSAANDUIDING),
    /**
     * Representeert de tabel: 'LandGebied'.
     */
    LAND_GEBIED(3041, "Land/gebied", "LandGebied", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'LandGebied'.
     */
    LAND_GEBIED__I_D(3049, "Land/gebied - ID", "LandGebied.ID", LAND_GEBIED),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'LandGebied'.
     */
    LAND_GEBIED__CODE(5614, "Land/gebied - Code", "LandGebied.Code", LAND_GEBIED),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'LandGebied'.
     */
    LAND_GEBIED__NAAM(3107, "Land/gebied - Naam", "LandGebied.Naam", LAND_GEBIED),
    /**
     * Representeert de kolom: 'ISO31661Alpha2' van de tabel: 'LandGebied'.
     */
    LAND_GEBIED__I_S_O31661_ALPHA2(2144, "Land/gebied - ISO 3166-1 alpha 2", "LandGebied.ISO31661Alpha2", LAND_GEBIED),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'LandGebied'.
     */
    LAND_GEBIED__DATUM_AANVANG_GELDIGHEID(3937, "Land/gebied - Datum aanvang geldigheid", "LandGebied.DatAanvGel", LAND_GEBIED),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'LandGebied'.
     */
    LAND_GEBIED__DATUM_EINDE_GELDIGHEID(3938, "Land/gebied - Datum einde geldigheid", "LandGebied.DatEindeGel", LAND_GEBIED),
    /**
     * Representeert de tabel: 'MultirealiteitRegel'.
     */
    MULTIREALITEIT_REGEL(2047, "Multirealiteit regel", "MultirealiteitRegel", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'MultirealiteitRegel'.
     */
    MULTIREALITEIT_REGEL__I_D(2049, "Multirealiteit regel - ID", "MultirealiteitRegel.ID", MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'GeldigVoor' van de tabel: 'MultirealiteitRegel'.
     */
    MULTIREALITEIT_REGEL__GELDIG_VOOR(2050, "Multirealiteit regel - Geldig voor", "MultirealiteitRegel.GeldigVoor", MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'MultirealiteitRegel'.
     */
    MULTIREALITEIT_REGEL__SOORT(2051, "Multirealiteit regel - Soort", "MultirealiteitRegel.Srt", MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'MultirealiteitRegel'.
     */
    MULTIREALITEIT_REGEL__PERSOON(2053, "Multirealiteit regel - Persoon", "MultirealiteitRegel.Pers", MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'MultirealiteitPers' van de tabel: 'MultirealiteitRegel'.
     */
    MULTIREALITEIT_REGEL__MULTIREALITEIT_PERSOON(2054, "Multirealiteit regel - Multirealiteit persoon", "MultirealiteitRegel.MultirealiteitPers",
            MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'Relatie' van de tabel: 'MultirealiteitRegel'.
     */
    MULTIREALITEIT_REGEL__RELATIE(2055, "Multirealiteit regel - Relatie", "MultirealiteitRegel.Relatie", MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'Betr' van de tabel: 'MultirealiteitRegel'.
     */
    MULTIREALITEIT_REGEL__BETROKKENHEID(2056, "Multirealiteit regel - Betrokkenheid", "MultirealiteitRegel.Betr", MULTIREALITEIT_REGEL),
    /**
     * Representeert de tabel: 'Naamgebruik'.
     */
    NAAMGEBRUIK(3617, "Naamgebruik", "Naamgebruik", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Naamgebruik'.
     */
    NAAMGEBRUIK__I_D(3619, "Naamgebruik - ID", "Naamgebruik.ID", NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Naamgebruik'.
     */
    NAAMGEBRUIK__CODE(3621, "Naamgebruik - Code", "Naamgebruik.Code", NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Naamgebruik'.
     */
    NAAMGEBRUIK__NAAM(3623, "Naamgebruik - Naam", "Naamgebruik.Naam", NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Naamgebruik'.
     */
    NAAMGEBRUIK__OMSCHRIJVING(2001, "Naamgebruik - Omschrijving", "Naamgebruik.Oms", NAAMGEBRUIK),
    /**
     * Representeert de tabel: 'NadereBijhaard'.
     */
    NADERE_BIJHOUDINGSAARD(10865, "Nadere bijhoudingsaard", "NadereBijhaard", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'NadereBijhaard'.
     */
    NADERE_BIJHOUDINGSAARD__I_D(10868, "Nadere bijhoudingsaard - ID", "NadereBijhaard.ID", NADERE_BIJHOUDINGSAARD),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'NadereBijhaard'.
     */
    NADERE_BIJHOUDINGSAARD__CODE(10894, "Nadere bijhoudingsaard - Code", "NadereBijhaard.Code", NADERE_BIJHOUDINGSAARD),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'NadereBijhaard'.
     */
    NADERE_BIJHOUDINGSAARD__NAAM(10869, "Nadere bijhoudingsaard - Naam", "NadereBijhaard.Naam", NADERE_BIJHOUDINGSAARD),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'NadereBijhaard'.
     */
    NADERE_BIJHOUDINGSAARD__OMSCHRIJVING(10896, "Nadere bijhoudingsaard - Omschrijving", "NadereBijhaard.Oms", NADERE_BIJHOUDINGSAARD),
    /**
     * Representeert de tabel: 'Nation'.
     */
    NATIONALITEIT(3087, "Nationaliteit", "Nation", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Nation'.
     */
    NATIONALITEIT__I_D(3089, "Nationaliteit - ID", "Nation.ID", NATIONALITEIT),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Nation'.
     */
    NATIONALITEIT__CODE(6080, "Nationaliteit - Code", "Nation.Code", NATIONALITEIT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Nation'.
     */
    NATIONALITEIT__NAAM(3109, "Nationaliteit - Naam", "Nation.Naam", NATIONALITEIT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'Nation'.
     */
    NATIONALITEIT__DATUM_AANVANG_GELDIGHEID(3942, "Nationaliteit - Datum aanvang geldigheid", "Nation.DatAanvGel", NATIONALITEIT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'Nation'.
     */
    NATIONALITEIT__DATUM_EINDE_GELDIGHEID(3943, "Nationaliteit - Datum einde geldigheid", "Nation.DatEindeGel", NATIONALITEIT),
    /**
     * Representeert de tabel: 'Onderzoek'.
     */
    ONDERZOEK(3167, "Onderzoek", "Onderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Onderzoek'.
     */
    ONDERZOEK__I_D(3169, "Onderzoek - ID", "Onderzoek.ID", ONDERZOEK),
    /**
     * Representeert de kolom: 'DatAanv' van de tabel: 'Onderzoek'.
     */
    ONDERZOEK__DATUM_AANVANG(3178, "Onderzoek - Datum aanvang", "Onderzoek.DatAanv", ONDERZOEK),
    /**
     * Representeert de kolom: 'VerwachteAfhandeldat' van de tabel: 'Onderzoek'.
     */
    ONDERZOEK__VERWACHTE_AFHANDELDATUM(10848, "Onderzoek - Verwachte afhandeldatum", "Onderzoek.VerwachteAfhandeldat", ONDERZOEK),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'Onderzoek'.
     */
    ONDERZOEK__DATUM_EINDE(3179, "Onderzoek - Datum einde", "Onderzoek.DatEinde", ONDERZOEK),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Onderzoek'.
     */
    ONDERZOEK__OMSCHRIJVING(3772, "Onderzoek - Omschrijving", "Onderzoek.Oms", ONDERZOEK),
    /**
     * Representeert de kolom: 'Status' van de tabel: 'Onderzoek'.
     */
    ONDERZOEK__STATUS(10849, "Onderzoek - Status", "Onderzoek.Status", ONDERZOEK),
    /**
     * Representeert de kolom: 'AdmHnd' van de tabel: 'Onderzoek'.
     */
    ONDERZOEK__ADMINISTRATIEVE_HANDELING(10842, "Onderzoek - Administratieve handeling", "Onderzoek.AdmHnd", ONDERZOEK),
    /**
     * Representeert de tabel: 'Partij'.
     */
    PARTIJ(3141, "Partij", "Partij", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Partij'.
     */
    PARTIJ__I_D(3143, "Partij - ID", "Partij.ID", PARTIJ),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Partij'.
     */
    PARTIJ__NAAM(3145, "Partij - Naam", "Partij.Naam", PARTIJ),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Partij'.
     */
    PARTIJ__SOORT(2195, "Partij - Soort", "Partij.Srt", PARTIJ),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Partij'.
     */
    PARTIJ__CODE(4601, "Partij - Code", "Partij.Code", PARTIJ),
    /**
     * Representeert de kolom: 'DatAanv' van de tabel: 'Partij'.
     */
    PARTIJ__DATUM_AANVANG(2199, "Partij - Datum aanvang", "Partij.DatAanv", PARTIJ),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'Partij'.
     */
    PARTIJ__DATUM_EINDE(2200, "Partij - Datum einde", "Partij.DatEinde", PARTIJ),
    /**
     * Representeert de kolom: 'IndVerstrbeperkingMogelijk' van de tabel: 'Partij'.
     */
    PARTIJ__INDICATIE_VERSTREKKINGSBEPERKING_MOGELIJK(2196, "Partij - Verstrekkingsbeperking mogelijk?", "Partij.IndVerstrbeperkingMogelijk", PARTIJ),
    /**
     * Representeert de tabel: 'PartijOnderzoek'.
     */
    PARTIJ_ONDERZOEK(10775, "Partij \\ Onderzoek", "PartijOnderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PartijOnderzoek'.
     */
    PARTIJ_ONDERZOEK__I_D(10778, "Partij \\ Onderzoek - ID", "PartijOnderzoek.ID", PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'PartijOnderzoek'.
     */
    PARTIJ_ONDERZOEK__PARTIJ(10780, "Partij \\ Onderzoek - Partij", "PartijOnderzoek.Partij", PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'Onderzoek' van de tabel: 'PartijOnderzoek'.
     */
    PARTIJ_ONDERZOEK__ONDERZOEK(10782, "Partij \\ Onderzoek - Onderzoek", "PartijOnderzoek.Onderzoek", PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'Rol' van de tabel: 'PartijOnderzoek'.
     */
    PARTIJ_ONDERZOEK__ROL(10786, "Partij \\ Onderzoek - Rol", "PartijOnderzoek.Rol", PARTIJ_ONDERZOEK),
    /**
     * Representeert de tabel: 'PartijRol'.
     */
    PARTIJ_ROL(2181, "Partij \\ Rol", "PartijRol", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PartijRol'.
     */
    PARTIJ_ROL__I_D(2184, "Partij \\ Rol - ID", "PartijRol.ID", PARTIJ_ROL),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'PartijRol'.
     */
    PARTIJ_ROL__PARTIJ(2185, "Partij \\ Rol - Partij", "PartijRol.Partij", PARTIJ_ROL),
    /**
     * Representeert de kolom: 'Rol' van de tabel: 'PartijRol'.
     */
    PARTIJ_ROL__ROL(2186, "Partij \\ Rol - Rol", "PartijRol.Rol", PARTIJ_ROL),
    /**
     * Representeert de tabel: 'Pers'.
     */
    PERSOON(3010, "Persoon", "Pers", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Pers'.
     */
    PERSOON__I_D(3015, "Persoon - ID", "Pers.ID", PERSOON),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Pers'.
     */
    PERSOON__SOORT(1997, "Persoon - Soort", "Pers.Srt", PERSOON),
    /**
     * Representeert de kolom: 'AdmHnd' van de tabel: 'Pers'.
     */
    PERSOON__ADMINISTRATIEVE_HANDELING(10111, "Persoon - Administratieve handeling", "Pers.AdmHnd", PERSOON),
    /**
     * Representeert de kolom: 'TsLaatsteWijz' van de tabel: 'Pers'.
     */
    PERSOON__TIJDSTIP_LAATSTE_WIJZIGING(3251, "Persoon - Tijdstip laatste wijziging", "Pers.TsLaatsteWijz", PERSOON),
    /**
     * Representeert de kolom: 'IndOnderzoekNaarNietOpgenome' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_ONDERZOEK_NAAR_NIET_OPGENOMEN_GEGEVENS(3166, "Persoon - Onderzoek naar niet opgenomen gegevens?",
            "Pers.IndOnderzoekNaarNietOpgenome", PERSOON),
    /**
     * Representeert de kolom: 'Sorteervolgorde' van de tabel: 'Pers'.
     */
    PERSOON__SORTEERVOLGORDE(10404, "Persoon - Sorteervolgorde", "Pers.Sorteervolgorde", PERSOON),
    /**
     * Representeert de kolom: 'IndOnverwBijhvoorstelNietIng' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_ONVERWERKT_BIJHOUDINGSVOORSTEL_NIET_INGEZETENE_AANWEZIG(10899,
            "Persoon - Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig?", "Pers.IndOnverwBijhvoorstelNietIng", PERSOON),
    /**
     * Representeert de kolom: 'TsLaatsteWijzGBASystematiek' van de tabel: 'Pers'.
     */
    PERSOON__TIJDSTIP_LAATSTE_WIJZIGING_G_B_A_SYSTEMATIEK(10901, "Persoon - Tijdstip laatste wijziging GBA-systematiek",
            "Pers.TsLaatsteWijzGBASystematiek", PERSOON),
    /**
     * Representeert de kolom: 'BSN' van de tabel: 'Pers'.
     */
    PERSOON__BURGERSERVICENUMMER(3018, "Persoon - Burgerservicenummer", "Pers.BSN", PERSOON),
    /**
     * Representeert de kolom: 'ANr' van de tabel: 'Pers'.
     */
    PERSOON__ADMINISTRATIENUMMER(3013, "Persoon - Administratienummer", "Pers.ANr", PERSOON),
    /**
     * Representeert de kolom: 'IndAfgeleid' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_AFGELEID(3914, "Persoon - Afgeleid?", "Pers.IndAfgeleid", PERSOON),
    /**
     * Representeert de kolom: 'IndNreeks' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_NAMENREEKS(3592, "Persoon - Namenreeks?", "Pers.IndNreeks", PERSOON),
    /**
     * Representeert de kolom: 'Predicaat' van de tabel: 'Pers'.
     */
    PERSOON__PREDICAAT(1969, "Persoon - Predicaat", "Pers.Predicaat", PERSOON),
    /**
     * Representeert de kolom: 'Voornamen' van de tabel: 'Pers'.
     */
    PERSOON__VOORNAMEN(3092, "Persoon - Voornamen", "Pers.Voornamen", PERSOON),
    /**
     * Representeert de kolom: 'AdellijkeTitel' van de tabel: 'Pers'.
     */
    PERSOON__ADELLIJKE_TITEL(1968, "Persoon - Adellijke titel", "Pers.AdellijkeTitel", PERSOON),
    /**
     * Representeert de kolom: 'Voorvoegsel' van de tabel: 'Pers'.
     */
    PERSOON__VOORVOEGSEL(3309, "Persoon - Voorvoegsel", "Pers.Voorvoegsel", PERSOON),
    /**
     * Representeert de kolom: 'Scheidingsteken' van de tabel: 'Pers'.
     */
    PERSOON__SCHEIDINGSTEKEN(3253, "Persoon - Scheidingsteken", "Pers.Scheidingsteken", PERSOON),
    /**
     * Representeert de kolom: 'Geslnaamstam' van de tabel: 'Pers'.
     */
    PERSOON__GESLACHTSNAAMSTAM(3094, "Persoon - Geslachtsnaamstam", "Pers.Geslnaamstam", PERSOON),
    /**
     * Representeert de kolom: 'DatGeboorte' van de tabel: 'Pers'.
     */
    PERSOON__DATUM_GEBOORTE(3673, "Persoon - Datum geboorte", "Pers.DatGeboorte", PERSOON),
    /**
     * Representeert de kolom: 'GemGeboorte' van de tabel: 'Pers'.
     */
    PERSOON__GEMEENTE_GEBOORTE(3675, "Persoon - Gemeente geboorte", "Pers.GemGeboorte", PERSOON),
    /**
     * Representeert de kolom: 'WplnaamGeboorte' van de tabel: 'Pers'.
     */
    PERSOON__WOONPLAATSNAAM_GEBOORTE(3676, "Persoon - Woonplaatsnaam geboorte", "Pers.WplnaamGeboorte", PERSOON),
    /**
     * Representeert de kolom: 'BLPlaatsGeboorte' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDSE_PLAATS_GEBOORTE(3677, "Persoon - Buitenlandse plaats geboorte", "Pers.BLPlaatsGeboorte", PERSOON),
    /**
     * Representeert de kolom: 'BLRegioGeboorte' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDSE_REGIO_GEBOORTE(3530, "Persoon - Buitenlandse regio geboorte", "Pers.BLRegioGeboorte", PERSOON),
    /**
     * Representeert de kolom: 'OmsLocGeboorte' van de tabel: 'Pers'.
     */
    PERSOON__OMSCHRIJVING_LOCATIE_GEBOORTE(3678, "Persoon - Omschrijving locatie geboorte", "Pers.OmsLocGeboorte", PERSOON),
    /**
     * Representeert de kolom: 'LandGebiedGeboorte' van de tabel: 'Pers'.
     */
    PERSOON__LAND_GEBIED_GEBOORTE(3543, "Persoon - Land/gebied geboorte", "Pers.LandGebiedGeboorte", PERSOON),
    /**
     * Representeert de kolom: 'Geslachtsaand' van de tabel: 'Pers'.
     */
    PERSOON__GESLACHTSAANDUIDING(3031, "Persoon - Geslachtsaanduiding", "Pers.Geslachtsaand", PERSOON),
    /**
     * Representeert de kolom: 'DatInschr' van de tabel: 'Pers'.
     */
    PERSOON__DATUM_INSCHRIJVING(3570, "Persoon - Datum inschrijving", "Pers.DatInschr", PERSOON),
    /**
     * Representeert de kolom: 'Versienr' van de tabel: 'Pers'.
     */
    PERSOON__VERSIENUMMER(3250, "Persoon - Versienummer", "Pers.Versienr", PERSOON),
    /**
     * Representeert de kolom: 'Dattijdstempel' van de tabel: 'Pers'.
     */
    PERSOON__DATUMTIJDSTEMPEL(11186, "Persoon - Datumtijdstempel", "Pers.Dattijdstempel", PERSOON),
    /**
     * Representeert de kolom: 'VorigeBSN' van de tabel: 'Pers'.
     */
    PERSOON__VORIGE_BURGERSERVICENUMMER(3134, "Persoon - Vorige burgerservicenummer", "Pers.VorigeBSN", PERSOON),
    /**
     * Representeert de kolom: 'VolgendeBSN' van de tabel: 'Pers'.
     */
    PERSOON__VOLGENDE_BURGERSERVICENUMMER(3136, "Persoon - Volgende burgerservicenummer", "Pers.VolgendeBSN", PERSOON),
    /**
     * Representeert de kolom: 'VorigeANr' van de tabel: 'Pers'.
     */
    PERSOON__VORIGE_ADMINISTRATIENUMMER(3247, "Persoon - Vorige administratienummer", "Pers.VorigeANr", PERSOON),
    /**
     * Representeert de kolom: 'VolgendeANr' van de tabel: 'Pers'.
     */
    PERSOON__VOLGENDE_ADMINISTRATIENUMMER(3248, "Persoon - Volgende administratienummer", "Pers.VolgendeANr", PERSOON),
    /**
     * Representeert de kolom: 'Bijhpartij' van de tabel: 'Pers'.
     */
    PERSOON__BIJHOUDINGSPARTIJ(3573, "Persoon - Bijhoudingspartij", "Pers.Bijhpartij", PERSOON),
    /**
     * Representeert de kolom: 'Bijhaard' van de tabel: 'Pers'.
     */
    PERSOON__BIJHOUDINGSAARD(3568, "Persoon - Bijhoudingsaard", "Pers.Bijhaard", PERSOON),
    /**
     * Representeert de kolom: 'NadereBijhaard' van de tabel: 'Pers'.
     */
    PERSOON__NADERE_BIJHOUDINGSAARD(10864, "Persoon - Nadere bijhoudingsaard", "Pers.NadereBijhaard", PERSOON),
    /**
     * Representeert de kolom: 'IndOnverwDocAanw' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG(3578, "Persoon - Onverwerkt document aanwezig?", "Pers.IndOnverwDocAanw", PERSOON),
    /**
     * Representeert de kolom: 'DatOverlijden' van de tabel: 'Pers'.
     */
    PERSOON__DATUM_OVERLIJDEN(3546, "Persoon - Datum overlijden", "Pers.DatOverlijden", PERSOON),
    /**
     * Representeert de kolom: 'GemOverlijden' van de tabel: 'Pers'.
     */
    PERSOON__GEMEENTE_OVERLIJDEN(3551, "Persoon - Gemeente overlijden", "Pers.GemOverlijden", PERSOON),
    /**
     * Representeert de kolom: 'WplnaamOverlijden' van de tabel: 'Pers'.
     */
    PERSOON__WOONPLAATSNAAM_OVERLIJDEN(3544, "Persoon - Woonplaatsnaam overlijden", "Pers.WplnaamOverlijden", PERSOON),
    /**
     * Representeert de kolom: 'BLPlaatsOverlijden' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDSE_PLAATS_OVERLIJDEN(3552, "Persoon - Buitenlandse plaats overlijden", "Pers.BLPlaatsOverlijden", PERSOON),
    /**
     * Representeert de kolom: 'BLRegioOverlijden' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDSE_REGIO_OVERLIJDEN(3556, "Persoon - Buitenlandse regio overlijden", "Pers.BLRegioOverlijden", PERSOON),
    /**
     * Representeert de kolom: 'OmsLocOverlijden' van de tabel: 'Pers'.
     */
    PERSOON__OMSCHRIJVING_LOCATIE_OVERLIJDEN(3555, "Persoon - Omschrijving locatie overlijden", "Pers.OmsLocOverlijden", PERSOON),
    /**
     * Representeert de kolom: 'LandGebiedOverlijden' van de tabel: 'Pers'.
     */
    PERSOON__LAND_GEBIED_OVERLIJDEN(3558, "Persoon - Land/gebied overlijden", "Pers.LandGebiedOverlijden", PERSOON),
    /**
     * Representeert de kolom: 'Naamgebruik' van de tabel: 'Pers'.
     */
    PERSOON__NAAMGEBRUIK(3593, "Persoon - Naamgebruik", "Pers.Naamgebruik", PERSOON),
    /**
     * Representeert de kolom: 'IndNaamgebruikAfgeleid' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_NAAMGEBRUIK_AFGELEID(3633, "Persoon - Naamgebruik afgeleid?", "Pers.IndNaamgebruikAfgeleid", PERSOON),
    /**
     * Representeert de kolom: 'PredicaatNaamgebruik' van de tabel: 'Pers'.
     */
    PERSOON__PREDICAAT_NAAMGEBRUIK(3703, "Persoon - Predicaat naamgebruik", "Pers.PredicaatNaamgebruik", PERSOON),
    /**
     * Representeert de kolom: 'VoornamenNaamgebruik' van de tabel: 'Pers'.
     */
    PERSOON__VOORNAMEN_NAAMGEBRUIK(3319, "Persoon - Voornamen naamgebruik", "Pers.VoornamenNaamgebruik", PERSOON),
    /**
     * Representeert de kolom: 'AdellijkeTitelNaamgebruik' van de tabel: 'Pers'.
     */
    PERSOON__ADELLIJKE_TITEL_NAAMGEBRUIK(6113, "Persoon - Adellijke titel naamgebruik", "Pers.AdellijkeTitelNaamgebruik", PERSOON),
    /**
     * Representeert de kolom: 'VoorvoegselNaamgebruik' van de tabel: 'Pers'.
     */
    PERSOON__VOORVOEGSEL_NAAMGEBRUIK(3355, "Persoon - Voorvoegsel naamgebruik", "Pers.VoorvoegselNaamgebruik", PERSOON),
    /**
     * Representeert de kolom: 'ScheidingstekenNaamgebruik' van de tabel: 'Pers'.
     */
    PERSOON__SCHEIDINGSTEKEN_NAAMGEBRUIK(3580, "Persoon - Scheidingsteken naamgebruik", "Pers.ScheidingstekenNaamgebruik", PERSOON),
    /**
     * Representeert de kolom: 'GeslnaamstamNaamgebruik' van de tabel: 'Pers'.
     */
    PERSOON__GESLACHTSNAAMSTAM_NAAMGEBRUIK(3323, "Persoon - Geslachtsnaamstam naamgebruik", "Pers.GeslnaamstamNaamgebruik", PERSOON),
    /**
     * Representeert de kolom: 'SrtMigratie' van de tabel: 'Pers'.
     */
    PERSOON__SOORT_MIGRATIE(10881, "Persoon - Soort migratie", "Pers.SrtMigratie", PERSOON),
    /**
     * Representeert de kolom: 'RdnWijzMigratie' van de tabel: 'Pers'.
     */
    PERSOON__REDEN_WIJZIGING_MIGRATIE(11277, "Persoon - Reden wijziging migratie", "Pers.RdnWijzMigratie", PERSOON),
    /**
     * Representeert de kolom: 'AangMigratie' van de tabel: 'Pers'.
     */
    PERSOON__AANGEVER_MIGRATIE(11278, "Persoon - Aangever migratie", "Pers.AangMigratie", PERSOON),
    /**
     * Representeert de kolom: 'LandGebiedMigratie' van de tabel: 'Pers'.
     */
    PERSOON__LAND_GEBIED_MIGRATIE(3579, "Persoon - Land/gebied migratie", "Pers.LandGebiedMigratie", PERSOON),
    /**
     * Representeert de kolom: 'BLAdresRegel1Migratie' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDS_ADRES_REGEL1_MIGRATIE(10882, "Persoon - Buitenlands adres regel 1 migratie", "Pers.BLAdresRegel1Migratie", PERSOON),
    /**
     * Representeert de kolom: 'BLAdresRegel2Migratie' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDS_ADRES_REGEL2_MIGRATIE(10883, "Persoon - Buitenlands adres regel 2 migratie", "Pers.BLAdresRegel2Migratie", PERSOON),
    /**
     * Representeert de kolom: 'BLAdresRegel3Migratie' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDS_ADRES_REGEL3_MIGRATIE(10884, "Persoon - Buitenlands adres regel 3 migratie", "Pers.BLAdresRegel3Migratie", PERSOON),
    /**
     * Representeert de kolom: 'BLAdresRegel4Migratie' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDS_ADRES_REGEL4_MIGRATIE(10885, "Persoon - Buitenlands adres regel 4 migratie", "Pers.BLAdresRegel4Migratie", PERSOON),
    /**
     * Representeert de kolom: 'BLAdresRegel5Migratie' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDS_ADRES_REGEL5_MIGRATIE(10886, "Persoon - Buitenlands adres regel 5 migratie", "Pers.BLAdresRegel5Migratie", PERSOON),
    /**
     * Representeert de kolom: 'BLAdresRegel6Migratie' van de tabel: 'Pers'.
     */
    PERSOON__BUITENLANDS_ADRES_REGEL6_MIGRATIE(10887, "Persoon - Buitenlands adres regel 6 migratie", "Pers.BLAdresRegel6Migratie", PERSOON),
    /**
     * Representeert de kolom: 'AandVerblijfsr' van de tabel: 'Pers'.
     */
    PERSOON__AANDUIDING_VERBLIJFSRECHT(3310, "Persoon - Aanduiding verblijfsrecht", "Pers.AandVerblijfsr", PERSOON),
    /**
     * Representeert de kolom: 'DatMededelingVerblijfsr' van de tabel: 'Pers'.
     */
    PERSOON__DATUM_MEDEDELING_VERBLIJFSRECHT(3325, "Persoon - Datum mededeling verblijfsrecht", "Pers.DatMededelingVerblijfsr", PERSOON),
    /**
     * Representeert de kolom: 'DatVoorzEindeVerblijfsr' van de tabel: 'Pers'.
     */
    PERSOON__DATUM_VOORZIEN_EINDE_VERBLIJFSRECHT(3481, "Persoon - Datum voorzien einde verblijfsrecht", "Pers.DatVoorzEindeVerblijfsr", PERSOON),
    /**
     * Representeert de kolom: 'IndUitslKiesr' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_UITSLUITING_KIESRECHT(3322, "Persoon - Uitsluiting kiesrecht?", "Pers.IndUitslKiesr", PERSOON),
    /**
     * Representeert de kolom: 'DatVoorzEindeUitslKiesr' van de tabel: 'Pers'.
     */
    PERSOON__DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT(3559, "Persoon - Datum voorzien einde uitsluiting kiesrecht", "Pers.DatVoorzEindeUitslKiesr",
            PERSOON),
    /**
     * Representeert de kolom: 'IndDeelnEUVerkiezingen' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_DEELNAME_E_U_VERKIEZINGEN(3320, "Persoon - Deelname EU verkiezingen?", "Pers.IndDeelnEUVerkiezingen", PERSOON),
    /**
     * Representeert de kolom: 'DatAanlAanpDeelnEUVerkiezing' van de tabel: 'Pers'.
     */
    PERSOON__DATUM_AANLEIDING_AANPASSING_DEELNAME_E_U_VERKIEZINGEN(3562, "Persoon - Datum aanleiding aanpassing deelname EU verkiezingen",
            "Pers.DatAanlAanpDeelnEUVerkiezing", PERSOON),
    /**
     * Representeert de kolom: 'DatVoorzEindeUitslEUVerkiezi' van de tabel: 'Pers'.
     */
    PERSOON__DATUM_VOORZIEN_EINDE_UITSLUITING_E_U_VERKIEZINGEN(3564, "Persoon - Datum voorzien einde uitsluiting EU verkiezingen",
            "Pers.DatVoorzEindeUitslEUVerkiezi", PERSOON),
    /**
     * Representeert de kolom: 'GemPK' van de tabel: 'Pers'.
     */
    PERSOON__GEMEENTE_PERSOONSKAART(3233, "Persoon - Gemeente persoonskaart", "Pers.GemPK", PERSOON),
    /**
     * Representeert de kolom: 'IndPKVolledigGeconv' van de tabel: 'Pers'.
     */
    PERSOON__INDICATIE_PERSOONSKAART_VOLLEDIG_GECONVERTEERD(3313, "Persoon - Persoonskaart volledig geconverteerd?", "Pers.IndPKVolledigGeconv", PERSOON),
    /**
     * Representeert de tabel: 'PersAdres'.
     */
    PERSOON_ADRES(3237, "Persoon \\ Adres", "PersAdres", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__I_D(3239, "Persoon \\ Adres - ID", "PersAdres.ID", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__PERSOON(3241, "Persoon \\ Adres - Persoon", "PersAdres.Pers", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__SOORT(3263, "Persoon \\ Adres - Soort", "PersAdres.Srt", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'RdnWijz' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__REDEN_WIJZIGING(3715, "Persoon \\ Adres - Reden wijziging", "PersAdres.RdnWijz", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'AangAdresh' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__AANGEVER_ADRESHOUDING(3301, "Persoon \\ Adres - Aangever adreshouding", "PersAdres.AangAdresh", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'DatAanvAdresh' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__DATUM_AANVANG_ADRESHOUDING(3730, "Persoon \\ Adres - Datum aanvang adreshouding", "PersAdres.DatAanvAdresh", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'IdentcodeAdresseerbaarObject' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT(3284, "Persoon \\ Adres - Identificatiecode adresseerbaar object",
            "PersAdres.IdentcodeAdresseerbaarObject", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'IdentcodeNraand' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__IDENTIFICATIECODE_NUMMERAANDUIDING(3286, "Persoon \\ Adres - Identificatiecode nummeraanduiding", "PersAdres.IdentcodeNraand",
            PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Gem' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__GEMEENTE(3788, "Persoon \\ Adres - Gemeente", "PersAdres.Gem", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'NOR' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__NAAM_OPENBARE_RUIMTE(3269, "Persoon \\ Adres - Naam openbare ruimte", "PersAdres.NOR", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'AfgekorteNOR' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__AFGEKORTE_NAAM_OPENBARE_RUIMTE(3267, "Persoon \\ Adres - Afgekorte naam openbare ruimte", "PersAdres.AfgekorteNOR", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Gemdeel' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__GEMEENTEDEEL(3265, "Persoon \\ Adres - Gemeentedeel", "PersAdres.Gemdeel", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Huisnr' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__HUISNUMMER(3271, "Persoon \\ Adres - Huisnummer", "PersAdres.Huisnr", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Huisletter' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__HUISLETTER(3273, "Persoon \\ Adres - Huisletter", "PersAdres.Huisletter", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Huisnrtoevoeging' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__HUISNUMMERTOEVOEGING(3275, "Persoon \\ Adres - Huisnummertoevoeging", "PersAdres.Huisnrtoevoeging", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Postcode' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__POSTCODE(3281, "Persoon \\ Adres - Postcode", "PersAdres.Postcode", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Wplnaam' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__WOONPLAATSNAAM(3282, "Persoon \\ Adres - Woonplaatsnaam", "PersAdres.Wplnaam", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'LocTenOpzichteVanAdres' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__LOCATIE_TEN_OPZICHTE_VAN_ADRES(3278, "Persoon \\ Adres - Locatie ten opzichte van adres", "PersAdres.LocTenOpzichteVanAdres",
            PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Locoms' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__LOCATIEOMSCHRIJVING(3288, "Persoon \\ Adres - Locatieomschrijving", "PersAdres.Locoms", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel1' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__BUITENLANDS_ADRES_REGEL1(3291, "Persoon \\ Adres - Buitenlands adres regel 1", "PersAdres.BLAdresRegel1", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel2' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__BUITENLANDS_ADRES_REGEL2(3292, "Persoon \\ Adres - Buitenlands adres regel 2", "PersAdres.BLAdresRegel2", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel3' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__BUITENLANDS_ADRES_REGEL3(3293, "Persoon \\ Adres - Buitenlands adres regel 3", "PersAdres.BLAdresRegel3", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel4' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__BUITENLANDS_ADRES_REGEL4(3709, "Persoon \\ Adres - Buitenlands adres regel 4", "PersAdres.BLAdresRegel4", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel5' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__BUITENLANDS_ADRES_REGEL5(3710, "Persoon \\ Adres - Buitenlands adres regel 5", "PersAdres.BLAdresRegel5", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel6' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__BUITENLANDS_ADRES_REGEL6(3711, "Persoon \\ Adres - Buitenlands adres regel 6", "PersAdres.BLAdresRegel6", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'LandGebied' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__LAND_GEBIED(3289, "Persoon \\ Adres - Land/gebied", "PersAdres.LandGebied", PERSOON_ADRES),
    /**
     * Representeert de kolom: 'IndPersAangetroffenOpAdres' van de tabel: 'PersAdres'.
     */
    PERSOON_ADRES__INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES(9540, "Persoon \\ Adres - Persoon aangetroffen op adres?",
            "PersAdres.IndPersAangetroffenOpAdres", PERSOON_ADRES),
    /**
     * Representeert de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT(3020, "Persoon \\ Geslachtsnaamcomponent", "PersGeslnaamcomp", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT__I_D(3648, "Persoon \\ Geslachtsnaamcomponent - ID", "PersGeslnaamcomp.ID", PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT__PERSOON(3024, "Persoon \\ Geslachtsnaamcomponent - Persoon", "PersGeslnaamcomp.Pers", PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Volgnr' van de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT__VOLGNUMMER(3029, "Persoon \\ Geslachtsnaamcomponent - Volgnummer", "PersGeslnaamcomp.Volgnr",
            PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Predicaat' van de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT__PREDICAAT(3117, "Persoon \\ Geslachtsnaamcomponent - Predicaat", "PersGeslnaamcomp.Predicaat",
            PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'AdellijkeTitel' van de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT__ADELLIJKE_TITEL(3118, "Persoon \\ Geslachtsnaamcomponent - Adellijke titel", "PersGeslnaamcomp.AdellijkeTitel",
            PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Voorvoegsel' van de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT__VOORVOEGSEL(3030, "Persoon \\ Geslachtsnaamcomponent - Voorvoegsel", "PersGeslnaamcomp.Voorvoegsel",
            PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Scheidingsteken' van de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT__SCHEIDINGSTEKEN(3069, "Persoon \\ Geslachtsnaamcomponent - Scheidingsteken", "PersGeslnaamcomp.Scheidingsteken",
            PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Stam' van de tabel: 'PersGeslnaamcomp'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT__STAM(3025, "Persoon \\ Geslachtsnaamcomponent - Stam", "PersGeslnaamcomp.Stam", PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de tabel: 'PersIndicatie'.
     */
    PERSOON_INDICATIE(3637, "Persoon \\ Indicatie", "PersIndicatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersIndicatie'.
     */
    PERSOON_INDICATIE__I_D(3656, "Persoon \\ Indicatie - ID", "PersIndicatie.ID", PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersIndicatie'.
     */
    PERSOON_INDICATIE__PERSOON(3657, "Persoon \\ Indicatie - Persoon", "PersIndicatie.Pers", PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'PersIndicatie'.
     */
    PERSOON_INDICATIE__SOORT(3658, "Persoon \\ Indicatie - Soort", "PersIndicatie.Srt", PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'Waarde' van de tabel: 'PersIndicatie'.
     */
    PERSOON_INDICATIE__WAARDE(3659, "Persoon \\ Indicatie - Waarde", "PersIndicatie.Waarde", PERSOON_INDICATIE),
    /**
     * Representeert de tabel: 'PersNation'.
     */
    PERSOON_NATIONALITEIT(3129, "Persoon \\ Nationaliteit", "PersNation", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersNation'.
     */
    PERSOON_NATIONALITEIT__I_D(3652, "Persoon \\ Nationaliteit - ID", "PersNation.ID", PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersNation'.
     */
    PERSOON_NATIONALITEIT__PERSOON(3130, "Persoon \\ Nationaliteit - Persoon", "PersNation.Pers", PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'Nation' van de tabel: 'PersNation'.
     */
    PERSOON_NATIONALITEIT__NATIONALITEIT(3131, "Persoon \\ Nationaliteit - Nationaliteit", "PersNation.Nation", PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'RdnVerk' van de tabel: 'PersNation'.
     */
    PERSOON_NATIONALITEIT__REDEN_VERKRIJGING(3229, "Persoon \\ Nationaliteit - Reden verkrijging", "PersNation.RdnVerk", PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'RdnVerlies' van de tabel: 'PersNation'.
     */
    PERSOON_NATIONALITEIT__REDEN_VERLIES(3230, "Persoon \\ Nationaliteit - Reden verlies", "PersNation.RdnVerlies", PERSOON_NATIONALITEIT),
    /**
     * Representeert de tabel: 'PersOnderzoek'.
     */
    PERSOON_ONDERZOEK(6127, "Persoon \\ Onderzoek", "PersOnderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersOnderzoek'.
     */
    PERSOON_ONDERZOEK__I_D(6130, "Persoon \\ Onderzoek - ID", "PersOnderzoek.ID", PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersOnderzoek'.
     */
    PERSOON_ONDERZOEK__PERSOON(6131, "Persoon \\ Onderzoek - Persoon", "PersOnderzoek.Pers", PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'Onderzoek' van de tabel: 'PersOnderzoek'.
     */
    PERSOON_ONDERZOEK__ONDERZOEK(6132, "Persoon \\ Onderzoek - Onderzoek", "PersOnderzoek.Onderzoek", PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'Rol' van de tabel: 'PersOnderzoek'.
     */
    PERSOON_ONDERZOEK__ROL(10771, "Persoon \\ Onderzoek - Rol", "PersOnderzoek.Rol", PERSOON_ONDERZOEK),
    /**
     * Representeert de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT(3576, "Persoon \\ Reisdocument", "PersReisdoc", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__I_D(3751, "Persoon \\ Reisdocument - ID", "PersReisdoc.ID", PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__PERSOON(3752, "Persoon \\ Reisdocument - Persoon", "PersReisdoc.Pers", PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__SOORT(3739, "Persoon \\ Reisdocument - Soort", "PersReisdoc.Srt", PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Nr' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__NUMMER(3741, "Persoon \\ Reisdocument - Nummer", "PersReisdoc.Nr", PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'LengteHouder' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__LENGTE_HOUDER(3749, "Persoon \\ Reisdocument - Lengte houder", "PersReisdoc.LengteHouder", PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'AutVanAfgifte' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__AUTORITEIT_VAN_AFGIFTE(3744, "Persoon \\ Reisdocument - Autoriteit van afgifte", "PersReisdoc.AutVanAfgifte",
            PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatIngangDoc' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__DATUM_INGANG_DOCUMENT(6126, "Persoon \\ Reisdocument - Datum ingang document", "PersReisdoc.DatIngangDoc", PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatEindeDoc' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__DATUM_EINDE_DOCUMENT(3745, "Persoon \\ Reisdocument - Datum einde document", "PersReisdoc.DatEindeDoc", PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatUitgifte' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__DATUM_UITGIFTE(3742, "Persoon \\ Reisdocument - Datum uitgifte", "PersReisdoc.DatUitgifte", PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatInhingVermissing' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__DATUM_INHOUDING_VERMISSING(3746, "Persoon \\ Reisdocument - Datum inhouding/vermissing", "PersReisdoc.DatInhingVermissing",
            PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'AandInhingVermissing' van de tabel: 'PersReisdoc'.
     */
    PERSOON_REISDOCUMENT__AANDUIDING_INHOUDING_VERMISSING(3747, "Persoon \\ Reisdocument - Aanduiding inhouding/vermissing",
            "PersReisdoc.AandInhingVermissing", PERSOON_REISDOCUMENT),
    /**
     * Representeert de tabel: 'PersVerificatie'.
     */
    PERSOON_VERIFICATIE(3775, "Persoon \\ Verificatie", "PersVerificatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersVerificatie'.
     */
    PERSOON_VERIFICATIE__I_D(3777, "Persoon \\ Verificatie - ID", "PersVerificatie.ID", PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'Geverifieerde' van de tabel: 'PersVerificatie'.
     */
    PERSOON_VERIFICATIE__GEVERIFIEERDE(2142, "Persoon \\ Verificatie - Geverifieerde", "PersVerificatie.Geverifieerde", PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'PersVerificatie'.
     */
    PERSOON_VERIFICATIE__PARTIJ(10915, "Persoon \\ Verificatie - Partij", "PersVerificatie.Partij", PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'PersVerificatie'.
     */
    PERSOON_VERIFICATIE__SOORT(3779, "Persoon \\ Verificatie - Soort", "PersVerificatie.Srt", PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'Dat' van de tabel: 'PersVerificatie'.
     */
    PERSOON_VERIFICATIE__DATUM(3778, "Persoon \\ Verificatie - Datum", "PersVerificatie.Dat", PERSOON_VERIFICATIE),
    /**
     * Representeert de tabel: 'PersVerstrbeperking'.
     */
    PERSOON_VERSTREKKINGSBEPERKING(9344, "Persoon \\ Verstrekkingsbeperking", "PersVerstrbeperking", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersVerstrbeperking'.
     */
    PERSOON_VERSTREKKINGSBEPERKING__I_D(9349, "Persoon \\ Verstrekkingsbeperking - ID", "PersVerstrbeperking.ID", PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersVerstrbeperking'.
     */
    PERSOON_VERSTREKKINGSBEPERKING__PERSOON(9351, "Persoon \\ Verstrekkingsbeperking - Persoon", "PersVerstrbeperking.Pers",
            PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'PersVerstrbeperking'.
     */
    PERSOON_VERSTREKKINGSBEPERKING__PARTIJ(9352, "Persoon \\ Verstrekkingsbeperking - Partij", "PersVerstrbeperking.Partij",
            PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'OmsDerde' van de tabel: 'PersVerstrbeperking'.
     */
    PERSOON_VERSTREKKINGSBEPERKING__OMSCHRIJVING_DERDE(10912, "Persoon \\ Verstrekkingsbeperking - Omschrijving derde", "PersVerstrbeperking.OmsDerde",
            PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'GemVerordening' van de tabel: 'PersVerstrbeperking'.
     */
    PERSOON_VERSTREKKINGSBEPERKING__GEMEENTE_VERORDENING(10913, "Persoon \\ Verstrekkingsbeperking - Gemeente verordening",
            "PersVerstrbeperking.GemVerordening", PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de tabel: 'PersVoornaam'.
     */
    PERSOON_VOORNAAM(3022, "Persoon \\ Voornaam", "PersVoornaam", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersVoornaam'.
     */
    PERSOON_VOORNAAM__I_D(3644, "Persoon \\ Voornaam - ID", "PersVoornaam.ID", PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersVoornaam'.
     */
    PERSOON_VOORNAAM__PERSOON(3023, "Persoon \\ Voornaam - Persoon", "PersVoornaam.Pers", PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'Volgnr' van de tabel: 'PersVoornaam'.
     */
    PERSOON_VOORNAAM__VOLGNUMMER(3028, "Persoon \\ Voornaam - Volgnummer", "PersVoornaam.Volgnr", PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'PersVoornaam'.
     */
    PERSOON_VOORNAAM__NAAM(3026, "Persoon \\ Voornaam - Naam", "PersVoornaam.Naam", PERSOON_VOORNAAM),
    /**
     * Representeert de tabel: 'PersCache'.
     */
    PERSOON_CACHE(10385, "Persoon cache", "PersCache", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersCache'.
     */
    PERSOON_CACHE__I_D(10387, "Persoon cache - ID", "PersCache.ID", PERSOON_CACHE),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersCache'.
     */
    PERSOON_CACHE__PERSOON(10401, "Persoon cache - Persoon", "PersCache.Pers", PERSOON_CACHE),
    /**
     * Representeert de kolom: 'Versienr' van de tabel: 'PersCache'.
     */
    PERSOON_CACHE__VERSIENUMMER(10388, "Persoon cache - Versienummer", "PersCache.Versienr", PERSOON_CACHE),
    /**
     * Representeert de kolom: 'PersHistorieVolledigChecksum' van de tabel: 'PersCache'.
     */
    PERSOON_CACHE__PERSOON_HISTORIE_VOLLEDIG_CHECKSUM(10392, "Persoon cache - Persoon historie volledig checksum",
            "PersCache.PersHistorieVolledigChecksum", PERSOON_CACHE),
    /**
     * Representeert de kolom: 'PersHistorieVolledigGegevens' van de tabel: 'PersCache'.
     */
    PERSOON_CACHE__PERSOON_HISTORIE_VOLLEDIG_GEGEVENS(10393, "Persoon cache - Persoon historie volledig gegevens",
            "PersCache.PersHistorieVolledigGegevens", PERSOON_CACHE),
    /**
     * Representeert de kolom: 'AfnemerindicatieChecksum' van de tabel: 'PersCache'.
     */
    PERSOON_CACHE__AFNEMERINDICATIE_CHECKSUM(11423, "Persoon cache - Afnemerindicatie checksum", "PersCache.AfnemerindicatieChecksum", PERSOON_CACHE),
    /**
     * Representeert de kolom: 'AfnemerindicatieGegevens' van de tabel: 'PersCache'.
     */
    PERSOON_CACHE__AFNEMERINDICATIE_GEGEVENS(11422, "Persoon cache - Afnemerindicatie gegevens", "PersCache.AfnemerindicatieGegevens", PERSOON_CACHE),
    /**
     * Representeert de tabel: 'Plaats'.
     */
    PLAATS(3037, "Plaats", "Plaats", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Plaats'.
     */
    PLAATS__I_D(3039, "Plaats - ID", "Plaats.ID", PLAATS),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Plaats'.
     */
    PLAATS__CODE(5668, "Plaats - Code", "Plaats.Code", PLAATS),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Plaats'.
     */
    PLAATS__NAAM(3115, "Plaats - Naam", "Plaats.Naam", PLAATS),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'Plaats'.
     */
    PLAATS__DATUM_AANVANG_GELDIGHEID(3984, "Plaats - Datum aanvang geldigheid", "Plaats.DatAanvGel", PLAATS),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'Plaats'.
     */
    PLAATS__DATUM_EINDE_GELDIGHEID(3985, "Plaats - Datum einde geldigheid", "Plaats.DatEindeGel", PLAATS),
    /**
     * Representeert de tabel: 'Predicaat'.
     */
    PREDICAAT(3095, "Predicaat", "Predicaat", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Predicaat'.
     */
    PREDICAAT__I_D(3112, "Predicaat - ID", "Predicaat.ID", PREDICAAT),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Predicaat'.
     */
    PREDICAAT__CODE(3613, "Predicaat - Code", "Predicaat.Code", PREDICAAT),
    /**
     * Representeert de kolom: 'NaamMannelijk' van de tabel: 'Predicaat'.
     */
    PREDICAAT__NAAM_MANNELIJK(3119, "Predicaat - Naam mannelijk", "Predicaat.NaamMannelijk", PREDICAAT),
    /**
     * Representeert de kolom: 'NaamVrouwelijk' van de tabel: 'Predicaat'.
     */
    PREDICAAT__NAAM_VROUWELIJK(3120, "Predicaat - Naam vrouwelijk", "Predicaat.NaamVrouwelijk", PREDICAAT),
    /**
     * Representeert de tabel: 'Rechtsgrond'.
     */
    RECHTSGROND(8125, "Rechtsgrond", "Rechtsgrond", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Rechtsgrond'.
     */
    RECHTSGROND__I_D(8128, "Rechtsgrond - ID", "Rechtsgrond.ID", RECHTSGROND),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Rechtsgrond'.
     */
    RECHTSGROND__CODE(8131, "Rechtsgrond - Code", "Rechtsgrond.Code", RECHTSGROND),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Rechtsgrond'.
     */
    RECHTSGROND__SOORT(8141, "Rechtsgrond - Soort", "Rechtsgrond.Srt", RECHTSGROND),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Rechtsgrond'.
     */
    RECHTSGROND__OMSCHRIJVING(8129, "Rechtsgrond - Omschrijving", "Rechtsgrond.Oms", RECHTSGROND),
    /**
     * Representeert de kolom: 'IndLeidtTotStrijdigheid' van de tabel: 'Rechtsgrond'.
     */
    RECHTSGROND__INDICATIE_LEIDT_TOT_STRIJDIGHEID(11105, "Rechtsgrond - Leidt tot strijdigheid?", "Rechtsgrond.IndLeidtTotStrijdigheid", RECHTSGROND),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'Rechtsgrond'.
     */
    RECHTSGROND__DATUM_AANVANG_GELDIGHEID(9173, "Rechtsgrond - Datum aanvang geldigheid", "Rechtsgrond.DatAanvGel", RECHTSGROND),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'Rechtsgrond'.
     */
    RECHTSGROND__DATUM_EINDE_GELDIGHEID(9174, "Rechtsgrond - Datum einde geldigheid", "Rechtsgrond.DatEindeGel", RECHTSGROND),
    /**
     * Representeert de tabel: 'RdnEindeRelatie'.
     */
    REDEN_EINDE_RELATIE(3200, "Reden einde relatie", "RdnEindeRelatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'RdnEindeRelatie'.
     */
    REDEN_EINDE_RELATIE__I_D(3202, "Reden einde relatie - ID", "RdnEindeRelatie.ID", REDEN_EINDE_RELATIE),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'RdnEindeRelatie'.
     */
    REDEN_EINDE_RELATIE__CODE(6078, "Reden einde relatie - Code", "RdnEindeRelatie.Code", REDEN_EINDE_RELATIE),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'RdnEindeRelatie'.
     */
    REDEN_EINDE_RELATIE__OMSCHRIJVING(3204, "Reden einde relatie - Omschrijving", "RdnEindeRelatie.Oms", REDEN_EINDE_RELATIE),
    /**
     * Representeert de tabel: 'RdnVerkNLNation'.
     */
    REDEN_VERKRIJGING_N_L_NATIONALITEIT(3214, "Reden verkrijging NL nationaliteit", "RdnVerkNLNation", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'RdnVerkNLNation'.
     */
    REDEN_VERKRIJGING_N_L_NATIONALITEIT__I_D(3217, "Reden verkrijging NL nationaliteit - ID", "RdnVerkNLNation.ID", REDEN_VERKRIJGING_N_L_NATIONALITEIT),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'RdnVerkNLNation'.
     */
    REDEN_VERKRIJGING_N_L_NATIONALITEIT__CODE(6262, "Reden verkrijging NL nationaliteit - Code", "RdnVerkNLNation.Code",
            REDEN_VERKRIJGING_N_L_NATIONALITEIT),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'RdnVerkNLNation'.
     */
    REDEN_VERKRIJGING_N_L_NATIONALITEIT__OMSCHRIJVING(3219, "Reden verkrijging NL nationaliteit - Omschrijving", "RdnVerkNLNation.Oms",
            REDEN_VERKRIJGING_N_L_NATIONALITEIT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'RdnVerkNLNation'.
     */
    REDEN_VERKRIJGING_N_L_NATIONALITEIT__DATUM_AANVANG_GELDIGHEID(3991, "Reden verkrijging NL nationaliteit - Datum aanvang geldigheid",
            "RdnVerkNLNation.DatAanvGel", REDEN_VERKRIJGING_N_L_NATIONALITEIT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'RdnVerkNLNation'.
     */
    REDEN_VERKRIJGING_N_L_NATIONALITEIT__DATUM_EINDE_GELDIGHEID(3992, "Reden verkrijging NL nationaliteit - Datum einde geldigheid",
            "RdnVerkNLNation.DatEindeGel", REDEN_VERKRIJGING_N_L_NATIONALITEIT),
    /**
     * Representeert de tabel: 'RdnVerliesNLNation'.
     */
    REDEN_VERLIES_N_L_NATIONALITEIT(3215, "Reden verlies NL nationaliteit", "RdnVerliesNLNation", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'RdnVerliesNLNation'.
     */
    REDEN_VERLIES_N_L_NATIONALITEIT__I_D(3223, "Reden verlies NL nationaliteit - ID", "RdnVerliesNLNation.ID", REDEN_VERLIES_N_L_NATIONALITEIT),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'RdnVerliesNLNation'.
     */
    REDEN_VERLIES_N_L_NATIONALITEIT__CODE(3226, "Reden verlies NL nationaliteit - Code", "RdnVerliesNLNation.Code", REDEN_VERLIES_N_L_NATIONALITEIT),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'RdnVerliesNLNation'.
     */
    REDEN_VERLIES_N_L_NATIONALITEIT__OMSCHRIJVING(6267, "Reden verlies NL nationaliteit - Omschrijving", "RdnVerliesNLNation.Oms",
            REDEN_VERLIES_N_L_NATIONALITEIT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'RdnVerliesNLNation'.
     */
    REDEN_VERLIES_N_L_NATIONALITEIT__DATUM_AANVANG_GELDIGHEID(3994, "Reden verlies NL nationaliteit - Datum aanvang geldigheid",
            "RdnVerliesNLNation.DatAanvGel", REDEN_VERLIES_N_L_NATIONALITEIT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'RdnVerliesNLNation'.
     */
    REDEN_VERLIES_N_L_NATIONALITEIT__DATUM_EINDE_GELDIGHEID(3995, "Reden verlies NL nationaliteit - Datum einde geldigheid",
            "RdnVerliesNLNation.DatEindeGel", REDEN_VERLIES_N_L_NATIONALITEIT),
    /**
     * Representeert de tabel: 'RdnWijzVerblijf'.
     */
    REDEN_WIJZIGING_VERBLIJF(3789, "Reden wijziging verblijf", "RdnWijzVerblijf", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'RdnWijzVerblijf'.
     */
    REDEN_WIJZIGING_VERBLIJF__I_D(3683, "Reden wijziging verblijf - ID", "RdnWijzVerblijf.ID", REDEN_WIJZIGING_VERBLIJF),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'RdnWijzVerblijf'.
     */
    REDEN_WIJZIGING_VERBLIJF__CODE(3685, "Reden wijziging verblijf - Code", "RdnWijzVerblijf.Code", REDEN_WIJZIGING_VERBLIJF),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'RdnWijzVerblijf'.
     */
    REDEN_WIJZIGING_VERBLIJF__NAAM(3687, "Reden wijziging verblijf - Naam", "RdnWijzVerblijf.Naam", REDEN_WIJZIGING_VERBLIJF),
    /**
     * Representeert de tabel: 'Regel'.
     */
    REGEL(5800, "Regel", "Regel", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Regel'.
     */
    REGEL__I_D(5803, "Regel - ID", "Regel.ID", REGEL),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Regel'.
     */
    REGEL__SOORT(5990, "Regel - Soort", "Regel.Srt", REGEL),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Regel'.
     */
    REGEL__CODE(5807, "Regel - Code", "Regel.Code", REGEL),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Regel'.
     */
    REGEL__OMSCHRIJVING(5808, "Regel - Omschrijving", "Regel.Oms", REGEL),
    /**
     * Representeert de kolom: 'Specificatie' van de tabel: 'Regel'.
     */
    REGEL__SPECIFICATIE(5813, "Regel - Specificatie", "Regel.Specificatie", REGEL),
    /**
     * Representeert de tabel: 'Regelverantwoording'.
     */
    REGELVERANTWOORDING(6145, "Regelverantwoording", "Regelverantwoording", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Regelverantwoording'.
     */
    REGELVERANTWOORDING__I_D(6148, "Regelverantwoording - ID", "Regelverantwoording.ID", REGELVERANTWOORDING),
    /**
     * Representeert de kolom: 'Actie' van de tabel: 'Regelverantwoording'.
     */
    REGELVERANTWOORDING__ACTIE(6149, "Regelverantwoording - Actie", "Regelverantwoording.Actie", REGELVERANTWOORDING),
    /**
     * Representeert de kolom: 'Regel' van de tabel: 'Regelverantwoording'.
     */
    REGELVERANTWOORDING__REGEL(6152, "Regelverantwoording - Regel", "Regelverantwoording.Regel", REGELVERANTWOORDING),
    /**
     * Representeert de tabel: 'Relatie'.
     */
    RELATIE(3184, "Relatie", "Relatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Relatie'.
     */
    RELATIE__I_D(3186, "Relatie - ID", "Relatie.ID", RELATIE),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Relatie'.
     */
    RELATIE__SOORT(3198, "Relatie - Soort", "Relatie.Srt", RELATIE),
    /**
     * Representeert de kolom: 'IndAsymmetrisch' van de tabel: 'Relatie'.
     */
    RELATIE__INDICATIE_ASYMMETRISCH(10904, "Relatie - Asymmetrisch?", "Relatie.IndAsymmetrisch", RELATIE),
    /**
     * Representeert de kolom: 'DatAanv' van de tabel: 'Relatie'.
     */
    RELATIE__DATUM_AANVANG(3754, "Relatie - Datum aanvang", "Relatie.DatAanv", RELATIE),
    /**
     * Representeert de kolom: 'GemAanv' van de tabel: 'Relatie'.
     */
    RELATIE__GEMEENTE_AANVANG(3755, "Relatie - Gemeente aanvang", "Relatie.GemAanv", RELATIE),
    /**
     * Representeert de kolom: 'WplnaamAanv' van de tabel: 'Relatie'.
     */
    RELATIE__WOONPLAATSNAAM_AANVANG(3756, "Relatie - Woonplaatsnaam aanvang", "Relatie.WplnaamAanv", RELATIE),
    /**
     * Representeert de kolom: 'BLPlaatsAanv' van de tabel: 'Relatie'.
     */
    RELATIE__BUITENLANDSE_PLAATS_AANVANG(3757, "Relatie - Buitenlandse plaats aanvang", "Relatie.BLPlaatsAanv", RELATIE),
    /**
     * Representeert de kolom: 'BLRegioAanv' van de tabel: 'Relatie'.
     */
    RELATIE__BUITENLANDSE_REGIO_AANVANG(3759, "Relatie - Buitenlandse regio aanvang", "Relatie.BLRegioAanv", RELATIE),
    /**
     * Representeert de kolom: 'OmsLocAanv' van de tabel: 'Relatie'.
     */
    RELATIE__OMSCHRIJVING_LOCATIE_AANVANG(3758, "Relatie - Omschrijving locatie aanvang", "Relatie.OmsLocAanv", RELATIE),
    /**
     * Representeert de kolom: 'LandGebiedAanv' van de tabel: 'Relatie'.
     */
    RELATIE__LAND_GEBIED_AANVANG(3760, "Relatie - Land/gebied aanvang", "Relatie.LandGebiedAanv", RELATIE),
    /**
     * Representeert de kolom: 'RdnEinde' van de tabel: 'Relatie'.
     */
    RELATIE__REDEN_EINDE(3207, "Relatie - Reden einde", "Relatie.RdnEinde", RELATIE),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'Relatie'.
     */
    RELATIE__DATUM_EINDE(3762, "Relatie - Datum einde", "Relatie.DatEinde", RELATIE),
    /**
     * Representeert de kolom: 'GemEinde' van de tabel: 'Relatie'.
     */
    RELATIE__GEMEENTE_EINDE(3763, "Relatie - Gemeente einde", "Relatie.GemEinde", RELATIE),
    /**
     * Representeert de kolom: 'WplnaamEinde' van de tabel: 'Relatie'.
     */
    RELATIE__WOONPLAATSNAAM_EINDE(3764, "Relatie - Woonplaatsnaam einde", "Relatie.WplnaamEinde", RELATIE),
    /**
     * Representeert de kolom: 'BLPlaatsEinde' van de tabel: 'Relatie'.
     */
    RELATIE__BUITENLANDSE_PLAATS_EINDE(3765, "Relatie - Buitenlandse plaats einde", "Relatie.BLPlaatsEinde", RELATIE),
    /**
     * Representeert de kolom: 'BLRegioEinde' van de tabel: 'Relatie'.
     */
    RELATIE__BUITENLANDSE_REGIO_EINDE(3767, "Relatie - Buitenlandse regio einde", "Relatie.BLRegioEinde", RELATIE),
    /**
     * Representeert de kolom: 'OmsLocEinde' van de tabel: 'Relatie'.
     */
    RELATIE__OMSCHRIJVING_LOCATIE_EINDE(3766, "Relatie - Omschrijving locatie einde", "Relatie.OmsLocEinde", RELATIE),
    /**
     * Representeert de kolom: 'LandGebiedEinde' van de tabel: 'Relatie'.
     */
    RELATIE__LAND_GEBIED_EINDE(3768, "Relatie - Land/gebied einde", "Relatie.LandGebiedEinde", RELATIE),
    /**
     * Representeert de kolom: 'DatNaamskeuzeOngeborenVrucht' van de tabel: 'Relatie'.
     */
    RELATIE__DATUM_NAAMSKEUZE_ONGEBOREN_VRUCHT(9379, "Relatie - Datum naamskeuze ongeboren vrucht", "Relatie.DatNaamskeuzeOngeborenVrucht", RELATIE),
    /**
     * Representeert de kolom: 'DatErkenningOngeborenVrucht' van de tabel: 'Relatie'.
     */
    RELATIE__DATUM_ERKENNING_ONGEBOREN_VRUCHT(9383, "Relatie - Datum erkenning ongeboren vrucht", "Relatie.DatErkenningOngeborenVrucht", RELATIE),
    /**
     * Representeert de tabel: 'Rol'.
     */
    ROL(2152, "Rol", "Rol", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Rol'.
     */
    ROL__I_D(2191, "Rol - ID", "Rol.ID", ROL),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Rol'.
     */
    ROL__NAAM(2192, "Rol - Naam", "Rol.Naam", ROL),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'Rol'.
     */
    ROL__DATUM_AANVANG_GELDIGHEID(4781, "Rol - Datum aanvang geldigheid", "Rol.DatAanvGel", ROL),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'Rol'.
     */
    ROL__DATUM_EINDE_GELDIGHEID(4782, "Rol - Datum einde geldigheid", "Rol.DatEindeGel", ROL),
    /**
     * Representeert de tabel: 'SrtNLReisdoc'.
     */
    SOORT_NEDERLANDS_REISDOCUMENT(3791, "Soort Nederlands reisdocument", "SrtNLReisdoc", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtNLReisdoc'.
     */
    SOORT_NEDERLANDS_REISDOCUMENT__I_D(3794, "Soort Nederlands reisdocument - ID", "SrtNLReisdoc.ID", SOORT_NEDERLANDS_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'SrtNLReisdoc'.
     */
    SOORT_NEDERLANDS_REISDOCUMENT__CODE(3796, "Soort Nederlands reisdocument - Code", "SrtNLReisdoc.Code", SOORT_NEDERLANDS_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtNLReisdoc'.
     */
    SOORT_NEDERLANDS_REISDOCUMENT__OMSCHRIJVING(3798, "Soort Nederlands reisdocument - Omschrijving", "SrtNLReisdoc.Oms", SOORT_NEDERLANDS_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'SrtNLReisdoc'.
     */
    SOORT_NEDERLANDS_REISDOCUMENT__DATUM_AANVANG_GELDIGHEID(6083, "Soort Nederlands reisdocument - Datum aanvang geldigheid", "SrtNLReisdoc.DatAanvGel",
            SOORT_NEDERLANDS_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'SrtNLReisdoc'.
     */
    SOORT_NEDERLANDS_REISDOCUMENT__DATUM_EINDE_GELDIGHEID(6084, "Soort Nederlands reisdocument - Datum einde geldigheid", "SrtNLReisdoc.DatEindeGel",
            SOORT_NEDERLANDS_REISDOCUMENT),
    /**
     * Representeert de tabel: 'SrtActie'.
     */
    SOORT_ACTIE(3063, "Soort actie", "SrtActie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtActie'.
     */
    SOORT_ACTIE__I_D(3065, "Soort actie - ID", "SrtActie.ID", SOORT_ACTIE),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtActie'.
     */
    SOORT_ACTIE__NAAM(3068, "Soort actie - Naam", "SrtActie.Naam", SOORT_ACTIE),
    /**
     * Representeert de tabel: 'SrtAdmHnd'.
     */
    SOORT_ADMINISTRATIEVE_HANDELING(9196, "Soort administratieve handeling", "SrtAdmHnd", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtAdmHnd'.
     */
    SOORT_ADMINISTRATIEVE_HANDELING__I_D(9199, "Soort administratieve handeling - ID", "SrtAdmHnd.ID", SOORT_ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'SrtAdmHnd'.
     */
    SOORT_ADMINISTRATIEVE_HANDELING__CODE(9202, "Soort administratieve handeling - Code", "SrtAdmHnd.Code", SOORT_ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtAdmHnd'.
     */
    SOORT_ADMINISTRATIEVE_HANDELING__NAAM(9200, "Soort administratieve handeling - Naam", "SrtAdmHnd.Naam", SOORT_ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'CategorieAdmHnd' van de tabel: 'SrtAdmHnd'.
     */
    SOORT_ADMINISTRATIEVE_HANDELING__CATEGORIE_ADMINISTRATIEVE_HANDELING(9874, "Soort administratieve handeling - Categorie administratieve handeling",
            "SrtAdmHnd.CategorieAdmHnd", SOORT_ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'Module' van de tabel: 'SrtAdmHnd'.
     */
    SOORT_ADMINISTRATIEVE_HANDELING__MODULE(9532, "Soort administratieve handeling - Module", "SrtAdmHnd.Module", SOORT_ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de kolom: 'Alias' van de tabel: 'SrtAdmHnd'.
     */
    SOORT_ADMINISTRATIEVE_HANDELING__ALIAS(11438, "Soort administratieve handeling - Alias", "SrtAdmHnd.Alias", SOORT_ADMINISTRATIEVE_HANDELING),
    /**
     * Representeert de tabel: 'SrtBetr'.
     */
    SOORT_BETROKKENHEID(3846, "Soort betrokkenheid", "SrtBetr", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtBetr'.
     */
    SOORT_BETROKKENHEID__I_D(3851, "Soort betrokkenheid - ID", "SrtBetr.ID", SOORT_BETROKKENHEID),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'SrtBetr'.
     */
    SOORT_BETROKKENHEID__CODE(3852, "Soort betrokkenheid - Code", "SrtBetr.Code", SOORT_BETROKKENHEID),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtBetr'.
     */
    SOORT_BETROKKENHEID__NAAM(3853, "Soort betrokkenheid - Naam", "SrtBetr.Naam", SOORT_BETROKKENHEID),
    /**
     * Representeert de tabel: 'SrtDbObject'.
     */
    SOORT_DATABASE_OBJECT(5646, "Soort database object", "SrtDbObject", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtDbObject'.
     */
    SOORT_DATABASE_OBJECT__I_D(5649, "Soort database object - ID", "SrtDbObject.ID", SOORT_DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtDbObject'.
     */
    SOORT_DATABASE_OBJECT__NAAM(5650, "Soort database object - Naam", "SrtDbObject.Naam", SOORT_DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'SrtDbObject'.
     */
    SOORT_DATABASE_OBJECT__DATUM_AANVANG_GELDIGHEID(5656, "Soort database object - Datum aanvang geldigheid", "SrtDbObject.DatAanvGel",
            SOORT_DATABASE_OBJECT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'SrtDbObject'.
     */
    SOORT_DATABASE_OBJECT__DATUM_EINDE_GELDIGHEID(5657, "Soort database object - Datum einde geldigheid", "SrtDbObject.DatEindeGel", SOORT_DATABASE_OBJECT),
    /**
     * Representeert de tabel: 'SrtDoc'.
     */
    SOORT_DOCUMENT(3149, "Soort document", "SrtDoc", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtDoc'.
     */
    SOORT_DOCUMENT__I_D(3151, "Soort document - ID", "SrtDoc.ID", SOORT_DOCUMENT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtDoc'.
     */
    SOORT_DOCUMENT__NAAM(3154, "Soort document - Naam", "SrtDoc.Naam", SOORT_DOCUMENT),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtDoc'.
     */
    SOORT_DOCUMENT__OMSCHRIJVING(9443, "Soort document - Omschrijving", "SrtDoc.Oms", SOORT_DOCUMENT),
    /**
     * Representeert de kolom: 'Rangorde' van de tabel: 'SrtDoc'.
     */
    SOORT_DOCUMENT__RANGORDE(9444, "Soort document - Rangorde", "SrtDoc.Rangorde", SOORT_DOCUMENT),
    /**
     * Representeert de tabel: 'SrtElement'.
     */
    SOORT_ELEMENT(3716, "Soort element", "SrtElement", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtElement'.
     */
    SOORT_ELEMENT__I_D(3717, "Soort element - ID", "SrtElement.ID", SOORT_ELEMENT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtElement'.
     */
    SOORT_ELEMENT__NAAM(3718, "Soort element - Naam", "SrtElement.Naam", SOORT_ELEMENT),
    /**
     * Representeert de tabel: 'SrtElementAutorisatie'.
     */
    SOORT_ELEMENT_AUTORISATIE(13515, "Soort element autorisatie", "SrtElementAutorisatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtElementAutorisatie'.
     */
    SOORT_ELEMENT_AUTORISATIE__I_D(13518, "Soort element autorisatie - ID", "SrtElementAutorisatie.ID", SOORT_ELEMENT_AUTORISATIE),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtElementAutorisatie'.
     */
    SOORT_ELEMENT_AUTORISATIE__NAAM(13519, "Soort element autorisatie - Naam", "SrtElementAutorisatie.Naam", SOORT_ELEMENT_AUTORISATIE),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtElementAutorisatie'.
     */
    SOORT_ELEMENT_AUTORISATIE__OMSCHRIJVING(14007, "Soort element autorisatie - Omschrijving", "SrtElementAutorisatie.Oms", SOORT_ELEMENT_AUTORISATIE),
    /**
     * Representeert de tabel: 'SrtIndicatie'.
     */
    SOORT_INDICATIE(3582, "Soort indicatie", "SrtIndicatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtIndicatie'.
     */
    SOORT_INDICATIE__I_D(3588, "Soort indicatie - ID", "SrtIndicatie.ID", SOORT_INDICATIE),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtIndicatie'.
     */
    SOORT_INDICATIE__NAAM(3590, "Soort indicatie - Naam", "SrtIndicatie.Naam", SOORT_INDICATIE),
    /**
     * Representeert de kolom: 'IndMaterieleHistorieVanToepa' van de tabel: 'SrtIndicatie'.
     */
    SOORT_INDICATIE__INDICATIE_MATERIELE_HISTORIE_VAN_TOEPASSING(2014, "Soort indicatie - Materiele historie van toepassing?",
            "SrtIndicatie.IndMaterieleHistorieVanToepa", SOORT_INDICATIE),
    /**
     * Representeert de tabel: 'SrtMigratie'.
     */
    SOORT_MIGRATIE(10872, "Soort migratie", "SrtMigratie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtMigratie'.
     */
    SOORT_MIGRATIE__I_D(10875, "Soort migratie - ID", "SrtMigratie.ID", SOORT_MIGRATIE),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'SrtMigratie'.
     */
    SOORT_MIGRATIE__CODE(11182, "Soort migratie - Code", "SrtMigratie.Code", SOORT_MIGRATIE),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtMigratie'.
     */
    SOORT_MIGRATIE__NAAM(10877, "Soort migratie - Naam", "SrtMigratie.Naam", SOORT_MIGRATIE),
    /**
     * Representeert de tabel: 'SrtMultirealiteitRegel'.
     */
    SOORT_MULTIREALITEIT_REGEL(2057, "Soort multirealiteit regel", "SrtMultirealiteitRegel", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtMultirealiteitRegel'.
     */
    SOORT_MULTIREALITEIT_REGEL__I_D(2059, "Soort multirealiteit regel - ID", "SrtMultirealiteitRegel.ID", SOORT_MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtMultirealiteitRegel'.
     */
    SOORT_MULTIREALITEIT_REGEL__NAAM(2060, "Soort multirealiteit regel - Naam", "SrtMultirealiteitRegel.Naam", SOORT_MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtMultirealiteitRegel'.
     */
    SOORT_MULTIREALITEIT_REGEL__OMSCHRIJVING(5662, "Soort multirealiteit regel - Omschrijving", "SrtMultirealiteitRegel.Oms", SOORT_MULTIREALITEIT_REGEL),
    /**
     * Representeert de tabel: 'SrtPartij'.
     */
    SOORT_PARTIJ(2171, "Soort partij", "SrtPartij", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtPartij'.
     */
    SOORT_PARTIJ__I_D(2173, "Soort partij - ID", "SrtPartij.ID", SOORT_PARTIJ),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtPartij'.
     */
    SOORT_PARTIJ__NAAM(2176, "Soort partij - Naam", "SrtPartij.Naam", SOORT_PARTIJ),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'SrtPartij'.
     */
    SOORT_PARTIJ__DATUM_AANVANG_GELDIGHEID(4009, "Soort partij - Datum aanvang geldigheid", "SrtPartij.DatAanvGel", SOORT_PARTIJ),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'SrtPartij'.
     */
    SOORT_PARTIJ__DATUM_EINDE_GELDIGHEID(4010, "Soort partij - Datum einde geldigheid", "SrtPartij.DatEindeGel", SOORT_PARTIJ),
    /**
     * Representeert de tabel: 'SrtPartijOnderzoek'.
     */
    SOORT_PARTIJ_ONDERZOEK(10784, "Soort partij \\ onderzoek", "SrtPartijOnderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtPartijOnderzoek'.
     */
    SOORT_PARTIJ_ONDERZOEK__I_D(10789, "Soort partij \\ onderzoek - ID", "SrtPartijOnderzoek.ID", SOORT_PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtPartijOnderzoek'.
     */
    SOORT_PARTIJ_ONDERZOEK__NAAM(10790, "Soort partij \\ onderzoek - Naam", "SrtPartijOnderzoek.Naam", SOORT_PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtPartijOnderzoek'.
     */
    SOORT_PARTIJ_ONDERZOEK__OMSCHRIJVING(10791, "Soort partij \\ onderzoek - Omschrijving", "SrtPartijOnderzoek.Oms", SOORT_PARTIJ_ONDERZOEK),
    /**
     * Representeert de tabel: 'SrtPers'.
     */
    SOORT_PERSOON(1987, "Soort persoon", "SrtPers", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtPers'.
     */
    SOORT_PERSOON__I_D(1990, "Soort persoon - ID", "SrtPers.ID", SOORT_PERSOON),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'SrtPers'.
     */
    SOORT_PERSOON__CODE(1992, "Soort persoon - Code", "SrtPers.Code", SOORT_PERSOON),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtPers'.
     */
    SOORT_PERSOON__NAAM(1993, "Soort persoon - Naam", "SrtPers.Naam", SOORT_PERSOON),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtPers'.
     */
    SOORT_PERSOON__OMSCHRIJVING(2002, "Soort persoon - Omschrijving", "SrtPers.Oms", SOORT_PERSOON),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'SrtPers'.
     */
    SOORT_PERSOON__DATUM_AANVANG_GELDIGHEID(4012, "Soort persoon - Datum aanvang geldigheid", "SrtPers.DatAanvGel", SOORT_PERSOON),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'SrtPers'.
     */
    SOORT_PERSOON__DATUM_EINDE_GELDIGHEID(4013, "Soort persoon - Datum einde geldigheid", "SrtPers.DatEindeGel", SOORT_PERSOON),
    /**
     * Representeert de tabel: 'SrtPersOnderzoek'.
     */
    SOORT_PERSOON_ONDERZOEK(10764, "Soort persoon \\ onderzoek", "SrtPersOnderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtPersOnderzoek'.
     */
    SOORT_PERSOON_ONDERZOEK__I_D(10767, "Soort persoon \\ onderzoek - ID", "SrtPersOnderzoek.ID", SOORT_PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtPersOnderzoek'.
     */
    SOORT_PERSOON_ONDERZOEK__NAAM(10769, "Soort persoon \\ onderzoek - Naam", "SrtPersOnderzoek.Naam", SOORT_PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtPersOnderzoek'.
     */
    SOORT_PERSOON_ONDERZOEK__OMSCHRIJVING(10774, "Soort persoon \\ onderzoek - Omschrijving", "SrtPersOnderzoek.Oms", SOORT_PERSOON_ONDERZOEK),
    /**
     * Representeert de tabel: 'SrtRechtsgrond'.
     */
    SOORT_RECHTSGROND(8132, "Soort rechtsgrond", "SrtRechtsgrond", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtRechtsgrond'.
     */
    SOORT_RECHTSGROND__I_D(8135, "Soort rechtsgrond - ID", "SrtRechtsgrond.ID", SOORT_RECHTSGROND),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtRechtsgrond'.
     */
    SOORT_RECHTSGROND__NAAM(8136, "Soort rechtsgrond - Naam", "SrtRechtsgrond.Naam", SOORT_RECHTSGROND),
    /**
     * Representeert de tabel: 'SrtRelatie'.
     */
    SOORT_RELATIE(3191, "Soort relatie", "SrtRelatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtRelatie'.
     */
    SOORT_RELATIE__I_D(3193, "Soort relatie - ID", "SrtRelatie.ID", SOORT_RELATIE),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'SrtRelatie'.
     */
    SOORT_RELATIE__CODE(3492, "Soort relatie - Code", "SrtRelatie.Code", SOORT_RELATIE),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtRelatie'.
     */
    SOORT_RELATIE__NAAM(3195, "Soort relatie - Naam", "SrtRelatie.Naam", SOORT_RELATIE),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtRelatie'.
     */
    SOORT_RELATIE__OMSCHRIJVING(5620, "Soort relatie - Omschrijving", "SrtRelatie.Oms", SOORT_RELATIE),
    /**
     * Representeert de tabel: 'StatusOnderzoek'.
     */
    STATUS_ONDERZOEK(10853, "Status onderzoek", "StatusOnderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'StatusOnderzoek'.
     */
    STATUS_ONDERZOEK__I_D(10856, "Status onderzoek - ID", "StatusOnderzoek.ID", STATUS_ONDERZOEK),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'StatusOnderzoek'.
     */
    STATUS_ONDERZOEK__NAAM(10858, "Status onderzoek - Naam", "StatusOnderzoek.Naam", STATUS_ONDERZOEK),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'StatusOnderzoek'.
     */
    STATUS_ONDERZOEK__OMSCHRIJVING(10859, "Status onderzoek - Omschrijving", "StatusOnderzoek.Oms", STATUS_ONDERZOEK),
    /**
     * Representeert de tabel: 'StatusTerugmelding'.
     */
    STATUS_TERUGMELDING(10742, "Status terugmelding", "StatusTerugmelding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'StatusTerugmelding'.
     */
    STATUS_TERUGMELDING__I_D(10745, "Status terugmelding - ID", "StatusTerugmelding.ID", STATUS_TERUGMELDING),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'StatusTerugmelding'.
     */
    STATUS_TERUGMELDING__NAAM(10747, "Status terugmelding - Naam", "StatusTerugmelding.Naam", STATUS_TERUGMELDING),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'StatusTerugmelding'.
     */
    STATUS_TERUGMELDING__OMSCHRIJVING(10748, "Status terugmelding - Omschrijving", "StatusTerugmelding.Oms", STATUS_TERUGMELDING),
    /**
     * Representeert de tabel: 'Terugmelding'.
     */
    TERUGMELDING(10716, "Terugmelding", "Terugmelding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__I_D(10719, "Terugmelding - ID", "Terugmelding.ID", TERUGMELDING),
    /**
     * Representeert de kolom: 'TerugmeldendePartij' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__TERUGMELDENDE_PARTIJ(10736, "Terugmelding - Terugmeldende partij", "Terugmelding.TerugmeldendePartij", TERUGMELDING),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__PERSOON(10737, "Terugmelding - Persoon", "Terugmelding.Pers", TERUGMELDING),
    /**
     * Representeert de kolom: 'Bijhgem' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__BIJHOUDINGSGEMEENTE(10738, "Terugmelding - Bijhoudingsgemeente", "Terugmelding.Bijhgem", TERUGMELDING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__TIJDSTIP_REGISTRATIE(10839, "Terugmelding - Tijdstip registratie", "Terugmelding.TsReg", TERUGMELDING),
    /**
     * Representeert de kolom: 'Onderzoek' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__ONDERZOEK(10740, "Terugmelding - Onderzoek", "Terugmelding.Onderzoek", TERUGMELDING),
    /**
     * Representeert de kolom: 'Status' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__STATUS(10752, "Terugmelding - Status", "Terugmelding.Status", TERUGMELDING),
    /**
     * Representeert de kolom: 'Toelichting' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__TOELICHTING(11092, "Terugmelding - Toelichting", "Terugmelding.Toelichting", TERUGMELDING),
    /**
     * Representeert de kolom: 'KenmerkMeldendePartij' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__KENMERK_MELDENDE_PARTIJ(10741, "Terugmelding - Kenmerk meldende partij", "Terugmelding.KenmerkMeldendePartij", TERUGMELDING),
    /**
     * Representeert de kolom: 'Email' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__EMAIL(11096, "Terugmelding - Email", "Terugmelding.Email", TERUGMELDING),
    /**
     * Representeert de kolom: 'Telefoonnr' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__TELEFOONNUMMER(11097, "Terugmelding - Telefoonnummer", "Terugmelding.Telefoonnr", TERUGMELDING),
    /**
     * Representeert de kolom: 'Voornamen' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__VOORNAMEN(11101, "Terugmelding - Voornamen", "Terugmelding.Voornamen", TERUGMELDING),
    /**
     * Representeert de kolom: 'Voorvoegsel' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__VOORVOEGSEL(11102, "Terugmelding - Voorvoegsel", "Terugmelding.Voorvoegsel", TERUGMELDING),
    /**
     * Representeert de kolom: 'Scheidingsteken' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__SCHEIDINGSTEKEN(11103, "Terugmelding - Scheidingsteken", "Terugmelding.Scheidingsteken", TERUGMELDING),
    /**
     * Representeert de kolom: 'Geslnaamstam' van de tabel: 'Terugmelding'.
     */
    TERUGMELDING__GESLACHTSNAAMSTAM(11104, "Terugmelding - Geslachtsnaamstam", "Terugmelding.Geslnaamstam", TERUGMELDING),
    /**
     * Representeert de tabel: 'Voorvoegsel'.
     */
    VOORVOEGSEL(9262, "Voorvoegsel", "Voorvoegsel", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Voorvoegsel'.
     */
    VOORVOEGSEL__I_D(9265, "Voorvoegsel - ID", "Voorvoegsel.ID", VOORVOEGSEL),
    /**
     * Representeert de kolom: 'Voorvoegsel' van de tabel: 'Voorvoegsel'.
     */
    VOORVOEGSEL__VOORVOEGSEL(9268, "Voorvoegsel - Voorvoegsel", "Voorvoegsel.Voorvoegsel", VOORVOEGSEL),
    /**
     * Representeert de kolom: 'Scheidingsteken' van de tabel: 'Voorvoegsel'.
     */
    VOORVOEGSEL__SCHEIDINGSTEKEN(9269, "Voorvoegsel - Scheidingsteken", "Voorvoegsel.Scheidingsteken", VOORVOEGSEL),
    /**
     * Representeert de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP(3858, "His Ouder Ouderschap", "His_OuderOuderschap", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__I_D(4509, "His Ouder Ouderschap - ID", "His_OuderOuderschap.ID", HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'Betr' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__BETROKKENHEID(4025, "His Ouder Ouderschap - Betrokkenheid", "His_OuderOuderschap.Betr", HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__DATUM_AANVANG_GELDIGHEID(6089, "His Ouder Ouderschap - Datum aanvang geldigheid", "His_OuderOuderschap.DatAanvGel",
            HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__DATUM_EINDE_GELDIGHEID(6090, "His Ouder Ouderschap - Datum einde geldigheid", "His_OuderOuderschap.DatEindeGel",
            HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__DATUM_TIJD_REGISTRATIE(4026, "His Ouder Ouderschap - Datum/tijd registratie", "His_OuderOuderschap.TsReg",
            HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__DATUM_TIJD_VERVAL(4027, "His Ouder Ouderschap - Datum/tijd verval", "His_OuderOuderschap.TsVerval", HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__NADERE_AANDUIDING_VERVAL(11113, "His Ouder Ouderschap - Nadere aanduiding verval", "His_OuderOuderschap.NadereAandVerval",
            HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__B_R_P_ACTIE_INHOUD(4028, "His Ouder Ouderschap - BRP Actie inhoud", "His_OuderOuderschap.ActieInh", HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__B_R_P_ACTIE_VERVAL(4029, "His Ouder Ouderschap - BRP Actie verval", "His_OuderOuderschap.ActieVerval", HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__B_R_P_ACTIE_AANPASSING_GELDIGHEID(6091, "His Ouder Ouderschap - BRP Actie Aanpassing Geldigheid",
            "His_OuderOuderschap.ActieAanpGel", HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'IndOuder' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__INDICATIE_OUDER(9691, "His Ouder Ouderschap - Ouder?", "His_OuderOuderschap.IndOuder", HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de kolom: 'IndAdresgevendeOuder' van de tabel: 'His_OuderOuderschap'.
     */
    HIS__OUDER_OUDERSCHAP__INDICATIE_ADRESGEVENDE_OUDER(6260, "His Ouder Ouderschap - Adresgevende ouder?", "His_OuderOuderschap.IndAdresgevendeOuder",
            HIS__OUDER_OUDERSCHAP),
    /**
     * Representeert de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG(3211, "His Ouder Ouderlijk gezag", "His_OuderOuderlijkGezag", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__I_D(4512, "His Ouder Ouderlijk gezag - ID", "His_OuderOuderlijkGezag.ID", HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'Betr' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__BETROKKENHEID(4033, "His Ouder Ouderlijk gezag - Betrokkenheid", "His_OuderOuderlijkGezag.Betr",
            HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__DATUM_AANVANG_GELDIGHEID(4034, "His Ouder Ouderlijk gezag - Datum aanvang geldigheid",
            "His_OuderOuderlijkGezag.DatAanvGel", HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__DATUM_EINDE_GELDIGHEID(4035, "His Ouder Ouderlijk gezag - Datum einde geldigheid", "His_OuderOuderlijkGezag.DatEindeGel",
            HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__DATUM_TIJD_REGISTRATIE(4036, "His Ouder Ouderlijk gezag - Datum/tijd registratie", "His_OuderOuderlijkGezag.TsReg",
            HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__DATUM_TIJD_VERVAL(4037, "His Ouder Ouderlijk gezag - Datum/tijd verval", "His_OuderOuderlijkGezag.TsVerval",
            HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__NADERE_AANDUIDING_VERVAL(11114, "His Ouder Ouderlijk gezag - Nadere aanduiding verval",
            "His_OuderOuderlijkGezag.NadereAandVerval", HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__B_R_P_ACTIE_INHOUD(4038, "His Ouder Ouderlijk gezag - BRP Actie inhoud", "His_OuderOuderlijkGezag.ActieInh",
            HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__B_R_P_ACTIE_VERVAL(4039, "His Ouder Ouderlijk gezag - BRP Actie verval", "His_OuderOuderlijkGezag.ActieVerval",
            HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4040, "His Ouder Ouderlijk gezag - BRP Actie Aanpassing Geldigheid",
            "His_OuderOuderlijkGezag.ActieAanpGel", HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de kolom: 'IndOuderHeeftGezag' van de tabel: 'His_OuderOuderlijkGezag'.
     */
    HIS__OUDER_OUDERLIJK_GEZAG__INDICATIE_OUDER_HEEFT_GEZAG(9692, "His Ouder Ouderlijk gezag - Ouder heeft gezag?",
            "His_OuderOuderlijkGezag.IndOuderHeeftGezag", HIS__OUDER_OUDERLIJK_GEZAG),
    /**
     * Representeert de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT(3784, "His Document", "His_Doc", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__I_D(4515, "His Document - ID", "His_Doc.ID", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'Doc' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__DOCUMENT(4043, "His Document - Document", "His_Doc.Doc", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__DATUM_TIJD_REGISTRATIE(4044, "His Document - Datum/tijd registratie", "His_Doc.TsReg", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__DATUM_TIJD_VERVAL(4045, "His Document - Datum/tijd verval", "His_Doc.TsVerval", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__NADERE_AANDUIDING_VERVAL(11118, "His Document - Nadere aanduiding verval", "His_Doc.NadereAandVerval", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__B_R_P_ACTIE_INHOUD(4046, "His Document - BRP Actie inhoud", "His_Doc.ActieInh", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__B_R_P_ACTIE_VERVAL(4047, "His Document - BRP Actie verval", "His_Doc.ActieVerval", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'Ident' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__IDENTIFICATIE(9701, "His Document - Identificatie", "His_Doc.Ident", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'Aktenr' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__AKTENUMMER(9702, "His Document - Aktenummer", "His_Doc.Aktenr", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__OMSCHRIJVING(9703, "His Document - Omschrijving", "His_Doc.Oms", HIS__DOCUMENT),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'His_Doc'.
     */
    HIS__DOCUMENT__PARTIJ(9704, "His Document - Partij", "His_Doc.Partij", HIS__DOCUMENT),
    /**
     * Representeert de tabel: 'His_MultirealiteitRegel'.
     */
    HIS__MULTIREALITEIT_REGEL(2080, "His Multirealiteit regel", "His_MultirealiteitRegel", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_MultirealiteitRegel'.
     */
    HIS__MULTIREALITEIT_REGEL__I_D(5479, "His Multirealiteit regel - ID", "His_MultirealiteitRegel.ID", HIS__MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'MultirealiteitRegel' van de tabel: 'His_MultirealiteitRegel'.
     */
    HIS__MULTIREALITEIT_REGEL__MULTIREALITEIT_REGEL(14002, "His Multirealiteit regel - Multirealiteit regel",
            "His_MultirealiteitRegel.MultirealiteitRegel", HIS__MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_MultirealiteitRegel'.
     */
    HIS__MULTIREALITEIT_REGEL__DATUM_TIJD_REGISTRATIE(5473, "His Multirealiteit regel - Datum/tijd registratie", "His_MultirealiteitRegel.TsReg",
            HIS__MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_MultirealiteitRegel'.
     */
    HIS__MULTIREALITEIT_REGEL__DATUM_TIJD_VERVAL(5474, "His Multirealiteit regel - Datum/tijd verval", "His_MultirealiteitRegel.TsVerval",
            HIS__MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_MultirealiteitRegel'.
     */
    HIS__MULTIREALITEIT_REGEL__NADERE_AANDUIDING_VERVAL(11120, "His Multirealiteit regel - Nadere aanduiding verval",
            "His_MultirealiteitRegel.NadereAandVerval", HIS__MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_MultirealiteitRegel'.
     */
    HIS__MULTIREALITEIT_REGEL__B_R_P_ACTIE_INHOUD(5475, "His Multirealiteit regel - BRP Actie inhoud", "His_MultirealiteitRegel.ActieInh",
            HIS__MULTIREALITEIT_REGEL),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_MultirealiteitRegel'.
     */
    HIS__MULTIREALITEIT_REGEL__B_R_P_ACTIE_VERVAL(5476, "His Multirealiteit regel - BRP Actie verval", "His_MultirealiteitRegel.ActieVerval",
            HIS__MULTIREALITEIT_REGEL),
    /**
     * Representeert de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK(3774, "His Onderzoek", "His_Onderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__I_D(4521, "His Onderzoek - ID", "His_Onderzoek.ID", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'Onderzoek' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__ONDERZOEK(4068, "His Onderzoek - Onderzoek", "His_Onderzoek.Onderzoek", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__DATUM_TIJD_REGISTRATIE(4069, "His Onderzoek - Datum/tijd registratie", "His_Onderzoek.TsReg", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__DATUM_TIJD_VERVAL(4070, "His Onderzoek - Datum/tijd verval", "His_Onderzoek.TsVerval", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__NADERE_AANDUIDING_VERVAL(11121, "His Onderzoek - Nadere aanduiding verval", "His_Onderzoek.NadereAandVerval", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__B_R_P_ACTIE_INHOUD(4071, "His Onderzoek - BRP Actie inhoud", "His_Onderzoek.ActieInh", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__B_R_P_ACTIE_VERVAL(4072, "His Onderzoek - BRP Actie verval", "His_Onderzoek.ActieVerval", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'DatAanv' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__DATUM_AANVANG(9709, "His Onderzoek - Datum aanvang", "His_Onderzoek.DatAanv", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'VerwachteAfhandeldat' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__VERWACHTE_AFHANDELDATUM(10921, "His Onderzoek - Verwachte afhandeldatum", "His_Onderzoek.VerwachteAfhandeldat", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__DATUM_EINDE(9710, "His Onderzoek - Datum einde", "His_Onderzoek.DatEinde", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__OMSCHRIJVING(9711, "His Onderzoek - Omschrijving", "His_Onderzoek.Oms", HIS__ONDERZOEK),
    /**
     * Representeert de kolom: 'Status' van de tabel: 'His_Onderzoek'.
     */
    HIS__ONDERZOEK__STATUS(10922, "His Onderzoek - Status", "His_Onderzoek.Status", HIS__ONDERZOEK),
    /**
     * Representeert de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF(10841, "His Onderzoek Afgeleid administratief", "His_OnderzoekAfgeleidAdminis", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF__I_D(10973, "His Onderzoek Afgeleid administratief - ID", "His_OnderzoekAfgeleidAdminis.ID",
            HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'Onderzoek' van de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF__ONDERZOEK(10923, "His Onderzoek Afgeleid administratief - Onderzoek",
            "His_OnderzoekAfgeleidAdminis.Onderzoek", HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF__DATUM_TIJD_REGISTRATIE(10924, "His Onderzoek Afgeleid administratief - Datum/tijd registratie",
            "His_OnderzoekAfgeleidAdminis.TsReg", HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF__DATUM_TIJD_VERVAL(10925, "His Onderzoek Afgeleid administratief - Datum/tijd verval",
            "His_OnderzoekAfgeleidAdminis.TsVerval", HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF__NADERE_AANDUIDING_VERVAL(11122, "His Onderzoek Afgeleid administratief - Nadere aanduiding verval",
            "His_OnderzoekAfgeleidAdminis.NadereAandVerval", HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF__B_R_P_ACTIE_INHOUD(10926, "His Onderzoek Afgeleid administratief - BRP Actie inhoud",
            "His_OnderzoekAfgeleidAdminis.ActieInh", HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF__B_R_P_ACTIE_VERVAL(10927, "His Onderzoek Afgeleid administratief - BRP Actie verval",
            "His_OnderzoekAfgeleidAdminis.ActieVerval", HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'AdmHnd' van de tabel: 'His_OnderzoekAfgeleidAdminis'.
     */
    HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF__ADMINISTRATIEVE_HANDELING(10928, "His Onderzoek Afgeleid administratief - Administratieve handeling",
            "His_OnderzoekAfgeleidAdminis.AdmHnd", HIS__ONDERZOEK_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de tabel: 'His_Partij'.
     */
    HIS__PARTIJ(4618, "His Partij", "His_Partij", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__I_D(4630, "His Partij - ID", "His_Partij.ID", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__PARTIJ(4620, "His Partij - Partij", "His_Partij.Partij", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__DATUM_TIJD_REGISTRATIE(4621, "His Partij - Datum/tijd registratie", "His_Partij.TsReg", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__DATUM_TIJD_VERVAL(4622, "His Partij - Datum/tijd verval", "His_Partij.TsVerval", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__NADERE_AANDUIDING_VERVAL(11123, "His Partij - Nadere aanduiding verval", "His_Partij.NadereAandVerval", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__B_R_P_ACTIE_INHOUD(4623, "His Partij - BRP Actie inhoud", "His_Partij.ActieInh", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__B_R_P_ACTIE_VERVAL(4624, "His Partij - BRP Actie verval", "His_Partij.ActieVerval", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'DatAanv' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__DATUM_AANVANG(9713, "His Partij - Datum aanvang", "His_Partij.DatAanv", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__DATUM_EINDE(9712, "His Partij - Datum einde", "His_Partij.DatEinde", HIS__PARTIJ),
    /**
     * Representeert de kolom: 'IndVerstrbeperkingMogelijk' van de tabel: 'His_Partij'.
     */
    HIS__PARTIJ__INDICATIE_VERSTREKKINGSBEPERKING_MOGELIJK(9714, "His Partij - Verstrekkingsbeperking mogelijk?", "His_Partij.IndVerstrbeperkingMogelijk",
            HIS__PARTIJ),
    /**
     * Representeert de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK(10785, "His Partij \\ Onderzoek", "His_PartijOnderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK__I_D(10830, "His Partij \\ Onderzoek - ID", "His_PartijOnderzoek.ID", HIS__PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'PartijOnderzoek' van de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK__PARTIJ_ONDERZOEK(10797, "His Partij \\ Onderzoek - Partij \\ Onderzoek", "His_PartijOnderzoek.PartijOnderzoek",
            HIS__PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK__DATUM_TIJD_REGISTRATIE(10798, "His Partij \\ Onderzoek - Datum/tijd registratie", "His_PartijOnderzoek.TsReg",
            HIS__PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK__DATUM_TIJD_VERVAL(10799, "His Partij \\ Onderzoek - Datum/tijd verval", "His_PartijOnderzoek.TsVerval", HIS__PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK__NADERE_AANDUIDING_VERVAL(11125, "His Partij \\ Onderzoek - Nadere aanduiding verval", "His_PartijOnderzoek.NadereAandVerval",
            HIS__PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK__B_R_P_ACTIE_INHOUD(10800, "His Partij \\ Onderzoek - BRP Actie inhoud", "His_PartijOnderzoek.ActieInh", HIS__PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK__B_R_P_ACTIE_VERVAL(10801, "His Partij \\ Onderzoek - BRP Actie verval", "His_PartijOnderzoek.ActieVerval",
            HIS__PARTIJ_ONDERZOEK),
    /**
     * Representeert de kolom: 'Rol' van de tabel: 'His_PartijOnderzoek'.
     */
    HIS__PARTIJ_ONDERZOEK__ROL(10802, "His Partij \\ Onderzoek - Rol", "His_PartijOnderzoek.Rol", HIS__PARTIJ_ONDERZOEK),
    /**
     * Representeert de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL(2182, "His Partij \\ Rol", "His_PartijRol", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__I_D(4792, "His Partij \\ Rol - ID", "His_PartijRol.ID", HIS__PARTIJ_ROL),
    /**
     * Representeert de kolom: 'PartijRol' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__PARTIJ_ROL(4783, "His Partij \\ Rol - Partij \\ Rol", "His_PartijRol.PartijRol", HIS__PARTIJ_ROL),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__DATUM_AANVANG_GELDIGHEID(4784, "His Partij \\ Rol - Datum aanvang geldigheid", "His_PartijRol.DatAanvGel", HIS__PARTIJ_ROL),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__DATUM_EINDE_GELDIGHEID(4785, "His Partij \\ Rol - Datum einde geldigheid", "His_PartijRol.DatEindeGel", HIS__PARTIJ_ROL),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__DATUM_TIJD_REGISTRATIE(4786, "His Partij \\ Rol - Datum/tijd registratie", "His_PartijRol.TsReg", HIS__PARTIJ_ROL),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__DATUM_TIJD_VERVAL(4787, "His Partij \\ Rol - Datum/tijd verval", "His_PartijRol.TsVerval", HIS__PARTIJ_ROL),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__NADERE_AANDUIDING_VERVAL(11380, "His Partij \\ Rol - Nadere aanduiding verval", "His_PartijRol.NadereAandVerval", HIS__PARTIJ_ROL),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__B_R_P_ACTIE_INHOUD(4788, "His Partij \\ Rol - BRP Actie inhoud", "His_PartijRol.ActieInh", HIS__PARTIJ_ROL),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PartijRol'.
     */
    HIS__PARTIJ_ROL__B_R_P_ACTIE_VERVAL(4789, "His Partij \\ Rol - BRP Actie verval", "His_PartijRol.ActieVerval", HIS__PARTIJ_ROL),
    /**
     * Representeert de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF(3909, "His Persoon Afgeleid administratief", "His_PersAfgeleidAdministrati", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__I_D(10169, "His Persoon Afgeleid administratief - ID", "His_PersAfgeleidAdministrati.ID",
            HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__PERSOON(10148, "His Persoon Afgeleid administratief - Persoon", "His_PersAfgeleidAdministrati.Pers",
            HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__DATUM_TIJD_REGISTRATIE(10149, "His Persoon Afgeleid administratief - Datum/tijd registratie",
            "His_PersAfgeleidAdministrati.TsReg", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__DATUM_TIJD_VERVAL(10150, "His Persoon Afgeleid administratief - Datum/tijd verval",
            "His_PersAfgeleidAdministrati.TsVerval", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__NADERE_AANDUIDING_VERVAL(11126, "His Persoon Afgeleid administratief - Nadere aanduiding verval",
            "His_PersAfgeleidAdministrati.NadereAandVerval", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__B_R_P_ACTIE_INHOUD(10151, "His Persoon Afgeleid administratief - BRP Actie inhoud",
            "His_PersAfgeleidAdministrati.ActieInh", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__B_R_P_ACTIE_VERVAL(10152, "His Persoon Afgeleid administratief - BRP Actie verval",
            "His_PersAfgeleidAdministrati.ActieVerval", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'AdmHnd' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__ADMINISTRATIEVE_HANDELING(10153, "His Persoon Afgeleid administratief - Administratieve handeling",
            "His_PersAfgeleidAdministrati.AdmHnd", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'TsLaatsteWijz' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__TIJDSTIP_LAATSTE_WIJZIGING(10154, "His Persoon Afgeleid administratief - Tijdstip laatste wijziging",
            "His_PersAfgeleidAdministrati.TsLaatsteWijz", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'IndOnderzoekNaarNietOpgenome' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__INDICATIE_ONDERZOEK_NAAR_NIET_OPGENOMEN_GEGEVENS(10930,
            "His Persoon Afgeleid administratief - Onderzoek naar niet opgenomen gegevens?", "His_PersAfgeleidAdministrati.IndOnderzoekNaarNietOpgenome",
            HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'Sorteervolgorde' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__SORTEERVOLGORDE(10406, "His Persoon Afgeleid administratief - Sorteervolgorde",
            "His_PersAfgeleidAdministrati.Sorteervolgorde", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'IndOnverwBijhvoorstelNietIng' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__INDICATIE_ONVERWERKT_BIJHOUDINGSVOORSTEL_NIET_INGEZETENE_AANWEZIG(10931,
            "His Persoon Afgeleid administratief - Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig?",
            "His_PersAfgeleidAdministrati.IndOnverwBijhvoorstelNietIng", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'TsLaatsteWijzGBASystematiek' van de tabel: 'His_PersAfgeleidAdministrati'.
     */
    HIS__PERSOON_AFGELEID_ADMINISTRATIEF__TIJDSTIP_LAATSTE_WIJZIGING_G_B_A_SYSTEMATIEK(10932,
            "His Persoon Afgeleid administratief - Tijdstip laatste wijziging GBA-systematiek",
            "His_PersAfgeleidAdministrati.TsLaatsteWijzGBASystematiek", HIS__PERSOON_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS(3344, "His Persoon Identificatienummers", "His_PersIDs", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__I_D(4524, "His Persoon Identificatienummers - ID", "His_PersIDs.ID", HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__PERSOON(4077, "His Persoon Identificatienummers - Persoon", "His_PersIDs.Pers", HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__DATUM_AANVANG_GELDIGHEID(4078, "His Persoon Identificatienummers - Datum aanvang geldigheid",
            "His_PersIDs.DatAanvGel", HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__DATUM_EINDE_GELDIGHEID(4079, "His Persoon Identificatienummers - Datum einde geldigheid",
            "His_PersIDs.DatEindeGel", HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__DATUM_TIJD_REGISTRATIE(4080, "His Persoon Identificatienummers - Datum/tijd registratie", "His_PersIDs.TsReg",
            HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__DATUM_TIJD_VERVAL(4081, "His Persoon Identificatienummers - Datum/tijd verval", "His_PersIDs.TsVerval",
            HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__NADERE_AANDUIDING_VERVAL(11127, "His Persoon Identificatienummers - Nadere aanduiding verval",
            "His_PersIDs.NadereAandVerval", HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__B_R_P_ACTIE_INHOUD(4082, "His Persoon Identificatienummers - BRP Actie inhoud", "His_PersIDs.ActieInh",
            HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__B_R_P_ACTIE_VERVAL(4083, "His Persoon Identificatienummers - BRP Actie verval", "His_PersIDs.ActieVerval",
            HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4084, "His Persoon Identificatienummers - BRP Actie Aanpassing Geldigheid",
            "His_PersIDs.ActieAanpGel", HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'BSN' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__BURGERSERVICENUMMER(9715, "His Persoon Identificatienummers - Burgerservicenummer", "His_PersIDs.BSN",
            HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de kolom: 'ANr' van de tabel: 'His_PersIDs'.
     */
    HIS__PERSOON_IDENTIFICATIENUMMERS__ADMINISTRATIENUMMER(9716, "His Persoon Identificatienummers - Administratienummer", "His_PersIDs.ANr",
            HIS__PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Representeert de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM(3557, "His Persoon Samengestelde naam", "His_PersSamengesteldeNaam", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__I_D(4530, "His Persoon Samengestelde naam - ID", "His_PersSamengesteldeNaam.ID", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__PERSOON(4098, "His Persoon Samengestelde naam - Persoon", "His_PersSamengesteldeNaam.Pers",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__DATUM_AANVANG_GELDIGHEID(4099, "His Persoon Samengestelde naam - Datum aanvang geldigheid",
            "His_PersSamengesteldeNaam.DatAanvGel", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__DATUM_EINDE_GELDIGHEID(4100, "His Persoon Samengestelde naam - Datum einde geldigheid",
            "His_PersSamengesteldeNaam.DatEindeGel", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__DATUM_TIJD_REGISTRATIE(4101, "His Persoon Samengestelde naam - Datum/tijd registratie",
            "His_PersSamengesteldeNaam.TsReg", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__DATUM_TIJD_VERVAL(4102, "His Persoon Samengestelde naam - Datum/tijd verval", "His_PersSamengesteldeNaam.TsVerval",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__NADERE_AANDUIDING_VERVAL(11128, "His Persoon Samengestelde naam - Nadere aanduiding verval",
            "His_PersSamengesteldeNaam.NadereAandVerval", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__B_R_P_ACTIE_INHOUD(4103, "His Persoon Samengestelde naam - BRP Actie inhoud", "His_PersSamengesteldeNaam.ActieInh",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__B_R_P_ACTIE_VERVAL(4104, "His Persoon Samengestelde naam - BRP Actie verval",
            "His_PersSamengesteldeNaam.ActieVerval", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4105, "His Persoon Samengestelde naam - BRP Actie Aanpassing Geldigheid",
            "His_PersSamengesteldeNaam.ActieAanpGel", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'IndAfgeleid' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__INDICATIE_AFGELEID(9717, "His Persoon Samengestelde naam - Afgeleid?", "His_PersSamengesteldeNaam.IndAfgeleid",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'IndNreeks' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__INDICATIE_NAMENREEKS(9718, "His Persoon Samengestelde naam - Namenreeks?", "His_PersSamengesteldeNaam.IndNreeks",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'Predicaat' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__PREDICAAT(9719, "His Persoon Samengestelde naam - Predicaat", "His_PersSamengesteldeNaam.Predicaat",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'Voornamen' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__VOORNAMEN(9720, "His Persoon Samengestelde naam - Voornamen", "His_PersSamengesteldeNaam.Voornamen",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'AdellijkeTitel' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__ADELLIJKE_TITEL(9721, "His Persoon Samengestelde naam - Adellijke titel", "His_PersSamengesteldeNaam.AdellijkeTitel",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'Voorvoegsel' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__VOORVOEGSEL(9722, "His Persoon Samengestelde naam - Voorvoegsel", "His_PersSamengesteldeNaam.Voorvoegsel",
            HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'Scheidingsteken' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__SCHEIDINGSTEKEN(9723, "His Persoon Samengestelde naam - Scheidingsteken",
            "His_PersSamengesteldeNaam.Scheidingsteken", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de kolom: 'Geslnaamstam' van de tabel: 'His_PersSamengesteldeNaam'.
     */
    HIS__PERSOON_SAMENGESTELDE_NAAM__GESLACHTSNAAMSTAM(9724, "His Persoon Samengestelde naam - Geslachtsnaamstam",
            "His_PersSamengesteldeNaam.Geslnaamstam", HIS__PERSOON_SAMENGESTELDE_NAAM),
    /**
     * Representeert de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE(3514, "His Persoon Geboorte", "His_PersGeboorte", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__I_D(4536, "His Persoon Geboorte - ID", "His_PersGeboorte.ID", HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__PERSOON(4132, "His Persoon Geboorte - Persoon", "His_PersGeboorte.Pers", HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__DATUM_TIJD_REGISTRATIE(4133, "His Persoon Geboorte - Datum/tijd registratie", "His_PersGeboorte.TsReg", HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__DATUM_TIJD_VERVAL(4134, "His Persoon Geboorte - Datum/tijd verval", "His_PersGeboorte.TsVerval", HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__NADERE_AANDUIDING_VERVAL(11129, "His Persoon Geboorte - Nadere aanduiding verval", "His_PersGeboorte.NadereAandVerval",
            HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__B_R_P_ACTIE_INHOUD(4135, "His Persoon Geboorte - BRP Actie inhoud", "His_PersGeboorte.ActieInh", HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__B_R_P_ACTIE_VERVAL(4136, "His Persoon Geboorte - BRP Actie verval", "His_PersGeboorte.ActieVerval", HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'DatGeboorte' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__DATUM_GEBOORTE(9725, "His Persoon Geboorte - Datum geboorte", "His_PersGeboorte.DatGeboorte", HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'GemGeboorte' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__GEMEENTE_GEBOORTE(9726, "His Persoon Geboorte - Gemeente geboorte", "His_PersGeboorte.GemGeboorte", HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'WplnaamGeboorte' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__WOONPLAATSNAAM_GEBOORTE(9727, "His Persoon Geboorte - Woonplaatsnaam geboorte", "His_PersGeboorte.WplnaamGeboorte",
            HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'BLPlaatsGeboorte' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__BUITENLANDSE_PLAATS_GEBOORTE(9728, "His Persoon Geboorte - Buitenlandse plaats geboorte", "His_PersGeboorte.BLPlaatsGeboorte",
            HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'BLRegioGeboorte' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__BUITENLANDSE_REGIO_GEBOORTE(9729, "His Persoon Geboorte - Buitenlandse regio geboorte", "His_PersGeboorte.BLRegioGeboorte",
            HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'OmsLocGeboorte' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__OMSCHRIJVING_LOCATIE_GEBOORTE(9730, "His Persoon Geboorte - Omschrijving locatie geboorte", "His_PersGeboorte.OmsLocGeboorte",
            HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de kolom: 'LandGebiedGeboorte' van de tabel: 'His_PersGeboorte'.
     */
    HIS__PERSOON_GEBOORTE__LAND_GEBIED_GEBOORTE(9731, "His Persoon Geboorte - Land/gebied geboorte", "His_PersGeboorte.LandGebiedGeboorte",
            HIS__PERSOON_GEBOORTE),
    /**
     * Representeert de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING(3554, "His Persoon Geslachtsaanduiding", "His_PersGeslachtsaand", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__I_D(4527, "His Persoon Geslachtsaanduiding - ID", "His_PersGeslachtsaand.ID", HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__PERSOON(4088, "His Persoon Geslachtsaanduiding - Persoon", "His_PersGeslachtsaand.Pers",
            HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__DATUM_AANVANG_GELDIGHEID(4089, "His Persoon Geslachtsaanduiding - Datum aanvang geldigheid",
            "His_PersGeslachtsaand.DatAanvGel", HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__DATUM_EINDE_GELDIGHEID(4090, "His Persoon Geslachtsaanduiding - Datum einde geldigheid",
            "His_PersGeslachtsaand.DatEindeGel", HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__DATUM_TIJD_REGISTRATIE(4091, "His Persoon Geslachtsaanduiding - Datum/tijd registratie",
            "His_PersGeslachtsaand.TsReg", HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__DATUM_TIJD_VERVAL(4092, "His Persoon Geslachtsaanduiding - Datum/tijd verval", "His_PersGeslachtsaand.TsVerval",
            HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__NADERE_AANDUIDING_VERVAL(11130, "His Persoon Geslachtsaanduiding - Nadere aanduiding verval",
            "His_PersGeslachtsaand.NadereAandVerval", HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__B_R_P_ACTIE_INHOUD(4093, "His Persoon Geslachtsaanduiding - BRP Actie inhoud", "His_PersGeslachtsaand.ActieInh",
            HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__B_R_P_ACTIE_VERVAL(4094, "His Persoon Geslachtsaanduiding - BRP Actie verval", "His_PersGeslachtsaand.ActieVerval",
            HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4095, "His Persoon Geslachtsaanduiding - BRP Actie Aanpassing Geldigheid",
            "His_PersGeslachtsaand.ActieAanpGel", HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de kolom: 'Geslachtsaand' van de tabel: 'His_PersGeslachtsaand'.
     */
    HIS__PERSOON_GESLACHTSAANDUIDING__GESLACHTSAANDUIDING(9732, "His Persoon Geslachtsaanduiding - Geslachtsaanduiding",
            "His_PersGeslachtsaand.Geslachtsaand", HIS__PERSOON_GESLACHTSAANDUIDING),
    /**
     * Representeert de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING(3521, "His Persoon Inschrijving", "His_PersInschr", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__I_D(4566, "His Persoon Inschrijving - ID", "His_PersInschr.ID", HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__PERSOON(4236, "His Persoon Inschrijving - Persoon", "His_PersInschr.Pers", HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__DATUM_TIJD_REGISTRATIE(4239, "His Persoon Inschrijving - Datum/tijd registratie", "His_PersInschr.TsReg",
            HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__DATUM_TIJD_VERVAL(4240, "His Persoon Inschrijving - Datum/tijd verval", "His_PersInschr.TsVerval",
            HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__NADERE_AANDUIDING_VERVAL(11131, "His Persoon Inschrijving - Nadere aanduiding verval", "His_PersInschr.NadereAandVerval",
            HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__B_R_P_ACTIE_INHOUD(4241, "His Persoon Inschrijving - BRP Actie inhoud", "His_PersInschr.ActieInh",
            HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__B_R_P_ACTIE_VERVAL(4242, "His Persoon Inschrijving - BRP Actie verval", "His_PersInschr.ActieVerval",
            HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'DatInschr' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__DATUM_INSCHRIJVING(9733, "His Persoon Inschrijving - Datum inschrijving", "His_PersInschr.DatInschr",
            HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'Versienr' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__VERSIENUMMER(9734, "His Persoon Inschrijving - Versienummer", "His_PersInschr.Versienr", HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de kolom: 'Dattijdstempel' van de tabel: 'His_PersInschr'.
     */
    HIS__PERSOON_INSCHRIJVING__DATUMTIJDSTEMPEL(11238, "His Persoon Inschrijving - Datumtijdstempel", "His_PersInschr.Dattijdstempel",
            HIS__PERSOON_INSCHRIJVING),
    /**
     * Representeert de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING(10900, "His Persoon Nummerverwijzing", "His_PersNrverwijzing", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__I_D(10976, "His Persoon Nummerverwijzing - ID", "His_PersNrverwijzing.ID", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__PERSOON(10933, "His Persoon Nummerverwijzing - Persoon", "His_PersNrverwijzing.Pers", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__DATUM_AANVANG_GELDIGHEID(10934, "His Persoon Nummerverwijzing - Datum aanvang geldigheid",
            "His_PersNrverwijzing.DatAanvGel", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__DATUM_EINDE_GELDIGHEID(10935, "His Persoon Nummerverwijzing - Datum einde geldigheid",
            "His_PersNrverwijzing.DatEindeGel", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__DATUM_TIJD_REGISTRATIE(10936, "His Persoon Nummerverwijzing - Datum/tijd registratie", "His_PersNrverwijzing.TsReg",
            HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__DATUM_TIJD_VERVAL(10937, "His Persoon Nummerverwijzing - Datum/tijd verval", "His_PersNrverwijzing.TsVerval",
            HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__NADERE_AANDUIDING_VERVAL(11132, "His Persoon Nummerverwijzing - Nadere aanduiding verval",
            "His_PersNrverwijzing.NadereAandVerval", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__B_R_P_ACTIE_INHOUD(10938, "His Persoon Nummerverwijzing - BRP Actie inhoud", "His_PersNrverwijzing.ActieInh",
            HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__B_R_P_ACTIE_VERVAL(10939, "His Persoon Nummerverwijzing - BRP Actie verval", "His_PersNrverwijzing.ActieVerval",
            HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__B_R_P_ACTIE_AANPASSING_GELDIGHEID(10940, "His Persoon Nummerverwijzing - BRP Actie Aanpassing Geldigheid",
            "His_PersNrverwijzing.ActieAanpGel", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'VorigeBSN' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__VORIGE_BURGERSERVICENUMMER(10941, "His Persoon Nummerverwijzing - Vorige burgerservicenummer",
            "His_PersNrverwijzing.VorigeBSN", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'VolgendeBSN' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__VOLGENDE_BURGERSERVICENUMMER(10942, "His Persoon Nummerverwijzing - Volgende burgerservicenummer",
            "His_PersNrverwijzing.VolgendeBSN", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'VorigeANr' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__VORIGE_ADMINISTRATIENUMMER(10943, "His Persoon Nummerverwijzing - Vorige administratienummer",
            "His_PersNrverwijzing.VorigeANr", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de kolom: 'VolgendeANr' van de tabel: 'His_PersNrverwijzing'.
     */
    HIS__PERSOON_NUMMERVERWIJZING__VOLGENDE_ADMINISTRATIENUMMER(10944, "His Persoon Nummerverwijzing - Volgende administratienummer",
            "His_PersNrverwijzing.VolgendeANr", HIS__PERSOON_NUMMERVERWIJZING),
    /**
     * Representeert de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING(3664, "His Persoon Bijhouding", "His_PersBijhouding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__I_D(4551, "His Persoon Bijhouding - ID", "His_PersBijhouding.ID", HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__PERSOON(4188, "His Persoon Bijhouding - Persoon", "His_PersBijhouding.Pers", HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__DATUM_AANVANG_GELDIGHEID(4189, "His Persoon Bijhouding - Datum aanvang geldigheid", "His_PersBijhouding.DatAanvGel",
            HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__DATUM_EINDE_GELDIGHEID(4190, "His Persoon Bijhouding - Datum einde geldigheid", "His_PersBijhouding.DatEindeGel",
            HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__DATUM_TIJD_REGISTRATIE(4191, "His Persoon Bijhouding - Datum/tijd registratie", "His_PersBijhouding.TsReg",
            HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__DATUM_TIJD_VERVAL(4192, "His Persoon Bijhouding - Datum/tijd verval", "His_PersBijhouding.TsVerval", HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__NADERE_AANDUIDING_VERVAL(11133, "His Persoon Bijhouding - Nadere aanduiding verval", "His_PersBijhouding.NadereAandVerval",
            HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__B_R_P_ACTIE_INHOUD(4193, "His Persoon Bijhouding - BRP Actie inhoud", "His_PersBijhouding.ActieInh", HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__B_R_P_ACTIE_VERVAL(4194, "His Persoon Bijhouding - BRP Actie verval", "His_PersBijhouding.ActieVerval",
            HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4195, "His Persoon Bijhouding - BRP Actie Aanpassing Geldigheid",
            "His_PersBijhouding.ActieAanpGel", HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'Bijhpartij' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__BIJHOUDINGSPARTIJ(9738, "His Persoon Bijhouding - Bijhoudingspartij", "His_PersBijhouding.Bijhpartij",
            HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'Bijhaard' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__BIJHOUDINGSAARD(9737, "His Persoon Bijhouding - Bijhoudingsaard", "His_PersBijhouding.Bijhaard", HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'NadereBijhaard' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__NADERE_BIJHOUDINGSAARD(10946, "His Persoon Bijhouding - Nadere bijhoudingsaard", "His_PersBijhouding.NadereBijhaard",
            HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de kolom: 'IndOnverwDocAanw' van de tabel: 'His_PersBijhouding'.
     */
    HIS__PERSOON_BIJHOUDING__INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG(9740, "His Persoon Bijhouding - Onverwerkt document aanwezig?",
            "His_PersBijhouding.IndOnverwDocAanw", HIS__PERSOON_BIJHOUDING),
    /**
     * Representeert de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN(3515, "His Persoon Overlijden", "His_PersOverlijden", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__I_D(4539, "His Persoon Overlijden - ID", "His_PersOverlijden.ID", HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__PERSOON(4145, "His Persoon Overlijden - Persoon", "His_PersOverlijden.Pers", HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__DATUM_TIJD_REGISTRATIE(4146, "His Persoon Overlijden - Datum/tijd registratie", "His_PersOverlijden.TsReg",
            HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__DATUM_TIJD_VERVAL(4147, "His Persoon Overlijden - Datum/tijd verval", "His_PersOverlijden.TsVerval", HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__NADERE_AANDUIDING_VERVAL(11134, "His Persoon Overlijden - Nadere aanduiding verval", "His_PersOverlijden.NadereAandVerval",
            HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__B_R_P_ACTIE_INHOUD(4148, "His Persoon Overlijden - BRP Actie inhoud", "His_PersOverlijden.ActieInh", HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__B_R_P_ACTIE_VERVAL(4149, "His Persoon Overlijden - BRP Actie verval", "His_PersOverlijden.ActieVerval",
            HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'DatOverlijden' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__DATUM_OVERLIJDEN(9742, "His Persoon Overlijden - Datum overlijden", "His_PersOverlijden.DatOverlijden",
            HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'GemOverlijden' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__GEMEENTE_OVERLIJDEN(9743, "His Persoon Overlijden - Gemeente overlijden", "His_PersOverlijden.GemOverlijden",
            HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'WplnaamOverlijden' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__WOONPLAATSNAAM_OVERLIJDEN(9744, "His Persoon Overlijden - Woonplaatsnaam overlijden", "His_PersOverlijden.WplnaamOverlijden",
            HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'BLPlaatsOverlijden' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__BUITENLANDSE_PLAATS_OVERLIJDEN(9745, "His Persoon Overlijden - Buitenlandse plaats overlijden",
            "His_PersOverlijden.BLPlaatsOverlijden", HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'BLRegioOverlijden' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__BUITENLANDSE_REGIO_OVERLIJDEN(9746, "His Persoon Overlijden - Buitenlandse regio overlijden",
            "His_PersOverlijden.BLRegioOverlijden", HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'OmsLocOverlijden' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__OMSCHRIJVING_LOCATIE_OVERLIJDEN(9747, "His Persoon Overlijden - Omschrijving locatie overlijden",
            "His_PersOverlijden.OmsLocOverlijden", HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de kolom: 'LandGebiedOverlijden' van de tabel: 'His_PersOverlijden'.
     */
    HIS__PERSOON_OVERLIJDEN__LAND_GEBIED_OVERLIJDEN(9748, "His Persoon Overlijden - Land/gebied overlijden", "His_PersOverlijden.LandGebiedOverlijden",
            HIS__PERSOON_OVERLIJDEN),
    /**
     * Representeert de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK(3487, "His Persoon Naamgebruik", "His_PersNaamgebruik", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__I_D(4533, "His Persoon Naamgebruik - ID", "His_PersNaamgebruik.ID", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__PERSOON(4115, "His Persoon Naamgebruik - Persoon", "His_PersNaamgebruik.Pers", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__DATUM_TIJD_REGISTRATIE(4118, "His Persoon Naamgebruik - Datum/tijd registratie", "His_PersNaamgebruik.TsReg",
            HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__DATUM_TIJD_VERVAL(4119, "His Persoon Naamgebruik - Datum/tijd verval", "His_PersNaamgebruik.TsVerval",
            HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__NADERE_AANDUIDING_VERVAL(11135, "His Persoon Naamgebruik - Nadere aanduiding verval",
            "His_PersNaamgebruik.NadereAandVerval", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__B_R_P_ACTIE_INHOUD(4120, "His Persoon Naamgebruik - BRP Actie inhoud", "His_PersNaamgebruik.ActieInh",
            HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__B_R_P_ACTIE_VERVAL(4121, "His Persoon Naamgebruik - BRP Actie verval", "His_PersNaamgebruik.ActieVerval",
            HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'Naamgebruik' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__NAAMGEBRUIK(9749, "His Persoon Naamgebruik - Naamgebruik", "His_PersNaamgebruik.Naamgebruik", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'IndNaamgebruikAfgeleid' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__INDICATIE_NAAMGEBRUIK_AFGELEID(9751, "His Persoon Naamgebruik - Naamgebruik afgeleid?",
            "His_PersNaamgebruik.IndNaamgebruikAfgeleid", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'PredicaatNaamgebruik' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__PREDICAAT_NAAMGEBRUIK(9752, "His Persoon Naamgebruik - Predicaat naamgebruik", "His_PersNaamgebruik.PredicaatNaamgebruik",
            HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'VoornamenNaamgebruik' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__VOORNAMEN_NAAMGEBRUIK(9753, "His Persoon Naamgebruik - Voornamen naamgebruik", "His_PersNaamgebruik.VoornamenNaamgebruik",
            HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'AdellijkeTitelNaamgebruik' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__ADELLIJKE_TITEL_NAAMGEBRUIK(9754, "His Persoon Naamgebruik - Adellijke titel naamgebruik",
            "His_PersNaamgebruik.AdellijkeTitelNaamgebruik", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'VoorvoegselNaamgebruik' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__VOORVOEGSEL_NAAMGEBRUIK(9755, "His Persoon Naamgebruik - Voorvoegsel naamgebruik",
            "His_PersNaamgebruik.VoorvoegselNaamgebruik", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'ScheidingstekenNaamgebruik' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__SCHEIDINGSTEKEN_NAAMGEBRUIK(9756, "His Persoon Naamgebruik - Scheidingsteken naamgebruik",
            "His_PersNaamgebruik.ScheidingstekenNaamgebruik", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de kolom: 'GeslnaamstamNaamgebruik' van de tabel: 'His_PersNaamgebruik'.
     */
    HIS__PERSOON_NAAMGEBRUIK__GESLACHTSNAAMSTAM_NAAMGEBRUIK(9757, "His Persoon Naamgebruik - Geslachtsnaamstam naamgebruik",
            "His_PersNaamgebruik.GeslnaamstamNaamgebruik", HIS__PERSOON_NAAMGEBRUIK),
    /**
     * Representeert de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE(3790, "His Persoon Migratie", "His_PersMigratie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__I_D(4563, "His Persoon Migratie - ID", "His_PersMigratie.ID", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__PERSOON(4225, "His Persoon Migratie - Persoon", "His_PersMigratie.Pers", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__DATUM_AANVANG_GELDIGHEID(4226, "His Persoon Migratie - Datum aanvang geldigheid", "His_PersMigratie.DatAanvGel",
            HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__DATUM_EINDE_GELDIGHEID(4227, "His Persoon Migratie - Datum einde geldigheid", "His_PersMigratie.DatEindeGel",
            HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__DATUM_TIJD_REGISTRATIE(4228, "His Persoon Migratie - Datum/tijd registratie", "His_PersMigratie.TsReg", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__DATUM_TIJD_VERVAL(4229, "His Persoon Migratie - Datum/tijd verval", "His_PersMigratie.TsVerval", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__NADERE_AANDUIDING_VERVAL(11136, "His Persoon Migratie - Nadere aanduiding verval", "His_PersMigratie.NadereAandVerval",
            HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__B_R_P_ACTIE_INHOUD(4230, "His Persoon Migratie - BRP Actie inhoud", "His_PersMigratie.ActieInh", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__B_R_P_ACTIE_VERVAL(4231, "His Persoon Migratie - BRP Actie verval", "His_PersMigratie.ActieVerval", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4232, "His Persoon Migratie - BRP Actie Aanpassing Geldigheid",
            "His_PersMigratie.ActieAanpGel", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'SrtMigratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__SOORT_MIGRATIE(10947, "His Persoon Migratie - Soort migratie", "His_PersMigratie.SrtMigratie", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'RdnWijzMigratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__REDEN_WIJZIGING_MIGRATIE(11279, "His Persoon Migratie - Reden wijziging migratie", "His_PersMigratie.RdnWijzMigratie",
            HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'AangMigratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__AANGEVER_MIGRATIE(11280, "His Persoon Migratie - Aangever migratie", "His_PersMigratie.AangMigratie", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'LandGebiedMigratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__LAND_GEBIED_MIGRATIE(9758, "His Persoon Migratie - Land/gebied migratie", "His_PersMigratie.LandGebiedMigratie",
            HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'BLAdresRegel1Migratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__BUITENLANDS_ADRES_REGEL1_MIGRATIE(10948, "His Persoon Migratie - Buitenlands adres regel 1 migratie",
            "His_PersMigratie.BLAdresRegel1Migratie", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'BLAdresRegel2Migratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__BUITENLANDS_ADRES_REGEL2_MIGRATIE(10949, "His Persoon Migratie - Buitenlands adres regel 2 migratie",
            "His_PersMigratie.BLAdresRegel2Migratie", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'BLAdresRegel3Migratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__BUITENLANDS_ADRES_REGEL3_MIGRATIE(10950, "His Persoon Migratie - Buitenlands adres regel 3 migratie",
            "His_PersMigratie.BLAdresRegel3Migratie", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'BLAdresRegel4Migratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__BUITENLANDS_ADRES_REGEL4_MIGRATIE(10951, "His Persoon Migratie - Buitenlands adres regel 4 migratie",
            "His_PersMigratie.BLAdresRegel4Migratie", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'BLAdresRegel5Migratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__BUITENLANDS_ADRES_REGEL5_MIGRATIE(10952, "His Persoon Migratie - Buitenlands adres regel 5 migratie",
            "His_PersMigratie.BLAdresRegel5Migratie", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de kolom: 'BLAdresRegel6Migratie' van de tabel: 'His_PersMigratie'.
     */
    HIS__PERSOON_MIGRATIE__BUITENLANDS_ADRES_REGEL6_MIGRATIE(10953, "His Persoon Migratie - Buitenlands adres regel 6 migratie",
            "His_PersMigratie.BLAdresRegel6Migratie", HIS__PERSOON_MIGRATIE),
    /**
     * Representeert de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT(3517, "His Persoon Verblijfsrecht", "His_PersVerblijfsr", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__I_D(4542, "His Persoon Verblijfsrecht - ID", "His_PersVerblijfsr.ID", HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__PERSOON(4158, "His Persoon Verblijfsrecht - Persoon", "His_PersVerblijfsr.Pers", HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__DATUM_AANVANG_GELDIGHEID(4159, "His Persoon Verblijfsrecht - Datum aanvang geldigheid", "His_PersVerblijfsr.DatAanvGel",
            HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__DATUM_EINDE_GELDIGHEID(4160, "His Persoon Verblijfsrecht - Datum einde geldigheid", "His_PersVerblijfsr.DatEindeGel",
            HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__DATUM_TIJD_REGISTRATIE(4161, "His Persoon Verblijfsrecht - Datum/tijd registratie", "His_PersVerblijfsr.TsReg",
            HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__DATUM_TIJD_VERVAL(4162, "His Persoon Verblijfsrecht - Datum/tijd verval", "His_PersVerblijfsr.TsVerval",
            HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__NADERE_AANDUIDING_VERVAL(11137, "His Persoon Verblijfsrecht - Nadere aanduiding verval",
            "His_PersVerblijfsr.NadereAandVerval", HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__B_R_P_ACTIE_INHOUD(4163, "His Persoon Verblijfsrecht - BRP Actie inhoud", "His_PersVerblijfsr.ActieInh",
            HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__B_R_P_ACTIE_VERVAL(4164, "His Persoon Verblijfsrecht - BRP Actie verval", "His_PersVerblijfsr.ActieVerval",
            HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4165, "His Persoon Verblijfsrecht - BRP Actie Aanpassing Geldigheid",
            "His_PersVerblijfsr.ActieAanpGel", HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'AandVerblijfsr' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__AANDUIDING_VERBLIJFSRECHT(9760, "His Persoon Verblijfsrecht - Aanduiding verblijfsrecht",
            "His_PersVerblijfsr.AandVerblijfsr", HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'DatMededelingVerblijfsr' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__DATUM_MEDEDELING_VERBLIJFSRECHT(9761, "His Persoon Verblijfsrecht - Datum mededeling verblijfsrecht",
            "His_PersVerblijfsr.DatMededelingVerblijfsr", HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de kolom: 'DatVoorzEindeVerblijfsr' van de tabel: 'His_PersVerblijfsr'.
     */
    HIS__PERSOON_VERBLIJFSRECHT__DATUM_VOORZIEN_EINDE_VERBLIJFSRECHT(9762, "His Persoon Verblijfsrecht - Datum voorzien einde verblijfsrecht",
            "His_PersVerblijfsr.DatVoorzEindeVerblijfsr", HIS__PERSOON_VERBLIJFSRECHT),
    /**
     * Representeert de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT(3519, "His Persoon Uitsluiting kiesrecht", "His_PersUitslKiesr", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__I_D(4545, "His Persoon Uitsluiting kiesrecht - ID", "His_PersUitslKiesr.ID", HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__PERSOON(4171, "His Persoon Uitsluiting kiesrecht - Persoon", "His_PersUitslKiesr.Pers",
            HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__DATUM_TIJD_REGISTRATIE(4172, "His Persoon Uitsluiting kiesrecht - Datum/tijd registratie",
            "His_PersUitslKiesr.TsReg", HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__DATUM_TIJD_VERVAL(4173, "His Persoon Uitsluiting kiesrecht - Datum/tijd verval", "His_PersUitslKiesr.TsVerval",
            HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__NADERE_AANDUIDING_VERVAL(11138, "His Persoon Uitsluiting kiesrecht - Nadere aanduiding verval",
            "His_PersUitslKiesr.NadereAandVerval", HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__B_R_P_ACTIE_INHOUD(4174, "His Persoon Uitsluiting kiesrecht - BRP Actie inhoud", "His_PersUitslKiesr.ActieInh",
            HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__B_R_P_ACTIE_VERVAL(4175, "His Persoon Uitsluiting kiesrecht - BRP Actie verval", "His_PersUitslKiesr.ActieVerval",
            HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de kolom: 'IndUitslKiesr' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__INDICATIE_UITSLUITING_KIESRECHT(9764, "His Persoon Uitsluiting kiesrecht - Uitsluiting kiesrecht?",
            "His_PersUitslKiesr.IndUitslKiesr", HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de kolom: 'DatVoorzEindeUitslKiesr' van de tabel: 'His_PersUitslKiesr'.
     */
    HIS__PERSOON_UITSLUITING_KIESRECHT__DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT(9765,
            "His Persoon Uitsluiting kiesrecht - Datum voorzien einde uitsluiting kiesrecht", "His_PersUitslKiesr.DatVoorzEindeUitslKiesr",
            HIS__PERSOON_UITSLUITING_KIESRECHT),
    /**
     * Representeert de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN(3901, "His Persoon Deelname EU verkiezingen", "His_PersDeelnEUVerkiezingen", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__I_D(4548, "His Persoon Deelname EU verkiezingen - ID", "His_PersDeelnEUVerkiezingen.ID",
            HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__PERSOON(4179, "His Persoon Deelname EU verkiezingen - Persoon", "His_PersDeelnEUVerkiezingen.Pers",
            HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__DATUM_TIJD_REGISTRATIE(4180, "His Persoon Deelname EU verkiezingen - Datum/tijd registratie",
            "His_PersDeelnEUVerkiezingen.TsReg", HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__DATUM_TIJD_VERVAL(4181, "His Persoon Deelname EU verkiezingen - Datum/tijd verval",
            "His_PersDeelnEUVerkiezingen.TsVerval", HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__NADERE_AANDUIDING_VERVAL(11139, "His Persoon Deelname EU verkiezingen - Nadere aanduiding verval",
            "His_PersDeelnEUVerkiezingen.NadereAandVerval", HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__B_R_P_ACTIE_INHOUD(4182, "His Persoon Deelname EU verkiezingen - BRP Actie inhoud",
            "His_PersDeelnEUVerkiezingen.ActieInh", HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__B_R_P_ACTIE_VERVAL(4183, "His Persoon Deelname EU verkiezingen - BRP Actie verval",
            "His_PersDeelnEUVerkiezingen.ActieVerval", HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'IndDeelnEUVerkiezingen' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__INDICATIE_DEELNAME_E_U_VERKIEZINGEN(9766, "His Persoon Deelname EU verkiezingen - Deelname EU verkiezingen?",
            "His_PersDeelnEUVerkiezingen.IndDeelnEUVerkiezingen", HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'DatAanlAanpDeelnEUVerkiezing' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__DATUM_AANLEIDING_AANPASSING_DEELNAME_E_U_VERKIEZINGEN(9767,
            "His Persoon Deelname EU verkiezingen - Datum aanleiding aanpassing deelname EU verkiezingen",
            "His_PersDeelnEUVerkiezingen.DatAanlAanpDeelnEUVerkiezing", HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de kolom: 'DatVoorzEindeUitslEUVerkiezi' van de tabel: 'His_PersDeelnEUVerkiezingen'.
     */
    HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN__DATUM_VOORZIEN_EINDE_UITSLUITING_E_U_VERKIEZINGEN(9768,
            "His Persoon Deelname EU verkiezingen - Datum voorzien einde uitsluiting EU verkiezingen",
            "His_PersDeelnEUVerkiezingen.DatVoorzEindeUitslEUVerkiezi", HIS__PERSOON_DEELNAME_E_U_VERKIEZINGEN),
    /**
     * Representeert de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART(3662, "His Persoon Persoonskaart", "His_PersPK", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__I_D(4560, "His Persoon Persoonskaart - ID", "His_PersPK.ID", HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__PERSOON(4217, "His Persoon Persoonskaart - Persoon", "His_PersPK.Pers", HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__DATUM_TIJD_REGISTRATIE(4218, "His Persoon Persoonskaart - Datum/tijd registratie", "His_PersPK.TsReg",
            HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__DATUM_TIJD_VERVAL(4219, "His Persoon Persoonskaart - Datum/tijd verval", "His_PersPK.TsVerval", HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__NADERE_AANDUIDING_VERVAL(11140, "His Persoon Persoonskaart - Nadere aanduiding verval", "His_PersPK.NadereAandVerval",
            HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__B_R_P_ACTIE_INHOUD(4220, "His Persoon Persoonskaart - BRP Actie inhoud", "His_PersPK.ActieInh", HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__B_R_P_ACTIE_VERVAL(4221, "His Persoon Persoonskaart - BRP Actie verval", "His_PersPK.ActieVerval",
            HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de kolom: 'GemPK' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__GEMEENTE_PERSOONSKAART(9769, "His Persoon Persoonskaart - Gemeente persoonskaart", "His_PersPK.GemPK",
            HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de kolom: 'IndPKVolledigGeconv' van de tabel: 'His_PersPK'.
     */
    HIS__PERSOON_PERSOONSKAART__INDICATIE_PERSOONSKAART_VOLLEDIG_GECONVERTEERD(9770, "His Persoon Persoonskaart - Persoonskaart volledig geconverteerd?",
            "His_PersPK.IndPKVolledigGeconv", HIS__PERSOON_PERSOONSKAART),
    /**
     * Representeert de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES(6063, "His Persoon \\ Adres", "His_PersAdres", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__I_D(6075, "His Persoon \\ Adres - ID", "His_PersAdres.ID", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'PersAdres' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__PERSOON_ADRES(6065, "His Persoon \\ Adres - Persoon \\ Adres", "His_PersAdres.PersAdres", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__DATUM_AANVANG_GELDIGHEID(6066, "His Persoon \\ Adres - Datum aanvang geldigheid", "His_PersAdres.DatAanvGel", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__DATUM_EINDE_GELDIGHEID(6067, "His Persoon \\ Adres - Datum einde geldigheid", "His_PersAdres.DatEindeGel", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__DATUM_TIJD_REGISTRATIE(6068, "His Persoon \\ Adres - Datum/tijd registratie", "His_PersAdres.TsReg", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__DATUM_TIJD_VERVAL(6069, "His Persoon \\ Adres - Datum/tijd verval", "His_PersAdres.TsVerval", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__NADERE_AANDUIDING_VERVAL(11141, "His Persoon \\ Adres - Nadere aanduiding verval", "His_PersAdres.NadereAandVerval",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__B_R_P_ACTIE_INHOUD(6070, "His Persoon \\ Adres - BRP Actie inhoud", "His_PersAdres.ActieInh", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__B_R_P_ACTIE_VERVAL(6071, "His Persoon \\ Adres - BRP Actie verval", "His_PersAdres.ActieVerval", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__B_R_P_ACTIE_AANPASSING_GELDIGHEID(6072, "His Persoon \\ Adres - BRP Actie Aanpassing Geldigheid", "His_PersAdres.ActieAanpGel",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__SOORT(9771, "His Persoon \\ Adres - Soort", "His_PersAdres.Srt", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'RdnWijz' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__REDEN_WIJZIGING(9772, "His Persoon \\ Adres - Reden wijziging", "His_PersAdres.RdnWijz", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'AangAdresh' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__AANGEVER_ADRESHOUDING(9773, "His Persoon \\ Adres - Aangever adreshouding", "His_PersAdres.AangAdresh", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'DatAanvAdresh' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__DATUM_AANVANG_ADRESHOUDING(9774, "His Persoon \\ Adres - Datum aanvang adreshouding", "His_PersAdres.DatAanvAdresh",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'IdentcodeAdresseerbaarObject' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT(9775, "His Persoon \\ Adres - Identificatiecode adresseerbaar object",
            "His_PersAdres.IdentcodeAdresseerbaarObject", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'IdentcodeNraand' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__IDENTIFICATIECODE_NUMMERAANDUIDING(9776, "His Persoon \\ Adres - Identificatiecode nummeraanduiding",
            "His_PersAdres.IdentcodeNraand", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Gem' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__GEMEENTE(9777, "His Persoon \\ Adres - Gemeente", "His_PersAdres.Gem", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'NOR' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__NAAM_OPENBARE_RUIMTE(9778, "His Persoon \\ Adres - Naam openbare ruimte", "His_PersAdres.NOR", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'AfgekorteNOR' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__AFGEKORTE_NAAM_OPENBARE_RUIMTE(9779, "His Persoon \\ Adres - Afgekorte naam openbare ruimte", "His_PersAdres.AfgekorteNOR",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Gemdeel' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__GEMEENTEDEEL(9780, "His Persoon \\ Adres - Gemeentedeel", "His_PersAdres.Gemdeel", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Huisnr' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__HUISNUMMER(9781, "His Persoon \\ Adres - Huisnummer", "His_PersAdres.Huisnr", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Huisletter' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__HUISLETTER(9782, "His Persoon \\ Adres - Huisletter", "His_PersAdres.Huisletter", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Huisnrtoevoeging' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__HUISNUMMERTOEVOEGING(9783, "His Persoon \\ Adres - Huisnummertoevoeging", "His_PersAdres.Huisnrtoevoeging", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Postcode' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__POSTCODE(9784, "His Persoon \\ Adres - Postcode", "His_PersAdres.Postcode", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Wplnaam' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__WOONPLAATSNAAM(9785, "His Persoon \\ Adres - Woonplaatsnaam", "His_PersAdres.Wplnaam", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'LocTenOpzichteVanAdres' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__LOCATIE_TEN_OPZICHTE_VAN_ADRES(9786, "His Persoon \\ Adres - Locatie ten opzichte van adres",
            "His_PersAdres.LocTenOpzichteVanAdres", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'Locoms' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__LOCATIEOMSCHRIJVING(9787, "His Persoon \\ Adres - Locatieomschrijving", "His_PersAdres.Locoms", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel1' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__BUITENLANDS_ADRES_REGEL1(9789, "His Persoon \\ Adres - Buitenlands adres regel 1", "His_PersAdres.BLAdresRegel1",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel2' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__BUITENLANDS_ADRES_REGEL2(9790, "His Persoon \\ Adres - Buitenlands adres regel 2", "His_PersAdres.BLAdresRegel2",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel3' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__BUITENLANDS_ADRES_REGEL3(9791, "His Persoon \\ Adres - Buitenlands adres regel 3", "His_PersAdres.BLAdresRegel3",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel4' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__BUITENLANDS_ADRES_REGEL4(9792, "His Persoon \\ Adres - Buitenlands adres regel 4", "His_PersAdres.BLAdresRegel4",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel5' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__BUITENLANDS_ADRES_REGEL5(9793, "His Persoon \\ Adres - Buitenlands adres regel 5", "His_PersAdres.BLAdresRegel5",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'BLAdresRegel6' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__BUITENLANDS_ADRES_REGEL6(9794, "His Persoon \\ Adres - Buitenlands adres regel 6", "His_PersAdres.BLAdresRegel6",
            HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'LandGebied' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__LAND_GEBIED(9795, "His Persoon \\ Adres - Land/gebied", "His_PersAdres.LandGebied", HIS__PERSOON_ADRES),
    /**
     * Representeert de kolom: 'IndPersAangetroffenOpAdres' van de tabel: 'His_PersAdres'.
     */
    HIS__PERSOON_ADRES__INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES(9796, "His Persoon \\ Adres - Persoon aangetroffen op adres?",
            "His_PersAdres.IndPersAangetroffenOpAdres", HIS__PERSOON_ADRES),
    /**
     * Representeert de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT(3598, "His Persoon \\ Geslachtsnaamcomponent", "His_PersGeslnaamcomp", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__I_D(4578, "His Persoon \\ Geslachtsnaamcomponent - ID", "His_PersGeslnaamcomp.ID",
            HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'PersGeslnaamcomp' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__PERSOON_GESLACHTSNAAMCOMPONENT(4301, "His Persoon \\ Geslachtsnaamcomponent - Persoon \\ Geslachtsnaamcomponent",
            "His_PersGeslnaamcomp.PersGeslnaamcomp", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__DATUM_AANVANG_GELDIGHEID(4302, "His Persoon \\ Geslachtsnaamcomponent - Datum aanvang geldigheid",
            "His_PersGeslnaamcomp.DatAanvGel", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__DATUM_EINDE_GELDIGHEID(4303, "His Persoon \\ Geslachtsnaamcomponent - Datum einde geldigheid",
            "His_PersGeslnaamcomp.DatEindeGel", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__DATUM_TIJD_REGISTRATIE(4304, "His Persoon \\ Geslachtsnaamcomponent - Datum/tijd registratie",
            "His_PersGeslnaamcomp.TsReg", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__DATUM_TIJD_VERVAL(4305, "His Persoon \\ Geslachtsnaamcomponent - Datum/tijd verval",
            "His_PersGeslnaamcomp.TsVerval", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__NADERE_AANDUIDING_VERVAL(11143, "His Persoon \\ Geslachtsnaamcomponent - Nadere aanduiding verval",
            "His_PersGeslnaamcomp.NadereAandVerval", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__B_R_P_ACTIE_INHOUD(4306, "His Persoon \\ Geslachtsnaamcomponent - BRP Actie inhoud",
            "His_PersGeslnaamcomp.ActieInh", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__B_R_P_ACTIE_VERVAL(4307, "His Persoon \\ Geslachtsnaamcomponent - BRP Actie verval",
            "His_PersGeslnaamcomp.ActieVerval", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4308,
            "His Persoon \\ Geslachtsnaamcomponent - BRP Actie Aanpassing Geldigheid", "His_PersGeslnaamcomp.ActieAanpGel",
            HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Predicaat' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__PREDICAAT(9797, "His Persoon \\ Geslachtsnaamcomponent - Predicaat", "His_PersGeslnaamcomp.Predicaat",
            HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'AdellijkeTitel' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__ADELLIJKE_TITEL(9798, "His Persoon \\ Geslachtsnaamcomponent - Adellijke titel",
            "His_PersGeslnaamcomp.AdellijkeTitel", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Voorvoegsel' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__VOORVOEGSEL(9799, "His Persoon \\ Geslachtsnaamcomponent - Voorvoegsel", "His_PersGeslnaamcomp.Voorvoegsel",
            HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Scheidingsteken' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__SCHEIDINGSTEKEN(9800, "His Persoon \\ Geslachtsnaamcomponent - Scheidingsteken",
            "His_PersGeslnaamcomp.Scheidingsteken", HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de kolom: 'Stam' van de tabel: 'His_PersGeslnaamcomp'.
     */
    HIS__PERSOON_GESLACHTSNAAMCOMPONENT__STAM(9801, "His Persoon \\ Geslachtsnaamcomponent - Stam", "His_PersGeslnaamcomp.Stam",
            HIS__PERSOON_GESLACHTSNAAMCOMPONENT),
    /**
     * Representeert de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE(3654, "His Persoon \\ Indicatie", "His_PersIndicatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__I_D(4581, "His Persoon \\ Indicatie - ID", "His_PersIndicatie.ID", HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'PersIndicatie' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__PERSOON_INDICATIE(4315, "His Persoon \\ Indicatie - Persoon \\ Indicatie", "His_PersIndicatie.PersIndicatie",
            HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__DATUM_AANVANG_GELDIGHEID(4316, "His Persoon \\ Indicatie - Datum aanvang geldigheid", "His_PersIndicatie.DatAanvGel",
            HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__DATUM_EINDE_GELDIGHEID(4317, "His Persoon \\ Indicatie - Datum einde geldigheid", "His_PersIndicatie.DatEindeGel",
            HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__DATUM_TIJD_REGISTRATIE(4318, "His Persoon \\ Indicatie - Datum/tijd registratie", "His_PersIndicatie.TsReg",
            HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__DATUM_TIJD_VERVAL(4319, "His Persoon \\ Indicatie - Datum/tijd verval", "His_PersIndicatie.TsVerval", HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__NADERE_AANDUIDING_VERVAL(11144, "His Persoon \\ Indicatie - Nadere aanduiding verval", "His_PersIndicatie.NadereAandVerval",
            HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__B_R_P_ACTIE_INHOUD(4320, "His Persoon \\ Indicatie - BRP Actie inhoud", "His_PersIndicatie.ActieInh", HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__B_R_P_ACTIE_VERVAL(4321, "His Persoon \\ Indicatie - BRP Actie verval", "His_PersIndicatie.ActieVerval",
            HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4322, "His Persoon \\ Indicatie - BRP Actie Aanpassing Geldigheid",
            "His_PersIndicatie.ActieAanpGel", HIS__PERSOON_INDICATIE),
    /**
     * Representeert de kolom: 'Waarde' van de tabel: 'His_PersIndicatie'.
     */
    HIS__PERSOON_INDICATIE__WAARDE(9802, "His Persoon \\ Indicatie - Waarde", "His_PersIndicatie.Waarde", HIS__PERSOON_INDICATIE),
    /**
     * Representeert de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT(3604, "His Persoon \\ Nationaliteit", "His_PersNation", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__I_D(4584, "His Persoon \\ Nationaliteit - ID", "His_PersNation.ID", HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'PersNation' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__PERSOON_NATIONALITEIT(4325, "His Persoon \\ Nationaliteit - Persoon \\ Nationaliteit", "His_PersNation.PersNation",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__DATUM_AANVANG_GELDIGHEID(4326, "His Persoon \\ Nationaliteit - Datum aanvang geldigheid", "His_PersNation.DatAanvGel",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__DATUM_EINDE_GELDIGHEID(4327, "His Persoon \\ Nationaliteit - Datum einde geldigheid", "His_PersNation.DatEindeGel",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__DATUM_TIJD_REGISTRATIE(4328, "His Persoon \\ Nationaliteit - Datum/tijd registratie", "His_PersNation.TsReg",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__DATUM_TIJD_VERVAL(4329, "His Persoon \\ Nationaliteit - Datum/tijd verval", "His_PersNation.TsVerval",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__NADERE_AANDUIDING_VERVAL(11145, "His Persoon \\ Nationaliteit - Nadere aanduiding verval",
            "His_PersNation.NadereAandVerval", HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__B_R_P_ACTIE_INHOUD(4330, "His Persoon \\ Nationaliteit - BRP Actie inhoud", "His_PersNation.ActieInh",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__B_R_P_ACTIE_VERVAL(4331, "His Persoon \\ Nationaliteit - BRP Actie verval", "His_PersNation.ActieVerval",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4332, "His Persoon \\ Nationaliteit - BRP Actie Aanpassing Geldigheid",
            "His_PersNation.ActieAanpGel", HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'RdnVerk' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__REDEN_VERKRIJGING(9803, "His Persoon \\ Nationaliteit - Reden verkrijging", "His_PersNation.RdnVerk",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de kolom: 'RdnVerlies' van de tabel: 'His_PersNation'.
     */
    HIS__PERSOON_NATIONALITEIT__REDEN_VERLIES(9804, "His Persoon \\ Nationaliteit - Reden verlies", "His_PersNation.RdnVerlies",
            HIS__PERSOON_NATIONALITEIT),
    /**
     * Representeert de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK(10763, "His Persoon \\ Onderzoek", "His_PersOnderzoek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK__I_D(10833, "His Persoon \\ Onderzoek - ID", "His_PersOnderzoek.ID", HIS__PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'PersOnderzoek' van de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK__PERSOON_ONDERZOEK(10804, "His Persoon \\ Onderzoek - Persoon \\ Onderzoek", "His_PersOnderzoek.PersOnderzoek",
            HIS__PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK__DATUM_TIJD_REGISTRATIE(10805, "His Persoon \\ Onderzoek - Datum/tijd registratie", "His_PersOnderzoek.TsReg",
            HIS__PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK__DATUM_TIJD_VERVAL(10806, "His Persoon \\ Onderzoek - Datum/tijd verval", "His_PersOnderzoek.TsVerval", HIS__PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK__NADERE_AANDUIDING_VERVAL(11146, "His Persoon \\ Onderzoek - Nadere aanduiding verval", "His_PersOnderzoek.NadereAandVerval",
            HIS__PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK__B_R_P_ACTIE_INHOUD(10807, "His Persoon \\ Onderzoek - BRP Actie inhoud", "His_PersOnderzoek.ActieInh", HIS__PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK__B_R_P_ACTIE_VERVAL(10808, "His Persoon \\ Onderzoek - BRP Actie verval", "His_PersOnderzoek.ActieVerval",
            HIS__PERSOON_ONDERZOEK),
    /**
     * Representeert de kolom: 'Rol' van de tabel: 'His_PersOnderzoek'.
     */
    HIS__PERSOON_ONDERZOEK__ROL(10809, "His Persoon \\ Onderzoek - Rol", "His_PersOnderzoek.Rol", HIS__PERSOON_ONDERZOEK),
    /**
     * Representeert de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT(3577, "His Persoon \\ Reisdocument", "His_PersReisdoc", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__I_D(4587, "His Persoon \\ Reisdocument - ID", "His_PersReisdoc.ID", HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'PersReisdoc' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__PERSOON_REISDOCUMENT(4336, "His Persoon \\ Reisdocument - Persoon \\ Reisdocument", "His_PersReisdoc.PersReisdoc",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__DATUM_TIJD_REGISTRATIE(4339, "His Persoon \\ Reisdocument - Datum/tijd registratie", "His_PersReisdoc.TsReg",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__DATUM_TIJD_VERVAL(4340, "His Persoon \\ Reisdocument - Datum/tijd verval", "His_PersReisdoc.TsVerval",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__NADERE_AANDUIDING_VERVAL(11147, "His Persoon \\ Reisdocument - Nadere aanduiding verval",
            "His_PersReisdoc.NadereAandVerval", HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__B_R_P_ACTIE_INHOUD(4341, "His Persoon \\ Reisdocument - BRP Actie inhoud", "His_PersReisdoc.ActieInh",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__B_R_P_ACTIE_VERVAL(4342, "His Persoon \\ Reisdocument - BRP Actie verval", "His_PersReisdoc.ActieVerval",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'Nr' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__NUMMER(9805, "His Persoon \\ Reisdocument - Nummer", "His_PersReisdoc.Nr", HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'LengteHouder' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__LENGTE_HOUDER(9806, "His Persoon \\ Reisdocument - Lengte houder", "His_PersReisdoc.LengteHouder",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'AutVanAfgifte' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__AUTORITEIT_VAN_AFGIFTE(9807, "His Persoon \\ Reisdocument - Autoriteit van afgifte", "His_PersReisdoc.AutVanAfgifte",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatIngangDoc' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__DATUM_INGANG_DOCUMENT(9808, "His Persoon \\ Reisdocument - Datum ingang document", "His_PersReisdoc.DatIngangDoc",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatEindeDoc' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__DATUM_EINDE_DOCUMENT(9810, "His Persoon \\ Reisdocument - Datum einde document", "His_PersReisdoc.DatEindeDoc",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatUitgifte' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__DATUM_UITGIFTE(9809, "His Persoon \\ Reisdocument - Datum uitgifte", "His_PersReisdoc.DatUitgifte",
            HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'DatInhingVermissing' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__DATUM_INHOUDING_VERMISSING(9811, "His Persoon \\ Reisdocument - Datum inhouding/vermissing",
            "His_PersReisdoc.DatInhingVermissing", HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de kolom: 'AandInhingVermissing' van de tabel: 'His_PersReisdoc'.
     */
    HIS__PERSOON_REISDOCUMENT__AANDUIDING_INHOUDING_VERMISSING(9812, "His Persoon \\ Reisdocument - Aanduiding inhouding/vermissing",
            "His_PersReisdoc.AandInhingVermissing", HIS__PERSOON_REISDOCUMENT),
    /**
     * Representeert de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE(3783, "His Persoon \\ Verificatie", "His_PersVerificatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE__I_D(4590, "His Persoon \\ Verificatie - ID", "His_PersVerificatie.ID", HIS__PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'PersVerificatie' van de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE__PERSOON_VERIFICATIE(4352, "His Persoon \\ Verificatie - Persoon \\ Verificatie", "His_PersVerificatie.PersVerificatie",
            HIS__PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE__DATUM_TIJD_REGISTRATIE(4353, "His Persoon \\ Verificatie - Datum/tijd registratie", "His_PersVerificatie.TsReg",
            HIS__PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE__DATUM_TIJD_VERVAL(4354, "His Persoon \\ Verificatie - Datum/tijd verval", "His_PersVerificatie.TsVerval",
            HIS__PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE__NADERE_AANDUIDING_VERVAL(11148, "His Persoon \\ Verificatie - Nadere aanduiding verval",
            "His_PersVerificatie.NadereAandVerval", HIS__PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE__B_R_P_ACTIE_INHOUD(4355, "His Persoon \\ Verificatie - BRP Actie inhoud", "His_PersVerificatie.ActieInh",
            HIS__PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE__B_R_P_ACTIE_VERVAL(4356, "His Persoon \\ Verificatie - BRP Actie verval", "His_PersVerificatie.ActieVerval",
            HIS__PERSOON_VERIFICATIE),
    /**
     * Representeert de kolom: 'Dat' van de tabel: 'His_PersVerificatie'.
     */
    HIS__PERSOON_VERIFICATIE__DATUM(9813, "His Persoon \\ Verificatie - Datum", "His_PersVerificatie.Dat", HIS__PERSOON_VERIFICATIE),
    /**
     * Representeert de tabel: 'His_PersVerstrbeperking'.
     */
    HIS__PERSOON_VERSTREKKINGSBEPERKING(9347, "His Persoon \\ Verstrekkingsbeperking", "His_PersVerstrbeperking", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersVerstrbeperking'.
     */
    HIS__PERSOON_VERSTREKKINGSBEPERKING__I_D(9376, "His Persoon \\ Verstrekkingsbeperking - ID", "His_PersVerstrbeperking.ID",
            HIS__PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'PersVerstrbeperking' van de tabel: 'His_PersVerstrbeperking'.
     */
    HIS__PERSOON_VERSTREKKINGSBEPERKING__PERSOON_VERSTREKKINGSBEPERKING(10954,
            "His Persoon \\ Verstrekkingsbeperking - Persoon \\ Verstrekkingsbeperking", "His_PersVerstrbeperking.PersVerstrbeperking",
            HIS__PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersVerstrbeperking'.
     */
    HIS__PERSOON_VERSTREKKINGSBEPERKING__DATUM_TIJD_REGISTRATIE(9365, "His Persoon \\ Verstrekkingsbeperking - Datum/tijd registratie",
            "His_PersVerstrbeperking.TsReg", HIS__PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersVerstrbeperking'.
     */
    HIS__PERSOON_VERSTREKKINGSBEPERKING__DATUM_TIJD_VERVAL(9366, "His Persoon \\ Verstrekkingsbeperking - Datum/tijd verval",
            "His_PersVerstrbeperking.TsVerval", HIS__PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersVerstrbeperking'.
     */
    HIS__PERSOON_VERSTREKKINGSBEPERKING__NADERE_AANDUIDING_VERVAL(11149, "His Persoon \\ Verstrekkingsbeperking - Nadere aanduiding verval",
            "His_PersVerstrbeperking.NadereAandVerval", HIS__PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersVerstrbeperking'.
     */
    HIS__PERSOON_VERSTREKKINGSBEPERKING__B_R_P_ACTIE_INHOUD(9367, "His Persoon \\ Verstrekkingsbeperking - BRP Actie inhoud",
            "His_PersVerstrbeperking.ActieInh", HIS__PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersVerstrbeperking'.
     */
    HIS__PERSOON_VERSTREKKINGSBEPERKING__B_R_P_ACTIE_VERVAL(9368, "His Persoon \\ Verstrekkingsbeperking - BRP Actie verval",
            "His_PersVerstrbeperking.ActieVerval", HIS__PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Representeert de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM(3050, "His Persoon \\ Voornaam", "His_PersVoornaam", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__I_D(4593, "His Persoon \\ Voornaam - ID", "His_PersVoornaam.ID", HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'PersVoornaam' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__PERSOON_VOORNAAM(4359, "His Persoon \\ Voornaam - Persoon \\ Voornaam", "His_PersVoornaam.PersVoornaam", HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__DATUM_AANVANG_GELDIGHEID(4360, "His Persoon \\ Voornaam - Datum aanvang geldigheid", "His_PersVoornaam.DatAanvGel",
            HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__DATUM_EINDE_GELDIGHEID(4361, "His Persoon \\ Voornaam - Datum einde geldigheid", "His_PersVoornaam.DatEindeGel",
            HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__DATUM_TIJD_REGISTRATIE(4362, "His Persoon \\ Voornaam - Datum/tijd registratie", "His_PersVoornaam.TsReg",
            HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__DATUM_TIJD_VERVAL(4363, "His Persoon \\ Voornaam - Datum/tijd verval", "His_PersVoornaam.TsVerval", HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__NADERE_AANDUIDING_VERVAL(11150, "His Persoon \\ Voornaam - Nadere aanduiding verval", "His_PersVoornaam.NadereAandVerval",
            HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__B_R_P_ACTIE_INHOUD(4364, "His Persoon \\ Voornaam - BRP Actie inhoud", "His_PersVoornaam.ActieInh", HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__B_R_P_ACTIE_VERVAL(4365, "His Persoon \\ Voornaam - BRP Actie verval", "His_PersVoornaam.ActieVerval", HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'ActieAanpGel' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__B_R_P_ACTIE_AANPASSING_GELDIGHEID(4366, "His Persoon \\ Voornaam - BRP Actie Aanpassing Geldigheid",
            "His_PersVoornaam.ActieAanpGel", HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'His_PersVoornaam'.
     */
    HIS__PERSOON_VOORNAAM__NAAM(9814, "His Persoon \\ Voornaam - Naam", "His_PersVoornaam.Naam", HIS__PERSOON_VOORNAAM),
    /**
     * Representeert de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF(10903, "His Relatie Afgeleid administratief", "His_RelatieAfgeleidAdministr", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF__I_D(10979, "His Relatie Afgeleid administratief - ID", "His_RelatieAfgeleidAdministr.ID",
            HIS__RELATIE_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'Relatie' van de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF__RELATIE(10955, "His Relatie Afgeleid administratief - Relatie", "His_RelatieAfgeleidAdministr.Relatie",
            HIS__RELATIE_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF__DATUM_TIJD_REGISTRATIE(10956, "His Relatie Afgeleid administratief - Datum/tijd registratie",
            "His_RelatieAfgeleidAdministr.TsReg", HIS__RELATIE_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF__DATUM_TIJD_VERVAL(10957, "His Relatie Afgeleid administratief - Datum/tijd verval",
            "His_RelatieAfgeleidAdministr.TsVerval", HIS__RELATIE_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF__NADERE_AANDUIDING_VERVAL(11152, "His Relatie Afgeleid administratief - Nadere aanduiding verval",
            "His_RelatieAfgeleidAdministr.NadereAandVerval", HIS__RELATIE_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF__B_R_P_ACTIE_INHOUD(10958, "His Relatie Afgeleid administratief - BRP Actie inhoud",
            "His_RelatieAfgeleidAdministr.ActieInh", HIS__RELATIE_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF__B_R_P_ACTIE_VERVAL(10959, "His Relatie Afgeleid administratief - BRP Actie verval",
            "His_RelatieAfgeleidAdministr.ActieVerval", HIS__RELATIE_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de kolom: 'IndAsymmetrisch' van de tabel: 'His_RelatieAfgeleidAdministr'.
     */
    HIS__RELATIE_AFGELEID_ADMINISTRATIEF__INDICATIE_ASYMMETRISCH(10960, "His Relatie Afgeleid administratief - Asymmetrisch?",
            "His_RelatieAfgeleidAdministr.IndAsymmetrisch", HIS__RELATIE_AFGELEID_ADMINISTRATIEF),
    /**
     * Representeert de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP(3599, "His Huwelijk/Geregistreerd partnerschap", "His_HuwelijkGeregistreerdPar", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__I_D(4596, "His Huwelijk/Geregistreerd partnerschap - ID", "His_HuwelijkGeregistreerdPar.ID",
            HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'Relatie' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__RELATIE(4369, "His Huwelijk/Geregistreerd partnerschap - Relatie", "His_HuwelijkGeregistreerdPar.Relatie",
            HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__DATUM_TIJD_REGISTRATIE(4370, "His Huwelijk/Geregistreerd partnerschap - Datum/tijd registratie",
            "His_HuwelijkGeregistreerdPar.TsReg", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__DATUM_TIJD_VERVAL(4371, "His Huwelijk/Geregistreerd partnerschap - Datum/tijd verval",
            "His_HuwelijkGeregistreerdPar.TsVerval", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__NADERE_AANDUIDING_VERVAL(11153, "His Huwelijk/Geregistreerd partnerschap - Nadere aanduiding verval",
            "His_HuwelijkGeregistreerdPar.NadereAandVerval", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__B_R_P_ACTIE_INHOUD(4372, "His Huwelijk/Geregistreerd partnerschap - BRP Actie inhoud",
            "His_HuwelijkGeregistreerdPar.ActieInh", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__B_R_P_ACTIE_VERVAL(4373, "His Huwelijk/Geregistreerd partnerschap - BRP Actie verval",
            "His_HuwelijkGeregistreerdPar.ActieVerval", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'DatAanv' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__DATUM_AANVANG(9820, "His Huwelijk/Geregistreerd partnerschap - Datum aanvang",
            "His_HuwelijkGeregistreerdPar.DatAanv", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'GemAanv' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__GEMEENTE_AANVANG(9821, "His Huwelijk/Geregistreerd partnerschap - Gemeente aanvang",
            "His_HuwelijkGeregistreerdPar.GemAanv", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'WplnaamAanv' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__WOONPLAATSNAAM_AANVANG(9822, "His Huwelijk/Geregistreerd partnerschap - Woonplaatsnaam aanvang",
            "His_HuwelijkGeregistreerdPar.WplnaamAanv", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'BLPlaatsAanv' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__BUITENLANDSE_PLAATS_AANVANG(9823, "His Huwelijk/Geregistreerd partnerschap - Buitenlandse plaats aanvang",
            "His_HuwelijkGeregistreerdPar.BLPlaatsAanv", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'BLRegioAanv' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__BUITENLANDSE_REGIO_AANVANG(9824, "His Huwelijk/Geregistreerd partnerschap - Buitenlandse regio aanvang",
            "His_HuwelijkGeregistreerdPar.BLRegioAanv", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'OmsLocAanv' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__OMSCHRIJVING_LOCATIE_AANVANG(9825, "His Huwelijk/Geregistreerd partnerschap - Omschrijving locatie aanvang",
            "His_HuwelijkGeregistreerdPar.OmsLocAanv", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'LandGebiedAanv' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__LAND_GEBIED_AANVANG(9826, "His Huwelijk/Geregistreerd partnerschap - Land/gebied aanvang",
            "His_HuwelijkGeregistreerdPar.LandGebiedAanv", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'RdnEinde' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__REDEN_EINDE(9827, "His Huwelijk/Geregistreerd partnerschap - Reden einde",
            "His_HuwelijkGeregistreerdPar.RdnEinde", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__DATUM_EINDE(9828, "His Huwelijk/Geregistreerd partnerschap - Datum einde",
            "His_HuwelijkGeregistreerdPar.DatEinde", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'GemEinde' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__GEMEENTE_EINDE(9829, "His Huwelijk/Geregistreerd partnerschap - Gemeente einde",
            "His_HuwelijkGeregistreerdPar.GemEinde", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'WplnaamEinde' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__WOONPLAATSNAAM_EINDE(9830, "His Huwelijk/Geregistreerd partnerschap - Woonplaatsnaam einde",
            "His_HuwelijkGeregistreerdPar.WplnaamEinde", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'BLPlaatsEinde' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__BUITENLANDSE_PLAATS_EINDE(9831, "His Huwelijk/Geregistreerd partnerschap - Buitenlandse plaats einde",
            "His_HuwelijkGeregistreerdPar.BLPlaatsEinde", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'BLRegioEinde' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__BUITENLANDSE_REGIO_EINDE(9832, "His Huwelijk/Geregistreerd partnerschap - Buitenlandse regio einde",
            "His_HuwelijkGeregistreerdPar.BLRegioEinde", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'OmsLocEinde' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__OMSCHRIJVING_LOCATIE_EINDE(9833, "His Huwelijk/Geregistreerd partnerschap - Omschrijving locatie einde",
            "His_HuwelijkGeregistreerdPar.OmsLocEinde", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de kolom: 'LandGebiedEinde' van de tabel: 'His_HuwelijkGeregistreerdPar'.
     */
    HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP__LAND_GEBIED_EINDE(9834, "His Huwelijk/Geregistreerd partnerschap - Land/gebied einde",
            "His_HuwelijkGeregistreerdPar.LandGebiedEinde", HIS__HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Representeert de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT(9378, "His Naamskeuze ongeboren vrucht", "His_NaamskeuzeOngeborenVruch", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT__I_D(9427, "His Naamskeuze ongeboren vrucht - ID", "His_NaamskeuzeOngeborenVruch.ID",
            HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'Relatie' van de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT__RELATIE(9404, "His Naamskeuze ongeboren vrucht - Relatie", "His_NaamskeuzeOngeborenVruch.Relatie",
            HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT__DATUM_TIJD_REGISTRATIE(9405, "His Naamskeuze ongeboren vrucht - Datum/tijd registratie",
            "His_NaamskeuzeOngeborenVruch.TsReg", HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT__DATUM_TIJD_VERVAL(9406, "His Naamskeuze ongeboren vrucht - Datum/tijd verval",
            "His_NaamskeuzeOngeborenVruch.TsVerval", HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT__NADERE_AANDUIDING_VERVAL(11154, "His Naamskeuze ongeboren vrucht - Nadere aanduiding verval",
            "His_NaamskeuzeOngeborenVruch.NadereAandVerval", HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT__B_R_P_ACTIE_INHOUD(9407, "His Naamskeuze ongeboren vrucht - BRP Actie inhoud",
            "His_NaamskeuzeOngeborenVruch.ActieInh", HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT__B_R_P_ACTIE_VERVAL(9408, "His Naamskeuze ongeboren vrucht - BRP Actie verval",
            "His_NaamskeuzeOngeborenVruch.ActieVerval", HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'DatNaamskeuzeOngeborenVrucht' van de tabel: 'His_NaamskeuzeOngeborenVruch'.
     */
    HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT__DATUM_NAAMSKEUZE_ONGEBOREN_VRUCHT(9835, "His Naamskeuze ongeboren vrucht - Datum naamskeuze ongeboren vrucht",
            "His_NaamskeuzeOngeborenVruch.DatNaamskeuzeOngeborenVrucht", HIS__NAAMSKEUZE_ONGEBOREN_VRUCHT),
    /**
     * Representeert de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT(9382, "His Erkenning ongeboren vrucht", "His_ErkenningOngeborenVrucht", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT__I_D(9433, "His Erkenning ongeboren vrucht - ID", "His_ErkenningOngeborenVrucht.ID", HIS__ERKENNING_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'Relatie' van de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT__RELATIE(9418, "His Erkenning ongeboren vrucht - Relatie", "His_ErkenningOngeborenVrucht.Relatie",
            HIS__ERKENNING_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT__DATUM_TIJD_REGISTRATIE(9419, "His Erkenning ongeboren vrucht - Datum/tijd registratie",
            "His_ErkenningOngeborenVrucht.TsReg", HIS__ERKENNING_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT__DATUM_TIJD_VERVAL(9420, "His Erkenning ongeboren vrucht - Datum/tijd verval",
            "His_ErkenningOngeborenVrucht.TsVerval", HIS__ERKENNING_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT__NADERE_AANDUIDING_VERVAL(11155, "His Erkenning ongeboren vrucht - Nadere aanduiding verval",
            "His_ErkenningOngeborenVrucht.NadereAandVerval", HIS__ERKENNING_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT__B_R_P_ACTIE_INHOUD(9421, "His Erkenning ongeboren vrucht - BRP Actie inhoud",
            "His_ErkenningOngeborenVrucht.ActieInh", HIS__ERKENNING_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT__B_R_P_ACTIE_VERVAL(9422, "His Erkenning ongeboren vrucht - BRP Actie verval",
            "His_ErkenningOngeborenVrucht.ActieVerval", HIS__ERKENNING_ONGEBOREN_VRUCHT),
    /**
     * Representeert de kolom: 'DatErkenningOngeborenVrucht' van de tabel: 'His_ErkenningOngeborenVrucht'.
     */
    HIS__ERKENNING_ONGEBOREN_VRUCHT__DATUM_ERKENNING_ONGEBOREN_VRUCHT(9836, "His Erkenning ongeboren vrucht - Datum erkenning ongeboren vrucht",
            "His_ErkenningOngeborenVrucht.DatErkenningOngeborenVrucht", HIS__ERKENNING_ONGEBOREN_VRUCHT),
    /**
     * Representeert de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING(10739, "His Terugmelding", "His_Terugmelding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__I_D(10836, "His Terugmelding - ID", "His_Terugmelding.ID", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'Terugmelding' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__TERUGMELDING(10811, "His Terugmelding - Terugmelding", "His_Terugmelding.Terugmelding", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__DATUM_TIJD_REGISTRATIE(10812, "His Terugmelding - Datum/tijd registratie", "His_Terugmelding.TsReg", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__DATUM_TIJD_VERVAL(10813, "His Terugmelding - Datum/tijd verval", "His_Terugmelding.TsVerval", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__NADERE_AANDUIDING_VERVAL(11156, "His Terugmelding - Nadere aanduiding verval", "His_Terugmelding.NadereAandVerval",
            HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__B_R_P_ACTIE_INHOUD(10814, "His Terugmelding - BRP Actie inhoud", "His_Terugmelding.ActieInh", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__B_R_P_ACTIE_VERVAL(10815, "His Terugmelding - BRP Actie verval", "His_Terugmelding.ActieVerval", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'Onderzoek' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__ONDERZOEK(10816, "His Terugmelding - Onderzoek", "His_Terugmelding.Onderzoek", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'Status' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__STATUS(10818, "His Terugmelding - Status", "His_Terugmelding.Status", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'Toelichting' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__TOELICHTING(11157, "His Terugmelding - Toelichting", "His_Terugmelding.Toelichting", HIS__TERUGMELDING),
    /**
     * Representeert de kolom: 'KenmerkMeldendePartij' van de tabel: 'His_Terugmelding'.
     */
    HIS__TERUGMELDING__KENMERK_MELDENDE_PARTIJ(10817, "His Terugmelding - Kenmerk meldende partij", "His_Terugmelding.KenmerkMeldendePartij",
            HIS__TERUGMELDING),
    /**
     * Representeert de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON(11095, "His Terugmelding Contactpersoon", "His_TerugmeldingContactpers", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__I_D(11174, "His Terugmelding Contactpersoon - ID", "His_TerugmeldingContactpers.ID",
            HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'Terugmelding' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__TERUGMELDING(11158, "His Terugmelding Contactpersoon - Terugmelding", "His_TerugmeldingContactpers.Terugmelding",
            HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__DATUM_TIJD_REGISTRATIE(11159, "His Terugmelding Contactpersoon - Datum/tijd registratie",
            "His_TerugmeldingContactpers.TsReg", HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__DATUM_TIJD_VERVAL(11160, "His Terugmelding Contactpersoon - Datum/tijd verval",
            "His_TerugmeldingContactpers.TsVerval", HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__NADERE_AANDUIDING_VERVAL(11163, "His Terugmelding Contactpersoon - Nadere aanduiding verval",
            "His_TerugmeldingContactpers.NadereAandVerval", HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__B_R_P_ACTIE_INHOUD(11161, "His Terugmelding Contactpersoon - BRP Actie inhoud",
            "His_TerugmeldingContactpers.ActieInh", HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__B_R_P_ACTIE_VERVAL(11162, "His Terugmelding Contactpersoon - BRP Actie verval",
            "His_TerugmeldingContactpers.ActieVerval", HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'Email' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__EMAIL(11164, "His Terugmelding Contactpersoon - Email", "His_TerugmeldingContactpers.Email",
            HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'Telefoonnr' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__TELEFOONNUMMER(11165, "His Terugmelding Contactpersoon - Telefoonnummer", "His_TerugmeldingContactpers.Telefoonnr",
            HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'Voornamen' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__VOORNAMEN(11166, "His Terugmelding Contactpersoon - Voornamen", "His_TerugmeldingContactpers.Voornamen",
            HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'Voorvoegsel' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__VOORVOEGSEL(11167, "His Terugmelding Contactpersoon - Voorvoegsel", "His_TerugmeldingContactpers.Voorvoegsel",
            HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'Scheidingsteken' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__SCHEIDINGSTEKEN(11168, "His Terugmelding Contactpersoon - Scheidingsteken",
            "His_TerugmeldingContactpers.Scheidingsteken", HIS__TERUGMELDING_CONTACTPERSOON),
    /**
     * Representeert de kolom: 'Geslnaamstam' van de tabel: 'His_TerugmeldingContactpers'.
     */
    HIS__TERUGMELDING_CONTACTPERSOON__GESLACHTSNAAMSTAM(11169, "His Terugmelding Contactpersoon - Geslachtsnaamstam",
            "His_TerugmeldingContactpers.Geslnaamstam", HIS__TERUGMELDING_CONTACTPERSOON);

    private Integer id;
    private String naam;
    private String databaseNaam;
    private DatabaseObjectKern ouder;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param id Id voor DatabaseObjectKern
     * @param naam Naam voor DatabaseObjectKern
     * @param databaseNaam DatabaseNaam voor DatabaseObjectKern
     * @param ouder Ouder voor DatabaseObjectKern
     */
    private DatabaseObjectKern(final Integer id, final String naam, final String databaseNaam, final DatabaseObjectKern ouder) {
        this.id = id;
        this.naam = naam;
        this.databaseNaam = databaseNaam;
        this.ouder = ouder;
    }

    /**
     * Retourneert id van DatabaseObjectKern.
     *
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retourneert naam van DatabaseObjectKern.
     *
     * @return naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert databaseNaam van DatabaseObjectKern.
     *
     * @return databaseNaam.
     */
    public String getDatabaseNaam() {
        return databaseNaam;
    }

    /**
     * Retourneert ouder van DatabaseObjectKern.
     *
     * @return ouder.
     */
    public DatabaseObjectKern getOuder() {
        return ouder;
    }

    /**
     * Geeft het database object met een bepaald id terug.
     *
     * @param id het id van het gezochte database object
     * @return het java type of null indien niet gevonden
     */
    public static DatabaseObjectKern findById(final Integer id) {
        DatabaseObjectKern databaseObject = null;
        for (DatabaseObjectKern enumWaarde : DatabaseObjectKern.values()) {
            if (enumWaarde.getId().equals(id)) {
                databaseObject = enumWaarde;
                break;
            }
        }
        return databaseObject;

    }

    /**
     * Geeft aan of dit database object een tabel is of niet.
     *
     * @return tabel (true) of kolom (false)
     */
    public Boolean isTabel() {
        return this.ouder == null;
    }

    /**
     * Geeft aan of dit database object een kolom is of niet.
     *
     * @return kolom (true) of tabel (false)
     */
    public Boolean isKolom() {
        return this.ouder != null;
    }

}
