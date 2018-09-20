Meta:
@sprintnummer       72
@epic               Mutatielevering basis
@auteur             rarij
@jiraIssue          TEAMBRP-1954
@status             Klaar

Narrative:  Als afnemer,
            wil ik dat een volledigbericht met een onbekende ouder correct goed wordr verwerkt,
            zodat deze correct geleverd wordt.

            Deze situatie kan optreden tijdens het inschrijven van een vondeling en waarvan dus
            de ouders onbekend zijn.

Scenario:   1. Afnemer met autorisatie op historie en verantwoording ontvangt een volledigbericht n.a.v. het registreren
            van een kind met onbekende ouders (vondeling).
            Verwacht resultaat:
            - 1 voorkomen van groep ouder
            - 0 voorkomen van groep persoon onder groep ouder
            - 1 voorkomen van groep ouderschap onder groep ouder met historie en verantwoording informatie (tsReg, dAG,
              aI, iAO)

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 290978981 zijn verwijderd
Given de persoon beschrijvingen:

Jimmy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 19800203) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Jimmy'
                geslachtsnaam 'Scott'
            }
        ouders (moeder: null)
        identificatienummers bsn: 290978981, anummer: 2000603090
    }
}
slaOp(Jimmy)

When voor persoon 290978981 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut              | verwachteWaardes |
| identificatienummers | burgerservicenummer    | 290978981        |
| ouderschap           | datumAanvangGeldigheid | 1980-02-03       |

And heeft het bericht 1 groepen 'ouder'

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut                  | aanwezig |
| ouder      | 1      | persoon                    | nee      |
| ouderschap | 1      | tijdstipRegistratie        | ja       |
| ouderschap | 1      | actieInhoud                | ja       |
| ouderschap | 1      | indicatieAdresgevendeOuder | nee      |


Scenario:   2. Afnemer zonder autorisatie op historie en verantwoording ontvangt een volledigbericht n.a.v. het
            registreren van een kind met onbekende ouders (vondeling).
            Verwacht resultaat:
            - 1 voorkomen van groep ouder
            - 0 voorkomen van groep persoon onder groep ouder
            - 1 voorkomen van groep ouderschap onder groep ouder met alleen DAG

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_zonder_formele_historie_en_verantwoording
Given de personen 290978981 zijn verwijderd
Given de persoon beschrijvingen:

Jimmy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 19800203) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Jimmy'
                geslachtsnaam 'Scott'
            }
        ouders (moeder: null)
        identificatienummers bsn: 290978981, anummer: 2000603090
    }
}
slaOp(Jimmy)

When voor persoon 290978981 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo zonder formele historie en verantwoording is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut              | verwachteWaardes |
| identificatienummers | burgerservicenummer    | 290978981        |
| ouderschap           | datumAanvangGeldigheid | 1980-02-03       |

And heeft het bericht 1 groepen 'ouder'

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut                  | aanwezig |
| ouder      | 1      | persoon                    | nee      |
| ouderschap | 1      | tijdstipRegistratie        | nee      |
| ouderschap | 1      | actieInhoud                | nee      |
| ouderschap | 1      | indicatieAdresgevendeOuder | nee      |
