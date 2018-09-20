Meta:
@auteur             rarij
@status             Uitgeschakeld

Narrative:
            Als
            wil ik
            zodat

Scenario:   1. BRPbijhouding verhuizing binnenGemeente. Abonnement o.b.v. afnemerindicatie levert mutatieBericht

Meta:
@regels diana2

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 17401, aanvang: 19911111, toelichting: '1e kind') {
        op '1991/11/11' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'MAN'
        namen {
            voornamen 'Bruce', 'Jun-fan'
            geslachtsnaam 'Lee'

        }
        ouders moeder: moederBruce, vader: vaderBruce
        identificatienummers bsn: 361599882, anummer: 2763926162
    }
    afnemerindicaties {
                plaatsVoor(afnemer: 17401, abonnement: 'Geen pop.bep. levering op basis van afnemerindicatie') {
                    datumAanvangMaterielePeriode '2012/05/15'
                }
    }
    verhuizing(aanvang: 20120101) {
            binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Giessenlanden"
    }
}
slaOp(Bruce)

When voor persoon 361599882 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   2. GBAbijhouding verbeteringGeboorteakte. Abonnement o.b.v. afnemerindicatie levert volledigBericht

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 361599882 zijn verwijderd
Given de persoon beschrijvingen:
def vaderBruce      = Persoon.uitDatabase(bsn: 306867837)
def moederBruce     = Persoon.uitDatabase(bsn: 306741817)

Bruce = uitGebeurtenissen {
    geboorte(partij: 17401, aanvang: 19911111, toelichting: '1e kind') {
        op '1991/11/11' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'MAN'
        namen {
            voornamen 'Bruce', 'Jun-fan'
            geslachtsnaam 'Lee'

        }
        ouders moeder: moederBruce, vader: vaderBruce
        identificatienummers bsn: 361599882, anummer: 2763926162
    }
    afnemerindicaties {
                plaatsVoor(afnemer: 17401, abonnement: 'Geen pop.bep. levering op basis van afnemerindicatie') {
                    datumAanvangMaterielePeriode '2012/05/15'
                }
    }
    GBABijhoudingOverig() {
            binnenGemeente straat: 'Dorpstraat', nummer: 14, postcode: '2002AA', woonplaats: "Giessenlanden"
    }
}
slaOp(Bruce)

When voor persoon 361599882 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then verantwoording acties staan in persoon

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

!-- bevinding: Geboorte via DSL: kind neemt niet automatisch adres van ouder 1
!-- bevinding: afnemerindicatie levert nu altijd een volledigbericht
!-- bevinding: verhuizing binnengemeente kan niet als losse gebeurtenis
