myApp.controller(
	'runISCController', [
		'$scope',
		'runService',
		'$interval',
		'$rootScope',
		function($scope, runService, $interval, $rootScope) {

			$scope.safeApply = function(fn) {				
				var phase = this.$root.$$phase;
				if(phase == '$apply' || phase == '$digest') {
					if(fn && (typeof(fn) === 'function')) {
						fn();
					}
				} else {
					this.$apply(fn);
					this.$root.$apply();
				}
			};

			$scope.log = [];
			$scope.timeout = [];
			
			$scope.password = DEFAULT_PASSWORD;
			
			$scope.running = false;
			var headers = {};
			
			$scope.status = [];
			$scope.status['stop_isc'] = LABEL_ENABLED;
			$scope.status['clean_actuals_M01'] = LABEL_ENABLED;
			$scope.status['clean_actuals_M02'] = LABEL_ENABLED;
			$scope.status['start_isc'] = LABEL_ENABLED;
			$scope.status['copy_db'] = LABEL_ENABLED;
			$scope.status['run_afterburner'] = LABEL_ENABLED;

			function isTaskEnabled(what) {
				return $scope.status[what] && $scope.status[what] != LABEL_DISABLED;
			}

			function countEnabled() {
				var count = 0;
				
				for (var key in $scope.status) {
					if ($scope.status[key] == LABEL_ENABLED) {
						count ++;
					}
				}
				
				return count;
			}

			function setStatus(id, status) {
				$scope.safeApply(function() {
					$scope.status[id] = status;
				});
			}

			function getStatus(id) {
				return $scope.status[id];
			}

			function setLog(what, data) {
				if (isTaskEnabled(what)) {
					$scope.safeApply(function() {
						$scope.log[what] = data;
					});
				}
			}

			function toggleLabels(what) {
				var status = getStatus(what);
				switch (status) {
					case LABEL_ENABLED:
						setStatus(what, LABEL_IN_PROGRESS);
						break;
					case LABEL_IN_PROGRESS:
						setStatus(what, LABEL_SUCCESS);
						break;
					case LABEL_SUCCESS:
						setStatus(what, LABEL_ENABLED);
						break;
					default:
						break;
				}
			}

			var tasks = 3;
			var task_counter = 0;
			
			function handleError(what, xhr) {
				$scope.safeApply(function() {
					$scope.status[what] = LABEL_ERROR;
					$scope.log[what] = xhr.responseText;
					$("#go").removeClass('disabled');
					$scope.running = false;
				});
			}

			function geturl(what) {
				if (isTaskEnabled(what)) {
					return runService.geturl(what, '',  $scope.password);
				} else {
					return runService.getdummyurl(what, '', $scope.password);
				}
			}

			function handleStep(status, step, handleError) {
				toggleLabels(step);
				var promise = $.ajax({
					url: geturl(step),
					error: function(xhr) {
						handleError(step, xhr);
					},
					success: function(data) {
						setLog(step, data);
						toggleLabels(step);
					}
				});
				
				return promise;
			}
			
			$scope.start = function() {
				$scope.running = true;

				// reset all success
				for (var key in $scope.status) {
					console.log($scope.status[key]);
					if ($scope.status[key] != LABEL_DISABLED) {
						$scope.safeApply(function () {
							$scope.status[key] = LABEL_ENABLED;
						});
					}
				}

				var promise = runService.cleanMutatieEnv(handleStep, toggleLabels, geturl, setLog, handleError);
				promise
					.then(function() {
						$("#go").removeClass('disabled');
						$scope.running = false;
					});
				
				$("#go").addClass('disabled');
			}

			$scope.spanClick = function() {
				var id = arguments[0];
				if (!$scope.running) {
					$scope.status[id] = $scope.status[id] == LABEL_ENABLED ? LABEL_DISABLED : LABEL_ENABLED;				
					$scope.log[id] = "";
				}
			}		
		}

	]);
