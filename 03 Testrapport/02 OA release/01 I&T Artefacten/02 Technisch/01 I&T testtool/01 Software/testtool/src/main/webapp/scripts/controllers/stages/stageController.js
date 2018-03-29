myApp
	.controller('stageController', ['$scope', '$http', '_', '$routeParams', '$location', '$window', function($scope, $http, _, $routeParams, $location, $window) {

		function getPrevNextStage(data) {
			var keys = _.keys(data)
			if ((index = keys.indexOf($scope.stage)) != -1) {
				if (index > 0 && data[keys[index - 1]]) {
					$scope.previousStage=keys[index - 1];
				} else {
					$scope.previousStage="";
				}
				
				if (index < (keys.length - 1) && data[keys[index + 1]]) {
					$scope.nextStage=keys[index + 1];
				} else {
					$scope.nextStage="";
				}
			}
		}

		$scope.init = function() {
			$scope.stage=$routeParams.stage;
			$scope.substage=$routeParams.substage;
			$scope.flavour=$routeParams.flavour;
			$scope.anr=$routeParams.nr;
			
			$http.get(URL_CONTEXT_PATH + "/stages?stage=" + $scope.stage + "&anr=" + $scope.anr)
				.success(function(data) {
					$scope.anr=data.meta['anr'];
					$scope.test=data.meta['test'];
					$scope.stage=data.meta['stage'];
					$scope.flavour=data.meta['flavour'];
					$scope.bsn=data.meta['bsn'];
					$scope.excel=data.meta['excel'];
					for (key in data.results[$scope.stage]) {
						if (data.results[$scope.stage][key].status == "ok") {
							data.results[$scope.stage][key]['class']="btn-success";
						} else if (data.results[$scope.stage][key].status == "nok") {
							data.results[$scope.stage][key]['class']="btn-danger";
						} else {
							data.results[$scope.stage][key]['class']="btn-warning";						
						}
					}

					$scope.status=data.results[$scope.stage];
					getPrevNextStage(data.results);
				});
		}

		$scope.open = function(stage, substage, flavour) {
			if (flavour == 'GBA' && stage != 'TC') {
				$location.path("/Testtool/Stages/Mutaties/" + stage + "/" + flavour + "/" + arguments[3]);
			} else if (flavour == 'BRP') {
				$location.path("/Testtool/Stages/Mutaties/" + stage + "/" + flavour + "/" + arguments[3]);
			} else if (flavour == "RESBIJ" || flavour == "RESBEV") {
				$location.path("/Testtool/Stages/Responses/" + stage + "/" + flavour + "/" + arguments[3]);
			} else {
				$location.path("/Testtool/Stages/Mutaties/" + stage + "/" + flavour + "/" + arguments[3]);
			} 
		}
		
		$scope.showPL = function() {
			var url="#Show/" + $scope.stage + "/" + $scope.anr;
			$window.open($location.absUrl().split('#')[0] + url);
		}
	}
]);