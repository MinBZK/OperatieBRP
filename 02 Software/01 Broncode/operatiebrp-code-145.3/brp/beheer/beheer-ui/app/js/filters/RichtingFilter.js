(function (angular) {
    angular.module('beheerApp.filters').filter('richting', [
        function () {

            function wijzigNaam(soortBerichtVrijBericht) {
                if (soortBerichtVrijBericht.id === 1) {
                    soortBerichtVrijBericht.naam = 'Verzonden';
                }
                if (soortBerichtVrijBericht.id === 2) {
                    soortBerichtVrijBericht.naam = 'Ingekomen';
                }
            }

            return function (soortBerichtVrijBericht) {
                if (Array.isArray(soortBerichtVrijBericht)) {
                    soortBerichtVrijBericht.forEach(function(item) {
                        wijzigNaam(item);
                    });
                } else {
                    wijzigNaam(soortBerichtVrijBericht);
                }
            };
        }]);
})(angular);