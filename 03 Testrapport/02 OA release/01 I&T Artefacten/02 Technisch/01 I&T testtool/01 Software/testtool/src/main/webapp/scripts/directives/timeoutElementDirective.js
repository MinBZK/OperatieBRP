myApp
	.directive('timeoutElement', function() {
		return {
			restrict: 'E',	
			scope: {
				status: '=',
				countdown: '=',
				callback: '=',
				running: '='
			},
			controller: ['$scope', function ($scope) {
				var ENABLED = "label label-info";
				var IN_PROGRESS = "label label-warning";
				var SUCCESS = "label label-success";
				
				$scope.spanClick = function() {
					if (!$scope.running) {			
						$scope.status = $scope.status == ENABLED ? DISABLED : ENABLED;				
						$scope.log = "";
					}
				}
			}],
			transclude: true,
			templateUrl: 'views/fragments/timeoutElement.html'
		}
	});
