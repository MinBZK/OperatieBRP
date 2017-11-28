angular.module('InfoController', [])
.controller('InfoController', ['$scope', '$timeout',
    function ($scope, $timeout) {

        $scope.informatie = [];

        $scope.infosLeegMaken = function () {
            $scope.informatie.length = 0;
        };

        $scope.informatieBeschikbaar = function() {
            return $scope.informatie.length > 0;
        };

        $scope.infoWissen = function(index) {
            $scope.informatie.splice(index, 1);
        };

        $scope.$on('info', function (event, data) {
            $scope.informatie.push(data);
            if ($scope.informatie.length > 0) {
                $timeout(function () {
                    $scope.infosLeegMaken();
                }, 5000);
            }
        });
    }
]);
