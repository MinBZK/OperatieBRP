Meta:
@auteur             rarij
@status             Uitgeschakeld

Narrative:
            Als
            wil ik
            zodat

Scenario:   1. BRPbijhouding verhuizing binnenGemeente. Abonnement o.b.v. afnemerindicatie levert mutatieBericht

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind') {
        op '1991/11/11' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'MAN'
        namen {
            voornamen 'Bruce', 'Jun-fan'
            geslachtsnaam 'Lee'

        }
        ouders moeder: moederBruce, vader: vaderBruce
        identificatienummers bsn: 361599882, anummer: 2763926162
    }
}
slaOp(Bruce)

When voor persoon 361599882 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon
