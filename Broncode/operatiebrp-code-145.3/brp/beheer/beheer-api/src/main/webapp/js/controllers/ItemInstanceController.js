(function (angular) {
    var controllers = angular.module('beheerApp.controllers');

    var processConditioneleVelden = function (_kolommen, params) {
        var kolommen = angular.copy(_kolommen);
        angular.forEach(kolommen, function (kolom) {
            if (kolom.toonOpBewerken && kolom.conditioneelParam && kolom.conditioneelWaarde !== params[kolom.conditioneelParam]) {
                kolom.toonOpBewerken = false;
            }
        });
        return kolommen;
    };

    var mergeObjects = function (obj1, obj2) {
        var obj3 = {};
        for (var attrname1 in obj1) {
            obj3[attrname1] = obj1[attrname1];
        }
        for (var attrname2 in obj2) {
            obj3[attrname2] = obj2[attrname2];
        }
        return obj3;
    };

    controllers.controller(
        'ItemInstanceController',
        ['$rootScope', '$scope', '$modalInstance', 'context', 'onderhandenitem', 'bron', 'subkolommen', 'titel',
         function ($rootScope, $scope, $modalInstance, context, onderhandenitem, bron, subkolommen, titel) {

             $scope.onderhandenitem = onderhandenitem;
             $scope.subkolommen = processConditioneleVelden(subkolommen, mergeObjects(onderhandenitem.item, context.params));

             var actie = ' wijzigen';
             if (onderhandenitem.status.nieuw) {
                 actie = ' toevoegen';
             }
             if (onderhandenitem.relatie) {
                 $scope.titel = titel + actie + ' - ' + onderhandenitem.relatie;
             } else {
                 $scope.titel = titel + actie;
             }

             $scope.opslaan = function (form) {
                 if (form.$dirty && !form.$invalid) {
                     var gewijzigdItem = new bron($scope.onderhandenitem.item);
                     gewijzigdItem.$save(
                         context.params,
                         function (value) {
                             if (onderhandenitem.status.nieuw) {
                                 angular.copy(value, $scope.onderhandenitem.item);
                                 if (onderhandenitem.blijfOpWijzig) {
                                     context.params[onderhandenitem.contextParamNaam] = value.id;
                                     onderhandenitem.status.nieuw = false;
                                     $scope.subkolommen = processConditioneleVelden(subkolommen, mergeObjects(onderhandenitem.item, context.params));
                                 } else {
                                     $modalInstance.close(value);
                                 }
                             } else {
                                 $modalInstance.close(value);
                             }
                             $rootScope.$broadcast('info', {type: 'success', message: "Opslaan gelukt."});
                             $rootScope.$broadcast('succes', {item: 'sub'});
                         },
                         function (httpResponse) {
                             if (httpResponse.data) {
                                 $rootScope.$broadcast('info', {
                                     type: 'danger',
                                     code: "SAVE_FAILED",
                                     message: (httpResponse.data.message ? httpResponse.data.message
                                         : "Opslaan mislukt.")
                                 });
                             } else {
                                 $rootScope.$broadcast('info', {
                                     type: 'danger',
                                     code: "SAVE_FAILED",
                                     message: "Opslaan niet mogelijk, object is van onbekend type"
                                 });
                             }

                         }
                     );
                 }
             };

             $scope.annuleren = function () {
                 $modalInstance.dismiss('cancel');
             };
         }
        ]);
})(angular);
