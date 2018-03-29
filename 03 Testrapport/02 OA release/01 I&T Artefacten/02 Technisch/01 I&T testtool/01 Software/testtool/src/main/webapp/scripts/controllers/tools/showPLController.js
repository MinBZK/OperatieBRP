myApp.controller('showPLController', [
	'$scope', 
	'$routeParams',
	'$http',
	'tidyService',
	'_',
	function($scope, $routeParams, $http, tidyService, _) {
		$scope.show = function() {
			var stage = $routeParams.stage;
			var anr = $routeParams.anr;

			$http.get(URL_CONTEXT_PATH + "/show_pl?stage=" + stage + "&anr=" + anr)
				.success(function(data) {
					$scope.pl=data;
			});
		}
	}
])
.filter(function(_) {
	return function (input) {
		return angular.isArray(input);
	}
});