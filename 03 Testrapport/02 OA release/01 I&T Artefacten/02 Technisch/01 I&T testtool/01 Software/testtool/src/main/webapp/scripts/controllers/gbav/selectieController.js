myApp.controller('selectieController', [
	'$scope', 
	'$routeParams',
	'$http',
	'$route',
	function($scope, $routeParams, $http, $route) {

		$scope.log = {};
		
		$scope.selectie = function() {
			$("#go_selectie").addClass('disabled');
			$http.get(URL_CONTEXT_PATH + '/gbav/selectie')
				.success(function(data) {
					$scope.log['selectie']=data;
					$("#go_selectie").removeClass('disabled');
				});
		}

		$scope.verzend = function() {
			$("#go_verzend").addClass('disabled');
			$http.get(URL_CONTEXT_PATH + '/gbav/verzend')
				.success(function(data) {
					$scope.log['verzend']=data;
					$("#go_verzend").removeClass('disabled');
				});
		}

		$scope.protocolleer = function() {
			$("#go_protocolleer").addClass('disabled');
			$http.get(URL_CONTEXT_PATH + '/gbav/protocolleer')
				.success(function(data) {
					$scope.log['protocolleer']=data;
					$("#go_protocolleer").removeClass('disabled');
				});
		}

		$scope.verwijderen = function() {
			$("#go_verwijderen").addClass('disabled');
			$http.get(URL_CONTEXT_PATH + '/gbav/verwijderen')
				.success(function(data) {
					$scope.log['verwijderen']=data;
					$("#go_verwijderen").removeClass('disabled');
				});
		}
		
		$scope.toonlog = function() {
			$http.get(URL_CONTEXT_PATH + '/gbav/toonlog')
				.success(function(data) {
					$scope.log['gbavlog']=data;
					$scope.labelClick('gbavlog');
				});
		}
		
		$scope.labelClick = function(what) {
			var modal = $('#loggingModal')
				.one(
					'show.bs.modal',
					function() {
						$(this).find(".modal-body")
							.html("<pre>" + $scope.log[what] + "</pre>");
					});
	
			modal.modal('show');
		}	
	}
]);
