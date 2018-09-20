Meta:
@status         Onderhanden
@regels         geboortenaam1
@auteur         dihoe
@sleutelwoorden geboorteEnVerhuizing

Narrative: Deze testscenario laat zien hoe een testpersoon wordt geboren met 2 ouders
zonder dat een bijhouding nodig is
en er wordt een vulbericht aangemaakt.

Scenario: 1. Persoon wordt geboren

Given de database is gereset voor de personen 306867837, 306741817
Given de personen 410217360 zijn verwijderd
Given de persoon beschrijvingen:
def adam = uitDatabase persoon: 5
def eva = uitDatabase persoon: 6

tester = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind') {
        op '1991/11/11' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 410217360, anummer: 2489643026
    }
    naamswijziging(aanvang:20090909) {
            geslachtsnaam(stam: 'Smith').wordt(stam: 'Cock', voorvoegsel: 'de')
    }
}

slaOp(tester)

When voor persoon 410217360 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep	            | nummer | attribuut         | verwachteWaarde |
| samengesteldeNaam | 1      | geslachtsnaamstam | Cock            |
| samengesteldeNaam | 2      | geslachtsnaamstam | Smith           |
| samengesteldeNaam | 3      | geslachtsnaamstam | Smith           |

