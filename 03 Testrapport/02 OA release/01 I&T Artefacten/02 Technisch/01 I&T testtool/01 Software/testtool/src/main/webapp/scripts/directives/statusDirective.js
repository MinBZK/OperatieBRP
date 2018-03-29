myApp
	.directive('status', function() {
		return {
			restrict: 'E',	
			scope: {
				running: '='
			},
			controller: ['$scope', '$interval', '$http', function ($scope, $interval, $http) {
				function checkStatus() {
					if (!$scope.running) {
						$.ajax({
							url: URL_CONTEXT_PATH + "/status",
							success : function(data) {
								if (data.process.length > 0) {
									$scope.alertMessage = "LET OP: mogelijk nog draaiende processen op de server. De volgende processen zijn nu actief op de server: " + data.process;	
								} else {
									$scope.alertMessage = "";
								}
							}
						});
					}
				}

				$scope.$on('$destroy', function() {
					$interval.cancel(statusCheck);
			    });
				
				checkStatus();
				statusCheck = $interval(checkStatus, 5000);
			}],
			transclude: true,
			templateUrl: 'views/fragments/status.html'
		}
	});
