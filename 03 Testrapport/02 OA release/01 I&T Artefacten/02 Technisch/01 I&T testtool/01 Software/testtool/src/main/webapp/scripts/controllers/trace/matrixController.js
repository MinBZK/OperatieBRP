myApp.controller('matrixController', [
	'$scope', 
	'$routeParams',
	'$http',
	'tidyService',
	'utils',
	'$location',
	'_',
	function($scope, $routeParams, $http, tidyService, utils, $location, _) {

		$scope.matrix = function() {
			var regel=$routeParams.regel;
			$http.get(URL_CONTEXT_PATH + "/trace/matrix")
				.success(function(data) {
					$scope.data=data.data;

					$scope.tm={};
					for (var i = 0; i < data.data.fysiek.length; i ++) {	
						koppel=data.data.fysiek[i].koppel.split(',');
						for (var j = 0; j < koppel.length; j ++) {
							if (!$scope.tm[koppel[j]]) {
								$scope.tm[koppel[j]] = {};
							}
							
							$scope.tm[koppel[j]][i] = 'T';
						}
					}
				})
				.error(function(data, status, headers, config) {
					$scope.alert="alert alert-danger"
					$scope.alertMessage = "De matrix kan niet gegenereerd worden";
				});
		}
		
		$scope.click = function(row, $index) {
			column=$index;
			if ($scope.tm[row][column] == "") {
				$scope.tm[row][column]='T';
			} else if ($scope.tm[row][column] != 'B') {
				$scope.tm[row][column]='B';
			} else if ($scope.tm[row][column] == 'B') {
				$scope.tm[row][column]='';
			}
		}
	}

]);