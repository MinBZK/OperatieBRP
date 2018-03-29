angular.module('ItemController', [])
    .controller('ItemController', ['$window', '$route', '$location', 'context', '$http', '$rootScope', '$scope',
        function ($window, $route, $location, context, $http, $rootScope, $scope) {
            $scope.list = false;

            $scope.opslaan = function (form) {
                if (form.$dirty && !form.$invalid) {
                    var gewijzigdItem = new $scope.resource($scope.onderhandenitem.item);
                    var nieuwStatus = $scope.status.nieuw;
                    gewijzigdItem.$save(
                        {},
                        function (value) {
                            if ($scope.status.nieuw) {
                                if (!$scope.lijst.content) {
                                    $scope.lijst.content = [];
                                }
                                $scope.lijst.content.push(value);
                                angular.copy(value, $scope.onderhandenitem.item);

                                if ($scope.blijfOpWijzig) {
                                    $scope.$parent.resetNieuw(form);
                                } else {
                                    $scope.$parent.toonLijst();
                                }
                            } else {
                                angular.copy(value, $scope.onderhandenitem.item);
                                $scope.$parent.toonLijst();
                            }
                            $rootScope.$broadcast('info', {type: 'success', message: "Opslaan gelukt."});
                            $rootScope.$broadcast('succes', {item: 'ingang', nieuw: nieuwStatus});
                        },
                        function (httpResponse) {
                            $rootScope.$broadcast('info', {
                                type: 'danger',
                                code: "SAVE_FAILED",
                                message: (httpResponse.data.message ? httpResponse.data.message : "Opslaan mislukt.")
                            });
                        }
                    );
                }
            };

            $scope.annuleren = function () {
                if ($route.current.params.refid) {
                    // andere controller, reset gaat dan automatisch
                    var vorigDoel = context.vorig[$route.current.params.refid];
                    if (vorigDoel) {
                        $location.url(vorigDoel);
                        delete context.vorig[$route.current.params.refid];
                    } else {
                        $window.close();
                    }
                } else {
                    // alleen resetten indien in dezelfde controller gebleven wordt.
                    $scope.$parent.reset();
                    $scope.$parent.toonLijst();
                }
            };
        }]);
