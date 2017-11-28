var migblokConfig = angular.module('MigblokConfig', []);
var Blokkering = {
    titel: 'Blokkering',
    resourceNaam: 'Blokkering',
    resourceUrl: 'blokkering/:id',
    loaderNaam: 'BlokkeringLoader',
    kolommen: [
        { name: 'Id', display: 'id'},
        { name: 'A-nummer', display:'aNummer', sort: 'aNummer', filter: 'filterAnummer'},
        { name: 'Reden blokkering', display: 'redenBlokkering', bron: 'RedenBlokkering', type:'select' },
        { name: 'ISC Proces instantie id', display:'procesInstantieId'},
        { name: 'Code gemeente vestiging', display:'codeGemeenteVestiging'},
        { name: 'Code gemeente registraties', display:'codeGemeenteRegistratie'},
        { name: 'Tijdstip registratie', display:'tijdstipRegistratie'},
    ],
    listUri: '/blokkering/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
migblokConfig.value("BlokkeringConfig", Blokkering);
beheerConfiguratie.setup(Blokkering);

var RedenBlokkering = {
    titel: 'Reden Blokkering',
    resourceNaam: 'RedenBlokkering',
    resourceUrl: 'redenblokkering/:id',
    loaderNaam: 'RedenBlokkeringLoader',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Reden Blokkering', display:'naam'},
    ],
    listUri: '/redenblokkering/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
migblokConfig.value("RedenBlokkeringConfig", RedenBlokkering);
beheerConfiguratie.setup(RedenBlokkering);
