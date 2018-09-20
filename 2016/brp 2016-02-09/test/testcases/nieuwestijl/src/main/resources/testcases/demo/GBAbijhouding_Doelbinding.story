Meta:
@auteur             rarij
@status             Uitgeschakeld

Narrative:
            Als
            wil ik
            zodat

Scenario:   1. BRPbijhouding verhuizing binnenGemeente. Abonnement o.b.v. doelbinding levert mutatieBericht

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind') {
        op '1991/11/11' te 'Haarlem' gemeente 'Haarlem'
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

def Bruce1 = Persoon.uitDatabase(bsn: 361599882)
nieuweGebeurtenissenVoor(Bruce1) {
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 19930731) {
        naarGemeente 'Haarlem',
           straat: 'Dorpstraat', nummer: 13, postcode: '2000AA', woonplaats: "Haarlem"
    }
    verhuizing(aanvang: 20120101) {
        binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Haarlem"
    }
}
slaOp(Bruce1)

When voor persoon 361599882 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep    | attribuut      | verwachteWaardes          |
| adressen | woonplaatsnaam | Haarlem, Haarlem, Haarlem |

When het volledigbericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   2. GBAbijhouding verhuizing binnenGemeente. Abonnement o.b.v. doelbinding levert volledigBericht

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind') {
        op '1991/11/11' te 'Haarlem' gemeente 'Haarlem'
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

def Bruce1 = Persoon.uitDatabase(bsn: 361599882)
nieuweGebeurtenissenVoor(Bruce1) {
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 19930731) {
        naarGemeente 'Haarlem',
           straat: 'Dorpstraat', nummer: 13, postcode: '2000AA', woonplaats: "Haarlem"
    }
    GBABijhoudingOverig() {
        binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Haarlem"
    }
}
slaOp(Bruce1)

When voor persoon 361599882 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het mutatiebericht voor leveringsautorisatie postcode gebied Haarlem 2000 - 2099 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
