myApp.controller(
	'afnemerindicatiesController', ['$scope', '$filter', '$http', '$location', function($scope, $filter, $http, $location) {
	
		$scope.type=['GBA', 'BRP'];
		$scope.anummers=[];
		$scope.afnemers=[];
		$scope.autorisaties=[];

		$scope.afnemerIsDisabled=false;	
		$scope.typeSelectedItem="";

		$scope.sendItems=[];
		$scope.running=false;

		$scope.init = function() {
			$http.get(URL_CONTEXT_PATH + "/get_all_anrs")
				.success(function(data) {
					$scope.anummers=data;
				})
				.error(function(data, status, headers, config) {
					// TODO
				});
			
			checkAvailable();
		}

		function checkAvailable() {
			$http.get(URL_CONTEXT_PATH + "/upload_available")
				.success (function (data) {
					if (data.length > 0) {
						$scope.showResults=true;
					} else {
						$scope.showResults=false;
					}
				}
			);
		}
		
		function getAfnemers() {
			$http.get(URL_CONTEXT_PATH + "/get_all_afnemers?type=" + $scope.typeSelectedItem)
				.success(function(data) {
					$scope.afnemers=data;
				})
				.error(function(data, status, headers, config) {
					// TODO
				});
		}
		
		function getAutorisaties() {
			$http.get(URL_CONTEXT_PATH + "/get_all_autorisaties")
				.success(function(data) {
					$scope.autorisaties=data;
				})
				.error(function(data, status, headers, config) {
					// TODO
				});
		}
		
		function reset() {
			$scope.typeSelectedItem='';
			$scope.anummerSelectedItem='';
			$scope.afnemerSelectedItem='';
			$scope.autorisatieSelectedItem='';
			$scope.afnemers=[];
			$scope.autorisaties=[];
			$scope.autorisatieIsDisabled=false;
		}
		
		$scope.$watch('typeSelectedItem', function(newValue, oldValue) {
			if (newValue != '' && newValue != oldValue) {
				getAfnemers();
				
				if (newValue == "GBA") {
					$scope.autorisatieIsDisabled=true;
					$scope.autorisatieSelectedItem='';
				} else {
					$scope.autorisatieIsDisabled=false;
					getAutorisaties();
				}
				
			}
		});
					
		$scope.querySearch = function (searchText, collection) {
			return $filter('filter') (collection, searchText);
		}
		
		$scope.add = function (type, anummer, afnemer, autorisatie) {
			$scope.sendItems.push({
				'type' : type,
				'anummer' : anummer[0],
				'afnemer' : afnemer[0],
				'autorisatie' : autorisatie
			});
			
			reset();
		};

		$scope.delete = function (index) {
			$scope.sendItems.splice(index, 1);
		};
		
		$scope.execute = function() {
			$scope.running=true;
			$("#execute").addClass('disabled');

			var parameters="";
			for (var i = 0; i < $scope.sendItems.length; i ++) {
				if (i != 0) {
					parameters += ",";
				}
				
				var j=0;
				for (key in $scope.sendItems[i]) {
					if (j != 0) {
						parameters += ";";
					}
					if (key == "anummer" && $scope.sendItems[i]["type"] == "BRP") {
						parameters += _.find($scope.anummers, function(item) {
							return item[0] == $scope.sendItems[i][key];
						})[2];
						
					} else {
						parameters += $scope.sendItems[i][key];
					}
					j ++;
				}
			}
			
			$http.get(URL_CONTEXT_PATH + "/execute_ai?ais=" + parameters)
				.success(function(data) {
					$scope.running=false;
					$("#execute").removeClass('disabled');
					$scope.sendItems=[];
					reset();
					checkAvailable();
				})
		}
		
		$scope.openResults = function() {
			$location.path("/Testtool/Tools/Upload/Stages");
		}
		
		$scope.resetAll = function() {
			$http.get(URL_CONTEXT_PATH + '/delete_pls?all=true')
				.success(function(data) {
					checkAvailable();
				});
		}
	}
]);
