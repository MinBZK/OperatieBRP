myApp.controller('editController', [
	'$scope', 
	'$routeParams',
	'$http',
	'tidyService',
	'utils',
	'$route',
	function($scope, $routeParams, $http, tidyService, utils, $route) {

		var emptyRow=[];
		var columnsLength = 0;
		
		$scope.view = function() {
			var regel=$routeParams.regel;
			$http.get(URL_CONTEXT_PATH + "/trace/view?regel=" + regel)
				.success(function(data) {
					$scope.header=data.header;
					$scope.entiteit=data.data.entiteit;
					$scope.kenmerk=data.data.kenmerk;
					$scope.aanname=data.aanname;
					$scope.omschrijving=data.omschrijving;
					$scope.rows=data.rows;
					
					// init: for creating new rows
					emptyRow=[];
					for (var i=0; i < $scope.rows[0][0].length; i ++ ) {
						emptyRow.push("");
					}
					
				})
				.error(function(data, status, headers, config) {
					$scope.alert="alert alert-danger"
					$scope.alertMessage = "De regel kan niet opgehaald worden ";
				});
		}
		
		$scope.addKolom = function() {
			for (key in $scope.rows[arguments[0]]) {
				$scope.rows[arguments[0]][key].push('');
			}

			$scope.header[arguments[0]].push('');
		}

		$scope.removeKolom = function() {
			for (var key in $scope.rows[arguments[0]]) {
				$scope.rows[arguments[0]][key].splice(-1, 1);
			}

			$scope.header[arguments[0]].splice(-1, 1);
		}
		
		$scope.addRij = function() {
			var index=0;
			// determine highest key
			for (var key in $scope.rows[arguments[0]]) {
				if (index == 0 || index < (key - 0)) {
					index=key-0;
				}
			}

			
			$scope.rows[arguments[0]].splice(index, 0, emptyRow);
			$scope.entiteit[arguments[0]].splice(index, 0, "");
			$scope.kenmerk[arguments[0]].splice(index, 0, "");
		}
	
		$scope.removeRij = function() {
			$scope.entiteit[arguments[0]].splice(-2, 1);
			$scope.kenmerk[arguments[0]].splice(-2,1);
			$scope.rows[arguments[0]].splice(-2, 1)
		}

		$scope.save = function() {
			
		}

	}
]);