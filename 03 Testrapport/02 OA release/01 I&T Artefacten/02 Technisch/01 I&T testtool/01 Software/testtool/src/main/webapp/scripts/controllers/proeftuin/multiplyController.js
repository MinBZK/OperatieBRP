myApp.controller(
	'multiplyController', ['$scope', '$http',
		function($scope, $http) {
		
			var MAX_VALUE=999;
			$scope.contextPath = URL_CONTEXT_PATH;

			$scope.init = function() {
				$scope.errorMessages = [];
				$http.get(URL_CONTEXT_PATH + "/proeftuin/show_input")
					.success(function(data) {
						$scope.gemeentebestanden = data;
					})
					.error(function(data) {
						errorMessage.push("er is iets fout gegaan bij het ophalen van de inhoud van de gemeentebestand directory: " + response); 
					});
			}

			function checkParams() {
				if (!$scope.gemeentebestand || $scope.gemeentebestand == "") {
					$scope.errorMessages.push("kies een gemeente bestand");
					$("#go").removeClass('disabled');
				}
				
				if (!$scope.factor || !$.isNumeric($scope.factor)) {
					$scope.errorMessages.push("factor bevat geen valide input");
					$("#go").removeClass('disabled');
				}
				
				if ($scope.errorMessages.length == 0) {
					return true;
				}
				
				return false;
			}
			
			$scope.execute = function() {
				$("#go").addClass('disabled');
				$scope.errorMessages=[];
				
				if (checkParams()) {
					$http.get(URL_CONTEXT_PATH + "/proeftuin/run?gemeentebestand=" + $scope.gemeentebestand + "&factor=" + $scope.factor)
						.success(function(response) {
							$scope.refreshArtefacten();
							$("#go").removeClass('disabled');
						})
						.error(function(response) {
							errorMessage.push("er is iets fout gegaan bij het aansturen van de multiplier: " + response + ", argumenten: gemeentebestand=" + $scope.gemeentebestand + "&factor=" + $scope.factor); 
							$("#go").removeClass('disabled');
						});
				}
			}
			
			$scope.refreshArtefacten = function() {
				$http.get(URL_CONTEXT_PATH + "/proeftuin/show_artefacts")
					.success(function(response) {
						$scope.artefacten = response;
					})
					.error(function(response) {
						errorMessage.push("er is iets fout gegaan bij het ophalen van de inhoud van de artefacten directory: " + response); 
					});
			}
			
			$scope.checkValue = function() {
				if (!$scope.factor) {
					return false;
				}
				
				if (!$.isNumeric($scope.factor)) {
					return true;
				} else {
					if ($scope.factor > MAX_VALUE) {
						return true;	
					}
				}
				
				return false;
			}

			$scope.getDownload = function(bestand) {
				return (URL_CONTEXT_PATH + "/proeftuin/download?bestand=" + bestand);
			}
			
			$scope.load = function() {
				$http.get(URL_CONTEXT_PATH + "/proeftuin/load")
					.success(function(data) {
						// voer load uit
					})
					.error(function(data) {
						errorMessage.push("er is iets fout gegaan bij het uitvoeren van het laden: " + response); 
					});
			}
		}
	]
);
