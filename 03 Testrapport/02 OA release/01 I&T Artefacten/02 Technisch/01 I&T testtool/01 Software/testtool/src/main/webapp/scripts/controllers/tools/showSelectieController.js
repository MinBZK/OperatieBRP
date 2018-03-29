myApp.controller(
	'showSelectieController', [
		'$scope', '$filter', '$http', '$mdToast', function($scope, $filter, $http, $mdToast) {			

		$scope.type=['SQL', 'GBA', 'BRP'];
		$scope.controles=[];
		$scope.momenten=['IV', 'TC'];
		$scope.controleIsDisabled=false;
		
		$scope.init = function() {
			getMomenten();
		}
		
		function reset() {
			$scope.controleSearchText="";
			$scope.controleSelectedItem="";
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
				
		$scope.querySearch = function (searchText, collection) {
			return $filter('filter') (collection, searchText);
		}			
		
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
		
		$scope.selecteer = function() {
			
			
			
			
		}
	}
]);
