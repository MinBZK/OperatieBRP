describe('json-toon', function() {
    var $compile, $rootScopei, scope;

    beforeEach(module('json-toon'));
    beforeEach(inject(function(_$compile_, _$rootScope_) {
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        scope = _$rootScope_.$new();
    }));


    it('Element moet worden vervangen', function() {
        scope.gegevens = {'test': 'wauw'}
        var element = $compile('<json-toon gegevens="gegevens"></json-toon>')(scope);
        $rootScope.$digest();
        expect(element.html()).toContain('wauw');
    });

    it('Element moet object in object tonen', function() {
        scope.gegevens = {'test': { 'eerste': 1, 'tweede': 2} }
        var element = $compile('<json-toon gegevens="gegevens"></json-toon>')(scope);
        $rootScope.$digest();
        expect(element.html()).toContain('<label class="ng-binding">eerste</label></div>');
        expect(element.html()).toContain('<label class="ng-binding">tweede</label></div>');
        expect(element.html()).toContain('<div class="col-xs-12 col-sm-9 col-md-10 ng-binding">1</div>');
        expect(element.html()).toContain('<div class="col-xs-12 col-sm-9 col-md-10 ng-binding">2</div>');
    });

    it('Element moet velden beginnend met $ niet tonen', function() {
        scope.gegevens = {'test': { 'eerste': 1, '$tweede': 2} }
        var element = $compile('<json-toon gegevens="gegevens"></json-toon>')(scope);
        $rootScope.$digest();
        expect(element.html()).toContain('<label class="ng-binding">eerste</label></div>');
        expect(element.html()).not.toContain('<label class="ng-binding">$tweede</label></div>');
        expect(element.html()).toContain('<div class="col-xs-12 col-sm-9 col-md-10 ng-binding">1</div>');
        expect(element.html()).not.toContain('<div class="col-xs-12 col-sm-9 col-md-10 ng-binding">2</div>');
    });

    it('Verwerk een complex object', function() {
        scope.gegevens = { 'Afgeleid administratief' : {
            '$type' : 'groep',
            'Technisch ID' : 1,
            '$indexObject' : '140',
            '$sleutel' : 'Afgeleid administratief',
            'Administratieve handeling' : '1001',
            'Tijdstip laatste wijziging' : '2015-05-15 09:38:47',
            'Sorteervolgorde' : '1',
            'Onderzoek naar niet opgenomen gegevens?' : 'Nee',
            'Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig?' : 'Nee',
            'Tijdstip laatste wijziging GBA-systematiek' : '2015-05-15 09:38:47',
            'Mutaties voor Afgeleid administratief' : {
              '$indexObject' : '141',
              '$type' : 'tabel',
              'kolomtitels' : [ '$indexObject', 'Datum/tijd registratie', 'Datum/tijd verval', 'Nadere aanduiding verval', 'Toegevoegd', 'Vervallen', '$css', '$detail' ],
              'data' : [ {
                '$indexObject' : '142',
                'Datum/tijd registratie' : '2015-05-15 09:38:47',
                'Datum/tijd verval' : null,
                'Nadere aanduiding verval' : null,
                'Toegevoegd' : {
                  '$type' : 'cssLink',
                  'linkTekst' : 'Ja',
                  'link' : 'brp-markeer-1001'
                },
                'Vervallen' : {
                  '$type' : 'cssLink',
                  'linkTekst' : 'Nee',
                  'link' : ''
                },
                '$css' : 'brp-markeer-1001 ',
                '$detail' : {
                  'Technisch ID' : 1,
                  '$indexObject' : '143',
                  'Administratieve handeling' : '1001',
                  'Tijdstip laatste wijziging' : '2015-05-15 09:38:47',
                  'Sorteervolgorde' : '1',
                  'Onderzoek naar niet opgenomen gegevens?' : 'Nee',
                  'Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig?' : 'Nee',
                  'Tijdstip laatste wijziging GBA-systematiek' : '2015-05-15 09:38:47'
                }
              } ]
            }
          }
        };
        var element = $compile('<json-toon gegevens="gegevens"></json-toon>')(scope);
        $rootScope.$digest();
        console.log(element.html());
    });
});
