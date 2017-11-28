var kernConfig = angular.module('KernConfig', []);
var AanduidingInhoudingVermissingReisdocument = {
    titel: 'Aanduiding Inhouding Vermissing Reisdocument',
    resourceNaam: 'AanduidingInhoudingVermissingReisdocument',
    resourceUrl: 'aanduidinginhoudingvermissingreisdocument/:id',
    loaderNaam: 'AanduidingInhoudingVermissingReisdocumentLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', type: 'text', maxlength: 1, required: true},
        {name: 'Naam', display: 'naam', sort: 'naam', type: 'text', maxlength: 80, required: true},
    ],
    listUri: '/aanduidinginhoudingvermissingreisdocument/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("AanduidingInhoudingVermissingReisdocumentConfig", AanduidingInhoudingVermissingReisdocument);
beheerConfiguratie.setup(AanduidingInhoudingVermissingReisdocument);

var AanduidingVerblijfsrecht = {
    titel: 'Aanduiding Verblijfsrecht',
    resourceNaam: 'AanduidingVerblijfsrecht',
    resourceUrl: 'aanduidingverblijfsrecht/:id',
    loaderNaam: 'AanduidingVerblijfsrechtLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', required: true, maxlength: 2, minlength: 2, pattern: '\\d*', type: 'text'},
        {name: 'Omschrijving', display: 'omschrijving', sort: 'omschrijving', required: true, maxlength: 250},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/aanduidingverblijfsrecht/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("AanduidingVerblijfsrechtConfig", AanduidingVerblijfsrecht);
beheerConfiguratie.setup(AanduidingVerblijfsrecht);

var Aangever = {
    titel: 'Aangever',
    resourceNaam: 'Aangever',
    resourceUrl: 'aangever/:id',
    loaderNaam: 'AangeverLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', required: true, maxlength: 1},
        {name: 'Naam', display: 'naam', sort: 'naam', required: true, maxlength: 80},
        {name: 'Omschrijving', display: 'omschrijving', sort: 'omschrijving', required: true, maxlength: 250},
    ],
    listUri: '/aangever/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("AangeverConfig", Aangever);
beheerConfiguratie.setup(Aangever);

var AdellijkeTitel = {
    titel: 'Adellijke Titel',
    resourceNaam: 'AdellijkeTitel',
    resourceUrl: 'adellijketitel/:id',
    loaderNaam: 'AdellijkeTitelLoader',
    listUri: '/adellijketitel/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', required: true, maxlength: 1},
        {name: 'Naam mannelijk', display: 'naamMannelijk', sort: 'naamMannelijk', required: true, maxlength: 80},
        {name: 'Naam vrouwelijk', display: 'naamVrouwelijk', sort: 'naamVrouwelijk', required: true, maxlength: 80},
    ],
    viewTemplateUrl: 'views/generic/view.html',
    geenDetail: true
};
kernConfig.value("AdellijkeTitelConfig", AdellijkeTitel);
beheerConfiguratie.setup(AdellijkeTitel);

var AutoriteittypeVanAfgifteReisdocument = {
    titel: 'Autoriteittype van Afgifte Reisdocument',
    resourceNaam: 'AutoriteittypeVanAfgifteReisdocument',
    resourceUrl: 'autoriteittypevanafgiftereisdocument/:id',
    loaderNaam: 'AutoriteittypeVanAfgifteReisdocumentLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', required: true, maxlength: 2},
        {name: 'Autoriteittype van Afgifte Reisdocument', display: 'naam', sort: 'naam', required: true, maxlength: 80},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/autoriteittypevanafgiftereisdocument/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("AutoriteittypeVanAfgifteReisdocumentConfig", AutoriteittypeVanAfgifteReisdocument);
beheerConfiguratie.setup(AutoriteittypeVanAfgifteReisdocument);

var Bijhoudingsaard = {
    titel: 'Bijhoudingsaard',
    resourceNaam: 'Bijhoudingsaard',
    resourceUrl: 'bijhoudingsaard/:id',
    loaderNaam: 'BijhoudingsaardLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Naam', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/bijhoudingsaard/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("BijhoudingsaardConfig", Bijhoudingsaard);
beheerConfiguratie.setup(Bijhoudingsaard);

var BurgerzakenModule = {
    titel: 'Burgerzaken Module',
    resourceNaam: 'BurgerzakenModule',
    resourceUrl: 'burgerzakenmodule/:id',
    loaderNaam: 'BurgerzakenModuleLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Burgerzaken Module', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/burgerzakenmodule/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("BurgerzakenModuleConfig", BurgerzakenModule);
beheerConfiguratie.setup(BurgerzakenModule);

var CategorieAdministratieveHandeling = {
    titel: 'Categorie Administratieve Handeling',
    resourceNaam: 'CategorieAdministratieveHandeling',
    resourceUrl: 'categorieadministratievehandeling/:id',
    loaderNaam: 'CategorieAdministratieveHandelingLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Categorie Administratieve Handeling', display: 'naam'},
    ],
    listUri: '/categorieadministratievehandeling/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("CategorieAdministratieveHandelingConfig", CategorieAdministratieveHandeling);
beheerConfiguratie.setup(CategorieAdministratieveHandeling);

var CategoriePersonen = {
    titel: 'Categorie Personen',
    resourceNaam: 'CategoriePersonen',
    resourceUrl: 'categoriepersonen/:id',
    loaderNaam: 'CategoriePersonenLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Categorie Personen', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/categoriepersonen/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("CategoriePersonenConfig", CategoriePersonen);
beheerConfiguratie.setup(CategoriePersonen);

var Element = {
    titel: 'Element',
    resourceNaam: 'Element',
    resourceUrl: 'element/:id',
    loaderNaam: 'ElementLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Naam', display: 'naam'},
        {name: 'Soort', display: 'soort.naam'},
        {name: 'Element Naam', display: 'elementNaam'},
        {name: 'Objecttype', display: 'objecttype.naam'},
        {name: 'Groep', display: 'groep.naam', toonOpLijst: false},
        {name: 'Volgnummer', display: 'volgnummer', toonOpLijst: false},
        {name: 'Alias van', display: 'aliasVan', toonOpLijst: false},
        {name: 'Expressie', display: 'expressie', toonOpLijst: false},
        {name: 'Autorisatie', display: 'autorisatie.naam', toonOpLijst: false},
        {name: 'Tabel', display: 'tabel.naam', toonOpLijst: false},
        {name: 'Identificatie database', display: 'identificatieDatabase', toonOpLijst: false},
        {name: 'Historie tabel', display: 'hisTabel.naam', toonOpLijst: false},
        {name: 'Historie identificatie database', display: 'hisIdentifierDatabase', toonOpLijst: false},
        {name: 'Leveren als stamgegeven', display: 'leverenAlsStamgegeven', type: 'checkboxJaNee', toonOpLijst: false},
        {name: 'Datum Aanvang Geldigheid', display: 'datumAanvangGeldigheid', type: 'datumonbekend', toonOpLijst: false},
        {name: 'Datum Einde Geldigheid', display: 'datumEindeGeldigheid', type: 'datumonbekend', toonOpLijst: false},
    ],
    listUri: '/element/list',
    listTemplateUrl: 'views/generic/list.html',
    geenNieuw: true,
    geenEdit: true,
};
kernConfig.value("ElementConfig", Element);
beheerConfiguratie.setup(Element);

var FunctieAdres = {
    titel: 'Functie Adres',
    resourceNaam: 'FunctieAdres',
    resourceUrl: 'functieadres/:id',
    loaderNaam: 'FunctieAdresLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Functie Adres', display: 'naam'},
    ],
    listUri: '/functieadres/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("FunctieAdresConfig", FunctieAdres);
beheerConfiguratie.setup(FunctieAdres);

var Gemeente = {
    titel: 'Gemeente',
    resourceNaam: 'Gemeente',
    resourceUrl: 'gemeente/:id',
    loaderNaam: 'GemeenteLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Gemeente', display: 'naam', sort: 'naam', filter: 'naam', required: true, maxlength: 80},
        {name: 'Code', display: 'code', sort: 'code', filter: 'code', required: true, type: 'nummer', maxlength: 4},
        {name: 'Partij', display: 'partij', sort: 'partij.naam', filter: 'partij.id', bron: 'Partij', type: 'select', required: true},
        {name: 'Voortzettende gemeente', sort: 'voortzettendeGemeente.naam', filter: 'voortzettendeGemeente.id', display: 'voortzettendeGemeente', bron: 'Gemeente', type: 'select'},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/gemeente/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("GemeenteConfig", Gemeente);
beheerConfiguratie.setup(Gemeente);

var Geslachtsaanduiding = {
    titel: 'Geslachtsaanduiding',
    resourceNaam: 'Geslachtsaanduiding',
    resourceUrl: 'geslachtsaanduiding/:id',
    loaderNaam: 'GeslachtsaanduidingLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Geslachtsaanduiding', display: 'naam'},
    ],
    listUri: '/geslachtsaanduiding/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("GeslachtsaanduidingConfig", Geslachtsaanduiding);
beheerConfiguratie.setup(Geslachtsaanduiding);

var Historievorm = {
    titel: 'Historievorm',
    resourceNaam: 'Historievorm',
    resourceUrl: 'historievorm/:id',
    loaderNaam: 'HistorievormLoader',
    listUri: '/historievorm/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Historievorm', display:'naam'},
        { name:'Omschrijving', display:'omschrijving'},
    ],
    geenDetail: true
};
kernConfig.value("HistorievormConfig", Historievorm);
beheerConfiguratie.setup(Historievorm);

var Koppelvlak = {
    titel: 'Koppelvlak',
    resourceNaam: 'Koppelvlak',
    resourceUrl: 'koppelvlak/:id',
    loaderNaam: 'KoppelvlakLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Koppelvlak', display: 'naam'},
        {name: 'Stelsel', display: 'stelsel'},
    ],
    listUri: '/koppelvlak/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("KoppelvlakConfig", Koppelvlak);
beheerConfiguratie.setup(Koppelvlak);

var LandGebied = {
    titel: 'Land/Gebied',
    resourceNaam: 'LandGebied',
    resourceUrl: 'landgebied/:id',
    loaderNaam: 'LandGebiedLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', filter: 'code', sort: 'code', required: true, type: 'nummer', maxlength: 4},
        {name: 'Land/Gebied', filter: 'naam', display: 'naam', sort: 'naam', required: true, maxlength: 80},
        {name: 'ISO 3166-1 alpha-2', display: 'iso31661Alpha2', filter: 'iso31661Alpha2', sort: 'iso31661Alpha2', maxlength: 2},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/landgebied/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("LandGebiedConfig", LandGebied);
beheerConfiguratie.setup(LandGebied);

var Naamgebruik = {
    titel: 'Naamgebruik',
    resourceNaam: 'Naamgebruik',
    resourceUrl: 'naamgebruik/:id',
    loaderNaam: 'NaamgebruikLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Naamgebruik', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/naamgebruik/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("NaamgebruikConfig", Naamgebruik);
beheerConfiguratie.setup(Naamgebruik);

var NadereBijhoudingsaard = {
    titel: 'Nadere Bijhoudingsaard',
    resourceNaam: 'NadereBijhoudingsaard',
    resourceUrl: 'naderebijhoudingsaard/:id',
    loaderNaam: 'NadereBijhoudingsaardLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Nadere Bijhoudingsaard', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/naderebijhoudingsaard/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("NadereBijhoudingsaardConfig", NadereBijhoudingsaard);
beheerConfiguratie.setup(NadereBijhoudingsaard);

var Nationaliteit = {
    titel: 'Nationaliteit',
    resourceNaam: 'Nationaliteit',
    resourceUrl: 'nationaliteit/:id',
    loaderNaam: 'NationaliteitLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', filter: 'code', required: true, type: 'text', minlength: 4, maxlength: 4, pattern: '\\d*'},
        {name: 'Nationaliteit', display: 'naam', sort: 'naam', filter: 'naam', required: true, maxlength: 80},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/nationaliteit/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("NationaliteitConfig", Nationaliteit);
beheerConfiguratie.setup(Nationaliteit);

var Partij = {
    titel: 'Partij',
    resourceNaam: 'Partij',
    resourceUrl: 'partij/:id',
    loaderNaam: 'PartijLoader',
    vastebreedte: true,
    sortering: 'naam',
    contextParam: "partij",
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', type: 'nummer', sort: 'code', filter: 'filterCode', required: true, minlength: 6, maxlength: 6, readonly: true, nieuwReadonly: false},
        {name: 'Naam', display: 'naam', sort: 'naam', filter: 'filterNaam', required: true, maxlength: 80, lijstbreedte: 4 },
        {name: 'OIN', display: 'oin', maxlength: 40, filter: 'filterOin', toonOpLijst: false},
        {name: 'Soort partij', display: 'soort', type: 'select', bron: "SoortPartij", filter: "filterSoort", lijstbreedte: 2 },
        {name: 'Datum ingang', display: 'datumIngang', sort: 'datumIngang', filter: 'filterDatumIngang', type: 'datum', required: true, readonly: false },
        {name: 'Datum einde', display: 'datumEinde', sort: 'datumEinde', filter: 'filterDatumEinde', type: 'datum'},
        {name: 'Verstrekkingsbeperking mogelijk?', display: 'verstrekkingsbeperkingMogelijk', filter: 'filterIndicatie', type: 'checkboxJaNee' },
        {name: 'Automatisch fiatteren?', display: 'automatischFiatteren', type: 'checkboxJaNee', filter: 'filterIndicatieAutomatischFiatteren' },
        {name: 'Datum overgang naar BRP', display: 'datumOvergangNaarBrp', type: 'datum', filter: 'filterDatumOvergangNaarBrp' },
        {name: 'Ondertekenaar vrij bericht', display: 'ondertekenaarVrijBericht', type: 'select', bron: 'Partij', toonOpLijst: false },
        {name: 'Transporteur vrij bericht', display: 'transporteurVrijBericht', type: 'select', bron: 'Partij', toonOpLijst: false },
        {name: 'Datum ingang vrij bericht', display: 'datumIngangVrijBericht', sort: 'datumIngangVrijBericht', type: 'datum', toonOpLijst: false, readonly: false },
        {name: 'Datum einde vrij bericht', display: 'datumEindeVrijBericht', sort: 'datumEindeVrijBericht', toonOpLijst: false, type: 'datum' },
        {name: 'Geblokkeerd vrij bericht?', display: 'isVrijBerichtGeblokkeerd', type: 'checkboxJaNee', toonOpLijst: false },
        {name: 'Afleverpunt vrij bericht', display: 'afleverpuntVrijBericht', toonOpLijst: false },
        {name: 'Partijrol', display: 'rol', type: 'select', bron: 'Rol', toonOpBewerken: false, toonOpLijst: false, filter: 'filterPartijRol' },
        {
            name: "Partij rollen",
            display: 'partijrol',
            type: 'inlinelijst',
            bron: 'PartijPartijRol',
            velden: [
                {name: 'Rol', display: 'rol', type: 'select', bron: "Rol", lijstbreedte: 2},
                {name: 'Datum ingang', display: 'datumIngang', type: 'datum', maxlength: 8, required: true},
                {name: 'Datum einde', display: 'datumEinde', type: 'datum', maxlength: 8}
            ],
            relatie: "partij",
            toonOpLijst: false
        }
    ],
    listUri: '/partijen',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false

};
kernConfig.value("PartijConfig", Partij);
beheerConfiguratie.setup(Partij);

var PartijPartijRol = {
    titel: 'PartijRol',
    resourceNaam: 'PartijPartijRol',
    resourceUrl: 'partij/:partij/partijrollen/:id',
    loaderNaam: 'PartijPartijRolLoader',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Rol', display: 'rol', type: 'select', bron: "Rol", lijstbreedte: 2, required: true, readonly: true, nieuwReadonly: false},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datum', maxlength: 8, required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datum', maxlength: 8}
    ],
    listUri: '/partijrollen',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("PartijPartijRolConfig", PartijPartijRol);
beheerConfiguratie.setup(PartijPartijRol);

var PartijRol = {
    titel: 'PartijRol',
    resourceNaam: 'PartijRol',
    resourceUrl: 'partijrollen/:id',
    loaderNaam: 'PartijRolLoader',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Rol', display: 'rol', type: 'select', bron: "Rol", lijstbreedte: 2, required: true},
        {name: 'Omschrijving', display: 'omschrijving'},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datum', maxlength: 8, required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datum', maxlength: 8}
    ],
    listUri: '/partijrollen',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("PartijRolConfig", PartijRol);
beheerConfiguratie.setup(PartijRol);

var Plaats = {
    titel: 'Plaats',
    resourceNaam: 'Plaats',
    resourceUrl: 'plaats/:id',
    loaderNaam: 'PlaatsLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Plaats', display: 'naam', sort: 'naam', filter: 'naam', required: true, maxlength: 80},
    ],
    listUri: '/plaats/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("PlaatsConfig", Plaats);
beheerConfiguratie.setup(Plaats);

var Predicaat = {
    titel: 'Predicaat',
    resourceNaam: 'Predicaat',
    resourceUrl: 'predicaat/:id',
    loaderNaam: 'PredicaatLoader',
    listUri: '/predicaat/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: 'Id', display: 'id', filter: 'filterId', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', filter: 'filterCode', required: true, maxlength: 1},
        {name: 'Naam mannelijk', display: 'naamMannelijk', sort: 'naamMannelijk', required: true, maxlength: 80},
        {name: 'Naam vrouwelijk', display: 'naamVrouwelijk', sort: 'naamVrouwelijk', required: true, maxlength: 80},
    ],
    viewTemplateUrl: 'views/generic/view.html',
    geenDetail: true
};
kernConfig.value("PredicaatConfig", Predicaat);
beheerConfiguratie.setup(Predicaat);

var RedenEindeRelatie = {
    titel: 'Reden Einde Relatie',
    resourceNaam: 'RedenEindeRelatie',
    resourceUrl: 'redeneinderelatie/:id',
    loaderNaam: 'RedenEindeRelatieLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Reden Einde Relatie', display: 'code', sort: 'code', required: true, maxlength: 1},
        {name: 'Omschrijving', display: 'omschrijving', sort: 'omschrijving', required: true, maxlength: 250},
    ],
    listUri: '/redeneinderelatie/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("RedenEindeRelatieConfig", RedenEindeRelatie);
beheerConfiguratie.setup(RedenEindeRelatie);

var RedenVerkrijgingNLNationaliteit = {
    titel: 'Reden Verkrijging NL Nationaliteit',
    resourceNaam: 'RedenVerkrijgingNLNationaliteit',
    resourceUrl: 'redenverkrijgingnlnationaliteit/:id',
    loaderNaam: 'RedenVerkrijgingNLNationaliteitLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', required: true, maxlength: 5, type: 'nummer'},
        {name: 'Reden Verkrijging NL Nationaliteit', display: 'omschrijving', sort: 'omschrijving', required: true, maxlength: 250},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'}
    ],
    listUri: '/redenverkrijgingnlnationaliteit/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("RedenVerkrijgingNLNationaliteitConfig", RedenVerkrijgingNLNationaliteit);
beheerConfiguratie.setup(RedenVerkrijgingNLNationaliteit);

var RedenVerliesNLNationaliteit = {
    titel: 'Reden Verlies NL Nationaliteit',
    resourceNaam: 'RedenVerliesNLNationaliteit',
    resourceUrl: 'redenverliesnlnationaliteit/:id',
    loaderNaam: 'RedenVerliesNLNationaliteitLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', required: true, maxlength: 5},
        {name: 'Reden Verlies NL Nationaliteit', display: 'omschrijving', sort: 'omschrijving', required: true, maxlength: 250},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'}
    ],
    listUri: '/redenverliesnlnationaliteit/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("RedenVerliesNLNationaliteitConfig", RedenVerliesNLNationaliteit);
beheerConfiguratie.setup(RedenVerliesNLNationaliteit);

var RedenWijzigingVerblijf = {
    titel: 'Reden Wijziging Verblijf',
    resourceNaam: 'RedenWijzigingVerblijf',
    resourceUrl: 'redenwijzigingverblijf/:id',
    loaderNaam: 'RedenWijzigingVerblijfLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Code', display: 'code', sort: 'code', required: true, maxlength: 1},
        {name: 'Reden Wijziging Verblijf', display: 'naam', sort: 'naam', required: true, maxlength: 80},
    ],
    listUri: '/redenwijzigingverblijf/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("RedenWijzigingVerblijfConfig", RedenWijzigingVerblijf);
beheerConfiguratie.setup(RedenWijzigingVerblijf);

var Regel = {
    titel: 'Regel',
    resourceNaam: 'Regel',
    resourceUrl: 'regel/:id',
    loaderNaam: 'RegelLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Soort', display: 'soortMelding.naam'},
        {name: 'Melding', display: 'melding'},
    ],
    listUri: '/regel/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("RegelConfig", Regel);
beheerConfiguratie.setup(Regel);

var Rol = {
    titel: 'Rol',
    resourceNaam: 'Rol',
    resourceUrl: 'rol/:id',
    loaderNaam: 'RolLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Rol', display: 'naam'},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/rol/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("RolConfig", Rol);
beheerConfiguratie.setup(Rol);

var SoortActie = {
    titel: 'Soort Actie',
    resourceNaam: 'SoortActie',
    resourceUrl: 'soortactie/:id',
    loaderNaam: 'SoortActieLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Actie', display: 'naam'},
    ],
    listUri: '/soortactie/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortActieConfig", SoortActie);
beheerConfiguratie.setup(SoortActie);

var SoortActieBrongebruik = {
    titel: 'Soort Actie Brongebruik',
    resourceNaam: 'SoortActieBrongebruik',
    resourceUrl: 'soortactiebrongebruik/:id',
    loaderNaam: 'SoortActieBrongebruikLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Soort document', sort: 'soortActieBrongebruikSleutel.soortDocument.naam', filter: 'soortActieBrongebruikSleutel.soortDocument.id', display: 'soortDocument', type: 'select', bron: 'SoortDocument'},
        {name: 'Soort actie', display: 'soortActie', sort: 'soortActieBrongebruikSleutel.soortActieId', filter: 'soortActieBrongebruikSleutel.soortActieId', type: 'select', bron: 'SoortActie'},
        {name: 'Soort administratieve handeling', sort: 'soortActieBrongebruikSleutel.soortAdministratieveHandelingId', filter: 'soortActieBrongebruikSleutel.soortAdministratieveHandelingId', display: 'soortAdministratieveHandeling', type: 'select', bron: 'SoortAdministratieveHandeling'},
        {name: 'Datum aanvang geldigheid', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde geldigheid', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/soortactiebrongebruik/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("SoortActieBrongebruikConfig", SoortActieBrongebruik);
beheerConfiguratie.setup(SoortActieBrongebruik);

var SoortAdministratieveHandeling = {
    titel: 'Soort Administratieve Handeling',
    resourceNaam: 'SoortAdministratieveHandeling',
    resourceUrl: 'soortadministratievehandeling/:id',
    loaderNaam: 'SoortAdministratieveHandelingLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Soort Administratieve Handeling', display: 'naam'},
        {name: 'Categorie Administratieve Handeling', display: 'categorieAdministratieveHandeling', bron: 'CategorieAdministratieveHandeling', type: 'select'},
        {name: 'Burgerzaken Module', display: 'module.naam'},
        {name: 'Alias', display: 'alias'}
    ],
    listUri: '/soortadministratievehandeling/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortAdministratieveHandelingConfig", SoortAdministratieveHandeling);
beheerConfiguratie.setup(SoortAdministratieveHandeling);

var SoortBetrokkenheid = {
    titel: 'Soort Betrokkenheid',
    resourceNaam: 'SoortBetrokkenheid',
    resourceUrl: 'soortbetrokkenheid/:id',
    loaderNaam: 'SoortBetrokkenheidLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Soort Betrokkenheid', display: 'naam'},
    ],
    listUri: '/soortbetrokkenheid/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortBetrokkenheidConfig", SoortBetrokkenheid);
beheerConfiguratie.setup(SoortBetrokkenheid);

var SoortDocument = {
    titel: 'Soort Document',
    resourceNaam: 'SoortDocument',
    resourceUrl: 'soortdocument/:id',
    loaderNaam: 'SoortDocumentLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Soort Document', display: 'naam', sort: 'naam', required: true, maxlength: 80},
        {name: 'Omschrijving', display: 'omschrijving', sort: 'omschrijving', required: true, maxlength: 250},
        {name: 'Rangorde', display: 'rangorde', type: 'nummer'},
        {name: 'Registersoort', display: 'registersoort', type: 'nummer'},
    ],
    listUri: '/soortdocument/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("SoortDocumentConfig", SoortDocument);
beheerConfiguratie.setup(SoortDocument);

var SoortElement = {
    titel: 'Soort Element',
    resourceNaam: 'SoortElement',
    resourceUrl: 'soortelement/:id',
    loaderNaam: 'SoortElementLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Element', display: 'naam'},
    ],
    listUri: '/soortelement/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortElementConfig", SoortElement);
beheerConfiguratie.setup(SoortElement);

var SoortElementAutorisatie = {
    titel: 'Soort Element Autorisatie',
    resourceNaam: 'SoortElement Autorisatie',
    resourceUrl: 'soortelementautorisatie/:id',
    loaderNaam: 'SoortElementAutorisatieLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Element Autorisatie', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/soortelementautorisatie/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortElementAutorisatieConfig", SoortElementAutorisatie);
beheerConfiguratie.setup(SoortElementAutorisatie);

var SoortIndicatie = {
    titel: 'Soort Indicatie',
    resourceNaam: 'SoortIndicatie',
    resourceUrl: 'soortindicatie/:id',
    loaderNaam: 'SoortIndicatieLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Indicatie', display: 'omschrijving'},
        {name: 'Materiele historie van toepassing?', display: 'materieleHistorieVanToepassing', type: 'checkboxJaNee'},
    ],
    listUri: '/soortindicatie/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortIndicatieConfig", SoortIndicatie);
beheerConfiguratie.setup(SoortIndicatie);

var SoortMelding = {
    titel: 'Soort Melding',
    resourceNaam: 'SoortMelding',
    resourceUrl: 'soortmelding/:id',
    loaderNaam: 'SoortMeldingLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Naam', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
        {name: 'Meldingniveau', display: 'meldingNiveau'},
    ],
    listUri: '/soortmelding/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortMeldingConfig", SoortMelding);
beheerConfiguratie.setup(SoortMelding);

var SoortMigratie = {
    titel: 'Soort Migratie',
    resourceNaam: 'SoortMigratie',
    resourceUrl: 'soortmigratie/:id',
    loaderNaam: 'SoortMigratieLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Soort Migratie', display: 'naam'},
    ],
    listUri: '/soortmigratie/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortMigratieConfig", SoortMigratie);
beheerConfiguratie.setup(SoortMigratie);

var SoortNederlandsReisdocument = {
    titel: 'Soort Nederlands Reisdocument',
    resourceNaam: 'SoortNederlandsReisdocument',
    resourceUrl: 'soortnederlandsreisdocument/:id',
    loaderNaam: 'SoortNederlandsReisdocumentLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Soort Nederlands Reisdocument', display: 'code', sort: 'code'},
        {name: 'Omschrijving', display: 'omschrijving', sort: 'omschrijving'},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/soortnederlandsreisdocument/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("SoortNederlandsReisdocumentConfig", SoortNederlandsReisdocument);
beheerConfiguratie.setup(SoortNederlandsReisdocument);

var SoortPartij = {
    titel: 'Soort Partij',
    resourceNaam: 'SoortPartij',
    resourceUrl: 'soortpartij/:id',
    loaderNaam: 'SoortPartijLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Soort Partij', display: 'naam', sort: 'naam', required: true},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/soortpartij/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("SoortPartijConfig", SoortPartij);
beheerConfiguratie.setup(SoortPartij);

var SoortVrijBericht = {
    titel: 'Soort Vrij Bericht',
    resourceNaam: 'SoortVrijBericht',
    resourceUrl: 'soortvrijber/:id',
    loaderNaam: 'SoortVrijBerichtLoader',
    kolommen: [{name: 'Id', display: 'id', readonly: true,},
        {name: 'Soort Vrij Bericht', display: 'naam', sort: 'naam', required: true},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/soortvrijber/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("SoortVrijBerichtConfig", SoortVrijBericht);
beheerConfiguratie.setup(SoortVrijBericht);

var SoortPartijOnderzoek = {
    titel: 'Soort Partij Onderzoek',
    resourceNaam: 'SoortPartijOnderzoek',
    resourceUrl: 'soortpartijonderzoek/:id',
    loaderNaam: 'SoortPartijOnderzoekLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Partij', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/soortpartijonderzoek/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortPartijOnderzoekConfig", SoortPartijOnderzoek);
beheerConfiguratie.setup(SoortPartijOnderzoek);

var SoortPersoon = {
    titel: 'Soort Persoon',
    resourceNaam: 'SoortPersoon',
    resourceUrl: 'soortpersoon/:id',
    loaderNaam: 'SoortPersoonLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Soort Persoon', display: 'naam'},
        {name: 'Datum ingang', display: 'datumAanvangGeldigheid', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEindeGeldigheid', type: 'datumonbekend'},
    ],
    listUri: '/soortpersoon/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortPersoonConfig", SoortPersoon);
beheerConfiguratie.setup(SoortPersoon);

var SoortPersoonOnderzoek = {
    titel: 'Soort Persoon Onderzoek',
    resourceNaam: 'SoortPersoonOnderzoek',
    resourceUrl: 'soortpersoononderzoek/:id',
    loaderNaam: 'SoortPersoonOnderzoekLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Persoon', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/soortpersoononderzoek/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortPersoonOnderzoekConfig", SoortPersoonOnderzoek);
beheerConfiguratie.setup(SoortPersoonOnderzoek);

var SoortRelatie = {
    titel: 'Soort Relatie',
    resourceNaam: 'SoortRelatie',
    resourceUrl: 'soortrelatie/:id',
    loaderNaam: 'SoortRelatieLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'code', display: 'code'},
        {name: 'Soort Relatie', display: 'naam'},
    ],
    listUri: '/soortrelatie/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortRelatieConfig", SoortRelatie);
beheerConfiguratie.setup(SoortRelatie);

var StatusOnderzoek = {
    titel: 'Status Onderzoek',
    resourceNaam: 'StatusOnderzoek',
    resourceUrl: 'statusonderzoek/:id',
    loaderNaam: 'StatusOnderzoekLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Status Onderzoek', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/statusonderzoek/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("StatusOnderzoekConfig", StatusOnderzoek);
beheerConfiguratie.setup(StatusOnderzoek);

var Stelsel = {
    titel: 'Stelsel',
    resourceNaam: 'Stelsel',
    resourceUrl: 'stelsel/:id',
    loaderNaam: 'StelselLoader',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Stelsel', display: 'naam'},
    ],
    listUri: '/stelsel/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("StelselConfig", Stelsel);
beheerConfiguratie.setup(Stelsel);

var Voorvoegsel = {
    titel: 'Voorvoegsel',
    resourceNaam: 'Voorvoegsel',
    resourceUrl: 'voorvoegsel/:id',
    loaderNaam: 'VoorvoegselLoader',
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Voorvoegsel', display: 'voorvoegsel', sort: 'voorvoegselSleutel.voorvoegsel', required: true, maxlength: 10},
        {name: 'Scheidingsteken', display: 'scheidingsteken', sort: 'voorvoegselSleutel.scheidingsteken', required: true, maxlength: 1, trim: false},
    ],
    listUri: '/voorvoegsel/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("VoorvoegselConfig", Voorvoegsel);
beheerConfiguratie.setup(Voorvoegsel);
