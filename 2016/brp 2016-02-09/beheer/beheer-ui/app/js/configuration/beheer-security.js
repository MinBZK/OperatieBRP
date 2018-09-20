beheerApp.config([ '$routeProvider', '$httpProvider', 'localStorageServiceProvider', function($routeProvider, $httpProvider, localStorageServiceProvider) {

    // ======= local storage configuration ========

    localStorageServiceProvider.prefix = 'brp-beheer';
    localStorageServiceProvider.setStorageType('sessionStorage');

	// ======= router configuration =============

	$routeProvider
		.when('/login', {
			templateUrl: 'views/login.html'
		})
		.when('/unauthorised', {
			templateUrl: 'views/login.html'
		})
		;

	// ======== http configuration ===============

	//configure $http to view a login whenever a 401 unauthorized response arrives
	$httpProvider.interceptors.push(function($rootScope, $q) {
		return {
			    responseError: function(response) {
                    if (response.status === 401 || response.status === 0) {
                        var deferred = $q.defer(),
                            req = {
                                config: response.config,
                                deferred: deferred
                            };

                        $httpProvider.defaults.headers.common.Authorization = null;

                        $rootScope.requests401.push(req);
                        $rootScope.$broadcast('event:loginRequired');

                        return deferred.promise;
                    }
                    if(response.status == 403) {
                    	$rootScope.$broadcast('event:unauthorised');
                    	return $q.defer().promise;
                    }
                    if(response.status == 404) {
                        $rootScope.$broadcast('info', {type: 'danger', code: "SEARCH_FAILED", message: "Geen resultaat beschikbaar"});
                        return $q.reject(response);
                    }
                    return $q.reject(response);
			    }
			  };
	});

}]);

beheerApp.run(function ($rootScope, $http, $location, Base64Service, AuthenticationService, localStorageService) {

    $rootScope.user = localStorageService.get('localStorageUser');
    $http.defaults.headers.common.Authorization = localStorageService.get('localStorageAuth');

    $rootScope.errors = [];
    $rootScope.requests401 = [];
    $rootScope.navigateTo = "/main";

    $rootScope.$on('$routeChangeSuccess', function (event, next, current) {
        $rootScope.user = localStorageService.get('localStorageUser');
        $http.defaults.headers.common.Authorization = localStorageService.get('localStorageAuth');
    });

    /**
     * Holds all the requests which failed due to 401 response.
     */
    $rootScope.$on('event:loginRequired', function () {
        $rootScope.requests401 = [];

        if ($location.path().indexOf("/login") == -1) {
            $rootScope.navigateTo = $location.path();
        }

        $location.path('/login');
    });

    /**
     * On 'event:loginConfirmed', resend all the 401 requests.
     */
    $rootScope.$on('event:loginConfirmed', function () {
        var i,
            requests = $rootScope.requests401,
            retry = function (req) {
                $http(req.config).then(function (response) {
                    req.deferred.resolve(response);
                });
            };

        for (i = 0; i < requests.length; i += 1) {
            retry(requests[i]);
        }

        $rootScope.requests401 = [];
        $rootScope.errors = [];
    });

    /**
     * On 'event:loginRequest' send credentials to the server.
     */
    $rootScope.$on('event:loginRequest', function (event, username, password) {
        // set the basic authentication header that will be parsed in the next request and used to authenticate
        var authstring = 'Basic ' + Base64Service.encode(username + ':' + password);
        $http.defaults.headers.common.Authorization = authstring;

        AuthenticationService.login(authstring).then(
            function success() {
                $rootScope.user = localStorageService.get('localStorageUser');
                $rootScope.$broadcast('event:loginConfirmed');
            },
            function error() {
                $rootScope.errors.push({ code: "LOGIN_FAILED", message: "Er is een fout opgetreden, probeer nogmaals" });
            });
    });

    /**
     * On 'logoutRequest' invoke logout on the server.
     */
    $rootScope.$on('event:logoutRequest', function () {
        //$http.defaults.headers.common.Authorization = null;

        AuthenticationService.logout().then(
            function success() {
                $rootScope.user = null;
                $location.path("/login");
                //localStorageService.remove('localStorageUser');
                //localStorageService.remove('localStorageAuth');
            },
            function error() {
                $rootScope.errors.push({ code: "LOGOUT_FAILED", message: "Er is een fout opgetreden, probeer nogmaals" });
            });
    });

    /**
     * Holds all the requests which failed due to 401 response.
     */
    $rootScope.$on('event:unauthorised', function () {
        $location.path('/unauthorised');
    });
});
