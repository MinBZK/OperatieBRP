var istConfig = angular.module('IstConfig', []);
var Autorisatietabel = {
	titel: 'Autorisatietabel',
	resourceNaam: 'Autorisatietabel',
	resourceUrl: 'autorisatietabel/:id',
	loaderNaam: 'AutorisatietabelLoader',
	kolommen: [{ name:'Autorisatietabel (TODO)', display:''}],
	listUri: '/autorisatietabel/list',
	listTemplateUrl: 'views/generic/list.html',
    readonly: true
};
istConfig.value("AutorisatietabelConfig", Autorisatietabel);
beheerConfiguratie.setup(Autorisatietabel);
