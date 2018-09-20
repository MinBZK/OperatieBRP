var migblokConfig = angular.module('MigblokConfig', []);
var RedenBlokkering = {
	titel: 'Reden Blokkering (Statisch Stamgegeven)',
	resourceNaam: 'RedenBlokkering',
	resourceUrl: 'redenblokkering/:id',
	loaderNaam: 'RedenBlokkeringLoader',
	kolommen: [
	           { name:'#', display:'ordinal'},
	           { name:'Reden Blokkering', display:'naam'},
	           ],
	listUri: '/redenblokkering/list',
	listTemplateUrl: 'views/generic/list.html',
    geenDetail: true
};
migblokConfig.value("RedenBlokkeringConfig", RedenBlokkering);
beheerConfiguratie.setup(RedenBlokkering);
