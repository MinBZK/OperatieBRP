var verwerkKolom = function (kolom, config) {
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
    if (typeof kolom.lijstbreedte === "undefined") {
        kolom.lijstbreedte = 1;
    }
    if (typeof kolom.breedte === "undefined") {
        if (kolom.type === 'inlinelijst' || kolom.type === 'inlinearray' || kolom.type === 'inlijneLijstArea' || kolom.type === 'inlinekruislijst') {
            kolom.breedte = 10;
        } else {
            kolom.breedte = 5;
        }
    }
};

var beheerConfiguratie = {
    setup: function (config) {
        // jsonview pagina
        if (!config.jsonPagina) {
            config.jsonPagina = 'views/json/view.html';
        }

        // stel standaard item pagina in
        if (!config.itemPagina) {
            if (config.readonly) {
                config.itemPagina = 'views/generic/view.html';
            } else {
                config.itemPagina = 'views/generic/edit.html';
            }
        }

        if (config.geenDetail) {
            config.geenNieuw = true;

            if (config.cache === undefined) {
                config.cache = true;
            }
        }

        if (config.geenEdit === "undefined") {
            config.geenEdit = false;
        }

        if (config.geenEdit) {
            config.geenNieuw = true;
        }

        angular.forEach(config.kolommen, function(value) {
            verwerkKolom(value, config);
        });

        // Resource
        beheerApp.factory(config.resourceNaam, ['$resource', 'instelling', function ($resource, instelling) {
            return {
                resource: function () {
                    var parameters = {};
                    if (config.resourceFilter) {
                        parameters = config.resourceFilter;
                    }
                    parameters.size = instelling.size;
                    if (config.sortering) {
                        parameters.sort = config.sortering;
                    }
                    var methoden = {
                        get: {method: 'get', isArray: false},
                        query: {method: 'get', isArray: false},
                        delete: {method: 'delete', params: {id: '@id'}},
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
        if (config.loaderNaam) {
            beheerApp.factory(config.loaderNaam, [config.resourceNaam, '$q',
                function (Resource, $q) {
                    return function () {
                        var delay = $q.defer();
                        Resource.resource().query(function (lijst) {
                            delay.resolve(lijst);
                        });
                        return delay.promise;
                    };
                }]);

            // List view
            beheerApp.config(['$routeProvider',
                function ($routeProvider) {
                    $routeProvider
                        .when(config.listUri, {
                            templateUrl: config.listTemplateUrl,
                            controller: 'ListController',
                            resolve: {
                                resource: [config.resourceNaam, function (Resource) {
                                    return Resource;
                                }],
                                lijst: config.geenInitieleLijst ? function () {
                                    return {};
                                } : [config.loaderNaam, function (Loader) {
                                    return Loader();
                                }],
                                item: function () {
                                    return null;
                                },
                                config: function () {
                                    return config;
                                }
                            }
                        });
                }]);

            // New Detail view
            beheerApp.config(['$routeProvider',
                function ($routeProvider) {
                    $routeProvider
                        .when(config.listUri + "/:id", {
                            templateUrl: config.listTemplateUrl,
                            controller: 'ListController',
                            resolve: {
                                resource: [config.resourceNaam, function (Resource) {
                                    return Resource;
                                }],
                                lijst: function () {
                                    return {};
                                },
                                item: ['$q', '$route', config.resourceNaam, function ($q, $route, Resource) {
                                    var delay = $q.defer();
                                    Resource.resource().get({id: $route.current.params.id}, function (result) {
                                        delay.resolve(result);
                                    });
                                    return delay.promise;
                                }],
                                config: function () {
                                    return config;
                                }
                            }
                        });
                }]);

            beheerApp.config(['$routeProvider',
                function ($routeProvider) {
                    $routeProvider
                        .when("/vrijbericht/nieuw", {
                            templateUrl: 'views/vrijbericht.html',
                            controller: 'VrijBerichtController',
                            controllerAs: 'vrijBericht',
                            resolve: {
                                soortenVrijBericht: ['VrijBerichtService', function (VrijBerichtService) {
                                    return VrijBerichtService.getSoortVrijBericht();
                                }],
                                partijen: ['VrijBerichtService', function (VrijBerichtService) {
                                    return VrijBerichtService.getPartijen();
                                }]
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
    kolommen: [{name: 'Id', display: 'id', sort: 'id', filter: 'persoonId'},
        {name: 'Soort', filter: 'soort', type: 'select', bron: 'SoortPersoon'},
        {name: 'Soort', display: 'Soort'},
        {
            name: 'Burgerservicenummer',
            display: 'Burgerservicenummer',
            filter: 'bsn',
            sort: 'identificatienummers.burgerservicenummer'
        },
        {
            name: 'Administratienummer',
            display: 'Administratienummer',
            filter: 'anr',
            sort: 'identificatienummers.administratienummer'
        },
        {name: 'Afgeleid?', filter: 'afgeleid', type: 'checkboxJaNee'},
        {name: 'Namenreeks?', filter: 'namenreeks', type: 'checkboxJaNee'},
        {name: 'Predicaat', filter: 'predicaat', type: 'selectCodeMV', bron: 'Predicaat'},
        {name: 'Voornamen', display: 'Voornamen', filter: 'voornamen'},
        {name: 'Adellijke titel', filter: 'adellijketitel', type: 'selectCodeMV', bron: 'AdellijkeTitel'},
        {name: 'Voorvoegsel', display: 'Voorvoegsel', filter: 'voorvoegsel'},
        {name: 'Scheidingsteken', display: 'Scheidingsteken', filter: 'scheidingsteken'},
        {
            name: 'Geslachtsnaamstam',
            display: 'Geslachtsnaamstam',
            filter: 'geslachtsnaamstam',
            sort: 'samengesteldeNaam.geslachtsnaamstam'
        },
        {name: 'Datum geboorte', display: 'Datum geboorte', type: 'datumonbekend', filter: 'geboortedatum'},
        {name: 'Gemeente geboorte', filter: 'geboortegemeente', type: 'select', bron: 'Gemeente'},
        {name: 'Woonplaatsnaam geboorte', filter: 'geboortewoonplaats'},
        {name: 'Buitenlandse plaats geboorte', filter: 'geboortebuitenlandseplaats'},
        {name: 'Buitenlandse regio geboorte', filter: 'geboortebuitenlandseregio'},
        {
            name: 'Land/gebied geboorte',
            display: 'geboorteland',
            filter: 'geboorteland',
            type: 'select',
            bron: 'LandGebied',
            toonOpLijst: false
        },
        {name: 'Geslachtsaanduiding', display: 'Geslachtsaanduiding'},
        {name: 'Soort adres', filter: 'soortadres', type: 'nummer', toonOpLijst: false},
        {name: 'Identificatie adresseerbaar object', filter: 'idadresseerbaarobject', toonOpLijst: false},
        {name: 'Identificatie nummer aanduiding', filter: 'idnummeraanduiding', toonOpLijst: false},
        {name: 'Gemeente', filter: 'gemeente', type: 'select', bron: 'Gemeente', toonOpLijst: false},
        {name: 'Naam openbare ruimte', filter: 'naamopenbareruimte', toonOpLijst: false},
        {name: 'Afgekorte naam openbare ruimte', filter: 'afgekortenaamopenbareruimte', toonOpLijst: false},
        {name: 'Huisnummer', filter: 'huisnummer', toonOpLijst: false},
        {name: 'Huisletter', filter: 'huisletter', toonOpLijst: false},
        {name: 'Huisnummertoevoeging', filter: 'huisnummertoevoeging', toonOpLijst: false},
        {name: 'Postcode', filter: 'postcode', toonOpLijst: false},
        {name: 'Woonplaatsnaam', filter: 'woonplaatsnaam', toonOpLijst: false},
        {name: 'Buitenlands adres', filter: 'buitenlandsadres', toonOpLijst: false},
        {name: 'Land of Gebied', filter: 'landofgebied', type: 'select', bron: 'LandGebied', toonOpLijst: false},
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
        {name: 'id', display: 'id', readonly: true},
        {
            name: 'Soort',
            display: 'Soort',
            filter: 'soort',
            type: 'select',
            bron: 'SoortAdministratieveHandeling',
            readonly: true
        },
        {
            name: 'Administratieve Handeling Partij',
            display: 'Partij',
            filter: 'partij',
            type: 'select',
            bron: 'Partij',
            readonly: true
        },
        {
            name: 'Administratieve Handeling Partijcode',
            display: 'PartijCode',
            filter: 'partijCode',
            type: 'text',
            readonly: true
        },
        {name: 'Toelichting ontlening', display: 'toelichtingOntlening', readonly: true, toonOpLijst: false},
        {name: 'Tijdstip registratie', display: 'Tijdstip registratie', sort: 'tijdstipRegistratie', readonly: true},
        {name: 'Tijdstip registratie begin', filter: 'tijdstipRegistratieBegin', type: 'datumtijd', toonOpLijst: false},
        {name: 'Tijdstip registratie eind', filter: 'tijdstipRegistratieEinde', type: 'datumtijd', toonOpLijst: false},
        {name: 'Burgerservicenummer', filter: 'bsn', type: 'nummer', toonOpLijst: false},
        {name: 'Administratienummer', filter: 'anr', type: 'nummer', toonOpLijst: false},
        {name: 'Tijdstip levering', display: 'Tijdstip levering', sort: 'levering.tijdstipLevering', readonly: true},
        {name: 'Tijdstip levering begin', filter: 'tijdstipLeveringBegin', type: 'datumtijd', toonOpLijst: false},
        {name: 'Tijdstip levering eind', filter: 'tijdstipLeveringEinde', type: 'datumtijd', toonOpLijst: false},
        {
            name: 'Berichten',
            display: 'berichten',
            indicatieDisplayVasteWaarde: true,
            type: 'link',
            link: 'id',
            linkDoel: 'Bericht',
            linkParameter: 'filterAdministratieveHandeling',
            linkVeld: 'id',
            toonOpLijst: false
        },
        {
            name: 'Processen (GBA)',
            display: 'processen (isc-console)',
            indicatieDisplayVasteWaarde: true,
            type: 'link',
            linkExtern: 'isc-processenOpAh',
            link: 'id',
            toonOpLijst: false
        },
        {
            name: 'Acties',
            display: 'actie',
            type: 'inlinelijst',
            bron: 'Actie',
            velden: [
                {name: 'Id', display: 'id', sort: 'Id', filter: 'id', readonly: true},
                {name: 'Soort', display: 'Soort', sort: 'soort', bron: 'SoortActie', type: 'select', readonly: true},
                {name: 'Datum ontlening', display: 'actiedatumOntlening', readonly: true},
                {name: 'Tijdstip registratie', display: 'Tijdstip registratie', readonly: true},
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
                {name: "Identiteit", display: "Identiteit"},
                {name: "Code", display: "Code"},
                {name: "Soort melding", display: "Soort melding"},
                {name: "Melding", display: "Melding"}
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
    kolommen: [{name: 'id', display: 'id', readonly: true},
        {
            name: 'Soort',
            display: 'Soort',
            sort: 'Soort',
            filter: 'soort',
            bron: 'SoortActie',
            type: 'select',
            readonly: true
        },
        {name: 'Datum ontlening', display: 'actiedatumOntlening', readonly: true},
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
    kolommen: [{name: 'Id', display: 'id', sort: 'Id', filter: 'filterId'},
        {
            name: 'Soort',
            display: 'soort',
            sort: 'Soort',
            filter: 'filterSoort',
            type: 'selectIdentifier',
            bron: 'SoortBericht',
            readonly: true,
            toonOpBewerken: false
        },
        {name: 'Soort', display: 'soortNaam', readonly: true, toonOpLijst: false},
        {
            name: 'Zendende partij',
            display: 'zendendePartij',
            sort: 'stuurgegevens.zendendePartijId',
            filter: 'filterZendendePartij',
            type: 'selectPartij',
            bron: 'Partij',
            toonOpBewerken: false
        },
        {name: 'Zendende partij', display: 'zendendePartijNaamCode', toonOpLijst: false, readonly: true},
        {
            name: 'Ontvangende partij',
            display: 'ontvangendePartij',
            sort: 'stuurgegevens.ontvangendePartijId',
            filter: 'filterOntvangendePartij',
            type: 'selectPartij',
            bron: 'Partij',
            toonOpBewerken: false
        },
        {name: 'Ontvangende partij', display: 'ontvangendePartijNaamCode', toonOpLijst: false, readonly: true},
        {name: '(Cross)referentienummer', filter: 'filterReferentienummer', toonOpLijst: false, readonly: true},
        {name: 'Referentienummer', display: 'referentienummer', sort: 'stuurgegevens.referentienummer', readonly: true},
        {
            name: 'Cross referentienummer',
            display: 'crossReferentienummer',
            sort: 'stuurgegevens.crossReferentienummer',
            readonly: true
        },
        {name: 'Begindatum', filter: 'filterBegindatum', type: 'datumtijd', toonOpLijst: false, readonly: true},
        {name: 'Einddatum', filter: 'filterEinddatum', type: 'datumtijd', toonOpLijst: false, readonly: true},
        {
            name: 'Verzenddatum',
            display: 'verzenddatum',
            sort: 'stuurgegevens.datumTijdVerzending',
            readonly: true
        },
        {
            name: 'Ontvangstdatum',
            display: 'ontvangstdatum',
            sort: 'stuurgegevens.datumTijdOntvangst',
            readonly: true
        },
        {
            name: 'Soort synchronisatie',
            display: 'soortSynchronisatie',
            sort: 'parameters.soortSynchronisatie',
            filter: 'filterSoortSynchronisatie',
            type: 'select',
            bron: 'SoortSynchronisatie',
            readonly: true,
            toonOpBewerken: false
        },
        {name: 'Soort synchronisatie', display: 'soortSynchronisatieNaam', readonly: true, toonOpLijst: false},
        {
            name: 'Verwerkingswijze',
            display: 'verwerkingswijze',
            sort: 'parameters.verwerkingswijze',
            filter: 'filterVerwerkingswijze',
            type: 'select',
            bron: 'Verwerkingswijze',
            readonly: true,
            toonOpBewerken: false
        },
        {name: 'Verwerkingswijze', display: 'verwerkingswijzeNaam', readonly: true, toonOpLijst: false},
        {
            name: 'Bijhouding',
            display: 'bijhouding',
            sort: 'resultaat.bijhouding',
            filter: 'filterBijhouding',
            type: 'select',
            bron: 'Bijhoudingsresultaat',
            toonOpBewerken: false
        },
        {name: 'Bijhouding', display: 'bijhoudingNaam', readonly: true, toonOpLijst: false},
        {name: 'Leveringsautorisatie', display: 'leveringsautorisatie', filter: 'filterLeveringsautorisatieNaam',  readonly: true, toonOpLijst: false},
        {name: 'Dienst', bron:'SoortDienst', type: 'select', filter: 'filterSoortDienst', toonOpLijst: false, readonly: true},
        {name: 'Dienst', display: 'dienst', toonOpLijst: false},
        {
            name: 'Verwerking',
            display: 'verwerking',
            type: 'select',
            bron: 'Verwerkingsresultaat',
            toonOpLijst: false,
            readonly: true,
            toonOpBewerken: false
        },
        {name: 'Verwerking', display: 'verwerkingNaam', toonOpLijst: false, readonly: true},
        {name: 'Richting', display: 'richting', toonOpLijst: false},
        {
            name: 'Administratieve handeling',
            display: 'administratieveHandelingSoortNaam',
            type: 'link',
            link: 'administratieveHandelingId',
            linkDoel: "AdministratieveHandelingen",
            toonOpLijst: false
        },
        {
            name: 'Antwoord op',
            display: 'antwoordOpSoortNaam',
            type: 'link',
            link: 'antwoordOpId',
            linkDoel: "Bericht",
            toonOpLijst: false
        },
        {name: 'Zendende systeem', display: 'zendendeSysteem', toonOpLijst: false, filter: 'filterZendendeSysteem'},
        {name: 'Hoogste meldingsniveau', display: 'hoogsteMeldingsniveau', toonOpLijst: false},
        {name: 'Data', display: 'data', toonOpLijst: false},
    ],
    geenEdit: true,
//    readonly: true,
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
    listTemplateUrl: 'views/versie/view.html',
    kolommen: [
        {name: 'Naam', display: 'name'},
        {name: 'Artifact', display: 'artifact'},
        {name: 'Group', display: 'group'},
        {name: 'Versie', display: 'versie'},
        {
            name: 'Componenten',
            display: 'componenten',
            type: 'inlinearray',
            velden: [
                {name: 'Naam', display: 'name'},
                {name: 'Artifact', display: 'artifact'},
                {name: 'Group', display: 'group'},
                {name: 'Versie', display: 'versie'},
            ],
            geenNieuw: true,
            readOnly: true
        }
    ],
    cache: false,
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


var images = [{
    src: 'img/bin.png',
}];
