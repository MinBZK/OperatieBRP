angular.module('BrpZoekveld', [])
    .directive("brpZoekveld", ["$injector", '$filter', function ($injector, $filter) {
        return {
            restrict: 'E',
            scope: {
                waarde: '='
            },

            link: function (scope, element, attrs) {
                scope.kolom = JSON.parse(attrs.kolom);
                scope.veld = 'fields/zoek/' + scope.kolom.type + '.html';

                if (scope.kolom.bron && scope.kolom.type.indexOf('select') === 0) {
                    scope.config = $injector.get(scope.kolom.bron + 'Config');
                    scope.resource = $injector.get(scope.config.resourceNaam).resource();
                    scope.resource.query({size: 4000}, function (data) {
                        scope.options = data;
                        if (data.content) {
                            scope.items = data.content;
                            if (scope.kolom.ngFilter) {
                                $filter(scope.kolom.ngFilter)(scope.items);
                            }
                        }
                    });
                }

                if (scope.kolom.type === 'datum') {
                    scope.datepicker = {};
                    scope.datepicker.dateOptions = {
                        formatDay: 'd',
                        startingDay: 1
                    };

                    scope.open = function (event) {
                        event.preventDefault();
                        event.stopPropagation();

                        scope.datepicker.opened = true;
                    };
                }
            },
            template: '<div ng-include="veld"></div>'
        };
    }]);
