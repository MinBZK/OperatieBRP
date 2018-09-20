Meta:
@sprintnummer               74
@epic                       Afhankelijkheden migratie
@auteur                     dihoe
@jiraIssue                  TEAMBRP-2915
@status                     Klaar

Narrative:
        Als beheerder
        wil ik de actuele Datum Aanvang Geldigheid van een groep kunnen gebruiken in expressies
        zodat ik bestaande GBA voorwaarderegels kan converteren.
         Scenario 1. Met de nieuwe expressie in abo "abo attendering obv datum aanvang geldigheid" wordt alleen geleverd als
         datum aanvang geldigheid bij een verhuizing voldoet aan de expressie: Persoon.Adres.DatumAanvangGeldigheid = 20150601

Scenario: 1. Een Attendering is gemaakt die een bericht verstuurt wanneer een DatumAanvangGeldigheid is bijgehouden

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_datum_aanvang_geldigheid
Given de persoon beschrijvingen:
def moederJohnny   = uitDatabase bsn: 64258099
def vaderJohnny    = uitDatabase bsn: 126477735

Johnny = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19901225, toelichting: '1e kind', registratieDatum: 19901225) {
        op '1995/12/25' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Johnny', 'James'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederJohnny, vader: vaderJohnny
        identificatienummers bsn: 556645959, anummer: 5607075602
    }
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20150601, registratieDatum: 20150601) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 50, postcode: '2180AA', woonplaats: "Hillegom"
    }
}
slaOp(Johnny)

When voor persoon 556645959 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie mutatielevering obv datum aanvang geldigheid is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut                | verwachteWaarde |
| adres | 1      | datumAanvangAdreshouding | 2015-06-01      |

Scenario: 2. Een Populatiebeperking is gemaakt die een bericht verstuurd wanneer de actuele waarde van een DatumAanvangGeldigheid wordt gebruikt

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_datum_aanvang_geldigheid
Given de persoon beschrijvingen:
def moederJohnny   = uitDatabase bsn: 64258099
def vaderJohnny    = uitDatabase bsn: 126477735

Johnny = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19901225, toelichting: '1e kind', registratieDatum: 19901225) {
        op '1995/12/25' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Johnny', 'James'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederJohnny, vader: vaderJohnny
        identificatienummers bsn: 556645959, anummer: 5607075602
    }
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20150601, registratieDatum: 20150601) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 50, postcode: '2180AA', woonplaats: "Hillegom"
    }
}
slaOp(Johnny)

When voor persoon 556645959 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie mutatielevering obv datum aanvang geldigheid is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut                | verwachteWaarde |
| adres | 1      | datumAanvangAdreshouding | 2015-06-01      |

Scenario: 3. Wanneer de actuele waarde van een DatumAanvangGeldigheid niet voldoet aan de expressie dan wordt geen bericht verstuurd

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_datum_aanvang_geldigheid
Given de persoon beschrijvingen:
def moederJohnny   = uitDatabase bsn: 64258099
def vaderJohnny    = uitDatabase bsn: 126477735

Johnny = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19901225, toelichting: '1e kind', registratieDatum: 19901225) {
        op '1995/12/25' te 'Delft' gemeente 503
        geslacht 'MAN'
        namen {
            voornamen 'Johnny', 'James'
            geslachtsnaam 'Jansen'

        }
        ouders moeder: moederJohnny, vader: vaderJohnny
        identificatienummers bsn: 556645959, anummer: 5607075602
    }
    verhuizing(partij: 'Gemeente Rotterdam', aanvang: 20150602, registratieDatum: 20150602) {
                naarGemeente 'Rotterdam',
                    straat: 'Havenstraat', nummer: 83, postcode: '3200AA', woonplaats: "Rotterdam"
    }
}
slaOp(Johnny)

When voor persoon 556645959 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen

When volledigbericht voor leveringsautorisatie mutatielevering obv datum aanvang geldigheid wordt bekeken
Then is er geen synchronisatiebericht gevonden




