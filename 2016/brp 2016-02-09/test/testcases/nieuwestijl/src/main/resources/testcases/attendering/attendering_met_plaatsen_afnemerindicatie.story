Meta:
@sprintnummer           69
@epic                   Attendering
@auteur                 devel
@jiraIssue              TEAMBRP-2268
@status                 Klaar
@regels                 VR00108
@sleutelwoorden         attendering, plaatsenAfnemerindicatie, duplicaat_van_attendering_met_plaatsen_afnemerindicatie.story_in_SA.0.AA_Attendering_met_plaatsen_afnemerindicatie

Narrative:
    Afnemer heeft diensten 'Mutatielevering op afnemerindicatie' en 'Attendering met plaatsen afnemerindicatie'.
    Het attenderingscriterium is ingesteld op een wijziging van de bijhoudingsgemeente. De populatiebeperking
    eist dat de geboorte.woonplaatsnaam gelijk is aan "Giessenlanden", om leveringen op andere testcases uit te sluiten.
    Bijhouding 1: Persoon komt binnen doelbindinffg. Attendering plaatst een afnemerindicatie en stuurt een VolledigBericht
    Bijhouding 2: De betreffende persoon wordt nogmaals bijgehouden maar blijft binnen de doelbinding. Er wordt enkel een MutatieBericht verzonden

Scenario: 1. Bijhouding 1, persoon wordt geboren, persoon komt in doelbinding en afnemerindicatie wordt geplaatst

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie
Given de personen 767618439 zijn verwijderd
Given de database is gereset voor de personen 306867837, 306741817
Given de persoon beschrijvingen:
def adam    = Persoon.uitDatabase(bsn: 306867837)
def eva     = Persoon.uitDatabase(bsn: 306741817)

tester = uitGebeurtenissen {
    geboorte(partij: 34401, aanvang: 19911111, toelichting: '1e kind', registratieDatum: 19911111) {
        op '1991/11/11' te 'Giessenlanden' gemeente 689
        geslacht 'MAN'
        namen {
            voornamen 'Petrus', 'Matheus'
            geslachtsnaam 'Smith'

        }
        ouders moeder: eva, vader: adam
        identificatienummers bsn: 767618439, anummer: 4030342674
    }
}

slaOp(tester)

When voor persoon 767618439 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                     | attribuut                     | verwachteWaardes          |
| parameters                | dienstIdentificatie           | 1                         |
| parameters                | effectAfnemerindicatie        | Plaatsing                 |

Scenario: 2. Bijhouding 2, Persoon wordt gewijzigd, blijft binnen doelbinding, afnemerindicatie bestaat reeds

Given de persoon beschrijvingen:

tester2 = Persoon.uitDatabase(bsn: 767618439)

Persoon.nieuweGebeurtenissenVoor(tester2) {
    verhuizing(partij: 'Gemeente Gorinchem') {
        naarGemeente 'Gorinchem',
           straat: 'Dorpstraat', nummer: 13, postcode: '4207AA', woonplaats: "Gorinchem"
    }
}

slaOp(tester2)

When voor persoon 767618439 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                     | attribuut                     | verwachteWaardes                                  |
| administratieveHandeling  | categorie                     | Actualisering                                     |

When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
