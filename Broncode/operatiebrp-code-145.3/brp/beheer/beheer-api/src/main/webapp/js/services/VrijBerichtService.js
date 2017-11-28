(function (angular) {

    var services = angular.module('beheerApp.services');

    services.factory('VrijBerichtService', ['$resource', 'instelling',
         function ($resource, instelling) {
             
             function getSoortVrijBericht() {
                 return $resource(instelling.api_locatie + 'vrijbericht/geldigesoortvrijber').query().$promise;
             }

             function getPartijen() {
                 return $resource(instelling.api_locatie + 'vrijbericht/geldigepartijen').query().$promise;
             }

             function verstuurVrijBericht(soort, inhoud, partijen) {
                 var partijArray = [];
                 partijen.forEach(function (partij) {
                    partijArray.push(partij.id);
                 });
                 var object = {
                     soortvrijber: soort,
                     data: inhoud,
                     partijen: partijArray
                 };
                 var resource = $resource(instelling.api_locatie + 'vrijbericht', {}, {verstuur: { method: 'POST' }});
                 return resource.verstuur(object).$promise;
             }

             return {
                 getSoortVrijBericht: getSoortVrijBericht,
                 getPartijen: getPartijen,
                 verstuurVrijBericht: verstuurVrijBericht
             };
         }
    ]);
})(angular);
