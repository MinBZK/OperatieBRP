Meta:
@sprintnummer           69
@epic                   Change yyyynn: CorLev - Element tabel
@auteur                 rarij
@jiraIssue              TEAMBRP-2435, TEAMBRP-2456
@status                 Klaar
@sleutelwoorden         synchronisatie

Narrative:
    Als stelselbeheerder
    wil ik per soort betrokkenheid onderscheid kunnen maken bij het instellen van de autorisatie van een Abonnement,
    zodat ik van gerelateerde personen niet meer gegevens verstrek dan noodzakelijk is voor die afnemer.

Scenario: Zowel 'actuele' als 'formele' historie van de groep geboorte voor
          de hoofdpersoon en enkel 'actuele' historie voor de gemuteerde ouder

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Abo_autorisatie_geen_geboorte_historie_voor_gerelateerde_ouder
Given de persoon beschrijvingen:
def oma     = Persoon.uitDatabase(bsn: 999341091)
def opa     = Persoon.uitDatabase(bsn: 400126217)
def moeder  = Persoon.uitDatabase(bsn: 110012823)

vader = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19670203, toelichting: '1e kind', registratieDatum: 19670201) {
        op '1967/02/00' te 'Delft' gemeente 0503
        geslacht 'MAN'
        namen {
            voornamen 'Gregory'
            geslachtsnaam 'Porter'
        }
        ouders moeder: oma, vader: opa
        identificatienummers bsn: 410060094, anummer: 1823669458
    }
}
slaOp(vader)

def vader = Persoon.uitDatabase(bsn: 410060094)
Persoon.nieuweGebeurtenissenVoor(vader) {
    verbeteringGeboorteakte(partij: 34401, aanvang: 19870103, toelichting:'Correctie datumGeboorte en wplGeboorte', registratieDatum: 19870103){
        op '1967/02/01' te 'Delft' gemeente 503
    }
}
slaOp(vader)

zoon = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19870103, toelichting: '1e kind', registratieDatum: 19870103) {
        op '1987/01/01' te 'Bedum' gemeente 0005
        geslacht 'MAN'
        namen {
            voornamen 'Louis'
            geslachtsnaam 'Armstong'
        }
        ouders moeder: moeder, vader: vader
        identificatienummers bsn: 410206027, anummer: 2638768018
    }
}
slaOp(zoon)

def zoon = Persoon.uitDatabase(bsn: 410206027)
Persoon.nieuweGebeurtenissenVoor(zoon) {
    verbeteringGeboorteakte(partij: 34401, aanvang: 19870105, toelichting:'Correctie datumGeboorte en wplGeboorte', registratieDatum: 19870105){
        op '1987/01/01' te 'Groningen' gemeente 0014
    }
}
slaOp(zoon)


Given verzoek voor leveringsautorisatie 'Abo autorisatie geen geboorte historie voor gerelateerde ouder' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand abonnement_autorisatie_ per_soort_abonnement_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo autorisatie geen geboorte historie voor gerelateerde ouder is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep             | attribuut         | verwachteWaardes                              |
| geboorte          | datum             | 1987-01-01,1987-01-01,1969-05-16,1967-02-01   |

Then hebben attributen in voorkomens de volgende waardes:
| groep             | nummer            | attribuut          | verwachteWaarde |
| geboorte          | 1                 | woonplaatsnaam     | Groningen       |
| geboorte          | 2                 | woonplaatsnaam     | Bedum           |


Scenario: Enkel 'actuele' historie van de groep geboorte voor de hoofdpersoon
             en zowel 'actuele' als 'formele' historie voor de gemuteerde ouder

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/Abo_autorisatie_alleen_geboorte_historie_voor_gerelateerde_ouder
Given de persoon beschrijvingen:
def oma     = Persoon.uitDatabase(bsn: 999341091)
def opa     = Persoon.uitDatabase(bsn: 400126217)
def moeder  = Persoon.uitDatabase(bsn: 110012823)

vader = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19670203, toelichting: '1e kind', registratieDatum: 19670203) {
        op '1967/02/00' te 'Delft' gemeente 0503
        geslacht 'MAN'
        namen {
            voornamen 'Gregory'
            geslachtsnaam 'Porter'
        }
        ouders moeder: oma, vader: opa
        identificatienummers bsn: 410060094, anummer: 1823669458
    }
}
slaOp(vader)

def vader = Persoon.uitDatabase(bsn: 410060094)
Persoon.nieuweGebeurtenissenVoor(vader) {
    verbeteringGeboorteakte(partij: 34401, aanvang: 19870103, toelichting:'Correctie datumGeboorte en wplGeboorte', registratieDatum: 19870103){
        op '1967/02/01' te 'Delft' gemeente 503
    }
}
slaOp(vader)

zoon = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19870103, toelichting: '1e kind', registratieDatum: 19870103) {
        op '1987/01/01' te 'Bedum' gemeente 0005
        geslacht 'MAN'
        namen {
            voornamen 'Louis'
            geslachtsnaam 'Armstong'
        }
        ouders moeder: moeder, vader: vader
        identificatienummers bsn: 410206027, anummer: 2638768018
    }
}
slaOp(zoon)

def zoon = Persoon.uitDatabase(bsn: 410206027)
Persoon.nieuweGebeurtenissenVoor(zoon) {
    verbeteringGeboorteakte(partij: 34401, aanvang: 19870106, toelichting:'Correctie datumGeboorte en wplGeboorte', registratieDatum: 19870106){
        op '1987/01/01' te 'Groningen' gemeente 0014
    }
}
slaOp(zoon)

Given verzoek voor leveringsautorisatie 'Abo autorisatie alleen geboorte historie voor gerelateerde ouder' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand abonnement_autorisatie_ per_soort_abonnement_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo autorisatie alleen geboorte historie voor gerelateerde ouder is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep             | attribuut         | verwachteWaardes                              |
| geboorte          | datum             | 1987-01-01,1969-05-16,1967-02-01,1967-02      |

Then hebben attributen in voorkomens de volgende waardes:
| groep             | nummer            | attribuut          | verwachteWaarde |
| geboorte          | 1                 | woonplaatsnaam     | Groningen       |
| geboorte          | 2                 | woonplaatsnaam     | Reijmerstok     |
