myApp.controller(
	'levsautController', [
		'$scope', '$filter', '$http', function($scope, $filter, $http) {			
			$scope.convert = function() {
				$("#go").addClass('disabled');
				$http.post(URL_CONTEXT_PATH + "/levsaut_convert", $scope.csv)
					.success(function(data) {
						$scope.result=data;
						$("#go").removeClass('disabled');
				});
			}
		}
]);
