angular.module('ItemInstanceController', [])
.controller('ItemInstanceController', ['$rootScope', '$scope', '$modalInstance', 'context', 'onderhandenitem', 'bron', 'subkolommen', 'titel',
    function($rootScope, $scope, $modalInstance, context, onderhandenitem, bron, subkolommen, titel) {
        $scope.onderhandenitem = onderhandenitem;
        $scope.subkolommen = subkolommen;
        var actie = ' wijzigen';
        if (onderhandenitem.status.nieuw) {
            actie = ' toevoegen';
        }
        if (onderhandenitem.relatie) {
            $scope.titel = titel + actie + ' - ' + onderhandenitem.relatie;
        } else {
            $scope.titel = titel + actie;
        }

        $scope.opslaan = function (form) {
        	if(form.$dirty && !form.$invalid){
	            var gewijzigdItem = new bron($scope.onderhandenitem.item);
	            gewijzigdItem.$save(
	                    context.params,
	                    function success(value, responseHeaders) {
	                        if (onderhandenitem.status.nieuw) {
	                            angular.copy(value, $scope.onderhandenitem.item);
	                            if (onderhandenitem.blijfOpWijzig) {
	                                context.params[onderhandenitem.contextParamNaam] = value.iD;
	                                onderhandenitem.status.nieuw = false;
	                            } else {
	                                $modalInstance.close(value);
	                            }
	                        } else {
	                            $modalInstance.close(value);
	                        }
	                        $rootScope.$broadcast('info', {type: 'success', message: "Opslaan gelukt."});
	                        $rootScope.$broadcast('succes', {item: 'sub'});
	                    },
	                    function error(httpResponse) {
	                        if (httpResponse.data) {
	                            $rootScope.$broadcast('info', {type: 'danger', code: "SAVE_FAILED", message: (httpResponse.data.message ? httpResponse.data.message : "Opslaan mislukt.")});
	                        } else {
	                            $rootScope.$broadcast('info', {type: 'danger', code: "SAVE_FAILED", message: "Opslaan niet mogelijk, object is van onbekend type"});
	                        }
	
	                    }
	            );
	        }
        };

        $scope.annuleren = function () {
            $modalInstance.dismiss('cancel');
        };
    }
]);
