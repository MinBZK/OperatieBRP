angular.module('InfoController', [])
.value('informatie', [])
.controller('InfoController', ['$scope', 'informatie', '$timeout',
    function ($scope, informatie, $timeout) {

        $scope.informatie = informatie;

        $scope.infosLeegMaken = function () {
            informatie.length = 0;
        };

        $scope.informatieBeschikbaar = function() {
            return $scope.informatie.length > 0;
        };

        $scope.infoWissen = function(index) {
            $scope.informatie.splice(index, 1);
        };

        $scope.$on('info', function (event, data) {
            //console.log(data);
            informatie.push(data);
            if (informatie.length > 0) {
                $timeout(function () {
                    $scope.infosLeegMaken();
                }, 5000);
            }
        });
    }
]);
