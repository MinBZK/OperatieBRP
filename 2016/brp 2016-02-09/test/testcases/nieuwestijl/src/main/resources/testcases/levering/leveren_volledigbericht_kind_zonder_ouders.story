Meta:
@sprintnummer       72
@epic               Mutatielevering basis
@auteur             rohar
@jiraIssue          TEAMBRP-1956
@status             Klaar

Narrative:  Als afnemer wil ik dat een VolledigBericht met "vastgesteld geen ouder" correct wordt geleverd

Bij de registratie van een (rechtstreekse) RNI-er en bij inschrijvingen op basis van aktes naar buitenlands recht worden
er heel soms geen Ouders geregistreerd bij een Persoon. Er bestaat echter wel een Familierechtelijke betrekking.
Dit betekent dat de persoon juridisch gezien geen ouders heeft. Dat is dus anders dan de 'onbekende ouder' waar er
juridisch wel een ouder is, maar er geen gegevens over die persoon bekend zijn.

Scenario:  1. kind zonder ouders, afnemer heeft volledige authorisatie.

Meta:
@regels             VR00120a

!-- Op dit moment (sprint 73) is het zo dat het letterlijk toepassen van VR00120 zou betekenen dat de groep Relatie
!-- eigenlijk nooit in het bericht komt. Derhalve wordt dit scenario uitgeschakkeld en zodra dit weer opgepakt wordt,
!-- aangezet.
!-- zie jira issue TEAMBRP-2975

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 800641481 zijn verwijderd
And de persoon beschrijvingen:
vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20100203, toelichting: 'vondeling', registratieDatum: 20100203) {
            op '2010/02/03' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Rémi'
                geslachtsnaam 'Barberin'
            }
        ouders()
        identificatienummers bsn: 800641481, anummer: 1063708634
    }
}
slaOp(vondeling)

When voor persoon 800641481 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                        | nummer | attribuut                    | aanwezig |
| kind                         | 1      | familierechtelijkeBetrekking | ja       |
| familierechtelijkeBetrekking | 1      | relatie                      | ja       |
| relatie                      | 1      | ouder                        | nee      |

Scenario:  2. kind zonder ouders, afnemer heeft geen formele historie en verantwoording

Meta:
@status             Klaar

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_zonder_formele_historie_en_verantwoording
Given de personen 800641481 zijn verwijderd
And de persoon beschrijvingen:
vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20100203, toelichting: 'vondeling', registratieDatum: 20100203) {
            op '2010/02/03' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Rémi'
                geslachtsnaam 'Barberin'
            }
        ouders()
        identificatienummers bsn: 800641481, anummer: 1063708634
    }
}
slaOp(vondeling)

When voor persoon 800641481 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo zonder formele historie en verantwoording is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                        | nummer | attribuut                    | aanwezig |
| kind                         | 1      | familierechtelijkeBetrekking | ja       |
| familierechtelijkeBetrekking | 1      | relatie                      | nee      |
