myApp.controller(
	'afterburnerController', [
		'$scope', '$filter', '$http', function($scope, $filter, $http) {			
			$scope.init = function() {
				$http.get(URL_CONTEXT_PATH + "/view_afterburner")
					.success(function(data) {
						$scope.afterburner=data['afterburner'].join('\n');
						$scope.log=data['log'].join('\n');
					}
				);
			}

			$scope.save = function() {
				$http.post(URL_CONTEXT_PATH + "/save_afterburner", $scope.afterburner)
					.success(function(data) {
						$scope.alert="alert alert-success";
						$scope.alertMessage="De afterburner is opgeslagen";
					}
				);
			}
		}
]);
