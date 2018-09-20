var autautConfig = angular.module('AutAutConfig', []);
var AanduidingMedium = {
    titel: 'Aanduiding Medium',
    resourceNaam: 'AanduidingMedium',
    resourceUrl: 'aanduidingmedium/:id',
    loaderNaam: 'AanduidingMediumLoader',
    listUri: '/aanduidingmedium/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Aanduiding Medium', display: 'naam'},
    ],
    geenDetail: true
};
autautConfig.value("AanduidingMediumConfig", AanduidingMedium);
beheerConfiguratie.setup(AanduidingMedium);

var Afleverwijze = {
    titel: 'Afleverwijze',
    resourceNaam: 'Afleverwijze',
    resourceUrl: 'abonnementen/:abonnement/toegangen/:toegang/afleverwijzen/:id',
    kolommen: [
        {name: 'Kanaal', display: 'kanaal', type: 'select', bron: 'Kanaal', required: true, readonly: true, nieuwReadonly: false},
        {name: 'URI', display: 'uri'},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datumonbekend'},
        {name: 'Datum einde', display: 'datumEinde', type: 'datumonbekend'}
    ]
};
autautConfig.value("AfleverwijzeConfig", Afleverwijze);
beheerConfiguratie.setup(Afleverwijze);

var CatalogusOptie = {
    titel: 'Catalogus Optie',
    resourceNaam: 'CatalogusOptie',
    resourceUrl: 'catalogusoptie/:id',
    loaderNaam: 'CatalogusOptieLoader',
    listUri: '/catalogusoptie/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Categorie Dienst', display: 'categorieDienst.naam'},
        {name: 'Effect Afnemerindicaties', display: 'effectAfnemerindicaties.naam'},
        {name: 'Aanduiding Medium', display: 'aanduidingMedium.naam'},
    ],
    geenDetail: true
};
autautConfig.value('CatalogusOptieConfig', CatalogusOptie);
beheerConfiguratie.setup(CatalogusOptie);

var CategorieDienst = {
    titel: 'Categorie Dienst',
    resourceNaam: 'CategorieDienst',
    resourceUrl: 'categoriedienst/:id',
    loaderNaam: 'CategorieDienstLoader',
    listUri: '/categoriedienst/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Categorie Dienst', display: 'naam'},
    ],
    geenDetail: true
};
autautConfig.value('CategorieDienstConfig', CategorieDienst);
beheerConfiguratie.setup(CategorieDienst);

var Dienst = {
    titel: 'Dienst',
    resourceNaam: 'Dienst',
    resourceUrl: 'abonnementen/:abonnement/diensten/:id',
    kolommen: [
        {name: 'Catalogusoptie', display: 'catalogusoptie', type: 'select', bron: 'CatalogusOptie', required: true, readonly: true, nieuwReadonly: false},
        {name: 'Nadere populatiebeperking', display: 'naderePopulatieBeperking', toonOpLijst: false},
        {name: 'Attenderingscriterium', display: 'attenderingscriterium', toonOpLijst: false},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datumonbekend', required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datumonbekend'},
        {name: 'Toestand', display: 'toestand', type: 'select', bron: 'Toestand', required: true},
        {name: 'Eerste selectie datum', display: 'eersteSelectiedatum', type: 'datumonbekend'},
        {name: 'Selectie periode in maanden', display: 'selectieperiodeInMaanden', type: 'nummer'},
        {name: 'Indicatie nadere populatiebeperking volledig geconverteerd', display: 'indicatieNaderePopulatieBeperkingVolledigGeconverteerd', type: 'checkboxJaNee'},
        {name: 'Toelichting', display: 'toelichting', toonOpLijst: false}
    ],
    geenDetail: false
};
autautConfig.value('DienstConfig', Dienst);
beheerConfiguratie.setup(Dienst);

var DatabaseObjectAutAut = {
    titel: 'Database Object AutAut',
    resourceNaam: 'DatabaseObjectAutAut',
    resourceUrl: 'databaseobjectautaut/:id',
    loaderNaam: 'DatabaseObjectAutAutLoader',
    listUri: '/databaseobjectautaut/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Id', display: 'id'},
        {name: 'Naam', display: 'naam'},
        {name: 'Database naam', display: 'databaseNaam'},
        {name: 'Ouder', display: 'ouder.naam'},
    ],
    geenDetail: true
};
autautConfig.value('DatabaseObjectAutAutConfig', DatabaseObjectAutAut);
beheerConfiguratie.setup(DatabaseObjectAutAut);

var EffectAfnemerindicaties = {
    titel: 'Effect Afnemerindicaties',
    resourceNaam: 'EffectAfnemerindicaties',
    resourceUrl: 'effectafnemerindicaties/:id',
    loaderNaam: 'EffectAfnemerindicatiesLoader',
    listUri: '/effectafnemerindicaties/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Effect Afnemerindicaties', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    geenDetail: true
};
autautConfig.value('EffectAfnemerindicatiesConfig', EffectAfnemerindicaties);
beheerConfiguratie.setup(EffectAfnemerindicaties);

var Functie = {
    titel: 'Functie',
    resourceNaam: 'Functie',
    resourceUrl: 'functie/:id',
    loaderNaam: 'FunctieLoader',
    listUri: '/functie/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Functie', display: 'naam'},
    ],
    geenDetail: true
};
autautConfig.value('FunctieConfig', Functie);
beheerConfiguratie.setup(Functie);

var Kanaal = {
    titel: 'Kanaal',
    resourceNaam: 'Kanaal',
    resourceUrl: 'kanaal/:id',
    loaderNaam: 'KanaalLoader',
    listUri: '/kanaal/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Kanaal', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    geenDetail: true
};
autautConfig.value('KanaalConfig', Kanaal);
beheerConfiguratie.setup(Kanaal);

var Leveringsautorisatie = {
    titel: 'Leveringsautorisatie',
    resourceNaam: 'Leveringsautorisatie',
    resourceUrl: 'leveringsautorisaties/:id',
    loaderNaam: 'LeveringsautorisatieLoader',
    contextParam: 'leveringsautorisatie',
    vastebreedte: true,
    kolommen: [
        {name: 'Naam', display: 'naam', sort: 'naam', filter: 'filterNaam', required: true, maxlength: 80, readonly: true, nieuwReadonly: false, breedte: 4},
        {name: 'Datum ingang', display: 'Datum ingang', sort: 'datumIngang', filter: 'filterDatumIngang', type:'datumonbekend', required: true },
        {name: 'Datum einde', display: 'Datum einde', sort: 'datumEinde', filter: 'filterDatumEinde', type: 'datumonbekend'},
        {name: 'Populatiebeperking', display: 'populatieBeperking', maxlength: 50, toonOpLijst: false},
        {name: 'Protocolleringsniveau', display: 'protocolleringsniveau', filter: 'filterProtocollering', type:'select', bron: 'Protocolleringsniveau', required: true, breedte: 2},
        {name: 'Indicatie geblokkeerd', display: 'Indicatie geblokkeerd', type: 'checkboxJaNee'},
        {name: 'Indicatie populatiebeperking volledig geconverteerd', display: 'indicatiePopulatieBeperkingVolledigGeconverteerd', filter: 'filterIndPopbeperkVolledig', type: 'checkboxJaNee'},
        {name: 'Toelichting', display: 'toelichting', toonOpLijst: false},
        {
            name: 'Toegangen',
            display: 'toegangen',
            type: 'inlinelijst',
            bron: 'ToegangLeveringsautorisatie',
            velden: [
                {name: 'Partij', display: 'partij', type: 'select', bron: 'Partij'},
                {name: 'Datum ingang', display: 'datumIngang', type: 'datumonbekend'},
                {name: 'Datum einde', display: 'datumEinde', type: 'datumonbekend'}
            ],
            relatie: 'abonnement',
            toonOpLijst: false
        },
    ],
    listUri: '/leveringsautorisaties',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false,
    blijfOpWijzig: true
};
autautConfig.value("LeveringsautorisatieConfig", Leveringsautorisatie);
beheerConfiguratie.setup(Leveringsautorisatie);

var LO3Rubriek = {
    titel: 'LO3 Rubrieken',
    resourceNaam: 'LO3Rubriek',
    resourceUrl: 'abonnementen/:abonnement/lo3rubrieken/:id',
    loaderNaam: 'LO3RubriekLoader',
    kolommen: [
    ],
    geenDetail: false
};
autautConfig.value('LO3RubriekConfig', LO3Rubriek);
beheerConfiguratie.setup(LO3Rubriek);

var Protocolleringsniveau = {
    titel: 'Protocolleringsniveau',
    resourceNaam: 'Protocolleringsniveau',
    resourceUrl: 'protocolleringsniveau/:id',
    loaderNaam: 'ProtocolleringsniveauLoader',
    listUri: '/protocolleringsniveau/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Code', display: 'code'},
        {name: 'Protocolleringsniveau', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
        {name: 'Datum aanvang geldigheid', display: 'datumAanvangGeldigheid', type:'datumonbekend' },
        {name: 'Datum einde geldigheid', display: 'datumEindeGeldigheid', type:'datumonbekend' },
    ],
    geenDetail: true
};
autautConfig.value('ProtocolleringsniveauConfig', Protocolleringsniveau);
beheerConfiguratie.setup(Protocolleringsniveau);

var SoortAutorisatiebesluit = {
    titel: 'Soort Autorisatiebesluit',
    resourceNaam: 'SoortAutorisatiebesluit',
    resourceUrl: 'soortautorisatiebesluit/:id',
    loaderNaam: 'SoortAutorisatiebesluitLoader',
    listUri: '/soortautorisatiebesluit/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Soort Autorisatiebesluit', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
        {name: 'Datum aanvang geldigheid', display: 'datumAanvangGeldigheid', type:'datumonbekend' },
        {name: 'Datum einde geldigheid', display: 'datumEindeGeldigheid', type:'datumonbekend' },
    ],
    geenDetail: true
};
autautConfig.value('SoortAutorisatiebesluitConfig', SoortAutorisatiebesluit);
beheerConfiguratie.setup(SoortAutorisatiebesluit);

var SoortBevoegdheid = {
    titel: 'Soort Bevoegdheid',
    resourceNaam: 'SoortBevoegdheid',
    resourceUrl: 'soortbevoegdheid/:id',
    loaderNaam: 'SoortBevoegdheidLoader',
    listUri: '/soortbevoegdheid/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Soort Bevoegdheid', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
        {name: 'Datum aanvang geldigheid', display: 'datumAanvangGeldigheid', type:'datumonbekend' },
        {name: 'Datum einde geldigheid', display: 'datumEindeGeldigheid', type:'datumonbekend' },
    ],
    geenDetail: true
};
autautConfig.value('SoortBevoegdheidConfig', SoortBevoegdheid);
beheerConfiguratie.setup(SoortBevoegdheid);

var ToegangLeveringsautorisatie = {
    titel: 'Toegang',
    resourceNaam: 'ToegangLeveringsautorisatie',
    resourceUrl: 'leveringsautorisaties/:leveringsautorisatie/toegangen/:id',
    contextParam: 'toegang',
    kolommen: [
        {name: 'Partij', display: 'partij', type: 'selectRef', bron: 'Partij', required: true, nieuwReadonly: false},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datumonbekend', required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datumonbekend'},
        {
            name: 'Afleverwijzen',
            display: 'afleverwijzen',
            type: 'inlinelijst',
            bron: 'Afleverwijze',
            velden: [
                {name: 'Kanaal', display: 'kanaal', type: 'selectRef', bron: 'Kanaal'},
                {name: 'URI', display: 'uri'},
                {name: 'Datum ingang', display: 'datumIngang', type: 'datumonbekend'},
                {name: 'Datum einde', display: 'datumEinde', type: 'datumonbekend'}
            ],
            relatie: 'toegang',
            toonOpLijst: false
        }
    ],
    geenDetail: false,
    blijfOpWijzig: true
};
autautConfig.value('ToegangLeveringsautorisatieConfig', ToegangLeveringsautorisatie);
beheerConfiguratie.setup(ToegangLeveringsautorisatie);

var Toestand = {
    titel: 'Toestand',
    resourceNaam: 'Toestand',
    resourceUrl: 'toestand/:id',
    loaderNaam: 'ToestandLoader',
    listUri: '/toestand/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: '#', display: 'ordinal'},
        {name: 'Toestand', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
        {name: 'Datum aanvang geldigheid', display: 'datumAanvangGeldigheid', type:'datumonbekend' },
        {name: 'Datum einde geldigheid', display: 'datumEindeGeldigheid', type:'datumonbekend' },
    ],
    geenDetail: true
};
autautConfig.value('ToestandConfig', Toestand);
beheerConfiguratie.setup(Toestand);
