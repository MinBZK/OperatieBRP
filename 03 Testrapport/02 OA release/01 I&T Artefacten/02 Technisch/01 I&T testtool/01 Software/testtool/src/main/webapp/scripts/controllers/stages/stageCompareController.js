myApp.controller('stageCompareController', [
	'$scope', 
	'$routeParams',
	'$http',
	'tidyService',
	'compareService',
	'utils',
	'$route',
	'_',
	function($scope, $routeParams, $http, tidyService, compareService, utils, $route, _) {
		
		$scope.show = function() {
			var stage = $routeParams.stage;
			var flavour = $routeParams.flavour || "BRP";
			$scope.anr = $routeParams.nr;
			$scope.stage = stage;
			$scope.flavour = flavour;
			
			if (flavour == 'GBA' && stage != 'TC') {
				var afnemer = $routeParams.afnemer;
				var berichtnummer = $routeParams.berichttype;
				var script = $routeParams.script;				
								
				$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&afn=' + afnemer + "&stage=" + stage + "&flavour=" + flavour + "&bn=" + berichtnummer + "&script=" + script)
					.success(function(data) {
						$scope.data = [];
						$scope.script = script;
						$scope.afnemer = afnemer;
						$scope.berichtnummer = berichtnummer;
						$scope.excel = data.meta.excel;
						$scope.bsn = data.meta.bsn;
						$scope.status_table_actual = [];
						$scope.status_table_expected = [];
						$scope.timestamps = data.timestamps;
						
						$scope.data = compareService.parseLO3("TOOL", data.data);
					});
			} else if (flavour == 'BRP') {
				var afnemer = $routeParams.afnemer;
				var berichttype = $routeParams.berichttype;
				var script = $routeParams.script;
				var volgnr = $routeParams.volgnr;
				
				$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&script=' + script + "&stage=" + stage + "&flavour=" + flavour + "&berichttype=" + encodeURIComponent(berichttype) + "&afn=" + afnemer + "&volgnr=" + volgnr)
					.success(function(data) {
						$scope.stage = stage;
						$scope.flavour = flavour;
						$scope.script = script;
						$scope.excel = data.meta.excel;
						$scope.bsn = data.meta.bsn;
						$scope.volgnr = volgnr;
						$scope.timestamps = data.timestamps;
						
						$scope.headers=[];
						$scope.data = compareService.fixBRPResult(data, $scope.headers, $scope.script);
					});
				
				$scope.flavour = flavour;
				$scope.script = script;
				$scope.afnemer = afnemer;
				$scope.berichttype = berichttype;
				
			} else if (flavour == 'RESBIJ' || flavour == 'RESBEV') {
				var berichttype = $routeParams.berichttype;
				var script = $routeParams.script;
				var volgnr = $routeParams.volgnr;
				
				$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&script=' + script + "&stage=" + stage + "&flavour=" + flavour + "&berichttype=" + encodeURIComponent(berichttype) + "&volgnr=" + volgnr)
					.success(function(data) {
						$scope.stage = stage;
						$scope.flavour = flavour;
						$scope.script = script;
						$scope.excel = data.meta.excel;
						$scope.bsn = data.meta.bsn;
						$scope.volgnr = volgnr;
						$scope.timestamps = data.timestamps;
						
						$scope.headers=[];
						$scope.data = compareService.fixBRPResult(data, $scope.headers, $scope.script);
					});
				
				$scope.flavour = flavour;
				$scope.script = script;
				$scope.berichttype = berichttype;
			
		} else if (stage == 'TC') {			
				var script = $routeParams.script;
				$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&script=' + script + "&stage=" + stage + "&flavour=" + flavour)
					.success(function(data) {						
						$scope.stage = stage;
						$scope.flavour = flavour;
						$scope.script = script;
						$scope.data = {};
						$scope.bsn = data.meta.bsn;
						$scope.excel = data.meta.excel;
						$scope.timestamps = data.timestamps;
						$scope.data = compareService.parseLO3("TOOL", data.data);
					});
		} else {
				var script = $routeParams.script;
				$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&script=' + script + "&stage=" + stage + "&flavour=" + flavour)
					.success(function(data) {
						$scope.stage = stage;
						$scope.flavour = flavour;
						$scope.script = script;
						$scope.data = data.data;
						$scope.bsn = data.meta.bsn;
						$scope.excel = data.meta.excel;
						$scope.timestamps = data.timestamps;

						if (data.compare.message) {
							switch (data.compare.message) {
								case "ACTUAL_NIET_CORRECT" :
									$scope.alert = "alert alert-danger";
									$scope.alertMessage = "Het actual bestand is leeg. Dit duidt hoogstwaarschijnlijk op een incorrecte query. Controleer de query met behulp van pgAdmin.";
									break;
								default:
									break;
							}
						}
					});
			}
		}
		
		$scope.showRaw = function() {
			$http.get(URL_CONTEXT_PATH + '/show_raw?anr=' + $scope.anr + '&volgnr=' + $scope.volgnr + '&afn=' + $scope.afnemer + '&berichttype=' + encodeURIComponent($scope.flavour=="BRP"?$scope.berichttype:$scope.berichtnummer) + "&stage=" + $scope.stage + "&substage=" + $scope.substage + "&flavour=" + $scope.flavour)
				.success(function(data) {
					var modal = $('#rawModal')
						.css ({
							width:'auto', //probably not needed
							height:'auto', //probably not needed 
							'max-height':'100%'
						})
						.one('show.bs.modal', function(e) {
						});
					modal.modal('show');
					$scope.rawData=data;
				});
		}

		$scope.copy = function(stage, substage, flavour) {			
			if (flavour == 'GBA' && stage != 'TC') {
				$http.get(URL_CONTEXT_PATH + '/copy?anr=' + arguments[3] + '&afn=' + arguments[4] + '&bn=' + arguments[5] + "&stage=" + stage + "&substage=" + substage + "&flavour=" + flavour + '&script=' + arguments[6])
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			} else if (flavour == 'BRP') {
				$http.get(URL_CONTEXT_PATH + '/copy?anr=' + arguments[3] + '&afn=' + arguments[4] + '&berichttype=' + encodeURIComponent(arguments[5]) + '&script=' + arguments[7] + '&volgnr=' + arguments[6] + "&stage=" + stage + "&substage=" + substage + "&flavour=" + flavour)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			} else if (flavour == 'RESBIJ' || flavour == 'RESBEV') {
				$http.get(URL_CONTEXT_PATH + '/copy?anr=' + arguments[3] + '&berichttype=' + encodeURIComponent(arguments[4]) + '&script=' + arguments[6] + '&volgnr=' + arguments[5] + "&stage=" + stage + "&substage=" + substage + "&flavour=" + flavour)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			} else {
				$http.get(URL_CONTEXT_PATH + '/copy?anr=' + arguments[3] + '&script=' + arguments[4] + "&stage=" + stage + "&substage=" + substage + "&flavour=" + flavour)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			}
		}
		
		$scope.delete = function(stage, substage, flavour, anr) {
			var type = arguments[0];
			if (flavour == 'GBA' && stage != 'TC') {
				$http.get(URL_CONTEXT_PATH + '/delete_expected?anr=' + anr + '&afn=' + arguments[4] + '&bn=' + arguments[5] + '&script=' + arguments[6] + "&stage=" + stage + "&substage=" + substage + "&flavour=" + flavour)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			} else if (flavour == 'BRP') {
				$http.get(URL_CONTEXT_PATH + '/delete_expected?anr=' + anr + '&afn=' + arguments[4] + '&berichttype=' + encodeURIComponent(arguments[5]) + '&volgnr=' + arguments[6] + '&script=' + arguments[7] + "&stage=" + stage + "&substage=" + substage + "&flavour=" + flavour)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			} else {
				$http.get(URL_CONTEXT_PATH + '/delete_expected?anr=' + anr + '&script=' + arguments[4] + "&stage=" + stage + "&substage=" + substage + "&flavour=" + flavour)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			}
		}		
	}
])
.filter('isArray', function() {
	return function (input) {
		return angular.isArray(input) || angular.isObject(input);
	};
}).filter('isNotArray', function() {
	return function (input) {
		return !angular.isArray(input) && !angular.isObject(input);
	};
});
