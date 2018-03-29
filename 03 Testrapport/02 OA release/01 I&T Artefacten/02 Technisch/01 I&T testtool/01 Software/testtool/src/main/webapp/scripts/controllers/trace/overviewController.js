myApp.controller('overviewController', [
	'$scope', 
	'$routeParams',
	'$http',
	'tidyService',
	'utils',
	'$location',
	function($scope, $routeParams, $http, tidyService, utils, $location) {

		$scope.list = function () {
			$http.get(URL_CONTEXT_PATH + "/trace/list")
				.success(function(data) {
					$scope.lijst=data.data;
				})
				.error(function(data, status, headers, config) {
					// TODO
				});
		}
		
		$scope.open = function (regel) {
			$location.path("/Testtool/Tools/Trace/Edit/" + regel);
		}
		
		$scope.matrix = function() {
			$location.path("/Testtool/Tools/Trace/Matrix");
		}
	}
]);