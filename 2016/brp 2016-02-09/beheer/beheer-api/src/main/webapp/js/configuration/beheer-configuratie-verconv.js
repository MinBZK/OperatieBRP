var verconvConfig = angular.module('VerconvConfig', []);
var LO3BerichtenBron = {
	titel: 'LO3 Berichten Bron (Statisch Stamgegeven)',
	resourceNaam: 'LO3BerichtenBron',
	resourceUrl: 'lo3berichtenbron/:id',
	loaderNaam: 'LO3BerichtenBronLoader',
	kolommen: [
	           { name:'#', display:'ordinal'},
	           { name:'LO3 Berichten Bron', display:'naam'},
	           ],
	listUri: '/lo3berichtenbron/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("LO3BerichtenBronConfig", LO3BerichtenBron);
beheerConfiguratie.setup(LO3BerichtenBron);

var LO3CategorieMelding = {
	titel: 'LO3 Categorie Melding (Statisch Stamgegeven)',
	resourceNaam: 'LO3CategorieMelding',
	resourceUrl: 'lo3categoriemelding/:id',
	loaderNaam: 'LO3CategorieMeldingLoader',
	kolommen: [
	           { name:'#', display:'ordinal'},
	           { name:'LO3 Categorie Melding', display:'naam'},
	           ],
	listUri: '/lo3categoriemelding/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("LO3CategorieMeldingConfig", LO3CategorieMelding);
beheerConfiguratie.setup(LO3CategorieMelding);

var LO3Severity = {
	titel: 'LO3 Severity (Statisch Stamgegeven)',
	resourceNaam: 'LO3Severity',
	resourceUrl: 'lo3severity/:id',
	loaderNaam: 'LO3SeverityLoader',
	kolommen: [
	           { name:'#', display:'ordinal'},
	           { name:'LO3 Severity', display:'naam'},
	           ],
	listUri: '/lo3severity/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("LO3SeverityConfig", LO3Severity);
beheerConfiguratie.setup(LO3Severity);

var LO3SoortAanduidingOuder = {
		titel: 'LO3 Soort Aanduiding Ouder (Statisch Stamgegeven)',
		resourceNaam: 'LO3SoortAanduidingOuder',
		resourceUrl: 'lo3soortaanduidingouder/:id',
		loaderNaam: 'LO3SoortAanduidingOuderLoader',
		kolommen: [
		           { name:'#', display:'ordinal'},
		           { name:'Naam', display:'naam'},
		           ],
		listUri: '/lo3soortaanduidingouder/list',
		listTemplateUrl: 'views/generic/list.html',
	    geenDetail: true
	};
verconvConfig.value("LO3SoortAanduidingOuderConfig", LO3SoortAanduidingOuder);
beheerConfiguratie.setup(LO3SoortAanduidingOuder);

var LO3SoortMelding = {
	titel: 'LO3 Soort Melding (Statisch Stamgegeven)',
	resourceNaam: 'LO3SoortMelding',
	resourceUrl: 'lo3soortmelding/:id',
	loaderNaam: 'LO3SoortMeldingLoader',
	kolommen: [
	           { name:'#', display:'ordinal'},
	           { name:'Code', display:'code'},
	           { name:'LO3 Soort Melding', display:'omschrijving'},
	           { name:'LO3 Categorie Melding', display:'categorieMelding.naam'},
	           ],
	listUri: '/lo3soortmelding/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
verconvConfig.value("LO3SoortMeldingConfig", LO3SoortMelding);
beheerConfiguratie.setup(LO3SoortMelding);
