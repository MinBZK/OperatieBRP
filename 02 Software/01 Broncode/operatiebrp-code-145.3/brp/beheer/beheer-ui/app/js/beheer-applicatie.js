angular.module('beheerApp.filters', []);
angular.module('beheerApp.controllers', []);
angular.module('beheerApp.services', []);
angular.module('beheerApp.directives', []);

var beheerApp = angular.module('beheerApp', [
    'ngRoute',
    'ngResource',
    'ui.bootstrap',
    'ui.bootstrap.datetimepicker',
    'angular-loading-bar',
    'ngSanitize',
    'ui.select',
    'beheerApp.filters',
    'beheerApp.services',
    'beheerApp.controllers',
    'beheerApp.directives',
    'json-toon',
    'Keybind',
    'BrpVeld',
    'BrpLijstveld',
    'BrpZoekveld',
    'DatumFormats',
    'SecurityController',
    'BeheerInstelling',
    'InfoController',
    'ItemController',
    'ItemJsonController',
    'ListController',
    'Lo3FilterRubriekInstanceController',
    'LocalStorageModule',
    'AutAutConfig',
    'BerConfig',
    'BrmConfig',
    'ConvConfig',
    'IstConfig',
    'KernConfig',
    'MigblokConfig',
    'VerconvConfig',
    'AuthenticationService',
    'Base64Service',
    'VrijBerichtConfig'
]);

beheerApp.config(
    ['$routeProvider',
     function ($routeProvider) {
         $routeProvider
             .when('/', {
                 templateUrl: 'views/welkom.html'
             })
             .otherwise({redirectTo: '/'});
     }]);

beheerApp.config(['paginationConfig', function (paginationConfig) {
    paginationConfig.rotate = true;
    paginationConfig.maxSize = 9;
    paginationConfig.boundaryLinks = true;
    paginationConfig.firstText = '«'; // &laquo;
    paginationConfig.previousText = '‹'; // &lsaquo;
    paginationConfig.nextText = '›'; // &rsaquo;
    paginationConfig.lastText = '»'; // &raquo;
}]);

beheerApp.config(['cfpLoadingBarProvider', function (cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
}]).config(['cfpLoadingBarProvider', function (cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeBar = true;
}]);

beheerApp.value("context", {params: {}, vorig: {}});

beheerApp.run(function ($rootScope, $injector) {
    var config = $injector.get('VersieConfig');
    var resource = $injector.get(config.resourceNaam).resource();
    resource.get(function (versie) {
        $rootScope.versie = versie.versie;
    });
});

beheerApp.run(function ($rootScope, $injector) {
    var config = $injector.get('ConfiguratieConfig');
    var resource = $injector.get(config.resourceNaam).resource();
    resource.get(function (configuratie) {
        $rootScope.configuratie = configuratie;
    });
});
