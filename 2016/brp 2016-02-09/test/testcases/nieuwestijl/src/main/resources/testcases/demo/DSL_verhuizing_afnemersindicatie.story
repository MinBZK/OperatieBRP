Meta:
@auteur             rarij
@status             Uitgeschakeld
@sleutelwoorden     verhuizingEnAfnemersindicatie

Narrative:  Als
            wil ik
            zodat

Scenario:   Afnemer krijgt a.g.v. afnemersindicatie een volledigBericht met daarin de verhuizing

Given de database is gereset voor de personen 150018502, 306741817
Given de personen 290124050 zijn verwijderd
Given de persoon beschrijvingen:
def vader_Mose     =    Persoon.uitDatabase(bsn: 150018502)
def moeder_Mose    =    Persoon.uitDatabase(bsn: 306741817)

Mose = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Mose'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Mose, vader: vader_Mose
        identificatienummers bsn: 290124050, anummer: 2000603090
    }
}
slaOp(Mose)

persoon = Persoon.uitDatabase(bsn: 290124050)

Persoon.nieuweGebeurtenissenVoor(Mose) {
    verhuizing(partij: 'Gemeente Groningen', aanvang: 20120516) {
        naarGemeente 'Groningen',
           straat: 'Dorpstraat', nummer: 13, postcode: '4207AA', woonplaats: "Groningen"
    }
}
slaOp(Mose)

persoon = Persoon.uitDatabase(bsn: 290124050)
nieuweGebeurtenissenVoor(Mose) {
    afnemerindicaties {
        plaatsVoor(afnemer: 34401, abonnement: 'Geen pop.bep. levering op basis van afnemerindicatie') {
            datumAanvangMaterielePeriode '2015/05/15'
        }
    }
}
slaOp(Mose)

When voor persoon 290124050 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290124050            |

Scenario:   Verwijderen van een afnemersindicatie

Given de persoon beschrijvingen:

persoon = Persoon.uitDatabase(bsn: 290124050)
nieuweGebeurtenissenVoor(persoon) {
    afnemerindicaties {
        verwijderVan(afnemer: 34401, abonnement: 'Geen pop.bep. levering op basis van afnemerindicatie')
    }
}
slaOp(persoon)

When voor persoon 290124050 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290124050            |
