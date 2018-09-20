angular.module('BrpVeld', [])
.directive("brpVeld", ['$window', '$location', '$injector', '$q', '$timeout', '$sce', '$rootScope', '$modal', 'context',
    function ($window, $location, $injector, $q, $timeout, $sce, $rootScope, $modal, context) {
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

                if (scope.kolom.bron) {
                    scope.waardenlijst = {};

                    var haalRubriekenOp = function(lijst) {
                        var rubriekBron = $injector.get('ConversieLo3Rubriek').resource();
                        var beloften = lijst.map(function(rubriek) {
                            var delay = $q.defer();
                            if (!rubriek.rubrieknaam) {
                                var params = {};
                                params.id = rubriek.rubriek;
                                rubriekBron.get(params, function(data) {
                                    // bewaar naam voor eventuele opslag
                                    rubriek.rubrieknaam = data.naam;
                                    delay.resolve(rubriek);
                                });
                            } else {
                                delay.resolve(rubriek);
                            }
                            return delay.promise;
                        });
                        return beloften;
                    };

                    var setWaardenlijst = function(data) {
                        var beloften = haalRubriekenOp(data);
                        $q.all(beloften).then(function(opgehaaldeLijst) {
                            var toonlijst = [];
                            angular.forEach(opgehaaldeLijst, function(value) {
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
                        scope.$on('succes', function(event, value) {
                            if (value && value.item === 'ingang' && value.nieuw) {
                                scope.waardenlijst.text = 'klik hier om LO3 rubrieken toe te voegen';
                            }
                        });
                    }

                    scope.config = $injector.get(scope.kolom.bron + 'Config');
                    scope.resource = $injector.get(scope.config.resourceNaam).resource();

                    if (scope.kolom.type !== 'select' && scope.kolom.type !== 'selectRef' && scope.kolom.type !== 'selectCodeMV' && scope.kolom.type !== 'selectCodeOmschrijving') {
                        // bewerk kolomdefinitie voor gebruik op subformulier om naam uniek te houden
                        scope[scope.kolom.display + '_subkolommen'] = [];

                        angular.forEach(scope.config.kolommen, function(value) {
                            if (value.display) {
                                var kolom = angular.copy(value);
                                kolom.id = scope.kolom.display + '_' + kolom.display;
                                scope[scope.kolom.display + '_subkolommen'].push(kolom);
                            }
                        });

                        scope.$watch('waarde', function (value) {
                            scope.waardenlijst = {};
                            if (scope.waarde && !scope.status.nieuw) {
                                scope.resource.query(context.params, function(data) {
                                    scope.waarde[scope.kolom.display] = data;
                                    if (scope.kolom.type === 'inlineLijstArea' && data.content) {
                                        setWaardenlijst(data.content);
                                    } else {
                                        scope.waardenlijst.text = 'klik hier om LO3 rubrieken toe te voegen';
                                    }
                                });
                            }
                        });

                        scope.viewItem = function () {
                            if (scope.config.toonJsonOpDetail) {
                                // event toonJson wordt gevuurd daar het tonen via de hoofdpagina gaat
                                if (scope.config.lijstBevatGeenDetail) {
                                    context.params.id = scope.waarde[scope.kolom.display].content[this.$index].iD;
                                    scope.resource.query(context.params, function(data) {
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
                            } else {
                                var onderhandenitem = {};
                                onderhandenitem.status = {};
                                onderhandenitem.status.nieuw = false;
                                modalResolve = {
                                    bron: function() {
                                        return scope.resource;
                                    },
                                    titel: function() {
                                        return scope.config.titel;
                                    }
                                };
                                if (scope.kolom.type === 'inlinelijst') {
                                    if (scope.config.lijstBevatGeenDetail) {
                                        context.params.id = scope.waarde[scope.kolom.display].content[this.$index].iD;
                                        scope.resource.query(context.params, function(data) {
                                            onderhandenitem.item = data;
                                        });
                                    } else {
                                        onderhandenitem.item = scope.waarde[scope.kolom.display].content[this.$index];
                                    }
                                    onderhandenitem.relatie = scope.waarde.naam;
                                    modalTemplateUrl = 'views/generic/editInline.html';
                                    modalController = 'ItemInstanceController';
                                    modalResolve.subkolommen = function() {
                                        return scope[scope.kolom.display + '_subkolommen'];
                                    };
                                } else {
                                    var rubriekbron = $injector.get('ConversieLo3Rubriek').resource();
                                    modalResolve.rubriekBron = function() {
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

                                modalResolve.onderhandenitem = function() {
                                    return onderhandenitem;
                                };

                                modalResolve.toonJsonItem = function() {
                                    return scope.config.toonJsonOpDetail;
                                };

                                if (scope.config.contextParam) {
                                    context.params[scope.config.contextParam] = onderhandenitem.item.iD;
                                }
                                var modalInstance = $modal.open({
                                    templateUrl: modalTemplateUrl,
                                    controller: modalController,
                                    size: 'lg',
                                    resolve: modalResolve
                                });
                                modalInstance.result.then(function(bewaardItem) {
                                    if (scope.kolom.name === 'LO3 Rubrieken') {
                                        // ververs de gegevens door ze opnieuw op te halen. Om dit
                                        // aan client kant te doen is complex
                                        $timeout(function() {
                                            scope.resource.query(context.params, function(data) {
                                                scope.waarde[scope.kolom.display] = data.content;
                                                if (scope.kolom.type === 'inlineLijstArea' && data.content) {
                                                    setWaardenlijst(data.content);
                                                } else {
                                                    scope.waardenlijst.text = 'klik hier om LO3 rubrieken toe te voegen';
                                                }
                                            });
                                        }, 1000);
                                    }
                                });
                            }
                        };

                        scope.nieuwItem = function () {
                            var onderhandenitem = {};
                            onderhandenitem.status = {};
                            onderhandenitem.status.nieuw = true;
                            onderhandenitem.blijfOpWijzig = scope.config.blijfOpWijzig;
                            onderhandenitem.contextParamNaam = scope.config.contextParam;
                            onderhandenitem.item = {};
                            onderhandenitem.item[scope.kolom.relatie] = scope.waarde.iD;
                            if (scope.config.contextParam) {
                                context.params[scope.config.contextParam] = null;
                            }
                            var modalInstance = $modal.open({
                                templateUrl: 'views/generic/editInline.html',
                                controller: 'ItemInstanceController',
                                size: 'lg',
                                resolve: {
                                    onderhandenitem: function() {
                                        return onderhandenitem;
                                    },
                                    bron: function() {
                                        return scope.resource;
                                    },
                                    subkolommen: function() {
                                        return scope[scope.kolom.display + '_subkolommen'];
                                    },
                                    titel: function() {
                                        return scope.config.titel;
                                    },
                                }
                            });
                            modalInstance.result.then(function(bewaardItem) {
                                if (!scope.waarde[scope.kolom.display]) {
                                    scope.waarde[scope.kolom.display] = { 'content': [], 'totalElements': 1, 'size': 20 };
                                }
                                scope.waarde[scope.kolom.display].content.push(bewaardItem);
                            });
                        };

                    } else {
                        scope.resource.query({size: 10000}, function(data) {
                            scope.options = data;
                            if (data.numberOfElements > 0) {
                                for (var x = 0; x < data.content.length; x++) {
                                    if (data.content[x].iD) {
                                        if (scope.waarde[scope.kolom.display] === data.content[x].iD) {
                                            scope.geselecteerdeOptie = data.content[x].naam;
                                        }
                                    } else {
                                        if (scope.waarde[scope.kolom.display] === data.content[x].ordinal) {
                                            scope.geselecteerdeOptie = data.content[x].naam;
                                        }
                                    }
                                }
                                scope.items = data.content;
                            }
                        });
                    }
                } else {
                	if(scope.kolom.type === 'inlinelijst') {
			        	// Geen bron
			        	scope.config = { geenNieuw: true };
                	}
                }


                // link functionaliteit
                if (scope.kolom.type === 'link') {
                    var getUniqueId = function () {
                        var d = new Date().getTime();
                        d += (parseInt(Math.random() * 100)).toString();
                        return d;
                    };

                    scope.$watch('waarde', function (value) {
                        var uuid = getUniqueId();

                        if(scope.kolom.linkExtern) {
                        	scope.linkExtern = $rootScope.configuratie[scope.kolom.linkExtern] + value[scope.kolom.link];
                        } else {
                            var linkConfig = $injector.get(scope.kolom.linkDoel + 'Config');
	                        if (scope.kolom.linkParameter) {
	                            scope.linkdoel = linkConfig.listUri + '?' + scope.kolom.linkParameter + '=' + value[scope.kolom.link] + '&refid=' + uuid;
	                        } else {
	                            scope.linkdoel = linkConfig.listUri + '/' + value[scope.kolom.link] + "?refid=" + uuid;
	                        }
                        }

                        context.vorig[uuid] = context.vorigeurl + "/" + value.iD;
                    });

                    scope.volgLink = function(event) {
                    	if(scope.linkExtern) {
                    		$window.open(scope.linkExtern);
                    	} else {
	                        if (event.ctrlKey == 1) {
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
