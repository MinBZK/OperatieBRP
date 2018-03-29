myApp
	.controller('navController', ['$scope', '$location', '$rootScope', function($scope, $location, $rootScope) {
		
		$scope.getClass = function(path) {
			if ($location.path().substr(0, path.length) === path) {
				return 'active';
			} else {
				return '';
			}
  		}
		
		$scope.open = function(what) {
			switch (what) {
				case 'dashboard':
					$location.path("/Testtool/Dashboard");
					break;
				case 'testgevallen':
					$location.path("/Testtool/Stages");
					break;
				case 'matrix':
					$location.path("/Testtool/Stages/Matrix");
					break;
				case 'afterburner':
					$location.path("/Testtool/Tools/Afterburner");
					break;
				case 'tidy':
					$location.path("/Testtool/Tools/Tidy");
					break;
				case 'stopstart':
					$location.path("/Testtool/Tools/RunISC");
					break;
				case 'transform':
					$location.path("/Testtool/Tools/Transform");
					break;
				case 'update':
					$location.path("/Testtool/Tools/Update");
					break;
				case 'upload':
					$location.path("/Testtool/Tools/Upload");
					break;
				case 'consolideren':
					$location.path("/Testtool/Tools/Consolideer");
					break;
				case 'logs':
					$location.path("/Testtool/Tools/Logs");
					break;
				case 'afnemerindicaties':
					$location.path("/Testtool/Tools/Afnemerindicaties");
					break;
				case 'multiply':
					$location.path("/Testtool/Proeftuin/Multiply");
					break;
				case 'stappenplan':
					$location.path("/Testtool/Proeftuin/Stappenplan");
					break;
				case 'selectie':
					$location.path("/Testtool/GBA-V/Selectie");
					break;
			}
		}
	}])
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/Testtool', {
			controller : ''
		}).when('/Testtool/Dashboard', {
			templateUrl : 'views/dashboard.html',
			controller : 'dashboardController'
		}).when('/Testtool/Stages', {
			templateUrl : 'views/stages/stageOverview.html',
			controller : 'stageOverviewController'
		}).when('/Testtool/Stages/:stage/:nr', {
			templateUrl : 'views/stages/stage.html',
			controller : 'stageController'
		}).when('/Testtool/Stages/Enkel/:stage/:flavour/:nr', {
			templateUrl : 'views/stages/stageList.html',
			controller : 'stageListController'
		}).when('/Testtool/Stages/Enkel/:stage/:flavour/:nr/:script', {
			templateUrl : 'views/stages/stageCompare.html',
			controller : 'stageCompareController'
		}).when('/Testtool/Stages/Mutaties/:stage/:nr', {
			templateUrl : 'views/stages/stage.html',
			controller : 'stageController'
		}).when('/Testtool/Stages/Mutaties/:stage/:flavour/:nr', {
			templateUrl : 'views/stages/stageList.html',
			controller : 'stageListController'
		}).when('/Testtool/Stages/Mutaties/:stage/:flavour/:nr/:script', {
			templateUrl : 'views/stages/stageCompare.html',
			controller : 'stageCompareController'
		}).when('/Testtool/Stages/Responses/:stage/:flavour/:nr', {
			templateUrl : 'views/stages/stageList.html',
			controller : 'stageListController'
		}).when('/Testtool/Stages/Responses/:stage/:flavour/:nr/:berichttype/:volgnr', {
			templateUrl : 'views/stages/stageList.html',
			controller : 'stageListController'
		}).when('/Testtool/Stages/Responses/:stage/:flavour/:nr/:berichttype/:volgnr/:script', {
			templateUrl : 'views/stages/stageCompare.html',
			controller : 'stageCompareController'
		}).when('/Testtool/Stages/Mutaties/:stage/:flavour/:nr/:afnemer/:berichttype', {
			templateUrl : 'views/stages/stageList.html',
			controller : 'stageListController'
		}).when('/Testtool/Stages/Mutaties/:stage/BRP/:nr/:afnemer/:berichttype/:volgnr', {
			templateUrl : 'views/stages/stageList.html',
			controller : 'stageListController'
		}).when('/Testtool/Stages/Mutaties/:stage/BRP/:nr/:afnemer/:berichttype/:volgnr/:script', {
			templateUrl : 'views/stages/stageCompare.html',
			controller : 'stageCompareController'
		}).when('/Testtool/Stages/Mutaties/:stage/:flavour/:nr/:afnemer/:berichttype/:script', {
			templateUrl : 'views/stages/stageCompare.html',
			controller : 'stageCompareController'
		}).when('/Testtool/Stages/Matrix', {
			templateUrl : 'views/stages/stageMatrix.html',
			controller : 'stageOverviewController'
		}).when('/Testtool/Tools/Tidy', {
			templateUrl : 'views/tools/tidy.html',
			controller : 'tidyController'
		}).when('/Testtool/Tools/RunISC', {
			templateUrl : 'views/tools/runISC.html',
			controller : 'runISCController'			
		}).when('/Testtool/Tools/Update', {
			templateUrl : 'views/tools/update.html',
			controller : 'updateController'
		}).when('/Testtool/Tools/Transform', {
			templateUrl : 'views/tools/transform.html',
			controller : 'transformController'
		}).when('/Testtool/Tools/Afterburner', {
			templateUrl : 'views/tools/afterburner.html',
			controller : 'afterburnerController'
		}).when('/Testtool/Tools/Levsaut', {
			templateUrl : 'views/tools/levsaut.html',
			controller : 'levsautController'
		}).when('/Testtool/Tools/Upload', {
			templateUrl : 'views/tools/upload.html',
			controller : 'uploadController'
		}).when('/Testtool/Tools/Afnemerindicaties', {
			templateUrl : 'views/tools/afnemerindicaties.html',
			controller : 'afnemerindicatiesController'
		}).when('/Testtool/Tools/Consolideer', {
			templateUrl : 'views/tools/consolideer.html',
			controller : 'consolideerController'
		}).when('/Testtool/Tools/Upload/Stages', {
			templateUrl : 'views/stages/stageOverview.html',
			controller : 'stageOverviewController'
		}).when('/Testtool/Tools/Logs', {
			templateUrl : 'views/tools/showLogs.html',
			controller : 'showLogsController'
		}).when('/Testtool/Proeftuin/Multiply', {
			templateUrl : 'views/proeftuin/multiply.html',
			controller : 'multiplyController'
		}).when('/Testtool/Proeftuin/Stappenplan', {
			templateUrl : 'views/proeftuin/run.html',
			controller : 'runController'
		}).when('/Testtool/GBA-V/Selectie', {
			templateUrl : 'views/gbav/selectie.html',
			controller : 'selectieController'
		}).when('/Show/:stage/:anr', {
			templateUrl : 'views/tools/showPL.html',
			controller : 'showPLController'
		}).otherwise({
			redirectTo : '/Testtool'
		})
	}]);
