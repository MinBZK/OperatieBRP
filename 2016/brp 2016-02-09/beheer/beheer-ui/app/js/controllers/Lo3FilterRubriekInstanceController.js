angular.module('Lo3FilterRubriekInstanceController', [])
.controller('Lo3FilterRubriekInstanceController', ['$rootScope', '$scope', '$q', '$modalInstance', 'onderhandenitem', 'context', 'bron', 'titel', 'rubriekBron',
    function($rootScope, $scope, $q, $modalInstance, onderhandenitem, context, bron, titel, rubriekBron) {
        $scope.titel = titel;
        if (onderhandenitem.waardenlijst.indexOf('rubrieken') === -1) {
            $scope.waardenlijst = onderhandenitem.waardenlijst;
        } else {
            $scope.waardenlijst = "";
        }

        var bepaalBijwerkenRubrieken = function(orginelelijst, nieuwelijst) {
            var oudelijstmap = {};
            orginelelijst.map(function(rubriek) {
                oudelijstmap[rubriek.rubrieknaam] = rubriek;
            });
            var oudelijst = orginelelijst.map(function(rubriek) {
                return rubriek.rubrieknaam;
            });
            //console.log(orginelelijst);
            var resultaat = [];
            //loop door oudelijst en check welke voor update
            angular.forEach(oudelijst, function(ouderubriek) {
                var ouderubriekobject = angular.copy(oudelijstmap[ouderubriek]);
                if (~nieuwelijst.indexOf(ouderubriekobject.rubrieknaam)) {
                    if (ouderubriekobject.actief !== 'Ja') {
                        ouderubriekobject.actief = 'Ja';
                        resultaat.push(ouderubriekobject);
                    }
                } else {
                    if (ouderubriekobject.actief === 'Ja') {
                        ouderubriekobject.actief = 'Nee';
                        resultaat.push(ouderubriekobject);
                    }
                }
            });
            return resultaat;
        };

        var bepaalNieuwToetevoegen = function(nieuwelijst, oudelijst) {
            var nieuwtoevoegen = nieuwelijst.filter(function(el) {
                var result = true;
                angular.forEach(oudelijst, function(item) {
                    if (result) {
                        result = (item !== el);
                    }
                });
                if (result) {
                    return el;
                }
            });
            var beloften = nieuwtoevoegen.map(function(nieuwerubriek, i) {
                var belofte = $q.defer();
                var nieuwerubriekobject = {};
                nieuwerubriekobject.abonnement = context.params.abonnement;
                nieuwerubriekobject.actief = 'Ja';
                var params = {
                    naam: nieuwerubriek
                };
                rubriekBron.get(params, function success(data) {
                    if (data.numberOfElements == 1) {
                        ontvangenrubriek = data.content[0];
                        nieuwerubriekobject.rubriek = ontvangenrubriek.iD;
                        nieuwerubriekobject.rubrieknaam = ontvangenrubriek.naam;
                        belofte.resolve(nieuwerubriekobject);
                    } else {
                        belofte.reject('verkeerde gegevens ontvangen');
                    }
                }, function error(data) {
                    belofte.reject(data);
                });
                return belofte.promise;
            });
            return beloften;
        };

        var bepaalNieuweLijst = function(waardenlijst) {
            console.log(waardenlijst);
            return waardenlijst.split('#');
        };

        var opslaanRubrieken = function(lijst) {
            var beloften = lijst.map(function(item) {
                var gewijzigdItem = new bron(item);
                return gewijzigdItem.$save(context.params);
            });
            return beloften;
        };

        $scope.opslaan = function () {
            if (!onderhandenitem.item) {
                onderhandenitem.item = [];
            }
            var nl = bepaalNieuweLijst($scope.waardenlijst);
            var lijstbijtewerken = bepaalBijwerkenRubrieken(onderhandenitem.item, nl);
            var oudelijst = onderhandenitem.item.map(function(rubriek) {
                return rubriek.rubrieknaam;
            });
            $q.all(
                bepaalNieuwToetevoegen(nl, oudelijst)
            ).then(function success(data) {
                return data.concat(lijstbijtewerken);
            }).then(function success(data) {
                return opslaanRubrieken(data);
            }).then(function success(data) {
                console.log(data);
                $modalInstance.close();
                $rootScope.$broadcast('info', {type: 'success', message: "Opslaan gelukt."});
                $rootScope.$broadcast('succes', {item: 'sub'});
            }).catch(function () {
                $rootScope.$broadcast('info', {type: 'danger', message: "Een van de opgevoerde rubrieken is niet correct"});
            });
        };

        $scope.annuleren = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.hasRole = function(role) {
        	return $rootScope.user && $rootScope.user.authorities.indexOf(role) != -1;
        };
    }
]);
