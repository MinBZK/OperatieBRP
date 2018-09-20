describe('InfoController', function() {
    var scope, ctrl;

    beforeEach(module('InfoController'));
    beforeEach(inject(function($controller, $rootScope) {
        scope = $rootScope.$new();
        ctrl = $controller('InfoController', {$scope: scope});
    }));


    it('InfoController moet bij start geen info hebben', function() {
        expect(scope.informatieBeschikbaar()).toBe(false);
    });

    it('InfoController moet een succes event opvangen en info doorgeven', inject(function($rootScope) {
        $rootScope.$broadcast('info', {type: 'success', message: "Opslaan gelukt."});
        expect(scope.informatieBeschikbaar()).toBe(true);
    }));

    it('InfoController moet na timeout informatie wissen', inject(function($rootScope, $timeout) {
        $rootScope.$broadcast('info', {type: 'success', message: "Opslaan gelukt."});
        expect(scope.informatieBeschikbaar()).toBe(true);
        $timeout(function() {
            expect(scope.informatieBeschikbaar()).toBe(false);
            console.log("test timeout works");
        }, 6000);
        $timeout.flush();
    }));
});
