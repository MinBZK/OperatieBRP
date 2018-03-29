myApp
	.directive('compareElement', function() {
		return {
			restrict: 'E',	
			scope: {
				datakey: '=',
				data: '=',
				other: '=',
				status: '=',
				flavour: '=',
				headers: '='
			},
			transclude: false,
			controller: function ($scope) {
				$scope['ELEMENTS']=LO3_ELEMENTS;
				$scope['GROUPS']=LO3_GROUPS;			
			},
			templateUrl: 'views/fragments/compareElement.html'
		}
	});
