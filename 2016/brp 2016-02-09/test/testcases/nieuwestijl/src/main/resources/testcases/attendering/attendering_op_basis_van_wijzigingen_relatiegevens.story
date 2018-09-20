Meta:
@sprintnummer               75
@epic                       Afhankelijkheden migratie
@auteur                     dihoe
@jiraIssue                  TEAMBRP-2989
@status                     Klaar

Narrative:  Als afnemer
            wil ik attenderingen kunnen krijgen op basis van wijzigingen in relatiegegevens

Scenario: 1. Johnny en Anita gaan een voltrekking huwelijk in Nederland aan, check expressietaal, bericht wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/attendering_obv_wijziging_in_relatiegegevens_huwelijk
Given de personen 556645959 zijn verwijderd
Given de database is gereset voor de personen 126477735, 64258099
Given de persoon beschrijvingen:
def moederJohnny   = uitDatabase bsn: 64258099
def vaderJohnny    = uitDatabase bsn: 126477735

Johnny = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19951225, toelichting: '1e kind', registratieDatum: 19951225) {
        op '1995/12/25' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Johnny', 'James'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederJohnny, vader: vaderJohnny
        identificatienummers bsn: 556645959, anummer: 5607075602
    }
}
slaOp(Johnny)
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 754209878 zijn verwijderd
Given de persoon beschrijvingen:
def moederAnita = uitDatabase bsn: 306741817
def vaderAnita  = uitDatabase bsn: 306867837
def Johnny      = uitDatabase bsn: 556645959

Anita = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19960108, toelichting: '1e kind', registratieDatum: 19960108) {
        op '1996/01/08' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Anita', 'Karen'
            geslachtsnaam 'Corner'

        }
        ouders moeder: moederAnita, vader: vaderAnita
        identificatienummers bsn: 754209878, anummer: 6248016146
    }
    huwelijk(aanvang: 20150501, registratieDatum: 20150501) {
          op 20150501 te 'Delft' gemeente 'Delft'
          met Johnny
    }
}
slaOp(Anita)
When voor persoon 754209878 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie attendering obv wijziging in relatiegegevens - huwelijk is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep   | nummer | attribuut    | verwachteWaarde |
| relatie | 2      | datumAanvang | 2015-05-01      |

Scenario: 2. Geslachtsnaam wordt gewijzigd van Kindje van Johnny en Anita, check expressietaal, bericht wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/attendering_obv_wijziging_in_relatiegegevens_geslachtsnaam
Given de personen 564568041 zijn verwijderd
Given de persoon beschrijvingen:
def moederKindje = uitDatabase bsn: 754209878
def vaderKindje  = uitDatabase bsn: 556645959

Kindje = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 20150601, toelichting: '1e kind', registratieDatum: 20150601) {
        op '2015/05/30' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Jennifer', 'Anita'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederKindje, vader: vaderKindje
        identificatienummers bsn: 564568041, anummer: 3549026834
    }
    naamswijziging(aanvang:20150601, registratieDatum: 20150601) {
            geslachtsnaam(stam:'Jansen').wordt(stam:'Vries', voorvoegsel:'van')
    }
}
slaOp(Kindje)
When voor persoon 564568041 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie attendering obv wijziging in relatiegegevens - geslachtsnaam is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut         | verwachteWaarde |
| samengesteldeNaam  | 4      | geslachtsnaamstam | Vries           |

Scenario: 3. Voornaam wordt gewijzigd van Kindje van Johnny en Anita, check expressietaal, bericht wordt niet geleverd

Given leveringsautorisatie uit /levering_autorisaties/attendering_obv_wijziging_in_relatiegegevens_geslachtsnaam
Given de persoon beschrijvingen:
def Kindje = uitDatabase bsn: 564568041

Persoon.nieuweGebeurtenissenVoor(Kindje) {

    naamswijziging(aanvang:20150601, registratieDatum: 20150601) {
            geslachtsnaam(voorvoegsel:'van').wordt(voorvoegsel:'de')
    }
}
slaOp(Kindje)
When voor persoon 564568041 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie attendering obv wijziging in relatiegegevens - geslachtsnaam is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
