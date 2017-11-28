angular.module('BrpLijstveld', [])
    .directive("brpLijstveld", ["$injector", "$filter", function ($injector, $filter) {
        return {
            restrict: 'E',
            scope: {
                waarde: '='
            },
            link: function (scope, element, attrs) {
                scope.kolom = JSON.parse(attrs.kolom);
                scope.veld = 'fields/lijstspan.html';

                scope.verversLijst = function () {
                    if (scope.kolom.bron && scope.kolom.type.indexOf('select') === 0) {
                        scope.config = $injector.get(scope.kolom.bron + 'Config');
                        scope.resource = $injector.get(scope.config.resourceNaam).resource();
                        var sid = scope.waarde[scope.kolom.display];
                        if (sid) {
                            var swaarde = scope.resource.get({id: sid, size: null}, function () {
                                if (scope.kolom.ngFilter) {
                                    $filter(scope.kolom.ngFilter)(swaarde);
                                }
                                var voorloper = '';
                                if (swaarde.code) {
                                    voorloper = swaarde.code + ' - ';
                                }

                                if (scope.kolom.type === 'selectCodeMV') {
                                    scope.geselecteerdewaarde = voorloper + swaarde.naamMannelijk + '/' + swaarde.naamVrouwelijk;
                                } else if (scope.kolom.type === 'selectCodeOmschrijving') {
                                    scope.geselecteerdewaarde = voorloper + swaarde.omschrijving;
                                } else if (scope.kolom.type === 'selectIdentifier') {
                                    scope.geselecteerdewaarde = voorloper + swaarde.identifier;
                                } else {
                                    scope.geselecteerdewaarde = voorloper + swaarde.naam;
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

                scope.$on('succes', function () {
                    scope.verversLijst();
                });
            },
            template: '<span style="cursor: hand;">{{geselecteerdewaarde}}</span>'
        };
    }]);
