var berConfig = angular.module('BerConfig', []);
var Bijhoudingsresultaat = {
	titel: 'Bijhoudingsresultaat',
	resourceNaam: 'Bijhoudingsresultaat',
	resourceUrl: 'bijhoudingsresultaat/:id',
	loaderNaam: 'BijhoudingsresultaatLoader',
	listUri: '/bijhoudingsresultaat/list',
	listTemplateUrl: 'views/generic/list.html',
	kolommen: [
	           { name:'#', display:'ordinal'},
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
	           { name:'#', display:'ordinal'},
	           { name:'Bijhoudingssituatie', display:'naam'},
	           { name:'Omschrijving', display:'omschrijving'},
	           ],
	geenDetail: true
};
berConfig.value("BijhoudingssituatieConfig", Bijhoudingssituatie);
beheerConfiguratie.setup(Bijhoudingssituatie);

var Historievorm = {
	titel: 'Historievorm',
	resourceNaam: 'Historievorm',
	resourceUrl: 'historievorm/:id',
	loaderNaam: 'HistorievormLoader',
	listUri: '/historievorm/list',
	listTemplateUrl: 'views/generic/list.html',
	kolommen: [
	           { name:'#', display:'ordinal'},
	           { name:'Historievorm', display:'naam'},
	           { name:'Omschrijving', display:'omschrijving'},
	],
	geenDetail: true
};
berConfig.value("HistorievormConfig", Historievorm);
beheerConfiguratie.setup(Historievorm);

var Richting = {
	titel: 'Richting',
	resourceNaam: 'Richting',
	resourceUrl: 'richting/:id',
	loaderNaam: 'RichtingLoader',
	listUri: '/richting/list',
	listTemplateUrl: 'views/generic/list.html',
	kolommen: [
	           { name:'#', display:'ordinal'},
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
	           { name:'#', display:'ordinal'},
	           { name:'Soort Bericht', display:'naam'},
	           { name:'Omschrijving', display:'omschrijving'},
	           { name:'Datum aanvang geldigheid', display:'datumAanvangGeldigheid', type:'datumonbekend' },
	           { name:'Datum einde geldigheid', display:'datumEindeGeldigheid', type:'datumonbekend' },
	],
	geenDetail: true
};
berConfig.value("SoortBerichtConfig", SoortBericht);
beheerConfiguratie.setup(SoortBericht);

var SoortMelding = {
	titel: 'Soort Melding',
	resourceNaam: 'SoortMelding',
	resourceUrl: 'soortmelding/:id',
	loaderNaam: 'SoortMeldingtLoader',
	listUri: '/soortmelding/list',
	listTemplateUrl: 'views/generic/list.html',
	kolommen: [
	           { name:'#', display:'ordinal'},
	           { name:'Soort Melding', display:'naam'},
	           { name:'Omschrijving', display:'omschrijving'},
	],
	geenDetail: true
};
berConfig.value("SoortMeldingConfig", SoortMelding);
beheerConfiguratie.setup(SoortMelding);

var SoortSynchronisatie = {
	titel: 'Soort Synchronisatie',
	resourceNaam: 'SoortSynchronisatie',
	resourceUrl: 'soortsynchronisatie/:id',
	loaderNaam: 'SoortSynchronisatieLoader',
	listUri: '/soortsynchronisatie/list',
	listTemplateUrl: 'views/generic/list.html',
	kolommen: [
	           { name:'#', display:'ordinal'},
	           { name:'Soort Synchronisatie', display:'naam'},
	           { name:'Omschrijving', display:'omschrijving'},
	],
	geenDetail: true
};
berConfig.value("SoortSynchronisatieConfig", SoortSynchronisatie);
beheerConfiguratie.setup(SoortSynchronisatie);

var Verwerkingsresultaat = {
	titel: 'Verwerkingsresultaat',
	resourceNaam: 'Verwerkingsresultaat',
	resourceUrl: 'verwerkingsresultaat/:id',
	loaderNaam: 'VerwerkingsresultaatLoader',
	listUri: '/verwerkingsresultaat/list',
	listTemplateUrl: 'views/generic/list.html',
	kolommen: [
	           { name:'#', display:'ordinal'},
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
	           { name:'#', display:'ordinal'},
	           { name:'Verwerkingswijze', display:'naam'},
	           { name:'Omschrijving', display:'omschrijving'},
	],
	geenDetail: true
};
berConfig.value("VerwerkingswijzeConfig", Verwerkingswijze);
beheerConfiguratie.setup(Verwerkingswijze);
