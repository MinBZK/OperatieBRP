var verconvConfig = angular.module('VerconvConfig', []);
var Lo3BerichtenBron = {
    titel: 'LO3 Berichten Bron',
    resourceNaam: 'Lo3BerichtenBron',
    resourceUrl: 'lo3berichtenbron/:id',
    loaderNaam: 'Lo3BerichtenBronLoader',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Lo3 Berichten Bron', display:'naam'},
    ],
    listUri: '/lo3berichtenbron/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("Lo3BerichtenBronConfig", Lo3BerichtenBron);
beheerConfiguratie.setup(Lo3BerichtenBron);

var Lo3CategorieMelding = {
    titel: 'LO3 Categorie Melding',
    resourceNaam: 'Lo3CategorieMelding',
    resourceUrl: 'lo3categoriemelding/:id',
    loaderNaam: 'Lo3CategorieMeldingLoader',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Lo3 Categorie Melding', display:'naam'},
    ],
    listUri: '/lo3categoriemelding/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("Lo3CategorieMeldingConfig", Lo3CategorieMelding);
beheerConfiguratie.setup(Lo3CategorieMelding);

var Lo3Severity = {
    titel: 'LO3 Severity',
    resourceNaam: 'Lo3Severity',
    resourceUrl: 'lo3severity/:id',
    loaderNaam: 'Lo3SeverityLoader',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Lo3 Severity', display:'naam'},
    ],
    listUri: '/lo3severity/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("Lo3SeverityConfig", Lo3Severity);
beheerConfiguratie.setup(Lo3Severity);

var Lo3SoortAanduidingOuder = {
    titel: 'LO3 Soort Aanduiding Ouder',
    resourceNaam: 'Lo3SoortAanduidingOuder',
    resourceUrl: 'lo3soortaanduidingouder/:id',
    loaderNaam: 'Lo3SoortAanduidingOuderLoader',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Ouder', display:'id'},
    ],
    listUri: '/lo3soortaanduidingouder/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("Lo3SoortAanduidingOuderConfig", Lo3SoortAanduidingOuder);
beheerConfiguratie.setup(Lo3SoortAanduidingOuder);

var Lo3SoortMelding = {
    titel: 'LO3 Soort Melding',
    resourceNaam: 'Lo3SoortMelding',
    resourceUrl: 'lo3soortmelding/:id',
    loaderNaam: 'Lo3SoortMeldingLoader',
    kolommen: [
        { name:'Id', display: 'id'},
        { name:'Code', display:'code'},
        { name:'Lo3 Soort Melding', display:'omschrijving'},
        { name:'Lo3 Categorie Melding', display:'categorieMelding.naam'},
    ],
    listUri: '/lo3soortmelding/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("Lo3SoortMeldingConfig", Lo3SoortMelding);
beheerConfiguratie.setup(Lo3SoortMelding);
