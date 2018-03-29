Meta:
@status Klaar
@driverSoort phantomjs
@binaryPath phantomjs.binary
@closeDriver true

Narrative:
Als beheerder wil ik de in te plannen selectietaken kunnen raadplegen


Scenario:   0. Taken met verschillende statussen toevoegen aan de db als preconditie voor overige scenarios
            Uitwerking:
            Taak met status gepland voor scenario om de filter Reeds gepland te checken
            Taak met status uitvoerbaar voor scenario om de filter Reeds gepland te checken is nog buiten scope dit moet nog gebouwd worden!


Given alle selectietaken zijn verwijderd
Given een selectierun met de volgende selectie taken:
| datplanning | status      | indsellijstgebruiken | dienstSleutel                 |
| 20170601    | Afgekeurd   | false                | SelectieStandaardGeenInterval |




Scenario:   1. Standaard zoekperiode en filters checken op het zoekscherm selectie
            Doel: Check of de standaard zoekperiode correct wordt getoond (Begin 1 maand voor vandaag Eind 3 maanden na vandaag)
            Check op filtering op Opnieuw in te plannen
            Check op filtering Dagelijks icm Opnieuw in te plannen


Given er is een verbinding met de applicatie
Then wordt de standaard zoekperiode correct getoond

When begindatum is aangepast naar 01-06-2017
And einddatum is aangepast naar 02-06-2017

When er op zoeken wordt geklikt
Then is er 0 selectietaak aanwezig

When er op zoeken wordt geklikt
And de volgende checkboxen worden aangevinkt:
|id
|opnieuw-te-plannen

!-- NB: selectie taken die status In te plannen hebben waarvan de datum in het verleden liggen komen ook in het overzicht 'opnieuw-te-plannen'
Then is er 4 selectietaak aanwezig


Scenario:   2. Filtering dagelijkse terugkerend niet standaard in het overzicht
            LT: BS.1.RI_002_LT01, BS.1.RI_002_LT02
            Doel:
            Check op filtering Dagelijks terugkerend
            Uitwerking:
            Op 30-6-2018 is 1 selectietaak om in te plannen, deze valt echter onder een dagelijks terugkerende selectie
            Zonder filter Dagelijs terugkerend wordt de taak niet getoond, met de filter aan wordt de taak wel getoond.

Given er is een verbinding met de applicatie

When begindatum is aangepast naar 30-06-2018
And einddatum is aangepast naar 01-07-2018
When er op zoeken wordt geklikt
Then wacht tot meldingstekst 'Er zijn geen in te plannen selecties gevonden.' aanwezig is
Then is er 0 selectietaak aanwezig
Then is de melding met tekst 'Er zijn geen in te plannen selecties gevonden.' aanwezig


When er op zoeken wordt geklikt
And de volgende checkboxen worden aangevinkt:
|id
|dagelijks-terugkerend
Then is er 1 selectietaak aanwezig



Scenario:   3. Filtering Reeds Geplande taken tonen in het overzicht
            LT: BS.1.RI_003_LT01, BS.1.RI_003_LT02
            Doel:
            Check op toevoeging Reeds geplande taken
            Uitwerking:
            Op 30-6-2019 is 1 selectietaak reeds gepland, deze wordt in scenario 0 toegevoegd als testdata

Given er is een verbinding met de applicatie

When begindatum is aangepast naar 30-06-2019
And einddatum is aangepast naar 01-07-2019
When er op zoeken wordt geklikt
Then wacht tot meldingstekst 'Er zijn geen in te plannen selecties gevonden.' aanwezig is

!-- BS.1.RI_003_LT01
Then is er 0 selectietaak aanwezig
Then is de melding met tekst 'Er zijn geen in te plannen selecties gevonden.' aanwezig


When er op zoeken wordt geklikt
And de volgende checkboxen worden aangevinkt:
|id
|dagelijks-terugkerend
|reeds-gepland

!-- BS.1.RI_003_LT02
Then is er 1 selectietaak aanwezig



