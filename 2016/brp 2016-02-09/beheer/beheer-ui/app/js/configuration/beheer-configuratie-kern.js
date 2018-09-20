var kernConfig = angular.module('KernConfig', []);
var AanduidingInhoudingVermissingReisdocument = {
  titel: 'Aanduiding Inhouding Vermissing Reisdocument',
  resourceNaam: 'AanduidingInhoudingVermissingReisdocument',
  resourceUrl: 'aanduidinginhoudingvermissingreisdocument/:id',
  loaderNaam: 'AanduidingInhoudingVermissingReisdocumentLoader',
  kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
         { name: 'Code', display: 'Code', sort:'code', type:'text', maxlength:1, required:true, },
         { name: 'Naam', display: 'Naam', sort:'naam', type:'text', maxlength:80, required:true, },
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
  kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
         { name: 'Code', display: 'Code', sort:'code', required:true, maxlength:5, },
         { name: 'Omschrijving', display: 'Omschrijving', sort:'omschrijving', required:true, maxlength:250 },
         { name: 'Datum ingang', display: 'Datum ingang', type: 'datumonbekend'},
         { name: 'Datum einde' , display: 'Datum einde', type: 'datumonbekend'},
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
  kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
         { name: 'Code', display: 'Code', sort:'code', required:true, maxlength:1, },
         { name: 'Naam', display: 'Naam', sort:'naam', required:true, maxlength:80, },
         { name: 'Omschrijving', display: 'Omschrijving', sort:'omschrijving', required:true, maxlength:250, },
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
  kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
         { name: 'Code', display: 'Code', sort:'code',  required:true, maxlength:1, },
         { name: 'Naam mannelijk', display:'naamMannelijk', sort:'naamMannelijk', required:true,  maxlength:80, },
         { name: 'Naam vrouwelijk', display:'naamVrouwelijk', sort:'naamVrouwelijk', required:true,  maxlength:80, },
            ],
  viewTemplateUrl: 'views/generic/view.html',
  geenDetail: false
};
kernConfig.value("AdellijkeTitelConfig", AdellijkeTitel);
beheerConfiguratie.setup(AdellijkeTitel);

var AutoriteittypeVanAfgifteReisdocument = {
  titel: 'Autoriteittype van Afgifte Reisdocument',
  resourceNaam: 'AutoriteittypeVanAfgifteReisdocument',
  resourceUrl: 'autoriteittypevanafgiftereisdocument/:id',
  loaderNaam: 'AutoriteittypeVanAfgifteReisdocumentLoader',
  kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
         { name: 'Code', display: 'Code', sort:'code', required:true, maxlength:2, },
         { name: 'Autoriteittype van Afgifte Reisdocument', display: 'Naam', sort:'naam', required:true, maxlength:80, },
         { name: 'Datum ingang', display: 'Datum ingang', type: 'datumonbekend'},
         { name: 'Datum einde' , display: 'Datum einde', type: 'datumonbekend'},
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
  kolommen: [{ name: '#', display: 'ordinal', },
         { name: 'Code', display: 'code', },
         { name: 'Naam', display: 'naam',  },
         { name: 'Omschrijving', display: 'omschrijving', },
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
  kolommen: [{ name: '#', display: 'ordinal', },
         { name: 'Burgerzaken Module', display: 'naam',  },
         { name: 'Omschrijving', display: 'omschrijving', },
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
  kolommen: [{ name: '#', display: 'ordinal', },
         { name: 'Categorie Administratieve Handeling', display: 'naam',  },
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
  kolommen: [{ name: '#', display: 'ordinal', },
         { name: 'Categorie Personen', display: 'naam',  },
         { name: 'Omschrijving', display: 'omschrijving', },
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
	kolommen: [{ name: 'Id', display: 'iD', },
	           { name: 'Naam', display: 'Naam', },
	           { name: 'Soort', display: 'soort.naam', },
	           { name: 'Element Naam', display: 'elementNaam', },
	           { name: 'Objecttype', display: 'objecttype.Naam' },
	           { name: 'Groep', display: 'groep.Naam', toonOpLijst:false, },
	           { name: 'Volgnummer', display: 'volgnummer', toonOpLijst:false, },
	           { name: 'Alias van', display: 'aliasVan', toonOpLijst:false, },
	           { name: 'Expressie', display: 'expressie', toonOpLijst:false, },
	           { name: 'Autorisatie', display: 'autorisatie.Naam', toonOpLijst:false, },
	           { name: 'Tabel', display: 'tabel.naam', toonOpLijst:false, },
	           { name: 'Identificatie database', display: 'identificatieDatabase', toonOpLijst:false, },
	           { name: 'Historie tabel', display: 'hisTabel.Naam', toonOpLijst:false, },
	           { name: 'Historie identificatie database', display: 'hisIdentifierDatabase', toonOpLijst:false, },
	           { name: 'Leveren als stamgegeven', display: 'leverenAlsStamgegeven', type:'checkboxJaNee', toonOpLijst:false, },
	           { name: 'Datum Aanvang Geldigheid', display: 'Datum ingang', type:'datumonbekend', toonOpLijst:false, },
	           { name: 'Datum Einde Geldigheid', display: 'Datum einde', type:'datumonbekend', toonOpLijst:false, },
	           ],
	listUri: '/element/list',
	listTemplateUrl: 'views/generic/list.html',
    geenNieuw: true,
    readonly: true,
};
kernConfig.value("ElementConfig", Element);
beheerConfiguratie.setup(Element);

var FunctieAdres = {
	titel: 'Functie Adres',
	resourceNaam: 'FunctieAdres',
	resourceUrl: 'functieadres/:id',
	loaderNaam: 'FunctieAdresLoader',
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code', },
	           { name: 'Functie Adres', display: 'naam',  },
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
	kolommen: [{ name: 'Id', display: 'iD', readonly:true, },
	           { name: 'Gemeente', display: 'Naam', required:true, maxlength:80, },
	           { name: 'Code', display: 'Code', required:true, maxlength:5, },
	           { name: 'Partij', display: 'Partij', bron: 'Partij', type:'selectRef', required:true },
	           { name: 'Voortzettende gemeente', display: 'Voortzettende gemeente', bron: 'Gemeente', type:'selectRef', },
	           { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend', },
	           { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend', },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code', },
	           { name: 'Geslachtsaanduiding', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
	              ],
	listUri: '/geslachtsaanduiding/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("GeslachtsaanduidingConfig", Geslachtsaanduiding);
beheerConfiguratie.setup(Geslachtsaanduiding);

var LandGebied = {
	titel: 'Land/Gebied',
	resourceNaam: 'LandGebied',
	resourceUrl: 'landgebied/:id',
	loaderNaam: 'LandGebiedLoader',
	kolommen: [{ name: 'Id', display: 'iD', readonly:true, },
	           { name: 'Code', display: 'Code', required:true, type: 'nummer', maxlength:5, },
	           { name: 'Land/Gebied', display: 'Naam', required:true, maxlength:80 },
	           { name: 'ISO-31661-Alpha-2', display: 'iso31661Alpha2', maxlength:2 },
	           { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend', },
	           { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend', },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code', },
	           { name: 'Naamgebruik', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code', },
	           { name: 'Nadere Bijhoudingsaard', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
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
	kolommen: [{ name: 'Id', display: 'iD', readonly:true, },
	           { name: 'Code', display: 'Code', required:true, type: 'nummer', maxlength:5, },
	           { name: 'Nationaliteit', display: 'Naam', required:true, maxlength:80, },
	           { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend', },
	           { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend',  },
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
	kolommen: [{ name: 'Id', display: 'iD', readonly: true, },
	           { name: 'Code', display: 'Code', type: 'nummer', sort: 'code', filter: 'filterCode', required: true, minlength: 6, maxlength: 6, readonly: true, nieuwReadonly: false },
	           { name: 'Naam', display: 'Naam', sort: 'naam', filter: 'filterNaam', required: true, maxlength: 80, readonly: true, nieuwReadonly: false, breedte: 4 },
	           { name: 'Soort partij', display: 'Soort', type:'selectRef', bron: "SoortPartij", filter: "filterSoort", breedte: 2 },
	           { name: 'Datum ingang', display: 'Datum ingang', sort: 'datumIngang', filter: 'filterDatumIngang', type:'datumonbekend', required: true, readonly: false },
	           { name: 'Datum einde', display: 'Datum einde', sort: 'datumEinde', filter: 'filterDatumEinde', type:'datumonbekend' },
	           { name: 'Verstrekkingsbeperking mogelijk?', display: 'Verstrekkingsbeperking mogelijk?', filter: 'filterIndicatie', type:'checkboxJaNee' },
	           { name: 'Automatisch fiatteren?', display: 'Automatisch fiatteren?', type:'checkboxJaNee' },
	           { name: 'Datum overgang naar BRP', display: 'Datum overgang naar BRP', type:'datumonbekend' },
	              ],
	listUri: '/partijen',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: false

};
kernConfig.value("PartijConfig", Partij);
beheerConfiguratie.setup(Partij);

var PartijRol = {
    titel: 'PartijRol',
    resourceNaam: 'PartijRol',
    resourceUrl: 'partijrol/:id',
    loaderNaam: 'PartijRolLoader',
    vastebreedte: true,
    kolommen: [{ name: 'Id', display: 'iD', readonly: true, },
               { name: 'Partij', display: 'Partij', filter: 'partij', type:'selectRef', bron: "Partij", breedte: 2 },
               { name: 'Rol', display: 'Rol', type:'select', bron: "Rol", breedte: 2 },
               { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend', maxlength:8 , required: true},
	       { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend', maxlength:8 }
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
	kolommen: [{ name: 'Id', display: 'iD', readonly:true, },
	           { name: 'Code', display: 'Code', required:true, maxlength:4, type: 'nummer', },
	           { name: 'Plaats', display: 'Naam', required:true, maxlength:80 },
	           { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend', pattern: '(0|\\d{8})', maxlength:8 },
	           { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend', pattern: '(0|\\d{8})', maxlength:8 },
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
  kolommen: [{ name: 'ID', display: 'iD', filter:'filterId', readonly:true, },
         { name: 'Code', display: 'Code', sort:'code', filter:'filterCode', required:true, maxlength:1, },
         { name: 'Naam mannelijk', display:'naamMannelijk', sort:'naamMannelijk', required:true, maxlength:80, },
         { name: 'Naam vrouwelijk', display:'naamVrouwelijk', sort:'naamVrouwelijk', required:true, maxlength:80, },
            ],
  viewTemplateUrl: 'views/generic/view.html',
  geenDetail: false
};
kernConfig.value("PredicaatConfig", Predicaat);
beheerConfiguratie.setup(Predicaat);

var Rechtsgrond = {
	titel: 'Rechtsgrond',
	resourceNaam: 'Rechtsgrond',
	resourceUrl: 'rechtsgrond/:id',
	loaderNaam: 'RechtsgrondLoader',
	kolommen: [{ name: 'Id', display: 'iD', readonly: true, },
	           { name: 'Code', display: 'Code', required: true, type: 'nummer', },
	           { name: 'Soort', display: 'soort', required: true, bron: 'SoortRechtsgrond', type: 'select', },
	           { name: 'Omschrijving', display: 'Omschrijving', required: true, maxlength: 250, },
	           { name: 'Indicatie leidt tot strijdigheid', display: 'indicatieLeidtTotStrijdigheid', type:'checkbox', },
	           { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend', },
	           { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend', },
	              ],
	listUri: '/rechtsgrond/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("RechtsgrondConfig", Rechtsgrond);
beheerConfiguratie.setup(Rechtsgrond);

var RedenEindeRelatie = {
	titel: 'Reden Einde Relatie',
	resourceNaam: 'RedenEindeRelatie',
	resourceUrl: 'redeneinderelatie/:id',
	loaderNaam: 'RedenEindeRelatieLoader',
	kolommen: [{ name: 'Id', display: 'iD', readonly:true, },
	           { name: 'Reden Einde Relatie', display: 'Code', required:true, maxlength:1, },
	           { name: 'Omschrijving', display: 'Omschrijving', required:true, maxlength:250, },
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
	kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
	           { name: 'Code', display: 'Code', required:true, maxlength:5, type: 'nummer', },
	           { name: 'Reden Verkrijging NL Nationaliteit', display: 'Omschrijving', required:true, maxlength:250, },
	           { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend', },
	           { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend', },
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
	kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
	           { name: 'Code', display: 'Code', required:true, maxlength:5, type: 'nummer', },
	           { name: 'Reden Verlies NL Nationaliteit', display: 'Omschrijving', required:true, maxlength:250, },
	           { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend', },
	           { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend', },
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
	kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
	           { name: 'Code', display: 'Code', required:true, maxlength:1, },
	           { name: 'Reden Wijziging Verblijf', display: 'Naam', required:true, maxlength:80, },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code',  },
	           { name: 'Omschrijving', display: 'omschrijving', },
	           { name: 'Specificatie', display: 'specificatie', },
	           { name: 'Property Melding Paden', display: 'propertyMeldingPaden', },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Rol', display: 'naam',  },
	           { name: 'Datum ingang', display: 'datumAanvangGeldigheid', type:'datumonbekend'  },
	           { name: 'Datum einde', display: 'datumEindeGeldigheid', type:'datumonbekend' },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Soort Actie', display: 'naam',  },
	              ],
	listUri: '/soortactie/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortActieConfig", SoortActie);
beheerConfiguratie.setup(SoortActie);

var SoortAdministratieveHandeling = {
	titel: 'Soort Administratieve Handeling',
	resourceNaam: 'SoortAdministratieveHandeling',
	resourceUrl: 'soortadministratievehandeling/:id',
	loaderNaam: 'SoortAdministratieveHandelingLoader',
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code',  },
	           { name: 'Soort Administratieve Handeling', display: 'naam',  },
	           { name: 'Categorie Administratieve Handeling', display: 'categorieAdministratieveHandeling.naam',  },
	           { name: 'Burgerzaken Module', display: 'module.naam',  },
	           { name: 'Alias', display: 'alias',  },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code',  },
	           { name: 'Soort Betrokkenheid', display: 'naam',  },
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
	kolommen: [{ name: 'Id', display: 'iD', readonly:true, },
	           { name: 'Soort Document', display: 'Naam', required:true, maxlength:80, },
	           { name: 'Omschrijving', display: 'Omschrijving', required:true, maxlength:250, },
	           { name: 'Rangorde', display: 'rangorde', type: 'nummer', },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Soort Element', display: 'naam',  },
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
		kolommen: [{ name: '#', display: 'ordinal', },
		           { name: 'Soort Element Autorisatie', display: 'naam',  },
		           { name: 'Omschrijving', display: 'omschrijving',  },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Soort Indicatie', display: 'naam',  },
	           { name: 'Materiele historie van toepassing?', display:'indicatieMaterieleHistorieVanToepassing', type:'checkboxJaNee', },
	              ],
	listUri: '/soortindicatie/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortIndicatieConfig", SoortIndicatie);
beheerConfiguratie.setup(SoortIndicatie);

var SoortMigratie = {
	titel: 'Soort Migratie',
	resourceNaam: 'SoortMigratie',
	resourceUrl: 'soortmigratie/:id',
	loaderNaam: 'SoortMigratieLoader',
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code',  },
	           { name: 'Soort Migratie', display: 'naam',  },
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
	kolommen: [{ name: 'Id', display: 'iD', readonly: true, },
	           { name: 'Soort Nederlands Reisdocument', display: 'Code',  },
	           { name: 'Omschrijving', display: 'Omschrijving',  },
	           { name: 'Datum ingang', display: 'Datum ingang', type:'datumonbekend' },
	           { name: 'Datum einde', display: 'Datum einde', type:'datumonbekend' },
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
	kolommen: [{ name: 'id', display: 'iD', readonly: true,},
	           { name: 'Soort Partij', display: 'Naam',  },
               { name: 'Datum ingang', display: 'Datum ingang', type: 'datumonbekend'},
               { name: 'Datum einde' , display: 'Datum einde', type: 'datumonbekend'},
              ],
	listUri: '/soortpartij/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("SoortPartijConfig", SoortPartij);
beheerConfiguratie.setup(SoortPartij);

var SoortPartijOnderzoek = {
	titel: 'Soort Partij Onderzoek',
	resourceNaam: 'SoortPartijOnderzoek',
	resourceUrl: 'soortpartijonderzoek/:id',
	loaderNaam: 'SoortPartijOnderzoekLoader',
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Soort Partij', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Code', display: 'code',  },
	           { name: 'Soort Persoon', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
	           { name: 'Datum ingang', display: 'datumAanvangGeldigheid', type:'datumonbekend' },
	           { name: 'Datum einde', display: 'datumEindeGeldigheid', type:'datumonbekend' },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Soort Persoon', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
              ],
	listUri: '/soortpersoononderzoek/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortPersoonOnderzoekConfig", SoortPersoonOnderzoek);
beheerConfiguratie.setup(SoortPersoonOnderzoek);

var SoortRechtsgrond = {
	titel: 'Soort Rechtsgrond',
	resourceNaam: 'SoortRechtsgrond',
	resourceUrl: 'soortrechtsgrond/:id',
	loaderNaam: 'SoortRechtsgrondLoader',
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Soort Rechtsgrond', display: 'naam',  },
              ],
	listUri: '/soortrechtsgrond/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("SoortRechtsgrondConfig", SoortRechtsgrond);
beheerConfiguratie.setup(SoortRechtsgrond);

var SoortRelatie = {
	titel: 'Soort Relatie',
	resourceNaam: 'SoortRelatie',
	resourceUrl: 'soortrelatie/:id',
	loaderNaam: 'SoortRelatieLoader',
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'code', display: 'code', },
	           { name: 'Soort Relatie', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
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
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Status Onderzoek', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
              ],
	listUri: '/statusonderzoek/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("StatusOnderzoekConfig", StatusOnderzoek);
beheerConfiguratie.setup(StatusOnderzoek);

var StatusTerugmelding = {
	titel: 'Status Terugmelding',
	resourceNaam: 'StatusTerugmelding',
	resourceUrl: 'statusterugmelding/:id',
	loaderNaam: 'StatusTerugmeldingLoader',
	kolommen: [{ name: '#', display: 'ordinal', },
	           { name: 'Status Terugmelding', display: 'naam',  },
	           { name: 'Omschrijving', display: 'omschrijving',  },
              ],
	listUri: '/statusterugmelding/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
kernConfig.value("StatusTerugmeldingConfig", StatusTerugmelding);
beheerConfiguratie.setup(StatusTerugmelding);

var Voorvoegsel = {
	titel: 'Voorvoegsel',
	resourceNaam: 'Voorvoegsel',
	resourceUrl: 'voorvoegsel/:id',
	loaderNaam: 'VoorvoegselLoader',
	kolommen: [{ name: 'ID', display: 'iD', readonly:true, },
	           { name: 'Voorvoegsel', display: 'voorvoegsel', required:true, maxlength:10, },
	           { name: 'Scheidingsteken', display: 'scheidingsteken', required:true, maxlength:1, trim:false, },
              ],
	listUri: '/voorvoegsel/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
kernConfig.value("VoorvoegselConfig", Voorvoegsel);
beheerConfiguratie.setup(Voorvoegsel);
