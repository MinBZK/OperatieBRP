Meta:
@status Klaar
@auteur dihoe
@regels geboorte

Narrative: Deze testscenario laat zien hoe een testpersoon wordt geboren met 2 ouders
zonder dat een bijhouding nodig is
en er wordt een vulbericht aangemaakt.

Scenario: 1. Persoon wordt geboren

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 410615523 zijn verwijderd
Given de persoon beschrijvingen:
def adam    =   Persoon.uitDatabase(bsn: 306867837)
def eva     =   Persoon.uitDatabase(bsn: 306741817)

testpersoon = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind') {
        op '1991/11/11' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'
            samengesteldeNaam voornamen: 'Cees', stam: 'Override', scheidingsteken: '-'
        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 410615523, anummer: 2489643065
    }
}

slaOp(testpersoon)

When voor persoon 410615523 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep	            | nummer | attribuut | verwachteWaarde |
| samengesteldeNaam | 1      | voornamen | Cees            |
| samengesteldeNaam | 2      | voornamen | Albert          |
| samengesteldeNaam | 3      | voornamen | Anne-Marie      |
