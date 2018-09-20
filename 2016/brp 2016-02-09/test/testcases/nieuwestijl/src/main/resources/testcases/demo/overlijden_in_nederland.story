Meta:
@auteur             dihoe
@status             Onderhanden
@regels dianaover
@sleutelwoorden     overlijdenInNederland

Narrative:  Als
            wil ik
            zodat

Scenario: 1. Elvis is geboren en overlijdt in Monster

Given de database is gereset voor de personen 126477735, 64258099
Given de personen 429543657 zijn verwijderd
Given de persoon beschrijvingen:
def moederElvis   = uitDatabase bsn: 64258099
def vaderElvis    = uitDatabase bsn: 126477735

Elvis = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19901225, toelichting: '1e kind') {
        op '1995/12/25' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Elvis', 'Aaron'
            geslachtsnaam 'Presley'

        }
        ouders moeder: moederElvis, vader: vaderElvis
        identificatienummers bsn: 429543657, anummer: 5607075602
    }
    overlijden() {
      op '2015/05/18' te 'Monster' gemeente 'Westland'
    }
}
slaOp(Elvis)

When voor persoon 429543657 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep      | nummer | attribuut    | verwachteWaarde |
| overlijden | 1      | datum        | 2015-05-18      |
