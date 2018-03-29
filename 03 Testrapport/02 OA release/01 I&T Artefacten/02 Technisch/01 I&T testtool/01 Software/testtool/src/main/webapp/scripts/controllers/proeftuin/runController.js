myApp.controller(
	'runController', [
		'$scope',
		'runService',
		function($scope, runService) {

			$scope.safeApply = function(fn) {
				var phase = this.$root.$$phase;
				if(phase == '$apply' || phase == '$digest') {
					if(fn && (typeof(fn) === 'function')) {
						fn();
					}
				} else {
					this.$apply(fn);
				}
			};				

			$scope.log = [];
			$scope.running = false;

			var headers = {};

			$scope.status = [];

			$scope.status['copy_data_set'] = LABEL_DISABLED;
			$scope.status['clean_actuals_iv'] = LABEL_DISABLED;	
			$scope.status['read_into_gbav'] = LABEL_DISABLED;

			$scope.status['clean_gbav_db'] = LABEL_DISABLED;
			$scope.status['stop_iv'] = LABEL_ENABLED;
			$scope.status['start_iv'] = LABEL_ENABLED;

			$scope.status['truncate_pl'] = LABEL_ENABLED;
			$scope.status['create_pl'] = LABEL_ENABLED;
			$scope.status['run_pl'] = LABEL_ENABLED;

			$scope.status['stop_isc'] = LABEL_DISABLED;
			$scope.status['copy_db'] = LABEL_DISABLED;
			$scope.status['run_afterburner'] = LABEL_DISABLED;
			$scope.status['start_isc'] = LABEL_DISABLED;

			$scope.password=DEFAULT_PASSWORD;

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

			function handleError(what, xhr) {	
				$scope.status[what] = LABEL_ERROR;
				$scope.log[what] = xhr.responseText;
				$scope.safeApply();
				$("#go").removeClass('disabled');
				$scope.running = false;
			}
			
			function setLog(what, data) {
				if (isTaskEnabled(what)) {
					$scope.log[what] = data;
				}
			}
			
			function geturl(what) {
				if (isTaskEnabled(what)) {
					return runService.geturl(what, '', $scope.password);
				} else {
					return runService.getdummyurl(what, '', $scope.password);
				}
			}

			function handleStep(status, step, handleError) {
				status(step);
				var promise = $.ajax({
					url: geturl(step),
					error: function(xhr) {
						handleError(step, xhr);
					},
					success: function(data) {
						setLog(step, data);
						status(step);
					}
				});
				
				return promise;
			}

			function startTimer(what) {
				toggleLabels(what);
				if (isTaskEnabled(what)) {
					document.getElementById(what).getElementsByTagName('timer')[0].start();
				} else {
					switch(what) {
						case "timeout1":
							$scope.executeAfterBreak();
							break;
					}
				}
	        }

			function finish() {
				$("#go").removeClass('disabled');
				$scope.running = false;
			}

			$scope.start = function() {
				$scope.running = true;
				// reset all success
				for (var key in $scope.status) {
					if ($scope.status[key] == LABEL_SUCCESS || $scope.status[key] == LABEL_ERROR) {
						$scope.status[key] = LABEL_ENABLED;
					}
				}

				$("#go").addClass('disabled');
				
				var step1 = handleStep(toggleLabels, 'copy_data_set', handleError);
				$.when(step1)
					.done(function() {
						var step2 = handleStep(toggleLabels, 'clean_gbav_db', handleError);
						$.when(step2)
							.done(function() {
								var step3 = handleStep(toggleLabels, 'read_into_gbav', handleError);
								$.when(step3)
									.done(function() {
										runService.startIV(handleStep, toggleLabels, geturl, handleError, setLog, startNext);
									});
							});
					});
			}

			function startNext() {
				var promise = runService.cleanMutatieEnv (handleStep, toggleLabels, geturl, setLog, handleError);
				promise
					.then(function() {
						finish();
					});
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
