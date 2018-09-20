angular.module('SecurityController', [])
.controller('SecurityController', function ($rootScope, $scope, $location) {
    $scope.logout = function () {
        $scope.$emit('event:logoutRequest');
        $rootScope.user = null;
        $location.path("/login");
    };

    $scope.login = function (credentials) {
        $scope.$emit('event:loginRequest', credentials.username, credentials.password);
        $location.path($rootScope.navigateTo);
    };

    $scope.hasRole = function(role) {
    	return $rootScope.user && $rootScope.user.authorities.indexOf(role) != -1;
    };
})
.run(function($rootScope, $location, $interval) {

    var lastDigestRun = Date.now();

    $rootScope.$watch(function(evt) {
        var now = Date.now();
        if (now - lastDigestRun > 30*60*1000) {
            $rootScope.$emit('event:logoutRequest');
            $rootScope.user = null;
            $location.path("/login");
        }
        lastDigestRun = Date.now();
    });
});
