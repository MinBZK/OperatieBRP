Meta:
@sprintnummer       72
@epic               Mutatielevering basis
@auteur             rarij
@jiraIssue          TEAMBRP-1955
@status             Klaar

Narrative:  Als afnemer
            wil ik dat een correctie van de aanvang ouderschap correct wordt geleverd
            zodat een afnemer met volledige autorisatie, een mutatiebericht ontvangt waarin het ouderdeel is bijgewerkt.

            In essentie gaat het hier om het corrigeren van de ouderschap waarbij de oude groep ouderschap komt te vervallen
            en de nieuwe groep ouderschap wordt gecreëerd met als datumAanvangGeldigheid, dezelfde datum als de aanvang
            van de correctie. Het derde voorkomen van het bsn van de ouder is van de wijziging van de ouder zelf.

Scenario:   1. Afnemer met volledige autorisatie ontvangt een mutatiebericht n.a.v. een correctie in aanvangsdatum van
            het ouderschap.
            Verwacht resultaat:
            - Een vervallen groep ouderschap
            - Een toegevoegde groep ouderschap

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de persoon beschrijvingen:
def vader_John     =   Persoon.uitDatabase(bsn: 150018502)
def moeder_John    =   Persoon.uitDatabase(bsn: 306741817)

John = uitGebeurtenissen {
    geboorte(partij: 63701, aanvang: 19800201, toelichting: '1e kind', registratieDatum: 19800202) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'John'
                geslachtsnaam 'Hendricks'
            }
        ouders moeder: moeder_John
        erkendDoor vader_John
        identificatienummers bsn: 290045678, anummer: 2000600189
    }
}
slaOp(John)

def John = Persoon.uitDatabase(bsn: 290045678)

Persoon.nieuweGebeurtenissenVoor(John) {

    erkend(partij: 63701, aanvang: 19800101, toelichting: 'Correct n.a.v. een erkening ongeboren vrucht', registratieDatum: 19810101) {
        door vader_John
    }
}
slaOp (John)

When voor persoon 290045678 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290045678, 150018502, 150018502, 150018502 |

And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep         | nummer    | verwerkingssoort  |
| ouderschap    | 1         | Verval            |
| ouderschap    | 2         | Toevoeging        |

And hebben attributen in voorkomens de volgende waardes:
| groep      | nummer | attribuut              | verwachteWaarde |
| ouderschap | 1      | datumAanvangGeldigheid | 1980-02-01      |
| ouderschap | 2      | datumAanvangGeldigheid | 1980-01-01      |


Scenario:   2. Afnemer zonder autorisatie op formele historie en verantwoording otvangt een mutatiebericht n.a.v. een
            correctie in aanvangsdatum van het ouderschap.
            Verwacht resultaat:
            - Een vervallen groep ouderschap met alleen attribuut 'datumAanvangGeldigheid'
            - Een toegevoegde groep ouderschap met alleen attribuut 'datumAanvangGeldigheid'

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_zonder_formele_historie_en_verantwoording
Given de persoon beschrijvingen:
def vader_John     =   Persoon.uitDatabase(persoon: 26003)
def moeder_John    =   Persoon.uitDatabase(bsn: 306741817)

John = uitGebeurtenissen {
    geboorte(partij: 63701, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 19800203) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'John'
                geslachtsnaam 'Hendricks'
            }
        ouders moeder: moeder_John, vader: vader_John
        identificatienummers bsn: 290045678, anummer: 2000600189
    }
}
slaOp(John)

def John = Persoon.uitDatabase(bsn: 290045678)

Persoon.nieuweGebeurtenissenVoor(John) {
    erkend(partij: 63701, aanvang: 19800201, toelichting: 'Correct n.a.v. een erkening ongeboren vrucht', registratieDatum: 19800204) {
        door vader_John
    }
}
slaOp (John)

When voor persoon 290045678 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Abo zonder formele historie en verantwoording is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290045678            |

And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep         | nummer    | verwerkingssoort  |
| ouderschap    | 1         | Verval            |
| ouderschap    | 2         | Toevoeging        |

And hebben attributen in voorkomens de volgende waardes:
| groep      | nummer | attribuut              | verwachteWaarde |
| ouderschap | 1      | datumAanvangGeldigheid | 1980-02-03      |
| ouderschap | 2      | datumAanvangGeldigheid | 1980-02-01      |
