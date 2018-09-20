var beheerConfiguratie = {
	setup: function(config) {
		// jsonview pagina
		if ( !config.jsonPagina) {
			config.jsonPagina = 'views/json/view.html';
		}

        // stel standaard item pagina in
        if ( !config.itemPagina ) {
            if (config.readonly) {
                config.itemPagina = 'views/generic/view.html';
            } else {
                config.itemPagina = 'views/generic/edit.html';
            }
        }

		if (config.geenDetail) {
			config.geenNieuw = true;
			config.cache = true;
		}

		if (config.geenEdit === "undefined") {
			config.geenEdit = false;
		}

		if (config.geenEdit) {
			config.geenNieuw = true;
		}

        //console.log('Setup: ' + config.titel);
		for(var index in config.kolommen) {
            var kolom = config.kolommen[index];
            // id veld is ter voorkoming van naam botsingen. Wordt in de code verder
            // gewijzigd indien nodig. Niet handmatig te zetten.
            kolom.id = kolom.display;

            if (typeof kolom.edit === "undefined") {
                kolom.edit = kolom.display;
            }
            if (config.geenEdit) {
                kolom.readonly = true;
            } else {
	            if (typeof kolom.readonly === "undefined") {
	                kolom.readonly = false;
	            }
            }
            if (typeof kolom.required === "undefined") {
                kolom.required = false;
            }
            if (typeof kolom.type === "undefined") {
                kolom.type = "text";
            }
            if (typeof kolom.toonOpLijst === "undefined") {
                kolom.toonOpLijst = true;
            }
            if (typeof kolom.toonOpBewerken === "undefined") {
                kolom.toonOpBewerken = true;
            }
            if (typeof kolom.nieuwReadonly === "undefined") {
                kolom.nieuwReadonly = kolom.readonly;
            }
			if (typeof kolom.breedte === "undefined") {
				kolom.breedte = 1;
			}
        }

        // Resource
        //console.log('Setup resource: ' + config.resourceNaam + ' -> ' + config.resourceUrl);
        beheerApp.factory(config.resourceNaam, ['$resource', 'instelling', function ($resource, instelling) {
                return {
                    resource: function () {
                        var parameters = {size: instelling.size};
                        if (config.sortering) {
                            parameters.sort = config.sortering;
                        }
                        var methoden = {
							get: {method: 'get', isArray: false},
							query: {method: 'get', isArray: false},
							delete: {method: 'delete', params: {id: '@iD'}},
							lijst: {method: 'get', isArray: true}
						};
						if (config.cache) {
							methoden.get.cache = true;
							methoden.query.cache = true;
						}
						return $resource(
							instelling.api_locatie + config.resourceUrl,
							parameters,
							methoden);
                    }
                };
            }]);

        // Loader
        //console.log('Setup loader: ' + config.loaderNaam);
		if (config.loaderNaam) {
	        beheerApp.factory(config.loaderNaam, [config.resourceNaam, '$q',
				function(Resource, $q) {
					return function() {
	                    var delay = $q.defer();
						Resource.resource().query(function(lijst) { delay.resolve(lijst); });
	                    return delay.promise;
	                };
	            }]);

	        // List view
	        //console.log('Setup list view: ' + config.listUri + ' -> ' + config.listTemplateUrl);
	        beheerApp.config(['$routeProvider',
			  function($routeProvider) {
	                $routeProvider
                        .when(config.listUri, {
                            templateUrl: config.listTemplateUrl,
                            controller: 'ListController',
                            resolve: {
		      					resource: [config.resourceNaam, function(Resource) { return Resource; }],
		      					lijst: config.geenInitieleLijst ? function() { return {}; } : [config.loaderNaam, function(Loader) { return Loader(); }],
                    			item: function() { return null; },
		        				config: function() {
                                    return config;
                                }
                            }
                        });
	            }]);

	        // New Detail view
	        beheerApp.config(['$routeProvider',
			  function($routeProvider) {
	                $routeProvider
                        .when(config.listUri + "/:id", {
                            templateUrl: config.listTemplateUrl,
                            controller: 'ListController',
                            resolve: {
                                resource: [config.resourceNaam, function(Resource) { return Resource; }],
                                lijst: function() { return {}; },
                                item: ['$q', '$route', config.resourceNaam, function($q, $route, Resource) {
									var delay = $q.defer();
								 	Resource.resource().get({id:$route.current.params.id}, function success(result) {
										//console.log("promise item uitgevoerd");
										delay.resolve(result);
									});
									return delay.promise;
								}],
                                config: function() {
                                    return config;
                                }
                            }
                        });
	            }]);
		}
    }
};

beheerConfiguratie.setup({
    titel: 'Personen',
    resourceNaam: 'Persoon',
    resourceUrl: 'persoon/:id',
    loaderNaam: 'PersoonLoader',
    listUri: '/persoon/list',
    listTemplateUrl: 'views/generic/list.html',
	kolommen: [{ name:'ID', display:'iD', sort:'ID', filter:'iD'},
               { name:'Soort', filter:'soort', type:'select', bron:'SoortPersoon'},
               { name:'Soort', display:'Soort'},
	           { name:'Burgerservicenummer', display:'Burgerservicenummer', filter:'bsn', sort:'identificatienummers.burgerservicenummer'},
	           { name:'Administratienummer', display:'Administratienummer', filter:'anr', sort:'identificatienummers.administratienummer'},
               { name:'Afgeleid?', filter:'afgeleid', type:'checkboxJaNee'},
               { name:'Namenreeks?', filter:'namenreeks', type:'checkboxJaNee'},
               { name:'Predicaat', filter:'predicaat', type:'selectCodeMV', bron:'Predicaat'},
               { name:'Voornamen', display:'Voornamen', filter:'voornamen'},
               { name:'Adellijke titel', filter:'adellijketitel', type:'selectCodeMV', bron:'AdellijkeTitel'},
               { name:'Voorvoegsel', display:'Voorvoegsel', filter:'voorvoegsel'},
               { name:'Scheidingsteken', display:'Scheidingsteken', filter:'scheidingsteken'},
               { name:'Geslachtsnaamstam', display:'Geslachtsnaamstam', filter:'geslachtsnaamstam', sort:'samengesteldeNaam.geslachtsnaamstam'},
               { name:'Datum geboorte', display:'Datum geboorte', filter:'geboortedatum'},
               { name:'Gemeente geboorte', filter:'geboortegemeente', type:'selectCode', bron:'Gemeente' },
               { name:'Woonplaatsnaam geboorte', filter:'geboortewoonplaats'},
               { name:'Buitenlandse plaats geboorte', filter:'geboortebuitenlandseplaats'},
               { name:'Buitenlandse regio geboorte', filter:'geboortebuitenlandseregio'},
               { name:'Land/gebied geboorte', filter:'geboorteland', type:'selectCode', bron:'LandGebied'},
               { name:'Geslachtsaanduiding', display:'Geslachtsaanduiding'},
    ],
    geenNieuw: true,
    geenEdit: true,
    geenInitieleLijst: true,
    lijstBevatGeenDetail: true,
    toonJsonOpDetail: true
});

var AdministratieveHandelingen = {
    titel: 'Administratieve Handelingen',
    resourceNaam: 'AdministratieveHandelingen',
    resourceUrl: 'administratievehandelingen/:id',
    loaderNaam: 'AdministratieveHandelingenLoader',
    listUri: '/administratievehandelingen',
    listTemplateUrl: 'views/generic/list.html',
	contextParam: 'administratieveHandeling',
	kolommen: [
		{ name:'id', display:'iD', readonly: true},
	    { name:'Soort', display:'Soort', filter:'soort', type: 'select', bron: 'SoortAdministratieveHandeling', readonly: true},
    	{ name:'Partij', display:'Partij', filter: 'partij', type: 'selectRef', bron: 'Partij', readonly: true},
		{ name:'Partijcode', display:'PartijCode', filter: 'partijCode', type: 'text', readonly: true },
		{ name:'Toelichting ontlening', display:'toelichtingOntlening', readonly: true, toonOpLijst: false},
    	{ name:'Tijdstip registratie', display:'Tijdstip registratie', sort:'tijdstipRegistratie', readonly: true},
		{ name:'Tijdstip registratie begin', filter: 'tijdstipRegistratieBegin', type: 'datumtijd', toonOpLijst: false},
		{ name:'Tijdstip registratie einde', filter: 'tijdstipRegistratieEinde', type: 'datumtijd', toonOpLijst: false},
		{ name:'Burgerservicenummer', filter: 'bsn', type: 'nummer', toonOpLijst: false},
		{ name:'Administratienummer', filter: 'anummer', type: 'nummer', toonOpLijst: false},
		{ name:'Tijdstip levering', display:'tijdstipLevering', sort:'levering.tijdstipLevering', readonly: true},
		{ name:'Tijdstip levering begin', filter: 'tijdstipLeveringBegin', type: 'datumtijd', toonOpLijst: false},
		{ name:'Tijdstip levering einde', filter: 'tijdstipLeveringEinde', type: 'datumtijd', toonOpLijst: false},
		{ name:'Berichten', display:'berichten', indicatieDisplayVasteWaarde: true, type: 'link', link:'iD', linkDoel: 'Bericht', linkParameter: 'filterAdministratieveHandeling', linkVeld: 'iD', toonOpLijst: false},
		{ name:'Processen (GBA)', display:'processen (isc-console)', indicatieDisplayVasteWaarde: true, type: 'link', linkExtern: 'isc-processenOpAh', link:'iD', toonOpLijst: false},
		{
            name: 'Acties',
            display: 'actie',
            type: 'inlinelijst',
            bron: 'Actie',
            velden: [
				{ name:'ID', display:'iD', sort:'ID', filter:'id', readonly: true},
				{ name:'Soort', display:'Soort', sort:'soort', bron: 'SoortActie', type: 'select', readonly: true},
			    { name:'Datum ontlening', display:'actiedatumOntlening', readonly: true},
			    { name: 'Tijdstip registratie', display: 'Tijdstip registratie', readonly: true},
            ],
            relatie: 'administratieveHandeling',
            toonOpLijst: false
        },
        {
            name: 'Gedeblokkeerde Meldingen',
            display: 'geblokkeerdemelding',
            type: 'inlinelijst',
            bron: 'GedeblokkeerdeMeldingen',
            velden: [
                { name:'Identiteit', display: "Identiteit" },
                { name: "Code", display: 'Code' },
                { name: "Omschrijving", display: "Omschrijving" },
                { name: "Melding", display: "Melding" }
            ],
            relatie: 'administratieveHandeling',
            toonOpLijst: false
        }
    ],
    geenNieuw: true,
    geenEdit: true,
    geenInitieleLijst: true,
};
beheerApp.value("AdministratieveHandelingenConfig", AdministratieveHandelingen);
beheerConfiguratie.setup(AdministratieveHandelingen);

var Actie = {
    titel: 'Actie',
    resourceNaam: 'Actie',
    resourceUrl: 'administratievehandelingen/:administratieveHandeling/acties/:id',
    loaderNaam: 'ActieLoader',
    listUri: '/actie/list',
    listTemplateUrl: 'views/generic/list.html',
	kolommen: [{ name:'id', display:'iD', readonly: true},
	           { name:'Soort', sort:'soort', filter:'soort', bron: 'SoortActie', type: 'select', readonly: true},
			   { name:'Soort', display: 'Soort', readonly:true },
			   { name:'Datum ontlening', display:'actiedatumOntlening', readonly: true},
        	   {name: 'Tijdstip registratie', display: 'Tijdstip Registratie', readonly: true},
    ],
	geenNieuw: true,
	lijstBevatGeenDetail: true,
	toonJsonOpDetail: true
};
beheerApp.value("ActieConfig", Actie);
beheerConfiguratie.setup(Actie);

var Bericht = {
    titel: 'Berichten',
    resourceNaam: 'Bericht',
    resourceUrl: 'bericht/:id',
    loaderNaam: 'BerichtLoader',
    listUri: '/bericht/list',
    listTemplateUrl: 'views/generic/list.html',
	kolommen: [{ name:'ID', display:'iD', sort:'ID', filter:'filterId'},
	           { name:'Soort', display:'soort', sort:'Soort', filter:'filterSoort', type: 'select', bron: 'SoortBericht' },
	           { name:'Zendende partij', display:'zendendePartij', sort:'stuurgegevens.zendendePartijId', filter:'filterZendendePartij', type: 'selectRef', bron: 'Partij'},
	           { name:'Ontvangende partij', display:'ontvangendePartij', sort:'stuurgegevens.ontvangendePartijId', filter:'filterOntvangendePartij', type: 'selectRef', bron: 'Partij' },
	           { name:'(Cross)referentienummer', filter:'filterReferentienummer', toonOpLijst:false},
	           { name:'Referentienummer', display:'referentienummer', sort:'stuurgegevens.referentienummer'},
	           { name:'Cross referentienummer', display:'crossReferentienummer', sort:'stuurgegevens.crossReferentienummer'},
	           { name:'Begindatum', filter:'filterBegindatum', type: 'datumtijd', toonOpLijst:false },
	           { name:'Einddatum', filter:'filterEinddatum', type: 'datumtijd', toonOpLijst:false },
	           { name:'Datum/tijd verzending', display:'verzenddatum', sort:'stuurgegevens.datumTijdVerzending'},
	           { name:'Datum/tijd ontvangst', display:'ontvangstdatum', sort:'stuurgegevens.datumTijdOntvangst'},
	           { name:'Soort synchronisatie', display:'soortSynchronisatie', sort:'parameters.soortSynchronisatie', filter:'filterSoortSynchronisatie', type: 'select', bron: 'SoortSynchronisatie'},
	           { name:'Verwerkingswijze', display:'verwerkingswijze', sort:'parameters.verwerkingswijze', filter:'filterVerwerkingswijze', type: 'select', bron: 'Verwerkingswijze'},
	           { name:'Bijhouding', display:'bijhouding', sort:'resultaat.bijhouding', filter:'filterBijhouding', type: 'select', bron: 'Bijhoudingsresultaat' },
	           { name:'Verwerking', display:'verwerking', type: 'select', bron: 'Verwerkingsresultaat', toonOpLijst: false },
	           { name:'Richting', display:'richting', toonOpLijst: false},
	           { name:'Administratieve handeling', display:'administratieveHandelingSoortNaam', type: 'link', link:'administratieveHandelingId', linkDoel: "AdministratieveHandelingen", toonOpLijst: false},
			   { name:'Antwoord op', display:'antwoordOpSoortNaam', type: 'link', link:'antwoordOpId', linkDoel: "Bericht", toonOpLijst: false},
	           { name:'Zendende systeem', display:'zendendeSysteem', toonOpLijst: false, filter: 'filterZendendeSysteem'},
	           { name:'Ontvangende systeem', display:'ontvangendeSysteem', toonOpLijst: false, filter: 'filterOntvangendeSysteem'},
	           { name:'Hoogste meldingsniveau', display:'hoogsteMeldingsniveau', toonOpLijst: false},
	           { name:'Abonnement', filter:'filterAbonnement', type: 'selectRef', bron: 'Abonnement', toonOpLijst: false },
	           { name:'Abonnement', display:'abonnementNaam', type: "link", link:'abonnementId', linkDoel: "Abonnement", toonOpLijst: false},
	           { name:'Dienst', display:'categorieDienst', filter:'filterCategorieDienst', type: 'select', bron:'CategorieDienst', toonOpLijst: false},
	           { name:'Data', display:'data', toonOpLijst: false},
    ],
    geenEdit: true,
    lijstBevatGeenDetail: true,
    geenInitieleLijst: true,
};
beheerApp.value("BerichtConfig", Bericht);
beheerConfiguratie.setup(Bericht);

var GedeblokkeerdeMeldingen = {
    titel: 'Gedeblokkeerde Meldingen',
    resourceNaam: 'GedeblokkeerdeMelding',
    resourceUrl: 'administratievehandelingen/:administratieveHandeling/gedeblokkeerdemeldingen/:id',
    loaderNaam: 'GedeblokkeerdeMeldingLoader',
    geenNieuw: true,
    lijstBevatGeenDetail: false,
    toonJsonOpDetail: true
};
beheerApp.value("GedeblokkeerdeMeldingenConfig", GedeblokkeerdeMeldingen);
beheerConfiguratie.setup(GedeblokkeerdeMeldingen);

var Versie = {
	    titel: 'Versie',
	    resourceNaam: 'Versie',
	    resourceUrl: 'versie',
	    loaderNaam: 'VersieLoader',
	    listUri: '/versie',
	    listTemplateUrl: 'views/generic/edit.html',
	    kolommen: [
			    { name:'Naam', display:'name'},
	   	        { name:'Artifact', display:'artifact'},
		        { name:'Group', display:'group'},
		        { name:'Versie', display:'versie'},
		        { name:'Revisie', display:'revision'},
		        { name:'Build', display:'build'},
				{
		            name: 'Componenten',
		            display: 'componenten',
		            type: 'inlinearray',
		            velden: [
	     			    { name:'Naam', display:'name'},
	    	   	        { name:'Artifact', display:'artifact'},
	    		        { name:'Group', display:'group'},
	    		        { name:'Versie', display:'versie'},
	    		        { name:'Revisie', display:'revision'},
	    		        { name:'Build', display:'build'},
		            ],
		        }
	    ],
	    cache: true,
	    geenEdit: true,
	    geenNieuw: true,
	    geenSluiten: true,
	};
beheerApp.value("VersieConfig", Versie);
beheerConfiguratie.setup(Versie);

var Configuratie = {
	    resourceNaam: 'Configuratie',
	    resourceUrl: 'configuratie',
	};
beheerApp.value("ConfiguratieConfig", Configuratie);
beheerConfiguratie.setup(Configuratie);
