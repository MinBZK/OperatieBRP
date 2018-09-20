Meta:
@sprintnummer       71
@epic               Change 2015003: Gegevensmodel relaties en betrokkenheden (fund.issue)
@auteur             rarij
@jiraIssue          TEAMBRP-2582
@status             Klaar


Narrative:  Als stelsebeheerder,
            wil ik dat autorisatie ivm attributen ook toegepast wordt op objecten,
            zodat de afnemer geen lege groepen krijgt over wat hij niet mag zien

Scenario:   1. Indien een object over een of meerdere geautoriseerde attributen beschikt, die lager in de hirarchie zitten,
            dan mag dit object NIET gefiltert worden.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_met_1_attribuut_voor_GerelateerdeOuder.Persoon.Identificatienummers
Given de persoon beschrijvingen:
def vader_Mose     =    Persoon.uitDatabase(persoon: 26003)
def moeder_Mose    =    Persoon.uitDatabase(bsn: 306741817)

Mose = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 19800203) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Mose'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Mose, vader: vader_Mose
        identificatienummers bsn: 290653332, anummer: 590360073
    }
}
slaOp(Mose)

When voor persoon 290653332 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo met 1 attribuut voor GerelateerdeOuder.Persoon.Identificatienummers is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290653332, 306741817 |

And heeft het bericht 3 groepen 'persoon'
And heeft het bericht geen kinderen voor xpath /brp:lvg_synVerwerkPersoon/brp:synchronisatie/brp:bijgehoudenPersonen/brp:persoon/brp:betrokkenheden/brp:kind/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder[2]/brp:persoon


Scenario:   2. Indien een object NIET over een of meerdere geautoriseerde attributen beschikt, die lager in de hirarchie zitten,
               dan mag dit object WEL gefiltert worden.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_met_1_attribuut_voor_GerelateerdeOuder.OuderlijkGezag
Given de persoon beschrijvingen:
def vader_Mose     =    Persoon.uitDatabase(bsn: 150018502)
def moeder_Mose    =    Persoon.uitDatabase(bsn: 306741817)

Mose = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 19800203) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Mose'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Mose, vader: vader_Mose
        identificatienummers bsn: 290653332, anummer: 590360073
    }
}
slaOp(Mose)


Given verzoek voor leveringsautorisatie 'Abo met 1 attribuut voor GerelateerdeOuder.OuderlijkGezag' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 290653332 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo met 1 attribuut voor GerelateerdeOuder.OuderlijkGezag is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 290653332            |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut | aanwezig |
| ouder | 1      | persoon   | nee      |
| ouder | 2      | persoon   | nee      |



Scenario:   3. Indien de ouders ontbreken, maar er bestaat een geautoriseerd attribuut,
                dan moet er een familierechtelijke betrekking zijn, maar geen lege
                betrokkenheden container.
Meta:
@regels             VR00121a

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_met_1_attribuut_voor_GerelateerdeOuder.OuderlijkGezag
Given de personen 290653332 zijn verwijderd
Given de persoon beschrijvingen:
Mose = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind', registratieDatum: 19800203) {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Mose'
                geslachtsnaam 'Scott'
            }
        ouders()
        identificatienummers bsn: 290653332, anummer: 590360073
    }
}
slaOp(Mose)

When voor persoon 290653332 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo met 1 attribuut voor GerelateerdeOuder.OuderlijkGezag is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                        | nummer | attribuut      | aanwezig |
| familierechtelijkeBetrekking | 1      | relatie        | nee      |
| familierechtelijkeBetrekking | 1      | betrokkenheden | nee      |
| relatie                      | 1      | actieInhoud    | nee      |
