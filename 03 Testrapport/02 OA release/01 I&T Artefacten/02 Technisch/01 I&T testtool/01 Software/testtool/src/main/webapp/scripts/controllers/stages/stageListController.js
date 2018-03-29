myApp.controller('stageListController', [
	'$scope',
	'$http',
	'$interval',
	'$timeout',
	'$location',
	'$routeParams',
	'_',
	'$q',
	'$route',
	'$filter',
	'$window',
	'$sce',
	'compareService',
	function($scope, $http, $interval, $timeout, $location, $routeParams, _, $q, $route, $filter, $window, $sce, compareService) {

		$scope['ELEMENTS']=LO3_ELEMENTS;
		$scope['GROUPS']=LO3_GROUPS;			

		$scope.btn={};
		var previousBtn={};
		$scope.checkbox={};
		
		$scope.$watch('btn', function(newValue, oldValue) {
			if (! _.isEqual(previousBtn, $scope.btn)) {
				$scope.checkbox={};
				var list=_.clone($filter('custom')($scope.content, $scope.btn));	
				for (var i = 0; i < list.length; i ++ ) {
					var index=_.indexOf($scope.content, _.findWhere($scope.content, {'showCompare': 'true'}));
					if (index != -1) {
						$scope.content.splice(index, 1);
					}
				}

				list=_.clone($filter('custom')($scope.content, $scope.btn));	
				if (_.isEqual(newValue, {status: 'nok'})) {
					for (var i = 0; i < list.length; i ++ ) {
						var index=_.indexOf($scope.content, _.findWhere($scope.content, {'naam': list[i]['naam']}));
						if (index != -1) {
							$scope.content.splice(index+1, 0, {'naam': list[i]['naam'], 
								'showCompare': 'true', 
								'status': 'nok'});
							getCompareData(list[i]['naam'], newValue);
						}
					}
				} else if (_.isEqual(newValue, {status: 'notchecked'})) {
					for (var i = 0; i < list.length; i ++ ) {
						var index=_.indexOf($scope.content, _.findWhere($scope.content, {'naam': list[i]['naam']}));
						if (index != -1) {
							$scope.content.splice(index+1, 0, {'naam': list[i]['naam'], 
								'showCompare': 'true', 
								'status': 'notchecked'});
							getCompareData(list[i]['naam'], newValue);
						}
					}
				} else if (_.isEqual(newValue, {status: 'ok'})) {
					for (var i = 0; i < list.length; i ++ ) {
						var index=_.indexOf($scope.content, _.findWhere($scope.content, {'naam': list[i]['naam']}));
						if (index != -1) {
							$scope.content.splice(index+1, 0, {'naam': list[i]['naam'], 
								'showCompare': 'true', 
								'status': 'ok'});
							getCompareData(list[i]['naam'], newValue);
						}
					}
				}
				
				previousBtn=$scope.btn;
			}
		});

		function getCompareData (script, status) {
			var index=_.indexOf($scope.content, _.findWhere($scope.content, {'naam': script}));
 			switch ($scope.flavour) { 				
 				case 'SQL': 
					$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&script=' + script + "&stage=" + $scope.stage + "&flavour=" + $scope.flavour)
						.success(function(data) {
							$scope.content[index+1]['data']=data.data;
							$scope.content[index+1]['timestamps']=data.timestamps;
						});
 					
 					break;
 				case 'TC':
 					$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&script=' + script + "&stage=" + $scope.stage + "&flavour=" + $scope.flavour)
						.success(function(data) {						
							$scope.content[index+1]['data']=compareService.parseLO3("TOOL", data.data);
							$scope.content[index+1]['timestamps']=data.timestamps;
						});				
 					
 					break;
 				case 'BRP':
					$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&script=' + script + "&stage=" + $scope.stage + "&flavour=" + $scope.flavour + "&berichttype=" + encodeURIComponent($scope.berichttype) + "&afn=" + $scope.afnemer + "&volgnr=" + $scope.volgnr)
						.success(function(data) {
							var headers=[];
							$scope.content[index+1]['data']=compareService.fixBRPResult(data, headers, script);
							$scope.content[index+1]['headers']=headers;
							$scope.content[index+1]['timestamps']=data.timestamps;
						}); 					
 					break;
 				case 'GBA':
					$http.get(URL_CONTEXT_PATH + '/stages_compare?anr=' + $scope.anr + '&afn=' + $scope.afnemer + "&stage=" + $scope.stage + "&flavour=" + $scope.flavour + "&bn=" + $scope.berichtnummer + "&script=" + script)
						.success(function(data) {
							$scope.content[index+1]['data']=compareService.parseLO3("TOOL", data.data);
							$scope.content[index+1]['timestamps']=data.timestamps;
						});
					break;
 			}
		}
		
		function setBadges(data) {
			$scope.ok = 0;
			$scope.nok = 0;
			$scope.notchecked = 0;
			
			for (var i = 0; i < data.length; i ++) {
				if (data[i]['status'] == "ok") {
					$scope.ok ++;
				}
				
				if (data[i]['status'] == "nok") {
					$scope.nok ++;
				}
				
				if (data[i]['status'] == "notchecked") {
					$scope.notchecked ++;
				}
			}
			
			$scope.total=$scope.ok+$scope.nok+$scope.notchecked;
		}
		
		$scope.list = function() {
			$scope.stage=$routeParams.stage;
			$scope.substage=$routeParams.substage;
			$scope.flavour=$routeParams.flavour || "BRP";
			
			if ($scope.flavour != 'GBA') {
				$scope.anr = $routeParams.nr;
				if ($scope.flavour == "BRP" || $scope.flavour == "RESBIJ" || $scope.flavour == "RESBEV") {
					if (!$routeParams.berichttype) {
						listLeveringen($routeParams.stage, $routeParams.substage, $scope.flavour, $scope.anr);
					} else {
						$scope.afnemer = $routeParams.afnemer;
						$scope.berichttype = $routeParams.berichttype;
						$scope.volgnr = $routeParams.volgnr;
						$scope.showScripts=true;
						listScripts($routeParams.stage, $routeParams.substage, $scope.flavour, $scope.anr, $routeParams.afnemer, $routeParams.berichttype, $routeParams.volgnr);
					}
				} else {
					$scope.showScripts=true;
					listScripts($routeParams.stage, $routeParams.substage, $routeParams.flavour, $scope.anr);
				}
			} else {
				$scope.afnemer = $routeParams.afnemer;
				$scope.berichtnummer = $routeParams.berichttype;
				$scope.anr = $routeParams.nr;
				if (!$routeParams.berichttype) {
					listLeveringen($routeParams.stage, $routeParams.substage, $routeParams.flavour, $scope.anr);
					if ($scope.stage == "TC") {
						$scope.showScripts=true;
					}
				} else {
					$scope.showScripts=true;
					listScripts($routeParams.stage, $routeParams.substage, $routeParams.flavour, $scope.anr, $routeParams.afnemer, $routeParams.berichttype);
				}
			}
		}

		$scope.open = function(stage, substage, flavour) {
			if (flavour == 'BRP') {
				if (!arguments[7]) {
					// list
					$location.path("/Testtool/Stages/Mutaties/" + stage + "/"  + flavour + "/" + arguments[3] + "/" + arguments[4] + "/" + arguments[5] + "/" + arguments[6]);
				} else {
					// compare
					$location.path("/Testtool/Stages/Mutaties/" + stage + "/" + flavour + "/" + arguments[3] + "/" + arguments[4] + "/" + arguments[5] + "/" + arguments[6] + "/" + arguments[7]);
				}
				
			} else if (flavour == 'GBA' && stage != 'TC') {
				if (!arguments[6]) {
					// list
					$location.path("/Testtool/Stages/Mutaties/" + stage + "/"  + flavour + "/" + arguments[3] + "/" + arguments[4] + "/" + arguments[5]);
				} else {
					// compare
					$location.path("/Testtool/Stages/Mutaties/" + stage + "/" + flavour + "/" + arguments[3] + "/" + arguments[4] + "/" + arguments[5] + "/" + arguments[6]);
				}
			} else if (flavour == 'RESBIJ' || flavour == "RESBEV") {
				if (!arguments[6]) {
					// list
					$location.path("/Testtool/Stages/Responses/" + stage + "/"  + flavour + "/" + arguments[3] + "/" + arguments[4] + "/" + arguments[5]);
				} else {
					// compare
					$location.path("/Testtool/Stages/Responses/" + stage + "/" + flavour + "/" + arguments[3] + "/" + arguments[4] + "/" + arguments[5] + "/" + arguments[6]);
				}
			} else {
				// voor flavour SQL (alle) en GBA (TC)
				if (stage != 'IV' && stage != 'TC') {
					$location.path("/Testtool/Stages/Mutaties/" + stage + "/"  + flavour + "/" + arguments[3] + "/" + arguments[4]);
				} else {
					$location.path("/Testtool/Stages/Enkel/" + stage + "/" + flavour + "/" + arguments[3] + "/" + arguments[4]);
				}
			} 
		}		

		function listScripts(stage, substage, flavour, argument3, afnemer, berichttype, volgnr) {
			var url = URL_CONTEXT_PATH + "/stages_list?stage=" + stage + "&substage=" + substage + "&flavour=" + flavour;
			url += "&anr=" + argument3;
			
			if (flavour == "GBA") {
				url += "&show_scripts=true";
				url += "&afn=" + afnemer;
				url += "&bn=" + berichttype;
			} else if (flavour == "BRP") {
				url += "&afn=" + afnemer;
				url += "&show_scripts=true";
				url += "&berichttype=" + encodeURIComponent(berichttype);
				url += "&volgnr=" + volgnr;
			} else if (flavour == "RESBEV" || flavour == "RESBIJ") {
				url += "&show_scripts=true";
				url += "&berichttype=" + encodeURIComponent(berichttype);
				url += "&volgnr=" + volgnr;
			}
			
			$http.get(url)
				.success(function(data) {
					$scope.content = data.data;
					$scope.excel = data.meta.excel;
					$scope.header = _.keys(data.data[0]);
					$scope.bsn = data.meta.bsn;
					$scope.excel = data.meta.excel;
					setBadges(data.data);
				})
				.error(function(data, status, headers, config) {
					// TODO
				});

		}

		function listLeveringen(stage, substage, flavour, argument2) {
			var url = URL_CONTEXT_PATH + "/stages_list?stage=" + stage + "&substage=" + substage + "&flavour=" + flavour;
			url += "&anr=" + argument2;	

			$http.get(url)
				.success(function(data) {
					$scope.content = data.data;
					$scope.anr = data.meta.anr;
					$scope.bsn = data.meta.bsn;
					$scope.excel = data.meta.excel;
					$scope.header = _.keys(data.data[0]);

					setBadges(data.data);
				})
				.error(function(data, status, headers, config) {
					// TODO
				});
		}
		
		$scope.showlog = function() {
			var arg = arguments[0];
			var modal = $('#loggingModal')
				.one('show.bs.modal', function(e) {
					if ($scope.logging[arg].log) {
						$(this).find(".modal-body #log").html($scope.logging[arg].log);
						$(this).find(".modal-body #query").html($scope.logging[arg].query);
					} else {
						var log = "";
						$(_.values($scope.logging[arg])).each(function() {
							log += this.log + "<br />"; 
						});
						
						$(this).find(".modal-body #log").html(log);
					}
				});
			$.when($scope.logVal)
				.done(function() {
					modal.modal('show');
			});
		}
		
		$scope.copyAll = function(stage, substage, flavour) {
			var valueArray = [];
			for (key in $scope.checkbox) {
				if ($scope.checkbox[key]) {
					valueArray.push(key);	
				}
			}
			var naam = valueArray.join();			
			if (flavour == 'GBA' && stage != 'TC') {
				$http.get(URL_CONTEXT_PATH + '/copy?anr=' + arguments[3] + '&afn=' + arguments[4] + '&bn=' + arguments[5] + "&stage=" + stage + "&flavour=" + flavour + "&script=" + naam)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			} else if (flavour == 'BRP') {
				$http.get(URL_CONTEXT_PATH + '/copy?anr=' + arguments[3] + '&afn=' + arguments[4] + '&berichttype=' + encodeURIComponent(arguments[5]) + "&stage=" + stage + "&flavour=" + flavour + "&volgnr=" + $scope.volgnr + "&script=" + naam)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			} else {
				$http.get(URL_CONTEXT_PATH + '/copy?anr=' + arguments[3] + "&stage=" + stage + "&flavour=" + flavour + "&script=" + naam)
					.success(function(data) {
						$scope.status = data.status;
						$route.reload();
					});
			}
		}

		$scope.showPL = function() {
			var url="#Show/" + $scope.stage + "/" + $scope.anr;
			$window.open($location.absUrl().split('#')[0] + url);
		}

		$scope.checkAll = function(selectAll) {
			angular.forEach($scope.checkbox, function(value, key) {
				$scope.checkbox[key]=selectAll;
			});
		}
		
		$scope.showRaw = function() {
			var url = URL_CONTEXT_PATH + '/show_raw?anr=' + $scope.anr;
			if ($scope.flavour != "RESBIJ" && $scope.flavour != "RESBEV") {
				url += '&afn=' + $scope.afnemer;
			}
			url += '&volgnr=' + $scope.volgnr + '&berichttype=' + encodeURIComponent($scope.flavour=="GBA"?$scope.berichtnummer:$scope.berichttype) + "&stage=" + $scope.stage + "&substage=" + $scope.substage + "&flavour=" + $scope.flavour;

			$http.get(url)
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
					if ($scope.flavour == 'RESBIJ' || $scope.flavour == 'RESBEV') {
						$scope.rawData=$filter('prettyXml')(data);
					} else {
						$scope.rawData=data;
					}
				});
		}		
	}
])
.filter('custom', ['_', function(_) {
	return function (input, btn) {
		return _.where(input, btn);
	}
}])
.filter('equals', ['_', function(_) {
	return function (input, other) {
		return _.isEqual(input, other);
	}
}])
.filter('stripPercent', function() {
	return function (input) {
		return input.replace(/%/g, ' ');	
	}
});
