Meta:
@sprintnummer       92
@epic               Maak BRP Bericht
@auteur             rarij
@jiraIssue          TEAMBRP-2582
@status             Klaar
@regels             VR00121, R1976

Narrative:  Als stelsebeheerder,
            wil ik dat autorisatie ivm attributen ook toegepast wordt op objecten,
            zodat de afnemer geen lege groepen krijgt over wat hij niet mag zien

Scenario:   1. Indien een object over een of meerdere geautoriseerde attributen beschikt, die lager in de hirarchie zitten,
            dan mag dit object NIET gefiltert worden.
            Logisch testgeval: R1976_L01
            Verwacht resultaat: Groep wordt geleverd incl attributen

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_met_1_attribuut_voor_GerelateerdeOuder.Persoon.Identificatienummers

Given de personen 436257865, 634915113, 743274313, 205416937, 906868233, 877968585, 555163209, 786228489 zijn verwijderd
Given de standaardpersoon UC_Huisman met bsn 436257865 en anr 4121946386 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
def vader_Mose     =    Persoon.uitDatabase(bsn: 436257865)
def moeder_Mose    =    Persoon.uitDatabase(bsn: 877968585)

Mose = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20160203, toelichting: '1e kind') {
            op '2016/02/03' te 'Utrecht' gemeente 'Utrecht'
            geslacht 'MAN'
            namen {
                voornamen 'Mose'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Mose, vader: vader_Mose
        identificatienummers bsn: 786228489, anummer: 4756739602
    }
}
slaOp(Mose)

When voor persoon 786228489 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo met 1 attribuut voor GerelateerdeOuder.Persoon.Identificatienummers is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 786228489, 436257865, 877968585 |

And heeft het bericht 3 groepen 'persoon'


Scenario:   2. Indien een object NIET over een of meerdere geautoriseerde attributen beschikt, die lager in de hirarchie zitten,
               dan mag dit object WEL gefiltert worden.
               Logisch testgeval: R1976_L02
               Verwacht resultaat: Object (ouder) is niet aanwezig in bericht

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Abo_met_1_attribuut_voor_GerelateerdeOuder.OuderlijkGezag

Given de personen 266670969, 634915113, 743274313, 205416937, 906868233, 877968585, 555163209, 769779785 zijn verwijderd
Given de standaardpersoon UC_Huisman met bsn 266670969 en anr 7939257106 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
def vader_Mose     =    Persoon.uitDatabase(bsn: 634915113)
def moeder_Mose    =    Persoon.uitDatabase(bsn: 743274313)

Mose = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Mose'
                geslachtsnaam 'Scott'
            }
        ouders moeder: moeder_Mose, vader: vader_Mose
        identificatienummers bsn: 769779785, anummer: 6074303762
    }
}
slaOp(Mose)

Given verzoek voor leveringsautorisatie 'Abo met 1 attribuut voor GerelateerdeOuder.OuderlijkGezag' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1976_afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 769779785 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo met 1 attribuut voor GerelateerdeOuder.OuderlijkGezag is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 769779785            |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut | aanwezig |
| ouder | 1      | persoon   | nee      |
| ouder | 2      | persoon   | nee      |



Scenario:   3. Indien de ouders ontbreken, maar er bestaat een geautoriseerd attribuut,
                dan moet er een familierechtelijke betrekking zijn, maar geen lege
                betrokkenheden container.

Given de personen 125494567 zijn verwijderd
Given de persoon beschrijvingen:
Mose = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19800203, toelichting: '1e kind') {
            op '1980/02/01' te 'Delft' gemeente 'Delft'
            geslacht 'MAN'
            namen {
                voornamen 'Mose'
                geslachtsnaam 'Scott'
            }
        ouders()
        identificatienummers bsn: 125494567, anummer: 6231207698
    }
}
slaOp(Mose)

When voor persoon 125494567 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo met 1 attribuut voor GerelateerdeOuder.OuderlijkGezag is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                        | nummer | attribuut      | aanwezig |
| familierechtelijkeBetrekking | 1      | relatie        | nee      |
| familierechtelijkeBetrekking | 1      | betrokkenheden | nee      |
| relatie                      | 1      | actieInhoud    | nee      |
