angular.module('AuthenticationService', [])
.service('AuthenticationService', function ($http, $q, localStorageService, instelling) {
    this.login = function (authstring) {
        var d = $q.defer();

        $http.get(instelling.api_locatie + 'user/authenticated')
            .success(function (user) {
                localStorageService.set('localStorageUser', user);
                localStorageService.set('localStorageAuth', authstring);
                d.resolve();
            })
            .error(function () {
                d.reject();
            });

        return d.promise;
    };

    this.logout = function () {
        var d = $q.defer();

        $http.defaults.headers.common.Authorization = localStorageService.get('localStorageAuth');

        $http.post(instelling.api_locatie + 'logout')
            .success(function () {
                localStorageService.remove('localStorageUser');
                localStorageService.remove('localStorageAuth');
                $http.defaults.headers.common.Authorization = null;
                d.resolve();
            })
            .error(function () {
                d.reject();
            });

        return d.promise;
    };
});
