myApp
	.directive('runElement', function() {
		return {
			restrict: 'E',	
			scope: {
				status: '=',
				log: '=',
				type: '=',
				running: '='
			},
			controller: ['$scope', function ($scope) {				
				$scope.spanClick = function() {
					if (!$scope.running) {			
						$scope.status = $scope.status == LABEL_ENABLED ? LABEL_DISABLED : LABEL_ENABLED;				
						$scope.log = "";
					}
				}
				
				$scope.labelClick = function() {
					var modal = $('#outputModal')
						.one(
							'show.bs.modal',
							function() {
								$(this).find(".modal-title")
									.html("Output " + $scope.type);
								$(this).find(".modal-body")
									.html($scope.log);
							});
	
					modal.modal('show');
				}
			}],
			transclude: true,
			templateUrl: 'views/fragments/runElement.html'
		}
	});
