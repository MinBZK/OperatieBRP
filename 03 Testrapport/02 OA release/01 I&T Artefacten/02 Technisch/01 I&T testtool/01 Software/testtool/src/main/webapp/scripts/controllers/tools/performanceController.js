myApp.controller(
	'performanceController', [
		'$scope',
		'$http',
		function($scope, $http) {
			$scope.url = "";
			$scope.get = function() {
				$http.get($scope.url)
					.success(function(data) {
						for (key in data) {
							$scope.content = data;
						}
					});
			}
		}
]);