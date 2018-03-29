myApp
	.controller('stageOverviewController', 
			['$scope', '$http', '_', '$location', '$timeout', 'modalService', 'runService', '$cookies', '$route',
			 function($scope, $http, _, $location, $timeout, modalService, runService, $cookies, $route) {
	
	if ($route.current.$$route.originalPath == "/Testtool/Tools/Upload/Stages") {
		$scope.upload=true;
	} else {
		$scope.upload=false;
	}

	$scope.checkbox={};
	$scope.running = false;
	
	var anrArray = [];
	var inviewArray = {};
	var statusCheck;
	
	var MUTATIE_STAGES = ['M01', 'M02', 'M03', 'M04', 'M05', 'M06', 'M07', 'M08', 'M09', 'M10'];
	var MUTATIE_TYPES = ['GBA', 'BRP'];
	
	var BUTTON_CLASS_SUCCESS="btn-success";
	var BUTTON_CLASS_WARNING="btn-warning";
	var BUTTON_CLASS_ERROR="btn-danger";
	
	var COOKIE_ORDER="order";
	var COOKIE_LIMIT="limit";
	var COOKIE_PAGE="page";
	var COOKIE_ANR="anr";
	var COOKIE_FILTER="filter";
	var COOKIE_STADIUMACTIVE="stadiumActive";
	var COOKIE_STATUSACTIVE="statusActive";

	$scope.available={};
	$scope.stagesAvailable={};
	$scope.canRun=false;
	$scope.runStatus=[];

	$scope.count = {
		"ok": 0,
		"notchecked": 0,
		"nok": 0
	};
	
	$(document).ready(function(){
    	$('[data-toggle="tooltip"]').tooltip(); 
	});
	
	$scope.queryConfig = {
		order: $cookies.get(COOKIE_ORDER) || 'test',
		limit: $cookies.get(COOKIE_LIMIT) || 20,
		page: $cookies.get(COOKIE_PAGE) || 1,
		filter: $cookies.get(COOKIE_FILTER) || '',
		stadiumActive: $cookies.get(COOKIE_STADIUMACTIVE) ? JSON.parse($cookies.get(COOKIE_STADIUMACTIVE)) : {},
		statusActive: $cookies.get(COOKIE_STATUSACTIVE) ? JSON.parse($cookies.get(COOKIE_STATUSACTIVE)) : {}
	};

	$scope.limitOptions = [5, 10, 20, 40, 60, {
		label: 'Alles',
		value: function () {
			return $scope.content.length;
		}
	}];
		
	$scope.$watch('queryConfig.order', function() {
		$cookies.put(COOKIE_ORDER, $scope.queryConfig.order);
	});

	$scope.$watch('queryConfig.limit', function() {
		$cookies.put(COOKIE_LIMIT, $scope.queryConfig.limit);
	});

	$scope.$watch('queryConfig.page', function() {
		$cookies.put(COOKIE_PAGE, $scope.queryConfig.page);
	});
	
	$scope.$watch('queryConfig.filter', function() {
		$cookies.put(COOKIE_FILTER, $scope.queryConfig.filter);
	});
	
	function setResults(data) {
		// tel aantal keer testgeval voorkomt (voor rowspan)
		$scope.occurences=_.chain(data)
			.pluck('test')
			.countBy(function(element) { return element; })
			.value();

		for (var i = 0; i < data.length; i ++) {
			for (key in data[i].results) {
				if (data[i].results[key].status == "ok") {
					data[i].results[key]['class']=BUTTON_CLASS_SUCCESS;
				} else if (data[i].results[key].status == "nok") {
					data[i].results[key]['class']=BUTTON_CLASS_ERROR;
				} else {
					data[i].results[key]['class']=BUTTON_CLASS_WARNING;						
				}

				// voor de matrix
				for (key2 in data[i].results[key]) {
					if (data[i].results[key][key2].status == "ok") {
						data[i].results[key][key2]['class']=BUTTON_CLASS_SUCCESS;
					} else if (data[i].results[key][key2].status == "nok") {
						data[i].results[key][key2]['class']=BUTTON_CLASS_ERROR;
					} else {
						data[i].results[key][key2]['class']=BUTTON_CLASS_WARNING;						
					}
				}
			}
		}

		for (var i = 0; i < data.length; i ++) {
			$scope.count['ok'] += _.where(data[i].results, { "status" : "ok" }).length;
			$scope.count['notchecked'] += _.where(data[i].results, { "status" : "notchecked" }).length;
			$scope.count['nok'] += _.where(data[i].results, { "status" : "nok" }).length;
		}
		
		_.each(data, function (element, index) {
			if (element.commentArray.length > 0) {
				element.comment=element.commentArray.join('\n');						
			}
			if (element.omschrijvingArray.length > 0) {
				element.omschrijving=element.omschrijvingArray.join('\n');						
			}
		});	
	}
	
	$scope.list = function() {
		var url = URL_CONTEXT_PATH + "/stages_overview";
		if ($scope.upload) {
			url += "?upload=true";
		}

		$scope.promise = $http.get(url)
			.success(function(data) {
				setResults(data.data);				
				$scope.content=data.data;
				hideStages(data.available);
				$scope.stagesAvailable=data.stages_available;
			})
			.error(function(data, status, headers, config) {
				// TODO
			});
	};
	
	$scope.openType = function(stage, substage, anr, flavour) {
		if (stage == 'IV') {
			$location.path("/Testtool/Stages/Enkel/" + stage + "/SQL/" + anr);		
		} else if (stage == 'TC') {
			$location.path("/Testtool/Stages/Enkel/" + stage + "/GBA/" + anr);
		} else {
			if (flavour) {
				$location.path("/Testtool/Stages/Mutaties/" + stage + "/" + flavour + "/" + anr);
			} else {
				$location.path("/Testtool/Stages/Mutaties/" + stage + "/" + anr);
			}
		}
	}
	
	function hideStages(available) {
		for (var i=0; i < MUTATIE_STAGES.length; i ++) {
			for (var j=0; j < MUTATIE_TYPES.length; j ++) {
				var name = MUTATIE_STAGES[i];
				if (available.indexOf (name) == -1) {
					$scope.available[name]='hide';
				} else {
					$scope.available[name]='';
				}
			}
		}
	}
	
	function correctStatus(available) {
		for (key in inviewArray) {
			setStatus(key);	
		}
		
		hideStages(available);
	}
	
	function toggle(stage, type) {
		if ($("#" + stage + "_" + type).hasClass('disabled')) {
			$("#" + stage + "_" + type).removeClass('disabled');
		} else {
			$("#" + stage + "_" + type).addClass('disabled');
		}
	}
	
	function stop(what, type) {
		if (type == STATUS_RUN) {
			$scope.running = false;
			$scope.safeApply(function() {
				$scope.canRun=false;
			});
		}
		
		toggle(what, type);
		$scope.safeApply(function() {
			document.getElementById("timer_" + what).getElementsByTagName('timer')[0].stop()
		});
	}
	
	function start(what, type) {
		toggle(what, type);
		if (type == STATUS_RUN) {
			$scope.running = true;
		}
		
		$scope.safeApply(function() {
			document.getElementById("timer_" + what).getElementsByTagName('timer')[0].start()
		});
	}
	
	$scope.timeout=2;
	function checkFinishedAndContinue(stage, substage, count) {
		status("wachten", STATUS_RUN);
		var promise = $http.get(URL_CONTEXT_PATH + "/check_stage_finished?stage=" + stage + "&substage=" + substage + "&count=" + count)
			.success(function(data) {
				status("wachten", STATUS_RUN);
				if ($scope.timeout != 0) {
					checkFinishedAndContinue(stage, substage, ++count);
					var timeout=0;
					for (key in data) {
						if (data[key].timeout && data[key].timeout > timeout) {
							timeout=data[key].timeout
						}
					}
					
					$scope.timeout=timeout;
				} else {
					$scope.query(stage, substage, STATUS_RUN, ++count);
					if (stage == 'IV') {
						$scope.timeout=10;
						runService.doTC(status, function() {
							checkFinishedAndContinue('TC', '', 0);
						});
					}
				}
			});
			
		return promise;
	}
	
	$scope.run = function(stage, substage) {
		if (!$scope.running && $scope.canRun) {
			$scope.timeout=2;
			count=0;

			var stepNaam = '';
			start(stage, STATUS_RUN);
			if (stage == "IV") {
				runService.doIV(status, function() {
					checkFinishedAndContinue(stage, substage, 0);
				});
			} else {
				runService.doMutaties(status, stage, substage, function() {
					checkFinishedAndContinue(stage, substage, 0);
				});
			}
		}
	};

	$scope.safeApply = function(fn) {
		if (this.$root) {
			var phase = this.$root.$$phase;
			if(phase == '$apply' || phase == '$digest') {
				if(fn && (typeof(fn) === 'function')) {
					fn();
				}
			} else {
				this.$apply(fn);
			}
		}
	};				
	
	function status(status, type) {
		$scope.safeApply(function() {
			if ((index = _.indexOf($scope.runStatus, status)) != -1) {
				$scope.runStatus.splice(index, 1);
			} else {
				$scope.runStatus.push(status);
			}
		});
	}

	$scope.query = function(stage, substage, statusType) {
		if (stage == "IV") {
			runService.getIVResults(status, function(data) {
				if (data.info && data.info.available) {
					correctStatus(data.info.available);
				} else if (data.data) {				
					setResults(data.data);
					$scope.content = data.data;
				}
			});
		} else if (stage == "TC") {
			runService.getTCResults(status, function(data) {
				if (data.data) {
					correctStatus(data.info.available);
					setResults(data.data);
					$scope.content = data.data;
				}
				
				// IV moet gestopt worden
				stop('IV', STATUS_RUN);
			});	
		} else {
			runService.getAllResults(status, stage, substage, function(data) {
				stop(stage, statusType);
				correctStatus(data.info.available);
				
				setResults(data.data);
				$scope.content = data.data;
			});	
		}
	}
	
	$scope.refresh = function() {
		$("#refresh").addClass('icon-spin');
		var url = URL_CONTEXT_PATH + "/refresh_all";
		if ($scope.upload) {
			url += "?upload=true";
		}
		
		var promise = $http.get(url)
			.success(function(data) {
				setResults(data.data);
				$scope.content = data.data;
				$("#refresh").removeClass('icon-spin');
			});
	}

	$scope.export = function(bestand) {
		var url = URL_CONTEXT_PATH + "/export";
		if ($scope.upload) {
			url += "?upload=true";
		}
		return url;
	}

	$scope.determine_occurences = function(key, filteredContent) {
		if (filteredContent[key]) {
			if (key != 0 && filteredContent[key-1] && filteredContent[key-1].test == filteredContent[key].test) {
				return 1;
			} else {
				var tst = filteredContent[key].test;
				var count = 0;
				for (; key < filteredContent.length; key ++) {
					if (tst == filteredContent[key].test) {
						count ++;
					} else {
						break;
					}
				}
	
				return count;
			}
		} else {
			return 1;
		}
	}
	
	$scope.saveComment = function(anr, comment) {
		$http.post(URL_CONTEXT_PATH + "/save_comment?anr=" + anr, comment);
	}

	$scope.saveOmschrijving = function(anr, omschrijving) {
		$http.post(URL_CONTEXT_PATH + "/save_omschrijving?anr=" + anr, omschrijving);
	}

	$scope.toggleStadium = function(value) {
		if (value) {
			$scope.queryConfig.stadiumActive[value]?
					$scope.queryConfig.stadiumActive[value]=false:
					$scope.queryConfig.stadiumActive[value]=true;
			$cookies.put(COOKIE_STADIUMACTIVE, JSON.stringify($scope.queryConfig.stadiumActive));
		} else {
			$scope.queryConfig.stadiumActive={};
			$cookies.remove(COOKIE_STADIUMACTIVE);
		}
		$scope.queryConfig.page=1;
	}
	
	$scope.toggleStatus = function(value) {
		$scope.queryConfig.statusActive[value]?
				$scope.queryConfig.statusActive[value]=false:
				$scope.queryConfig.statusActive[value]=true;
		$cookies.put(COOKIE_STATUSACTIVE, JSON.stringify($scope.queryConfig.statusActive));
		$scope.queryConfig.page=1;
	}

	function status(status, type) {
		$scope.safeApply(function() {
			if ((index = _.indexOf($scope.runStatus, status)) != -1) {
				$scope.runStatus.splice(index, 1);
			} else {
				$scope.runStatus.push(status);
			}
		});
	}

	$scope.stripRun = function(value) {
		return value?value.replace(/_run/, ""):value;	
	}
	
	$scope.checkAll = function(selectAll) {
		angular.forEach($scope.checkbox, function(value, key) {
			$scope.checkbox[key]=selectAll;
		});
	}
	
	$scope.resetPLs = function() {
		var valueArray = [];
		for (key in $scope.checkbox) {
			if ($scope.checkbox[key]) {
				valueArray.push(key);	
			}
		}
		
		var anrs = valueArray.join();
		$http.get(URL_CONTEXT_PATH + '/delete_pls?anrs=' + anrs)
			.success(function(data) {
				$scope.status = data.status;
				$route.reload();
			});
	}
	
}])
.filter('stadiumFilter', function() {
	return function(input, active) {
		if (!input) return [];	

		var none_selected = _.every(active, function(val) {
			return val == false;
		});

		if (none_selected) return input;
		
		var list = [];
		for (var i = 0; i < input.length; i ++) {
			if (_.size(active) > 0) {
				for (key in active) {					
					if (active[key] && input[i].results[key] && !_.contains(list, input[i])) {
						list.push(input[i]);
					}
				}
			} else {
				list.push(input[i]);	
			}
		}

		return list;
	};
})
.filter('statusFilter', function() {
	
	return function(input, active) {
		if (!input) return [];	

		var none_selected = _.every(active, function(val) {
			return val == false;
		});

		if (none_selected) return input;
		
		var list = [];
		for (var i = 0; i < input.length; i ++) {
			if (_.size(active) > 0) {
				for (key in active) {
					var count = _.where(input[i].results, { "status" : key }).length;
					if (active[key] && count != 0 && !_.contains(list, input[i])) {
						list.push(input[i]);
					}
				}
			} else {
				list.push(input[i]);	
			}
		}

		return list;
	};
})
.filter('link', function() {
	return function (input, target) {
		return input.replace(/\[([^|]*)\|([^\]]*)\]/gi, '<a href="$2" target="_blank">$1</a>');
	};
})
.run (function(editableOptions, editableThemes) {
	editableThemes.bs3.buttonsClass='hide';
	editableOptions.theme='bs3';
	editableOptions.blurElem="submit";
});
