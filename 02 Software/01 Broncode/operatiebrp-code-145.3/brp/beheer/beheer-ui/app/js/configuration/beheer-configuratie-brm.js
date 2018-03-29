var brmConfig = angular.module('BrmConfig', []);

var Regelsituatie = {
    titel: 'Regelsituatie',
    resourceNaam: 'Regelsituatie',
    resourceUrl: 'regelsituatie/:id',
    loaderNaam: 'RegelsituatieLoader',
    kolommen: [
        {name:'Id', display:'id', readonly: true},
        {name:'Regel \\  Soort Bericht', display:'regelSoortBericht', required: true, bron: 'RegelSoortBericht', type: 'select'},
        {name:'Bijhoudingsaard', display:'bijhoudingsaard', bron: 'Bijhoudingsaard', type: 'select'},
        {name:'Nadere bijhoudingsaard', display:'nadereBijhoudingsaard', bron: 'NadereBijhoudingsaard', type: 'select'},
        {name:'Effect', display:'effect', bron: 'Regeleffect', type: 'select'},
        {name:'Actief?', display:'indicatieActief', type:'checkboxJaNee' },
    ],
    listUri: '/regelsituatie/list',
    listTemplateUrl: 'views/generic/list.html'
};
brmConfig.value("RegelsituatieConfig", Regelsituatie);
beheerConfiguratie.setup(Regelsituatie);

var RegelSoortBericht = {
    titel: 'Regel / Soort Bericht',
    resourceNaam: 'RegelSoortBericht',
    resourceUrl: 'regelsoortbericht/:id',
    loaderNaam: 'RegelSoortBerichtLoader',
    kolommen: [
        {name:'Id', display: 'id'},
        {name:'Regel', display:'regel.naam'},
        {name:'Soort Bericht', display:'soortBericht.naam' },
    ],
    listUri: '/regelsoortbericht/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true,
    geenNieuw: true,
};
brmConfig.value("RegelSoortBerichtConfig", RegelSoortBericht);
beheerConfiguratie.setup(RegelSoortBericht);

var Regeleffect = {
    titel: 'Regeleffect',
    resourceNaam: 'Regeleffect',
    resourceUrl: 'regeleffect/:id',
    loaderNaam: 'RegeleffectLoader',
    kolommen: [
        {name:'Id', display: 'id'},
        {name:'Naam', display:'naam'},
        {name:'Omschrijving', display:'omschrijving' },
        {name: 'Datum aanvang geldigheid', display: 'datumAanvangGeldigheid', type:'datumonbekend'},
        {name: 'Datum einde geldigheid', display: 'datumEindeGeldigheid', type:'datumonbekend'},
    ],
    listUri: '/regeleffect/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true,
    geenNieuw: true,
};
brmConfig.value("RegeleffectConfig", Regeleffect);
beheerConfiguratie.setup(Regeleffect);

var SoortRegel = {
    titel: 'Soort Regel',
    sourceNaam: 'SoortRegel',
    resourceUrl: 'soortregel/:id',
    loaderNaam: 'SoortRegelLoader',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Naam', display:'naam'},
        { name:'Omschrijving', display:'omschrijving' },
        { name: 'Datum aanvang geldigheid', display: 'datumAanvangGeldigheid', type:'datumonbekend'},
        { name: 'Datum einde geldigheid', display: 'datumEindeGeldigheid', type:'datumonbekend'},
    ],
    listUri: '/soortregel/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true,
    geenNieuw: true,
};
brmConfig.value("SoortRegelConfig", SoortRegel);
beheerConfiguratie.setup(SoortRegel);
