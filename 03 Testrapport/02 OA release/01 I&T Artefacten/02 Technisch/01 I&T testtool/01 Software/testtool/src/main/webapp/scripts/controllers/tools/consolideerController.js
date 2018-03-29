myApp.controller(
	'consolideerController', [
		'$scope', '$filter', '$http', '$mdToast', function($scope, $filter, $http, $mdToast) {			

		$scope.type=['SQL', 'GBA', 'BRP'];
		$scope.controles=[];
		$scope.momenten=['IV', 'TC'];
		$scope.anummers=[];
		$scope.negeerAnummers=[];
		$scope.controleIsDisabled=false;
		
		$scope.init = function() {
			getMomenten();
			getAnummers();
		}
		
		function reset() {
			$scope.controleSearchText="";
			$scope.controleSelectedItem="";
			$scope.anummerSelectedItem="";
			$scope.anummerSearchText="";
			$scope.typeSelectedItem="";
			$scope.momentSelectedItem="";
			$scope.momentSearchText="";
			$scope.negeerAnummers=[];
			$scope.controles=[];
		}
		
		$scope.$watch('typeSelectedItem', function(newValue, oldValue) {
			if (newValue != '' && newValue != oldValue) {
				getControles();				

				$scope.controleSearchText="";
				$scope.controleSelectedItem="";
			}
		});

		$scope.$watch('alleControles', function(newValue, oldValue) {
			if (newValue && newValue != oldValue) {
				$scope.controleSelectedItem='';
				$scope.controleIsDisabled = newValue;
			} else {
				$scope.controleIsDisabled = false;
			}
		});

		$scope.$watch('alleMomenten', function(newValue, oldValue) {
			if (newValue && newValue != oldValue) {
				$scope.momentSelectedItem='';
				$scope.momentIsDisabled = newValue;
			} else {
				$scope.momentIsDisabled = false;
			}
		});
		
		function getAnummers() {
			$http.get(URL_CONTEXT_PATH + "/get_all_anrs")
				.success(function(data) {
					$scope.anummers=data;
				})
				.error(function(data, status, headers, config) {
					// TODO
				});
		}
		
		$scope.querySearch = function (searchText, collection) {
			return $filter('filter') (collection, searchText);
		}			

		$scope.toevoegen = function () {
			if ($scope.anummerSelectedItem != null 
					&& $scope.anummerSelectedItem != "" 
					&& $scope.negeerAnummers.indexOf($scope.anummerSelectedItem) == -1) {
				$scope.negeerAnummers.push($scope.anummerSelectedItem);
				$scope.anummerSelectedItem='';
			}
		}

		$scope.delete = function (index) {
			$scope.negeerAnummers.splice(index, 1);
		};
		
		function getControles() {	
			$http.get(URL_CONTEXT_PATH + "/get_controles?type=" + $scope.typeSelectedItem)
				.success(function(data) {
					$scope.controles=data;
				})
				.error(function(data, status, headers, config) {
					console.error(data + " " + status);
				});
		}		
		
		function getMomenten() {	
			$http.get(URL_CONTEXT_PATH + "/get_momenten")
				.success(function(data) {
					for (var i = 0; i < data.available.length; i ++) {
						$scope.momenten.push(data.available[i]);
					}
				})
				.error(function(data, status, headers, config) {
					console.error(data + " " + status);
				});
		}
		
		$scope.consolideer = function() {
			$("#consolideer").addClass('disabled');

			console.log($scope.negeerAnummers);
			
			// creeer de paramater van negeer lijstje
			var parameters="";
			for (var i = 0; i < $scope.negeerAnummers.length; i ++) {
				if (i != 0) {
					parameters += ",";
				}
				parameters += $scope.negeerAnummers[i][0];
			}

			$http.get(URL_CONTEXT_PATH + "/consolideer_expecteds?ais=" + parameters + "&type=" 
					+ $scope.typeSelectedItem + "&controle=" + $scope.controleSelectedItem 
					+ "&moment=" + $scope.momentSelectedItem + "&heeftExpected=" + ($scope.heeftExpected || false)
					+ "&alleMomenten=" + ($scope.alleMomenten || false)  + "&alleControles=" + ($scope.alleControles || false))
				.success(function(data) {
					$("#consolideer").removeClass('disabled');
					
					$mdToast.show(
						$mdToast.simple()
				      	  .textContent(data.count + " actual(s) gekopiëerd")
				        	.position("top right")
				        	.highlightAction(true)
				        	.hideDelay(3000)
					);
					
				})			
		}
	}
]);
