angular.module('ListController', [])
.controller('ListController', ['$window', '$route', '$q', '$location', '$scope', 'lijst', 'item', 'resource', 'config', '$rootScope', 'instelling', 'informatie', 'context',
    function ($window, $route, $q, $location, $scope, lijst, item, resource, config, $rootScope, instelling, informatie, context) {
        $scope.titel = config.titel;
        $scope.itemPagina = config.itemPagina;
        $scope.jsonPagina = config.jsonPagina;
        $scope.status = {};
        $scope.status.nieuw = false;
        $scope.lijst = lijst;
        $scope.instelling = instelling;
        $scope.informatie = informatie;
        $scope.lijstZichtbaar = true;
        $scope.jsonZichtbaar = false;
        $scope.resource = resource.resource();
        $scope.geenDetail = config.geenDetail;
        $scope.geenNieuw = config.geenNieuw;
        $scope.blijfOpWijzig = config.blijfOpWijzig;
        $scope.onderhandenitem = {};
        $scope.readonly = config.geenEdit;
        $scope.vastebreedte = config.vastebreedte;
        $scope.listUri = config.listUri;
        $scope.zoekenZichtbaar = config.geenInitieleLijst;

        // start nieuwe context wanneer ListController het eerst wordt gestart.
        context.params = {};

        // kolommen voor de formulieren
        $scope.columns = [];
        for (var i = 0; i < config.kolommen.length; i++) {
            if (config.kolommen[i].display) {
                $scope.columns.push(config.kolommen[i]);
            }
        }

        // filters is voor zoeken
        $scope.filterValues = {};
        $scope.filters = [];
        for (var k = 0; k < config.kolommen.length; k++) {
            if (config.kolommen[k].filter) {
                $scope.filters.push(config.kolommen[k]);
            }
        }

        //$scope.filterValues[$scope.filters[i].filter];
        //$route kan 0 of meerdere params bevatten. Indien $route params bevat moet er
        // gezocht worden tenzij de param alleen een refid bevat
        var vulZoekveldenEnZoekIndienNodig = function() {
            if (!item) {
                var zoeken = false;
                for (var param in $route.current.params) {
                    if ('refid' !== param) {
                        zoeken = true;
                        $scope.filterValues[param] = $route.current.params[param];
                    }
                }
                if (zoeken) {
                    $scope.zoeken();
                }
            }
        };

        var tonenDetailVoorbereiden = function(value) {
            if (!$scope.geenDetail) {
                if (config.toonJsonOpDetail) {
                    // event toonJson wordt gevuurd daar het tonen via de hoofdpagina gaat
                    if (config.lijstBevatGeenDetail) {
                        context.params.id = value.iD;
                        $scope.resource.get(context.params, function(data) {
                            $scope.jsonData = data;
                            delete $scope.jsonData.$promise;
                            $scope.lijstZichtbaar = false;
                            $scope.jsonZichtbaar = true;
                            $scope.jsonVanuitLijst = true;
                        });
                    } else {
                        $scope.jsonData = value;
                        delete $scope.jsonData.$promise;
                        $scope.lijstZichtbaar = false;
                        $scope.jsonZichtbaar = true;
                        $scope.jsonVanuitLijst = true;
                    }
                } else {
                    if (config.lijstBevatGeenDetail) {
                        // Detail item ophalen
                        $scope.resource.get({id:value.iD}, function(data) {
                            $scope.onderhandenitem.item = data;
                            $scope.lijstZichtbaar = false;
                            if (config.contextParam) {
                               context.params = {};
                               context.params[config.contextParam] = $scope.onderhandenitem.item.iD;
                            }
                        });
                    } else {
                        $scope.onderhandenitem.item = value;
                        $scope.onderhandenitem.orgineelItem = {};
                        angular.copy($scope.onderhandenitem.item, $scope.onderhandenitem.orgineelItem);
                        if (config.contextParam) {
                            context.params = {};
                            context.params[config.contextParam] = $scope.onderhandenitem.item.iD;
                        }
                        $scope.lijstZichtbaar = false;
                    }
                }
                context.vorigeurl = config.listUri;
            }
        };

        if ($scope.lijst.sort) {
            $scope.sort = $scope.lijst.sort.orders[0].property;
            $scope.sortDirection = $scope.lijst.sort.orders[0].direction == 1 ? "desc" : "asc";
        } else {
            $scope.sort = "";
            $scope.sortDirection = "";
        }

        $scope.paginaNummer = $scope.lijst.number + 1;
        $scope.eerstePagina = $scope.lijst.first;
        $scope.laatstePagina = $scope.lijst.last;

        if (item) {
            $q.all(item).then(function(value) {
                $scope.onderhandenitem.item = value;
                $scope.onderhandenitem.orgineelItem = {};
                angular.copy($scope.onderhandenitem.item, $scope.onderhandenitem.orgineelItem);
                $scope.lijstZichtbaar = false;
                if (config.toonJsonOpDetail) {
                    $scope.jsonData = value;
                    delete $scope.jsonData.$promise;
                    $scope.jsonVanuitLijst = true;
                    $scope.jsonZichtbaar = true;
                }

                // niet opgehaalde details moeten nog worden opgehaald.
                if (config.contextParam) {
                    context.params = {};
                    context.params[config.contextParam] = value.iD;
                }
            });
        }

        $scope.reQuery = function () {
            var page = $scope.paginaNummer - 1;
            var sort = $scope.sort !== "" ? ($scope.sort + "," + $scope.sortDirection) : null;
            var parameters = {page: page, sort: sort, size: $scope.instelling.size};
            for (var i = 0; i < $scope.filters.length; i++) {
                parameters[$scope.filters[i].filter] = $scope.filterValues[$scope.filters[i].filter];
            }

            $scope.resource.query(parameters,
                    function (lijst) {
                        $scope.lijst = lijst;
                        $scope.paginaNummer = $scope.lijst.number + 1;
                        $scope.eerstePagina = $scope.lijst.first;
                        $scope.laatstePagina = $scope.lijst.last;
                        if ($scope.lijst.sort) {
                            $scope.sort = $scope.lijst.sort.orders[0].property;
                            $scope.sortDirection = $scope.lijst.sort.orders[0].direction == 1 ? "desc" : "asc";
                        }
                        if(lijst.warning) {
                        	  $rootScope.$broadcast('info', {type: 'danger', message: lijst.warning});
                        }
                    }
            );
        };

        $scope.zoeken = function () {
            $scope.sort = "";
            $scope.sortDirection = "asc";
            $scope.paginaNummer = 1;
            $scope.reQuery();
        };

        $scope.leegMaken = function () {
            $scope.sort = "";
            $scope.sortDirection = "asc";
            $scope.paginaNummer = 1;
            $scope.filterValues = {};
            $scope.reQuery();
        };

        $scope.sorteren = function (sortering) {
            if (sortering == $scope.sort) {
                if ($scope.sortDirection == "asc") {
                    $scope.sortDirection = "desc";
                } else {
                    $scope.sortDirection = "asc";
                }
            } else {
                $scope.sort = sortering;
                $scope.sortDirection = "asc";
            }
            $scope.paginaNummer = 1;
            $scope.reQuery();
        };

        $scope.pagineren = function () {
            $scope.reQuery();
        };

        $scope.vorigePagina = function () {
            if ($scope.eerstePagina === false) {
                $scope.paginaNummer = $scope.paginaNummer - 1;
                $scope.reQuery();
            }
        };

        $scope.volgendePagina = function () {
            if ($scope.laatstePagina === false) {
                $scope.paginaNummer = $scope.paginaNummer + 1;
                $scope.reQuery();
            }
        };

        $scope.viewItem = function (event) {
            if (event.ctrlKey == 1) {
                $window.open($window.location.pathname + '#' + config.listUri + '/' + $scope.lijst.content[this.$index].iD);
            } else {
                tonenDetailVoorbereiden($scope.lijst.content[this.$index]);
            }
        };

        $scope.reset = function () {
            if ($scope.onderhandenitem.orgineelItem) {
                angular.copy($scope.onderhandenitem.orgineelItem, $scope.onderhandenitem.item);
            }
        };

        $scope.nieuwItem = function () {
            context.params = {};
            $scope.onderhandenitem.item = {};
            $scope.onderhandenitem.orgineelItem = null;
            $scope.lijstZichtbaar = false;
            $scope.status.nieuw = true;
            context.vorigeurl = config.listUri;
        };

        $scope.toonLijst = function () {
            $scope.lijstZichtbaar = true;
            $scope.status.nieuw = false;
            $scope.jsonZichtbaar = false;
        };

        // wordt aangeroepen na opslag van een nieuw object
        $scope.resetNieuw = function (form) {
            $scope.status.nieuw = false;
            if (config.contextParam) {
                context.params[config.contextParam] = $scope.onderhandenitem.item.iD;
            }
            if (form) {
                form.$setPristine();
            }
        };

        // toggle jsonZichtbaar
        $scope.toggleJson = function() {
            $scope.jsonZichtbaar = !$scope.jsonZichtbaar;
            if ($scope.jsonVanuitLijst) {
                $scope.jsonVanuitLijst = false;
                $scope.lijstZichtbaar = true;
            }
        };

        vulZoekveldenEnZoekIndienNodig();

    }
]);
