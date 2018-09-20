Meta:
@sprintnummer           67
@epic                   Change yyyynn: CorLev - Element tabel
@auteur                 dihoe
@jiraIssue              TEAMBRP-2434
@status                 Klaar
@regels                 VR00052,VR00081
@sleutelwoorden         synchronisatie

Narrative:
    stelselbeheerder
    wil ik dat de gegevensautorisatie blijft werken als de Abonnementen de groepen en attributen aanwijzen via de Elementtabel.
Deze testscenario test acceptatiecriteria 1a en 1b

Scenario: 1. Abo met formele historie = ja voor groep samengesteldeNaam - alle voorkomens worden getoond in een volledigbericht

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/abo_formele_hist_ja_voor_groep_samengesteldenaam
Given de persoon beschrijvingen:
def Laar    = uitDatabase bsn: 306867837
def Verheul = uitDatabase bsn: 306741817

testpersoon = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: Verheul, vader: Laar
        identificatienummers bsn: 410139464, anummer: 2489643038
    }
}

slaOp(testpersoon)

testpersoon = Persoon.uitDatabase(bsn: 410139464)

Persoon.nieuweGebeurtenissenVoor(testpersoon) {
    naamswijziging(aanvang:20150101, registratieDatum: 20150101) {
            geslachtsnaam(stam: 'Smith').wordt(stam: 'Bond')
    }
}

slaOp(testpersoon)

Given verzoek voor leveringsautorisatie 'Abo formele hist ja voor groep samengesteldenaam' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand samengestelde_naam_met_voorkomens_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Abo formele hist ja voor groep samengesteldenaam is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep             | attribuut              | verwachteWaardes                                 |
| samengesteldeNaam | geslachtsnaamstam      | Bond,Smith                                       |


Scenario: 2. Abo met formele historie = nee voor groep samengesteldeNaam - 1 voorkomen wordt getoond van testpersoon in een volledigbericht

Given leveringsautorisatie uit /levering_autorisaties/abo_formele_hist_nee_voor_groep_samengesteldenaam
Given verzoek voor leveringsautorisatie 'Abo formele hist nee voor groep samengesteldenaam' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand samengestelde_naam_met_voorkomens_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Abo formele hist nee voor groep samengesteldenaam is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep             | attribuut         | verwachteWaardes                                  |
| samengesteldeNaam | geslachtsnaamstam | Bond                                             |

