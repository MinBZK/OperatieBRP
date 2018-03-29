var berConfig = angular.module('BerConfig', []);
var Bijhoudingsresultaat = {
    titel: 'Bijhoudingsresultaat',
    resourceNaam: 'Bijhoudingsresultaat',
    resourceUrl: 'bijhoudingsresultaat/:id',
    loaderNaam: 'BijhoudingsresultaatLoader',
    listUri: '/bijhoudingsresultaat/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
               { name:'Id', display: 'id'},
               { name:'Bijhoudingsresultaat', display:'naam'},
               { name:'Omschrijving', display:'omschrijving'},
               ],
    geenDetail: true
};
berConfig.value("BijhoudingsresultaatConfig", Bijhoudingsresultaat);
beheerConfiguratie.setup(Bijhoudingsresultaat);

var Bijhoudingssituatie = {
    titel: 'Bijhoudingssituatie',
    resourceNaam: 'Bijhoudingssituatie',
    resourceUrl: 'bijhoudingssituatie/:id',
    loaderNaam: 'BijhoudingssituatieLoader',
    listUri: '/bijhoudingssituatie/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
               { name:'Id', display: 'id'},
               { name:'Bijhoudingssituatie', display:'naam'},
               { name:'Omschrijving', display:'omschrijving'},
               ],
    geenDetail: true
};
berConfig.value("BijhoudingssituatieConfig", Bijhoudingssituatie);
beheerConfiguratie.setup(Bijhoudingssituatie);

var Richting = {
    titel: 'Richting',
    resourceNaam: 'Richting',
    resourceUrl: 'richting/:id',
    loaderNaam: 'RichtingLoader',
    listUri: '/richting/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
               { name:'Id', display: 'id'},
               { name:'Richting', display:'naam'},
               { name:'Omschrijving', display:'omschrijving'},
    ],
    geenDetail: true
};
berConfig.value("RichtingConfig", Richting);
beheerConfiguratie.setup(Richting);

var SoortBericht = {
    titel: 'Soort Bericht',
    resourceNaam: 'SoortBericht',
    resourceUrl: 'soortbericht/:id',
    loaderNaam: 'SoortBerichtLoader',
    listUri: '/soortbericht/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
               { name:'Id', display: 'id'},
               { name:'Soort Bericht', display:'identifier'},
               { name:'Stelsel', display: 'koppelvlak.stelsel'},
               { name:'Koppelvlak', display: 'koppelvlak.naam'},
               { name:'Module', display:'module.naam'},
               { name:'Omschrijving', display:'module.omschrijving'},
               { name:'Datum aanvang geldigheid', display:'datumAanvangGeldigheid', type:'datumonbekend' },
               { name:'Datum einde geldigheid', display:'datumEindeGeldigheid', type:'datumonbekend' },
    ],
    geenDetail: true
};
berConfig.value("SoortBerichtConfig", SoortBericht);
beheerConfiguratie.setup(SoortBericht);

var SoortSynchronisatie = {
    titel: 'Soort Synchronisatie',
    resourceNaam: 'SoortSynchronisatie',
    resourceUrl: 'soortsynchronisatie/:id',
    loaderNaam: 'SoortSynchronisatieLoader',
    listUri: '/soortsynchronisatie/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
               { name:'Id', display: 'id'},
               { name:'Soort Synchronisatie', display:'naam'},
               { name:'Omschrijving', display:'omschrijving'},
    ],
    geenDetail: true
};
berConfig.value("SoortSynchronisatieConfig", SoortSynchronisatie);
beheerConfiguratie.setup(SoortSynchronisatie);

var Verantwoording = {
    titel: 'Verantwoording',
    resourceNaam: 'Verantwoording',
    resourceUrl: 'verantwoording/:id',
    loaderNaam: 'VerantwoordingLoader',
    kolommen: [
        { name: 'Id', display: 'id', },
        { name: 'Naam', display: 'naam'},
        { name: 'Omschrijving', display: 'omschrijving'},
    ],
    listUri: '/verantwoording/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
berConfig.value("VerantwoordingConfig", Verantwoording);
beheerConfiguratie.setup(Verantwoording);

var VerantwoordingCategorie = {
    titel: 'Verantwoording Categorie',
    resourceNaam: 'VerantwoordingCategorie',
    resourceUrl: 'verantwoordingcategorie/:id',
    loaderNaam: 'VerantwoordingCategorieLoader',
    kolommen: [
        { name: 'Id', display: 'id'},
        { name: 'Code', display: 'code'},
        { name: 'Omschrijving', display: 'naam'},
        ],
    listUri: '/verantwoordingcategorie/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
berConfig.value("VerantwoordingCategorieConfig", VerantwoordingCategorie);
beheerConfiguratie.setup(VerantwoordingCategorie);

var Verwerkingsresultaat = {
    titel: 'Verwerkingsresultaat',
    resourceNaam: 'Verwerkingsresultaat',
    resourceUrl: 'verwerkingsresultaat/:id',
    loaderNaam: 'VerwerkingsresultaatLoader',
    listUri: '/verwerkingsresultaat/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Verwerkingsresultaat', display:'naam'},
        { name:'Omschrijving', display:'omschrijving'},
    ],
    geenDetail: true
};
berConfig.value("VerwerkingsresultaatConfig", Verwerkingsresultaat);
beheerConfiguratie.setup(Verwerkingsresultaat);

var Verwerkingswijze = {
    titel: 'Verwerkingswijze',
    resourceNaam: 'Verwerkingswijze',
    resourceUrl: 'verwerkingswijze/:id',
    loaderNaam: 'VerwerkingswijzeLoader',
    listUri: '/verwerkingswijze/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Verwerkingswijze', display:'naam'},
        { name:'Omschrijving', display:'omschrijving'},
    ],
    geenDetail: true
};
berConfig.value("VerwerkingswijzeConfig", Verwerkingswijze);
beheerConfiguratie.setup(Verwerkingswijze);
