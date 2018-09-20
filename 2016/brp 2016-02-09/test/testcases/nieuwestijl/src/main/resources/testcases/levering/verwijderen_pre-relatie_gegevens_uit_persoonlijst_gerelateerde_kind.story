Meta:
@sprintnummer       82
@epic               Levering juridische PL
@auteur             rarij
@jiraIssue          TEAMBRP-3502
@status             Klaar
@regels             R1328, R1283
@sleutelwoorden     pre-relatie, kind relatie, datum onbekend

Narrative:
            Als afnemer
            wil ik dat er een filter wordt toegepast op gegevens van gerelateerde personen (kind)
            zodat ik uitsluitend gegevens meegeleverd krijg die niet voor aanvang van de relatie of op dezelfde dag zijn
            beëindigd of vervallen

Scenario:   1. VolledigBericht voor afnemer met focus op mutaties op kind < aanvang betrokkenheid kind
                Testdata:
                    - Sakura: Wordt geboren en adopteerd daarna Sarada
                    - Sarada: Wordt geboren, krijgt een naamswijziging en wordt vervolgens geadopteerd door Sakura
                Verwacht resultaat:
                    - Actuele groep 'samengesteldeNaam' Sadara wordt getoond onder object 'kind'
                    - Beëindigde groep 'samengesteldeNaam' Sadara wordt niet getoond doordat:
                      - datumAanvangGeldigheid van de groep 'samengesteldeNaam' < datumAanvangGeldigheid betrokkenheid kind
                    - Vervallen groep 'samengesteldeNaam' Sadara wordt niet getoond doordat:
                      - Datum/tijd verval van de groep 'samengesteldeNaam' < datumAanvangGeldigheid betrokkenheid kind

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de persoon beschrijvingen:

def moeder = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19900909, registratieDatum: 19900909, toelichting: '1e kind') {
        op '1990/09/09' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'VROUW'
        namen {
            voornamen 'Sakura'
            geslachtsnaam 'Uzumaki'
        }
        ouders()
        identificatienummers bsn: 559754115, anummer: 5472184594
    }
}
slaOp(moeder)

def vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20101212, registratieDatum: 20101212, toelichting: 'vondeling') {
            op '2010/12/12' te 'Delft' gemeente 'Delft'
            geslacht 'VROUW'
            namen {
                voornamen 'Sarada'
                geslachtsnaam 'Barberin'
            }
        ouders (moeder: null)
        identificatienummers bsn: 935420149, anummer: 9478636818
    }
    naamswijziging(aanvang: 20110101, registratieDatum: 20110101) {
            geslachtsnaam([stam:'Barberin']).wordt([stam:'Uchiha'])
    }
    geadopteerd(aanvang: 20140102, registratieDatum: 20140102) {
        ouders(moeder: moeder)
    }
}
slaOp(vondeling)

When voor persoon 559754115 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 559754115, 935420149 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes         |
| persoon | geslachtsnaamstam | Uzumaki, Uzumaki, Uchiha |

Scenario:   2. VolledigBericht voor afnemer met focus op mutaties op kind < aanvang betrokkenheid kind
                Testdata:
                    - Sakura: Wordt geboren en adopteerd daarna Sarada
                    - Sarada: Wordt geboren, krijgt een naamswijziging en wordt vervolgens geadopteerd door Sakura
                Verwacht resultaat:
                    - Actuele groep 'samengesteldeNaam' Sadara wordt getoond onder object 'kind'
                    - Beëindigde groep 'samengesteldeNaam' Sadara wordt niet getoond doordat:
                      - datumAanvangGeldigheid van de groep 'samengesteldeNaam' = datumAanvangGeldigheid betrokkenheid kind
                    - Vervallen groep 'samengesteldeNaam' Sadara wordt niet getoond doordat:
                      - Datum/tijd verval van de groep 'samengesteldeNaam' = datumAanvangGeldigheid betrokkenheid kind

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 559754115, 935420149 zijn verwijderd
Given de persoon beschrijvingen:

def moeder = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19900909, registratieDatum: 19900909, toelichting: '1e kind') {
        op '1990/09/09' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'VROUW'
        namen {
            voornamen 'Sakura'
            geslachtsnaam 'Uzumaki'
        }
        ouders()
        identificatienummers bsn: 559754115, anummer: 5472184594
    }
}
slaOp(moeder)

def vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20101212, registratieDatum:20101212, toelichting: 'vondeling') {
            op '2010/12/12' te 'Delft' gemeente 'Delft'
            geslacht 'VROUW'
            namen {
                voornamen 'Sarada'
                geslachtsnaam 'Barberin'
            }
        ouders (moeder: null)
        identificatienummers bsn: 935420149, anummer: 9478636818
    }
    naamswijziging(aanvang:20110918, registratieDatum:20110918) {
            geslachtsnaam([stam:'Barberin']).wordt([stam:'Uchiha'])
    }
    geadopteerd(aanvang: 20110918, registratieDatum:20110918) {
        ouders(moeder: moeder)
    }
}
slaOp(vondeling)

When voor persoon 559754115 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 559754115, 935420149 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes         |
| persoon | geslachtsnaamstam | Uzumaki, Uzumaki, Uchiha |

Scenario:   3. VolledigBericht voor afnemer met focus op mutaties op kind < aanvang betrokkenheid kind met een datum onbekend
                Testdata:
                    - Sakura: Wordt geboren en adopteerd daarna Sarada
                    - Sarada: Wordt geboren, krijgt een naamswijziging en wordt vervolgens geadopteerd door Sakura
                Verwacht resultaat:
                Hoewel datum aanvang adoptie onbekend is, dient het systeem in staat te zijn om te kunnen bepalen, dat
                deze datum aanvang, na datum/tijd registratie van de naamswijziging ligt. Derhalve liggen in dit geval
                zowel de tsVerval als de DEG voor de DAG adoptie. Derhalve wordt het volgende verwacht:
                    - Actuele groep 'samengesteldeNaam' Sadara wordt getoond onder object 'kind'
                    - Beëindigde groep 'samengesteldeNaam' Sadara wordt niet getoond doordat:
                      - datumAanvangGeldigheid van de groep 'samengesteldeNaam' = datumAanvangGeldigheid betrokkenheid kind
                    - Vervallen groep 'samengesteldeNaam' Sadara wordt niet getoond doordat:
                      - Datum/tijd verval van de groep 'samengesteldeNaam' = datumAanvangGeldigheid betrokkenheid kind

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 559754115, 935420149 zijn verwijderd
Given de persoon beschrijvingen:

def moeder = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19900909, registratieDatum: 19900909, toelichting: '1e kind') {
        op '1990/09/09' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'VROUW'
        namen {
            voornamen 'Sakura'
            geslachtsnaam 'Uzumaki'
        }
        ouders()
        identificatienummers bsn: 559754115, anummer: 5472184594
    }
}
slaOp(moeder)

def vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20100202, toelichting: 'vondeling') {
            op '2010/02/02' te 'Delft' gemeente 'Delft'
            geslacht 'VROUW'
            namen {
                voornamen 'Sarada'
                geslachtsnaam 'Barberin'
            }
        ouders (moeder: null)
        identificatienummers bsn: 935420149, anummer: 9478636818
    }
    naamswijziging(aanvang:20100303, registratieDatum:20100303) {
        geslachtsnaam([stam:'Barberin']).wordt([stam:'Uchiha'])
    }
    geadopteerd(aanvang:20100900, registratieDatum:20100900) {
        ouders(moeder: moeder)
    }
}
slaOp(vondeling)

When voor persoon 559754115 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 559754115, 935420149 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes         |
| persoon | geslachtsnaamstam | Uzumaki, Uzumaki, Uchiha |

Scenario:   4. VolledigBericht voor afnemer met focus op mutaties op kind < aanvang betrokkenheid kind met een datum onbekend
                Testdata:
                    - Sakura: Wordt geboren en adopteerd daarna Sarada
                    - Sarada: Wordt geboren, krijgt een naamswijziging en wordt vervolgens geadopteerd door Sakura
                Verwacht resultaat:
                Hoewel datum aanvang adoptie onbekend is, dient het systeem in staat te zijn om te kunnen bepalen, dat
                deze datum aanvang, na datum/tijd registratie van de naamswijziging ligt. Derhalve is het niet mogelijk
                te bepalen of tsVerval als en DEG voor de DAG adoptie liggen. Dus wordt het volgende verwacht:
                    - Actuele groep 'samengesteldeNaam' Sadara wordt getoond onder object 'kind'
                    - Beëindigde groep 'samengesteldeNaam' Sadara wordt getoond
                    - Vervallen groep 'samengesteldeNaam' Sadara wordt getoond

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 559754115, 935420149 zijn verwijderd
Given de persoon beschrijvingen:

def moeder = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19900909, registratieDatum: 19900909, toelichting: '1e kind') {
        op '1990/09/09' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'VROUW'
        namen {
            voornamen 'Sakura'
            geslachtsnaam 'Uzumaki'
        }
        ouders()
        identificatienummers bsn: 559754115, anummer: 5472184594
    }
}
slaOp(moeder)

def vondeling = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 20100202, toelichting: 'vondeling') {
            op '2010/02/02' te 'Delft' gemeente 'Delft'
            geslacht 'VROUW'
            namen {
                voornamen 'Sarada'
                geslachtsnaam 'Barberin'
            }
        ouders (moeder: null)
        identificatienummers bsn: 935420149, anummer: 9478636818
    }
    naamswijziging(aanvang:20100303, registratieDatum:20100918) {
        geslachtsnaam([stam:'Barberin']).wordt([stam:'Uchiha'])
    }
    geadopteerd(aanvang:20100000, registratieDatum:20100000) {
        ouders(moeder: moeder)
    }
}
slaOp(vondeling)

When voor persoon 559754115 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 559754115, 935420149 |

And heeft het bericht 4 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes                             |
| persoon | geslachtsnaamstam | Uzumaki, Uzumaki, Uchiha, Barberin, Barberin |
