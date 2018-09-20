Meta:
@sprintnummer               79
@epic                       Change 2015003: Geg.model relaties & betrokkenh.
@auteur                     dadij
@jiraIssue                  TEAMBRP-3287
@regels                     VR00122, R1320
@status                     Klaar

Narrative:  Verwerkingssoort op persoon van betrokken kind.
R1320	VR00122	Bepalen verwerkingssoort van objecten

Scenario: 0. Afnemerindicatie wordt geplaatst.

Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie
Given de personen 274289465, 795375177 zijn verwijderd
Given de persoon beschrijvingen:
Anita = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19960108, toelichting: '1e kind', registratieDatum: 19960108) {
        op '1996/01/08' te 'Giessenlanden' gemeente 689
        geslacht 'VROUW'
        namen {
            voornamen 'Anita', 'Goedemeid'
            geslachtsnaam 'Corner'
        }
        ouders ()
        identificatienummers bsn: 274289465, anummer: 3715863826
    }
}
slaOp(Anita)

When voor persoon 274289465 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                     | attribuut                     | verwachteWaardes          |
| parameters                | effectAfnemerindicatie        | Plaatsing                 |


Scenario: 1. Kind wordt geboren, ouder krijgt betrokken kind persoon verwerkingssoort Toevoeging.

Given de persoon beschrijvingen:
def Anita = uitDatabase(bsn: 274289465)

Patty = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 20140108, toelichting: '1e kind', registratieDatum: 20140108) {
        op '2014/01/08' te 'Niet Giessenlanden' gemeente 689
        geslacht 'VROUW'
        namen {
            voornamen 'Patty'
            geslachtsnaam 'Corner'
        }
        ouders moeder: Anita
        identificatienummers bsn: 795375177, anummer: 7296596754
    }
}
slaOp(Patty)

When voor persoon 795375177 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                    | attribuut           | verwachteWaardes      |
| administratieveHandeling | naam                | Geboorte in Nederland |
| identificatienummers     | burgerservicenummer | 274289465, 795375177  |
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                    | nummer              | verwerkingssoort      |
| persoon                  | 2                   | Toevoeging            |


Scenario: 2. Kind wijzigt geslachtsaanduiding, ouder krijgt geen bericht. Het bericht van de laatste handeling voor de ouder is van de geboorte.

Given de persoon beschrijvingen:
Patty = Persoon.uitDatabase(bsn: 795375177)
nieuweGebeurtenissenVoor(Patty) {
    geslachtswijziging(aanvang: 20150101,registratieDatum: 20150101) {
        geslacht 'MAN'
    }
}
slaOp(Patty)

When voor persoon 795375177 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden


Scenario: 3. Kind wijzigt geslachtsnaam, ouder krijgt betrokken kind persoon verwerkingssoort Wijziging.

Given de persoon beschrijvingen:
Patty = Persoon.uitDatabase(bsn: 795375177)
nieuweGebeurtenissenVoor(Patty) {
    naamswijziging(aanvang: 20150202,registratieDatum: 20150202) {
        geslachtsnaam(stam: 'Corner').wordt(stam: 'Meyer')
    }
}
slaOp(Patty)

When voor persoon 795375177 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                    | attribuut           | verwachteWaardes        |
| administratieveHandeling | naam                | Wijziging geslachtsnaam |
| identificatienummers     | burgerservicenummer | 274289465, 795375177    |

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                    | nummer              | verwerkingssoort        |
| persoon                  | 2                   | Wijziging               |

