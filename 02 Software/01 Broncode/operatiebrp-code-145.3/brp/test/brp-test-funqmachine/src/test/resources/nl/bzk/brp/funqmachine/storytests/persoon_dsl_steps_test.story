Meta:
@epic Test

Scenario: Toon gebruik van de PersoonDSL

Given de persoon beschrijvingen:
def tester = Persoon.uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19781023, toelichting: 'ooievaar') {
        geslacht 'MAN'
        namen {
            voornamen 'Jan', 'Peter'
            geslachtsnaam 'Balkenende'
        }
        op '1978/10/22' te 'Monster' gemeente 'Westland'
        identificatienummers bsn: 123434538, anummer: 8934753756
    }
}
// niet uitvoeren in unittest: Persoon.slaOp(tester)
