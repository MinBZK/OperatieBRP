Meta:
@status Onderhanden
@auteur dihoe
@regels diana7

Narrative: Deze testscenario laat zien hoe een testpersoon wordt geboren met 2 ouders
zonder dat een bijhouding nodig is
en er wordt een vulbericht aangemaakt.

Scenario: 1. Persoon wordt geboren
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 410263874 zijn verwijderd
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
        identificatienummers bsn: 410263874, anummer: 2489643053
    }
    naamswijziging(aanvang:20090909) {
            geslachtsnaam(stam: 'Smith').wordt(stam: 'Cock', voorvoegsel: 'de')
    }
}


slaOp(tester)

Persoon.nieuweGebeurtenissenVoor(adam) {
   naamswijziging(aanvang:20101010) {
            geslachtsnaam(stam: 'Laar').wordt(stam: 'Testvader')
    }
}

slaOp(adam)

Persoon.nieuweGebeurtenissenVoor(eva) {
    naamswijziging(aanvang:20121212) {
            geslachtsnaam(stam: 'Verheul').wordt(stam: 'Testmoeder')
    }
}

slaOp(eva)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand geboorte_daarna_naamswijziging_ook_van_ouders_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep	            | nummer | attribuut         | verwachteWaarde |
| samengesteldeNaam | 1      | geslachtsnaamstam | Testvader       |


