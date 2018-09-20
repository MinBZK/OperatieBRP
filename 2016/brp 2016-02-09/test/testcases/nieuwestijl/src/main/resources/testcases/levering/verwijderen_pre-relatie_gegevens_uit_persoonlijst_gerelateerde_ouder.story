Meta:
@sprintnummer       82
@epic               Levering juridische PL
@auteur             rarij
@jiraIssue          TEAMBRP-3502
@status             Klaar
@regels             R1328, R1283
@sleutelwoorden     pre-relatie, ouder-relatie, datum onbekend

Narrative:
            Als afnemer
            wil ik dat er een filter wordt toegepast op gegevens van gerelateerde personen (ouder)
            zodat ik uitsluitend gegevens meegeleverd krijg die niet voor aanvang van de relatie of op dezelfde dag zijn
            beëindigd of vervallen

Scenario:   1. VolledigBericht voor afnemer met focus op mutaties op kind < aanvang betrokkenheid ouder
                Testdata:
                    - Sakura: Wordt geboren, krijgt een naamswijziging en adopteerd daarna Sarada
                    - Sarada: Wordt geboren en wordt vervolgens geadopteerd door Sakura
                Verwacht resultaat:
                    - Actuele groep 'samengesteldeNaam' Sakura wordt getoond onder object 'ouder'
                    - Beëindigde groep 'samengesteldeNaam' Sakura wordt niet getoond doordat:
                      - datumEindeGeldigheid van de groep 'samengesteldeNaam' < datumAanvangGeldigheid betrokkenheid ouder
                    - Vervallen groep 'samengesteldeNaam' Sakura wordt niet getoond doordat:
                      - Datum/tijd verval van de groep 'samengesteldeNaam' < datumAanvangGeldigheid betrokkenheid ouder

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 245057055, 311565499 zijn verwijderd
Given de persoon beschrijvingen:

def moeder = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19900909, registratieDatum: 19900909, toelichting: '1e kind') {
        op '1990/09/09' te 'Delft' gemeente 'Delft'
        geslacht 'VROUW'
        namen {
            voornamen 'Sakura'
            geslachtsnaam 'Uzumaki'
        }
        ouders()
        identificatienummers bsn: 245057055, anummer: 2073247634
    }
    naamswijziging(aanvang: 20110101, registratieDatum: 20110101) {
        geslachtsnaam([stam:'Uzumaki']).wordt([stam:'Uchiha'])
    }
}
slaOp(moeder)

def vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20121212, registratieDatum: 20121212, toelichting: 'vondeling') {
            op '2012/12/12' te 'Giessenlanden' gemeente 'Giessenlanden'
            geslacht 'VROUW'
            namen {
                voornamen 'Sarada'
                geslachtsnaam 'Barberin'
            }
        ouders (moeder: null)
        identificatienummers bsn: 311565499, anummer: 3406149650
    }
    geadopteerd(aanvang: 20140101, registratieDatum: 20140101) {
            ouders(moeder: moeder)
    }
}
slaOp(vondeling)

When voor persoon 311565499 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 311565499, 245057055 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes           |
| persoon | geslachtsnaamstam | Barberin, Barberin, Uchiha |

Scenario:   2. VolledigBericht voor afnemer met focus op mutaties op kind = aanvang betrokkenheid ouder
                Testdata:
                    - Sakura: Wordt geboren, krijgt een naamswijziging en adopteerd daarna Sarada
                    - Sarada: Wordt geboren en wordt vervolgens geadopteerd door Sakura
                Verwacht resultaat:
                    - Actuele groep 'samengesteldeNaam' Sakura wordt getoond onder object 'ouder'
                    - Beëindigde groep 'samengesteldeNaam' Sakura wordt niet getoond doordat:
                      - datumEindeGeldigheid van de groep 'samengesteldeNaam' = datumAanvangGeldigheid betrokkenheid ouder
                    - Vervallen groep 'samengesteldeNaam' Sakura wordt niet getoond doordat:
                      - Datum/tijd verval van de groep 'samengesteldeNaam' = datumAanvangGeldigheid betrokkenheid ouder

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 245057055, 311565499 zijn verwijderd
Given de persoon beschrijvingen:

def moeder = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19900909, registratieDatum: 19900909, toelichting: '1e kind') {
        op '1990/09/09' te 'Delft' gemeente 'Delft'
        geslacht 'VROUW'
        namen {
            voornamen 'Sakura'
            geslachtsnaam 'Uzumaki'
        }
        ouders()
        identificatienummers bsn: 245057055, anummer: 2073247634
    }
    naamswijziging(aanvang: 20140918, registratieDatum: 20140918) {
        geslachtsnaam([stam:'Uzumaki']).wordt([stam:'Uchiha'])
    }
}
slaOp(moeder)

def vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20121212, registratieDatum: 20121212, toelichting: 'vondeling') {
            op '2012/12/12' te 'Giessenlanden' gemeente 'Giessenlanden'
            geslacht 'VROUW'
            namen {
                voornamen 'Sarada'
                geslachtsnaam 'Barberin'
            }
        ouders (moeder: null)
        identificatienummers bsn: 311565499, anummer: 3406149650
    }
    geadopteerd(aanvang: 20140918, registratieDatum: 20140918) {
            ouders(moeder: moeder)
    }
}
slaOp(vondeling)

When voor persoon 311565499 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 311565499, 245057055 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes           |
| persoon | geslachtsnaamstam | Barberin, Barberin, Uchiha |

Scenario:   3. VolledigBericht voor afnemer met focus op mutaties op ouder < aanvang betrokkenheid ouder met een datum onbekend
                Testdata:
                    - Sakura: Wordt geboren, krijgt een naamswijziging en adopteerd daarna Sarada
                    - Sarada: Wordt geboren en wordt vervolgens geadopteerd door Sakura
                Verwacht resultaat:
                    - Actuele groep 'samengesteldeNaam' Sakura wordt getoond onder object 'ouder'
                    - Beëindigde groep 'samengesteldeNaam' Sakura wordt niet getoond doordat:
                      - datumAanvangGeldigheid van de groep 'samengesteldeNaam' < datumAanvangGeldigheid betrokkenheid ouder
                    - Vervallen groep 'samengesteldeNaam' Sakura wordt niet getoond doordat:
                      - Datum/tijd verval van de groep 'samengesteldeNaam' < datumAanvangGeldigheid betrokkenheid ouder

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 245057055, 311565499 zijn verwijderd
Given de persoon beschrijvingen:

def moeder = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19900909, registratieDatum: 19900909, toelichting: '1e kind') {
        op '1990/09/09' te 'Delft' gemeente 'Delft'
        geslacht 'VROUW'
        namen {
            voornamen 'Sakura'
            geslachtsnaam 'Uzumaki'
        }
        ouders()
        identificatienummers bsn: 245057055, anummer: 2073247634
    }
    naamswijziging(aanvang:20110101, registratieDatum: 20110101) {
        geslachtsnaam([stam:'Uzumaki']).wordt([stam:'Uchiha'])
    }
}
slaOp(moeder)

def vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20121212, registratieDatum: 20121212, toelichting: 'vondeling') {
            op '2012/12/12' te 'Giessenlanden' gemeente 'Giessenlanden'
            geslacht 'VROUW'
            namen {
                voornamen 'Sarada'
                geslachtsnaam 'Barberin'
            }
        ouders (moeder: null)
        identificatienummers bsn: 311565499, anummer: 3406149650
    }
    geadopteerd(aanvang: 20140000, registratieDatum: 20140000) {
            ouders(moeder: moeder)
    }
}
slaOp(vondeling)

When voor persoon 311565499 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 311565499, 245057055 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes           |
| persoon | geslachtsnaamstam | Barberin, Barberin, Uchiha |
