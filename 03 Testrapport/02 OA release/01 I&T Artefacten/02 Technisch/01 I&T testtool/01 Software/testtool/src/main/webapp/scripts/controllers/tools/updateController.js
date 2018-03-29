myApp.controller('updateController', [
	'$scope', 
	'$routeParams',
	'$http',
	'$route',
	function($scope, $routeParams, $http, $route) {

		$scope.log = {};
		
		$scope.update = function() {
			$("#go").addClass('disabled');
			$http.get(URL_CONTEXT_PATH + '/update')
				.success(function(data) {
					$scope.log['SQL']=data.log;
					$("#go").removeClass('disabled');
				});
		}

		$scope.labelClick = function(what) {
			var modal = $('#loggingModal')
				.one(
					'show.bs.modal',
					function() {
						$(this).find(".modal-body")
							.html($scope.log[what]);
					});
	
			modal.modal('show');
		}	
	}
]);
