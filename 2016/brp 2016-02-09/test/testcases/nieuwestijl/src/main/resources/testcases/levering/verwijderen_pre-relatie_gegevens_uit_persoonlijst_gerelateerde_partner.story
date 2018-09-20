Meta:
@sprintnummer       82
@epic               Levering juridische PL
@auteur             rarij
@jiraIssue          TEAMBRP-3502
@status             Klaar
@regels             R1328, R1283
@sleutelwoorden     pre-relatie, partner relatie, datum onbekend

Narrative:
            Als afnemer
            wil ik dat er een filter wordt toegepast op gegevens van gerelateerde personen (partners)
            zodat ik uitsluitend gegevens meegeleverd krijg die niet voor aanvang van de relatie of op dezelfde dag zijn
            beëindigd of vervallen

Scenario:   1. VolledigBericht voor afnemer met focus op mutaties op partner < aanvang huwelijk
                Testdata:
                    - Sean: Wordt geboren en treedt daarna in huwelijk met Donata
                    - Hinata: Wordt geboren, krijgt een naamswijziging en treedt daarna in huwelijk met persoon A
                Verwacht resultaat:
                    - Actuele groep 'samengesteldeNaam' Jansen wordt getoond onder object 'Partner'
                    - Beëindigde groep 'samengesteldeNaam' Hyūga wordt niet getoond doordat:
                      - datumEindeGeldigheid van de beëindigde groep 'samengesteldeNaam' < datumAanvang huwelijk relatie
                    - Vervallen groep 'samengesteldeNaam' Hyūga wordt niet getoond doordat:
                      - Datum-deel van Datum/tijd verval de vervallen groep 'samengesteldeNaam' < datumAanvang huwelijk relatie

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 137423251, 232153103 zijn verwijderd
Given de persoon beschrijvingen:

def man = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19700101, toelichting: '1e kind', registratieDatum: 19700101) {
        op '1970/01/01' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'MAN'
        namen {
            voornamen 'Sean'
            geslachtsnaam 'Penn'
        }
        ouders()
        identificatienummers bsn: 137423251, anummer: 1394039186
    }
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20011010, registratieDatum: 20011010) {
        naarGemeente 'Haarlem',
            straat: 'Dorpstraat', nummer: 15, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(man)

def vrouw = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19700202, toelichting: '1e kind', registratieDatum: 19700202) {
        op '1970/02/02' te 'Delft' gemeente 'Delft'
        geslacht 'VROUW'
        namen {
            voornamen 'Hinata'
            geslachtsnaam 'Hyūga'
        }
        ouders()
        identificatienummers bsn: 232153103, anummer: 2428173458
    }
    naamswijziging(aanvang:19900707, registratieDatum: 19900707) {
            geslachtsnaam(stam:'Hyūga').wordt(stam:'Jansen')
    }
    huwelijk(aanvang:20000505, registratieDatum: 20000505) {
        op 20000505 te 'Delft' gemeente 'Delft'
            met man
    }
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20011010, registratieDatum: 20011010) {
        naarGemeente 'Haarlem',
            straat: 'Dorpstraat', nummer: 15, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(vrouw)

When voor persoon 137423251 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 137423251, 232153103 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes   |
| persoon | geslachtsnaamstam | Penn, Penn, Jansen |


Scenario:   2. VolledigBericht voor afnemer met focus op mutaties op partner = aanvang huwelijk
                Testdata:
                    - Marco: Wordt geboren en treedt daarna in huwelijk met Donata
                    - Donata: Wordt geboren, krijgt een naamswijziging en treedt tegelijkertijd in huwelijk met persoon A
                Verwacht resultaat:
                    - Actuele groep 'samengesteldeNaam' Jansen wordt getoond onder object 'Partner'
                    - Beëindigde groep 'samengesteldeNaam' Hyūga wordt niet getoond doordat:
                      - datumEindeGeldigheid van de beëindigde groep 'samengesteldeNaam' = datumAanvang huwelijk relatie
                    - Vervallen groep 'samengesteldeNaam' Hyūga wordt niet getoond doordat:
                      - Datum-deel van Datum/tijd verval de vervallen groep 'samengesteldeNaam' = datumAanvang huwelijk relatie

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 137423251, 232153103 zijn verwijderd
Given de persoon beschrijvingen:

def man = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19700101, toelichting: '1e kind', registratieDatum: 19700101) {
        op '1970/01/01' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'MAN'
        namen {
            voornamen 'Sean'
            geslachtsnaam 'Penn'
        }
        ouders()
        identificatienummers bsn: 137423251, anummer: 1394039186
    }
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20011010, registratieDatum: 20011010) {
        naarGemeente 'Haarlem',
            straat: 'Dorpstraat', nummer: 15, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(man)

def vrouw = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19700202, toelichting: '1e kind', registratieDatum: 19700202) {
        op '1970/02/02' te 'Delft' gemeente 'Delft'
        geslacht 'VROUW'
        namen {
            voornamen 'Hinata'
            geslachtsnaam 'Hyūga'
        }
        ouders()
        identificatienummers bsn: 232153103, anummer: 2428173458
    }
    naamswijziging(aanvang:20000505, registratieDatum: 20000505) {
            geslachtsnaam([stam:'Hyūga']).wordt([stam:'Jansen'])
    }
    huwelijk(aanvang:20000505, registratieDatum: 20000505) {
        op 200000505 te 'Delft' gemeente 'Delft'
            met man
    }
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20011010, registratieDatum: 20011010) {
        naarGemeente 'Haarlem',
            straat: 'Dorpstraat', nummer: 15, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(vrouw)

When voor persoon 137423251 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 137423251, 232153103 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes   |
| persoon | geslachtsnaamstam | Penn, Penn, Jansen |


Scenario:   3. VolledigBericht voor afnemer met focus op mutaties op partner < aanvang huwelijk met een datum onbekend
                Testdata:
                    - Sean: Wordt geboren en treedt daarna in huwelijk met Donata
                    - Hinata: Wordt geboren, krijgt een naamswijziging en treedt daarna in huwelijk met persoon A
                Verwacht resultaat:
                    - Actuele groep 'samengesteldeNaam' Jansen wordt getoond onder object 'Partner'
                    - Beëindigde groep 'samengesteldeNaam' Hyūga wordt niet getoond doordat:
                      - datumEindeGeldigheid van de beëindigde groep 'samengesteldeNaam' = datumAanvang huwelijk relatie
                    - Vervallen groep 'samengesteldeNaam' Hyūga wordt niet getoond doordat:
                      - Datum-deel van Datum/tijd verval de vervallen groep 'samengesteldeNaam' = datumAanvang huwelijk relatie

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie_waar
Given de personen 137423251, 232153103 zijn verwijderd
Given de persoon beschrijvingen:

def man = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19700101, toelichting: '1e kind', registratieDatum: 19700101) {
        op '1970/01/01' te 'Giessenlanden' gemeente 'Giessenlanden'
        geslacht 'MAN'
        namen {
            voornamen 'Sean'
            geslachtsnaam 'Penn'
        }
        ouders()
        identificatienummers bsn: 137423251, anummer: 1394039186
    }
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20011010, registratieDatum: 20011010) {
        naarGemeente 'Haarlem',
            straat: 'Dorpstraat', nummer: 15, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(man)

def vrouw = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19700202, toelichting: '1e kind', registratieDatum: 19700202) {
        op '1970/02/02' te 'Delft' gemeente 'Delft'
        geslacht 'VROUW'
        namen {
            voornamen 'Hinata'
            geslachtsnaam 'Hyūga'
        }
        ouders()
        identificatienummers bsn: 232153103, anummer: 2428173458
    }
    naamswijziging(aanvang:19900707, registratieDatum: 19900918) {
            geslachtsnaam([stam:'Hyūga']).wordt([stam:'Jansen'])
    }
    huwelijk() {
        op 20000000 te 'Delft' gemeente 'Delft'
            met man
    }
    verhuizing(partij: 'Gemeente Haarlem', aanvang: 20011010, registratieDatum: 20011010) {
        naarGemeente 'Haarlem',
            straat: 'Dorpstraat', nummer: 15, postcode: '2000AA', woonplaats: "Haarlem"
    }
}
slaOp(vrouw)

When voor persoon 137423251 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes     |
| identificatienummers | burgerservicenummer | 137423251, 232153103 |

And heeft het bericht 2 groepen 'samengesteldeNaam'
And hebben de attributen in de groepen de volgende waardes:
| groep   | attribuut         | verwachteWaardes   |
| persoon | geslachtsnaamstam | Penn, Penn, Jansen |
