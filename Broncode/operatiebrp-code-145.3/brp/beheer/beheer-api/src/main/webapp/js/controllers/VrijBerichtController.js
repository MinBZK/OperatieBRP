/**
 * Controller specifiek voor nieuwe vrije berichten.
 */
(function (angular) {
    var controllers = angular.module('beheerApp.controllers');

    controllers.controller('VrijBerichtController', ['$rootScope', 'soortenVrijBericht', 'partijen', 'VrijBerichtService',
        function($rootScope, soortenVrijBericht, partijen, VrijBerichtService) {

            var vrijBericht = this;

            vrijBericht.soortenVrijBericht = soortenVrijBericht;
            vrijBericht.partijen = partijen;

            if (partijen.length === 0) {
                $rootScope.$broadcast('info', {type: 'danger', message: "Geen geldige partijen met geldige vrij bericht autorisatie gevonden."});
            }

            vrijBericht.verstuur = function(form) {
                if (form.$valid) {
                    VrijBerichtService.verstuurVrijBericht(form.soortVrijBericht.$modelValue,
                                                           form.inhoud.$modelValue,
                                                           form.partijen.$modelValue)
                        .then(function() {
                            $rootScope.$broadcast('info', {type: 'success', message: "Vrij bericht verstuurd."});
                            delete vrijBericht.soort;
                            delete vrijBericht.geselecteerdePartijen;
                        });
                }
            };

        }
    ]);
})(angular);