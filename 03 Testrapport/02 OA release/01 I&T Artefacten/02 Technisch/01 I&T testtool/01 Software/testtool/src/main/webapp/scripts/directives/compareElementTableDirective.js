myApp
	.directive('compareElementTable', function($templateRequest, $compile) {
		return {
			restrict: 'E',	
			scope: {
				datakey: '=',
				data: '=',
				other: '=',
				flavour: '=',
				style: '@',
				timestamp: '=',
				headers: '='
			},
			compile: function () {
				return function($scope) {
					$scope['ELEMENTS']=LO3_ELEMENTS;
					$scope['GROUPS']=LO3_GROUPS;
				}
			},
			templateUrl: 'views/fragments/compareElementTable.html'
		}
	});
