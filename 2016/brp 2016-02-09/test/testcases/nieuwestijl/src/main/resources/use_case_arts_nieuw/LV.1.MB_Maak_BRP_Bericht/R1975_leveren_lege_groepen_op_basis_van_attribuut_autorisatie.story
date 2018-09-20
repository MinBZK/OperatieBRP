Meta:
@sprintnummer       71
@epic               Change 2015003: Gegevensmodel relaties en betrokkenheden (fund.issue)
@auteur             rarij
@jiraIssue          TEAMBRP-2581
@status             Klaar
@regels             VR00120,R1975

Narrative:  Als stelsebeheerder,
            wil ik dat autorisatie ivm attributen ook toegepast wordt op groepen,
            zodat de afnemer geen lege groepen krijgt over wat hij niet mag zien

Scenario:   1. Afnemer zonder autorisatie op attributen binnen groep 'Overlijden' ontvangt een 'MutatieBericht' n.a.v.
            het overlijden van een 'gehuwd' persoon.
            Verwacht resultaat:
            Afnemer ziet wijzigingen in groepen 'Bijhouding' en 'Huwelijk' maar ziet geen groep 'Overlijden'.

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_zonder_attributen_voor_groep_overlijden
Given de database is gereset voor de personen 150018502, 306741817, 972220112
Given de personen 290978981 zijn verwijderd
Given de persoon beschrijvingen:
def vader_Jimmy     =   Persoon.uitDatabase(bsn: 150018502)
def moeder_Jimmy    =   Persoon.uitDatabase(bsn: 306741817)
def partner_Jimmy   =   Persoon.uitDatabase(bsn: 972220112)

Jimmy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Jimmy'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Jimmy, vader: vader_Jimmy
        identificatienummers bsn: 290978981, anummer: 2000603090
    }
        huwelijk() {
          op 20120531 te 'Delft' gemeente 'Delft'
          met partner_Jimmy
        }
        overlijden() {
          op 20150401 te "'s-Gravenhage" gemeente "'s-Gravenhage"
        }
}
slaOp(Jimmy)

When voor persoon 290978981 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Abo zonder attributen voor groep overlijden is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290978981            |

Then heeft het bericht 0 groepen 'overlijden'

Scenario:   2a. Bij een VolledigBericht onder hetzelfde Abonnement, zal ook de groep Overlijden ontbreken in het
            resultaatbericht.

Given de database is gereset voor de personen 150018502, 306741817, 972220112
Given de personen 290978981 zijn verwijderd
Given de persoon beschrijvingen:
def vader_Jimmy     =   Persoon.uitDatabase(bsn: 150018502)
def moeder_Jimmy    =   Persoon.uitDatabase(bsn: 306741817)
def partner_Jimmy   =   Persoon.uitDatabase(bsn: 972220112)

Jimmy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Jimmy'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Jimmy, vader: vader_Jimmy
        identificatienummers bsn: 290978981, anummer: 2000603090
    }
        huwelijk() {
          op 20120531 te 'Delft' gemeente 'Delft'
          met partner_Jimmy
        }
        overlijden() {
          op 20150401 te "'s-Gravenhage" gemeente "'s-Gravenhage"
        }
}
slaOp(Jimmy)

Given verzoek voor leveringsautorisatie 'Abo zonder attributen voor groep overlijden' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand R1975_leveren_lege_groepen_op_basis_van_attribuut_autorisatie_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo zonder attributen voor groep overlijden is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290978981            |

Then heeft het bericht 0 groepen 'overlijden'

Scenario:   2b. Bij het opvragen met GeefDetailsPersoon onder hetzelfde Abonnement, zal ook de groep Overlijden
            ontbreken in het resultaatbericht.

Given de database is gereset voor de personen 150018502, 306741817, 972220112
Given de personen 290978981 zijn verwijderd
Given de persoon beschrijvingen:
def vader_Jimmy     =   Persoon.uitDatabase(bsn: 150018502)
def moeder_Jimmy    =   Persoon.uitDatabase(bsn: 306741817)
def partner_Jimmy   =   Persoon.uitDatabase(bsn: 972220112)

Jimmy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Jimmy'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Jimmy, vader: vader_Jimmy
        identificatienummers bsn: 290978981, anummer: 2000603090
    }
        huwelijk() {
          op 20120531 te 'Delft' gemeente 'Delft'
          met partner_Jimmy
        }
        overlijden() {
          op 20150401 te "'s-Gravenhage" gemeente "'s-Gravenhage"
        }
}
slaOp(Jimmy)

Given verzoek voor leveringsautorisatie 'Abo zonder attributen voor groep overlijden' en partij 'Gemeente Utrecht'
And verzoek van bericht lvg_bvgGeefDetailsPersoon
And testdata uit bestand R1975_leveren_lege_groepen_op_basis_van_attribuut_autorisatie_02.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And hebben in het antwoordbericht de attributen in de groepen de volgende waardes:
| groep                    | attribuut                 | verwachteWaardes        |
| identificatienummers     | burgerservicenummer       | 290978981               |

Then heeft het antwoordbericht 0 groepen 'overlijden'

Scenario:   3. Afnemer heeft een abonnement waarin het attribuut Overlijden.BuitenlandseRegioOverlijden aanwezig is
            (als enig attribuut in die groep).
            Verwacht resultaat:
            Omdat het een binnenlands overlijden betreft zal deze leeg blijven. Deze afnemer zal echter in de
            bovenstaande gevallen wel de groep Overlijden in zijn bericht aantreffen, maar deze zal geen inhoudelijke
            attributen bevatten (alleen de 'materiele-, formele- en verantwoordings aspecten).

Given leveringsautorisatie uit /levering_autorisaties/Abo_met_1_attribuut_voor_groep_overlijden
Given de database is gereset voor de personen 150018502, 306741817, 972220112
Given de personen 290978981 zijn verwijderd
Given de persoon beschrijvingen:
def vader_Jimmy     =   Persoon.uitDatabase(bsn: 150018502)
def moeder_Jimmy    =   Persoon.uitDatabase(bsn: 306741817)
def partner_Jimmy   =   Persoon.uitDatabase(bsn: 972220112)

Jimmy = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Jimmy'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Jimmy, vader: vader_Jimmy
        identificatienummers bsn: 290978981, anummer: 2000603090
    }
        huwelijk() {
          op 20120531 te 'Delft' gemeente 'Delft'
          met partner_Jimmy
        }
        overlijden() {
          op 20150401 te "'s-Gravenhage" gemeente "'s-Gravenhage"
        }
}
slaOp(Jimmy)

When voor persoon 290978981 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Abo met 1 attribuut voor groep overlijden is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes |
| identificatienummers | burgerservicenummer | 290978981        |

Then heeft het bericht 1 groep 'overlijden'

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut           | aanwezig |
| overlijden | 1      | tijdstipRegistratie | ja       |
| overlijden | 1      | actieInhoud         | ja       |
