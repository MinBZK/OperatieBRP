angular.module('BrpLijstveld', [])
.directive("brpLijstveld", ["$injector", '$rootScope', function ($injector, $rootScope) {
        return {
            restrict: 'E',
            scope: {
                waarde: '='
            },
            link: function (scope, element, attrs) {
                scope.kolom = JSON.parse(attrs.kolom);
                scope.veld = 'fields/lijstspan.html';

                scope.verversLijst = function() {
                    if (scope.kolom.bron && (scope.kolom.type === 'select' || scope.kolom.type === 'selectRef' || scope.kolom.type === 'selectCodeMV' || scope.kolom.type === 'selectCodeOmschrijving')) {
                        scope.config = $injector.get(scope.kolom.bron + 'Config');
                        scope.resource = $injector.get(scope.config.resourceNaam).resource();
                        var sid = scope.waarde[scope.kolom.display];
                        if (sid) {
                            var swaarde = scope.resource.get({id: sid, size: null} , function() {
                            	if (scope.kolom.type === 'selectCodeMV') {
                            	    scope.geselecteerdewaarde = swaarde.naamMannelijk + '/' + swaarde.naamVrouwelijk;
                            	} else if (scope.kolom.type === 'selectCodeOmschrijving') {
                                    scope.geselecteerdewaarde = swaarde.Omschrijving;
                                } else if (scope.kolom.type === 'select') {
                                    scope.geselecteerdewaarde = swaarde.naam;
                                } else {
                                    scope.geselecteerdewaarde = swaarde.Naam;
                                }
                            });
                        }
                    } else {
                        var path = scope.kolom.display.split('.');
                        scope.geselecteerdewaarde = scope.waarde[path[0]];
                        if (scope.geselecteerdewaarde) {
                            for (var x = 1; x < path.length; x++) {
                                scope.geselecteerdewaarde = scope.geselecteerdewaarde[path[x]];
                            }
                        }
                    }
                    if (scope.geselecteerdewaarde === 'undefined') {
                        scope.geselecteerdewaarde = "";
                    }
                };
                scope.verversLijst();

                scope.$on('succes', function() {
                    scope.verversLijst();
                });
            },
            template: '<span  style="cursor: hand;">{{geselecteerdewaarde}}</span>'
        };
    }]);
