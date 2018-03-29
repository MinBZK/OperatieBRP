myApp.controller('showLogsController', [
	'$scope', 
	'$routeParams',
	'$http',
	function($scope, $routeParams, $http) {
		$scope.show = function() {
			$http.get(URL_CONTEXT_PATH + "/getErrorLog")
				.success(function(data) {
					$scope.logs=data;
			});
		}
	}
]);