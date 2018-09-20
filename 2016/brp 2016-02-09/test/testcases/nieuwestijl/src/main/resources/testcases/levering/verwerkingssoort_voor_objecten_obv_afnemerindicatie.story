Meta:
@sprintnummer               78, 79
@epic                       Change 2015003: Geg.model relaties & betrokkenh.
@auteur                     luwid
@jiraIssue                  TEAMBRP-2708, TEAMBRP-3287, TEAMBRP-3777
@status                     Klaar
@regels                     VR00122, R1320

Narrative:  Als afnemer wil ik Verwerkingssoort ontvangen op objecten in MutatieBerichten,
            zodat ik die berichten eenvoudiger kan interpreteren/verwerken in mijn eigen systeem.

            R1320	VR00122	Bepalen verwerkingssoort van objecten

!-- Deze ART tests de levering op basis van afnemerindicatie.
!-- Voor levering op basis van doelbinding zie: verwerkingssoort_voor_objecten_leveren_obv_doelbinding.story.
!-- Door implementatie van TEAMBRP-3287 zijn sommige expecteds aangepast: bij huwelijk en scheiding wordt de verwerkingssoort van gerelateerde persoon
!-- 'Identificatie'

Scenario: 1. Anita is geboren, afnemerindicatie wordt geplaatst.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/attendering_met_plaatsing_afnemerindicatie
Given de persoon beschrijvingen:
def moederAnita = uitDatabase bsn: 306741817
def vaderAnita  = uitDatabase bsn: 306867837


Anita = uitGebeurtenissen {
    geboorte(partij: 36101, aanvang: 19960108, toelichting: '1e kind', registratieDatum: 19960108) {
        op '1996/01/08' te 'Giessenlanden' gemeente 689
        geslacht 'VROUW'
        namen {
            voornamen 'Anita', 'Goedemeid'
            geslachtsnaam 'Corner'
        }
        ouders moeder: moederAnita, vader: vaderAnita
        identificatienummers bsn: 754209878, anummer: 6248016146
    }
}
slaOp(Anita)

When voor persoon 754209878 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                     | attribuut                     | verwachteWaardes          |
| parameters                | effectAfnemerindicatie        | Plaatsing                 |


Scenario: 2. Johnny en Anita gaan een voltrekking huwelijk in Nederland aan, mutatiebericht wordt geleverd. Controle op de Verwerkingssoort van de
objecten.

Given de persoon beschrijvingen:
def John = Persoon.uitDatabase(bsn: 340014155)
def Anita = Persoon.uitDatabase(bsn: 754209878)
Persoon.nieuweGebeurtenissenVoor(Anita) {
    huwelijk(aanvang: 20140603, registratieDatum: 20140603) {
          op 20140603 te 'Rotterdam' gemeente 'Rotterdam'
          met John
    }
}
slaOp(Anita)

When voor persoon 754209878 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| partner           | 1         | Toevoeging        |
| huwelijk          | 1         | Toevoeging        |
| partner           | 2         | Toevoeging        |
| persoon           | 2         | Identificatie     |


Scenario: 3. Levering mutatiebericht na het veranderen van geslachtsnaam van de gerelateerde partner. Controle op de Verwerkingssoort van de objecten.

Given de persoon beschrijvingen:
def John = Persoon.uitDatabase(bsn: 340014155)
Persoon.nieuweGebeurtenissenVoor(John) {
    naamswijziging(aanvang: 20150613, registratieDatum: 20150613) {
          geslachtsnaam(1) wordt stam:'Zonnig', voorvoegsel:'het'
    }
}
slaOp(John)

When voor persoon 340014155 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen
When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| partner           | 1         | Referentie        |
| huwelijk          | 1         | Referentie        |
| partner           | 2         | Referentie        |
| persoon           | 2         | Wijziging         |


Scenario: 4. Levering mutatiebericht na scheiding. Controle op de Verwerkingssoort van de objecten.

Given de persoon beschrijvingen:
def John = Persoon.uitDatabase(bsn: 340014155)
def Anita = Persoon.uitDatabase(bsn: 754209878)
Persoon.nieuweGebeurtenissenVoor(Anita) {
    scheiding(aanvang: 20150615, registratieDatum: 20150615) {
        van John
        op(20150000).te('Groningen').gemeente('Groningen')
    }
}
slaOp(Anita)

When voor persoon 754209878 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| partner           | 1         | Referentie        |
| huwelijk          | 1         | Wijziging         |
| partner           | 2         | Referentie        |
| persoon           | 2         | Identificatie     |


Scenario: 5. Levering mutatiebericht na nietig verklaren van huwelijk. Controle op de Verwerkingssoort van de objecten.
TEAMBRP-3777 - Aanscherping verwerkingssoort Verval op objecten

Given de persoon beschrijvingen:
def John = Persoon.uitDatabase(bsn: 340014155)
def Anita = Persoon.uitDatabase(bsn: 754209878)
Persoon.nieuweGebeurtenissenVoor(Anita) {
    nietigVerklaringHuwelijk(aanvang: 20150620, registratieDatum: 20150620) {
    }
}
slaOp(Anita)

When voor persoon 754209878 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| partner           | 1         | Verval            |
| huwelijk          | 1         | Verval            |
| partner           | 2         | Verval            |
| persoon           | 2         | Identificatie     |


Scenario: 6. Anita is verhuisd, mutatiebericht wordt geleverd. Controle op de Verwerkingssoort van de objecten.

Given de persoon beschrijvingen:
def Anita = Persoon.uitDatabase(bsn: 754209878)
Persoon.nieuweGebeurtenissenVoor(Anita) {
     verhuizing(aanvang: 20150702, registratieDatum: 20150702) {
         binnenGemeente straat: 'Strateling', nummer: 13, postcode: '3572XX', woonplaats: "Smalle Ee"
    }
}
slaOp(Anita)

When voor persoon 754209878 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Attendering met plaatsing afnemerindicatie is ontvangen en wordt bekeken

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| adres             | 1         | Toevoeging        |
| adres             | 2         | Wijziging         |
| adres             | 3         | Verval            |
