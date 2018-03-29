myApp.controller(
	'dashboardController', [
		'$scope', '$http', '_', '$interval', '$q', function($scope, $http, _, $interval, $q) {

			$scope.data = [];

			Chart.defaults.global.colours = ["#5cb85c", "#f0ad4e", "#d9534f"];
			$scope.labels = ["ok bevonden", "niet volledig gecontroleerd", "niet gecontroleerd/ok"];
			$scope.options = {
				animationEasing: "easeOutQuart",
				animationSteps: 50,
				//tooltipEvents:["touchstart", "touchmove"],
				tooltipTemplate: "<%=label%>: <%= value %> (<%= (circumference / 6.283 * 100).toFixed(2) %>%)",
				tooltipCaretSize: 0
    		};

			function setTotal() {
				var totalOk = 0, totalError = 0, totalnotchecked = 0;
				for (var key in $scope.data) {
					totalOk += $scope.data[key][0];
					totalnotchecked +=  $scope.data[key][1];
					totalError +=  $scope.data[key][2];
				}
				$scope.dataTotal = [totalOk, totalnotchecked, totalError];
			}

			function perform(stage, substage, flavour) {
				var d = $q.defer();
				$http.get(URL_CONTEXT_PATH + "/anr_compare?stage=" + stage + "&flavour="+ flavour)
					.success(function(data) {
						var ok = 0, notchecked = 0, error = 0;
						$(data).each(function(index, element) {
								if (!element["niet gecontroleerd"] || element["niet gecontroleerd"] == "~") {
									element["niet gecontroleerd"]=0;
								}
							
								if (!element.fouten || element.fouten == "~") {
									element.fouten=0;
								}

								if (!element.totaal || element.totaal == "~") {
									element.totaal=0;
								}

								notchecked += (element["niet gecontroleerd"] - 0);
								error += (element.fouten - 0);
								ok += ((element.totaal - 0) - (element.fouten - 0) - (element["niet gecontroleerd"] - 0));
							});;

						if (stage == 'IV' || stage == 'TC') {
							$scope.data[stage] = [ok, notchecked, error];
							setTotal();
							d.resolve($scope.data[stage]);
						} else {
							$scope.data[stage + "-" + flavour] = [ok, notchecked, error];
							setTotal();
							d.resolve($scope.data[stage + "-" + flavour]);
						}
					})
					.error(function(data, status, headers, config) {
						// TODO
					});
				return d.promise;
			}

			function go() {
				setTotal();			
				var iv = perform('IV', '', 'SQL');
				var tc = perform('TC', '', 'GBA');
				var m01_sql = perform('M01', '', 'SQL');
				var m01_lo3 = perform('M01', '', 'GBA');
				var m01_brp = perform('M01', '', 'BRP');
				var m02_sql = perform('M02', '', 'SQL');
				var m02_lo3 = perform('M02', '', 'GBA');
				var m02_brp = perform('M02', '', 'BRP');
				
				$q.all(
					[iv, tc, 
					 	m01_sql, m01_lo3, 
					 	m01_brp, m02_sql, 
					 	m02_lo3, m02_brp]
				)
				.then(function () {
					setTotal();
				});
			}

			go();
			var promise = $interval(go, 300000);			
			$scope.$on('$destroy', function(){
				$interval.cancel(promise);
			});
	}]);