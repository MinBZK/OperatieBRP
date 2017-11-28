angular.module('ItemJsonController', [])
.controller('ItemJsonController', ['$scope', 'context',
    function($scope, context) {
        $scope.$on('toonJson', function (event, data) {
            $scope.titel = data.titel;
            $scope.jsonData = data.json;
            delete $scope.jsonData.$promise;
            $scope.$parent.toggleJson();
        });

        $scope.sluiten = function () {
            // verwijder id van de context zodat vorige scherm weer goed gaat.
            // deze wordt weer opnieuw geladen (wat best vreemd is)
            delete context.params.id;
            $scope.$parent.toggleJson();
        };
    }
]);
