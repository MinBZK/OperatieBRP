var myApp = angular.module('testtoolApp', ['ngRoute', 
										  'ui.bootstrap', 'angular-loading-bar', 
										  'ngAnimate', 'chart.js', 'angular-md5', 'timer', 'ngXslt', 'prettyXml',
										  'angular-inview', 'hljs', 'frapontillo.bootstrap-switch', 'ngMaterial',
										  'md.data.table', 'ngCookies', 'angularFileUpload', 'cl.paging',
										  'ngSanitize', 'xeditable', 'ngFileUpload'])
	.config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
		cfpLoadingBarProvider.includeSpinner = false;
	}])
	.config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
		cfpLoadingBarProvider.includeBar = true;
	}])
	.config(['hljsServiceProvider', function (hljsServiceProvider) {
		hljsServiceProvider.setOptions({
			// replace tab with 4 spaces
			tabReplace: '    '
		})
	}])
  
