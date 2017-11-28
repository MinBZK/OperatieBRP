angular.module('BrpVeld', [])
    .directive("brpVeld", ['$window', '$location', '$injector', '$q', '$timeout', '$sce', '$rootScope', '$modal', 'context', '$filter',
        function ($window, $location, $injector, $q, $timeout, $sce, $rootScope, $modal, context, $filter) {
            return {
                restrict: 'E',
                require: '^form',
                scope: {
                    status: '=itemstatus',
                    waarde: '='
                },
                link: function (scope, element, attrs, formCtrl) {
                    scope.kolom = angular.fromJson(attrs.kolom);
                    scope.veld = 'fields/' + scope.kolom.type + '.html';
                    // voeg formcontrol toe aan scope voor validatie berichten
                    scope.editForm = formCtrl;
                    scope.toon = true;
                    scope.verborgenKolommen = [];

                    if (scope.kolom.bron) {
                        scope.waardenlijst = {};

                        var haalRubriekenOp = function (lijst) {
                            var rubriekBron = $injector.get('ConversieLo3Rubriek').resource();
                            return lijst.map(function (rubriek) {
                                var delay = $q.defer();
                                if (!rubriek.rubrieknaam) {
                                    var params = {};
                                    params.id = rubriek.rubriek;
                                    rubriekBron.get(params, function (data) {
                                        // bewaar naam voor eventuele opslag
                                        rubriek.rubrieknaam = data.naam;
                                        delay.resolve(rubriek);
                                    });
                                } else {
                                    delay.resolve(rubriek);
                                }
                                return delay.promise;
                            });
                        };

                        var setWaardenlijst = function (data) {
                            var beloften = haalRubriekenOp(data);
                            $q.all(beloften).then(function (opgehaaldeLijst) {
                                var toonlijst = [];
                                angular.forEach(opgehaaldeLijst, function (value) {
                                    if (value && value.actief === 'Ja') {
                                        toonlijst.push(value.rubrieknaam);
                                    }
                                    toonlijst.sort();
                                });
                                if (toonlijst.length > 0) {
                                    scope.waardenlijst.text = toonlijst.join('#');
                                } else {
                                    scope.waardenlijst.text = 'klik hier om LO3 rubrieken toe te voegen';
                                }
                            });
                        };

                        if (scope.kolom.type === 'inlineLijstArea') {
                            scope.$on('succes', function (event, value) {
                                if (value && value.item === 'ingang' && value.nieuw) {
                                    scope.waardenlijst.text = 'klik hier om LO3 rubrieken toe te voegen';
                                }
                            });
                        }

                        var specialeRequery = function(paginaGrootte, updateLijst) {
                            var page = scope.paginaNummer - 1;
                            var size = paginaGrootte;
                            var sort = scope.config.sortering !== "" ? (scope.config.sortering) : null;
                            // Zet de paginering en sortering.
                            var parameters = {page: page, size: size, sort: sort};
                            // Zet de relatie.
                            parameters[scope.kolom.relatie] = context.params[scope.kolom.relatie];
                            // Zet de bekende context parameters
                            for (var param in context.params) {
                                parameters[param] = context.params[param];
                            }
                            scope.resource.query(parameters,
                                function (data) {
                                    if(updateLijst){
                                        scope.waarde[scope.kolom.display] = data;
                                    }
                                    scope.paginaNummer = data.number + 1;
                                    scope.eerstePagina = data.first;
                                    scope.laatstePagina = data.last;

                                    if (data.warning) {
                                        $rootScope.$broadcast('info', {type: 'danger', message: data.warning});
                                    }

                                    scope.items = data.content;
                                }
                            );
                        };

                        var broadcastSucces  = function () {
                            specialeRequery(20, true);

                            $rootScope.$broadcast('info', {type: 'success', message: "Opslaan gelukt."});
                            $rootScope.$broadcast('succes', {item: 'sub'});

                            // Enable de buttons om verder interactie weer mogelijk te maken.
                            scope.buttonSelecteerAlles = "Selecteer alles";
                            scope.buttonDeselecteerAlles = "Deselecteer alles";
                        };

                        if (scope.kolom.type === 'inlinekruislijst') {

                            scope.buttonSelecteerAlles = "Selecteer alles";
                            scope.buttonDeselecteerAlles = "Deselecteer alles";

                            scope.activateForMultipleItems = function (index, isLaatste) {

                                scope.editForm.$setPristine(true);

                                var onderhandenitem = {};
                                onderhandenitem.status = {};
                                onderhandenitem.status.nieuw = false;

                                onderhandenitem.item = scope.items[index];
                                onderhandenitem.relatie = scope.waarde.naam;

                                var gewijzigdItem = new scope.resource(onderhandenitem.item);
                                gewijzigdItem.$save(
                                    context.params,
                                    function (value) {
                                        if (scope.items[index].actief !== 'Nee') {
                                            scope.items[index].id = value.content[index].id;
                                        }
                                        scope.items[index].id = value.content[index].id;
                                        if(isLaatste){
                                            broadcastSucces();
                                        }
                                    },
                                    function (httpResponse) {
                                        if (httpResponse.data) {
                                            $rootScope.$broadcast('info', {
                                                type: 'danger',
                                                code: "SAVE_FAILED",
                                                message: (httpResponse.data.message ? httpResponse.data.message : "Opslaan mislukt.")
                                            });
                                        } else {
                                            $rootScope.$broadcast('info', {
                                                type: 'danger',
                                                code: "SAVE_FAILED",
                                                message: "Opslaan niet mogelijk, object is van onbekend type"
                                            });
                                        }
                                        if(isLaatste){
                                            scope.buttonSelecteerAlles = "Selecteer alles";
                                            scope.buttonDeselecteerAlles = "Deselecteer alles";
                                        }
                                    }
                                );
                            };

                            scope.selectAll = function () {
                                // Disable de buttons om verdere interactie te voorkomen.
                                scope.buttonSelecteerAlles = "Alles selecteren";

                                var originelePagina = scope.paginaNummer;
                                scope.paginaNummer = 1;
                                // Query alles
                                specialeRequery(10000);

                                var activation = function() {
                                    var index = 0;
                                    angular.forEach(scope.items, function (item) {

                                        var isLaatste = index === scope.items.length - 1;

                                        if(item.actief !== 'Ja'){
                                            item.actief = 'Ja';
                                            scope.activateForMultipleItems(index, isLaatste);
                                        } else if(isLaatste){
                                            broadcastSucces();
                                        }
                                        index++;
                                    } );

                                    scope.paginaNummer = originelePagina;
                                };

                                $timeout(activation, 1000);
                            };

                            scope.deselectAll = function () {
                                // Disable de buttons om verdere interactie te voorkomen.
                                scope.buttonDeselecteerAlles = "Alles deselecteren";

                                var originelePagina = scope.paginaNummer;
                                scope.paginaNummer = 1;
                                // Query alles
                                specialeRequery(10000);

                                var activation = function() {
                                    var index = 0;
                                    angular.forEach(scope.items, function (item) {

                                        var isLaatste = index === scope.items.length - 1;

                                        if(item.actief !== 'Nee'){
                                            item.actief = 'Nee';
                                            scope.activateForMultipleItems(index, isLaatste);
                                        } else if(isLaatste){
                                            broadcastSucces();
                                        }
                                        index++;
                                    } );

                                    scope.paginaNummer = originelePagina;
                                };

                                $timeout(activation, 1000);
                            };
                        }


                        scope.config = $injector.get(scope.kolom.bron + 'Config');
                        scope.resource = $injector.get(scope.config.resourceNaam).resource();

                        if (scope.kolom.type.indexOf('select') !== 0) {
                            // bewerk kolomdefinitie voor gebruik op subformulier om naam uniek te houden
                            scope[scope.kolom.display + '_subkolommen'] = [];

                            angular.forEach(scope.config.kolommen, function (value) {
                                if (value.display) {
                                    var kolom = angular.copy(value);
                                    kolom.id = scope.kolom.display + '_' + kolom.display;
                                    scope[scope.kolom.display + '_subkolommen'].push(kolom);
                                }
                            });

                            scope.$watch('waarde', function () {
                                scope.waardenlijst = {};
                                if (scope.waarde && !scope.status.nieuw) {
                                    scope.resource.query(context.params, function (data) {
                                        scope.waarde[scope.kolom.display] = data;
                                        scope.paginaNummer = data.number + 1;
                                        scope.eerstePagina = data.first;
                                        scope.laatstePagina = data.last;
                                        if (scope.kolom.type === 'inlineLijstArea' && data.content) {
                                            setWaardenlijst(data.content);
                                        } else {
                                            scope.waardenlijst.text = 'klik hier om LO3 rubrieken toe te voegen';
                                        }
                                    });
                                }
                            });

                            scope.viewItem = function (index) {
                                if (scope.verwijderActie) {
                                    if (confirm('Groep ' + scope.verwijderGroep.Naam + ' verwijderen?')) {
                                        var teVerwijderenGroep = new scope.resource(scope.verwijderGroep);
                                        teVerwijderenGroep.$delete(
                                            context.params,
                                            function () {
                                                $rootScope.$broadcast('info', {
                                                    type: 'success',
                                                    message: "Verwijderen gelukt."
                                                });
                                                $rootScope.$broadcast('succes', {item: 'sub'});
                                                scope.waarde[scope.kolom.display].content.splice(index, 1);
                                            },
                                            function (httpResponse) {
                                                if (httpResponse.data) {
                                                    $rootScope.$broadcast('info', {
                                                        type: 'danger',
                                                        code: "DELETE_FAILED",
                                                        message: (httpResponse.data.message ? httpResponse.data.message : "Verwijderen mislukt.")
                                                    });
                                                } else {
                                                    $rootScope.$broadcast('info', {
                                                        type: 'danger',
                                                        code: "DELETE_FAILED",
                                                        message: "Verwijderen niet mogelijk, object is van onbekend type"
                                                    });
                                                }

                                            }
                                        );
                                    }
                                    scope.verwijderActie = false;
                                    scope.verwijderGroep = undefined;
                                } else {
                                    if (scope.config.toonJsonOpDetail) {
                                        // event toonJson wordt gevuurd daar het tonen via de hoofdpagina gaat
                                        if (scope.config.lijstBevatGeenDetail) {
                                            context.params.id = scope.waarde[scope.kolom.display].content[this.$index].id;
                                            scope.resource.query(context.params, function (data) {
                                                var gegevens = {};
                                                gegevens.titel = scope.config.titel;
                                                gegevens.json = data;
                                                $rootScope.$broadcast('toonJson', gegevens);
                                            });
                                        } else {
                                            var gegevens = {};
                                            gegevens.titel = scope.config.titel;
                                            gegevens.json = scope.waarde[scope.kolom.display].content[this.$index];
                                            $rootScope.$broadcast('toonJson', gegevens);
                                        }
                                    } else if (scope.config.geenDetail !== true) {
                                        var onderhandenitem = {};
                                        onderhandenitem.status = {};
                                        onderhandenitem.status.nieuw = false;
                                        var modalResolve = {
                                            bron: function () {
                                                return scope.resource;
                                            },
                                            titel: function () {
                                                return scope.config.titel;
                                            }
                                        };

                                        var modalTemplateUrl;
                                        var modalController;

                                        if (scope.kolom.type === 'inlinelijst') {
                                            if (scope.config.lijstBevatGeenDetail) {
                                                context.params.id = scope.waarde[scope.kolom.display].content[this.$index].id;
                                                scope.resource.query(context.params, function (data) {
                                                    onderhandenitem.item = data;

                                                    if (scope.config.contextParam) {
                                                        context.params[scope.config.contextParam] = onderhandenitem.item.id;
                                                    }
                                                });
                                            } else {
                                                onderhandenitem.item = angular.copy(scope.waarde[scope.kolom.display].content[this.$index]);

                                                if (scope.config.contextParam) {
                                                    context.params[scope.config.contextParam] = onderhandenitem.item.id;
                                                }
                                            }
                                            onderhandenitem.relatie = scope.waarde.naam;

                                            modalTemplateUrl = 'views/generic/editInline.html';
                                            modalController = 'ItemInstanceController';
                                            modalResolve.subkolommen = function() {
                                                return scope[scope.kolom.display + '_subkolommen'];
                                            };
                                        } else {
                                            var rubriekbron = $injector.get('ConversieLo3Rubriek').resource();
                                            modalResolve.rubriekBron = function () {
                                                return rubriekbron;
                                            };
                                            onderhandenitem.waardenlijst = scope.waardenlijst.text;
                                            if (scope.waarde[scope.kolom.display]) {
                                                onderhandenitem.item = scope.waarde[scope.kolom.display].content;
                                            }
                                            if (!onderhandenitem.item) {
                                                onderhandenitem.item = [];
                                            }
                                            modalTemplateUrl = 'views/lo3filterrubriek/editInline.html';
                                            modalController = 'Lo3FilterRubriekInstanceController';
                                        }

                                        modalResolve.onderhandenitem = function () {
                                            return onderhandenitem;
                                        };

                                        modalResolve.toonJsonItem = function () {
                                            return scope.config.toonJsonOpDetail;
                                        };

                                        var modalInstance = $modal.open({
                                            templateUrl: modalTemplateUrl,
                                            controller: modalController,
                                            size: 'lg',
                                            resolve: modalResolve
                                        });
                                        modalInstance.result.then(function (bewaardItem) {
                                            if (scope.kolom.name === 'Lo3 Rubrieken') {
                                                // ververs de gegevens door ze opnieuw op te halen. Om dit
                                                // aan client kant te doen is complex
                                                $timeout(function () {
                                                    scope.resource.query(context.params, function (data) {
                                                        scope.waarde[scope.kolom.display] = data.content;
                                                        if (scope.kolom.type === 'inlineLijstArea' && data.content) {
                                                            setWaardenlijst(data.content);
                                                        } else {
                                                            scope.waardenlijst.text = 'klik hier om LO3 rubrieken toe te voegen';
                                                        }
                                                    });
                                                }, 1000);
                                            }

                                            if (scope.kolom.type === 'inlinelijst') {
                                                scope.waarde[scope.kolom.display].content[index] = bewaardItem;
                                            }
                                        });
                                    }
                                }
                            };

                            scope.activateItem = function (index, currentFieldName) {
                                // Inline kruislijst elementen moeten het form niet dirty maken.
                                // Overige form elementen die dirty zijn moeten behouden blijven.
                                var dirty = false;
                                angular.forEach(scope.editForm, function (element, name) {
                                    if (element && element.$dirty && name !== currentFieldName) {
                                        dirty = true;
                                    }
                                });
                                if (!dirty) {
                                    scope.editForm.$setPristine(true);
                                }

                                var onderhandenitem = {};
                                onderhandenitem.status = {};
                                onderhandenitem.status.nieuw = false;

                                onderhandenitem.item = scope.waarde[scope.kolom.display].content[index];
                                onderhandenitem.relatie = scope.waarde.naam;

                                var gewijzigdItem = new scope.resource(onderhandenitem.item);
                                gewijzigdItem.$save(
                                    context.params,
                                    function (value) {
                                        if (scope.waarde[scope.kolom.display].content[index].actief !== 'Nee') {
                                            scope.waarde[scope.kolom.display].content[index].id = value.content[index].id;
                                        }
                                        $rootScope.$broadcast('info', {type: 'success', message: "Opslaan gelukt."});
                                        $rootScope.$broadcast('succes', {item: 'sub'});
                                    },
                                    function (httpResponse) {
                                        if (httpResponse.data) {
                                            $rootScope.$broadcast('info', {
                                                type: 'danger',
                                                code: "SAVE_FAILED",
                                                message: (httpResponse.data.message ? httpResponse.data.message : "Opslaan mislukt.")
                                            });
                                        } else {
                                            $rootScope.$broadcast('info', {
                                                type: 'danger',
                                                code: "SAVE_FAILED",
                                                message: "Opslaan niet mogelijk, object is van onbekend type"
                                            });
                                        }
                                    }
                                );
                            };

                            scope.verwijderItem = function (groep) {
                                scope.verwijderActie = true;
                                scope.verwijderGroep = groep;
                            };

                            scope.nieuwItem = function () {
                                var onderhandenitem = {};
                                onderhandenitem.status = {};
                                onderhandenitem.status.nieuw = true;
                                onderhandenitem.blijfOpWijzig = scope.config.blijfOpWijzig;
                                onderhandenitem.contextParamNaam = scope.config.contextParam;
                                onderhandenitem.extraContextParams = scope.config.extraContextParams;
                                onderhandenitem.item = {};
                                onderhandenitem.item[scope.kolom.relatie] = scope.waarde.id;
                                if (scope.config.contextParam) {
                                    context.params[scope.config.contextParam] = null;
                                }
                                if (scope.config.extraContextParams) {
                                    context.params[scope.config.extraContextParams] = null;
                                }

                                var modalInstance = $modal.open({
                                    templateUrl: 'views/generic/editInline.html',
                                    controller: 'ItemInstanceController',
                                    size: 'lg',
                                    resolve: {
                                        onderhandenitem: function () {
                                            return onderhandenitem;
                                        },
                                        bron: function () {
                                            return scope.resource;
                                        },
                                        subkolommen: function() {
                                            return scope[scope.kolom.display + '_subkolommen'];
                                        },
                                        titel: function () {
                                            return scope.config.titel;
                                        },
                                        config: function () {
                                            return scope.config;
                                        }
                                    }
                                });
                                modalInstance.result.then(function (bewaardItem) {
                                    if (!scope.waarde[scope.kolom.display] || scope.waarde[scope.kolom.display].totalElements === 0) {
                                        scope.waarde[scope.kolom.display] = {
                                            'content': [],
                                            'totalElements': 1,
                                            'size': 20
                                        };
                                    }
                                    scope.waarde[scope.kolom.display].content.push(bewaardItem);
                                });
                            };

                            scope.reQuery = function () {
                                var page = scope.paginaNummer - 1;
                                var size = scope.waarde[scope.kolom.display].size;
                                var sort = scope.config.sortering !== "" ? (scope.config.sortering) : null;
                                // Zet de paginering en sortering.
                                var parameters = {page: page, size: size, sort: sort};
                                // Zet de relatie.
                                parameters[scope.kolom.relatie] = context.params[scope.kolom.relatie];
                                // Zet de bekende context parameters
                                for (var param in context.params) {
                                    parameters[param] = context.params[param];
                                }
                                scope.resource.query(parameters,
                                    function (data) {
                                        scope.waarde[scope.kolom.display] = data;
                                        scope.paginaNummer = data.number + 1;
                                        scope.eerstePagina = data.first;
                                        scope.laatstePagina = data.last;

                                        if (data.warning) {
                                            $rootScope.$broadcast('info', {type: 'danger', message: data.warning});
                                        }

                                        scope.items = data.content;
                                    }
                                );
                            };

                            scope.pagineren = function (pagina) {
                                scope.paginaNummer = pagina;
                                scope.reQuery();
                            };

                            scope.vorigePagina = function (pagina) {
                                if (scope.eerstePagina === false) {
                                    scope.paginaNummer = pagina - 1;
                                    scope.reQuery();
                                }
                            };

                            scope.volgendePagina = function (pagina) {
                                if (scope.laatstePagina === false) {
                                    scope.paginaNummer = pagina + 1;
                                    scope.reQuery();
                                }
                            };

                        } else {
                            var speciaal = {};
                            if (scope.config.resourceFilter) {
                                speciaal[scope.config.resourceFilter] = context.params[scope.config.resourceFilter];
                            }
                            speciaal.size = 10000;
                            scope.resource.query(speciaal, function (data) {
                                scope.options = data;

                                if (data.numberOfElements > 0) {
                                    for (var x = 0; x < data.content.length; x++) {
                                        if (data.content[x].id) {
                                            if (scope.waarde[scope.kolom.display] === data.content[x].id) {
                                                scope.geselecteerdeOptie = data.content[x].naam;
                                            }
                                        } else {
                                            if (scope.waarde[scope.kolom.display] === data.content[x].ordinal) {
                                                scope.geselecteerdeOptie = data.content[x].naam;
                                            }
                                        }
                                    }
                                    if (scope.kolom.ngFilter) {
                                        $filter(scope.kolom.ngFilter)(data.content);
                                    }
                                    scope.items = data.content;
                                }

                                if (scope.kolom.type === 'inlinekruislijst') {

                                    angular.forEach(scope.waarde[scope.kolom.id], function (item) {
                                        var index = scope.options.content.map(function (contentItem) {
                                            return contentItem.id;
                                        }).indexOf(item.id);
                                        if (index > -1) {
                                            scope.options.content.splice(index, 1);
                                        }
                                    });
                                }
                            });
                        }
                    } else {
                        if (scope.kolom.type === 'inlinelijst') {
                            // Geen bron
                            scope.config = {geenNieuw: true};
                        }
                    }


                    // link functionaliteit
                    if (scope.kolom.type === 'link') {
                        var getUniqueid = function () {
                            var d = new Date().getTime();
                            d += (parseInt(Math.random() * 100)).toString();
                            return d;
                        };

                        scope.$watch('waarde', function (value) {
                            var uuid = getUniqueid();

                            if (scope.kolom.linkExtern) {
                                scope.linkExtern = $rootScope.configuratie[scope.kolom.linkExtern] + value[scope.kolom.link];
                            } else {
                                var linkConfig = $injector.get(scope.kolom.linkDoel + 'Config');
                                if (scope.kolom.linkParameter) {
                                    scope.linkdoel = linkConfig.listUri + '?' + scope.kolom.linkParameter + '=' + value[scope.kolom.link] + '&refid=' + uuid;
                                } else {
                                    scope.linkdoel = linkConfig.listUri + '/' + value[scope.kolom.link] + "?refid=" + uuid;
                                }
                            }

                            context.vorig[uuid] = context.vorigeurl + "/" + value.id;
                        });

                        scope.volgLink = function () {
                            if (scope.linkExtern) {
                                $window.open(scope.linkExtern);
                            } else {
                                if (event.ctrlKey === 1) {
                                    $window.open($window.location.pathname + '#' + scope.linkdoel);
                                } else {
                                    $location.url(scope.linkdoel);
                                }
                            }
                        };
                    }

                },
                template: '<div ng-include="veld"></div>'
            };
        }]);
